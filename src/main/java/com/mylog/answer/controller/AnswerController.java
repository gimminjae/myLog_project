package com.mylog.answer.controller;

import com.mylog.answer.dto.AnswerDto;
import com.mylog.answer.entity.Answer;
import com.mylog.answer.service.AnswerService;
import com.mylog.base.util.Ut;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.service.MemberService;
import com.mylog.post.dto.PostDto;
import com.mylog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
@PreAuthorize("isAuthenticated()")
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
    @GetMapping("/delete/{answerId}")
    public String deleteAnswer(Principal principal, @PathVariable long answerId) {
        MemberDto memberDto = memberService.getByUsername(principal.getName());
        AnswerDto answerDto = answerService.getById(answerId);

        if(memberDto.getId() != answerDto.getMemberId()) {
            return "redirect:/post/%d?errorMsg=%s".formatted(answerDto.getPostId(), Ut.url.encode("작성자만 삭제할 수 있습니다."));
        }
        answerService.delete(answerDto);

        return "redirect:/post/%d?msg=%s".formatted(answerDto.getPostId(), Ut.url.encode("삭제되었습니다."));
    }
}
