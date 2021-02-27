package com.luoshunkeji.comic.network;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import com.luoshunkeji.comic.MyApplication;
import com.luoshunkeji.comic.R;
import com.luoshunkeji.comic.utils.L;
import com.luoshunkeji.comic.utils.Pkey;
import com.luoshunkeji.comic.utils.SPUtils;
import com.luoshunkeji.comic.utils.Sign;
import com.luoshunkeji.comic.utils.SystemTime;
import com.luoshunkeji.comic.utils.Token;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by user on 2016/10/24.
 */
public class OkhttpUtils {

    private static final String TAG = "OkhttpUtils";

    private Dialog mdialog;// 加载窗口

    private Map<String, Object> params;// 提交的参数

    private String flag;// 请求标示
    private String header = "";// 请求标示

    private int timeout = 30000;

    private Context mcontext;

    private OkhttpListener listener;

    private static OkHttpClient mOkHttpClient;

    private static Handler mDelivery;

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private String path = "";//文件路径

    public OkhttpUtils(Context mcontext) {
        this.mcontext = mcontext;
    }

    private boolean saveCache;//是否保存缓存

    private String url;

    private ACache aCache;

    public static OkhttpUtils request(Context context) {
        // 网络请求类初始化
        if (mOkHttpClient == null) {
            try {
                File sdcache = context.getExternalCacheDir();
                int cacheSize = 10 * 1024 * 1024;
                OkHttpClient.Builder ClientBuilder = new OkHttpClient.Builder();
                ClientBuilder.readTimeout(30, TimeUnit.SECONDS);//读取超时
                ClientBuilder.connectTimeout(10, TimeUnit.SECONDS);//连接超时
                ClientBuilder.writeTimeout(10, TimeUnit.SECONDS);//写入超时
                ClientBuilder.cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
                mOkHttpClient = ClientBuilder.build();
                mDelivery = new Handler(Looper.getMainLooper());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new OkhttpUtils(context);
    }

    /**
     * 回调接口
     */

    public interface OkhttpListener {
        void onAccessComplete(boolean success, String object,
                              IOException error, String flag);
    }


    /**
     * 设置请求内容
     *
     * @param params
     * @return
     */
    public OkhttpUtils setParams(Map<String, Object> params) throws NoSuchAlgorithmException {
//        params.put("timestamp", SystemTime.getTime());
//        params.put("nonce_str", APIUtil.getRandomString(32));
//        params.put("sign", APIUtil.getSign(params, "bazinga", ""));
        this.params = params;
        return this;
    }


    /**
     * 设置请求内容带Token
     *
     * @param params
     * @return
     */
    public OkhttpUtils setParams2(Map<String, Object> params) {
        params.put("time", SystemTime.getTime());
        params.put("token", Token.getToken(mcontext));
        String sign_key = "";
        List<String> list = new ArrayList<String>();
        Iterator i = params.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();
            if (null != entry.getKey() && null != entry.getValue()) {
                String value = (String) entry.getKey() + entry.getValue();
                list.add(value);
            }
        }
        Collections.sort(list);
        for (String temp : list) {
            sign_key = sign_key + temp;
        }
        params.put("sign", Sign.getSign(sign_key));
        this.params = params;
        return this;
    }


    public OkhttpUtils setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public OkhttpUtils setHeader(String header) {
        this.header = header;
        return this;
    }


    public OkhttpUtils showDialog(boolean canCancel) {
        if (!(mdialog == null || !mdialog.isShowing())) {
            mdialog.dismiss();
        }
        mdialog = new Dialog(mcontext, R.style.LoadingProgressBar);
        mdialog.setContentView(R.layout.layout_loading);
        mdialog.setCancelable(canCancel);
        mdialog.show();
        return this;
    }

    /**
     * 设置超时时间，默认20秒
     *
     * @param timeout
     * @return
     */
    public OkhttpUtils setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * 设置文件路径
     *
     * @param path
     * @return
     */
    public OkhttpUtils setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * 开始get请求
     *
     * @param url
     * @param listener
     */
    synchronized public void byGet(String url, OkhttpListener listener) {
        url += getParamStr(params);
        L.i(TAG, "gurl-->" + url);
        startrequest(url, 1, false, listener);

    }


    /**
     * 开始post 请求
     *
     * @param url
     * @param listener
     */
    synchronized public void byPost(String url, OkhttpListener listener) {
        L.i(TAG, "purl-->" + url + getParamStr(params));
        startrequest(url, 2, false, listener);
    }


    /**
     * 开始cachePost 请求
     *
     * @param url
     * @param listener
     */
    synchronized public void byCachePost(String url, OkhttpListener listener) {
        L.i(TAG, "purl-->" + url + getParamStr(params));
        if (aCache == null) {
            aCache = ACache.get(mcontext);
        }
        if (aCache.getAsString(url) != null && !aCache.getAsString(url).equals("")) {
            listener.onAccessComplete(true, aCache.getAsString(url), null, flag);
        }
        startrequest(url, 2, true, listener);
    }


    /**
     * 只读取缓存不请求
     *
     * @param url
     * @param listener
     */
    synchronized public void byOnlyCache(String url, OkhttpListener listener) {
        L.i(TAG, "purl-->" + url + getParamStr(params));
        if (aCache == null) {
            aCache = ACache.get(mcontext);
        }
        if (aCache.getAsString(url) != null && !aCache.getAsString(url).equals("")) {
            listener.onAccessComplete(true, aCache.getAsString(url), null, flag);
        }
    }


    /**
     * 开始post 上传文件
     *
     * @param url
     * @param listener
     */
    synchronized public void postFile(String url, OkhttpListener listener) {
        postAsynFile(url, listener);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param listener
     */
    synchronized public void downloadFile(String url, OkhttpListener listener) {
        downAsynFile(url, listener);
    }

    synchronized private void startrequest(String url, int method,
                                           boolean saveCache, OkhttpListener listener) {
        try {
            this.listener = listener;
            this.saveCache = saveCache;
            this.url = url;
            if (method == 2) {
                RequestBody body = null;
                okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
                Iterator iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    if (null != entry.getKey() && null != entry.getValue()) {
                        formEncodingBuilder.add(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
                body = formEncodingBuilder.build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
//                        .addHeader("AUTHORIZATION", header)
                        .addHeader("AUTHORIZATION", SPUtils.getPrefString(MyApplication.getContext(), Pkey.token,""))
                        .build();
                Call call = mOkHttpClient.newCall(request);
                MyCallback callback = new MyCallback(this);
                call.enqueue(callback);
            } else {
                Request.Builder requestBuilder = new Request.Builder().url(url);
                requestBuilder.method("GET", null);
//                requestBuilder.addHeader("AUTHORIZATION", header);
                requestBuilder.addHeader("AUTHORIZATION", SPUtils.getPrefString(MyApplication.getContext(), Pkey.token,""));
                Request request = requestBuilder.build();
                Call call = mOkHttpClient.newCall(request);
                MyCallback callback = new MyCallback(this);
                call.enqueue(callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步上传文件
     */
    private void postAsynFile(String url, OkhttpListener listener) {
        this.listener = listener;
        File file = new File(path);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        Call call = mOkHttpClient.newCall(request);
        MyCallback callback = new MyCallback(this);
        call.enqueue(callback);
    }

    /**
     * 异步下载文件
     */
    private void downAsynFile(String url, OkhttpListener listener) {
        this.listener = listener;
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(new File(path));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    private class MyCallback implements Callback {
        private OkhttpUtils net;

        public MyCallback(OkhttpUtils net) {
            this.net = net;
        }

        @Override
        public void onFailure(Call call, final IOException e) {
            L.e(TAG, "callback-->error:" + e.getMessage());

            if (!(net.mdialog == null || !net.mdialog.isShowing())) {
                net.mdialog.dismiss();
            }
            if (net.listener != null) {
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        net.listener.onAccessComplete(false, null, e, net.flag);
                    }
                });

            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            byte[] b = response.body().bytes(); //获取数据的bytes
            final String info = new String(b, "UTF-8"); //然后将其转为UTF-8
            L.i(TAG, "callback-->" + info);
            if (!(net.mdialog == null || !net.mdialog.isShowing())) {
                net.mdialog.dismiss();
            }
            if (net.listener != null) {
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        if (saveCache) {
                            aCache.put(OkhttpUtils.this.url, info);
                        }
                        net.listener.onAccessComplete(true, info, null, net.flag);
                    }
                });
            }

        }
    }

    /**
     * 获取参数(得到 ?a=12&b=123)
     *
     * @param params
     * @return
     * @author ping 2014-4-10 上午9:27:01
     */
    private String getParamStr(Map<String, Object> params) {
        StringBuffer bf = new StringBuffer("?");
        if (params != null) {
            for (String key : params.keySet()) {
                bf.append(key + "=" + params.get(key) + "&");
            }
        }
        String str = bf.toString();
        return str.substring(0, str.length() - 1);
    }

}
