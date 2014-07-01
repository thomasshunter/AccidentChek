package com.tomhunter.accidentchek;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccidentChekUser implements Serializable
{
    public static String USER_TYPE_MAP_USER             = "MAP_USER";
    public static String USER_TYPE_MAP_ADMIN            = "MAP_ADMIN";
    public static final String SESSION_ACCIDENTS_JSON   = "accidentsJson";
    
    
    private String userType                     = AccidentChekUser.USER_TYPE_MAP_USER; // Default
    private double userLatitude;
    private double userLongitude;
    
    @Override
    public String toString()
    {
        StringBuilder out = new StringBuilder();
        
        out.append( "\n--------------------" );
        out.append( "\n  AccidentChekUser  " );
        out.append( "\n--------------------" );
        out.append( "\n userType          =" + userType );
        out.append( "\n userLatitude      =" + userLatitude );
        out.append( "\n userLongitude     =" + userLongitude );
        out.append( "\n--------------------" );
               
        return out.toString();
    }
    
    public double getUserLatitude()
    {
        return userLatitude;
    }
    public void setUserLatitude(double userLatitude)
    {
        this.userLatitude = userLatitude;
    }
    
    public double getUserLongitude()
    {
        return userLongitude;
    }
    public void setUserLongitude(double userLongitude)
    {
        this.userLongitude = userLongitude;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }
    
    
}
