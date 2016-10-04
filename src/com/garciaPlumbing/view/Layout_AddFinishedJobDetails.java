/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.CustomerDetails;
import com.garciaPlumbing.model.JobDetails;
import com.garciaPlumbing.model.JobOrderData;
import com.garciaPlumbing.model.LabourDetails;
import com.garciaPlumbing.view.util.StringProcessor;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author FURIOUS
 */
public class Layout_AddFinishedJobDetails extends BorderPane {

    static AutoCompleteTextField txtEmail = new AutoCompleteTextField(CustomerDetails.getCustomerEmailList()) {
        ContextMenu contextMenu = super.entriesPopup;

        @Override
        public void populatePopup(List<String> searchResult) {

            List<CustomMenuItem> menuItems = new LinkedList<>();
            int maxEntries = 10;
            int count = Math.min(searchResult.size(), maxEntries);
            for (int i = 0; i < count; i++) {
                String result = searchResult.get(i);
                Label entryLabel = new Label(result);
                CustomMenuItem item = new CustomMenuItem(entryLabel, true);
                item.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        setText(result);
                        contextMenu.hide();
                        customerEmail = txtEmail.getText();
                        txtName.setText(CustomerDetails.getCustomerNameByEmail(txtEmail.getText()));
                        customerName = txtName.getText();
                    }
                });
                menuItems.add(item);
            }
            contextMenu.getItems().clear();
            contextMenu.getItems().addAll(menuItems);
        }
    };
    static TextField txtName = new TextField();
    static AutoCompleteTextField txtHouse;
    static AutoCompleteTextField txtCity = new AutoCompleteTextField(CustomerDetails.getCities()) {
        ContextMenu contextMenu = super.entriesPopup;

        @Override
        public void populatePopup(List<String> searchResult) {

            List<CustomMenuItem> menuItems = new LinkedList<>();
            int maxEntries = 10;
            int count = Math.min(searchResult.size(), maxEntries);
            for (int i = 0; i < count; i++) {
                String result = searchResult.get(i);
                Label entryLabel = new Label(result);
                CustomMenuItem item = new CustomMenuItem(entryLabel, true);
                item.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        txtCity.setText(result);
                        contextMenu.hide();
                        city = result;
                        System.out.println(result);
                    }
                });
                menuItems.add(item);
            }
            contextMenu.getItems().clear();
            contextMenu.getItems().addAll(menuItems);
        }
    };

    static AutoCompleteTextField txtStreet = new AutoCompleteTextField(CustomerDetails.getStreets(txtCity.getText())) {
        ContextMenu contextMenu = super.entriesPopup;

        @Override
        public void populatePopup(List<String> searchResult) {

            List<CustomMenuItem> menuItems = new LinkedList<>();
            int maxEntries = 10;
            int count = Math.min(searchResult.size(), maxEntries);
            for (int i = 0; i < count; i++) {
                String result = searchResult.get(i);
                Label entryLabel = new Label(result);
                CustomMenuItem item = new CustomMenuItem(entryLabel, true);
                item.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        setText(result);
                        street = result;
                        contextMenu.hide();
                    }
                });
                menuItems.add(item);
            }
            contextMenu.getItems().clear();
            contextMenu.getItems().addAll(menuItems);
        }
    };
    static ComboBox<String> cmbJobType;
    static DatePicker datePicker = new DatePicker();
    static CheckBox[] chklaboursArray = new CheckBox[LabourDetails.countLabour()];
    static TextField[] txtHoursWorkedArray = new TextField[LabourDetails.countLabour()];
    static ArrayList<ComboBox<Integer>> cmbMinutesWorkedArray = new ArrayList<>();
    static Label[] lblHoursWorkedArray = new Label[LabourDetails.countLabour()];
    static Label[] lblMinutesWorkedArray = new Label[LabourDetails.countLabour()];
    static HBox[] hBoxArrayForLabours = new HBox[LabourDetails.countLabour()];
    static ObservableList<Integer> minutesList = FXCollections.observableArrayList();
    static CheckBox[] chkFittingsArray = null;
    static TextField[] txtTotalUsedArray = null;
    static Label[] lblQuantityArray = null;
    static HBox[] hBoxArrayForFittings = null;
    static ArrayList<String> labours = new ArrayList<>();
    static ArrayList<Integer> hours = new ArrayList<Integer>();
    static ArrayList<Integer> mins = new ArrayList<Integer>();
    static ArrayList<Integer> quantities = new ArrayList<Integer>();
    static ArrayList<String> fittings = new ArrayList<String>();
    static Date date;
    Button btnSubmit = new Button("Save");
    static String selectedJobType = "Please select a job type";
    BorderPane bp = this;
    GridPane gp = new GridPane();
    static String customerName;
    static String customerEmail;
    static String city;
    static String street;
    static String house;

    /**
     * Constructor
     */
    public Layout_AddFinishedJobDetails() {
        minutesList.addAll(00, 10, 20, 30, 40, 50);
        gp.addColumn(0, getLeftContainer());
        gp.addColumn(1, getCenteredContainer());
        gp.addColumn(2, getRightContainer());
        HBox hbBottom = new HBox();
        hbBottom.setAlignment(Pos.CENTER);
        btnSubmit.setPrefWidth(150);
        btnSubmit.setPrefHeight(30);
        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                JobDetails jd = new JobDetails();
                String customerID = null;
                customerName = txtName.getText();
                if(StringProcessor.isEmail(txtEmail.getText())){
                    customerEmail = txtEmail.getText();
                    customerID = StringProcessor.getUsername(customerEmail);
                }
                city = txtCity.getText();
                street = txtStreet.getText();
                house = txtHouse.getText();

                

                if (customerEmail.equals("") || customerEmail==null) {
                    BasicLayout.showMessageDialog("Please enter customer email.");
                } else if (customerName.equals("")) {
                    BasicLayout.showMessageDialog("Please enter customer name.");
                } else if (city.equals("")) {
                    BasicLayout.showMessageDialog("Please enter the city.");
                } else if (street.equals("")) {
                    BasicLayout.showMessageDialog("Please enter the street.");
                } else if (house.equals("")) {
                    BasicLayout.showMessageDialog("Please enter the house.");
                } else {
                    for (int x = 0; x < chklaboursArray.length; x++) {
                        if (chklaboursArray[x].isSelected() == true) {
                            if (Layout_AddFitting.isNumeric(txtHoursWorkedArray[x].getText()) || !txtHoursWorkedArray[x].getText().equals("")) {
                                labours.add(chklaboursArray[x].getText());
                                hours.add(Integer.parseInt(txtHoursWorkedArray[x].getText()));
                                mins.add(cmbMinutesWorkedArray.get(x).getSelectionModel().getSelectedItem());
                            } else {
                                BasicLayout.showMessageDialog("Please enter total hours for each labour.");
                            }
                        }
                    }
                    for (int x = 0; x < chkFittingsArray.length; x++) {
                        if (chkFittingsArray[x].isSelected() == true) {
                            if (Layout_AddFitting.isNumeric(txtTotalUsedArray[x].getText()) || !txtTotalUsedArray[x].getText().equals("")) {
                                quantities.add(Integer.parseInt(txtTotalUsedArray[x].getText()));
                                fittings.add(chkFittingsArray[x].getText());
                            } else {
                                BasicLayout.showMessageDialog("Please enter fitting quantity.");
                            }
                        }
                    }
                    BasicLayout.showMessageDialog("Job record has been added suvccessfully.");
                    jd.insertWorkedJobDetails(customerName, customerID, house, city, street, customerEmail, cmbJobType.getSelectionModel().getSelectedItem(), date, fittings, quantities, labours, hours, mins);
                }
            }
        });
        hbBottom.getChildren().add(btnSubmit);
        hbBottom.setPadding(new Insets(50));
        hbBottom.setSpacing(40);
        bp.setBottom(hbBottom);
        bp.setCenter(gp);

    }

    /**
     * Left pane to insert customer details for a job.
     */
    private VBox getLeftContainer() {
        VBox vBox = new VBox();
        try {

            Label lblCustomerEmail = new Label("Enter customer email");
            Label lblCustomerName = new Label("Enter customer name");
            Label lblCity = new Label("Enter city");
            Label lblStreet = new Label("Enter street");
            Label lblHouse = new Label("Enter house");
            Label lblSelectJobType = new Label("Select job type");
            Label lblPickDate = new Label("Pick up a date when it was worked on:");

            Locale.setDefault(Locale.UK);
            datePicker.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    date = Date.valueOf(datePicker.getValue());
                }
            });

            txtHouse = new AutoCompleteTextField(CustomerDetails.getHouses(txtStreet.getText())) {
                ContextMenu contextMenu = super.entriesPopup;

                @Override
                public void populatePopup(List<String> searchResult) {

                    List<CustomMenuItem> menuItems = new LinkedList<>();
                    int maxEntries = 10;
                    int count = Math.min(searchResult.size(), maxEntries);
                    for (int i = 0; i < count; i++) {
                        String result = searchResult.get(i);
                        Label entryLabel = new Label(result);
                        CustomMenuItem item = new CustomMenuItem(entryLabel, true);
                        item.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                setText(result);
                                house = result;
                                contextMenu.hide();
                            }
                        });
                        menuItems.add(item);
                    }
                    contextMenu.getItems().clear();
                    contextMenu.getItems().addAll(menuItems);
                }
            };
            cmbJobType = new ComboBox<>();
            cmbJobType.setItems(JobOrderData.getJobTypeList());
            cmbJobType.setValue(selectedJobType);
            vBox.setPrefWidth(350);
            vBox.setPadding(new Insets(20));
            vBox.setSpacing(10);
            txtCity.setPrefSize(200, 30);
            cmbJobType.setPrefSize(300, 30);
            cmbJobType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    selectedJobType = t1;
                    gp.getChildren().clear();
                    gp.addColumn(0, getLeftContainer());
                    gp.addColumn(1, getCenteredContainer(t1));
                    gp.addColumn(2, getRightContainer());
                }
            });
            txtEmail.setPrefSize(200, 30);
            txtHouse.setPrefSize(200, 30);
            txtName.setPrefSize(200, 30);
            txtStreet.setPrefSize(200, 30);
            datePicker.setPrefSize(300, 30);
            txtName.setPadding(new Insets(5));

            vBox.getChildren().addAll(lblCustomerEmail, txtEmail, lblCustomerName, txtName,
                    lblCity, txtCity, lblStreet, txtStreet, lblHouse, txtHouse, lblPickDate, datePicker, lblSelectJobType, cmbJobType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vBox;
    }

    /**
     * Empty fittings while no fitting type is selected.
     */
    private VBox getCenteredContainer() {
        VBox vBox = new VBox();
        vBox.setPrefWidth(320);
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        Label lblHeading = new Label("No fitting type has been selected yet.");
        lblHeading.setPrefWidth(350);
        lblHeading.setFont(Font.font("Calibri", FontWeight.BOLD, 13));
        lblHeading.setAlignment(Pos.CENTER);
        vBox.getChildren().add(lblHeading);
        return vBox;
    }

    /**
     * Fetches all fitting details according to fitting types and makes those
     * visual to admin panel.
     */
    private ScrollPane getCenteredContainer(String fittingTypeName) {
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox vBox = new VBox();
        vBox.setPrefWidth(350);
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        ObservableList<String> ol = JobDetails.getBathroomFittings(fittingTypeName);
        int arrLen = ol.size();
        chkFittingsArray = new CheckBox[arrLen];
        txtTotalUsedArray = new TextField[arrLen];
        hBoxArrayForFittings = new HBox[arrLen];
        lblQuantityArray = new Label[arrLen];
        Label lblHeading = new Label(fittingTypeName + " Fittings");
        lblHeading.setPrefWidth(320);
        lblHeading.setFont(Font.font("Calibri", FontWeight.BOLD, 13));
        lblHeading.setAlignment(Pos.CENTER);
        vBox.getChildren().add(lblHeading);
        for (int x = 0; x < arrLen; x++) {
            chkFittingsArray[x] = new CheckBox(ol.get(x));
            chkFittingsArray[x].setSelected(false);
            chkFittingsArray[x].setAlignment(Pos.CENTER);
            chkFittingsArray[x].selectedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    for (int x = 0; x < chkFittingsArray.length; x++) {
                        if (chkFittingsArray[x].isSelected()) {
                            txtTotalUsedArray[x].setDisable(false);
                        } else {
                            txtTotalUsedArray[x].setDisable(true);
                        }

                    }
                }

            });
            lblQuantityArray[x] = new Label("Quantity: ");
            txtTotalUsedArray[x] = new TextField();
            txtTotalUsedArray[x].setPrefSize(70, 30);
            txtTotalUsedArray[x].setPadding(new Insets(5));
            txtTotalUsedArray[x].setDisable(true);
            hBoxArrayForFittings[x] = new HBox(lblQuantityArray[x], txtTotalUsedArray[x]);
            hBoxArrayForFittings[x].setSpacing(10);
            vBox.getChildren().addAll(chkFittingsArray[x], hBoxArrayForFittings[x]);
        }
        sp.setContent(vBox);
        return sp;
    }

    /**
     * Fetches all employee details and makes those visual to admin panel.
     */
    private ScrollPane getRightContainer() {
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox vBox = new VBox();
        vBox.setPrefWidth(450);
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);
        Label lblHeading = new Label("Select staff");
        lblHeading.setPrefWidth(320);
        lblHeading.setFont(Font.font("Calibri", FontWeight.BOLD, 13));
        lblHeading.setAlignment(Pos.CENTER);
        vBox.getChildren().add(lblHeading);
        for (int x = 0; x < chklaboursArray.length; x++) {
            chklaboursArray[x] = new CheckBox(LabourDetails.getListOfLabour().get(x).getLabourName());
            hBoxArrayForLabours[x] = new HBox();
            hBoxArrayForLabours[x].setSpacing(10);
            chklaboursArray[x].setSelected(false);
            chklaboursArray[x].setAlignment(Pos.CENTER);
            chklaboursArray[x].selectedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    for (int x = 0; x < chklaboursArray.length; x++) {
                        if (chklaboursArray[x].isSelected()) {
                            txtHoursWorkedArray[x].setDisable(false);
                            cmbMinutesWorkedArray.get(x).setDisable(false);
                        } else {
                            txtHoursWorkedArray[x].setDisable(true);
                            cmbMinutesWorkedArray.get(x).setDisable(true);
                        }

                    }
                }

            });
            lblHoursWorkedArray[x] = new Label("Hours worked : ");
            txtHoursWorkedArray[x] = new TextField();
            txtHoursWorkedArray[x].setPrefSize(70, 37);
            txtHoursWorkedArray[x].setPadding(new Insets(5));
            txtHoursWorkedArray[x].setDisable(true);
            lblMinutesWorkedArray[x] = new Label("Minutes worked : ");
            cmbMinutesWorkedArray.add(new ComboBox<>(minutesList));
            cmbMinutesWorkedArray.get(x).setValue(0);
            cmbMinutesWorkedArray.get(x).setPrefSize(80, 35);
            cmbMinutesWorkedArray.get(x).setPadding(new Insets(5));
            cmbMinutesWorkedArray.get(x).setDisable(true);
            hBoxArrayForLabours[x] = new HBox(lblHoursWorkedArray[x], txtHoursWorkedArray[x], lblMinutesWorkedArray[x], cmbMinutesWorkedArray.get(x));
            hBoxArrayForLabours[x].setSpacing(10);
            hBoxArrayForLabours[x].setPadding(new Insets(10));
            vBox.getChildren().addAll(chklaboursArray[x], hBoxArrayForLabours[x]);
        }
        sp.setContent(vBox);
        return sp;
    }
}

/**
 *
 * Abstract class for making autocompleted text field.
 */
abstract class AutoCompleteTextField extends TextField {

    TextField tf = this;
    private SortedSet<String> entries;
    protected ContextMenu entriesPopup;

    public AutoCompleteTextField(ObservableList<String> e) {
        super();
        entries = new TreeSet<>();
        try {
            for (String s : e) {
                entries.add(s);
            }
            entriesPopup = new ContextMenu();
            textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                    if (getText().length() == 0) {
                        entriesPopup.hide();
                    } else {
                        LinkedList<String> searchResult = new LinkedList<>();
                        searchResult.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
                        if (entries.size() > 0) {
                            populatePopup(searchResult);
                            if (!entriesPopup.isShowing()) {
                                entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                            }
                        } else {
                            entriesPopup.hide();
                        }
                    }
                }
            });

            focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                    entriesPopup.hide();
                }
            });

        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public SortedSet<String> getEntries() {
        return entries;
    }

    public abstract void populatePopup(List<String> searchResult);

    public void setEntries(SortedSet<String> entries) {
        this.entries = entries;
    }

    public static String getUsername(String email) {
        int x = email.indexOf("@");
        String username = email.substring(0, x);
        return username;
    }
}
