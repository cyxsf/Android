package com.example.dell.myapplication3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.Toast;
import java.lang.String;

public class BookManager{

    Context context;
    myDatabaseHelper MyDatabaseHelper;

    public BookManager(Context context){
        this.context=context;
        MyDatabaseHelper=new myDatabaseHelper(context);
    }
    public void insert(String book_id,String book_name,String book_author,String book_pub,int book_num,String book_sort,String book_record){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="insert into book(book_id,book_name,book_author,book_pub,book_num,book_sort,book_record)values(?,?,?,?,?,?,?)";
        Object args[]=new Object[]{book_id,book_name,book_author,book_pub,book_num,book_sort,book_record};
        db.execSQL(sql,args);
        db.close();
    }

    public void update(String book_id,String book_name,String book_author,String book_pub,int book_num,String book_sort,String book_record){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="update book set book_name=?,book_author=?,book_pub=?,book_num=?,book_sort=?,book_record=? where book_id=?";
        Object args[]=new Object[]{book_name,book_author,book_pub,book_num,book_sort,book_record,book_id};
        db.execSQL(sql,args);
        db.close();
    }

    public void delete(String book_id,String book_name){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="delete from book where book_id=? and book_name=?";
        Object args[]=new Object[]{book_id,book_name};
        db.execSQL(sql,args);
        db.close();
    }

    public void updatenum(String book_id,int book_num){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="update book set book_num=? where book_id=?";
        Object args[]=new Object[]{book_num,book_id};
        db.execSQL(sql,args);
        db.close();
    }

    public int search(String book_id){
        SQLiteDatabase db=MyDatabaseHelper.getReadableDatabase();
        int result=0;
        String sql="select * from book where book_id=?";
        Cursor mCursor=db.rawQuery(sql,new String[]{book_id});
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
        }
        return result;
    }
}