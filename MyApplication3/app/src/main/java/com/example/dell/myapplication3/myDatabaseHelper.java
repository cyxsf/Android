package com.example.dell.myapplication3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDatabaseHelper extends SQLiteOpenHelper{
    private static final String DBNAME ="library.db";
    private static final int VERSION = 2;

    public static final String student="create table student (stu_id varchar(20) not null primary key,stu_name varchar(40),"+
            "stu_pwd varchar(20),stu_sex varchar(5), stu_age int, stu_pro varchar(20), stu_grade varchar(20), stu_integrity int)";

    public static final String book="create table book (book_id varchar(20) not null primary key,book_name varchar(40),"+
            "book_author varchar(40),book_pub varchar(40),book_num int,book_sort varchar(20),book_record datetime)";

    public static final String book_sort="create table book_sort (sort_id varchar(20) not null  primary key,"+
            "sort_name varchar(40))";

    public static final String borrow="create table borrow (stu_id varchar(20),"+
            "book_id varchar(20),borrow_date datetime,return_date datetime)";
    public static final String return_table="create table return_table(stu_id varchar(20),"+
            "book_id varchar(20),borrow_date datetime,return_date datetime)";

    public static final String ticket="create table ticket (stu_id varchar(40) not null primary key,"+
            "book_id varchar(40),over_data int,ticket_fee float)";

    public static final String manager="create table manager (manager_id varchar(40) not null primary key,"+
            "manager_name varchar(40),manager_pwd varchar(20),manager_age varchar(10),manager_phone varchar(20))";

    public myDatabaseHelper(Context context){

        super(context,DBNAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(student);
        db.execSQL(book);
        db.execSQL(book_sort);
        db.execSQL(borrow);
        db.execSQL(return_table);
        db.execSQL(ticket);
        db.execSQL(manager);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        onCreate(db);
    }
}

