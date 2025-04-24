package com.mycompany.kasirami;

public class LoggedUser {
    private static int id;
    private static String username;
    private static String role;

    // Getter dan Setter untuk id
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        LoggedUser.id = id;
    }

    // Getter dan Setter untuk username
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LoggedUser.username = username;
    }

    // Getter dan Setter untuk role
    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        LoggedUser.role = role;
    }
}
