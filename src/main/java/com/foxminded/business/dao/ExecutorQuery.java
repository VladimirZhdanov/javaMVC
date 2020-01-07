package com.foxminded.business.dao;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import static com.foxminded.business.constants.Constants.NULL_WAS_PASSED;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Repository
public class ExecutorQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorQuery.class);

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ExecutorQuery(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void executeQuery(String fileName) {
        LOGGER.debug("Execute query from file name: {}", fileName);
        if (fileName == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        Resource resource = new ClassPathResource(fileName);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }
}
