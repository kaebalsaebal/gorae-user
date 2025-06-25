package com.gorae.gorae_user.service;

import com.gorae.gorae_user.common.aws.S3Service;
import com.gorae.gorae_user.common.exception.AlreadyExists;
import com.gorae.gorae_user.common.exception.BadParameter;
import com.gorae.gorae_user.common.exception.NotFound;
import com.gorae.gorae_user.domain.dto.*;
import com.gorae.gorae_user.domain.entity.SiteUser;
import com.gorae.gorae_user.domain.repository.SiteUserRepository;
import com.gorae.gorae_user.kafka.producer.KafkaMessageProducer;
import com.gorae.gorae_user.kafka.producer.leaderboard.dto.UserLeaderBoardEvent;
import com.gorae.gorae_user.kafka.producer.notification.dto.ChangeUserNotificationEvent;
import com.gorae.gorae_user.kafka.producer.notification.dto.UserNotificationEvent;
import com.gorae.gorae_user.kafka.producer.post.dto.ChangeUserInfoEvent;
import com.gorae.gorae_user.kafka.producer.post.dto.UserInfoEvent;
import com.gorae.gorae_user.secret.hash.SecureHashUtils;
import com.gorae.gorae_user.secret.jwt.TokenGenerator;
import com.gorae.gorae_user.secret.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteUserService {
    //레포, 캎카 불러오기
    private final SiteUserRepository siteUserRepository;
    private final KafkaMessageProducer kafkaMessageProducer;
    private final TokenGenerator tokenGenerator;
    private final S3Service s3Service;

    //회원가입
    @Transactional
    public void registerUser(SiteUserRegister_IN registerDto, MultipartFile profileImage){

        //프로파일 이미지부터 s3에 저장
        String profileUrl = "";
        try{
            profileUrl = s3Service.uploadFile(profileImage, "");
        } catch(IOException e){

        }

        SiteUser user2 = siteUserRepository.findByUserName(registerDto.getUserName());
        if (user2 != null) {
            throw new AlreadyExists("동일한 닉네임의 사용자가 이미 있어");
        }

        //저장-dto에 있는 엔티티화 메쏘드 사용
        SiteUser siteUser = registerDto.toEntity();
        //포라파일 이미지는 따로 셋터
        siteUser.setUserProfile(profileUrl);
        siteUserRepository.save(siteUser);

        //알림에 캎카 퍼블리쉬(topic: user-notification)
        UserNotificationEvent alimEvent = UserNotificationEvent.fromEntity(siteUser);
        kafkaMessageProducer.send(UserNotificationEvent.Topic, alimEvent);
        //포스트에 캎카 퍼블리쉬(topic: user-info)
        UserInfoEvent postEvent = UserInfoEvent.fromEntity(siteUser);
        kafkaMessageProducer.send(UserInfoEvent.Topic, postEvent);
        //리더보드에 캎카 퍼블리쉬
        UserLeaderBoardEvent leaderBoardEvent = UserLeaderBoardEvent.fromEntity("register-user", siteUser);
        kafkaMessageProducer.send(UserLeaderBoardEvent.Topic, leaderBoardEvent);

    }

    //로그인
    @Transactional(readOnly=true)
    public SiteUserLogin_OUT login(SiteUserLogin_IN loginDto) {
        //찾기
        System.out.println(loginDto.getUserId());
        System.out.println(loginDto.getPassword());
        SiteUser user = siteUserRepository.findByUserId(loginDto.getUserId());
        SiteUserLogin_OUT result = new SiteUserLogin_OUT();
        if (user == null) {
            throw new NotFound("아이디 또는 패쓰워드가 맞지 않아");
        }
        if (user.getDeleted()){
            throw new NotFound("존재하지 않는 사용자");
        }
        //비밀번호 체크
        if( !SecureHashUtils.matches(loginDto.getPassword(), user.getPassword())){throw new BadParameter("비밀번호가 맞지 않아");
        }
        //JWT생성
        TokenDto.AccessRefreshToken token = tokenGenerator.generateAccessRefreshToken(loginDto.getUserId(), "WEB");
        return result.fromEntity(user, token);
    }

    //아이디 중복확인
    @Transactional(readOnly = true)
    public void isValidUser(String userId){
        SiteUser user = siteUserRepository.findByUserIdAndDeleted(userId, false);

        if (user != null){
            throw new AlreadyExists("동일한 아이디의 유저있어");
        }
    }

    //사용자정보 갱신
    @Transactional
    public void updateUserInfo(SiteUserUpdate_IN updateDto, MultipartFile profileImage){
        SiteUser user = siteUserRepository.findByUserId(updateDto.getUserId());
        if (user == null) {
            throw new NotFound("로그인 정보가 없어");
        }
        SiteUser user2 = siteUserRepository.findByUserName(updateDto.getUserName());
        if (user2 != null) {
            throw new AlreadyExists("동일한 닉네임의 사용자가 이미 있어");
        }

        //프로파일 이미지 교체
        String profileUrl = user.getUserProfile();
        try{
            profileUrl = s3Service.uploadFile(profileImage, profileUrl);
        } catch(IOException e){

        }

        //없데이트(트랜잭션이라 set만 해도 자동으로~)
        user.setUserName(updateDto.getUserName());
        user.setPhoneNumber(updateDto.getPhoneNumber());
        user.setEmail(updateDto.getEmail());
        user.setUserProfile(profileUrl);

        //알림에 캎카 퍼블리쉬(topic: user-notification-change, action: ChangeUserInfo)
        ChangeUserNotificationEvent alimEvent = ChangeUserNotificationEvent.fromEntity(user);
        kafkaMessageProducer.send(UserNotificationEvent.Topic, alimEvent);
        //포스트에 캎카 퍼블리쉬(topic: change-user-info, action: ChangeUserInfo)
        ChangeUserInfoEvent postEvent = ChangeUserInfoEvent.fromEntity(user);
        kafkaMessageProducer.send(ChangeUserInfoEvent.Topic, postEvent);
        //리더보드에 캎카 퍼블리쉬
        UserLeaderBoardEvent leaderBoardEvent = UserLeaderBoardEvent.fromEntity("change-user", user);
        kafkaMessageProducer.send(UserLeaderBoardEvent.Topic, leaderBoardEvent);
    }

    //비번갱신
    @Transactional
    public void updatePassword(SiteUserPassword_IN passwordDto){
        SiteUser user = siteUserRepository.findByUserId(passwordDto.getUserId());

        // 올드비번이 맞는지 확인
        if( !SecureHashUtils.matches(passwordDto.getOldPassword(), user.getPassword())){
            throw new BadParameter("비밀번호가 맞지 않아");
        }
        // 새비번으로 교체(해쉬하는거 잊지말고!)
        user.setPassword(SecureHashUtils.hash(passwordDto.getNewPassword()));
    }

    //회원탈퇴
    @Transactional
    public void removeUser(SiteUserRemove_IN removeDto){
        SiteUser user = siteUserRepository.findByUserId(removeDto.getUserId());

        user.setDeleted(true);
    }
}
