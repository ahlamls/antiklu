package com.antiklu.aplikasi.model;

public class AlamatModel {
    private String id;
    private String judul;
    private String alamat;


    public AlamatModel(String id, String judul,String alamat) {
        this.id = id;
        this.judul = judul;
        this.alamat = alamat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
