package com.luoshunkeji.comic.utils.okhttp;

import java.io.File;

import okhttp3.Response;

/**
 * 文件传输的回调
 */
public interface ITransferListener {

    void onStart();

    /**
     * 当传输进度发生变化时，会回调此方法。
     *
     * @param progress 当前的传输进度，参数值的范围是0-100。
     */
    void onProgress(int progress);

    /**
     * 传输完成的通知
     */
    void onSuccess(Response response, File target);

    /**
     * 传输失败通知
     */
    void onFailed(Exception e);
}