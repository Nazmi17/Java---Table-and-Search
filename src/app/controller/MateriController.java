package app.controller;

import app.model.Materi;
import app.model.koneksi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MateriController implements Initializable {

    @FXML
    private Button btnKelas10;

    @FXML
    private Button btnKelas11;

    @FXML
    private Button btnKembali;

    @FXML
    private Button btnKelas12;

    @FXML
    private TableView<Materi> tblMateri;

    @FXML
    private TableColumn<Materi, String> clmMateri;

    @FXML
    private TableColumn<Materi, String> clmPenjelasan;

    @FXML
    private TableColumn<Materi, String> clmWebsite;

    @FXML
    private TableColumn<Materi, String> clmVideo;

    @FXML
    private TextField searchBar;

    private static int kelasIdDipilih;

    public static int getKelasIdDipilih() {
        return kelasIdDipilih;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (btnKelas10 != null) {
            btnKelas10.setOnAction(event -> bukaTableMateri(1));
        }

        if (btnKelas11 != null) {
            btnKelas11.setOnAction(event -> bukaTableMateri(2));
        }

        if (btnKembali != null) {
            btnKembali.setOnAction(event -> kembali());
        }

        if (btnKelas11 != null) {
            btnKelas12.setOnAction(event -> bukaTableMateri(3));
        }

        if (tblMateri != null) {
            setKolom();
            tampilkanMateri(getKelasIdDipilih());

            tblMateri.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && tblMateri.getSelectionModel().getSelectedItem() != null) {
                    Materi materi = tblMateri.getSelectionModel().getSelectedItem();
                    tampilkanDetail(materi);
                }
            });
        }
    }

    private void tampilkanDetail(Materi materi) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/detailMateri.fxml"));
            Parent root = loader.load();

            DetailMateriController controller = loader.getController();
            controller.setData(
                    materi.getNamaMateri(),
                    materi.getPenjelasan(),
                    materi.getLinkWebsite(),
                    materi.getLinkVideo()
            );

            Stage stage = new Stage();
            stage.setTitle("Detail Materi");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bukaTableMateri(int kelasId) {
        try {
            kelasIdDipilih = kelasId;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/tableMateri.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Daftar Materi");
            stage.show();


            Stage currentStage = (Stage) btnKelas10.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void kembali() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/view/beranda.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnKembali.getScene().getWindow();
            stage.close();
            stage.setScene(new Scene(root));
            stage.setTitle("Beranda");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setKolom() {
        clmMateri.setCellValueFactory(new PropertyValueFactory<>("namaMateri"));
        clmPenjelasan.setCellValueFactory(new PropertyValueFactory<>("penjelasan"));
        clmWebsite.setCellValueFactory(new PropertyValueFactory<>("linkWebsite"));
        clmVideo.setCellValueFactory(new PropertyValueFactory<>("linkVideo"));
    }

    private void tampilkanMateri(int kelasId) {
        ObservableList<Materi> dataMateri = FXCollections.observableArrayList();
        String query = "SELECT * FROM materi WHERE kelas_id = ?";

        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, kelasId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Materi m = new Materi(
                        rs.getString("nama_materi"),
                        rs.getString("penjelasan"),
                        rs.getString("link_website"),
                        rs.getString("link_video")
                );
                dataMateri.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tblMateri.setItems(dataMateri);

        FilteredList<Materi> filteredData = new FilteredList<>(dataMateri, p -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(materi -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (materi.getNamaMateri().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (materi.getPenjelasan().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (materi.getLinkWebsite().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (materi.getLinkVideo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            tblMateri.setItems(filteredData);
        });
    }
}
