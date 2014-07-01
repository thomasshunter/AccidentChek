package com.tomhunter.accidentchek.intersection.bean;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class AccidentIntersectionBean implements Serializable
{
    private int id;
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
    private String latitudeDecimalNumber;           // 13
    private String longitudeDecimalNumber;          // 14
    private String latLongText;                     // 15 

    private int riskScorePrevious;                  // 16
    private int riskScoreCurrent;                   // 17
    private Date calculationDate;                   // 18
    
    
    public String toJsonString()
    {
        StringBuilder json = new StringBuilder();
        
        
        
        
        return json.toString();
    }
    

    @Override
    public String toString()
    {
        StringBuilder out = new StringBuilder();
        
        out.append( "\n-----------------------------------" );
        out.append( "\n    AccidentIntersectionBean       " );
        out.append( "\n-----------------------------------" );
        out.append( "\n id                               =" + id );
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
        out.append( "\n latitudeDecimalNumber            =" + latitudeDecimalNumber );
        out.append( "\n longitudeDecimalNumber           =" + longitudeDecimalNumber );
        out.append( "\n latLongText                      =" + latLongText );
        out.append( "\n-----------------------------------" );
        out.append( "\n riskScorePrevious                =" + riskScorePrevious );
        out.append( "\n riskScoreCurrent                 =" + riskScoreCurrent );
        out.append( "\n calculationDate                  =" + calculationDate );
        out.append( "\n-----------------------------------" );
        
        return out.toString();
    }
    
    public static String selectAllAccidentIntersectionBeansQuery()
    {
        StringBuilder select = new StringBuilder();

        select.append( "\n SELECT "                                                 );
        select.append( "\n    `ID`, "                                               ); // 1
        select.append( "\n    `INDIVIDUAL_MR_RECORD`, "                             ); // 2
        select.append( "\n    `MANNER_COLLISION_DESCR`, "                           ); // 3
        select.append( "\n    `UNIT_NUMBER`, "                                      ); // 4
        select.append( "\n    `UNIT_TYPE_DESCR`, "                                  ); // 5
        select.append( "\n    `VEHICLE_USE_DESCR`, "                                ); // 6
        select.append( "\n    `VEHICLE_STATE_DESCR`, "                              ); // 7
        select.append( "\n    `TRAVEL_DIRECTION_DESCR`, "                           ); // 8
        select.append( "\n    `PRE_COLLISION_ACTION_DESCR`, "                       ); // 9
        select.append( "\n    `AXELS_TXT`, "                                        ); // 10
        select.append( "\n    `OCCUPANTS_NUMBER`, "                                 ); // 11
        select.append( "\n    `TOWED_INDICATOR`, "                                  ); // 12
        select.append( "\n    `FIRE_INDICATOR`, "                                   ); // 13
        select.append( "\n    `LATITUDE_DECIMAL_NUMBER`, "                          ); // 14
        select.append( "\n    `LONGITUDE_DECIMAL_NUMBER`, "                         ); // 15
        select.append( "\n    `LAT_LONG_TEXT`, "                                    ); // 16
        select.append( "\n    `RISK_SCORE_PREVIOUS`, "                              ); // 17
        select.append( "\n    `RISK_SCORE_CURRENT`, "                               ); // 18
        select.append( "\n    `CALCULATION_DATE` "                                  ); // 19
        select.append( "\n FROM "                                                   );
        select.append( "\n     ACCIDENT_INTERSECTION "                              );
        select.append( "\n WHERE "                                                  );
        select.append( "\n     ID BETWEEN ? AND ? "                                 ); // Bind Variables #1, #2
        select.append( "\n     AND "                                                );
        select.append( "\n     LATITUDE_DECIMAL_NUMBER <> 0.0 "                     );
        select.append( "\n     AND "                                                );
        select.append( "\n     LATITUDE_DECIMAL_NUMBER <> 1.0 "                     );
        select.append( "\n     AND "                                                );
        select.append( "\n     LONGITUDE_DECIMAL_NUMBER <> 0.0 "                    );
        select.append( "\n     AND"                                                 );
        select.append( "\n     LONGITUDE_DECIMAL_NUMBER <> 1.0 "                    );
        select.append( "\n ORDER BY "                                               );
        select.append( "\n     INDIVIDUAL_MR_RECORD "                               );
        
        return select.toString();
    }
    
    
    public static String getAccidentIntersectionInsertQuery()
    {
        StringBuilder insert = new StringBuilder();

        insert.append( "\n INSERT INTO `ACCIDENTCHEK`.`ACCIDENT_INTERSECTION` "     );
        insert.append( "\n ( "                                                      );
        insert.append( "\n    `INDIVIDUAL_MR_RECORD`, "                             ); // 1
        insert.append( "\n    `MANNER_COLLISION_DESCR`, "                           ); // 2
        insert.append( "\n    `UNIT_NUMBER`, "                                      ); // 3
        insert.append( "\n    `UNIT_TYPE_DESCR`, "                                  ); // 4
        insert.append( "\n    `VEHICLE_USE_DESCR`, "                                ); // 5
        insert.append( "\n    `VEHICLE_STATE_DESCR`, "                              ); // 6
        insert.append( "\n    `TRAVEL_DIRECTION_DESCR`, "                           ); // 7
        insert.append( "\n    `PRE_COLLISION_ACTION_DESCR`, "                       ); // 8
        insert.append( "\n    `AXELS_TXT`, "                                        ); // 9
        insert.append( "\n    `OCCUPANTS_NUMBER`, "                                 ); // 10
        insert.append( "\n    `TOWED_INDICATOR`, "                                  ); // 11
        insert.append( "\n    `FIRE_INDICATOR`, "                                   ); // 12
        insert.append( "\n    `LATITUDE_DECIMAL_NUMBER`, "                          ); // 13
        insert.append( "\n    `LONGITUDE_DECIMAL_NUMBER`, "                         ); // 14
        insert.append( "\n    `LAT_LONG_TEXT`, "                                    ); // 15
        insert.append( "\n    `RISK_SCORE_PREVIOUS`, "                              ); // 16
        insert.append( "\n    `RISK_SCORE_CURRENT`, "                               ); // 17
        insert.append( "\n    `CALCULATION_DATE` "                                  ); // 18
        insert.append( "\n )   "                                                    );
        insert.append( "\n VALUES "                                                 );
        insert.append( "\n (   "                                                    );  
        insert.append( "\n    ?, "                                                  ); // 1
        insert.append( "\n    ?, "                                                  ); // 2
        insert.append( "\n    ?, "                                                  ); // 3
        insert.append( "\n    ?, "                                                  ); // 4
        insert.append( "\n    ?, "                                                  ); // 5
        insert.append( "\n    ?, "                                                  ); // 6
        insert.append( "\n    ?, "                                                  ); // 7
        insert.append( "\n    ?, "                                                  ); // 8
        insert.append( "\n    ?, "                                                  ); // 9
        insert.append( "\n    ?, "                                                  ); // 10
        insert.append( "\n    ?, "                                                  ); // 11
        insert.append( "\n    ?, "                                                  ); // 12
        insert.append( "\n    ?, "                                                  ); // 13
        insert.append( "\n    ?, "                                                  ); // 14
        insert.append( "\n    ?, "                                                  ); // 15
        insert.append( "\n    ?, "                                                  ); // 16
        insert.append( "\n    ?, "                                                  ); // 17
        insert.append( "\n    ? "                                                   ); // 18
        insert.append( "\n )   "                                                    );
        
        return insert.toString();
    }
    
    

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
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

    public String getLatitudeDecimalNumber()
    {
        return latitudeDecimalNumber;
    }
    public void setLatitudeDecimalNumber(String latitudeDecimalNumber)
    {
        this.latitudeDecimalNumber = latitudeDecimalNumber;
    }

    public String getLongitudeDecimalNumber()
    {
        return longitudeDecimalNumber;
    }
    public void setLongitudeDecimalNumber(String longitudeDecimalNumber)
    {
        this.longitudeDecimalNumber = longitudeDecimalNumber;
    }

    public String getLatLongText()
    {
        return latLongText;
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
    
}
