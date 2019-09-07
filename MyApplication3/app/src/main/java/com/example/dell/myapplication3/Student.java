package com.example.dell.myapplication3;

import java.util.ArrayList;
import java.util.List;

public class Student{
    private String stu_id,stu_name,stu_sex,stu_pro,stu_grade,stu_pwd;
    private int stu_age;

    public String getId() { //获取学生编号
        return stu_id;
    }
    public void setId(String stu_id) {
        this.stu_id = stu_id;
    }
    public String getName() {
        return stu_name;
    }
    public void setName(String stu_name) {
        this.stu_name =stu_name;
    }
    public String getPassword() {
        return stu_pwd;
    }
    public void setPassword(String stu_pwd) {
        this.stu_pwd=stu_pwd;
    }
    public int getAge() {
        return stu_age;
    }
    public void setAge(int stu_age) {
        this.stu_age =stu_age;
    }
    public String getSex() {
        return stu_sex;
    }
    public void setSex(String stu_sex) {
        this.stu_sex = stu_sex;
    }
    public String getPro() {
        return stu_pro;
    }
    public void setPro(String stu_pro) {
        this.stu_pro = stu_pro;
    }
    public String getGrade() {
        return stu_grade;
    }
    public void setGrade(String stu_grade) {
        this.stu_grade = stu_grade;
    }
    public Student(String stu_id,String stu_name) {
        this.stu_id=stu_id;
        this.stu_name=stu_name;
    }
}
