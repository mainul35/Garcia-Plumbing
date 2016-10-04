/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.CustomerDetails;
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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 *
 * @author FURIOUS
 */
class Layout_SpecificLocationBasedCustomer extends BorderPane {

    TableView<CustomerDetails> tblCustomerInfo = new TableView<>();
    ComboBox<String> cmbSelectCity = new ComboBox<>();
    ComboBox<String> cmbSelectStreet = new ComboBox<>();
    ObservableList<String> oListForCmbSelectCity = null;
    ObservableList<String> oListForCmbSelectStreet = null;
    ObservableList<CustomerDetails> oListForTblSpecificLocation = null;
    TableColumn<CustomerDetails, String> colCustomerName = new TableColumn<>("Customer Name");
    TableColumn<CustomerDetails, String> colHome = new TableColumn<>("Home");
    TableColumn<CustomerDetails, String> colShowJobsButton = new TableColumn<>("All Jobs");
    TableColumn<CustomerDetails, String> colShowDetailsButton = new TableColumn<>("Personal Details");
    BorderPane bp = this;

    public Layout_SpecificLocationBasedCustomer() {
        try {
            bp.getChildren().clear();
            bp.setTop(getLayoutSpecificLocation());
            bp.setCenter(getLocationBasedCustomerTable());
        } catch (IllegalArgumentException e) {
        }
    }

    private HBox getLayoutSpecificLocation() throws IllegalArgumentException {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        Label lblSelectCity = new Label("Secect a City");
        Label lblSelectStreet = new Label("Select Street");
        lblSelectCity.setAlignment(Pos.CENTER);
        lblSelectStreet.setAlignment(Pos.CENTER);

        oListForCmbSelectCity = CustomerDetails.getCities();
        cmbSelectCity.getItems().addAll(oListForCmbSelectCity);
        cmbSelectCity.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (!t1.equals("Secect a City")) {
                    System.out.println(t1);
                    if (oListForCmbSelectStreet != null) {
                        oListForCmbSelectStreet.clear();
                    }
                    oListForCmbSelectStreet = CustomerDetails.getStreets(t1);
                    cmbSelectStreet.getItems().clear();
                    cmbSelectStreet.getItems().addAll(oListForCmbSelectStreet);
                    cmbSelectStreet.setValue("Please select one");
                }
            }
        });

        cmbSelectCity.setValue("Please select one");
        cmbSelectStreet.setValue("Please select one");
        cmbSelectStreet.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if (!t1.equals("Please select one") | !t1.equals(null)) {
                        bp.setCenter(getLocationBasedCustomerTable());
                    }
                } catch (NullPointerException npe) {
                }
            }
        });
        hBox.getChildren().addAll(lblSelectCity, cmbSelectCity, lblSelectStreet, cmbSelectStreet);

        return hBox;
    }

    private HBox getLocationBasedCustomerTable() throws IllegalArgumentException {
        HBox hBox = new HBox();
        String city = cmbSelectCity.getSelectionModel().getSelectedItem();
        String street = cmbSelectStreet.getSelectionModel().getSelectedItem();
        oListForTblSpecificLocation = CustomerDetails.getSpecificLocalCustomerEntries(city, street);
        tblCustomerInfo.setItems(oListForTblSpecificLocation);
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colHome.setCellValueFactory(new PropertyValueFactory<>("house"));
        colShowDetailsButton.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colShowJobsButton.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        tblCustomerInfo.setPrefWidth(1145);
        colCustomerName.setMinWidth(tblCustomerInfo.getPrefWidth() / 4);
        colHome.setMinWidth(tblCustomerInfo.getPrefWidth() / 4);
        colShowDetailsButton.setMinWidth(tblCustomerInfo.getPrefWidth() / 4);
        colShowJobsButton.setMinWidth(tblCustomerInfo.getPrefWidth() / 4);

        colCustomerName.setStyle("-fx-alignment: CENTER;");
        colHome.setStyle("-fx-alignment: CENTER;");
        colShowDetailsButton.setStyle("-fx-alignment: CENTER;");
        colShowJobsButton.setStyle("-fx-alignment: CENTER;");

        Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>> cellButtonShowJobs = //
                new Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        final TableCell cell = new TableCell() {

                            @Override
                            public void updateItem(Object item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setText(null);
                                    setGraphic(null);
                                } else {
                                    final Button btnPrint = new Button("View");
                                    btnPrint.setOnAction(new EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent event) {
                                            p.getTableView().getSelectionModel().select(getIndex());
                                            CustomerDetails item = tblCustomerInfo.getSelectionModel().getSelectedItem();
                                            if (item != null) {
                                                System.out.println(item.getCustomerID());
//                                                Layout_ViewJobsForIndividualCustomer lvjfic= new Layout_ViewJobsForIndividualCustomer(item.getCustomerID());
                                                
                                            }
                                        }
                                    });
                                    setGraphic(btnPrint);
                                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                }
                            }
                        };
                        return cell;
                    }
                };

        colShowJobsButton.setCellFactory(cellButtonShowJobs);

        Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>> cellButtonShowDetails = //
                new Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        final TableCell cell = new TableCell() {

                            @Override
                            public void updateItem(Object item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setText(null);
                                    setGraphic(null);
                                } else {
                                    final Button btnPrint = new Button("View");
                                    btnPrint.setOnAction(new EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent event) {
                                            p.getTableView().getSelectionModel().select(getIndex());
                                            CustomerDetails item = tblCustomerInfo.getSelectionModel().getSelectedItem();
                                            if (item != null) {
                                                System.out.println();
                                            }
                                        }
                                    });
                                    setGraphic(btnPrint);
                                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                }
                            }
                        };
                        return cell;
                    }
                };

        colShowDetailsButton.setCellFactory(cellButtonShowDetails);
        tblCustomerInfo.setItems(oListForTblSpecificLocation);
        tblCustomerInfo.getColumns().clear();
        tblCustomerInfo.getColumns().addAll(colCustomerName, colHome, colShowJobsButton, colShowDetailsButton);
        tblCustomerInfo.setMinWidth(1160);
        hBox.getChildren().clear();
        hBox.getChildren().add(tblCustomerInfo);
        hBox.setMinWidth(1120);
        return hBox;
    }
}

public class Layout_ViewCustomerDetails extends BorderPane {

    ComboBox<String> cmbSelectCustomersBy = new ComboBox<>();
    TableView<CustomerDetails> tblCustomerData = new TableView<>();
    ObservableList<CustomerDetails> ol = null;
    TableColumn<CustomerDetails, String> colCustomerName = new TableColumn<>("Customer Name");
    TableColumn<CustomerDetails, String> colHome = new TableColumn<>("Home");
    TableColumn<CustomerDetails, String> colStreet = new TableColumn<>("Street");
    TableColumn<CustomerDetails, String> colCity = new TableColumn<>("City");
    TableColumn<CustomerDetails, String> colShowJobsButton = new TableColumn<>("All Jobs");
    TableColumn<CustomerDetails, String> colShowDetailsButton = new TableColumn<>("Personal Details");
    HBox hBox = null;
    Label lblSelectHowToDisplay = new Label("Show customers from: ");

    BorderPane bp = this;

    public Layout_ViewCustomerDetails() throws IllegalArgumentException {
        hBox = new HBox();
        hBox.setPadding(new Insets(20));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        lblSelectHowToDisplay.setAlignment(Pos.CENTER);
        tblCustomerData.setStyle("-fx-alignment: CENTER;");

        cmbSelectCustomersBy.getItems().addAll("All Locations", "Specific Location");
        cmbSelectCustomersBy.setValue(cmbSelectCustomersBy.getItems().get(0));
        hBox.getChildren().clear();
        hBox.getChildren().addAll(lblSelectHowToDisplay, cmbSelectCustomersBy);
        this.getChildren().clear();
        this.setTop(hBox);
        this.setCenter(getDefaultCustomerTable());
        cmbSelectCustomersBy.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    if (t1.equals("Specific Location")) {
                        bp.setCenter(new Layout_SpecificLocationBasedCustomer());
                        BasicLayout.borderPane.setCenter(bp);
                    } else {
                        bp.setCenter(getDefaultCustomerTable());
                        BasicLayout.borderPane.setCenter(bp);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private HBox getDefaultCustomerTable() throws IllegalArgumentException {
        ol = new CustomerDetails().getDefaultCustomerEntries();
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colHome.setCellValueFactory(new PropertyValueFactory<>("house"));
        colShowDetailsButton.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colShowJobsButton.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colStreet.setCellValueFactory(new PropertyValueFactory<>("street"));

        tblCustomerData.setPrefWidth(1145);
        colCity.setMinWidth(tblCustomerData.getPrefWidth() / 6);
        colCustomerName.setMinWidth(tblCustomerData.getPrefWidth() / 6);
        colHome.setMinWidth(tblCustomerData.getPrefWidth() / 6);
        colShowDetailsButton.setMinWidth(tblCustomerData.getPrefWidth() / 6);
        colShowJobsButton.setMinWidth(tblCustomerData.getPrefWidth() / 6);
        colStreet.setMinWidth(tblCustomerData.getPrefWidth() / 6);

        colCity.setStyle("-fx-alignment: CENTER;");
        colCustomerName.setStyle("-fx-alignment: CENTER;");
        colHome.setStyle("-fx-alignment: CENTER;");
        colShowDetailsButton.setStyle("-fx-alignment: CENTER;");
        colShowJobsButton.setStyle("-fx-alignment: CENTER;");
        colStreet.setStyle("-fx-alignment: CENTER;");

        Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>> cellButtonShowJobs = //
                new Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        final TableCell cell = new TableCell() {

                            @Override
                            public void updateItem(Object item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setText(null);
                                    setGraphic(null);
                                } else {
                                    final Button btnPrint = new Button("View");
                                    btnPrint.setOnAction(new EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent event) {
                                            p.getTableView().getSelectionModel().select(getIndex());
                                            CustomerDetails item = tblCustomerData.getSelectionModel().getSelectedItem();
                                            if (item != null) {
                                                Layout_ViewJobsForIndividualCustomer lvjfic = 
                                                        new Layout_ViewJobsForIndividualCustomer(item.getCustomerID());
//                                                System.err.println(item.getCustomerID());
                                            }
                                        }
                                    });
                                    setGraphic(btnPrint);
                                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                }
                            }
                        };
                        return cell;
                    }
                };

        colShowJobsButton.setCellFactory(cellButtonShowJobs);

        Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>> cellButtonShowDetails = //
                new Callback<TableColumn<CustomerDetails, String>, TableCell<CustomerDetails, String>>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        final TableCell cell = new TableCell() {

                            @Override
                            public void updateItem(Object item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setText(null);
                                    setGraphic(null);
                                } else {
                                    final Button btnPrint = new Button("View");
                                    btnPrint.setOnAction(new EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent event) {
                                            p.getTableView().getSelectionModel().select(getIndex());
                                            CustomerDetails item = tblCustomerData.getSelectionModel().getSelectedItem();
                                            if (item != null) {
                                                System.out.println();
                                            }
                                        }
                                    });
                                    setGraphic(btnPrint);
                                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                }
                            }
                        };
                        return cell;
                    }
                };

        colShowDetailsButton.setCellFactory(cellButtonShowDetails);
        tblCustomerData.setItems(ol);
        tblCustomerData.getColumns().clear();
        tblCustomerData.getColumns().addAll(colCustomerName, colHome, colStreet, colCity, colShowJobsButton, colShowDetailsButton);
        tblCustomerData.setMinWidth(1160);
        HBox hBox = new HBox();
        hBox.getChildren().clear();
        hBox.getChildren().add(tblCustomerData);
        hBox.setMinWidth(1120);
        return hBox;
    }
}
