package com.example.dell.myapplication3;

public class Borrowbook {
    private String stu_id,book_id,borrow_date,expect_return_date;

    public String getStuId() { //获取学生编号
        return stu_id;
    }
    public void setStuId(String stu_id) {
        this.stu_id = stu_id;
    }
    public String getBookId() {
        return book_id;
    }
    public void setBookId(String book_id) {
        this.book_id=book_id;
    }
    public String getBorrow_date() {
        return borrow_date;
    }
    public void setBorrow_date(String borrow_date) {
        this.borrow_date=borrow_date;
    }
    public String getExpect_return_date() {
        return expect_return_date;
    }
    public void setExpect_return_date(String expect_return_date) {
        this.expect_return_date=expect_return_date;
    }
    public Borrowbook(String stu_id, String book_id,String borrow_date,String expect_return_date) {
        this.stu_id=stu_id;
        this.book_id=book_id;
        this.borrow_date=borrow_date;
        this.expect_return_date=expect_return_date;
    }
}
