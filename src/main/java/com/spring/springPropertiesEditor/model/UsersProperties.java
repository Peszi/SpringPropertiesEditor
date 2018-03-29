package com.spring.springPropertiesEditor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@org.springframework.context.annotation.PropertySource("classpath:/users.properties")
@ConfigurationProperties(prefix = "user")
public class UsersProperties {

    final static Logger logger = LoggerFactory.getLogger(UsersProperties.class);

    private Environment environment;

    private List<User> users = new ArrayList<>();

    public UsersProperties(Environment environment) {
        this.environment = environment;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void reloadUsers() {
//        ReloadableResourceBundleMessageSource rs = Global.getBean("messageSource", ReloadableResourceBundleMessageSource.class);
//        rs.clearCache();
        Map<String, Object> map = new HashMap();
        for(Iterator it = ((AbstractEnvironment) this.environment).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource propertySource = (PropertySource) it.next();
            logger.info(" >" + propertySource.getName() + "< ");
            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
                for (String property : ((MapPropertySource) propertySource).getPropertyNames())
                    logger.info(" >> " + property);
            }
        }
        logger.info("END!");
        this.environment.getProperty("");

//        ((AbstractEnvironment) this.environment).get

        MapPropertySource data = (MapPropertySource) ((AbstractEnvironment) this.environment).getPropertySources().get("class path resource [users.properties]");
        Map<String, Object> name = data.getSource();
        logger.info("TO" + name.entrySet());
//        User[] springRocks = this.environment.getProperty("user.users", User[].class);
//        for (User user : springRocks)
//            logger.info(user.getName());
    }


//    public String[] getAll() {
//        return environment.getProperty("users.user3", String[].class);
//    }
}
