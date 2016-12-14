package com.netsun.toocle.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/12/12.
 */

public class HttpUtil {
    public static final int HTTP_GET = 1;
    public static final int HTTP_POST = 2;

    public static void getHttp(final String url, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();//创建OKHttpClient实例
        Request request = new Request.Builder().url(url).build();// 创建一个Request对象
        client.newCall(request).enqueue(callback);//调用client的enqueue()方法获取服务器返回数据
    }
}
