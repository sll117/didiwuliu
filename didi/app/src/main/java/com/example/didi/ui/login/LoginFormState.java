package com.example.didi.ui.login;

import androidx.annotation.Nullable;

/**
 * 登录表单的状态
 */
class LoginFormState {
    @Nullable
    private Integer accountError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    LoginFormState(@Nullable Integer accountError, @Nullable Integer passwordError) {
        this.accountError = accountError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.accountError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getAccountError() {
        return accountError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
