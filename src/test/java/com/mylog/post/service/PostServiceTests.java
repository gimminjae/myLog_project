package com.mylog.post.service;

import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;
import com.mylog.post.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostServiceTests {
    @Autowired
    private PostService postService;

    @Autowired
    private PlatformTransactionManager transactionManager;


    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        // 트랜잭션 시작
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        // 트랜잭션 롤백
        transactionManager.rollback(status);
    }

    @Test
    @DisplayName("글 작성")
    void test1() {

        PostDto postDto = postService.create("subject", "content");

        List<PostDto> postDtos = postService.getAll();
        assertThat(postDtos.size()).isEqualTo(1);
    }
    @Test
    @DisplayName("글 조회(by id, subject)")
    void test2() {
        PostDto postDto = postService.create("subject", "content");

        PostDto postDto1 = postService.getById(postDto.getId());
        assertThat(postDto1.getSubject()).isEqualTo("subject");

        PostDto postDto2 = postService.getBySubject(postDto.getSubject());
        assertThat(postDto2.getSubject()).isEqualTo("subject");
        assertThat(postDto2.getContent()).isEqualTo("content");
    }
    @Test
    @DisplayName("글 수정")
    void test3() {
        PostDto postDto = postService.create("subject", "content");

        postService.modify(postDto, "modify subject", "modify content");

        PostDto postDto1 = postService.getById(postDto.getId());

        assertThat(postDto1.getSubject()).isEqualTo("modify subject");
        assertThat(postDto1.getContent()).isEqualTo("modify content");
    }
    @Test
    @DisplayName("글 삭제")
    void test4() {
        PostDto postDto = postService.create("subject", "content");

        List<PostDto> postDtos = postService.getAll();
        assertThat(postDtos.size()).isEqualTo(1);

        postService.delete(postDto);

        postDtos = postService.getAll();
        assertThat(postDtos.size()).isEqualTo(0);
    }
}
