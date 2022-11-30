package com.mylog.series.dto;

import com.mylog.member.dto.MemberDto;
import com.mylog.post.dto.PostDto;
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
public class SeriesDto {
    private String id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String subject;
    private List<PostDto> postDtoList;
    private MemberDto memberDto;
}
