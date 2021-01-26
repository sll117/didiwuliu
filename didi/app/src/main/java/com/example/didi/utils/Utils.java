package com.example.didi.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.didi.beans.LoginBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 检查昵称是否为2-16个字符
     *
     * @param nickName
     * @return
     */
    public static boolean isNickNameValid(String nickName) {
        int len = nickName.length();
        if (len > 2 && len < 16) {
            Pattern pattern = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\\\\\.<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]");
            Matcher matcher = pattern.matcher(nickName);
            return !matcher.find();
        }
        return false;
    }

    /**
     * 检查密码是否为8-16位并且包含数字和字母
     *
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {

        if (password == null)
            return false;
        int length = password.length();

        if (length >= 8 && length <= 16) {
            boolean hasNum = false, hasChar = false;
            for (int i = 0; i < length; i++) {
                char ch = password.charAt(i);
                if (Character.isLetter(ch)) {
                    hasChar = true;
                } else if (Character.isDigit(ch)) {
                    hasNum = true;
                }
                if (hasChar && hasNum)
                    return true;
            }
        }
        return false;
    }

    public static boolean isAccountValid(String account) {
        if (account == null || account.trim().isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");
        return pattern.matcher(account).matches();
    }

    public static String getJsonFromStream(InputStream is) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 保留两位小数
     *
     * @param money
     * @return
     */
    public static String formatBalance(float money) {
        //构造方法的字符格式这里如果小数不足2位,会以0补足
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        //format 返回的是字符串
        return decimalFormat.format(money);
    }

    /**
     * 从sharedPreferences删除用户账号信息
     *
     * @param context
     */
    public static void removeUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("account");
        editor.remove("pwd");
        editor.remove("type");

        editor.apply();
    }

    /**
     * 保存用户账号密码信息到sharedPreferences
     *
     * @param context
     * @param loginBean
     */
    public static void saveUser(Context context, LoginBean loginBean) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("account", loginBean.getPhone());
        editor.putString("pwd", loginBean.getPwd());
        editor.putInt("type", loginBean.getType());
        //步骤4：提交
        editor.apply();
    }

    /**
     * 从sharedPreferences读取用户账号密码信息
     *
     * @param context
     * @return
     */
    public static LoginBean loadUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        LoginBean loginBean = null;
        String phone = sharedPreferences.getString("account", "");
        String pwd = sharedPreferences.getString("pwd", "");
        int type = sharedPreferences.getInt("type", -1);
        if (!phone.isEmpty() && !pwd.isEmpty() && type != -1) {
            loginBean = new LoginBean();
            loginBean.setPhone(phone);
            loginBean.setPwd(pwd);
            loginBean.setType(type);
        }

        return loginBean;
    }
}
