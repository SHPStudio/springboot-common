package com.shapestudio.common.autoconfig;
import com.google.common.collect.Maps;
import com.shapestudio.common.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.Map;


/**
 * 环境属性解密处理
 */
public class EnvironmentSecurityPropertyProcessor implements EnvironmentPostProcessor {
    private final static String securityFileName = "security.properties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        ClassPathResource resource = new ClassPathResource(securityFileName);
        try {
            ResourcePropertySource propertySource = new ResourcePropertySource(resource);
            processPropertyByPropertySource(propertySource, environment);
        } catch (IOException e) {

        }
    }

    private void processPropertyByPropertySource(ResourcePropertySource securitySource, ConfigurableEnvironment environment) {
        Map<String, Object> securityMap = securitySource.getSource();
        securityMap.keySet().forEach(key -> securityMap.put(key, SecurityUtil.decretValue((String) securityMap.get(key))));
        environment.getPropertySources().addLast(securitySource);
    }
}
