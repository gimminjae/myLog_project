package com.mylog.keyword.service;

import com.mylog.keyword.entity.Keyword;
import com.mylog.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public Keyword save(String keywordContent) {
        Keyword opKeyword = keywordRepository.findByContent(keywordContent).orElse(null);

        if(opKeyword != null) return opKeyword;

        Keyword keyword = Keyword.builder()
                .content(keywordContent)
                .build();
        keywordRepository.save(keyword);

        return keyword;
    }
}
