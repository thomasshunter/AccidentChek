package com.tomhunter.accidentchek.proximity.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GeoLocationPair implements Serializable
{
    private GeoLocation point1;
    private GeoLocation point2;
    
    public GeoLocation getPoint1()
    {
        return point1;
    }
    public void setPoint1(GeoLocation point1)
    {
        this.point1 = point1;
    }
    
    public GeoLocation getPoint2()
    {
        return point2;
    }
    public void setPoint2(GeoLocation point2)
    {
        this.point2 = point2;
    }
    
    
}
