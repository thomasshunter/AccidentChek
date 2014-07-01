package com.tomhunter.accidentchek.intersection.bean;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class AccidentBean implements Serializable
{
	private long id;
	private String individualMrRecord;			// 1			1
	private String collisionTimeAmPm;			// 2
	private String agencyOrder;					// 3
	private String localityDescription;			// 4
	private String townshipDescription;			// 5
	private String cityDescription;				// 6
	private String mannerCollide;				// 7			2
	private String primaryFactorDescription;	// 8
	private String countyDescription;			// 9
	private Date collisionDate;					// 10
	private double collisionTime;				// 11
	private int motorVehiclesInvolved;			// 12
	private int trailersInvolved;				// 13
	private int injuredNumber;					// 14
	private int deadNumber;						// 15
	private int deerNumber;						// 16
	private String roadwayId;					// 17
	private String roadwayNumberText;			// 18
	private String roadwayRampText;				// 19
	private String surfaceTypeCodeAndConditionsDescription;	// 20
	private String interstateName;				// 21
	private String interstateNumber;			// 22
	private String interstateMileMarkerNumber;	// 23
	private String directionFromPointCode;		// 24
	private double feetFromPointNumber;			// 25
	private String latitudeDecimalNumber;		// 26
	private String longitudeDecimalNumber;		// 27
	private String roadwayClassDescription;		// 28
	private String roadTypeDescription;			// 29
	private String roadwayCharDescripton;		// 30
	private int surfaceTypeDescripton;			// 31
	private String roadwayJunctionDescription;	// 32
	private String medianTypeDescription;		// 33
	private String trafficControlDescription;	// 34
	private String lightConditionsDescription;	// 35
	private String weatherDescription;			// 36
	private int contructionIndicator;			// 37
	private String constructionTypeDescription;	// 38
	private int hitAndRunIndicator;				// 39
	private int fireIndicator;					// 40
	private int rumbleStripIndicator;			// 41
	private int schoolzoneIndicator;			// 42 

	private int scorePredicted;					// 43
	private int scorePrevious;					// 44
	private Date calculationDate;				// 45
		
	public static String getIntersectionInsertQuery()
	{
		StringBuilder insert = new StringBuilder();
		
		insert.append( "\n INSERT INTO ACCIDENTCHEK.INTERSECTIONS " 	);
		insert.append( "\n (" 											);
		insert.append( "\n      INDIVIDUAL_MR_RECORD," 					); // 1
		insert.append( "\n      COLLISION_TIME_AM_PM," 					); // 2
		insert.append( "\n      AGENCYORIDESCR," 						); // 3
		insert.append( "\n      LOCALITYDESCR," 						); // 4
		insert.append( "\n      TOWNSHIPDESCR," 						); // 5
		insert.append( "\n      CITYDESCR," 							); // 6
		insert.append( "\n      MANNERCOLLDESCR," 						); // 7
		insert.append( "\n      PRIMARYFACTORDESCR," 					); // 8
		insert.append( "\n      COUNTYDESCR," 							); // 9
		insert.append( "\n      COLLDTE," 								); // 10
		insert.append( "\n      COLLSION_TIME," 						); // 11
		insert.append( "\n      MOTORVEHINVOLVEDNMB," 					); // 12
		insert.append( "\n      TRAILERSINVOLVEDNMB," 					); // 13
		insert.append( "\n      INJUREDNMB," 							); // 14
		insert.append( "\n      DEADNMB," 								); // 15
		insert.append( "\n      DEERNMB," 								); // 16
		insert.append( "\n      RDWYIDTXT," 							); // 17
		insert.append( "\n      RDWYNUMBERTXT," 						); // 18
		insert.append( "\n      RDWYRAMPTXT," 							); // 19
		insert.append( "\n      SURFACETYPECDE_CONDDESCR," 				); // 20
		insert.append( "\n      INTERNAMETXT," 							); // 21
		insert.append( "\n      INTERNUMBERTXT," 						); // 22
		insert.append( "\n      INTERMILEMARKNMB," 						); // 23
		insert.append( "\n      DIRFROMPOINTCDE," 						); // 24
		insert.append( "\n      FEETFROMPOINTNMB," 						); // 25
		insert.append( "\n      LATDECIMALNMB," 						); // 26
		insert.append( "\n      LONGDECIMALNMB," 						); // 27
		insert.append( "\n      RDWYCLASSDESCR," 						); // 28
		insert.append( "\n      ROADTYPEDESCR," 						); // 29
		insert.append( "\n      RDWYCHARDESCR," 						); // 30
		insert.append( "\n      SURFACETYPESCR," 						); // 31
		insert.append( "\n      RDWYJUNCTIONDESCR," 					); // 32
		insert.append( "\n      MEDIANTYPEDESCR," 						); // 33
		insert.append( "\n      TRAFFICCNTRLDESCR," 					); // 34
		insert.append( "\n      LIGHTCONDDESCR," 						); // 35
		insert.append( "\n      WEATHERDESCR," 							); // 36
		insert.append( "\n      CONSTRUCTIND," 							); // 37
		insert.append( "\n      CONSTRUCTTYPEDESCR," 					); // 38
		insert.append( "\n      HITRUNIND," 							); // 39
		insert.append( "\n      FIREIND," 								); // 40
		insert.append( "\n      RUMBLESTRIPIND," 						); // 41
		insert.append( "\n      SCHOOLZONEIND," 						); // 42
		insert.append( "\n      SCORE_PREDICTED," 						); // 43
		insert.append( "\n      SCORE_PREVIOUS," 						); // 44
		insert.append( "\n      CALCULATION_DATE" 						); // 45
		insert.append( "\n )" 											);
		insert.append( "\n VALUES" 										);
		insert.append( "\n (" 											);  
		insert.append( "\n      ?, " 									); // 1
		insert.append( "\n      ?, " 									); // 2
		insert.append( "\n      ?, " 									); // 3
		insert.append( "\n      ?, " 									); // 4
		insert.append( "\n      ?, " 									); // 5
		insert.append( "\n      ?, " 									); // 6
		insert.append( "\n      ?, " 									); // 7
		insert.append( "\n      ?, " 									); // 8
		insert.append( "\n      ?, " 									); // 9
		insert.append( "\n      ?, " 									); // 10
		insert.append( "\n      ?, " 									); // 11
		insert.append( "\n      ?, " 									); // 12
		insert.append( "\n      ?, " 									); // 13
		insert.append( "\n      ?, " 									); // 14
		insert.append( "\n      ?, " 									); // 15
		insert.append( "\n      ?, " 									); // 16
		insert.append( "\n      ?, " 									); // 17
		insert.append( "\n      ?, " 									); // 18
		insert.append( "\n      ?, " 									); // 19
		insert.append( "\n      ?, " 									); // 20
		insert.append( "\n      ?, " 									); // 21
		insert.append( "\n      ?, " 									); // 22
		insert.append( "\n      ?, " 									); // 23
		insert.append( "\n      ?, " 									); // 24
		insert.append( "\n      ?, " 									); // 25
		insert.append( "\n      ?, " 									); // 26
		insert.append( "\n      ?, " 									); // 27
		insert.append( "\n      ?, " 									); // 28
		insert.append( "\n      ?, " 									); // 29
		insert.append( "\n      ?, " 									); // 30
		insert.append( "\n      ?, " 									); // 31
		insert.append( "\n      ?, " 									); // 32
		insert.append( "\n      ?, " 									); // 33
		insert.append( "\n      ?, " 									); // 34
		insert.append( "\n      ?, " 									); // 35
		insert.append( "\n      ?, " 									); // 36
		insert.append( "\n      ?, " 									); // 37
		insert.append( "\n      ?, " 									); // 38
		insert.append( "\n      ?, " 									); // 39
		insert.append( "\n      ?, " 									); // 40
		insert.append( "\n      ?, " 									); // 41
		insert.append( "\n      ?, " 									); // 42
		insert.append( "\n      ?, " 									); // 43
		insert.append( "\n      ?, " 									); // 44
		insert.append( "\n      ? " 									); // 45
		insert.append( "\n ) " 											);
		
		return insert.toString();
	}
	
	

	public long getId()
	{
		return id;
	}
	public void setId( long id)
	{
		this.id = id;
	}

	public String getIndividualMrRecord()
	{
		return individualMrRecord;
	}

	public void setIndividualMrRecord(String individualMrRecord)
	{
		this.individualMrRecord = individualMrRecord;
	}

	public String getCollisionTimeAmPm()
	{
		return collisionTimeAmPm;
	}

	public void setCollisionTimeAmPm(String collisionTimeAmPm)
	{
		this.collisionTimeAmPm = collisionTimeAmPm;
	}

	public String getAgencyOrder()
	{
		return agencyOrder;
	}

	public void setAgencyOrder(String agencyOrder)
	{
		this.agencyOrder = agencyOrder;
	}

	public String getLocalityDescription()
	{
		return localityDescription;
	}

	public void setLocalityDescription(String localityDescription)
	{
		this.localityDescription = localityDescription;
	}

	public String getTownshipDescription()
	{
		return townshipDescription;
	}

	public void setTownshipDescription(String townshipDescription)
	{
		this.townshipDescription = townshipDescription;
	}

	public String getCityDescription()
	{
		return cityDescription;
	}

	public void setCityDescription(String cityDescription)
	{
		this.cityDescription = cityDescription;
	}

	public String getMannerCollide()
	{
		return mannerCollide;
	}

	public void setMannerCollide(String mannerCollide)
	{
		this.mannerCollide = mannerCollide;
	}

	public String getPrimaryFactorDescription()
	{
		return primaryFactorDescription;
	}

	public void setPrimaryFactorDescription(String primaryFactorDescription)
	{
		this.primaryFactorDescription = primaryFactorDescription;
	}

	public String getCountyDescription()
	{
		return countyDescription;
	}

	public void setCountyDescription(String countyDescription)
	{
		this.countyDescription = countyDescription;
	}

	public Date getCollisionDate()
	{
		return collisionDate;
	}

	public void setCollisionDate(Date collisionDate)
	{
		this.collisionDate = collisionDate;
	}

	public double getCollisionTime()
	{
		return collisionTime;
	}

	public void setCollisionTime(double collisionTime)
	{
		this.collisionTime = collisionTime;
	}

	public int getMotorVehiclesInvolved()
	{
		return motorVehiclesInvolved;
	}

	public void setMotorVehiclesInvolved(int motorVehiclesInvolved)
	{
		this.motorVehiclesInvolved = motorVehiclesInvolved;
	}

	public int getTrailersInvolved()
	{
		return trailersInvolved;
	}

	public void setTrailersInvolved(int trailersInvolved)
	{
		this.trailersInvolved = trailersInvolved;
	}

	public int getInjuredNumber()
	{
		return injuredNumber;
	}

	public void setInjuredNumber(int injuredNumber)
	{
		this.injuredNumber = injuredNumber;
	}

	public int getDeadNumber()
	{
		return deadNumber;
	}

	public void setDeadNumber(int deadNumber)
	{
		this.deadNumber = deadNumber;
	}

	public int getDeerNumber()
	{
		return deerNumber;
	}

	public void setDeerNumber(int deerNumber)
	{
		this.deerNumber = deerNumber;
	}

	public String getRoadwayId()
	{
		return roadwayId;
	}

	public void setRoadwayId(String roadwayId)
	{
		this.roadwayId = roadwayId;
	}

	public String getRoadwayNumberText()
	{
		return roadwayNumberText;
	}

	public void setRoadwayNumberText(String roadwayNumberText)
	{
		this.roadwayNumberText = roadwayNumberText;
	}

	public String getRoadwayRampText()
	{
		return roadwayRampText;
	}

	public void setRoadwayRampText(String roadwayRampText)
	{
		this.roadwayRampText = roadwayRampText;
	}

	public String getSurfaceTypeCodeAndConditionsDescription()
	{
		return surfaceTypeCodeAndConditionsDescription;
	}

	public void setSurfaceTypeCodeAndConditionsDescription(
			String surfaceTypeCodeAndConditionsDescription)
	{
		this.surfaceTypeCodeAndConditionsDescription = surfaceTypeCodeAndConditionsDescription;
	}

	public String getInterstateName()
	{
		return interstateName;
	}

	public void setInterstateName(String interstateName)
	{
		this.interstateName = interstateName;
	}

	public String getInterstateNumber()
	{
		return interstateNumber;
	}

	public void setInterstateNumber(String interstateNumber)
	{
		this.interstateNumber = interstateNumber;
	}

	public String getInterstateMileMarkerNumber()
	{
		return interstateMileMarkerNumber;
	}

	public void setInterstateMileMarkerNumber(String interstateMileMarkerNumber)
	{
		this.interstateMileMarkerNumber = interstateMileMarkerNumber;
	}

	public String getDirectionFromPointCode()
	{
		return directionFromPointCode;
	}

	public void setDirectionFromPointCode(String directionFromPointCode)
	{
		this.directionFromPointCode = directionFromPointCode;
	}

	public double getFeetFromPointNumber()
	{
		return feetFromPointNumber;
	}

	public void setFeetFromPointNumber(double feetFromPointNumber)
	{
		this.feetFromPointNumber = feetFromPointNumber;
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

	public String getRoadwayClassDescription()
	{
		return roadwayClassDescription;
	}

	public void setRoadwayClassDescription(String roadwayClassDescription)
	{
		this.roadwayClassDescription = roadwayClassDescription;
	}

	public String getRoadTypeDescription()
	{
		return roadTypeDescription;
	}

	public void setRoadTypeDescription(String roadTypeDescription)
	{
		this.roadTypeDescription = roadTypeDescription;
	}

	public String getRoadwayCharDescripton()
	{
		return roadwayCharDescripton;
	}

	public void setRoadwayCharDescripton(String roadwayCharDescripton)
	{
		this.roadwayCharDescripton = roadwayCharDescripton;
	}

	public int getSurfaceTypeDescripton()
	{
		return surfaceTypeDescripton;
	}

	public void setSurfaceTypeDescripton(int surfaceTypeDescripton)
	{
		this.surfaceTypeDescripton = surfaceTypeDescripton;
	}

	public String getRoadwayJunctionDescription()
	{
		return roadwayJunctionDescription;
	}

	public void setRoadwayJunctionDescription(String roadwayJunctionDescription)
	{
		this.roadwayJunctionDescription = roadwayJunctionDescription;
	}

	public String getMedianTypeDescription()
	{
		return medianTypeDescription;
	}

	public void setMedianTypeDescription(String medianTypeDescription)
	{
		this.medianTypeDescription = medianTypeDescription;
	}

	public String getTrafficControlDescription()
	{
		return trafficControlDescription;
	}

	public void setTrafficControlDescription(String trafficControlDescription)
	{
		this.trafficControlDescription = trafficControlDescription;
	}

	public String getLightConditionsDescription()
	{
		return lightConditionsDescription;
	}

	public void setLightConditionsDescription(String lightConditionsDescription)
	{
		this.lightConditionsDescription = lightConditionsDescription;
	}

	public String getWeatherDescription()
	{
		return weatherDescription;
	}

	public void setWeatherDescription(String weatherDescription)
	{
		this.weatherDescription = weatherDescription;
	}

	public int getContructionIndicator()
	{
		return contructionIndicator;
	}

	public void setContructionIndicator(int contructionIndicator)
	{
		this.contructionIndicator = contructionIndicator;
	}

	public String getConstructionTypeDescription()
	{
		return constructionTypeDescription;
	}

	public void setConstructionTypeDescription(
			String constructionTypeDescription)
	{
		this.constructionTypeDescription = constructionTypeDescription;
	}

	public int getHitAndRunIndicator()
	{
		return hitAndRunIndicator;
	}

	public void setHitAndRunIndicator(int hitAndRunIndicator)
	{
		this.hitAndRunIndicator = hitAndRunIndicator;
	}

	public int getFireIndicator()
	{
		return fireIndicator;
	}

	public void setFireIndicator(int fireIndicator)
	{
		this.fireIndicator = fireIndicator;
	}

	public int getRumbleStripIndicator()
	{
		return rumbleStripIndicator;
	}

	public void setRumbleStripIndicator(int rumbleStripIndicator)
	{
		this.rumbleStripIndicator = rumbleStripIndicator;
	}

	public int getSchoolzoneIndicator()
	{
		return schoolzoneIndicator;
	}

	public void setSchoolzoneIndicator(int schoolzoneIndicator)
	{
		this.schoolzoneIndicator = schoolzoneIndicator;
	}

	public int getScorePredicted()
	{
		return scorePredicted;
	}

	public void setScorePredicted(int scorePredicted)
	{
		this.scorePredicted = scorePredicted;
	}

	public int getScorePrevious()
	{
		return scorePrevious;
	}

	public void setScorePrevious(int scorePrevious)
	{
		this.scorePrevious = scorePrevious;
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
