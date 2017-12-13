package com.mattspissell.digdug;

import java.util.Vector;

/**
 * Created by Jessica on 12/13/2017.
 */

public class Vector2 {
    public float x;
    public float y;
    public static float magnitude(Vector2 vector2){
        return (float)Math.sqrt(vector2.x * vector2.x + vector2.y * vector2.y);
    }
    public static void normalize(Vector2 vector){
        double temp = magnitude(vector);
        vector.x /= temp;
        vector.y /= temp;
    }
    public Vector2(){
        this.x = 0;
        this.y = 0;
    }
    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    //Addition
    public Vector2 add(Vector2 input){
        return new Vector2(this.x + input.x, this.y + input.y);
    }
    //Subtraction
    public Vector2 subtract(Vector2 input){
        return new Vector2(this.x - input.x, this.y - input.y);
    }
    //Scalar Multiply
    public Vector2 scalarMultiply(float scalar){
        return new Vector2(this.x * scalar, this.y * scalar);
    }
    //Dot Product
    public float dotProduct(Vector2 input){
        return this.x * input.x + this.y * input.y;
    }

}
