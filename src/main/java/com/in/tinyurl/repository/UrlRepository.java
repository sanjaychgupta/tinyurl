package com.in.tinyurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url,Integer> {
    Url findByTinyUrl(String tinyUrl);
}

