package com.example.didi.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.didi.R;
import com.example.didi.beans.SendBean;
import com.example.didi.beans.UserInfoBean;
import com.example.didi.data.DataShare;
import com.example.didi.data.LoginRepository;
import com.example.didi.ui.login.LoginActivity;
import com.example.didi.utils.HttpUtils;
import com.example.didi.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserFragment extends Fragment {

    private UserViewModel mUserViewModel;
    private Handler mHandler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mUserViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        final TextView tvAccount = root.findViewById(R.id.tv_account);
        final TextView tvNickName = root.findViewById(R.id.tv_nick_name);
        final TextView tvPhone = root.findViewById(R.id.tv_phone);
        final TextView tvSex = root.findViewById(R.id.tv_sex);
        TextView tvBalance = root.findViewById(R.id.tv_balance);
        Button button = root.findViewById(R.id.btn_logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (LoginRepository.getInstance().logout(getActivity())) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                    Activity activity = getActivity();
                                    if (activity != null) {
                                        activity.finish();
                                    }
                                }
                            });
                        } else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "注销失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        button = root.findViewById(R.id.btn_add_balance);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = getLayoutInflater().inflate(R.layout.dialog_add_balance, null);
                EditText editText = v.findViewById(R.id.editText);
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("充值")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                addBalance(editText.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setView(v)
                        .create();

                alertDialog.show();

            }
        });

        mUserViewModel.getUser().observe(this, new Observer<UserInfoBean>() {
            @Override
            public void onChanged(UserInfoBean user) {
                tvAccount.setText(String.valueOf(user.getId()));
                tvNickName.setText(user.getNickName());
                tvPhone.setText(user.getPhone());
                tvSex.setText(user.getSex());
                tvBalance.setText(Utils.formatBalance(user.getBalance()));
            }
        });

        updateView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(HttpUtils.updateUserInfoFromInternet())
                {
                    updateView();
                }

            }
        }).start();
        return root;
    }

    private void addBalance(String balance) {
        if (TextUtils.isEmpty(balance))
            return;
        OkHttpClient okHttpClient = HttpUtils.getOkHttpClient();
        Gson gson = new Gson();

        Request request = new Request.Builder()
                .url(HttpUtils.BASE_URL + "/addbalance?money=" + balance)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Log.d("update", json);
                if (!json.isEmpty()) {
                    SendBean<Boolean> result = gson.fromJson(json
                            , new TypeToken<SendBean<Boolean>>() {
                            }.getType());
                    if (result.getStatus().equals("ok")) {
                        if (result.getData()) {
                            if(HttpUtils.updateUserInfoFromInternet())
                            {
                                updateView();
                            }
                        } else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "充值失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }
            }

        });
    }

    private void updateView() {
        mUserViewModel.setUser(DataShare.getUser());
    }
}