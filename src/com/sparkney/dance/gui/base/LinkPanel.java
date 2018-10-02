/*
 * Link.java
 *
 * Created on den 11 juli 2005, 02:14
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;

/**
 *
 * @author Örjan Derelöv
 *
 * Change log:
 * 140721 String are now interpreted as javaScript code. URLs should always be wrapped with Location.
 *
 * 141125 Link is transformed into a panel, where setText(String) is deprecated and
 * replaced by setComponent(Component). Now the Link may act on any component, not just text. In
 * practice, it has been transformed into a panel. The only reason it is not called LinkPanle is that
 * it would be intuitively confusing. Since it is now a panel, layout properties like font, padding,
 * backgroundColor, etc are also deprecated.
 *
 * 141230 (Dance3) Renamed from Link to LinkPanel
 *
 */
public class LinkPanel extends Component{

    private Component content = null;
    private String tooltip = null;
    private Object onClick = null;
    private Object onMouseOver = null;
    private Object onMouseOut = null;
    private String target = null;
    
    public LinkPanel(){
    }

    public LinkPanel(Component content, String javaScript){
        this.content = content;
        this.onClick = javaScript;
    }

    public LinkPanel(Component content, Action action){
        this.content = content;
        this.onClick = action;
    }
    
    public LinkPanel(Component content, Location location){
        this.content = content;
        this.onClick = location;
    }

    public LinkPanel setContent(Component content){
        this.content = content;
        return this;
    }

    public LinkPanel setTooltip(String tooltip){
        this.tooltip = tooltip;
        return this;
    }

    public LinkPanel onClick(String javaScript){
        this.onClick = javaScript;
        return this;
    }
    
    public LinkPanel onClick(Action action){
        this.onClick = action;
        return this;
    }
    
    public LinkPanel onClick(Location location){
        this.onClick = location;
        return this;
    }

    public LinkPanel onMouseOver(String javaScript){
        this.onMouseOver = javaScript;
        return this;
    }
    
    public LinkPanel onMouseOver(Action action){
        this.onMouseOver = action;
        return this;
    }
        
    public LinkPanel onMouseOut(String javaScript){
        this.onMouseOut = javaScript;
        return this;
    }
    
    public LinkPanel onMouseOut(Action action){
        this.onMouseOut = action;
        return this;
    }
        
    
    public LinkPanel setTarget(String target){
        this.target = target;
        return this;
    }

    /**
     * A simple default renderer for test purpouse. Should be overridden by controllers that extend
     * this class.
     */
    public void render(Context context) throws Exception{
        StringBuilder style = new StringBuilder();
        style.append("text-decoration:none;");
        
        PrintWriter out = context.getPrintWriter();
        
        out.append("<a");
        if(id!=null) out.append(" id=\""+id+"\"");
        out.append(" style=\"" + style.toString() + "\"");

        if(onClick!=null){
            if(onClick instanceof AjaxAction){
                out.append(" href=\"#\" onclick=\""+((AjaxAction)onClick)+";return false;\"");
            }else if(onClick instanceof Action){
                out.append(" href=\""+((Action)onClick).getUri()+"\"");
            }else if(onClick instanceof Location){
                out.append(" href=\""+(Location)onClick+"\"");
            }else if(onClick instanceof String){
                //out.append(" href=\"JavaScript:"+(String)onClick+"\"");
                out.append(" href=\"#\" onclick=\""+((String)onClick)+";return false;\"");
            }else{
                throw new Exception("The instance of " + onClick.getClass() + " is not supported");
            }
        }

        if(onMouseOver!=null) out.append(Renderer.onMouseOver(onMouseOver, context));
        if(onMouseOut!=null) out.append(Renderer.onMouseOut(onMouseOut, context));

        if(tooltip!=null){
            out.append(" title=\""+tooltip+"\"");
        }

        if(target!=null){
            out.append(" target=\""+target+"\"");
        }

        out.append(">");
        
        if(content!=null) content.render(context);

        out.append("</a>");

    }

    
}
