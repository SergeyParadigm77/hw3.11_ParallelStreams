package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService facultyService;
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @GetMapping("{id}") //http://localhost:8080/faculties/1
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id){
        Faculty faculty = facultyService.findFacultyById(id);
        if (faculty == null) {
            //return.404
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @PostMapping//POST http://localhost:8080/faculties
    public Faculty createFaculty (@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }
    @PutMapping//PUT http://localhost:8080/faculties
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }
    @DeleteMapping("{id}") //Delete http://localhost:8080/faculties/2
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFacultyById(id);
        if (faculty != null) {
            facultyService.deleteFaculty(id);
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAll() {
        return ResponseEntity.ok(facultyService.getAll());
    }
    @GetMapping("/color")
    Collection<Faculty> getAllByColor(@RequestParam String color) {
        return facultyService.getAllByColor(color);
    }
    @GetMapping("/name")
    public Faculty findByFacultyName(@RequestParam String name) {
        return facultyService.findByFacultyName(name);
    }
    @GetMapping("/getLongestFacultyName")
    public String getLongestFacultyName() {
        return facultyService.getLongestFacultyName();
    }
}
