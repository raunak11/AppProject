package com.example.raunak.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Adapter.RecyclerAdapter;
import Database.DataBaseHandler;
import Model.User;

public class ListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private List<User> UserList;
    private DataBaseHandler db;
    private List<User> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        db = new DataBaseHandler(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItem = new ArrayList<>();
        UserList = db.getAllUser();

        for(User user : UserList){
            User newuser = new User();
            newuser.setName("Name: " + user.getName());
            newuser.setEmail("Email: " + user.getEmail());
            newuser.setPassword("Password: " + user.getPassword());
            newuser.setId(user.getId());

            listItem.add(newuser);
        }

        adapter = new RecyclerAdapter(this,listItem);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
