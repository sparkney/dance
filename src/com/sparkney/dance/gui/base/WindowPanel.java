 /*
 * WindowPanel.java
 *
 * Created on den 11 juli 2005, 00:31
 *
 * TODO: Add setters for metadata, JavaScript, external CSS, etc.
 *
 * CHANGELOG
 * 
 * 180108 Modal dialog
 *
 * Remove the falty model where modal dialogs are set in the window. This is a relic
 * from before we used AJAX and there were not other way of popping upp a modal dialog.
 * The problem was that modal dialogs always had to be enabled via dummy dialog that
 * was popped upp every tome the page was rendered, and the immediately closed. For
 * slow devices or possibly slow connections, this would show. Now, we will always
 * enable modal dialogs, and they are popped up via AJAX.
 * 
 * Since modal dialogs are always enebled the JavaScript functions _openModalDialog()
 * and _closeModalDialog() can always be loaded and we can move them in dance.js.
 * Update: We already have _openModalDialog2() and _closeModalDialog2() in dance.js.
 * Rename them _openModalDialog() and _closeModalDialog(), and remove _openModalDialog()
 * and _closeModalDialog() from WindowPanel.
 *
 * There is currently a bug in iOS 11 concerning model dialogs. We need an exception
 * for this bug. See dance.js
 * Bug description, not resolved by 2018-01-05
 * https://hackernoon.com/how-to-fix-the-ios-11-input-element-in-fixed-modals-bug-aaf66c7ba3f8
 * https://bugs.webkit.org/show_bug.cgi?id=176896
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Örjan Derelöv
 *
 * Generates the general HTML-page properties, like tags for html, head, title and body.
 *
 */
public class WindowPanel extends Component {

    private String title = "";
    private ArrayList<String> javaScriptUrls = null;
    protected ArrayList<String> javaScript = null;
    private ArrayList<String> styleSheetUrls = null;
    private HashMap<String,String> metas = null;
    private Component modalDialog = null;
    private Component content = null;
    private Font defaultFont = null;
    private Font defaultLinkFont = null;
    private Color backgroundColor = null;
    private BackgroundImage backgroundImage = null;
    private String faviconUrl = null;
    private String charset = "UTF-8";

    private int width = -1;
    private int height = -1;
    private int relativeWidth = -1;
    private int relativeHeight = -1;

    private StringBuilder styleSheetStatement = null;

    public WindowPanel(){
    }

    public WindowPanel(Component content){
        this.content = content;
    }
    
    /**
     * @deprecated Replaced by setDefaultFont(Font font)
     */
    public WindowPanel setFont(Font font){
        this.defaultFont = font;
        return this;
    }

    /**
     * @deprecated Replaced by setDefaultFont(Font font)
     */
    public WindowPanel setFontColor(Color fontColor){
        if(defaultFont==null) defaultFont = new Font();
        this.defaultFont.color = fontColor;
        return this;
    }

    /**
     * @deprecated Find a better solution. Maybe a class StyleSheet, that has
     * standard settings, that can be altered (like default font, default link
     * font, link underline, and other properties for resetting the style) use
     * setStyleSheet(StyleSheet styleSheet) when we want to user it.
     */
    public WindowPanel setDefaultFont(Font font){
        this.defaultFont = font;
        return this;
    }

    /**
     * @deprecated This was only a temporary solution in a specific project. Do
     * not user this method, it will definitely be removed in a near future. It
     * has not yet any replacement.
     */
    public WindowPanel setDefaultLinkFont(Font font){
        this.defaultLinkFont = font;
        return this;
    }

    public WindowPanel setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }

    public WindowPanel setBackgroundImage(BackgroundImage image){
        this.backgroundImage = image;
        return this;
    }

    public WindowPanel setFaviconUrl(String url){
        this.faviconUrl = url;
        return this;
    }

    public WindowPanel setCharset(String charset){
        this.charset = charset;
        return this;
    }

    /**
     * Set desired width.
     */
    public WindowPanel setWidth(int width){
        this.width = width;
        return this;
    }

    /**
     * Set desired relative width in procentage of the context space.
     */
    public WindowPanel setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }

    /**
     * Set desired relative height in procentage of the context space.
     */
    public WindowPanel setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }


    /**
     * Set desired height.
     */
    public WindowPanel setHeight(int height){
        this.height = height;
        return this;
    }

    public WindowPanel setTitle(String title){
        this.title = title;
        return this;
    }
    
    public WindowPanel setContent(Component content){
        this.content = content;
        return this;
    }

    /**
     * Add a JavaScript URL to this window.
     */
    public void addJavaScriptUrl(String javaScriptUrl){
        if(javaScriptUrls==null){
            javaScriptUrls = new ArrayList<String>();
        }
        this.javaScriptUrls.add(javaScriptUrl);
    }

    /**
     * Add a JavaScript code.
     */
    public void addJavaScript(String javaScript) throws Exception{
        if(this.javaScript==null){
            this.javaScript = new ArrayList<String>();
        }
        this.javaScript.add(javaScript);
    }


    /**
     * Add a AtyleSheet URL to this window.
     */
    public void addStyleSheetUrl(String styleSheetUrl){
        if(styleSheetUrls==null){
            styleSheetUrls = new ArrayList<String>();
        }
        this.styleSheetUrls.add(styleSheetUrl);
    }

    /**
     * Add a AtyleSheet URL to this window.
     */
    public void addMeta(String name, String content){
        if(metas==null){
            metas = new HashMap<String, String>();
        }
        this.metas.put(name, content);
    }

    /**
     * Add a style sheet statement. Should only be used when nothing else works.
     */
    public void addStyleSheetStatement(String statement){
        if(this.styleSheetStatement==null){
            this.styleSheetStatement = new StringBuilder();
        }
        this.styleSheetStatement.append(" ");
        this.styleSheetStatement.append(statement);
    }
    
    /**
     * Set a modal dialog component.
     * @param modalDialog
     */
    public void setModalDialog(Component modalDialog){
        this.modalDialog = modalDialog;
    }

    public void render(Context context) throws Exception{

        if(content==null){
            throw new Exception("No component set in WindowPanel");
        }

        StringBuilder bodyStyle = new StringBuilder();
        bodyStyle.append("margin:0px;");
        if(backgroundColor!=null) bodyStyle.append("background-color:"+backgroundColor+";");
        if(backgroundImage!=null) bodyStyle.append(backgroundImage);
        if(width!=-1) bodyStyle.append("width:"+width+"px;");
        if(height!=-1) bodyStyle.append("height:"+height+"px;");
        if(relativeWidth!=-1) bodyStyle.append("width:"+relativeWidth+"%;");
        if(relativeHeight!=-1) bodyStyle.append("height:"+relativeHeight+"%;");

        PrintWriter out = context.getPrintWriter();

        out.append("<!DOCTYPE html>");
        out.append(BR+"<html style=\"");
        if(width!=-1) out.append("width:"+width+"px;");
        if(height!=-1) out.append("height:"+height+"px;");
        if(relativeWidth!=-1) out.append("width:"+relativeWidth+"%;");
        if(relativeHeight!=-1) out.append("height:"+relativeHeight+"%;");
        out.append("\">");

        out.append(BR+"<head>");
        out.append(BR+"<title>");
        out.append(title);
        out.append(BR+"</title>");

        out.append(BR+"<meta charset=\""+charset+"\">");

        if(metas!=null){
            for (Map.Entry<String, String> entry : metas.entrySet()) {
                out.append(BR+"<meta name=\""+entry.getKey()+"\" content=\""+entry.getValue()+"\">");
            }
        }

        if(styleSheetUrls!=null){
            for(String styleSheetUrl : styleSheetUrls){
                out.append(BR+"<link href=\""+styleSheetUrl+"\" rel=\"stylesheet\">");
            }
        }

        if(faviconUrl!=null){
            out.append(BR+"<link rel=\"icon\" href=\""+faviconUrl+"\" >");
        }

        if(javaScriptUrls!=null){
            for(String javaScriptUrl : javaScriptUrls){
                out.append(BR+"<script src=\""+javaScriptUrl+"\"></script>");
            }
        }

        if(javaScript!=null){
            out.append(BR+"<script type=\"text/javascript\">");
            for(int i=0; i<javaScript.size();i++){
                out.append(BR+javaScript.get(i));
            }
            out.append(BR+"</script>");
        }

        out.append(BR+"<style type=\"text/css\">");
        out.append(BR+" * {-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;}"); //Natural box model

        //TODO: Should everything aligt at top? Good for layout, but not for spread sheet-like tables where we want the info to aligin in the middel of the cells.
        out.append(BR+" * {text-align:left;vertical-align:top;}"); //Reset alignments
        out.append(BR+" html {height:100%;}");
        out.append(BR+" body {height:100%;");
        if(defaultFont!=null) out.append(defaultFont.toString()); else out.append("font-size:15px;font-family:arial;");
        out.append("}");
        out.append(BR+" input[type=\"button\"],input[type=\"submit\"] {-webkit-appearance:none;border-radius:0;}"); //Reset input for iOS. Resetting for all input elements will make checkboxes disapear in Chrome.
        
        if(defaultLinkFont!=null) out.append(BR+" a{" + defaultLinkFont + "}");
        if(styleSheetStatement!=null) out.append(BR+styleSheetStatement);
        out.append(BR+"</style>");
        
        out.append(BR+"</head>");
        out.append(BR+"<body");
        out.append(" style=\""+bodyStyle+"\"");
        out.append(">");

        //Conserning modal dialogs: If there is a problem with modal dialogs using position:fixed, use position:absolute
        //and make sure to scroll to top. Problems may arise when the modal dialog is heigher then the viewport, especially
        //for smartphones when the virutal keyboard cover half the screen. Scrolling in a fixed position element seem to
        //be extremely hard to do, or even impossible. Using position:absolute and scroll to top is also the workaround for
        //the iOS 11 bug, where the carret is positiont outside textboxes in modal dialogs when virutal keyboard opens.
        //TODO: If possible, elaborate one single dialog model that works on all devices. Scrolling on small devices is
        //a problem. Research if layerd iframes with fixed position, 100% with and height and autoscrolling is the way to
        //Modal dialogs should be a special case, since they are used extensively.
        //180404: Have done some research on how to scroll modal dialogs, this works in GC, FF, Androeid. Don't know about iOS:
        //
        //<div id="_modalBlur" style="position:fixed;visibility:visible;top:0;right:0;bottom:0;left:0;height:100%;width:100%;margin:0;padding:0;background-color:#000000;opacity:.50;filter:alpha(opacity=50);-moz-opacity:0.50;z-index:1000;"></div>
        //<div id="_modalDialog" style="max-height:calc(100vh - 100px);overflow-y:auto;position:fixed;left:50%;top:30%;transform:translate(-50%, -30%);visibility:visible;z-index:1001;border:5px solid red"> CONTENT </div>
        //
        //Spaces in "calc(100vh - 100px)" needed!! Scroll in modal dialogs work. Have to check iOS.
        //
        //Test here: http://sparkney.se/crisispilot/test/modal/test1.jsp
        //
        //It's working on iPhone accorting to mail from Ingrid 04/04/18 11:57 "Re: Dialogrutor till Crisispilot"
                
        //Enable modal dialog popups
        if(modalDialog!=null){
            //Render and view modal dialog container and dialog component on page load
            out.append(BR+"<table id=\"_modalBlur\" style=\"position:fixed;top:0;right:0;bottom:0;left:0;height:100%;width:100%;margin:0;padding:0;background-color:#000000;opacity:.50;filter:alpha(opacity=50);-moz-opacity:0.50;z-index:1000;\"><tr><td></td></tr></table>");
            out.append(BR+"<table id=\"_modalDialog\" style=\"position:fixed;top:0;right:0;bottom:0;left:0;height:100%;width:100%;margin:0;padding:0;z-index:1001;\"><tr><td id=\"_modalComponent\" style=\"text-align:center;vertical-align:middle\">");
            modalDialog.render(context);
            out.append(BR+"</td></tr></table>");
        }else{
            //Render but hide modal dialog container 
            out.append(BR+"<table id=\"_modalBlur\" style=\"position:fixed;visibility:hidden;top:0;right:0;bottom:0;left:0;height:100%;width:100%;margin:0;padding:0;background-color:#000000;opacity:.50;filter:alpha(opacity=50);-moz-opacity:0.50;z-index:1000;\"><tr><td></td></tr></table>");
            out.append(BR+"<table id=\"_modalDialog\" style=\"position:fixed;visibility:hidden;top:0;right:0;bottom:0;left:0;height:100%;width:100%;margin:0;padding:0;z-index:1001;\"><tr><td id=\"_modalComponent\" style=\"text-align:center;vertical-align:middle\"></td></tr></table>");
        }
        
        content.render(context);

        //Can't be rendered in the header, since then no components that may
        //add a javascript url are rendered at that satege.
        if(context.getJavaScriptUrls()!=null){
            for(String javaScriptUrl : context.getJavaScriptUrls()){
                out.append(BR+"<script src=\""+javaScriptUrl+"\"></script>");
            }
        }

        if(context.getOnResize()!=null){
            out.append(BR+"<script>");
            for(String onResize : context.getOnResize()){
                out.append(BR+"window.addEventListener(\"resize\","+onResize+");");
            }
            out.append(BR+"</script>");
        }

        if(context.getOnLoad()!=null){
            out.append(BR+"<script>");
            for(String onLoad : context.getOnLoad()){
                out.append(BR+"window.addEventListener(\"load\","+onLoad+");");
            }
            out.append(BR+"</script>");
        }

        out.append(BR+"</body>");
        out.append(BR+"</html>");

    }

}
