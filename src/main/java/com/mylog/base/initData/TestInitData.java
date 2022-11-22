package com.mylog.base.initData;

import com.mylog.member.dto.MemberDto;
import com.mylog.member.service.MemberService;
import com.mylog.post.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
public class TestInitData {
    @Bean
    CommandLineRunner init(
            PostService postService,
            MemberService memberService
    ) {
        return args -> {
            memberService.create("user1", "user1", "min356812@naver.com", "minjjai");

            MemberDto memberDto = memberService.getById(1L);
            postService.create("subject1", "content1", memberDto);
            postService.create("subject2", "content2", memberDto);
            postService.create("subject3", "content3", memberDto);
        };
    }
}
