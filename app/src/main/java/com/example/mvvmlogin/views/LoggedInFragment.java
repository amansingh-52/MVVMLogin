package com.example.mvvmlogin.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.mvvmlogin.R;
import com.example.mvvmlogin.data.ListViewAdaptor;
import com.example.mvvmlogin.data.User;
import com.example.mvvmlogin.viewmodel.ListDisplayVIewModel;
import com.example.mvvmlogin.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoggedInFragment extends Fragment {
    private TextView loggedInUserTextView;
    private Button logOutButton;
    private LoginViewModel loggedInViewModel;
    private List<User> userList;
    private ListDisplayVIewModel listDisplayVIewModel;
    private ListView listView;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("Aman");
        user1.setEmail("amansingh8066@gmail.com");
        user1.setPhone("7634880605");
        user1.setDOB("02/03/1999");
        User user2 = new User();
        user2.setName("Nitesh");
        user2.setEmail("niteshsingh8066@gmail.com");
        user2.setPhone("9834880605");
        user2.setDOB("02/03/1499");
        User user3 = new User();
        user3.setName("Iman");
        user3.setEmail("iman@gmail.com");
        user3.setPhone("7634880606");
        user3.setDOB("02/03/1998");
        User user4 = new User();
        user4.setName("Aman");
        user4.setEmail("amansingh8066@gmail.com");
        user4.setPhone("7634880605");
        user4.setDOB("02/03/1999");User user6 = new User();
        user6.setName("Aman");
        user6.setEmail("amansingh8066@gmail.com");
        user6.setPhone("7634880605");
        user6.setDOB("02/03/1999");
        User user5 = new User();
        user5.setName("Ramesh");
        user5.setEmail("rameshingh8066@gmail.com");
        user5.setPhone("7634553605");
        user5.setDOB("02/03/1999");User user7 = new User();
        user7.setName("Himanshu");
        user7.setEmail("himanshusingh8066@gmail.com");
        user7.setPhone("7634880605");
        user7.setDOB("02/03/1999");User user8 = new User();
        user8.setName("Aman");
        user8.setEmail("amansingh8066@gmail.com");
        user8.setPhone("7634880605");
        user8.setDOB("02/03/1999");User user9 = new User();
        user9.setName("Aman");
        user9.setEmail("amansingh8066@gmail.com");
        user9.setPhone("7634880605");
        user9.setDOB("02/03/1999");

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);userList.add(user4);userList.add(user5);userList.add(user6);userList.add(user7);userList.add(user9);userList.add(user8);




        loggedInViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loggedInViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    loggedInUserTextView.setText("Welcome: " + firebaseUser.getEmail());

                    logOutButton.setEnabled(true);
                } else {
                    logOutButton.setEnabled(false);
                }
            }
        });


        listDisplayVIewModel = ViewModelProviders.of(this).get(ListDisplayVIewModel.class);
        listDisplayVIewModel.getUserListLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userList = users;
            }
        });



        loggedInViewModel.getLoggedOutLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut) {
                    Toast.makeText(getContext(), "User Logged Out", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_loggedInFragment_to_loginRegisterFragment);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loggedin, container, false);

        loggedInUserTextView = view.findViewById(R.id.fragment_loggedin_loggedInUser);
        logOutButton = view.findViewById(R.id.fragment_loggedin_logOut);
        ListViewAdaptor listViewAdaptor = new ListViewAdaptor(getContext(),R.layout.listview,userList);
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(listViewAdaptor);
        Log.i("okok",userList.toString());
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedInViewModel.logOut();
            }
        });



        return view;

    }
}
