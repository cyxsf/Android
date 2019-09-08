package com.example.mi.myapplication2;

import android.content.Intent;
import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Calendar;

public class Stu_home extends AppCompatActivity {
    private BorrowManager borrowManager;
    private myDatabaseHelper MyDatabaseHelper;

    private Button stu_borrow, stu_search,stu_return;
    private EditText search_content;
    private ArrayList<Book> lists;
    MyAdapter myAdapter;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_home);

        borrowManager=new BorrowManager(this);

        search_content = (EditText) findViewById(R.id.stu_content);
        stu_borrow = (Button) findViewById(R.id.stu_borrow);
        stu_search = (Button) findViewById(R.id.stu_search);
        stu_return = (Button) findViewById(R.id.stu_return);

        stu_search.setOnClickListener(Stu_Listener);      //注册界面两个按钮的监听事件
        stu_borrow.setOnClickListener(Stu_Listener);
        stu_return.setOnClickListener(Stu_Listener);

        MyDatabaseHelper = new myDatabaseHelper(this);
        database= MyDatabaseHelper.getReadableDatabase();

        final ListView listView = (ListView) findViewById(R.id.book_list); //初始化ListView
        lists = new ArrayList<Book>();
        myAdapter = new MyAdapter(Stu_home.this);
        listView.setAdapter(myAdapter);

        // 查询数据
        Cursor cursor = database.query("book", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String book_id = cursor.getString(0);
            String book_name = cursor.getString(1);
            String book_author = cursor.getString(2);
            int   book_num = cursor.getInt(4);
            Book book = new Book(book_id, book_name, book_author, book_num);
            lists.add(book);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book b =lists.get(position);

                if(b.getNum()!=0) {
                    int count=borrowManager.find(b.getId());
                    if(count==0) {
                        Intent intent = getIntent();                                             //获得上一个活动的数据
                        String student_id = intent.getStringExtra("student_id");

                        Intent xxintent=new Intent(Stu_home.this,Jieyue.class);
                        xxintent.putExtra("book_id",b.getId());
                        xxintent.putExtra("book_name",b.getName());        //传递数据给Jieyue页面
                        xxintent.putExtra("student_id",student_id);
                        startActivity(xxintent);

                    }else{
                        Toast.makeText(Stu_home.this, "不能借阅相同的书", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Stu_home.this, "借阅失败", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    class MyAdapter extends BaseAdapter {   //适配器
        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book books = lists.get(position);
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.listview, null);
                viewHolder.txt_id = (TextView) convertView
                        .findViewById(R.id.book_id);
                viewHolder.txt_name = (TextView) convertView
                        .findViewById(R.id.book_name);
                viewHolder.txt_author = (TextView) convertView
                        .findViewById(R.id.author);
                viewHolder.txt_num = (TextView) convertView
                        .findViewById(R.id.number);
                convertView.setTag(viewHolder); //绑定对象
            } else {
                viewHolder = (ViewHolder) convertView.getTag(); //取出对象
            }
            //向TextView中插入数据
            viewHolder.txt_id.setText("书号："+books.getId());
            viewHolder.txt_name.setText("书名："+books.getName());
            viewHolder.txt_author.setText("作者："+books.getAuthor());
            viewHolder.txt_num.setText("数量："+books.getNum()+" ");

            return convertView;
        }
    }

    //存放控件
    class ViewHolder {
        private TextView txt_id;
        private TextView txt_name;
        private TextView txt_author;
        private TextView txt_num;
    }


    View.OnClickListener Stu_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stu_search:
                    search_check();
                    break;
                case R.id.stu_borrow:
                    Intent  xintent=getIntent();
                    String stu_id=xintent.getStringExtra("student_id");

                    Intent intent_home_to_borrow = new Intent(Stu_home.this, Borrow.class);
                    intent_home_to_borrow.putExtra("student_id",stu_id);
                    startActivity(intent_home_to_borrow);
                    break;
                case R.id.stu_return:
                    Intent intent_home = new Intent(Stu_home.this, Login.class);
                    startActivity(intent_home);
            }
        }
    };

    public void search_check() {   //查询图书功能
        lists.clear();
        String search_name;
        search_name = search_content.getText().toString();
        MyDatabaseHelper = new myDatabaseHelper(this);
        SQLiteDatabase db = MyDatabaseHelper.getReadableDatabase();
        String selection = "book_name=?" ;
        String[] selectionArgs = new String[]{search_name};
        Cursor cursor = db.query("book", null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            String book_id = cursor.getString(0);
            String book_name = cursor.getString(1);
            String book_author = cursor.getString(2);
            int book_num=cursor.getInt(4);
            Book book = new Book(book_id, book_name, book_author,book_num);
            lists.add(book);
        }
    }


}