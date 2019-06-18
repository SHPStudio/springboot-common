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

### 性能日志
  `@Performance` 在方法或者类上加入该注解，将会打印调用该方法耗时日志(warn级别)，后期可能会使用内存数据库存储，方便查询

### 异常日志
  `@LogException` 在方法或者类上加入该注解，将会在(debug级别)输出入参和出参，在调用异常时(error级别)打印错误日志

### Controller层通用异常处理
  controller可以不需要try catch处理，统一会进行处理，目前只处理resultful形式的接口异常。
  1. controller接口返回值是否属于集合，如果是集合返回前端空数组
  2.

### 配置文件加密
  放在`security.properties`配置文件下的属性值将会被解密

### 默认日志配置
  如果没有配置`logging.config`的话，就会在配置中默认加入本jar包的日志配置。
  1. 默认输出日志文件路径 `/export/log/spring.log`
  2. 输出异常日志文件路径 `/export/log/except.log`
  3. 输出性能日志文件路径 `/export/log/performance.log`
  4. 输出controller异常日志文件路径 `/export/log/url-except.log`