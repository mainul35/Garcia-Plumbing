/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.LabourDetails;
import com.garciaPlumbing.view.util.StringProcessor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author FURIOUS
 */
public class Layout_AddLabour extends GridPane{
    Label lblLabourName;
    Label lblLabourEmail;
    Label lblLabourPassword;
    Label lblAvailability;
    Label lblHourlyRate;
    
    TextField txtLabourName;
    TextField txtLabourEmail;
    TextField txtHourlyRate;
    
    PasswordField pswLabourPassword;
    ComboBox<String> cmbAvailability;
    
    Button btnSave;

    public Layout_AddLabour() {
        lblAvailability = new Label("Availability Status");
        lblHourlyRate = new Label("Hourly Rate");
        lblLabourEmail = new Label("Email Address");
        lblLabourName = new Label("Full Name");
        lblLabourPassword = new Label("Password");
        
        txtHourlyRate = new TextField();
        txtLabourEmail = new TextField();
        txtLabourName = new TextField();
        
        pswLabourPassword = new PasswordField();
        cmbAvailability = new ComboBox<>();
        cmbAvailability.getItems().addAll("Yes", "No");
        cmbAvailability.setValue("Yes");
        btnSave = new Button("Save");
        btnSave.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                String labourName = txtLabourName.getText();
                String email = txtLabourEmail.getText();
                String password = pswLabourPassword.getText();
                double perHourRate = 0;
                String available = cmbAvailability.getSelectionModel().getSelectedItem().toString();
                if(labourName.equals("")){
                    BasicLayout.showMessageDialog("Please insert labour name.");
                }else if(email.equals("")){
                    BasicLayout.showMessageDialog("Please insert labour email.");
                }else if(!StringProcessor.isEmail(email)){
                    BasicLayout.showMessageDialog("email id is not valid.");
                }else if(password.equals("")){
                    BasicLayout.showMessageDialog("Please insert labour password.");
                }else if(!Layout_AddFitting.isNumeric(txtHourlyRate.getText().trim()) || txtHourlyRate.getText().trim().equals("")){
                    BasicLayout.showMessageDialog("Hourly rate is not in numeric format.");
                }else{
                    BasicLayout.showMessageDialog("Labour has been added successfully.");
                    perHourRate = Double.parseDouble(txtHourlyRate.getText().trim());
                    LabourDetails labourDetails = new LabourDetails(labourName, email, password, perHourRate, available);
                }
            }
        });
        this.setVgap(10);
        this.setHgap(10);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        
        lblAvailability.setPrefWidth(120);
        lblHourlyRate.setPrefWidth(120);
        lblLabourEmail.setPrefWidth(120);
        lblLabourName.setPrefWidth(120);
        lblLabourPassword.setPrefWidth(120);
        txtHourlyRate.setPrefWidth(180);
        txtLabourEmail.setPrefWidth(180);
        txtLabourName.setPrefWidth(180);
        cmbAvailability.setPrefWidth(180);
        pswLabourPassword.setPrefWidth(180);
        pswLabourPassword.setPrefHeight(25);
        btnSave.setPrefWidth(100);
        
        this.add(lblLabourName, 1, 1);
        this.add(lblLabourEmail, 1, 2);
        this.add(lblLabourPassword, 1, 3);
        this.add(lblAvailability, 1, 4);
        this.add(lblHourlyRate, 1, 5);
        
        this.add(txtLabourName, 2, 1);
        this.add(txtLabourEmail, 2, 2);
        this.add(pswLabourPassword, 2, 3);
        this.add(cmbAvailability, 2, 4);
        this.add(txtHourlyRate, 2, 5);
        this.add(btnSave, 2, 6);
        this.setStyle("-fx-border-color: black;");
    }
}
