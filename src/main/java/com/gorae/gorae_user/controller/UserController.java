package com.gorae.gorae_user.controller;

import com.gorae.gorae_user.common.dto.ApiResponseDto;
import com.gorae.gorae_user.domain.dto.SiteUserPasswordDto;
import com.gorae.gorae_user.domain.dto.SiteUserRemoveDto;
import com.gorae.gorae_user.domain.dto.SiteUserUpdateDto;
import com.gorae.gorae_user.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final SiteUserService siteUserService;

    @PostMapping(value = "/update")
    public ApiResponseDto<String> updateUserInfo(@RequestPart(value = "updateData") @Valid SiteUserUpdateDto updateDto,
                                                 @RequestPart(value = "profileImage", required = false) MultipartFile profileImage){
        siteUserService.updateUserInfo(updateDto, profileImage);
        return ApiResponseDto.defaultOk();
    }

    @PostMapping(value = "/password")
    public ApiResponseDto<String> updatePassword(@RequestBody @Valid SiteUserPasswordDto passwordDto){
        siteUserService.updatePassword(passwordDto);
        return ApiResponseDto.defaultOk();
    }

    @PostMapping(value = "/remove")
    public ApiResponseDto<String> removeUser(@RequestBody @Valid SiteUserRemoveDto removeDto){
        siteUserService.removeUser(removeDto);
        return ApiResponseDto.defaultOk();
    }
}