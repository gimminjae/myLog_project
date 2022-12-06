package com.mylog.keyword.service;

import com.mylog.keyword.entity.Keyword;
import com.mylog.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;
}
