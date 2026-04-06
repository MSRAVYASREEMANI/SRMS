-- 1. Create Admin Table and insert Default Admin
CREATE TABLE admin (
    admin_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR2(50) UNIQUE NOT NULL,
    password VARCHAR2(255) NOT NULL
);

INSERT INTO admin (username, password) VALUES ('admin', 'admin123');

-- 2. Create Student Table
CREATE TABLE student (
    student_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_name VARCHAR2(100) NOT NULL,
    roll_no VARCHAR2(50) UNIQUE NOT NULL,
    password VARCHAR2(255) NOT NULL, -- Required for Student Login
    email VARCHAR2(100),
    phone VARCHAR2(20),
    semester NUMBER
);

-- 3. Create Subject Table
CREATE TABLE subject (
    subject_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    subject_name VARCHAR2(100) NOT NULL,
    subject_code VARCHAR2(50) UNIQUE NOT NULL,
    credits NUMBER
);

-- 4. Create Result Table
CREATE TABLE result (
    result_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id NUMBER NOT NULL,
    subject_id NUMBER NOT NULL,
    marks NUMBER(5,2),
    total_marks NUMBER(5,2),
    grade VARCHAR2(5),
    semester NUMBER,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
    CONSTRAINT fk_subject FOREIGN KEY (subject_id) REFERENCES subject(subject_id) ON DELETE CASCADE
);

COMMIT;
