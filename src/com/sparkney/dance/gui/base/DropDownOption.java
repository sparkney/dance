/*
 * DropDownOption.java
 *
 * Created on den 7 november 2006
 *
 */

package com.sparkney.dance.gui.base;

/**
 *
 * @author Örjan Derelöv
 */
public class DropDownOption {

    protected String name = null;
    protected String value = null;
    protected boolean selected = false;
    protected boolean disabled = false;

    public DropDownOption(String name, String value){
        this.name = name;
        this.value = value;
    }

    public DropDownOption(String name, int value){
        this.name = name;
        this.value = String.valueOf(value);
    }
    
    public DropDownOption(String name, long value){
        this.name = name;
        this.value = String.valueOf(value);
    }

    public DropDownOption(int name, String value){
        this.name = String.valueOf(name);
        this.value = value;
    }

    public DropDownOption(int name, int value){
        this.name = String.valueOf(name);
        this.value = String.valueOf(value);
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setDisabled(boolean disabled){
        this.disabled = disabled;
    }

    public boolean isDisabled(){
        return disabled;
    }


}
