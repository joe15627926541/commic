package com.luoshunkeji.comic.entity;

public class vipEntity {

    /**
     * id : 1
     * tips : 热销
     * is_vip : 0
     * amount : 99
     * amount_data : VIP月卡+送15天
     * amount_msg : 特惠VIP全站免费看
     */

    private String tips;
    private int is_vip;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int amount;
    private String amount_data;
    private String amount_msg;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmount_data() {
        return amount_data;
    }

    public void setAmount_data(String amount_data) {
        this.amount_data = amount_data;
    }

    public String getAmount_msg() {
        return amount_msg;
    }

    public void setAmount_msg(String amount_msg) {
        this.amount_msg = amount_msg;
    }
}
