package com.luoshunkeji.comic.utils;

import android.widget.TextView;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by user on 2017/6/22.
 */
public class NumAnim {
    //每秒刷新多少次
    //默认保留三位小数点
    private static final int COUNTPERS = 100;

    public static void startAnim(TextView textV, float num) {
        startAnim(textV, num, 500);
    }

    public static void startAnim(TextView textV, float num, long time) {
        if (num == 0) {
            textV.setText(NumUtil.NumberFormat(num,3));
            return;
        }

        Float[] nums = splitnum(num, (int)((time/1000f)*COUNTPERS));

        Counter counter = new Counter(textV, nums, time);

        textV.removeCallbacks(counter);
        textV.post(counter);
    }

    private static Float[] splitnum(float num, int count) {
        Random random = new Random();
        float numtemp = num;
        float sum = 0;
        LinkedList<Float> nums = new LinkedList<Float>();
        nums.add(0f);
        while (true) {
            float nextFloat = NumUtil.NumberFormatFloat(
                    (random.nextFloat()*num*2f)/(float)count,
                    3);
            if (numtemp - nextFloat >= 0) {
                sum = NumUtil.NumberFormatFloat(sum + nextFloat, 3);
                nums.add(sum);
                numtemp -= nextFloat;
            } else {
                nums.add(num);
                return nums.toArray(new Float[0]);
            }
        }
    }

    static class Counter implements Runnable {

        private final TextView view;
        private Float[] nums;
        private long pertime;

        private int i = 0;

        Counter(TextView view, Float[] nums, long time) {
            this.view = view;
            this.nums = nums;
            this.pertime = time/nums.length;
        }

        @Override
        public void run() {
            if (i>nums.length-1) {
                view.removeCallbacks(Counter.this);
                return;
            }
            view.setText(NumUtil.NumberFormat(nums[i++],3));
            view.removeCallbacks(Counter.this);
            view.postDelayed(Counter.this, pertime);
        }
    }

}
