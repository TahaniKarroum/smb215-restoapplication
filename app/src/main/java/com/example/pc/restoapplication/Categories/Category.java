package com.example.pc.restoapplication.Categories;

/**
 * Created by softlusion on 7/27/16.
 */
public class Category {
    private String id;
    private String name;
    private String thumbnail;

    public Category() {
    }

    public Category(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public Category(String id, String name,String thumbnail) {
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
