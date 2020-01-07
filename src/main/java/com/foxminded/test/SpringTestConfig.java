package com.foxminded.test;

import com.foxminded.configs.SpringConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


import javax.sql.DataSource;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Configuration
@PropertySource("classpath:properties/h2.properties")
@Import(SpringConfig.class)
public class SpringTestConfig {

    @Value("${h2.url}")
    private String url;
    @Value("${h2.username}")
    private String username;
    @Value("${h2.password}")
    private String password;
    @Value("${h2.driverClassName}")
    private String driver;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }
}
