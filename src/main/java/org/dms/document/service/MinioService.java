package org.dms.document.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dms.document.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {



    @Value("${spring.minio.bucket}")
    private String bucket;

    private final MinioClient minioClient;

    @SneakyThrows
    public String addFile(MultipartFile file) {
        log.info("Start Call MinioService.addFile");
        try (InputStream inputStream = new BufferedInputStream(file.getInputStream())) {
            PutObjectArgs documentArgs = PutObjectArgs
                    .builder()
                    .bucket(bucket)
                    .contentType(file.getContentType())
                    .object(FileUtils.generateReferenceFile(file.getOriginalFilename(), file.getContentType()))
                    .stream(inputStream, file.getSize(), -1)
                    .build();
            var obj = minioClient.putObject(documentArgs);
            return obj.object();
        } catch (IOException | ServerException | InsufficientDataException| ErrorResponseException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException | InternalException exception){
            log.error("Error Call MinioService.addFile", exception.getCause());
            throw new Exception(exception.getCause());
        }
    }

    @SneakyThrows
    public ResponseEntity<InputStreamResource> getFile(String fileName){
        log.info("Start Call MinioService.getFile with name {}", fileName);
        log.info("Bucket {}", bucket);
        try {
            var file = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build()
            );

            HttpHeaders headers = new HttpHeaders();
            //headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.valueOf(Objects.requireNonNull(file.headers().get("Content-Type"))))
                    .body(new InputStreamResource(file));
        } catch (MinioException | IOException e) {
            log.error("Error Call MinioService.getFile"+ e.getMessage());
            // Handle MinioException and IOException appropriately
            return ResponseEntity
                    .status(500)
                    .body(null);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error Call MinioService.getFile"+ e.getMessage());
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            log.error("Error Call MinioService.getFile"+ e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Error Call MinioService.getFile"+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
