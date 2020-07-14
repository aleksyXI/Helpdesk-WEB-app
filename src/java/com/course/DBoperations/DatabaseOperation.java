package com.course.DBoperations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author afedo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperation {

    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String db_url = "jdbc:mysql://localhost:3306/course2",
                    db_userName = "root",
                    db_password = "";
            connObj = DriverManager.getConnection(db_url, db_userName, db_password);
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
        return connObj;
    }
    
    public static boolean validate(String user, int password) {

        try {
            connObj = getConnection();
            pstmt = connObj.prepareStatement("Select user_name, user_password from users where user_name = ? and user_password = ?");
            pstmt.setString(1, user);
            pstmt.setInt(2, password);
            resultSetObj = pstmt.executeQuery();
            if (resultSetObj.next()) {
                
                connObj.close();
                return true;
            }
            connObj.close();
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());             
            return false;
        } finally {           
        }
        return false;
    }
    
    public static String role(String user) {
        try {
            connObj = getConnection();
            pstmt = connObj.prepareStatement("Select user_role from users where user_name = ?");
            pstmt.setString(1, user);
            resultSetObj = pstmt.executeQuery();

            if (resultSetObj.next()) {
                
                return resultSetObj.getString("user_role");
            }
            connObj.close();
        } catch (SQLException ex) {
            System.out.println("Role error -->" + ex.getMessage());
        } 
        return "fff";        
    }
}
