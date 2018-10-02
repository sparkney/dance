/*
 * ActionMapperImpl.java
 *
 */

package com.sparkney.dance.core;

/**
 *
 * @author orjan
 *
 */
public class ActionMapperImpl implements ActionMapper{
    
    /**
     * The urlPattern
     */
    private String urlPattern = null;
    
    /**
     * The package where the actions reside
     */
    private String actionPackage = null;
    
    public ActionMapperImpl(){
    }
    
    public ActionMapperImpl(String urlPattern, String actionPackage){
        this.urlPattern = urlPattern;
        this.actionPackage = actionPackage;
    }

    /**
     * com.example.pub.action.ViewStartPage <=> /com/example/pub/action/viewStartPage
     */
    public Action getAction(String uri) throws Exception{
        String className = "";
        try{
            if(actionPackage==null){
                //Map the uri directly to the same package name
                //uri = /com/example/pub/action/viewStartPage
                //return com.example.pub.action.ViewStartPage
                int lastSlashIndex = uri.lastIndexOf('/');
                String simpleCalssName = Character.toUpperCase(uri.charAt(lastSlashIndex+1)) + uri.substring(lastSlashIndex+2,uri.length());
                className = uri.substring(1,lastSlashIndex+1).replace('/','.') + simpleCalssName;
                System.out.println("Try loading: " + className);
                Object object = Class.forName(className).newInstance(); //Load class
                return (Action)object;
            }else{
                //Map the uri to the package name in actionPackage
                //uri = /action/viewStartPage
                //actionPackage = com.example.pub
                //return com.example.pub.action.ViewStartPage
                int lastSlashIndex = uri.lastIndexOf('/');
                String simpleCalssName = Character.toUpperCase(uri.charAt(lastSlashIndex+1)) + uri.substring(lastSlashIndex+2,uri.length());
                className = actionPackage + '.' + simpleCalssName;
                System.out.println("Try loading: " + className);
                Object object = Class.forName(className).newInstance();   //Load class
                return (Action)object;
            }
        }catch(ClassNotFoundException e){
            throw new ClassNotFoundException("No action "+className+" found for the request URI " + uri);
        }catch(ClassCastException e){
            throw new ClassCastException("The class "+className+" does not extend Action.");
        }catch(StringIndexOutOfBoundsException e){
            throw new StringIndexOutOfBoundsException("No action present in the request URI " + uri);
        }catch(Exception e){
            //TODO: There could be more exceptions here then just a Malformed request URI.
            //Catch class load exceptions, or just throw the genereted exception.
            //throw new Exception("Malformed request URI " + uri);
            throw e;
        }
    }
    
    /**
     * com.example.pub.action.ViewStartPage <=> /com/example/pub/action/viewStartPage
     */
    public String getUri(Action action){
        if(urlPattern==null){
            //Map the class directly to the uri
            //class = com.example.pub.action.ViewStartPage
            //return /com/example/pub/action/viewStartPage
            String className = action.getClass().getName();
            int lastDotIndex = className.lastIndexOf('.');
            String simpleCalssName = Character.toLowerCase(className.charAt(lastDotIndex+1)) + className.substring(lastDotIndex+2,className.length());
            return '/' + className.substring(0,lastDotIndex+1).replace('.','/') + simpleCalssName;
        }else{
            //Map the class to the uriBase
            //class = com.example.pub.action.ViewStartPage
            //uriBase = /action/
            //return /action/viewStartPage
            String simpleClassName = action.getClass().getSimpleName();
            return urlPattern + Character.toLowerCase(simpleClassName.charAt(0)) + simpleClassName.substring(1,simpleClassName.length());
        }
    }
    
    
}
