package com.shapestudio.common.autoconfig;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ConditionalOnWebApplication
@RestController
@RequestMapping("/admin")
public class WebAdminAutoConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    @GetMapping("/getAllBeans")
    public String[] getAllBeans() {
        return applicationContext.getBeanDefinitionNames();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
