package com.mylog.member.controller;

import com.mylog.member.form.MemberForm;
import com.mylog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/join")
    public String join(MemberForm memberForm) {
        return "member/join_form";
    }
    @PostMapping("/join")
    public String join(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "member/join_form";
        }
        if(!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "member/join_form";
        }
        try {
            memberService.create(memberForm.getUsername(), memberForm.getPassword1(), memberForm.getEmail(), memberForm.getNickname());
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("joinFailed", "이미 등록된 회원입니다.");
            return "member/join_form";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "member/join_form";
        }
        return "redirect:/member/login";
    }
}
