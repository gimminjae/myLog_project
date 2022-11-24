package com.mylog.member.controller;

import com.mylog.base.exception.DataNotFoundException;
import com.mylog.base.util.Ut;
import com.mylog.mail.MailService;
import com.mylog.mail.MailTO;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.form.MemberForm;
import com.mylog.member.form.ModifyMemberForm;
import com.mylog.member.form.ModifyPasswordForm;
import com.mylog.member.service.MemberService;
import com.mylog.post.dto.PostDto;
import com.mylog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final MailService mailService;
    private final PostService postService;
    //로그인 폼
    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login() {
        return "member/login";
    }

    //회원가입 폼
    @GetMapping("/join")
    @PreAuthorize("isAnonymous()")
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

    //이메일로 아이디 찾기
    @PostMapping("/username")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public String findUsernameByEmail(@RequestParam("email") String email) {
        MemberDto memberDto = memberService.getByEmail(email);

        return memberDto.getUsername();
    }

    //임시 비밀번호 발급
    @PostMapping("/password")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public String find_password(@RequestParam("email") String email,
                                @RequestParam("username") String username) {
        MemberDto member = null;
        try {
            //회원 찾기
            member = memberService.getByUsername(username);

            if (!member.getEmail().equals(email)) {
                throw new DataNotFoundException("회원을 찾을 수 없습니다.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        //임시 비밀번호 생성
        String newPassword = Ut.Str.makeRandomPassword();

        //임시 비밀번호로 비밀번호 변겅
        memberService.modifyPassword(member, newPassword);

        //메일 생성 & 발송
        MailTO mail = new MailTO(email, "임시 비밀번호 발급", "회원님의 임시 비밀번호는 " + newPassword + " 입니다.");
        mailService.sendMail(mail);

        return "success";
    }

    //마이페이지
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public String myPage(Principal principal, Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "kw", defaultValue = "") String kw) {
        MemberDto memberDto = memberService.getByUsername(principal.getName());

        Page<PostDto> postDtoList = postService.getByMember(page, memberDto);

        model.addAttribute("postList", postDtoList);
        model.addAttribute("member", memberDto);

        return "member/profile";
    }

    //비밀번호 변경 폼
    @GetMapping("/modifyPassword")
    @PreAuthorize("isAuthenticated()")
    public String modifyPassword(ModifyPasswordForm modifyPasswordForm) {

        return "member/modify_password";
    }

    //비밀번호 변경 처리
    @PostMapping("/modifyPassword")
    @PreAuthorize("isAuthenticated()")
    public String modifyPassword(@Valid ModifyPasswordForm modifyPasswordForm, BindingResult bindingResult,
                                 Principal principal) {

        //빈 항목이 있을 경우
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return "redirect:/member/modifyPassword?errorMsg=%s".formatted(Ut.url.encode(errors.get(0)));
        }

        MemberDto memberDto = memberService.getByUsername(principal.getName());

        //기존 비밀번호가 틀렸을 경우
        if(!memberService.passwordConfirm(modifyPasswordForm.getOldPassword(), memberDto)) {
            return "redirect:/member/modifyPassword?errorMsg=%s".formatted(Ut.url.encode("기존 비밀번호가 틀렸습니다."));
        }
        //새 비밀번호와 비밀번호 확인이 일치하지 않을 경우
        if(!modifyPasswordForm.getNewPassword1().equals(modifyPasswordForm.getNewPassword2())) {
            return "redirect:/member/modifyPassword?errorMsg=%s".formatted(Ut.url.encode("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다."));
        }

       //비밀번호 변경
        memberService.modifyPassword(memberDto, modifyPasswordForm.getNewPassword1());

        return "redirect:/member?msg=%s".formatted(Ut.url.encode("비밀번호가 변경되었습니다."));
    }
    @GetMapping("/modifyMember")
    @PreAuthorize("isAuthenticated()")
    public String modifyMember(ModifyMemberForm modifyMemberForm, Principal principal) {
        MemberDto memberDto = memberService.getByUsername(principal.getName());

        modifyMemberForm.setEmail(memberDto.getEmail());
        modifyMemberForm.setNickname(memberDto.getNickname());

        return "member/modify_member";
    }
}
