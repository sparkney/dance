/*
 * DropDownField.java
 *
 * Created on den 11 april 2006, 12:53
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.util.ArrayList;
import java.io.PrintWriter;


/**
 *
 * @author Örjan Derelöv
 */
public class DropDownField extends Component{

    private String name = null;
    private ArrayList options = new ArrayList();
    private Object onChange = null;
    private Object onKeyUp = null;
    private Object onMouseOver= null;
    private Object onMouseOut = null;
    private Object onFocus= null;
    private Object onBlur = null;
    
    private boolean multipleChoices = false;
    private String selectedValue = null;

    private int width = -1;
    private int height = -1;
    private int relativeWidth = -1;
    private int relativeHeight = -1;
    private Color backgroundColor = null;
    private Font font = null;
    private Padding padding = null;
    private Margin margin = null;

    private  String explicitStyle = null;

    public DropDownField(){
    }

    public DropDownField setName(String name){
        this.name = name;
        return this;
    }

    public DropDownOption addOption(String name, String value){
        DropDownOption option = new DropDownOption(name,value);
        options.add(option);
        return option;
    }

    public DropDownOption addOption(String name, int value){
        DropDownOption option = new DropDownOption(name,value);
        options.add(option);
        return option;
    }
    
    public DropDownOption addOption(String name, long value){
        DropDownOption option = new DropDownOption(name,value);
        options.add(option);
        return option;
    }

    public DropDownOption addOption(int name, String value){
        DropDownOption option = new DropDownOption(name,value);
        options.add(option);
        return option;
    }

    public DropDownOption addOption(int name, int value){
        DropDownOption option = new DropDownOption(name,value);
        options.add(option);
        return option;
    }

    public DropDownField onChange(String javascript){
        this.onChange = javascript;
        return this;
    }

    public DropDownField onChange(Action action){
        this.onChange = action;
        return this;
    }

    public DropDownField onChange(SubmitForm submitForm){
        this.onChange = submitForm;
        return this;
    }

    public DropDownField setOnKeyUp(String javascript){
        this.onKeyUp= javascript;
        return this;
    }

    public DropDownField setOnKeyUp(Action action){
        this.onKeyUp= action;
        return this;
    }

    public DropDownField setOnKeyUp(SubmitForm submitForm){
        this.onKeyUp = submitForm;
        return this;
    }

    public DropDownField onMouseOver(String javascript){
        this.onMouseOver = javascript;
        return this;
    }

    public DropDownField onMouseOver(Action action){
        this.onMouseOver = action;
        return this;
    }

    public DropDownField onMouseOver(SubmitForm submitForm){
        this.onMouseOver = submitForm;
        return this;
    }

    public DropDownField onMouseOut(String javascript){
        this.onMouseOut = javascript;
        return this;
    }

    public DropDownField onMouseOut(Action action){
        this.onMouseOut = action;
        return this;
    }

    public DropDownField onMouseOut(SubmitForm submitForm){
        this.onMouseOut = submitForm;
        return this;
    }

    public DropDownField onFocus(String javascript){
        this.onFocus = javascript;
        return this;
    }

    public DropDownField onFocus(Action action){
        this.onFocus = action;
        return this;
    }

    public DropDownField onFocus(SubmitForm submitForm){
        this.onFocus = submitForm;
        return this;
    }

    public DropDownField onBlur(String javascript){
        this.onBlur = javascript;
        return this;
    }

    public DropDownField onBlur(Action action){
        this.onBlur = action;
        return this;
    }

    public DropDownField onBlur(SubmitForm submitForm){
        this.onBlur = submitForm;
        return this;
    }

    public DropDownField setMultipleChoices(boolean multipleChoices){
        this.multipleChoices = multipleChoices;
        return this;
    }
    
    /**
     * Set the value in the list that should be selected by default.
     */
    public DropDownField setSelectedValue(String selectedValue){
        this.selectedValue = selectedValue;
        return this;
    }
    
    /**
     * Set the value in the list that should be selected by default.
     */
    public DropDownField setSelectedValue(int selectedValue){
        this.selectedValue = String.valueOf(selectedValue);
        return this;
    }

    /**
     * Set the value in the list that should be selected by default.
     */
    public DropDownField setSelectedValue(long selectedValue){
        this.selectedValue = String.valueOf(selectedValue);
        return this;
    }

    /**
     * Set desired width.
     */
    public DropDownField setWidth(int width){
        this.width = width;
        return this;
    }

    /**
     * Set desired height.
     */
    public DropDownField setHeight(int height){
        this.height = height;
        return this;
    }

    /**
     * Set desired relative width in procentage of the context space.
     */
    public DropDownField setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }

    /**
     * Set desired relative height in procentage of the context space.
     */
    public DropDownField setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }

    /**
     * Set background color.
     */
    public DropDownField setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }

    public DropDownField setFont(Font font){
        this.font = font;
        return this;
    }

    /**
     * @deprecated Use Font.setColor() instead
     */
    public DropDownField setColor(Color color){
        if(font==null) font = new Font();
        this.font.color = color;
        return this;
    }

    /**
     * @deprecated Use Font.setFamily() instead
     */
    public DropDownField setFontFamily(String fontFamily){
        if(font==null) font = new Font();
        this.font.family = fontFamily;
        return this;
    }

    /**
     * @deprecated Use Font.setSize() instead
     */
    public DropDownField setFontSize(int fontSize){
        if(font==null) font = new Font();
        this.font.size = fontSize;
        return this;
    }

    /**
     * Add explicit style. Should only be used when nothing else works.
     */
    public DropDownField addExplicitStyle(String name, String value){
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
        if(relativeWidth!=-1) style.append("width:"+relativeWidth+"%;");
        if(relativeHeight!=-1) style.append("height:"+relativeHeight+"%;");
        if(padding!=null) style.append("padding:"+padding.top+"px "+padding.right+"px "+padding.bottom+"px "+padding.left+"px;");
        if(margin!=null) style.append("margin:"+margin.top+"px "+margin.right+"px "+margin.bottom+"px "+margin.left+"px;");
        if(explicitStyle!=null) style.append(explicitStyle);

        PrintWriter out = context.getPrintWriter();
        
        out.append("<select");
        if(id!=null) out.append(" id=\"" + id + "\"");
        if(name!=null) out.append(" name=\"" + name + "\"");
        if(multipleChoices) out.append(" multiple");
        out.append(" style=\"" + style.toString() + "\"");

        if(onBlur!=null) out.append(Renderer.onBlur(onBlur, context));
        if(onChange!=null) out.append(Renderer.onChange(onChange, context));
        if(onFocus!=null) out.append(Renderer.onFocus(onFocus, context));
        if(onKeyUp!=null) out.append(Renderer.onKeyUp(onKeyUp, context));
        if(onMouseOut!=null) out.append(Renderer.onMouseOut(onMouseOut, context));
        if(onMouseOver!=null) out.append(Renderer.onMouseOver(onMouseOver, context));
        
        out.append(">");

        //Render options
        for(int i=0; i<options.size(); i++){
            DropDownOption option = (DropDownOption)options.get(i);
            out.append("<option");
            out.append(" value=\"" + option.value + "\"");
            if(font!=null){
                out.append(" style=\"" + font + "\"");
            }
            if(selectedValue!=null){
                if(option.value.equals(selectedValue)) out.append(" selected");
            }else{
                if(option.isSelected()) out.append(" selected");
            }
            if(option.isDisabled()) out.append(" disabled");
            out.append(">");
            out.append(option.name);
            out.append("</option>");
        }

        out.append("</select>");

    }

}
