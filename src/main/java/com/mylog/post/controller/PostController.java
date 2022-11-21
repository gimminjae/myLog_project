package com.mylog.post.controller;

import com.mylog.post.dto.PostDto;
import com.mylog.post.form.PostForm;
import com.mylog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String postList(Model model) {
        List<PostDto> postDtos = postService.getAll();

        model.addAttribute("postList", postDtos);
        return "post/list";
    }
    @GetMapping("/write")
    public String postWrite(PostForm postForm) {

        return "post/write";
    }
    @PostMapping("/write")
    @ResponseBody
    public String postWrite(@Valid PostForm postForm, BindingResult bindingResult) {

        postService.create(postForm.getSubject(), postForm.getContent());

        return "success";
    }
}
