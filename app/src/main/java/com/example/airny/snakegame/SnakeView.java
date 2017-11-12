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
}
