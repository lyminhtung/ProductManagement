package com.user.managements.Controller;

import com.user.managements.Model.ResponseObject;
import com.user.managements.Services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping(path = "api/v1/file")
public class UploadFileController {
    @Autowired
    private IStorageService storageService;
    @PostMapping
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            String generatedFileName = storageService.storageFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Uploaded file successfully!",generatedFileName)
            );
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("error","Upload file failed!",e.getMessage())
            );
        }
    }
}
