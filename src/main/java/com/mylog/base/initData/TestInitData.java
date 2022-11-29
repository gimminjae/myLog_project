package com.mylog.base.initData;

import com.mylog.answer.service.AnswerService;
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
            MemberService memberService,
            AnswerService answerService
    ) {
        return args -> {
            memberService.create("user1", "user1", "min356812@naver.com", "minjjai");
            memberService.create("user2", "user2", "user2@test.com", "user2");

            MemberDto memberDto = memberService.getById(1L);
            MemberDto memberDto2 = memberService.getById(2L);
            postService.create("subject1", "content1", memberDto);
            postService.create("subject2", "content2", memberDto);
            postService.create("subject3", "content3", memberDto);
            postService.create("subject4", "content4", memberDto);
            postService.create("subject5", "content5", memberDto);
            postService.create("subject6", "content6", memberDto);
            postService.create("subject7", "content7", memberDto);
            postService.create("subject8", "content8", memberDto);
            postService.create("subject9", "content9", memberDto);
            postService.create("subject10", "content10", memberDto);
            postService.create("subject11", "content11", memberDto);
            postService.create("subject12", "content12", memberDto);

            postService.create("subject11", "content1", memberDto2);
            postService.create("subject21", "content2", memberDto2);
            postService.create("subject31", "content3", memberDto2);
            postService.create("subject41", "content4", memberDto2);
            postService.create("subject51", "content5", memberDto2);
            postService.create("subject61", "content6", memberDto2);
            postService.create("subject71", "content7", memberDto2);
            postService.create("subject81", "content8", memberDto2);
            postService.create("subject91", "content9", memberDto2);
            postService.create("subject101", "content10", memberDto2);
            postService.create("subject111", "content11", memberDto2);
            postService.create("subject121", "content12", memberDto2);

            answerService.create("answer1", postService.getById(12), memberDto);
            answerService.create("answer2", postService.getById(12), memberDto);
            answerService.create("answer3", postService.getById(12), memberDto2);
        };
    }
}
