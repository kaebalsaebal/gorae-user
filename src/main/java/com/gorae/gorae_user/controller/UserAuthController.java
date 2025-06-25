package com.gorae.gorae_user.controller;

import com.gorae.gorae_user.common.dto.ApiResponseDto;
import com.gorae.gorae_user.domain.dto.SiteUserLogin_IN;
import com.gorae.gorae_user.domain.dto.SiteUserLogin_OUT;
import com.gorae.gorae_user.domain.dto.SiteUserRefresh_IN;
import com.gorae.gorae_user.domain.dto.SiteUserRegister_IN;
import com.gorae.gorae_user.secret.jwt.dto.TokenDto;
import com.gorae.gorae_user.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserAuthController {
    private final SiteUserService siteUserService;

    @PostMapping(value = "/register")
    public ApiResponseDto<String> register(@RequestBody @Valid SiteUserRegister_IN registerDto) {
        siteUserService.registerUser(registerDto);
        return ApiResponseDto.defaultOk();
    }

    @PostMapping(value = "/login")
    public ApiResponseDto<SiteUserLogin_OUT> login(@RequestBody @Valid SiteUserLogin_IN loginDto) {
        SiteUserLogin_OUT userInfo = siteUserService.login(loginDto);
        return ApiResponseDto.createOk(userInfo);
    }

    @GetMapping(value = "/isvalid")
    public ApiResponseDto<String> isValid(@RequestParam @Valid String userId) {
        siteUserService.isValidUser(userId);
        return ApiResponseDto.defaultOk();
    }

    @PostMapping(value = "/refresh")
    public ApiResponseDto<TokenDto.AccessToken> refresh(@RequestBody @Valid SiteUserRefresh_IN refreshDto) {
        TokenDto.AccessToken token = siteUserService.refresh(refreshDto);
        return ApiResponseDto.createOk(token);
    }
}