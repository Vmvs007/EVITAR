package com.example.evitar.MovimentosFolder;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovimentoRepository {
    private static MovimentoRepository instance;
    private List<Movimento> mList=new ArrayList();
    private MutableLiveData<List<Movimento>> data=new MutableLiveData<>();

    public static MovimentoRepository getInstance(){
        if (instance==null){
            instance=new MovimentoRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Movimento>> getMovimentos(String token){
        Call<List<Movimento>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getNotifications("Bearer "+token);
        call.enqueue(new Callback<List<Movimento>>() {
            @Override
            public void onResponse(Call<List<Movimento>> call, Response<List<Movimento>> response) {
                if(response.code()==200){
                    mList=response.body();

                    data.setValue(mList);
                }else{
                }
            }

            @Override
            public void onFailure(Call<List<Movimento>> call, Throwable t) {
            }
        });
        return data;
    }



}
