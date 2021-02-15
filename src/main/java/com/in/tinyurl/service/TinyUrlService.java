package com.in.tinyurl.service;

import com.in.tinyurl.repository.Url;
import com.in.tinyurl.repository.UrlRepository;
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
import java.util.Date;

@Service
public class TinyUrlService {
    @Autowired
    private UrlRepository urlRepository;
    @Value("${base-url}")
    private String baseUrl;

    public Url getTinyUrl(String url, Date currentTimestamp) {
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
            // calculate has for url.
            String hashValue = getHashSHA256(url);
            // calculate base64 from hash value.
            String base64EncodedValue = base64Encoder(hashValue);
            Url url1 = new Url();
            url1.setTinyUrl(base64EncodedValue.substring(0, 7));
            url1.setUrl(url);
            url1.setCreationDate(currentTimestamp);
            // @TODO we need to avoid collision here, because current JPA save() will update if exist then
            Url url2 = urlRepository.save(url1);
            url2.setTinyUrl(baseUrl + url2.getTinyUrl().substring(0, 7));
            return url2;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Tiny url conversion was into trouble due hashing algorithm, Please contact Admin to confirm hashing logic.");
        } catch (IllegalArgumentException e) { // db entry
            throw new RuntimeException(String.format("DB Operation had collision issue for url [%s], Please contact Admin", url));
        }
    }

    public Url getUrl(String tinyUrl){
        URL u = null; // this would check for the protocol
        try {
            u = new URL(tinyUrl);
            u.toURI();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid tiny url you provide is in correct please confirm and retry");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid tiny url you provide is in correct please confirm and retry");
        }
        tinyUrl = tinyUrl.substring(tinyUrl.lastIndexOf("/")+1);
        Url url = urlRepository.findByTinyUrl(tinyUrl);
        return url;
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
}
