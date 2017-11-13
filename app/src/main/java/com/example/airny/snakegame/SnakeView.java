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
    private int m_get_mouse_sound = -1;
    private int m_dead_sound = -1;
    //звук

    public enum Direction {UP, RIGHT, DOWN, LEFT}

    //для отслеживания движения
    private Direction m_Direction = Direction.RIGHT;
    //старт при косании вправо

    private int m_ScreenWidth;
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


    public SnakeView(Context context, Point size) {
        super(context);

        m_context = context;

        m_ScreenWidth = size.x;
        m_ScreenHeight = size.y;

        m_BlockSize = m_ScreenWidth / NUM_BLOCKS_WIDE;
        // определить размер каждого блока на игровом экране
        m_NumBlocksHigh = m_ScreenHeight / m_BlockSize;
        // Сколько блоков одинакого размера будет вписываться в высоту

    loadSound();
        //Загрузка звука

        m_Holder = getHolder();
        m_Paint = new Paint();
        //объекты рисования

        m_SnakeXs = new int[200];
        m_SnakeYs = new int[200];
        //При 200 очках игра скрашится

    startGame();
        //Запуск игры
    }
    public void startGame() {
        // Start with just a head, in the middle of the screen
        m_SnakeLength = 1;
        m_SnakeXs[0] = NUM_BLOCKS_WIDE / 2;
        m_SnakeYs[0] = m_NumBlocksHigh / 2;

        // And a mouse to eat
        spawnMouse();

        // Reset the m_Score
        m_Score = 0;

        // Setup m_NextFrameTime so an update is triggered immediately
        m_NextFrameTime = System.currentTimeMillis();
    }

    public void spawnMouse() {
        Random random = new Random();
        m_MouseX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        m_MouseY = random.nextInt(m_NumBlocksHigh - 1) + 1;
    }

    public void loadSound() {
        m_SoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            // Create objects of the 2 required classes
            // Use m_Context because this is a reference to the Activity
            AssetManager assetManager = m_context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the two sounds in memory
            descriptor = assetManager.openFd("get_mouse_sound.ogg");
            m_get_mouse_sound = m_SoundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("death_sound.ogg");
            m_dead_sound = m_SoundPool.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }
    }
    private void eatMouse(){
        //  Got one! Squeak!!
        // Increase the size of the snake
        m_SnakeLength++;
        //replace the mouse
        spawnMouse();
        //add to the m_Score
        m_Score = m_Score + 1;
        m_SoundPool.play(m_get_mouse_sound, 1, 1, 0, 0, 1);
    }
    private void moveSnake(){
        // Move the body
        for (int i = m_SnakeLength; i > 0; i--) {
            // Start at the back and move it
            // to the position of the segment in front of it
            m_SnakeXs[i] = m_SnakeXs[i - 1];
            m_SnakeYs[i] = m_SnakeYs[i - 1];

            // Exclude the head because
            // the head has nothing in front of it
        }

        // Move the head in the appropriate m_Direction
        switch (m_Direction) {
            case UP:
                m_SnakeYs[0]--;
                break;

            case RIGHT:
                m_SnakeXs[0]++;
                break;

            case DOWN:
                m_SnakeYs[0]++;
                break;

            case LEFT:
                m_SnakeXs[0]--;
                break;
        }
    }
    public void updateGame() {
        // Did the head of the snake touch the mouse?
        if (m_SnakeXs[0] == m_MouseX && m_SnakeYs[0] == m_MouseY) {
            eatMouse();
        }

        moveSnake();

        if (detectDeath()) {
            //start again
            m_SoundPool.play(m_dead_sound, 1, 1, 0, 0, 1);

            startGame();
        }
    }
    private boolean detectDeath(){
        // Has the snake died?
        boolean dead = false;

        // Hit a wall?
        if (m_SnakeXs[0] == -1) dead = true;
        if (m_SnakeXs[0] >= NUM_BLOCKS_WIDE) dead = true;
        if (m_SnakeYs[0] == -1) dead = true;
        if (m_SnakeYs[0] == m_NumBlocksHigh) dead = true;

        // Eaten itself?
        for (int i = m_SnakeLength - 1; i > 0; i--) {
            if ((i > 4) && (m_SnakeXs[0] == m_SnakeXs[i]) && (m_SnakeYs[0] == m_SnakeYs[i])) {
                dead = true;
            }
        }

        return dead;
    }
    public void drawGame() {
        // Prepare to draw
        if (m_Holder.getSurface().isValid()) {
            m_Canvas = m_Holder.lockCanvas();

            // Clear the screen with my favorite color
            m_Canvas.drawColor(Color.argb(255, 120, 197, 87));

            // Set the color of the paint to draw the snake and mouse with
            m_Paint.setColor(Color.argb(255, 255, 255, 255));

            // Choose how big the score will be
            m_Paint.setTextSize(30);
            m_Canvas.drawText("Score:" + m_Score, 10, 30, m_Paint);

            //Draw the snake
            for (int i = 0; i < m_SnakeLength; i++) {
                m_Canvas.drawRect(m_SnakeXs[i] * m_BlockSize,
                        (m_SnakeYs[i] * m_BlockSize),
                        (m_SnakeXs[i] * m_BlockSize) + m_BlockSize,
                        (m_SnakeYs[i] * m_BlockSize) + m_BlockSize,
                        m_Paint);
            }

            //draw the mouse
            m_Canvas.drawRect(m_MouseX * m_BlockSize,
                    (m_MouseY * m_BlockSize),
                    (m_MouseX * m_BlockSize) + m_BlockSize,
                    (m_MouseY * m_BlockSize) + m_BlockSize,
                    m_Paint);

            // Draw the whole frame
            m_Holder.unlockCanvasAndPost(m_Canvas);
        }
    }
    public boolean checkForUpdate() {

        // Are we due to update the frame
        if(m_NextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            m_NextFrameTime =System.currentTimeMillis() + MILLIS_IN_A_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }
    @Override
    public void run() {
        while (m_Playing) {
            //Проверка переменной m_Playing предотвращает ошибку при старте
            //обновление 10 кадро в секунду
            if (checkForUpdate()) {
                updateGame();
                drawGame();
            }
        }
    }

    public void pause() {
        m_Playing = false;
        try {
            m_Thread.join();
        } catch (InterruptedException e) {
            //ошибка
        }
    }

    public void resume() {
        m_Playing = true;
        m_Thread = new Thread(this);
        m_Thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= m_ScreenWidth / 2) {
                    switch(m_Direction){
                        case UP:
                            m_Direction = Direction.RIGHT;
                            break;
                        case RIGHT:
                            m_Direction = Direction.DOWN;
                            break;
                        case DOWN:
                            m_Direction = Direction.LEFT;
                            break;
                        case LEFT:
                            m_Direction = Direction.UP;
                            break;
                    }
                } else {
                    switch(m_Direction){
                        case UP:
                            m_Direction = Direction.LEFT;
                            break;
                        case LEFT:
                            m_Direction = Direction.DOWN;
                            break;
                        case DOWN:
                            m_Direction = Direction.RIGHT;
                            break;
                        case RIGHT:
                            m_Direction = Direction.UP;
                            break;
                    }
                }
        }
        return true;
    }
 }
