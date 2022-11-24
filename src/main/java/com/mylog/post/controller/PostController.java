package com.mylog.post.controller;

import com.mylog.base.util.Ut;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.service.MemberService;
import com.mylog.post.dto.PostDto;
//import com.mylog.post.form.PostForm;
import com.mylog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;
    //글 목록, 메인 페이지, 시작 페이지
    @GetMapping("/list")
    public String postList(Model model) {
        List<PostDto> postDtos = postService.getAll();

        model.addAttribute("postList", postDtos);
        return "post/list";
    }

    //글 작성 폼
    @GetMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String postWriteForm() {
        return "post/write";
    }

    //글 작성 처리
    @PostMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public String postWrite(Principal principal,
                            @RequestParam("subject") String subject,
                            @RequestParam("content") String content,
                            @RequestParam("tagString") String tagString) {

        MemberDto memberDto = memberService.getByUsername(principal.getName());
        PostDto postDto = postService.create(subject, content, memberDto);

        return "redirect:/post/%d?msg=%s".formatted(postDto.getId(), Ut.url.encode("글이 작성되었습니다!"));
    }

    //글 상세 페이지
    @GetMapping("/{id}")
    public String postDetail(@PathVariable("id") long id, Model model) {
        PostDto postDto = postService.getById(id);

        model.addAttribute("post", postDto);

        return "post/detail";
    }

    //글 수정
    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String postModify(@PathVariable("id") long id, Model model) {
        PostDto postDto = postService.getById(id);

        model.addAttribute("post", postDto);
        return "post/modify";
    }

    //글 수정 처리
    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String postModify(@PathVariable("id") long id,
                             @RequestParam String subject, @RequestParam String tagString, @RequestParam String content) {
        PostDto postDto = postService.getById(id);
        postService.modify(postDto, subject, content);

        return "redirect:/post/%d?msg=%s".formatted(postDto.getId(), Ut.url.encode("글이 수정되었습니다!"));
    }

    //글 삭제 처리
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String postDelete(@PathVariable("id") long id) {
        PostDto postDto = postService.getById(id);
        postService.delete(postDto);

        return "redirect:/post/list?msg=%s".formatted(Ut.url.encode("글이 삭제되었습니다!"));
    }
}
