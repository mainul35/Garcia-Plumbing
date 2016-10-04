/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author FURIOUS
 */
public class LoginLayout extends GridPane {

    String username = "admin";
    String pass = "admin";
    Label lblUserName, lblPassword;
    PasswordField password;
    TextField txtUserName;
    Button btnLogin;

    public LoginLayout() {
//        ColumnConstraints cc = new ColumnConstraints(10, 15, 20);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10));
        lblUserName = new Label("Username");
        lblPassword = new Label("Password");
        password = new PasswordField();
        txtUserName = new TextField();
        btnLogin = new Button("Log in");

        lblPassword.setPadding(new Insets(5));
        lblPassword.setMinWidth(15);
        lblUserName.setPadding(new Insets(5));
        lblUserName.setMinWidth(15);
        txtUserName.setPadding(new Insets(5));
        txtUserName.setMinWidth(80);
        password.setPadding(new Insets(5));
        password.setMinWidth(90);
        btnLogin.setPadding(new Insets(5));

        this.setAlignment(Pos.CENTER);
        this.add(lblUserName, 2, 2);
        this.add(lblPassword, 2, 3);
        this.add(txtUserName, 3, 2);
        this.add(password, 3, 3);
        this.add(btnLogin, 3, 4);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (username.equals(txtUserName.getText())) {
                        if (pass.equals(password.getText())) {
                            BasicLayout.setLoggedIn(true);
                            BasicLayout.getMainScreen().setCenter(new Layout_AddFittingType());
                            Scene scene = new Scene(BasicLayout.getMainScreen());
                            BasicLayout.stage.setScene(scene);
                        }else if(password.getText().equals("")){
                            BasicLayout.showMessageDialog("Please enter password!");
                        }else{
                            BasicLayout.showMessageDialog("Password missmatched!");
                        }
                    }else if(txtUserName.getText().equals("")){
                        BasicLayout.showMessageDialog("Please insert your user id.");
                    }else{
                        BasicLayout.showMessageDialog("This user id is not found.");
                    }
                } catch (IllegalArgumentException e) {
                }
            }
        });
    }
}
