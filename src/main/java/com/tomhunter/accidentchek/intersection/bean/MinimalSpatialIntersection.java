package com.tomhunter.accidentchek.intersection.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "accident")
@SuppressWarnings("serial")
public class MinimalSpatialIntersection implements Serializable
{
    protected int id;
    protected double distance;                        
    private double latitude;
    private double longitude;
    
    @XmlElement
    public void setId(int id)
    {
        this.id = id;
    }
    
    @XmlElement
    public void setDistance(double distance)
    {
        this.distance = distance;
    }
    
    public double getLatitude()
    {
        return latitude;
    }
    @XmlElement
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }
    
    public double getLongitude()
    {
        return longitude;
    }
    @XmlElement
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    
}
