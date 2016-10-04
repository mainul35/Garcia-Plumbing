/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Siam
 */
public class JobOrderData {

    private String jobTypeNmae = null;
    private String jobOrderDescription = null;
    private String customerName = null;
    private int jobOrderId = 0;
    private String orderDate = null;
    private String yetToStart = "Yes";

    public static ObservableList<JobOrderData> getLatestOrderEntries() {
        try {
            DatabaseManager.connect();
            String sql = "SELECT c.customerName, jo.jobType, jo.jobOrderDescription, jo.orderDate, jo.jobOrderId "
                    + "FROM customer c, job_order jo "
                    + "WHERE c.customerID = jo.customerID "
                    + "AND jo.yetToStart = 'Yes'";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery(sql);
            ObservableList<JobOrderData> ol = FXCollections.observableArrayList();
            while (rs.next()) {
                JobOrderData data = new JobOrderData();
                data.setCustomerName(rs.getString("customerName"));
                data.setJobOrderDescription(rs.getString("jobOrderDescription"));
                data.setJobTypeNmae(rs.getString("jobType"));
                data.setOrderDate(rs.getString("orderDate"));
                data.setJobOrderId(rs.getInt("jobOrderId"));
                ol.add(data);
            }
            return ol;
        } catch (SQLException ex) {
            Logger.getLogger(JobOrderData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(JobOrderData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static ObservableList<String> getJobTypeList(){
        try {
            ObservableList<String> list = FXCollections.observableArrayList();
            DatabaseManager.connect();
            String sql = "SELECT jobType FROM job_order GROUP BY jobType;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                String jobType = rs.getString("jobType");
                list.add(jobType);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(JobOrderData.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(JobOrderData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * @return the jobTypeNmae
     */
    public String getJobTypeNmae() {
        return jobTypeNmae;
    }

    /**
     * @param jobTypeNmae the jobTypeNmae to set
     */
    public void setJobTypeNmae(String jobTypeNmae) {
        this.jobTypeNmae = jobTypeNmae;
    }

    /**
     * @return the jobOrderDescription
     */
    public String getJobOrderDescription() {
        return jobOrderDescription;
    }

    /**
     * @param jobOrderDescription the jobOrderDescription to set
     */
    public void setJobOrderDescription(String jobOrderDescription) {
        this.jobOrderDescription = jobOrderDescription;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the jobOrderId
     */
    public int getJobOrderId() {
        return jobOrderId;
    }

    /**
     * @param jobOrderId the jobOrderId to set
     */
    public void setJobOrderId(int jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    /**
     * @return the orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the yetToStart
     */
    public String getYetToStart() {
        return yetToStart;
    }

    /**
     * @param yetToStart the yetToStart to set
     */
    public void setYetToStart(String yetToStart) {
        this.yetToStart = yetToStart;
    }
}
