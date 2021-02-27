package com.luoshunkeji.comic.entity;

import java.util.List;

public class HomeEntity {

    /**
     * title : Banner轮播大图
     * data : [{"comic_id":117,"name":"戀愛輔助器","cover":"images/cover/202004/15857361442su8XLkYbrc1XIYr.jpg","cover_h":"images/cover/201908/1565604669QZcf4kHHehhzBe4w.jpg"},{"comic_id":45,"name":"窺視","cover":"images/cover/202004/1586037104G8Zqu6Hl8UAxPysz.jpg","cover_h":"images/cover/202003/15853917140ue4NzIQasRYAXOj.jpg"},{"comic_id":48,"name":"秘密教學","cover":"images/cover/202004/1586948369cWCC0n3QeHX2USDj.jpg","cover_h":"images/cover/202003/1585226894zDUJQEy02CtO1nkn.jpg"}]
     */

    private String title;
    private List<DataBean> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * comic_id : 117
         * name : 戀愛輔助器
         * cover : images/cover/202004/15857361442su8XLkYbrc1XIYr.jpg
         * cover_h : images/cover/201908/1565604669QZcf4kHHehhzBe4w.jpg
         */

        private int comic_id;
        private String name;
        private String cover;
        private String cover_h;
        private long click;

        public long getClick() {
            return click;
        }

        public void setClick(long click) {
            this.click = click;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        private String description;


        public int getComic_id() {
            return comic_id;
        }

        public void setComic_id(int comic_id) {
            this.comic_id = comic_id;
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

        public String getCover_h() {
            return cover_h;
        }

        public void setCover_h(String cover_h) {
            this.cover_h = cover_h;
        }
    }
}
