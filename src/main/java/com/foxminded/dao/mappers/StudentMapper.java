package com.foxminded.dao.mappers;

import com.foxminded.model.Group;
import com.foxminded.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class StudentMapper implements RowMapper<Student> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentMapper.class);

    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug("ResultSet: {}, rowNum: {}", rs, rowNum);
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setGroup(new Group(rs.getInt("group_id")));
        LOGGER.debug("Returned student: {}", student);
        return student;
    }
}