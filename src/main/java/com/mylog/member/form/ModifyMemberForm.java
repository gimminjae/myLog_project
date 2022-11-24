package com.mylog.member.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ModifyMemberForm {
    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
