package com.luoshunkeji.comic.me;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.adapter.RankTitleAdapter;
import com.luoshunkeji.comic.dao.BaseFramActivity;
import com.luoshunkeji.comic.fragment.OrderFragment;
import com.luoshunkeji.comic.network.MQuery;
import com.luoshunkeji.comic.network.OkhttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OrderActivity extends BaseFramActivity implements OkhttpUtils.OkhttpListener, View.OnClickListener {
    private ArrayList<String> mTitleList = new ArrayList<>();
    private RecyclerView recycleView;
    private ViewPager viewPager;
    private RankTitleAdapter mRankTitleAdapter;
    int mPosition;
    private MQuery mq;


    @Override
    public void createActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
    }

    @Override
    public void initData() {
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), mTitleList));
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mRankTitleAdapter = new RankTitleAdapter(this, mTitleList);
        recycleView.setAdapter(mRankTitleAdapter);
        mRankTitleAdapter.setOnItemClickListener(new RankTitleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mPosition != position) {
                    mPosition = position;
                    viewPager.setCurrentItem(position);
                    recycleView.scrollToPosition(position);
                    mRankTitleAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void initView() {
        mq = new MQuery(this);
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(linearLayoutManager);
        mTitleList.add("全部订单");
        mTitleList.add("已完成");
        mTitleList.add("未完成");
        mq.id(R.id.back).clicked(this);
        mq.id(R.id.title).text("订单信息");
    }


    public class MyAdapter extends FragmentStatePagerAdapter {
        FragmentManager fm;
        List<String> list;

        public MyAdapter(FragmentManager fm, List<String> list) {
            super(fm);
            this.fm = fm;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            //TODO   具体逻辑
            if (position == 0) {
                return new OrderFragment(0);
            } else if (position == 1) {
                return new OrderFragment(1);
            } else {
                return new OrderFragment(2);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageSelected(int position) {
            Iterator i = mRankTitleAdapter.isCheckMap.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry entry = (Map.Entry) i.next();
                entry.setValue(false);
            }
            mRankTitleAdapter.isCheckMap.put(position, true);
            mRankTitleAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {

    }
}
