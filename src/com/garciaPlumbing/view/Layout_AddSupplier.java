/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.FittingDetails;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

/**
 *
 * @author FURIOUS
 */
public class Layout_AddSupplier extends GridPane{
    private Label lblSupplierName;
    private TextField txtSupplierName = new TextField();
    private Button btnSave = new Button("Save");

    public Layout_AddSupplier() {
        lblSupplierName = new Label("Supplier Name");
        this.setVgap(10);
        this.setHgap(10);
        this.setPadding(new Insets(10));
        
        lblSupplierName.setPrefWidth(120);
        txtSupplierName.setPrefWidth(180);
        btnSave.setPrefWidth(100);
        btnSave.setPrefHeight(30);
        btnSave.setOnAction(e->{
            
        });
        this.setAlignment(Pos.CENTER);
        this.add(lblSupplierName, 1, 1);
        this.add(txtSupplierName, 2, 1);
        this.add(btnSave, 2, 2);
    }

    /**
     * @return the btnSave
     */
    public Button getBtnSave() {
        return btnSave;
    }

    /**
     * @return the txtSupplierName
     */
    public TextField getTxtSupplierName() {
        return txtSupplierName;
    }
}
