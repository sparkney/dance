/*
 * Margin.java
 *
 * Created on den 26 juli 2005, 00:40
 */

package com.sparkney.dance.gui.base;

/**
 *
 * @author Örjan Derelöv
 *
 * Defines a margin.
 *
 */
public class Margin {

    protected int top = 0;
    protected int left = 0;
    protected int bottom = 0;
    protected int right = 0;

    public Margin(){
    }

    public Margin(int width){
        this.top = width;
        this.left = width;
        this.bottom = width;
        this.right = width;
    }

    public Margin(int top, int right, int bottom, int left){
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public void setTop(int top){
        this.top = top;
    }

    public void setLeft(int left){
        this.left = left;
    }

    public void setBottom(int bottom){
        this.bottom = bottom;
    }

    public void setRight(int right){
        this.right = right;
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
        return "margin:"+top+"px "+right+"px "+bottom+"px "+left+"px;";
    }

}
