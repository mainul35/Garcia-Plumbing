/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.model;

import com.garciaPlumbing.view.BasicLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Siam
 */
public class FittingDetails {

    private String supplierName;
    private String fittingName;
    private double fittingCost;
    private String fittingTypeName;

    /**
     * @return the supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * @param supplierName the supplierName to set
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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
     * @return the fittingCost
     */
    public double getFittingCost() {
        return fittingCost;
    }

    /**
     * @param fittingCost the fittingCost to set
     */
    public void setFittingCost(double fittingCost) {
        this.fittingCost = fittingCost;
    }

    /**
     * @return the fittingTypeName
     */
    public String getFittingTypeName() {
        return fittingTypeName;
    }

    /**
     * @param fittingTypeName the fittingTypeName to set
     */
    public void setFittingTypeName(String fittingTypeName) {
        this.fittingTypeName = fittingTypeName;
    }

    /**
     * @return an ObservableList<FittingDetails> object.
     */
    public ObservableList<FittingDetails> getFittingDetails() {
        try {
            ObservableList<FittingDetails> ol = FXCollections.observableArrayList();
            DatabaseManager.connect();
            String sql = "SELECT s.supplierName, ft.fittingTypeName, f.fittingName, f.fittingUnitCost "
                    + "FROM supplier s, fitting f, fitting_type ft "
                    + "WHERE s.supplierID = ft.supplierID "
                    + "AND ft.fittingTypeId = f.fittingTypeId;";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while (rs.next()) {
                FittingDetails fd = new FittingDetails();
                fd.fittingCost = rs.getDouble("fittingUnitCost");
                fd.fittingName = rs.getString("fittingName");
                fd.fittingTypeName = rs.getString("fittingTypeName");
                fd.supplierName = rs.getString("supplierName");
                ol.add(fd);
            }
            return ol;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean insertSupplier(String supplierName) {
        try {
            DatabaseManager.connect();
            String sql = "SELECT supplierName FROM supplier WHERE supplierName = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, supplierName);
            ResultSet rs = DatabaseManager.statement.executeQuery();

            while (rs.next()) {
                if (rs.getString("supplierName").equals(supplierName)) {
                    return false;
                }
            }

            sql = "insert into supplier(supplierName) values (?)";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, supplierName);
            DatabaseManager.statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            BasicLayout.showMessageDialog("Error inserting data.\n" + ex.getMessage());
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public ArrayList<String> getAllSuppliers() {
        try {
            ArrayList<String> suppliers = new ArrayList<>();
            DatabaseManager.connect();

            String sql = "SELECT * FROM supplier";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.executeQuery(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery();

            while (rs.next()) {
                suppliers.add(rs.getString("supplierName"));
            }
            rs.close();
            return suppliers;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean insertFittingType(String fittingTypeName, String supplierName) {
        try {
            int supplierID = getSupplierIdByName(supplierName);
            DatabaseManager.connect();
            String sql = "SELECT fittingTypeName FROM fitting_type WHERE fittingTypeName = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, fittingTypeName);
            ResultSet rs = DatabaseManager.statement.executeQuery();

            while (rs.next()) {
                if (rs.getString("fittingTypeName").equals(fittingTypeName)) {
                    return false;
                }
            }

            sql = "insert into fitting_type(fittingTypeName, supplierID) values (?, ?)";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, fittingTypeName);
            DatabaseManager.statement.setInt(2, supplierID);
            DatabaseManager.statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            BasicLayout.showMessageDialog("Error inserting data.\n" + ex.getMessage());
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public ArrayList<String> getAllFittingTypeNames() {

        try {
            ArrayList<String> al = new ArrayList<>();
            DatabaseManager.connect();
            String sql = "SELECT * FROM fitting_type";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            ResultSet rs = DatabaseManager.statement.executeQuery();

            while (rs.next()) {
                al.add(rs.getString("fittingTypeName"));
            }
            rs.close();
            return al;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static boolean insertFittingDetails(String fittingName, String retailPrice, String fittingTypeName) {
        try {
            int fittingTypeID = getFittingTypeIDByFittingType(fittingTypeName);
            DatabaseManager.connect();
            String sql = "SELECT fittingName FROM fitting WHERE fittingName = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, fittingName);
            ResultSet rs = DatabaseManager.statement.executeQuery();

            while (rs.next()) {
                if (rs.getString("fittingName").equals(fittingName)) {
                    return false;
                }
            }
            
            sql = "insert into fitting(fittingName, fittingUnitCost, fittingTypeId) values (?, ?, ?)";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, fittingName);
            DatabaseManager.statement.setDouble(2, Double.parseDouble(retailPrice));
            DatabaseManager.statement.setInt(3, fittingTypeID);
            DatabaseManager.statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            BasicLayout.showMessageDialog("Error inserting data.\n" + ex.getMessage());
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public static void updateFittingDetails(String fittingName, String supplierName, String fittingTypeName, String price, FittingDetails fd) {
        try {
            int supplierID = getSupplierIdByName(supplierName);
            int fittingTypeID = getFittingTypeIDByFittingType(fittingTypeName);
            DatabaseManager.connect();
            String sql = "update fitting "
                    + "set fitting.fittingTypeId= ?, "
                    + "fitting.fittingUnitCost = ?, "
                    + "fitting.fittingName=? "
                    + "where fitting.fittingName=?" ;
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, fittingTypeID);
            DatabaseManager.statement.setDouble(2, Double.parseDouble(price));
            DatabaseManager.statement.setString(3, fittingName);
            DatabaseManager.statement.setString(4, fd.getFittingName());
            DatabaseManager.statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static int getFittingTypeIDByFittingType(String fittingTypeName) {
        try {
            DatabaseManager.connect();
            int fittingTypeID = 0;
            String sql = "SELECT fittingTypeId FROM fitting_type WHERE fittingTypeName = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, fittingTypeName);
            ResultSet rs = DatabaseManager.statement.executeQuery();

            while (rs.next()) {
                fittingTypeID = rs.getInt("fittingTypeId");
            }
            return fittingTypeID;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public static int getSupplierIdByName(String supplierName) {
        try {
            DatabaseManager.connect();
            int supplierID = 0;
            String sql = "SELECT supplierID FROM supplier WHERE supplierName = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, supplierName);
            ResultSet rs = DatabaseManager.statement.executeQuery();

            while (rs.next()) {
                supplierID = rs.getInt("supplierID");
            }
            return supplierID;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    public static String getFittingTypeBySupplier(String supplierName){
        String fittingTypeName = null;
        try {
            int supplierID = FittingDetails.getSupplierIdByName(supplierName);
            DatabaseManager.connect();
            String sql = "select fittingTypeName from fitting_type where supplierID = ?";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setInt(1, supplierID);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while(rs.next()){
                fittingTypeName = rs.getString("fittingTypeName");
            }
            return fittingTypeName;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fittingTypeName;
    }
    
    public static String getSupplierByFittingType(String fittingTypeName){
        String supplierName = null;
        try {
            DatabaseManager.connect();
            String sql = "select supplierName "
                    + "from supplier, fitting_type "
                    + "where supplier.supplierID = fitting_type.supplierID "
                    + "and fitting_type.fittingTypeName = ? ";
            DatabaseManager.statement = DatabaseManager.connection.prepareStatement(sql);
            DatabaseManager.statement.setString(1, fittingTypeName);
            ResultSet rs = DatabaseManager.statement.executeQuery();
            while(rs.next()){
                supplierName = rs.getString("supplierName");
            }
            return supplierName;
        } catch (SQLException ex) {
            Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                DatabaseManager.connection.close();
                DatabaseManager.statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(FittingDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return supplierName;
    }
}