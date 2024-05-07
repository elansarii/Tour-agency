package com.example.demo2;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CustomerViewController {
    @FXML
    private TableView<Customer> tableView;
    @FXML
    private TableColumn<Customer, Integer> CustomerIDLV;
    @FXML
    private TableColumn<Customer, String> NameLV, PassportLV, PhoneLV, ResidencyLV;
    @FXML
    private TextField IDTextBox;
    int Agent_ID;
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        CustomerIDLV.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        NameLV.setCellValueFactory(new PropertyValueFactory<>("name"));
        PassportLV.setCellValueFactory(new PropertyValueFactory<>("passport"));
        ResidencyLV.setCellValueFactory(new PropertyValueFactory<>("residency"));
        PhoneLV.setCellValueFactory(new PropertyValueFactory<>("phone"));

        loadData();
        tableView.setItems(customers);
    }

    @FXML
    private void addCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/addcustomer-view.fxml"));
        Parent root = loader.load();
        AddcustomerViewController sceneController = loader.getController();

        Stage addStage = new Stage();
        addStage.setTitle("Add Customer");
        addStage.setScene(new Scene(root));
        sceneController.setAgent_ID(Agent_ID);
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.showAndWait();

        Customer c = sceneController.getCustomer();
        if (c != null) {
            if (customers.stream().noneMatch(customer -> customer.getCustomer_ID() == c.getCustomer_ID())) {
                customers.add(c);
            }
        }
    }
    @FXML
    private void updateCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/updatecustomer-view.fxml"));
        Parent root = loader.load();
        UpdateCustomerController sceneController = loader.getController();

        Stage addStage = new Stage();
        addStage.setTitle("Update Customer");
        addStage.setScene(new Scene(root));
        UpdateCustomerController UCController = loader.getController();
        Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
        System.out.println("Agent selected customer id: "+selectedCustomer.getCustomer_ID());
        UCController.setCustomer_ID(selectedCustomer.Customer_ID);
        UCController.getNameField().setText(selectedCustomer.getName());
        UCController.getPassportField().setText(selectedCustomer.getPassport());
        UCController.getPhoneField().setText(selectedCustomer.getPhone());
        UCController.getResidencyField().setText(selectedCustomer.getResidency());
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.showAndWait();
    }
    @FXML
    void SearchByID(ActionEvent event) {
        if (IDTextBox.getText().isEmpty()) {
            System.out.println("Error: No ID entered.");
            return;
        }

        try {

            int customerID = Integer.parseInt(IDTextBox.getText());
            System.out.println("Parsed customer ID: " + customerID);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/CustomerReservations.fxml"));
            Parent root = loader.load();
            CustomerReservationsController sceneController = loader.getController();


            sceneController.setCustomer_ID(customerID);
            System.out.println("Set customer ID in controller.");


            Stage addStage = new Stage();
            addStage.setTitle("Customer Reservations");
            addStage.initModality(Modality.APPLICATION_MODAL);
            addStage.setScene(new Scene(root));
            addStage.showAndWait();

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input. Please enter a numeric ID.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed to load the FXML file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
            e.printStackTrace();
        }
    }



    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo2/menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
    void setAgent_ID(int Agent_ID){
        this.Agent_ID=Agent_ID;
    }

    @FXML
    private void deleteCustomer(ActionEvent actionEvent) {
        Task<Boolean> deleteTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                int customerId = Integer.parseInt(IDTextBox.getText());
                return deleteCustomerFromDatabase(customerId);
            }
        };

        deleteTask.setOnSucceeded(event -> {
            boolean wasDeleted = deleteTask.getValue();
            if (wasDeleted) {
                Platform.runLater(this::loadData);
                showAlert(Alert.AlertType.INFORMATION, "Customer successfully deleted.");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No customer found with given ID.");
            }
        });

        deleteTask.setOnFailed(event -> {
            Throwable exc = deleteTask.getException();
            Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Failed to delete customer: " + exc.getMessage()));
        });

        new Thread(deleteTask).start();
    }

    private boolean deleteCustomerFromDatabase(int customerId) {
        Declaration dc = new Declaration();
        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM CUSTOMER WHERE customer_id = ?")) {
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error deleting customer: " + ex.getMessage());
            throw new RuntimeException("Error deleting customer", ex);
        }
    }



    @FXML
    private void refreshTable() {
        loadData();
        tableView.setItems(customers);
    }
    @FXML



    private void loadData() {
        customers.clear();
        Declaration dc = new Declaration();
        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CUSTOMER");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("CUSTOMER_ID"),
                        rs.getString("NAME"),
                        rs.getString("PASSPORT"),
                        rs.getString("RESIDENCY"),
                        rs.getString("PHONE")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving data: " + ex.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String content) {
        Alert alert = new Alert(type);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
