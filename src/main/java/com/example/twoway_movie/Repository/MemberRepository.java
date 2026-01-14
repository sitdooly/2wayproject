package com.example.twoway_movie.Repository;



import com.example.twoway_movie.Entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    @Modifying
    @Transactional
    //아이디중복검사
    boolean existsByUserid(String userid);

    public MemberEntity findByUserid(String userid);
    @Query(value = """
        select * from TWOWAY_MOVIE_MEMBER
        where
        (:mkey ='userid' and userid like '%' || :mvalue ||'%')
        or
        (:mkey='username' and username like '%' || :mvalue ||'%')
        """,nativeQuery = true)

    List<MemberEntity> searchgo(String mkey, String mvalue);
}


