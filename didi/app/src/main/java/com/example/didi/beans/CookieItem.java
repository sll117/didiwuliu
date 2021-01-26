package com.example.didi.beans;

import java.util.List;

public class CookieItem {
    private String host;
    private List<String> cookies;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getCookies() {
        return cookies;
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }
}
