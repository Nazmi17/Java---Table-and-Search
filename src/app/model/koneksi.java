package app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class koneksi {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        try {
            if (koneksi == null || koneksi.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/mtk";
                String user = "root";
                String pass = "";
                koneksi = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi berhasil");
            }
        } catch (SQLException e) {
            System.out.println("Koneksi gagal, penyebab : " + e.getMessage());
        }
        return koneksi;
    }
}

