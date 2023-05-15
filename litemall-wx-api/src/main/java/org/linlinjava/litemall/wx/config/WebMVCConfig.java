package org.linlinjava.litemall.wx.config;

import org.linlinjava.litemall.wx.config.intercept.WxInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private WxInterceptor wxInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(wxInterceptor)
                .addPathPatterns("/wx/**");
    }
}
