package com.classprj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.classprj.myapplication.read.PlayState;
import com.classprj.myapplication.read.TextPlayer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class TTSActivity extends AppCompatActivity implements TextPlayer, View.OnClickListener {

    private static String IP_ADDRESS = "jhk.n-e.kr";
    private static String TAG = "phpreviewdownload";
    private String mJsonString;


    //책받을 번들 << 알아볼것
    private final Bundle params = new Bundle();
    //현 읽고있는 글 색상 yellow 부분 수정할것
    private final BackgroundColorSpan colorSpan = new BackgroundColorSpan(Color.YELLOW);
    //enum
    private PlayState playState = PlayState.STOP;
    private Spannable spannable;
    private int standbyIndex = 0;
    private int lastPlayIndex = 0;

    //book이 맞나?
    private ArrayList<Book> mArrayList;
    private TextToSpeech tts;

    Button playBtn, pauseBtn, stopBtn;
    TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        initView();

        initTTS();

    }

    private void initView() {
        playBtn = findViewById(R.id.btn_play);
        pauseBtn = findViewById(R.id.btn_pause);
        stopBtn = findViewById(R.id.btn_stop);
        contentTextView = findViewById(R.id.tv_content);

        playBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    private void initTTS() {
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
                changeHighlight(standbyIndex + start, standbyIndex + end);
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
        }
        showState(playState.getState());
    }

    @Override
    public void startPlay() {
        String content = null;/*inputEditText.getText().toString();*/
        if (playState.isStopping() && !tts.isSpeaking()) {
            setContentFromEditText(content);
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

    private void changeHighlight(final int start, final int end) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spannable.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });
    }

    private void setContentFromEditText(String content) {
        contentTextView.setText(content, TextView.BufferType.SPANNABLE);
        spannable = (SpannableString) contentTextView.getText();
    }

    private void startSpeak(final String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, params, text);
    }

    private void clearAll() {
        playState = PlayState.STOP;
        standbyIndex = 0;
        lastPlayIndex = 0;

        if (spannable != null) {
            changeHighlight(0, 0); // remove highlight
        }
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

    private class GetData extends AsyncTask<String, void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        // 백그라운드 작업 전
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null) {
                contentTextView.setText(errorString);
            } else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String serverURL = strings[0];
            String name = strings[1];

            String postParameters = "name=" + name;
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }
        }

        private void showResult() {

            String IDX = "idx";
            String BOOK_ID = "book_id";
            String BOOK_PAGE = "book_page";
            String BOOK_CONTENT = "book_content";
            String CONTENT_LENGTH = "content_length";
            String BOOK_PAGE_IDX = "book_page_idx";

            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(IDX);

                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject item = jsonArray.getJSONObject(i);

                    
                }
            }catch (Exception e){

            }

            /*
            String TAG_JSON="user";
            String TAG_rate = "rate";
            String TAG_userID ="userID";
            String TAG_title="title";
            String TAG_review="review";



            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);


                    String rate = item.getString(TAG_rate);
                    String userID = item.getString(TAG_userID);
                    String title =item.getString(TAG_title);
                    String review =item.getString(TAG_review);


                    ReviewData ReviewData = new ReviewData();

                    ReviewData.setReview_rate(rate);
                    ReviewData.setReview_userID(userID);
                    ReviewData.setReview_title(title);
                    ReviewData.setReview_review(review);


                    mArrayList.add(ReviewData);
                    mAdapter.notifyDataSetChanged();
                }



            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }
            */
        }
    }
}