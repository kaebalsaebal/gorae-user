package com.gorae.gorae_user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserRefresh_IN {
    @NotBlank(message = "리프레쉬토큰")
    private String token;
}
