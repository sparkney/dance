/*
 * Location.java
 *
 * Created on den 21 juli 2014, 10:02
 */

package com.sparkney.dance.gui.base;

/**
 * TODO: Why not call this Url or URL? Since we have a java.net.URL?
 *
 * @author Örjan Derelöv
 */
public class Location {

    private String url = null;

    public Location(String url){
        this.url = url;
    }

    public String toString(){
        return url;
    }

}
