/*
 * HorizontalLayout.java
 *
 * Created on den 11 juli 2005, 02:24
 *
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.util.ArrayList;
import java.io.PrintWriter;


/**
 *
 * @author Örjan Derelöv
 *
 * Lays out the components in a horizontal row.
 *
 */
public class HorizontalLayout extends Component{

    //### TODO: max-width and min-width does not work for any tables element in
    //### newer versions of Chrome and Safari (WebKis enegine). Solution: Wrap
    //### tables element in inline-block, where max-width and min-width work,
    //### and that can be aligned by text-align. Only wrap when max-width and
    //### min-width is used.

    private boolean HORIZONTAL = true;  //If not horizontal it is vertical.

    private ArrayList cellList = new ArrayList();   //Properties for the container cell that the component is added to
    private Border cellBorder = null; //Borders of all cells in this container
    private int cellBorderRadius = 0;
    private Padding cellPadding = null; //Padding of all cells in this container
    
    private int width = -1;
    private int minWidth = -1;
    private int maxWidth = -1;
    private int height = -1;
    private int relativeWidth = -1;
    private int relativeHeight = -1;
    private Color backgroundColor = null;
    private BackgroundImage backgroundImage = null;
    private int backgroundRepete = -1;
    private int backgroundHAlign = -1;
    private int backgroundVAlign = -1;
    
    private Padding padding = null;
    private Margin margin = null;
    private Border border = null;
    private int borderRadius = 0;
    
    private String comment = null;
    private String explicitStyle = null;
    private String explicitAttribute = null;
    
    private int gap = -1;
    
    public HorizontalLayout setBackgroundImage(BackgroundImage image){
        this.backgroundImage = image;
        return this;
    }
    
    public HorizontalLayout setBackgroundRepete(int repete){
        this.backgroundRepete = repete;
        return this;
    }
    
    public HorizontalLayout setBackgroundAlignment(int horizontalAlignment, int verticalAlignment){
        this.backgroundHAlign = horizontalAlignment;
        this.backgroundVAlign = verticalAlignment;
        return this;
    }

    public HorizontalLayout setGap(int gap){
        this.gap = gap;
        return this;
    }
    
    /**
     * Set borders around all cells in this container.
     */
    public HorizontalLayout setCellBorder(Border cellBorder){
        this.cellBorder = cellBorder;
        return this;
    }

    public HorizontalLayout setCellBorderRadius(int radius){
        cellBorderRadius = radius;
        return this;
    }
    
    /**
     * Set desired width.
     */
    public HorizontalLayout setWidth(int width){
        this.width = width;
        return this;
    }
    
    public HorizontalLayout setMinWidth(int minWidth){
        this.minWidth = minWidth;
        return this;
    }

    public HorizontalLayout setMaxWidth(int maxWidth){
        this.maxWidth = maxWidth;
        return this;
    }

    /**
     * Set desired height.
     */
    public HorizontalLayout setHeight(int height){
        this.height = height;
        return this;
    }
    
    /**
     * Set desired relative width in procentage of the context space.
     */
    public HorizontalLayout setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }
    
    /**
     * Set desired relative height in procentage of the context space.
     */
    public HorizontalLayout setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }
    
    /**
     * Set background color.
     */
    public HorizontalLayout setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    /**
     * Sätter avståndet mellan komponentens innerkant, eller ramens innerkant om
     * det finns någon ram, och cellerna i panelen. Färgen på avståndet är samma
     * som panelens bakgrundsfärg.
     */
    public HorizontalLayout setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }
    
    public HorizontalLayout setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }
    
    public HorizontalLayout setPadding(Padding padding){
        this.padding = padding;
        return this;
    }

    public HorizontalLayout setTopPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.top = width;
        return this;
    }

    public HorizontalLayout setRightPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.right = width;
        return this;
    }

    public HorizontalLayout setBottomPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.bottom = width;
        return this;
    }

    public HorizontalLayout setLeftPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.left = width;
        return this;
    }

    public HorizontalLayout setCellPadding(int width){
        this.cellPadding = new Padding(width);
        return this;
    }

    public HorizontalLayout setCellPadding(int top, int right, int bottom, int left){
        this.cellPadding = new Padding(top,right,bottom,left);
        return this;
    }

    public HorizontalLayout setCellPadding(Padding padding){
        this.cellPadding = padding;
        return this;
    }

    public HorizontalLayout setMargin(int width){
        this.margin = new Margin(width);
        return this;
    }
    
    public HorizontalLayout setMargin(int top, int right, int bottom, int left){
        this.margin = new Margin(top,right,bottom,left);
        return this;
    }
    
    public HorizontalLayout setMargin(Margin margin){
        this.margin = margin;
        return this;
    }
    
    public HorizontalLayout setBorder(Border border){
        this.border = border;
        return this;
    }

    public HorizontalLayout setBorderRadius (int borderRadius){
        this.borderRadius = borderRadius;
        return this;
    }

    public HorizontalLayout setComment(String comment){
        this.comment = comment;
        return this;
    }
    
    public HorizontalLayout addExplicitStyle(String name, String value){
        if(this.explicitStyle==null){
            this.explicitStyle = name + ":" + value + ";";
        }else{
            this.explicitStyle += name + ":" + value + ";";
        }
        return this;
    }

    public HorizontalLayout addExplicitAttribute(String name, String value){
        if(this.explicitAttribute==null){
            this.explicitAttribute = " " + name + "=\"" + value + "\"";
        }else{
            this.explicitAttribute += " " + name + "=\"" + value + "\"";
        }
        return this;
    }

    /**
     * Add a component to the container. Return the cell in the container
     * that the component is added to.
     */
    public HorizontalLayoutCell add(Component component){
        HorizontalLayoutCell cell = new HorizontalLayoutCell(component);
        cellList.add(cell);
        return cell;
    }

    public void render(Context context) throws Exception{

        StringBuilder containerStyle = containerStyle();

        StringBuilder containerAttributes = new StringBuilder();
        if(id!=null) containerAttributes.append(" id='"+id+"'");
        if(containerStyle.length()>0) containerAttributes.append(" style='"+containerStyle+"'");
        if(explicitAttribute!=null) containerAttributes.append(explicitAttribute);
        
        PrintWriter out = context.getPrintWriter();
        
        if(comment!=null) out.append(BR+"<!--" + comment + " -->");
        
        out.append(BR+"<div").append(containerAttributes).append(">");
        
        if(cellList.isEmpty()){
            //Empty container, don't append anything
        }else{
            
            for(int i=0; i<cellList.size(); i++){
                HorizontalLayoutCell cell = (HorizontalLayoutCell)cellList.get(i);
                
                //Set all cell borders to the cellBorder defined in the layout.
                //Cells that have their borders set explicitly are not affected.
                if(cell.border==null && cellBorder!=null) cell.border = cellBorder;

                //Set all cell padding to the cellPadding defined in the layout.
                //Cells that have their padding set explicitly are not affected.
                if(cell.padding==null && cellPadding!=null) cell.padding = cellPadding;

                StringBuilder cellStyle = cellStyle(cell);

                StringBuilder cellAttributes = new StringBuilder();
                if(cellStyle.length()>0) cellAttributes.append(" style='"+cellStyle+"'");
                if(cell.id!=null) cellAttributes.append(" id='"+cell.id+"'");
                if(cell.explicitAttribute!=null) cellAttributes.append(cell.explicitAttribute);
                
                out.append(BR+"<div").append(cellAttributes).append(">");
                
                //Render the added components
                cell.component.render(context);
                
                out.append(BR+"</div>");

                //Add gap
                if(gap!=-1){
                    if(cellList.size()>1){
                        //Handels the more-then-one-cell-case
                        if(i==0){
                            //First cell, add gap after it
                            if(HORIZONTAL){
                                //Horizontal gap
                                out.append(BR+"<div style='display:table-cell;width:"+gap+"px;padding-left:"+gap+"px'></div>");   //We need both width and padding for the gap to be fixed
                            }else{
                                //Vertical gap
                                out.append(BR+"<div style='display:block;height:"+gap+"px;padding-bottom:"+gap+"px'></div>");   //We need both height and padding for the gap to be fixed
                            }
                        }else if(i==cellList.size()-1){
                            //Last cell
                            //Don't add anything after it
                        }else{
                            //Cell between the first and the last cell, att gap after it
                            if(HORIZONTAL){
                                //Horizontal gap
                                out.append(BR+"<div style='display:table-cell;width:"+gap+"px;padding-left:"+gap+"px'></div>");   //We need both width and padding for the gap to be fixed
                            }else{
                                //Vertical gap
                                out.append(BR+"<div style='display:block;height:"+gap+"px;padding-bottom:"+gap+"px'></div>");   //We need both height and padding for the gap to be fixed
                            }
                        }
                    }else{
                        //Handels the one-cell-case
                        //Gap is not applicable
                    }
                }
            }
        }
        
        out.append(BR+"</div>");
        
    }

    private StringBuilder containerStyle(){
        StringBuilder containerStyle = new StringBuilder();
        if(HORIZONTAL){
            //Horizontal
            containerStyle.append("display:inline-table;");
        }else{
            //Vertical
            containerStyle.append("display:block;");
        }
        if(width!=-1) containerStyle.append("width:"+width+"px;");
        if(minWidth!=-1) containerStyle.append("min-width:"+minWidth+"px;");
        if(maxWidth!=-1) containerStyle.append("max-width:"+maxWidth+"px;");
        if(height!=-1) containerStyle.append("height:"+height+"px;");
        if(backgroundColor!=null) containerStyle.append("background-color:"+backgroundColor+";");
        if(backgroundRepete!=-1) containerStyle.append("background-repeat:"+Repete.toString(backgroundRepete)+";");
        if(backgroundHAlign!=-1 || backgroundVAlign!=-1){
            containerStyle.append("background-position:"+Align.toString(backgroundHAlign)+" "+Align.toString(backgroundVAlign)+";");
        }
        if(backgroundImage!=null) containerStyle.append(backgroundImage);
        if(margin!=null) containerStyle.append("margin:"+margin.top+"px "+margin.right+"px "+margin.bottom+"px "+margin.left+"px;");
        if(relativeWidth!=-1) containerStyle.append("width:"+relativeWidth+"%;");
        if(relativeHeight!=-1) containerStyle.append("height:"+relativeHeight+"%;");
        if(padding!=null) containerStyle.append(padding);
        if(border!=null) containerStyle.append(border);
        if(borderRadius!=0) containerStyle.append("border-radius:"+borderRadius+"px;");
        if(explicitStyle!=null) containerStyle.append(explicitStyle);

        return containerStyle;
    }

    private StringBuilder cellStyle(HorizontalLayoutCell cell){

        StringBuilder cellStyle = new StringBuilder();
        if(HORIZONTAL){
            //Horizontal
            cellStyle.append("display:table-cell;");
            cellStyle.append("height:100%;");
        }else{
            //Vertical
            cellStyle.append("display:block;");
        }
        if(cell.hAlign!=-1) cellStyle.append("text-align:"+Align.toString(cell.hAlign)+";");
        if(cell.vAlign!=-1) cellStyle.append("vertical-align:"+(cell.vAlign==Align.CENTER ? "middle" : Align.toString(cell.vAlign))+";");
        if(cell.width!=-1){
            if(cell.relativeWidth){
                cellStyle.append("width:"+cell.width+"%;");
            }else{
                cellStyle.append("width:"+cell.width+"px;");
            }
        }
        if(cell.display!=true) cellStyle.append("display:none;");
        if(cell.backgroundColor!=null) cellStyle.append("background-color:"+cell.backgroundColor+";");
        if(cell.backgroundImage!=null) cellStyle.append(cell.backgroundImage);
        if(cell.backgroundRepete!=-1) cellStyle.append("background-repeat:"+Repete.toString(cell.backgroundRepete)+";");
        if(cell.backgroundHAlign!=-1 || cell.backgroundVAlign!=-1){
            cellStyle.append("background-position:"+Align.toString(cell.backgroundHAlign)+" "+Align.toString(cell.backgroundVAlign)+";");
        }
        if(cell.border!=null) cellStyle.append(cell.border);
        if(cellBorderRadius!=0) cellStyle.append("border-radius:"+cellBorderRadius+"px;");
        if(cell.padding!=null) cellStyle.append(cell.padding);
        if(cell.explicitStyle!=null) cellStyle.append(cell.explicitStyle);

        return cellStyle;
    }



}
