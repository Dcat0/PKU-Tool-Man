package edu.pku.PKUToolMan.config;

import edu.pku.PKUToolMan.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

// 只配置了Intercepter，没配置跨域和异步，不知道会不会有问题。
// https://blog.csdn.net/songfei_dream/article/details/103335575?utm_medium=distribute.pc_relevant.none-task-blog-title-2&spm=1001.2101.3001.4242
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private TokenInterceptor tokenInterceptor;

    public WebConfiguration(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/user/login");
        excludePath.add("/user/register");
        excludePath.add("/user/forget");
        excludePath.add("/order/add");
        excludePath.add("/order/myorderlist");
        excludePath.add("/order/receive");
        excludePath.add("/order/complete");
        excludePath.add("/order/delete");
        excludePath.add("/order/cancel");
        excludePath.add("/order/query");
        excludePath.add("/order/orderlist");
        excludePath.add("/error");
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
