package com.example.demo.object;

import java.io.Serializable;

public class Model implements Serializable {
    String id;
    String title;
    String image;
    String author;
    String luotxem;
    String genre;
    public Model() {
    }

    public Model(String id, String title, String image, String author, String luotxem) {
        this.id=id;
        this.title = title;
        this.image = image;
        this.author = author;
        this.luotxem = luotxem;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLuotxem() {
        return luotxem;
    }

    public void setLuotxem(String luotxem) {
        this.luotxem = luotxem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
