/*
 * GridBagPanel.java
 *
 * Created on den 26 augusti 2005, 23:46
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.io.PrintWriter;

/**
 *
 * @author Örjan Derelöv
 *
 * TODO: För användarvänlighetens skulle kanske det skulle vara bra om
 * första kolumnen och första raden hade index 1,1 istället för 0,0.
 *
 * TODO: Make it possible to set borders on non empty cells only. Currently, all cells
 * have a border, no matter if they are empty or not. eg: Empty cells in a calendar table
 * should not have borders.
 *
 * TODO: User width and height starting at 1,1 even in the exception messages.
 * If components overlap at 1,5, the exception will now show "java.lang.Exception:
 * Components overlap in cell 0,3"
 */


public class GridSpanLayout extends Component{
    
    private CellTable cellTable = new CellTable();
    protected Border cellBorder = null; //Borders of all cells in this container
    
    private int width = -1;
    private int height = -1;
    private int relativeWidth = -1;
    private int relativeHeight = -1;
    private Color backgroundColor = null;
    
    private Padding padding = null;
    private Margin margin = null;
    private Border border = null;     //Borders around this component
    
    private String comment = null;
    
    protected int rowGap = -1;
    protected int columnGap = -1;
    
    /**
     * Set borders around all cells in this container. The cell borders will
     * collapse with each other. (Currently there is no way to have separate
     * borders.)
     */
    public GridSpanLayout setCellBorder(Border cellBorder){
        this.cellBorder = cellBorder;
        return this;
    }
    
    /**
     * Set desired width.
     */
    public GridSpanLayout setWidth(int width){
        this.width = width;
        return this;
    }
    
    /**
     * Set desired height.
     */
    public GridSpanLayout setHeight(int height){
        this.height = height;
        return this;
    }
    
    /**
     * Set desired relative width in procentage of the context space.
     */
    public GridSpanLayout setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }
    
    /**
     * Set desired relative height in procentage of the context space.
     */
    public GridSpanLayout setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }
    
    /**
     * Set background color.
     */
    public GridSpanLayout setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    /**
     * Sätter avståndet mellan komponentens innerkant, eller ramens innerkant om
     * det finns någon ram, och cellerna i panelen. Färgen på avståndet är samma
     * som panelens bakgrundsfärg.
     */
    public GridSpanLayout setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }
    
    public GridSpanLayout setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }
    
    public GridSpanLayout setPadding(Padding padding){
        this.padding = padding;
        return this;
    }
    
    /**
     *  Sätter minsta avståndet mellan komponentens ytterkant, eller ramens ytterkant
     *  om det finns någon ram, och de komponenter som ligger utanför panelen. Färgen
     *  på marginalen är transparant.
     */
    public GridSpanLayout setMargin(int width){
        this.margin = new Margin(width);
        return this;
    }
    
    public GridSpanLayout setMargin(int top, int right, int bottom, int left){
        this.margin = new Margin(top,right,bottom,left);
        return this;
    }
    
    public GridSpanLayout setMargin(Margin margin){
        this.margin = margin;
        return this;
    }
    
    /**
     * Set borders around the container. Use setCellBorder() to set the inner
     * borders of the container.
     */
    public GridSpanLayout setBorder(Border border){
        this.border = border;
        return this;
    }
    
    public GridSpanLayout setComment(String comment){
        this.comment = comment;
        return this;
    }
    
    /**
     * Add a component to the GridBagPanel at specifyed position, width and height.
     * The first cell is positioned at (1,1).
     */
    public GridSpanLayoutCell add(int gridX, int gridY, int spanWidth, int spanHeight, Component component) throws Exception{
        GridSpanLayoutCell cell = new GridSpanLayoutCell(component);
        gridX--;    //Since grid should start with (1,1), not (0,0) as in previous version.
        gridY--;
        cell.gridX = gridX;
        cell.gridY = gridY;
        cell.spanWidth = spanWidth;
        cell.spanHeight = spanHeight;
        
        if(spanWidth>1 || spanHeight>1){
            //Component spans more then one cell, add the component to all cells is span
            for(int xOffset=0; xOffset<spanWidth; xOffset++){
                for(int yOffset=0; yOffset<spanHeight; yOffset++){
                    int x = gridX+xOffset;
                    int y = gridY+yOffset;
                    //If the table position is not empty the components will overlap, then throw an exception
                    if(cellTable.get(x,y)==null){
                        //Table position is empty, add cell
                        cellTable.add(cell, x, y);
                    }else{
                        //Table position is not empty, components overlap, throw exception
                        //BUG: The exeption message probably state the wrong cell, since cells now start at (1,1), not (0,0)
                        throw new Exception("Components overlap in cell " + x + "," + y);
                    }
                }
            }
        }else{
            //Component only spans one cell
            cellTable.add(cell, gridX, gridY);
        }
        
        return cell;
    }
    
    public GridSpanLayout setGap(int gap){
        this.rowGap = gap;
        this.columnGap = gap;
        return this;
    }
    
    public GridSpanLayout setRowGap(int rowGap){
        this.rowGap = rowGap;
        return this;
    }
    
    public GridSpanLayout setColumnGap(int columnGap){
        this.columnGap = columnGap;
        return this;
    }
    
    public void render(Context context) throws Exception{
        StringBuilder tableStyle = new StringBuilder();
        tableStyle.append("border-collapse:collapse;"); //Always collapse cell borders TODO: Optinize, use only when cellborders are used
        if(width!=-1) tableStyle.append("width:"+width+"px;");
        if(height!=-1) tableStyle.append("height:"+height+"px;");
        if(backgroundColor!=null) tableStyle.append("background-color:"+backgroundColor+";");
        if(margin!=null) tableStyle.append("margin:"+margin.top+"px "+margin.right+"px "+margin.bottom+"px "+margin.left+"px;");
        if(relativeWidth!=-1) tableStyle.append("width:"+relativeWidth+"%;");
        if(relativeHeight!=-1) tableStyle.append("height:"+relativeHeight+"%;");
        
        //New implementation of border. 071214
        if(border!=null){
            if(border.topWidth == border.leftWidth && border.leftWidth == border.bottomWidth && border.bottomWidth == border.rightWidth){
                //Widths are equal, use the simple border definition
                tableStyle.append("border:"+border.topWidth+"px ");
                tableStyle.append(border.getStyleAsString()+" ");
                tableStyle.append(border.color+";");
            }else{
                //Widths are not equal, use the more complex border definition
                tableStyle.append("border-top-width:"+border.topWidth+"px;");
                tableStyle.append("border-left-width:"+border.leftWidth+"px;");
                tableStyle.append("border-bottom-width:"+border.bottomWidth+"px;");
                tableStyle.append("border-right-width:"+border.rightWidth+"px;");
                tableStyle.append("border-style:"+border.getStyleAsString()+";");
                tableStyle.append("border-color:"+border.color+";");
            }
        }
        
        StringBuilder tableParms = new StringBuilder();
        if(id!=null) tableParms.append(" id='"+id+"'");
        if(tableStyle.length()>0) tableParms.append(" style='"+tableStyle+"'");
        tableParms.append(" cellpadding='0'");
        tableParms.append(" cellspacing='0'");
        
        PrintWriter out = context.getPrintWriter();
        
        if(comment!=null) out.append("<!--" + comment + " -->");
        
        out.append("<table").append(tableParms).append(">"+BR+"");
        
        if(cellTable.size()==0){
            //Empty container, append empty cell
            out.append("<tr>"+BR+"<td></td>"+BR+"</tr>"+BR+"");
        }else{
            //When cellBorders are used, the gap is distributed on each side of the border. DEBUG!
            int underGap = rowGap/2;                //Gap under the cell border
            int overGap = underGap + rowGap%2;      //Gap above the cell border
            int rightGap = columnGap/2;             //Gap to the right of the cell border
            int leftGap = rightGap + columnGap%2;   //Gap to the left of the cell border
            
            //Calculate number of rows and columns
            int cols = cellTable.maxX+1;
            int rows = cellTable.maxY+1;
            
            for(int rowIndex=0; rowIndex<rows; rowIndex++){
                out.append("<tr>"+BR+"");
                for(int colIndex=0; colIndex<cols; colIndex++){
                    GridSpanLayoutCell cell = (GridSpanLayoutCell)cellTable.get(colIndex,rowIndex);
                    
                    //If cell is missing, create a cell with an empty element at position colIndex,rowIndex.
                    if(cell==null){
                        cell = new GridSpanLayoutCell(new Space());
                        cell.gridX = colIndex;
                        cell.gridY = rowIndex;
                    }
                    
                    //Don't render the td-tag if the cell is already rendered.
                    //(Component spans over more the one cell.)
                    if(!cell.rendered){
                        
                        //Mark cell as rendered
                        cell.rendered = true;
                        
                        //Set all cell borders to the cellBorder defined in the panel.
                        //Cells that have their borders set explicitly are not affected.
                        if(cell.border==null && cellBorder!=null) cell.border = cellBorder;
                        
                        StringBuilder tdStyle = new StringBuilder();
                        
                        if(cell.backgroundColor!=null) tdStyle.append("background-color:"+cell.backgroundColor+";");
                        if(cell.backgroundImage!=null) tdStyle.append("background-image: url("+cell.backgroundImage.getUrl()+");");
                        
                        if(cell.border!=null){
                            if(cell.border.topWidth == cell.border.leftWidth && cell.border.leftWidth == cell.border.bottomWidth && cell.border.bottomWidth == cell.border.rightWidth){
                                //Widths are equal, use the simpler border definition
                                tdStyle.append("border:"+cell.border.topWidth+"px ");
                                tdStyle.append(cell.border.getStyleAsString()+" ");
                                tdStyle.append(cell.border.color+";");
                            }else{
                                //Widths are not equal, use the more complex border definition
                                tdStyle.append("border-top-width:"+cell.border.topWidth+"px;");
                                tdStyle.append("border-left-width:"+cell.border.leftWidth+"px;");
                                tdStyle.append("border-bottom-width:"+cell.border.bottomWidth+"px;");
                                tdStyle.append("border-right-width:"+cell.border.rightWidth+"px;");
                                tdStyle.append("border-style:"+cell.border.getStyleAsString()+";");
                                tdStyle.append("border-color:"+cell.border.color+";");
                            }
                        }
                        
                        //Calculate inner padding from table padding, cell padding, rowGap and columnGap
                        if(padding!=null || cell.padding!=null || rowGap!=-1 || columnGap!=-1){
                            int topPadding = 0;
                            int leftPadding = 0;
                            int bottomPadding = 0;
                            int rightPadding = 0;
                            
                            //Add cell padding
                            if(cell.padding!=null){
                                topPadding += cell.padding.top;
                                leftPadding += cell.padding.left;
                                bottomPadding += cell.padding.bottom;
                                rightPadding += cell.padding.right;
                            }
                            
                            //TODO: Optimize, there may be a simpler way to allt rowGaps
                            //Add row gap padding
                            if(rowGap!=-1){
                                if(rows>1){
                                    //Handels the more-then-one-row-case
                                    if(rowIndex==0){
                                        //Topmost row
                                        if(cell.spanHeight == rows){
                                            //If the row spans all the way to the bottom of the component,
                                            //it is also the bottommost row, and row gap is not applicable.
                                        }else{
                                            bottomPadding += overGap;
                                        }
                                    }else if(rowIndex==rows-1){
                                        //Bottommost row
                                        topPadding += underGap;
                                    }else{
                                        //All rows except top and bottom
                                        if(rowIndex + cell.spanHeight == rows){
                                            //If the row spans all the way to the bottom of the component,
                                            //it is also the bottommost row, and row gap is not applicable.
                                        }else{
                                            bottomPadding += overGap;
                                        }
                                        topPadding += underGap;
                                    }
                                }else{
                                    //Handels the one-row-case
                                    //Row gap is not applicable
                                }
                            }
                            
                            //TODO: Optimize, there may be a simpler way to allt columnGaps
                            //Add column gap padding
                            if(columnGap!=-1){
                                if(cols>1){
                                    //Handels the more-then-one-column-case
                                    if(colIndex==0){
                                        //Leftmost column
                                        if(cell.spanWidth == cols){
                                            //If the column spans all the way to the right side of the component,
                                            //it is also the rightmost column, and column gap is not applicable.
                                        }else{
                                            rightPadding += leftGap;
                                        }
                                    }else if(colIndex==cols-1){
                                        //Rightmost column
                                        leftPadding += rightGap;
                                    }else{
                                        //All columns except left and right
                                        if(colIndex + cell.spanWidth == cols){
                                            //If the column spans all the way to the right side of the component,
                                            //it is also the rightmost column, and column gap is not applicable.
                                        }else{
                                            rightPadding += leftGap;
                                        }
                                        leftPadding += rightGap;
                                    }
                                }else{
                                    //Handels the one-column-case
                                    //Column gap is not applicable
                                }
                            }
                            
                            //Add panel padding
                            if(padding!=null){
                                if(cols==1 && rows==1){
                                    //Handels the one-cell-case
                                    topPadding += padding.top;
                                    leftPadding += padding.left;
                                    bottomPadding += padding.bottom;
                                    rightPadding += padding.right;
                                }else if(cols==1 && rows>1){
                                    //Handels the one-column-case
                                    if(rowIndex==0){
                                        //Top cell
                                        topPadding += padding.top;
                                        leftPadding += padding.left;
                                        rightPadding += padding.right;
                                    }else if(rowIndex==rows-1){
                                        //Bottom cell
                                        leftPadding += padding.left;
                                        bottomPadding += padding.bottom;
                                        rightPadding += padding.right;
                                    }else{
                                        //Cells between the top and bottom cell
                                        leftPadding += padding.left;
                                        rightPadding += padding.right;
                                    }
                                }else if(cols>1 && rows==1){
                                    //Handels the one-row-case
                                    if(colIndex==0){
                                        //Left cell
                                        topPadding += padding.top;
                                        leftPadding += padding.left;
                                        bottomPadding += padding.bottom;
                                    }else if(colIndex==cols-1){
                                        //Right cell
                                        topPadding += padding.top;
                                        bottomPadding += padding.bottom;
                                        rightPadding += padding.right;
                                    }else{
                                        //Cells between the left and right cell
                                        topPadding += padding.top;
                                        bottomPadding += padding.bottom;
                                    }
                                }else{
                                    //Handels the more-then-one-rows-and-columns-case
                                    if(colIndex==0 && rowIndex==0){
                                        //Top left cell
                                        topPadding += padding.top;
                                        leftPadding += padding.left;
                                    }else if(colIndex==cols-1 && rowIndex==0){
                                        //Top right cell
                                        topPadding += padding.top;
                                        rightPadding += padding.right;
                                    }else if(colIndex==cols-1 && rowIndex==rows-1){
                                        //Bottom right cell
                                        bottomPadding += padding.bottom;
                                        rightPadding += padding.right;
                                    }else if(colIndex==0 && rowIndex==rows-1){
                                        //Bottom left cell
                                        bottomPadding += padding.bottom;
                                        leftPadding += padding.left;
                                    }else if(rowIndex==0){
                                        //Top row, except left and right cells
                                        topPadding += padding.top;
                                    }else if(rowIndex==rows-1){
                                        //Bottom row, except left and right cells
                                        bottomPadding += padding.bottom;
                                    }else if(colIndex==0){
                                        //Left row, except top and bottom cells
                                        leftPadding += padding.left;
                                    }else if(colIndex==cols-1){
                                        //Right row, except top and bottom cells
                                        rightPadding += padding.right;
                                    }
                                }
                            }
                            
                            tdStyle.append("padding:"+topPadding+"px "+rightPadding+"px "+bottomPadding+"px "+leftPadding+"px;");
                        }
                        
                        StringBuilder tdParms = new StringBuilder();
                        if(tdStyle.length()>0) tdParms.append(" style='"+tdStyle+"'");
                        if(cell.hAlign!=-1) tdParms.append(" align='"+Align.toString(cell.hAlign)+"'");
                        if(cell.vAlign!=-1) tdParms.append(" valign='"+Align.toString(cell.vAlign)+"'");
                        if(cell.spanWidth>1) tdParms.append(" colspan='"+cell.spanWidth+"'");
                        if(cell.spanHeight>1) tdParms.append(" rowspan='"+cell.spanHeight+"'");
                        
                        out.append("<td").append(tdParms).append(">");
                        
                        //Render the added components
                        cell.component.render(context);
                        
                        out.append("</td>"+BR);
                    }
                    
                }
                
                out.append("</tr>"+BR);
            }
        }
        
        out.append("</table>");
        
    }
    
    
    
    class CellTable{
        java.util.Hashtable table = new java.util.Hashtable();
        int maxX = -1;
        int maxY = -1;
        
        void add(GridSpanLayoutCell cell, int x, int y){
            table.put("" + x + ":" + y, cell);
            if(cell.gridX+cell.spanWidth-1>maxX){
                maxX=cell.gridX+cell.spanWidth-1;
            }
            if(cell.gridY+cell.spanHeight-1>maxY){
                maxY=cell.gridY+cell.spanHeight-1;
            }
        }
        
        GridSpanLayoutCell get(int x, int y){
            return (GridSpanLayoutCell)table.get("" + x + ":" + y);
        }
        
        int size(){
            return table.size();
        }
    }
    
    
    
    
}
