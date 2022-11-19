package com.mylog.base.dto;

import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;

public class DtoUt {
    public static PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .createDate(post.getCreateDate())
                .updateDate(post.getUpdateDate())
                .content(post.getContent())
                .contentHtml(post.getContentHtml())
                .like(post.getLike())
                .build();
    }
}
