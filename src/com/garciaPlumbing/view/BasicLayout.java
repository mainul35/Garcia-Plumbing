/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import com.garciaPlumbing.view.util.ExtendedLabel;
import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author FURIOUS
 */
public class BasicLayout extends Application{
    
    private static final String[] MENU_ITEM_LABELS = 
    {"Welcome","Insert Fitting details", "View fitting details", "Insert Labour details", 
    "View Labour details", "View Customer Details", "View Ordered Jobs", "Insert job description","View Jobs Worked",
    "View Pending jobs", "Profile Settings", "Sign out"
    };
//    public static final int DEFAULT = 1000000000;
    public static MenuItemTask menuItemTask = new MenuItemTask();
    private static boolean loggedIn = false;
    protected static Stage stage = null;
    private final static String BANNER_BACKGROUND = "-fx-background-color:#BFF196";
    private final static String LEFT_CONTAINER_BACKGROUND = "-fx-background-color:#4195FF;"
            + "-fx-text-fill:white";
    private final static Font MENU_ITEM_FONT = Font.font("Calibri", FontWeight.BOLD, 14);
    private final static Font BANNER_FONT = Font.font("Calibri", FontPosture.REGULAR, 24);
    private final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private final static double SCREEN_WIDTH = SCREEN_SIZE.getWidth();
    private final static double SCREEN_HEIGHT = SCREEN_SIZE.getHeight();
    static ExtendedLabel[] lblMEnuItems = null;
    static BorderPane borderPane = new BorderPane();
    
    public BasicLayout() {
        
    }

    /**
     * @return the loggedIn
     */
    public static boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param aLoggedIn the loggedIn to set
     */
    public static void setLoggedIn(boolean aLoggedIn) {
        loggedIn = aLoggedIn;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        BasicLayout.stage = primaryStage;
        Scene scene = new Scene(BasicLayout.getMainScreen());
        stage.setScene(scene);
        stage.show();
    }
    
    public static BorderPane getMainScreen(){
        LoginLayout loginLayout = new LoginLayout();
        stage.setMinHeight(700);
        stage.setMinWidth(1280);
        stage.setMaxHeight(SCREEN_HEIGHT);
        stage.setMaxWidth(SCREEN_WIDTH);
        borderPane.setTop(BasicLayout.getTopPane());
        if(isLoggedIn()==false){
            borderPane.setCenter(loginLayout);
        }else{
            borderPane.setLeft(getLeftPane());
            borderPane.setCenter(new Layout_Default());
        }
        return borderPane;
    }
    
    public static Pane getTopPane(){
        Pane topPane = new Pane();
        Label lblTop = new Label();
        lblTop.setMinWidth(SCREEN_WIDTH);
        lblTop.setMinHeight(100);
        lblTop.setStyle(BANNER_BACKGROUND);
        lblTop.setFont(BANNER_FONT);
        lblTop.setText("Garcia Plumbing");
        lblTop.setAlignment(Pos.CENTER);
        topPane.getChildren().add(lblTop);
        return topPane;
    }
    
    public static Pane getLeftPane(){
        GridPane gridPane = new GridPane();
        gridPane.setMinHeight(600);
        gridPane.setMinWidth(220);
        gridPane.setStyle(LEFT_CONTAINER_BACKGROUND);
        
        lblMEnuItems = new ExtendedLabel[9];
        for(int i = 0; i<lblMEnuItems.length; i++){
            lblMEnuItems[i] = new ExtendedLabel();
            lblMEnuItems[i].setMinWidth(220);
            lblMEnuItems[i].setMaxHeight(50);
            lblMEnuItems[i].setPadding(new Insets(18, 25, 10, 25));
            lblMEnuItems[i].setText(MENU_ITEM_LABELS[i]);
            lblMEnuItems[i].setStyle(LEFT_CONTAINER_BACKGROUND);
            lblMEnuItems[i].setFont(MENU_ITEM_FONT);

            lblMEnuItems[i].setOnMouseEntered(new EventHandler<MouseEvent>() {

                public void handle(MouseEvent e) {
                    for(int x = 0; x<lblMEnuItems.length; x++){
                        if(e.getSource().equals(lblMEnuItems[x])){
                            lblMEnuItems[x].setStyle("-fx-background-color:#dddddd; -fx-text-fill:black");
                        }
                    }
                }
            });
            
            lblMEnuItems[i].setOnMouseExited(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent e) {
                    for(int x = 0; x<lblMEnuItems.length; x++){
                        if(e.getSource().equals(lblMEnuItems[x]) && lblMEnuItems[x].isSelected()!=true){
                            lblMEnuItems[x].setStyle(LEFT_CONTAINER_BACKGROUND);
                        }
                    }
                    for(int x = 0; x<lblMEnuItems.length; x++){
                        if(lblMEnuItems[x].isSelected()==true){
                            lblMEnuItems[x].setStyle("-fx-background-color:#BFF196; -fx-text-fill:black");
                        }
                    }
                }
            });
            
            lblMEnuItems[i].setOnMouseClicked(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent e) {
                    for(int x = 0; x<lblMEnuItems.length; x++){
                        if(lblMEnuItems[x].isSelected()==true){
                            lblMEnuItems[x].setStyle(LEFT_CONTAINER_BACKGROUND);
                            lblMEnuItems[x].setSelected(false);
                        }
                    }
                    for(int x = 0; x<lblMEnuItems.length; x++){
                        if(e.getSource().equals(lblMEnuItems[x])){
                            lblMEnuItems[x].setStyle("-fx-background-color:#BFF196; -fx-text-fill:black");
                            lblMEnuItems[x].setSelected(true);
                            menuItemTask.doInBackground(x);
                        }
                    }
                }
            });
            gridPane.add(lblMEnuItems[i],0, i);
        }
        return gridPane;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void showMessageDialog(String message) {
        VBox container = new VBox();
        container.setStyle("-fx-background-color:#cddcee");
        container.setPadding(new Insets(10, 10, 10, 10));
        container.setSpacing(10);
        Label messageLabel = new Label(message);
        container.setPrefWidth(messageLabel.getPrefWidth());
        container.setPrefHeight(messageLabel.getPrefHeight());
        container.setAlignment(Pos.CENTER);
        messageLabel.setAlignment(Pos.CENTER);

        HBox buttonContainer = new HBox();
        buttonContainer.setPadding(new Insets(0, 0, 0, 100));
        buttonContainer.setSpacing(5);
        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        Button btnOK = new Button("OK");
        btnOK.setPrefSize(130, 30);

        Button btnCancell = new Button("Cancel");
        btnCancell.setPrefSize(130, 30);

        buttonContainer.getChildren().addAll(btnOK, btnCancell);

        container.getChildren().addAll(messageLabel, buttonContainer);

        Scene scene = new Scene(container);
        Stage stage = new Stage();
        stage.setTitle("Message");
        stage.setResizable(false);
        
        btnOK.setOnAction(e -> {
            stage.close();
        });
        
        btnCancell.setOnAction(e -> {
            stage.close();
        });
        stage.setScene(scene);
        stage.show();
    }

}
