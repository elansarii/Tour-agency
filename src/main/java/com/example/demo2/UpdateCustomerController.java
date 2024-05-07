package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCustomerController {

    public TextField ResidencyField;
    public TextField PassportField;
    public TextField PhoneField;
    public TextField NameField;
    int Customer_ID;
    public void Submit(ActionEvent actionEvent) throws SQLException {
        updateCustomer(Customer_ID);
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
    private void updateCustomer(int customerId) throws SQLException {
        System.out.println("The agent selecter: "+customerId);
        Declaration dc = new Declaration();
        String sql = "UPDATE CUSTOMER SET NAME = ?, PASSPORT = ?, PHONE = ?, RESIDENCY = ? WHERE CUSTOMER_ID = ?";
        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, NameField.getText());
            pstmt.setString(2, PassportField.getText());
            pstmt.setString(3, PhoneField.getText());
            pstmt.setString(4, ResidencyField.getText());
            pstmt.setInt(5, customerId);

            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println(updatedRows + " row(s) updated.");
            } else {
                System.out.println("No rows updated. Check if the customer ID exists.");
            }
        }
    }


    public void Cancel(ActionEvent actionEvent) {
    }

    public TextField getResidencyField() {
        return ResidencyField;
    }

    public void setResidencyField(TextField residencyField) {
        ResidencyField = residencyField;
    }

    public TextField getPassportField() {
        return PassportField;
    }

    public void setPassportField(TextField passportField) {
        PassportField = passportField;
    }

    public TextField getPhoneField() {
        return PhoneField;
    }

    public void setPhoneField(TextField phoneField) {
        PhoneField = phoneField;
    }

    public TextField getNameField() {
        return NameField;
    }

    public void setNameField(TextField nameField) {
        NameField = nameField;
    }
    public void setCustomer_ID(int customer_ID){
        Customer_ID=customer_ID;

    }
}
