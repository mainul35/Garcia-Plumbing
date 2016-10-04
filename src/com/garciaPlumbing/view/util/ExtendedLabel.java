/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.view.util;

import javafx.scene.control.Label;

/**
 *
 * @author FURIOUS
 */
public class ExtendedLabel extends Label{
    private boolean selected = false;

    public ExtendedLabel() {
        super();
    }

    public ExtendedLabel(String string) {
        super(string);
    }

    /**
     * @return the isSelected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param isSelected
     */
    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }
    
}
