package com.example.dell.myapplication3;

import java.util.ArrayList;
import java.util.List;

public class Book{
    private String book_id;
    private String book_name,book_author,book_pub,book_sort,book_record;
    private int book_num;

    public String getId() {
        return book_id;
    }
    public void setId(String book_id) {
        this.book_id = book_id;
    }
    public String getName() {
        return book_name;
    }
    public void setName(String book_name) {
        this.book_name =book_name;
    }
    public int getNum() {
        return book_num;
    }
    public void setNum(int book_num) {
        this.book_num =book_num;
    }
    public String getAuthor() {
        return book_author;
    }
    public void setAuthor(String book_author) {
        this.book_author = book_author;
    }
    public String getPub() {
        return book_pub;
    }
    public void setPub(String book_pub) {
        this.book_pub = book_pub;
    }
    public String getSort() {
        return book_sort;
    }
    public void setSort(String book_sort) {
        this.book_sort = book_sort;
    }
    public String getRecord() {
        return book_record;
    }
    public void setRecord(String book_record) {
        this.book_record = book_record;
    }
    public Book( String book_id,String book_name,String book_author,int book_num) {
        this.book_id=book_id;
        this.book_name=book_name;
        this.book_author=book_author;
        this.book_num=book_num;
    }
}
