package com.mylog.base.dto;

import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;

public class DtoUt {
    public static PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .createDate(post.getCreateDate())
                .updateDate(post.getUpdateDate())
                .subject(post.getSubject())
                .content(post.getContent())
                .contentHtml(post.getContentHtml())
//                .likes(post.getLikes())
                .build();
    }
}
