package com.mylog.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String username;
    private String email;
    private String nickname;
    private String password;
    private String profileImg;
}
