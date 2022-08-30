package com.gaoice.distributed.scheduling;

import com.gaoice.distributed.scheduling.aspect.ScheduledAspect;
import com.gaoice.distributed.scheduling.redis.RedisDistributedHandler;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;

/**
 * @author gaoice
 */
@Configuration
@EnableConfigurationProperties(DistributedSchedulingAutoConfigure.class)
@ConfigurationProperties("com.gaoice.distributed.scheduling")
public class DistributedSchedulingAutoConfigure implements ApplicationContextAware {

    private String appName;

    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(appName)) {
            appName = applicationContext.getId();
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    public DistributedHandler redisDistributedHandler(StringRedisTemplate stringRedisTemplate) {
        return new RedisDistributedHandler(stringRedisTemplate);
    }

    @Bean
    public ScheduledAspect scheduledAspect(DistributedHandler distributedHandler) {
        return new ScheduledAspect(appName, distributedHandler);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
