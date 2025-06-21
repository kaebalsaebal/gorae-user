package com.gorae.gorae_user.api.open;

import com.gorae.gorae_user.common.dto.ApiResponseDto;
import com.gorae.gorae_user.domain.dto.SiteUserPasswordDto;
import com.gorae.gorae_user.domain.dto.SiteUserRegisterDto;
import com.gorae.gorae_user.domain.dto.SiteUserUpdateDto;
import com.gorae.gorae_user.remote.alim.RemoteAlimService;
import com.gorae.gorae_user.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final RemoteAlimService remoteAlimService;
    private final SiteUserService siteUserService;

    @PostMapping(value = "/update")
    public ApiResponseDto<String> updateUserInfo(@RequestBody @Valid SiteUserUpdateDto updateDto){
        siteUserService.updateUserInfo(updateDto);
        return ApiResponseDto.defaultOk();
    }

    @PostMapping(value = "/password")
    public ApiResponseDto<String> updatePassword(@RequestBody @Valid SiteUserPasswordDto passwordDto){
        siteUserService.updatePassword(passwordDto);
        return ApiResponseDto.defaultOk();
    }
}