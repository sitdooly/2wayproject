package com.example.twoway_movie.Controller;

import com.example.twoway_movie.DTO.MovieDTO;
import com.example.twoway_movie.Entity.MovieEntity;
import com.example.twoway_movie.service.movie.MovieServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MovieController {

    String path = "C:\\sundo\\springboot\\twoway_movie\\src\\main\\resources\\static\\image";

    @Autowired
    MovieServie movieServie;

    // 메인
    @GetMapping("/")

    public String movie1() {
        return "main";
    }

    // 입력 폼
    @GetMapping("/mv_inputgo")
    public String movie2() {
        return "movie/mv_input";
    }

    // 저장
    @PostMapping("/mv_inputsave")
    public String movice3(MovieDTO mdto) throws IOException {

        MultipartFile mf = mdto.getMimage1();
        String fname = UUID.randomUUID() + "_" + mf.getOriginalFilename();

        mdto.setMimage(fname);
        MovieEntity mentity = mdto.toentity();
        movieServie.insertp(mentity);

        mf.transferTo(new File(path + "\\" + fname));

        return "redirect:/mv_outgo";
    }

    // 목록 + 페이징
    @GetMapping("/mv_outgo")
    public String movie4(
            Model mo,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {

        Page<MovieEntity> listPage = movieServie.entitypage(page);

        mo.addAttribute("list", listPage.getContent());
        mo.addAttribute("nowpage", listPage.getNumber() + 1);
        mo.addAttribute("totalPage", listPage.getTotalPages());

        return "movie/mv_out";
    }

    // 수정 폼
    @GetMapping("/update")
    public String movie5(@RequestParam("number") long mbunho, Model mo) {

        MovieEntity mentity = movieServie.updatee(mbunho);
        mo.addAttribute("movie", mentity);

        return "movie/mv_update";
    }

    // 수정 저장
    @PostMapping("/updateSave")
    public String movie6(
            MovieDTO mdto,
            @RequestParam("oldimage") String oldimage
    ) throws IOException {

        MovieEntity mentity = movieServie.updatee(mdto.getMbunho());

        MultipartFile mf = mdto.getMimage1();
        String fname = oldimage;

        if (mf != null && !mf.isEmpty()) {

            File oldFile = new File(path + "\\" + oldimage);
            if (oldFile.exists()) oldFile.delete();

            String ext = mf.getOriginalFilename()
                    .substring(mf.getOriginalFilename().lastIndexOf("."));
            fname = UUID.randomUUID() + ext;

            mf.transferTo(new File(path + "\\" + fname));
        }


        mentity.setMtitle(mdto.getMtitle());
        mentity.setMprice(mdto.getMprice());
        mentity.setMimage(fname);

        movieServie.updateae(mentity);

        return "redirect:/mv_outgo";
    }


    // 삭제
    @GetMapping("/delete")
    public String movie7(
            @RequestParam("number") long mbunho,
            @RequestParam("delimage") String mimage
    ) {

        movieServie.deletea(mbunho);

        File ff = new File(path + "\\" + mimage);
        if (ff.exists()) ff.delete();

        return "redirect:/mv_outgo";
    }

    // 상세보기
    @GetMapping("/detail")
    public String movie8(@RequestParam("number") long mbunho, Model mo) {

        MovieEntity mentity = movieServie.detail(mbunho);
        mo.addAttribute("dto", mentity);

        return "movie/mv_detailview";
    }
    @GetMapping("/mv_searchgo")
    public  String movie9(){
        return "movie/mv_search";
    }
    @PostMapping("/searchgo1")
    public String movie10(@RequestParam("mkey")String mkey,
                      @RequestParam("mvalue")String mvalue,Model model){

        List<MovieEntity> list = movieServie.searchgo2(mkey,mvalue);
        model.addAttribute("list",list);
        return "movie/mv_searchout";
    }

    public String main() {
        return "main";
    }


}
