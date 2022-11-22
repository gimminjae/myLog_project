package com.mylog.member.service;

import com.mylog.member.dto.MemberDto;
import com.mylog.post.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTests {
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
    @DisplayName("회원 생성")
    void test1() {
        String username = "user1";
        String password = "user1";
        String email = "test12@test.com";
        String nickname = "user1";

        MemberDto memberDto = memberService.create(username, password, email, nickname);

        List<MemberDto> memberDtoList = memberService.getAll();
        assertThat(memberDtoList.size()).isEqualTo(1);
    }
}
