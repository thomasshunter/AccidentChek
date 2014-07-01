package com.tomhunter.accidentchek.proximity.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tomhunter.accidentchek.intersection.dao.AccidentDAO;
import com.tomhunter.accidentchek.proximity.bean.GeoLocation;
import com.tomhunter.accidentchek.proximity.bean.GeoLocationPair;
import com.tomhunter.accidentchek.proximity.bean.HazardCluster;

@Service( "proximityService" )
public class ProximityService
{
    private AccidentDAO accidentDAO;

    
    public void proximateAccidentsService()
    {
        double separation                       = 0.001309;
        HazardCluster hazardClusters            = this.accidentDAO.getHazardClustersBasedOnDistanceDAO( separation, GeoLocation.UNITS_M );
        
        List<GeoLocationPair> geoLocationPairs  = hazardClusters.getHazardGeoLocationPairs();
        Iterator<GeoLocationPair> geoLocsIts    = geoLocationPairs.iterator();
        
        while( geoLocsIts.hasNext() )
        {
            GeoLocationPair aPair   = geoLocsIts.next();
            GeoLocation point1      = aPair.getPoint1();
            GeoLocation point2      = aPair.getPoint2();
            
            double distanceInMiles  = this.determineDistanceBetweenTwoPoints( point1, point2 );
            double distanceInFeet   = distanceInMiles * 5280;
            
            System.out.println( "distanceInFeet=" + distanceInFeet );
        }
    }
    
    
    
    private double determineDistanceBetweenTwoPoints( GeoLocation point1, GeoLocation point2 )
    {
        String units        = point1.getUnits();
        
        double lat1         = point1.getLatitude();
        double lon1         = point1.getLongitude();
        double lat2         = point2.getLatitude();
        double lon2         = point2.getLongitude();
        double theta        = lon1 - lon2; 
        double distance     = Math.sin( deg2rad( lat1 ) ) * Math.sin( deg2rad( lat2 ) ) + Math.cos( deg2rad( lat1 ) ) * Math.cos( deg2rad( lat2 ) ) * Math.cos( deg2rad( theta ) ); 

        distance            = Math.acos( distance );
        distance            = rad2deg( distance );
        
        distance            = distance * 60 * 1.1515; // Miles
        
        if( GeoLocation.UNITS_K.equals( units ) )
        {
            distance = distance * 1.609344;
        }
        else if( GeoLocation.UNITS_N.equals( units ) )
        {
            distance = distance * 0.8684;
        }
        
        return distance;
    }
    
    private double deg2rad( double deg )
    {
        return (deg * Math.PI/180.0);
    }
    
    private double rad2deg( double rad )
    {
        return (rad * 180)/Math.PI;
    }
    
    
    
    @Autowired
    public void setAccidentDAO(AccidentDAO accidentDAO)
    {
        this.accidentDAO = accidentDAO;
    }

    
    public static void main( String[] args )
    {
        ProximityService serv = new ProximityService();
        serv.setAccidentDAO( new AccidentDAO() );
        serv.proximateAccidentsService();
    }
    
}


