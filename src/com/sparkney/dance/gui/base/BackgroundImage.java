/*
 * 2015-03-12
 * 
 */

package com.sparkney.dance.gui.base;

/**
 *
 * @author orjan
 */
public class BackgroundImage {

    public enum Repete {
        NO_REPETE,
        HORIZONTAL,
        VERTICAL,
        AREA;

        @Override
        public String toString() {
            switch(this) {
                case NO_REPETE: return "no-repeat";
                case HORIZONTAL: return "repeat-x";
                case VERTICAL: return "repeat-y";
                case AREA: return "repeat";
                default: throw new IllegalArgumentException();
            }
        }
    }

    public enum Scale {
        COVER,
        CONTAIN;
        @Override
        public String toString() {
            switch(this) {
                case COVER: return "cover";
                case CONTAIN: return "contain";
                default: throw new IllegalArgumentException();
            }
        }
    }

    private String url = null;
    private int hAlign = -1;
    private int vAlign = -1;
    private Scale scale = null;
    private  Repete repete = null;

    public BackgroundImage(){
    }

    public BackgroundImage(String url){
        this.url = url;
    }

    public BackgroundImage setUrl(String url){
        this.url = url;
        return this;
    }

    public BackgroundImage setAlign(int hAlign, int vAlign){
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        return this;
    }

    public BackgroundImage setScale(Scale scale){
        this.scale = scale;
        return this;
    }

    public BackgroundImage setRepete(Repete repete){
        this.repete = repete;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(url!=null) sb.append("background-image:url("+url+");");
        if(hAlign!=-1 && vAlign!=-1) sb.append("background-position:"+Align.toString(hAlign)+" "+Align.toString(vAlign)+";");
        if(scale!=null) sb.append("background-size:"+this.scale+";");
        if(repete!=null) sb.append("background-repeat:"+this.repete+";");
        return sb.toString();
    }

}
