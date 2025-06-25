package com.gorae.gorae_user.kafka.producer.notification.dto;

import com.gorae.gorae_user.domain.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChangeUserNotificationEvent {
    public static final String Topic = "user-notification-change";

    private String userId;

    private String userName;

    private String profileImgUrl;

    public static ChangeUserNotificationEvent fromEntity(SiteUser siteUser){
        ChangeUserNotificationEvent event = new ChangeUserNotificationEvent();

        event.userId = siteUser.getUserId();
        event.userName = siteUser.getUserName();
        event.profileImgUrl = siteUser.getUserProfile();

        return event;
    }
}
