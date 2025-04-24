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
        if (!LoggedUser.role.equals("kasir")) {
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
            String sql = "SELECT * FROM produk WHERE id_produk=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idProduk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int stok = rs.getInt("stok");
                if (stok >= jumlah) {
                    double harga = rs.getDouble("harga");
                    double total = harga * jumlah;

                    // Update stok produk
                    String updateStok = "UPDATE produk SET stok=? WHERE id_produk=?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateStok);
                    updateStmt.setInt(1, stok - jumlah);
                    updateStmt.setInt(2, idProduk);
                    updateStmt.executeUpdate();

                    // Insert transaksi
                    String insertTransaksi = "INSERT INTO transaksi (id_produk, jumlah, total) VALUES (?, ?, ?)";
                    PreparedStatement insertStmt = conn.prepareStatement(insertTransaksi);
                    insertStmt.setInt(1, idProduk);
                    insertStmt.setInt(2, jumlah);
                    insertStmt.setDouble(3, total);
                    insertStmt.executeUpdate();

                    System.out.println("Transaksi berhasil! Total: " + total);
                } else {
                    System.out.println("Stok tidak cukup.");
                }
            } else {
                System.out.println("Produk tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("Gagal transaksi: " + e.getMessage());
        }
    }
}

