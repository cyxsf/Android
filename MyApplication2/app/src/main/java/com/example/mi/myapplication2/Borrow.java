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
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Borrow extends AppCompatActivity {
    private Button button_return;

    private ArrayList<Borrowbook> lists;
    MyAdapter myAdapter;
    SQLiteDatabase database;
    myDatabaseHelper MyDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow);

        button_return=findViewById(R.id.button1);
        ListView listView = (ListView) findViewById(R.id.borrow_list); //初始化ListView
        lists = new ArrayList<Borrowbook>();

        myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);

        Intent xintent=getIntent();
        final String student_id=xintent.getStringExtra("student_id");

        MyDatabaseHelper = new myDatabaseHelper(this);
        database= MyDatabaseHelper.getReadableDatabase();

        String selection = "stu_id=?" ;
        String[] selectionArgs = new String[]{student_id};
        Cursor cursor = database.query("borrow", null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            String stu_id = cursor.getString(0);
            String book_id = cursor.getString(1);
            String borrow_date = cursor.getString(2);
            String return_date = cursor.getString(3);
            Borrowbook bb = new Borrowbook(stu_id,book_id,borrow_date,return_date);
            lists.add(bb);
        }

        button_return.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent_B_S = new Intent(Borrow.this,Stu_home.class);
                intent_B_S.putExtra("student_id",student_id);
                startActivity(intent_B_S);
                finish();
            }
        });

    }

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
            Borrowbook borrows = lists.get(position);
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.borrow_listview, null);
                viewHolder.txt_id = (TextView) convertView
                        .findViewById(R.id.book_id);
                viewHolder.txt_borrowdate = (TextView) convertView
                        .findViewById(R.id.borrow_date);
                viewHolder.txt_expectdate = (TextView) convertView
                        .findViewById(R.id.expect_date);
                convertView.setTag(viewHolder); //绑定对象
            } else {
                viewHolder = (ViewHolder) convertView.getTag(); //取出对象
            }
            //向TextView中插入数据
            viewHolder.txt_id.setText("书号："+borrows.getBookId());
            viewHolder.txt_borrowdate.setText("借阅日期："+borrows.getBorrow_date());
            viewHolder.txt_expectdate.setText("预归还日期："+borrows.getExpect_return_date());

            return convertView;
        }
    }

    //存放控件
    class ViewHolder {
        private TextView txt_id;
        private TextView txt_borrowdate;
        private TextView txt_expectdate;
    }


}