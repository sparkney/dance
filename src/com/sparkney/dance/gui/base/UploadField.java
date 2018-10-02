/*
 * UploadField.java
 *
 * Created on den 16 augusti 2006, 14:33
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;


/**
 *
 * @author Örjan Derelöv
 */
public class UploadField extends Component{

    private String name = null;
    private String value = null;
    
    private Object onBlur = null;
    private Object onChange = null;
    private Object onFocus = null;
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

    public UploadField(){
    }

    public UploadField setName(String name){
        this.name = name;
        return this;
    }

    /**
     * Set the default value.
     * Warning: Currently, this property won't work with most browsers. It should be seen as
     * future feature.
     */
    public UploadField setValue(String value){
        this.value = value;
        return this;
    }
    
    public UploadField onMouseOver(String url){
        this.onMouseOver = url;
        return this;
    }
    
    public UploadField onMouseOver(Action action){
        this.onMouseOver = action;
        return this;
    }
    
    public UploadField onMouseOut(String url){
        this.onMouseOut = url;
        return this;
    }
    
    public UploadField onMouseOut(Action action){
        this.onMouseOut = action;
        return this;
    }
    
    public UploadField onFocus(String url){
        this.onFocus = url;
        return this;
    }
    
    public UploadField onFocus(Action action){
        this.onFocus = action;
        return this;
    }
    
    public UploadField onBlur(String url){
        this.onBlur = url;
        return this;
    }
    
    public UploadField onBlur(Action action){
        this.onBlur = action;
        return this;
    }
    
    public UploadField onChange(Action action){
        this.onChange = action;
        return this;
    }

    public UploadField onChange(SubmitForm submitForm){
        this.onChange = submitForm;
        return this;
    }

    /**
     * Set desired width.
     * Warning: This property may not work in many browsers. Currently, Mozilla ignores
     * it and setting the width will cause Explorer to render the field as if the width
     * is 0.
     */
    public UploadField setWidth(int width){
        this.width = width;
        return this;
    }

    /**
     * Set desired height.
     */
    public UploadField setHeight(int height){
        this.height = height;
        return this;
    }

    /**
     * Set desired relative width in procentage of the context space.
     */
    public UploadField setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }

    /**
     * Set desired relative height in procentage of the context space.
     */
    public UploadField setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }

    /**
     * Set background color.
     */
    public UploadField setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }

    public UploadField setFont(Font font){
        this.font = font;
        return this;
    }

    /**
     * Set font font family.
     */
    public UploadField setFontFamily(String fontFamily){
        if(font==null) font = new Font();
        this.font.family = fontFamily;
        return this;
    }

    /**
     * Set fontSize image.
     */
    public UploadField setFontSize(int fontSize){
        if(font==null) font = new Font();
        this.font.size = fontSize;
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

        PrintWriter out = context.getPrintWriter();

        out.append("<input");
        if(id!=null) out.append(" id=\"" + id + "\"");
        out.append(" type=\"file\"");
        if(name!=null) out.append(" name=\"" + name + "\"");
        if(value!=null) out.append(" value=\"" + value + "\"");
        out.append(" style=\"" + style.toString() + "\"");
        
        if(onBlur!=null) out.append(Renderer.onBlur(onBlur, context));
        if(onChange!=null) out.append(Renderer.onChange(onChange, context));
        if(onFocus!=null) out.append(Renderer.onFocus(onFocus, context));
        if(onMouseOut!=null) out.append(Renderer.onMouseOut(onMouseOut, context));
        if(onMouseOver!=null) out.append(Renderer.onMouseOver(onMouseOver, context));
        
        out.append(">");

    }

}
