package com.example.pc.restoapplication.Products;

/**
 * Created by softlusion on 7/27/16.
 */
public class Product {
    private String id;
    private String name;
    private String thumbnail;

    public Product() {
    }

    public Product(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public Product(String id, String name,String thumbnail) {
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
