/*
 * AjaxAction.java
 *
 * Created on den 23 dec 2012, 14:14
 *
 */

package com.sparkney.dance.core;

/**
 * TODO: Replace setBeforeAction(..) and setAfterAction(..) width
 * addBeforeAction(..) and addAfterAction(..), since we often like to do more
 * the one thing. Those methods should take JavaScript and AjaxAction, not
 * just a String.
 *
 * TODO 131202: It is confusing that AjaxAction inherits the method getUri(). It makes
 * no sense to get the URI.
 * 
 * TODO 171212: Use default target. When developing an AJAX centic site, every actions
 * needs a target. A default target will be very convenient.
 *
 * @author  Örjan Derelöv
 *
 * 
 */
public abstract class AjaxAction extends AbstractAction{

    protected String targetElementId = null;    //Id of the element where the response text will be inserted.
    private String beforeAjax = null;           //JavaScript to executed befor the ajax request is performed
    private String afterAjax = null;            //JavaScript to executed just after the ajax request is performed
    private String afterAction = null;          //JavaScript to execute after the ajax actions has returnd the result.
    private String formName = null;             //If the action is a form submit, this is the name of the submitted form.

    /**
     * Set the id of the element where the response text will be inserted.
     */
    public void setTargetElementId(String responseElementId) {
        this.targetElementId = responseElementId;
    }

    /**
     * @deprecated Replaced by setBeforeAjax
     */
    public void setBeforeAction(String beforeAction) {
        this.beforeAjax = beforeAction;
    }

    public void setBeforeAjax(String beforeAjax) {
        this.beforeAjax = beforeAjax;
    }

    public void setAfterAjax(String afterAjax) {
        this.afterAjax = afterAjax;
    }

    public void setAfterAction(String afterAction) {
        this.afterAction = afterAction;
    }

    /**
     * @deprecated
     * This method is only used by Dance when submitting a form. It should never
     * be used in an application.
     * TODO: Bad implementation due to lack of time. Form name should be hidden
     * from the application. The implementation will be changed in the future,
     * and this why it is deprecated.
     */
    public void setFormName(String formName) {
        this.formName = formName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(beforeAjax!=null){
            sb.append(beforeAjax + ";");
        }

        //window.top. is very importana since calling ajaxPost from embedded iframes
        //that may realod will result in very complicaded bugs. Everything should be
        //run in the window.top-context.
        sb.append("window.top.ajaxPost('"+super.toString()+"',");

        if(targetElementId!=null){
            sb.append("'"+targetElementId+"',");
        }else{
            sb.append("null,");
        }

        if(afterAction!=null){
            sb.append("function(){"+afterAction+"},");
        }else{
            sb.append("null,");
        }

        if(formName!=null){
            sb.append("'"+formName+"');");
        }else{
            sb.append("null);");
        }

        if(afterAjax!=null){
            sb.append(afterAjax + ";");
        }


        return sb.toString();
    }

        

}
