package com.example.twoway_movie.Repository;

import com.example.twoway_movie.Entity.MovieEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE TWOWAY_MOVIE " +
                    "SET mreadcount = mreadcount + 1 " +
                    "WHERE mbunho = :mbunho",
            nativeQuery = true
    )
    void readcount(@Param("mbunho") long mbunho);


    MovieEntity findByMbunho(long mbunho);

    List<MovieEntity> findByMdate(LocalDate mdate);

    List<MovieEntity> findByMtitleContaining(String mtitle);
    List<MovieEntity> findByMprice(int mprice);

}



