package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.net.URI;

public class DetailMateriController {

    @FXML
    private Label lblNama;

    @FXML
    private TextArea lblPenjelasan;

    @FXML
    private Hyperlink linkWebsite;

    @FXML
    private Hyperlink linkVideo;

    public void setData(String nama, String penjelasan, String website, String video) {
        lblNama.setText(nama);
        lblPenjelasan.setText(penjelasan);
        linkWebsite.setText(website);
        linkVideo.setText(video);

        linkWebsite.setOnAction(e -> bukaDiBrowser(website));
        linkVideo.setOnAction(e -> bukaDiBrowser(video));
    }

    private void bukaDiBrowser(String url) {
        new Thread(() -> {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}

