package com.luoshunkeji.comic.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * MD5加密
 * @author WWJ
 * @date 2014-9-3 下午2:56:33 
 * @param
 */
public class Md5 {

	// 加密字符
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getNowTime() {
		SimpleDateFormat date = new SimpleDateFormat("HHmmss");
		return date.format(new java.util.Date());
	}

	// 获取当时时间
	public static String getNowTime2() {
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return date.format(new java.util.Date());
	}

	// 获取密钥
	public static String getKey() {
		String str = Md5.MD5(getNowTime() + Math.random());
		return str;
	}

	// 获取随机6个数字
	public static int getRandoMnumber() {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (999999 - 100000 + 1) + 100000;
	}

	// //获取能插进mysql时间
	// public static java.sql.Date SetCreateUserDate(){
	// SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	// String str=date.format(new java.util.Date());
	// java.sql.Date date2=java.sql.Date.valueOf(str);
	// back date2;
	//
	// }

	// public static void main(String[] args) {
	// System.out.println(Md5.MD5("123456" + "zhongdafuliao"));
	// // 123:202cb962ac59075b964b07152d234b70
	// // 12:c20ad4d76fe97759aa27a0c99bff6710
	// // c8b2f17833a4c73bb20f88876219ddcd
	// // 1234:81dc9bdb52d04dc20036dbd8313ed055
	//
	// }

}
