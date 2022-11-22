package com.mylog.member.controller;

import com.mylog.member.form.LoginForm;
import com.mylog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }
}
