package com.example.dell.myapplication3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class ReturnManager{
    Context context;
    myDatabaseHelper MyDatabaseHelper;

    public ReturnManager(Context context){
        this.context=context;
        MyDatabaseHelper=new myDatabaseHelper(context);
    }
    public void insert(String stu_id,String book_id,String borrow_date,String return_date){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="insert into return_table(stu_id,book_id,borrow_date,return_date) values (?,?,?,?)";
        Object args[]=new Object[]{stu_id,book_id,borrow_date,return_date};
        db.execSQL(sql,args);
        db.close();
    }


}
