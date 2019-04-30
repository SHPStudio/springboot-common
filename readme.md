# Springboot-common
  springboot项目通用开发jar包，目的用于web项目简化配置，让开发项目重点放在业务逻辑上而不是还需要配置一堆其他的通用功能，导致项目变得特别臃肿，不用每次创建项目都需要引入相关springboot的jar包，还有一些工具
包等。并且加入一些项目需要的通用功能，登录拦截、cors跨域等等。

## 使用

### maven
1. 配置私服地址
```
<repositories>
    <repository>
       <id>rdc-snapshots</id>
       <url>https://repo.rdc.aliyun.com/repository/71096-snapshot-MCVVvS/</url>
    </repository>
</repositories>
```

2. jar包pom（目前只有开发版本）
```
 <dependency>
     <groupId>com.shapestudio</groupId>
     <artifactId>springboot-common</artifactId>
     <version>1.0-SNAPSHOT</version>
 </dependency>
```

3. 在项目里引入这一个jar包，然后创建一个带有`@SpringBootApplication`启动类即可

## 目前版本
    1.0-SNAPSHOT 基于jdk8, Springboot 2.1.0.RELEASE并使用Gradle构建。

## 建议
    如果要使用该jar包，推荐新加有关springboot jar包版本与该jar的springboot版本一致
    项目中使用了lombok，推荐使用IntellJ Idea开发并安装lombok插件，否则lombok相关注解将不生效。

## 功能

### CORS跨域配置
  1. 通过属性`shape.cors.enable`开启关闭cors。
  2. 通过属性`shape.except.cors.urls`排除不需要跨域的url。
  3. 通过实现`com.shapestudio.common.interceptor.CorsInterceptor`接口创建自定义Cors拦截Bean。

### 登录拦截器
  1. 通过属性`shape.except.login.urls`排除不需要登录拦截的url
  2. 通过实现`com.shapestudio.common.interceptor.LoginInterceptor`接口创建自定义登录拦截Bean。

### 工具类
  1. apache-common通用工具类
  2. guava工具类
  3. fastjson工具类