package com.example.mi.myapplication3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserManager {
    Context context;
    myDatabaseHelper MyDatabaseHelper;

    public UserManager(Context context) {
        this.context = context;
        MyDatabaseHelper = new myDatabaseHelper(context);
    }

    public void insert(String user_id, String user_name, String user_pwd) {
        SQLiteDatabase db = MyDatabaseHelper.getWritableDatabase();
        String sql = "insert into user (user_id,user_name,user_pwd) values(?,?,?)";
        Object args[] = new Object[]{user_id, user_name, user_pwd};
        db.execSQL(sql, args);
        db.close();
    }

    public void update(String user_id, String user_pwd) {
        SQLiteDatabase db = MyDatabaseHelper.getWritableDatabase();
        String sql = "update user set user_pwd=? where user_id=? ";
        Object args[] = new Object[]{user_pwd,user_id};
        db.execSQL(sql, args);
        db.close();
    }

    public int refind(String user_id) {
        SQLiteDatabase db = MyDatabaseHelper.getReadableDatabase();
        int result = 0;
        String sql = "select * from user where user_id=? ";
        Cursor mCursor = db.rawQuery(sql, new String[]{user_id});
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
        }
        return result;
    }

    public int lofind(String user_id,String user_name,String user_pwd) {
        SQLiteDatabase db = MyDatabaseHelper.getReadableDatabase();
        int result = 0;
        String sql = "select * from user where user_id=? or user_name=? and user_pwd=?";
        Cursor mCursor = db.rawQuery(sql, new String[]{user_id,user_name,user_pwd});
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
        }
        return result;
    }
}
