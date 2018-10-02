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
 * The container cell holds the component added to the container and the
 * properties that is associated with this particular cell and component.
 */
public class GridSpanLayoutCell{

    protected Component component = null;   //The component in this cell

    protected int hAlign = -1;
    protected int vAlign = -1;
    protected Padding padding = null;
    protected Border border = null;
    protected Color backgroundColor = null;
    protected Image backgroundImage = null;


    protected int gridX = 0;        //0->not explicitly laid out, first cell position is gridX=1,gridY=1
    protected int gridY = 0;        //0->not explicitly laid out, first cell position is gridX=1,gridY=1
    protected int spanWidth = 1;    //By default the component spans only one cell
    protected int spanHeight = 1;   //By default the component spans only one cell
    protected boolean rendered = false;


    public GridSpanLayoutCell(Component component){
        this.component = component;
    }

    /**
     * Horizontal alignment of the component in the cell.
     */
    public GridSpanLayoutCell setHorizontalAlignment(int hAlign){
        this.hAlign = hAlign;
        return this;
    }

    /**
     * Vertical alignment of the component in the cell.
     */
    public GridSpanLayoutCell setVerticalAlignment(int vAlign){
        this.vAlign = vAlign;
        return this;
    }

    /**
     * Horizontal and vertical alignment of the component in the cell.
     */
    public GridSpanLayoutCell setAlignment(int hAlign, int vAlign){
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        return this;
    }

    /**
     * Minsta avståndet från cellens innerkanter till komponenten i cellen.
     */
    public GridSpanLayoutCell setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }

    public GridSpanLayoutCell setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }

    public GridSpanLayoutCell setPadding(Padding padding){
        this.padding = padding;
        return this;
    }

    /*
     * Set the cell border. A cell border will always collapse with neighbour
     * cell borders.
     */
    public GridSpanLayoutCell setBorder(Border border){
        this.border = border;
        return this;
    }

    public void setBackgroundColor(Color color){
        this.backgroundColor = color;
    }

    public GridSpanLayoutCell setBackgroundImage(Image image){
        this.backgroundImage = image;
        return this;
    }

    public Component getComponent(){
        return component;
    }



}
