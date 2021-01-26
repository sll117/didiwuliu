package com.example.didi.ui.login;

import com.example.didi.beans.UserInfoBean;

public class LoginResult {
    private Boolean success;
    private String Error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}
