package com.shenlan.log_filter_demo.config;

import com.shenlan.log_filter_demo.filter.APILogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new APILogFilter());
        registration.addUrlPatterns("/api/*");
        return registration;
    }
}
