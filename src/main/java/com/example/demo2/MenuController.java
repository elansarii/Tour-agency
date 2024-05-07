package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.io.IOException;
import java.sql.*;


public class MenuController {
    int Agent_ID;
    private TextField startTimeField;
    private TextField destinationField;
    private TextField priceField;
    private TextField tourDateField;
    private TextField durationField;
    private TextField locationField;
    private TextField guideIDField;
    private TextField siteNameField;
    private TextField tourIDField;
    @FXML
    void SwitchToCustomerView(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/customer-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        CustomerViewController next = loader.getController();
        next.setAgent_ID(Agent_ID);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void SwitchToReportsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/menu2-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void SwitchToToursView(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Tour Trip Management");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(11, 11, 11, 11));
        grid.setVgap(8);
        grid.setHgap(10);

        Label startTimeLabel = new Label("Start Time:");
        GridPane.setConstraints(startTimeLabel, 0, 0);
        startTimeField = new TextField();
        startTimeField.setPromptText("HH:MM:SS");
        GridPane.setConstraints(startTimeField, 1, 0);

        Label destinationLabel = new Label("Destination:");
        GridPane.setConstraints(destinationLabel, 0, 1);
        destinationField = new TextField();
        GridPane.setConstraints(destinationField, 1, 1);

        Label priceLabel = new Label("Price:");
        GridPane.setConstraints(priceLabel, 0, 2);
        priceField = new TextField();
        GridPane.setConstraints(priceField, 1, 2);

        Label tourDateLabel = new Label("Tour Date:");
        GridPane.setConstraints(tourDateLabel, 0, 3);
        tourDateField = new TextField();
        tourDateField.setPromptText("YYYY-MM-DD");
        GridPane.setConstraints(tourDateField, 1, 3);

        Label durationLabel = new Label("Duration:");
        GridPane.setConstraints(durationLabel, 0, 4);
        durationField = new TextField();
        GridPane.setConstraints(durationField, 1, 4);

        Label locationLabel = new Label("Location:");
        GridPane.setConstraints(locationLabel, 0, 5);
        locationField = new TextField();
        GridPane.setConstraints(locationField, 1, 5);

        Label guideIDLabel = new Label("Guide ID:");
        GridPane.setConstraints(guideIDLabel, 0, 6);
        guideIDField = new TextField();
        GridPane.setConstraints(guideIDField, 1, 6);



        Label siteNameLabel = new Label("Site Name:");
        GridPane.setConstraints(siteNameLabel, 0, 7);
        siteNameField = new TextField();
        GridPane.setConstraints(siteNameField, 1, 7);



        Button addTourTripButton = new Button("Add Tour Trip");
        GridPane.setConstraints(addTourTripButton, 0, 8);
        addTourTripButton.setOnAction(e -> addTourTrip());

        Button updateTourTripButton = new Button("Switch to Tour Update");
        GridPane.setConstraints(updateTourTripButton, 1, 8);

        updateTourTripButton.setOnAction(e -> updateTourGrid(primaryStage));


        Button backtoMain = new Button("Back to Main");
        GridPane.setConstraints(backtoMain, 2, 8);
        backtoMain.setOnAction(e -> {
            try {
                backToMenu(primaryStage);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });



        grid.getChildren().addAll(startTimeLabel, startTimeField, destinationLabel, destinationField, priceLabel, priceField,
                tourDateLabel, tourDateField, durationLabel, durationField, locationLabel, locationField,
                guideIDLabel, guideIDField, siteNameLabel, siteNameField, addTourTripButton, updateTourTripButton,backtoMain);

        Scene scene = new Scene(grid, 500, 450);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    private void addTourTrip() {

        Declaration dc = new Declaration();
        int tour_pk=0;
        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass)) {
            String pk_sql= "select MAX(Tour_ID) from TourTrip";
            PreparedStatement pk_statement = conn.prepareStatement(pk_sql);
            ResultSet rs = pk_statement.executeQuery();
            if (rs.next()) {
                tour_pk=  rs.getInt(1);
                tour_pk++;

            }

            String sql = "INSERT INTO TourTrip (Start_Time, Destination, Price, Tour_Date, Duration, Tour_ID, Location, Guide_ID, Site_Name) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setTime(1, Time.valueOf(startTimeField.getText()));
                statement.setString(2, destinationField.getText());
                statement.setDouble(3, Double.parseDouble(priceField.getText()));
                statement.setDate(4, Date.valueOf(tourDateField.getText()));
                statement.setInt(5, Integer.parseInt(durationField.getText()));
                statement.setInt(6, tour_pk);
                statement.setString(7, locationField.getText());
                statement.setInt(8, Integer.parseInt(guideIDField.getText()));
                statement.setString(9, siteNameField.getText());
                System.out.println(statement);
                statement.executeUpdate();
                System.out.println("Tour trip added successfully");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTourGrid(Stage primaryStage) {
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(11, 11, 11, 11));
        grid.setVgap(8);
        grid.setHgap(10);

        Label tourIDLabel = new Label("Tour ID:");
        GridPane.setConstraints(tourIDLabel, 0, 0);
        tourIDField = new TextField();
        GridPane.setConstraints(tourIDField, 1, 0);

        Button searchByIDButton = new Button("Search");
        GridPane.setConstraints(searchByIDButton, 2, 0);
        searchByIDButton.setOnAction(e -> retrieveTourTripByID(Integer.parseInt(tourIDField.getText())));


        Label startTimeLabel = new Label("Start Time:");
        GridPane.setConstraints(startTimeLabel, 0, 1);
        startTimeField = new TextField();
        startTimeField.setPromptText("HH:MM:SS");
        GridPane.setConstraints(startTimeField, 1, 1);

        Label destinationLabel = new Label("Destination:");
        GridPane.setConstraints(destinationLabel, 0, 2);
        destinationField = new TextField();
        GridPane.setConstraints(destinationField, 1, 2);

        Label priceLabel = new Label("Price:");
        GridPane.setConstraints(priceLabel, 0,3 );
        priceField = new TextField();
        GridPane.setConstraints(priceField, 1, 3);

        Label tourDateLabel = new Label("Tour Date:");
        GridPane.setConstraints(tourDateLabel, 0, 4);
        tourDateField = new TextField();
        tourDateField.setPromptText("YYYY-MM-DD");
        GridPane.setConstraints(tourDateField, 1, 4);

        Label durationLabel = new Label("Duration:");
        GridPane.setConstraints(durationLabel, 0, 5);
        durationField = new TextField();
        GridPane.setConstraints(durationField, 1, 5);

        Label locationLabel = new Label("Location:");
        GridPane.setConstraints(locationLabel, 0, 6);
        locationField = new TextField();
        GridPane.setConstraints(locationField, 1, 6);

        Label guideIDLabel = new Label("Guide ID:");
        GridPane.setConstraints(guideIDLabel, 0, 7);
        guideIDField = new TextField();
        GridPane.setConstraints(guideIDField, 1, 7);



        Label siteNameLabel = new Label("Site Name:");
        GridPane.setConstraints(siteNameLabel, 0, 8);
        siteNameField = new TextField();
        GridPane.setConstraints(siteNameField, 1, 8);



        Button updateTourTripButton = new Button("Update Tour Trip");
        GridPane.setConstraints(updateTourTripButton, 0, 9);
        updateTourTripButton.setOnAction(e -> updateTourTrip(Integer.parseInt(tourIDField.getText())));



        Button backtoMain = new Button("Back to Main");
        GridPane.setConstraints(backtoMain, 1, 9);
        backtoMain.setOnAction(e -> {
            try {
                backToMenu(primaryStage);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });


        grid.getChildren().addAll(tourIDLabel,tourIDField, searchByIDButton,startTimeLabel, startTimeField, destinationLabel, destinationField, priceLabel, priceField,
                tourDateLabel, tourDateField, durationLabel, durationField, locationLabel, locationField,
                guideIDLabel, guideIDField, siteNameLabel, siteNameField, updateTourTripButton,backtoMain);
        Scene scene = new Scene(grid, 500, 450);

        primaryStage.setScene(scene);
        primaryStage.show();


    }


    @FXML
    private void backToMenu(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    private void retrieveTourTripByID(int tourID) {
        // SELECT  Start_Time, Destination, Price,   Tour_Date, Duration,  Location, Guide_ID, Site_Name FROM TourTrip WHERE Tour_ID = 3;


        Declaration dc = new Declaration();
        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass)) {
            String sql= " SELECT  Start_Time, Destination, Price,   Tour_Date, Duration,  Location, Guide_ID, Site_Name FROM TourTrip WHERE Tour_ID =?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, tourID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {



                startTimeField.setText(rs.getTime(1).toString());
                destinationField.setText(rs.getString(2));
                priceField.setText(String.valueOf(rs.getDouble(3)));
                tourDateField.setText(rs.getDate(4).toString());
                durationField.setText(String.valueOf(rs.getInt(5)));
                locationField.setText(rs.getString(6));
                guideIDField.setText(String.valueOf(rs.getInt(7)));
                siteNameField.setText(rs.getString(8));

                System.out.println("Tour trip retrieved successfully");


            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    private void updateTourTrip(int tourID) {
        Declaration dc = new Declaration();
        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass)) {
            String sql = "UPDATE TourTrip SET Start_Time = ?, Destination = ?, Price = ?, Tour_Date = ?, Duration = ?, Location = ?, Guide_ID = ?, Site_Name = ? WHERE Tour_ID = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setTime(1, Time.valueOf(startTimeField.getText()));
                statement.setString(2, destinationField.getText());
                statement.setDouble(3, Double.parseDouble(priceField.getText()));
                statement.setDate(4, Date.valueOf(tourDateField.getText()));
                statement.setInt(5, Integer.parseInt(durationField.getText()));
                statement.setString(6, locationField.getText());
                statement.setInt(7, Integer.parseInt(guideIDField.getText()));
                statement.setString(8, siteNameField.getText());
                statement.setInt(9, tourID);


                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Tour trip updated successfully");
                } else {
                    System.out.println("No tour trip found with the provided ID");
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


