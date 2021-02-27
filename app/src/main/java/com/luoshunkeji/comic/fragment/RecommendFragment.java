package com.luoshunkeji.comic.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.adapter.RankTitleAdapter;
import com.luoshunkeji.comic.dao.BaseFragment;
import com.luoshunkeji.comic.network.OkhttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RecommendFragment extends BaseFragment implements OkhttpUtils.OkhttpListener {

    private View view;
    private ArrayList<String> mTitleList = new ArrayList<>();
    private RecyclerView recycleView;
    private EditText edtSearch;
    private ViewPager viewPager;
    private RankTitleAdapter mRankTitleAdapter;
    int mPosition;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    @Override
    public void initData() {

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager(), mTitleList));
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        mRankTitleAdapter = new RankTitleAdapter(getActivity(), mTitleList);
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
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(linearLayoutManager);
        mTitleList.add("人气排行");
        mTitleList.add("点击排行");
        mTitleList.add("最新漫画");

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtSearch.getText().toString().equals("")) {
                    hideInput();
                } else {
                    notifySearch();
                }
            }
        });

    }

    private void notifySearch() {
        EventBus.getDefault().post(edtSearch.getText().toString());
    }


    protected void hideInput() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        View v = getActivity().getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onAccessComplete(boolean success, String object, IOException error, String flag) {

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
                return new RankFragment("popularity");
            } else if (position == 1) {
                return new RankFragment("click");
            } else {
                return new RankFragment("created_at");
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
            hideInput();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String tager) {

    }
}
