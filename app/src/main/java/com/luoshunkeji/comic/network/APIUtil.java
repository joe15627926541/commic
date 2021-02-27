package com.luoshunkeji.comic.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APIUtil {
    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
    };

    private APIUtil() {
    }

    /**
     * 生成指定长度的随机字符串（大小写，数字）
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(DIGITS[r.nextInt(DIGITS.length)]);
        }
        return sb.toString();
    }

    /**
     * 签名，不加盐
     *
     * @param map
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSign(Map<String, Object> map, String key) throws NoSuchAlgorithmException {
        return getSign(map, key, null);
    }

    /**
     * 签名，参数按字典排序，拼接字符串，加上key，再加上盐，最后md5，大写
     *
     * @param map
     * @param key
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSign(Map<String, Object> map, String key, String salt) throws NoSuchAlgorithmException {
        List<Map.Entry<String, Object>> list = new ArrayList<>(map.entrySet());
        if (list.isEmpty()) {
            return null;
        }
        // 按字典排序
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuilder sb = new StringBuilder();
        // 拼接参数，不用URLEncode
        Iterator<Map.Entry<String, Object>> iter = list.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            if (!"sign".equals(entry.getKey()) && entry.getValue() != null && !TextUtils.isEmpty(entry.getValue().toString())) {
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue().toString());
                sb.append('&');
            }
        }
        // 加上key
        sb.append("key=");
        sb.append(key);
        // 加盐
        if (salt != null) {
            sb.append(salt);
        }




        return Utils.hexString(Utils.MD5(sb.toString())).toUpperCase();
    }
    /**
     * 去除空格回车符等
     *
     * @param s
     */
    public static String removeSpace(String s) {

        String regex = "\\s";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.replaceAll("");
    }

    /**
     * 拼接参数为URL格式的字符串
     *
     * @param params
     * @return
     */
    public static String encodeParameters(Map<String, Object> params) {
        String paramsEncoding = "utf-8";
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (sb.length() > 0) {
                    sb.append('&');
                }
                sb.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue().toString(), paramsEncoding));
            }
        } catch (UnsupportedEncodingException uee) {
            Log.e("haha", "Encoding not supported: " + paramsEncoding, uee);
        }
        return sb.toString();
    }


    /**
     * Map对象 转换为 XML字符串
     *
     * @param parameters
     * @return
     */
    public static String map2Xml(Map<String, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Iterator<Map.Entry<String, Object>> iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue().toString();

            sb.append("<");
            sb.append(key);
            sb.append("><![CDATA[");
            sb.append(value);
            sb.append("]]></");
            sb.append(key);
            sb.append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * XML字符串 转换为 Map对象
     * 只解释第二层（第一层为<xml></xml>），三层以上将被忽略
     *
     * @param xmlString
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static Map<String, String> xml2Map(String xmlString) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(new StringReader(xmlString));

        Map<String, String> map = new HashMap<>();
        String key = null;
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
            } else if (eventType == XmlPullParser.START_TAG) {
                if (xpp.getDepth() == 2) {
                    key = xpp.getName();
                }
            } else if (eventType == XmlPullParser.TEXT) {
                if (xpp.getDepth() == 2 && !TextUtils.isEmpty(key)) {
                    map.put(key, xpp.getText());
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                if (xpp.getDepth() == 2) {
                    key = null;
                }
            }
            eventType = xpp.next();
        }

        return map;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
