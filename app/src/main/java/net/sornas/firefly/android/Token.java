package net.sornas.firefly.android;

import java.io.Serializable;

public class Token implements Serializable {
    private String name;
    private String url;
    private String token;
    private boolean active;

    public Token(String name, String url, String token) {
        this.name = name;
        this.url = url;
        this.token = token;
        this.active = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
