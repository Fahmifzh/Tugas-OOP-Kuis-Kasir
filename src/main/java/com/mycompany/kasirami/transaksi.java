/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kasirami;
import java.sql.*;
import java.util.Scanner;
public class transaksi {
    static Scanner input = new Scanner(System.in);

    public static void doTransaction() {
        if (!LoggedUser.getRole().equals("kasir")) {
            System.out.println("Akses ditolak. Hanya kasir yang bisa melakukan transaksi.");
            return;
        }

        System.out.println("--- Transaksi Pembelian ---");
        System.out.print("ID Produk: ");
        int idProduk = input.nextInt();
        System.out.print("Jumlah: ");
        int jumlah = input.nextInt();
        input.nextLine();

        try {
            Connection conn = Database.getConnection();
            // Retrieve product details
            String sql = "SELECT * FROM produk WHERE id_produk=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idProduk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int stok = rs.getInt("stok");
                double harga = rs.getDouble("harga");

                if (stok < jumlah) {
                    System.out.println("Stok tidak cukup!");
                } else {
                    double total = harga * jumlah;
                    System.out.println("Total: " + total);

                    // Update product stock
                    String updateSql = "UPDATE produk SET stok = stok - ? WHERE id_produk=?";
                    PreparedStatement updatePs = conn.prepareStatement(updateSql);
                    updatePs.setInt(1, jumlah);
                    updatePs.setInt(2, idProduk);
                    updatePs.executeUpdate();

                    // Log the transaction
                    String transaksiSql = "INSERT INTO transaksi (id_produk, jumlah, total) VALUES (?, ?, ?)";
                    PreparedStatement transaksiPs = conn.prepareStatement(transaksiSql);
                    transaksiPs.setInt(1, idProduk);
                    transaksiPs.setInt(2, jumlah);
                    transaksiPs.setDouble(3, total);
                    transaksiPs.executeUpdate();

                    System.out.println("Transaksi berhasil!");
                }
            } else {
                System.out.println("Produk tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat transaksi: " + e.getMessage());
        }
    }
}
