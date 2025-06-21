package com.gorae.gorae_user.common.web.context;

import com.gorae.gorae_user.common.exception.NotFound;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GatewayRequestHeaderUtils {
    private static String getRequestHeaderParamAsString(String key){
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest().getHeader(key);
    }

    private static String getUserId(){
        return getRequestHeaderParamAsString("X-Auth-UserId");
    }

    private static String getClientDevice(){
        return getRequestHeaderParamAsString("X-Client-Device");
    }

    private static String getClientAddress(){
        return getRequestHeaderParamAsString("X-Client-Address");
    }

    public static String getUserIdOrThrowException(){
        String userId = getUserId();
        if(userId == null){
            throw new NotFound("헤더에 UserId정보가 없슴");
        }

        return userId;
    }

    public static String getClientDeviceOrThrowException(){
        String clientDevice = getClientDevice();
        if (clientDevice == null){
            throw new NotFound("헤더에 사용자 장치정보가 없슴");
        }

        return clientDevice;
    }

    public static String getclientAddressOrThrowException(){
        String clientAddress = getClientAddress();
        if (clientAddress == null){
            throw new NotFound("헤더에 사용자 IP주소 정보가 없슴");
        }

        return clientAddress;
    }
}
