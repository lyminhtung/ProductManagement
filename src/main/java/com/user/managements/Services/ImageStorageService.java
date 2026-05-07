package com.user.managements.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
@Service // để JAVA hiểu đây là 1 cái service
public class ImageStorageService implements IStorageService{
    private final Path storageFolder = Paths.get("uploads");
    public ImageStorageService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename()); // lấy đuôi của các hàm
        return Arrays.asList(new String[] {"png","jpg","jpeg"}).contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storageFile(MultipartFile file) {
        try{
            if (file.isEmpty()){
                throw new RuntimeException("Empty file");
            }
            if(!isImageFile(file)){
                throw new RuntimeException("You can only upload images");
            }

            float fileSize = file.getSize() / 1_000_000f;
            if(fileSize > 5.0f){
                throw new RuntimeException("File must be <= 5mb");
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if (!destinationFilePath.getParent()
                    .equals(this.storageFolder.toAbsolutePath())) {

                throw new RuntimeException(
                        "Cannot store file outside of the current working directory"
                );
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
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
    public void delteAllFiles() {

    }
}
