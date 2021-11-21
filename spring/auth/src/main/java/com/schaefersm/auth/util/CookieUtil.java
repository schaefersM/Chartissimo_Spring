package com.schaefersm.auth.util;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class CookieUtil {

    @Value("${cookieName.access}")
    private String accessCookieName;

    @Value("${cookieName.refresh}")
    private String refreshCookieName;



    @Value("${validity.refresh}")
    private int refreshValidity;

    @Value("${validity.access}")
    private int accessValidity;

    public Cookie getAccessCookie(String value) {
        Cookie cookie = new Cookie(accessCookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(accessValidity);
        cookie.setSecure(false);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie getRefreshCookie(String value) {
        Cookie cookie = new Cookie(refreshCookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(refreshValidity);
        cookie.setSecure(false);
        cookie.setPath("/");
        return cookie;
    }

}
