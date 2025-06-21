package com.gorae.gorae_user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserRemoveDto {
    @NotBlank(message="아이디")
    private String userId;
}
