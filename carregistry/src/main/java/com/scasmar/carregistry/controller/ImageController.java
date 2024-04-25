package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.service.CarService;
import com.scasmar.carregistry.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
@Slf4j
public class ImageController {

    private final UserService userService;

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file){
        if (file.isEmpty()){
            log.error("File is empty");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        log.info("Filename it´s {}", file.getOriginalFilename());

        return ResponseEntity.ok("File successfully update");
    }

    @GetMapping("/downloadFile")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> downloadFile() throws IOException{
        MediaType contentType = MediaType.IMAGE_PNG;
        InputStream in = new FileInputStream("src/main/resources/spring.png");

        return ResponseEntity.ok().contentType(contentType).body(new InputStreamResource(in));
    }

    @PostMapping(value = "/uploadCSV", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCSV(@RequestParam(value = "file") MultipartFile file){
        if (file.isEmpty()){
            log.error("The file it´s empty");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (file.getOriginalFilename().contains(".csv")){
            log.info("File it´s {}", file.getOriginalFilename());

            return ResponseEntity.ok("File successfully upload");
        } else {
            log.error("The file it´s not a CSV");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
