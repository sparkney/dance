/*
 * TextField.java
 *
 * Created on den 15 agusti 2006, 14:43
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;


/**
 *
 * @author Örjan Derelöv
 */
public class TextArea extends Component{

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
    private int relativeHeight = -1;
    private Color backgroundColor = null;
    private Font font = null;
    private Padding padding = null;
    private Margin margin = null;

    private  String explicitStyle = null;

    public TextArea(){
    }

    public TextArea setName(String name){
        this.name = name;
        return this;
    }

    public TextArea setValue(String value){
        this.value = value;
        return this;
    }
    
    public TextArea setOnKeyUp(String url){
        this.onKeyUp= url;
        return this;
    }

    public TextArea setOnKeyUp(Action action){
        this.onKeyUp= action;
        return this;
    }

    public TextArea setOnKeyUp(SubmitForm submitForm){
        this.onKeyUp = submitForm;
        return this;
    }

    public TextArea onMouseOver(String url){
        this.onMouseOver = url;
        return this;
    }

    public TextArea onMouseOver(Action action){
        this.onMouseOver = action;
        return this;
    }

    public TextArea onMouseOver(SubmitForm submitForm){
        this.onMouseOver = submitForm;
        return this;
    }

    public TextArea onMouseOut(String url){
        this.onMouseOut = url;
        return this;
    }

    public TextArea onMouseOut(Action action){
        this.onMouseOut = action;
        return this;
    }

    public TextArea onMouseOut(SubmitForm submitForm){
        this.onMouseOut = submitForm;
        return this;
    }

    public TextArea onFocus(String url){
        this.onFocus = url;
        return this;
    }

    public TextArea onFocus(Action action){
        this.onFocus = action;
        return this;
    }

    public TextArea onFocus(SubmitForm submitForm){
        this.onFocus = submitForm;
        return this;
    }

    public TextArea onBlur(String url){
        this.onBlur = url;
        return this;
    }

    public TextArea onBlur(Action action){
        this.onBlur = action;
        return this;
    }

    public TextArea onBlur(SubmitForm submitForm){
        this.onBlur = submitForm;
        return this;
    }
    
    /**
     * Set desired width.
     */
    public TextArea setWidth(int width){
        this.width = width;
        return this;
    }

    /**
     * Set desired height.
     */
    public TextArea setHeight(int height){
        this.height = height;
        return this;
    }

    /**
     * Set desired relative width in procentage of the context space.
     */
    public TextArea setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }

    /**
     * Set desired relative height in procentage of the context space.
     */
    public TextArea setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }

    /**
     * Set background color.
     */
    public TextArea setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }

    public TextArea setFont(Font font){
        this.font = font;
        return this;
    }

    /**
     * Set font font family.
     */
    public TextArea setFontFamily(String fontFamily){
        if(font==null) font = new Font();
        this.font.family = fontFamily;
        return this;
    }

    /**
     * Set fontSize image.
     */
    public TextArea setFontSize(int fontSize){
        if(font==null) font = new Font();
        this.font.size = fontSize;
        return this;
    }

    /**
     * Add explicit style. Should only be used when nothing else works.
     */
    public TextArea addExplicitStyle(String name, String value){
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
        if(relativeHeight!=-1) style.append("height:"+relativeHeight+"%;");
        if(explicitStyle!=null) style.append(explicitStyle);

        PrintWriter out = context.getPrintWriter();
        
        out.append(BR+"<textarea");
        if(id!=null) out.append(" id=\"" + id + "\"");
        if(name!=null) out.append(" name=\"" + name + "\"");
        out.append(" style=\"" + style.toString() + "\"");
        
        if(onBlur!=null) out.append(Renderer.onBlur(onBlur, context));
        if(onFocus!=null) out.append(Renderer.onFocus(onFocus, context));
        if(onKeyUp!=null) out.append(Renderer.onKeyUp(onKeyUp, context));
        if(onMouseOut!=null) out.append(Renderer.onMouseOut(onMouseOut, context));
        if(onMouseOver!=null) out.append(Renderer.onMouseOver(onMouseOver, context));
        
        out.append(">");
        
        if(value!=null) out.append(value);
        
        out.append("</textarea>");
    }

}
