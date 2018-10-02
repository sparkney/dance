/*
 *
 * Created on den 1 dec 2016
 *
 * NOTES
 *
 * 161201 There can not be one single flow layout, since boxes and texts have
 * different properties. Boxes have to have display:inline-block, and texts have
 * to have display:inline to flow as expected. We need a BloxFlowLayoute and a
 * TextFlowLayout. (See comment 161201 in Text.) To be able to use make boxes
 * float in the text with float:left and float:right, we also need to use
 * display:inline instead of display:inline-block.
 *
 * TODO
 * 161201 Add a way of floating boxes in the text, float:left and float:right
 * style.
 *
 * CHANGES
 *
 */


package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.Component;
import com.sparkney.dance.core.Context;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Örjan Derelöv
 *
 * This layout manager is used for composing text with different properties and
 * let them flow as the display size changes.
 * 
 * For flowing boxes, use the BoxFlowLayout.
 *
 */
public class TextFlowLayout extends Component{

    protected ArrayList<TextFlowLayoutCell> cellList = new ArrayList();
        
    private class Props{
        private int width = -1;
        private float relativeWidth = -1;
        private Padding padding = null;
        private Color backgroundColor = null;

        //TODO: Maybe these cell props should have their own props-class. Maybe even a subclass of FlowLayoutCell.Props
        private Spacing cellSpacing = null;
        private float cellRelativeWidth = -1;     //Can't be set explicitly, but is calculated from the number of columns.
        private Color cellBackgroundColor = null;
    }

    protected Props defaultContainerProps = new Props();
    protected HashMap<Media, Props> mediaMap = new HashMap<Media, Props>();
    private ArrayList<Media> mediaList = new ArrayList<Media>();            //Unique medias used in this layout and the cells. New medias used in cells will also be added to the this media list. When rendering the cells, the media list is used to iterate over all the medias for each cell.

    // <editor-fold defaultstate="collapsed" desc="All Getters and Setters that we like to hide away">

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

    public TextFlowLayout setWidth(int width){
        this.defaultContainerProps.width = width;
        return this;
    }
        
    public TextFlowLayout setRelativeWidth(int relativeWidth){
        this.defaultContainerProps.relativeWidth = relativeWidth;
        return this;
    }
        
    public TextFlowLayout setNumColumns(int numColumns){
        this.defaultContainerProps.cellRelativeWidth = 100F/numColumns;
        return this;
    }

    public TextFlowLayout setNumColumns(Media media, int numColumns){
        getProps(media).cellRelativeWidth = 100F/numColumns;
        return this;
    }

    public TextFlowLayout setRowSpacing(int rowSpacing){
        if(this.defaultContainerProps.cellSpacing==null){
            this.defaultContainerProps.cellSpacing = new Spacing();
        }
        this.defaultContainerProps.cellSpacing.top = rowSpacing;
        this.defaultContainerProps.cellSpacing.bottom = rowSpacing;
        return this;
    }

    public TextFlowLayout setColSpacing(int colSpacing){
        if(this.defaultContainerProps.cellSpacing==null){
            this.defaultContainerProps.cellSpacing = new Spacing();
        }
        this.defaultContainerProps.cellSpacing.left = colSpacing;
        this.defaultContainerProps.cellSpacing.right = colSpacing;
        return this;
    }

    public TextFlowLayout setBackgroundColor(Color backgroundColor){
        this.defaultContainerProps.backgroundColor = backgroundColor;
        return this;
    }

    public TextFlowLayout setBackgroundColor(Media media, Color backgroundColor){
        getProps(media).backgroundColor = backgroundColor;
        return this;
    }

    public TextFlowLayout setCellBackgroundColor(Color backgroundColor){
        this.defaultContainerProps.cellBackgroundColor = backgroundColor;
        return this;
    }

    public TextFlowLayout setCellBackgroundColor(Media media, Color backgroundColor){
        getProps(media).cellBackgroundColor = backgroundColor;
        return this;
    }

    public TextFlowLayoutCell add(Component component){
        TextFlowLayoutCell cell = new TextFlowLayoutCell(mediaList,component);
        cellList.add(cell);
        return cell;
    }

    // </editor-fold>


    public void render(Context context) throws Exception{
        PrintWriter out = context.getPrintWriter();


        String containerName = generateName(this.hashCode());

        StringBuffer defaultContainerStyle = new StringBuffer();
        defaultContainerStyle.append("display:inline-block;");  //Compnent surfaces are always inline-something, to make them aligned by text-align.
        if(defaultContainerProps.width!=-1) defaultContainerStyle.append("width:"+defaultContainerProps.width+"px;");
        if(defaultContainerProps.relativeWidth!=-1) defaultContainerStyle.append("width:"+defaultContainerProps.relativeWidth+"%;");
        if(defaultContainerProps.backgroundColor!=null) defaultContainerStyle.append("background-color:"+defaultContainerProps.backgroundColor+";");

        out.append(BR+"<style>");
        out.append(BR+"."+containerName+"{");
        out.append(defaultContainerStyle.toString()+"");
        out.append("}");

        for (Map.Entry<Media, Props> mediaMapEntry : mediaMap.entrySet()) {
            Media media = mediaMapEntry.getKey();
            Props mediaContainerProps = mediaMapEntry.getValue();

            out.append(BR+"@media all");
            if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
            if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
            out.append("{");

            out.append(BR+"."+containerName+"{");
            if(mediaContainerProps.width!=-1) out.append("width:"+mediaContainerProps.width+"px;");
            if(mediaContainerProps.relativeWidth!=-1) out.append("width:"+mediaContainerProps.relativeWidth+"%;");
            if(mediaContainerProps.backgroundColor!=null) out.append("background-color:"+mediaContainerProps.backgroundColor+";");
            out.append("}");
            out.append(BR+"}");
        }


        for(int i=0; i<cellList.size(); i++){
            TextFlowLayoutCell cell = cellList.get(i);
            TextFlowLayoutCell.Props defaultCellProps = cell.props;

            //Overriding cell properties set at the container level by properties set
            //explicitly at the cell level. Keep properties set at cell leven.
            if(defaultContainerProps.cellRelativeWidth!=-1 && defaultCellProps.relativeWidth==-1) defaultCellProps.relativeWidth = defaultContainerProps.cellRelativeWidth;
            if(defaultContainerProps.cellSpacing!=null) defaultCellProps.spacing = defaultContainerProps.cellSpacing;   //Spacing can not be set at cell level.



            String cellName = generateName(this.hashCode() + i + 1);

            StringBuilder defaultCellStyle = new StringBuilder();
            defaultCellStyle.append("display:inline;");   //Have to be display:inline to let the text floaw as expected.
            if(defaultCellProps.relativeWidth!=-1) defaultCellStyle.append("width:" + defaultCellProps.relativeWidth +"%;");
            if(defaultCellProps.hAlign!=-1) defaultCellStyle.append("text-align:"+Align.toString(defaultCellProps.hAlign)+";");
            if(defaultCellProps.spacing!=null) defaultCellStyle.append("padding:"+defaultCellProps.spacing.top+"px "+defaultCellProps.spacing.right+"px "+defaultCellProps.spacing.bottom+"px "+defaultCellProps.spacing.left+"px;");
            if(defaultCellProps.backgroundColor!=null) defaultCellStyle.append("background-color:"+defaultCellProps.backgroundColor+";");
            if(defaultCellProps.backgroundImage!=null) defaultCellStyle.append(defaultCellProps.backgroundImage.toString());
            
            out.append(BR+"."+cellName+"{");
            out.append(defaultCellStyle.toString()+"");
            out.append("}");

            for(Media media : mediaList){
                Props mediaContainerProps = mediaMap.get(media);
                TextFlowLayoutCell.Props mediaCellProps = cell.mediaMap.get(media);

                if(mediaContainerProps!=null && mediaCellProps==null){
                    //For this media, we have container props but no cell props. Use cell props
                    //set at container level. Since there are no media cell props, props set at
                    //container level are not overridden by props set at cell level.

                    out.append(BR+"@media all");
                    if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
                    if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
                    out.append("{");

                    out.append(BR+"."+cellName+"{");
                    if(mediaContainerProps.cellRelativeWidth!=-1) out.append("width:"+mediaContainerProps.cellRelativeWidth+"%;");
                    if(mediaContainerProps.cellBackgroundColor!=null) out.append("background-color:"+mediaContainerProps.cellBackgroundColor+";");
                    out.append("}");
                    out.append(BR+"}");

                }else if(mediaContainerProps==null && mediaCellProps!=null){
                    //For this media, we no container props but there are cell props

                    out.append(BR+"@media all");
                    if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
                    if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
                    out.append("{");

                    out.append(BR+"."+cellName+"{");
                    if(mediaCellProps.relativeWidth!=-1) out.append("width:"+mediaCellProps.relativeWidth+"%;");
                    if(mediaCellProps.backgroundColor!=null) out.append("background-color:"+mediaCellProps.backgroundColor+";");
                    out.append("}");
                    out.append(BR+"}");

                }else if(mediaContainerProps!=null && mediaCellProps!=null){
                    //For this media, we have both container props and cell props

                    //Properties set att cell level will override properties set at
                    //container level. I.e keep properties set at cell level.
                    if(mediaContainerProps.cellRelativeWidth!=-1 && mediaCellProps.relativeWidth==-1) mediaCellProps.relativeWidth = mediaContainerProps.cellRelativeWidth;
                    if(mediaContainerProps.cellRelativeWidth!=-1) mediaCellProps.relativeWidth = mediaContainerProps.cellRelativeWidth;

                    out.append(BR+"@media all");
                    if(media.minWidth!=-1) out.append(" and (min-width:"+media.minWidth+"px)");
                    if(media.maxWidth!=-1) out.append(" and (max-width:"+media.maxWidth+"px)");
                    out.append("{");

                    out.append(BR+"."+cellName+"{");
                    if(mediaCellProps.relativeWidth!=-1) out.append("width:"+mediaCellProps.relativeWidth+"%;");
                    if(mediaCellProps.backgroundColor!=null) out.append("background-color:"+mediaCellProps.backgroundColor+";");
                    out.append("}");
                    out.append(BR+"}");

                }else{
                    //For this media, we have no contatiner props or cell props
                    //This case should not occur, since mediaList would be empty
                }

            }

        }

        out.append(BR+"</style>");

        

        out.append(BR+"<div class=\""+containerName+"\">");

        for(int i=0; i<cellList.size(); i++){
            TextFlowLayoutCell cell = cellList.get(i);

            //TODO: cellName = generateName(cell.hashCode());
            String cellName = generateName(this.hashCode() + i + 1);

            out.append("<div class=\""+cellName+"\">");     //Should have been a BR befor the div, but that will break the desired HTML layout.
            cell.component.render(context);
            out.append(BR+"</div>");
        }

        out.append(BR+"</div>");


        
    }

    /**
     * Returns a radix 36 representation of the value, preceeded by an x, since
     * class names can not start with a number.
     */
    public static String generateName(int value){
        return "x" + Long.toString(Math.abs(value), 36);
    }



}
