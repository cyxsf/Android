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

public class Updatebook extends AppCompatActivity {
    private EditText editid, editname, editauthor, editpub, editnum, editsort, editrecord;
    private Button button_sure,button_delete, button_cancel;

    private BookManager bookManager;
    private myDatabaseHelper MyDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editbook);

        MyDatabaseHelper=new myDatabaseHelper(this);
        bookManager=new BookManager(this);

        editid = findViewById(R.id.text_id);
        editname = findViewById(R.id.text_name);
        editauthor = findViewById(R.id.text_author);
        editpub = findViewById(R.id.text_pub);
        editnum = findViewById(R.id.text_num);
        editsort = findViewById(R.id.text_sort);
        editrecord = findViewById(R.id.text_record);

        button_sure = findViewById(R.id.button1);
        button_delete=findViewById(R.id.button2);
        button_cancel = findViewById(R.id.button3);

        button_sure.setOnClickListener(edit_Listener);
        button_delete.setOnClickListener(edit_Listener);
        button_cancel.setOnClickListener(edit_Listener);

        Intent intent=getIntent();  //获取上一个活动的数据
        String bookid=intent.getStringExtra("book_id");
        String bookname=intent.getStringExtra("book_name");

        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
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
            editauthor.setText(author);
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
                    editbook_check();
                    break;
                case R.id.button2:
                    delete_check();
                    break;
                case R.id.button3:
                    Intent intent = new Intent(Updatebook.this,Manager_home.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    public void editbook_check() {
        String book_id=editid.getText().toString().trim();  //提取编辑框中的内容
        String book_name=editname.getText().toString().trim();
        String book_author=editauthor.getText().toString().trim();
        String bookpub=editpub.getText().toString().trim();
        int booknum=Integer.parseInt(editnum.getText().toString());
        String booksort=editsort.getText().toString().trim();
        String bookrecord=editrecord.getText().toString().trim();
        bookManager.update(book_id,book_name,book_author,bookpub,booknum,booksort,bookrecord);
        Toast.makeText(Updatebook.this, "更新成功", Toast.LENGTH_SHORT).show();
        Intent intent_B_M = new Intent(Updatebook.this,Manager_home.class);
        startActivity(intent_B_M);
        finish();
    }

    public void delete_check(){
        String bookid=editid.getText().toString().trim();
        String bookname=editname.getText().toString().trim();
        bookManager.delete(bookid,bookname);
        Toast.makeText(Updatebook.this, "删除成功", Toast.LENGTH_SHORT).show();
        Intent intent_B_M = new Intent(Updatebook.this,Manager_home.class);
        startActivity(intent_B_M);
        finish();
    }
}
