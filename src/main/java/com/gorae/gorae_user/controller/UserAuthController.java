package com.gorae.gorae_user.controller;

import com.gorae.gorae_user.common.dto.ApiResponseDto;
import com.gorae.gorae_user.domain.dto.SiteUserLoginDto;
import com.gorae.gorae_user.domain.dto.SiteUserRegisterDto;
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
    public ApiResponseDto<String> register(@RequestPart(value = "registerData") @Valid SiteUserRegisterDto registerDto,
                                           @RequestPart(value = "profileImage", required = false) MultipartFile profileImage){
        siteUserService.registerUser(registerDto, profileImage);
        return ApiResponseDto.defaultOk();
    }

    @PostMapping(value = "/login")
    public ApiResponseDto<TokenDto.AccessRefreshToken> login(@RequestBody @Valid SiteUserLoginDto loginDto){
        TokenDto.AccessRefreshToken token = siteUserService.login(loginDto);
        return ApiResponseDto.createOk(token);
    }

    @GetMapping(value = "/isvalid")
    public ApiResponseDto<String> isValid(@RequestParam @Valid String userId){
        siteUserService.isValidUser(userId);
        return ApiResponseDto.defaultOk();
    }
}