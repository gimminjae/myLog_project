package com.mylog.hashtag.service;

import com.mylog.base.dto.DtoUt;
import com.mylog.hashtag.dto.HashTagDto;
import com.mylog.hashtag.entity.HashTag;
import com.mylog.hashtag.repository.HashTagRepository;
import com.mylog.keyword.entity.Keyword;
import com.mylog.keyword.service.KeywordService;
import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {
    private final HashTagRepository hashTagRepository;
    private final KeywordService keywordService;

    //해시태그 글에 적용
    public void applyHashTags(Post post, String tagStr) {
        List<String> keywords = Arrays.stream(tagStr.split("#"))
                .map(s -> s.trim())
                .filter(s -> s.length() > 0)
                .toList();
        keywords.forEach(keyword -> {
            saveHashTag(post, keyword);
        });
    }

    //키워드(해시태그) 저장
    private HashTag saveHashTag(Post post, String keywordContent) {
        Keyword keyword = keywordService.save(keywordContent);

        HashTag isPresentedHashTag = hashTagRepository.findByPostIdAndKeywordId(post.getId(), keyword.getId()).orElse(null);

        if(isPresentedHashTag != null) return isPresentedHashTag;

        HashTag hashTag = HashTag.builder()
                .post(post)
                .keyword(keyword)
                .build();
        hashTagRepository.save(hashTag);

        return hashTag;
    }

    public List<HashTagDto> getByPost(PostDto postDto) {
        return hashTagRepository.findByPostId(postDto.getId()).stream().map(i -> DtoUt.toDto(i)).toList();
    }
}
