package com.classprj.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Book_View extends LinearLayout {
    ImageView imageView;
    TextView nameView;
    TextView pageView;

    public Book_View(Context context){
        super(context);
        init(context);
    }

    public Book_View(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gridview_part, this, true);
        imageView= findViewById(R.id.book_image);
        nameView = findViewById(R.id.book_name);
        pageView = findViewById(R.id.book_page);
    }
    public void setImageView(int imageId){
        imageView.setImageResource(imageId);
    }
    public void setNameView(String name){
        nameView.setText(name);
    }
    public void setNumberView(String book_page){
        pageView.setText(book_page);
    }
}

