package com.example.mi.myapplication2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class BorrowManager{
    Context context;
    myDatabaseHelper MyDatabaseHelper;

    public BorrowManager(Context context){
        this.context=context;
        MyDatabaseHelper=new myDatabaseHelper(context);
    }

    public void insert(String stu_id,String book_id,String borrow_date,String return_date){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="insert into borrow(stu_id,book_id,borrow_date,return_date) values (?,?,?,?)";
        Object args[]=new Object[]{stu_id,book_id,borrow_date,return_date};
        db.execSQL(sql,args);
        db.close();
    }

    public void delete(String stu_id,String book_id){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="delete from borrow where stu_id=? and book_id=?";
        Object args[]=new Object[]{stu_id,book_id};
        db.execSQL(sql,args);
        db.close();
    }

    public int find(String book_id){
        SQLiteDatabase db=MyDatabaseHelper.getReadableDatabase();
        int result=0;
        String sql="select * from borrow where book_id=?";
        Cursor mCursor=db.rawQuery(sql,new String[]{book_id});
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
        }
        return result;
    }

}