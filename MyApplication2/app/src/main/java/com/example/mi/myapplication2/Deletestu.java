package com.example.mi.myapplication2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Deletestu extends AppCompatActivity {
    private EditText editid, editname, editsex, editage, editpro, editgrade;
    private Button button_sure,button_cancel;

    private StudentManager studentManager;
    private myDatabaseHelper MyDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletestu);
        studentManager=new StudentManager(this);
        MyDatabaseHelper=new myDatabaseHelper(this);

        editid = findViewById(R.id.text_stuid);
        editname = findViewById(R.id.text_name);
        editsex = findViewById(R.id.text_sex);
        editage = findViewById(R.id.text_age);
        editpro = findViewById(R.id.text_pro);
        editgrade = findViewById(R.id.text_grade);

        button_sure = findViewById(R.id.button1);
        button_cancel = findViewById(R.id.button2);

        button_sure.setOnClickListener(delete_Listener);
        button_cancel.setOnClickListener(delete_Listener);

        Intent intent=getIntent();
        String stuid=intent.getStringExtra("stu_id");
        String stuname=intent.getStringExtra("stu_name");

        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="SELECT * FROM student where stu_id=? and stu_name=?";
        Cursor mCursor=db.rawQuery(sql,new String[]{stuid,stuname});

        while(mCursor.moveToNext()){
            String sex=mCursor.getString(mCursor.getColumnIndex("stu_sex"));
            String age=mCursor.getString(mCursor.getColumnIndex("stu_age"));
            String pro=mCursor.getString(mCursor.getColumnIndex("stu_pro"));
            String grade=mCursor.getString(mCursor.getColumnIndex("stu_grade"));
            editid.setText(stuid);
            editname.setText(stuname);
            editsex.setText(sex);
            editage.setText(age);
            editpro.setText(pro);
            editgrade.setText(grade);
        }

    }

    View.OnClickListener delete_Listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    deletestu_check();
                    break;
                case R.id.button2:
                    Intent intent = new Intent(Deletestu.this,Editstu.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    public void deletestu_check() {
        String stu_id=editid.getText().toString().trim();
        studentManager.delete(stu_id);
        Toast.makeText(Deletestu.this, "删除成功", Toast.LENGTH_SHORT).show();
        Intent intent_D_E = new Intent(Deletestu.this,Editstu.class);
        startActivity(intent_D_E);
        finish();
    }

}

