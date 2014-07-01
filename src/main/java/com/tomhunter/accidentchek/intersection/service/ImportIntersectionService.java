package com.tomhunter.accidentchek.intersection.service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.tomhunter.accidentchek.intersection.bean.AccidentBean;
import com.tomhunter.accidentchek.intersection.dao.AccidentDAO;

public class ImportIntersectionService 
{
	public static final String PATH_TO_IMPORT_FILE = "/Users/tomhunter/Desktop/ACCIDENT_CHEK/Accidents_2012_2013_Vehicles_geocoded.csv";
	
	private AccidentDAO accidentDAO;
	private boolean checkName                   = true;


	public ImportIntersectionService()
	{	
		FileReader fr 		= null;
		BufferedReader br 	= null;
		int count			= 0;
		
		try
		{
			fr					= new FileReader( ImportIntersectionService.PATH_TO_IMPORT_FILE );
			br 					= new BufferedReader( fr );
			boolean keepReading	= true;
		
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
			System.out.println( "org.tomhunter.accidentchek.intersection.service.ImportIntersectionService() threw an Exception, count=" + count + ", e=" + e );
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
	
	
	public ImportIntersectionService( String excelVersion )
	{	
        File file           		= null;
        InputStream fis     		= null;
		int count					= 0;
		
		try
		{
            file                = new File( ImportIntersectionService.PATH_TO_IMPORT_FILE );
            fis                 = new FileInputStream( file );
            Workbook workbook   = WorkbookFactory.create( fis );             
            Sheet sheet         = workbook.getSheetAt(0);
            int lastRowNum      = sheet.getLastRowNum();
            
            for( int i = 0; i < lastRowNum; i++ )
            {
                Row aRow    				= sheet.getRow( i );
        		AccidentBean accidentBean	= new AccidentBean();
                count      					+= this.processCells( accidentBean, aRow );                
            }
		}
		catch( Exception e )
		{
			System.out.println( "org.tomhunter.accidentchek.intersection.service.ImportIntersectionService() threw an Exception, count=" + count + ", e=" + e );
		}
		finally
		{
			try
			{
				if( fis != null )
				{
					fis.close();
				}
			}
			catch( Exception e )
			{
			}
		}
	}
	
	
	private int insertOneLineIntoTheIntersectionTable( String aLine ) throws Exception
	{
		int outcome			= 0;
		String[] fields 	= null;
		String originalLine = aLine;
		
		if( aLine != null )
		{
			aLine			= fixPairedCommas( aLine );
			fields 			= aLine.split( ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)" );
			int fieldCount 	= fields.length;
			
			if( fieldCount != 15 )
			{
				System.out.println( "fieldCount=" + fieldCount + "\t aLine=" + aLine );
			}
			
			outcome = this.getAccidentDAO().insertOneLineIntoTheIntersectionTableDAO( fields, originalLine );
		}
		
		return outcome;
	}


	
	private int processCells( AccidentBean accidentBean, Row aRow )
	{
		int updates       = 0;
	    boolean keepGoing = true;
	        
	    for( int i = 0; keepGoing ; i++ )  
	    {  
	    	Cell aCell          = aRow.getCell( i );
	    	
	    	if( i > 45 )
	    	{
	    		keepGoing = false;
	    	}
	                
	    	if( aCell == null )
	    	{
	    		continue;
	    	}
	            
	        if( checkName )
	        {
	        	String cellName     = aCell.getStringCellValue(); 
	            checkName           = false;
	                
				int indexOfHeaders = cellName.indexOf( "INDIVIDUAL_MR_RECORD" );
					
				if( indexOfHeaders > -1 )
				{
					return 0;
				}
			}
	            
            if( i == 0)
            {
                String individualMrRecord = aCell.getStringCellValue();
                accidentBean.setIndividualMrRecord( individualMrRecord );
            }
            else if( i == 1)
            {
            	String collisionTimeAmPm = aCell.getStringCellValue();
            	accidentBean.setCollisionTimeAmPm( collisionTimeAmPm );
            }
            else if( i == 2 )
            {
            	String agencyOrder = aCell.getStringCellValue();
            	accidentBean.setAgencyOrder( agencyOrder );
            }
            else if( i == 3 )
            {
            	String localityDescription = aCell.getStringCellValue();
            	accidentBean.setLocalityDescription( localityDescription );
            }
            else if( i == 4 )
            {
            	String townshipDescription = aCell.getStringCellValue();
            	accidentBean.setTownshipDescription( townshipDescription );
            }
            else if( i == 5 )
            {
            	String cityDescription = aCell.getStringCellValue();
            	accidentBean.setCityDescription( cityDescription );
            }
            else if( i == 6 )
            {
            	String mannerCollide = aCell.getStringCellValue();
            	accidentBean.setMannerCollide( mannerCollide );
            }
            else if( i == 7 )
            {
            	String primaryFactorDescription = aCell.getStringCellValue();
            	accidentBean.setPrimaryFactorDescription( primaryFactorDescription );
            }
            else if ( i == 8 )
            {
            	String countyDescription = aCell.getStringCellValue();
            	accidentBean.setCountyDescription( countyDescription );
            }
            else if( i == 9 )
            {
            	java.util.Date collisionDateUtil = aCell.getDateCellValue();
            	java.sql.Date collisionDate = new java.sql.Date( collisionDateUtil.getTime() );
            	accidentBean.setCollisionDate( collisionDate );
            }
            else if( i == 10 )
            {
            	double collisionTime = aCell.getNumericCellValue();
            	accidentBean.setCollisionTime( collisionTime );
            }
            else if( i == 11 )
            {
            	Double motorVehiclesInvolvedDouble 	= aCell.getNumericCellValue();
            	int motorVehiclesInvolved			= motorVehiclesInvolvedDouble.intValue();
            	accidentBean.setMotorVehiclesInvolved( motorVehiclesInvolved );
            }
            else if ( i == 12 )
            {
            	Double trailersInvolvedDouble 		= aCell.getNumericCellValue();
            	int trailersInvolved				= trailersInvolvedDouble.intValue();
            	accidentBean.setTrailersInvolved( trailersInvolved );
            }
            else if( i == 13 )
            {
            	Double injuredNumberDouble			= aCell.getNumericCellValue();
            	int injuredNumber					= injuredNumberDouble.intValue();
            	accidentBean.setInjuredNumber( injuredNumber );
            }
            else if( i == 14 )
            {
            	Double deadNumberDouble				= aCell.getNumericCellValue();
            	int deadNumber						= deadNumberDouble.intValue();
            	accidentBean.setDeadNumber( deadNumber );
            }
            else if( i == 15 )
            {
            	Double deerNumberDouble				= aCell.getNumericCellValue();
            	int deerNumber						= deerNumberDouble.intValue();
            	accidentBean.setDeerNumber( deerNumber );
            }
            else if( i == 16 )
            {
            	String roadwayId = aCell.getStringCellValue();
            	accidentBean.setRoadwayId( roadwayId );
            }
            else if( i == 17 )
            {
            	String roadwayNumberText = aCell.getStringCellValue();
            	accidentBean.setRoadwayNumberText( roadwayNumberText );
            }
            else if( i == 18 )
            {
            	String roadwayRampText = aCell.getStringCellValue();
            	accidentBean.setRoadwayRampText( roadwayRampText );
            }
            else if( i == 19 )
            {
            	String surfaceTypeCodeAndConditionsDescription = aCell.getStringCellValue();
            	accidentBean.setSurfaceTypeCodeAndConditionsDescription( surfaceTypeCodeAndConditionsDescription ); 
            }
            else if( i == 20 )
            {
            	String interstateName = aCell.getStringCellValue();
            	accidentBean.setInterstateName( interstateName );
            }
            else if( i == 21 )
            {
            	String interstateNumber = aCell.getStringCellValue();
            	accidentBean.setInterstateNumber( interstateNumber );
            }
            else if( i == 22 )
            {
            	String interstateMileMarkerNumber = aCell.getStringCellValue();
            	accidentBean.setInterstateMileMarkerNumber( interstateMileMarkerNumber );
            }
            else if( i == 23 )
            {
            	String directionFromPointCode = aCell.getStringCellValue();
            	accidentBean.setDirectionFromPointCode( directionFromPointCode );
            }
            else if( i == 24 )
            {
            	double feetFromPointNumber = aCell.getNumericCellValue();
            	accidentBean.setFeetFromPointNumber( feetFromPointNumber );
            }
            else if( i == 25 )
            {
            	String latitudeDecimalNumber = aCell.getStringCellValue();
            	accidentBean.setLatitudeDecimalNumber( latitudeDecimalNumber );
            }
            else if( i == 26 )
            {
            	String longitudeDecimalNumber = aCell.getStringCellValue();
            	accidentBean.setLongitudeDecimalNumber( longitudeDecimalNumber );
            }
            else if( i == 27 )
            {
            	String roadwayClassDescription = aCell.getStringCellValue();
            	accidentBean.setRoadwayClassDescription( roadwayClassDescription );
            }
            else if( i == 28 )
            {
            	String roadTypeDescription = aCell.getStringCellValue();
            	accidentBean.setRoadTypeDescription( roadTypeDescription );
            }
            else if( i == 29 )
            {
            	String roadwayCharDescripton = aCell.getStringCellValue();
            	accidentBean.setRoadwayCharDescripton( roadwayCharDescripton );
            }
            else if( i == 30 )
            {
            	Double surfaceTypeDescriptonDouble 	= aCell.getNumericCellValue();
            	int surfaceTypeDescripton			= surfaceTypeDescriptonDouble.intValue();
            	accidentBean.setSurfaceTypeDescripton( surfaceTypeDescripton );
            }
            else if( i == 31 )
            {
            	String roadwayJunctionDescription = aCell.getStringCellValue();
            	accidentBean.setRoadwayJunctionDescription( roadwayJunctionDescription );
            }
            else if( i == 32 )
            {
            	String medianTypeDescription = aCell.getStringCellValue();
            	accidentBean.setMedianTypeDescription( medianTypeDescription );
            }
            else if( i == 33 )
            {
            	String trafficControlDescription = aCell.getStringCellValue();
            	accidentBean.setTrafficControlDescription( trafficControlDescription );
            }
            else if( i == 34 )
            {
            	String lightConditionsDescription = aCell.getStringCellValue();
            	accidentBean.setLightConditionsDescription( lightConditionsDescription );
            }
            else if( i == 35 )
            {
            	String weatherDescription = aCell.getStringCellValue();
            	accidentBean.setWeatherDescription( weatherDescription );
            }
            else if( i == 36 )
            {
            	Double contructionIndicatorDouble 	= aCell.getNumericCellValue();
            	int contructionIndicator			= contructionIndicatorDouble.intValue();
            	accidentBean.setContructionIndicator( contructionIndicator );
            }
            else if( i == 37 )
            {
            	String constructionTypeDescription	= aCell.getStringCellValue();
            	accidentBean.setConstructionTypeDescription( constructionTypeDescription );
            }
            else if( i == 38 )
            {
            	int hitAndRunIndicator = this.decodeYNToInt( aCell.getStringCellValue() );
            	accidentBean.setHitAndRunIndicator( hitAndRunIndicator );
            }
            else if( i == 39 )
            {
            	int fireIndicator = this.decodeYNToInt( aCell.getStringCellValue() );
            	accidentBean.setFireIndicator( fireIndicator );
            }
            else if( i == 40 )
            {
            	int rumbleStripIndicator = this.decodeYNToInt( aCell.getStringCellValue() );
            	accidentBean.setRumbleStripIndicator( rumbleStripIndicator );
            }
            else if( i == 41 )
            {
            	int schoolzoneIndicator	= this.decodeYNToInt( aCell.getStringCellValue() );
            	accidentBean.setSchoolzoneIndicator( schoolzoneIndicator );
            }
            else
            {
                System.out.println( "Unexpected cell, cell.getStringCellValue()=" + aCell );
            }
        }
	    
		java.sql.Date calculationDate   = new java.sql.Date( Calendar.getInstance().getTime().getTime() );
	    accidentBean.setCalculationDate( calculationDate );
        
		updates = this.getAccidentDAO().insertOneLineIntoTheIntersectionTableDAO( accidentBean );
                                                               
        return updates;
    }

	private int decodeYNToInt( String yesOrNo )
	{
		if( yesOrNo != null && yesOrNo.equals( "Y" ) )
		{
			return 1;
		}
		else
		{
			return 0;
		}
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
	
	
	@SuppressWarnings("unused")
	public static void main( String[] args )
	{
		ImportIntersectionService importer = new ImportIntersectionService();
	}

	@Autowired
	public void setAccidentDAO(AccidentDAO accidentDAO)
	{
		this.accidentDAO = accidentDAO;
	}
	public AccidentDAO getAccidentDAO()
	{
		if( this.accidentDAO == null )
		{
			this.accidentDAO = new AccidentDAO();
		}

		return accidentDAO;
	}
}