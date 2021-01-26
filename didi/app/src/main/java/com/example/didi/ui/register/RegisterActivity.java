package com.example.didi.ui.register;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.didi.R;
import com.example.didi.beans.RegisterBean;
import com.example.didi.beans.SendBean;
import com.example.didi.utils.HttpUtils;
import com.example.didi.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup;
    private Handler mHandler = new Handler();
    private ProgressBar progressBar;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setActionBar();
        registerBtn = findViewById(R.id.btn_register);
        EditText nickNameEditText = findViewById(R.id.edit_text_nick_name);
        EditText accountEditText = findViewById(R.id.edit_text_account);
        EditText pwdEditText = findViewById(R.id.edit_text_password);
        mRadioGroup = findViewById(R.id.radio_group);
        progressBar = findViewById(R.id.loading);

        //检查数据是否有效
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean isValid = true;

                String str = nickNameEditText.getText().toString();
                if (!Utils.isNickNameValid(str)) {
                    isValid = false;
                    if (!str.isEmpty()) {
                        nickNameEditText.setError(getString(R.string.invalid_nick_name));
                    }

                }
                str = accountEditText.getText().toString();
                if (!Utils.isAccountValid(str)) {
                    isValid = false;
                    if (!str.isEmpty()) {
                        accountEditText.setError(getString(R.string.invalid_account));
                    }

                }
                str = pwdEditText.getText().toString();
                if (!Utils.isPasswordValid(str)) {
                    isValid = false;
                    if (!str.isEmpty()) {
                        pwdEditText.setError(getString(R.string.invalid_password));
                    }

                }
                registerBtn.setEnabled(isValid);

            }
        };
        nickNameEditText.addTextChangedListener(textWatcher);
        accountEditText.addTextChangedListener(textWatcher);
        pwdEditText.addTextChangedListener(textWatcher);
        Gson gson = new Gson();
        Type jsonType = new TypeToken<SendBean<Boolean>>() {
        }.getType();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoading(true);

                RegisterBean registerBeans = new RegisterBean();
                registerBeans.setPhone(accountEditText.getText().toString());
                registerBeans.setNickName(nickNameEditText.getText().toString());
                registerBeans.setPwd(pwdEditText.getText().toString());
                registerBeans.setType(getAccountType());
                OkHttpClient okHttpClient = HttpUtils.getOkHttpClient();

                String requestBody = gson.toJson(registerBeans);

                Log.d("register", requestBody);

                Request request = new Request.Builder()
                        .url(HttpUtils.BASE_URL + "/register")
                        .post(RequestBody.create(requestBody, HttpUtils.JSON))
                        .build();


                try {
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "无法连接到服务器", Toast.LENGTH_SHORT).show();
                                    setLoading(false);
                                }
                            });

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String json = response.body().string();
                            SendBean<Boolean> sendBeans = gson.fromJson(json, jsonType);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (sendBeans.getStatus().equals("ok")) {
                                        if (sendBeans.getData()) {
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, sendBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(RegisterActivity.this, sendBeans.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                    setLoading(false);
                                }
                            });
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 设置加载状态
     *
     * @param b
     */
    private void setLoading(boolean b) {
        registerBtn.setEnabled(!b);
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }

    /**
     * 获取选中的单选按钮的序号 0为货主，1为司机
     *
     * @return
     */
    private int getAccountType() {
        return mRadioGroup.getCheckedRadioButtonId() == R.id.radio_btn_owner ? 0 : 1;
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
