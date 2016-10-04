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
public class LabourOnJob {
    private int hours = 0;
    private int minutes = 0;
    private double totalDuration  = 0;

    /**
     * @return the hours
     */
    public int getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * @return the minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @param minutes the minutes to set
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * @return the totalDuration
     */
    public double getTotalDuration() {
        return totalDuration;
    }

    /**
     * @param totalDuration the totalDuration to set
     */
    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }
}
