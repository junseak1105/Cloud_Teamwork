package com.classprj.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private Button btnBook_list_setting, btnuser_logout, btnuser_Withdrawal, btnbook_delete;
    View book_setting_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("사용자 정보 수정");

        btnBook_list_setting = (Button) findViewById(R.id.btnBook_list_setting);
        btnuser_logout = (Button) findViewById(R.id.btnuser_logout);
        btnuser_Withdrawal = (Button) findViewById(R.id.btnuser_Withdrawal);

        btnBook_list_setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                book_setting_dialog = (View) View.inflate(SettingActivity.this,
                        R.layout.book_setting, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(SettingActivity.this);
                dlg.setTitle("책 목록 수정");

                dlg.setView(book_setting_dialog);
                dlg.setPositiveButton("삭제",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                //책 삭제 부분 넣으면 됌
                            }
                        });
                dlg.show();
            }
        });

    }
}