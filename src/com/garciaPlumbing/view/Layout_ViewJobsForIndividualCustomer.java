/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.model.FinishedJobDetails;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author FURIOUS
 */
public class Layout_ViewJobsForIndividualCustomer {
    TableView<FinishedJobDetails> tblNewJobOrderEntries = new TableView<>();
    TableColumn<FinishedJobDetails, String> colCustomerName = new TableColumn<>("Customer Name");;
    TableColumn<FinishedJobDetails, String> colJobType = new TableColumn<>("Job Type");
    TableColumn<FinishedJobDetails, String> colOrderDate  = new TableColumn<>("Finished on");
    TableColumn<FinishedJobDetails, String> colJobDetails = new TableColumn<>("Job Details");
    
    public Layout_ViewJobsForIndividualCustomer(String customerID) {
        BorderPane bp = new BorderPane();
        bp.setPrefWidth(1160);
        ScrollPane sp = new ScrollPane();
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setContent(getTableOfLatestOrderEntries(customerID));
        bp.setCenter(sp);
        Scene scene = new Scene(bp);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(1150);
        stage.setMaxWidth(1160);
        stage.show();
    }
    
    private TableView<FinishedJobDetails> getTableOfLatestOrderEntries(String customerID){
        colCustomerName = new TableColumn<>("Customer Name");
        colJobType = new TableColumn<>("Job Type");
        colOrderDate = new TableColumn<>("Order Date");
        colJobDetails = new TableColumn<>("Job Details");

        FinishedJobDetails finishedJobDetails = new FinishedJobDetails();
        tblNewJobOrderEntries.setItems(finishedJobDetails.getFinishedJobDetails(customerID));
        tblNewJobOrderEntries.setPrefWidth(1145);
        tblNewJobOrderEntries.setStyle("-fx-alignment: CENTER;");

        Callback<TableColumn<FinishedJobDetails, String>, TableCell<FinishedJobDetails, String>> cellButtonShowDetails = //
                new Callback<TableColumn<FinishedJobDetails, String>, TableCell<FinishedJobDetails, String>>() {

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
                                            FinishedJobDetails item = tblNewJobOrderEntries.getSelectionModel().getSelectedItem();
                                            if (item != null) {
//                                                System.out.println(getIndex()+"\t@ line 79");
//                                                System.out.println(+"\t@ line 80");
//                                                System.err.println(+"\t@ line 81");
                                                Layout_ReportPage layout_ReportPage = new Layout_ReportPage(item.getCustomerID(), item.getJobId(item.getCustomerID()).get(getIndex()));
                                                
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

        colJobDetails.setCellFactory(cellButtonShowDetails);

        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colJobDetails.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colJobType.setCellValueFactory(new PropertyValueFactory<>("jobType"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("finishedOn"));
        colCustomerName.setStyle("-fx-alignment: CENTER;");
        colJobDetails.setStyle("-fx-alignment: CENTER;");
        colJobType.setStyle("-fx-alignment: CENTER;");
        colOrderDate.setStyle("-fx-alignment: CENTER;");
        colCustomerName.setPrefWidth(tblNewJobOrderEntries.getPrefWidth()/4);
        colJobDetails.setPrefWidth(tblNewJobOrderEntries.getPrefWidth()/4);
        colJobType.setPrefWidth(tblNewJobOrderEntries.getPrefWidth()/4);
        colOrderDate.setPrefWidth(tblNewJobOrderEntries.getPrefWidth()/4);
        tblNewJobOrderEntries.getColumns().addAll(colCustomerName, colJobType, colOrderDate, colJobDetails);
        return tblNewJobOrderEntries;
    
    }

//    /**
//     * @return the customerID
//     */
//    public String getCustomerID() {
//        return customerID;
//    }

//    /**
//     * @param customerID the customerID to set
//     */
//    public void setCustomerID(String customerID) {
//        this.customerID = customerID;
//    }

//    @Override
//    public void start(Stage stage) {
//        this.customerID = customerID;
//        BorderPane bp = new BorderPane();
//        bp.setPrefWidth(1160);
//        bp.getChildren().add(getTableOfLatestOrderEntries(customerID));
//        Scene scene = new Scene(bp);
//        stage.setScene(scene);
//        stage.setMinWidth(1150);
//        stage.setMaxWidth(1150);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
}
