package com.example.oct2023.repository;

import com.example.oct2023.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    public List<Student> findByCity(String city);
    //select * from student where city=?

    public List<Student> findByName(String name);
    //select * from student where name=?

    //select * from student where name=? and city = ?
    public List<Student> findByNameAndCity(String name, String city);

    //select * from student where name=? or city = ?
    public List<Student> findByNameOrCity(String name, String city);

}





/*

    public List<Student> findByCity(String city);

    public List<Student> findByName(String name);

    //select * from student where name=? and city = ?
    public List<Student> findByNameAndCity(String name, String city);

    //select * from student where name=? or city = ?
    public List<Student> findByNameOrCity(String name, String city);

    @Query(value = "select * from student")
    public List<Student> findByQuery();
    */
















/*
    public List<Student> findByName(String name);

    public List<Student> findByCity(String name);
    //select * from student where name = ? and city = ?
    public List<Student> findByNameAndCity(String name, String City);

    //select * from student where name = ? or city = ?
    public List<Student> findByNameOrCity(String name, String City);*/
