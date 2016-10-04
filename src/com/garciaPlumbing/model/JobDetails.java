/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.model;

import com.garciaPlumbing.view.util.Mailer;
import com.garciaPlumbing.view.util.StringProcessor;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Siam
 */
public class JobDetails {

    public static ObservableList<String> getBathroomFittings(String fittingTypeName) {
        try {
            DatabaseManager.connect();
            String sql = "";
            if (fittingTypeName.equals("Repair")) {
                sql = "select f.fittingName from fitting f, fitting_type ft where f.`fittingTypeId` = ft.`fittingTypeId`;";
            } else if (fittingTypeName.equals("Kitchen and Bathroom Installation")) {
                sql = "select f.fittingName \n"
                        + "from fitting f, fitting_type ft \n"
                        + "where f.`fittingTypeId` = ft.`fittingTypeId`\n"
                        + "and not ft.`fittingTypeName`='Combination Boiler Parts'\n"
                        + "group by f.fittingName;";
            } else if (fittingTypeName.equals("Kitchen Installation")) {
                sql = "select f.fittingName \n"
                        + "from fitting f, fitting_type ft \n"
                        + "where f.`fittingTypeId` = ft.`fittingTypeId`\n"
                        + "and ft.`fittingTypeName`='Kitchen Fittings'\n"
                        + "group by f.fittingName;";
            } else if (fittingTypeName.equals("Bathroom Installation")) {
                sql = "select f.fittingName \n"
                        + "from fitting f, fitting_type ft \n"
                        + "where f.`fittingTypeId` = ft.`fittingTypeId`\n"
                        + "and ft.`fittingTypeName`='Bathroom Fittings'\n"
                        + "group by f.fittingName;";
            } else if (fittingTypeName.equals("Combination Boiler Parts Installation")) {
                sql = "select f.fittingName \n"
                        + "from fitting f, fitting_type ft \n"
                        + "where f.`fittingTypeId` = ft.`fittingTypeId`\n"
                        + "and ft.`fittingTypeName`='Combination Boiler Parts'\n"
                        + "group by f.fittingName;";
            }

            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            ObservableList<String> ol = FXCollections.observableArrayList();
            while (rs.next()) {
                ol.add(rs.getString("fittingName"));
            }
            return ol;
        } catch (SQLException ex) {
            Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void createCustomer(
            String customerName, String customerID,
            String customerEmail, String house,
            String city, String Street) {
        try {
            DatabaseManager.connect();
            String sql = "insert into customer(customerName, customerEmail, customerID, customerPassword)"
                    + " values(?,?,?,?);";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, customerName);
            DatabaseManager.statement.setString(2, customerEmail);
            System.out.println(customerEmail + "\t @line 84");
            DatabaseManager.statement.setString(3, customerID);
            DatabaseManager.statement.setString(4, "12345");
            DatabaseManager.statement.executeUpdate();

            sql = "insert into customer_address(house, Street, city, customerID) values (?,?,?,?);";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, house);
            DatabaseManager.statement.setString(2, Street);
            DatabaseManager.statement.setString(3, city);
            DatabaseManager.statement.setString(4, customerID);
            DatabaseManager.statement.executeUpdate();
            Mailer mailer = new Mailer();
//            mailer.setEmail("mainuls18@gmail.com");
//                mailer.setPassword();
                mailer.sendMail(customerEmail, "Welcoming greetings from Garcia Plumbing!!", "Your user id is: "+customerID
                        +"\n and Default Password is: D1gULoyc. \n You can visit your profile with your ID and Password and "
                        + "change your default profile information.");
                
        } catch (SQLException ex) {
            Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static boolean doesCustomerExists(String customerEmail) {
        try {
            DatabaseManager.connect();
            String sql = "select customerEmail from customer where customerEmail = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, customerEmail);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean insertWorkedJobDetails(
            String customerName, String customerID,
            String house, String city,
            String Street, String customerEmail,
            String jobTypeName, Date dateWorkedOn,
            ArrayList<String> fittings, ArrayList<Integer> quantities,
            ArrayList<String> labours, ArrayList<Integer> hours,
            ArrayList<Integer> mins) {
        if (doesCustomerExists(customerEmail) == true) {
            try {
                DatabaseManager.connect();
//                System.out.println(customerEmail + "\t@ line 137");

                String sql = "insert into job(customerId, jobTypeName, dateWorkedOn) values(?,?,?)";
                DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                DatabaseManager.statement.setString(1, customerID);
                DatabaseManager.statement.setString(2, jobTypeName);
                DatabaseManager.statement.setDate(3, dateWorkedOn);
                DatabaseManager.statement.executeUpdate();
                ResultSet rs = DatabaseManager.statement.getGeneratedKeys();
                rs.next();
                int jobId = rs.getInt(1);
//                System.out.println(Arrays.toString(labours)+" @ line 149");

                insertDetailsOfLaboursWorkedOnAJob(labours, jobId, hours, mins);
                insertQuantityOfUsedFittingsOnAJob(jobId, fittings, quantities);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    DatabaseManager.connection.close();
                    DatabaseManager.statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (doesCustomerExists(customerEmail) == false) {
            try {
                createCustomer(customerName, customerID, customerEmail, house, city, Street);
                DatabaseManager.connect();
                String sql = "insert into job(customerId, jobTypeName, dateWorkedOn) values(?,?,?)";
                DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                DatabaseManager.statement.setString(1, customerID);
                DatabaseManager.statement.setString(2, jobTypeName);
                DatabaseManager.statement.setDate(3, dateWorkedOn);
                DatabaseManager.statement.executeUpdate();
                ResultSet tableKeys = DatabaseManager.statement.getGeneratedKeys();
                tableKeys.next();
                int jobId = tableKeys.getInt(1);
                insertDetailsOfLaboursWorkedOnAJob(labours, jobId, hours, mins);
                insertQuantityOfUsedFittingsOnAJob(jobId, fittings, quantities);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    DatabaseManager.connection.close();
                    DatabaseManager.statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    public void insertDetailsOfLaboursWorkedOnAJob(ArrayList<String> labours,
            int jobId, ArrayList<Integer> hours, ArrayList<Integer> mins) {
        try {
            ArrayList<String> al = new ArrayList<>();
            for(int x = 0; x < labours.size(); x++){
                al.add(gerLabourIdByLabourName(labours.get(x)));
            }
            DatabaseManager.connect();
            String sql = "";
            int total = labours.size();
            sql = "insert into labour_on_job(labourId, jobId, hoursWorked, minutesWorked) values(?,?,?,?);";
            for (int x = 0; x < total; x++) {
                DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
//                System.out.println(Arrays.toString(labours)+" @ line 149"+labours[x]);
                DatabaseManager.statement.setString(1, al.get(x));
                DatabaseManager.statement.setInt(2, jobId);
                DatabaseManager.statement.setInt(3, hours.get(x));
                DatabaseManager.statement.setInt(4, mins.get(x));
                DatabaseManager.statement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void insertQuantityOfUsedFittingsOnAJob(int jobId, ArrayList<String> fittings,
            ArrayList<Integer> quantities) {
        try {
            ArrayList<Integer> al = new ArrayList<>();
            for(int x = 0; x < fittings.size(); x++){
                al.add(getFittingIdByName(fittings.get(x)));                
            }
            DatabaseManager.connect();
            int total = fittings.size();
            for (int x = 0; x < total; x++) {

                String sql = "insert into fittings_on_job(jobId, fittingId, fittingQuantity) values (?,?,?);";
                DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
                DatabaseManager.statement.setInt(1, jobId);
                DatabaseManager.statement.setInt(2, al.get(x));
                DatabaseManager.statement.setInt(3, quantities.get(x));
                DatabaseManager.statement.executeUpdate();
                DatabaseManager.statement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private int getFittingIdByName(String fittingName) {
        try {
            DatabaseManager.connect();
            String sql = "select f.fittingId from fitting f where f.fittingName = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, fittingName);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            int fittingId = 0;
            while (rs.next()) {
                 fittingId = rs.getInt("fittingId");
            }
            return fittingId;
        } catch (SQLException ex) {
            Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    private String gerLabourIdByLabourName(String labourName){
        try {
            DatabaseManager.connect();
            String sql = "SELECT l.labourId FROM labour l WHERE l.labourName = ?;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, labourName);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            String labourId = "";
            while (rs.next()) {                
                labourId = rs.getString("labourId");
            }
            return labourId;
        } catch (SQLException ex) {
            Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(JobDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
