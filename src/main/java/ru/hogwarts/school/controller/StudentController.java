package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;



@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    Collection<Student> getAll() {
        return studentService.getAll();
    }
    @GetMapping("/age")
    Collection<Student> getAllByAge(@RequestParam int age) {
        return studentService.getAllByAge(age);
    }
    @GetMapping("/{id}")
    public Student getStudentInfo(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        studentService.createStudent(student);
        return student;
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }
    @DeleteMapping({"/id"})
    public ResponseEntity deleteStudent(@RequestParam Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/findByAgeBetween")
    public Collection<Student> findByAgeBetween (@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetweenOrderByAge(min, max);
    }
    @GetMapping("/getFacultyByStudentId")
    public Faculty getFacultyByStudentId(@RequestParam Long id) {
        return studentService.getFacultyByStudentId(id);
    }
    @GetMapping("/findAllByFacultyId")
    public Collection<Student> findAllByFacultyId(@RequestParam Long id) {
        return studentService.findAllByFacultyId(id);
    }
    @GetMapping("/count")
    public int getCountOfStudents() {
        return studentService.getCountOfStudents();
    }
    @GetMapping("/average-age")
    public int getAverageAge() {
        return studentService.getAverageAge();
    }
    @GetMapping("/get-five-students-ordered-by-id")
    public Collection<Student> getFiveStudentsOrderedById() {
        return studentService.getFiveStudentsOrderedById();
    }

}
