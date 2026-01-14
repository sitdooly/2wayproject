package com.example.twoway_movie.Controller;

import com.example.twoway_movie.DTO.BoardDTO;
import com.example.twoway_movie.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 🔥 top 메뉴 호환용
    @GetMapping("/board_outgo")
    public String boardOutgoRedirect() {
        return "redirect:/board_all";
    }

    @GetMapping("/board_inputgo")
    public String boardInput(Model model) {
        model.addAttribute("dto", new BoardDTO());
        return "board/board_input";
    }

    @PostMapping("/board_inputgo")
    public String boardInsert(BoardDTO dto) {
        boardService.insert(dto);
        return "redirect:/board_all";
    }

    /** 수정 화면 이동 */
    @GetMapping("/board_updatego")
    public String boardUpdateGo(@RequestParam Long bbunho, Model model) {
        model.addAttribute("dto", boardService.selectOne(bbunho));
        return "board/board_update";
    }

    /** 수정 처리 */
    @PostMapping("/board_update")
    public String boardUpdate(BoardDTO dto) {
        boardService.update(dto);
        return "redirect:/board_all";
    }

    /* ===============================
       문의 삭제
       =============================== */

    /** 삭제 */
    @GetMapping("/board_delete")
    public String boardDelete(@RequestParam Long bbunho) {
        boardService.delete(bbunho);
        return "redirect:/board_all";
    }

    /* ===============================
       답글 처리
       =============================== */

    /** 답글 화면 이동 */
    @GetMapping("/board_replygo")
    public String replyGo(@RequestParam Long bbunho, Model model) {
        model.addAttribute("dto", boardService.selectOne(bbunho));
        return "board/board_reply";
    }

    /** 답글 등록 */
    @PostMapping("/board_reply")
    public String reply(@RequestParam Long bbunho,
                        @RequestParam String breply) {
        boardService.updateReply(bbunho, breply);
        return "redirect:/board_all";
    }

    /* ===============================
       상세보기
       =============================== */

    /** 문의 상세보기 */
    @GetMapping("/board_detail")
    public String boardDetail(@RequestParam Long bbunho, Model model) {
        model.addAttribute("dto", boardService.selectOne(bbunho));
        return "board/board_detail";
    }

    /* ===============================
       게시판 목록 (페이징)
       =============================== */

    /** 🎬 영화 문의 게시판 */
    @GetMapping("/board_movie")
    public String boardMovie(
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        int size = 10;
        int total = boardService.countByCategory("MOVIE");
        int totalPage = (int) Math.ceil((double) total / size);

        model.addAttribute("list",
                boardService.pagingByCategory("MOVIE", page, size));
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("category", "MOVIE");
        model.addAttribute("title", "🎬 영화 문의 게시판");

        return "board/board_inout";
    }

    /** 🏠 홈페이지 문의 게시판 */
    @GetMapping("/board_home")
    public String boardHome(
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        int size = 10;
        int total = boardService.countByCategory("HOME");
        int totalPage = (int) Math.ceil((double) total / size);

        model.addAttribute("list",
                boardService.pagingByCategory("HOME", page, size));
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("category", "HOME");
        model.addAttribute("title", "🏠 홈페이지 문의 게시판");

        return "board/board_inout";
    }

    /** 📋 전체 문의 목록 (기본) */
    @GetMapping("/board_all")
    public String boardAll(
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        int size = 10;
        int total = boardService.countAll();
        int totalPage = (int) Math.ceil((double) total / size);

        model.addAttribute("list",
                boardService.pagingAll(page, size));
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("category", "ALL");
        model.addAttribute("title", "📋 전체 문의 목록");

        return "board/board_inout";
    }
}
