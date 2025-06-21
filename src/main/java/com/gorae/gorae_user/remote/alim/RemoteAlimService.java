package com.gorae.gorae_user.remote.alim;

import com.gorae.gorae_user.common.dto.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "RemoteAlimService", url = "http://k8s-backend-alim-service:8080", path = "/backend/alim/v1")
public interface RemoteAlimService {
    @GetMapping(value = "/hello")
    public ApiResponseDto<String> hello();
}
