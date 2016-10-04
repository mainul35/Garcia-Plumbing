/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.garciaPlumbing.model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tarif Hasan
 */
public class DatabaseManager {
    private static final String CLASS_FORNAME = "com.mysql.jdbc.Driver";
    private static final String DATABASE_LOCATION = "jdbc:mysql://localhost:3306/";//"jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "GarciaPlumbing2";//"GarciaPlumbing2";
    private static final String USERNAME = "root";//"root";
    
    protected static Connection connection = null;
    protected static PreparedStatement statement = null;
    
    /**
     * 
     * Connect() method is responsible for establishing any connection to database.
     * 
     */
    public static void connect(){
        try {
            Class.forName(CLASS_FORNAME);
            connection = (Connection) DriverManager.getConnection(
                    DATABASE_LOCATION+DATABASE_NAME, USERNAME, "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
