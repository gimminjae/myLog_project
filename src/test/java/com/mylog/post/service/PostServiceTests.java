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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"mail", "test"})
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
    @DisplayName("글 조회(by id, subject)")
    void test2() {
        PostDto postDto1 = postService.getById(1L);
        assertThat(postDto1.getSubject()).isEqualTo("subject1");

        PostDto postDto2 = postService.getBySubject("subject2");
        assertThat(postDto2.getSubject()).isEqualTo("subject2");
        assertThat(postDto2.getContent()).isEqualTo("content2");

        List<PostDto> postDtos = postService.getAll();
        assertThat(postDtos.size()).isEqualTo(24);
    }
    @Test
    @DisplayName("글 수정")
    void test3() {
        PostDto postDto = postService.getById(1L);

        postService.modify(postDto, "modify subject", "modify content");

        PostDto postDto1 = postService.getById(postDto.getId());

        assertThat(postDto1.getSubject()).isEqualTo("modify subject");
        assertThat(postDto1.getContent()).isEqualTo("modify content");
    }
    @Test
    @DisplayName("글 삭제")
    void test4() {
        List<PostDto> postDtos = postService.getAll();
        assertThat(postDtos.size()).isEqualTo(24);

        postService.delete(postDtos.get(0));

        postDtos = postService.getAll();
        assertThat(postDtos.size()).isEqualTo(23);
    }
}
