package com.example.dell.myapplication3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentManager {
    Context context;
    myDatabaseHelper MyDatabaseHelper;

    public StudentManager(Context context){
        this.context=context;
        MyDatabaseHelper=new myDatabaseHelper(context);
    }

    public void insert(String stu_id,String stu_name,String stu_pwd,String stu_sex,int stu_age,String stu_pro,String stu_grade){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="insert into student (stu_id,stu_name,stu_pwd,stu_sex,stu_age,stu_pro,stu_grade) values(?,?,?,?,?,?,?)";
        Object args[]=new Object[]{stu_id,stu_name,stu_pwd,stu_sex,stu_age,stu_pro,stu_grade};
        db.execSQL(sql,args);
        db.close();
    }

    public void delete(String stu_id){
        SQLiteDatabase db=MyDatabaseHelper.getWritableDatabase();
        String sql="DELETE FROM student WHERE stu_id=?";
        Object args[]=new Object[]{stu_id};
        db.execSQL(sql,args);
        db.close();
    }

    public int refind(String stu_id,String stu_name){
        SQLiteDatabase db=MyDatabaseHelper.getReadableDatabase();
        int result=0;
        String sql="select * from student where stu_id=? and stu_name=?";
        Cursor mCursor=db.rawQuery(sql,new String[]{stu_id,stu_name});
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
        }
        return result;
    }

    public int lofind(String stu_id,String stu_pwd){
        SQLiteDatabase db=MyDatabaseHelper.getReadableDatabase();
        int result=0;
        String sql="select * from student where stu_id=? and stu_pwd=?";
        Cursor mCursor=db.rawQuery(sql,new String[]{stu_id,stu_pwd});
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
        }
        return result;
    }
}
