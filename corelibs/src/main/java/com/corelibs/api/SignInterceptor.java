package com.corelibs.api;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by john on 2017/9/27.
 */

public class SignInterceptor implements Interceptor {
    public SignInterceptor() {}
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        //拼接timestamp
        String url = request.url().toString() + "&timestamp=" + timestamp;
        //获取参数列表
        String[] parts = url.split("\\?");
        //TreeMap里面的数据会按照key值自动升序排列
        TreeMap<String, String> param_map = new TreeMap<String, String>();
        //获取参数对
        String[] param_pairs = parts[1].split("&");
        for (String pair : param_pairs) {
            String[] param = pair.split("=");
            if (param.length != 2) {
                //没有value的参数不进行处理
                continue;
            }
            param_map.put(param[0], param[1]);
        }
        StringBuilder sign = new StringBuilder();
        Iterator it = param_map.keySet().iterator();
        //拼接参数
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = param_map.get(key).toString();
            sign.append(key + value);
        }
        //Base64加密
//        String sign_s = android.util.Base64.encodeToString(sign.toString().getBytes(), android.util.Base64.NO_WRAP);
//        //Md5加密
//        sign_s = MD5.GetMD5Code(sign_s);
        //重新拼接url
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        //添加参数
//        httpUrlBuilder.addQueryParameter("sign", sign_s);
        httpUrlBuilder.addQueryParameter("timestamp", timestamp);
        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.url(httpUrlBuilder.build());
        request = requestBuilder.build();
        return chain.proceed(request);
    }
}
