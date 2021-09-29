package com.personalproject.myblog.controller;

import com.personalproject.myblog.models.Mylog;
import com.personalproject.myblog.models.MylogRepository;
import com.personalproject.myblog.models.MylogRequestDto;
import com.personalproject.myblog.service.MylogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MylogController {

    private final MylogRepository mylogRepository;
    private final MylogService mylogService;

    @PostMapping("/api/mylogs")
    public Mylog createMylog(@RequestBody MylogRequestDto RequestDto){
        Mylog mylog = new Mylog(RequestDto);
        return mylogRepository.save(mylog);
    }

    @GetMapping("/api/mylogs")
    public List<Mylog> getMylog()
    {
        return  mylogRepository.findAllByOrderByModifiedAtDesc();
    }

    @PutMapping("/api/mylogs/{id}")
    public Long updateMylog(@PathVariable Long id,@RequestBody MylogRequestDto requestDto){
        mylogService.update(id,requestDto);
        return id;
    }

    @DeleteMapping("/api/mylogs/{id}")
    public Long deleteMylog(@PathVariable Long id){
        mylogRepository.deleteById(id);
        return id;
    }
}
