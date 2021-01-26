package com.example.didi.data;

import android.content.Context;

import com.example.didi.beans.UserInfoBean;
import com.example.didi.utils.Utils;

/**
 * 该类从服务器请求身份验证和用户信息，并维护登录状态和用户凭据信息的内存缓存。
 *
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;


    //单例 懒汉模式
    private LoginRepository() {
        this.dataSource = new LoginDataSource();
    }
    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public boolean isLoggedIn(){
        return dataSource.isLoggedIn();
    }

    public boolean logout(Context context) {
        if (dataSource.logout()) {
            DataShare.setUser(null);
            Utils.removeUser(context);
            return true;
        } else {
            return false;
        }
    }

    private void setLoggedInUser(UserInfoBean user) {
        DataShare.setUser(user);
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public boolean login(String account, String password, int type) {
        // handle login
        Result<UserInfoBean> result = dataSource.login(account, password,type);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<UserInfoBean>) result).getData());
            return true;
        }
        return false;
    }
}
