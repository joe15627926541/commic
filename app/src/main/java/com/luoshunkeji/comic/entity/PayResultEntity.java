package com.luoshunkeji.comic.entity;

public class PayResultEntity {

    /**
     * success : true
     * msg : success
     * code : 200
     * timestamp : 1611911465112
     * data : {"id":"1355081016166842368","payUrl":"https://zfm.nnt.ltd/apiPay?orderNo=1355081016166842368"}
     */

    private boolean success;
    private String msg;
    private int code;
    private long timestamp;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1355081016166842368
         * payUrl : https://zfm.nnt.ltd/apiPay?orderNo=1355081016166842368
         */

        private String id;
        private String payUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPayUrl() {
            return payUrl;
        }

        public void setPayUrl(String payUrl) {
            this.payUrl = payUrl;
        }
    }
}
