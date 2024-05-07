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

public class TourViewController {

    @FXML
    private TableColumn<?, ?> DestinationCol;

    @FXML
    private TableView<Tour> TourTable;
    @FXML
    private TableColumn<Tour, Integer> Tour_IDCol;
    @FXML
    private TableColumn<Tour, String> DurationCol;
    @FXML
    private TableColumn<Tour, String> GuideNameCol;
    @FXML
    private TableColumn<Tour, String> LocationCol;
    @FXML
    private TableColumn<Tour, Double> PriceCol;
    @FXML
    private TableColumn<Tour, String> SiteNameCol;
    @FXML
    private TableColumn<Tour, Date> StartTimeCol;
    @FXML
    private TableColumn<Tour, Date> Tour_DateCol;
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
    public void initialize() {
        Tour_IDCol.setCellValueFactory(new PropertyValueFactory<>("tourId"));
        DestinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        DurationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        GuideNameCol.setCellValueFactory(new PropertyValueFactory<>("guideName"));
        LocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        SiteNameCol.setCellValueFactory(new PropertyValueFactory<>("siteName"));
        StartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        Tour_DateCol.setCellValueFactory(new PropertyValueFactory<>("tourDate"));
        loadTableData();
    }
    private void loadTableData() {
        ObservableList<Tour> tourData = FXCollections.observableArrayList();
        Declaration dc = new Declaration(); // Ensure this contains your database connection details

        try (Connection conn = DriverManager.getConnection(dc.conn, dc.user, dc.pass);
             PreparedStatement stmt = conn.prepareStatement("SELECT * From Trips_View");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tourData.add(new Tour(
                        rs.getInt("tour_Id"),
                        rs.getString("destination"),
                        rs.getString("duration"),
                        rs.getString("guideName"),
                        rs.getString("location"),
                        rs.getDouble("price"),
                        rs.getString("site_Name"),
                        rs.getDate("start_Time"),
                        rs.getDate("tour_Date")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving tour data: " + ex.getMessage());
            ex.printStackTrace();
        }

        TourTable.setItems(tourData);
    }

}
