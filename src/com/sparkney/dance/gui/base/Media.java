/*
 * Media.java
 *
 * Created on den 31 dec 2014, 02:22
 */

package com.sparkney.dance.gui.base;

/**
 * As in all CSS, the order of media queries matters. When two rules conflict,
 * the last one that was declared is used. This fact is often used in media queries,
 * by just defining the max OR the min width of the display, and place the media
 * queries in order with the widest range first. (max=800 before max=600, etc)
 * In Dance, we don't want to bother with the order of the media queries, since it may
 * possibly lead to very complex rules with unexpected and hard to understand results.
 * This is why we force the user to set a width range/interval, rather then max or min
 * widths.
 *
 * @author Örjan Derelöv
 */
public class Media {

    protected int minWidth = -1;
    protected int maxWidth = -1;
    
    public Media() {
    }

    public Media(int minWidth, int maxWidth) {
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
    }

    /**
     * 
     * @param minWidth
     * @param maxWidth
     * @return
     */
    public Media setWidthRange(int minWidth, int maxWidth) {
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        return this;
    }

    public int getMinWidth(){
        return minWidth;
    }

    public int getMaxWidth(){
        return maxWidth;
    }

    

}
