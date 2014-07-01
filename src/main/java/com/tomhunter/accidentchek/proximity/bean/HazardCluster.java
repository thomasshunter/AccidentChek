package com.tomhunter.accidentchek.proximity.bean;

import java.util.ArrayList;
import java.util.List;

public class HazardCluster
{
    private List<GeoLocationPair> hazardGeoLocationPairs = new ArrayList<GeoLocationPair>();
    
    
    public static String getHazardClusterProximityQuery( double separation )
    {
        StringBuilder query = new StringBuilder();
        
        query.append( "\n SELECT "                                                                                  );
        query.append( "\n   AI1.ID, "                                                                               );
        query.append( "\n   AI1.LATITUDE_DECIMAL_NUMBER, "                                                          );
        query.append( "\n   AI1.LONGITUDE_DECIMAL_NUMBER,"                                                          );
        query.append( "\n   AI2.ID, "                                                                               );
        query.append( "\n   AI2.LATITUDE_DECIMAL_NUMBER, "                                                          );
        query.append( "\n   AI2.LONGITUDE_DECIMAL_NUMBER "                                                          );
        query.append( "\n FROM  "                                                                                   );
        query.append( "\n   ACCIDENT_INTERSECTION AI1, "                                                            );
        query.append( "\n   ACCIDENT_INTERSECTION AI2 "                                                             );
        query.append( "\n WHERE  "                                                                                  );
        query.append( "\n ( "                                                                                       );
        query.append( "\n    (AI1.LATITUDE_DECIMAL_NUMBER - AI2.LATITUDE_DECIMAL_NUMBER < " + separation + ") "     );
        query.append( "\n    || "                                                                                   );
        query.append( "\n    (AI2.LATITUDE_DECIMAL_NUMBER - AI1.LATITUDE_DECIMAL_NUMBER < " + separation + ") "     );
        query.append( "\n )  "                                                                                      );
        query.append( "\n AND  "                                                                                    );
        query.append( "\n (  "                                                                                      );
        query.append( "\n   (AI1.LONGITUDE_DECIMAL_NUMBER - AI2.LONGITUDE_DECIMAL_NUMBER < " + separation + ") "    );
        query.append( "\n   || "                                                                                    );
        query.append( "\n   (AI2.LONGITUDE_DECIMAL_NUMBER - AI1.LONGITUDE_DECIMAL_NUMBER < " + separation + ") "    );
        query.append( "\n  )  "                                                                                     );
        query.append( "\n  AND "                                                                                    );
        query.append( "\n  AI1.LONGITUDE_DECIMAL_NUMBER <> 0.0 "                                                    );
        query.append( "\n  AND "                                                                                    );
        query.append( "\n  AI1.LATITUDE_DECIMAL_NUMBER <> 0.0 "                                                     );
        query.append( "\n  AND  "                                                                                   );
        query.append( "\n  AI1.ID BETWEEN 1 AND 100 "                                                               );
        query.append( "\n  AND "                                                                                    );
        query.append( "\n  AI2.ID BETWEEN 1 AND 100 "                                                               );
                
        return query.toString();
    }

    public List<GeoLocationPair> getHazardGeoLocationPairs()
    {
        return hazardGeoLocationPairs;
    }
    public void setHazardGeoLocationPairs(List<GeoLocationPair> hazardGeoLocationPairs)
    {
        this.hazardGeoLocationPairs = hazardGeoLocationPairs;
    }
    public void addOneHazardGeoLocationPair( GeoLocationPair aPair )
    {
        this.hazardGeoLocationPairs.add( aPair );
    }
    
}
