/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.FinishedJobDetails;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author FURIOUS
 */
public class Layout_ReportPage {

    String customerID = null;
    int jobId = 0;

    public Layout_ReportPage(String customerId, int jobId) {
        this.customerID = customerId;
        this.jobId = jobId;
        final BorderPane bp = new BorderPane();
        Label lblBanner = new Label("Garcia Plumbing");
        lblBanner.setAlignment(Pos.CENTER);
        lblBanner.setPadding(new Insets(20));
        bp.setTop(lblBanner);
        bp.setCenter(getReportPage());
        Scene scene = new Scene(bp);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMaxWidth(800);
        bp.setMinWidth(800);
        bp.setMaxWidth(800);
        lblBanner.setMinWidth(800);
        lblBanner.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        lblBanner.setMaxWidth(800);
        Button btnPrint = new Button("Print");
        btnPrint.setMinWidth(100);
        btnPrint.setMinHeight(30);
        btnPrint.setLayoutX(600);
        btnPrint.setLayoutY(20);
        Pane p = new Pane(btnPrint);
        p.setMinHeight(70);
        bp.setBottom(p);
        btnPrint.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Layout_ReportPage.print(bp);
            }
        });
        
        stage.show();

    }
    
    Insets insetsForLabels = new Insets(10, 20, 5, 10);
    Insets insetsForGridPanes = new Insets(5, 50, 5, 50);
    Font f = Font.font("Arial", FontWeight.BOLD, 12);
    public GridPane getReportPage() {
        FinishedJobDetails finishedJobDetails = new FinishedJobDetails();
        GridPane gpJobId = new GridPane();
        gpJobId.setPadding(insetsForGridPanes);
        Label lblJobIdKey = new Label("Job Id");
        lblJobIdKey.setAlignment(Pos.CENTER);
        lblJobIdKey.setFont(f);
        lblJobIdKey.setPadding(insetsForLabels);

        Label lblJobIdValue = new Label();
        lblJobIdValue.setText(Integer.toString(jobId));
        lblJobIdValue.setAlignment(Pos.CENTER);
        lblJobIdValue.setFont(f);
        lblJobIdValue.setPadding(insetsForLabels);
        gpJobId.addRow(0, lblJobIdKey, lblJobIdValue);

        GridPane gpJobType = new GridPane();
        gpJobType.setPadding(insetsForGridPanes);
        Label lblJobTypeKey = new Label("Job Type");
        lblJobTypeKey.setPadding(insetsForLabels);
        lblJobTypeKey.setFont(f);
        Label lblJobTypeValue = new Label(finishedJobDetails.getJobType(jobId));
        lblJobTypeValue.setPadding(insetsForLabels);
        lblJobTypeValue.setFont(f);
        gpJobType.addRow(0, lblJobTypeKey, lblJobTypeValue);
        
        GridPane gpCustomerName = new GridPane();
        gpCustomerName.setPadding(insetsForGridPanes);
        Label lblCustomerNameKey = new Label("Customer Name");
        lblCustomerNameKey.setPadding(insetsForLabels);
        lblCustomerNameKey.setFont(f);
        lblCustomerNameKey.setAlignment(Pos.CENTER);
        Label lblCustomerNameValue = new Label();
        if(customerID!=null){
            lblCustomerNameValue.setText((finishedJobDetails.getCustomerNameById(customerID)));
        }else{
            lblCustomerNameValue.setText("");
        }
        
        lblCustomerNameValue.setAlignment(Pos.CENTER);
        lblCustomerNameValue.setPadding(insetsForLabels);
        lblCustomerNameValue.setFont(f);
        gpCustomerName.addRow(0, lblCustomerNameKey, lblCustomerNameValue);
//        gpCustomerName.getChildren().addAll(lblCustomerNameKey, lblCustomerNameValue);

        GridPane gpDate = new GridPane();
        gpDate.setPadding(insetsForGridPanes);
        Label lblDateKey = new Label("Date");
        lblDateKey.setAlignment(Pos.CENTER);
        lblDateKey.setPadding(insetsForLabels);
        lblDateKey.setFont(f);
        Label lblDateValue = new Label(finishedJobDetails.getDateFinishedOn(jobId));
        lblDateValue.setAlignment(Pos.CENTER);
        lblDateValue.setPadding(insetsForLabels);
        lblDateValue.setFont(f);
        gpDate.addRow(0, lblDateKey, lblDateValue);

        GridPane gpUsedFittings = new GridPane();
        Label lblUsedFittings = new Label("Used Fittings");
        lblUsedFittings.setMinWidth(800);
        lblUsedFittings.setAlignment(Pos.CENTER);
        lblUsedFittings.setPadding(insetsForLabels);
        lblUsedFittings.setFont(f);
        gpUsedFittings.getChildren().add(lblUsedFittings);

        ArrayList<Integer> al = finishedJobDetails.getFittingIDsOnJob(jobId);

        GridPane gpIndividualFittingName = new GridPane();
        gpIndividualFittingName.setPadding(new Insets(5, 50, 5, 50));
        Label lblFittingName[] = new Label[al.size()+1];
        lblFittingName[0] = new Label("Fitting name");
        lblFittingName[0].setAlignment(Pos.CENTER);
        lblFittingName[0].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblFittingName[0].setPadding(new Insets(10, 0, 5, 0));
        for (int x = 0; x < al.size(); x++) {
            lblFittingName[x+1] = new Label(finishedJobDetails.getFittingNameById(al.get(x)));
            lblFittingName[x+1].setAlignment(Pos.CENTER);
            lblFittingName[x+1].setFont(Font.font("Arial", FontWeight.BOLD, 12));
            lblFittingName[x+1].setPadding(new Insets(10, 0, 5, 0));
        }
        gpIndividualFittingName.addColumn(0, lblFittingName);
        
        GridPane gpBoxIndividualFittingQuantity = new GridPane();
        gpBoxIndividualFittingQuantity.setPadding(new Insets(5, 50, 5, 50));
        Label lblFittingQty[] = new Label[al.size()+1];
        lblFittingQty[0] = new Label("Fitting Quantity");
        lblFittingQty[0].setAlignment(Pos.CENTER);
        lblFittingQty[0].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblFittingQty[0].setPadding(new Insets(10, 0, 5, 0));
        for (int x = 0; x < al.size(); x++) {
            lblFittingQty[x+1] = new Label(Integer.toString(finishedJobDetails.getFittingQuantityOnJob(jobId, al.get(x))));
            lblFittingQty[x+1].setAlignment(Pos.CENTER);
            lblFittingQty[x+1].setFont(Font.font("Arial", FontWeight.BOLD, 12));
            lblFittingQty[x+1].setPadding(new Insets(10, 0, 5, 0));
        }
        gpBoxIndividualFittingQuantity.addColumn(1, lblFittingQty);
        
        GridPane gpBoxIndividualFittingCost = new GridPane();
        gpBoxIndividualFittingCost.setPadding(new Insets(5, 50, 5, 50));
        Label lblFittingCost[] = new Label[al.size()+1];
        lblFittingCost[0] = new Label("Fitting cost");
        lblFittingCost[0].setAlignment(Pos.CENTER);
        lblFittingCost[0].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblFittingCost[0].setPadding(new Insets(10, 0, 5, 0));
        for (int x = 0; x < al.size(); x++) {
            lblFittingCost[x+1] = new Label(Double.toString(finishedJobDetails.getFittingCostsByFittingId(al.get(x))));
            lblFittingCost[x+1].setAlignment(Pos.CENTER);
            lblFittingCost[x+1].setFont(Font.font("Arial", FontWeight.BOLD, 12));
            lblFittingCost[x+1].setPadding(new Insets(10, 0, 5, 0));
        }
        gpBoxIndividualFittingCost.addColumn(2, lblFittingCost);
        
        GridPane gpFittingsContainer = new GridPane();
        gpFittingsContainer.addRow(0, gpIndividualFittingName, gpBoxIndividualFittingQuantity, gpBoxIndividualFittingCost);

        GridPane gpFittingTotalCost = new GridPane();
        gpFittingTotalCost.setPadding(new Insets(5, 50, 5, 50));
        double fittingTotalCost = 0;
        for (int i = 0; i < al.size(); i++) {
            fittingTotalCost += finishedJobDetails.getFittingCostsByFittingId(al.get(i))*finishedJobDetails.getFittingQuantityOnJob(jobId, al.get(i));
        }
        Label lblFittingCostKey = new Label("Total fitting cost");
        lblFittingCostKey.setAlignment(Pos.CENTER);
        lblFittingCostKey.setPadding(new Insets(5, 0, 5, 0));
        lblFittingCostKey.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label lblFittingCostValue = new Label(Double.toString(fittingTotalCost));
        lblFittingCostValue.setAlignment(Pos.CENTER);
        lblFittingCostValue.setPadding(new Insets(5, 0, 5, 50));
        lblFittingCostValue.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gpFittingTotalCost.addRow(0, lblFittingCostKey, lblFittingCostValue);
        
        GridPane gpWorkedLabours = new GridPane();
        Label lblWorkedLabours = new Label("Worked Employees");
        lblWorkedLabours.setMinWidth(800);
        lblWorkedLabours.setAlignment(Pos.CENTER);
        lblWorkedLabours.setPadding(insetsForLabels);
        lblWorkedLabours.setFont(f);
        gpWorkedLabours.getChildren().add(lblWorkedLabours);

        ObservableList<String> ol = finishedJobDetails.getWorkedLaboursOnJob(jobId);

        GridPane gpIndividualLabourName = new GridPane();
        gpIndividualLabourName.setPadding(new Insets(5, 50, 5, 50));
        Label lblLabourName[] = new Label[ol.size() + 1];
        lblLabourName[0] = new Label("Employee Name");
        lblLabourName[0].setPadding(new Insets(10, 0, 5, 0));
        lblLabourName[0].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        for (int x = 1, y = 0; y < ol.size(); x++, y++) {
            lblLabourName[x] = new Label(finishedJobDetails.getLabourNameById(ol.get(y)));
            lblLabourName[x].setAlignment(Pos.CENTER);
            lblLabourName[x].setPadding(new Insets(5, 0, 5, 0));
            lblLabourName[x].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        }
        gpIndividualLabourName.addColumn(0, lblLabourName);

        GridPane gpIndividualLabourWorkedHours = new GridPane();
        gpIndividualLabourWorkedHours.setPadding(new Insets(5, 50, 5, 50));
        Label lblLabourWorkedHours[] = new Label[ol.size() + 1];
        lblLabourWorkedHours[0] = new Label("Hours Worked");
        lblLabourWorkedHours[0].setPadding(new Insets(10, 0, 5, 0));
        lblLabourWorkedHours[0].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        for (int x = 1, y = 0; y < ol.size(); x++, y++) {
            lblLabourWorkedHours[x] = new Label(Integer.toString(finishedJobDetails.getTotalWorkedHoursOfLabourOnJob(ol.get(y), jobId).getHours()));
            lblLabourWorkedHours[x].setAlignment(Pos.CENTER);
            lblLabourWorkedHours[x].setPadding(new Insets(5, 0, 5, 0));
            lblLabourWorkedHours[x].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        }
        gpIndividualLabourWorkedHours.addColumn(1, lblLabourWorkedHours);

        GridPane gpIndividualLabourWorkedMinutes = new GridPane();
        gpIndividualLabourWorkedMinutes.setPadding(new Insets(5, 50, 5, 50));
        Label lblLabourWorkedMinutes[] = new Label[ol.size() + 1];
        lblLabourWorkedMinutes[0] = new Label("Minutes Worked");
        lblLabourWorkedMinutes[0].setPadding(new Insets(10, 0, 10, 0));
        lblLabourWorkedMinutes[0].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        for (int x = 1, y = 0; y < ol.size(); x++, y++) {
            lblLabourWorkedMinutes[x] = new Label(Integer.toString(finishedJobDetails.getTotalWorkedHoursOfLabourOnJob(ol.get(y), jobId).getMinutes()));
            lblLabourWorkedMinutes[x].setAlignment(Pos.CENTER);
            lblLabourWorkedMinutes[x].setPadding(new Insets(5, 0, 5, 0));
            lblLabourWorkedMinutes[x].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        }
        gpIndividualLabourWorkedMinutes.addColumn(2, lblLabourWorkedMinutes);

        GridPane gpIndividualLabourHourlyRate = new GridPane();
        gpIndividualLabourHourlyRate.setPadding(new Insets(5, 50, 5, 50));
        Label lblLabourHourlyRate[] = new Label[ol.size() + 1];
        lblLabourHourlyRate[0] = new Label("Hourly Rate");
        lblLabourHourlyRate[0].setPadding(new Insets(10, 0, 5, 0));
        lblLabourHourlyRate[0].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        for (int x = 1, y = 0; y < ol.size(); x++, y++) {
            lblLabourHourlyRate[x] = new Label(Double.toString(finishedJobDetails.getLabourCostById(ol.get(y))));
            lblLabourHourlyRate[x].setAlignment(Pos.CENTER);
            lblLabourHourlyRate[x].setPadding(new Insets(5, 0, 5, 0));
            lblLabourHourlyRate[x].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        }
        gpIndividualLabourHourlyRate.addColumn(3, lblLabourHourlyRate);

        GridPane gpLabourContainer = new GridPane();
        gpLabourContainer.addRow(0, gpIndividualLabourName, gpIndividualLabourWorkedHours, gpIndividualLabourWorkedMinutes, gpIndividualLabourHourlyRate);

        GridPane gpLabourCost = new GridPane();
        gpLabourCost.setPadding(new Insets(5, 50, 5, 50));
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double labourCost = 0;
        for (int i = 0; i < ol.size(); i++) {
            labourCost += (finishedJobDetails.getLabourCostById(ol.get(i)) * finishedJobDetails.getTotalWorkedHoursOfLabourOnJob(ol.get(i), jobId).getTotalDuration());
        }
        Label lblLabourCostKey = new Label("Total employee cost");
        lblLabourCostKey.setAlignment(Pos.CENTER);
        lblLabourCostKey.setPadding(new Insets(5, 0, 5, 0));
        lblLabourCostKey.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label lblLabourCostValue = new Label(formatter.format(labourCost)+"");
        lblLabourCostValue.setAlignment(Pos.CENTER);
        lblLabourCostValue.setPadding(new Insets(5, 0, 5, 10));
        lblLabourCostValue.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gpLabourCost.addRow(0, lblLabourCostKey, lblLabourCostValue);
        
        GridPane gpGrandTotal = new GridPane();
        gpGrandTotal.setPadding(new Insets(5, 50, 5, 50));
        Label lblGrandTotalKey = new Label("Grand Total");
        lblGrandTotalKey.setAlignment(Pos.CENTER);
        lblGrandTotalKey.setPadding(new Insets(5, 0, 5, 0));
        lblGrandTotalKey.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        Label lblGrandTotalValue = new Label(formatter.format(fittingTotalCost+labourCost)+"");
        lblGrandTotalValue.setAlignment(Pos.CENTER);
        lblGrandTotalValue.setPadding(new Insets(5, 0, 5, 10));
        lblGrandTotalValue.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gpGrandTotal.addRow(0, lblGrandTotalKey, lblGrandTotalValue);
        GridPane gp = new GridPane();
        gp.addRow(0, gpJobId);
        gp.addRow(1, gpJobType);
        gp.addRow(2, gpCustomerName);
        gp.addRow(3, gpDate);
        gp.addRow(4, gpUsedFittings);
        gp.addRow(5, gpFittingsContainer);
        gp.addRow(6, gpFittingTotalCost);
        gp.addRow(7, gpWorkedLabours);
        gp.addRow(8, gpLabourContainer);
        gp.addRow(9, gpLabourCost);
        gp.addRow(10, gpGrandTotal);
        return gp;
    }
    
    public static void print(final Node node) {
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = Printer.getDefaultPrinter().getDefaultPrinter();
        PageLayout pageLayout
                = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL);
        JobSettings jobSettings = job.getJobSettings();
        jobSettings.setPageLayout(pageLayout);
        boolean printed = job.printPage(node);
        if (printed) {
            job.endJob();
        }
    }
}
