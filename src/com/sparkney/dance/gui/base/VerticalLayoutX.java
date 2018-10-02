/*
 * VerticalPanel.java
 *
 * Created on den 11 juli 2005, 02:24
 *
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
 * Lays out the components in a vertical row.
 *
 */
public class VerticalLayoutX extends Component {
    
    protected ArrayList cellList = new ArrayList();   //Properties for the container cell that the component is added to
    protected Border cellBorder = null; //Borders of all cells in this container
    
    private int width = -1;
    private int height = -1;
    private int relativeWidth = -1;
    private int relativeHeight = -1;
    private Color backgroundColor = null;
    private Image backgroundImage = null;
    private int backgroundRepete = -1;
    private int backgroundHAlign = -1;
    private int backgroundVAlign = -1;
    private Font font = null;
    
    private Padding padding = null;
    private Margin margin = null;
    private Border border = null;     //Borders around this component

//    private Object onLoad = null;
    
    private boolean visible = true;
    private String comment = null;
    private String explicitStyle = null;
    private String explicitAttribute = null;
    
    protected int gap = -1;
    
    
    public VerticalLayoutX setBackgroundImage(Image image){
        this.backgroundImage = image;
        return this;
    }
    
    public VerticalLayoutX setBackgroundRepete(int repete){
        this.backgroundRepete = repete;
        return this;
    }
    
    public VerticalLayoutX setBackgroundAlignment(int horizontalAlignment, int verticalAlignment){
        this.backgroundHAlign = horizontalAlignment;
        this.backgroundVAlign = verticalAlignment;
        return this;
    }
    
    
    /**
     * Sätter önskat avståndet mellan cellerna i panelen, men inte avståndet mellan
     * cellerna och panelens kanter. Bakgrundsfärgen på avståndet är samma som panelens
     * bakgrundsfärg.
     * När cellBorders används distribueras gap-avståndet så att hälften av avståndet
     * hamnar på var sin sida av ramen mellan cellerna. Då gap är ett udda tal blir
     * avståndet ovanför ramen alltid en pixel större än avståndet under ramen. För att
     * få samma avstånd på båda sidor användas ett jämnt tal.
     */
    public VerticalLayoutX setGap(int gap){
        this.gap = gap;
        return this;
    }
    
    /**
     * Set borders around all cells in this container. The cell borders will
     * collapse with each other. (Currently there is no way to have separate
     * borders.)
     */
    public VerticalLayoutX setCellBorder(Border cellBorder){
        this.cellBorder = cellBorder;
        return this;
    }
    
    /**
     * Set desired width.
     */
    public VerticalLayoutX setWidth(int width){
        this.width = width;
        return this;
    }
    
    /**
     * Set desired height.
     */
    public VerticalLayoutX setHeight(int height){
        this.height = height;
        return this;
    }
    
    /**
     * Set desired relative width in procentage of the context space.
     */
    public VerticalLayoutX setRelativeWidth(int relativeWidth){
        this.relativeWidth = relativeWidth;
        return this;
    }
    
    /**
     * Set desired relative height in procentage of the context space.
     */
    public VerticalLayoutX setRelativeHeight(int relativeHeight){
        this.relativeHeight = relativeHeight;
        return this;
    }
    
    /**
     * Set background color.
     */
    public VerticalLayoutX setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    public VerticalLayoutX setFont(Font font){
        this.font = font;
        return this;
    }
        
    /**
     * Sätter avståndet mellan komponentens innerkant, eller ramens innerkant om
     * det finns någon ram, och cellerna i panelen. Färgen på avståndet är samma
     * som panelens bakgrundsfärg.
     */
    public VerticalLayoutX setPadding(int width){
        this.padding = new Padding(width);
        return this;
    }
    
    public VerticalLayoutX setPadding(int top, int right, int bottom, int left){
        this.padding = new Padding(top,right,bottom,left);
        return this;
    }
    
    public VerticalLayoutX setPadding(Padding padding){
        this.padding = padding;
        return this;
    }
    
    /**
     *  Sätter minsta avståndet mellan komponentens ytterkant, eller ramens ytterkant
     *  om det finns någon ram, och de komponenter som ligger utanför panelen. Färgen
     *  på marginalen är transparant.
     */
    public VerticalLayoutX setMargin(int width){
        this.margin = new Margin(width);
        return this;
    }
    
    public VerticalLayoutX setMargin(int top, int right, int bottom, int left){
        this.margin = new Margin(top,right,bottom,left);
        return this;
    }
    
    public VerticalLayoutX setMargin(Margin margin){
        this.margin = margin;
        return this;
    }
    
    /**
     * Set borders around the container. Use setCellBorder() to set the inner
     * borders of the container.
     */
    public VerticalLayoutX setBorder(Border border){
        this.border = border;
        return this;
    }

//    public VerticalLayout setOnLoad(Object onLoad){
//        this.onLoad = onLoad;
//        return this;
//    }


    /**
     * @deprecated No known usage. Never use. Should be replaced by setDisplay(boolean). Visibility should work like CSS visibility, ie if hidden, just leave the area empty, not remove it.
     */
    public VerticalLayoutX setVisible(boolean visible){
        this.visible = visible;
        return this;
    }

    public VerticalLayoutX setComment(String comment){
        this.comment = comment;
        return this;
    }
    
    /**
     * Add explicit style. Should only be used when nothing else works.
     */
    public VerticalLayoutX addExplicitStyle(String name, String value){
        if(this.explicitStyle==null){
            this.explicitStyle = name + ":" + value + ";";
        }else{
            this.explicitStyle += name + ":" + value + ";";
        }
        return this;
    }

    public VerticalLayoutX addExplicitAttribute(String name, String value){
        if(this.explicitAttribute==null){
            this.explicitAttribute = " " + name + "=\"" + value + "\"";
        }else{
            this.explicitAttribute += " " + name + "=\"" + value + "\"";
        }
        return this;
    }

    /**
     * Add a component to the container. Return the cell in the container that the
     * component is added to.
     */
    public VerticalLayoutCellX add(Component component){
        VerticalLayoutCellX cell = new VerticalLayoutCellX(component);
        cellList.add(cell);
        return cell;
    }
    
    /**
     * Render the container and its componenets.
     */
    public void render(Context context) throws Exception{
        StringBuilder tableStyle = new StringBuilder();
        tableStyle.append("border-collapse:collapse;"); //Always collapse cell borders TODO: Optinize, use only when cellborders are used
        if(width!=-1) tableStyle.append("width:"+width+"px;");
        if(height!=-1) tableStyle.append("height:"+height+"px;");
        if(backgroundColor!=null) tableStyle.append("background-color:"+backgroundColor+";");
        if(font!=null) tableStyle.append(font);
        if(backgroundImage!=null) tableStyle.append("background-image: url("+backgroundImage.getUrl()+");");
        if(backgroundRepete!=-1) tableStyle.append("background-repeat:"+Repete.toString(backgroundRepete)+";");
        if(backgroundHAlign!=-1 || backgroundVAlign!=-1){
            tableStyle.append("background-position:"+Align.toString(backgroundHAlign)+" "+Align.toString(backgroundVAlign)+";");
        }
        if(margin!=null) tableStyle.append("margin:"+margin.top+"px "+margin.right+"px "+margin.bottom+"px "+margin.left+"px;");
        if(relativeWidth!=-1) tableStyle.append("width:"+relativeWidth+"%;");
        if(relativeHeight!=-1) tableStyle.append("height:"+relativeHeight+"%;");
//        if(floatAlignment!=-1) tableStyle.append("float:"+Alignment.toString(floatAlignment)+";");
        if(visible){
//            if(layoutMethod!=-1) tableStyle.append("display:"+LayoutMethod.toString(layoutMethod)+";");
        }else{
            tableStyle.append("display:none;");
        }
        
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
        
        if(explicitStyle!=null) tableStyle.append(explicitStyle);

        StringBuilder tableAttributes = new StringBuilder();
        if(id!=null) tableAttributes.append(" id='"+id+"'");
        if(tableStyle.length()>0) tableAttributes.append(" style='"+tableStyle+"'");
        tableAttributes.append(" cellpadding='0'");
        tableAttributes.append(" cellspacing='0'");
        if(explicitAttribute!=null) tableAttributes.append(explicitAttribute);
//        if(onLoad!=null) tableParms.append(Renderer.onLoad(onLoad, context));
        
        //StringBuilder out = new StringBuilder();
        PrintWriter out = context.getPrintWriter();
        
        if(comment!=null) out.append("<!--" + comment + " -->");
        
        out.append("<table").append(tableAttributes).append(">"+BR+"");
        
        if(cellList.size()==0){
            //Empty container, append empty cell
            out.append("<tr>"+BR+"<td></td>"+BR+"</tr>"+BR+"");
        }else{
            //When cellBorders are used, the gap is distributed on each side of the border.
            int underGap = gap/2;           //Gap under the cell border
            int overGap = underGap + gap%2;    //Gap above the cell border
            
            for(int i=0; i<cellList.size(); i++){
                VerticalLayoutCellX cell = (VerticalLayoutCellX)cellList.get(i);
                
//                //If this container is disabled, disable all it's components
//                //TODO: Use cell.component.setEnabled(false)
//                if(!enabled) cell.getComponent().setEnabled(false);
                
                //Set all cell borders to the cellBorder defined in the panel.
                //Cells that have their borders set explicitly are not affected.
                if(cell.border==null && cellBorder!=null) cell.border = cellBorder;
                
                StringBuilder tdStyle = new StringBuilder();

                if(relativeWidth!=-1) tdStyle.append("width:"+relativeWidth+"%;");
                if(cell.hAlign!=-1) tdStyle.append("text-align:"+Align.toString(cell.hAlign)+";");
//                if(cell.vAlign!=-1) tdStyle.append("######='"+Align.toString(cell.vAlign)+"'");
                if(cell.height!=-1){
                    if(cell.relativeHeight){
                        tdStyle.append("height="+cell.height+"%;");
                    }else{
                        tdStyle.append("height="+cell.height+";");
                    }
                }
                if(cell.display!=true) tdStyle.append("display:none;");
                if(cell.backgroundColor!=null) tdStyle.append("background-color:"+cell.backgroundColor+";");
                if(cell.backgroundImage!=null) tdStyle.append("background-image: url("+cell.backgroundImage.getUrl()+");");
                if(cell.backgroundRepete!=-1) tdStyle.append("background-repeat:"+Repete.toString(cell.backgroundRepete)+";");
                if(cell.backgroundHAlign!=-1 || cell.backgroundVAlign!=-1){
                    tdStyle.append("background-position:"+Align.toString(cell.backgroundHAlign)+" "+Align.toString(cell.backgroundVAlign)+";");
                }
                
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
                
                //Calculate inner padding from table padding, cell padding and gap
                if(padding!=null || cell.padding!=null || gap!=-1){
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
                    
                    //Add gap padding
                    if(gap!=-1){
                        if(cellList.size()>1){
                            //Handels the more-then-one-cell-case
                            if(i==0){
                                //First cell
                                bottomPadding += overGap;
                            }else if(i==cellList.size()-1){
                                //Last cell
                                topPadding += underGap;
                            }else{
                                //Cells between the first and the last cell.
                                bottomPadding += overGap;
                                topPadding += underGap;
                            }
                        }else{
                            //Handels the one-cell-case
                            //Gap is not applicable
                        }
                    }
                    
                    //Add panel padding
                    if(padding!=null){
                        if(cellList.size()>1){
                            //Handels the more-then-one-cell-case
                            if(i==0){
                                //First cell
                                topPadding += padding.top;
                                leftPadding += padding.left;
                                rightPadding += padding.right;
                            }else if(i==cellList.size()-1){
                                //Last cell
                                leftPadding += padding.left;
                                bottomPadding += padding.bottom;
                                rightPadding += padding.right;
                            }else{
                                //Cells between the first and the last cell.
                                leftPadding += padding.left;
                                rightPadding += padding.right;
                            }
                        }else{
                            //Handels the one-cell-case
                            topPadding += padding.top;
                            leftPadding += padding.left;
                            bottomPadding += padding.bottom;
                            rightPadding += padding.right;
                        }
                    }
                    
                    tdStyle.append("padding:"+topPadding+"px "+rightPadding+"px "+bottomPadding+"px "+leftPadding+"px;");
                }

                if(cell.explicitStyle!=null) tdStyle.append(cell.explicitStyle);

                StringBuilder tdAttributes = new StringBuilder();

                if(tdStyle.length()>0) tdAttributes.append(" style='"+tdStyle+"'");
                if(cell.id!=null) tdAttributes.append(" id='"+cell.id+"'");
                if(cell.explicitAttribute!=null) tdAttributes.append(cell.explicitAttribute);

                out.append("<tr>"+BR);
                out.append("<td").append(tdAttributes).append(">");
                
                //Render the added components
                cell.component.render(context);
                
                out.append("</td>"+BR);
                out.append("</tr>"+BR);
            }
        }
        
        out.append("</table>");        
        
    }
    
    
}
