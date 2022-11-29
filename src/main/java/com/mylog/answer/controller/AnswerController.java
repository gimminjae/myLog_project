package com.mylog.answer.controller;

import com.mylog.answer.dto.AnswerDto;
import com.mylog.answer.entity.Answer;
import com.mylog.answer.service.AnswerService;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.service.MemberService;
import com.mylog.post.dto.PostDto;
import com.mylog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final MemberService memberService;
    private final PostService postService;

    @PostMapping("/create")
    public String writeAnswer(Principal principal,
                                 @RequestParam("answer") String content,
                                 @RequestParam("postId") long postId) {
        MemberDto memberDto = memberService.getByUsername(principal.getName());
        PostDto postDto = postService.getById(postId);

        AnswerDto answerDto = answerService.create(content, postDto, memberDto);

        return "redirect:/post/%d#%s".formatted(postId, "answer_" + answerDto.getId());
    }
}
