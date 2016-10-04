/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.model;

import com.garciaPlumbing.view.util.StringProcessor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author FURIOUS
 */
public class CustomerDetails {

    private String customerName = null;
    private String customerEmail = null;
    private String customerPassword = null;
    private String customerID = null;
    private String house = null;
    private String street = null;
    private String city = null;
    private String customerContactNo = null;
    private String customerProfileAvatar = null;

    public CustomerDetails() {
    }

    public boolean insertCustomerInfo(
            String customerName,
            String customerEmail,
            String customerPassword,
            String street,
            String city,
            String house,
            String customerContactNo,
            String customerProfileAvatar
    ) {
        String customerID = StringProcessor.getUsername(customerEmail);
        try {
            DatabaseManager.connect();
            String sql = "insert into customer("
                    + "customerID, customerName, customerEmail, customerPassword, customerContactNo, customerProfileAvatar) "
                    + "values(?,?,?,?,?,?)";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            int i = 1;
            DatabaseManager.statement.setString(i++, customerID);
            DatabaseManager.statement.setString(i++, customerName);
            DatabaseManager.statement.setString(i++, customerEmail);
            DatabaseManager.statement.setString(i++, customerPassword);
            DatabaseManager.statement.setString(i++, customerContactNo);
            DatabaseManager.statement.setString(i++, customerProfileAvatar);
            DatabaseManager.statement.executeUpdate();

//            sql = "insert into ";
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String getCustomerNameByEmail(String customerEmail) {
        String name = null;
        try {
            DatabaseManager.connect();
            String sql = "SELECT customerName from customer "
                    + "WHERE customerEmail= ? ";

            DatabaseManager.statement
                    = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, customerEmail);

            ResultSet rs = DatabaseManager.statement.executeQuery();

            while (rs.next()) {
                name = rs.getString("customerName");
            }
            return name;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return name;
    }

    
    public static ObservableList<String> getCities() {
        try {
            ObservableList<String> ol = FXCollections.observableArrayList();
            DatabaseManager.connect();
            String sql = "SELECT city FROM customer_address";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery(sql);
            while (rs.next()) {
                String cityName = rs.getString("city");
                ol.add(cityName);
            }
            return ol;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static ObservableList<String> getStreets(String city) {
        try {
            ObservableList<String> ol = FXCollections.observableArrayList();
            DatabaseManager.connect();
            String sql = "SELECT Street FROM customer_address WHERE city = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, city);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                String streetName = rs.getString("Street");
                System.out.println(streetName);
                ol.add(streetName);
            }
            return ol;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static ObservableList<String> getHouses(String street) {
        try {
            ObservableList<String> ol = FXCollections.observableArrayList();
            DatabaseManager.connect();
            String sql = "SELECT house FROM customer_address WHERE city = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, street);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                String house = rs.getString("house");
                ol.add(house);
            }
            return ol;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static ObservableList<CustomerDetails> getSpecificLocalCustomerEntries(String city, String street) {
        try {
            ObservableList<CustomerDetails> ol = FXCollections.observableArrayList();
            DatabaseManager.connect();
            String sql = "SELECT c.customerID, c.customerName, ca.house "
                    + "FROM customer c, customer_address ca "
                    + "WHERE c.customerID = ca.customerID "
                    + "AND ca.livingLocation = 'Yes' "
                    + "AND ca.city = ? "
                    + "AND ca.Street = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, city);
            DatabaseManager.statement.setString(2, street);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                CustomerDetails cd = new CustomerDetails();
                cd.customerID = rs.getString("customerID");
                cd.customerName = rs.getString("customerName");
                cd.house = rs.getString("house");
                ol.add(cd);
            }
            return ol;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ObservableList<CustomerDetails> getDefaultCustomerEntries() {
        try {
            ObservableList<CustomerDetails> ol = FXCollections.observableArrayList();
            DatabaseManager.connect();
            String sql = "SELECT c.customerID, c.customerName, ca.house, ca.city, ca.Street \n"
                    + "FROM customer c, customer_address ca\n"
                    + "WHERE c.customerID = ca.customerID\n"
                    + ";";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery(sql);
            while (rs.next()) {
                CustomerDetails cd = new CustomerDetails();
                cd.setCustomerID(rs.getString("customerID"));
                cd.setCustomerName(rs.getString("customerName"));
                cd.setHouse(rs.getString("house"));
                cd.setCity(rs.getString("city"));
                cd.setStreet(rs.getString("Street"));
                ol.add(cd);
            }
            return ol;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static ObservableList<String> getCustomerEmailList(){
        try {
            ObservableList<String> set = FXCollections.observableArrayList();
            DatabaseManager.connect();
            String sql = "SELECT customerEmail FROM customer";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                String email = rs.getString("customerEmail");
                set.add(email);
            }
            return set;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
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
     * @return the customerEmail
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * @param customerEmail the customerEmail to set
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * @return the customerPassword
     */
    public String getCustomerPassword() {
        return customerPassword;
    }

    /**
     * @param customerPassword the customerPassword to set
     */
    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    /**
     * @return the customerID
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the house
     */
    public String getHouse() {
        return house;
    }

    /**
     * @param house the house to set
     */
    public void setHouse(String house) {
        this.house = house;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the customerContactNo
     */
    public String getCustomerContactNo() {
        return customerContactNo;
    }

    /**
     * @param customerContactNo the customerContactNo to set
     */
    public void setCustomerContactNo(String customerContactNo) {
        this.customerContactNo = customerContactNo;
    }

    /**
     * @return the customerProfileAvatar
     */
    public String getCustomerProfileAvatar() {
        return customerProfileAvatar;
    }

    /**
     * @param customerProfileAvatar the customerProfileAvatar to set
     */
    public void setCustomerProfileAvatar(String customerProfileAvatar) {
        this.customerProfileAvatar = customerProfileAvatar;
    }
}
