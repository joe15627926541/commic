package com.luoshunkeji.comic.entity;

public class ReadHistoryEntity {

    /**
     * comic_id : 184
     * cover : images/cover/201906/1561208653wtrkFccdFGETbQQv.jpg
     * read_chapter : 第1话
     * read_chapter_id : 7543
     * name : 死了都要愛愛
     */

    private int comic_id;
    private String cover;
    private String read_chapter;
    private int read_chapter_id;
    private String name;

    public int getComic_id() {
        return comic_id;
    }

    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getRead_chapter() {
        return read_chapter;
    }

    public void setRead_chapter(String read_chapter) {
        this.read_chapter = read_chapter;
    }

    public int getRead_chapter_id() {
        return read_chapter_id;
    }

    public void setRead_chapter_id(int read_chapter_id) {
        this.read_chapter_id = read_chapter_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
