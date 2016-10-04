/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.JobOrderData;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

/**
 *
 * @author FURIOUS
 */
public class Layout_ViewJobOrders extends HBox{
    TableView<JobOrderData> tblNewJobOrderEntries = new TableView<>();
    ObservableList<JobOrderData> ol = null;
    TableColumn<JobOrderData, String> colCustomerName = new TableColumn<>("Customer Name");;
    TableColumn<JobOrderData, String> colJobType = new TableColumn<>("Job Type");
    TableColumn<JobOrderData, String> colJobDescription = new TableColumn<>("Job Description");;
    TableColumn<JobOrderData, String> colOrderDate  = new TableColumn<>("Order Date");
    TableColumn<JobOrderData, String> colStartJob = null;

    public Layout_ViewJobOrders() {
        this.setPrefWidth(1160);
        this.getChildren().add(getTableOfLatestOrderEntries());
    }
    
    private TableView<JobOrderData> getTableOfLatestOrderEntries(){
//        colCustomerName = new TableColumn<>("Customer Name");
//        colJobDescription = new TableColumn<>("Job Description");
//        colJobType = new TableColumn<>("Job Type");
//        colOrderDate = new TableColumn<>("Order Date");
        ol = JobOrderData.getLatestOrderEntries();
        tblNewJobOrderEntries.setItems(ol);
        tblNewJobOrderEntries.setPrefWidth(1145);
        tblNewJobOrderEntries.setStyle("-fx-alignment: CENTER;");
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colJobDescription.setCellValueFactory(new PropertyValueFactory<>("jobOrderDescription"));
        colJobType.setCellValueFactory(new PropertyValueFactory<>("jobTypeNmae"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCustomerName.setStyle("-fx-alignment: CENTER;");
        colJobDescription.setStyle("-fx-alignment: CENTER;");
        colJobType.setStyle("-fx-alignment: CENTER;");
        colOrderDate.setStyle("-fx-alignment: CENTER;");
        colCustomerName.setPrefWidth(tblNewJobOrderEntries.getPrefWidth()/4);
        colJobDescription.setPrefWidth(tblNewJobOrderEntries.getPrefWidth()/4);
        colJobType.setPrefWidth(tblNewJobOrderEntries.getPrefWidth()/4);
        colOrderDate.setPrefWidth(tblNewJobOrderEntries.getPrefWidth()/4);
        tblNewJobOrderEntries.getColumns().addAll(colCustomerName, colJobType, colJobDescription, colOrderDate);
        return tblNewJobOrderEntries;
    
    }
}
