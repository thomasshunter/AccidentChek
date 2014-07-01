package com.tomhunter.accidentchek.proximity.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GeoLocation implements Serializable
{
    public static final String UNITS_K = "K";
    public static final String UNITS_N = "N";
    public static final String UNITS_M = "M";
    
    private long id;
    private double latitude;
    private double longitude;
    private String label;
    private String units;
    
    @Override
    public String toString()
    {
        StringBuilder out = new StringBuilder();
        
        out.append( "\n--------------------------" );
        out.append( "\n       GeoLocation        " );
        out.append( "\n--------------------------" );
        out.append( "\n id                      =" + id );
        out.append( "\n latitude                =" + latitude );
        out.append( "\n longitude               =" + longitude );
        out.append( "\n label                   =" + label );
        out.append( "\n units                   =" + units );
        out.append( "\n--------------------------" );
        
        return out.toString();
    }
    
    public static String selectProximateIntersectionsByIdQuery()
    {
        StringBuilder query = new StringBuilder();
        
        query.append( "\n SELECT "                          );
        query.append( "\n    ID, "                          );
        query.append( "\n    ID_INTERSECTION_A, "           );
        query.append( "\n    ID_INTERSECTION_B, "           );
        query.append( "\n    SEPARATION_BETWEEN_POINTS, "   );
        query.append( "\n    UNITS, "                       );
        query.append( "\n    TIME_OF_DETERMINATION "        );
        query.append( "\n FROM "                            );
        query.append( "\n    PROXIMATE_INTERSECTIONS "      );
        query.append( "\n WHERE "                           );
        query.append( "\n    ID = ? "                       );
        
        return query.toString();
    }
    
    public static String selectProximateIntersectionsBySeparationBetweenPointsQuery()
    {
        StringBuilder query = new StringBuilder();
        
        query.append( "\n SELECT "                              );
        query.append( "\n    ID, "                              );
        query.append( "\n    ID_INTERSECTION_A, "               );
        query.append( "\n    ID_INTERSECTION_B, "               );
        query.append( "\n    SEPARATION_BETWEEN_POINTS, "       );
        query.append( "\n    UNITS, "                           );
        query.append( "\n    TIME_OF_DETERMINATION "            );
        query.append( "\n FROM "                                );
        query.append( "\n    PROXIMATE_INTERSECTIONS "          );
        query.append( "\n WHERE "                               );
        query.append( "\n    SEPARATION_BETWEEN_POINTS <  ? "   );
        query.append( "\n    AND "                              );
        query.append( "\n    UNITS = ? "                        );
        
        return query.toString();
    }
    
    public static String insertProximateIntersectionsQuery()
    {
        StringBuilder insert = new StringBuilder();
        
        insert.append( "\n INSERT INTO `ACCIDENTCHEK`.`PROXIMATE_INTERSECTIONS` "   );
        insert.append( "\n  ( "                                                     );
        insert.append( "\n    `ID_INTERSECTION_A`, "                                );
        insert.append( "\n    `ID_INTERSECTION_B`, "                                );
        insert.append( "\n    `SEPARATION_BETWEEN_POINTS`, "                        );
        insert.append( "\n    `UNITS`, "                                            );
        insert.append( "\n    `TIME_OF_DETERMINATION` "                             );
        insert.append( "\n ) "                                                      );
        insert.append( "\n VALUES "                                                 );
        insert.append( "\n ( "                                                      );
        insert.append( "\n    ?, "                                                  );
        insert.append( "\n    ?, "                                                  );
        insert.append( "\n    ?, "                                                  );
        insert.append( "\n    ?, "                                                  );
        insert.append( "\n    ? "                                                   );
        insert.append( "\n ) "                                                      );

        return insert.toString();
    }
    
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    public double getLatitude()
    {
        return latitude;
    }
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getLabel()
    {
        return label;
    }
    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getUnits()
    {
        return units;
    }
    public void setUnits(String units)
    {
        this.units = units;
    }
    
}
