package com.example.raunak.project1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Database.DataBaseHandler;
import Model.User;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private Button saveButton, loginbutton;
    private EditText newname, newemail, newpassword, loginname, loginpwd;
    private DataBaseHandler db;
    private ImageView imageView;
    private TextView seeall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHandler(this);

        imageView = findViewById(R.id.addimage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });

        seeall = findViewById(R.id.see_all);
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        });
        loginbutton = findViewById(R.id.login);
        loginpwd = findViewById(R.id.email_pwd);
        loginname = findViewById(R.id.name_email);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginname.setText(Integer.toString(db.getCount()));
                User user = new User();
                user.setName(loginname.getText().toString());
                user.setEmail(loginname.getText().toString());
                user.setPassword(loginpwd.getText().toString());
                if( db.check(user) ){
                    Intent intent = new Intent(MainActivity.this,Details.class);
                    intent.putExtra("Uid",user.getId());
                    intent.putExtra("Uname",user.getName());
                    intent.putExtra("Uemail",user.getEmail());
                    intent.putExtra("Upwd",user.getPassword());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"id not found",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void popup() {
        dialogBuilder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup,null);
        newname = view.findViewById(R.id.Name);
        newemail = view.findViewById(R.id.email);
        newpassword = view.findViewById(R.id.password);
        saveButton = view.findViewById(R.id.save);

        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!newname.getText().toString().isEmpty() &&
                        !newemail.getText().toString().isEmpty() && !newpassword.getText().toString().isEmpty())
                    saveUserToDB(v);
            }
        });
    }
    private void saveUserToDB(View v) {
        User user = new User();

        String name1 = newname.getText().toString();
        String eid = newemail.getText().toString();
        String pwd = newpassword.getText().toString();

        user.setName(name1);
        user.setEmail(eid);
        user.setPassword(pwd);

        db.addUser(user);

        //Snackbar.make(v,"Saved Succesful",Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
//                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        },100);
    }
}
