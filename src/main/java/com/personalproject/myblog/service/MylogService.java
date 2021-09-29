package com.personalproject.myblog.service;


import com.personalproject.myblog.models.Mylog;
import com.personalproject.myblog.models.MylogRepository;
import com.personalproject.myblog.models.MylogRequestDto;
import lombok.RequiredArgsConstructor;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service
public class MylogService {

    private final MylogRepository mylogRepository;

    @Transactional
    public Long update(Long id, MylogRequestDto requestDto){
        Mylog mylog = mylogRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("ID가 존재하지 않습니다.")
        );
        mylog.update(requestDto);
        return id;

    }
}
