/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Quis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author KING
 */
public class Koneksi {
    
    private static final String URL = "jdbc:mysql://localhost:3306/books";
    private static final String USER = "root"; // atau username database kamu
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi berhasil!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Koneksi gagal!");
        }
        return connection;
    }

    public static void main(String[] args) {
        // Uji koneksi
        getConnection();
    }
}
