package com.luoshunkeji.comic.utils.okhttp;

import android.util.Log;

import androidx.annotation.WorkerThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpUtils {
    private static String TAG = "OkHttpUtils";

    public static OkHttpClient.Builder getDefaultOkHttpBuilder() {
        int cacheSize = 100 * 1024 * 1024; // 100 MiB

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {
                //HttpLoggingInterceptor.Logger.DEFAULT.log(message);

                // Split by line, then ensure each line can fit into Log's maximum length.
                for (int i = 0, length = message.length(); i < length; i++) {
                    int newline = message.indexOf('\n', i);
                    newline = newline != -1 ? newline : length;
                    do {
                        int end = Math.min(newline, i + HttpLoggingInterceptor.MAX_LOG_LENGTH);
                        Log.d(TAG, message.substring(i, end));
                        i = end;
                    } while (i < newline);
                }
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(logging); // add logging as last interceptor
    }

    public static OkHttpClient getOkHttpClient() {
        // 创建使用 SSL 协议的 http client.
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, null);
        } catch (Exception e) {
            Log.e(TAG, "getOkHttpClient failed", e);
        }

        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        return getDefaultOkHttpBuilder()
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(hostnameVerifier)
                .build();
    }

    /**
     * 通过okhttp下载文件
     *
     * @param url      文件的url
     * @param filePath 文件的存放地址，如果该文件已经存在，将对下载的文件加上"(x)"，防止重名
     * @param listener 下载监听器
     */
    @WorkerThread
    public static void downloadFile(final String url, final String filePath, final ITransferListener listener) {
        OkHttpClient client = getOkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    Log.e(TAG, "downloadFile failure.url:" + url, e);
                    listener.onFailed(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                FileOutputStream fos = null;

                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    int len = 0;
                    int currentProgress = 0;

                    String tempFile = filePath + ".downloading." + UUID.randomUUID();

                    File file = new File(tempFile);

                    fos = new FileOutputStream(file);
                    long totalBytesRead = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        totalBytesRead += len;
                        int progress = (int) (totalBytesRead * 1.0f * 100 / total);

                        if (listener != null && currentProgress != progress) {
                            listener.onProgress(progress);
                        }
                    }

                    // 下载完成
                    fos.flush();

                    // 把下载的文件重命名，注意不要和别的文件重命名
                    File target = new File(filePath);
                    while (target.exists()) {
                        target = new File(FileUtils.renameIncrementFilename(target.getAbsolutePath()));
                    }
                    FileUtils.renameFile(tempFile, target.getAbsolutePath());

                    Log.i(TAG, "downloadFile success:" + url);
                    if (listener != null) {
                        listener.onSuccess(response, target);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "downloadFile process response failure.url:" + url, e);
                    if (listener != null) {
                        listener.onFailed(e);
                    }
                } finally {
                    CloseUtils.close(is);
                    CloseUtils.close(fos);
                }
            }
        });
    }



}
