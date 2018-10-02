/*
 * Color.java
 *
 * Created on den 26 juli 2005, 01:49
 */

package com.sparkney.dance.gui.base;

/**
 *
 * @author Örjan Derelöv
 */
public class Color{

    private String color = null;
    private RGB rgb = null;
    private Float opacity = null;
    
    /**
     * Set a hex color or a color name like "#ffdd44" or "green".
     */
    public Color(String value){
        this.color = value;
    }

    /**
     * Create a new RGB color from a three integers, 0,0,0 - 255,255,255.
     */
    public Color(int red, int green, int blue){
        rgb = new RGB();
        rgb.red = red;
        rgb.green = green;
        rgb.blue = blue;
    }
    
    /**
     * Set the RGB opacity in percentage. For this to work, the RGB color
     * constructor have to be used.
     * 
     * @param opacity in percentage 0-100.
     */
    public Color setOpacity(int opacity){
        this.opacity = opacity/100F;
        return this;
    }
    
    public String toString(){
        if(rgb!=null){
            if(opacity!=null){
                return "rgba("+rgb.red+","+rgb.green+","+rgb.blue+","+opacity +")";
            }else{
                return "rgba("+rgb.red+","+rgb.green+","+rgb.blue+")";
            }
        }else{
            return color;
        }
    }
    
    private class RGB{
        int red = 0;
        int green = 0;
        int blue = 0;
    }
    




}
