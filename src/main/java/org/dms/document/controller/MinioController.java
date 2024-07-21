package org.dms.document.controller;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.dms.document.service.MinioService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("minio")
public class MinioController {
    private final MinioClient minioClient;
    private final MinioService minioService;
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(minioService.addFile(file) ,HttpStatus.OK);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable String fileName) {
        return minioService.getFile(fileName);

    }
}
