package com.example.newapp.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.newapp.R;
import com.example.newapp.api.Api;
import com.example.newapp.api.ApiConfig;
import com.example.newapp.api.TtitCallBack;
import com.example.newapp.util.StringUtils;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {

    private EditText et_account;
    private EditText et_pwd;
    private Button btn_register;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        et_account = findViewById(R.id.et_account);
        et_pwd = findViewById(R.id.et_pwd);
        btn_register = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Account = et_account.getText().toString().trim();
                String Password = et_pwd.getText().toString().trim();
                Regist(Account, Password);
            }


        });
    }


    //登录账号账号密码判断是否为空
    private void Regist(String account, String password) {

        if (StringUtils.IsStringEmpty(account)) {
            ShowToast("No Account");
            return;
        }
        if (StringUtils.IsStringEmpty(password)) {
            ShowToast("No Password");
            return;
        }
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("mobile", account);
        m.put("password", password);
        Api.config(ApiConfig.REGISTER, m).PostRequest(this, new TtitCallBack() {
            @Override
            public void OnSuccess(String responseContent) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowToast(responseContent);
                    }
                });
            }

            @Override
            public void onFailure(Exception exception) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowToast(exception.getMessage());
                    }
                });
            }
        });

    }
}