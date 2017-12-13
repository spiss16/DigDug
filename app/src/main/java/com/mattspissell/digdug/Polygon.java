package com.mattspissell.digdug;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Jessica on 11/28/2017.
 */

public class Polygon extends GameObject {
    protected Vector2 position;
    protected int numSides = 3;
    protected Vector2[] points;
    protected Paint paint;
    protected float radius;
    protected float accumulatedAngle = 0.0f;

    public Polygon(Vector2 position, int numSides, float radius) {
        this.position = position;
        if(numSides <= 2)
            numSides = 3;
        this.numSides = numSides;
        if(radius <= 0.0f)
            radius = 1.0f;
        this.radius = radius;
        paint = new Paint();
        paint.setColor(Color.argb(255, 0, 255, 0));
        paint.setStrokeWidth(4.0f);
        points = new Vector2[numSides];
        calculatePoints();
    }

    protected void calculatePoints(){
        float angle = (float) (2.0 * Math.PI / numSides);
        double cumulativeRadians = angle;
        for(int i = 0; i < numSides; i++){
            points[i] = new Vector2(
                    radius * (float) Math.cos(cumulativeRadians + accumulatedAngle),
                    radius * (float) Math.sin(cumulativeRadians + accumulatedAngle));
            cumulativeRadians += angle;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void onUpdate() {
        accumulatedAngle += Math.PI / 10f * GameView.deltaTime;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int length = points.length;
        Vector2 temp1;
        Vector2 temp2;
        for(int i = 0; i < length - 1; i++){
            temp1 = Vector2.add(position, points[i]);
            temp2 = Vector2.add(position, points[i+1]);
            canvas.drawLine(temp1.x, temp1.y, temp2.x, temp2.y, paint);
        }

        temp1 = Vector2.add(position, points[length - 1]);
        temp2 = Vector2.add(position, points[0]);
        canvas.drawLine(temp1.x, temp1.y, temp2.x, temp2.y, paint);
    }
}
