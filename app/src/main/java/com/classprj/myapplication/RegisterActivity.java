package com.classprj.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.classprj.myapplication.member.ID_overlap_chk_request;
import com.classprj.myapplication.member.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pass, et_name, et_email,et_pass_chk;
    private Button btn_register;
    private Button btn_id_overlap_chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_pass_chk = findViewById(R.id.et_pass_chk);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);

        //아이디 중복체크 버튼 클릭 시 수행
        btn_id_overlap_chk = findViewById(R.id.btn_id_overlap_chk);
        btn_id_overlap_chk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean overlap = jsonObject.getBoolean("overlap");
                            System.out.println(overlap);
                            if (overlap) { // 사용가능 ID일 경우
                                Toast.makeText(getApplicationContext(), "사용 가능한 ID 입니다.", Toast.LENGTH_SHORT).show();
                            } else { // 이미 있는 ID일 경우
                                Toast.makeText(getApplicationContext(), "사용 불가능한 ID 입니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                ID_overlap_chk_request ID_overlap_chk_request = new ID_overlap_chk_request(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(ID_overlap_chk_request);
            }
        });
        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userPass_chk = et_pass_chk.getText().toString();
                String userName = et_name.getText().toString();
                String userEmail = et_email.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("yes");
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String returnMsg = jsonObject.getString("returnMsg");
                            System.out.println(returnMsg);
                            if(returnMsg.equals("ID_already_exists")) {
                                Toast.makeText(getApplicationContext(),"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            }else if(returnMsg.equals("Email_already_exists")){
                                Toast.makeText(getApplicationContext(),"이미 존재하는 이메일입니다.",Toast.LENGTH_SHORT).show();
                            }else if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                //회원가입 폼 검사
                if (!userPass.equals(userPass_chk)){
                    Toast.makeText(getApplicationContext(),"작성한 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                }else if(userName.equals("")) {
                    Toast.makeText(getApplicationContext(),"이름을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else if(userEmail.equals("")) {
                    Toast.makeText(getApplicationContext(),"이메일을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else{
                    // 서버로 Volley를 이용해서 요청을 함.
                    RegisterRequest registerRequest = new RegisterRequest(userID,userPass,userName,userEmail, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }


            }
        });

    }
}