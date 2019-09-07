package com.example.dell.myapplication3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;


import java.util.List;

public class Login extends AppCompatActivity {
    private EditText editid, editpwd,editcode;
    private Button button_commit, button_Login;
    private CheckBox checkBox1,checkBox2;
    private StudentManager studentManager;
    private Manager_M manager;
    private ImageView code;
    private String realCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        studentManager=new StudentManager(this);
        manager=new Manager_M(this);

        editid = findViewById(R.id.text_userid);
        editpwd = findViewById(R.id.text_userpwd);
        editcode=findViewById(R.id.text_usercode);
        code=findViewById(R.id.code_image);

        button_commit = findViewById(R.id.button1);
        button_Login = findViewById(R.id.button2);
        checkBox1=findViewById(R.id.checkBox1);
        checkBox2=findViewById(R.id.checkBox2);

        button_commit.setOnClickListener(Login_Listener);
        button_Login.setOnClickListener(Login_Listener);

        //将验证码用图片的形式显示出来
        code.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();

        code.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //点击更换验证码图片
                code.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
            }
        });
    }

    OnClickListener Login_Listener = new OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.button1:
                    String yanzhenCode = editcode.getText().toString().toLowerCase();
                    String msg = "生成的验证码："+realCode+"输入的验证码:"+yanzhenCode;
                    if (yanzhenCode.equals(realCode)) {
                        if(checkBox1.isChecked()){
                            Toast.makeText(Login.this, "学生登录", Toast.LENGTH_SHORT).show();
                            login_check();
                        }
                        else if(checkBox2.isChecked()) {
                            Toast.makeText(Login.this, "管理员登录", Toast.LENGTH_SHORT).show();
                            manager_check();
                        }
                        else{
                            Toast.makeText(Login.this, "请选择正确的用户身份", Toast.LENGTH_SHORT).show();
                        }

                   } else {
                        Toast.makeText(Login.this,  "验证码错误", Toast.LENGTH_SHORT).show();

                   }
                    break;
                case R.id.button2:
                    if(checkBox1.isChecked()) {
                        Intent intent_Login_to_Login = new Intent(Login.this, Register.class);
                        startActivity(intent_Login_to_Login);
                        finish();
                    }else if(checkBox2.isChecked()){
                        Intent intent_Login_to_Login = new Intent(Login.this, Register2.class);
                        startActivity(intent_Login_to_Login);
                        finish();
                }
                    break;
            }
        }
    };

    public void login_check() {
        if (isStudentValid()) {
            String id=editid.getText().toString().trim();
            String pwd = editpwd.getText().toString().trim();
            //检查用户是否存在
            int count = studentManager.lofind(id,pwd);
            if (count > 0) {
                Intent intent_Login_to_Stu = new Intent(Login.this, Stu_home.class);
                intent_Login_to_Stu.putExtra("student_id",id);
                startActivity(intent_Login_to_Stu);
                finish();
                Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, "登录失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public void manager_check() {
        if (isStudentValid()) {
            String id=editid.getText().toString().trim();
            String pwd = editpwd.getText().toString().trim();
            //检查用户是否存在
            int count = manager.find(id,pwd);
            if (count > 0) {
                Intent intent_L_M = new Intent(Login.this, Manager_home.class);
                startActivity(intent_L_M);
                Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, "登录失败", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public boolean isStudentValid() {  //确认账号和密码不为空
        if (editid.getText().toString().trim().equals("")) {
            Toast.makeText(Login.this, "账号为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editpwd.getText().toString().trim().equals("")) {
            Toast.makeText(Login.this, "密码为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}