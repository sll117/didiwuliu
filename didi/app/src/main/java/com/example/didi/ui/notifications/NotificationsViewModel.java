package com.example.didi.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.didi.beans.OrderItem;

import java.util.List;

public class NotificationsViewModel extends ViewModel {
    private MutableLiveData<List<OrderItem>> items;
    public NotificationsViewModel()
    {
        items=new MutableLiveData<>();
    }
    public LiveData<List<OrderItem>> getItems(){
        return items;
    }
    public void updateItems(List<OrderItem> list){
        items.postValue(list);
    }
}
