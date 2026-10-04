package com.ecommerce.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
public class ImageServeController {
    private final String uplaodDir = System.getProperty("user.dir")+ "uploads/products/";

    @GetMapping("/products/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path path = Paths.get(uplaodDir+filename);
        Resource resource = new UrlResource(path.toUri());
        if(!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
        }
    }
}
