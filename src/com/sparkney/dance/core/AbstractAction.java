/*
 * Action.java
 *
 * Created on den 12 okt 2006, 17:13
 *
 */

package com.sparkney.dance.core;

import java.util.ArrayList;

/**
 *
 * @author  örjan Derelöv
 *
 * The action of all actions.
 */
public abstract class AbstractAction implements Action{

    private ActionMapper actionMapper = null;
    private ArrayList parameters = null;
    
    public AbstractAction(){
        init();
    }

    @Override
    public AbstractAction addParameter(String name, String value){
        if(parameters==null){
            parameters = new ArrayList();
        }
        parameters.add(name+"="+value);
        return this;
    }

    @Override
    public AbstractAction addParameter(String name, int value){
        if(parameters==null){
            parameters = new ArrayList();
        }
        parameters.add(name+"="+value);
        return this;
    }

    @Override
    public AbstractAction addParameter(String name, long value){
        if(parameters==null){
            parameters = new ArrayList();
        }
        parameters.add(name+"="+value);
        return this;
    }
    
    /**
     * This method is invoked by the controller. It should return the
     * next component to be viewed.
     */
    @Override
    public abstract Component perform(Context context) throws Exception;

    /**
     * All actions need an action mapper, implement this method to initialize the
     * action mapper. We suggest you use an abstract do this:
     * 
     * <pre>
     * &#64Override
     * public void init(){
     *     setActionMapper(Controller.instance.getActionMapper());
     * }
     * </pre>
     * 
     */
     // Since all actions need an action mapper, we might as well force an the user
     // to override an init method. This is somehow more logical then using a
     // constructor to initialize the action mapper.
    public abstract void init();
    
    
    
    /**
     * Returns the Action class URI with a leading '/'. Adding parameters
     * if any.
     */
    @Override
    public String getUri(){
        if(parameters==null){
            //No paramters, just return the action uri
            return actionMapper.getUri(this);
        }else{
            //There are paramters, add them to the action uri
            StringBuilder query = new StringBuilder();

            query.append(actionMapper.getUri(this));

            for(int i=0;i<parameters.size(); i++){
                if(i==0){
                     query.append('?');
                }else{
                    query.append('&');
                }
                query.append((String)parameters.get(i));
            }

            return query.toString();
        }
    }    
    
    
    /**
     * Returns the Action class URI with a leading '/'. Adding parameters
     * if any.
     */
    @Override
    public String toString(){
        return getUri();
    }

    @Override
    //For consistency, this is final since setActionMapper is final.
    public final ActionMapper getActionMapper(){
        return actionMapper;
    }

    @Override
    //Need to be final, or we get "Overridable method call in constructor" when
    //setting the actionmapper in an Action's constructor.
    public final void setActionMapper(ActionMapper actionMapper) {
        this.actionMapper = actionMapper;
    }



}
