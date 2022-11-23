package com.mylog.member.controller;

import com.mylog.base.util.Ut;
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

    //로그인 폼
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    //회원가입 폼
    @GetMapping("/join")
    public String join(MemberForm memberForm) {
        return "member/join_form";
    }

    //회원가입 처리
    @PostMapping("/join")
    public String join(@Valid MemberForm memberForm, BindingResult bindingResult) {
        //빈 항목이 있을 경우
        if(bindingResult.hasErrors()) {
            bindingResult.reject("joinFailed", "빈 항목을 입력하세요");
            return "member/join_form";
        }

        //2개의 비밀번호가 일치하지 않을 경우
        if(!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordIncorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "member/join_form";
        }
        try {
            //회원가입
            memberService.create(memberForm.getUsername(), memberForm.getPassword1(), memberForm.getEmail(), memberForm.getNickname());
        } catch(DataIntegrityViolationException e) {
            //정보가 중복될 경우
            e.printStackTrace();
            bindingResult.reject("joinFailed", "이미 등록된 회원입니다.");
            return "member/join_form";
        } catch(Exception e) {
            //기타 에러
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "member/join_form";
        }

        //회원가입 완료
        return "redirect:/member/login?msg=%s".formatted(Ut.url.encode("회원가입 완료! 로그인하세요!"));
    }
}
