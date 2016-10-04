/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.FittingDetails;
import com.garciaPlumbing.model.LabourDetails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author FURIOUS
 */
public class Layout_ViewLabourDetails extends BorderPane {

    TableView<LabourDetails> tblLabourDetails = new TableView<>();
    ObservableList<LabourDetails> ol = null;
    TableColumn<LabourDetails, String> labourName = new TableColumn<>("Labour Name");
    TableColumn<LabourDetails, String> email = new TableColumn<>("Email");
    TableColumn<LabourDetails, Double> perHourRate = new TableColumn<>("Hourly Rate");
    TableColumn<LabourDetails, String> available = new TableColumn<>("Available");
    TextField txtLabourName = new TextField();
    TextField txtEmail = new TextField();
    TextField txtPerHourRate = new TextField();
    ComboBox<String> cmbAvailable = new ComboBox<>();
    String labourId = null;
    
    public Layout_ViewLabourDetails() {
        HBox hBox_TableContainer = new HBox();
        hBox_TableContainer.getChildren().add(0, getLabourTable());
        this.setCenter(hBox_TableContainer);
        this.setRight(getLayoutChangeTableValue());
    }

    TableView<LabourDetails> getLabourTable() {
        ol = LabourDetails.getListOfLabour();
        tblLabourDetails.setItems(ol);
        tblLabourDetails.setPrefWidth(920);

        labourName.setEditable(false);
        labourName.setCellValueFactory(new PropertyValueFactory<>("labourName"));
        labourName.setMinWidth(tblLabourDetails.getPrefWidth() / 4);

        email.setEditable(false);
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setMinWidth(tblLabourDetails.getPrefWidth() / 4);

        perHourRate.setEditable(false);
        perHourRate.setCellValueFactory(new PropertyValueFactory<>("perHourRate"));
        perHourRate.setMinWidth(tblLabourDetails.getPrefWidth() / 4);

        available.setEditable(false);
        available.setCellValueFactory(new PropertyValueFactory<>("available"));
        available.setMinWidth(tblLabourDetails.getPrefWidth() / 4);

        tblLabourDetails.getColumns().addAll(labourName, email, perHourRate, available);
        tblLabourDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LabourDetails>() {

            @Override
            public void changed(ObservableValue<? extends LabourDetails> ov, LabourDetails t, LabourDetails t1) {
                if (t1 != null) {
                    txtLabourName.setText(t1.getLabourName());
                    txtEmail.setText(t1.getEmail());
                    txtPerHourRate.setText(Double.toString(t1.getPerHourRate()));
                    cmbAvailable.setValue(t1.getAvailable());
                    labourId = t1.getLabourId();
                }
            }
        });

        return this.tblLabourDetails;
    }

    public VBox getLayoutChangeTableValue() {
        VBox vBox = new VBox();
        HBox buttonGroup = new HBox();
        Button btnUpdate = new Button("Update");
        Label lblUpdateDetails = new Label("Update Details");
        Label lblEmail = new Label("Email");
        Label lblLabourName = new Label("Labour Name");
        Label lblAvailable = new Label("Available");
        Label lblHourlyRate = new Label("Hourly Rate");

        lblUpdateDetails.setAlignment(Pos.CENTER);
        lblUpdateDetails.setMaxWidth(200);
        lblUpdateDetails.setPrefWidth(30);
        lblUpdateDetails.setFont(Font.font("Palatino Linotype", FontWeight.BOLD, 14));
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        buttonGroup.setSpacing(10);
        buttonGroup.setPadding(new Insets(10));
        btnUpdate.setStyle("-fx-background-color:#6495ED");
        vBox.setStyle("-fx-background-color:#AFEEEE");
        vBox.setAlignment(Pos.CENTER);

        txtLabourName.setMaxWidth(200);
        lblLabourName.setPrefWidth(200);
        lblAvailable.setPrefWidth(200);
        lblHourlyRate.setPrefWidth(200);
        lblEmail.setPrefWidth(200);
        txtEmail.setMaxWidth(200);
        btnUpdate.setPrefWidth(200);
        txtPerHourRate.setMaxWidth(200);
        cmbAvailable.setMaxWidth(200);
        cmbAvailable.getItems().addAll("Yes", "No");
        
        txtLabourName.setPrefHeight(30);
        txtPerHourRate.setPrefHeight(30);
        lblAvailable.setPrefHeight(30);
        lblEmail.setPrefHeight(30);
        lblHourlyRate.setPrefHeight(30);
        lblLabourName.setPrefHeight(30);
        txtEmail.setPrefHeight(30);
        cmbAvailable.setPrefHeight(30);
        btnUpdate.setPrefHeight(30);

        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                LabourDetails.updateLabourDetails(txtLabourName.getText(), txtEmail.getText(), cmbAvailable.getSelectionModel().getSelectedItem(), txtPerHourRate.getText(), labourId);
                try {
                    BasicLayout.borderPane.setCenter(new Layout_ViewLabourDetails());
                    Scene scene = new Scene(BasicLayout.borderPane);
                    BasicLayout.stage.setScene(scene);
                } catch (IllegalArgumentException e) {
                }
            }
        });
        
        buttonGroup.getChildren().addAll(btnUpdate);
        vBox.getChildren().addAll(
                lblUpdateDetails,
                lblLabourName,
                txtLabourName,
                lblEmail,
                txtEmail,
                lblAvailable,
                cmbAvailable,
                lblHourlyRate,
                txtPerHourRate,
                buttonGroup);
        return vBox;
    }

}
