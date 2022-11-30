package com.mylog.series.dto;

import com.mylog.member.dto.MemberDto;
import com.mylog.post.dto.PostDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class SeriesDto {
    private String id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String subject;
    private List<PostDto> postDtoList;
    private MemberDto memberDto;
}
