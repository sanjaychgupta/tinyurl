package com.in.tinyurl.modal;

import java.time.LocalDateTime;


public class Url {
    private String tinyUrl;
    private String url;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

    public Url() {
    }

    public Url(String tinyUrl, String url, LocalDateTime creationDate, LocalDateTime expirationDate) {
        this.tinyUrl = tinyUrl;
        this.url = url;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }


    public String getTinyUrl() {
        return tinyUrl;
    }

    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
