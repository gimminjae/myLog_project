package com.mylog.answer.dto;

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
}
