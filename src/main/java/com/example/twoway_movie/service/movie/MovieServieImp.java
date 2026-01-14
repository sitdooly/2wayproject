package com.example.twoway_movie.service.movie;

import com.example.twoway_movie.Entity.MovieEntity;
import com.example.twoway_movie.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServieImp implements  MovieServie{
    @Autowired
    MovieRepository moviceRepository;

    @Override
    public void insertp(MovieEntity mentity) {
        moviceRepository.save(mentity);
    }

    @Override
    public List<MovieEntity> allout() {
        return moviceRepository.findAll();
    }

    @Override
    public MovieEntity updatee(long mbunho) {
        return moviceRepository.findById(mbunho).orElse(null);
    }

    @Override
    public void updateae(MovieEntity mentity) {
        moviceRepository.save(mentity);
    }

    @Override
    public void deletea(long mbunho) {
        moviceRepository.deleteById(mbunho);
    }

    @Override
    public MovieEntity detail(long mbunho) {
        moviceRepository.readcount(mbunho);
        return moviceRepository.findById(mbunho).orElse(null);
    }


    @Override
    public void readcount(long mbunho) {
        moviceRepository.readcount(mbunho);
    }

    @Override
    public Page<MovieEntity> entitypage(int page) {
        return moviceRepository.findAll(PageRequest.of(page, 5));
    }

    @Override
    public List<MovieEntity> searchgo2(String mkey, String mvalue) {

        if ("mtitle".equals(mkey)) {
            return moviceRepository.findByMtitleContaining(mvalue);

        } else if ("mprice".equals(mkey)) {
            int price = Integer.parseInt(mvalue);
            return moviceRepository.findByMprice(price);
        }

        return List.of();
    }

}
