package com.example.didi.data;

import com.example.didi.beans.PathBean;
import com.example.didi.beans.SearchBean;
import com.example.didi.beans.UserInfoBean;

import java.util.List;

public class DataShare {
    private static UserInfoBean user;
    private static SearchBean sSearchBean;
    private static List<PathBean> sPathBeans;
    private static boolean loadCookie=false;

    public static SearchBean getsSearchBean() {
        return sSearchBean;
    }

    public static void setsSearchBean(SearchBean sSearchBean) {
        DataShare.sSearchBean = sSearchBean;
    }

    public static List<PathBean> getsPathBeans() {
        return sPathBeans;
    }

    public static void setsPathBeans(List<PathBean> sPathBeans) {
        DataShare.sPathBeans = sPathBeans;
    }

    public static boolean isLoadCookie() {
        return loadCookie;
    }

    public static void setLoadCookie(boolean loadCookie) {
        DataShare.loadCookie = loadCookie;
    }

    public static List<PathBean> getPathBeans() {
        return sPathBeans;
    }

    public static void setPathBeans(List<PathBean> pathBeans) {
        sPathBeans = pathBeans;
    }

    public static SearchBean getSearchBean() {
        return sSearchBean;
    }

    public static void setSearchBean(SearchBean searchBean) {
        sSearchBean = searchBean;
    }

    public static UserInfoBean getUser() {
        return user;
    }

    public static void setUser(UserInfoBean user) {
        DataShare.user = user;
    }
}
