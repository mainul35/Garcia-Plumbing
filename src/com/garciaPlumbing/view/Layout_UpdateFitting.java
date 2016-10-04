/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.FittingDetails;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author FURIOUS
 */
public class Layout_UpdateFitting extends GridPane{
    
    private Label lblFittingName, lblSelectType, lblRetailPrice;
    private TextField txtFittingName, txtRetailPrice;
    private ComboBox<String> cmbSelectFittingType = new ComboBox<>();
    private Button btnSave;
    
    public Layout_UpdateFitting() {
        lblFittingName = new Label("Fitting Name");
        lblSelectType = new Label("Select Type");
        lblRetailPrice = new Label("Retail price");
        txtFittingName = new TextField();
        txtRetailPrice = new TextField();
        cmbSelectFittingType = new ComboBox<>();
        btnSave = new Button("Update");
        
        this.setVgap(10);
        this.setHgap(10);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        
        lblFittingName.setPrefWidth(120);
        lblRetailPrice.prefWidth(120);
        lblSelectType.setPrefWidth(120);        
        txtFittingName.setPrefWidth(180);
        txtRetailPrice.setPrefWidth(180);
        cmbSelectFittingType.setPrefWidth(180);
        
        ArrayList<String> al = new FittingDetails().getAllFittingTypeNames();
        for (String col : al) {
            cmbSelectFittingType.getItems().add(col);
        }
        
        btnSave.setPrefWidth(100);
        btnSave.setPrefHeight(30);
        btnSave.setOnAction(e->{
            if(FittingDetails.insertFittingDetails(txtFittingName.getText(), txtRetailPrice.getText(), cmbSelectFittingType.getSelectionModel().getSelectedItem())==true){
                BasicLayout.showMessageDialog("Fitting updated successfully.");
            }else{
                BasicLayout.showMessageDialog("Error encountered in updating this fitting.\n"
                        + "Please consult your developer to fix this.");
            }
        });
        
        
        this.add(lblFittingName, 1, 1);
        this.add(lblSelectType, 1, 2);
        this.add(lblRetailPrice, 1, 3);
        
        this.add(txtFittingName, 2, 1);
        this.add(cmbSelectFittingType, 2, 2);
        this.add(txtRetailPrice, 2, 3);
        
        this.add(btnSave, 2, 4);
    }

    /**
     * @return the cmbSelectFittingType
     */
    public ComboBox<String> getCmbSelectFittingType() {
        return cmbSelectFittingType;
    }
}
