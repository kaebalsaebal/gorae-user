package com.gorae.gorae_user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserPasswordDto {
    @NotBlank(message="아이디")
    private String userId;
    @NotBlank(message="옛비번")
    private String oldPassword;
    @NotBlank(message="뉴비번")
    private String newPassword;
}
