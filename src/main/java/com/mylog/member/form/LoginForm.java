package com.mylog.member.form;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
