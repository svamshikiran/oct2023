package com.example.oct2023.service;

import com.example.oct2023.kafka.KafkaProducerService;
import com.example.oct2023.model.Student;
import com.example.oct2023.repository.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class StudentService {

    @Autowired
    StudentRepository stuRepo;

    @Autowired
    KafkaProducerService kafka;

    public List<Student> getAllStudents(){
        return stuRepo.findAll();
        //select * from student;
    }

    public Student getStudentByRollno(int rollno){
        System.out.println("INSIDE THE SERVICE - input: "+rollno);
        Optional<Student> opStudent = stuRepo.findById(rollno);
        //select * from student where rollno=?
        if(opStudent.isPresent()){
            System.out.println("POSTIVE SCENARIO - INSIDE IF");
            return opStudent.get();
        }
        else {
            System.out.println("NEGATIVE SCENARIO - INSIDE ELSE");
            return new Student();
        }
    }

    public List<Student> getStudentByCity(String city){
        return stuRepo.findByCity(city);
        //select * from student where city=?
    }

    public void upsert(Student student) throws JsonProcessingException { //update or insert
        kafka.sendSimpleMessage(student);
        //stuRepo.save(student); // insert and update
        //insert into student values();

        //if the record is already present in db - UPDATE
        //if the record is not present in db - INSERT
    }

    public void upsertStudents(List<Student> students){ //update or insert
        stuRepo.saveAll(students); // insert and update
        //insert into student values();

        //if the record is already present in db - UPDATE
        //if the record is not present in db - INSERT
    }

    public void readFileContents(InputStream inputStream)throws Exception {
        CSVParser parser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT);

        List<CSVRecord> records = parser.getRecords();
        Random randomRollNumbers = new Random();
        List<Student> studentList = new ArrayList<>();
        //Enhanced For Loop
        for (CSVRecord record : records) {

            Student student = new Student();
            //student.setRollno(Integer.parseInt(record.get(0)));
            student.setRollno(randomRollNumbers.nextInt(400));
            student.setName(record.get(0));
            student.setCity(record.get(1));
            student.setCreatedby("FileUpload");
            student.setCreateddate(Date.valueOf(LocalDate.now()));

            //custom logic to be implemented here as per requirements

            studentList.add(student);
        }

        stuRepo.saveAll(studentList);

    }

    public void writeStudentsToCsv(Writer writer) {
        //List<Student> students = getAllStudents();
        List<Student> students = stuRepo.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("ROLLNO", "NAME", "CITY", "CREATED BY", "CREATED DATE", "MODIFIED BY", "MODIFIED DATE", "COURSE ID");
            for (Student student : students) { //Enhanced For Loop
                csvPrinter.printRecord(student.getRollno(),
                        student.getName(),
                        student.getCity(),
                        student.getCreatedby(),
                        student.getCreateddate(),
                        student.getModifiedby(),
                        student.getModifieddate(),
                        student.getCourseid());
            }
        } catch (IOException e) {
            System.out.println("EXPCEPTION RAISED");
        }
    }

    public void deleteStudent(int rollno){
        stuRepo.deleteById(rollno);
    }
}
