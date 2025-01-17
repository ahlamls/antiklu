package com.antiklu.aplikasi.model;

public class OrderModel {

    private String id;
    private String name;
    private String desc;
    private String time;
    private String status;


    public OrderModel(String id, String name,String desc,String time,String status) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.time = time;
        this.status = status;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
