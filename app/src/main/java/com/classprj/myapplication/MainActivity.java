package com.classprj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gridview);
        BookAdapter adapter = new BookAdapter();
        adapter.addItem(new Book("a","01032312343", R.drawable.a1));
        adapter.addItem(new Book("b","01073842933", R.drawable.a1));
        adapter.addItem(new Book("c","01034495948", R.drawable.a1));
        adapter.addItem(new Book("d","01054894595", R.drawable.a1));
        adapter.addItem(new Book("e","01032312343", R.drawable.a1));
        adapter.addItem(new Book("f","01032312343", R.drawable.a1));
        adapter.addItem(new Book("g","018239585", R.drawable.a1));
        adapter.addItem(new Book("h","01092838575", R.drawable.a1));
        adapter.addItem(new Book("i","01072453443", R.drawable.a1));
        gridView.setAdapter(adapter);

    }
    public class BookAdapter extends BaseAdapter{
        ArrayList<Book> items = new ArrayList<>();

        @Override
        public int getCount(){
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void addItem(Book book){
            this.items.add(book);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book_View book_view = null;
            if(convertView==null){
                book_view = new Book_View(getApplicationContext());
            }else{
                book_view=(Book_View) convertView;
            }
            Book book = items.get(position);
            book_view.setNameView(book.getBook_name());
            book_view.setNumberView(book.getTotal_page());
            book_view.setImageView(book.getBook_image());

            return book_view;
        }
    }
}
