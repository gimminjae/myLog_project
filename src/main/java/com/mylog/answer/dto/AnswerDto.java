package com.mylog.answer.dto;

import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
import com.mylog.post.dto.PostDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String content;
    private long memberId;
    private String memberName;
    private String memberUsername;
//    private PostDto postDto;
//    private MemberDto memberDto;
}
