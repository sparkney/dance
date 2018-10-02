/*
 * Button.java
 *
 * Created on den 3 okt 2006, 10:34
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;


/**
 * TODO: Change to HTML5 <button>
 *
 * @author Örjan Derelöv
 *
 */
public class Button extends Component{
    
    private String text = null;
    private String name = null;
    private String tooltip = null;
    
    private Object onClick = null;
    private Object onMouseOver = null;
    private Object onMouseOut = null;
    
    private  Color backgroundColor = null;
    private  Font font = null;
    private  Padding padding = null;
    private  Margin margin = null;
    private  String explicitStyle = null;
    
    
    public Button(){
    }
    
    public Button setText(String text){
        this.text = text;
        return this;
    }
    
    public Button setName(String name){
        this.name = name;
        return this;
    }

    public Button setTooltip(String tooltip){
        this.tooltip = tooltip;
        return this;
    }

    public Button onClick(Action action){
        this.onClick = action;
        return this;
    }
    
    public Button onClick(AjaxAction ajaxAction){
        this.onClick = ajaxAction;
        return this;
    }

    public Button onClick(String url){
        this.onClick = url;
        return this;
    }
    
    public Button onClick(SubmitForm submitForm){
        this.onClick = submitForm;
        return this;
    }
    
    public Button onMouseOver(Action action){
        this.onMouseOver = action;
        return this;
    }
    
    public Button onMouseOver(String url){
        this.onMouseOver = url;
        return this;
    }
    
    public Button onMouseOver(SubmitForm submitForm){
        this.onMouseOver = submitForm;
        return this;
    }
    
    public Button onMouseOut(Action action){
        this.onMouseOut = action;
        return this;
    }
    
    public Button onMouseOut(String url){
        this.onMouseOut = url;
        return this;
    }
    
    public Button onMouseOut(SubmitForm submitForm){
        this.onMouseOut = submitForm;
        return this;
    }
    
    /**
     * Set background color.
     */
    public Button setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    public Button setFont(Font font){
        this.font = font;
        return this;
    }
    
    public Button setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }
    
    public Button setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }
    
    public Button setPadding(Padding padding){
        this.padding = padding;
        return this;
    }
    
    public Button setMargin(int width){
        this.margin = new Margin(width);
        return this;
    }
    
    public Button setMargin(int top, int right, int bottom, int left){
        this.margin = new Margin(top,right,bottom,left);
        return this;
    }
    
    public Button setMargin(Margin margin){
        this.margin = margin;
        return this;
    }
    
    
    /**
     * Add explicit style. Should only be used when nothing else works.
     */
    public Button addExplicitStyle(String name, String value){
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
        if(padding!=null) style.append("padding:"+padding.top+"px "+padding.right+"px "+padding.bottom+"px "+padding.left+"px;");
        if(margin!=null) style.append("margin:"+margin.top+"px "+margin.right+"px "+margin.bottom+"px "+margin.left+"px;");
        if(explicitStyle!=null) style.append(explicitStyle);
        
        PrintWriter out = context.getPrintWriter();
        
        out.append("<input type=\"button\"");
        if(id!=null) out.append(" id=\"" + id + "\"");
        if(name!=null) out.append(" name=\"" + name + "\"");
        if(text!=null) out.append(" value=\"" + text + "\"");
        if(tooltip!=null) out.append(" title=\"" + tooltip + "\"");
        out.append(" style=\"" + style.toString() + "\"");

        if(onClick!=null) out.append(Renderer.onClick(onClick, context));
        if(onMouseOut!=null) out.append(Renderer.onMouseOut(onMouseOut, context));
        if(onMouseOver!=null) out.append(Renderer.onMouseOver(onMouseOver, context));

        out.append(">");
        
    }
    
}
