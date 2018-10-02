/*
 * Html.java
 *
 * Created on den 16 jan 2011, 21:30
 *
 *
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;

/**
 *
 * @author Örjan Derelöv
 *
 */
public class Html extends Component{

    private String code = "";

    public Html(){
    }

    public Html(String code){
        this.code = code;
    }

    public Html setCode(String code){
        this.code = code;
        return this;
    }

    public void render(Context context) throws Exception{
        PrintWriter out = context.getPrintWriter();
        out.append(code);
    }

}
