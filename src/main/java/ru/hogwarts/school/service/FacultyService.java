package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final static Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("createFaculty method was invoked");
        return facultyRepository.save(faculty);
    }

    public Faculty findFacultyById(Long id)
    {    logger.info("findFacultyById method was invoked");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("editFaculty method was invoked");
        return facultyRepository.save(faculty);
    }
    public void deleteFaculty(Long id) {
        logger.info("deleteFaculty method was invoked");
        facultyRepository.deleteById(id);
    }
    public Collection<Faculty> getAll() {
        logger.info("getAll method was invoked");
        return facultyRepository.findAll();
    }
    public Collection<Faculty> getAllByColor(String color) {
        logger.info("getAllByColor method was invoked");
        return getAll().stream().filter(it -> it.getColor().equals(color)).collect(Collectors.toList());
    }
    public Faculty findByFacultyName(String name) {
        logger.info("findByFacultyName method was invoked");
        return facultyRepository.findByNameIgnoreCase(name);
    }
    public String getLongestFacultyName() {
        logger.info("getLongestFacultyName method was invoked");
        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }
}

