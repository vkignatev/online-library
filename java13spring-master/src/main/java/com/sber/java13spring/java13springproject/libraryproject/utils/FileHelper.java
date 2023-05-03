package com.sber.java13spring.java13springproject.libraryproject.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import static com.sber.java13spring.java13springproject.libraryproject.constants.FileDirectoriesConstants.BOOKS_UPLOAD_DIRECTORY;

@Slf4j
public class FileHelper {
    private FileHelper() {
    }
    
    public static String createFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String resultFileName = "";
        try {
            Path path = Paths.get(BOOKS_UPLOAD_DIRECTORY + "/" + fileName).toAbsolutePath().normalize();
            if (!path.toFile().exists()) {
                Files.createDirectories(path);
            }
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            //Будет сохранен полный путь до файла от самого корня
            resultFileName = path.toString();
            //Будет сохранен путь вида /files/books/{имя_книги}
            //resultFileName = BOOKS_UPLOAD_DIRECTORY + "/" + fileName;
        }
        catch (IOException ex) {
            log.error("FileHelper#createFile(): {}", ex.getMessage());
        }
        return resultFileName;
    }
    
    public static void deleteFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);
        }
        catch (IOException ex) {
            log.error("FileHelper#deleteFile(): {}", ex.getMessage());
        }
    }
}
