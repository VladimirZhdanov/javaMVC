package com.foxminded.service.layers;

import com.foxminded.model.Group;
import com.foxminded.model.Student;
import com.foxminded.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface GroupService {

    /**
     * Gets group by id.
     *
     * @param id - id
     * @return - Course
     * @throws DAOException - DAOException
     */
    Group getById(int id) throws DAOException;

    /**
     * Gets all groups.
     *
     * @return - List<Group>
     */
    List<Group> getAll();

    /**
     * Gets all students related to the group.
     *
     * @param groupName - groupName
     * @return - List<Student>
     * @throws DAOException - DAOException
     */
    List<Student> getStudents(String groupName) throws DAOException;

    /**
     * Inserts a Group to the table.
     *
     * @param group - group
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean insert(Group group) throws DAOException;

    /**
     * Updates a group.
     *
     * @param group - group
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean update(Group group) throws DAOException;

    /**
     * Deletes a record.
     *
     * @param groupName - groupName
     * @return - Group
     * @throws DAOException - DAOException
     */
    Group delete(String groupName) throws DAOException;
}
