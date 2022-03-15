package com.example.newapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initView();
        initData();
    }

    public void ShowToast(String str) {

        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();

    }

    public void insertVal(String Name, String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Name, token);
        editor.commit();
    }

    public void showToastSync(String str) {
        Looper.prepare();
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void Navigate(Class cls) {

        Intent intent = new Intent(mContext, cls);
        startActivity(intent);

    }

    public void navigateToWithFlag(Class cls, int flags) {
        Intent in = new Intent(mContext, cls);
        in.setFlags(flags);
        startActivity(in);
    }
}