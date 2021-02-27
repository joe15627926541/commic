package com.luoshunkeji.comic.adapter;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.entity.OrderEntity;
import com.luoshunkeji.comic.utils.ImageUtils;

import java.util.List;

public class OrderAdapter extends BaseQuickAdapter<OrderEntity, BaseViewHolder> {
    private Activity mActivity;
    private List<OrderEntity> list;

    public OrderAdapter(Activity activity, int layoutResId, @Nullable List<OrderEntity> data) {
        super(layoutResId, data);
        list = data;
        mActivity = activity;

    }

    @Override
    protected void convert(BaseViewHolder helper, OrderEntity item) {
        ImageView ivCover = helper.getView(R.id.ivCover);

        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvPrice, item.getPrice() + "");
        helper.setText(R.id.tvState, item.getState() + "");
        helper.setText(R.id.tvCreate, item.getCreate_time() + "");
        helper.setText(R.id.tvOrderNumber, item.getOrder_number() + "");
        if (item.getPay_time() != null) {
            helper.setText(R.id.tvPayTime, item.getPay_time() + "");
        }
        if (item.getIs_vip()==1){
            ivCover.setImageResource(R.mipmap.ordervip);
        }else{
            ivCover.setImageResource(R.mipmap.order_icon);
        }

    }
}
