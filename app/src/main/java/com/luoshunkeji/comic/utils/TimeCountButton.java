package com.luoshunkeji.comic.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;


public class TimeCountButton extends CountDownTimer {
    public Button btnGetcode;
    public String tip;
    public boolean withoutbg = false;
    public boolean textcolor = false;

    public void setbutton(Button btnGetcode) {
        // TODO Auto-generated method stub
        this.btnGetcode = btnGetcode;

    }

    public void setbutton(Button btnGetcode, String tip) {
        // TODO Auto-generated method stub
        this.btnGetcode = btnGetcode;
        this.tip = tip;
    }

    public void setbutton(Button btnGetcode, String tip, boolean withoutbg, boolean textcolor) {
        // TODO Auto-generated method stub
        this.btnGetcode = btnGetcode;
        this.tip = tip;
        this.withoutbg = withoutbg;
        this.textcolor = textcolor;

    }

    public TimeCountButton(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (!withoutbg) {
//            btnGetcode.setBackgroundResource(R.drawable.changitem_gray);
            btnGetcode.setTextColor(Color.parseColor("#8F79F9"));
        }else{
            btnGetcode.setTextColor(Color.parseColor("#7F7F7F"));
//            btnGetcode.setTextColor(Color.parseColor("#C5C6CB"));
        }

        btnGetcode.setClickable(false);
        if (tip != null && !tip.equals("")) {
            btnGetcode.setText(tip + " " + "(" + millisUntilFinished / 1000 + ")");
        } else {
            btnGetcode.setText( millisUntilFinished / 1000 + "s");
        }

    }

    @Override
    public void onFinish() {
//	        	btnGetcode.setBackgroundResource(R.drawable.heart_send_nor);

        if (tip != null && !tip.equals("")) {
            btnGetcode.setText(tip);
        } else {
            btnGetcode.setText("重新获取");
        }

        btnGetcode.setClickable(true);
//        if (!withoutbg) {
//            btnGetcode.setBackgroundResource(R.drawable.changitem_green);
//        }
            btnGetcode.setTextColor(Color.parseColor("#333333"));
//        if (!textcolor){
//        }else{
//            btnGetcode.setTextColor(Color.parseColor("#7940E5"));
//        }
    }
}