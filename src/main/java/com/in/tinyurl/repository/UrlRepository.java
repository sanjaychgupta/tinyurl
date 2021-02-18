package com.in.tinyurl.repository;

import com.in.tinyurl.repository.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity,Integer> {
    UrlEntity findByTinyUrl(String tinyUrl);
}

