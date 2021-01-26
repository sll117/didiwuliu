package com.example.didi.ui.edit;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didi.R;
import com.example.didi.beans.PathBean;

import java.util.List;

public class LocationEditAdapter extends RecyclerView.Adapter<LocationEditAdapter.MyViewHolder> {
    public List<PathBean> mList;
    public LocationEditAdapter(List<PathBean> list) {
        mList = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_edit_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        View view=holder.itemView;
        EditText locationEt=view.findViewById(R.id.et_location);
        EditText carriageEt=view.findViewById(R.id.et_carriage);
        Log.d("position","位置："+position);


        if(holder.mLocationTextWatcher ==null)
        {
            holder.mLocationTextWatcher =new LocationTextWatcher(position);
            locationEt.addTextChangedListener(holder.mLocationTextWatcher);
        }else {
            holder.mLocationTextWatcher.updatePosition(position);
        }

        if(holder.mCarriageTextWatcher==null)
        {
            holder.mCarriageTextWatcher=new CarriageTextWatcher(position);
            carriageEt.addTextChangedListener(holder.mCarriageTextWatcher);
        }else {
            holder.mCarriageTextWatcher.updatePosition(position);
        }

        PathBean pathBean=mList.get(position);
        locationEt.setText(pathBean.getLocation());
        carriageEt.setText(String.valueOf(pathBean.getCarriage()));

        ImageButton addBtn=view.findViewById(R.id.imgBtn_add);
        ImageButton removeBtn=view.findViewById(R.id.imgBtn_remove);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position>=mList.size()-1)
                {
                    mList.add(new PathBean());
                }else{
                    mList.add(position+1,new PathBean());
                }
                notifyDataSetChanged();
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    private class LocationTextWatcher implements TextWatcher{
        protected int mPosition;

        public LocationTextWatcher(int position) {
            this.mPosition = position;
        }
        public void updatePosition(int position)
        {
            this.mPosition=position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            mList.get(mPosition).setLocation(editable.toString());
        }
    }
    private class CarriageTextWatcher extends LocationTextWatcher{
        public CarriageTextWatcher(int position) {
            super(position);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s=editable.toString();
            float carriage= TextUtils.isEmpty(s)?0.0f:Float.parseFloat(s);
            mList.get(mPosition).setCarriage(carriage);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private LocationTextWatcher mLocationTextWatcher;
        private CarriageTextWatcher mCarriageTextWatcher;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
