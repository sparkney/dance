/*
 * Spacing.java
 *
 * Created on den 16 januari 2015, 15:50
 */

package com.sparkney.dance.gui.base;

/**
 * Defines a border spacing.
 *
 * @author Örjan Derelöv
 *
 */
public class Spacing {

    protected int top = 0;
    protected int left = 0;
    protected int bottom = 0;
    protected int right = 0;

    public Spacing(){
    }

    public Spacing(int width){
        this.top = width;
        this.left = width;
        this.bottom = width;
        this.right = width;
    }

    public Spacing(int top, int right, int bottom, int left){
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public Spacing setTop(int top){
        this.top = top;
        return this;
    }

    public Spacing setLeft(int left){
        this.left = left;
        return this;
    }

    public Spacing setBottom(int bottom){
        this.bottom = bottom;
        return this;
    }

    public Spacing setRight(int right){
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


}
