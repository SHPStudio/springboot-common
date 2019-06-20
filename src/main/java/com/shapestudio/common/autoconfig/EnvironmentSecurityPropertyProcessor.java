package com.shapestudio.common.autoconfig;
import com.google.common.collect.Maps;
import com.shapestudio.common.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 环境属性解密处理
 */
public class EnvironmentSecurityPropertyProcessor implements EnvironmentPostProcessor {
    private final static String securityFileName = "security.properties";
    private final static String logbackConfigFileName = "logging.config";
    private final static String defaultLogbackConfigFilePath = "classpath:logback-common.xml";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        ClassPathResource resource = new ClassPathResource(securityFileName);
        try {
            ResourcePropertySource propertySource = new ResourcePropertySource(resource);
            processPropertyByPropertySource(propertySource, environment);
        } catch (Exception e) {

        }
        try {
            processLogbackConfigXml(environment);
        }catch (Exception e) {

        }
    }

    private void processPropertyByPropertySource(ResourcePropertySource securitySource, ConfigurableEnvironment environment) {
        Map<String, Object> securityMap = securitySource.getSource();
        securityMap.keySet().forEach(key -> securityMap.put(key, SecurityUtil.decretValue((String) securityMap.get(key))));
        environment.getPropertySources().addLast(securitySource);
    }

    private void processLogbackConfigXml(ConfigurableEnvironment environment) {
        // 检查是否有自定义的logback配置文件配置
        String value = environment.getProperty(logbackConfigFileName);
        if (StringUtils.isNotBlank(value)) {
            return;
        }

        // 检查是否有多余的
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        int configFileSize = mutablePropertySources.size();
        if (configFileSize > 5) {
            // 说明有application相关的配置文件
            MapPropertySource lastConfigPropertySource = (MapPropertySource) mutablePropertySources.stream()
                    .filter(propertySource -> propertySource.getName().contains("application"))
                    .findFirst().orElse(null);
            lastConfigPropertySource.getSource().put(logbackConfigFileName,  defaultLogbackConfigFilePath);
        }else {
            LinkedHashMap<String, Object> customMap = Maps.newLinkedHashMap();
            customMap.put(logbackConfigFileName, defaultLogbackConfigFilePath);
            // 没有的话
            MapPropertySource mapPropertySource = new MapPropertySource("application-custom", customMap);
            mutablePropertySources.addLast(mapPropertySource);
        }
    }
}
