package com.classprj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로그인 토큰 체크
        SharedPreferences pref = getSharedPreferences("login_session",MODE_PRIVATE);
        if(!pref.getString("token","").equals("")) {
            String token = pref.getString("token", "");
            Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
        };
        //로그인 버튼 시작
        Button SigninButton = (Button) findViewById(R.id.button_login);
        SigninButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        //로그인 버튼 끝

        GridView gridView = findViewById(R.id.gridview);
        BookAdapter adapter = new BookAdapter();
        adapter.addItem(new Book("a","322/500", R.drawable.a1));
        adapter.addItem(new Book("b","322/500", R.drawable.a1));
        adapter.addItem(new Book("c","322/500", R.drawable.a1));
        adapter.addItem(new Book("d","322/500", R.drawable.a1));
        adapter.addItem(new Book("e","322/500", R.drawable.a1));
        adapter.addItem(new Book("f","322/500", R.drawable.a1));
        adapter.addItem(new Book("g","322/500", R.drawable.a1));
        adapter.addItem(new Book("h","322/500", R.drawable.a1));
        adapter.addItem(new Book("i","322/500", R.drawable.a1));
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
