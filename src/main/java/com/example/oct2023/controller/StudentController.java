package com.example.oct2023.controller;

import com.example.oct2023.dto.StudentDto;
import com.example.oct2023.model.Student;
import com.example.oct2023.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/all")
    @CrossOrigin("http://localhost:2024")
    public List<Student> getAllStudents(){
        List<Student> studentList = studentService.getAllStudents();

        return studentList;
    }

    @GetMapping("/get/{rollno}")
    @Operation(description = "This method retrieves the student details based on the rollno" +
            "provided. If exists, it returns the 200(OK) response, else it returns" +
            "400(BAD_REQUEST) response")
    @ApiResponse(responseCode = "200", description = "SUCCESS RESPONSE")
    public ResponseEntity<Object> getStudentByRollno(@PathVariable("rollno") int rollno){
        System.out.println("INSIDE THE CONTROLLER - input: "+rollno);
        try {
            Student student = studentService.getStudentByRollno(rollno);
            if (student.getRollno() == 0) {
                return new ResponseEntity<>("STUDENT DOESN'T EXIST", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("EXCEPTION RAISED, PLEASE CONTACT ADMINISTRATOR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbycity/{city}")
    public List<Student> getStudentsByCity(@PathVariable("city") String city){
        return studentService.getStudentByCity(city);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addStudent(@RequestBody Student student){
        if(student.getRollno() <= 0){
            return new ResponseEntity<>("MANDATORY FIELDS MISSING", HttpStatus.BAD_REQUEST);
        }
        try{
            studentService.upsert(student);
            return new ResponseEntity<>("STUDENT ADDED SUCCESSFULLY", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("EXCEPTION RAISED, PLEASE CONTACT ADMINISTRATOR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addList")
    public ResponseEntity<Object> addStudents(@RequestBody List<Student> students){
        try{
            studentService.upsertStudents(students);
            return new ResponseEntity<>("STUDENT ADDED SUCCESSFULLY", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("EXCEPTION RAISED, PLEASE CONTACT ADMINISTRATOR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file ) {
        try {
            studentService.readFileContents(file.getInputStream());
            //FileUtils.forceDelete(file.getResource().getFile());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("File uploaded successfully.");
    }

    @GetMapping(path = "/download")
    public void getAllEmployeesInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"students.csv\"");
        studentService.writeStudentsToCsv(servletResponse.getWriter());
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student){
        try{
            studentService.upsert(student);
            return new ResponseEntity<>("STUDENT ADDED SUCCESSFULLY", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("EXCEPTION RAISED, PLEASE CONTACT ADMINISTRATOR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{rollno}")
    public void deleteStudent(@PathVariable("rollno") int rollno){
        studentService.deleteStudent(rollno);
    }

}
