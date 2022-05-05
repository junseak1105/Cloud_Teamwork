package com.classprj.myapplication;

public class BookData {
    String IDX;
    String BOOK_ID;
    String BOOK_PAGE;
    String BOOK_CONTENT;
    String CONTENT_LENGTH;
    String BOOK_PAGE_IDX;

    public String getIDX() {
        return IDX;
    }

    public void setIDX(String IDX) {
        this.IDX = IDX;
    }

    public String getBOOK_ID() {
        return BOOK_ID;
    }

    public void setBOOK_ID(String BOOK_ID) {
        this.BOOK_ID = BOOK_ID;
    }

    public String getBOOK_PAGE() {
        return BOOK_PAGE;
    }

    public void setBOOK_PAGE(String BOOK_PAGE) {
        this.BOOK_PAGE = BOOK_PAGE;
    }

    public String getBOOK_CONTENT() {
        return BOOK_CONTENT;
    }

    public void setBOOK_CONTENT(String BOOK_CONTENT) {
        this.BOOK_CONTENT = BOOK_CONTENT;
    }

    public String getCONTENT_LENGTH() {
        return CONTENT_LENGTH;
    }

    public void setCONTENT_LENGTH(String CONTENT_LENGTH) {
        this.CONTENT_LENGTH = CONTENT_LENGTH;
    }

    public String getBOOK_PAGE_IDX() {
        return BOOK_PAGE_IDX;
    }

    public void setBOOK_PAGE_IDX(String BOOK_PAGE_IDX) {
        this.BOOK_PAGE_IDX = BOOK_PAGE_IDX;
    }

    public BookData() {
        this.IDX = IDX;
        this.BOOK_ID = BOOK_ID;
        this.BOOK_PAGE = BOOK_PAGE;
        this.BOOK_CONTENT = BOOK_CONTENT;
        this.CONTENT_LENGTH = CONTENT_LENGTH;
        this.BOOK_PAGE_IDX = BOOK_PAGE_IDX;
    }
}
