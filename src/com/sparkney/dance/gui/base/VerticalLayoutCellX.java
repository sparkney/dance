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
public class VerticalLayoutCellX{

    protected Component component = null;   //The component in this cell
    protected String id = null;

    protected int hAlign = -1;
//    protected int vAlign = -1;
    protected int height = -1;
    protected boolean relativeHeight = false;
    protected Padding padding = null;
    protected Border border = null;
    protected Color backgroundColor = null;
    protected Image backgroundImage = null;
    protected int backgroundRepete = -1;
    protected int backgroundHAlign = -1;
    protected int backgroundVAlign = -1;
    protected boolean display = true;
    protected String explicitStyle = null;
    protected String explicitAttribute = null;
    

    public VerticalLayoutCellX(Component component){
        this.component = component;
    }

    public VerticalLayoutCellX setId(String id){
        this.id = id;
        return this;
    }

    /**
     * Horizontal alignment of the component in the cell.
     */
    public VerticalLayoutCellX setHorizontalAlignment(int hAlign){
        this.hAlign = hAlign;
        return this;
    }

    /**
     * Vertical alignment of the component in the cell.
     */
//    public VerticalLayoutCell setVerticalAlignment(int vAlign){
//        this.vAlign = vAlign;
//        return this;
//    }

    /**
     * Horizontal and vertical alignment of the component in the cell.
     */
//    public VerticalLayoutCell setAlignment(int hAlign, int vAlign){
//        this.hAlign = hAlign;
//        this.vAlign = vAlign;
//        return this;
//    }

    /**
     * Desired height for this cell.
     */
    public VerticalLayoutCellX setHeight(int height){
        relativeHeight = false;
        this.height = height;
        return this;
    }

    /**
     * Set desired relative height in procentage of the context space.
     */
    public VerticalLayoutCellX setRelativeHeight(int height){
        this.relativeHeight = true;
        this.height = height;
        return this;
    }

    /**
     * Minsta avståndet från cellens innerkanter till komponenten i cellen.
     */
    public VerticalLayoutCellX setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }

    public VerticalLayoutCellX setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }

    public VerticalLayoutCellX setPadding(Padding padding){
        this.padding = padding;
        return this;
    }

    /*
     * Set the cell border. A cell border will always collapse with neighbour
     * cell borders.
     */
    public VerticalLayoutCellX setBorder(Border border){
        this.border = border;
        return this;
    }

    /**
     * Set background color for this cell.
     */
    public VerticalLayoutCellX setBackgroundColor(Color color){
        this.backgroundColor = color;
        return this;
    }

    public VerticalLayoutCellX setBackgroundImage(Image image){
        this.backgroundImage = image;
        return this;
    }
    
    public VerticalLayoutCellX setBackgroundRepete(int repete){
        this.backgroundRepete = repete;
        return this;
    }
    
    public VerticalLayoutCellX setBackgroundAlignment(int horizontalAlignment, int verticalAlignment){
        this.backgroundHAlign = horizontalAlignment;
        this.backgroundVAlign = verticalAlignment;
        return this;
    }

    public VerticalLayoutCellX setDisplay(boolean display){
        this.display = display;
        return this;
    }

    /**
     * Add explicit style. Should only be used when nothing else works.
     */
    public VerticalLayoutCellX addExplicitStyle(String name, String value){
        if(this.explicitStyle==null){
            this.explicitStyle = name + ":" + value + ";";
        }else{
            this.explicitStyle += name + ":" + value + ";";
        }
        return this;
    }

    public VerticalLayoutCellX addExplicitAttribute(String name, String value){
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
