/*
 *
 * Created on den 1 dec 2016
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.Component;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Örjan Derelöv
 *
 */
public class TextFlowLayoutCell{

    protected Component component = null;               //The component in this cell
    private ArrayList<Media> parentMediaList = null;    //The parent media list. We need access to this for adding new medias. The layout manager will iterate over the media list when rendering the cells.

    protected class Props{
        protected String id = null;
        protected boolean display = true;       //Display this cell or not. If not displayed, the cell and it's content will not take up any space.
        protected float relativeWidth = -1;
        protected int hAlign = -1;
        protected Spacing spacing = null;       //Can not be set explicitly at cell level, since this is intercellular row and column padding, and they can not be individual
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

    public TextFlowLayoutCell(ArrayList<Media> parentMediaList,Component component){
        this.parentMediaList = parentMediaList;
        this.component = component;
    }

    public TextFlowLayoutCell setId(String id){
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
//    public FlowLayoutCell setDisplay(boolean display) {
//        this.props.display = display;
//        return this;
//    }

    public TextFlowLayoutCell setDisplay(Media media, boolean display) {
        getProps(media).display = display;
        return this;
    }

    public TextFlowLayoutCell setHAlign(int hAlign){
        this.props.hAlign = hAlign;
        return this;
    }

    public TextFlowLayoutCell setRelativeWidth(int relativeWidth){
        this.props.relativeWidth = relativeWidth;
        return this;
    }

    public TextFlowLayoutCell setRelativeWidth(Media media, float relativeWidth){
        getProps(media).relativeWidth = relativeWidth;
        return this;
    }

    public TextFlowLayoutCell setSpacing(int spacing){
        this.props.spacing = new Spacing(spacing);
        return this;
    }

    public TextFlowLayoutCell setSpacing(int top, int right, int bottom, int left){
        this.props.spacing = new Spacing(top,right,bottom,left);
        return this;
    }

    public TextFlowLayoutCell setSpacing(Spacing spacing){
        this.props.spacing = spacing;
        return this;
    }

    public TextFlowLayoutCell setBackgroundColor(Color backgroundColor) {
        this.props.backgroundColor = backgroundColor;
        return this;
    }

    public TextFlowLayoutCell setBackgroundColor(Media media, Color backgroundColor) {
        getProps(media).backgroundColor = backgroundColor;
        return this;
    }

    public TextFlowLayoutCell setBackgroundImage(BackgroundImage backgroundImage) {
        this.props.backgroundImage = backgroundImage;
        return this;
    }


}
