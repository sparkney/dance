/*
 * Repete.java
 *
 * Created on den 31 augusti 2006, 11:58
 */

package com.sparkney.dance.gui.base;

/**
 *
 * @author Örjan Derelöv
 */
public class Repete {

    public static final int NO_REPETE = 0;
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;
    public static final int AREA = 3;

    private static final String[] alignments = {"no-repeat","repeat-x","repeat-y","repeat"};

    public static String toString(int align){
        return alignments[align];
    }
}
