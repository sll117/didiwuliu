package com.example.didi.ui.notifications;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didi.R;
import com.example.didi.beans.OrderItem;
import com.example.didi.beans.SendBean;
import com.example.didi.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private OrderAdapter mOrderAdapter;

    private Handler mHandler=new Handler();
    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        mRecyclerView=root.findViewById(R.id.recycler_view);

        mViewModel.getItems().observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
                mOrderAdapter.setList(orderItems);
                mOrderAdapter.notifyDataSetChanged();
                int size=mOrderAdapter.getItemCount();
                if(size>0)
                {
                    mRecyclerView.scrollToPosition(mOrderAdapter.getItemCount()-1);
                }

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mOrderAdapter=new OrderAdapter(null);
        mRecyclerView.setAdapter(mOrderAdapter);

        getOrderItems();
    }

    /**
     * 从服务器获取订单数据
     */
    private void getOrderItems()
    {
        Request request = new Request.Builder()
                .url(HttpUtils.BASE_URL + "/orderitem")
                .build();
        HttpUtils.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"服务器不可用", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                Log.d("orderItems",json);
                SendBean<List<OrderItem>> sendBean = gson.fromJson(json
                        , new TypeToken<SendBean<List<OrderItem>>>() {
                        }.getType());
                if (sendBean.getStatus().equals("ok")) {
                    if(sendBean.getData()!=null)
                    {
                        mViewModel.updateItems(sendBean.getData());
                    }
                }else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"获取订单数据失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
