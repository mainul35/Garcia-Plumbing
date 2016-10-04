/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author FURIOUS
 */
public class Layout_AddJobType extends GridPane{
    Label lblJobTypeName;
    TextField txtJobTypeName;
    Button btnSave;

    public Layout_AddJobType() {
        lblJobTypeName = new Label("Job Type Name");
        txtJobTypeName = new TextField();
        btnSave = new Button("Save");
        
        this.setVgap(10);
        this.setHgap(10);
        this.setPadding(new Insets(10));
        
        lblJobTypeName.setPrefWidth(120);
        txtJobTypeName.setPrefWidth(180);
        btnSave.setPrefWidth(100);
        
        this.setAlignment(Pos.CENTER);
        this.add(lblJobTypeName, 1, 1);
        this.add(txtJobTypeName, 2, 1);
        this.add(btnSave, 3, 1);
    }
}
