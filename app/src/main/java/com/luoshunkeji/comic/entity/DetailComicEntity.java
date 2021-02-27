package com.luoshunkeji.comic.entity;

import java.util.List;

public class DetailComicEntity {


    /**
     * comic_info : {"id":24,"click":61864304,"name":"朋友的姐姐","author":"佚名","cover":"images/cover/202006/1592646855mgJkKYYHymekLOHO.jpg","cover_h":"","popularity":979691001,"description":"喜歡偷看別人上床，血氣方剛的高中生恆軒，這次在偷窺過程中意外被抓包!情急之下躲進隔壁房間，卻沒想到房間內有個一絲不掛的女人\u2026!","serialise":"连载中"}
     * chapter_list : [{"chapter_id":968,"comic_id":"24","name":"第1話","price":0,"cover":"images/collection/chapter_cover/18375ba99bb-9b64-4d7e-852d-ac8305113174.jpeg"},{"chapter_id":969,"comic_id":"24","name":"第2話","price":0,"cover":"images/collection/chapter_cover/1835c35b63b-93c8-4e87-92cf-bffb016970e1.jpeg"},{"chapter_id":970,"comic_id":"24","name":"第3話","price":0,"cover":"images/collection/chapter_cover/183d998c4a4-522e-438e-9d8e-84bbc32f22b0.jpeg"},{"chapter_id":971,"comic_id":"24","name":"第4話","price":0,"cover":"images/collection/chapter_cover/18370a58fb1-3ab9-45d8-a804-bb0ab6b75707.jpeg"},{"chapter_id":972,"comic_id":"24","name":"第5話","price":0,"cover":"images/collection/chapter_cover/1835d05e40e-85c2-498d-8b29-42cb5480b5cd.jpeg"},{"chapter_id":973,"comic_id":"24","name":"第6話","price":0,"cover":"images/collection/chapter_cover/18380ce4a7d-5768-43b6-92ec-cefb43327e23.jpeg"},{"chapter_id":974,"comic_id":"24","name":"第7話","price":0,"cover":"images/collection/chapter_cover/183140433de-c049-4705-b434-299910b0ae4f.jpeg"},{"chapter_id":975,"comic_id":"24","name":"第8話","price":0,"cover":"images/collection/chapter_cover/1836bfab440-4eb9-4133-ad9b-4a0f973bbd85.jpeg"},{"chapter_id":976,"comic_id":"24","name":"第9話","price":0,"cover":"images/collection/chapter_cover/1834c459de1-0c77-4000-9e01-2aa0423e9309.jpeg"},{"chapter_id":977,"comic_id":"24","name":"第10話","price":0,"cover":"images/collection/chapter_cover/1835b47c772-ffe2-435c-bbca-47fdfecbd625.jpeg"},{"chapter_id":978,"comic_id":"24","name":"第11話","price":0,"cover":"images/collection/chapter_cover/183c716c4f8-7234-4d3f-8609-7c0398dcc86f.jpeg"},{"chapter_id":979,"comic_id":"24","name":"第12話","price":0,"cover":"images/collection/chapter_cover/18357a24341-f206-4ff3-8b76-fc3747b3d371.jpeg"},{"chapter_id":980,"comic_id":"24","name":"第13話","price":0,"cover":"images/collection/chapter_cover/1833672a8d0-2ff8-4ed1-a57d-86f0a3335165.jpeg"},{"chapter_id":981,"comic_id":"24","name":"第14話","price":0,"cover":"images/collection/chapter_cover/183c52df2aa-3d01-49b4-936b-7f47a36559c1.jpeg"},{"chapter_id":982,"comic_id":"24","name":"第15話","price":0,"cover":"images/collection/chapter_cover/183af887045-068a-4d1c-b218-5bcb896f9542.jpeg"},{"chapter_id":983,"comic_id":"24","name":"第16話","price":0,"cover":"images/collection/chapter_cover/1838306ba0c-0a9a-4f4c-bb03-27db2fa49de8.jpeg"},{"chapter_id":984,"comic_id":"24","name":"第17話","price":0,"cover":"images/collection/chapter_cover/183a199ca71-4d37-4c39-ae03-fd17ef7f4946.jpeg"},{"chapter_id":985,"comic_id":"24","name":"第18話","price":0,"cover":"images/collection/chapter_cover/183aa665a59-eaef-4bbb-aaba-aec0f0e6a52b.jpeg"},{"chapter_id":986,"comic_id":"24","name":"第19話","price":0,"cover":"images/collection/chapter_cover/183e599c832-d0a9-4450-ad62-3feb6c99e551.jpeg"},{"chapter_id":987,"comic_id":"24","name":"第20話","price":0,"cover":"images/collection/chapter_cover/183eab0cbd3-8e53-42f2-93bb-30cfce6a9260.jpeg"},{"chapter_id":988,"comic_id":"24","name":"第21話","price":0,"cover":"images/collection/chapter_cover/183b7fc1dce-d27f-456f-8692-d11c503b1993.jpeg"},{"chapter_id":989,"comic_id":"24","name":"第22話","price":0,"cover":"images/collection/chapter_cover/1838c1d682f-65d8-4e02-a1a7-dd690bae6ff6.jpeg"},{"chapter_id":990,"comic_id":"24","name":"第23話","price":0,"cover":"images/collection/chapter_cover/1833a80b499-8d49-4032-9835-380091441cee.jpeg"},{"chapter_id":991,"comic_id":"24","name":"第24話","price":0,"cover":"images/collection/chapter_cover/1830b9853fc-ed0e-48ab-b836-4907d389dc34.jpeg"},{"chapter_id":992,"comic_id":"24","name":"第25話","price":0,"cover":"images/collection/chapter_cover/18338359259-d8fb-4343-8ed1-4bc2e4b7fe2b.jpeg"},{"chapter_id":993,"comic_id":"24","name":"第26話","price":0,"cover":"images/collection/chapter_cover/183256800b4-578f-4bb3-84c0-ba2d214a0f23.jpeg"},{"chapter_id":994,"comic_id":"24","name":"第27話","price":0,"cover":"images/collection/chapter_cover/183f464e0d6-39e4-4f04-8258-70736cd5072e.jpeg"},{"chapter_id":995,"comic_id":"24","name":"第28話","price":0,"cover":"images/collection/chapter_cover/18365fcd67d-bae8-4658-a069-5f26f974cf44.jpeg"},{"chapter_id":996,"comic_id":"24","name":"第29话","price":0,"cover":"/images/collection/comic/183/1740583.jpg"},{"chapter_id":997,"comic_id":"24","name":"第30话","price":0,"cover":"/images/collection/comic/183/1753507.jpg"},{"chapter_id":998,"comic_id":"24","name":"第31话","price":0,"cover":"/images/collection/comic/183/1768874.jpg"},{"chapter_id":999,"comic_id":"24","name":"第32话","price":0,"cover":"/images/collection/comic/183/1774138.jpg"},{"chapter_id":1000,"comic_id":"24","name":"第33话","price":0,"cover":"/images/collection/comic/183/1795788.jpg"},{"chapter_id":1001,"comic_id":"24","name":"第34话","price":0,"cover":"/images/collection/comic/183/1809835.jpg"}]
     * you_like : [{"id":159,"name":"亞哈路（第二季）","cover":"images/cover/201911/1573734639mhELs6Nwqs-gWTQV.jpg"},{"id":156,"name":"惡女來了請小心","cover":"images/cover/201904/1555934742QtGBI4XIn58jfLHy.jpg"},{"id":122,"name":"馴服小姨子","cover":"images/collection/cover/xunfuxiaoyizi_22e5673a-21a0-43aa-acf5-86a89ed347f4.jpeg"},{"id":218,"name":"少女映畫之沖田總司 2 - Anime cosplay girl - (37P)","cover":"images/collection/cover/shaonyuyinghuazhichongtianzongsi_95b217f3094b7ff9e50e1c93b893265f.jpg"},{"id":89,"name":"下女, 初希","cover":"images/collection/cover/xianyuchuxi_7f992c93-e549-49fd-b4a5-3de22f757a45.jpeg"},{"id":48,"name":"秘密教學","cover":"images/cover/202004/1586948369cWCC0n3QeHX2USDj.jpg"},{"id":30,"name":"致命的你","cover":"images/cover/202006/1593509022WHx6mCgaE0bTwcrM.jpg"},{"id":96,"name":"老師,好久不見","cover":"images/collection/cover/laoshihaojiubujian_92db35f0-2e80-413a-bc4e-aff2a302d1b3.jpeg"},{"id":76,"name":"女朋友","cover":"images/cover/202001/1578219175JoJ6EV2MAFnq5IRc.jpg"}]
     */

    private ComicInfoBean comic_info;
    private List<ChapterListBean> chapter_list;
    private List<Comics> you_like;

    public ComicInfoBean getComic_info() {
        return comic_info;
    }

    public void setComic_info(ComicInfoBean comic_info) {
        this.comic_info = comic_info;
    }

    public List<ChapterListBean> getChapter_list() {
        return chapter_list;
    }

    public void setChapter_list(List<ChapterListBean> chapter_list) {
        this.chapter_list = chapter_list;
    }

    public List<Comics> getYou_like() {
        return you_like;
    }

    public void setYou_like(List<Comics> you_like) {
        this.you_like = you_like;
    }

    public static class ComicInfoBean {
        /**
         * id : 24
         * click : 61864304
         * name : 朋友的姐姐
         * author : 佚名
         * cover : images/cover/202006/1592646855mgJkKYYHymekLOHO.jpg
         * cover_h :
         * popularity : 979691001
         * description : 喜歡偷看別人上床，血氣方剛的高中生恆軒，這次在偷窺過程中意外被抓包!情急之下躲進隔壁房間，卻沒想到房間內有個一絲不掛的女人…!
         * serialise : 连载中
         */

        private int id;
        private long click;
        private String name;
        private String author;
        private String cover;
        private String cover_h;
        private long popularity;
        private String description;
        private String serialise;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getClick() {
            return click;
        }

        public void setClick(long click) {
            this.click = click;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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

        public long getPopularity() {
            return popularity;
        }

        public void setPopularity(long popularity) {
            this.popularity = popularity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSerialise() {
            return serialise;
        }

        public void setSerialise(String serialise) {
            this.serialise = serialise;
        }
    }

    public static class ChapterListBean {
        /**
         * chapter_id : 968
         * comic_id : 24
         * name : 第1話
         * price : 0
         * cover : images/collection/chapter_cover/18375ba99bb-9b64-4d7e-852d-ac8305113174.jpeg
         */

        private int chapter_id;
        private String comic_id;
        private String name;
        private int price;
        private String cover;

        public int getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(int chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getComic_id() {
            return comic_id;
        }

        public void setComic_id(String comic_id) {
            this.comic_id = comic_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }

    public static class YouLikeBean {
        /**
         * id : 159
         * name : 亞哈路（第二季）
         * cover : images/cover/201911/1573734639mhELs6Nwqs-gWTQV.jpg
         */

        private int id;
        private String name;
        private String cover;

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
    }
}
