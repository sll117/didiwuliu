package com.example.didi.ui.edit;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didi.R;
import com.example.didi.beans.PathBean;
import com.example.didi.data.DataShare;
import com.example.didi.utils.HttpUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditActivity extends AppCompatActivity {

    private static final String TAG="EditActivity";
    private List<PathBean> mList;
    private LocationEditAdapter locationEditAdapter;
    private Handler mHandler=new Handler();
    public static final String EXTRA_DATA="path";
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mRecyclerView=findViewById(R.id.recycler_view);
        // 设置布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mList= DataShare.getPathBeans();
        locationEditAdapter=new LocationEditAdapter(mList);
        mRecyclerView.setAdapter(locationEditAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu_add)
        {
            mList.add(new PathBean());
            locationEditAdapter.notifyDataSetChanged();
        }else if(id==R.id.menu_done)
        {
            for(int i=mList.size()-1;i>0;i--)
            {
                if(mList.get(i).getLocation().isEmpty())
                    mList.remove(i);
            }
            locationEditAdapter.notifyDataSetChanged();
            postPath();

        }
        return super.onOptionsItemSelected(item);
    }
    private void postPath()
    {
        Gson gson=new Gson();
        String body=gson.toJson(mList);
        Request request=new Request.Builder()
                .url(HttpUtils.BASE_URL+"/updatepath")
                .post(RequestBody.create(body, HttpUtils.JSON))
                .build();
        HttpUtils.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EditActivity.this,"更新数据失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
