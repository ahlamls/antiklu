package com.antiklu.aplikasi.model;

public class SliderModel {

    private String id;
    private String gambar;


    public SliderModel(String id, String gambar) {
        this.id = id;
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
