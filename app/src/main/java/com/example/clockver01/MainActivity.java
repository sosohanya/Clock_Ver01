package com.example.clockver01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        TextView tvTime = (TextView) findViewById(R.id.tv_time);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Calendar current = Calendar.getInstance();

                        int year = current.get(Calendar.YEAR);
                        int month = current.get(Calendar.MONDAY);
                        int day = current.get(Calendar.DATE);
                        String dayWeek = current.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.KOREA);

                        int hour = current.get(Calendar.HOUR_OF_DAY);
                        int minute = current.get(Calendar.MINUTE);
                        int second = current.get(Calendar.SECOND);

                        tvDate.setText(String.format("%04d.%02d.%02d (%s)", year, month, day, dayWeek));
                        tvTime.setText(String.format("%02d:%02d:%02d", hour, minute, second));
                    }
                });
            }
        };

        // 추가 : 0 millisecond 에 시작되도록 지연 시간 계산
        Calendar startTick = Calendar.getInstance();
        int delay = 1000 - startTick.get(Calendar.MILLISECOND);
        if(delay == 1000){
            delay = 0;
        }

        // 함수가 실행하면서 생긴 지연 시간을 무시
        timer.scheduleAtFixedRate(timerTask, delay, 1000);
        // 수정 전 : 선행 작업(가비지 컬렉션 등)으로 지연이 발생할 수 있음
        //timer.schedule(timerTask, 0, 1000);
    }
}