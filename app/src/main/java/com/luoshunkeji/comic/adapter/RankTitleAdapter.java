package com.luoshunkeji.comic.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.utils.ScreenUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/6/23.
 */
public class RankTitleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<String> list;

    public Map<Integer, Boolean> isCheckMap = new HashMap<>();
    private static OnItemClickListener mOnItemClickListener;

    public RankTitleAdapter(Activity activity, List<String> list) {
        this.activity = activity;
        this.list = list;
        configCheckMap(false);
    }

    public void configCheckMap(boolean bool) {
        for (int i = 1; i < list.size(); i++) {
            isCheckMap.put(i, bool);
        }
        isCheckMap.put(0, true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_rank_pager_title, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyHolder) {
            if (isCheckMap.get(position)) {
                ((MyHolder) holder).tv_title.setTextColor(activity.getResources().getColor(R.color.black_323233));
                ((MyHolder) holder).line.setVisibility(View.VISIBLE);


            } else {
                ((MyHolder) holder).tv_title.setTextColor(activity.getResources().getColor(R.color.black_646566));
                ((MyHolder) holder).line.setVisibility(View.INVISIBLE);

            }
            ((MyHolder) holder).tv_title.setText(list.get(position));
            ((MyHolder) holder).ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Iterator i = isCheckMap.entrySet().iterator();
                    while (i.hasNext()) {
                        Map.Entry entry = (Map.Entry) i.next();
                        entry.setValue(false);
                    }
                    isCheckMap.put(position, true);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private LinearLayout ly;
        private TextView tv_title;
        private TextView line;


        public MyHolder(View itemView) {
            super(itemView);
            ly = (LinearLayout) itemView.findViewById(R.id.ly);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            line = (TextView) itemView.findViewById(R.id.line);
            ViewGroup.LayoutParams lp = ly.getLayoutParams();
            lp.width = ScreenUtil.getScreenWidth(activity) / (list.size());
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            ly.setLayoutParams(lp);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

}
