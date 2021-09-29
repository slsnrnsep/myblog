package com.personalproject.myblog.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MylogRepository extends JpaRepository<Mylog,Long> {
    List<Mylog> findAllByOrderByModifiedAtDesc();
}
