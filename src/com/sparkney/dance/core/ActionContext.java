/*
 * ActionContext.java
 *
 * Created on den 19 juni 2007
 *
 */

package com.sparkney.dance.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @deprecated Replaced by Context REMOVE
 * @author Örjan Derelöv
 */
public class ActionContext {
    
    protected HttpServletRequest request = null;
    protected HttpServletResponse response = null;
    
    /**
     *
     */
    public ActionContext(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.setRequest(request);
        this.setResponse(response);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    
}
