package com.example.mi.myapplication2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Addbook extends AppCompatActivity {
    private EditText editid, editname, editauthor, editpub, editnum, editsort, editrecord;
    private Button button_sure, button_cancel;

    private BookManager bookManager;
    private myDatabaseHelper MyDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbook);
        bookManager=new BookManager(this);
        MyDatabaseHelper=new myDatabaseHelper(this);

        editid = findViewById(R.id.text_id);
        editname = findViewById(R.id.text_name);
        editauthor = findViewById(R.id.text_author);
        editpub = findViewById(R.id.text_pub);
        editnum = findViewById(R.id.text_num);
        editsort = findViewById(R.id.text_sort);
        editrecord = findViewById(R.id.text_record);

        button_sure = findViewById(R.id.button1);
        button_cancel = findViewById(R.id.button2);

        button_sure.setOnClickListener(edit_Listener);
        button_cancel.setOnClickListener(edit_Listener);

    }

    View.OnClickListener edit_Listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    insertbook_check();
                    break;
                case R.id.button2:
                    Intent intent = new Intent(Addbook.this,Manager_home.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    public void insertbook_check() {
        if (isBookValid()) {
            String book_id = editid.getText().toString().trim();
            String book_name = editname.getText().toString().trim();
            String book_author = editauthor.getText().toString().trim();
            String bookpub = editpub.getText().toString().trim();
            int booknum = Integer.parseInt(editnum.getText().toString());
            String booksort = editsort.getText().toString().trim();
            String bookrecord = editrecord.getText().toString().trim();

            int result = bookManager.search(book_id);
            if (result == 0) {
                bookManager.insert(book_id, book_name, book_author, bookpub, booknum, booksort, bookrecord);
                Toast.makeText(Addbook.this, "新增成功", Toast.LENGTH_SHORT).show();
                Intent intent_B_M = new Intent(Addbook.this, Manager_home.class);
                startActivity(intent_B_M);
                finish();
            } else {
                Toast.makeText(Addbook.this, "图书已存在", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean isBookValid() {
        if (editid.getText().toString().trim().equals("")) {
            Toast.makeText(Addbook.this, "序号为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(editname.getText().toString().trim().equals("")){
            Toast.makeText(Addbook.this, "名称为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if(editauthor.getText().toString().trim().equals("")){
            Toast.makeText(Addbook.this, "作者为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (editpub.getText().toString().trim().equals("")) {
            Toast.makeText(Addbook.this, "出版社为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (editnum.getText().toString().trim().equals("")) {
            Toast.makeText(Addbook.this, "数量为空", Toast.LENGTH_SHORT).show();
            return false;
        }else if (editsort.getText().toString().trim().equals("")) {
            Toast.makeText(Addbook.this, "分类为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
