package com.example.trial1tmdb;

public class Cast {
    private int castId;
    private String castName;
    private String characterRole;
    private String castImageUrl;

    public Cast(int castId, String castName, String characterRole, String castImageUrl) {
        this.castId = castId;
        this.castName = castName;
        this.characterRole = characterRole;
        this.castImageUrl = castImageUrl;
    }

    public int getCastId() {
        return castId;
    }

    public String getCastName() {
        return castName;
    }

    public String getCharacterRole() {
        return characterRole;
    }

    public String getCastImageUrl() {
        return castImageUrl;
    }
}
