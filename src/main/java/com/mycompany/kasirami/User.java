package com.mycompany.kasirami;

import java.sql.*;
import java.util.Scanner;

public class User {
    static Scanner input = new Scanner(System.in);

    public static void addUser() {
        if (!LoggedUser.getRole().equals("admin")) {
            System.out.println("Akses ditolak. Hanya admin yang bisa menambah user.");
            return;
        }

        System.out.println("--- Tambah User ---");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        System.out.print("Role (admin/kasir): ");
        String role = input.nextLine();

        try {
            Connection conn = Database.getConnection();
            String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();
            System.out.println("User berhasil ditambahkan.");
        } catch (Exception e) {
            System.out.println("Gagal tambah user: " + e.getMessage());
        }
    }

    public static void showUsers() {
        if (!LoggedUser.getRole().equals("admin")) {
            System.out.println("Akses ditolak. Hanya admin yang bisa melihat user.");
            return;
        }

        System.out.println("--- Daftar User ---");
        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");
            while (rs.next()) {
                System.out.println(rs.getInt("id_user") + ". " + rs.getString("username") + " - " + rs.getString("role"));
            }
        } catch (Exception e) {
            System.out.println("Gagal ambil user: " + e.getMessage());
        }
    }

    public static void deleteUser() {
        if (!LoggedUser.getRole().equals("admin")) {
            System.out.println("Akses ditolak. Hanya admin yang bisa menghapus user.");
            return;
        }

        System.out.print("ID User yang akan dihapus: ");
        int id = input.nextInt();
        input.nextLine();

        try {
            Connection conn = Database.getConnection();
            String sql = "DELETE FROM user WHERE id_user=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("User berhasil dihapus.");
        } catch (Exception e) {
            System.out.println("Gagal hapus user: " + e.getMessage());
        }
    }

    // Polymorphism: logout dengan aksi berbeda setelah login
    public static void logout() {
        System.out.println("Logout berhasil.");
        // Menampilkan pilihan setelah logout
        showLogoutOptions();
    }

    private static void showLogoutOptions() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nPilihan setelah Logout:");
        System.out.println("1. Kembali ke Menu Login");
        System.out.println("2. Keluar dari Aplikasi");
        System.out.print("Pilih opsi (1/2): ");
        int option = input.nextInt();

        if (option == 1) {
            // Kembali ke menu login
            System.out.println("Kembali ke Menu Login...");
        } else if (option == 2) {
            // Keluar dari aplikasi
            System.out.println("Terima kasih telah menggunakan aplikasi.");
            System.exit(0);
        } else {
            System.out.println("Opsi tidak valid. Silakan coba lagi.");
            showLogoutOptions(); // Tampilkan pilihan lagi jika input tidak valid
        }
    }

    // Otentikasi: Fungsi login
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
