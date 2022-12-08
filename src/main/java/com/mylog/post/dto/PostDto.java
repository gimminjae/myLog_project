package com.mylog.post.dto;

import com.mylog.hashtag.dto.HashTagDto;
import com.mylog.hashtag.entity.HashTag;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private int likes;
    private MemberDto memberDto;
    private List<HashTag> hashTags;

    public void setHashTagList(List<HashTag> hashTags) {
        this.hashTags = hashTags;
    }
    public String getHashTagStr() {
        StringBuilder sb = new StringBuilder();

        for(HashTag hashTag : this.getHashTags()) sb.append("#" + hashTag.getKeyword().getContent() + " ");

        return sb.toString();
    }
}
