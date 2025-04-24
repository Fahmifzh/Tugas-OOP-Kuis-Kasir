/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.kasirami;
import java.util.Scanner;
public class KasirAmi {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== Aplikasi Kasir ====");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Pilih: ");
            int pilihan = input.nextInt();
            input.nextLine();

            if (pilihan == 1) {
              if (login.login()) {
    boolean logout = false; // tambahkan flag logout
    while (!logout) {
        if (LoggedUser.role.equals("admin")) {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Tambah User");
            System.out.println("2. Lihat User");
            System.out.println("3. Hapus User");
            System.out.println("4. Tambah Produk");
            System.out.println("5. Lihat Produk");
            System.out.println("6. Hapus Produk");
            System.out.println("7. Logout");
            System.out.print("Pilih: ");
            int adminChoice = input.nextInt();
            input.nextLine();

            switch (adminChoice) {
                case 1: User.addUser(); break;
                case 2: User.showUsers(); break;
                case 3: User.deleteUser(); break;
                case 4: produk.addProduct(); break;
                case 5: produk.showProducts(); break;
                case 6: produk.deleteProduct(); break;
                case 7:
                    System.out.println("Logout...");
                    logout = true; // keluar dari loop
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
            }

        } else if (LoggedUser.role.equals("kasir")) {
            System.out.println("\n--- Menu Kasir ---");
            System.out.println("1. Transaksi");
            System.out.println("2. Logout");
            System.out.print("Pilih: ");
            int kasirChoice = input.nextInt();
            input.nextLine();

            switch (kasirChoice) {
                case 1: transaksi.doTransaction(); break;
                case 2:
                    System.out.println("Logout...");
                    logout = true; // keluar dari loop
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
            }
        }
    }
}

            } else if (pilihan == 2) {
                System.out.println("Keluar dari aplikasi...");
                break;
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }
}