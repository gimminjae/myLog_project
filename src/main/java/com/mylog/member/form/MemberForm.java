package com.mylog.member.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "")
    @Size(min = 5, max = 25, message = "아이디는 5자 이상입니다.")
    private String username;

    @NotEmpty(message = "")
    private String password1;

    @NotEmpty(message = "")
    private String password2;

    @NotEmpty(message = "")
    @Email
    private String email;

    @NotEmpty(message = "")
    private String nickname;
}
