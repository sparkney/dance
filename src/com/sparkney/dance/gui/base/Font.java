/*
 * Font.java
 *
 * Created on den 15 oktober 2006
 *
 */

package com.sparkney.dance.gui.base;

/**
 *
 * @author Örjan Derelöv
 */
public class Font {

    protected String family = null;
    protected int size = -1;
    protected Color color = null;
    protected int bold = -1;
    protected int underlined = -1;
    protected int italic = -1;
    protected int lineHeight = -1;
    protected int relativeLineHeight = -1;
    protected int weight = -1;
    private String explicitStyle = null;

    public Font() {
    }

    public Font(String family, int size) {
        this.family = family;
        this.size = size;
    }

    /**
     * Create a font from a template font. This handy when there is a default
     * font and we just want to override one or two properties from the default.
     * Font headline = new Font(defalutTextFont);
     * headline.setSize(defalutTextFont.getSize()+5);
     *
     */
    public Font(Font templateFont) {
        this.family = templateFont.family;
        this.size = templateFont.size;
        this.color = templateFont.color;
        this.bold = templateFont.bold;
        this.underlined = templateFont.underlined;
        this.italic = templateFont.italic;
        this.lineHeight = templateFont.lineHeight;
        this.relativeLineHeight = templateFont.relativeLineHeight;
        this.weight = templateFont.weight;
    }

    public Font setFamily(String family) {
        this.family = family;
        return this;
    }

    public Font setSize(int size) {
        this.size = size;
        return this;
    }

    public Font setColor(Color color) {
        this.color = color;
        return this;
    }

    public Font setBold(boolean bold) {
        this.bold = bold ? 1 : 0;
        return this;
    }

    public Font setUnderlined(boolean underlined) {
        this.underlined = underlined ? 1 : 0;
        return this;
    }

    public Font setItalic(boolean italic) {
        this.italic = italic ? 1 : 0;
        return this;
    }

    public Font setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public Font setRelativeLineHeight(int relativeLineHeight) {
        this.relativeLineHeight = relativeLineHeight;
        return this;
    }

    public Font setWeight(int weight) {
        this.weight = weight;
        return this;
    }
    
    public Font addExplicitStyle(String name, String value){
        if(this.explicitStyle==null){
            this.explicitStyle = name + ":" + value + ";";
        }else{
            this.explicitStyle += name + ":" + value + ";";
        }
        return this;
    }

    @Override
    public String toString(){
        StringBuilder style = new StringBuilder();
        if(family!=null) style.append("font-family:").append(family).append(";");
        if(size!=-1) style.append("font-size:").append(size).append("px;");
        if(color!=null) style.append("color:").append(color).append(";");
        if(bold!=-1){
            if(bold==1) style.append("font-weight:bold;"); else style.append("font-weight:normal;");
        }
        if(underlined!=-1){
            if(underlined==1) style.append("text-decoration:underline;"); else style.append("text-decoration:none;");
        }
        if(italic!=-1){
            if(italic==1) style.append("font-style:italic;"); else style.append("font-style:normal;");
        }
        if(lineHeight!=-1) style.append("line-height:").append(lineHeight).append("px;");
        if(relativeLineHeight!=-1) style.append("line-height:").append(relativeLineHeight).append("%;");
        if(weight!=-1) style.append("font-weight:").append(weight);
        if(explicitStyle!=null) style.append(explicitStyle);

        return style.toString();
    }


}
