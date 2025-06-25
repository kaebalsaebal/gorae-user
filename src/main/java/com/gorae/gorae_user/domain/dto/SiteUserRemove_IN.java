package com.gorae.gorae_user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserRemove_IN {
    @NotBlank(message="아이디")
    private String userId;
}
