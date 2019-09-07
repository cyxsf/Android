package com.example.dell.myapplication3;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Jieyue extends AppCompatActivity {
    private EditText editid, editname, editauthor, editpub, editnum, editsort, editrecord;
    private Button button_sure,button_cancel;

    private BookManager bookManager;
    private myDatabaseHelper MyDatabaseHelper;
    private BorrowManager borrowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jieyue);

        bookManager=new BookManager(this);
        borrowManager=new BorrowManager(this);
        MyDatabaseHelper=new myDatabaseHelper(this);

        editid = findViewById(R.id.text_id);
        editname = findViewById(R.id.text_name);
        editauthor = findViewById(R.id.text_author);
        editpub = findViewById(R.id.text_pub);
        editnum = findViewById(R.id.text_num);
        editsort = findViewById(R.id.text_sort);
        editrecord = findViewById(R.id.text_record);

        button_sure = findViewById(R.id.button1);
        button_cancel=findViewById(R.id.button2);

        button_sure.setOnClickListener(edit_Listener);
        button_cancel.setOnClickListener(edit_Listener);


        Intent intent=getIntent();  //获取上一个活动的数据
        String bookid=intent.getStringExtra("book_id");
        String bookname=intent.getStringExtra("book_name");

        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();  //打开数据库
        String sql="SELECT * FROM book where book_id=? and book_name=?";
        Cursor mCursor=db.rawQuery(sql,new String[]{bookid,bookname});
        while(mCursor.moveToNext()){
            String author=mCursor.getString(mCursor.getColumnIndex("book_author"));
            String pub=mCursor.getString(mCursor.getColumnIndex("book_pub"));
            String num=mCursor.getString(mCursor.getColumnIndex("book_num"));
            String sort=mCursor.getString(mCursor.getColumnIndex("book_sort"));
            String record=mCursor.getString(mCursor.getColumnIndex("book_record"));
            editid.setText(bookid);
            editname.setText(bookname);
            editauthor.setText(author);   //用提取的数据填充编辑框
            editpub.setText(pub);
            editnum.setText(num);
            editsort.setText(sort);
            editrecord.setText(record);
        }

    }

    View.OnClickListener edit_Listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    jieyuebook_check();
                    break;
                case R.id.button2:
                    Intent xintent = getIntent();                                             //获得上一个活动的数据
                    String student_id = xintent.getStringExtra("student_id");

                    Intent intent = new Intent(Jieyue.this,Stu_home.class);
                    intent.putExtra("student_id",student_id);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    public void jieyuebook_check() {
        Intent intent = getIntent();                                             //获得上一个活动的数据
        String student_id = intent.getStringExtra("student_id");

        String book_id=editid.getText().toString().trim();
        int num=Integer.parseInt(editnum.getText().toString().trim());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calendar = Calendar.getInstance();   //获得当前时间
        String borrow_time = sdf.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 3);
        String return_time = sdf.format(calendar.getTime());

        borrowManager.insert(student_id,book_id, borrow_time, return_time);
        bookManager.updatenum(book_id, num -1);

        Toast.makeText(Jieyue.this, "借阅成功", Toast.LENGTH_SHORT).show();
        Intent intent_J_S = new Intent(Jieyue.this,Stu_home.class);
        intent_J_S.putExtra("student_id",student_id);
        startActivity(intent_J_S);
        finish();
    }

}

