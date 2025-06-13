package app.model;

public class Materi {
    private String namaMateri;
    private String penjelasan;
    private String linkWebsite;
    private String linkVideo;

    public Materi(String namaMateri, String penjelasan, String linkWebsite, String linkVideo){
        this.namaMateri = namaMateri;
        this.penjelasan = penjelasan;
        this.linkWebsite = linkWebsite;
        this.linkVideo = linkVideo;
    }

    public String getPenjelasan() {
        return penjelasan;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public String getNamaMateri() {
        return namaMateri;
    }

    public String getLinkWebsite() {
        return linkWebsite;
    }

    public void setNamaMateri(String namaMateri) {
        this.namaMateri = namaMateri;
    }

    public void setPenjelasan(String penjelasan) {
        this.penjelasan = penjelasan;
    }

    public void setLinkWebsite(String linkWebsite) {
        this.linkWebsite = linkWebsite;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }
}
