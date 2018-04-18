package com.newland.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;



/**
 * 扩展 PropertyPlaceholderConfigurer 读取properties文件
 * 
 * @author hongye 2017-03-7
 * @version 1.0v
 */

/**
 * 
 * 我们都知道，Spring可以@Value的方式读取properties中的值，只需要在配置文件中配置org.springframework.beans.factory.config.PropertyPlaceholderConfigurer

<bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="location">
            <value>classpath:config.properties</value>
        </property>
    </bean>
那么在需要用到这些获取properties中值的时候，可以这样使用

    @Value("${sql.name}")
    private String sqlName;
但是这有一个问题，我每用一次配置文件中的值，就要声明一个局部变量。有没有用代码的方式，直接读取配置文件中的值。

   答案就是重写PropertyPlaceholderConfigurer
   
   在配置文件中，用上面的类，代替PropertyPlaceholderConfigurer

  <bean id="propertyConfigurer" class="com.newland.nlnetfinplatform.portal.env.PropertyPlaceholder">
        <property name="location">
            <value>classpath:config.properties</value>
        </property>
    </bean>
这样在代码中就可以直接用编程方式获取

 PropertyPlaceholder.getProperty("sql.name");
   
 */

public class PropertyPlaceholder extends PropertyPlaceholderConfigurer {

    private static Map<String,String> propertyMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        propertyMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertyMap.put(keyStr, value);
        }
    }

    //static method for accessing context properties
    public static Object getProperty(String name) {
        return propertyMap.get(name);
    }
}
