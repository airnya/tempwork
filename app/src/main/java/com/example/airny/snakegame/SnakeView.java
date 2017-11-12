package com.example.airny.snakegame;

/**
 * Created by airny on 12.11.2017.
 */
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Random;

class SnakeView extends SurfaceView implements Runnable {
    private Thread m_Thread = null;
    //Каждый код будет запускаться отдельно

    private volatile boolean m_Playing;
    // это переменная будет определять - запущена ли игра
    // volatile -изменчивый
    // it can be accessed from inside and outside the thread

    private Canvas m_Canvas;
    // для рисования

    private SurfaceHolder m_Holder;
    // нужно для того тоб рисовать

    private Paint m_Paint;
    // это позволит изменять цвета

    private Context m_context;
    //это будет ссылка на Activity

    private SoundPool m_SoundPool;
    private int m_gead_mouse_sound = -1;
    private int m_dead_sound = -1;
    //звук

    public enum Direction {UP, DOWN, LEFT, RIGHT}
    //для отслеживания движения
    private Direction m_Direction = Direction.RIGHT;
    //старт при косании вправо

    private int m_ScreenWidght;
    private int m_ScreenHeight;
    //данные разрешения экрана

    private long m_NextFrameTime;
    //Контроль паузы между обновлениями
    private final long FPS = 10;
    //10 кадров в сек
    private final long MILLIS_IN_A_SECOND = 1000;
    // надо потестить с фпс

    private int m_Score;
    //текущие очки

    private int[] m_SnakeXs;
    private int[] m_SnakeYs;
    //отслеживаем сегменты

    private int m_SnakeLength;
    //размер змейки на данный момент

    private int m_MouseX;
    private int m_MouseY;
    //отслеживание мышки

    private int m_BlockSize;
    //размер пикселей сегмента змеи

    private final int NUM_BLOCKS_WIDE = 40;
    //размер экрана игры
    private int m_NumBlocksHigh;
    //размер определяется динамически
}

public SnakeView(Context context, Point size) {
    super(context);

    m_context = context;

    m_ScreenWidght = size.x;
    m_ScreenHeight = size.y;

    m_BlockSize = m_ScreenWidght / NUM_BLOCKS_WIDE;
    // определить размер каждого блока на игровом экране
    m_NumBlocksHigh = m_ScreenHeight / m_BlockSize;
    // Сколько блоков одинакого размера будет вписываться в высоту

    loadSound();
    //Загрузка звука сделаю потом

    m_Holder = getHolder();
    m_Paint = new Paint();
    //объекты рисования

    m_SnakeXs = new int[200];
    m_SnakeYs = new int[200];
    //При 200 очках игра скрашится

    startGame();
    //Запуск игры сделаю потом
}
