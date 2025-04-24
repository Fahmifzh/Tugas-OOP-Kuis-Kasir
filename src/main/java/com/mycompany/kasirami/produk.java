
package com.mycompany.kasirami;
import java.sql.*;
import java.util.Scanner;
public class produk {
    static Scanner input = new Scanner(System.in);

    // Method untuk menambah produk
    public static void addProduct() {
        if (!LoggedUser.role.equals("admin")) {
            System.out.println("Akses ditolak. Hanya admin yang bisa menambah produk.");
            return;
        }

        System.out.println("--- Tambah Produk ---");
        System.out.print("Nama Produk: ");
        String nama = input.nextLine();

        // Menangani input harga dengan desimal
        double harga = 0;
        while (true) {
            try {
                System.out.print("Harga: ");
                harga = Double.parseDouble(input.nextLine()); // Parsing harga ke tipe desimal (double)
                if (harga < 0) {
                    System.out.println("Harga tidak bisa negatif. Coba lagi.");
                } else {
                    break; // Keluar dari loop jika input valid
                }
            } catch (NumberFormatException e) {
                System.out.println("Harga tidak valid, masukkan angka desimal yang benar!");
            }
        }

        // Menangani input stok dengan integer
        int stok = 0;
        while (true) {
            try {
                System.out.print("Stok: ");
                stok = Integer.parseInt(input.nextLine()); // Parsing stok ke tipe integer
                if (stok < 0) {
                    System.out.println("Stok tidak bisa negatif. Coba lagi.");
                } else {
                    break; // Keluar dari loop jika input valid
                }
            } catch (NumberFormatException e) {
                System.out.println("Stok tidak valid, masukkan angka integer yang benar!");
            }
        }

        // Menambahkan produk ke dalam database
        try {
            Connection conn = Database.getConnection();
            String sql = "INSERT INTO produk (nama_produk, harga, stok) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setDouble(2, harga); // Menyimpan harga sebagai tipe desimal
            ps.setInt(3, stok); // Menyimpan stok sebagai integer
            ps.executeUpdate();
            System.out.println("Produk berhasil ditambahkan.");

           
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat menambah produk: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Kesalahan tidak terduga: " + e.getMessage());
        }
    }

    // Method untuk menampilkan produk
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

    // Method untuk menghapus produk
    public static void deleteProduct() {
        if (!LoggedUser.role.equals("admin")) {
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
