package com.tomhunter.accidentchek.intersection.bean;

import java.io.Serializable;
import java.sql.Date;

import com.vividsolutions.jts.geom.Geometry;

@SuppressWarnings("serial")
public class SpatialIntersection extends MinimalSpatialIntersection implements Serializable
{
    private int individualMrRecord;                 // 1            
    private String mannerCollide;                   // 2            
    private int unitNumber;                         // 3
    private String unitTypeDescription;             // 4
    private String vehicleUseDescription;           // 5
    private String vehicleStateDescription;         // 6
    private String travelDirectionDescription;      // 7
    private String preCollisionActionDescription;   // 8
    private int axelsText;                          // 9
    private int occupantsNumber;                    // 10
    private String towedIndicator;                  // 11
    private String fireIndicator;                   // 12
    private Geometry gpsLocation;                   // 13
    private String latLongText;                     // 14

    private int riskScorePrevious;                  // 16
    private int riskScoreCurrent;                   // 17
    private Date calculationDate;                   // 18
    
    
    @Override
    public String toString()
    {
        StringBuilder out = new StringBuilder();
        
        out.append( "\n-----------------------------------" );
        out.append( "\n    AccidentIntersectionBean       " );
        out.append( "\n-----------------------------------" );
        out.append( "\n id                               =" + super.id );
        out.append( "\n individualMrRecord               =" + individualMrRecord );
        out.append( "\n mannerCollide                    =" + mannerCollide );
        out.append( "\n unitNumber                       =" + unitNumber );
        out.append( "\n unitTypeDescription              =" + unitTypeDescription );
        out.append( "\n vehicleUseDescription            =" + vehicleUseDescription );
        out.append( "\n vehicleStateDescription          =" + vehicleStateDescription );
        out.append( "\n travelDirectionDescription       =" + travelDirectionDescription );
        out.append( "\n preCollisionActionDescription    =" + preCollisionActionDescription );
        out.append( "\n axelsText                        =" + axelsText );
        out.append( "\n occupantsNumber                  =" + occupantsNumber );
        out.append( "\n towedIndicator                   =" + towedIndicator );
        out.append( "\n fireIndicator                    =" + fireIndicator );
        out.append( "\n gpsLocation                      =" + gpsLocation );
        out.append( "\n latLongText                      =" + latLongText );
        out.append( "\n-----------------------------------" );
        out.append( "\n riskScorePrevious                =" + riskScorePrevious );
        out.append( "\n riskScoreCurrent                 =" + riskScoreCurrent );
        out.append( "\n calculationDate                  =" + calculationDate );
        out.append( "\n distance                         =" + super.distance );
        out.append( "\n latitude                         =" + super.getLatitude() );
        out.append( "\n longitude                        =" + super.getLongitude() );
        out.append( "\n-----------------------------------" );
        
        return out.toString();
    }    
    
    @Deprecated
    public static String selectAllGeoLocationsBasedOnDistanceFromACenterPointBasedOnRadiusQuery()
    {
        StringBuilder select = new StringBuilder();
        
        select.append( "\n SELECT "                                                                         );
        select.append( "\n     st_distance((POINT( ? )), GPS_LOCATION) as DISTANCE, "                       ); // 1
        select.append( "\n     ID, "                                                                        ); // 2
        select.append( "\n     INDIVIDUAL_MR_RECORD, "                                                      ); // 3
        select.append( "\n     MANNER_COLLISION_DESCR, "                                                    ); // 4
        select.append( "\n     UNIT_NUMBER, "                                                               ); // 5
        select.append( "\n     UNIT_TYPE_DESCR, "                                                           ); // 6
        select.append( "\n     VEHICLE_USE_DESCR, "                                                         ); // 7
        select.append( "\n     VEHICLE_STATE_DESCR, "                                                       ); // 8
        select.append( "\n     TRAVEL_DIRECTION_DESCR, "                                                    ); // 9
        select.append( "\n     PRE_COLLISION_ACTION_DESCR, "                                                ); // 10
        select.append( "\n     AXELS_TXT, "                                                                 ); // 11
        select.append( "\n     OCCUPANTS_NUMBER, "                                                          ); // 12
        select.append( "\n     TOWED_INDICATOR, "                                                           ); // 13
        select.append( "\n     FIRE_INDICATOR,  "                                                           ); // 14
        select.append( "\n     GPS_LOCATION, "                                                              ); // 15
        select.append( "\n     LAT_LONG_TEXT, "                                                             ); // 16
        select.append( "\n     RISK_SCORE_PREVIOUS, "                                                       ); // 17
        select.append( "\n     RISK_SCORE_CURRENT, "                                                        ); // 18
        select.append( "\n     CALCULATION_DATE "                                                           ); // 19
        select.append( "\n FROM "                                                                           );
        select.append( "\n     SPATIAL_INTERSECTION "                                                       );
        select.append( "\n WHERE "                                                                          );
        select.append( "\n     DISTANCE <= ? "                                                              );
        select.append( "\n ORDER BY "                                                                       );
        select.append( "\n     DISTANCE "                                                                   );
        
        return select.toString();
    }
    
    
    public static String selectAllGeoLocationsInsideRadiusMBRUsingTwoPointsAsCornersQuery(  double latStart, double lonStart, double latEnd, double lonEnd, double dist  )
    {
        StringBuilder query = new StringBuilder();
        
        
        query.append( "\n SET @latStart = ?; "                                                                                      );
        query.append( "\n SET @lonStart = ?;"                                                                                       );
        query.append( "\n SET @latEnd   = ?; "                                                                                      );
        query.append( "\n SET @lonEnd   = ?; "                                                                                      );   
        query.append( "\n SET @dist = " + dist + ";"                                                                                ); // Miles
        query.append( "\n "                                                                                                         );
        query.append( "\n SELECT "                                                                                                  );
        query.append( "\n   AsText(GPS_LOCATION) AS LOCATION, "                                                                     );
        query.append( "\n   ID "                                                                                                    );
        query.append( "\n FROM "                                                                                                    );
        query.append( "\n   SPATIAL_INTERSECTION "                                                                                  );
        query.append( "\n WHERE "                                                                                                   );
        query.append( "\n   st_within(GPS_LOCATION, envelope(linestring(point(@lonStart, @latStart), point(@lonEnd, @latEnd)))) "   );
        query.append( "\n ORDER BY "                                                                                        );
        query.append( "\n   st_distance(point(@lon, @lat), GPS_LOCATION) limit 100;"                                        );
        
        return query.toString();
    }
    
    
    
    public static String selectAllGeoLocationsInsideRadiusMBRUsingStDistanceQuery( double lat, double lon, double distance )
    {
        StringBuilder query = new StringBuilder();
        
        query.append( "\n SET @lat =  " + lat + ";"                                                                         );
        query.append( "\n SET @lon =  " + lon + ";"                                                                         );
        query.append( "\n SET @dist = " + distance + ";"                                                                    ); // Miles
        query.append( "\n SET @rlon1 = @lon-@dist/abs(cos(radians(@lat))*69); "                                             );
        query.append( "\n SET @rlon2 = @lon+@dist/abs(cos(radians(@lat))*69); "                                             );
        query.append( "\n SET @rlat1 = @lat-(@dist/69); "                                                                   );
        query.append( "\n SET @rlat2 = @lat+(@dist/69); "                                                                   );
        query.append( "\n "                                                                                                 );
        query.append( "\n SELECT "                                                                                          );
        query.append( "\n   AsText(GPS_LOCATION) AS LOCATION, "                                                             );
        query.append( "\n   ID "                                                                                            );
        query.append( "\n FROM "                                                                                            );
        query.append( "\n   SPATIAL_INTERSECTION "                                                                          );
        query.append( "\n WHERE "                                                                                           );
        query.append( "\n   st_within(GPS_LOCATION, envelope(linestring(point(@rlon1, @rlat1), point(@rlon2, @rlat2)))) "   );
        query.append( "\n ORDER BY "                                                                                        );
        query.append( "\n   st_distance(point(@lon, @lat), GPS_LOCATION) limit 100;"                                        );
        
        //select astext(shape), name from waypoints
       // where st_within(shape, envelope(linestring(point(@rlon1, @rlat1), point(@rlon2, @rlat2))))
       // order by st_distance(point(@lon, @lat), shape) limit  10;
        
        return query.toString();
    }
    
    
    public static String getDistanceUsingMBRQuery( double lon, double lat, double radiusInMiles )
    {
        StringBuilder query    = new StringBuilder();
        
        double radius      = radiusInMiles/69;
        double x1          = lat - radius; 
        double y1          = lon - radius;
        double x2          = lat + radius; 
        double y2          = lon - radius;
        double x3          = lat + radius; 
        double y3          = lon + radius;
        double x4          = lat - radius; 
        double y4          = lon + radius;
        String polygonStr  = x1 + " " + y1 + "," + x2 + " " + y2 + "," + x3 + " " + y3 + "," + x4 + " " + y4 + "," + x1 + " " + y1;
        String pointStr    = lat + " " + lon;

        query.append( "\n SELECT "                                                                                   );
        query.append( "\n   id, "                                                                                    );
        query.append( "\n   AsText(GPS_LOCATION), "                                                                  );
        query.append( "\n   SQRT(POW( ABS( X(GPS_LOCATION) - " + lat + "), 2) +"                                     );
        query.append( "\n   POW( ABS(Y(GPS_LOCATION) - " + lon + "), 2 )) AS distance"                               );
        query.append( "\n FROM "                                                                                     );
        query.append( "\n   SPATIAL_INTERSECTION "                                                                   );
        query.append( "\n WHERE Intersects( GPS_LOCATION, GeomFromText('POLYGON((" + polygonStr + "))'))"            );
        query.append( "\n AND (SQRT(POW( ABS( X(GPS_LOCATION) - X(GeomFromText('POINT(" + pointStr + ")'))), 2) +"   );
        query.append( "\n POW( ABS(Y(GPS_LOCATION) - Y(GeomFromText('POINT(" + pointStr + ")'))), 2 ))) < " + radius );
        query.append( "\n order by distance"                                                                         );

        return query.toString();
    }
    
    public static String insertIntoSpatialIntersectionQuery( String lat, String lon )
    {
        StringBuilder insert = new StringBuilder();
        
        insert.append( "\n INSERT INTO SPATIAL_INTERSECTION "                       );
        insert.append( "\n ( "                                                      );
        insert.append( "\n     INDIVIDUAL_MR_RECORD, "                              ); // 1
        insert.append( "\n     MANNER_COLLISION_DESCR, "                            ); // 2
        insert.append( "\n     UNIT_NUMBER, "                                       ); // 3
        insert.append( "\n     UNIT_TYPE_DESCR, "                                   ); // 4
        insert.append( "\n     VEHICLE_USE_DESCR, "                                 ); // 5
        insert.append( "\n     VEHICLE_STATE_DESCR, "                               ); // 6
        insert.append( "\n     TRAVEL_DIRECTION_DESCR, "                            ); // 7
        insert.append( "\n     PRE_COLLISION_ACTION_DESCR, "                        ); // 8
        insert.append( "\n     AXELS_TXT, "                                         ); // 9
        insert.append( "\n     OCCUPANTS_NUMBER, "                                  ); // 10
        insert.append( "\n     TOWED_INDICATOR, "                                   ); // 11
        insert.append( "\n     FIRE_INDICATOR,  "                                   ); // 12
        insert.append( "\n     GPS_LOCATION, "                                      ); // 13
        insert.append( "\n     LAT_LONG_TEXT, "                                     ); 
        insert.append( "\n     RISK_SCORE_PREVIOUS, "                               ); // 14
        insert.append( "\n     RISK_SCORE_CURRENT, "                                ); // 15
        insert.append( "\n     CALCULATION_DATE "                                   ); // 16
        insert.append( "\n ) "                                                      );
        insert.append( "\n VALUES "                                                 );
        insert.append( "\n ( "                                                      );
        insert.append( "\n       ?, "                                               ); // 1
        insert.append( "\n       ?, "                                               ); // 2
        insert.append( "\n       ?, "                                               ); // 3
        insert.append( "\n       ?, "                                               ); // 4
        insert.append( "\n       ?, "                                               ); // 5
        insert.append( "\n       ?, "                                               ); // 6
        insert.append( "\n       ?, "                                               ); // 7
        insert.append( "\n       ?, "                                               ); // 8
        insert.append( "\n       ?, "                                               ); // 9
        insert.append( "\n       ?, "                                               ); // 10
        insert.append( "\n       ?, "                                               ); // 11
        insert.append( "\n       ?, "                                               ); // 12
        insert.append( "\n       GeomFromText('POINT(" + lat + " " + lon + ")'), "  );
        insert.append( "\n       ?, "                                               ); // 13
        insert.append( "\n       ?, "                                               ); // 14
        insert.append( "\n       ?, "                                               ); // 15
        insert.append( "\n       ?  "                                               ); // 16
        insert.append( "\n ) "                                                      );
        
        return insert.toString();
    }

    public void setId(int id)
    {
        super.setId( id );
    }

    public int getIndividualMrRecord()
    {
        return individualMrRecord;
    }
    public void setIndividualMrRecord(int individualMrRecord)
    {
        this.individualMrRecord = individualMrRecord;
    }

    public String getMannerCollide()
    {
        return mannerCollide;
    }
    public void setMannerCollide(String mannerCollide)
    {
        this.mannerCollide = mannerCollide;
    }

    public int getUnitNumber()
    {
        return unitNumber;
    }
    public void setUnitNumber(int unitNumber)
    {
        this.unitNumber = unitNumber;
    }

    public String getUnitTypeDescription()
    {
        return unitTypeDescription;
    }
    public void setUnitTypeDescription(String unitTypeDescription)
    {
        this.unitTypeDescription = unitTypeDescription;
    }

    public String getVehicleUseDescription()
    {
        return vehicleUseDescription;
    }
    public void setVehicleUseDescription(String vehicleUseDescription)
    {
        this.vehicleUseDescription = vehicleUseDescription;
    }

    public String getVehicleStateDescription()
    {
        return vehicleStateDescription;
    }
    public void setVehicleStateDescription(String vehicleStateDescription)
    {
        this.vehicleStateDescription = vehicleStateDescription;
    }

    public String getTravelDirectionDescription()
    {
        return travelDirectionDescription;
    }
    public void setTravelDirectionDescription(String travelDirectionDescription)
    {
        this.travelDirectionDescription = travelDirectionDescription;
    }

    public String getPreCollisionActionDescription()
    {
        return preCollisionActionDescription;
    }
    public void setPreCollisionActionDescription(String preCollisionActionDescription)
    {
        this.preCollisionActionDescription = preCollisionActionDescription;
    }

    public int getAxelsText()
    {
        return axelsText;
    }
    public void setAxelsText(int axelsText)
    {
        this.axelsText = axelsText;
    }

    public int getOccupantsNumber()
    {
        return occupantsNumber;
    }
    public void setOccupantsNumber(int occupantsNumber)
    {
        this.occupantsNumber = occupantsNumber;
    }

    public String getTowedIndicator()
    {
        return towedIndicator;
    }
    public void setTowedIndicator(String towedIndicator)
    {
        this.towedIndicator = towedIndicator;
    }

    public String getFireIndicator()
    {
        return fireIndicator;
    }
    public void setFireIndicator(String fireIndicator)
    {
        this.fireIndicator = fireIndicator;
    }

    public String getLatLongText()
    {
        return this.latLongText;
    }
    public void setLatLongText(String latLongText)
    {
        this.latLongText = latLongText;
    }

    public int getRiskScorePrevious()
    {
        return riskScorePrevious;
    }
    public void setRiskScorePrevious(int riskScorePrevious)
    {
        this.riskScorePrevious = riskScorePrevious;
    }

    public int getRiskScoreCurrent()
    {
        return riskScoreCurrent;
    }
    public void setRiskScoreCurrent(int riskScoreCurrent)
    {
        this.riskScoreCurrent = riskScoreCurrent;
    }

    public Date getCalculationDate()
    {
        return calculationDate;
    }
    public void setCalculationDate(Date calculationDate)
    {
        this.calculationDate = calculationDate;
    }

    public Geometry getGpsLocation()
    {
        return gpsLocation;
    }
    public void setGpsLocation(Geometry gpsLocation)
    {
        this.gpsLocation = gpsLocation;
    }

    public void setDistance(double distance)
    {
        super.setDistance( distance );
    }



  
}
