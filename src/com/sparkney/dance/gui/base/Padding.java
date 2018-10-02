/*
 * Padding.java
 *
 * Created on den 26 juli 2005, 00:40
 */

package com.sparkney.dance.gui.base;

/**
 * Defines a padding.
 *
 * @author Örjan Derelöv
 *
 */
public class Padding {

    protected int top = 0;
    protected int left = 0;
    protected int bottom = 0;
    protected int right = 0;

    public Padding(){
    }

    public Padding(int width){
        this.top = width;
        this.left = width;
        this.bottom = width;
        this.right = width;
    }

    public Padding(int top, int right, int bottom, int left){
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public Padding setTop(int top){
        this.top = top;
        return this;
    }

    public Padding setLeft(int left){
        this.left = left;
        return this;
    }

    public Padding setBottom(int bottom){
        this.bottom = bottom;
        return this;
    }

    public Padding setRight(int right){
        this.right = right;
        return this;
    }

    public int getTop(){
        return top;
    }

    public int getLeft(){
        return left;
    }

    public int getBottom(){
        return bottom;
    }

    public int getRight(){
        return right;
    }

    @Override
    public String toString() {
        //TODO: Naiv implementation.
        return "padding:"+top+"px "+right+"px "+bottom+"px "+left+"px;";
    }

    


}
