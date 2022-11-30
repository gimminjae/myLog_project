package com.mylog.base.dto;

import com.mylog.answer.dto.AnswerDto;
import com.mylog.answer.entity.Answer;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;
import com.mylog.series.dto.SeriesDto;
import com.mylog.series.entity.Series;

public class DtoUt {
    public static PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .createDate(post.getCreateDate())
                .updateDate(post.getUpdateDate())
                .subject(post.getSubject())
                .content(post.getContent())
                .contentHtml(post.getContentHtml())
                .memberDto(DtoUt.toDto(post.getMember()))
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
                .password(member.getPassword())
                .build();
    }
    public static AnswerDto toDto(Answer answer) {
        return AnswerDto.builder()
                .id(answer.getId())
                .createDate(answer.getCreateDate())
                .updateDate(answer.getUpdateDate())
                .content(answer.getContent())
                .memberId(answer.getMember().getId())
                .memberName(answer.getMember().getNickname())
                .memberUsername(answer.getMember().getUsername())
                .postId(answer.getPost().getId())
                .build();
    }
    public static SeriesDto toDto(Series series) {
        return SeriesDto.builder()
                .id(series.getId())
                .createDate(series.getCreateDate())
                .updateDate(series.getUpdateDate())
                .subject(series.getSubject())
//                .postDtoList(series.getPostList().stream().map(i -> DtoUt.toDto(i)).toList())
                .memberDto(DtoUt.toDto(series.getMember()))
                .build();
    }

    public static Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .createDate(memberDto.getCreateDate())
                .updateDate(memberDto.getUpdateDate())
                .nickname(memberDto.getNickname())
                .username(memberDto.getUsername())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .build();
    }
    public static Post toEntity(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .createDate(postDto.getCreateDate())
                .updateDate(postDto.getUpdateDate())
                .content(postDto.getContent())
                .contentHtml(postDto.getContentHtml())
                .subject(postDto.getSubject())
                .member(DtoUt.toEntity(postDto.getMemberDto()))
                .build();
    }
}
