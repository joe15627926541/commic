package com.luoshunkeji.comic.utils.okhttp;


import android.database.Cursor;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 关闭操作工具类
 *
 * @author rj-liang
 * @date 16-03-1 下午2:40
 */
public class CloseUtils {
    private static final String TAG = "CloseUtils";

    public static final void close(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static final void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static final void close(Socket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static final void close(ServerSocket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}