package com.personalproject.myblog.service;


import com.personalproject.myblog.dto.MylogRequestDto;
import com.personalproject.myblog.models.Mylog;
import com.personalproject.myblog.repository.MylogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service
public class MylogService {

    private final MylogRepository mylogRepository;

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Mylog createContents(MylogRequestDto requestDto, String username) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Mylog contents = new Mylog(requestDto, username);
        mylogRepository.save(contents);
        return contents;
    }


    @Transactional
    public Long update(Long id, MylogRequestDto requestDto){
        Mylog mylog = mylogRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("ID가 존재하지 않습니다.")
        );
        mylog.update(requestDto);
        return mylog.getId();

    }
}
