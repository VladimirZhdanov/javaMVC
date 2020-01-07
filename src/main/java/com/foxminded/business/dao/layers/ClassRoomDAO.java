package com.foxminded.business.dao.layers;

import com.foxminded.business.model.ClassRoom;
import com.foxminded.business.exceptions.DAOException;
import java.util.List;

/**
 * ClassRoom DAO.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface ClassRoomDAO {

    /**
     * Gets class room by id.
     *
     * @param id - id
     * @return - ClassRoom
     * @throws DAOException - DAOException
     */
    ClassRoom getById(int id) throws DAOException;

    /**
     * Gets class room by name.
     *
     * @param name - name
     * @return - ClassRoom
     * @throws DAOException - DAOException
     */
    ClassRoom getByName(String name) throws DAOException;

    /**
     * Gets all class rooms.
     *
     * @return - List<ClassRoom>
     */
    List<ClassRoom> getAll();

    /**
     * Inserts class room to the table.
     *
     * @param classRoom - class room
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean insert(ClassRoom classRoom) throws DAOException;

    /**
     * Updates a classRoom.
     *
     * @param classRoom - new capacity
     * @return - boolean
     * @throws DAOException - DAOException
     */
    boolean update(ClassRoom classRoom) throws DAOException;

    /**
     * Deletes a record from the table.
     *
     * @param classRoom - classRoom
     * @return - ClassRoom
     * @throws DAOException - DAOException
     */
    ClassRoom delete(ClassRoom classRoom) throws DAOException;
}
