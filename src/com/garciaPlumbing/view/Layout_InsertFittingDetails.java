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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author FURIOUS
 */
public class Layout_InsertFittingDetails extends VBox {

    public Layout_InsertFittingDetails() {
        Label lblAddSupplier = new Label("Add Supplier");
        lblAddSupplier.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        lblAddSupplier.setPadding(new Insets(25, 0, 0, 420));
        lblAddSupplier.setAlignment(Pos.CENTER);
        Layout_AddSupplier layout_AddSupplier = new Layout_AddSupplier();

        VBox vBox1 = new VBox(lblAddSupplier, layout_AddSupplier);
        vBox1.setSpacing(10);

        Label lblAddFittingType = new Label("Add Fitting Type");
        lblAddFittingType.setPadding(new Insets(5, 0, 0, 420));
        lblAddFittingType.setAlignment(Pos.CENTER);
        lblAddFittingType.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        Layout_AddFittingType layout_AddFittingType = new Layout_AddFittingType();
        VBox vBox2 = new VBox(lblAddFittingType, layout_AddFittingType);
        vBox2.setSpacing(10);

        layout_AddSupplier.getBtnSave().setOnAction(e -> {
            FittingDetails fittingDetails = new FittingDetails();
            if (layout_AddSupplier.getTxtSupplierName().getText().equals("")) {
                BasicLayout.showMessageDialog("Please insert a supplier name.");
            } else {
                if (fittingDetails.insertSupplier(layout_AddSupplier.getTxtSupplierName().getText()) == true) {
                    BasicLayout.showMessageDialog("New supplier added successfully.");
                } else {
                    BasicLayout.showMessageDialog("This supplier already exists.");
                }
            }
            ArrayList<String> al = fittingDetails.getAllSuppliers();
            layout_AddFittingType.getCmbSuppliers().getItems().clear();
            for (String col : al) {
                layout_AddFittingType.getCmbSuppliers().getItems().add(col);
            }
        });

        Label lblAddFitting = new Label("Add Fitting");
        lblAddFitting.setPadding(new Insets(5, 0, 0, 420));
        lblAddFitting.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        lblAddFitting.setAlignment(Pos.CENTER);
        Layout_AddFitting layout_AddFitting = new Layout_AddFitting();
        VBox vBox3 = new VBox(lblAddFitting, layout_AddFitting);
        vBox3.setSpacing(10);

        layout_AddFittingType.getBtnAdd().setOnAction(e -> {
            FittingDetails fittingDetails = new FittingDetails();
            if (layout_AddFittingType.getTxtFittingType().getText().equals("")) {
                BasicLayout.showMessageDialog("Please insert a fitting type name.");
            } else {
                if (fittingDetails.insertFittingType(layout_AddFittingType.getTxtFittingType().getText(), "" + layout_AddFittingType.getCmbSuppliers().getSelectionModel().getSelectedItem()) == true) {
                    BasicLayout.showMessageDialog("new fitting type added successfully.");
                } else {
                    BasicLayout.showMessageDialog("This fitting type already exists.");
                }
//                System.err.println(layout_AddFittingType.getCmbSuppliers().getSelectionModel().getSelectedItem());
            }

            ArrayList<String> al = fittingDetails.getAllFittingTypeNames();
            for (String col : al) {
                layout_AddFitting.getCmbSelectFittingType().getItems().add(col);
            }
        });

        this.getChildren().add(vBox1);
        this.getChildren().add(vBox2);
        this.getChildren().add(vBox3);
        this.setSpacing(10);
    }
}
