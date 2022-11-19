package com.mylog.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String subject;
    private String content;
    private String contentHtml;
    private int like;
}
