package com.tomhunter.accidentchek.springmvc.controller;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tomhunter.accidentchek.intersection.bean.MinimalSpatialIntersection;
import com.tomhunter.accidentchek.intersection.bean.SimpleLocation;
import com.tomhunter.accidentchek.intersection.service.LongLatService;
import com.tomhunter.accidentchek.intersection.service.SpatialIntersectionService;

@Controller
public class MapController
{
    
    private SpatialIntersectionService spatialIntersectionService;
    private LongLatService longLatService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getPages()
    {
        ModelAndView model = new ModelAndView("map");
        return model;
    }

        
    @RequestMapping(value = "/getScoreForRoute", method = RequestMethod.GET)
    public @ResponseBody String getScoreForRoute(@RequestParam String startAddress, @RequestParam String endAddress)
    {
        SimpleLocation startLocation    = this.longLatService.getLongitudeLatitude( startAddress );
        double latStart                 = -1;
        double lonStart                 = -1;
        SimpleLocation endLocation      = this.longLatService.getLongitudeLatitude( endAddress );
        double latEnd                   = -1;
        double lonEnd                   = -1;
        
        if( startLocation != null )
        {
            try
            {
                latStart    = Double.parseDouble( startLocation.getLatitude() );
                lonStart    = Double.parseDouble( startLocation.getLongitude() );
                
                latEnd      = Double.parseDouble( endLocation.getLatitude() );
                lonEnd      = Double.parseDouble( endLocation.getLongitude() );
            }
            catch( NumberFormatException nfe )
            {
                System.out.println( "MapController.getScoreForRoute() encountered a NumberFormatException while attempting to convert startLocation=" + startLocation + " into doubles, nfe=" + nfe );
            }
        }

        StringBuilder result = new StringBuilder();
        result.append( "\n { " );
        result.append( "\n    \"latStart\":\"" + latStart + "\", " );
        result.append( "\n    \"lonStart\":\"" + lonStart + "\", " );
        result.append( "\n    \"latEnd\"  :\"" + latEnd   + "\", " );
        result.append( "\n    \"lonEnd\"  :\"" + lonEnd   + "\", " );
        result.append( "\n    \"accidents\": [\n\t" );

        ObjectMapper mapper                                     = new ObjectMapper();
        List<MinimalSpatialIntersection> intersections          = this.spatialIntersectionService.selectAllRouteGeoLocationsWithinAMBRUsingTwoPointsService( latStart, lonStart, latEnd, lonEnd, 1000 );
        Iterator<MinimalSpatialIntersection> intersectionsIt    = intersections.iterator();
        boolean needComma                                       = false;

        try
        {
            int count = 0;
            while( intersectionsIt.hasNext() )
            {
                if( needComma )
                {
                    result.append( ",\n\t" );
                    needComma = false;
                    count++;
                }
                
                MinimalSpatialIntersection location     = intersectionsIt.next();
                result.append( mapper.writeValueAsString( location ) );
                needComma                               = true;
            }
            
            result.append( "\n] " );
            result.append( "\n } " );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result.toString();
    }
    
    
    @RequestMapping(value = "/getScoreForOneLocation", method = RequestMethod.GET)
    public @ResponseBody String getScoreForOneLocation(@RequestParam String streetAddress)
    {
        SimpleLocation simpleLocation = this.longLatService.getLongitudeLatitude( streetAddress );
        double lat                      = -1;
        double lon                      = -1;
        
        if( simpleLocation != null )
        {
            try
            {
                lat = Double.parseDouble( simpleLocation.getLatitude() );
                lon = Double.parseDouble( simpleLocation.getLongitude() );
            }
            catch( NumberFormatException nfe )
            {
                System.out.println( "MapController.getScoreForOneLocation() encountered a NumberFormatException while attempting to convert simpleLocation=" + simpleLocation + " into doubles, nfe=" + nfe );
            }
        }
        
        //String result = "{  \"latitude\":\"39.825419\", \"longitude\":\"-86.254511\", \"accidents\": [ {  \"latitude\": \"39.835419\", \"longitude\":\"-86.254511\"}, {\"latitude\": \"39.845419\", \"longitude\":\"-86.254511\"} ] }";

        StringBuilder result                                    = new StringBuilder( "{ \"latitude\":\"" + simpleLocation.getLatitude() + "\", \"longitude\":\"" + simpleLocation.getLongitude() + "\",\n\"accidents\": [\n\t" );
        
        ObjectMapper mapper                                     = new ObjectMapper();
        List<MinimalSpatialIntersection> intersections          = this.spatialIntersectionService.selectAllGeoLocationsInsideRadiusMBRUsingStDistanceService( lat, lon, 0.057 );
        Iterator<MinimalSpatialIntersection> intersectionsIt    = intersections.iterator();
        boolean needComma                                       = false;

        try
        {
            int count = 0;
            while( intersectionsIt.hasNext() )
            {
                if( needComma )
                {
                    result.append( ",\n\t" );
                    needComma = false;
                    count++;
                }
                
                MinimalSpatialIntersection location     = intersectionsIt.next();
                result.append( mapper.writeValueAsString( location ) );
                needComma                               = true;
            }
            
            result.append( "\n] " );
            
            String tail = " accidents were logged within a 100-yard radius.\"\n";
            
            if( count > 99 )
            {
                result.append( ",\n\"danger\":\"This intersection presents <b style='color:#FF0000'>Maximum</b> danger: " + count + tail );
            }
            else if( count > 70 && count <= 99 )
            {
                result.append( ",\n\"danger\":\"This intersection presents <b style='color:#A00000'>High</b> danger: " + count + tail );             
            }
            else if( count > 50 && count <= 70 )
            {
                result.append( ",\n\"danger\":\"This intersection presents <b style='color:#FF9933'>Signficant</b> danger: " + count + tail );                                
            }
            else if( count > 30 && count <= 50 )
            {
                result.append( ",\n\"danger\":\"This intersection presents <b style='color:#FF66CC'>Medium</b> danger: " + count + tail );                                
            }
            else if( count > 10 && count <= 30 )
            {
                result.append( ",\n\"danger\":\"This intersection presents <b style='color:#00CCCC'>Mild</b> danger: " + count + tail );                                
            }
            else if( count > 0 && count <= 10 )
            {
                result.append( ",\n\"danger\":\"This intersection presents <b style='color:#006600'>Low</b> danger: " + count + tail );                 
            }
            else
            {
                result.append( ",\n\"danger\":\"This intersection can be considered relatively safe. No " + tail );                                 
            }
                
            result.append( "}" );
            System.out.println( "count=" + count );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result.toString();
    }
    
    @Autowired
    public void setSpatialIntersectionService( SpatialIntersectionService spatialIntersectionService) 
    {
        this.spatialIntersectionService = spatialIntersectionService;
    }

    @Autowired
    public void setLongLatService(LongLatService longLatService)
    {
        this.longLatService = longLatService;
    }
    
}
