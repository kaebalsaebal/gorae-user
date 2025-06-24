package com.gorae.gorae_user.kafka.producer.leaderboard.dto;

import com.gorae.gorae_user.domain.entity.SiteUser;
import com.gorae.gorae_user.kafka.producer.notification.dto.UserNotificationEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserLeaderBoardEvent {
    public static final String Topic = "user";

    private String action;

    private String userId;

    private String userName;

    private String profileImgUrl;

    private String userBadge;

    private LocalDateTime eventTime;

    public static UserLeaderBoardEvent fromEntity(String action, SiteUser siteUser){
        UserLeaderBoardEvent event = new UserLeaderBoardEvent();

        event.action = action;
        event.userId = siteUser.getUserId();
        event.profileImgUrl = siteUser.getUserProfile();
        event.userBadge = "빈데이타";
        event.eventTime = LocalDateTime.now();

        return event;
    }
}
