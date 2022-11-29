package com.mylog.post.form;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
public class PostForm {
    @NotEmpty(message = "제목을 입력하세요")
    private String subject;

    @NotEmpty(message = "내용을 입력하세요")
    private String content;

    private String tagString;
}
