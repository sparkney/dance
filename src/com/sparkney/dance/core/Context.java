/*
 * Context.java
 *
 * Created on den 19 juni 2007
 *
 */

package com.sparkney.dance.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * @author Örjan Derelöv
 */
public class Context {
    
    protected PrintWriter printWriter = null;
    protected HttpServletRequest request = null;
    protected HttpServletResponse response = null;
    protected ArrayList<String> onLoad = null;
    protected ArrayList<String> onResize = null;
    protected ArrayList<String> javaScriptUrls = null;
    HashMap<String, Object> attribute = null;
    protected Object action = null;

    
    /**
     *
     */
    public Context(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.printWriter = response.getWriter();
        this.setRequest(request);
        this.setResponse(response);
    }

    public Context(PrintWriter printWriter){
        this.printWriter = printWriter;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void setAttribute(String name, Object value){
        if(attribute==null){
            attribute=new HashMap<String, Object>();
        }
        attribute.put(name, value);
    }

    public Object getAttribute(String name){
        return attribute.get(name);
    }

    public void removeAttribute(String name){
        attribute.remove(name);
    }

    public PrintWriter getPrintWriter() throws Exception{
        return printWriter;
    }
    
    public PrintWriter append(CharSequence csq) throws Exception{
        return printWriter.append(csq);
    }

    public PrintWriter append(char c) throws Exception{
        return printWriter.append(c);
    }
    
    /**
     * Add a JavaScript function that should be run just after the page has been
     * loaded. Can be used by any component anywhere to run an init script.
     */
    public void addOnLoad(String javaScript) throws Exception{
        if(this.onLoad==null){
            this.onLoad = new ArrayList();
        }
        this.onLoad.add(javaScript);
    }

    public ArrayList<String> getOnLoad(){
        return this.onLoad;
    }
    
    /**
     * Add a JavaScript function that should be run on page resize. Can be used
     * by any component anywhere.
     */
    public void addOnResize(String javaScript) throws Exception{
        if(this.onResize==null){
            this.onResize = new ArrayList();
        }
        this.onResize.add(javaScript);
    }

    public ArrayList<String> getOnResize(){
        return this.onResize;
    }

    /**
     * Add a JavaScript URL that will include a java script file at the end of the page.
     * Can be used by any component anywhere to run an init script.
     */
    public void addJavaScriptUrl(String javaScriptUrl){
        if(this.javaScriptUrls==null){
            this.javaScriptUrls = new ArrayList();
        }
        
        if(!this.javaScriptUrls.contains(javaScriptUrl)){
            this.javaScriptUrls.add(javaScriptUrl);
        }
    }

    public ArrayList<String> getJavaScriptUrls(){
        return this.javaScriptUrls;
    }

    /**
     * Close the context. Return the resources used in the context,
     * especially the PrintWriter
     */
    public void close() throws java.io.IOException{
        printWriter.close();
    }

    public Object getAction(){
        return action;
    }

    public void setAction(Object action){
        this.action = action;
    }
    
    
}
