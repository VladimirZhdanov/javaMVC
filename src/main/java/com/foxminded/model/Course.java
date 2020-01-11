package com.foxminded.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Course class.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class Course {
    private int id;
    private String name;
    private List<Student> students;

    public Course() {
    }

    public Course(int id) {
        this.id = id;
    }

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return id == course.id
                && Objects.equals(name, course.name)
                && Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students);
    }

    @Override
    public String toString() {
        return "Course{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", students=" + students
                + '}';
    }
}
