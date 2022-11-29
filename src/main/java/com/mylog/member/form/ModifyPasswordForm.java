package com.mylog.member.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ModifyPasswordForm {
    @NotBlank(message = "빈 항목이 있는지 확인하세요.")
    private String oldPassword;
    @NotBlank(message = "빈 항목이 있는지 확인하세요.")
    private String newPassword1;
    @NotBlank(message = "빈 항목이 있는지 확인하세요.")
    private String newPassword2;
}
