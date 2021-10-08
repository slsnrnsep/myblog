package com.personalproject.myblog.controller;

import com.personalproject.myblog.dto.MylogRequestDto;
import com.personalproject.myblog.models.Mylog;
import com.personalproject.myblog.repository.MylogRepository;
import com.personalproject.myblog.security.UserDetailsImpl;
import com.personalproject.myblog.service.MylogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MylogController {

    private final MylogRepository mylogRepository;
    private final MylogService mylogService;

    @PostMapping("/api/mylogs")
    public Mylog createMylog(@RequestBody MylogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails ){

        String username = userDetails.getUser().getUsername();
        Mylog mylogs = mylogService.createContents(requestDto,username);
        return mylogs;
    }

    @GetMapping("/api/mylogs")
    public List<Mylog> getMylog()
    {
        return  mylogRepository.findAllByOrderByModifiedAtDesc();
    }

    @GetMapping("/api/mylogs/{id}")
    public Mylog getContents(@PathVariable Long id) {
        Mylog contents =  mylogRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("id가 존재하지 않습니다."));
        return contents;
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
