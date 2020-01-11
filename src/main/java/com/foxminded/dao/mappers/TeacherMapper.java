package com.foxminded.dao.mappers;

import com.foxminded.model.Course;
import com.foxminded.model.Department;
import com.foxminded.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class TeacherMapper implements RowMapper<Teacher> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherMapper.class);

    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug("ResultSet: {}, rowNum: {}", rs, rowNum);
        Teacher teacher = new Teacher();
        teacher.setId(rs.getInt("id"));
        teacher.setFirstName(rs.getString("first_name"));
        teacher.setLastName(rs.getString("last_name"));
        teacher.setDepartment(new Department(rs.getInt("department_id")));
        teacher.setCourse(new Course(rs.getInt("course_id")));
        LOGGER.debug("Returned teacher: {}", teacher);
        return teacher;
    }
}
