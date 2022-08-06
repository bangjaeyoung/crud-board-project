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

@Controller
public class BoardController {
    // TODO BoardController는 JSP를 이용한 화면 로직과 연결되어 있습니다.
    // 다른 부분은 바꾸지 말고 TODO 부분만 작성해주시기를 권장합니다 :)
    // TODO please write code on only TODO part in BoardController

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
            board.setCnt((int) boardRepository.count() + 1);
        }

        Board newBoard = boardRepository.save(board);

        return "redirect:/"; // 추가 후 홈 화면으로
    }

    @RequestMapping("/")
    public String ReadAllPost(Model model){
        // List<PostDto> postList = /* TODO 게시물 전체를 받아오는 로직 */

        // ********************************************
        // 게시물을 받아오는 로직을 작성한 뒤엔 아래 2줄은 삭제 해주셔도 됩니다.
        List<PostDto> postList = new ArrayList<>();
        postList.add(new PostDto(10, "hey", "새 글", "예제"));
        // *****************************************

        model.addAttribute("postList", postList);
        return "index";
    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    public String updatePost(@ModelAttribute("command") PostDto postDto){
        System.out.println("update " + postDto);
        /* TODO 게시물 수정 로직 */
        return "redirect:/";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    public String deletePost(@PathVariable int id){
        System.out.println("삭제 " + id);
        /* TODO 게시물 삭제 로직 */
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


