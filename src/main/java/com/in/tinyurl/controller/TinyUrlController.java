package com.in.tinyurl.controller;

import com.in.tinyurl.config.CurrentDateTime;
import com.in.tinyurl.modal.Url;
import com.in.tinyurl.service.TinyUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@Api(value = "Tiny Url Backend Service", description = "TinyURL is a URL shortening web service, which provides short aliases for redirection of long URLs.")
public class TinyUrlController {
    @Autowired
    TinyUrlService tinyUrlService;

    @PostMapping("/tiny")
    public ResponseEntity<Url> createTinyUrl1(@ApiParam(value = "Provide URL for which you want Tinyurl in return", required = true) @RequestBody String url) {
        Url urlTiny = tinyUrlService.getTinyUrl(url, CurrentDateTime.now());
        return new ResponseEntity<>(urlTiny, HttpStatus.OK);
    }

    @GetMapping("/url")
    public ResponseEntity<Url> getUrl(@ApiParam(value = "Provide Tiny path for which you want actual URL back", required = true) @RequestParam String tinyPath) {
        Url url = tinyUrlService.getUrl(tinyPath);
        if (url != null) {
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);


    }
}
