package com.fastcampus.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    // PostDto는 JSP 화면과 Spring 서버가 주고받는 객체입니다.
    private long postId;
    private String nickName;
    private String title;
    private String content;
}
