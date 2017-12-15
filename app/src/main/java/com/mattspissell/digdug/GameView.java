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
    int i, dir;
    private int numSides = 3;
    private ArrayList<Tunnel> tunnel;
    private long tunnelStartTime;

    public static final int MOVESPEED = -3;


    private Bitmap background;
    private Background bg;
    private Player player;
    private Dragon dragon;
    private Goggles goggles;
    private Bitmap spritesheet, dragonspritesheet, gogglesspritesheet;

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

        // Background

        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        background = Bitmap.createScaledBitmap(background,SCREEN_WIDTH,SCREEN_HEIGHT, false);
        bg = new Background(background);
        // Player
        spritesheet = BitmapFactory.decodeResource(getResources(),R.drawable.dugdig);
        spritesheet = Bitmap.createScaledBitmap(spritesheet,2550,140, false);
        player = new Player(spritesheet,160,140,2);

        //Dragon
        dragonspritesheet = BitmapFactory.decodeResource(getResources(),R.drawable.dragon);
        dragonspritesheet = Bitmap.createScaledBitmap(dragonspritesheet,2360,150, false);
        dragon = new Dragon(dragonspritesheet,160,140,2);
        //Goggles
        gogglesspritesheet = BitmapFactory.decodeResource(getResources(),R.drawable.goggles);
        gogglesspritesheet = Bitmap.createScaledBitmap(gogglesspritesheet,1250,150, false);
        goggles = new Goggles(gogglesspritesheet,160,150,2);

        tunnel = new ArrayList<>();

        tunnelStartTime = System.nanoTime();

        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch (View view, MotionEvent event){
        player.setPlaying(true);

        int hL = SCREEN_HEIGHT/2;
        int vLL = SCREEN_WIDTH/4;
        int vLR = (3*SCREEN_WIDTH)/4;

        int x = (int) event.getX();
        int y = (int) event.getY();
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            //right
            if((x>vLR)&&(x<SCREEN_WIDTH)&&(y>0)&&(y<SCREEN_HEIGHT)){
            player.setUp(false);
            player.setDown(false);
            player.setLeft(false);
            player.setRight(true);
            player.setx(false);
            player.sety(false);
        }
        //up
        else if((x>vLL)&&(x<vLR)&&(y>0)&&(y<hL)){
                player.setUp(true);
                player.setDown(false);
                player.setLeft(false);
                player.setRight(false);
                player.setx(false);
                player.sety(true);
            }
            //left
            else if((x>0)&&(x<vLL)&&(y>0)&&(y<SCREEN_HEIGHT)){
                player.setUp(false);
                player.setDown(false);
                player.setLeft(true);
                player.setRight(false);
                player.setx(true);
                player.sety(false);
            }
            //down
            else if((x>vLL)&&(x<vLR)&&(y>hL)&&(y<SCREEN_HEIGHT)){
                player.setUp(false);
                player.setDown(true);
                player.setLeft(false);
                player.setRight(false);
                player.setx(true);
                player.sety(true);

            }
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
        if(player.getPlaying()) {
            bg.update();


            // Investigate this to improve performance
            long elapsed = (System.nanoTime()-tunnelStartTime)/1;
            if(elapsed > 1000000000) {
                tunnel.add(new Tunnel(player.getX(),player.getY()));
                tunnelStartTime = System.nanoTime();
            }

                for (int i = 0; i < tunnel.size(); i++) {
                    tunnel.get(i).update();
                    if (tunnel.get(i).getX() < -10) {
                        tunnel.remove(i);
                    }
                }

            player.update();
            dragon.update();
            goggles.update();
            tunnel.add(new Tunnel(player.getX(),player.getY()));


        }
    }

    void draw(){
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();

            bg.draw(canvas);
            for(Tunnel sp: tunnel) {
                sp.draw(canvas);
            }
            player.draw(canvas);
            dragon.draw(canvas);
            goggles.draw(canvas);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
