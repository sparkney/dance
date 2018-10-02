/*
 * Space.java
 *
 * Created on den 22 augsti 2005, 22:48
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;

/**
 *
 * @author Örjan Derelöv
 *
 * An empty component with zero width and height by default.
 *
 */
public class Space extends Component{

    private int width = 0;  //Default width
    private int height = 0; //Default height
    private int relativeWidth = -1;
    private int relativeHeight = -1;
    
    
    private Color backgroundColor = null;

    /**
     * Set desired width.
     */
    public Space setWidth(int width){
        this.width = width;
        return this;
    }

    /**
     * Set desired height.
     */
    public Space setHeight(int height){
        this.height = height;
        return this;
    }

    /**
     * Set desired relative width in procentage of the context space.
     */
    public Space setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }
    
    /**
     * Set desired relative height in procentage of the context space.
     */
    public Space setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }

    /**
     * Set background color.
     */
    public Space setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }

    public void render(Context context) throws Exception{
        StringBuilder tableStyle = new StringBuilder();
        if(backgroundColor!=null) tableStyle.append("background-color:"+backgroundColor+";");
        if(relativeWidth!=-1) tableStyle.append("width:"+width+"%;"); else tableStyle.append("width:"+width+"px;");
        if(relativeHeight!=-1) tableStyle.append("height:"+height+"%;"); else tableStyle.append("height:"+height+"px;");

        PrintWriter out = context.getPrintWriter();
        out.append("<table cellpadding='0' cellspacing='0'");
        out.append("><tr><td");
        if(id!=null) out.append(" id='" + id + "'");
        out.append(" style='" + tableStyle.toString() + "'></td></tr></table>");

    }

}
