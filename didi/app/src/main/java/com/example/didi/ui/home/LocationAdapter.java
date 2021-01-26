package com.example.didi.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didi.R;
import com.example.didi.beans.PathBean;
import com.example.didi.utils.Utils;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private List<PathBean> mList;
    public LocationAdapter(List<PathBean> list) {
        mList = list;
    }

    public List<PathBean> getList() {
        return mList;
    }

    public void setList(List<PathBean> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        View view=holder.itemView;
        TextView textView=view.findViewById(R.id.tv_location);
        PathBean pathBean=mList.get(position);
        textView.setText(pathBean.getLocation());
        textView=view.findViewById(R.id.tv_carriage);
        textView.setText(Utils.formatBalance(pathBean.getCarriage()));



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
