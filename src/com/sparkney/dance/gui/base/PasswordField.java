/*
 * PasswordField.java
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
public class PasswordField extends Component{

    private String name = null;
    private String value = null;

    private Object onBlur = null;
    private Object onFocus = null;
    private Object onKeyUp = null;
    private Object onMouseOut = null;
    private Object onMouseOver = null;
    
    private int width = -1;
    private int height = -1;
    private int relativeWidth = -1;

    private Color backgroundColor = null;
    private Font font = null;
    private Padding padding = null;
    private Margin margin = null;

    private  String explicitStyle = null;


    public PasswordField(){
    }

    public PasswordField setName(String name){
        this.name = name;
        return this;
    }

    public PasswordField setValue(String value){
        this.value = value;
        return this;
    }

    public PasswordField setValue(int value){
        this.value = String.valueOf(value);
        return this;
    }
    
    public PasswordField onMouseOver(String url){
        this.onMouseOver = url;
        return this;
    }
    
    public PasswordField onMouseOver(Action action){
        this.onMouseOver = action;
        return this;
    }
    
    public PasswordField onMouseOut(String url){
        this.onMouseOut = url;
        return this;
    }
    
    public PasswordField onMouseOut(Action action){
        this.onMouseOut = action;
        return this;
    }
    
    public PasswordField onFocus(String url){
        this.onFocus = url;
        return this;
    }
    
    public PasswordField onFocus(Action action){
        this.onFocus = action;
        return this;
    }
    
    public PasswordField onBlur(String url){
        this.onBlur = url;
        return this;
    }
    
    public PasswordField onBlur(Action action){
        this.onBlur = action;
        return this;
    }
    
    /**
     * Set desired width.
     */
    public PasswordField setWidth(int width){
        this.width = width;
        return this;
    }

    /**PasswordField
     * Set desired height.
     */
    public PasswordField setHeight(int height){
        this.height = height;
        return this;
    }

    /**
     * Set desired relative width in procentage of the context space.
     */
    public PasswordField setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }

    /**
     * Set background color.
     */
    public PasswordField setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }

    public PasswordField setFont(Font font){
        this.font = font;
        return this;
    }

    /**
     * Set foreground color.
     */
    public PasswordField setFontColor(Color fontColor){
        if(font==null) font = new Font();
        this.font.color = fontColor;
        return this;
    }

    /**
     * Set font font family.
     */
    public PasswordField setFontFamily(String fontFamily){
        if(font==null) font = new Font();
        this.font.family = fontFamily;
        return this;
    }

    /**
     * Set fontSize image.
     */
    public PasswordField setFontSize(int fontSize){
        if(font==null) font = new Font();
        this.font.size = fontSize;
        return this;
    }

    /**
     * Add explicit style. Should only be used when nothing else works.
     */
    public PasswordField addExplicitStyle(String name, String value){
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
        if(id!=null) out.append(" id=\"" + id + "\"");
        out.append(" type=\"password\"");
        if(name!=null) out.append(" name=\"" + name + "\"");
        if(value!=null) out.append(" value=\"" + value + "\"");
        out.append(" style=\"" + style.toString() + "\"");
        
        if(onBlur!=null) out.append(Renderer.onBlur(onBlur, context));
        if(onFocus!=null) out.append(Renderer.onFocus(onFocus, context));
        if(onKeyUp!=null) out.append(Renderer.onKeyUp(onKeyUp, context));
        if(onMouseOut!=null) out.append(Renderer.onMouseOut(onMouseOut, context));
        if(onMouseOver!=null) out.append(Renderer.onMouseOver(onMouseOver, context));
        
        out.append(">");

    }

}
