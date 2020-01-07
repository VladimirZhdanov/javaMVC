CREATE table if not exists groups (
                                    id serial primary key not null,
                                    name varchar(250),
                                    UNIQUE (name)
);

CREATE table if not exists students (
                                      id serial primary key not null,
                                      first_name varchar(250),
                                      last_name varchar(250),
                                      group_id int not null,
                                      FOREIGN KEY (group_id) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                      UNIQUE (first_name, last_name)
);

CREATE table if not exists courses (
                                     id serial primary key not null,
                                     name varchar(250),
                                     UNIQUE (name)
);

CREATE table if not exists student_course (
                                            student_id int NOT NULL,
                                            course_id int NOT NULL,
                                            FOREIGN KEY (student_id) REFERENCES students(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                            FOREIGN KEY (course_id) REFERENCES courses(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                            UNIQUE (student_id, course_id)
);

CREATE table if not exists departments (
                                     id serial primary key not null,
                                     name varchar(250),
                                     UNIQUE (name)
);

CREATE table if not exists teachers (
                                      id serial primary key not null,
                                      first_name varchar(250),
                                      last_name varchar(250),
                                      course_id int,
                                      department_id int,
                                      FOREIGN KEY (course_id) REFERENCES courses(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                      FOREIGN KEY (department_id) REFERENCES departments(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                      UNIQUE (first_name, last_name)
);

CREATE table if not exists class_rooms (
                                        id int primary key not null,
                                        name varchar(250),
                                        capacity int,
                                        UNIQUE (name)
);

CREATE table if not exists lectures (
                                      id serial primary key not null,
                                      name varchar(250),
                                      date timestamp,
                                      class_room_id int,
                                      teacher_id int,
                                      group_id int,
                                      course_id int,
                                      FOREIGN KEY (class_room_id) REFERENCES class_rooms(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                      FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                      FOREIGN KEY (group_id) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                      FOREIGN KEY (course_id) REFERENCES courses(id) ON UPDATE CASCADE ON DELETE CASCADE,
                                      UNIQUE (name)
);