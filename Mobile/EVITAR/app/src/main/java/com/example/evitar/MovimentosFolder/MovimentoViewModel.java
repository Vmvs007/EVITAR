package com.example.evitar.MovimentosFolder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MovimentoViewModel extends ViewModel {

    private MutableLiveData<List<Movimento>> mMov;
    private MovimentoRepository mRepo;

    public void init(String token){
        if (mMov!=null){
            return;
        }
        mRepo=MovimentoRepository.getInstance();
        mMov=mRepo.getMovimentos(token);
    }

    public LiveData<List<Movimento>> getMovimentos(){
        return mMov;
    }


}
