package com.example.demo.object;

import java.io.Serializable;

public class Model implements Serializable {
    long id;
    String title;
    String image;
    String author;
    long luotxem;
    String genre;
    public Model() {
    }

    public Model(long id,long luotxem) {
        this.id=id;
        this.luotxem = luotxem;
    }

    public Model(long id, String title, String image, String author, long luotxem) {
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

    public long getLuotxem() {
        return luotxem;
    }

    public void setLuotxem(long luotxem) {
        this.luotxem = luotxem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
