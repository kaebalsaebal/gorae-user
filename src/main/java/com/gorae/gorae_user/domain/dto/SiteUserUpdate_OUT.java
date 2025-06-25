package com.gorae.gorae_user.domain.dto;

import com.gorae.gorae_user.domain.entity.SiteUser;
import com.gorae.gorae_user.secret.jwt.dto.TokenDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SiteUserUpdate_OUT {
    private TokenDto.AccessRefreshToken tokens;
    private String userName;
    private String phoneNumber;
    private String email;
    private String userProfile;

    public SiteUserUpdate_OUT fromEntity(SiteUser user){
        SiteUserUpdate_OUT result = new SiteUserUpdate_OUT();
        result.setUserName(user.getUserName());
        result.setPhoneNumber(user.getPhoneNumber());
        result.setEmail(user.getEmail());
        result.setUserProfile(user.getUserProfile());

        return result;
    }
}
