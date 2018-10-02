/*
 * Component.java
 *
 * Created on den 7 februari 2006, 23:08
 */

package com.sparkney.dance.core;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

/**
 * @author Örjan Derelöv
 */
public abstract class Component{

    //protected static String BR = "\n";
    protected static String BR = "";

    protected String id = null;

    public Component setId(String id){
        this.id = id;
        return this;
    }



    /**
     * Render a component by writing to the context's PrintWriter:
     * 
     * PrintWriter out = context.getPrintWriter();
     * out.append("Hello world!");
     * @param context 
     * @throws java.lang.Exception 
     */
    public abstract void render(Context context) throws Exception;

    /**
     * Returns the rendered component as a string. Use this method instead of
     * toString() when you need to handle exceptions thrown in the rendering
     * process.
     */
    public String renderAsString() throws Exception{
        String componentString;
        CharArrayWriter writer = null;
        try{
            writer = new CharArrayWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            Context componentContext = new Context(printWriter);
            this.render(componentContext);
            componentString = writer.toString();
            writer.close();
        }finally{
            if(writer!=null){
                writer.close();
            }
        }
        return componentString;
    }

    @Override
    public String toString() {
        try{
            return renderAsString();
        }catch(Exception e){
            return null;
        }
    }
    
    

}
