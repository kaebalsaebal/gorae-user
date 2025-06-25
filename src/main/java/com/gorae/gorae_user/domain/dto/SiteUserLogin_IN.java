package com.gorae.gorae_user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserLogin_IN {
    @NotBlank(message="아이디")
    private String userId;
    @NotBlank(message="비번")
    private String password;
}
