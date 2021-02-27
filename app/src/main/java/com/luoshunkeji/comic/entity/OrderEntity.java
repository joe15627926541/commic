package com.luoshunkeji.comic.entity;

public class OrderEntity {

    /**
     * title : 16800 金币
     * is_vip : 0
     * price : 1
     * state : 未支付
     * create_time : 2021-01-29 18:55:27
     * pay_time : null
     * order_number : 1355107287361912832
     */

    private String title;
    private int is_vip;
    private int price;
    private String state;
    private String create_time;
    private Object pay_time;
    private String order_number;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Object getPay_time() {
        return pay_time;
    }

    public void setPay_time(Object pay_time) {
        this.pay_time = pay_time;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }
}
