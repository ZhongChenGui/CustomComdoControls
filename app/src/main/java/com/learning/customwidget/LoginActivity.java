package com.learning.customwidget;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.learning.customwidget.custmoize.loginpage.LoginPageView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginPageView loginPageView = this.findViewById(R.id.login_page_view);
        loginPageView.setOnLoginPageActionListener(new LoginPageView.OnLoginPageActionListener() {
            @Override
            public void onOpenAgreement() {
                //todo:打开协议
            }

            @Override
            public void onConfirmClick(String verifyCode, String phoneNum) {
                // todo: 点击登录
            }

            @Override
            public void onSendVerifyCodeClick(String phoneNum) {
                //todo 发送验证码
            }
        });
    }
}
