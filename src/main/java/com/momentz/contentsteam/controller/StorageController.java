package com.momentz.contentsteam.controller;

import com.momentz.contentsteam.model.Response;
import com.momentz.contentsteam.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/storage")
public class StorageController {

    private StorageService storageService;

    @PostMapping("/upload/{bucket}")
    public ResponseEntity<Response> upload(@PathVariable("bucket") String bucketName, @RequestParam("file") MultipartFile file) {
        return storageService.upload(file, bucketName);
    }

    @GetMapping("/url/{bucket}")
    public ResponseEntity<Response> getUrl(@PathVariable String bucket, @RequestParam("file") String fileName) {
        return storageService.getFileLink(fileName, bucket);
    }
}
