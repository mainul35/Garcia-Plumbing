/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author FURIOUS
 */
public class FinishedJobDetails {

    private String customerName = null;
    private String customerID = null;
    private String jobType = null;
    private String finishedOn = null;
    private CustomerAddress ca = null;
    private String workedEmployees = null;
    private int jobId = 0;
    private String usedFittings = null;

    public FinishedJobDetails(String customerID) {
        this.customerID = customerID;
    }

    public FinishedJobDetails() {
    }

    public ObservableList<FinishedJobDetails> getFinishedJobDetails(String customerID) {
        ObservableList<FinishedJobDetails> ol = FXCollections.observableArrayList();

        ObservableList<Integer> jobIds = getJobId(customerID);
        ObservableList<String> jobTypes = FXCollections.observableArrayList();
        for (int i = 0; i < jobIds.size(); i++) {
            jobTypes.add(getJobTypeName(jobIds.get(i)));
        }
        ObservableList<String> workedDates = FXCollections.observableArrayList();
        for (int i = 0; i < jobIds.size(); i++) {
            workedDates.add(getDateWorkedOn(jobIds.get(i)));
        }

        for (int x = 0; x < jobIds.size(); x++) {
            FinishedJobDetails fjd = new FinishedJobDetails(customerID);
            fjd.customerName = getCustomerNameById(customerID);
//            fjd.jobId = jobIds.get(x);
            fjd.jobType = jobTypes.get(x);
            fjd.finishedOn = workedDates.get(x);
            fjd.customerID = customerID;
            ol.add(fjd);
        }

        return ol;
    }

    public ObservableList<FinishedJobDetails> getFinishedJobDetails() {
        ObservableList<FinishedJobDetails> ol = FXCollections.observableArrayList();

        ObservableList<Integer> jobIds = getJobId(customerID);
        ObservableList<String> jobTypes = FXCollections.observableArrayList();
        for (int i = 0; i < jobIds.size(); i++) {
            jobTypes.add(getJobTypeName(jobIds.get(i)));
        }
        ObservableList<String> workedDates = FXCollections.observableArrayList();
        for (int i = 0; i < jobIds.size(); i++) {
            workedDates.add(getDateWorkedOn(jobIds.get(i)));
        }

        for (int x = 0; x < jobIds.size(); x++) {
            FinishedJobDetails fjd = new FinishedJobDetails(null);
            fjd.customerName = getCustomerNameById(customerID);
//            fjd.jobId = jobIds.get(x);
            fjd.jobType = jobTypes.get(x);
            fjd.finishedOn = workedDates.get(x);
            fjd.customerID = getAllCustomerId().get(x);
            ol.add(fjd);
        }

        return ol;
    }

    ArrayList<String> getAllCustomerId() {
        try {
            DatabaseManager.connect();
            String sql = "SELECT j.customerId\n"
                    + "FROM job j;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            ArrayList<String> al = new ArrayList();
            while (rs.next()) {
                al.add(rs.getString("customerId"));
            }
            return al;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getLabourNameById(String labourID) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT l.labourName\n"
                    + "FROM labour l\n"
                    + "WHERE l.labourId = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, labourID);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            String labourName = "";
            while (rs.next()) {
                labourName = rs.getString("labourName");
            }
            return labourName;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getCustomerIdByJobId(int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT j.customerId\n"
                    + "FROM job j\n"
                    + "WHERE j.jobId = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                this.customerID = rs.getString("customerId");
            }
            return customerID;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getCustomerNameById(String customerID) {
        try {
            DatabaseManager.connect();

            String sql = "";
            if (customerID == null) {
                sql = "SELECT c.customerName\n"
                        + "FROM job j, customer c\n"
                        + "WHERE j.customerId = c.customerID ";
                DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
//            DatabaseManager.statement.setString(1, this.getCustomerID());
                ResultSet rs = DatabaseManager.statement.executeQuery();
                String customerName = "";
                while (rs.next()) {
                    customerName = rs.getString("customerName");
                }
                return customerName;
            } else {
                sql = "SELECT c.customerName\n"
                        + "FROM job j, customer c\n"
                        + "WHERE j.customerId = c.customerID \n"
                        + "AND c.customerID = ?;";
                DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
                DatabaseManager.statement.setString(1, customerID);
                ResultSet rs = DatabaseManager.statement.executeQuery();
                String customerName = "";
                while (rs.next()) {
                    customerName = rs.getString("customerName");
                }
                return customerName;
            }

        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ObservableList<String> getWorkedLaboursOnJob(int jobId) {
        DatabaseManager.connect();
        try {
            String sql = "SELECT lj.labourId\n"
                    + "FROM labour_on_job lj, job j\n"
                    + "WHERE j.jobId = lj.jobId\n"
                    + "AND lj.jobId = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            ObservableList<String> al = FXCollections.observableArrayList();
            while (rs.next()) {
                al.add(rs.getString("labourId"));
                System.out.println(rs.getString("labourId"));
            }
            return al;
        } catch (SQLException e) {
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ObservableList<Integer> getUsedFittingsOnJob(int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT fj.fittingId\n"
                    + "FROM job j, fittings_on_job fj\n"
                    + "WHERE j.jobId = fj.jobId\n"
                    + "AND j.jobId = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            ObservableList<Integer> al = FXCollections.observableArrayList();
            while (rs.next()) {
                al.add(rs.getInt("fittingId"));
                System.out.println(rs.getInt("fittingId"));
            }
            return al;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ObservableList<Integer> getJobId(String customerId) {
        try {
            DatabaseManager.connect();
            String sql = "";
            if (customerId == null) {
                sql = "SELECT j.jobId\n"
                        + "FROM job j\n";
                DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            } else {
                sql = "SELECT j.jobId\n"
                        + "FROM job j\n"
                        + "WHERE j.customerId = ?";
                DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
                DatabaseManager.statement.setString(1, customerId);
            }

            ResultSet rs = DatabaseManager.statement.executeQuery();
            ObservableList<Integer> al = FXCollections.observableArrayList();
            while (rs.next()) {
                al.add(rs.getInt("jobId"));
                System.out.println(rs.getInt("jobId"));
            }
            return al;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public double getFittingCostsByFittingId(int fittingId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT f.fittingUnitCost\n"
                    + "FROM fitting f\n"
                    + "WHERE f.fittingId = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, fittingId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            double cost = 0;
            while (rs.next()) {
                cost = rs.getDouble("fittingUnitCost");
            }
            return cost;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public double getLabourCostById(String labourId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT l.perHourRate\n"
                    + "FROM labour l\n"
                    + "WHERE l.labourId = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, labourId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            double hourlyRate = 0;
            while (rs.next()) {
                hourlyRate = rs.getDouble("perHourRate");
                System.out.println(hourlyRate);
            }
            return hourlyRate;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public ArrayList<Integer> getFittingIDsOnJob(int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT f.fittingId\n"
                    + "FROM fitting f, fittings_on_job foj, job j\n"
                    + "WHERE f.fittingId = foj.fittingId\n"
                    + "AND foj.jobId = j.jobId\n"
                    + "and j.jobId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            ArrayList<Integer> al = new ArrayList<>();
            while (rs.next()) {
                al.add(rs.getInt("fittingId"));
            }
            return al;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String getFittingNameById(int id) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT f.fittingName\n"
                    + "FROM fitting f\n"
                    + "WHERE f.fittingId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, id);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            String fittingName = "";
            while (rs.next()) {
                fittingName = rs.getString("fittingName");
                System.out.println(fittingName);
            }
            return fittingName;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public LabourOnJob getTotalWorkedHoursOfLabourOnJob(String labourId, int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT loj.hoursWorked AS 'Hours', \n"
                    + "loj.minutesWorked AS 'Minutes', \n"
                    + "(loj.hoursWorked+(loj.minutesWorked/60)) AS 'Total duration'\n"
                    + "FROM labour l, labour_on_job loj, job j\n"
                    + "WHERE l.labourId = loj.labourId\n"
                    + "AND j.jobId = loj.jobId\n"
                    + "AND l.labourId = ?\n"
                    + "AND j.jobId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, labourId);
            DatabaseManager.statement.setInt(2, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            LabourOnJob labourOnJob = new LabourOnJob();
            while (rs.next()) {
                labourOnJob.setHours(rs.getInt("Hours"));
                labourOnJob.setMinutes(rs.getInt("Minutes"));
                labourOnJob.setTotalDuration(rs.getDouble("Total duration"));
//                System.err.println(rs.getInt("Hours"));
//                System.err.println(rs.getInt("Minutes"));
//                System.err.println(rs.getDouble("Total duration"));
            }
            return labourOnJob;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public int getFittingQuantityOnJob(int jobId, int fittingId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT foj.fittingQuantity as \"Total Quantity\"\n"
                    + "FROM fitting f, fittings_on_job foj, job j\n"
                    + "WHERE f.fittingId = foj.fittingId\n"
                    + "AND j.jobId = foj.jobId\n"
                    + "AND f.fittingId = ?\n"
                    + "AND j.jobId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, fittingId);
            DatabaseManager.statement.setInt(2, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            int quantity = 0;
            while (rs.next()) {
                quantity = rs.getInt("Total Quantity");
//                System.out.println(quantity);
            }
            return quantity;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    private String getDateWorkedOn(int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT j.dateWorkedOn\n"
                    + "FROM job j\n"
                    + "WHERE j.jobId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                this.finishedOn = rs.getString("dateWorkedOn");
            }
            return this.getFinishedOn();
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private String getJobTypeName(int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT j.jobTypeName\n"
                    + "FROM job j\n"
                    + "WHERE j.jobId = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                this.jobType = rs.getString("jobTypeName");
            }
            return this.jobType;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.statement.close();
                DatabaseManager.connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
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
     * @return the jobType
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * @return the jobType
     */
    public String getJobType(int jodID) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT j.jobTypeName\n"
                    + "FROM job j\n"
                    + "WHERE j.jobId = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, jodID);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                jobType = rs.getString("jobTypeName");
            }
            return jobType;
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the finishedOn
     */
    public String getDateFinishedOn(int jobId) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT j.dateWorkedOn\n"
                    + "FROM job j\n"
                    + "WHERE j.jobId = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, jobId);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                finishedOn = rs.getString("dateWorkedOn");
            }
            return getFinishedOn();
        } catch (SQLException ex) {
            Logger.getLogger(FinishedJobDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the jobIds
     */
    public Integer getJobId() {
        return jobId;
    }

    /**
     * @return the finishedOn
     */
    public String getFinishedOn() {
        return finishedOn;
    }

}