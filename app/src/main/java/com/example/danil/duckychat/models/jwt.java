package com.example.danil.duckychat.models;

import com.google.gson.annotations.SerializedName;

public class jwt {
    @SerializedName("token")
    public String token;

    public jwt(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
