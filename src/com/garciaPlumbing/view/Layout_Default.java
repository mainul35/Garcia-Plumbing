/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

/**
 *
 * @author FURIOUS
 */
public class Layout_Default extends Label{
    private static final String DEFAULT_MESSAGE = "Welcome to Garcia Plumbing Administration Panel.\n"
            + "In the left side, you can see all the menus that you can use to perform different actions.\n"
            + "To use more functionalities and even for signing out, please refer to Menu bar.";
    public Layout_Default() {
        this.setText(DEFAULT_MESSAGE);
        this.setPadding(new Insets(50));
    }
}
