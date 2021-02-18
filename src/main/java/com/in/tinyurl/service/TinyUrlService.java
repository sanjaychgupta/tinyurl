package com.in.tinyurl.service;

import com.in.tinyurl.config.CurrentDateTime;
import com.in.tinyurl.modal.Url;
import com.in.tinyurl.repository.UrlRepository;
import com.in.tinyurl.repository.entity.UrlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class TinyUrlService {
    @Autowired
    private UrlRepository urlRepository;

    // Base url will of web server from which you want provide redirection.
    @Value("${base-url}")
    private String baseUrl;

    public Url getTinyUrl(String url, CurrentDateTime currentDateTime) {
        URL u = null; // this would check for the protocol
        try {
            u = new URL(url);
            u.toURI();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid url you provide is in correct please confirm and retry");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid url you provide is in correct please confirm and retry");
        }

        try {
            String tinyPath = getTinayPath(url, false);
            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setTinyUrl(tinyPath);
            urlEntity.setUrl(url);
            urlEntity.setCreationDate(currentDateTime.value());
            urlEntity.setExpirationDate(currentDateTime.value().plusYears(1));
            urlEntity = urlRepository.save(urlEntity);
            urlEntity.setTinyUrl(baseUrl + urlEntity.getTinyUrl());
            return urlEntityMapper(urlEntity);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Tiny url conversion was into trouble due hashing algorithm, Please contact Admin to confirm hashing logic.");
        } catch (IllegalArgumentException e) { // db entry
            throw new RuntimeException(String.format("DB Operation had collision issue for url [%s], Please contact Admin", url));
        }
    }

    public Url getUrl(String tinyPath) {
        UrlEntity urlEntity = urlRepository.findByTinyUrl(tinyPath);
        urlEntity.setTinyUrl(baseUrl + urlEntity.getTinyUrl());
        return urlEntityMapper(urlEntity);
    }

    private String getHashSHA256(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] sha256 = md.digest(str.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, sha256);
        return number.toString(16);
    }

    private String getHashMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(str.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        return number.toString(16);
    }

    private String base64Encoder(String text) {
        String encodedString = Base64.getEncoder().encodeToString(text.getBytes());
        return encodedString;

    }

    private String getTinayPath(String url, Boolean collisionDetected) throws NoSuchAlgorithmException {
        // we will create tiny path by having hashing and followed with base encoding and getting first seven 7 character of output as final tiny path
        String hashValue = getHashSHA256(url);
        String base64EncodedValue = base64Encoder(hashValue);
        String tinyPath = base64EncodedValue.substring(0, 7);
        // Collision Avoidance
        if (urlRepository.findByTinyUrl(tinyPath) != null) {
            tinyPath = getTinayPath(url + getCollisionAvoidingPadding(), true);
        }
        return tinyPath;
    }

    private String getCollisionAvoidingPadding() {
        // @TODO Here I have choose current time but if we have multiple instances we need provide adding  via Apache Zookeeper.
        //Apache ZooKeeper is an open-source server for highly reliable distributed coordination of cloud applications.
        return CurrentDateTime.now().value().format(CurrentDateTime.formatter);
    }

    private Url urlEntityMapper(UrlEntity entity) {
        Url url = new Url();
        url.setUrl(entity.getUrl());
        url.setTinyUrl(entity.getTinyUrl());
        url.setCreationDate(entity.getCreationDate());
        url.setExpirationDate(entity.getExpirationDate());

        return url;
    }
}
