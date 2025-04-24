package com.mycompany.kasirami;

import java.sql.*;
import java.util.Scanner;

public class produk {
    static Scanner input = new Scanner(System.in);

    public static void addProduct() {
        if (!LoggedUser.getRole().equals("admin")) {
            System.out.println("Akses ditolak. Hanya admin yang bisa menambah produk.");
            return;
        }

        System.out.println("--- Tambah Produk ---");
        System.out.print("Nama Produk: ");
        String nama = input.nextLine();

        double harga = ambilHarga();
        int stok = ambilStok();

        try {
            Connection conn = Database.getConnection();
            String sql = "INSERT INTO produk (nama_produk, harga, stok) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setDouble(2, harga);
            ps.setInt(3, stok);
            ps.executeUpdate();
            System.out.println("Produk berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat menambah produk: " + e.getMessage());
        }
    }

    private static double ambilHarga() {
        while (true) {
            try {
                System.out.print("Harga: ");
                double harga = Double.parseDouble(input.nextLine());
                if (harga < 0) {
                    System.out.println("Harga tidak boleh negatif!");
                } else {
                    return harga;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka!");
            }
        }
    }

    private static int ambilStok() {
        while (true) {
            try {
                System.out.print("Stok: ");
                int stok = Integer.parseInt(input.nextLine());
                if (stok < 0) {
                    System.out.println("Stok tidak boleh negatif!");
                } else {
                    return stok;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka!");
            }
        }
    }

    public static void showProducts() {
        System.out.println("--- Daftar Produk ---");
        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM produk");
            while (rs.next()) {
                System.out.println(rs.getInt("id_produk") + ". " + rs.getString("nama_produk") + " - " +
                        rs.getDouble("harga") + " - " + rs.getInt("stok"));
            }
        } catch (Exception e) {
            System.out.println("Gagal ambil produk: " + e.getMessage());
        }
    }

    public static void deleteProduct() {
        if (!LoggedUser.getRole().equals("admin")) {
            System.out.println("Akses ditolak. Hanya admin yang bisa menghapus produk.");
            return;
        }

        System.out.print("ID Produk yang akan dihapus: ");
        int id = input.nextInt();
        input.nextLine();

        try {
            Connection conn = Database.getConnection();
            String sql = "DELETE FROM produk WHERE id_produk=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Produk berhasil dihapus.");
        } catch (Exception e) {
            System.out.println("Gagal hapus produk: " + e.getMessage());
        }
    }
}
