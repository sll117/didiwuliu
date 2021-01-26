package com.example.didi.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didi.R;
import com.example.didi.beans.OrderItem;
import com.example.didi.data.DataShare;
import com.example.didi.utils.Utils;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private List<OrderItem> mList;

    public OrderAdapter(List list) {
        mList = list;
    }

    public List getList() {
        return mList;
    }

    public void setList(List list) {
        mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        View view=holder.itemView;
        OrderItem item=mList.get(position);
        TextView tv=view.findViewById(R.id.tv_name_type);
        //货主登录显示司机，司机登录显示货主
        if(DataShare.getUser().getType()==0)//货主
        {
            tv.setText(R.string.deriver);
        }else {//司机
            tv.setText(R.string.owner);
        }

        tv=view.findViewById(R.id.tv_order_id);
        tv.setText(String.valueOf(item.getOrderID()));

        tv=view.findViewById(R.id.tv_start);
        tv.setText(item.getStart());

        tv=view.findViewById(R.id.tv_end);
        tv.setText(item.getEnd());

        tv=view.findViewById(R.id.tv_nick_name);
        tv.setText(item.getNickName());

        tv=view.findViewById(R.id.tv_account);
        tv.setText(String.valueOf(item.getAccount()));

        tv=view.findViewById(R.id.tv_phone);
        tv.setText(item.getPhone());

        tv=view.findViewById(R.id.tv_price);
        tv.setText(Utils.formatBalance(item.getPrice()));


    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
