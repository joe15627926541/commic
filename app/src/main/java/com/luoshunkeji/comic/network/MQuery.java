package com.luoshunkeji.comic.network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * 基于volley的精简AQuery(Mquery)
 *
 * @author ping
 * 2014-4-11 下午4:27:01
 * <p>
 * AQuery aq = new AQuery(mview);
 * AQuery aq = new AQuery(activity);
 * <p>
 * aq.request().setFlag("get").setParams(params).setTimeout(25000).byPost(Urls.LBSSTORE , this);
 * aq.id(R.id.image).image(Urls.ROOT + data.get(index).getGoogpic());
 * aq.id(textview).text("myname");
 * String pwd = aq.id(R.id.ed_pwd2).getText();
 */
public class MQuery {
    private View view;

    public MQuery(Activity activity) {
        view = activity.getWindow().getDecorView();
    }

    public MQuery(View view) {
        this.view = view;
    }

    public MQuery id(int id) {
        return id(view.findViewById(id));
    }

    public MQuery id(View view) {
        return new MQuery(view);
    }

    public NetAccess request() {
        return NetAccess.request(view.getContext());
    }

    public OkhttpUtils okRequest() {
        return OkhttpUtils.request(view.getContext());
    }

    // public Mdb db() {
    // storeback new Mdb(view.getContext());
    // }
    //
    // public Mdb db(String dbname) {
    // storeback new Mdb(view.getContext(),dbname);
    // }
    //
    // public Mdb db(String dbpath,String dbname) {
    // storeback new Mdb(view.getContext(),dbpath,dbname);
    // }

    public String getText() {
        CharSequence result = null;
        if (view instanceof TextView) {
            result = ((TextView) view).getText();
        }
        return result == null ? null : result.toString();
    }

    public String getTirmText() {
        String result = getText();
        return result == null ? null : result.trim();
    }

    public MQuery text(String str) {
        if (view instanceof TextView) {
            ((TextView) view).setText(str);
        }
        return this;
    }


    public MQuery alpha(float alpha) {
        if (view instanceof TextView || view instanceof Button) {
            ((TextView) view).setAlpha(alpha);
        }
        return this;
    }

    public MQuery settextsise(float size) {
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(size);
        }
        return this;
    }

    public MQuery text(int res) {
        if (view instanceof TextView) {
            ((TextView) view).setText(res);
        }
        return this;
    }

    public MQuery hint(String res) {
        if (view instanceof TextView) {
            ((TextView) view).setHint(res);
        }
        return this;
    }

    public MQuery image(String url) {
        if (view instanceof ImageView) {
//			NetAccess.image((ImageView) view, url);
            Glide.with(view.getContext()).load(url).dontAnimate().into((ImageView) view);
        }
        return this;
    }

    public MQuery image(String url, int loadingimg, int errorimg) {
        if (view instanceof ImageView) {
            NetAccess.image((ImageView) view, url, loadingimg, errorimg);
        }
        return this;
    }


    public MQuery image(int resid) {
        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            if (resid == 0) {
                iv.setImageBitmap(null);
            } else {
                iv.setImageResource(resid);
            }
        }
        return this;
    }

    public MQuery image(Drawable drawable) {
        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            iv.setImageDrawable(drawable);
        }
        return this;
    }

    /**
     * 获取view的bitmap数据 new mquery(activity).getViewbitmap >>截图
     *
     * @return
     * @author ping
     * @create 2014-4-23 下午2:16:48
     */
    public Bitmap getViewbitmap() {
        Bitmap bitmap = null;
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width != 0 && height != 0) {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view.layout(0, 0, width, height);
                view.draw(canvas);
            }
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }

    public MQuery image(Bitmap bm) {
        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            iv.setImageBitmap(bm);
        }
        return this;
    }

    public MQuery image(File file) {
        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        return image(bm);
    }

    public MQuery clickable(boolean clickable) {
        if (view != null) {
            view.setClickable(clickable);
        }
        return this;
    }

    public MQuery visibility(int visibility) {
        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public int visibility() {
        int vi = View.GONE;
        if (view != null) {
            vi = view.getVisibility();
        }
        return vi;
    }

    public MQuery checked(boolean checked) {
        if (view instanceof CompoundButton) {
            CompoundButton cb = (CompoundButton) view;
            cb.setChecked(checked);
        }
        return this;
    }

    public boolean isChecked() {
        boolean checked = false;
        if (view instanceof CompoundButton) {
            CompoundButton cb = (CompoundButton) view;
            checked = cb.isChecked();
        }
        return checked;
    }

    public MQuery enabled(boolean enabled) {
        if (view != null) {
            view.setEnabled(enabled);
        }
        return this;
    }

    public MQuery clicked(OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public MQuery background(int id) {
        if (view != null) {
            if (id != 0) {
                view.setBackgroundResource(id);
            } else {
                view.setBackgroundDrawable(null);
            }
        }
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param color
     * @return
     */
    public MQuery backgroundColor(int color) {
        if (view != null) {
            if (color != 0) {
                view.setBackgroundColor(color);
            } else {
                view.setBackgroundColor(0x00000000);
            }
        }
        return this;
    }

    @SuppressLint("NewApi")
    public MQuery background(Drawable dr) {
        if (view != null) {
            if (dr != null) {
                view.setBackground(dr);
            } else {
                view.setBackgroundDrawable(null);
            }
        }
        return this;
    }

    public MQuery itemClicked(OnItemClickListener listener) {
        if (view instanceof AdapterView) {
            AdapterView<?> alv = (AdapterView<?>) view;
            alv.setOnItemClickListener(listener);
        }
        return this;
    }

    public View getView() {
        return view;
    }

    public MQuery textColor(int color) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextColor(color);
        }
        return this;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public MQuery adapter(Adapter adapter) {
        if (view instanceof AdapterView) {
            AdapterView av = (AdapterView) view;
            av.setAdapter(adapter);
        }
        return this;
    }

    public MQuery adapter(ExpandableListAdapter adapter) {
        if (view instanceof ExpandableListView) {
            ExpandableListView av = (ExpandableListView) view;
            av.setAdapter(adapter);
        }
        return this;
    }

    public MQuery setSelection(int position) {
        if (view instanceof AdapterView) {
            AdapterView<?> alv = (AdapterView<?>) view;
            alv.setSelection(position);
        }
        return this;
    }

    public MQuery click() {
        if (view != null) {
            view.performClick();
        }
        return this;
    }

    public MQuery longclick() {
        if (view != null) {
            view.performLongClick();
        }
        return this;
    }
}
