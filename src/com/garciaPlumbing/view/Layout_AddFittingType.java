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
public class Layout_AddFittingType extends GridPane {

    private Label lblFittingType, lblSupplier;
    private TextField txtFittingType = new TextField();
    private ComboBox<String> cmbSuppliers = new ComboBox<>();
    private Button btnAdd = new Button("Add");

    public Layout_AddFittingType() {
        lblFittingType = new Label("Fitting type name");
        lblSupplier = new Label("Supplier Name");
        txtFittingType = new TextField();
        btnAdd = new Button("Add");
        this.setVgap(10);
        this.setHgap(10);
        this.setPadding(new Insets(10));

        lblFittingType.setPadding(new Insets(5));
        lblSupplier.setPadding(new Insets(5));
        txtFittingType.setPadding(new Insets(5));
//        cmbSuppliers.setPadding(new Insets(5));
        btnAdd.setPadding(new Insets(5));

        lblFittingType.setPrefWidth(120);
        lblSupplier.setPrefWidth(120);
        txtFittingType.setPrefWidth(180);
        cmbSuppliers.setPrefWidth(180);
        
        btnAdd.setPrefWidth(100);
        btnAdd.setPrefHeight(30);
        
        ArrayList<String> al = new FittingDetails().getAllSuppliers();
        for (String col : al) {
            cmbSuppliers.getItems().add(col);
        }

        this.setAlignment(Pos.CENTER);
        this.add(lblFittingType, 1, 1);
        this.add(lblSupplier, 1, 2);
        this.add(txtFittingType, 2, 1);
        this.add(cmbSuppliers, 2, 2);
        this.add(btnAdd, 2, 3);

    }

    /**
     * @return the cmbSuppliers
     */
    public ComboBox<String> getCmbSuppliers() {
        return cmbSuppliers;
    }

    /**
     * @return the btnAdd
     */
    public Button getBtnAdd() {
        return btnAdd;
    }

    /**
     * @return the txtFittingType
     */
    public TextField getTxtFittingType() {
        return txtFittingType;
    }
}
