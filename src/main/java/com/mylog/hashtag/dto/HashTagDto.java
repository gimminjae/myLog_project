package com.mylog.hashtag.dto;

import com.mylog.keyword.dto.KeywordDto;
import com.mylog.post.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashTagDto {
    private long id;
    private KeywordDto keywordDto;
    private PostDto postDto;
}
