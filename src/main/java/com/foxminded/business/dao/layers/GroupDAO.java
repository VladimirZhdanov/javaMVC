package com.foxminded.business.dao.layers;

import com.foxminded.business.model.Group;
import com.foxminded.business.exceptions.DAOException;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface GroupDAO {

    /**
     * Gets group by id.
     *
     * @param id - id
     * @return - Course
     * @throws DAOException - DAOException
     */
    Group getById(int id) throws DAOException;

    /**
     * Gets group by name.
     *
     * @param name - name
     * @return - Group
     * @throws DAOException - DAOException
     */
    Group getByName(String name) throws DAOException;

    /**
     * Gets all groups.
     *
     * @return - List<Group>
     */
    List<Group> getAll();

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
     * Deletes a record from the table.
     *
     * @param group - group
     * @return - Group
     * @throws DAOException - DAOException
     */
    Group delete(Group group) throws DAOException;
}
