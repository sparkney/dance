  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sparkney.dance.core;

/**
 *
 * @author orjan
 */
public interface Action{
    
    public Component perform(Context context) throws Exception;
        
    public String getUri();

    public ActionMapper getActionMapper();

    public void setActionMapper(ActionMapper actionMapper);
    
    public Action addParameter(String name, String value);

    public Action addParameter(String name, int value);

    public Action addParameter(String name, long value);
    
    
    
}
