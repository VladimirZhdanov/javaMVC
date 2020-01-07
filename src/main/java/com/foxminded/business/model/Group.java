package com.foxminded.business.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Group class.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class Group {
    private int id;
    private String name;
    private List<Student> students;

    public Group() {
    }

    public Group(int id) {
        this.id = id;
    }

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, List<Student> students) {
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
        Group group = (Group) o;
        return id == group.id
                && Objects.equals(name, group.name)
                && Objects.equals(students, group.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students);
    }

    @Override
    public String toString() {
        return "Group{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", students=" + students
                + '}';
    }
}
