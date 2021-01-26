package com.example.didi.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.didi.beans.UserInfoBean;

public class UserViewModel extends ViewModel {

    private MutableLiveData<UserInfoBean> mUser;

    public UserViewModel() {
        mUser = new MutableLiveData<>();
    }

    public void setUser(UserInfoBean userInfoBean){
        mUser.postValue(userInfoBean);
    }
    public LiveData<UserInfoBean> getUser() {
        return mUser;
    }

}