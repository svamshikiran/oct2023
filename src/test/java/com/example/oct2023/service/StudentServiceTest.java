package com.example.oct2023.service;

import com.example.oct2023.model.Student;
import com.example.oct2023.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    private static Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setRollno(1);
        student.setName("TESTNAME");
        student.setCity("TESTCITY");
    }

    @Test
    void getAllStudents() {
    }

    @Test
    void getStudentByRollno() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        Student actualStudent = studentService.getStudentByRollno(1);
        assertNotNull(actualStudent);
        assertEquals(student.getRollno(), actualStudent.getRollno());
    }

    @Test
    void getStudentByRollnoException() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());
        Student actualStudent = studentService.getStudentByRollno(1);
        assertNotNull(actualStudent);
        assertEquals(0, actualStudent.getRollno());
    }

    @Test
    void getStudentByCity() {
    }

    @Test
    void upsert() {
    }

    @Test
    void upsertStudents() {
    }

    @Test
    void readFileContents() {
    }

    @Test
    void writeStudentsToCsv() {
    }

    @Test
    void deleteStudent() {
    }
}