package com.mattspissell.digdug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.mattspissell.digdug.GameObject;

import java.util.Random;

/**
 * Created by Jessica on 12/13/2017.
 */

public class Starfield extends GameObject {

    protected Vector2 points[];
    protected int numberOfPoints;
    protected Paint paint;
    protected float currentAlpha = 0;
    protected float twinkleSpeed = 20.0f;
    protected int direction = 1;

    public Starfield(int numberOfPoints, float twinkleSpeed) {
        this.numberOfPoints = numberOfPoints;
        this.twinkleSpeed = twinkleSpeed;

        paint = new Paint();
        paint.setColor(Color.argb((int) currentAlpha, 255, 255, 255));
        points = new Vector2[numberOfPoints];
        Random random = new Random();
        for(int i = 0; i < numberOfPoints; i++){
            points[i] = new Vector2(random.nextInt(GameView.getScreenWidth()),
                    random.nextInt(GameView.getScreenHeight()))
;        }
    }

    @Override
    public void init() {

    }

    @Override
    public void onUpdate() {
        calculateAlpha();
    }

    private void calculateAlpha(){
        if(direction > 0){
            currentAlpha = (currentAlpha + twinkleSpeed * GameView.deltaTime * direction);
            if(currentAlpha >= 255){
                currentAlpha = 255;
                direction *= -1;
            }
        }
        else{
            currentAlpha = (currentAlpha + twinkleSpeed * GameView.deltaTime * direction);
            if(currentAlpha <= 0){
                currentAlpha = 0;
                direction *= -1;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.argb((int)currentAlpha, 255, 255, 255));
        for(int i = 0; i < numberOfPoints; i++){
            canvas.drawPoint(points[i].x, points[i].y, paint);
        }
    }
}
