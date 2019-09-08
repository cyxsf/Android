package com.example.mi.myapplication3;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Findpwd extends AppCompatActivity implements View.OnClickListener{
    String APPKEY = "2a0c09460df8f";
    String APPSECRET = "02f21b3210b9925ab4c76d27ef0c5149";

    private EditText inputPhoneEt,inputCodeEt,inputPwd,inputPwd2;
    private Button requestCodeBtn,commitBtn,returnBtn;
    private ImageView iv_pwd,iv_pwd2;

    private SQLiteOpenHelper helper;
    private UserManager userManager;

    private boolean isHideFirst = false;

    //倒计时显示   可以手动更改。
    int i = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpwd);
        initView();

        userManager=new UserManager(this);
        helper=new myDatabaseHelper(this);
        helper.getWritableDatabase();

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_find_login=new Intent(Findpwd.this,Login.class);
                startActivity(intent_find_login);
            }
        });

    }

    /**
     * 初始化控件
     */
    private void initView() {
        inputPhoneEt = (EditText) findViewById(R.id.find_input_phone_et);
        inputCodeEt = (EditText) findViewById(R.id.find_input_code_et);
        inputPwd=(EditText) findViewById(R.id.find_input_password_et);
        inputPwd2=(EditText)findViewById(R.id.find_input_password2_et);

        requestCodeBtn = (Button) findViewById(R.id.find_request_code_btn);
        commitBtn = (Button) findViewById(R.id.find_commit_btn);
        returnBtn = (Button) findViewById(R.id.find_return_btn);

        iv_pwd = (ImageView) findViewById(R.id.iv_password);
        iv_pwd2 = (ImageView) findViewById(R.id.iv_password2);

        requestCodeBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);

        iv_pwd.setImageResource(R.drawable.eyes_dis);
        iv_pwd2.setImageResource(R.drawable.eyes_dis);

        // 启动短信验证sdk
        MobSDK.init(this, APPKEY, APPSECRET);
        EventHandler eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

        iv_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHideFirst == true) {
                    iv_pwd.setImageResource(R.drawable.eyes_vis);
                    //显示密码
                    inputPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    isHideFirst = false;
                } else {
                    iv_pwd.setImageResource(R.drawable.eyes_dis);
                    // 隐藏密码
                    inputPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

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
                    inputPwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                    isHideFirst = false;
                } else {
                    iv_pwd2.setImageResource(R.drawable.eyes_dis);
                    // 隐藏密码
                    inputPwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    isHideFirst = true;

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String phoneNums = inputPhoneEt.getText().toString();  //取出咱们输入的手机号
        switch (v.getId()) {
            case R.id.find_request_code_btn:
                // 1. 判断手机号是不是11位并且看格式是否合理
                if (!judgePhoneNums(phoneNums)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                requestCodeBtn.setClickable(false);
                requestCodeBtn.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;

            case R.id.find_commit_btn:
                //将收到的验证码和手机号提交再次核对
                SMSSDK.submitVerificationCode("86", phoneNums, inputCodeEt
                        .getText().toString());
                break;

        }

    }

    /**
     *
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                requestCodeBtn.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                requestCodeBtn.setText("获取验证码");
                requestCodeBtn.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        String account=inputPhoneEt.getText().toString().trim();
                        int count = userManager.refind(account);
                        //用户已经存在时返回，给出提示文字
                        if (count > 0) {
                            String pwd=inputPwd.getText().toString().trim();
                            String pwd2=inputPwd2.getText().toString().trim();
                            if(pwd.equals(pwd2) == true){
                                userManager.update(account,pwd);
                                Intent intent_Find_Login = new Intent(Findpwd.this, Login.class);
                                startActivity(intent_Find_Login);
                                finish();
                            }else{
                                Toast.makeText(Findpwd.this, "两次密码不同", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(Findpwd.this, "用户不存在", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Findpwd.this,"验证码不正确",Toast.LENGTH_SHORT).show();
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };


    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！",Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    @Override
    protected void onDestroy() {
        //反注册回调监听接口
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}

