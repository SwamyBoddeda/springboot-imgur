
package com.syncb.controller;

import com.syncb.entity.Image;
import com.syncb.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping(consumes = {"multipart/form-data"})
    public Image upload(@AuthenticationPrincipal User user,
                        @RequestPart("file") MultipartFile file,
                        @RequestPart(value="title", required=false) String title) throws IOException {
        return imageService.upload(user.getUsername(), file, title);
    }

    @GetMapping
    public List<Image> list(@AuthenticationPrincipal User user) {
        return imageService.list(user.getUsername());
    }

    @GetMapping("/{id}")
    public Image get(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return imageService.get(user.getUsername(), id);
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal User user, @PathVariable Long id) {
        imageService.delete(user.getUsername(), id);
    }
}
