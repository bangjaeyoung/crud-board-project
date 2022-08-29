package com.fastcampus.board.controller;

import com.fastcampus.board.repository.BoardRepository;
import com.fastcampus.board.dto.PostDto;
import com.fastcampus.board.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {
    // TODO BoardController는 JSP를 이용한 화면 로직과 연결되어 있습니다.

    @Autowired
    BoardRepository boardRepository;

    @RequestMapping(value="/save",method = RequestMethod.POST)
    public String createPost(@ModelAttribute("command") PostDto postDto){
        System.out.println("save " + postDto);

        Board board = new Board();

        board.setSeq((int) postDto.getPostId());

        if(postDto.getNickName().equals("") || postDto.getTitle().equals("") || postDto.getContent().equals("")) {
            System.out.println("ERROR! Please enter the information correctly!");

            return "redirect:/";
        }
        else {
            board.setWriter(postDto.getNickName());
            board.setTitle(postDto.getTitle());
            board.setContent(postDto.getContent());
            board.setRegDate(LocalDateTime.now());
            board.setCnt(0);
        }

        Board newBoard = boardRepository.save(board);

        return "redirect:/"; // 추가 후 홈 화면으로
    }

    @RequestMapping("/")
    public String ReadAllPost(Model model){
        /* TODO 게시물 전체를 받아오는 로직 */
        // 조회 코드 구현 실패
        List<PostDto> postList = new ArrayList<>();

        PostDto postDto = new PostDto();

        if(postList.isEmpty()) {

            postList.add(new PostDto());
        }
        else {

            postList.add(postDto);
        }

        // 수정, 삭제 코드가 잘 구현되는지 확인하기 위해 만들어둔 코드
        postList.add(new PostDto(1, "Steve", "example", "ex"));
        postList.add(new PostDto(2, "Harry", "example2", "ex2"));
        postList.add(new PostDto(3, "Tom", "example3", "ex3"));

        model.addAttribute("postList", postList);

        return "index";
    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    public String updatePost(@ModelAttribute("command") PostDto postDto){
        System.out.println("update " + postDto);

        Optional<Board> board = boardRepository.findById((int) postDto.getPostId());

        board.ifPresent(selectPost -> {
            selectPost.setSeq((int) postDto.getPostId());
            selectPost.setWriter(postDto.getNickName());
            selectPost.setTitle(postDto.getTitle());
            selectPost.setContent(postDto.getContent());
            selectPost.setRegDate(LocalDateTime.now());
            selectPost.setCnt(board.get().getCnt() + 1);

            boardRepository.save(selectPost);
        });

        return "redirect:/";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    public String deletePost(@PathVariable int id){
        System.out.println("삭제 " + id);

        Optional<Board> board = boardRepository.findById(id);

        board.ifPresent(selectPost -> {
            boardRepository.delete(selectPost);
        });

        return "redirect:/";
    }

    // ***************************************************************************************************
    // ******************************************* Do not edit *******************************************
    // 아래 부분은 수정 안하셔도 됩니다. (글 생성, 글 업데이트 창으로 연결하는 부분)

    @RequestMapping(value="/createView")
    public String ViewCreate(Model model){
        model.addAttribute("command", new PostDto());
        return "create";
    }

    @RequestMapping(value="/updateView/{id}")
    public String ViewUpdate(@PathVariable int id, Model model){
        PostDto postDto = new PostDto();
        postDto.setPostId(id);
        model.addAttribute("command",postDto);
        return "update";
    }
}


