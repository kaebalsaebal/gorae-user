package com.gorae.gorae_user.kafka.producer.post.dto;

import com.gorae.gorae_user.domain.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserInfoEvent {
    public static final String Topic = "user-info";

    private String userId;

    private String userName;

    private String userImageUrl;

    public static UserInfoEvent fromEntity(SiteUser siteUser){
        UserInfoEvent event = new UserInfoEvent();

        event.userId = siteUser.getUserId();
        event.userName = siteUser.getUserName();
        event.userImageUrl = siteUser.getUserProfile();

        return event;
    }
}
