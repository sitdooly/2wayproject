package com.example.twoway_movie.Service.Member;

import com.example.twoway_movie.DTO.MemberDTO;
import com.example.twoway_movie.Entity.MemberEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MemberService {
    void memberinsert(MemberDTO memberDTO, HttpServletResponse response);

    Page<MemberEntity> entitypage(int page);


    List<MemberEntity> search(String mkey, String mvalue);
}
