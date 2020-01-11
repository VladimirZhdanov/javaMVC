package com.foxminded.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Student class.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private List<Course> courses;
    private Group group;

    public Student() {
    }

    public Student(int id) {
        this.id = id;
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
    }

    public Student(String firstName, String lastName, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public Student(String firstName, String lastName, Group group, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
        this.group = group;
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

    public Group getGroup() {
        return group;
    }

    public List<Course> getCourses() {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        return courses;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return id == student.id
                && Objects.equals(firstName, student.firstName)
                && Objects.equals(lastName, student.lastName)
                && Objects.equals(courses, student.courses)
                && Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, courses, group);
    }

    @Override
    public String toString() {
        return "Student{"
                + "id=" + id
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", courses=" + courses
                + ", group=" + group
                + '}';
    }
}
