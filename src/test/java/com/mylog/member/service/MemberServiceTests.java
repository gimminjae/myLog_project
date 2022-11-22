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

import java.util.List;

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
    @Test
    @DisplayName("회원 조회(by email, username, id)")
    void test2() {
        String username = "user1";
        String password = "user1";
        String email = "test12@test.com";
        String nickname = "user1";

        memberService.create(username, password, email, nickname);

        MemberDto memberDto1 = memberService.getById(1);
        MemberDto memberDto2 = memberService.getByUsername(username);
        MemberDto memberDto3 = memberService.getByEmail(email);

        assertThat(memberDto1.getNickname()).isEqualTo(nickname);
        assertThat(memberDto2.getNickname()).isEqualTo(nickname);
        assertThat(memberDto3.getNickname()).isEqualTo(nickname);
    }
    @Test
    @DisplayName("회원 정보 수정(email, nickname 등)")
    void test3() {
        String username = "user1";
        String password = "user1";
        String email = "test12@test.com";
        String nickname = "user1";

        memberService.create(username, password, email, nickname);

        MemberDto memberDto = memberService.getById(1);
        memberService.modify(memberDto, "modifyTest@test.com", "modifyUser");

        memberDto = memberService.getById(1);
        assertThat(memberDto.getNickname()).isEqualTo("modifyUser");
        assertThat(memberDto.getEmail()).isEqualTo("modifyTest@test.com");
    }
    @Test
    @DisplayName("회원 삭제")
    void test4() {
        String username = "user1";
        String password = "user1";
        String email = "test12@test.com";
        String nickname = "user1";

        MemberDto memberDto = memberService.create(username, password, email, nickname);

        List<MemberDto> memberDtoList = memberService.getAll();
        assertThat(memberDtoList.size()).isEqualTo(1);

        memberService.delete(memberDto);

        memberDtoList = memberService.getAll();
        assertThat(memberDtoList.size()).isEqualTo(0);
    }


}
