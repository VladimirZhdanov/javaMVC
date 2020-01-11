package com.foxminded.model;

import java.util.Objects;

/**
 * ClassRoom class.
 *
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class ClassRoom {
    private int id;
    private String name;
    private int capacity;

    public ClassRoom() {
    }

    public ClassRoom(int id) {
        this.id = id;
    }

    public ClassRoom(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public ClassRoom(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassRoom classRoom = (ClassRoom) o;
        return id == classRoom.id && capacity == classRoom.capacity && Objects.equals(name, classRoom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity);
    }

    @Override
    public String toString() {
        return "ClassRoom{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", capacity=" + capacity
                + '}';
    }
}
