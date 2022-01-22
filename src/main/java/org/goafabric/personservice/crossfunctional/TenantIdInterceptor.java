package org.goafabric.personservice.crossfunctional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class TenantIdInterceptor implements WebMvcConfigurer {
    private static final ThreadLocal<String> tenantIdThreadLocal = new ThreadLocal<>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                setTenantId(request.getHeader("X-TenantId"));
                return true;
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                tenantIdThreadLocal.remove();
            }
        });
    }

    public static String getTenantId() {
        final String tenantId = tenantIdThreadLocal.get();
        return tenantId == null ? "0" : tenantId;  //Todo: should throw exception
    }

    public static void setTenantId(String tenantId) {
        tenantIdThreadLocal.set(tenantId);
    }

}