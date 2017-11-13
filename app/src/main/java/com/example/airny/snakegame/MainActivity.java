package com.example.airny.snakegame;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;


public class MainActivity extends AppCompatActivity {
    SnakeView snakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        //узнать размер экрана

        Point size = new Point();
        display.getSize(size);
        //загрузить размер в point

        snakeView = new SnakeView(this, size);
        //создадим новое основываясь на данных
        setContentView(snakeView);
        //стандартное окно на snakeView
    }

    @Override
    protected void onResume() {
        super.onResume();
        snakeView.resume();
    }
    // возвращает приложение
    // показывает игроку
    @Override
    protected void onPause() {
        super.onPause();;
        snakeView.pause();
    }
    //проверяет свернута ли игра или на пазуе
}
