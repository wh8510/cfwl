package org.example.cfwl.config;
import org.example.cfwl.jwt.AdminJWT;
import org.example.cfwl.jwt.UserJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private UserJWT userJWT;
   @Autowired
   private AdminJWT adminJWT;
    /**
     * 给除了 /login 的接口都配置拦截器,拦截转向到 adminJWT或者userJWT
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userJWT)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/user");
    }
}