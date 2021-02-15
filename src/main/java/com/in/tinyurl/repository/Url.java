package com.in.tinyurl.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "url")
public class Url {
    /*@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)*/
    @Id
    private String tinyUrl;
    private String url;
    private Date creationDate;

    public Url() {
    }

    public Url(String tinyUrl, String url, Date creationDate) {
        this.tinyUrl = tinyUrl;
        this.url = url;
        this.creationDate = creationDate;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
