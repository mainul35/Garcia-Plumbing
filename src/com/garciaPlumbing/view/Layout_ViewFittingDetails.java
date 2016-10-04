/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.FittingDetails;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
public class Layout_ViewFittingDetails extends BorderPane {

    ComboBox<String> cmbSupplierName = new ComboBox<>();
    ComboBox<String> cmbFittingType = new ComboBox<>();
    TextField txtFittingName = new TextField();
    TextField txtRetailPrice = new TextField();
    Button btnUpdate = new Button("Update");
    Button btnDelete = new Button("Delete");
    TableView<FittingDetails> tblFittingDetails = new TableView<>();
    VBox vBox = new VBox();
    HBox buttonGroup = new HBox();
    ObservableList<FittingDetails> ol = null, selectedItem = null;

    TableColumn<FittingDetails, String> supplierNameCol = new TableColumn<>("Supplier name");
    TableColumn<FittingDetails, String> fittingTypeCol = new TableColumn<>("Fitting type");
    TableColumn<FittingDetails, String> fittingNameCol = new TableColumn<>("Fitting Name");
    TableColumn<FittingDetails, Double> priceCol = new TableColumn<>("Price");

    private static String supplierName = null;
    private static String fitingType = null;

    public Layout_ViewFittingDetails() {
        HBox hBox_TableContainer = new HBox();
        hBox_TableContainer.getChildren().add(0, getTableFittingDetails());
        this.setCenter(hBox_TableContainer);
        this.setRight(getLayoutChangeTableValue());
    }

    public TableView<FittingDetails> getTableFittingDetails() {
        ol = new FittingDetails().getFittingDetails();
        tblFittingDetails.setItems(ol);
        tblFittingDetails.setPrefWidth(920);

        supplierNameCol.setEditable(false);
        supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierNameCol.setMinWidth(tblFittingDetails.getPrefWidth() / 4);

        fittingNameCol.setEditable(false);
        fittingNameCol.setCellValueFactory(new PropertyValueFactory<>("fittingName"));
        fittingNameCol.setMinWidth(tblFittingDetails.getPrefWidth() / 4);

        fittingTypeCol.setEditable(false);
        fittingTypeCol.setCellValueFactory(new PropertyValueFactory<>("fittingTypeName"));
        fittingTypeCol.setMinWidth(tblFittingDetails.getPrefWidth() / 4);

        priceCol.setEditable(false);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("fittingCost"));
        priceCol.setMinWidth(tblFittingDetails.getPrefWidth() / 4);

        tblFittingDetails.getColumns().addAll(fittingNameCol, fittingTypeCol, supplierNameCol, priceCol);
        tblFittingDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FittingDetails>() {

            @Override
            public void changed(ObservableValue<? extends FittingDetails> observableValue, FittingDetails oldItem, FittingDetails currentItem) {
                if (tblFittingDetails.getSelectionModel().getSelectedItem() != null && currentItem != null) {
                    txtFittingName.setText(currentItem.getFittingName());
                    txtRetailPrice.setText(Double.toString(currentItem.getFittingCost()));
                    cmbSupplierName.getItems().clear();
                    cmbFittingType.getItems().clear();
                    fitingType = currentItem.getFittingTypeName();
                    cmbFittingType.setValue(currentItem.getFittingTypeName());
                    cmbFittingType.getItems().addAll(currentItem.getAllFittingTypeNames());
                    supplierName = currentItem.getSupplierName();
                    cmbSupplierName.setValue(currentItem.getSupplierName());
                    cmbSupplierName.getItems().addAll(currentItem.getAllSuppliers());

                    btnUpdate.setOnAction((e) -> {
                        FittingDetails.updateFittingDetails(
                                txtFittingName.getText(),
                                supplierName,
                                fitingType,
                                txtRetailPrice.getText(),
                                currentItem);
                        try {
                            BasicLayout.borderPane.setCenter(new Layout_ViewFittingDetails());
                            Scene scene = new Scene(BasicLayout.borderPane);
                            BasicLayout.stage.setScene(scene);
                        } catch (IllegalArgumentException ie) {
                        }
                    });
                }
            }
        });

        return this.tblFittingDetails;
    }

    public VBox getLayoutChangeTableValue() {
        Label lblUpdateDetails = new Label("Update Details");
        Label lblSupplierName = new Label("Supplier Name");
        Label lblFittingName = new Label("Fitting Name");
        Label lblFittingType = new Label("Fitting Type");
        Label lblRetailPrice = new Label("Price");
        lblUpdateDetails.setAlignment(Pos.CENTER);
        lblUpdateDetails.setMaxWidth(200);
        lblUpdateDetails.setPrefWidth(30);
        lblUpdateDetails.setFont(Font.font("Palatino Linotype", FontWeight.BOLD, 14));
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        buttonGroup.setSpacing(10);
        buttonGroup.setPadding(new Insets(10));
//        btnDelete.setStyle("-fx-background-color:#6495ED");
        btnUpdate.setStyle("-fx-background-color:#6495ED");
        vBox.setStyle("-fx-background-color:#AFEEEE");
        vBox.setAlignment(Pos.CENTER);

        txtFittingName.setMaxWidth(200);
        txtRetailPrice.setMaxWidth(200);
        lblFittingName.setPrefWidth(200);
        lblFittingType.setPrefWidth(200);
        lblRetailPrice.setPrefWidth(200);
        lblSupplierName.setPrefWidth(200);
        cmbFittingType.setPrefWidth(200);
        cmbSupplierName.setPrefWidth(200);
        btnUpdate.setPrefWidth(200);

        txtFittingName.setPrefHeight(30);
        txtRetailPrice.setPrefHeight(30);
        lblFittingName.setPrefHeight(30);
        lblFittingType.setPrefHeight(30);
        lblRetailPrice.setPrefHeight(30);
        lblSupplierName.setPrefHeight(30);
        cmbFittingType.setPrefHeight(30);
        cmbSupplierName.setPrefHeight(30);
        btnUpdate.setPrefHeight(30);

        cmbSupplierName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                fitingType = FittingDetails.getFittingTypeBySupplier(t1);
                cmbFittingType.setValue(FittingDetails.getFittingTypeBySupplier(t1));
            }
        });

        cmbFittingType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                supplierName = FittingDetails.getSupplierByFittingType(t1);
                cmbSupplierName.setValue(FittingDetails.getSupplierByFittingType(t1));
            }
        });
        buttonGroup.getChildren().addAll(btnUpdate);
        vBox.getChildren().addAll(
                lblUpdateDetails,
                lblFittingName,
                txtFittingName,
                lblFittingType,
                cmbFittingType,
                lblSupplierName,
                cmbSupplierName,
                lblRetailPrice,
                txtRetailPrice,
                buttonGroup);
        return vBox;
    }
}