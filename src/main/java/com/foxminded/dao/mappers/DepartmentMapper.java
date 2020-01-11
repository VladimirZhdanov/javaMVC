package com.foxminded.dao.mappers;

import com.foxminded.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class DepartmentMapper implements RowMapper<Department> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentMapper.class);

    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug("ResultSet: {}, rowNum: {}", rs, rowNum);
        Department department = new Department();
        department.setId(rs.getInt("id"));
        department.setName(rs.getString("name"));
        LOGGER.debug("Returned department: {}", department);
        return department;
    }
}