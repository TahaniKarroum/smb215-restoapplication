package com.example.pc.restoapplication.FeedBacks;

/**
 * Created by softlusion on 7/27/16.
 */
public class FeedBack {
    private String id;
    private String name;

    public FeedBack() {
    }

    public FeedBack(String id, String name) {
        this.name = name;
        this.id = id;
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

}
