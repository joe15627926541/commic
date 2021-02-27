package com.luoshunkeji.comic.entity;

public class payWay {

    /**
     * id : 7
     * pay_type : 1
     * pay_type_name : 支付宝
     */

    private int id;
    private int pay_type;
    private String pay_type_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_type_name() {
        return pay_type_name;
    }

    public void setPay_type_name(String pay_type_name) {
        this.pay_type_name = pay_type_name;
    }
}
