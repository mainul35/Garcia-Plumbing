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

/**
 *
 * @author FURIOUS
 */
public class CustomerAddress {

    private String house = null;
    private String street = null;
    private String city = null;
    private String customerId = null;

    public CustomerAddress(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the house
     */
    public String getHouse(int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT ca.house\n"
                    + "FROM customer_address ca, job j\n"
                    + "WHERE ca.customerID = ?\n"
                    + "AND j.jobId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, this.customerId);
            DatabaseManager.statement.setInt(2, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while(rs.next()){
                this.house = rs.getString("house");
            }
            return this.house;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerAddress.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerAddress.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.house;
    }

//    /**
//     * @param house the house to set
//     */
//    public void setHouse(String house) {
//        this.house = house;
//    }

    /**
     * @return the street
     */
    public String getStreet(int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT ca.Street\n"
                    + "FROM customer_address ca, job j\n"
                    + "WHERE ca.customerID = ?\n"
                    + "AND j.jobId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, this.customerId);
            DatabaseManager.statement.setInt(2, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while(rs.next()){
                this.street = rs.getString("Street");
            }
            return this.street;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerAddress.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerAddress.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.street;
    }

//    /**
//     * @param street the street to set
//     */
//    public void setStreet(String street) {
//        this.street = street;
//    }

    /**
     * @return the city
     */
    public String getCity(int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT ca.city\n"
                    + "FROM customer_address ca, job j\n"
                    + "WHERE ca.customerID = ?\n"
                    + "AND j.jobId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, this.customerId);
            DatabaseManager.statement.setInt(2, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while(rs.next()){
                this.city = rs.getString("city");
            }
            return this.city;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerAddress.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerAddress.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.city;
    }

//    /**
//     * @param city the city to set
//     */
//    public void setCity(String city) {
//        this.city = city;
//    }

}
