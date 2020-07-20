package com.antiklu.aplikasi.model;

public class MenuModel {
    private String id;
    private String name;
    private String desc;
    private String gambar;
    private long price;
    private long promoprice;


    public MenuModel(String id, String name, String desc , String gambar, long price,long promoprice) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.gambar = gambar;
        this.price = price;
        this.promoprice = promoprice;
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

    public long getPromoprice() {
        return promoprice;
    }

    public void setPromoprice(long promoprice) {
        this.promoprice = promoprice;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}