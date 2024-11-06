package com.example.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // สร้างนักศึกษาใหม่
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // ดึงข้อมูลนักศึกษาทั้งหมด
    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // ดึงข้อมูลนักศึกษาตาม ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // อัปเดตข้อมูลนักศึกษาตาม ID
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setUserName(studentDetails.getUserName());
                    student.setType(studentDetails.getType());
                    student.setEngname(studentDetails.getEngname());
                    student.setEmail(studentDetails.getEmail());
                    student.setFaculty(studentDetails.getFaculty());
                    Student updatedStudent = studentRepository.save(student);
                    return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ลบนักศึกษาตาม ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
