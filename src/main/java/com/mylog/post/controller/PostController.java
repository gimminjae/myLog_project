package com.mylog.post.controller;

import com.mylog.post.dto.PostDto;
import com.mylog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
