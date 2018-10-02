/*
 * Image.java
 *
 * Created on den 11 juli 2005, 02:14
 */

package com.sparkney.dance.gui.base;


import com.sparkney.dance.core.*;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Örjan Derelöv
 *
 * Note: For a floating <img> to render correct in IE, hspace and vspace must be
 * set to 0, ie <img hspace="0" vspace="0" .. > Else there will be a slight margin
 * to the lef and to the right of the image.
 */
public class Image extends Component{

    private class Props{
        private String url = null;
        private String tooltip = null;

        private Object onClick = null;
        private Object onMouseOver = null;
        private Object onMouseOut = null;

        private int width = -1;
        private int height = -1;
        private int relativeWidth = -1;
        private int relativeHeight = -1;
        private Color backgroundColor = null;

        private Padding padding = null;
        private Margin margin = null;
        private Border border = null;     //Borders around this component

        private String explicitStyle = null;
        private String explicitAttribute = null;
    }

    private Props props = new Props();
    private HashMap<Media, Props> mediaMap = new HashMap<Media, Props>();

    private Props getProps(Media media){
        Props props = mediaMap.get(media);
        if(props==null){
            props = new Props();
            mediaMap.put(media, props);
        }
        return props;
    }
    
    public Image(){
    }
    
    public Image(String url){
        this.props.url = url;
    }
    
    public Image setTooltip(String tooltip){
        this.props.tooltip = tooltip;
        return this;
    }

    public Image onClick(Action action){
        this.props.onClick = action;
        return this;
    }

    public Image onClick(AjaxAction ajaxAction){
        this.props.onClick = ajaxAction;
        return this;
    }

    public Image onClick(String url){
        this.props.onClick = url;
        return this;
    }

    public Image onClick(SubmitForm submitForm){
        this.props.onClick = submitForm;
        return this;
    }

    public Image onMouseOver(Action action){
        this.props.onMouseOver = action;
        return this;
    }

    public Image onMouseOver(String url){
        this.props.onMouseOver = url;
        return this;
    }

    public Image onMouseOver(SubmitForm submitForm){
        this.props.onMouseOver = submitForm;
        return this;
    }

    public Image onMouseOut(Action action){
        this.props.onMouseOut = action;
        return this;
    }

    public Image onMouseOut(String url){
        this.props.onMouseOut = url;
        return this;
    }

    public Image onMouseOut(SubmitForm submitForm){
        this.props.onMouseOut = submitForm;
        return this;
    }

    public Image setWidth(int width){
        this.props.width = width;
        return this;
    }

    public Image setWidth(Media media, int width){
        getProps(media).width = width;
        return this;
    }

    public Image setHeight(int height){
        this.props.height = height;
        return this;
    }
    
    public Image setHeight(Media media, int height){
        getProps(media).height = height;
        return this;
    }

    /**
     * Set desired relative width in procentage of the context space.
     */
    public Image setRelativeWidth(int relativeWidth){
        this.props.relativeWidth = relativeWidth;
        return this;
    }
    
    /**
     * Set desired relative height in procentage of the context space.
     */
    public Image setRelativeHeight(int relativeHeight){
        this.props.relativeHeight = relativeHeight;
        return this;
    }
    
    /**
     * Set background color.
     */
    public Image setBackgroundColor(Color backgroundColor){
        this.props.backgroundColor = backgroundColor;
        return this;
    }
    
    /**
     * Sätter avståndet mellan komponentens innerkant, eller ramens innerkant om
     * det finns någon ram, och cellerna i panelen. Färgen på avståndet är samma
     * som panelens bakgrundsfärg.
     */
    public Image setPadding(int width){
        this.props.padding = new Padding(width);
        return this;
    }
    
    public Image setPadding(int top, int right, int bottom, int left){
        this.props.padding = new Padding(top,right,bottom,left);
        return this;
    }
    
    public Image setPadding(Padding padding){
        this.props.padding = padding;
        return this;
    }
    
    /**
     *  Sätter minsta avståndet mellan komponentens ytterkant, eller ramens ytterkant
     *  om det finns någon ram, och de komponenter som ligger utanför panelen. Färgen
     *  på marginalen är transparant.
     */
    public Image setMargin(int width){
        this.props.margin = new Margin(width);
        return this;
    }
    
    public Image setMargin(int top, int right, int bottom, int left){
        this.props.margin = new Margin(top,right,bottom,left);
        return this;
    }
    
    public Image setMargin(Margin margin){
        this.props.margin = margin;
        return this;
    }
    
    /**
     * Set borders around the container. Use setCellBorder() to set the inner
     * borders of the container.
     */
    public Image setBorder(Border border){
        this.props.border = border;
        return this;
    }
    
    /**
     * Set image url
     */
    public Image setUrl(String url){
        this.props.url = url;
        return this;
    }
    
    /**
     * Get image url
     */
    public String getUrl(){
        return props.url;
    }

    /**
     * Add explicit style. Should only be used when nothing else works.
     */
    public Image addExplicitStyle(String name, String value){
        if(this.props.explicitStyle==null){
            this.props.explicitStyle = name + ":" + value + ";";
        }else{
            this.props.explicitStyle += name + ":" + value + ";";
        }
        return this;
    }

    public Image addExplicitAttribute(String name, String value){
        if(this.props.explicitAttribute==null){
            this.props.explicitAttribute = " " + name + "=\"" + value + "\"";
        }else{
            this.props.explicitAttribute += " " + name + "=\"" + value + "\"";
        }
        return this;
    }
    
    public void render(Context context) throws Exception{
        StringBuilder style = new StringBuilder();
        if(props.backgroundColor!=null) style.append("background-color:"+props.backgroundColor+";");
        if(props.padding!=null) style.append(props.padding);
        if(props.margin!=null) style.append(props.margin);
        if(props.width!=-1) style.append("width:"+props.width+"px;");
        if(props.height!=-1) style.append("height:"+props.height+"px;");
        if(props.relativeWidth!=-1) style.append("width:"+props.relativeWidth+"%;");
        if(props.relativeHeight!=-1) style.append("height:"+props.relativeHeight+"%;");
        if(props.border!=null) style.append(props.border);
        if(props.explicitStyle!=null) style.append(props.explicitStyle);
        
        PrintWriter out = context.getPrintWriter();

        if(mediaMap.isEmpty()){
            //There is no media, use inline styles.
            out.append("<img border=\"0\"");
            if(id!=null) out.append(" id=\"" + id + "\"");
            if(props.tooltip!=null) out.append(" title=\"" + props.tooltip + "\"");
            if(style.length()>0) out.append(" style=\"" + style.toString() + "\"");
            out.append(" src=\""+props.url+"\"");
            if(props.onClick!=null) out.append(Renderer.onClick(props.onClick, context));
            if(props.onMouseOut!=null) out.append(Renderer.onMouseOut(props.onMouseOut, context));
            if(props.onMouseOver!=null) out.append(Renderer.onMouseOver(props.onMouseOver, context));
            if(props.explicitAttribute!=null) out.append(props.explicitAttribute);
            out.append(">");
        }else{
            //We need styletags to define media statements
            String elementClassName = "class"+this.hashCode();      //Generate a random class name

            out.append(BR+"<style>");
            out.append("."+elementClassName+"{");
            out.append(style.toString()+"");
            out.append(BR+"}");

            for (Map.Entry<Media, Props> mediaMapEntry : mediaMap.entrySet()) {
                Media media = mediaMapEntry.getKey();
                Props mediaProps = mediaMapEntry.getValue();

                out.append(BR+"@media all");
                if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
                if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
                out.append("{");

                out.append("."+elementClassName+"{");
                if(mediaProps.backgroundColor!=null) style.append("background-color:"+mediaProps.backgroundColor+";");
                if(mediaProps.padding!=null) out.append(mediaProps.padding.toString());
                if(mediaProps.margin!=null) out.append(mediaProps.margin.toString());
                if(mediaProps.width!=-1) out.append("width:"+mediaProps.width+"px;");
                if(mediaProps.height!=-1) out.append("height:"+mediaProps.height+"px;");
                if(mediaProps.relativeWidth!=-1) out.append("width:"+mediaProps.relativeWidth+"%;");
                if(mediaProps.relativeHeight!=-1) out.append("height:"+mediaProps.relativeHeight+"%;");
                if(mediaProps.border!=null) out.append(mediaProps.border.toString());
                if(mediaProps.explicitStyle!=null) out.append(mediaProps.explicitStyle);

                out.append("}");
                out.append("}");
            }

            out.append(BR+"</style>");

            out.append("<img border=\"0\"");
            if(id!=null) out.append(" id=\"" + id + "\"");
            out.append(" class=\"" + elementClassName + "\"");
            out.append(" src=\""+props.url+"\"");
            if(props.tooltip!=null) out.append(" title=\"" + props.tooltip + "\"");
            if(props.onClick!=null) out.append(Renderer.onClick(props.onClick, context));
            if(props.onMouseOut!=null) out.append(Renderer.onMouseOut(props.onMouseOut, context));
            if(props.onMouseOver!=null) out.append(Renderer.onMouseOver(props.onMouseOver, context));
            if(props.explicitAttribute!=null) out.append(props.explicitAttribute);
            out.append(">");
            
        }
        



        
        
    }
    
}
