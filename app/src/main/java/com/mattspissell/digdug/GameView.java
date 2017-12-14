package com.mattspissell.digdug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Jessica on 12/13/2017.
 */

public class GameView extends SurfaceView implements Runnable, View.OnTouchListener {

    volatile boolean playing = false;
    Thread gameThread;
    SurfaceHolder surfaceHolder;
    private static final int FPS = 30;
    private static final int TBF = 1000 / FPS;
    private long previousTImeMilliseconds;
    private long currentTimeMilliseconds;
    public static float deltaTime; //seconds
    Point screenSize;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_WIDTH;
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private Camera camera;
    private Paint paint;
    int i;
    private int numSides = 3;

    private Bitmap background;
    private Background bg;


    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public GameView(Context context, Point point) {
        super(context);

        surfaceHolder = this.getHolder();
        screenSize = point;
        SCREEN_HEIGHT = point.y;
        SCREEN_WIDTH = point.x;

        // Background starting here
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        background = Bitmap.createScaledBitmap(background,SCREEN_WIDTH,SCREEN_HEIGHT,false);
        bg = new Background(background);
        bg.setVector(-3);


        // Starfield example
        //  gameObjects.add(new Starfield(100,30.0f));
      // Polygon Example
        // GameObject temp = new Polygon(new Vector2(getScreenWidth()/2.0f, getScreenHeight()/2.0f),5, 20.0f);
        // gameObjects.add(temp);


        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch (View view, MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                gameObjects.add(new Polygon(new Vector2(event.getX(), event.getY()),
                        numSides, 60.0f));
                numSides++;
                break;
            case MotionEvent.ACTION_MOVE:
                break;

        }
        return true;
    }

    @Override
    public void run() {
        previousTImeMilliseconds = System.currentTimeMillis();
        while(playing){
            currentTimeMilliseconds = System.currentTimeMillis();
            deltaTime = (currentTimeMilliseconds - previousTImeMilliseconds) / 1000.0f;
            update();
            draw();
            try{
                gameThread.sleep(TBF);
            }
            catch(InterruptedException e){}
            previousTImeMilliseconds = currentTimeMilliseconds;
        }
    }

    public void pause(){
        playing = false;
        try{
            gameThread.join();
        }
        catch(InterruptedException e){}
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    void update(){
        for(i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).onUpdate();
        }

        bg.update();
    }

    void draw(){
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            //Draw
            // Example
              // canvas.drawARGB(255, 0, 0, 0);
              //for(i = 0; i < gameObjects.size(); i++){
              //  gameObjects.get(i).onDraw(canvas);
              //}

            bg.draw(canvas);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
