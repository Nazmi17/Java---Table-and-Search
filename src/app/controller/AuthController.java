package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import app.model.koneksi;

public class AuthController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> {
            try (Connection conn = koneksi.getKoneksi()) {
                PreparedStatement stmt = conn.prepareStatement("SELECT 1 FROM admin WHERE username = ? AND password = ?");
                stmt.setString(1, usernameField.getText());
                stmt.setString(2, passwordField.getText());

                if (stmt.executeQuery().next()) {
                    JOptionPane.showMessageDialog(null, "Login berhasil!");
                    // TODO: Navigasi ke dashboard
                } else {
                    JOptionPane.showMessageDialog(null, "Username atau password salah!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Koneksi gagal: " + ex.getMessage());
            }
        });
    }
}