package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IndexActivity extends AppCompatActivity {
    //1.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        //getSupportActionBar().setTitle("Index 맛집");  // --> 얘를 주면 어플이 꺼짐
    }
    //2.크리에이트 후 시작
    @Override
    protected void onStart() {
        super.onStart();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //3.메인엑티비티로이동
                Intent intent=new Intent(IndexActivity.this,MainActivity.class);
                startActivity(intent); //다시안돌아올것이므로 스타트엑티비티
                finish(); //결과 => 인덱스 엑티비티 1.2초동안 보여주고 메인엑티비티로 이동하기
            }
        },1200); //얼마동안 딜레이할것인지 1초=1000
    }
}