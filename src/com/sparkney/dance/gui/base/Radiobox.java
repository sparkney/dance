/*
 * Radiobox.java
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
public class Radiobox extends Component{

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
    private int relativeWidth = -1;
    private int relativeHeight = -1;
    private Font font = null;
    private Margin margin = null;

    public Radiobox(){
    }

    public Radiobox setName(String name){
        this.name = name;
        return this;
    }

    public Radiobox setValue(String value){
        this.value = value;
        return this;
    }

    public Radiobox setValue(int value){
        this.value = String.valueOf(value);
        return this;
    }

    public Radiobox setChecked(boolean value){
        this.checked = value;
        return this;
    }

    public Radiobox setTooltip(String tooltip){
        this.tooltip = tooltip;
        return this;
    }
    
    public Radiobox onChange(SubmitForm submitForm){
        this.onChange = submitForm;
        return this;
    }

    public Radiobox onClick(Action action){
        this.onClick = action;
        return this;
    }

    public Radiobox onClick(AjaxAction ajaxAction){
        this.onClick = ajaxAction;
        return this;
    }

    public Radiobox onClick(String url){
        this.onClick = url;
        return this;
    }

    public Radiobox onClick(SubmitForm submitForm){
        this.onClick = submitForm;
        return this;
    }

    public Radiobox onMouseOver(Action action){
        this.onMouseOver = action;
        return this;
    }

    public Radiobox onMouseOver(String url){
        this.onMouseOver = url;
        return this;
    }

    public Radiobox onMouseOver(SubmitForm submitForm){
        this.onMouseOver = submitForm;
        return this;
    }

    public Radiobox onMouseOut(Action action){
        this.onMouseOut = action;
        return this;
    }

    public Radiobox onMouseOut(String url){
        this.onMouseOut = url;
        return this;
    }

    public Radiobox onMouseOut(SubmitForm submitForm){
        this.onMouseOut = submitForm;
        return this;
    }


    /**
     * Set desired size in pixels. The size of the radiobox is somewhat smaller in
     * Explorer then in Mozilla, but Explorer always adds padding to the radiobox
     * so that is takes up the same area in as in Mozilla. (setForegroundColor can
     * be used to change the color of the padding in Explorer. This property has
     * no effect in Mozilla.)
     */
    public Radiobox setSize(int size){
        this.width = size;
        this.height = size;
        return this;
    }

    /**
     * Set desired relative width in procentage of the context space.
     */
    public Radiobox setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }

    /**
     * Set desired relative height in procentage of the context space.
     */
    public Radiobox setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }

    /**
     * Set foreground color.
     * @deprecated
     */
    public void setColor(Color color){
        if(font==null) font = new Font();
        this.font.color = color;
    }

    public void render(Context context) throws Exception{
        StringBuilder style = new StringBuilder();
        if(font!=null) style.append(font);
        if(width!=-1) style.append("width:"+width+"px;");
        if(height!=-1) style.append("height:"+height+"px;");
        if(relativeWidth!=-1) style.append("width:"+relativeWidth+"%;");
        if(relativeHeight!=-1) style.append("height:"+relativeHeight+"%;");
        if(margin!=null) style.append("margin:"+margin.top+"px "+margin.right+"px "+margin.bottom+"px "+margin.left+"px;");

        PrintWriter out = context.getPrintWriter();
        
        out.append("<input");
        if(id!=null) out.append(" id=\"" + id + "\"");
        out.append(" type=\"radio\"");
        if(name!=null) out.append(" name=\"" + name + "\"");
        if(value!=null) out.append(" value=\"" + value + "\"");
        if(checked) out.append(" checked");

        if(onChange!=null) out.append(Renderer.onChange(onChange, context));
        if(onClick!=null) out.append(Renderer.onClick(onClick, context));
        if(onMouseOut!=null) out.append(Renderer.onMouseOut(onMouseOut, context));
        if(onMouseOver!=null) out.append(Renderer.onMouseOver(onMouseOver, context));

//        if(onChange!=null){
//            if(onChange instanceof Action){
//                out.append(" onChange=\"JavaScript:document.location='"+(Action)onChange+"'\"");
//            }else if(onChange instanceof String){
//                out.append(" onChange=\"JavaScript:document.location='"+(String)onChange+"'\"");
//            }else if(onChange instanceof JavaScript){
//                out.append(" onChange=\"JavaScript:"+(JavaScript)onChange+"\"");
//            }else if(onChange instanceof SubmitForm){
//                SubmitForm submitForm = (SubmitForm)onChange;
//                String formName;
//                try{
//                    formName = context.formPanel.getName();
//                }catch(Exception e){
//                    throw new Exception("Trying to submit a form without a FormPanel.");
//                }
//                if(submitForm.action instanceof Action){
//                    out.append(" onChange=\"JavaScript:document."+formName+".action='"+(Action)submitForm.action+"';document."+formName+".submit()\"");
//                }else if(submitForm.action instanceof String){
//                    out.append(" onChange=\"JavaScript:document."+formName+".action='"+(String)submitForm.action+"';document."+formName+".submit()\"");
//                }
//            }
//        }
        
//        if(onClick!=null){
//            if(onClick instanceof Action){
//                out.append(" onClick=\"JavaScript:document.location='"+(Action)onClick+"'\"");
//            }else if(onClick instanceof String){
//                out.append(" onClick=\"JavaScript:document.location='"+(String)onClick+"'\"");
//            }else if(onClick instanceof JavaScript){
//                out.append(" onClick=\"JavaScript:"+(JavaScript)onClick+"\"");
//            }else if(onClick instanceof SubmitForm){
//                SubmitForm submitForm = (SubmitForm)onClick;
//                String formName;
//                try{
//                    formName = context.formPanel.getName();
//                }catch(Exception e){
//                    throw new Exception("Trying to submit a form without a FormPanel.");
//                }
//                if(submitForm.action instanceof Action){
//                    out.append(" onClick=\"JavaScript:document."+formName+".action='"+(Action)submitForm.action+"';document."+formName+".submit()\"");
//                }else if(submitForm.action instanceof String){
//                    out.append(" onClick=\"JavaScript:document."+formName+".action='"+(String)submitForm.action+"';document."+formName+".submit()\"");
//                }
//            }
//        }
        
        out.append(" style=\"" + style.toString() + "\"");

        if(tooltip!=null){
            out.append(" title=\""+tooltip+"\"");
        }

        out.append(">");

    }

}
