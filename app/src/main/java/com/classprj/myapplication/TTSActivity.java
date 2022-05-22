package com.classprj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.classprj.myapplication.read.PlayState;
import com.classprj.myapplication.read.TextPlayer;

import org.json.JSONArray;
import org.json.JSONObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

public class TTSActivity extends AppCompatActivity implements TextPlayer, View.OnClickListener {

    String idx;

    private static String url = "http://192.168.219.103:80/book_content.php";

    //책받을 번들 << 알아볼것
    private final Bundle params = new Bundle();
    //현 읽고있는 글 색상 yellow 부분 수정할것
    private final BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.YELLOW);
    //enum
    private PlayState playState = PlayState.STOP;
    private Spannable spannable;
    private int standbyIndex = 0;
    private int lastPlayIndex = 0;
    int nowpage = 0;

    //book이 맞나?
    ArrayList<BookData> booklist = new ArrayList<>();
    private TextToSpeech tts;

    Button playBtn, pauseBtn, stopBtn, gonext, goback;
    TextView contentTextView, pagecheck;

    //테스트용


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        Intent intent = getIntent();
        //idx = intent.getExtras().getString("idx");


        initView();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String Book_ID = jsonObject.getString("book_id");
                        String Book_Page = jsonObject.getString("book_page");
                        String Book_Content = jsonObject.getString("book_content");
                        String Content_Length = jsonObject.getString("content_length");
                        String Book_Page_Idx = jsonObject.getString("book_page_idx");

                        booklist.add(new BookData(Book_ID ,Book_Page, Book_Content, Content_Length, Book_Page_Idx));

                    }
                    initTTS();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
            }
        });
        queue.add(req);



    }

    //초기화
    private void initView() {
        playBtn = findViewById(R.id.btn_play);
        pauseBtn = findViewById(R.id.btn_pause);
        stopBtn = findViewById(R.id.btn_stop);
        contentTextView = findViewById(R.id.tv_content);
        gonext = (Button) findViewById(R.id.btn_next);
        goback = (Button) findViewById(R.id.btn_back);
        pagecheck = findViewById(R.id.page_check);

        playBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        gonext.setOnClickListener(this);
        goback.setOnClickListener(this);
    }

    //TTS 기능
    private void initTTS() {
        contentTextView.setText(booklist.get(nowpage).getBOOK_CONTENT());
        pagecheck.setText((nowpage +1 ) + "/" + booklist.size());
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int state) {
                if (state == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.KOREAN);
                } else {
                    showState("TTS 객체 초기화 중 문제가 발생했습니다.");
                }
            }
        });

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
            }

            @Override
            public void onDone(String s) {
                clearAll();
            }

            @Override
            public void onError(String s) {
                showState("재생 중 에러가 발생했습니다.");
            }

            @Override
            public void onRangeStart(String utteranceId, int start, int end, int frame) {
                //changeHighlight(standbyIndex + start, standbyIndex + end);
                lastPlayIndex = start;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                startPlay();
                break;

            case R.id.btn_pause:
                pausePlay();
                break;

            case R.id.btn_stop:
                stopPlay();
                break;

            case R.id.btn_next:
                nextpage();
                break;

            case R.id.btn_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        }
        showState(playState.getState());
    }

    //버튼 관리
    @Override
    public void startPlay() {
        String content = contentTextView.getText().toString();
        if (playState.isStopping() && !tts.isSpeaking()) {
            startSpeak(content);
        } else if (playState.isWaiting()) {
            standbyIndex += lastPlayIndex;
            startSpeak(content.substring(standbyIndex));
        }
        playState = PlayState.PLAY;
    }

    @Override
    public void pausePlay() {
        if (playState.isPlaying()) {
            playState = PlayState.WAIT;
            tts.stop();
        }
    }

    @Override
    public void stopPlay() {
        tts.stop();
        clearAll();
    }

    public void nextpage() {
        if (nowpage < booklist.size()) {
            contentTextView.setText(booklist.get(nowpage + 1).getBOOK_CONTENT());
            nowpage++;
            pagecheck.setText((nowpage+1) + "/" + booklist.size());
        } else {
            AlertDialog.Builder malert = new AlertDialog.Builder(TTSActivity.this);
            malert.setTitle("");
            malert.setMessage("다 읽었습니다. 예/아니오로 나눌것");

            malert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // OK 버튼을 눌렸을 경우
                    Toast.makeText(getApplicationContext(), "Pressed OK",
                            Toast.LENGTH_SHORT).show();
                }
            });
            malert.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Cancle 버튼을 눌렸을 경우
                    Toast.makeText(getApplicationContext(), "Pressed Cancle",
                            Toast.LENGTH_SHORT).show();
                }
            });
            malert.show();
        }

    }

    //글자 색
    /*
    private void changeHighlight(final int start, final int end) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spannable.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });
    }
*/
    //실질적 tts 읽는 부분
    private void startSpeak(final String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, params, text);
    }

    //멈췄을때
    private void clearAll() {
        playState = PlayState.STOP;
        standbyIndex = 0;
        lastPlayIndex = 0;
/*
        if (spannable != null) {
            changeHighlight(0, 0); // remove highlight
        }
*/
    }

    private void showState(final String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if (playState.isPlaying()) {
            pausePlay();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (playState.isWaiting()) {
            startPlay();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        tts.stop();
        tts.shutdown();
        super.onDestroy();
    }

    // 책 내용 요청
    private void BookRequest() {

    }
}