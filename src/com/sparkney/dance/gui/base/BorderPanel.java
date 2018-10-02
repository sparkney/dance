/*
 * BorderPanel.java
 *
 * Created on den 22 feb 2015, 00:31
 *
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;


/**
 *
 * @author Örjan Derelöv
 *
 * Lays out the components in a horizontal row.
 *
 */
public class BorderPanel extends Component{


    public enum Style {DOTTED,DASHED,SOLID,DOUBLE,GROOVE,RIDGE,INSET,OUTSET,};

    private Component component = null;

    private Border border = new Border();

    private Padding padding = null;
    private Margin margin = null;

    public BorderPanel(){
    }

    public BorderPanel(Component component){
        this.component = component;
    }

    public BorderPanel setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }
    
    public BorderPanel setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }
    
    public BorderPanel setPadding(Padding padding){
        this.padding = padding;
        return this;
    }

    public BorderPanel setMargin(int width){
        this.margin = new Margin(width);
        return this;
    }
    
    public BorderPanel setMargin(int top, int right, int bottom, int left){
        this.margin = new Margin(top,right,bottom,left);
        return this;
    }
    
    public BorderPanel setMargin(Margin margin){
        this.margin = margin;
        return this;
    }
    
    public BorderPanel setBorder(Border border){
        this.border = border;
        return this;
    }

    /**
     * @deprecated Replaced by setContent(Component)
     * Add a component to the container. Return the cell in the container
     * that the component is added to.
     */
    public BorderPanel set(Component component){
        this.component = component;
        return this;
    }

    public BorderPanel setContent(Component component){
        this.component = component;
        return this;
    }

    public void render(Context context) throws Exception{

        StringBuilder style = new StringBuilder();
        style.append("width:100%;");
        style.append("height:100%;");

        //### Naive optimize
        if(padding!=null){
            style.append("padding:"+padding.top+"px "+padding.right+"px "+padding.bottom+"px "+padding.left+"px;");
        }

        //### Naive optimize
        if(margin!=null){
            style.append("margin:"+margin.top+"px "+margin.right+"px "+margin.bottom+"px "+margin.left+"px;");
        }

        style.append(border);

        StringBuilder attributes = new StringBuilder();
        if(id!=null) attributes.append(" id='"+id+"'");
        if(style.length()>0) attributes.append(" style='"+style+"'");
        
        PrintWriter out = context.getPrintWriter();
        
        out.append(BR+"<div").append(attributes).append(">");

        component.render(context);

        out.append(BR+"</div>");
    }


}
