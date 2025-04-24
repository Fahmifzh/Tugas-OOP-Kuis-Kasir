
package com.mycompany.kasirami;
import java.sql.*;
import java.util.Scanner;
public class login {
     static Scanner input = new Scanner(System.in);

    public static boolean login() {
        System.out.println("==== LOGIN ====");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LoggedUser.setId(rs.getInt("id_user"));
                LoggedUser.setUsername(rs.getString("username"));
                LoggedUser.setRole(rs.getString("role"));
                 System.out.println("Login berhasil. Role: " + LoggedUser.getRole());
                return true;
            } else {
                System.out.println("Username / Password salah.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }
}
