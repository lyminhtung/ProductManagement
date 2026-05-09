package com.user.managements.Services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements IStorageService {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg");
    private static final float MAX_FILE_SIZE_MB = 5.0f;

    private final Path storageFolder;

    public ImageStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.storageFolder = Paths.get(uploadDir);
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory: " + uploadDir, e);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return extension != null && ALLOWED_EXTENSIONS.contains(extension.trim().toLowerCase());
    }

    @Override
    public String storageFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        if (!isImageFile(file)) {
            throw new RuntimeException("Only image files (png, jpg, jpeg) are allowed");
        }
        float fileSizeMb = file.getSize() / 1_000_000f;
        if (fileSizeMb > MAX_FILE_SIZE_MB) {
            throw new RuntimeException("File size must be <= " + MAX_FILE_SIZE_MB + " MB");
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generatedFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        Path destination = storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();

        if (!destination.getParent().equals(storageFolder.toAbsolutePath())) {
            throw new RuntimeException("Cannot store file outside of the designated upload directory");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        return generatedFileName;
    }

    @Override
    public Stream<Path> loadAll() {
        return Stream.empty();
    }

    @Override
    public byte[] readFileContent(String fileName) {
        return new byte[0];
    }

    @Override
    public void deleteAllFiles() {
        // TODO: implement if needed
    }
}

