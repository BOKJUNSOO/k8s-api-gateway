package com.welab.k8s_api_gateway.gateway.filter;

import com.welab.k8s_api_gateway.security.jwt.authentication.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.function.Function;

public class AuthenticationHeaderFilterFunction {

    public static Function<ServerRequest, ServerRequest> addHeader() {
        return request -> {
            ServerRequest.Builder requestBuilder = ServerRequest.from(request);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserPrincipal userPrincipal) {
                requestBuilder.header("X-Auth-UserId",userPrincipal.getUserId());
                // 그외 Claims 작성
            }

            String remoteAdd = "70.1.23.15";
            requestBuilder.header("X-Client-Address", remoteAdd);

            String device = "WEB";
            requestBuilder.header("X-Client-Device",device);

            return requestBuilder.build();
        };
    }
}
