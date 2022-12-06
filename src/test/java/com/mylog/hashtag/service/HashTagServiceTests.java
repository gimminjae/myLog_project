package com.mylog.hashtag.service;

import com.mylog.base.dto.DtoUt;
import com.mylog.hashtag.dto.HashTagDto;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.service.MemberService;
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
public class HashTagServiceTests {
    @Autowired
    private PostService postService;

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private MemberService memberService;
    @Autowired
    private PlatformTransactionManager transactionManager;


    TransactionStatus status;

//    @BeforeEach
//    void beforeEach() {
//        // 트랜잭션 시작
//        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//    }
//
//    @AfterEach
//    void afterEach() {
//        // 트랜잭션 롤백
//        transactionManager.rollback(status);
//    }

    @Test
    @DisplayName("글 작성, 해시태그 2개 저장")
    void test1() {
        MemberDto memberDto = memberService.getByUsername("user1");
        PostDto postDto = postService.getById(12L);

        List<HashTagDto> hashTagDtos = hashTagService.getByPost(postDto);
        assertThat(hashTagDtos.size()).isEqualTo(2);

        List<PostDto> postDtos = postService.getAll();
        assertThat(postDtos.size()).isEqualTo(24);
    }
    @Test
    @DisplayName("글의 해시태그 수정 시, 기존 해시태그의 개수 혹은 내용이 바뀐다.")
    void test2() {
        MemberDto memberDto = memberService.getByUsername("user1");
        PostDto postDto = postService.getById(12L);

        String tags = "#java";

        List<HashTagDto> hashTagDtos = hashTagService.getByPost(postDto);
        assertThat(hashTagDtos.size()).isEqualTo(2);

        hashTagService.applyHashTags(DtoUt.toEntity(postDto), tags);

        List<HashTagDto> hashTagDto2s = hashTagService.getByPost(postDto);
        assertThat(hashTagDto2s.size()).isEqualTo(1);
    }
}

