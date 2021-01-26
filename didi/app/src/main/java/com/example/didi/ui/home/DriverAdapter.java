package com.example.didi.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didi.R;
import com.example.didi.beans.PathInfoBean;
import com.example.didi.ui.driverdetail.DriverDetailActivity;
import com.example.didi.utils.Utils;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyViewHolder> {
    private List<PathInfoBean> mList;
    private Activity mActivity;
    public DriverAdapter(List<PathInfoBean> list, Activity activity) {
        mList = list;
        mActivity =activity;
    }

    public List<PathInfoBean> getList() {
        return mList;
    }

    public void setList(List<PathInfoBean> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public DriverAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new DriverAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter.MyViewHolder holder, int position) {
        View view=holder.itemView;
        PathInfoBean path=mList.get(position);
        TextView textView=view.findViewById(R.id.tv_nick_name);
        textView.setText(path.getNickName());

        textView=view.findViewById(R.id.tv_phone);
        textView.setText(path.getPhone());

        TextView carriageTv=view.findViewById(R.id.tv_carriage);
        carriageTv.setText(Utils.formatBalance(path.getPrice()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mActivity, DriverDetailActivity.class);
                i.putExtra(DriverDetailActivity.EXTRA_NICK_NAME,path.getNickName());
                i.putExtra(DriverDetailActivity.EXTRA_PHONE,path.getPhone());
                i.putExtra(DriverDetailActivity.EXTRA_ID,path.getId());
                mActivity.startActivityForResult(i,1);
            }
        });
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
