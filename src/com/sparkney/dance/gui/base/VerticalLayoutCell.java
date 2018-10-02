/*
 * VerticalLayoutCell.java
 *
 * Created on den 11 mars 2015
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
public class VerticalLayoutCell{

    protected Component component = null;   //The component in this cell
    protected String id = null;

    protected int hAlign = -1;
    protected int vAlign = -1;
    protected int width = -1;
    protected boolean relativeWidth = false;
    protected Padding padding = null;
    protected Border border = null;
    protected Color backgroundColor = null;
    protected BackgroundImage backgroundImage = null;
    protected int backgroundRepete = -1;
    protected int backgroundHAlign = -1;
    protected int backgroundVAlign = -1;
    protected boolean display = true;
    protected String explicitStyle = null;
    protected String explicitAttribute = null;

    public VerticalLayoutCell(Component component){
        this.component = component;
    }

    public VerticalLayoutCell setId(String id){
        this.id = id;
        return this;
    }

    /**
     * Horizontal alignment of the component in the cell.
     */
    public VerticalLayoutCell setHAlign(int hAlign){
        this.hAlign = hAlign;
        return this;
    }

    public VerticalLayoutCell setVAlign(int vAlign){
        this.vAlign = vAlign;
        return this;
    }

    public VerticalLayoutCell setAlign(int hAlign,int vAlign){
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        return this;
    }

    /**
     * Desired height for this cell.
     */
    public VerticalLayoutCell setWidth(int width){
        this.relativeWidth = false;
        this.width = width;
        return this;
    }
    
    /**
     * Set desired relative width in procentage of the context space.
     */
    public VerticalLayoutCell setRelativeWidth(int width){
        this.relativeWidth = true;
        this.width = width;
        return this;
    }
    
    /**
     * Minsta avståndet från cellens innerkanter till komponenten i cellen.
     */
    public VerticalLayoutCell setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }

    public VerticalLayoutCell setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }

    public VerticalLayoutCell setPadding(Padding padding){
        this.padding = padding;
        return this;
    }
    
    public VerticalLayoutCell setTopPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.top = width;
        return this;
    }

    public VerticalLayoutCell setRightPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.right = width;
        return this;
    }

    public VerticalLayoutCell setBottomPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.bottom = width;
        return this;
    }

    public VerticalLayoutCell setLeftPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.left = width;
        return this;
    }

    /*
     * Set the cell border. A cell border will always collapse with neighbour
     * cell borders.
     */
    public VerticalLayoutCell setBorder(Border border){
        this.border = border;
        return this;
    }

    /**
     * Set background color for this cell.
     */
    public VerticalLayoutCell setBackgroundColor(Color color){
        this.backgroundColor = color;
        return this;
    }

    public VerticalLayoutCell setBackgroundImage(BackgroundImage image){
        this.backgroundImage = image;
        return this;
    }
    
    public VerticalLayoutCell setBackgroundRepete(int repete){
        this.backgroundRepete = repete;
        return this;
    }
    
    public VerticalLayoutCell setBackgroundAlignment(int horizontalAlignment, int verticalAlignment){
        this.backgroundHAlign = horizontalAlignment;
        this.backgroundVAlign = verticalAlignment;
        return this;
    }

    public VerticalLayoutCell setDisplay(boolean display){
        this.display = display;
        return this;
    }

    /**
     * Add explicit style. Should only be used when nothing else works.
     */
    public VerticalLayoutCell addExplicitStyle(String name, String value){
        if(this.explicitStyle==null){
            this.explicitStyle = name + ":" + value + ";";
        }else{
            this.explicitStyle += name + ":" + value + ";";
        }
        return this;
    }

    public VerticalLayoutCell addExplicitAttribute(String name, String value){
        if(this.explicitAttribute==null){
            this.explicitAttribute = name + ":" + value + ";";
        }else{
            this.explicitAttribute += name + ":" + value + ";";
        }
        return this;
    }

    public Component getComponent(){
        return component;
    }



}
