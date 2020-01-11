package com.foxminded.dao.mappers;

import com.foxminded.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class CourseMapper implements RowMapper<Course> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseMapper.class);

    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug("ResultSet: {}, rowNum: {}", rs, rowNum);
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setName(rs.getString("name"));
        LOGGER.debug("Returned course: {}", course);
        return course;
    }
}
