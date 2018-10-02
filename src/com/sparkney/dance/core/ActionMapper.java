/*
 * ActionMapperImpl.java
 *
 * Created on den 29 dec 2007
 *
 */

package com.sparkney.dance.core;

/**
 *
 * @author orjan
 *
 *
 */
public interface ActionMapper {
    
    /**
     * com.example.pub.action.ViewStartPage <=> /com/example/pub/action/ViewStartPage
     */
    public Action getAction(String uri) throws Exception;
            
    /**
     * com.example.pub.action.ViewStartPage <=> /com/example/pub/action/ViewStartPage
     */
    public String getUri(Action action);
    

}
