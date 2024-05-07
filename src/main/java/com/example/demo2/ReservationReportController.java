package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ReservationReportController {

    @FXML
    private TableColumn<Reservation, Integer> Agent_IDCol;

    @FXML
    private TableColumn<Reservation, Date> Booking_DateCol;

    @FXML
    private TableColumn<Reservation, Integer> Customer_IDCol;
    @FXML
    private TableView<Reservation> ReservationTable;
    @FXML
    private TableColumn<Reservation, Integer> Reservation_IDCol;

    @FXML
    private TableColumn<Reservation, Integer> StatusCol;

    @FXML
    void Back2Previous(ActionEvent event) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/menu2-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

    }
    @FXML
    private void initialize() {

        Reservation_IDCol.setCellValueFactory(new PropertyValueFactory<>("reservation_ID"));
        Agent_IDCol.setCellValueFactory(new PropertyValueFactory<>("agent_ID"));
        Customer_IDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        Booking_DateCol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        StatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadTableData();
    }
    private void loadTableData() {
        ObservableList<Reservation> reservationData = FXCollections.observableArrayList();

        Declaration dc = new Declaration();
        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RESERVATIONVIEW");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservationData.add(new Reservation(
                        rs.getString("STATUS"),
                        rs.getInt("RESERVATION_ID"),
                        rs.getInt("AGENT_ID"),
                        rs.getInt("CUSTOMER_ID"),
                        rs.getDate("BOOKING_DATE")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving data: " + ex.getMessage());
            ex.printStackTrace();
        }

        ReservationTable.setItems(reservationData);
    }




}
