package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);


    public Student createStudent (Student student) {
        logger.info("createStudent method was invoked");
        return studentRepository.save(student);
    }
    public Student findStudentById(Long id)
    {
        logger.info("findStudentById method was invoked");
        return studentRepository.findById(id).orElse(null);
    }
    public Student editStudent(Student student) {
        logger.info("editStudent method was invoked");
        return studentRepository.save(student);
    }
    public void deleteStudent(Long id) {
        logger.info("deleteStudent method was invoked");
        studentRepository.deleteById(id);
    }
    public Collection<Student> getAll() {
        logger.info("getAll method was invoked");
        return studentRepository.findAll();
    }
    public Collection<Student> getAllByAge(int age) {
        logger.info("getAllByAge method was invoked");
        return getAll().stream().filter(it -> it.getAge() == age).collect(Collectors.toList());
    }
    public ArrayList<Student> findByAgeBetweenOrderByAge(int min, int max) {
        logger.info("findByAgeBetweenOrderByAge method was invoked");
        return studentRepository.findByAgeBetweenOrderByAge(min, max);
    }

    public Faculty getFacultyByStudentId(Long id) {
        logger.info("getFacultyByStudentId method was invoked");
        return studentRepository.findById(id).map(Student::getFaculty).orElse(null);
    }
    public Collection<Student> findAllByFacultyId(Long id)
    {
        logger.info("findAllByFacultyId method was invoked");
        return studentRepository.findAllByFacultyId(id);
    }

    public int getCountOfStudents() {
        logger.info("getCountOfStudents method was invoked");
        return studentRepository.getCountOfStudents();
    }
    public int getAverageAge() {
        logger.info("getAverageAge method was invoked");
        return studentRepository.getAverageAge();
    }
    public List<Student> getFiveStudentsOrderedById() {
        logger.info("getFiveStudentsOrderedById method was invoked");
        return studentRepository.getFiveStudentsOrderedById();
    }
    public Collection<String> findAllWithNameStartsWithLetterA() {
        logger.info("findAllWithNameStartsWithLetterA method was invoked");
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(it -> it.charAt(0) == 'A')
                .sorted()
                .collect(Collectors.toList());
    }
    public float averageAgeViaStreams() {
        logger.info("averageAgeOfAll method was invoked");
        return (float) studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }
    public void threads() {
        List<Student> students = studentRepository.findAll();
        printStudentName(students.get(0));
        printStudentName(students.get(1));

        new Thread(()-> {
            printStudentName(students.get(2));
            printStudentName(students.get(3));
        }).start();

        new Thread(()-> {
            printStudentName(students.get(4));
            printStudentName(students.get(5));
        }).start();
    }
    private void printStudentName(Student student){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(student.getName());
    }
    public void synchronizedThreads() {
        List<Student> students = studentRepository.findAll();
        printStudentNameSynchronized(students.get(0));
        printStudentNameSynchronized(students.get(1));

        new Thread(()-> {
            printStudentNameSynchronized(students.get(2));
            printStudentNameSynchronized(students.get(3));
        }).start();

        new Thread(()-> {
            printStudentNameSynchronized(students.get(4));
            printStudentNameSynchronized(students.get(5));
        }).start();

    }
    Object object = new Object();
    private void printStudentNameSynchronized(Student student){
        //денег хватает?
        //если хватает, то уменьшаем баланс
        //если не хватает, то ничего не делаем
        synchronized(object) {
        printStudentName(student);
    }
    }
    }