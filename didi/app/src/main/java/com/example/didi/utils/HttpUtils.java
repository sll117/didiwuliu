package com.example.didi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.didi.beans.SendBean;
import com.example.didi.beans.UserInfoBean;
import com.example.didi.data.DataShare;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {
    private static final String FILE_COOKIE = "cookie.json";
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    public static final String BASE_URL = "http://10.0.2.2:8080";
    private static MyCookieJar cookieJar = new MyCookieJar();
    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().cookieJar(cookieJar).connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS).build();

    private static class MyCookieJar implements CookieJar {

        private Map<String, List<Cookie>> cookieStore = new HashMap<>();

        public Map<String, List<Cookie>> getCookieStore() {
            return cookieStore;
        }

        @NotNull
        @Override
        public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
            List<Cookie> cookies = cookieStore.get(httpUrl.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }

        @Override
        public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
            cookieStore.put(httpUrl.host(), list);
        }
    }

    /**
     * 保存Cookie到文件
     *
     * @param context
     */
    public static void saveCookies(Context context) {

        HttpUrl url = HttpUrl.parse(BASE_URL);
        if (url != null) {
            List<Cookie> cookies = cookieJar.getCookieStore().get(url.host());

            if (cookies != null) {
                String str = null;
                for (Cookie cookie : cookies) {
                    if (cookie.name().equals("JSESSIONID")) {
                        str = cookie.name() + "=" + cookie.value();
                        break;
                    }
                }
                if (str != null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("cookie", str);
                    //步骤4：提交
                    editor.apply();
                }
            }


        }


    }

    public static boolean updateUserInfoFromInternet() {

        Gson gson =new Gson();

        Request request = new Request.Builder()
                .url(HttpUtils.BASE_URL + "/userinfo")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String json = response.body().string();
            Log.d("userinfo",json);
            if (!json.isEmpty()) {
                SendBean<UserInfoBean> result = gson.fromJson(json
                        , new TypeToken<SendBean<UserInfoBean>>() {
                        }.getType());
                if (result.getStatus().equals("ok")) {
                    UserInfoBean user = result.getData();
                    if (user != null) {
                        DataShare.setUser(user);
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从文件读取cookie
     *
     * @param context
     * @return
     */
    public static void readCookie(Context context) {
        if(DataShare.isLoadCookie())
            return;

        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String cookieStr=sharedPreferences.getString("cookie", "");
        HttpUrl url=HttpUrl.parse(BASE_URL);

        if(url!=null&&!cookieStr.isEmpty())
        {
            Cookie cookie=Cookie.parse(url,cookieStr);
            if(cookie!=null)
            {
                String host=url.host();
                List<Cookie> list=cookieJar.getCookieStore().get(host);
                if(list==null)
                {
                    list=new ArrayList<>();

                }
                list.add(cookie);
                cookieJar.getCookieStore().put(host,list);

            }


        }
        DataShare.setLoadCookie(true);
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
