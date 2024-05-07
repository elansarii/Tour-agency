package com.example.demo2;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class AddcustomerViewController {

    public DatePicker DatePKRReservation;
    @FXML
    private TextField NameField;

    @FXML
    private TextField PassportField;

    @FXML
    private TextField PhoneField;

    @FXML
    private TextField ResidencyField;

    Customer customer=null;

    int Agent_ID;

    @FXML
    void Cancel(ActionEvent event) {

    }
    void setAgent_ID(int Agent_ID){
        this.Agent_ID=Agent_ID;
    }

    @FXML
    void Submit(ActionEvent event) {
          customer = new Customer(NameField.getText(),PassportField.getText(),ResidencyField.getText(),PhoneField.getText());



        addCustomer(customer);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

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
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass);
            String insertCustomerSQL = "INSERT INTO CUSTOMER (Passport, RESIDENCY, CUSTOMER_ID, PHONE, NAME) VALUES (?, ?, CUSTOMERID_SEQ.NEXTVAL, ?, ?)";


            try (PreparedStatement stmt = conn.prepareStatement(insertCustomerSQL)) {
                stmt.setString(1, customer.getPassport());
                stmt.setString(2, customer.getResidency());
                stmt.setString(3, customer.getPhone());
                stmt.setString(4, customer.getName());
                int affectedRowsCustomer = stmt.executeUpdate();

                if (affectedRowsCustomer == 0) {
                    throw new SQLException("Failed to add customer, no rows affected.");
                }
            }


            int customerId = 0;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT CUSTOMER_ID FROM CUSTOMER WHERE Passport = ?")) {
                stmt.setString(1, customer.getPassport());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    customerId = rs.getInt("CUSTOMER_ID");
                } else {
                    throw new SQLException("Failed to retrieve customer ID.");
                }
            }
            System.out.println(Agent_ID);

            String insertReservationSQL = "INSERT INTO RESERVATION (STATUS, BOOKING_DATE, CUSTOMER_ID, AGENT_ID, RESERVATION_ID) VALUES (?, ?, ?, ?, RESERVEID_SEQ.NEXTVAL)";
            try (PreparedStatement stmtReservation = conn.prepareStatement(insertReservationSQL)) {
                stmtReservation.setString(1, "Confirmed");
                Date reservationDate = Date.valueOf(DatePKRReservation.getValue());
                stmtReservation.setDate(2, reservationDate);
                stmtReservation.setInt(3, customerId);
                stmtReservation.setInt(4, Agent_ID);
                int affectedRowsReservation = stmtReservation.executeUpdate();
                if (affectedRowsReservation == 0) {
                    throw new SQLException("Failed to add reservation, no rows affected.");
                }
            }

            return true;
        } catch (SQLException e) {
            throw new SQLException("Database operation failed", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    throw new SQLException("Failed to close the connection", ex);
                }
            }
        }
    }

    int getAgent_ID(){
        return this.Agent_ID;
    }




    Customer getCustomer() {

            return customer;


    }

}
