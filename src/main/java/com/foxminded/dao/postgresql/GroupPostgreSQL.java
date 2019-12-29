package com.foxminded.dao.postgresql;

import com.foxminded.dao.PropertyLoader;
import com.foxminded.dao.layers.GroupDAO;
import com.foxminded.dao.mappers.GroupMapper;
import com.foxminded.domain.Group;
import com.foxminded.exceptions.DAOException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import static com.foxminded.constants.Constants.NULL_WAS_PASSED;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Repository
public class GroupPostgreSQL implements GroupDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupPostgreSQL.class);

    private PropertyLoader propertyLoader;
    private Properties properties;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupPostgreSQL(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        properties = new Properties();
        propertyLoader = new PropertyLoader();
        init();
    }

    /**
     * Initialisation properties.
     */
    private void init() {
        propertyLoader.loadProperty(properties, "queriesPostrgeSQL.properties");
    }

    /**
     * Gets group by id.
     *
     * @param id - id
     * @return - group
     * @throws DAOException - DAOException
     */
    @Override
    public Group getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        String sql = properties.getProperty("getGroupById");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new GroupMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find group by passed id: {}", id);
            throw new DAOException("Can't find group by passed id", e);
        }
    }

    /**
     * Gets group by name.
     *
     * @param name - name
     * @return - Group
     * @throws DAOException - DAOException
     */
    @Override
    public Group getByName(String name) throws DAOException {
        LOGGER.debug("Invoke method getByName({})", name);
        if (name == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getGroupByName");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, new GroupMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find group by passed name: {}", name);
            throw new DAOException("Can't find group by passed name", e);
        }
    }


    /**
     * Gets all groups.
     *
     * @return - List<Group>
     */
    @Override
    public List<Group> getAll() {
        String sql = properties.getProperty("getAllGroups");
        return jdbcTemplate.query(sql, new GroupMapper());
    }

    /**
     * Inserts a Group to the table.
     *
     * @param group - group
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean insert(Group group) throws DAOException {
        LOGGER.debug("Insert group: {}", group);
        if (group == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("insertGroup");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = 0;

        try {
            result = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(sql, new String[]{"id"});
                ps.setString(1, group.getName());
                return ps;
            }, keyHolder);

            group.setId((int) (keyHolder.getKey()));
        } catch (DuplicateKeyException e) {
            LOGGER.warn("Unique index or primary key violation");
            throw new DAOException("Unique index or primary key violation", e);
        }
        return result > 0;
    }

    /**
     * Updates a group.
     *
     * @param group - group
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean update(Group group) throws DAOException {
        LOGGER.debug("Update group: {}", group);
        if (group == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("updateGroup");
        boolean result = jdbcTemplate.update(sql, group.getName(), group.getId()) > 0;
        if (!result) {
            LOGGER.warn("The group does not exist: {}", group);
            throw new DAOException("The group does not exist");
        }
        return true;
    }

    /**
     * Deletes a record from the table by group.
     *
     * @param group - group
     * @return - Group
     * @throws DAOException - DAOException
     */
    @Override
    public Group delete(Group group) throws DAOException {
        LOGGER.debug("Delete group: {}", group);
        if (group == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("deleteGroup");
        boolean result = jdbcTemplate.update(sql, group.getId()) > 0;
        if (!result) {
            LOGGER.warn("The group does not exist: {}", group);
            throw new DAOException("The group does not exist");
        }
        return group;
    }
}
