package xyz.kuilei.spring.demo.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext APPLICATION_CONTEXT;

    @Nonnull
    public static ApplicationContext getApplicationContext() {
        ApplicationContext applicationContext = APPLICATION_CONTEXT;
        Assert.notNull(applicationContext, "APPLICATION_CONTEXT is null");
        return applicationContext;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }
}
