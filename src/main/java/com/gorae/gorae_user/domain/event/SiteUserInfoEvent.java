package com.gorae.gorae_user.domain.event;

import com.gorae.gorae_user.domain.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SiteUserInfoEvent {
    public static final String Topic = "userinfo";

    private String action;

    private String userId;

    private String phoneNumber;

    private String email;

    private String userProfile;

    private LocalDateTime eventTime;

    public static SiteUserInfoEvent fromEntity(String action, SiteUser siteUser){
        SiteUserInfoEvent event = new SiteUserInfoEvent();

        event.action = action;
        event.userId = siteUser.getUserId();
        event.phoneNumber = siteUser.getPhoneNumber();
        event.email = siteUser.getEmail();
        event.userProfile = siteUser.getUserProfile();
        event.eventTime = LocalDateTime.now();

        return event;
    }
}
