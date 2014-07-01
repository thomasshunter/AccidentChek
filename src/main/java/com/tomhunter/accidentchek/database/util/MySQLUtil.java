package com.tomhunter.accidentchek.database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MySQLUtil
{
    private DataSource dataSource;
    
    private DateFormat df = new SimpleDateFormat( "MM/dd/yyyy" ); 
    
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/ACCIDENTCHEK?autoReconnect=true";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "mermaid54321"; // B0xst3r555O
    
    
    public Connection getDatabaseConnectionUsingDriverManager()
    {
        Connection con = null;
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();                                       // DO NOT IMPORT com.mysql.jdbc.Driver  !!!
            con = DriverManager.getConnection( MySQLUtil.DB_URL, MySQLUtil.DB_USER, MySQLUtil.DB_PASS );
        }
        catch( SQLException sql )
        {
            System.err.println( "MySQLUtil.getDatabaseConnectionUsingDriverManager() threw an SQLException, sql=" + sql );
        }
        catch( IllegalAccessException iae )
        {
            System.err.println( "MySQLUtil.getDatabaseConnectionUsingDriverManager() threw an IllegalAccessException=" + iae );            
        }
        catch( InstantiationException ie )
        {
            System.err.println( "MySQLUtil.getDatabaseConnectionUsingDriverManager() threw an InstantiationException=" + ie );
        }
        catch( ClassNotFoundException cnfe )
        {
            System.err.println( "MySQLUtil.getDatabaseConnectionUsingDriverManager() threw a ClassNotFoundException=" + cnfe );
        }
        
        return con;
    }
    
    
    
    public Connection getDatabaseConnection()
    {
        Connection con = null;
        
        try
        {
            Context initCtx     = new InitialContext();
            Context envCtx      = (Context) initCtx.lookup( "java:comp/env" );
            DataSource ds       = (DataSource) envCtx.lookup( "jdbc/ACCIDENTCHEK" );
            con                 = ds.getConnection();
        }
        catch( NamingException ne )
        {
            System.out.println( "MySQLUtil.getDatabaseConnection() threw a NamingException while attempting to get the context, ne=" + ne );
        }
        catch( Exception e )
        {
            System.out.println( "MySQLUtil.getDatabaseConnection() threw an Exception while attempting to get the connection, e=" + e );            
        }
        
        if( con == null )
        {            
            System.out.println( "MySQLUtil.getDatabaseConnection() got a null DataSource from Tomcat. Will try to use the injected Spring DataSource instead." );
            
            if( this.dataSource != null )
            {
                try
                {
                    con = this.dataSource.getConnection();
                }
                catch( Exception ee )
                {
                    System.out.println( "MySQLUtil.getDatabaseConnection() failed a second time to get a DatabaseConnection. Will try one last time with DriverManager." );                    
                    con = this.getDatabaseConnectionUsingDriverManager();
                }
            }
            else
            {
                con = this.getDatabaseConnectionUsingDriverManager();
            }
        }
        
        return con;
    }
    
    
    public java.sql.Timestamp makeSqlDate( java.util.Date original )
    {
        long dateLong = original.getTime();
        
        return new java.sql.Timestamp( dateLong );
    }
    
    public java.sql.Date makeSqlDate( String original )
    {
        java.sql.Date sqlDate = null;
        
        try
        {
            java.util.Date parsedUtilDate   = df.parse( original );  
            sqlDate                         = new java.sql.Date( parsedUtilDate.getTime() );            
        }
        catch( Exception e )
        {
            System.out.println( "MySQLUtil.makeSqlDate() threw an Exception while attempting to convert original=" + original + " into a java.sql.Date, e=" + e );
        }

        return sqlDate;
    }
    
    public int convertStringToInt( String original )
    {
        int result = -1;
        
        try
        {
            Double originalDouble   = new Double( original );
            result                  = originalDouble.intValue();
        }
        catch( Exception e )
        {
            System.out.println( "MySQLUtil.convertDoubleToInt() threw an Exception while attempting to convert original=" + original + " to an int." );
        }
        
        return result;
    }
    
    public double convertStringToDouble( String original )
    {
        return Double.parseDouble( original );
    }

    public int convertDoubleToInt( double original )
    {
        int result = -1;
        
        try
        {
            Double originalDouble   = new Double( original );
            result                  = originalDouble.intValue();
        }
        catch( Exception e )
        {
            System.out.println( "MySQLUtil.convertDoubleToInt() threw an Exception while attempting to convert original=" + original + " to an int." );
        }
        
        return result;
    }
    
    public static boolean dateIsBefore( java.sql.Date isBeforeDate, java.sql.Date isAfterDate )
    {
       boolean isBefore    = false;
       Calendar calA       = Calendar.getInstance();
       Calendar calB       = Calendar.getInstance();
       
       calA.setTime(isBeforeDate);
       calB.setTime(isAfterDate);
       
       if( calA.before(calB) || calA.equals(calB) ){
          isBefore = true;
       }
       
      return isBefore;
    }

    @Autowired
    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
    public DataSource getDataSource()
    {
        return dataSource;
    }

}
