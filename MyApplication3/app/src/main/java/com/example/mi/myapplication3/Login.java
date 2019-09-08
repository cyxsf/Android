package com.example.mi.myapplication3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class Login extends AppCompatActivity {
    private EditText editid, editpwd,editcode;
    private Button button_Login, button_Register,button_find;
    private ImageView imageView,code;
    private CheckBox remember_pwd;

    private boolean isHideFirst = false;
    private UserManager userManager;
    private String realCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        userManager=new UserManager(this);

        editid = findViewById(R.id.login_input_account_et);
        editpwd = findViewById(R.id.login_input_password_et);
        editcode=findViewById(R.id.login_input_code_et);
        code=findViewById(R.id.login_code);
        imageView=findViewById(R.id.iv_password);

        button_Login = findViewById(R.id.login_btn);
        button_Register = findViewById(R.id.register_btn);
        button_find=findViewById(R.id.findpwd);
        remember_pwd=findViewById(R.id.checkBox_password);

        button_Login.setOnClickListener(Login_Listener);
        button_Register.setOnClickListener(Login_Listener);
        button_find.setOnClickListener(Login_Listener);

        initData();

        //将验证码用图片的形式显示出来
        code.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();

        imageView.setImageResource(R.drawable.eyes_dis);

        code.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){ //点击更换验证码图片
                code.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
            }
        });

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHideFirst == true) {
                    imageView.setImageResource(R.drawable.eyes_vis);
                    //显示密码
                    //editpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    editpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    isHideFirst = false;
                } else {
                    imageView.setImageResource(R.drawable.eyes_dis);
                    // 隐藏密码
                    //editpwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    isHideFirst = true;

                }
            }
        });
    }

    private void initData(){

        //判断是否记住密码
        if (remenberPassword()) {
            remember_pwd.setChecked(true);//勾选记住密码
            setTextNameAndPassword();//把密码和账号输入到输入框中
        } else {
            setTextName();//把用户账号放到输入账号的输入框中
        }

    }

    /**
     * 把本地保存的数据设置数据到输入框中
     */
    public void setTextNameAndPassword() {
        editid.setText("" + getLocalName());
        editpwd.setText("" + getLocalPassword());
    }

    /**
     * 设置数据到输入框中
     */
    public void setTextName() {
        editid.setText("" + getLocalName());
    }

    /**
     * 获得保存在本地的用户名
     */
    public String getLocalName() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String name = helper.getString("name");
        return name;
    }


    /**
     * 获得保存在本地的密码
     */
    public String getLocalPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String password = helper.getString("password");
        return password;

    }

    /**
     * 判断是否记住密码
     */
    private boolean remenberPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean remenberPassword = helper.getBoolean("remenberPassword", false);
        return remenberPassword;
    }

    OnClickListener Login_Listener = new OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.login_btn:
                    String yanzhenCode = editcode.getText().toString().toLowerCase();
                    String msg = "生成的验证码："+realCode+"输入的验证码:"+yanzhenCode;
                    if (yanzhenCode.equals(realCode)) {
                            login_check();
                    } else {
                        Toast.makeText(Login.this,  "验证码错误", Toast.LENGTH_SHORT).show();

                    }
                    break;
                case R.id.register_btn:
                        Intent intent_Login_to_Register = new Intent(Login.this, Register.class);
                        startActivity(intent_Login_to_Register);
                        finish();
                    break;
                case R.id.findpwd:
                    Intent intent_Login_to_Findpwd = new Intent(Login.this, Findpwd.class);
                    startActivity(intent_Login_to_Findpwd);
                    finish();
            }
        }
    };

    public void login_check() {
        if (isStudentValid()) {
            String id=editid.getText().toString().trim();
            String pwd = editpwd.getText().toString().trim();
            //检查用户是否存在
            int count = userManager.lofind(id,id,pwd);
            if (count > 0) {

                Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                loadCheckBoxState();//记录下当前用户记住密码和自动登录的状态;
                Intent intent_Login_to_Main = new Intent(Login.this, User_main.class);
                intent_Login_to_Main.putExtra("user_id",id);
                startActivity(intent_Login_to_Main);
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

    /**
     * 获取账号
     */
    public String getAccount() {
        return editid.getText().toString().trim();//去掉空格
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return editpwd.getText().toString().trim();//去掉空格
    }


    /**
     * 保存用户选择“记住密码"的状态
     */
    private void loadCheckBoxState() {
        loadCheckBoxState(remember_pwd);
    }

    /**
     * 保存按钮的状态值
     */
    public void loadCheckBoxState(CheckBox checkBox_password) {

        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");

        if (!checkBox_password.isChecked()) {
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                    new SharedPreferencesUtils.ContentValue("name",getAccount()),
                    new SharedPreferencesUtils.ContentValue("password", ""));
        } else if (checkBox_password.isChecked()) {
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", true),
                    new SharedPreferencesUtils.ContentValue("name", getAccount()),
                    new SharedPreferencesUtils.ContentValue("password", getPassword()));
        }
    }

}