package com.in.tinyurl.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "url")
public class UrlEntity {
    /*@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)*/
    @Id
    private String tinyUrl;
    private String url;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

    public UrlEntity() {
    }

    public UrlEntity(String tinyUrl, String url, LocalDateTime creationDate, LocalDateTime expirationDate) {
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
