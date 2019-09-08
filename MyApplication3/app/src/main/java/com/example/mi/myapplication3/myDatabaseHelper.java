package com.example.mi.myapplication3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDatabaseHelper extends SQLiteOpenHelper{
    private static final String DBNAME ="test.db";
    private static final int VERSION = 1;

    public static final String user="create table user(user_id varchar(20) not null primary key,user_name varchar(40),"+
            "user_pwd varchar(20))";
    public static final String user_info="create table user_info(user_id varchar(20) not null primary key,user_name varchar(40),"+
            "user_pw varchar(20))";



    public myDatabaseHelper(Context context){

        super(context,DBNAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(user);
        db.execSQL(user_info);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        onCreate(db);
    }
}

