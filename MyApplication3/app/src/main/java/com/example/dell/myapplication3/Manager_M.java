package com.example.dell.myapplication3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class Manager_M {
    Context context;
    myDatabaseHelper MyDatabaseHelper;

    public Manager_M(Context context){
        this.context=context;
        MyDatabaseHelper=new myDatabaseHelper(context);
    }

    public void insert(String manager_id,String manager_name,String manager_pwd,String manager_age,String manager_phone){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="insert into manager (manager_id,manager_name,manager_pwd,manager_age,manager_phone) values(?,?,?,?,?)";
        Object args[]=new Object[]{manager_id,manager_name,manager_pwd,manager_age,manager_phone};
        db.execSQL(sql,args);
        db.close();
    }

    public int find(String manager_id,String manager_pwd){
        SQLiteDatabase db=MyDatabaseHelper.getReadableDatabase();
        int result=0;
        String sql="select * from manager where manager_id=? and manager_pwd=?";
        Cursor mCursor=db.rawQuery(sql,new String[]{manager_id,manager_pwd});
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
        }
        return result;
    }

    public int refind(String manager_id,String manager_name){
        SQLiteDatabase db=MyDatabaseHelper.getReadableDatabase();
        int result=0;
        String sql="select * from manager where manager_id=? and manager_name=?";
        Cursor mCursor=db.rawQuery(sql,new String[]{manager_id,manager_name});
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
        }
        return result;
    }
}