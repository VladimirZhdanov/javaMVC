package com.foxminded.business.dao.mappers;

import com.foxminded.business.model.StudentCourse;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class StudentCourseMapper implements RowMapper<StudentCourse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCourseMapper.class);

    public StudentCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug("ResultSet: {}, rowNum: {}", rs, rowNum);
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(rs.getInt("student_id"));
        studentCourse.setCourseId(rs.getInt("course_id"));
        LOGGER.debug("Returned studentCourse: {}", studentCourse);
        return studentCourse;
    }
}
