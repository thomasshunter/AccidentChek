package com.tomhunter.accidentchek.intersection.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SimpleLocation implements Serializable
{
    private String address;
    private String latitude;
    private String longitude;
    
    
    @Override
    public String toString()
    {
        StringBuilder out = new StringBuilder();
        
        out.append( "\n------------------" );
        out.append( "\n   SimpleLocation " );
        out.append( "\n------------------" );
        out.append( "\n address         =" + address );
        out.append( "\n latitude        =" + latitude );
        out.append( "\n longitude       =" + longitude );
        out.append( "\n------------------" );
        
        return out.toString();
    }

    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getLatitude()
    {
        return latitude;
    }
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }
    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }
    
}
