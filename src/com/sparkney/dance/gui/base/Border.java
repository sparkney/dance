/*
 * Border.java
 *
 * Created on den 20 juli 2005, 02:48
 * 
 * CHANGES
 *
 * 171130
 * Widths work as intended and cell widths that are set at layout level can now
 * be removed at the layout cell level. 
 * 
 */

package com.sparkney.dance.gui.base;

/**
 *
 * 
 * @author Örjan Derelöv
 */
public class Border{

    public enum Style {
        DOTTED,DASHED,SOLID,DOUBLE,GROOVE,RIDGE,INSET,OUTSET;

        @Override
        public String toString() {
            switch(this) {
                case DOTTED: return "dotted";
                case DASHED: return "dashed";
                case SOLID: return "solid";
                case DOUBLE: return "double";
                case GROOVE: return "groove";
                case RIDGE: return "ridge";
                case INSET: return "inset";
                case OUTSET: return "outset";
                default: throw new IllegalArgumentException();
            }
        }
    }

    protected int topWidth = -1;                     //CSS default border widths are 2px, so we have to reset widths to 0.
    protected int leftWidth = -1;
    protected int bottomWidth = -1;
    protected int rightWidth = -1;
    protected Color color = new Color("#808080");    //Default border color gray
    protected Style style = Style.SOLID;                    //Default border style
    protected int radius = -1;
    protected int relativeRadius = -1;

    public Border(){
    }

    public Border(int width, Style style, Color color){
        this.topWidth = width;
        this.leftWidth = width;
        this.bottomWidth = width;
        this.rightWidth = width;
        this.style = style;
        this.color = color;
    }

    public Border setWidth(int width){
        this.topWidth = width;
        this.leftWidth = width;
        this.bottomWidth = width;
        this.rightWidth = width;
        return this;
    }

    public Border setWidth(int top, int right, int bottom, int left){
        this.topWidth = top;
        this.leftWidth = left;
        this.bottomWidth = bottom;
        this.rightWidth = right;
        return this;
    }

    public int getBottomWidth() {
        return bottomWidth;
    }

    public Border setBottomWidth(int bottomWidth) {
        this.bottomWidth = bottomWidth;
        return this;
    }

    public int getLeftWidth() {
        return leftWidth;
    }

    public Border setLeftWidth(int leftWidth) {
        this.leftWidth = leftWidth;
        return this;
    }

    public int getRightWidth() {
        return rightWidth;
    }

    public Border setRightWidth(int rightWidth) {
        this.rightWidth = rightWidth;
        return this;
    }

    public int getTopWidth() {
        return topWidth;
    }

    public Border setTopWidth(int topWidth) {
        this.topWidth = topWidth;
        return this;
    }

    public Color getColor(){
        return color;
    }

    public Border setColor(Color color){
        this.color = color;
        return this;
    }

    public Border setStyle(Style style){
        this.style = style;
        return this;
    }

    public Border setRadius(int radius){
        this.radius = radius;
        return this;
    }

    public Border setRelativeRadius(int relativeRadius){
        this.relativeRadius = relativeRadius;
        return this;
    }

    /**
     * @deprecated No need to return internal setting, use this.toString()
     */
    public String getStyleAsString(){
        return style.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        if(topWidth==-1 && rightWidth==-1 && bottomWidth==-1 && leftWidth==-1){
            //No border widths are set, which makes no sense, render a default border
            sb.append("border:1px ");
            sb.append(style+" ");
            sb.append(color+";");
        }else{
            if(topWidth == leftWidth && leftWidth == bottomWidth && bottomWidth == rightWidth){
                //All widths are set and equal, use the simple border definition
                sb.append("border:"+topWidth+"px ");
                sb.append(style+" ");
                sb.append(color+";");
            }else{
                //Widths are not equal, use the more complex border definition.
                //Widths that are not explicitly set should be 0.
                if(topWidth==-1) topWidth = 0;
                if(rightWidth==-1) rightWidth = 0;
                if(bottomWidth==-1) bottomWidth = 0;
                if(leftWidth==-1) leftWidth = 0;
                sb.append("border-width:"+topWidth+"px "+rightWidth+"px "+bottomWidth+"px "+leftWidth+"px;");
                sb.append("border-style:"+style+";");
                sb.append("border-color:"+color+";");
            }
        }
        if(radius!=-1) sb.append("border-radius:"+radius+"px;");
        if(relativeRadius!=-1) sb.append("border-radius:"+relativeRadius+"%;");

        return sb.toString();
    }
    
    




}
