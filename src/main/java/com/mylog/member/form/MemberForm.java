package com.mylog.member.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "아이디는 필수항목입니다.")
    @Size(min = 5, max = 25)
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입호다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일는 필수항목입니다.")
    @Email
    private String email;

    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String nickname;
}
