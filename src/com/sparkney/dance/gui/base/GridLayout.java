/*
 * GridPanel.java
 *
 * Created on den 23 augusti 2005, 01:29
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.*;
import java.util.ArrayList;
import java.io.PrintWriter;

/**
 *
 * @author Örjan Derelöv
 *
 * TODO: Make it possible to set borders on non empty cells only. Currently, all cells
 * have a border, no matter if they are empty or not. eg: Empty cells in a calendar table
 * should not have borders.
 */
public class GridLayout extends Component{
    
    public static final int ROW_LIMIT = 1;      //Limit with respect to the number of rows
    public static final int COLUMN_LIMIT = 2;   //Limit with respect to the number of columns
    
    private int limitType = COLUMN_LIMIT;   //Limit the number of columns by default
    private int limit = 1;                  //Limit to one column by default, i.e a vertical layout by default
    
    protected int rowGap = -1;
    protected int columnGap = -1;
    
    private StringBuilder tableStyle = new StringBuilder();
    protected ArrayList cellList = new ArrayList();   //Properties for the container cell that the component is added to
    protected Border cellBorder = null; //Borders of all cells in this container
    private Padding cellPadding = null; //Padding of all cells in this container
    
    private int width = -1;
    private int height = -1;
    private int relativeWidth = -1;
    private int relativeHeight = -1;
    private Color backgroundColor = null;
    private Font font = null;
    
    private Color oddRowColor = null;
    private Color evenRowColor = null;
    
    private Padding padding = null;
    private Margin margin = null;
    private Border border = null;     //Borders around this component
    
    private int cellHAlign = -1;
    private int cellVAlign = -1;
    
    private String comment = null;
        
    /*
     * Create a GridPanel that is limited in either number of rows or number of
     * columns. To use a layout with three columns and any number of rows, set
     * limitType COLUMN_LIMIT and limit to 3.
     */
    public GridLayout(int limitType, int limit){
        this.limitType = limitType;
        this.limit = limit;
    }
    
    /**
     * Set borders around all cells in this container. The cell borders will
     * collapse with each other. (Currently there is no way to have separate
     * borders.)
     */
    public GridLayout setCellBorder(Border cellBorder){
        this.cellBorder = cellBorder;
        return this;
    }
    
    /**
     * Set desired width.
     */
    public GridLayout setWidth(int width){
        this.width = width;
        return this;
    }
    
    /**
     * Set desired height.
     */
    public GridLayout setHeight(int height){
        this.height = height;
        return this;
    }
    
    /**
     * Set desired relative width in procentage of the context space.
     */
    public GridLayout setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }
    
    /**
     * Set desired relative height in procentage of the context space.
     */
    public GridLayout setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }
    
    /**
     * Set background color.
     */
    public GridLayout setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    /**
     * Set color of even rows. First row is odd.
     */
    public GridLayout setOddRowBgColor(Color oddRowColor){
        this.oddRowColor = oddRowColor;
        return this;
    }

    /**
     * Set color of even rows. First row is odd.
     */
    public GridLayout setEvenRowBgColor(Color evenRowColor){
        this.evenRowColor = evenRowColor;
        return this;
    }
    
    /**
     * @deprecated Always use setFont(Font)
     */
    public GridLayout setFontFamily(String fontFamily){
        if(font==null) font = new Font();
        this.font.family = fontFamily;
        return this;
    }
    
    /**
     * @deprecated Always use setFont(Font)
     */
    public GridLayout setFontSize(int fontSize){
        if(font==null) font = new Font();
        this.font.size = fontSize;
        return this;
    }
    
    /**
     * @deprecated Always use setFont(Font)
     */
    public GridLayout setColor(Color color){
        if(font==null) font = new Font();
        this.font.color = color;
        return this;
    }
    
    /**
     * @deprecated A container can not have a font, it doesn't make sense.
     */
    public GridLayout setFont(Font font){
        this.font = font;
        return this;
    }
    
    /**
     * Sätter avståndet mellan komponentens innerkant, eller ramens innerkant om
     * det finns någon ram, och cellerna i panelen. Färgen på avståndet är samma
     * som panelens bakgrundsfärg.
     */
    public GridLayout setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }
    
    public GridLayout setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }
    
    public GridLayout setPadding(Padding padding){
        this.padding = padding;
        return this;
    }
    
    public GridLayout setTopPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.top = width;
        return this;
    }

    public GridLayout setRightPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.right = width;
        return this;
    }

    public GridLayout setBottomPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.bottom = width;
        return this;
    }

    public GridLayout setLeftPadding(int width){
        if(this.padding==null) this.padding = new Padding();
        this.padding.left = width;
        return this;
    }

    public GridLayout setCellPadding(int width){
        this.cellPadding = new Padding(width);
        return this;
    }

    public GridLayout setCellPadding(int top, int right, int bottom, int left){
        this.cellPadding = new Padding(top,right,bottom,left);
        return this;
    }

    public GridLayout setCellPadding(Padding padding){
        this.cellPadding = padding;
        return this;
    }

    /**
     *  Sätter minsta avståndet mellan komponentens ytterkant, eller ramens ytterkant
     *  om det finns någon ram, och de komponenter som ligger utanför panelen. Färgen
     *  på marginalen är transparant.
     */
    public GridLayout setMargin(int width){
        this.margin = new Margin(width);
        return this;
    }
    
    public GridLayout setMargin(int top, int right, int bottom, int left){
        this.margin = new Margin(top,right,bottom,left);
        return this;
    }
    
    public GridLayout setMargin(Margin margin){
        this.margin = margin;
        return this;
    }
    
    /**
     * Set borders around the container. Use setCellBorder() to set the inner
     * borders of the container.
     */
    public GridLayout setBorder(Border border){
        this.border = border;
        return this;
    }
    
    public GridLayout setCellHAlign(int cellHorizontalAlignment){
        this.cellHAlign = cellHorizontalAlignment;
        return this;
    }
    
    public GridLayout setCellVAlign(int cellVerticalAlignment){
        this.cellVAlign = cellVerticalAlignment;
        return this;
    }
        
    public GridLayout setComment(String comment){
        this.comment = comment;
        return this;
    }
    
    /**
     * Sätter önskat avståndet mellan cellerna i panelen, men inte avståndet mellan
     * cellerna och panelens kanter. Bakgrundsfärgen är samma som panelens bakgrundsfärg.
     * När cellBorders används distribueras gap-avståndet så att hälften av avståndet
     * hamnar på var sin sida av bordern mellan cellerna. Då gap är ett uppda tal blir avståndet på
     * vänster sida om ramen alltid en pixel större än avståndet på höger sida. För att
     * så samma avstånd på båda sidor bör ett jämt tal användas. Se också setRowGap() och setColumnGap()
     */
    public GridLayout setGap(int gap){
        this.rowGap = gap;
        this.columnGap = gap;
        return this;
    }
    
    /**
     * Se setGap()
     */
    public GridLayout setRowGap(int rowGap){
        this.rowGap = rowGap;
        return this;
    }
    
    /**
     * Se setGap()
     */
    public GridLayout setColumnGap(int columnGap){
        this.columnGap = columnGap;
        return this;
    }
    
    /**
     * Add a component to the container. Return the cell in the container that the
     * component is added to.
     */
    public GridLayoutCell add(Component component){
        GridLayoutCell cell = new GridLayoutCell(component);
        cellList.add(cell);
        return cell;
    }
    
    public void render(Context context) throws Exception{
        tableStyle.append("border-collapse:collapse;"); //Always collapse cell borders TODO: Optinize, use only when cellborders are used
        if(font!=null) tableStyle.append(font);
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
                tableStyle.append("border-left-width  :"+border.leftWidth+"px;");
                tableStyle.append("border-bottom-width:"+border.bottomWidth+"px;");
                tableStyle.append("border-right-width:"+border.rightWidth+"px;");
                tableStyle.append("border-style:"+border.getStyleAsString()+";");
                tableStyle.append("border-color:"+border.color+";");
            }
        }
        
        StringBuilder tableParms = new StringBuilder();
        if(id!=null) tableParms.append(" id='"+id+"'");
        if(tableStyle.length()>0) tableParms.append(" style='"+tableStyle+"'");
        //TODO: The attributes cellpadding, cellspacing, valign and align is not valid in HTML5
        tableParms.append(" cellpadding='0'");
        tableParms.append(" cellspacing='0'");
        
        PrintWriter out = context.getPrintWriter();
        
        if(comment!=null) out.append("<!--" + comment + " -->");
        
        out.append("<table").append(tableParms).append(">"+BR+"");
        
        if(cellList.size()==0){
            //Empty container, append empty cell
            out.append("<tr>"+BR+"<td></td>"+BR+"</tr>"+BR+"");
        }else{
            //When cellBorders are used, the gap is distributed on each side of the border.
            int underGap = rowGap/2;                //Gap under the cell border
            int overGap = underGap + rowGap%2;      //Gap above the cell border
            int rightGap = columnGap/2;             //Gap to the right of the cell border
            int leftGap = rightGap + columnGap%2;   //Gap to the left of the cell border
            
            //Calculate number of rows and columns
            int rows = 0;
            int cols = 0;
            int comps = cellList.size();                    //Number of components
            int trueLimit = limit>comps ? comps : limit;    //Number of rows or column can not be greater the the number of components
            if(limitType==COLUMN_LIMIT){
                rows = comps/trueLimit + (comps%trueLimit==0 ? 0 : 1);
                cols = trueLimit;
            }else{
                rows = trueLimit;
                cols = comps/trueLimit + (comps%trueLimit==0 ? 0 : 1);
            }
            
            
            int cellIndex = 0;
            for(int rowIndex=0; rowIndex<rows; rowIndex++){
                out.append("<tr>"+BR+"");
                for(int colIndex=0; colIndex<cols; colIndex++){
                    GridLayoutCell cell;
                    if(cellIndex<comps){
                        //Get container components
                        cell = (GridLayoutCell)cellList.get(cellIndex++);
                    }else{
                        //Get empty component to fill out extra cells in the table
                        cell = new GridLayoutCell(new Space());
                    }
                                        
                    //Set all cell borders to the cellBorder defined in the panel.
                    //Cells that have their borders set explicitly are not affected.
                    if(cell.border==null && cellBorder!=null) cell.border = cellBorder;
                    
                    //Set all cell padding to the cellPadding defined in the layout.
                    //Cells that have their padding set explicitly are not affected.
                    if(cell.padding==null && cellPadding!=null) cell.padding = cellPadding;
                    
                    //If odd row, set all cells' background color to the oddRowColor defined in the layout.
                    //Cells that have their background color set explicitly are not affected.
                    if(oddRowColor!=null){
                        if(rowIndex%2==0 && cell.backgroundColor==null && oddRowColor!=null) cell.backgroundColor = oddRowColor;
                    }
                    
                    //If even row, set all cells' background color to the oddRowColor defined in the layout.
                    //Cells that have their background color set explicitly are not affected.
                    if(evenRowColor!=null){
                        if(rowIndex%2!=0 && cell.backgroundColor==null && evenRowColor!=null) cell.backgroundColor = evenRowColor;
                    }
                    
                    StringBuilder tdStyle = new StringBuilder();
                    
                    if(cell.backgroundColor!=null) tdStyle.append("background-color:"+cell.backgroundColor+";");
                    
                    if(cell.border!=null) tdStyle.append(cell.border);
                    
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
                        
                        //Add row gap padding
                        if(rowGap!=-1){
                            if(rows>1){
                                //Handels the more-then-one-row-case
                                if(rowIndex==0){
                                    //Top row
                                    bottomPadding += overGap;
                                }else if(rowIndex==rows-1){
                                    //Bottom row
                                    topPadding += underGap;
                                }else{
                                    //All rows except top and bottom
                                    topPadding += underGap;
                                    bottomPadding += overGap;
                                }
                            }else{
                                //Handels the one-row-case
                                //Row gap is not applicable
                            }
                        }
                        
                        //Add column gap padding
                        if(columnGap!=-1){
                            if(cols>1){
                                //Handels the more-then-one-column-case
                                if(colIndex==0){
                                    //Left column
                                    rightPadding += leftGap;
                                }else if(colIndex==cols-1){
                                    //Right column
                                    leftPadding += rightGap;
                                }else{
                                    //All columns except left and right
                                    leftPadding += rightGap;
                                    rightPadding += leftGap;
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

                    if(cell.hAlign!=-1){
                        tdStyle.append("text-align:"+Align.toString(cell.hAlign)+";");
                    }else if(cellHAlign!=-1){
                        tdStyle.append("text-align:"+Align.toString(cellHAlign)+";");
                    }

                    if(cell.vAlign!=-1){
                        tdStyle.append("vertical-align:"+(cell.vAlign==Align.CENTER ? "middle" : Align.toString(cell.vAlign))+";");
                    }else if(cellVAlign!=-1){
                        tdStyle.append("vertical-align:"+(cellVAlign==Align.CENTER ? "middle" : Align.toString(cellVAlign))+";");
                    }

                    StringBuilder tdParms = new StringBuilder();
                    if(tdStyle.length()>0) tdParms.append(" style='"+tdStyle+"'");
                    if(cell.id!=null) tdParms.append(" id='"+cell.id+"'");

//                    //TODO: The attributes cellpadding, cellspacing, valign and align is not valid in HTML5
//                    if(cell.hAlign!=-1){
//                        tdParms.append(" align='"+Align.toString(cell.hAlign)+"'");
//                    }else if(cellHorizontalAlignment!=-1){
//                        tdParms.append(" align='"+Align.toString(cellHorizontalAlignment)+"'");
//                    }

//                    //TODO: The attributes cellpadding, cellspacing, valign and align is not valid in HTML5
//                    if(cell.vAlign!=-1){
//                        tdParms.append(" valign='"+Align.toString(cell.vAlign)+"'");
//                    }else if(cellVerticalAlignment!=-1){
//                        tdParms.append(" valign='"+Align.toString(cellVerticalAlignment)+"'");
//                    }
                    
                    out.append("<td").append(tdParms).append(">");

                    //Render the added components
                    cell.component.render(context);

                    out.append("</td>"+BR);
                    
                }
                
                out.append("</tr>"+BR);
            }
        }
        
        out.append("</table>");

    }
    
}
