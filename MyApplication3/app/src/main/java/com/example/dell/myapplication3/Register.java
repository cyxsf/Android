package com.example.dell.myapplication3;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Register extends AppCompatActivity {
    private EditText editid, editpwd, editname, editsex, editage, editpro, editgrade,editpwd2;
    private Button button_commit, button_cancel;
    private  StudentManager studentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        studentManager=new StudentManager(this);

        editid = findViewById(R.id.text_stuid);
        editpwd = findViewById(R.id.text_stupwd);
        editpwd2=findViewById(R.id.text_stupwd2);
        editname = findViewById(R.id.text_name);
        editsex = findViewById(R.id.text_sex);
        editage = findViewById(R.id.text_age);
        editpro = findViewById(R.id.text_pro);
        editgrade = findViewById(R.id.text_grade);

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
                    Intent intent_Register_to_Login = new Intent(Register.this, Login.class);
                    startActivity(intent_Register_to_Login);
                    finish();
                    break;
            }
        }
    };

    public void register_check() {
        if (isStudentValid()) {
            String stuid=editid.getText().toString().trim();
            String name = editname.getText().toString().trim();
            String  pwd= editpwd.getText().toString().trim();
            String  pwd2= editpwd2.getText().toString().trim();
            //检查用户是否存在
            int count = studentManager.refind(stuid,name);
            //用户已经存在时返回，给出提示文字
            if (count > 0) {
                Toast.makeText(Register.this, "学生已存在", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (pwd.equals(pwd2) == true) {
                    String sex = editsex.getText().toString().trim();
                    int age = Integer.parseInt(editage.getText().toString());
                    String pro = editpro.getText().toString().trim();
                    String grade = editgrade.getText().toString().trim();
                    studentManager.insert(stuid, name, pwd, sex, age, pro, grade);
                    Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent_Register_to_Login = new Intent(Register.this, Login.class);
                    startActivity(intent_Register_to_Login);
                }else{
                    Toast.makeText(Register.this, "两次密码不同", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public boolean isStudentValid() {
        if (editid.getText().toString().trim().equals("")) {
            Toast.makeText(Register.this, "学号为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(editpwd.getText().toString().trim().equals("")){
            Toast.makeText(Register.this, "密码为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(editpwd2.getText().toString().trim().equals("")){
            Toast.makeText(Register.this, "第二次密码为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (editname.getText().toString().trim().equals("")) {
            Toast.makeText(Register.this, "姓名为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (editage.getText().toString().trim().equals("")) {
            Toast.makeText(Register.this, "年龄为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}