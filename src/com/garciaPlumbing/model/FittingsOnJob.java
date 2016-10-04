/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.model;

/**
 *
 * @author FURIOUS
 */
public class FittingsOnJob {
    private int fittingId = 0;
    private String fittingName = null;
    private int fittingQuantity = 0;
    /**
     * @return the fittingId
     */
    public int getFittingId() {
        return fittingId;
    }

    /**
     * @param fittingId the fittingId to set
     */
    public void setFittingId(int fittingId) {
        this.fittingId = fittingId;
    }

    /**
     * @return the fittingName
     */
    public String getFittingName() {
        return fittingName;
    }

    /**
     * @param fittingName the fittingName to set
     */
    public void setFittingName(String fittingName) {
        this.fittingName = fittingName;
    }

    /**
     * @return the fittingQuantity
     */
    public int getFittingQuantity() {
        return fittingQuantity;
    }

    /**
     * @param fittingQuantity the fittingQuantity to set
     */
    public void setFittingQuantity(int fittingQuantity) {
        this.fittingQuantity = fittingQuantity;
    }
    
}