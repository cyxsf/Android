package com.example.dell.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register2 extends AppCompatActivity {
    private EditText editid, editpwd, editname,  editage, editphone,editpwd2;
    private Button button_commit, button_cancel;
    private  Manager_M manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);
        manager=new Manager_M(this);

        editid = findViewById(R.id.text_id);
        editpwd = findViewById(R.id.text_pwd);
        editpwd2=findViewById(R.id.text_pwd2);
        editname = findViewById(R.id.text_name);
        editage = findViewById(R.id.text_age);
        editphone = findViewById(R.id.text_phone);

        button_commit = findViewById(R.id.button1);
        button_cancel = findViewById(R.id.button2);

        button_commit.setOnClickListener(register_Listener);      //注册界面两个按钮的监听事件
        button_cancel.setOnClickListener(register_Listener);


    }

    View.OnClickListener register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:                       //确认按钮的监听事件
                    register_check();
                    break;
                case R.id.button2:                     //取消按钮的监听事件,由注册界面返回登录界面
                    Intent intent_Register_to_Login = new Intent(Register2.this, Login.class);
                    startActivity(intent_Register_to_Login);
                    finish();
                    break;
            }
        }
    };

    public void register_check() {
        if (isManagerValid()) {
            String id=editid.getText().toString().trim();
            String name = editname.getText().toString().trim();
            String pwd=editpwd.getText().toString().trim();
            String pwd2=editpwd2.getText().toString().trim();
            //检查用户是否存在
            int count = manager.refind(id,name);
            Toast.makeText(Register2.this, "count", Toast.LENGTH_SHORT).show();
            //用户已经存在时返回，给出提示文字
            if (count > 0) {
                Toast.makeText(Register2.this, "管理员已存在", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if(pwd.equals(pwd2)==true) {
                    String age = editage.getText().toString().trim();
                    String phone = editphone.getText().toString().trim();
                    manager.insert(id, name, pwd, age, phone);
                    Toast.makeText(Register2.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent_Register_to_Login = new Intent(Register2.this, Login.class);
                    startActivity(intent_Register_to_Login);
                }else{
                    Toast.makeText(Register2.this, "两次密码不同", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public boolean isManagerValid() {
        if (editid.getText().toString().trim().equals("")) {
            Toast.makeText(Register2.this, "账号为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(editpwd.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this, "密码为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(editpwd2.getText().toString().trim().equals("")){
            Toast.makeText(Register2.this, "第二次密码为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (editname.getText().toString().trim().equals("")) {
            Toast.makeText(Register2.this, "姓名为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (editage.getText().toString().trim().equals("")) {
            Toast.makeText(Register2.this, "年龄为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}