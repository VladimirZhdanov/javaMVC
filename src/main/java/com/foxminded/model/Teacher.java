package com.foxminded.model;

import java.util.Objects;

/**
 * Teacher class.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class Teacher {
    private int id;
    private String firstName;
    private String lastName;
    private Course course;
    private Department department;

    public Teacher() {
    }

    public Teacher(int id) {
        this.id = id;
    }

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Teacher(String firstName, String lastName, Course course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }

    public Teacher(String firstName, String lastName, Course course, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.department = department;
    }

    public Teacher(String firstName, String lastName, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Course getCourse() {
        return course;
    }

    public Department getDepartment() {
        return department;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Teacher teacher = (Teacher) o;
        return id == teacher.id
                && Objects.equals(firstName, teacher.firstName)
                && Objects.equals(lastName, teacher.lastName)
                && Objects.equals(course, teacher.course)
                && Objects.equals(department, teacher.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, course, department);
    }

    @Override
    public String toString() {
        return "Teacher{"
                + "id=" + id
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", course=" + course
                + ", department=" + department
                + '}';
    }
}
