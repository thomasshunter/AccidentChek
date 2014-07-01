package com.tomhunter.accidentchek.intersection.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tomhunter.accidentchek.intersection.bean.MinimalSpatialIntersection;
import com.tomhunter.accidentchek.intersection.dao.AccidentDAO;

//http://localhost:8080/accidentchek/

@Service( "spatialIntersectionService" )
public class SpatialIntersectionService
{
    public static final String PATH_TO_IMPORT_FILE = "/Users/tomhunter/Desktop/ACCIDENT_CHEK/Accidents_2012_2013_Vehicles_geocoded.csv";
    
    private AccidentDAO accidentDAO;
    private ObjectMapper mapper = new ObjectMapper();
    
    public SpatialIntersectionService()
    {
    }
    
    public SpatialIntersectionService( String path )
    {
        FileReader fr       = null;
        BufferedReader br   = null;
        int count           = 0;
        
        try
        {
            fr                  = new FileReader( path );
            br                  = new BufferedReader( fr );
            boolean keepReading = true;
        
            while( keepReading )
            {
                String aLine = br.readLine();
                                
                if( aLine == null )
                {
                    keepReading = false;
                }
                else
                {
                    int indexOfHeaders = aLine.indexOf( "INDIVIDUAL_MR_RECORD" );
                    
                    if( indexOfHeaders > -1 )
                    {
                        continue;
                    }
                    
                    count += insertOneLineIntoTheIntersectionTable( aLine );
                }
            }
        }
        catch( Exception e )
        {
            System.out.println( "org.tomhunter.accidentchek.intersection.service.SpatialIntersectionService() threw an Exception, count=" + count + ", e=" + e );
        }
        finally
        {
            try
            {
                if( br != null )
                {
                    br.close();
                }
                if( fr != null )
                {
                    fr.close();
                }
            }
            catch( Exception e )
            {
            }
        }
        
    }
    
    
    private int insertOneLineIntoTheIntersectionTable( String aLine ) throws Exception
    {
        int outcome         = 0;
        String[] fields     = null;
        String originalLine = aLine;
        
        if( aLine != null )
        {
            aLine           = fixPairedCommas( aLine );
            fields          = aLine.split( ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)" );
            int fieldCount  = fields.length;
            
            if( fieldCount != 15 )
            {
                System.out.println( "fieldCount=" + fieldCount + "\t aLine=" + aLine );
            }
            
            outcome = this.getAccidentDAO().insertOneLineIntoTheSpatialIntersectionTableDAO( fields, originalLine );
        }
        
        return outcome;
    }
    
    
    public List<MinimalSpatialIntersection> selectAllRouteGeoLocationsWithinAMBRUsingTwoPointsService( double startLat, double startLon, double endLat, double endLon, int limit )
    {
        double lat1 = -1;
        double lon1 = -1;
        double lat2 = -1;
        double lon2 = -1;
        
        if( startLat > endLat && startLon < endLon )
        {
            lat1 = startLat;
            lon1 = startLon;
            lat2 = endLat;
            lon2 = endLon;
        }
        else if( startLat > endLat && startLon > endLon )
        {
            System.out.println( "wait" );
        }
        
        
        
        
        List<MinimalSpatialIntersection> accidents = this.accidentDAO.selectAllRouteGeoLocationsWithinAMBRUsingTwoPointsDAO( lat1, lon1, lat2, lon2, limit );
        
        return accidents;
    }
    
    
    
    
    public List<MinimalSpatialIntersection> selectAllGeoLocationsInsideRadiusMBRUsingStDistanceService( double lat, double lon, double distance )
    {
        List<MinimalSpatialIntersection> accidents = this.accidentDAO.selectAllGeoLocationsInsideRadiusMBRUsingStDistanceDAO( lat, lon, distance );
        
        return accidents;
    }
    
    
    
    @Deprecated
    public List<MinimalSpatialIntersection> selectAllGeoLocationsInsideRadiusMBRUsingTwoPointsAsCornersService( double latStart, double lonStart, double latEnd, double lonEnd, double distance )
    {
        List<MinimalSpatialIntersection> accidents = this.accidentDAO.selectAllGeoLocationsInsideRadiusMBRUsingTwoPointsAsCornersDAO( latStart, lonStart, latEnd, lonEnd,distance );
        
        return accidents;
    }
    
    @Deprecated
    public List<MinimalSpatialIntersection> selectAllGeoLocationsBasedOnDistanceFromACenterPointBasedOnRadiusService( double centerLon, double centerLat, double radius )
    {
        List<MinimalSpatialIntersection> accidents         = this.accidentDAO.selectAllGeoLocationsBasedOnDistanceFromACenterPointBasedOnRadiusDAO( centerLon, centerLat, radius );
        List<MinimalSpatialIntersection> finalList         = new ArrayList<MinimalSpatialIntersection>();
        
        Iterator<MinimalSpatialIntersection> accidentsIt   = accidents.iterator();
        
        Set<Double> distances                               = new HashSet<Double>();
        
        while( accidentsIt.hasNext() )
        {
            MinimalSpatialIntersection anAccident  = accidentsIt.next();
            //Double distance                         = anAccident.getDistance();
            boolean isNew                           = true; //distances.add( distance );
            
            if( isNew )
            {
                finalList.add( anAccident );
            }
        }
        
        return finalList;
    }
    
    @Deprecated
    private String getAllAccidentsBetweenIdsAsJSONService( double centerLon, double centerLat, double radius )
    {
        StringBuilder json                                 = new StringBuilder( "{ 'accidents':[");
        List<MinimalSpatialIntersection> accidents         = this.selectAllGeoLocationsInsideRadiusMBRUsingStDistanceService( centerLat, centerLon, radius  );
        Iterator<MinimalSpatialIntersection> accidentsIt   = accidents.iterator();    
        
        try
        {
            
            while( accidentsIt.hasNext() )
            {
                MinimalSpatialIntersection anAccident   = accidentsIt.next();
                String js                               = mapper.writeValueAsString( anAccident );
            
                json.append( js );
                json.append( ",\n" );
            }
            
            json.append( "]\n}" );
        }
        catch( Exception e )
        {
            System.out.println( "org.tomhunter.accidentchek.intersection.service.SpatialIntersectionService.getAllAccidentsBetweenIdsAsJSONService() threw an Exception, e=" + e );
        }
        
        return json.toString();        
    }
    
    
    public String fixPairedCommas( String aLine )
    {
        int tripledCommas = aLine.indexOf( ",,," );
        
        if( tripledCommas > -1 )
        {
            aLine = aLine.replaceAll( ",,,", ", , ," );
        }
        
        int indexOfDoubleCommas = aLine.indexOf( ",," );
        if( indexOfDoubleCommas > -1 )
        {
            aLine = aLine.replaceAll( ",,", ", ," );
        }
    
        return aLine;
    }    
    
    @Autowired
    public void setAccidentDAO(AccidentDAO accidentDAO)
    {
        this.accidentDAO = accidentDAO;
    }
    public AccidentDAO getAccidentDAO()
    {
        if( accidentDAO == null )
        {
            accidentDAO = new AccidentDAO();
        }
        
        return accidentDAO;
    }

    
    public static void main( String[] args )
    {
        SpatialIntersectionService service = new SpatialIntersectionService( ImportIntersectionService.PATH_TO_IMPORT_FILE );
    }
    
    
}
