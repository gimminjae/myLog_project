package com.mylog.post.repository;

import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findBySubject(String subject);

    List<Post> findByMemberId(long id);
}
