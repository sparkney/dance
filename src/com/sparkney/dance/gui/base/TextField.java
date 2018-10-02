/*
 * TextField.java
 *
 * Created on den 13 juli 2005, 02:44
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;


/**
 *
 * @author Örjan Derelöv
 */
public class TextField extends Component{

    private String name = null;
    private String value = null;
    private String tooltip = null;
    
    private Object onBlur = null;
    private Object onChange = null;
    private Object onFocus = null;
    private Object onKeyUp = null;
    private Object onMouseOut = null;
    private Object onMouseOver = null;
    
    private int width = -1;
    private int height = -1;
    private int relativeWidth = -1;

    private Color backgroundColor = null;
    private  Font font = null;
    private Padding padding = null;
    private Margin margin = null;

    private  String explicitStyle = null;

    public TextField(){
    }

    public TextField setName(String name){
        this.name = name;
        return this;
    }

    public TextField setValue(String value){
        this.value = value;
        return this;
    }

    public TextField setValue(int value){
        this.value = String.valueOf(value);
        return this;
    }

    public TextField setTooltip(String tooltip){
        this.tooltip = tooltip;
        return this;
    }

    public TextField onMouseOver(String javascript){
        this.onMouseOver = javascript;
        return this;
    }
    
    public TextField onMouseOver(Action action){
        this.onMouseOver = action;
        return this;
    }
    
    public TextField onMouseOut(String javascript){
        this.onMouseOut = javascript;
        return this;
    }
    
    public TextField onMouseOut(Action action){
        this.onMouseOut = action;
        return this;
    }
    
    public TextField onFocus(String javascript){
        this.onFocus = javascript;
        return this;
    }
    
    public TextField onFocus(Action action){
        this.onFocus = action;
        return this;
    }
    
    public TextField onBlur(String javascript){
        this.onBlur = javascript;
        return this;
    }
    
    public TextField onBlur(Action action){
        this.onBlur = action;
        return this;
    }
    
    public TextField onChange(String javascript){
        this.onChange = javascript;
        return this;
    }

    public TextField onChange(Action action){
        this.onChange = action;
        return this;
    }

    /**
     * Set desired width.
     */
    public TextField setWidth(int width){
        this.width = width;
        return this;
    }

    /**
     * Set desired height.
     */
    public TextField setHeight(int height){
        this.height = height;
        return this;
    }

    /**
     * Set desired relative width in percentage of the context space.
     */
    public TextField setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }

    /**
     * Set background color.
     */
    public TextField setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }

    public TextField setFont(Font font){
        this.font = font;
        return this;
    }
    
    /**
     * Add explicit style. Should only be used when nothing else works.
     */
    public TextField addExplicitStyle(String name, String value){
        if(this.explicitStyle==null){
            this.explicitStyle = name + ":" + value + ";";
        }else{
            this.explicitStyle += name + ":" + value + ";";
        }
        return this;
    }

    public void render(Context context) throws Exception{
        StringBuilder style = new StringBuilder();
        if(font!=null) style.append(font);
        if(backgroundColor!=null) style.append("background-color:"+backgroundColor+";");
        if(width!=-1) style.append("width:"+width+"px;");
        if(height!=-1) style.append("height:"+height+"px;");
        if(padding!=null) style.append("padding:"+padding.top+"px "+padding.right+"px "+padding.bottom+"px "+padding.left+"px;");
        if(margin!=null) style.append("margin:"+margin.top+"px "+margin.right+"px "+margin.bottom+"px "+margin.left+"px;");
        if(relativeWidth!=-1) style.append("width:"+relativeWidth+"%;");
        if(explicitStyle!=null) style.append(explicitStyle);
        
        PrintWriter out = context.getPrintWriter();
        
        out.append(BR+"<input");
        out.append(" type=\"text\"");
        if(id!=null) out.append(" id=\"" + id + "\"");
        if(name!=null) out.append(" name=\"" + name + "\"");
        if(value!=null) out.append(" value=\"" + value + "\"");
        if(tooltip!=null) out.append(" title=\"" + tooltip + "\"");

        out.append(" style=\"" + style.toString() + "\"");
        
        if(onBlur!=null) out.append(Renderer.onBlur(onBlur, context));
        if(onChange!=null) out.append(Renderer.onChange(onChange, context));
        if(onFocus!=null) out.append(Renderer.onFocus(onFocus, context));
        if(onKeyUp!=null) out.append(Renderer.onKeyUp(onKeyUp, context));
        if(onMouseOut!=null) out.append(Renderer.onMouseOut(onMouseOut, context));
        if(onMouseOver!=null) out.append(Renderer.onMouseOver(onMouseOver, context));
        
        out.append(">");
        
    }

}
