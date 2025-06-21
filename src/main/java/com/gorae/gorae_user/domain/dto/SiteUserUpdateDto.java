package com.gorae.gorae_user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserUpdateDto {
    @NotBlank(message = "아이디입력")
    private String userId;
    @NotBlank(message = "전번입력")
    private String phoneNumber;
    @NotBlank(message = "이메일입력")
    private String email;

    private String userProfile;
}
