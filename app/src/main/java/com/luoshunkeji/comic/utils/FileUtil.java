package com.luoshunkeji.comic.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class FileUtil {
	/**
	 * Assets文件复制到sdcrad(覆盖)
	 *
	 * @param myContext
	 * @param ASSETS_NAME 要复制的文件名
	 * @param savePath    要保存的路径
	 * @param saveName    复制后的文件名
	 */
	public static boolean AssetsFilecopy(Context myContext, String ASSETS_NAME,
                                         String savePath, String saveName) {
		String filename = savePath + "/" + saveName;
		File dir = new File(savePath);
		// 如果目录不中存在，创建这个目录
		if (!dir.exists())
			dir.mkdir();
		try {
			// if (!(new File(filename)).exists()) { //不覆盖
			InputStream is = myContext.getResources().getAssets()
					.open(ASSETS_NAME);
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] buffer = new byte[7168];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deletefile(String filepath, String filename) {
		File file = new File(filepath + "/" + filename);
		if (file.exists()) {
			return file.delete();
		} else {
			return true;
		}
	}

	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public static long getfilesize(String filepath) {
		File file = new File(filepath);
		if (file.isFile()) {
			return file.length();
		} else {
			return -1;
		}
	}


	// public void testCopy(Context context) {
	// String path=context.getFilesDir().getAbsolutePath();
	// String name="test.txt";
	// CopyFileFromAssets.copy(context, name, path, name);
	// }

	public static String file2str(String filename) {
		return file2str(new File(filename));
	}

	public static String file2str(File file) {
		String fileContent = "";
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuffer buffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			fileContent = buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	public static boolean save2File(String str, String filename) {
		return save2File(str, Environment.getExternalStorageDirectory()
				.getAbsolutePath(), filename);
	}

	public static boolean save2File(String str, String path, String filename) {
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();
		try {
			File file = new File(dir, filename);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(str.getBytes());
			fos.close();
			return true;
		} catch (Exception e) {
		}
		return true;
	}


	public static File saveBitmapFile(Activity activity, Bitmap bitmap) {
		File file = new File(activity.getExternalCacheDir(), Long.toString(System.currentTimeMillis()) + ".jpg");//将要保存图片的路径
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return file;
	}
	public static File saveBitmapFile(Context activity, Bitmap bitmap) {
		File file = new File(activity.getExternalCacheDir(), Long.toString(System.currentTimeMillis()) + ".jpg");//将要保存图片的路径
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return file;
	}

}
