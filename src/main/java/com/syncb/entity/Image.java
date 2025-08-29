
package com.syncb.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "images")
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imgurId;
    private String deleteHash;
    private String link;
    private Instant createdAt = Instant.now();

    public Image(String url) {
        this.link = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // getters/setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getImgurId() { return imgurId; }
    public void setImgurId(String imgurId) { this.imgurId = imgurId; }
    public String getDeleteHash() { return deleteHash; }
    public void setDeleteHash(String deleteHash) { this.deleteHash = deleteHash; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public Instant getCreatedAt() { return createdAt; }

}
