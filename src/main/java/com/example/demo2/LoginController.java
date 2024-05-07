package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
public class LoginController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField username_field;
    @FXML
    private TextField password_field;
    Login logininfo;
    @FXML

    public void onLoginButtonClick(ActionEvent actionEvent) {
        String query = "SELECT USERNAME,AGENT_ID FROM LOGIN WHERE USERNAME = ? AND PASSWORD = ? ";
        Declaration dc = new Declaration();
        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String username = username_field.getText();
            String password = password_field.getText();
            logininfo = new Login(username,password);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful for user: " + rs.getString("USERNAME") +", AGENT ID: "+rs.getString("AGENT_ID"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/menu-view.fxml"));
                logininfo.setAgent_ID(Integer.parseInt(rs.getString("AGENT_ID")));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                MenuController next = loader.getController();
                next.Agent_ID= logininfo.getAgent_ID();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                conn.close();
                rs.close();
                stmt.close();
            } else {
                System.out.println("Login failed. Please check your username and password.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Login getLogin(){
        return this.logininfo;
    }


}
