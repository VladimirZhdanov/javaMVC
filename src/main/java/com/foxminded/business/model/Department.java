package com.foxminded.business.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Department class.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class Department {
    private int id;
    private String name;
    private List<Teacher> teachers;

    public Department() {
    }

    public Department(int id) {
        this.id = id;
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Teacher> getTeachers() {
        if (teachers == null) {
            teachers = new ArrayList<>();
        }
        return teachers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Department that = (Department) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(teachers, that.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teachers);
    }

    @Override
    public String toString() {
        return "Department{" + "id=" + id + ", name='" + name + '\'' + ", teachers=" + teachers + '}';
    }
}
