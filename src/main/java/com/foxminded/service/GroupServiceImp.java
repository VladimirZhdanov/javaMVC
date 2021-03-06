package com.foxminded.service;

import com.foxminded.dao.layers.GroupDAO;
import com.foxminded.dao.layers.StudentDAO;
import com.foxminded.exceptions.DAOException;
import com.foxminded.model.Group;
import com.foxminded.model.Student;
import com.foxminded.service.layers.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Component
@Service("groupService")
public class GroupServiceImp implements GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImp.class);

    private StudentDAO studentDAO;
    private GroupDAO groupDAO;

    @Autowired
    public GroupServiceImp(StudentDAO studentDAO, GroupDAO groupDAO) {
        this.studentDAO = studentDAO;
        this.groupDAO = groupDAO;
    }

    /**
     * Gets group by id.
     *
     * @param id - id
     * @return - Course
     */
    @Override
    @Transactional(readOnly = true)
    public Group getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        return groupDAO.getById(id);
    }

    /**
     * Gets group by name.
     *
     * @param name - name
     * @return - Course
     * @throws DAOException - DAOException
     */
    @Override
    public Group getByName(String name) throws DAOException {
        return groupDAO.getByName(name);
    }

    /**
     * Gets all groups.
     *
     * @return - List<Group>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Group> getAll() {
        return groupDAO.getAll();

    }

    /**
     * Gets all students related to the group.
     *
     * @param groupName - groupName
     * @return - List<Student>
     */
    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudents(String groupName) throws DAOException {
        LOGGER.debug("Invoke method getStudents({})", groupName);
        Group group = groupDAO.getByName(groupName);
        return studentDAO.getStudentsByGroup(group);
    }

    /**
     * Inserts a Group to the table.
     *
     * @param group - group
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean insert(Group group) throws DAOException {
        LOGGER.debug("Invoke method insert({})", group);
        return groupDAO.insert(group);
    }

    /**
     * Updates a group.
     *
     * @param group - group
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean update(Group group) throws DAOException {
        LOGGER.debug("Invoke method update({})", group);
        return groupDAO.update(group);
    }

    /**
     * Deletes a record.
     *
     * @param groupName - groupName
     * @return - Group
     */
    @Override
    @Transactional
    public Group delete(String groupName) throws DAOException {
        LOGGER.debug("Invoke method delete({})", groupName);
        Group group = groupDAO.getByName(groupName);
        return groupDAO.delete(group);
    }
}
