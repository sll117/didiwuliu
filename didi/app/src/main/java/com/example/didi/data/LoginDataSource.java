package com.example.didi.data;


import android.util.Log;

import com.example.didi.beans.LoginBean;
import com.example.didi.beans.SendBean;
import com.example.didi.beans.UserInfoBean;
import com.example.didi.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    /**
     * @param account
     * @param password
     * @param type     0代表货主，1代表司机
     * @return
     */
    public Result<UserInfoBean> login(String account, String password, int type) {

        try {
            // TODO: handle loggedInUser authentication
            Gson gson = new Gson();
            OkHttpClient okHttpClient = HttpUtils.getOkHttpClient();
            LoginBean loginBeans = new LoginBean();
            loginBeans.setPhone(account);
            loginBeans.setPwd(password);
            loginBeans.setType(type);

            String requestBody = gson.toJson(loginBeans);


            Request request = new Request.Builder()
                    .url(HttpUtils.BASE_URL + "/login")
                    .post(RequestBody.create(requestBody, HttpUtils.JSON))
                    .build();
            Response response = okHttpClient.newCall(request).execute();

            String json = response.body().string();
            Log.d("login", json);

            if (!json.isEmpty()) {
                SendBean<UserInfoBean> result = gson.fromJson(json,
                        new TypeToken<SendBean<UserInfoBean>>() {
                        }.getType());
                if (result.getStatus().equals("ok")) {
                    if (result.getData() != null) {

                        DataShare.setUser(result.getData());

                    }
                    return new Result.Success<>(result.getData());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
        return new Result.Error(new Exception("Error logging in"));

    }

    public boolean logout() {
        // TODO: revoke authentication
        OkHttpClient okHttpClient = HttpUtils.getOkHttpClient();
        Request request = new Request.Builder().url(HttpUtils.BASE_URL + "/logout").build();

        try {
            Response response=okHttpClient.newCall(request).execute();
            String json = response.body().string();
            Log.d("logout",json);
            if (!json.isEmpty()) {
                Gson gson = new Gson();
                SendBean<Boolean> sendBean = gson.fromJson(json,new TypeToken<SendBean<Boolean>>(){}.getType());
                if(sendBean.getStatus().equals("ok"))
                {
                    return sendBean.getData();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isLoggedIn(){
        OkHttpClient okHttpClient = HttpUtils.getOkHttpClient();
        Request request = new Request.Builder()
                .url(HttpUtils.BASE_URL + "/islogin")
                .build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            String json = response.body().string();
            Log.d("logout",json);
            if (!json.isEmpty()) {
                Gson gson = new Gson();
                SendBean<Boolean> sendBean = gson.fromJson(json,new TypeToken<SendBean<Boolean>>(){}.getType());

                if(sendBean.getStatus().equals("ok"))
                {
                    return sendBean.getData();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
