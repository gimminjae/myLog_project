package com.mylog.base.dto;

import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
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
    public static MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .createDate(member.getCreateDate())
                .updateDate(member.getUpdateDate())
                .nickname(member.getNickname())
                .username(member.getUsername())
                .email(member.getEmail())
                .build();
    }
}
