package com.example.didi.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.didi.R;
import com.example.didi.data.LoginRepository;
import com.example.didi.utils.Utils;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public void login(final String account, final String password,final int type) {
        // can be launched in a separate asynchronous job
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = loginRepository.login(account, password,type);

                loginResult.postValue(result);
            }
        }).start();
    }

    /**
     * 判断账号密码是否有效
     * @param account
     * @param password
     */
    public void loginDataChanged(String account, String password) {
        if (!Utils.isAccountValid(account)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_account, null));
        } else if (!Utils.isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }



}
