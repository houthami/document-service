package org.dms.document.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @SneakyThrows
    public String addFile(MultipartFile file) {
        try (InputStream inputStream = new BufferedInputStream(file.getInputStream())) {
            System.out.println(file.getContentType());
            var obj = minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket("document")
                    .contentType(file.getContentType())
                    .object(file.getOriginalFilename())
                    .stream(inputStream, file.getSize(), -1)
                    .build());
            return obj.object();
        } catch (IOException | ServerException | InsufficientDataException| ErrorResponseException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException | InternalException exception){
            throw new Exception(exception.getCause());
        }
    }

    @SneakyThrows
    public ResponseEntity<InputStreamResource> getFile(String fileName){
        try {
            var file = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("document")
                            .object(fileName)
                            .build()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.valueOf(Objects.requireNonNull(file.headers().get("Content-Type"))))
                    .body(new InputStreamResource(file));
        } catch (MinioException | IOException e) {
            // Handle MinioException and IOException appropriately
            return ResponseEntity
                    .status(500)
                    .body(null);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
