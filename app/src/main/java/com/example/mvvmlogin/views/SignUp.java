package com.example.mvvmlogin.views;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.mvvmlogin.R;
import com.example.mvvmlogin.data.User;
import com.example.mvvmlogin.viewmodel.LoginSignUpViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUp extends Fragment {

    CardView signUp;
    private LoginSignUpViewModel loginRegisterViewModel;
    EditText emailEditText, passwordEditText, re_enterPasswordEditText, phoneNoEditText, nameEditText, dobEditText;
    TextView loginTextView;
    User user;
    FirebaseUser firebaseUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        loginRegisterViewModel = ViewModelProviders.of(this).get(LoginSignUpViewModel.class);
        loginRegisterViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_loggedInFragment);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup,container,false);
        signUp = view.findViewById(R.id.signUp_button);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.password);
        re_enterPasswordEditText = view.findViewById(R.id.passwordReCheck);
        nameEditText = view.findViewById(R.id.takeNameEditText);
        loginTextView = view.findViewById(R.id.signInText);
        phoneNoEditText = view.findViewById(R.id.phoneEditText);
        dobEditText = view.findViewById(R.id.DOBEditText);
        loginTextView.setOnClickListener(view1 -> Navigation.findNavController(getView()).navigate(R.id.signUpFrag_to_loginFrag));

        signUp.setOnClickListener(v -> {
            user = new User();
            Toast.makeText(getContext(),"Verifying Details",Toast.LENGTH_SHORT).show();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            user.setName(nameEditText.getText().toString());
            user.setEmail(emailEditText.getText().toString());
            user.setPhone(phoneNoEditText.getText().toString());
            user.setDOB(dobEditText.getText().toString());
            if (email.length() > 0 && password.length() > 0 && password.equals(re_enterPasswordEditText.getText().toString())) {
                loginRegisterViewModel.register(email, password, user);
             } else {
                 Toast.makeText(getContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
             }
        });
        return view;

    }
}
