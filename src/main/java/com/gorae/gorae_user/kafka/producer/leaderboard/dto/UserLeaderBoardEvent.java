package com.gorae.gorae_user.kafka.producer.leaderboard.dto;

import com.gorae.gorae_user.domain.entity.SiteUser;
import com.gorae.gorae_user.kafka.producer.notification.dto.UserNotificationEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserLeaderBoardEvent {
    public static final String Topic = "user-leaderboard";

    private String action;

    private String userId;

    private String userName;

    private String profileImgUrl;

    public static UserLeaderBoardEvent fromEntity(String action, SiteUser siteUser){
        UserLeaderBoardEvent event = new UserLeaderBoardEvent();

        event.action = action;
        event.userId = siteUser.getUserId();
        event.userName = siteUser.getUserName();
        event.profileImgUrl = siteUser.getUserProfile();

        return event;
    }
}
