package com.gorae.gorae_user.kafka.producer.notification.dto;

import com.gorae.gorae_user.domain.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserNotificationEvent {
    public static final String Topic = "user-notification";

    private String userId;

    private String userName;

    private String profileImgUrl;

    private LocalDateTime eventTime;

    public static UserNotificationEvent fromEntity(SiteUser siteUser){
        UserNotificationEvent event = new UserNotificationEvent();

        event.userId = siteUser.getUserId();
        event.profileImgUrl = siteUser.getUserProfile();
        event.eventTime = LocalDateTime.now();

        return event;
    }
}
