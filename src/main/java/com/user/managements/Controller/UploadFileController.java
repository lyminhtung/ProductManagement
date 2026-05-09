package com.user.managements.Controller;

import com.user.managements.Model.ResponseObject;
import com.user.managements.Services.IStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/v1/file")
@RequiredArgsConstructor
public class UploadFileController {

    private final IStorageService storageService;

    @PostMapping
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String generatedFileName = storageService.storageFile(file);
            return ResponseEntity.ok(new ResponseObject("ok", "Uploaded file successfully!", generatedFileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("error", e.getMessage(), ""));
        }
    }
}
