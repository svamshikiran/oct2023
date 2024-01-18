package com.example.oct2023.dto;

import com.example.oct2023.model.Student;

import java.util.List;

public class StudentDto {

    private int count;
    private List<Student> studentList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
