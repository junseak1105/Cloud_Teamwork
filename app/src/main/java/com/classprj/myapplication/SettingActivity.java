package com.classprj.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class SettingActivity extends AppCompatActivity {

    private Button btnBook_list_setting, btnuser_logout, btnuser_Withdrawal;
    View book_setting_dialog;
    TextView setting_userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("설정");

        TextView setting_userID = (TextView) findViewById(R.id.setting_userID);
        setting_userID.setText("아이디");

        btnBook_list_setting = (Button) findViewById(R.id.btnBook_list_setting); //사용자 책 목록
        btnuser_logout = (Button) findViewById(R.id.btnuser_logout); //로그아웃
        btnuser_Withdrawal = (Button) findViewById(R.id.btnuser_Withdrawal); //계정탈퇴

        //사용자 책 목록(삭제) 기능
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

        //로그아웃 기능
        btnuser_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB에 토큰으로 로그인 확인, SharedPreferences에 저장된 토큰 값을 빈칸" " 주면 해결
                SharedPreferences pref = getSharedPreferences("login_session",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("token","");
                editor.commit();

                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent logoutintent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(logoutintent);
            }
        });

        
    }
}