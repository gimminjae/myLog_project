package com.mylog.base.dto;

import com.mylog.answer.dto.AnswerDto;
import com.mylog.answer.entity.Answer;
import com.mylog.hashtag.dto.HashTagDto;
import com.mylog.hashtag.entity.HashTag;
import com.mylog.keyword.dto.KeywordDto;
import com.mylog.keyword.entity.Keyword;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DtoUt {
    public static HashTagDto toDto(HashTag hashTag) {
        return HashTagDto.builder()
                .id(hashTag.getId())
                .keywordDto(toDto(hashTag.getKeyword()))
                .build();
    }

    public static KeywordDto toDto(Keyword keyword) {
        return KeywordDto.builder()
                .id(keyword.getId())
                .content(keyword.getContent())
                .build();
    }

    public static PostDto toDto(Post post) {
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .createDate(post.getCreateDate())
                .updateDate(post.getUpdateDate())
                .subject(post.getSubject())
                .content(post.getContent())
                .contentHtml(post.getContentHtml())
                .memberDto(DtoUt.toDto(post.getMember()))
                .hashTags(null)
//                .likes(post.getLikes())
                .build();
        if(post.getHashTagList() == null) return postDto;

        postDto.setHashTagList(post.getHashTagList());

        return postDto;
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
                .profileImg(member.getProfileImg())
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

    public static Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .createDate(memberDto.getCreateDate())
                .updateDate(memberDto.getUpdateDate())
                .nickname(memberDto.getNickname())
                .username(memberDto.getUsername())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .profileImg(memberDto.getProfileImg())
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
