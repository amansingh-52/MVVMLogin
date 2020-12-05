package com.example.mvvmlogin.model;

import android.app.Application;
import android.app.usage.NetworkStats;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmlogin.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository {
    private final Application application;

    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUser> userLiveData;
    private final MutableLiveData<Boolean> loggedOutLiveData;
    private final MutableLiveData<List<User>> listMutableLiveData;
    private final FirebaseFirestore db;
    private final List<User> userList;

    public Repository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();
        this.listMutableLiveData = new MutableLiveData<>();
        this.userList = new ArrayList<>();

        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);

        }
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map map = document.getData();
                        User user = new User();
                        user.setName(map.toString());
                        if(map.get("name")!=null)
                            user.setName(map.get("name").toString());
                        if(map.get("DOB")!=null)
                            user.setDOB(map.get("DOB").toString());
                        if(map.get("phone")!=null)
                            user.setPhone(map.get("phone").toString());
                        if(map.get("email")!=null)
                            user.setEmail(map.get("email").toString());
                        userList.add(user);
                        Log.i("userList",userList.toString());
                        listMutableLiveData.postValue(userList);
                        Log.i("Hello",listMutableLiveData.toString());
                    }
                }else{
                    Toast.makeText(application.getApplicationContext(),"Database error!!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void display(){

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String email, String password, User user) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseAuth.signInWithEmailAndPassword(user.getEmail(),password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        user.setuID(firebaseUser.getUid().toString());
                                        db.collection("users").document(firebaseUser.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.isSuccessful()) {
                                                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                                                    } else {
                                                        Toast.makeText(application.getApplicationContext(), "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public MutableLiveData<List<User>> getAllUsers(){
        return listMutableLiveData;
    }



    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
}
