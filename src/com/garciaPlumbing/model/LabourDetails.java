/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.model;

import com.garciaPlumbing.view.BasicLayout;
import com.garciaPlumbing.view.util.Mailer;
import com.garciaPlumbing.view.util.StringProcessor;
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
public class LabourDetails {

    private String labourId;
    private String labourName;
    private String email;
    private String password;
    private double perHourRate;
    private String available;

    public LabourDetails() {
    }

    public LabourDetails(String labourName, String email, String password, double perHourRate, String isAvailable) {
        try {
            this.labourId = StringProcessor.getUsername(email);
            this.labourName = labourName;
            this.email = email;
            this.password = password;
            this.perHourRate = perHourRate;
            this.available = isAvailable;
            DatabaseManager.connect();
            String sql = "INSERT INTO labour(labourId, labourName, perHourRate, email, password, available)"
                    + "VALUES(?,?,?,?,?,?);";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, this.labourId);
            DatabaseManager.statement.setString(2, this.labourName);
            DatabaseManager.statement.setDouble(3, this.perHourRate);
            DatabaseManager.statement.setString(4, this.email);
            DatabaseManager.statement.setString(5, this.password);
            DatabaseManager.statement.setString(6, this.available);
            DatabaseManager.statement.executeUpdate();
            Mailer mailer = new Mailer();
            mailer.sendMail(this.email, "Greetings from Garcia Plumbing", "Your user id is: " + this.labourId
                    + "\n And Default Password is: " + this.password + ". \n You can visit your profile with your ID and Password and "
                    + "change your default profile information.");

//            BasicLayout.showMessageDialog("Labour added successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the labourId
     */
    public String getLabourId() {
        return labourId;
    }

    /**
     * @param labourId the labourId to set
     */
    public void setLabourId(String labourId) {
        this.labourId = labourId;
    }

    /**
     * @return the labourName
     */
    public String getLabourName() {
        return labourName;
    }

    /**
     * @param labourName the labourName to set
     */
    public void setLabourName(String labourName) {
        this.labourName = labourName;
    }

    /**
     * @return the perHourRate
     */
    public double getPerHourRate() {
        return perHourRate;
    }

    /**
     * @param perHourRate the perHourRate to set
     */
    public void setPerHourRate(double perHourRate) {
        this.perHourRate = perHourRate;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the isAvailable
     */
    public String getAvailable() {
        return available;
    }

    /**
     * @param available the isAvailable to set
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    public static int countLabour() {
        try {
            DatabaseManager.connect();
            String sql = "select count(labourId) as 'Total' from labour;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            int total = 0;
            while (rs.next()) {
                total = rs.getInt("Total");
            }
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public static ObservableList<LabourDetails> getListOfLabour() {
        try {
            ObservableList<LabourDetails> ol = FXCollections.observableArrayList();
            DatabaseManager.connect();
            String sql = "SELECT labourId, labourName, perHourRate, email, available FROM labour;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                LabourDetails labourDetails = new LabourDetails();
                labourDetails.labourName = rs.getString("labourName");
                labourDetails.email = rs.getString("email");
                labourDetails.perHourRate = rs.getDouble("perHourRate");
                labourDetails.available = rs.getString("available");
                labourDetails.labourId = rs.getString("labourId");
                ol.add(labourDetails);
            }
            return ol;
        } catch (SQLException ex) {
            Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static void updateLabourDetails(String labourName, String email, String available, String perHourRate, String labourId) {
        try {
            DatabaseManager.connect();
            String sql = "UPDATE labour "
                    + "SET labourName = ?,"
                    + "email = ?,"
                    + "available = ?,"
                    + "perHourRate = ?"
                    + "WHERE labourId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, labourName);
            DatabaseManager.statement.setString(2, email);
            DatabaseManager.statement.setString(3, available);
            DatabaseManager.statement.setDouble(4, Double.parseDouble(perHourRate));
            DatabaseManager.statement.setString(5, labourId);
            DatabaseManager.statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(LabourDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
