package com.gorae.gorae_user.service.probe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProbeService{
    public void validateLiveness(){
        //써비스 재시작시 exception
    }

    public void validateReadiness(){
        //써비스 일시중지시 exception
    }
}
