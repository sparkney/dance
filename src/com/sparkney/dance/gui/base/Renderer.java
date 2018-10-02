/*
 * HtmlRenderer.java
 *
 * Created on October 16, 2008, 12:47 PM
 *
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;

/**
 *
 * @author orjan
 *
 * Change log:
 * 140721 Strings are now interpreted as JavaScript code. URLs should always be wrapped with Location.
 */
public final class Renderer {
    
    
    public static String onBlur(Object onBlur, Context context) throws Exception{
        return onXxxx(onBlur,context,"onBlur");
    }

    public static String onChange(Object onChange, Context context) throws Exception{
        return onXxxx(onChange,context,"onChange");
    }
    
    public static String onClick(Object onClick, Context context) throws Exception{
        return onXxxx(onClick,context,"onClick");
    }

    public static String onFocus(Object onFocus, Context context) throws Exception{
        return onXxxx(onFocus,context,"onFocus");
    }

    public static String onKeyUp(Object onKeyUp, Context context) throws Exception{
        return onXxxx(onKeyUp,context,"onKeyUp");
    }
    
    public static String onLoad(Object onLoad, Context context) throws Exception{
        return onXxxx(onLoad,context,"onLoad");
    }

    public static String onMouseOut(Object onMouseOut, Context context) throws Exception{
        return onXxxx(onMouseOut,context,"onMouseOut");
    }

    public static String onMouseOver(Object onMouseOver, Context context) throws Exception{
        return onXxxx(onMouseOver,context,"onMouseOver");
    }

    private static String onXxxx(Object onXxxx, Context context, String eventName) throws Exception{
        if(onXxxx instanceof AjaxAction){
            return " "+eventName+"=\""+(AjaxAction)onXxxx+"\"";
        }else if(onXxxx instanceof Action) {
            return " "+eventName+"=\"JavaScript:document.location='"+(Action)onXxxx+"'\"";
        }else if(onXxxx instanceof String){
            return " "+eventName+"=\"JavaScript:"+onXxxx+"\"";
        }else if(onXxxx instanceof Location){
            return " "+eventName+"=\"JavaScript:document.location='"+(Location)onXxxx+"'\"";
        }else if(onXxxx instanceof SubmitForm){
            SubmitForm submitForm = (SubmitForm)onXxxx;

            String formName;
            try{
                formName = ((FormPanel)context.getAttribute("formpanel")).getName();
            }catch(Exception e){
                throw new Exception("Trying to submit a form without a FormPanel.");
            }

            if(submitForm.action instanceof AjaxAction){
                AjaxAction ajaxAction = (AjaxAction)submitForm.action;
                ajaxAction.setFormName(formName);
                return " "+eventName+"=\""+ajaxAction+"\"";
            }else if(submitForm.action instanceof Action){
                return " "+eventName+"=\"JavaScript:document."+formName+".action='"+(Action)submitForm.action+"';document."+formName+".submit()\"";
            }else if(submitForm.action instanceof String){
                return " "+eventName+"=\"JavaScript:document."+formName+".action='"+(String)submitForm.action+"';document."+formName+".submit()\"";
            }else{
                throw new Exception("The instance of " + submitForm.action.getClass() + " in a form submission is not supported");
            }
        }else{
            throw new Exception("The instance of " + onXxxx.getClass() + " is not supported");
        }
    }
    
    
    
    
    
    
    
}
