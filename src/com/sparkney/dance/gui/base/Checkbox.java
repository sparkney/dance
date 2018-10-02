/*
 * Checkbox.java
 *
 * Created on den 23 august 2006, 14:29
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;

/**
 *
 * @author Örjan Derelöv
 */
public class Checkbox extends Component{

    private StringBuilder style = new StringBuilder();

    private String name = null;
    private String value = null;
    private boolean checked = false;
    private String tooltip = null;

    private Object onChange = null;
    private Object onClick = null;
    private Object onMouseOver = null;
    private Object onMouseOut = null;

    private int width = -1;
    private int height = -1;
    private Margin margin = null;

    public Checkbox(){
    }

    public Checkbox setName(String name){
        this.name = name;
        return this;
    }

    public Checkbox setValue(String value){
        this.value = value;
        return this;
    }

    public Checkbox setValue(int value){
        this.value = String.valueOf(value);
        return this;
    }

    /**
     * @deprecated Replaced by setTooltip(..)
     */
    public Checkbox setTitle(String title){
        this.tooltip = title;
        return this;
    }

    public Checkbox setTooltip(String tooltip){
        this.tooltip = tooltip;
        return this;
    }

    public Checkbox setChecked(boolean value){
        this.checked = value;
        return this;
    }

    public Checkbox onChange(Action action){
        this.onChange = action;
        return this;
    }

    public Checkbox onChange(String javascript){
        this.onChange = javascript;
        return this;
    }

    public Checkbox onChange(SubmitForm submitForm){
        this.onChange = submitForm;
        return this;
    }

    public Checkbox onClick(Action action){
        this.onClick = action;
        return this;
    }

    public Checkbox onClick(AjaxAction ajaxAction){
        this.onClick = ajaxAction;
        return this;
    }

    public Checkbox onClick(String javascript){
        this.onClick = javascript;
        return this;
    }

    public Checkbox onClick(SubmitForm submitForm){
        this.onClick = submitForm;
        return this;
    }

    public Checkbox onMouseOver(Action action){
        this.onMouseOver = action;
        return this;
    }

    public Checkbox onMouseOver(String javascript){
        this.onMouseOver = javascript;
        return this;
    }

    public Checkbox onMouseOver(SubmitForm submitForm){
        this.onMouseOver = submitForm;
        return this;
    }

    public Checkbox onMouseOut(Action action){
        this.onMouseOut = action;
        return this;
    }

    public Checkbox onMouseOut(String javascript){
        this.onMouseOut = javascript;
        return this;
    }

    public Checkbox onMouseOut(SubmitForm submitForm){
        this.onMouseOut = submitForm;
        return this;
    }


    /**
     * Set desired size in pixels. The size of the checkbox is somewhat smaller in
     * Explorer then in Mozilla, but Explorer always adds padding to the checkbox
     * so that is takes up the same area in as in Mozilla. (setForegroundColor can
     * be used to change the color of the padding in Explorer. This property has
     * no effect in Mozilla.)
     */
    public Checkbox setSize(int size){
        this.width = size;
        this.height = size;
        return this;
    }

    /**
     * Adds style to the components on the form "name:value". Since this method
     * is implementation specific it should never be used by clients. It should
     * only be used by layout panels if necessary.
     *
     */
    public Checkbox addStyle(String style){
        this.style.append(style + ';');
        return this;
    }

    public void render(Context context) throws Exception{
        if(width!=-1) style.append("width:"+width+"px;");
        if(height!=-1) style.append("height:"+height+"px;");
        if(margin!=null) style.append("margin:"+margin.top+"px "+margin.right+"px "+margin.bottom+"px "+margin.left+"px;");

        PrintWriter out = context.getPrintWriter();
        
        out.append("<input");
        if(id!=null) out.append(" id=\"" + id + "\"");
        out.append(" type=\"checkbox\"");
        if(name!=null) out.append(" name=\"" + name + "\"");
        if(value!=null) out.append(" value=\"" + value + "\"");
        if(checked) out.append(" checked");

        if(onChange!=null) out.append(Renderer.onChange(onChange, context));
        if(onClick!=null) out.append(Renderer.onClick(onClick, context));
        if(onMouseOut!=null) out.append(Renderer.onMouseOut(onMouseOut, context));
        if(onMouseOver!=null) out.append(Renderer.onMouseOver(onMouseOver, context));

        out.append(" style=\"" + style.toString() + "\"");

        if(tooltip!=null){
            out.append(" title=\""+tooltip+"\"");
        }

        out.append(">");

    }

}
