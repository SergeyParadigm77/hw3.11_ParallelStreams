package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.exaption.StudentIsNotFound;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final AvatarMapper avatarMapper;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository, AvatarMapper avatarMapper) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarMapper = avatarMapper;
    }
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private final static Logger logger = LoggerFactory.getLogger(AvatarService.class);
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("UploadAvatar method was invoked");
        Student student = studentRepository.findById(studentId).orElseThrow(StudentIsNotFound::new);

        Path path = uploadTODisk(student, avatarFile);
        uploadToTheDatabase(path, student, avatarFile);
    }

    private Path uploadTODisk(Student student, MultipartFile avatarFile) throws IOException {
        Path filePath = Path.of(avatarsDir, student.getName() + "StudentAvatar-" + student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os,1024);
        ) {
            bis.transferTo(bos);
        }
        return filePath;
    }
    private String getExtensions (String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
    private void uploadToTheDatabase(Path path, Student student, MultipartFile avatarFile) throws IOException {
        Avatar avatar= findAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }
    private Avatar findAvatar(Long studentId) {
        Avatar avatar = avatarRepository.findByStudent_Id(studentId);
        return avatar == null ? new Avatar() : avatar;
    }
    public Avatar findById(Long studentId) {
        logger.info("findById method was invoked");
        Avatar avatar = avatarRepository.findByStudent_Id(studentId);
        return avatar;
    }
    public Collection<AvatarDTO> getAvatars(int pageNumber, int pageSize) {
        logger.info("getAvatars method was invoked");
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent()
                .stream()
                .map(it -> avatarMapper.mapToDTO(it))
                .collect(Collectors.toList());
    }
 }
