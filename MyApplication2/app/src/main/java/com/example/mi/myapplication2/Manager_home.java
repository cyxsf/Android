package com.example.mi.myapplication2;

import android.content.Intent;
import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Manager_home extends AppCompatActivity {
    private Button button_return,button_book,button_stu,button_borrow;

    private List<Book> lists;
    MyAdapter myAdapter;
    SQLiteDatabase database;
    myDatabaseHelper MyDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_home);

        button_book=(Button)findViewById(R.id.button1);
        button_stu=(Button)findViewById(R.id.button2);
        button_return=(Button)findViewById(R.id.button3);
        button_borrow=(Button)findViewById(R.id.button4);

        MyDatabaseHelper = new myDatabaseHelper(this);
        database= MyDatabaseHelper.getReadableDatabase();

        ListView listView = (ListView) findViewById(R.id.manager_list); //初始化ListView
        lists = new ArrayList<Book>();

        // 查询数据
        Cursor cursor = database.query("book", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String book_id = cursor.getString(0);
            String book_name = cursor.getString(1);
            String book_author = cursor.getString(2);
            int  book_num=cursor.getInt(4);
            Book book = new Book(book_id, book_name, book_author,book_num);
            lists.add(book);
        }

        button_book.setOnClickListener(manager_Listener);
        button_stu.setOnClickListener(manager_Listener);
        button_return.setOnClickListener(manager_Listener);
        button_borrow.setOnClickListener(manager_Listener);

        myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book b =lists.get(position);
                Intent intent=new Intent(Manager_home.this,Updatebook.class);
                intent.putExtra("book_id",b.getId());
                intent.putExtra("book_name",b.getName());
                startActivity(intent);
            }
        });

    }

    OnClickListener manager_Listener = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    Intent intent_M_A = new Intent(Manager_home.this,Addbook.class);
                    startActivity(intent_M_A);
                    finish();
                    break;
                case R.id.button2:
                    Intent intent_M_S = new Intent(Manager_home.this,Editstu.class);
                    startActivity(intent_M_S);
                    finish();
                    break;
                case  R.id.button3:
                    Intent intent_M_L = new Intent(Manager_home.this,Login.class);
                    startActivity(intent_M_L);
                    finish();
                    break;
                case R.id.button4:
                    Intent intent_M_G = new Intent(Manager_home.this,Guihuan.class);
                    startActivity(intent_M_G);
                    finish();
                    break;
            }

        }
    };

    class MyAdapter extends BaseAdapter {
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
            // 从personList取出Person
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
                viewHolder.txt_num =(TextView) convertView
                        .findViewById(R.id.number);
                convertView.setTag(viewHolder); //绑定对象
            } else {
                viewHolder = (ViewHolder) convertView.getTag(); //取出对象
            }
            //向TextView中插入数据
            viewHolder.txt_id.setText("书号："+books.getId());
            viewHolder.txt_name.setText("书名："+books.getName());
            viewHolder.txt_author.setText("作者："+books.getAuthor());
            viewHolder.txt_num.setText("数量："+books.getNum()+"");

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


}
