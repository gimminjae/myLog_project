package com.mylog.answer.service;

import com.mylog.answer.dto.AnswerDto;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"mail", "test"})
public class AnswerServiceTests {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;

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
    @DisplayName("댓글 조회")
    void test2() {
        PostDto postDto = postService.getById(12L);
        MemberDto memberDto = memberService.getByUsername("user1");

        AnswerDto answerDto1 = answerService.getById(1L);
        assertThat(answerDto1.getContent()).isEqualTo("answer1");

        List<AnswerDto> answerDtos2 = answerService.getByPost(postDto);
        assertThat(answerDtos2.get(0).getContent()).isEqualTo("answer1");
    }
    @Test
    @DisplayName("댓글 수정")
    void test3() {
        AnswerDto answerDto = answerService.getById(1L);

        assertThat(answerDto.getContent()).isEqualTo("answer1");

        answerService.modify(answerDto, "modify content");

        AnswerDto modifiedAnswerDto = answerService.getById(1L);

        assertThat(modifiedAnswerDto.getContent()).isEqualTo("modify content");
    }
    @Test
    @DisplayName("댓글 삭제")
    void test4() {
        List<AnswerDto> answerDtos1 = answerService.getAll();
        assertThat(answerDtos1.size()).isEqualTo(9);

        AnswerDto answerDto = answerService.getById(1);
        answerService.delete(answerDto);

        List<AnswerDto> answerDtos2 = answerService.getAll();
        assertThat(answerDtos2.size()).isEqualTo(8);
    }
}
