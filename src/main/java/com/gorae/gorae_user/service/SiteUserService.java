package com.gorae.gorae_user.service;

import com.gorae.gorae_user.common.exception.BadParameter;
import com.gorae.gorae_user.common.exception.NotFound;
import com.gorae.gorae_user.domain.dto.*;
import com.gorae.gorae_user.domain.entity.SiteUser;
import com.gorae.gorae_user.domain.event.SiteUserInfoEvent;
import com.gorae.gorae_user.domain.repository.SiteUserRepository;
import com.gorae.gorae_user.event.producer.KafkaMessageProducer;
import com.gorae.gorae_user.secret.hash.SecureHashUtils;
import com.gorae.gorae_user.secret.jwt.TokenGenerator;
import com.gorae.gorae_user.secret.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteUserService {
    //레포, 캎카 불러오기
    private final SiteUserRepository siteUserRepository;
    private final KafkaMessageProducer kafkaMessageProducer;
    private final TokenGenerator tokenGenerator;

    //회원가입
    @Transactional
    public void registerUser(SiteUserRegisterDto registerDto){
        //저장-dto에 있는 엔티티화 메쏘드 사용
        SiteUser siteUser = registerDto.toEntity();
        siteUserRepository.save(siteUser);

        //캎카 퍼블리쉬
        SiteUserInfoEvent event = SiteUserInfoEvent.fromEntity("UserCreate", siteUser);
        kafkaMessageProducer.send(SiteUserInfoEvent.Topic, event);
    }

    //로그인
    @Transactional(readOnly=true)
    public TokenDto.AccessRefreshToken login(SiteUserLoginDto loginDto) {
        //찾기
        SiteUser user = siteUserRepository.findByUserId(loginDto.getUserId());
        if (user == null) {
            throw new NotFound("다시 로그인해");
        }
        if (user.getDeleted()){
            throw new BadParameter("존재하지 않는 사용자");
        }
        //비밀번호 체크
        if( !SecureHashUtils.matches(loginDto.getPassword(), user.getPassword())){throw new BadParameter("다시 로그인해");
        }
        //JWT생성
        return tokenGenerator.generateAccessRefreshToken(loginDto.getUserId(), "WEB");
    }

    //아이디 중복확인
    @Transactional(readOnly = true)
    public void isValidUser(String userId){
        SiteUser user = siteUserRepository.findByUserIdAndDeleted(userId, false);

        if (user != null){
            throw new NotFound("동일한 아이디의 유저있어");
        }
    }

    //사용자정보 갱신
    @Transactional
    public void updateUserInfo(SiteUserUpdateDto updateDto){
        SiteUser user = siteUserRepository.findByUserId(updateDto.getUserId());
        if (user == null) {
            throw new NotFound("다시 로그인해");
        }
        //없데이트(트랜잭션이라 set만 해도 자동으로~)
        user.setPhoneNumber(updateDto.getPhoneNumber());
        user.setEmail(updateDto.getEmail());
        user.setUserProfile(updateDto.getUserProfile());

        //캎카 퍼블리쉬
        SiteUserInfoEvent event = SiteUserInfoEvent.fromEntity("UserUpdate", user);
        kafkaMessageProducer.send(SiteUserInfoEvent.Topic, event);
    }

    //비번갱신
    @Transactional
    public void updatePassword(SiteUserPasswordDto passwordDto){
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
    public void removeUser(SiteUserRemoveDto removeDto){
        SiteUser user = siteUserRepository.findByUserId(removeDto.getUserId());

        user.setDeleted(true);
    }
}
