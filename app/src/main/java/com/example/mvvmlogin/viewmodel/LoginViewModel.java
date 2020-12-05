package com.example.mvvmlogin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.mvvmlogin.model.Repository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        userLiveData = repository.getUserLiveData();
        loggedOutLiveData = repository.getLoggedOutLiveData();
    }
    public void logOut() {
        repository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
}
