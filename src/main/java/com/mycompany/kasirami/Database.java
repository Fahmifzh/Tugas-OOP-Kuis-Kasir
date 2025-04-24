/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirami;
import java.sql.Connection;
import java.sql.DriverManager;
public class Database {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/KasirAmi";
            String user = "root";  
            String pass = "";      
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}

