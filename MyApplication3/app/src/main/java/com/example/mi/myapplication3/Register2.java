package com.example.mi.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class Register2 extends AppCompatActivity {
    private EditText editid, editpwd, editpwd2;
    private Button button_commit, button_cancel;
    private ImageView iv_pwd,iv_pwd2;
    private boolean isHideFirst = false;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);
        userManager = new UserManager(this);

        editid = findViewById(R.id.re2_input_account_et);
        editpwd = findViewById(R.id.re2_input_password_et);
        editpwd2 = findViewById(R.id.re2_input_password2_et);

        iv_pwd = findViewById(R.id.iv_password);
        iv_pwd2 = findViewById(R.id.iv_password2);

        button_commit = findViewById(R.id.register_btn);
        button_cancel = findViewById(R.id.cancel_btn);

        button_commit.setOnClickListener(register_Listener);      //注册界面两个按钮的监听事件
        button_cancel.setOnClickListener(register_Listener);

        iv_pwd.setImageResource(R.drawable.eyes_dis);
        iv_pwd2.setImageResource(R.drawable.eyes_dis);

        iv_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHideFirst == true) {
                    iv_pwd.setImageResource(R.drawable.eyes_vis);
                    //显示密码
                    editpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    isHideFirst = false;
                } else {
                    iv_pwd.setImageResource(R.drawable.eyes_dis);
                    // 隐藏密码
                    editpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    isHideFirst = true;

                }
            }
        });

        iv_pwd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHideFirst == true) {
                    iv_pwd2.setImageResource(R.drawable.eyes_vis);
                    //显示密码
                    editpwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    isHideFirst = false;
                } else {
                    iv_pwd2.setImageResource(R.drawable.eyes_dis);
                    // 隐藏密码
                    editpwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    isHideFirst = true;

                }
            }
        });

    }

    View.OnClickListener register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn:                       //确认按钮的监听事件
                    register_check();
                    break;
                case R.id.cancel_btn:                     //取消按钮的监听事件,由注册界面返回登录界面
                    Intent intent_Register_to_Login = new Intent(Register2.this, Login.class);
                    startActivity(intent_Register_to_Login);
                    finish();
                    break;
            }
        }
    };

    public void register_check() {
        if (isStudentValid()) {
            Intent intent = getIntent();                                             //获得上一个活动的数据
            String id = intent.getStringExtra("user_id");
            String name = editid.getText().toString().trim();
            String pwd = editpwd.getText().toString().trim();
            String pwd2 = editpwd2.getText().toString().trim();
            if (pwd.equals(pwd2) == true) {
                userManager.insert(id, name, pwd);
                Intent intent_Register_to_Login = new Intent(Register2.this, Login.class);
                startActivity(intent_Register_to_Login);
            } else {
                    Toast.makeText(Register2.this, "两次密码不同", Toast.LENGTH_SHORT).show();
                }

        }
    }

    public boolean isStudentValid() {
        if (editid.getText().toString().trim().equals("")) {
            Toast.makeText(Register2.this, "用户名为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editpwd.getText().toString().trim().equals("")) {
            Toast.makeText(Register2.this, "密码为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editpwd2.getText().toString().trim().equals("")) {
            Toast.makeText(Register2.this, "第二次密码为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}



