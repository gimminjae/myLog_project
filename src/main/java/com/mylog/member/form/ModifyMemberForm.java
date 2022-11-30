package com.mylog.member.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ModifyMemberForm {
    @NotBlank(message = "빈 항목을 확인해주세요.")
    private String nickname;

    @NotBlank(message = "빈 항목을 확인해주세요.")
    private String email;

    @NotBlank(message = "빈 항목을 확인해주세요.")
    private String password;
}
