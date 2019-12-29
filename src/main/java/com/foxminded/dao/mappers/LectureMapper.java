package com.foxminded.dao.mappers;

import com.foxminded.domain.ClassRoom;
import com.foxminded.domain.Course;
import com.foxminded.domain.Group;
import com.foxminded.domain.Lecture;
import com.foxminded.domain.Teacher;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class LectureMapper implements RowMapper<Lecture> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LectureMapper.class);

    public Lecture mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug("ResultSet: {}, rowNum: {}", rs, rowNum);
        Lecture lecture = new Lecture();
        lecture.setId(rs.getInt("id"));
        lecture.setName(rs.getString("name"));
        lecture.setDate(rs.getTimestamp("date").toLocalDateTime());
        lecture.setClassRoom(new ClassRoom(rs.getInt("class_room_id")));
        lecture.setGroup(new Group(rs.getInt("group_id")));
        lecture.setTeacher(new Teacher(rs.getInt("teacher_id")));
        lecture.setCourse(new Course((rs.getInt("course_id"))));
        LOGGER.debug("Returned lecture: {}", lecture);
        return lecture;
    }
}
