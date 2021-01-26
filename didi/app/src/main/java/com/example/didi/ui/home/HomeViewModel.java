package com.example.didi.ui.home;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.didi.beans.PathBean;
import com.example.didi.beans.PathInfoBean;
import com.example.didi.beans.SearchBean;
import com.example.didi.beans.SendBean;
import com.example.didi.data.DataShare;
import com.example.didi.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<PathBean>> mPathData;
    private MutableLiveData<List<PathInfoBean>> mSearchDrivers;

    public HomeViewModel() {
        mSearchDrivers = new MutableLiveData<>();
        mPathData = new MutableLiveData<>();
    }

    public LiveData<List<PathBean>> getPathData() {
        return mPathData;
    }

    public LiveData<List<PathInfoBean>> getDrivers() {
        return mSearchDrivers;
    }

    /**
     * 搜索司机的路线信息
     */
    public void updatePath() {
        Request request = new Request.Builder()
                .url(HttpUtils.BASE_URL + "/path")
                .build();
        HttpUtils.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                SendBean<List<PathBean>> sendBean = gson.fromJson(json
                        , new TypeToken<SendBean<List<PathBean>>>() {
                        }.getType());
                if (sendBean.getStatus().equals("ok")) {
                    mPathData.postValue(sendBean.getData());
                    DataShare.setPathBeans(sendBean.getData());
                }
            }
        });
    }

    /**
     * 根据出发地点和终点 搜索司机信息
     * @param searchBean
     */
    public void updateDriver(SearchBean searchBean) {

        if(!TextUtils.isEmpty(searchBean.getStart())&&!TextUtils.isEmpty(searchBean.getEnd()))
        {
            Gson gson = new Gson();
            String json = gson.toJson(searchBean);
            Request request = new Request.Builder()
                    .url(HttpUtils.BASE_URL + "/search")
                    .post(RequestBody.create(json, HttpUtils.JSON))
                    .build();
            HttpUtils.getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String json = response.body().string();
                    Log.d("search",json);
                    Gson gson = new Gson();
                    Log.d("search",json);
                    SendBean<List<PathInfoBean>> sendBean = gson.fromJson(json
                            , new TypeToken<SendBean<List<PathInfoBean>>>() {
                            }.getType());
                    if (sendBean.getStatus().equals("ok")) {
                        if(sendBean.getData()!=null)
                        {
                            mSearchDrivers.postValue(sendBean.getData());
                            //保存终点与起点
                            DataShare.setSearchBean(searchBean);
                        }

                    }
                }
            });
        }
    }
}