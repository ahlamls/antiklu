package com.antiklu.aplikasi.model;

public class RestoListModel {
    private String id;
    private String name;
    private String desc;
    private String range;
    private String gambar;


    public RestoListModel(String id, String name,String desc,String range , String gambar) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.range = range;
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
