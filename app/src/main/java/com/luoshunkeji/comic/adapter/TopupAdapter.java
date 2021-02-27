package com.luoshunkeji.comic.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.entity.vipEntity;
import com.luoshunkeji.comic.utils.DensityUtil;
import com.luoshunkeji.comic.utils.ScreenUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopupAdapter extends BaseQuickAdapter<vipEntity, BaseViewHolder> {
    private List<vipEntity> list;
    private Activity mActivity;
    public Map<Integer, Boolean> isCheckMap = new HashMap<>();

    public TopupAdapter(Activity activity, int layoutResId, @Nullable List<vipEntity> data) {
        super(layoutResId, data);
        list = data;
        mActivity = activity;
        configCheckMap(0);
    }

    public void configCheckMap(int position) {
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                isCheckMap.put(i, true);
            } else {
                isCheckMap.put(i, false);
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, vipEntity item) {
        RelativeLayout rlItem = helper.getView(R.id.rlItem);
        ImageView ivIcon = helper.getView(R.id.ivIcon);
        TextView tvPrice = helper.getView(R.id.tvPrice);
        TextView tvContent = helper.getView(R.id.tvContent);
        TextView tvState = helper.getView(R.id.tvState);
        TextView tvTip = helper.getView(R.id.tvTip);
        ViewGroup.LayoutParams lp = rlItem.getLayoutParams();
        lp.width = (ScreenUtil.getScreenWidth(mActivity) - DensityUtil.dip2px(20)) / 2;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        rlItem.setLayoutParams(lp);
        helper.addOnClickListener(R.id.rlItem);
        if (isCheckMap.get(helper.getAdapterPosition() - 1)) {//有头部局
            //选中
            rlItem.setBackgroundResource(R.drawable.changitem_top_up_selected);
            tvPrice.setTextColor(mActivity.getResources().getColor(R.color.white));
            tvTip.setTextColor(mActivity.getResources().getColor(R.color.white));
            tvContent.setTextColor(mActivity.getResources().getColor(R.color.yellow_fff700));
        } else {
            //未选中
            rlItem.setBackgroundResource(R.drawable.changitem_top_up);
            tvPrice.setTextColor(mActivity.getResources().getColor(R.color.black));
            tvTip.setTextColor(mActivity.getResources().getColor(R.color.black));
            tvContent.setTextColor(mActivity.getResources().getColor(R.color.red_ea222c));
        }


        helper.setText(R.id.tvPrice, item.getAmount() + "元");
        helper.setText(R.id.tvContent, item.getAmount_data());
        helper.setText(R.id.tvTip, item.getAmount_msg());
        helper.setText(R.id.tvState, item.getTips());
        if (item.getIs_vip() == 0) {
            ivIcon.setImageResource(R.mipmap.vip_icon);
        } else {
            ivIcon.setImageResource(R.mipmap.jinbi);
        }

        if (item.getTips().equals("热销")) {
            //红色
            tvState.setBackgroundResource(R.drawable.changitem_hot_sale);
        } else if (item.getTips().equals("推荐")) {
            //蓝色
            tvState.setBackgroundResource(R.drawable.changitem_recomment);
        } else {
            //限时 黑色
            tvState.setBackgroundResource(R.drawable.changitem_limit_time);
        }


    }
}
