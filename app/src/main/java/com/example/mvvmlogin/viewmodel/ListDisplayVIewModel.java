package com.example.mvvmlogin.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmlogin.data.User;
import com.example.mvvmlogin.model.Repository;

import java.util.List;

public class ListDisplayVIewModel extends AndroidViewModel {

    private MutableLiveData<List<User>> userMutableLiveData;
    private Repository repository;

    public ListDisplayVIewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        repository.display();


    }
    public  MutableLiveData<List<User>> getUserListLiveData(){
        userMutableLiveData=repository.getAllUsers();
        return userMutableLiveData;
    }
}
