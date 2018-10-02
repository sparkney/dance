/*
 * AbstractController.java
 *
 */

package com.sparkney.dance.core;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Extend the this abstract controller and annotate it with WebServlet annotation
 * to create a controller for the controllers package (context). We suggests this
 * basic implementation:
 * 
 * <pre> 
 * &#64WebServlet(urlPatterns = {"/context/*"})
 * public class Controller extends AbstractController{
 * 
 *     public static Controller instance;
 *     
 *     &#64Override
 *     public void init(){
 *         super.init();
 *         instance=this;
 *     }
 * }
 * </pre>
 * 
 * The abstract controller will initialize the action mapper with "context" as
 * urlPattern and the the controllers package.
 * 
 * Optionally, you can specify the action mapper explicitly in the init method,
 * before calling super.init(); That is if you want the controller to reside in
 * a different package then the actions.
 * 
 * Currently, if you need more flexibility and let the same controller to handle
 * multiple url patterns  and packages, or you need to develop you own. It's not
 * hard, just use the this abstract controller as a template and add WebServlet
 * annotations.
 *  
 * @author orjan
 * 
 */
public abstract class AbstractController extends HttpServlet {
            
    private ActionMapper actionMapper = null;
    private String contentType = "text/html;charset=UTF-8";           //Default content type
    private String characterEncoding = "UTF-8";                       //Default character encoding

    @Override
    public void init(){
        if(actionMapper==null){
            //Actionmapper is not set explicitly. Configure an implicit actionamapper
            //by getting the urlPattern annotation from the extending servlet and the
            //package name for this class.
            try{
                //Get url pattern from the extending servlet
                WebServlet webServletAnnotation = this.getClass().getAnnotation(WebServlet.class);
                String urlPattern = webServletAnnotation.urlPatterns()[0];
                urlPattern = urlPattern.replace("*","");

                //Get package name of the extending servlet
                String packageName = this.getClass().getPackage().getName();

            
                //Create a new default action mapper for this controller
                ActionMapperImpl ActionMapperImpl = new ActionMapperImpl(urlPattern,packageName);
                
                //Set the controller's action mapper
                actionMapper = ActionMapperImpl;
                
                                
            }catch(Exception e){
                //Throw unchecked exception
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    /**
     * This method is called before each request is mapped to an action.
     */
    public void requestInit(Context context){
    }
    
    /**
     * This method is called before each requested action is performed. It returns
     * the requested action by default. 
     * This method is meant to be overridden to perform tasks required for
     * each action, and to override requested action if necessary. Typically,
     * it will be used for checking if a user is logged in, and if not, return
     * an action that forward to a login page, or else return null to perform
     * the requested action. By default, the method does nothing and returns
     * null.
     * @param context
     * @param requestedAction
     * @return An action to be performed or null if the response is taken care of
     * in the overridden method, like send redirect.
     */
    public Action overrideRequestedAction(Context context, Action requestedAction){
        return requestedAction;
    }


    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{

            //Set character encoding
            request.setCharacterEncoding(characterEncoding);
            response.setCharacterEncoding(characterEncoding);

            //Set content type and character encoding
            response.setContentType(contentType);

            //Create action context
            Context context = new Context(request, response);
            
            //Do request inizialization if any
            requestInit(context);            

            //Map an action to the request URI
            //TODO: actionMapper may not have been initialized
            Action action = actionMapper.getAction(request.getRequestURI());
            
            //Get overridden action if any
            action = overrideRequestedAction(context, action);

            if(action==null){
                //No action is returned, response is haneled in overrideRequestedAction(..)
                return;
            }
            
            //Perform the action and get the resulting component
            Component component = action.perform(context);
            
            if(component==null){
                //No component is returned, the action handels the response 
                return;
            }else{
                //A component is returned, render the component
                
                //Create a component context
                Context componentContext = new Context(request, response);
                
                componentContext.setAction(action);

                //Render the component
                component.render(componentContext);

                //Make sure to close the context, ie the output stream in the context
                componentContext.close();
            }
            
        }catch(Exception e){
            try{
                e.printStackTrace();
                response.setContentType("text/plain;charset=UTF-8");
                e.printStackTrace(response.getWriter());
            }catch(Exception e2){}
        }
    }
        
    public ActionMapper getActionMapper(){
        return actionMapper;
    }    

    public void setActionMapper(ActionMapper actionMapper){
        this.actionMapper = actionMapper;
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>


}
