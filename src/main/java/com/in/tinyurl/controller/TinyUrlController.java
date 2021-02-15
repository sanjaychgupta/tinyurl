package com.in.tinyurl.controller;

import com.in.tinyurl.repository.Url;
import com.in.tinyurl.repository.UrlRepository;
import com.in.tinyurl.service.TinyUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/v1")
@Api(value = "Employee Management System", description = "Operations pertaining to employee in Employee Management System")
public class TinyUrlController {
    @Autowired
    UrlRepository urlRepository;
    @Autowired
    TinyUrlService tinyUrlService;

    @PostMapping("/tiny")
    public ResponseEntity<Url> createTinyUrl1(@ApiParam(value = "Provide URL for which you want tiny url back", required = true) @RequestBody String url) {
        Url url1 = tinyUrlService.getTinyUrl(url, new Date());
        return new ResponseEntity<>(url1, HttpStatus.OK);
    }

    @GetMapping("/url")
    public ResponseEntity<Url> getUrl(@ApiParam(value = "Provide URL for which you want tiny url back", required = true) @RequestParam String tinyUrl) {
        Url url=tinyUrlService.getUrl(tinyUrl);
        if(url != null){
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
        return new ResponseEntity<>(url, HttpStatus.NOT_FOUND);


    }
}
