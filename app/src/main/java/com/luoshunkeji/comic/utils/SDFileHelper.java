package com.luoshunkeji.comic.utils;//package com.luoshunkeji.yuelm.utils;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.os.Environment;
//import android.widget.Toast;
//
//
//
//import java.io.File;
//import java.io.FileOutputStream;
//
///**
// * Created by SongNo1 on 2017/10/17.
// */
//
////这个类是利用Glide本身的功能来下载图片
//
//public class SDFileHelper {
//
//    private Context context;
//
//    public SDFileHelper() {
//    }
//
//    public SDFileHelper(Context context) {
//
//        this.context = context;
//
//    }
//
//    private String FP = "";
//
//    /**
//     * Glide保存图片
//     *
//     * @param fileName 你要保存的图片名字
//     * @param url      网络图片的链接
//     */
//    public String savePicture(final String fileName, final String url) {
//
////        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
////            @Override
////            public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
////                try {
////                    FP = savaFileToSD(fileName, resource);
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        });
//
//        return FP;
//
//    }
//
//    /**
//     * @param filename 同上
//     * @param bytes
//     * @throws Exception
//     */
//    public String savaFileToSD(String filename, byte[] bytes) throws Exception {
//
//        String FP = "";
//
//        //如果手机已插入sd卡,且app具有读写sd卡的权限
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + AppUtil.getAppName() + "/";
//            File dir1 = new File(filePath);
//            if (!dir1.exists()) {
//                dir1.mkdirs();
//            }
//            filename = filePath + "/" + filename;
//            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
//            FileOutputStream output = new FileOutputStream(filename);
//            output.write(bytes);
//            //将bytes写入到输出流中
//            output.close();
//            //关闭输出流
//            Toast.makeText(context, "图片已成功保存到" + filePath, Toast.LENGTH_SHORT).show();
//
//            FP = filename;
//
//        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
//
//        return FP;
//    }
//
//}
