package com.gorae.gorae_user.domain.dto;

import com.gorae.gorae_user.domain.entity.SiteUser;
import com.gorae.gorae_user.secret.jwt.dto.TokenDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SiteUserLogin_OUT {
    private TokenDto.AccessRefreshToken tokens;
    private String userId;
    private String userName;
    private String userProfile;

    public SiteUserLogin_OUT fromEntity(SiteUser user, TokenDto.AccessRefreshToken token){
        SiteUserLogin_OUT result = new SiteUserLogin_OUT();
        result.setUserId(user.getUserId());
        result.setUserName(user.getUserName());
        result.setUserProfile(user.getUserProfile());
        result.setTokens(token);

        return result;
    }
}
