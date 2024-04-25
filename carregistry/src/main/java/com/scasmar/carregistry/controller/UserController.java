package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/userImage/{id}/get")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long id){
        try{
            byte[] imageBytes = userService.getUserImage(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/userImage/{id}/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<String> addProduct(@PathVariable Long id, @RequestParam(value = "imageFile") MultipartFile imageFile){
        try{
            if(imageFile.getOriginalFilename().contains(".png") || imageFile.getOriginalFilename().contains(".jpg")){

                userService.addUserImage(id, imageFile);

                return ResponseEntity.ok("Image successfully saved.");
            } else {
                log.error("Not png or jpg");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
