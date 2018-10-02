/*
 * SubmitForm.java
 *
 * Created on December 19, 2007
 *
 */

package com.sparkney.dance.gui.base;

import com.sparkney.dance.core.Action;

/**
 *
 * @author orjan
 */
public class SubmitForm {
    
    protected Object action = null;
    
    private SubmitForm() {
    }

    public SubmitForm(String url) {
        action = url;
    }
        
    public SubmitForm(Action action) {
        this.action = action;
    }
    
}
