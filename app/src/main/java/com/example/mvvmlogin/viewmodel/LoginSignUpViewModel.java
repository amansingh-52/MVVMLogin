package com.example.mvvmlogin.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmlogin.data.User;
import com.example.mvvmlogin.model.Repository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LoginSignUpViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<FirebaseUser> userLiveData;

    public LoginSignUpViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        userLiveData = repository.getUserLiveData();
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
       repository.login(email, password);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String email, String password, User user) {
        repository.register(email, password, user);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }


}
