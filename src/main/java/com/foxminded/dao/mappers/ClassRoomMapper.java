package com.foxminded.dao.mappers;

import com.foxminded.domain.ClassRoom;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class ClassRoomMapper implements RowMapper<ClassRoom> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomMapper.class);

    public ClassRoom mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug("ResultSet: {}, rowNum: {}", rs, rowNum);
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(rs.getInt("id"));
        classRoom.setName(rs.getString("name"));
        classRoom.setCapacity(rs.getInt("capacity"));
        LOGGER.debug("Returned class room: {}", classRoom);
        return classRoom;
    }
}
