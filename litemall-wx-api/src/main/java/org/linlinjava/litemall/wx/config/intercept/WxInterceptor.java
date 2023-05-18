package org.linlinjava.litemall.wx.config.intercept;

import org.apache.commons.lang3.StringUtils;
import org.linlinjava.litemall.db.config.WxUserThreadLocal;
import org.linlinjava.litemall.db.config.SysUser;
import org.linlinjava.litemall.wx.service.UserTokenManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.linlinjava.litemall.wx.annotation.support.LoginUserHandlerMethodArgumentResolver.LOGIN_TOKEN_KEY;

@Component
public class WxInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        if (StringUtils.isNotBlank(token)){
            SysUser sysUser = UserTokenManager.getSysUser(token);
            WxUserThreadLocal.put(sysUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WxUserThreadLocal.remove();
    }
}
