package com.gorae.gorae_user.domain.dto;

import com.gorae.gorae_user.domain.entity.SiteUser;
import com.gorae.gorae_user.secret.hash.SecureHashUtils;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserRegisterDto {
    @NotBlank(message = "아이디입력")
    private String userId;
    @NotBlank(message = "이름")
    private String userName;
    @NotBlank(message = "비번입력")
    private String password;
    @NotBlank(message = "전번입력")
    private String phoneNumber;
    @NotBlank(message = "이메일입력")
    private String email;

    private String userProfile;

    public SiteUser toEntity(){
        SiteUser siteUser = new SiteUser();

        siteUser.setUserId(this.userId);
        siteUser.setUserName(this.userName);
        siteUser.setPassword(SecureHashUtils.hash(this.password));
        siteUser.setPhoneNumber(this.phoneNumber);
        siteUser.setEmail(this.email);
        siteUser.setUserProfile(this.userProfile);

        return siteUser;
    }
}
