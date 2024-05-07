package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.sql.*;

public class CustomerReservationsController {
    int Customer_ID;
    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TableColumn<Reservation, Date> Booking_DateCol;

    @FXML
    private TableColumn<Reservation, String> DestinationCol;

    @FXML
    private TableColumn<Reservation, String> DurationCol;

    @FXML
    private TableColumn<Reservation, Double> PriceCol;

    @FXML
    private TableColumn<Reservation, String> Site_nameCol;

    @FXML
    private TableColumn<Reservation, String> StatusCol;

    @FXML
    private TableColumn<Reservation, Date> Tour_DateCol;
    @FXML
    private Text TextCustomerID;

    void setCustomer_ID(int customer_ID){
        Customer_ID=customer_ID;
        TextCustomerID.setText(""+Customer_ID);
        loadReservations();
    }

    @FXML
    public void initialize() {
        Booking_DateCol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        DestinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        DurationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        Site_nameCol.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        StatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        Tour_DateCol.setCellValueFactory(new PropertyValueFactory<>("tourDate"));




    }
    private void loadReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        String sql = "SELECT STATUS, BOOKING_DATE, TOURTRIP.DESTINATION, TOUR_DATE, SITE_NAME, PRICE, DURATION FROM RESERVATION " +
                "JOIN RESERVATION_BOOKING ON RESERVATION.RESERVATION_ID=RESERVATION_BOOKING.RESERVATION_ID " +
                "JOIN TOURTRIP ON TOURTRIP.TOUR_ID=RESERVATION_BOOKING.TOUR_ID " +
                "WHERE CUSTOMER_ID = ?";
        Declaration dc = new Declaration();
        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("Querying database for Customer ID: " + Customer_ID);
            pstmt.setInt(1, Customer_ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Date bookingDate = rs.getDate("BOOKING_DATE");
                String destination = rs.getString("DESTINATION");
                Date tourDate = rs.getDate("TOUR_DATE");
                String siteName = rs.getString("SITE_NAME");
                Double price = rs.getDouble("PRICE");
                String duration = rs.getString("DURATION");
                String status = rs.getString("STATUS");

                reservations.add(new Reservation(bookingDate, destination, duration, price, siteName, status, tourDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        reservationsTable.setItems(reservations);
    }
}

