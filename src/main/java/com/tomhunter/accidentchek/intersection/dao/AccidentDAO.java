package com.tomhunter.accidentchek.intersection.dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.tomhunter.accidentchek.database.util.MySQLUtil;
import com.tomhunter.accidentchek.intersection.bean.AccidentBean;
import com.tomhunter.accidentchek.intersection.bean.AccidentIntersectionBean;
import com.tomhunter.accidentchek.intersection.bean.MinimalSpatialIntersection;
import com.tomhunter.accidentchek.intersection.bean.SpatialIntersection;
import com.tomhunter.accidentchek.proximity.bean.GeoLocation;
import com.tomhunter.accidentchek.proximity.bean.GeoLocationPair;
import com.tomhunter.accidentchek.proximity.bean.HazardCluster;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKBReader;

@Component
public class AccidentDAO
{
	public static final String DATE_FORMAT 		= "yyyy-MM-dd HH:mm:ss";
	public static final String DATA_FORMAT_INDY = "MM/dd/yyyy hh:mm:ss"; // 01/11/2013 12:00:00 AM
	public static final String TIME_FORMAT		= "hh:mm";
	private MySQLUtil db;
	@SuppressWarnings("unused")
	private DateFormat df 						= new SimpleDateFormat( AccidentDAO.DATA_FORMAT_INDY );
	
	
    
    public List<MinimalSpatialIntersection> selectAllRouteGeoLocationsWithinAMBRUsingTwoPointsDAO( double lat1, double lon1, double lat2, double lon2, int limit )
    {
        Connection con                               = null;
        CallableStatement cs                         = null;
        ResultSet rs                                 = null;
        List<MinimalSpatialIntersection> accidents   = new ArrayList<MinimalSpatialIntersection>();
        DataSource ds                                = null;
        int count                                    = 0;
        String call                                  = "{call route_accidents( ?,?,?,?,? )}";
        
        try
        {
            ds      = this.getDb().getDataSource();
            con     = ds.getConnection();
            cs      = con.prepareCall( call );
            cs.setDouble( 1, lat1 );
            cs.setDouble( 2, lon1 );
            cs.setDouble( 3, lat2 );
            cs.setDouble( 4, lon2 );
            cs.setInt(    5, limit );            
            rs      = cs.executeQuery(); 
           
            while( rs.next() )
            {
                count++;
                String location         = rs.getString( 1);
                String[] latLong        = null;
                
                if( location != null )
                {
                    location    = location.replaceAll( "POINT", "" );
                    location    = location.replaceAll( "\\(", "" );
                    location    = location.replaceAll( "\\)", "" );
                    location    = location.replaceAll( ",", "" );
                    latLong     = location.split( " " );
                }
                
                int id                  = rs.getInt( 2 ); 
                
                MinimalSpatialIntersection accident    = new MinimalSpatialIntersection();
                accident.setId( id );
                String lat              = latLong[ 0 ]; 
                String lng              = latLong[ 1 ];
                
                if( lat.trim().equals( "1" ) || lat.trim().equals( "11" ) )
                {
                    continue;
                }
                
                accident.setLatitude( Double.parseDouble( lat ) );
                accident.setLongitude( Double.parseDouble( lng ) );
                
                accidents.add( accident );
            }
            
            System.out.println( "count=" + count );
        }
        catch( Exception e )
        {
            System.out.println( "AccidentDAO.selectAllRouteGeoLocationsWithinAMBRUsingTwoPointsDAO() threw an Exception while attempting to collect all matching SpatialIntersection instances, e=" + e );
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    rs.close();
                }
                if( cs != null )
                {
                    cs.close();
                }
                if( con != null && !con.isClosed() )
                {
                    con.close();
                }
            }
            catch( Exception ee )
            {
                System.out.println( "AccidentDAO.selectAllRouteGeoLocationsWithinAMBRUsingTwoPointsDAO() threw an Exception while attempting to close resources, ee=" + ee );
            }            
        }
        
        return accidents;
    }
	
	
	
	public List<MinimalSpatialIntersection> selectAllGeoLocationsInsideRadiusMBRUsingStDistanceDAO( double lat, double lon, double distance )
	{
	    Connection con                               = null;
        CallableStatement cs                         = null;
        ResultSet rs                                 = null;
        List<MinimalSpatialIntersection> accidents   = new ArrayList<MinimalSpatialIntersection>();
        DataSource ds                                = null;
        int count                                    = 0;
        String call                                  = "{call accident_radius( ?,?,?,? )}";
        
        try
        {
            ds      = this.getDb().getDataSource();
            con     = ds.getConnection();
            cs      = con.prepareCall( call );
            cs.setDouble( 1, lon );
            cs.setDouble( 2, lat );
            cs.setDouble( 3, distance );
            cs.setInt(    4, 400 );            
            rs      = cs.executeQuery(); 
           
            
            while( rs.next() )
            {
                count++;
                String location         = rs.getString( 1);
                String[] latLong        = null;
                
                if( location != null )
                {
                    location    = location.replaceAll( "POINT", "" );
                    location    = location.replaceAll( "\\(", "" );
                    location    = location.replaceAll( "\\)", "" );
                    latLong     = location.split( " " );
                }
                
                int id                  = rs.getInt( 2 ); 
                
                MinimalSpatialIntersection accident    = new MinimalSpatialIntersection();
                accident.setId( id );
                accident.setLatitude( Double.parseDouble( latLong[ 0 ] ) );
                accident.setLongitude( Double.parseDouble( latLong[ 1 ] ) );
                accident.setDistance( distance );
                
                accidents.add( accident );
            }
            
            System.out.println( "count=" + count );
        }
        catch( Exception e )
        {
            System.out.println( "AccidentDAO.selectAllGeoLocationsInMBRUsingStDistanceDAO() threw an Exception while attempting to collect all matching SpatialIntersection instances, e=" + e );
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    rs.close();
                }
                if( cs != null )
                {
                    cs.close();
                }
                if( con != null && !con.isClosed() )
                {
                    con.close();
                }
            }
            catch( Exception ee )
            {
                System.out.println( "AccidentDAO.selectAllGeoLocationsInMBRUsingStDistanceDAO() threw an Exception while attempting to close resources, ee=" + ee );
            }            
        }
        
        return accidents;
	}
	
	
	@Deprecated
	public List<MinimalSpatialIntersection> selectAllGeoLocationsInsideRadiusMBRUsingTwoPointsAsCornersDAO( double latStart, double lonStart, double latEnd, double lonEnd, double dist )
	{
	    List<MinimalSpatialIntersection> accidents = new ArrayList<MinimalSpatialIntersection>();
	    Connection con                             = null;
	    PreparedStatement ps                       = null;
	    ResultSet rs                               = null;
        String query                                = SpatialIntersection.selectAllGeoLocationsInsideRadiusMBRUsingTwoPointsAsCornersQuery( latStart, lonStart, latEnd, lonEnd, dist );
        int count                                   = 0;
	    
	    try
	    {
            con = this.getDb().getDatabaseConnectionUsingDriverManager();
            ps  = con.prepareStatement( query );               
            rs  = ps.executeQuery();

            while( rs.next() )
            {
                count++;
                int id              = rs.getInt(    1 );    
                String location     = rs.getString( 2 );
                String[] latLong    = null;
                
                if( location != null )
                {
                    location    = location.replaceAll( "POINT", "" );
                    location    = location.replaceAll( "\\(", "" );
                    location    = location.replaceAll( "\\)", "" );
                    latLong     = location.split( " " );
                }
                
                double distance                        = rs.getDouble( 3 );
                
                MinimalSpatialIntersection accident    = new MinimalSpatialIntersection();
                accident.setId( id );
                accident.setLongitude( Double.parseDouble( latLong[ 0 ] ) );
                accident.setLatitude( Double.parseDouble( latLong[ 1 ] ) );
                accident.setDistance( distance );
                
                accidents.add( accident );
            }
            
            System.out.println( "count=" + count );
	    }
	    catch( Exception e )
	    {
            System.out.println( "AccidentDAO.selectAllGeoLocationsInsideRadiusMBRUsingTwoPointsAsCornersDAO() threw an Exception while attempting to collect all matching SpatialIntersection instances, e=" + e );	        
	    }
	    finally
	    {
            try
            {
                if( rs != null )
                {
                    rs.close();
                }
                if( ps != null )
                {
                    ps.close();
                }
                if( con != null && !con.isClosed() )
                {
                    con.close();
                }
            }
            catch( Exception ee )
            {
                System.out.println( "AccidentDAO.selectAllGeoLocationsInsideRadiusMBRUsingTwoPointsAsCornersDAO() threw an Exception while attempting to close resources, ee=" + ee );
            }
	    }
	    
	    return accidents;
	}
	
	
	@Deprecated
	public List<MinimalSpatialIntersection> selectAllGeoLocationsBasedOnDistanceFromACenterPointBasedOnRadiusDAO( double lon, double lat, double radius )
	{
        Connection con                              = null;
        PreparedStatement ps                        = null;
        ResultSet rs                                = null;
        List<MinimalSpatialIntersection> accidents = new ArrayList<MinimalSpatialIntersection>();
        String query                                = SpatialIntersection.getDistanceUsingMBRQuery( lon, lat, radius ); //SpatialIntersection.selectAllGeoLocationsBasedOnDistanceFromACenterPointBasedOnRadiusQuery();
        int count                                   = 0;
	    
        try
        {
            con = this.getDb().getDatabaseConnectionUsingDriverManager();
            ps  = con.prepareStatement( query );               
            rs  = ps.executeQuery();
            
            while( rs.next() )
            {
                count++;
                int id                                  = rs.getInt(    1 );    
                String location                         = rs.getString( 2 );
                String[] latLong                        = null;
                
                if( location != null )
                {
                    location    = location.replaceAll( "POINT", "" );
                    location    = location.replaceAll( "\\(", "" );
                    location    = location.replaceAll( "\\)", "" );
                    latLong     = location.split( " " );
                }
                
                double distance                         = rs.getDouble( 3 );
                
                MinimalSpatialIntersection accident    = new MinimalSpatialIntersection();
                accident.setId( id );
                accident.setLongitude( Double.parseDouble( latLong[ 0 ] ) );
                accident.setLatitude( Double.parseDouble( latLong[ 1 ] ) );
                accident.setDistance( distance );
                
                accidents.add( accident );
            }
            
            System.out.println( "count=" + count );
        }
        catch( Exception e )
        {
            System.out.println( "AccidentDAO.selectAllGeoLocationsBasedOnDistanceFromACenterPointBasedOnRadiusDAO() threw an Exception while attempting to collect all matching SpatialIntersection instances, e=" + e );
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    rs.close();
                }
                if( ps != null )
                {
                    ps.close();
                }
                if( con != null && !con.isClosed() )
                {
                    con.close();
                }
            }
            catch( Exception ee )
            {
                System.out.println( "AccidentDAO.selectAllGeoLocationsBasedOnDistanceFromACenterPointBasedOnRadiusDAO() threw an Exception while attempting to close resources, ee=" + ee );
            }
        }
        
        return accidents;     
	}
	
	
	@Deprecated
    public List<SpatialIntersection> getAllSpatialAccidentIntersectionsInIdOrderDAO( long startId, long endId )
    {
        Connection con                              = null;
        PreparedStatement ps                        = null;
        ResultSet rs                                = null;
        List<SpatialIntersection> accidents         = new ArrayList<SpatialIntersection>();
        String query                                = AccidentIntersectionBean.selectAllAccidentIntersectionBeansQuery();
        
        try
        {
            con = this.getDb().getDatabaseConnectionUsingDriverManager();
            ps  = con.prepareStatement( query );
            ps.setLong( 1, startId );
            ps.setLong( 2, endId );
            
            rs  = ps.executeQuery();
            
            while( rs.next() )
            {
                int id                                  = rs.getInt(    1 );    
                int individualMrRecord                  = rs.getInt(    2 );                
                String mannerCollide                    = rs.getString( 3 );                
                int unitNumber                          = rs.getInt(    4 );    
                String unitTypeDescription              = rs.getString( 5 );    
                String vehicleUseDescription            = rs.getString( 6 );    
                String vehicleStateDescription          = rs.getString( 7 );    
                String travelDirectionDescription       = rs.getString( 8 );    
                String preCollisionActionDescription    = rs.getString( 9 );    
                int axelsText                           = rs.getInt(    10 );   
                int occupantsNumber                     = rs.getInt(    11 );   
                String towedIndicator                   = rs.getString( 12 );   
                String fireIndicator                    = rs.getString( 13 );   
                
                InputStream inputStreamGpsLocation      = rs.getBinaryStream( 14 );
                Geometry gpsLocation                    = getGeometryFromInputStream( inputStreamGpsLocation );

                String latLongText                      = rs.getString( 15 );    
                int riskScorePrevious                   = rs.getInt(    16 );   
                int riskScoreCurrent                    = rs.getInt(    17 );   
                Date calculationDate                    = rs.getDate(   18 );   
                
                SpatialIntersection accident            = new SpatialIntersection();
                accident.setId( id );
                accident.setIndividualMrRecord( individualMrRecord );
                accident.setMannerCollide( mannerCollide );
                accident.setUnitNumber( unitNumber );
                accident.setUnitTypeDescription( unitTypeDescription );
                accident.setVehicleUseDescription( vehicleUseDescription );
                accident.setVehicleStateDescription( vehicleStateDescription );
                accident.setTravelDirectionDescription( travelDirectionDescription );
                accident.setPreCollisionActionDescription( preCollisionActionDescription );
                accident.setAxelsText( axelsText );
                accident.setOccupantsNumber( occupantsNumber );
                accident.setTowedIndicator( towedIndicator );
                accident.setFireIndicator( fireIndicator );
                accident.setGpsLocation( gpsLocation );
                accident.setLatLongText( latLongText );
                accident.setRiskScorePrevious( riskScorePrevious );
                accident.setRiskScoreCurrent( riskScoreCurrent );
                accident.setCalculationDate( calculationDate );
                
                accidents.add( accident );
            }
        }
        catch( Exception e )
        {
            System.out.println( "AccidentDAO.getAllSpatialAccidentIntersectionsInIdOrderDAO() threw an Exception while attempting to collect all AccidentIntersectionBean instances, e=" + e );
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    rs.close();
                }
                if( ps != null )
                {
                    ps.close();
                }
                if( con != null && !con.isClosed() )
                {
                    con.close();
                }
            }
            catch( Exception ee )
            {
                System.out.println( "AccidentDAO.getAllSpatialAccidentIntersectionsInIdOrderDAO() threw an Exception while attempting to close resources, ee=" + ee );
            }
        }
        
        return accidents;
    }

    @Deprecated
    private Geometry getGeometryFromInputStream(InputStream inputStream) throws Exception 
    {    
       Geometry dbGeometry = null;
   
       if (inputStream != null) 
       {
           //convert the stream to a byte[] array so it can be passed to the WKBReader
           byte[] buffer                = new byte[255]; 
           int bytesRead                = 0;
           ByteArrayOutputStream baos   = new ByteArrayOutputStream();
           
           while ((bytesRead = inputStream.read(buffer)) != -1) 
           {
               baos.write(buffer, 0, bytesRead);
           }
   
           byte[] geometryAsBytes = baos.toByteArray();
   
           if (geometryAsBytes.length < 5) 
           {
               throw new Exception("Invalid geometry inputStream - less than five bytes");
           }
   
           //first four bytes of the geometry are the SRID, followed by the actual WKB.  Determine the SRID here
           byte[] sridBytes             = new byte[4];
           System.arraycopy(geometryAsBytes, 0, sridBytes, 0, 4);
           boolean bigEndian            = (geometryAsBytes[4] == 0x00);
   
           int srid = 0;
           
           if (bigEndian) 
           {
              for (int i = 0; i < sridBytes.length; i++) 
              {
                 srid = (srid << 8) + (sridBytes[i] & 0xff);
              }
           } 
           else 
           {
              for (int i = 0; i < sridBytes.length; i++) 
              {
                srid += (sridBytes[i] & 0xff) << (8 * i);
              }
           }
   
           //use the JTS WKBReader for WKB parsing
           WKBReader wkbReader          = new WKBReader();
   
           //copy the byte array, removing the first four SRID bytes
           byte[] wkb                   = new byte[geometryAsBytes.length - 4];
           System.arraycopy(geometryAsBytes, 4, wkb, 0, wkb.length);
           dbGeometry                   = wkbReader.read(wkb);
           dbGeometry.setSRID(srid);
       }
   
       return dbGeometry;
   }
	
	
    @Deprecated	
	public List<AccidentIntersectionBean> getAllAccidentIntersectionsInIdOrderDAO( long startId, long endId )
	{
        Connection con                              = null;
        PreparedStatement ps                        = null;
        ResultSet rs                                = null;
        List<AccidentIntersectionBean> accidents    = new ArrayList<AccidentIntersectionBean>();
        String query                                = AccidentIntersectionBean.selectAllAccidentIntersectionBeansQuery();
        
        try
        {
            con = this.getDb().getDatabaseConnectionUsingDriverManager();
            ps  = con.prepareStatement( query );
            ps.setLong( 1, startId );
            ps.setLong( 2, endId );
            
            rs  = ps.executeQuery();
            
            while( rs.next() )
            {
                int id                                  = rs.getInt(    1 );    
                int individualMrRecord                  = rs.getInt(    2 );                
                String mannerCollide                    = rs.getString( 3 );                
                int unitNumber                          = rs.getInt(    4 );    
                String unitTypeDescription              = rs.getString( 5 );    
                String vehicleUseDescription            = rs.getString( 6 );    
                String vehicleStateDescription          = rs.getString( 7 );    
                String travelDirectionDescription       = rs.getString( 8 );    
                String preCollisionActionDescription    = rs.getString( 9 );    
                int axelsText                           = rs.getInt(    10 );   
                int occupantsNumber                     = rs.getInt(    11 );   
                String towedIndicator                   = rs.getString( 12 );   
                String fireIndicator                    = rs.getString( 13 );   
                String latitudeDecimalNumber            = rs.getString( 14 );   
                String longitudeDecimalNumber           = rs.getString( 15 );   
                String latLongText                      = rs.getString( 16 );    
                int riskScorePrevious                   = rs.getInt(    17 );   
                int riskScoreCurrent                    = rs.getInt(    18 );   
                Date calculationDate                    = rs.getDate(   19 );   
                
                AccidentIntersectionBean accident       = new AccidentIntersectionBean();
                accident.setId( id );
                accident.setIndividualMrRecord( individualMrRecord );
                accident.setMannerCollide( mannerCollide );
                accident.setUnitNumber( unitNumber );
                accident.setUnitTypeDescription( unitTypeDescription );
                accident.setVehicleUseDescription( vehicleUseDescription );
                accident.setVehicleStateDescription( vehicleStateDescription );
                accident.setTravelDirectionDescription( travelDirectionDescription );
                accident.setPreCollisionActionDescription( preCollisionActionDescription );
                accident.setAxelsText( axelsText );
                accident.setOccupantsNumber( occupantsNumber );
                accident.setTowedIndicator( towedIndicator );
                accident.setFireIndicator( fireIndicator );
                accident.setLatitudeDecimalNumber( latitudeDecimalNumber );
                accident.setLongitudeDecimalNumber( longitudeDecimalNumber );
                accident.setLatLongText( latLongText );
                accident.setRiskScorePrevious( riskScorePrevious );
                accident.setRiskScoreCurrent( riskScoreCurrent );
                accident.setCalculationDate( calculationDate );
                
                accidents.add( accident );
            }
        }
        catch( Exception e )
        {
            System.out.println( "AccidentDAO.getAllAccidentIntersectionsInIdOrderDAO() threw an Exception while attempting to collect all AccidentIntersectionBean instances, e=" + e );
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    rs.close();
                }
                if( ps != null )
                {
                    ps.close();
                }
                if( con != null && !con.isClosed() )
                {
                    con.close();
                }
            }
            catch( Exception ee )
            {
                System.out.println( "AccidentDAO.getAllAccidentIntersectionsInIdOrderDAO() threw an Exception while attempting to close resources, ee=" + ee );
            }
        }
        
        return accidents;
	}


    @Deprecated	
	public HazardCluster getHazardClustersBasedOnDistanceDAO( double separation, String units )
	{
	    HazardCluster hazardCluster    = new HazardCluster();
	    Connection con                 = null;
	    PreparedStatement ps           = null;
	    ResultSet rs                   = null;
	    String query                   = HazardCluster.getHazardClusterProximityQuery( separation );
	    
	    try
	    {
	        con    = this.getDb().getDatabaseConnectionUsingDriverManager();
	        ps     = con.prepareStatement( query );
	        rs     = ps.executeQuery();
	        
	        while( rs.next() )
	        {
	            long point1Id      = rs.getLong( 1 );
	            double latitude1   = rs.getDouble( 2 );
	            double longitude1  = rs.getDouble( 3 );
	            long point2Id      = rs.getLong( 4 );
	            double latitude2   = rs.getDouble( 5 );
	            double longitude2  = rs.getDouble( 6 );
	            
	            GeoLocation point1 = new GeoLocation();
	            point1.setId( point1Id );
	            point1.setLatitude( latitude1 );
	            point1.setLongitude( longitude1 );
	            
	            GeoLocation point2 = new GeoLocation();
	            point2.setId( point2Id );
	            point2.setLatitude( latitude2 );
	            point2.setLongitude( longitude2 );
	            
	            GeoLocationPair aPair = new GeoLocationPair();
	            aPair.setPoint1( point1 );
	            aPair.setPoint2( point2 );
	            
	            hazardCluster.addOneHazardGeoLocationPair( aPair );
	        }
	    }
	    catch( Exception e )
	    {
	        System.out.println( "AccidentDAO.getHazardClustersBasedOnDistance() threw an Exception while attempting to collect HazardClusters, e=" + e );
	    }
	    finally
	    {
	        try
	        {
	            if( rs != null )
	            {
	                rs.close();
	            }
	            if( ps != null )
	            {
	                ps.close();
	            }
	            if( con != null && !con.isClosed() )
	            {
	                con.close();
	            }
	        }
	        catch( Exception ee )
	        {
	            System.out.println( "AccidentDAO.getHazardClustersBasedOnDistance() threw an Exception while attempting to close resources, ee=" + ee );
	        }
	    }
	    
	    return hazardCluster;
	}
	
	
	public int insertOneLineIntoTheIntersectionTableDAO( String[] fields, String originalLine ) throws Exception
	{
		int outcome 			= 0;
		Connection con 			= null;
		PreparedStatement ps	= null;
		String insert			= AccidentIntersectionBean.getAccidentIntersectionInsertQuery();
		
		try
		{
			con = this.getDb().getDatabaseConnectionUsingDriverManager();
			ps  = con.prepareStatement( insert );
					
			try
			{
				int individualMrRecord					= -1;
				
				try
				{
					individualMrRecord = Integer.parseInt( fields[ 0 ].trim() );
				}
				catch( NumberFormatException nfe )
				{
					System.out.println( "Bad individualMrRecord, fields[ 0 ]=" + fields[ 0 ] );
				}
				
				String mannerCollisionDescription		= fields[ 1 ]; 
				int unitNumber							= -1;
				
				try
				{
					unitNumber = Integer.parseInt( fields[ 2 ].trim() );
				}
				catch( NumberFormatException nfe )
				{
					System.out.println( "Bad unitNumber, fields[ 2 ]" + fields[ 2 ] );
				}
				
				String unitTypeDescription				= fields[ 3 ];
				String vehicleUseDescription			= fields[ 4 ];
				String vehicleStateDescription			= fields[ 5 ];
				String travelDirectionDescripton		= fields[ 6 ];
				String preCollisionActionDescription	= fields[ 7 ];
				
				int axelsTxt							= -1;
				
				try
				{
					axelsTxt = Integer.parseInt( fields[ 8 ].trim() );
				}
				catch( NumberFormatException nfe )
				{
					System.out.println( "Bad axelsTxt, fields[ 8 ]=" + fields[ 8 ] );
				}
				
				int occupantsNumber						= -1;
				
				try
				{
					occupantsNumber = Integer.parseInt( fields[ 9 ].trim() );	
				}
				catch( NumberFormatException nfe )
				{
					System.out.println( "Bad occupantsNumber, fields[ 9 ]=" + fields[ 9 ] );
				}
				
				String towedIndicator					= fields[ 10 ];
				String fireIndicator					= fields[ 11 ];
				double latitudeDecimalNumber			= 0.0;
				
				try
				{
					latitudeDecimalNumber = Double.parseDouble( fields[ 12 ] );
				}
				catch( NumberFormatException nfe )
				{
					System.out.println( "bad latitude, fields[ 12 ]=" + fields[ 12 ] );
				}
				
				double longitudeDecimalNumber			= 0.0;
				
				try
				{
					longitudeDecimalNumber = Double.parseDouble( fields[ 13 ] );
				}
				catch( NumberFormatException nfe )
				{
					System.out.println( "bad longitude, fields[ 13 ]=" + fields[ 13 ] );
				}
				catch( ArrayIndexOutOfBoundsException aioobe )
				{
					System.out.println( aioobe );
				}
				
				String latLongText						= fields[ 14 ];
				latLongText                             = latLongText.replaceAll( "\"", "" );
				
				if( "(0.0,0.0)".equals( latLongText ) )
				{
				    return 0;
				}
				
				java.sql.Date calculationDate   		= new java.sql.Date( Calendar.getInstance().getTime().getTime() );

				ps.setInt( 1, individualMrRecord );
				ps.setString( 2, mannerCollisionDescription );
				ps.setInt( 3, unitNumber );
				ps.setString( 4, unitTypeDescription );
				ps.setString( 5, vehicleUseDescription );
				ps.setString( 6, vehicleStateDescription );
				ps.setString( 7, travelDirectionDescripton );
				ps.setString( 8, preCollisionActionDescription );
				ps.setInt( 9, axelsTxt );
				ps.setInt( 10, occupantsNumber );
				ps.setString( 11, towedIndicator );
				ps.setString( 12,fireIndicator );
				ps.setDouble( 13, latitudeDecimalNumber );
				ps.setDouble( 14, longitudeDecimalNumber );
				ps.setString( 15, latLongText );
				ps.setInt(    16, -1 );
				ps.setInt(    17, -1 );
				ps.setDate(   18, calculationDate );
				
				outcome 								= ps.executeUpdate();
				
				if( outcome != 1 )
				{
					System.out.println( "org.tomhunter.accidentchek.intersection.dao.AccidentDAO.insertOneLineIntoTheIntersectionTableDAO() INSERT FAILED" );
				}
			}
			catch( NumberFormatException nfe )
			{
				System.out.println( "org.tomhunter.accidentchek.intersection.dao.AccidentDAO.insertOneLineIntoTheIntersectionTableDAO() threw a NumberFormatException , nfe=" + nfe + ", originalLine=" + originalLine );
			}			
		}
		catch( Exception e )
		{
			System.out.println( "org.tomhunter.accidentchek.intersection.dao.AccidentDAO.insertOneLineIntoTheIntersectionTableDAO() threw an Exception, e=" + e + ", originalLine=" + originalLine );
		}
		finally
		{
			try
			{
				if( ps != null )
				{
					ps.close();
				}
				if( con != null && !con.isClosed() )
				{
					con.close();
				}
			}
			catch( Exception ee )
			{
			}
		}
		
		return outcome;
	}
	
	
    public int insertOneLineIntoTheSpatialIntersectionTableDAO( String[] fields, String originalLine ) throws Exception
    {
        if( fields.length < 15 )
        {
            return 0;
        }
            
        int outcome             = 0;
        Connection con          = null;
        PreparedStatement ps    = null;
        String insert           = SpatialIntersection.insertIntoSpatialIntersectionQuery( fields[ 12 ], fields[ 13 ] );
        
        try
        {
            con = this.getDb().getDatabaseConnectionUsingDriverManager();
            ps  = con.prepareStatement( insert );
                    
            try
            {
                int individualMrRecord                  = -1;
                
                try
                {
                    individualMrRecord = Integer.parseInt( fields[ 0 ].trim() );
                }
                catch( NumberFormatException nfe )
                {
                    System.out.println( "Bad individualMrRecord, fields[ 0 ]=" + fields[ 0 ] );
                }
                
                String mannerCollisionDescription       = fields[ 1 ]; 
                int unitNumber                          = -1;
                
                try
                {
                    unitNumber = Integer.parseInt( fields[ 2 ].trim() );
                }
                catch( NumberFormatException nfe )
                {
                    System.out.println( "Bad unitNumber, fields[ 2 ]" + fields[ 2 ] );
                }
                
                String unitTypeDescription              = fields[ 3 ];
                String vehicleUseDescription            = fields[ 4 ];
                String vehicleStateDescription          = fields[ 5 ];
                String travelDirectionDescripton        = fields[ 6 ];
                String preCollisionActionDescription    = fields[ 7 ];
                
                int axelsTxt                            = -1;
                
                try
                {
                    axelsTxt = Integer.parseInt( fields[ 8 ].trim() );
                }
                catch( NumberFormatException nfe )
                {
                    System.out.println( "Bad axelsTxt, fields[ 8 ]=" + fields[ 8 ] );
                }
                
                int occupantsNumber                     = -1;
                
                try
                {
                    occupantsNumber = Integer.parseInt( fields[ 9 ].trim() );   
                }
                catch( NumberFormatException nfe )
                {
                    System.out.println( "Bad occupantsNumber, fields[ 9 ]=" + fields[ 9 ] );
                }
                
                String towedIndicator                   = fields[ 10 ];
                String fireIndicator                    = fields[ 11 ];
                String latLongText                      = fields[ 14 ];
                
                latLongText                             = latLongText.replaceAll( "\"", "" );
                
                if( "(0.0,0.0)".equals( latLongText ) || "(0, 0)".equals( latLongText ) )
                {
                    return 0;
                }
                
                java.sql.Date calculationDate           = new java.sql.Date( Calendar.getInstance().getTime().getTime() );

                ps.setInt(      1, individualMrRecord );
                ps.setString(   2, mannerCollisionDescription );
                ps.setInt(      3, unitNumber );
                ps.setString(   4, unitTypeDescription );
                ps.setString(   5, vehicleUseDescription );
                ps.setString(   6, vehicleStateDescription );
                ps.setString(   7, travelDirectionDescripton );
                ps.setString(   8, preCollisionActionDescription );
                ps.setInt(      9, axelsTxt );
                ps.setInt(      10, occupantsNumber );
                ps.setString(   11, towedIndicator );
                ps.setString(   12, fireIndicator );
                ps.setString(   13, latLongText );
                ps.setInt(      14, -1 );
                ps.setInt(      15, -1 );
                ps.setDate(     16, calculationDate );
                
                outcome                                 = ps.executeUpdate();
                
                if( outcome != 1 )
                {
                    System.out.println( "org.tomhunter.accidentchek.intersection.dao.AccidentDAO.insertOneLineIntoTheSpatialIntersectionTableDAO() INSERT FAILED" );
                }
            }
            catch( NumberFormatException nfe )
            {
                System.out.println( "org.tomhunter.accidentchek.intersection.dao.AccidentDAO.insertOneLineIntoTheSpatialIntersectionTableDAO() threw a NumberFormatException , nfe=" + nfe + ", originalLine=" + originalLine );
            }           
        }
        catch( Exception e )
        {
            System.out.println( "org.tomhunter.accidentchek.intersection.dao.AccidentDAO.insertOneLineIntoTheSpatialIntersectionTableDAO() threw an Exception, e=" + e + ", originalLine=" + originalLine );
        }
        finally
        {
            try
            {
                if( ps != null )
                {
                    ps.close();
                }
                if( con != null && !con.isClosed() )
                {
                    con.close();
                }
            }
            catch( Exception ee )
            {
            }
        }
        
        return outcome;
    }	
	
    
	public int insertOneLineIntoTheIntersectionTableDAO( AccidentBean accidentBean )
	{
		int outcome 			= 0;
		Connection con 			= null;
		PreparedStatement ps	= null;
		String insert			= AccidentBean.getIntersectionInsertQuery();
		
		try
		{
			con = this.getDb().getDatabaseConnectionUsingDriverManager();
			ps  = con.prepareStatement( insert );
			
			ps.setString( 1,  accidentBean.getIndividualMrRecord() );
			ps.setString( 2,  accidentBean.getCollisionTimeAmPm() );
			ps.setString( 3,  accidentBean.getAgencyOrder() );
			ps.setString( 4,  accidentBean.getLocalityDescription() );
			ps.setString( 5,  accidentBean.getTownshipDescription() );
			ps.setString( 6,  accidentBean.getCityDescription() );
			ps.setString( 7,  accidentBean.getMannerCollide() );
			ps.setString( 8,  accidentBean.getPrimaryFactorDescription() );
			ps.setString( 9,  accidentBean.getCountyDescription() );
			ps.setDate(   10, accidentBean.getCollisionDate() );
			
			Double collisionTime = accidentBean.getCollisionTime() ;
			
			ps.setString( 11, collisionTime.toString() );
			ps.setInt(    12, accidentBean.getMotorVehiclesInvolved()  );
			ps.setInt(    13, accidentBean.getTrailersInvolved()  );
			ps.setInt(    14, accidentBean.getInjuredNumber() );
			ps.setInt(    15, accidentBean.getDeadNumber() );
			ps.setInt(    16, accidentBean.getDeerNumber() );
			ps.setString( 17, accidentBean.getRoadwayId() );
			ps.setString( 18, accidentBean.getRoadwayNumberText() );
			ps.setString( 19, accidentBean.getRoadwayRampText() );
			ps.setString( 20, accidentBean.getSurfaceTypeCodeAndConditionsDescription() );
			ps.setString( 21, accidentBean.getInterstateName() );
			ps.setString( 22, accidentBean.getInterstateNumber() );
			ps.setString( 23, accidentBean.getInterstateMileMarkerNumber() );
			ps.setString( 24, accidentBean.getDirectionFromPointCode() );
			ps.setDouble( 25, accidentBean.getFeetFromPointNumber() );
			ps.setString( 26, accidentBean.getLatitudeDecimalNumber() );
			ps.setString( 27, accidentBean.getLongitudeDecimalNumber() );
			ps.setString( 28, accidentBean.getRoadwayClassDescription() );
			ps.setString( 29, accidentBean.getRoadTypeDescription() );
			ps.setString( 30, accidentBean.getRoadwayCharDescripton() );
			ps.setInt( 31, accidentBean.getSurfaceTypeDescripton() );
			ps.setString( 32, accidentBean.getRoadwayJunctionDescription() );
			ps.setString( 33, accidentBean.getMedianTypeDescription() );
			ps.setString( 34, accidentBean.getTrafficControlDescription()  );
			ps.setString( 35, accidentBean.getLightConditionsDescription() );
			ps.setString( 36, accidentBean.getWeatherDescription() );
			ps.setInt(    37, accidentBean.getContructionIndicator() );
			ps.setString( 38, accidentBean.getConstructionTypeDescription() );
			ps.setInt(    39, accidentBean.getHitAndRunIndicator() );
			ps.setInt(    40, accidentBean.getFireIndicator() );
			ps.setInt(    41, accidentBean.getRumbleStripIndicator() );
			ps.setInt(    42, accidentBean.getSchoolzoneIndicator() );
			ps.setInt(    43, -1 );
			ps.setInt(    44, -1 );
			ps.setDate(   45, accidentBean.getCalculationDate() );
			
			outcome 	= ps.executeUpdate();
			
			if( outcome != 1 )
			{
				System.out.println( "org.tomhunter.accidentchek.intersection.dao.AccidentDAO.insertOneLineIntoTheIntersectionTableDAO() INSERT FAILED" );
			}
		}
		catch( Exception e )
		{
			System.out.println( "org.tomhunter.accidentchek.intersection.dao.AccidentDAO.insertOneLineIntoTheIntersectionTableDAO() threw an Exception, e=" + e );
		}
		finally
		{
			try
			{
				if( ps != null )
				{
					ps.close();
				}
				if( con != null && !con.isClosed() )
				{
					con.close();
				}
			}
			catch( Exception ee )
			{
			}
		}
		
		return outcome;
	}
	
	
	
	
	@Autowired
	public void setDb(MySQLUtil db) 
	{
		this.db = db;
	}
	public MySQLUtil getDb()
	{		
		if( this.db == null )
		{
			this.db = new MySQLUtil();
		}
		
		return db;
	}
	
}
