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
import android.widget.ArrayAdapter;
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

public class Editstu extends AppCompatActivity {
    private StudentManager studentManager;
    private Button button_return;

    private ArrayList<Student> lists;
    MyAdapter myAdapter;
    SQLiteDatabase database;
    myDatabaseHelper MyDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editstu);
        studentManager=new StudentManager(this);

        button_return=findViewById(R.id.button1);

        final ListView listView = (ListView) findViewById(R.id.stu_list); //初始化ListView
        lists = new ArrayList<Student>();
        myAdapter = new MyAdapter(Editstu.this);
        listView.setAdapter(myAdapter);

        MyDatabaseHelper = new myDatabaseHelper(this);
        database= MyDatabaseHelper.getReadableDatabase();
        // 查询数据
        Cursor cursor = database.query("student", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String student_id = cursor.getString(0);
            String student_name =cursor.getString(1);
            Student student=new Student(student_id,student_name);
            lists.add(student);
        }

        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student students=lists.get(position);
                Intent intent=new Intent(Editstu.this,Deletestu.class);
                intent.putExtra("stu_id",students.getId());
                intent.putExtra("stu_name",students.getName());
                startActivity(intent);
            }
        });

        button_return.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent_E_M = new Intent(Editstu.this,Manager_home.class);
                startActivity(intent_E_M);
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
            Student students = lists.get(position);
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.stu_listview, null);
                viewHolder.txt_id = (TextView) convertView
                        .findViewById(R.id.stu_id);
                viewHolder.txt_name = (TextView) convertView
                        .findViewById(R.id.stu_name);
                convertView.setTag(viewHolder); //绑定对象
            } else {
                viewHolder = (ViewHolder) convertView.getTag(); //取出对象
            }
            //向TextView中插入数据
            viewHolder.txt_id.setText("学号："+students.getId());
            viewHolder.txt_name.setText("姓名："+students.getName());

            return convertView;
        }
    }

    //存放控件
    class ViewHolder {
        private TextView txt_id;
        private TextView txt_name;
    }

}