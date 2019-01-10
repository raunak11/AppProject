package com.example.raunak.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    private TextView user, name, email, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        user = findViewById(R.id.userid);
        name = findViewById(R.id.name);
        email = findViewById(R.id.emailid);
        pwd = findViewById(R.id.password);

        Bundle bundle = getIntent().getExtras();

        user.setText(Integer.toString(bundle.getInt("Uid")));
        name.setText(bundle.getString("Uname"));
        email.setText(bundle.getString("Uemail"));
        pwd.setText(bundle.getString("Upwd"));
    }
}
