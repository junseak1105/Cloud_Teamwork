package com.classprj.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.classprj.myapplication.member.LoginRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class LogoutActivity extends AppCompatActivity {

    // 서버 URL 설정 ( PHP 파일 연동 ) //세션? PHP?
    final static private String URL = "http://jhk.n-e.kr:8080/logout.php";
    private Map<String, String> map;

    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn_logout = findViewById(R.id.btn_logout); //로그아웃 버튼
        Button btn_logout = (Button) findViewById(R.id.btn_logout);

        /*로그아웃하면 세션 초기화, 로그인 화면으로 넘어가게*/

        btn_logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                /*UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                // 로그아웃이 되었다면 로그인 액티비티로 넘어간다.
                                Intent intent = new Intent(MypageActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });*/
            }
        });
    }
}
