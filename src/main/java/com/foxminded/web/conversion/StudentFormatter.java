package com.foxminded.web.conversion;

import com.foxminded.business.exceptions.DAOException;
import com.foxminded.business.model.Student;
import com.foxminded.business.service.layers.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.util.Locale;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class StudentFormatter implements Formatter<Student> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentFormatter.class);

    @Autowired
    private StudentService studentService;


    public StudentFormatter() {
        super();
    }

    public Student parse(final String text, final Locale locale) {
        final int studentId = Integer.parseInt(text);
        try {
            return this.studentService.getById(studentId);
        } catch (DAOException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return null;
    }


    public String print(final Student object, final Locale locale) {
        return (object != null ? String.valueOf(object.getId()) : "");
    }
}
