package com.example.trial1tmdb;

public class TrailerEntity {
    private String name;
    private String key;
    private  String type;

    public TrailerEntity(String name, String key, String type) {
        this.name = name;
        this.key = key;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }
}
