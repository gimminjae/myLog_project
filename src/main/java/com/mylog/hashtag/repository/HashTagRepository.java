package com.mylog.hashtag.repository;

import com.mylog.hashtag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    Optional<HashTag> findByPostIdAndKeywordId(Long postId, Long keywordId);

    List<HashTag> findByPostId(long id);
}
