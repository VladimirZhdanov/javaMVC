package com.foxminded.business.service;

import com.foxminded.business.dao.layers.ClassRoomDAO;
import com.foxminded.business.exceptions.DAOException;
import com.foxminded.business.model.ClassRoom;
import com.foxminded.business.service.layers.ClassRoomService;
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
@Service("classRoomService")
public class ClassRoomServiceImp implements ClassRoomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomServiceImp.class);

    private ClassRoomDAO classRoomDAO;

    @Autowired
    public ClassRoomServiceImp(ClassRoomDAO classRoomDAO) {
        this.classRoomDAO = classRoomDAO;
    }


    /**
     * Gets class room by id.
     *
     * @param id - id
     * @return - ClassRoom
     */
    @Override
    @Transactional(readOnly = true)
    public ClassRoom getById(int id) throws DAOException {
        LOGGER.debug("Invoke method getById({})", id);
        return classRoomDAO.getById(id);
    }

    /**
     * Gets all class rooms.
     *
     * @return - List<ClassRoom>
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClassRoom> getAll() {
        return classRoomDAO.getAll();
    }

    /**
     * Inserts class room to the table.
     *
     * @param classRoom - class room
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean insert(ClassRoom classRoom) throws DAOException {
        LOGGER.debug("Invoke method insert({})", classRoom);
        return classRoomDAO.insert(classRoom);
    }

    /**
     * Updates a classRoom.
     *
     * @param classRoom - new capacity
     * @return - boolean
     */
    @Override
    @Transactional
    public boolean update(ClassRoom classRoom) throws DAOException {
        LOGGER.debug("Invoke method update({})", classRoom);
        return classRoomDAO.update(classRoom);
    }

    /**
     * Deletes a record.
     *
     * @param classRoomId - classRoomId
     * @return - boolean
     */
    @Override
    @Transactional
    public ClassRoom delete(int classRoomId) throws DAOException {
        LOGGER.debug("Invoke method delete({})", classRoomId);
        ClassRoom classRoom = classRoomDAO.getById(classRoomId);
        return classRoomDAO.delete(classRoom);
    }
}
