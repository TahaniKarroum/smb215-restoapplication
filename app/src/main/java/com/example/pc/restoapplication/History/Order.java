package com.example.pc.restoapplication.History;

/**
 * Created by softlusion on 7/27/16.
 */
public class Order {
    private String id;
    private String name;
    private String thumbnail;
    private String price;
    private int qty;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Order() {

    }

    public Order(String id, String name, String thumbnail, String price, int qty) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
        this.qty = qty;
    }

    public Order(String id, String name, String thumbnail, String price) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Order(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public Order(String id, String name, String thumbnail) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
