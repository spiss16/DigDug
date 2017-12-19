package com.mattspissell.digdug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jessica on 12/13/2017.
 */

//All of the everything!!!

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
    private Paint paint;
    private int numSides = 3;
    private ArrayList<Tunnel> tunnel;
    private long tunnelStartTime;

    private ArrayList<Monsters> monsters;
    private ArrayList<Monsters2> monsters2;
    private long monstersstarttime;
    private Random rand = new Random();

    private boolean newGameCreated;
    private Death death;
    private long startReset;
    private boolean reset;
    private boolean disappear;
    private boolean started;

    private int best = 0;

    public static final int MOVESPEED = -3;

    private Bitmap background, startscreen;
    private Background bg;
    private Player player;
    private Dragon dragon;
    private Goggles goggles;
    private Bitmap spritesheet, spritesheet2, deathspritesheet;

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

        //Bunches of monsters
        monsters = new ArrayList<>();
        monsters2 = new ArrayList<>();

        monstersstarttime = System.nanoTime();
        //tunnels
        tunnel = new ArrayList<>();

        tunnelStartTime = System.nanoTime();

        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch (View view, MotionEvent event){

        //borders for touching the screen
        int hL = SCREEN_HEIGHT/2;
        int vLL = SCREEN_WIDTH/4;
        int vLR = (3*SCREEN_WIDTH)/4;

        //getting the location of the screen touch
        int x = (int) event.getX();
        int y = (int) event.getY();

        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(!player.getPlaying() && newGameCreated && reset) {
                player.setPlaying(true);
            }
            if(player.getPlaying())
            {
                if(!started)started = true;
                reset = false;
            }

            return true;
        }
        //setting the direction of Dug's acceleration based on screen touch
        if(event.getAction()==MotionEvent.ACTION_UP){
            //right
            if((x>vLR)&&(x<SCREEN_WIDTH)&&(y>0)&&(y<SCREEN_HEIGHT)){
            player.setUp(false);
            player.setDown(false);
            player.setLeft(false);
            player.setRight(true);
        }
        //up
            else if((x>vLL)&&(x<vLR)&&(y>0)&&(y<hL)){
                player.setUp(true);
                player.setDown(false);
                player.setLeft(false);
                player.setRight(false);
            }
            //left
            else if((x>0)&&(x<vLL)&&(y>0)&&(y<SCREEN_HEIGHT)){
                player.setUp(false);
                player.setDown(false);
                player.setLeft(true);
                player.setRight(false);
            }
            //down
            else if((x>vLL)&&(x<vLR)&&(y>hL)&&(y<SCREEN_HEIGHT)){
                player.setUp(false);
                player.setDown(true);
                player.setLeft(false);
                player.setRight(false);

            }
            return true;
        }
        return super.onTouchEvent(event);
    }


    //running the business
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
    //pausing the business
    public void pause(){
        playing = false;
        try{
            gameThread.join();
        }
        catch(InterruptedException e){}
    }
    //resuming the business
    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //updating the game
    void update(){

        if(player.getPlaying()) {
            //update
            bg.update();


            // building the tunnel, slows down performance
            long elapsed = (System.nanoTime()-tunnelStartTime)/1;
            if(elapsed > 1000000000)
            {
                tunnel.add(new Tunnel(player.getX(),player.getY()));
                tunnelStartTime = System.nanoTime();
            }
            //remove tunnels after they pass
            for (int i = 0; i < tunnel.size(); i++)
            {
                    tunnel.get(i).update();
                    if (tunnel.get(i).getX() < -10) {
                        tunnel.remove(i);
                    }
            }

            player.update();

            //add monsters with a timer
            long monsterselapsed = (System.nanoTime()-monstersstarttime)/1000000;
            if(monsterselapsed>(2000- player.getScore()/4)){

                //first monster goes down middle
                if(monsters.size()==0)
                {
                    spritesheet = BitmapFactory.decodeResource(getResources(),R.drawable.dragon);
                    spritesheet = Bitmap.createScaledBitmap(spritesheet,2360,150, false);
                    monsters.add(new Monsters(spritesheet,SCREEN_WIDTH+10,SCREEN_HEIGHT/2, 160, 140,0, 2));
                }
                //other monsters are randomly generated
                else
                {
                    spritesheet = BitmapFactory.decodeResource(getResources(),R.drawable.dragon);
                    spritesheet = Bitmap.createScaledBitmap(spritesheet,2360,150, false);
                    monsters.add(new Monsters(spritesheet,SCREEN_WIDTH+10,(int)((rand.nextDouble()*SCREEN_HEIGHT)+245), 160, 140,0, 2));

                    spritesheet2 = BitmapFactory.decodeResource(getResources(),R.drawable.goggles);
                    spritesheet2 = Bitmap.createScaledBitmap(spritesheet2,1250,150, false);
                    monsters2.add(new Monsters2(spritesheet2,SCREEN_WIDTH+10,(int)((rand.nextDouble()*SCREEN_HEIGHT)+245), 160, 150,0, 2));

                }
                //reset the timer
                monstersstarttime = System.nanoTime();
            }
            //check through all monsters for collisions or for necessary removal
            for(int i = 0; i< monsters.size(); i++)
            {
                //update the monsters
                monsters.get(i).update();
                //collision with a monster
                if(collision(monsters.get(i), player))
                {
                    monsters.remove(i);
                    player.setPlaying(false);
                    break;
                }
                //when monster gets offscreen
                if(monsters.get(i).getX()<-100)
                {
                    monsters.remove(i);
                    break;
                }
            }
            for(int i = 0; i< monsters2.size(); i++)
            {
                //update the monsters2
                monsters2.get(i).update();
                //collision with a monster2
                if(collision(monsters2.get(i), player))
                {
                    monsters2.remove(i);
                    player.setPlaying(false);
                    break;
                }
                //when monster gets offscreen
                if(monsters2.get(i).getX()<-100)
                {
                    monsters2.remove(i);
                    break;
                }
            }
            //add tunnel
            tunnel.add(new Tunnel(player.getX(),player.getY()));


        }
        else
        {
            player.resetDY();

            if(!reset)
            {
                newGameCreated = false;
                startReset = System.nanoTime();
                reset = true;
                disappear = true;

                spritesheet = BitmapFactory.decodeResource(getResources(),R.drawable.dugdead);
                spritesheet = Bitmap.createScaledBitmap(spritesheet,2000,200, false);
                death = new Death(spritesheet,player.getX(),player.getY(), 170, 200, 2);
            }

            death.update();
            long resetElapsed = (System.nanoTime() - startReset/1000000);

            if(resetElapsed>2500000 && !newGameCreated)
            {
                newGame();
            }

        }
    }

    //determine if Dug crashes into a monster
    public boolean collision(GameObject a, GameObject b)
    {

        if(Rect.intersects(a.getRectangle(), b.getRectangle()))
        {
            return true;
        }
        return false;
    }

    //drawing that business
    void draw(){
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();

            bg.draw(canvas);
            for(Tunnel sp: tunnel) {
                sp.draw(canvas);
            }
            if(!disappear) {
                player.draw(canvas);
            }
            for(Monsters m: monsters)
            {
                m.draw(canvas);
            }
            for(Monsters2 m: monsters2)
            {
                m.draw(canvas);
            }

            //draw death
            if(started)
            {
                death.draw(canvas);
            }

            drawText(canvas);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    //starting a new game for that business
    public void newGame()
    {
        if(player.getScore()>best)
        {
            best = player.getScore();
        }

        player.resetScore();
        disappear = false;
        monsters.clear();
        monsters2.clear();
        tunnel.clear();
        player.setY(SCREEN_HEIGHT/2);
        player.setX(100);

        newGameCreated = true;
    }
    //draw text to that screen
    public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(SCREEN_WIDTH/40);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Distance: "+ (player.getScore()), SCREEN_WIDTH/15, SCREEN_HEIGHT/12, paint);
        canvas.drawText("Best: " + best, (SCREEN_WIDTH-SCREEN_WIDTH/7) , SCREEN_HEIGHT/12, paint);

        if (!player.getPlaying()&&newGameCreated && reset)
        {
            Paint paint1 = new Paint();
            paint1.setColor(Color.RED);
            paint1.setTextSize(SCREEN_WIDTH/15);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("TIME TO EXCAVATE!", SCREEN_WIDTH/3, SCREEN_HEIGHT/2, paint1);

            paint1.setTextSize(SCREEN_WIDTH/30);
            canvas.drawText("TAP A DIRECTION TO DIG", SCREEN_WIDTH/2, (SCREEN_HEIGHT/2) + SCREEN_HEIGHT/8, paint1);

        }
    }

}
