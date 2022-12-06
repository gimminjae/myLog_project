package com.mylog.member.service;

import com.mylog.member.dto.MemberDto;
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

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"mail", "test"})
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
    @DisplayName("회원 조회(by email, username, id)")
    void test2() {

        MemberDto memberDto2 = memberService.getByUsername("user1");
        MemberDto memberDto3 = memberService.getByEmail("min356812@naver.com");

        assertThat(memberDto2.getNickname()).isEqualTo("minjjai");
        assertThat(memberDto3.getNickname()).isEqualTo("minjjai");
    }
    @Test
    @DisplayName("회원 정보 수정(email, nickname 등)")
    void test3() {

        MemberDto memberDto = memberService.getByUsername("user1");
        memberService.modify(memberDto, "modifyTest@test.com", "modifyUser");

        memberDto = memberService.getByUsername("user1");
        assertThat(memberDto.getNickname()).isEqualTo("modifyUser");
        assertThat(memberDto.getEmail()).isEqualTo("modifyTest@test.com");
    }
    @Test
    @DisplayName("회원 삭제")
    void test4() {
        List<MemberDto> memberDtoList = memberService.getAll();
        assertThat(memberDtoList.size()).isEqualTo(2);

        MemberDto memberDto = memberService.getByUsername("user1");
        memberService.delete(memberDto);

        List<MemberDto> memberDtoList2 = memberService.getAll();
        assertThat(memberDtoList2.size()).isEqualTo(1);
    }
}
