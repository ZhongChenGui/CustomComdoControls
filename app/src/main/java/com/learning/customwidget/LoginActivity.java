package com.learning.customwidget;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
                App.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 提示
                        Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        loginPageView.onVerifyCodeError();
                    }
                }, 3000);
            }

            @Override
            public void onSendVerifyCodeClick(String phoneNum) {
                //todo 发送验证码
            }
        });
    }
}
