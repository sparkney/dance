/*
 * ContainerCell.java
 *
 * Created on den 18 juli 2005, 22:24
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;

/**
 *
 * @author Örjan Derelöv
 *
 */
public class GridLayoutCell{

    protected Component component = null;   //The component in this cell
    protected String id = null;

    protected int hAlign = -1;
    protected int vAlign = -1;
    protected Padding padding = null;
    protected Border border = null;
    protected Color backgroundColor = null;

    public GridLayoutCell(Component component){
        this.component = component;
    }

    public GridLayoutCell setId(String id){
        this.id = id;
        return this;
    }

    /**
     * Horizontal alignment of the component in the cell.
     */
    public GridLayoutCell setHAlign(int hAlign){
        this.hAlign = hAlign;
        return this;
    }

    /**
     * Vertical alignment of the component in the cell.
     */
    public GridLayoutCell setVAlign(int vAlign){
        this.vAlign = vAlign;
        return this;
    }

    /**
     * Horizontal and vertical alignment of the component in the cell.
     */
    public GridLayoutCell setAlign(int hAlign, int vAlign){
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        return this;
    }

    /**
     * Minsta avståndet från cellens innerkanter till komponenten i cellen.
     */
    public GridLayoutCell setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }

    public GridLayoutCell setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }

    public GridLayoutCell setPadding(Padding padding){
        this.padding = padding;
        return this;
    }

    public GridLayoutCell setTopPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.top = width;
        return this;
    }

    public GridLayoutCell setRightPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.right = width;
        return this;
    }

    public GridLayoutCell setBottomPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.bottom = width;
        return this;
    }

    public GridLayoutCell setLeftPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.left = width;
        return this;
    }

    /*
     * Set the cell border. A cell border will always collapse with neighbour
     * cell borders.
     */
    public GridLayoutCell setBorder(Border border){
        this.border = border;
        return this;
    }

    /**
     * Set background color for this cell.
     */
    public GridLayoutCell setBackgroundColor(Color color){
        this.backgroundColor = color;
        return this;
    }

    public Component getComponent(){
        return component;
    }



}
