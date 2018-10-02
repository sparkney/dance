/*
 * Text.java
 *
 * Created on den 11 juli 2005, 02:14
 *
 * NOTES
 *
 * 161201 One thing you want to do with text is to compost different texts with
 * different properties and let it flow in one single paragraph when the display
 * is resized. This can not be done with display:inline-box. With inline-box,
 * the "boxes" are shifted down when the display width is decreasing, BEFORE the
 * words in the texts are shifted down. This is not how you like text to flow on
 * the page. With text and flowing text layout display:inline have to be used.
 *
 *
 * Old note: For text to render exactly the same in IE and NS line-height must
 * explicitly be set to the same value. Else, IE has a slight larget line-height.
 * This may be important when floating an object inside a text and the text
 * should wrap nicely around the object. There is no way to fine-tune this
 * without having explicitly set the line-height.
 *
 * Old note: The <div> element must be used for the component to work width the
 * FlowPanel manager. If <table> is used, the text can not wrap around an image.
 *
 * CHANGES
 *
 * 161201 The display:inline-box is changed to display:inline. This is necessary
 * to let composed texts flow as expected with a TextFlowLayout. The
 * display:inline-box will shift the "text boxes" down before the words in the
 * texts are shifted, and this is not what we expect from a flowing text.
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//### Skapa en ny layout manager där man väljer som den ska vara horisontell eller
//### vertikal, och där det går att välja med mediaqueries. För varje cell måste
//### det finnas hide- eller show-funktion, som också kan sättas med media queries.
//### Detta för att det ska gå att byta innehåll i en cell helt och hållet om man
//### vill vid olika skärmstorlekar.

/**
 *
 * @author Örjan Derelöv
 *
 */
public class Text extends Component{

    private class Props{
        private String text = "";
        private String tooltip = null;
        private Font font = null;
        private Color backgroundColor = null;
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

    public Text(){
    }

    public Text(String text){
        this.props.text = text;
    }

    public Text(int text){
        this.props.text = String.valueOf(text);
    }

    public Text setText(String text){
        this.props.text = text;
        return this;
    }
    
    public Text setText(int text){
        this.props.text = String.valueOf(text);
        return this;
    }
    
    public Text setTooltip(String tooltip){
        this.props.tooltip = tooltip;
        return this;
    }

    public Text setBackgroundColor(Color backgroundColor){
        this.props.backgroundColor = backgroundColor;
        return this;
    }

    public Text setBackgroundColor(Media media, Color backgroundColor){
        getProps(media).backgroundColor = backgroundColor;
        return this;
    }

    public Text setFont(Font font){
        this.props.font = font;
        return this;
    }

    public Text setFont(Media media, Font font){
        getProps(media).font = font;
        return this;
    }

    public Text addExplicitStyle(String name, String value){
        if(this.props.explicitStyle==null){
            this.props.explicitStyle = name + ":" + value + ";";
        }else{
            this.props.explicitStyle += name + ":" + value + ";";
        }
        return this;
    }

    public Text addExplicitAttribute(String name, String value){
        if(this.props.explicitAttribute==null){
            this.props.explicitAttribute = " " + name + "=\"" + value + "\"";
        }else{
            this.props.explicitAttribute += " " + name + "=\"" + value + "\"";
        }
        return this;
    }

    public void render(Context context) throws Exception{
        StringBuilder defaultStyle = new StringBuilder();
        defaultStyle.append("display:inline;");
        if(props.font!=null) defaultStyle.append(props.font);
        if(props.backgroundColor!=null) defaultStyle.append("background-color:"+props.backgroundColor+";");
        if(props.explicitStyle!=null) defaultStyle.append(props.explicitStyle);

        PrintWriter out = context.getPrintWriter();

        if(mediaMap.isEmpty()){
            //There is no media, use inline styles.
            out.append(BR+"<div");
            if(id!=null) out.append(" id=\"" + id + "\"");
            out.append(" style=\"" + defaultStyle.toString() +"\"");
            if(props.tooltip!=null) out.append(" title=\""+props.tooltip+"\"");
            if(props.explicitAttribute!=null) out.append(props.explicitAttribute);
            out.append(">");
            out.append(props.text);
            out.append("</div>");

        }else{
            //We need style tags to define media statements
            String elementClassName = "class"+this.hashCode();      //Generate a random class name

            out.append(BR+"<style>");
            out.append("."+elementClassName+"{");
            out.append(defaultStyle.toString()+"");
            out.append(BR+"}");

            for(Map.Entry<Media, Props> mediaMapEntry : mediaMap.entrySet()){
                Media media = mediaMapEntry.getKey();
                Props mediaProps = mediaMapEntry.getValue();

                out.append(BR+"@media all");
                if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
                if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
                out.append("{");

                out.append("."+elementClassName+"{");
                if(mediaProps.font!=null) out.append(mediaProps.font.toString());
                if(mediaProps.backgroundColor!=null) out.append("background-color:"+mediaProps.backgroundColor+";");

                out.append("}");
                out.append("}");
            }

            out.append(BR+"</style>");

            out.append(BR+"<div");
            if(id!=null) out.append(" id=\"" + id + "\"");
            out.append(" class=\"" + elementClassName + "\"");
            if(props.tooltip!=null) out.append(" title=\""+props.tooltip+"\"");
            if(props.explicitAttribute!=null) out.append(props.explicitAttribute);
            out.append(">");
            out.append(props.text);
            out.append("</div>");

       }

    }

}
