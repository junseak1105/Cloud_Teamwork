package com.classprj.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final private static String TAG = "tag";
    Button btn_photo, btn_tts;
    ImageView iv_photo;
    final static int TAKE_PICTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_photo = findViewById(R.id.iv_photo);
        btn_photo = findViewById(R.id.btn_photo);
        btn_tts = findViewById(R.id.btn_tts);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_photo:
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        btn_tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ttsintent = new Intent(MainActivity.this, TTSActivity.class);
                startActivity(ttsintent);
            }
        });

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
    }//onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        iv_photo.setImageBitmap(bitmap);
                    }
                }
                break;
        }
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
