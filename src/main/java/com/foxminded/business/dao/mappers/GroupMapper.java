package com.foxminded.business.dao.mappers;

import com.foxminded.business.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class GroupMapper implements RowMapper<Group> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupMapper.class);

    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug("ResultSet: {}, rowNum: {}", rs, rowNum);
        Group group = new Group();
        group.setId(rs.getInt("id"));
        group.setName(rs.getString("name"));
        LOGGER.debug("Returned group: {}", group);
        return group;
    }
}
