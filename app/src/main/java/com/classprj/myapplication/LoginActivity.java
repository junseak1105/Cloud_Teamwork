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
import com.classprj.myapplication.member.login_chk;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    private CheckBox cb_autologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences pref = getSharedPreferences("login_session",MODE_PRIVATE);
        // //세션 체크
        // SharedPreferences sf = getSharedPreferences("login_session",MODE_PRIVATE);
        // if(sf.getString("YN","").equals("yes")){
        //     String userID = sf.getString("ID","");
        //     String userPass = sf.getString("PW","");
        //     Response.Listener<String> responseListener_autologin = new Response.Listener<String>() {
        //         @Override
        //         public void onResponse(String response) {
        //             try {
        //                 JSONObject jsonObject = new JSONObject(response);
        //                 boolean success = jsonObject.getBoolean("success");
        //                 if (success) { // 로그인에 성공한 경우
        //                     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        //                     intent.putExtra("userID", userID);
        //                     intent.putExtra("userPass", userPass);
        //                     startActivity(intent);
        //                 } else { // 로그인에 실패한 경우
        //                     Toast.makeText(getApplicationContext(),"자동로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
        //                     return;
        //                 }
        //             } catch (JSONException e) {
        //                 e.printStackTrace();
        //             }
        //         }
        //     };
        //     login_chk login_chk = new login_chk(userID, userPass, responseListener_autologin);
        //     RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        //     queue.add(login_chk);
        // };


        et_id = findViewById(R.id.et_id); //아이디
        et_pass = findViewById(R.id.et_pass);//패스워드
        btn_login = findViewById(R.id.btn_login);//로그인 버튼
        btn_register = findViewById(R.id.btn_register);//회원가입 버튼
        cb_autologin = findViewById(R.id.cb_autologin);//자동로그인 체크박스


        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // EditText에 현재 입력되어있는 값을 get
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                //로그인 폼 미입력 처리
                if(userID.equals("")){
                    Toast.makeText(getApplicationContext(),"아이디를 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }else if(userPass.equals("")){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해 주세요.",Toast.LENGTH_SHORT).show();
                };

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인에 성공한 경우
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                String token = jsonObject.getString("token");
                                //자동로그인 체크시 수행
                                if(cb_autologin.isChecked()){
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("token",token);
                                    editor.commit();
                                }else{
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("token","");
                                }
                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPass", userPass);
                                startActivity(intent);
                            } else { // 로그인에 실패한 경우

                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });


    }
}
