package com.foxminded.business.dao.postgresql;

import com.foxminded.business.dao.PropertyLoader;
import com.foxminded.business.dao.layers.ClassRoomDAO;
import com.foxminded.business.dao.mappers.ClassRoomMapper;
import com.foxminded.business.model.ClassRoom;
import com.foxminded.business.exceptions.DAOException;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import static com.foxminded.business.constants.Constants.NULL_WAS_PASSED;

/**
 * ClassRoom DAO.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Repository
public class ClassRoomPostgreSQL implements ClassRoomDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomPostgreSQL.class);

    private PropertyLoader propertyLoader;
    private Properties properties;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertClassRoom;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ClassRoomPostgreSQL(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertClassRoom = new SimpleJdbcInsert(dataSource).withTableName("class_rooms").usingGeneratedKeyColumns("id");
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        properties = new Properties();
        propertyLoader = new PropertyLoader();
        init();
    }

    /**
     * Initialisation properties.
     */
    private void init() {
        propertyLoader.loadProperty(properties, "properties/queriesPostrgeSQL.properties");
    }

    /**
     * Gets class room by id.
     *
     * @param id - id
     * @return - ClassRoom
     * @throws DAOException - DAOException
     */
    @Override
    public ClassRoom getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        String sql = properties.getProperty("getClassRoomById");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ClassRoomMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find class room by passed id: {}", id);
            throw new DAOException("Can't find class room by passed id", e);
        }
    }


    /**
     * Gets class room by name.
     *
     * @param name - name
     * @return - ClassRoom
     * @throws DAOException - DAOException
     */
    @Override
    public ClassRoom getByName(String name) throws DAOException {
        LOGGER.debug("Invoke method getByName({})", name);
        if (name == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("getClassRoomByName");

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, new ClassRoomMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Can't find class room by passed name: {}", name);
            throw new DAOException("Can't find class room by passed name", e);
        }
    }

    /**
     * Gets all class rooms.
     *
     * @return - List<ClassRoom>
     */
    @Override
    public List<ClassRoom> getAll() {
        String sql = properties.getProperty("getAllClassRooms");
        return jdbcTemplate.query(sql, new ClassRoomMapper());
    }

    /**
     * Inserts class room to the table.
     *
     * @param classRoom - class room
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean insert(ClassRoom classRoom) throws DAOException {
        LOGGER.debug("Insert classRoom: {}", classRoom);
        if (classRoom == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("insertClassRoom");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", classRoom.getId())
                .addValue("name", classRoom.getName())
                .addValue("capacity", classRoom.getCapacity());

        try {
            int result = namedParameterJdbcTemplate.update(sql, namedParameters);
            return result > 0;
        } catch (DuplicateKeyException e) {
            LOGGER.warn("Unique index or primary key violation");
            throw new DAOException("Unique index or primary key violation", e);
        }
    }

    /**
     * Updates a classRoom.
     *
     * @param classRoom - new capacity
     * @return - boolean
     * @throws DAOException - DAOException
     */
    @Override
    public boolean update(ClassRoom classRoom) throws DAOException {
        LOGGER.debug("Update classRoom: {}", classRoom);
        if (classRoom == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }

        String sql = properties.getProperty("updateClassRoom");
        boolean result = jdbcTemplate.update(sql, classRoom.getName(), classRoom.getCapacity(), classRoom.getId()) > 0;
        if (!result) {
            LOGGER.warn("The class room does not exist: {}", classRoom);
            throw new DAOException("The class room does not exist");
        }
        return true;
    }

    /**
     * Deletes a record from the table.
     *
     * @param classRoom - classRoom
     * @return - ClassRoom
     * @throws DAOException - DAOException
     */
    @Override
    public ClassRoom delete(ClassRoom classRoom) throws DAOException {
        LOGGER.debug("Delete classRoom: {}", classRoom);
        if (classRoom == null) {
            LOGGER.error(NULL_WAS_PASSED);
            throw new IllegalArgumentException(NULL_WAS_PASSED);
        }
        String sql = properties.getProperty("deleteClassRoom");
        boolean result = jdbcTemplate.update(sql, classRoom.getId()) > 0;
        if (!result) {
            LOGGER.warn("The class room does not exist: {}", classRoom);
            throw new DAOException("The class room does not exist");
        }
        return classRoom;
    }
}