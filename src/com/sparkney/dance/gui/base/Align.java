/*
 * Alignment.java
 *
 * Created on den 6 oktober 2005, 22:08
 */

package com.sparkney.dance.gui.base;

/**
 *
 * @author Örjan Derelöv
 */
public class Align {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    public static final int BOTTOM = 3;
    public static final int CENTER = 4;

    private static final String[] alignments = {"left","right","top","bottom","center"};

    public static String toString(int align){
        return alignments[align];
    }
}
