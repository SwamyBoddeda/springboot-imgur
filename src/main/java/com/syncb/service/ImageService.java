package com.syncb.service;

import com.syncb.client.ImgurApiClient;
import com.syncb.entity.Image;
import com.syncb.entity.User;
import com.syncb.repository.ImageRepository;
import com.syncb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImgurApiClient imgurApiClient;

   public Image upload(String username, MultipartFile file, String title) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow();
        var data = imgurApiClient.upload(file.getBytes(), title);
        Image img = new Image();
        img.setUser(user);
        img.setTitle(title);
        img.setImgurId(data.id);
        img.setDeleteHash(data.deletehash);
        img.setLink(data.link);
        imageRepository.save(img);
        return img;
    }

    public List<Image> list(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return imageRepository.findByUser(user);
    }

    public Image get(String username, Long id) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Image img = imageRepository.findById(id).orElseThrow();
        if (!img.getUser().getId().equals(user.getId())) throw new RuntimeException("Forbidden");
        return img;
    }

    public void delete(String username, Long id) {
        Image img = get(username, id);
        String del = img.getDeleteHash() != null ? img.getDeleteHash() : img.getImgurId();
        imgurApiClient.delete(del);
        imageRepository.delete(img);
    }

}
