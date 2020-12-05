package com.example.mvvmlogin.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvvmlogin.R;

import java.util.List;

public class ListViewAdaptor extends ArrayAdapter<User> {

    List<User> userList;
    Context context;
    int resource;

    public ListViewAdaptor(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.userList = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource,null,false);

        TextView name = view.findViewById(R.id.nameDisplay);
        TextView email = view.findViewById(R.id.emailDisplay);
        TextView phone = view.findViewById(R.id.phoneDisplay);
        TextView dob = view.findViewById(R.id.DOBDisplay);

        User user = userList.get(position);

        name.setText(user.name);
        email.setText(user.email);
        phone.setText(user.phone);
        dob.setText(user.getDOB());

        return view;
    }
}
