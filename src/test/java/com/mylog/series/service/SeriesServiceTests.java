package com.mylog.series.service;

import com.mylog.member.dto.MemberDto;
import com.mylog.member.service.MemberService;
import com.mylog.series.dto.SeriesDto;
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
public class SeriesServiceTests {
    @Autowired
    private SeriesService seriesService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlatformTransactionManager transactionManager;


    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        // 트랜잭션 시작
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        memberService.create("user", "1234", "min@teset.com", "user");
    }

    @AfterEach
    void afterEach() {
        // 트랜잭션 롤백
        transactionManager.rollback(status);
    }

    @Test
    @DisplayName("시리즈 생성")
    void test1() {
        MemberDto memberDto = memberService.getByUsername("user");

        seriesService.create("subject1", memberDto);

        List<SeriesDto> seriesDtoList = seriesService.getAll();
        assertThat(seriesDtoList.size()).isEqualTo(1);
    }
    @Test
    @DisplayName("시리즈 조회")
    void test2() {
        MemberDto memberDto = memberService.getByUsername("user");

        seriesService.create("subject1", memberDto);

        SeriesDto seriesDto1 = seriesService.getBySubject("subject1");
        SeriesDto seriesDto2 = seriesService.getByMember(memberDto);

        assertThat(seriesDto1.getId()).isEqualTo(seriesDto2.getId());
    }
    @Test
    @DisplayName("시리즈 수정")
    void test3() {
        MemberDto memberDto = memberService.getByUsername("user");

        seriesService.create("subject1", memberDto);

        SeriesDto seriesDto1 = seriesService.getByMember(memberDto);
        assertThat(seriesDto1.getSubject()).isEqualTo("subject1");

        seriesService.modify("modify subject", seriesDto1);

        SeriesDto seriesDto2 = seriesService.getByMember(memberDto);
        assertThat(seriesDto2.getSubject()).isEqualTo("modify subject");
    }
    @Test
    @DisplayName("시리즈 삭제")
    void test4() {
        MemberDto memberDto = memberService.getByUsername("user");

        seriesService.create("subject1", memberDto);

        List<SeriesDto> seriesDtoList = seriesService.getAll();
        assertThat(seriesDtoList.size()).isEqualTo(1);

        seriesService.delete(seriesDtoList.get(0));

        List<SeriesDto> seriesDtoList2 = seriesService.getAll();
        assertThat(seriesDtoList2.size()).isEqualTo(0);
    }

}
