/*
 * FormPanel.java
 *
 * Created on den 11 juli 2005, 02:14
 *
 * CHANGELOG
 * 2017-04-11 Added set width, height, relativeWidth and relativeHeight.
 */

package com.sparkney.dance.gui.base;

import java.util.Vector;
import com.sparkney.dance.core.Action;
import com.sparkney.dance.core.AjaxAction;
import com.sparkney.dance.core.Component;
import com.sparkney.dance.core.Context;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Örjan Derelöv
 */
public class FormPanel extends Component{
    
    //TODO: Make sure requestMethod can only be GET or POST and
    //that encodingType can only be DEFAULT or MULTIPART;

    public static final int GET = 1;
    public static final int POST = 2;

    public static final int DEFAULT = 3;
    public static final int MULTIPART = 4;

    private Component component = null;
    private ArrayList values = null;
    private String formName = null;
    private Object defaultAction = null;
    private int requestMethod = POST;
    private int encodingType = DEFAULT;

    private int width = -1;
    private int height = -1;
    private int relativeWidth = -1;
    private int relativeHeight = -1;

    public FormPanel(){
    }
    
    public FormPanel setContent(Component component){
        this.component = component;
        return this;
    }

    public FormPanel setRequestMethod(int requestMethod){
        this.requestMethod = requestMethod;
        return this;
    }

    public FormPanel setEncodingType(int encodingType){
        this.encodingType = encodingType;
        return this;
    }

    public FormPanel setName(String name){
        this.formName = name;
        return this;
    }

    public FormPanel setDefaultAction(String action){
        this.defaultAction = action;
        return this;
    }

    public FormPanel setDefaultAction(Action action){
        this.defaultAction = action;
        return this;
    }

    public FormPanel setDefaultAction(Location action){
        this.defaultAction = action;
        return this;
    }

    public FormPanel setWidth(int width){
        this.width = width;
        return this;
    }

    public FormPanel setHeight(int height){
        this.height = height;
        return this;
    }

    /**
     * Set desired relative width in procentage of the context space.
     */
    public FormPanel setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }

    /**
     * Set desired relative height in procentage of the context space.
     */
    public FormPanel setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }

    public FormPanel addParameter(String name, String value){
        if(values==null){
            values = new ArrayList();
        }
        values.add(new FormValue(name,value));
        return this;
    }

    public FormPanel addParameter(String name, int value){
        if(values==null){
            values = new ArrayList();
        }
        values.add(new FormValue(name, String.valueOf(value)));
        return this;
    }

    public FormPanel addParameter(String name, long value){
        if(values==null){
            values = new ArrayList();
        }
        values.add(new FormValue(name, String.valueOf(value)));
        return this;
    }


    public String getName(){
        return formName;
    }


    public void render(Context context) throws Exception{
        String method = null;
        switch(requestMethod){
            case POST: method="post"; break;
            case GET: method="get"; break;
            default: method="post";
        }
        
        if(formName==null){
            //The form must always have a name. If there is no explicit name,
            //set a temporaty unique name.
            formName = "form"+this.hashCode();
        }

        PrintWriter out = context.getPrintWriter();
        
        out.append(BR+"<form");
        if(id!=null) out.append(" id=\"" + id + "\"");
        out.append(" name=\""+formName+"\"");
        out.append(" method=\""+method+"\"");
        if(defaultAction!=null){
            if(defaultAction instanceof String){
                out.append(" action=\""+(String)defaultAction+"\"");
            }else if(defaultAction instanceof AjaxAction){
                AjaxAction ajaxAction = (AjaxAction)defaultAction;
                ajaxAction.setFormName(formName);
                out.append(" action=\"JavaScript:"+ajaxAction+"\"");
            }else if(defaultAction instanceof Action){
                out.append(" action=\""+((Action)defaultAction).getUri()+"\"");
            }else if(defaultAction instanceof Location){
                out.append(" action=\""+((Location)defaultAction)+"\"");
            }
        }
        if(encodingType==MULTIPART) out.append(" enctype=\"multipart/form-data\"");
        out.append(" style=\"");
        out.append("margin:0;");               //Eliminates new line after </form> in IE
        out.append("display:inline-block");    //Must be inline-block for text-align to work
        if(width!=-1) out.append("width:"+width+";");
        if(height!=-1) out.append("height:"+height+";");
        if(relativeWidth!=-1) out.append("width:"+relativeWidth+"%;");
        if(relativeHeight!=-1) out.append("height:"+relativeHeight+"%;");

        out.append("\">");
        if(values!=null){
            for(int i=0; i<values.size(); i++){
                FormValue formValue = (FormValue)values.get(i);
                out.append(BR+"<input type=\"hidden\" name=\""+formValue.name+"\" value=\""+formValue.value+"\">");
            }
        }
        
        //Set the name of the form and and make it visible to all components in the form panel
        context.setAttribute("formpanel",this);

        //In some rare cases, form panel don't have any content to render.
        if(component!=null){
            component.render(context);
        }

        //Remove this form name from the context
        context.removeAttribute("formpanel");
        
        out.append("</form>");
    }
    
}

class FormValue{
    String name;
    String value;
    FormValue(String name, String value){
        this.name = name;
        this.value = value;
    }
}
