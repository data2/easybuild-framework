package com.data2.easybuild.configuration.common;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author data2
 * @description
 * @date 2021/3/26 下午3:08
 */
public abstract class AbstractBeanFilter implements BeanPostProcessor, ApplicationContextAware {
    protected ApplicationContext applicationContext;
}
