/*
 * TODO
 *
 * Add setHGap(int) and setVGap(int). That works like Gap, but only apply horizontally
 * or vertically, depending on the Method used. Would add to spacing, but just between
 * cells. (Currently we use Spacing to get the the gap functionality, in a rather bulky
 * way.)
 * 
 * Wouold be nice with laytou.setCellDisplay(boolean). Howerver, this is a bit tricky,
 * since it involves the core layout of the manager and we would have to user Boolean
 * and check for null wherever display is used. 
 * 
 * Layout height don't seem to work as expected when adding to components in a vertical
 * fashion. A 100x100 layout will become 100x200, where each cell is 100x100. This is
 * probably because vertical layout is not table, but block.
 *
 * No need for border container/cell when borders are not used. Only render
 * border containers when borders are used.
 *
 * Document why we need border containers. Why can't borders be applied on the main
 * container cells?
 *
 * If a block contains an inline-table, there will always be space (current line height)
 * between two vertical cells. The space is of font-size height. This is making BasicLayout
 * useless for creating layouts where we need thin horizontal lines, eg in menus where we
 * need a 1 px line separating the header from the menu. I have no other idea to solve
 * this problem, other then removing the border layout and the border cell when they
 * are not needed. This however is not easy, since border cell have a lot of properties
 * that need to be moved to the container cell. And we are likely to run in to other
 * problems if we do that. Maybe there is another combination of display-values that
 * work even when we need to layout two vertical cells with no space between them.
 * This need further research. (The problem is to get text-align and vertical-align
 * to work.) We could set the line-height of the container cell to 0 pix (tested), but
 * then we would have to reset it somehow, since we need a line height for text.
 * SOLUTION 170315: It is display:inline-table in the border layout that causes the problem,
 * but since we don't need the inline-properties in the border container we can swap
 * it to display:table. (Need table layout to keep vertical-align woorking.)
 *
 *
 * CHANGE LOG
 * 
 * 180904
 * Added setPadding(int) and  setPadding(Media,int) to the Layout.Fix Spacing that
 * that previously didn't work properly.
 * 
 * 170308
 * Un-display/display-problem is no longer a problem. We can now remove a cell
 * from display by default, and view it for just one media type. Like this
 *
 *   basicLayout.add(comp1).setDisplay(false).setDisplay(mediaMobile, true);
 *
 * setDisplay(false) -> By default, remove cell from all displays.
 * setDisplay(mediaMobile, true) -> View cell for the media type mediaMobile.
 *
 * Created on den 4 january 2014, 02:37
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.Component;
import com.sparkney.dance.core.Context;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic layout menager that handles horizontal and vertical layout depending on
 * media. 
 *
 * @author Örjan Derelöv
 */
public class BasicLayout  extends Component{

 
    
//    ### Om man anger basicLayout.setHeight(123) så är cellerna alltid 123px höga, oavsett om
//    ### man använder vertial eller horisontell layout,
//    ### Kanske beror på table-cell, går möjligen inte att fixa. Hur gör vi?


    /**
     * Layout methods
     */
    public enum Method {
        HORIZONTAL,         //Horizontal layout method
        VERTICAL            //Vertical layout method
    };

    private ArrayList<BasicLayoutCell> cellList = new ArrayList<BasicLayoutCell>();

    private class Props{
        private Method method = null;
        private int width = -1;
        private int height = -1;
        private float relativeWidth = -1;
        private float relativeHeight = -1;
        private int maxWidth = -1;
//        private int maxHeight = -1;
        private int minWidth = -1;
//        private int minHeight = -1;
        private Padding padding = null;
        private Integer gap = null;
        private Padding cellPadding = null; //Borders round earch cell in this container
        private Border border = null;       //Borders round the container
        private Border cellBorder = null;
        private Spacing cellSpacing = null;
        private  Color backgroundColor = null;
        private float cellRelativeWidth = -1;
        private float cellRelativeHeight = -1;
    }

    private Props defaultLayoutProps = new Props();
    private HashMap<Media, Props> mediaMap = new HashMap<Media, Props>();
    private ArrayList<Media> mediaList = new ArrayList<Media>();            //Unique medias used in this layout and the cells. New medias used in cells will also be added to the this media list. When rendering the cells, the media list is used to iterate over all the medias for each cell.

    private Props getProps(Media media){
        Props props = mediaMap.get(media);
        if(props==null){
            props = new Props();
            mediaMap.put(media, props);
            if(!mediaList.contains(media)){
                mediaList.add(media);
            }
        }
        return props;
    }

    public BasicLayout(Method method){
        defaultLayoutProps.method = method;
    }

    public BasicLayout setMethod(Method method){
        defaultLayoutProps.method = method;
        return this;
    }

    public BasicLayout setMethod(Media media, Method method){
        getProps(media).method = method;
        return this;
    }

    public BasicLayout setBorder(Border border){
        defaultLayoutProps.border = border;
        return this;
    }

    public BasicLayout setCellBorder(Border cellBorder){
        defaultLayoutProps.cellBorder = cellBorder;
        return this;
    }

    public BasicLayout setWidth(int width){
        defaultLayoutProps.width = width;
        return this;
    }

    public BasicLayout setHeight(int height){
        defaultLayoutProps.height = height;
        return this;
    }

    public BasicLayout setRelativeWidth(float relativeWidth){
        defaultLayoutProps.relativeWidth = relativeWidth;
        return this;
    }


    public BasicLayout setCellRelativeWidth(float cellRelativeWidth){
        defaultLayoutProps.cellRelativeWidth = cellRelativeWidth;
        return this;
    }

    public BasicLayout setCellRelativeWidth(Media media, float cellRelativeWidth){
        getProps(media).cellRelativeWidth = cellRelativeWidth;
        return this;
    }



    public BasicLayout setRelativeHeight(float relativeHeight){
        defaultLayoutProps.relativeHeight = relativeHeight;
        return this;
    }

    public BasicLayout setCellRelativeHeight(float cellRelativeHeight){
        defaultLayoutProps.cellRelativeHeight = cellRelativeHeight;
        return this;
    }

    public BasicLayout setMaxWidth(int maxWidth) {
        defaultLayoutProps.maxWidth = maxWidth;
        return this;
    }

//    public BasicLayout setMaxHeight(int maxHeight) {
//        defaultLayoutProps.maxHeight = maxHeight;
//        return this;
//    }

    public BasicLayout setMinWidth(int minWidth) {
        defaultLayoutProps.minWidth = minWidth;
        return this;
    }

//    public BasicLayout setMinHeight(int minHeight) {
//        defaultLayoutProps.minHeight = minHeight;
//        return this;
//    }

    /**
     * Set the distance between the inner edge of the cell, or the inner edge of the border,
     * if there is one, and the outer edge of the content. This distance is covered by the
     * background color of the cell.
     */
    public BasicLayout setPadding(int width){
        defaultLayoutProps.padding = new Padding(width);
        return this;
    }

    public BasicLayout setPadding(int top, int right, int bottom, int left){
        defaultLayoutProps.padding = new Padding(top,right,bottom,left);
        return this;
    }

    public BasicLayout setPadding(Padding padding){
        defaultLayoutProps.padding = padding;
        return this;
    }
    
    public BasicLayout setPadding(Media media, int width){
        getProps(media).padding = new Padding(width);
        return this;
    }
 
    public BasicLayout setPadding(Media media, int top, int right, int bottom, int left){
        getProps(media).padding = new Padding(top,right,bottom,left);
        return this;
    }

    public BasicLayout setPadding(Media media, Padding padding){
        getProps(media).padding = padding;
        return this;
    }

    public BasicLayout setCellPadding(int width){
        defaultLayoutProps.cellPadding = new Padding(width);
        return this;
    }

    public BasicLayout setCellPadding(int top, int right, int bottom, int left){
        defaultLayoutProps.cellPadding = new Padding(top,right,bottom,left);
        return this;
    }

    public BasicLayout setCellPadding(Padding padding){
        defaultLayoutProps.cellPadding = padding;
        return this;
    }

    /**
     * Set the space between two cells, similar to cell spacing.
     */
    public BasicLayout setGap(int width){
        defaultLayoutProps.gap = width;
        return this;
    }

    public BasicLayout setGap(Media media, int width){
        getProps(media).gap = width;
        return this;
    }

    /**
     * Set the distance between the outer edge of the content and the inner edge
     * of the container, or the outer edge of the border, if there is one, and the
     * inner edge of the container. This distance is not covered by the background
     * color of the cell.
     */
    public BasicLayout setCellSpacing(int width){
        defaultLayoutProps.cellSpacing = new Spacing(width);
        return this;
    }

    public BasicLayout setCellSpacing(int top, int right, int bottom, int left){
        defaultLayoutProps.cellSpacing = new Spacing(top,right,bottom,left);
        return this;
    }

    public BasicLayout setCellSpacing(Spacing spacing){
        defaultLayoutProps.cellSpacing = spacing;
        return this;
    }

    public BasicLayout setBackgroundColor(Color backgroundColor) {
        defaultLayoutProps.backgroundColor = backgroundColor;
        return this;
    }

    public BasicLayout setBackgroundColor(Media media, Color backgroundColor) {
        getProps(media).backgroundColor = backgroundColor;
        return this;
    }

    public BasicLayoutCell add(Component component){
        BasicLayoutCell cell = new BasicLayoutCell(mediaList,component);    //We need the cell to know about the media list, so it can add new medias if necessary.
        cellList.add(cell);
        return cell;
    }

    @Override
    public void render(Context context) throws Exception {

        StringBuilder wrapperStyle = null;   //Wrapper style needed for max-width and min-width to work in newer versions of Chrome and Safari (WebKit). We move the width properties from the container to the wrapper, and let the container span 100% width and height inside the wrapper.
        if(defaultLayoutProps.maxWidth!=-1 || defaultLayoutProps.minWidth!=-1){
            wrapperStyle = new StringBuilder();
            wrapperStyle.append("display:inline-block;");   //inline-block allow max-width and min-width, and can be centered by text-align.

            if(defaultLayoutProps.width!=-1) wrapperStyle.append("width:"+defaultLayoutProps.width+"px;");
            if(defaultLayoutProps.height!=-1) wrapperStyle.append("height:"+defaultLayoutProps.height+"px;");
            if(defaultLayoutProps.relativeWidth!=-1) wrapperStyle.append("width:"+defaultLayoutProps.relativeWidth+"%;");
            if(defaultLayoutProps.relativeHeight!=-1) wrapperStyle.append("height:"+defaultLayoutProps.relativeHeight+"%;");
            if(defaultLayoutProps.maxWidth!=-1) wrapperStyle.append("max-width:"+defaultLayoutProps.maxWidth+"px;");
            if(defaultLayoutProps.minWidth!=-1) wrapperStyle.append("min-width:"+defaultLayoutProps.minWidth+"px;");

            defaultLayoutProps.width = -1;          //Width is now applied to the wrapper and can not be appiled to the container.
            defaultLayoutProps.height = -1;         //Heightis now applied to the wrapper and can not be appiled to the container.
            defaultLayoutProps.relativeWidth=100;   //Span container width 100% in the wrapper.
            defaultLayoutProps.relativeHeight=100;  //Span container height 100% in the wrapper.
        }

        StringBuilder containerStyle = new StringBuilder();
        //defaultLayoutProps.method is never null
        containerStyle.append(defaultLayoutProps.method.equals(Method.HORIZONTAL) ? "display:inline-table;" : "display:inline-block;");
        if(defaultLayoutProps.width!=-1) containerStyle.append("width:"+defaultLayoutProps.width+"px;");
        if(defaultLayoutProps.height!=-1) containerStyle.append("height:"+defaultLayoutProps.height+"px;");
        if(defaultLayoutProps.relativeWidth!=-1) containerStyle.append("width:"+defaultLayoutProps.relativeWidth+"%;");
        if(defaultLayoutProps.relativeHeight!=-1) containerStyle.append("height:"+defaultLayoutProps.relativeHeight+"%;");
        if(defaultLayoutProps.backgroundColor!=null) containerStyle.append("background-color:"+defaultLayoutProps.backgroundColor+";");
        if(defaultLayoutProps.padding!=null) containerStyle.append(defaultLayoutProps.padding);
        if(defaultLayoutProps.border!=null) containerStyle.append(defaultLayoutProps.border);

        PrintWriter out = context.getPrintWriter();

        if(mediaList.isEmpty()){
            //There is no media, use inline styles instead of styletags.
            if(wrapperStyle!=null) out.append(BR+"<div data-desc=\"WRAPPER\" style=\""+wrapperStyle+"\">");
            out.append(BR+"<div data-desc=\"CONTAINER\" style=\""+containerStyle+"\">");
        }else{
            //We need styletags to define media statements
            String wrapperClassName = "wrapper"+this.hashCode();      //Generate a random class name
            String containerClassName = "container"+this.hashCode();      //Generate a random class name

            //Default style
            out.append(BR+"<style>");

            if(wrapperStyle!=null){
                out.append(BR+"."+wrapperClassName+"{");
                out.append(wrapperStyle.toString());
                out.append("}");
            }

            out.append(BR+"."+containerClassName+"{");
            out.append(containerStyle.toString());
            out.append("}");

            //Media styles
            for (Map.Entry<Media, Props> mediaMapEntry : mediaMap.entrySet()) {
                Media media = mediaMapEntry.getKey();
                Props props = mediaMapEntry.getValue();

                out.append(BR+"@media all");
                if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
                if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
                out.append("{");

                out.append(BR+"."+containerClassName+"{");

                if(props.method!=null) out.append(props.method.equals(Method.HORIZONTAL) ? "display:inline-table;" : "display:inline-block;");  //Use inline-block for vertical layout, since block can not be align by text-align.
                if(props.backgroundColor!=null) out.append("background-color:"+props.backgroundColor+";");
                if(props.padding!=null) out.append(props.padding.toString());

                out.append("}");
                out.append(BR+"}");
            }

            out.append(BR+"</style>");
            if(wrapperStyle!=null) out.append(BR+"<div data-desc=\"WRAPPER\" class=\""+wrapperClassName+"\">");
            out.append(BR+"<div data-desc=\"CONTAINER\" class=\""+containerClassName+"\">");
        }

        //Iterate over the cells
        for(int cellIndex=0; cellIndex<cellList.size(); cellIndex++){
            BasicLayoutCell cell = cellList.get(cellIndex);
            BasicLayoutCell.Props defaultCellProps = cell.props;

            overrideDefaultCellProperties(defaultCellProps);

            StringBuilder cellStyle = new StringBuilder();
            cellStyle.append("height:100%;");       //Needed to make cell bottom lines aligned
            //if(defaultCellProps.spacing!=null) cellStyle.append("padding:"+defaultCellProps.spacing.top+"px "+defaultCellProps.spacing.right+"px "+defaultCellProps.spacing.bottom+"px "+defaultCellProps.spacing.left+"px;");

            //Display this cell according to layout method
            if(defaultLayoutProps.method.equals(Method.HORIZONTAL)){
                //Horizontal layout
                if(defaultCellProps.display) cellStyle.append("display:table-cell;"); else cellStyle.append("display:none;");
                if(defaultCellProps.width!=-1) cellStyle.append("width:"+defaultCellProps.width+"px;");
                if(defaultCellProps.relativeWidth!=-1) cellStyle.append("width:"+defaultCellProps.relativeWidth+"%;");
            }else{
                //Vertical layout.
                if(defaultCellProps.display) cellStyle.append("display:block;width:100%;"); else cellStyle.append("display:none;width:100%;");
            }

            if(!defaultCellProps.visible) cellStyle.append("visibility:hidden;");
            if(defaultLayoutProps.gap!=null || defaultCellProps.spacing!=null) cellStyle.append(calculateSpacing(defaultLayoutProps.method,cellIndex,defaultLayoutProps.gap,defaultCellProps.spacing));

            StringBuilder borderContainerStyle = new StringBuilder();
            //borderContainerStyle.append("display:inline-table;");   //The border cell container need to be inline-table for vertical-align to work.
            borderContainerStyle.append("display:table;");  //The border cell container need to be some kind of table for vertical-align to work. However, display:inline-table is not working since it will always expand the height of the container to the current line height, but display:table will not do that. We need empty cells to have zero height.
            borderContainerStyle.append("width:100%;");
            borderContainerStyle.append("height:100%;");

            StringBuilder borderCellStyle = new StringBuilder();
            borderCellStyle.append("display:table-cell;");          //The border cell is table-cell, need for vertical-align to work.
            borderCellStyle.append("width:100%;");                  //TODO: Relly needed? Remove and test.
            borderCellStyle.append("height:100%;");                 //TODO: Relly needed? May be needed to align content at bottom line. Remove and test.
            if(defaultCellProps.backgroundColor!=null) borderCellStyle.append("background-color:"+defaultCellProps.backgroundColor+";");
            if(defaultCellProps.backgroundImage!=null) borderCellStyle.append(defaultCellProps.backgroundImage);
            if(defaultCellProps.hAlign!=-1) borderCellStyle.append("text-align:"+Align.toString(defaultCellProps.hAlign)+";");
            if(defaultCellProps.vAlign!=-1) borderCellStyle.append("vertical-align:"+(defaultCellProps.vAlign==Align.CENTER ? "middle" : Align.toString(defaultCellProps.vAlign))+";");
            if(defaultCellProps.padding!=null) borderCellStyle.append(defaultCellProps.padding);
            if(defaultCellProps.border!=null) borderCellStyle.append(defaultCellProps.border);

            String idAttrib = defaultCellProps.id!=null ? "id=\"" + defaultCellProps.id + "\" " : "";

            if(mediaList.isEmpty()){
                //There is no media, use inline styles instead of styletags.
                out.append(BR+"<div data-desc=\"CONTAINER CELL\" style=\""+cellStyle+"\">");
                out.append(BR+"<div data-desc=\"BORDER\" style=\""+borderContainerStyle+"\">");
                out.append(BR+"<div data-desc=\"BORDER CELL\" " + idAttrib + "style=\""+borderCellStyle+"\">");
                cell.component.render(context);
                out.append(BR+"</div> <!-- BORDER CELL -->");
                out.append(BR+"</div> <!-- BORDER -->");
                out.append(BR+"</div> <!-- CONTAINER CELL -->");
            }else{
                //We need styletags to define media statements
                String containerCellClassName = "cell"+cellIndex+this.hashCode();       //Generate a random class name for cell
                String borderClassName = "border"+cellIndex+this.hashCode();            //Generate a random class name for border container
                String borderCellClassName = "borderCell"+cellIndex+this.hashCode();    //Generate a random class name for border cell

                out.append(BR+"<style>");

                //Default cell style selector
                out.append(BR+"."+containerCellClassName+"{");
                out.append(cellStyle.toString()+"");
                out.append("}");

                //Default border container style selector
                out.append(BR+"."+borderClassName+"{");
                out.append(borderContainerStyle.toString()+"");
                out.append("}");

                //Default border cell style selector
                out.append(BR+"."+borderCellClassName+"{");
                out.append(borderCellStyle.toString()+"");
                out.append("}");

                //Iterate over the medias in this layout manager for each cell
                for(Media media : mediaList){
                    Props mediaLayouotProps = mediaMap.get(media);
                    BasicLayoutCell.Props mediaCellProps = cell.mediaMap.get(media);

                    if(mediaLayouotProps==null && mediaCellProps==null){
                        //Case 1
                        //No media layout props or media cell props, render no media query for this media
                    }else if(mediaLayouotProps!=null && mediaCellProps==null){
                        //Case 2
                        //We have media layput props but no media cell props
                        out.append(BR+"@media all");
                        if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
                        if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
                        out.append("{");

                        out.append(BR+"."+containerCellClassName+"{");
                        if(mediaLayouotProps.method!=null){
                            if(mediaLayouotProps.method.equals(Method.HORIZONTAL)){
                                //Horizontal layout
                                if(defaultCellProps.display) out.append("display:table-cell;"); else out.append("display:none;");
                            }else{
                                //Vertical layout
                                if(defaultCellProps.display) out.append("display:block;width:100%;"); else out.append("display:none;width:100%;");
                            }
                        }
                        if(mediaLayouotProps.cellRelativeWidth!=-1) out.append("width:"+mediaLayouotProps.cellRelativeWidth+"%;");
                        if(mediaLayouotProps.cellSpacing!=null) out.append("padding:"+mediaLayouotProps.cellSpacing.top+"px "+mediaLayouotProps.cellSpacing.right+"px "+mediaLayouotProps.cellSpacing.bottom+"px "+mediaLayouotProps.cellSpacing.left+"px;");
                        
                        
                        
                        {//TODO: Identical bracket below
                            //Select gap
                            Integer gap = mediaLayouotProps.gap!=null ? mediaLayouotProps.gap : defaultLayoutProps.gap;

                            //Select spacing i priority order, cell level settings override layout level settings
                            Spacing cellSpacing;
                            if(defaultCellProps.spacing!=null){
                                cellSpacing = defaultCellProps.spacing;
                            }else if(mediaLayouotProps.cellSpacing!=null){
                                cellSpacing = mediaLayouotProps.cellSpacing;
                            }else{
                                cellSpacing = defaultLayoutProps.cellSpacing;
                            }

                            Method method = mediaLayouotProps.method!=null ? mediaLayouotProps.method : defaultLayoutProps.method;

                            if(gap!=null || cellSpacing!=null) out.append(calculateSpacing(method,cellIndex,gap,cellSpacing));
                        }//
                        
                        out.append("}");
                        out.append(BR+"}");

                    }else if(mediaLayouotProps==null && mediaCellProps!=null){
                        //Case 3
                        //We have no layput props but we have cell props
                        out.append(BR+"@media all");
                        if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
                        if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
                        out.append("{");

                        out.append(BR+"."+containerCellClassName+"{");
                        if(mediaCellProps.display){
                            if(defaultLayoutProps.method.equals(Method.HORIZONTAL)){
                                //Horizontal layout
                                out.append("display:table-cell;");
                                if(mediaCellProps.width!=-1) out.append("width:"+mediaCellProps.width+"px;");
                                if(mediaCellProps.relativeWidth!=-1) out.append("width:"+mediaCellProps.relativeWidth+"%;");
                            }else{
                                //Vertical layout
                                out.append("display:block;width:100%;");
                            }
                        }else{
                            //Don't display this cell
                            out.append("display:none;");
                        }
/**/                    if(mediaCellProps.spacing!=null) out.append("padding:"+mediaCellProps.spacing.top+"px "+mediaCellProps.spacing.right+"px "+mediaCellProps.spacing.bottom+"px "+mediaCellProps.spacing.left+"px;");
                        out.append("}");

                        //TODO: Identical below
                        out.append(BR+"."+borderCellClassName+"{");
                        if(mediaCellProps.hAlign!=-1) out.append("text-align:"+Align.toString(mediaCellProps.hAlign)+";");
                        if(mediaCellProps.vAlign!=-1) out.append("vertical-align:"+(mediaCellProps.vAlign==Align.CENTER ? "middle" : Align.toString(mediaCellProps.vAlign))+";");
                        if(mediaCellProps.padding!=null) out.append(mediaCellProps.padding.toString());
                        if(mediaCellProps.border!=null) out.append(mediaCellProps.border.toString());
                        if(mediaCellProps.backgroundColor!=null) out.append("background-color:"+mediaCellProps.backgroundColor+";");
                        if(mediaCellProps.backgroundImage!=null) out.append(mediaCellProps.backgroundImage.toString());
                        out.append("}");

                        out.append(BR+"}");
                    }else if(mediaLayouotProps!=null && mediaCellProps!=null){
                        //Case 4
                        //We have both layput props and cell props
                        out.append(BR+"@media all");
                        if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
                        if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
                        out.append("{");

                        out.append(BR+"."+containerCellClassName+"{");
                        if(mediaCellProps.display){
                            //Display this cell
                            if(mediaLayouotProps.method!=null){
                                //There is a media layout method, override default layout method
                                if(mediaLayouotProps.method.equals(Method.HORIZONTAL)){
                                    //Horizontal layout
                                    out.append("display:table-cell;");
                                    if(mediaCellProps.width!=-1) out.append("width:"+mediaCellProps.width+"px;");
                                    if(mediaCellProps.relativeWidth!=-1) out.append("width:"+mediaCellProps.relativeWidth+"%;");
                                }else{
                                    //Vertical layout
                                    out.append("display:block;width:100%;");
                                }
                            }else{
                                //Use the default layout method
                                if(defaultLayoutProps.method.equals(Method.HORIZONTAL)){
                                    //Horizontal layout
                                    out.append("display:table-cell;");
                                    if(mediaCellProps.width!=-1) out.append("width:"+mediaCellProps.width+"px;");
                                    if(mediaCellProps.relativeWidth!=-1) out.append("width:"+mediaCellProps.relativeWidth+"%;");
                                }else{
                                    //Vertical layout
                                    out.append("display:block;width:100%;");
                                }
                            }
                        }else{
                            //Don't display this cell
                            out.append("display:none;");
                        }
                        //
                        if(mediaLayouotProps.cellRelativeWidth!=-1) out.append("width:"+mediaLayouotProps.cellRelativeWidth+"%;");
                        
//                        if(mediaLayouotProps.gap!=null){
//                            //Explicit cell props overrides layout cell props
//                            if(mediaCellProps.spacing!=null){
//                                out.append(calculateSpacing(mediaLayouotProps.method,cellIndex,mediaLayouotProps.gap,mediaCellProps.spacing));
//                            }else if(mediaLayouotProps.cellSpacing!=null){                            
//                                out.append(calculateSpacing(mediaLayouotProps.method,cellIndex,mediaLayouotProps.gap,mediaLayouotProps.cellSpacing));
//                            }
//                            
//                        }
                        
                        {//TODO: Identical bracket above
                            //Select gap
                            Integer gap = mediaLayouotProps.gap!=null ? mediaLayouotProps.gap : defaultLayoutProps.gap;

                            //Select spacing i priority order, cell level settings override layout level settings
                            Spacing cellSpacing;
                            if(defaultCellProps.spacing!=null){
                                cellSpacing = defaultCellProps.spacing;
                            }else if(mediaLayouotProps.cellSpacing!=null){
                                cellSpacing = mediaLayouotProps.cellSpacing;
                            }else{
                                cellSpacing = defaultLayoutProps.cellSpacing;
                            }

                            Method method = mediaLayouotProps.method!=null ? mediaLayouotProps.method : defaultLayoutProps.method;

                            if(gap!=null || cellSpacing!=null) out.append(calculateSpacing(method,cellIndex,gap,cellSpacing));
                        }//
                        
                        out.append("}");

                        //TODO: Identical above
                        out.append(BR+"."+borderCellClassName+"{");
                        if(mediaCellProps.hAlign!=-1) out.append("text-align:"+Align.toString(mediaCellProps.hAlign)+";");
                        if(mediaCellProps.vAlign!=-1) out.append("vertical-align:"+(mediaCellProps.vAlign==Align.CENTER ? "middle" : Align.toString(mediaCellProps.vAlign))+";");
                        if(mediaCellProps.padding!=null) out.append(mediaCellProps.padding.toString());
                        if(mediaCellProps.border!=null) out.append(mediaCellProps.border.toString());
                        if(mediaCellProps.backgroundColor!=null) out.append("background-color:"+mediaCellProps.backgroundColor+";");
                        if(mediaCellProps.backgroundImage!=null) out.append(mediaCellProps.backgroundImage.toString());
                        out.append("}");

                        out.append(BR+"}");
                    }

                }

                out.append(BR+"</style>");
                out.append(BR+"<div data-desc=\"CONTAINER CELL\" class=\""+containerCellClassName+"\">");
                out.append(BR+"<div data-desc=\"BORDER\" class=\""+borderClassName+"\">");
                out.append(BR+"<div data-desc=\"BORDER CELL\" " + idAttrib + "class=\""+borderCellClassName+"\">");
                cell.component.render(context);
                out.append(BR+"</div> <!-- BORDER CELL -->");
                out.append(BR+"</div> <!-- BORDER -->");
                out.append(BR+"</div> <!-- CONTAINER CELL -->");
            }

        }

        out.append(BR+"</div> <!-- CONTAINER -->");    //End container
        if(wrapperStyle!=null) out.append(BR+"</div> <!-- WRAPPER -->");    //End wrapper, if any

    }


    private void overrideDefaultCellProperties(BasicLayoutCell.Props defaultCellProps){
        //Overriding cell properties set at the container level by properties set
        //explicitly at the cell level. Keep properties set at cell leven.
        if(defaultLayoutProps.cellPadding!=null && defaultCellProps.padding==null) defaultCellProps.padding = defaultLayoutProps.cellPadding;
        if(defaultLayoutProps.cellBorder!=null && defaultCellProps.border==null) defaultCellProps.border = defaultLayoutProps.cellBorder;
        if(defaultLayoutProps.cellSpacing!=null && defaultCellProps.spacing==null) defaultCellProps.spacing = defaultLayoutProps.cellSpacing;
        if(defaultLayoutProps.cellRelativeWidth!=-1 && defaultCellProps.relativeWidth==-1) defaultCellProps.relativeWidth = defaultLayoutProps.cellRelativeWidth;
    }

    /**
     * Calculate the total cell spacing and return the padding string. Total spacing depends on
     * the explicit cell spacing and gap, that in tern depends on the layout method.
     * @return 
     */
    private String calculateSpacing(Method method, int cellIndex, Integer gap, Spacing cellSpacing) throws Exception{
        if(method.equals(Method.HORIZONTAL)){
            //Handle horizontal layout
            if(gap!=null && cellSpacing==null){
                //Horizintal gap only
                int halfGap1 = gap/2;
                int halfGap2 = gap/2 + gap%2;
                if(cellIndex==0){
                    //First cell
                    return "padding:"+0+"px "+halfGap1+"px "+0+"px "+0+"px;";
                }else if(cellIndex==cellList.size()-1){
                    //Last cell
                    return "padding:"+0+"px "+0+"px "+0+"px "+halfGap2+"px;";
                }else{
                    //Intermediate cells
                    return "padding:"+0+"px "+halfGap1+"px "+0+"px "+halfGap2+"px;";
                }
            }else if(gap==null && cellSpacing!=null){
                //Cell spacing only
                return "padding:"+cellSpacing.top+"px "+cellSpacing.right+"px "+cellSpacing.bottom+"px "+cellSpacing.left+"px;";
            }else if(gap!=null && cellSpacing!=null){
                //Horizontal gap and and cell spacing combined
                int halfGap1 = gap/2;
                int halfGap2 =gap/2 +gap%2;
                if(cellIndex==0){
                    //First cell
                    return "padding:"+cellSpacing.top+"px "+(cellSpacing.right+halfGap1)+"px "+cellSpacing.bottom+"px "+cellSpacing.left+"px;";
                }else if(cellIndex==cellList.size()-1){
                    //Last cell
                    return "padding:"+cellSpacing.top+"px "+cellSpacing.right+"px "+cellSpacing.bottom+"px "+(cellSpacing.left+halfGap2)+"px;";
                }else{
                    //Intermediate cells
                    return "padding:"+cellSpacing.top+"px "+(cellSpacing.right+halfGap1)+"px "+cellSpacing.bottom+"px "+(cellSpacing.left+halfGap2)+"px;";
                }
            }else{
                throw new Exception("Both gap and spacing is null in horizontal layout.");
            }
        }else{
            //Handle vertical layout
            if(gap!=null && cellSpacing==null){
                //Horizintal gap only
                int halfGap1 = gap/2;
                int halfGap2 = gap/2 + gap%2;
                if(cellIndex==0){
                    //First cell
                    return "padding:"+0+"px "+0+"px "+halfGap2+"px "+0+"px;";
                }else if(cellIndex==cellList.size()-1){
                    //Last cell
                    return "padding:"+halfGap1+"px "+0+"px "+0+"px "+0+"px;";
                }else{
                    //Intermediate cells
                    return "padding:"+halfGap1+"px "+0+"px "+halfGap2+"px "+0+"px;";
                }
            }else if(gap==null && cellSpacing!=null){
                //Cell spacing only
                return "padding:"+cellSpacing.top+"px "+cellSpacing.right+"px "+cellSpacing.bottom+"px "+cellSpacing.left+"px;";
            }else if(gap!=null && cellSpacing!=null){
                //Vertical gap and and cell spacing combined
                int halfGap1 = gap/2;
                int halfGap2 = gap/2 + gap%2;
                if(cellIndex==0){
                    //First cell
                    return "padding:"+cellSpacing.top+"px "+cellSpacing.right+"px "+(cellSpacing.bottom+halfGap2)+"px "+cellSpacing.left+"px;";
                }else if(cellIndex==cellList.size()-1){
                    //Last cell
                    return "padding:"+(cellSpacing.top+halfGap1)+"px "+cellSpacing.right+"px "+cellSpacing.bottom+"px "+cellSpacing.left+"px;";
                }else{
                    //Intermediate cells
                    return "padding:"+(cellSpacing.top+halfGap1)+"px "+cellSpacing.right+"px "+(cellSpacing.bottom+halfGap2)+"px "+cellSpacing.left+"px;";
                }
            }else{
                throw new Exception("Both gap and spacing is null in vertical layout.");
            }
        }
    }
    

}
