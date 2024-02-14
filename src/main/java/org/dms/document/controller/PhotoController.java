package org.dms.document.controller;

import lombok.RequiredArgsConstructor;
import org.dms.document.config.LoadFile;
import org.dms.document.dto.PhotoDto;
import org.dms.document.model.Photo;
import org.dms.document.service.PhotoService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoService photoService;
    @PostMapping
    public String addPhoto(@RequestParam("title") String title,
                           @RequestParam("image") MultipartFile image, Model model)
            throws IOException {
        String id = photoService.addPhoto(title, image);
        return "redirect:/photos/" + id;
    }

    @GetMapping("/{id}")
    public String getPhoto(@PathVariable String id, Model model) {
        Photo photo = photoService.getPhoto(id);
        model.addAttribute("title", photo.getTitle());
        model.addAttribute("image",
                Base64.getEncoder().encodeToString(photo.getImage().getData()));
        return "photo";
    }

    @PostMapping("resize/{id}")
    public ResponseEntity<ByteArrayResource> ResizePhoto(@PathVariable String id, @RequestBody PhotoDto photoDto) throws IOException {
        LoadFile photo = photoService.resize(id, photoDto);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.getFileType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getFilename() + "\"")
                .body(new ByteArrayResource(photo.getFile()));
    }
}
