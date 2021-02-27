package com.luoshunkeji.comic.entity;

import java.util.List;

public class Comics {

    /**
     * id : 44
     * name : 妹妹的義務
     * cover : images/collection/cover/meimeideyiwu_21b5784c-be0d-4ce4-8d6b-79f1ed8c7b7c.jpeg
     * author : 佚名
     * tags : []
     */

    private int id;
    private String name;
    private String cover;
    private String author;
    private List<?> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }
}
