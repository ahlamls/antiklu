package com.antiklu.aplikasi.model;

public class OrderMenuModel {
    private String id;
    private String name;
    private String info;
    private int count;
    private long price;


    public OrderMenuModel(String id, String name,String info,int count,long price) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.count = count;
        this.price = price;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}