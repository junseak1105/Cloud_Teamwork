package com.classprj.myapplication;

//책정보 데이터 타입 getter,setter
public class Book {
    String book_name;
    String total_page;
    int book_image;

    public Book(String book_name, String total_page, int book_image){
        this.book_name = book_name;
        this.total_page = total_page;
        this.book_image = book_image;
    }

    //getter
    public String getBook_name(){
        return book_name;
    }

    public String getTotal_page(){
        return total_page;
    }

    public int getBook_image(){
        return book_image;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setTotal_page(String total_page) {
        this.total_page = total_page;
    }

    public void setBook_image(int book_image) {
        this.book_image = book_image;
    }
}
