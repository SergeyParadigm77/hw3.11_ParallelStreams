package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    ArrayList<Student> findByAgeBetweenOrderByAge(int min, int max);
    Collection<Student> findAllByFacultyId(Long id);
    @Query(value = "select count(*) from student", nativeQuery = true)
    int getCountOfStudents();
    @Query(value = "select avg(age) from student", nativeQuery = true)
    int getAverageAge();
    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> getFiveStudentsOrderedById();


}
