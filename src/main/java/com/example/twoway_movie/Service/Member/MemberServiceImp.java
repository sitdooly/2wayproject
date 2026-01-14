package com.example.twoway_movie.Service.Member;

import com.example.twoway_movie.DTO.MemberDTO;
import com.example.twoway_movie.Entity.MemberEntity;
import com.example.twoway_movie.Repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService {
   private final MemberRepository memberRepository;
   //암호화 과정남아있음
   private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void memberinsert(MemberDTO memberDTO, HttpServletResponse response) {
        if(memberRepository.existsByUserid(memberDTO.getUserid())){

        }
        MemberEntity me=new MemberEntity();
        me.setUsernum(memberDTO.getUsernum());
        me.setUserid(memberDTO.getUserid());
        me.setUserpw(bCryptPasswordEncoder.encode(memberDTO.getUserpw()));
        me.setUsername(memberDTO.getUsername());
        me.setUsertell(memberDTO.getUsertell());
        me.setRole("ROLE_USER");
        memberRepository.save(me);
    }

   //엔티티 자료 페이징 처리 출력
    @Override
    public Page<MemberEntity> entitypage(int page) {
        return memberRepository.findAll(PageRequest.of(page,5));
    }

    @Override
    public List<MemberEntity> search(String mkey, String mvalue) {
        List<MemberEntity>list=memberRepository.searchgo(mkey,mvalue);
        return list;
    }

}
