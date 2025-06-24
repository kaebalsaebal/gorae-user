package com.gorae.gorae_user.kafka.producer.post.dto;

import com.gorae.gorae_user.domain.entity.SiteUser;
import lombok.Getter;
import lombok.Setter;


// User 의 정보가 수정됐다면 Post 에서 사용중인 DB 갱신을 위한 토픽
@Getter
@Setter
public class ChangeUserInfoEvent {
    public static final String Topic = "change-user-info";


    private String userId;

    private String userName;

    private String userImageUrl;


    public static ChangeUserInfoEvent fromEntity(SiteUser siteUser){
        ChangeUserInfoEvent event = new ChangeUserInfoEvent();

        event.userId = siteUser.getUserId();
        event.userName = siteUser.getUserName();
        event.userImageUrl = siteUser.getUserProfile();

        return event;
    }
}