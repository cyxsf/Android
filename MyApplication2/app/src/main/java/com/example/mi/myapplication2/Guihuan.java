package com.example.mi.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Guihuan extends AppCompatActivity {
    private ReturnManager returnManager;
    private BorrowManager borrowManager;
    private BookManager bookManager;
    private Button button_return;
    private ListView listView;
    private ArrayList<Borrowbook> lists;
    MyAdapter myAdapter;
    SQLiteDatabase database;
    myDatabaseHelper MyDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guihuan);

        returnManager=new ReturnManager(this);
        borrowManager=new BorrowManager(this);
        bookManager=new BookManager(this);

        button_return=findViewById(R.id.button1);
        listView = (ListView) findViewById(R.id.borrow_list); //初始化ListView
        lists = new ArrayList<Borrowbook>();

        myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);

        MyDatabaseHelper = new myDatabaseHelper(this);
        database= MyDatabaseHelper.getReadableDatabase();

        Cursor cursor = database.query("borrow", null,null,null, null, null, null);
        while (cursor.moveToNext()) {
            String stu_id = cursor.getString(0);
            String book_id = cursor.getString(1);
            String borrow_date = cursor.getString(2);
            String return_date = cursor.getString(3);
            Borrowbook bb = new Borrowbook(stu_id,book_id,borrow_date,return_date);
            lists.add(bb);
        }

        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Borrowbook borrow = lists.get(position);

                String borrow_date=borrow.getBorrow_date();
                String book_id= borrow.getBookId();
                String stu_id =borrow.getStuId();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                Calendar calendar=Calendar.getInstance();
                calendar.add(Calendar.MONTH,1);//获得当前时间
                String return_time=sdf.format(calendar.getTime());

                returnManager.insert(stu_id,book_id,borrow_date,return_time);
                borrowManager.delete(stu_id,book_id);

                SQLiteDatabase db=MyDatabaseHelper.getReadableDatabase();
                String selection = "book_id=?" ;
                String[] selectionArgs = new String[]{book_id};
                Cursor cursor = db.query("book", null, selection, selectionArgs, null, null, null);
                while(cursor.moveToNext()){
                    int num=cursor.getInt(4);
                    bookManager.updatenum(book_id, num + 1);
                    cursor.close();
                }
                Toast.makeText(Guihuan.this,  "归还成功", Toast.LENGTH_SHORT).show();
                Intent intent_B_S = new Intent(Guihuan.this,Manager_home.class);
                startActivity(intent_B_S);
                finish();
            }
        });

        button_return.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent_B_S = new Intent(Guihuan.this,Manager_home.class);
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
                convertView = inflater.inflate(R.layout.guihuan_listview, null);
                viewHolder.txt_stuid = (TextView) convertView
                        .findViewById(R.id.stu_id);
                viewHolder.txt_bookid = (TextView) convertView
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
            viewHolder.txt_stuid.setText("学号："+borrows.getStuId());
            viewHolder.txt_bookid.setText("书号："+borrows.getBookId());
            viewHolder.txt_borrowdate.setText("借阅日期："+borrows.getBorrow_date());
            viewHolder.txt_expectdate.setText("预归还日期："+borrows.getExpect_return_date());

            return convertView;
        }
    }

    //存放控件
    class ViewHolder {
        private TextView txt_stuid;
        private TextView txt_bookid;
        private TextView txt_borrowdate;
        private TextView txt_expectdate;
    }


}