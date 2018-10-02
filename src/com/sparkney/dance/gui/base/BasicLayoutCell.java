/*
 * Created on den 4 january 2014, 03:04
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.Component;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Örjan Derelöv
 */
public class BasicLayoutCell {

    protected Component component = null;               //The component in this cell
    private ArrayList<Media> parentMediaList = null;    //The parent media list. We need access to this for adding new medias. The layout manager will iterate over the media list when rendering the cells.
    
    protected class Props{
        protected String id = null;
        protected boolean display = true;       //Display this cell or not. If not displayed, the cell and it's content will not take up any space.
        protected boolean visible = true;       //View this cell or not. If not viewed, the cell and it's content will take up the same space as if it was visible.
        protected int hAlign = -1;
        protected int vAlign = -1;
        protected int width = -1;
        protected float relativeWidth = -1;
        protected Padding padding = null;
        protected Border border = null;
        protected Spacing spacing = null;
        protected Color backgroundColor = null;
        protected BackgroundImage backgroundImage = null;
    }

    protected Props props = new Props();
    protected HashMap<Media, Props> mediaMap = new HashMap<Media, Props>();

    protected Props getProps(Media media){
        Props props = mediaMap.get(media);
        if(props==null){
            props = new Props();
            mediaMap.put(media, props);

            //Add this media to the parent media list if it don't laready exist.
            if(!parentMediaList.contains(media)){
                parentMediaList.add(media);
            }
        }
        return props;
    }

    protected BasicLayoutCell(ArrayList<Media> parentMediaList,Component component){
        this.parentMediaList = parentMediaList;
        this.component = component;
    }

    public BasicLayoutCell setId(String id){
        this.props.id = id;
        return this;
    }

    //We can not let the user set display to false by default. Since doing that
    //would remove all informatino about the layout method. (CSS display are used
    //to remove an element from display, as well as deciding what layout method to
    //use.) The only way, in this implementation, to remove a component from
    //display is to show it by default, and remove it by a media query, rather then
    //doing it the other way around. This is why setDisplay(boolean) is removed, and
    //we can only use setDisplay(Media, boolean).
    public BasicLayoutCell setDisplay(boolean display) {
        this.props.display = display;
        return this;
    }

    public BasicLayoutCell setDisplay(Media media, boolean display) {
        getProps(media).display = display;
        return this;
    }

    public BasicLayoutCell setVisible(boolean visible) {
        this.props.visible = visible;
        return this;
    }

    public BasicLayoutCell setHAlign(int hAlign){
        this.props.hAlign = hAlign;
        return this;
    }

    public BasicLayoutCell setHAlign(Media media, int hAlign){
        getProps(media).hAlign = hAlign;
        return this;
    }

    public BasicLayoutCell setVAlign(int vAlign){
        this.props.vAlign = vAlign;
        return this;
    }

    public BasicLayoutCell setVAlign(Media media, int vAlign){
        getProps(media).vAlign = vAlign;
        return this;
    }

    public BasicLayoutCell setWidth(int width){
        this.props.width = width;
        return this;
    }

    public BasicLayoutCell setWidth(Media media, int width){
        getProps(media).width = width;
        return this;
    }

    public BasicLayoutCell setRelativeWidth(float relativeWidth){
        this.props.relativeWidth = relativeWidth;
        return this;
    }

    public BasicLayoutCell setRelativeWidth(Media media, float relativeWidth){
        getProps(media).relativeWidth = relativeWidth;
        return this;
    }

    public BasicLayoutCell setBackgroundColor(Color backgroundColor) {
        this.props.backgroundColor = backgroundColor;
        return this;
    }

    public BasicLayoutCell setBackgroundColor(Media media, Color backgroundColor) {
        getProps(media).backgroundColor = backgroundColor;
        return this;
    }

    public BasicLayoutCell setBackgroundImage(BackgroundImage backgroundImage) {
        this.props.backgroundImage = backgroundImage;
        return this;
    }

    public BasicLayoutCell setBackgroundImage(Media media, BackgroundImage backgroundImage) {
        getProps(media).backgroundImage = backgroundImage;
        return this;
    }

    public BasicLayoutCell setPadding(int width){
        this.props.padding = new Padding(width);
        return this;
    }

    public BasicLayoutCell setPadding(Media media, int width){
        getProps(media).padding = new Padding(width);
        return this;
    }

    public BasicLayoutCell setPadding(int top, int right, int bottom, int left){
        this.props.padding = new Padding(top,right,bottom,left);
        return this;
    }

    public BasicLayoutCell setPadding(Media media, int top, int right, int bottom, int left){
        getProps(media).padding = new Padding(top,right,bottom,left);
        return this;
    }

    public BasicLayoutCell setPadding(Padding padding){
        this.props.padding = padding;
        return this;
    }

    public BasicLayoutCell setPadding(Media media, Padding padding){
        getProps(media).padding = padding;
        return this;
    }

    public BasicLayoutCell setBorder(Border border){
        this.props.border = border;
        return this;
    }

    public BasicLayoutCell setBorder(Media media, Border border){
        getProps(media).border = border;
        return this;
    }

    public BasicLayoutCell setSpacing(int width){
        this.props.spacing = new Spacing(width);
        return this;
    }

    public BasicLayoutCell setSpacing(Media media, int width){
        getProps(media).spacing = new Spacing(width);
        return this;
    }

    public BasicLayoutCell setSpacing(int top, int right, int bottom, int left){
        this.props.spacing = new Spacing(top,right,bottom,left);
        return this;
    }

    public BasicLayoutCell setSpacing(Media media, int top, int right, int bottom, int left){
        getProps(media).spacing = new Spacing(top,right,bottom,left);
        return this;
    }

    public BasicLayoutCell setSpacing(Spacing spacing){
        this.props.spacing = spacing;
        return this;
    }

    public BasicLayoutCell setSpacing(Media media, Spacing spacing){
        getProps(media).spacing = spacing;
        return this;
    }


}
