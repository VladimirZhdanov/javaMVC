package com.foxminded.business.service.layers;

import com.foxminded.business.exceptions.DAOException;
import com.foxminded.business.model.ClassRoom;

import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public interface ClassRoomService {

    /**
     * Gets class room by id.
     *
     * @param id - id
     * @return - ClassRoom
     * @throws DAOException - DAOException
     */
    ClassRoom getById(int id) throws DAOException;

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
     * Deletes a record.
     *
     * @param classRoomId - classRoomId
     * @return - ClassRoom
     * @throws DAOException - DAOException
     */
    ClassRoom delete(int classRoomId) throws DAOException;
}
