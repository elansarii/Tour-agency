package com.example.demo2;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddcustomerViewControllerBackup {

    @FXML
    private TextField NameField;

    @FXML
    private TextField PassportField;

    @FXML
    private TextField PhoneField;

    @FXML
    private TextField ResidencyField;
    @FXML
    private TextField CustomerIDField;
    Customer customer=null;
    @FXML
    void Cancel(ActionEvent event) {

    }

    @FXML
    void Submit(ActionEvent event) {
          customer = new Customer(NameField.getText(),PassportField.getText(),ResidencyField.getText(),PhoneField.getText());
        addCustomer(customer);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // Refresh the table after successful addition

    }
    private void addCustomer(Customer newCustomer) {
        Task<Boolean> addTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return addCustomerToDatabase(newCustomer);
            }
        };
        new Thread(addTask).start();
    }

    private boolean addCustomerToDatabase(Customer customer) throws SQLException {
        Declaration dc = new Declaration();
        Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass);
             PreparedStatement stmtCustomer = conn.prepareStatement("INSERT INTO CUSTOMER (Passport, RESIDENCY, CUSTOMER_ID, PHONE,NAME) VALUES (?, ?, CUSTOMERID_SEQ.NEXTVAL, ?,?)");
            stmtCustomer.setString(5, customer.getName());
        stmtCustomer.setString(1, customer.getPassport());
        stmtCustomer.setString(2, customer.getResidency());
        stmtCustomer.setString(4, customer.getPhone());
            int affectedRows = stmtCustomer.executeUpdate();
        PreparedStatement stmtReservation = conn.prepareStatement("INSERT INTO RESERVATION (STATUS, RESERVATION_ID, BOOKING_DATE, CUSTOMER_ID, AGENT_ID) VALUES (?, RESERVEID_SEQ.NEXTVAL, ?, ?, ?)");

        return true;
        }

    Customer getCustomer() {

            return customer;


    }

}
