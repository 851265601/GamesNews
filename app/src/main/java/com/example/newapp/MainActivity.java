package com.example.newapp;

import android.view.View;
import android.widget.Button;

import com.example.newapp.activity.BaseActivity;
import com.example.newapp.activity.LoginActivity;
import com.example.newapp.activity.RegisterActivity;

public class MainActivity extends BaseActivity {
    private Button btn_login;
    private Button btn_register;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent=  new Intent(getApplicationContext(), LoginActivity.class);
                // startActivity(intent);

                Navigate(LoginActivity.class);
            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent=  new Intent(getApplicationContext(), RegisterActivity.class);
                //startActivity(intent);

                Navigate(RegisterActivity.class);

            }
        });
    }


}