package com.schaefersm.auth.util;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class CookieUtil {


    @Value("${validity.refresh}")
    private int refreshValidity;

    @Value("${validity.access}")
    private int accessValidity;

    public Cookie getAccessCookie(String value) {
        Cookie cookie = new Cookie("accessToken", value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(accessValidity);
        cookie.setSecure(false);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie getRefreshCookie(String value) {
        Cookie cookie = new Cookie("refreshToken", value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(refreshValidity);
        cookie.setSecure(false);
        cookie.setPath("/");
        return cookie;
    }

}
