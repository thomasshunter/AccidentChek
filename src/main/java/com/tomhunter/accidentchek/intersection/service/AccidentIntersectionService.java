package com.tomhunter.accidentchek.intersection.service;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tomhunter.accidentchek.intersection.bean.AccidentIntersectionBean;
import com.tomhunter.accidentchek.intersection.dao.AccidentDAO;

@Service( "accidentIntersectionService" )
public class AccidentIntersectionService
{
    private AccidentDAO accidentDAO;
    private ObjectMapper mapper = new ObjectMapper();
    
    public List<AccidentIntersectionBean> getAllAccidentIntersectionsInIdOrderService( long startId, long endId )
    {
        List<AccidentIntersectionBean> accidents = this.getAccidentDAO().getAllAccidentIntersectionsInIdOrderDAO( startId, endId );
        
        return accidents;
    }
    
    
    public String getAllAccidentsBetweenIdsAsJSONService( long startId, long endId )
    {
        StringBuilder json                              = new StringBuilder( "{ 'accidents':[");
        List<AccidentIntersectionBean> accidents        = this.getAllAccidentIntersectionsInIdOrderService( startId, endId );
        Iterator<AccidentIntersectionBean> accidentsIt  = accidents.iterator();    
        
        try
        {
            while( accidentsIt.hasNext() )
            {
                AccidentIntersectionBean anAccident = accidentsIt.next();
                String js                           = mapper.writeValueAsString( anAccident );
            
                json.append( js );
            }
            
            json.append( "]\n}" );
        }
        catch( Exception e )
        {
            System.out.println( "org.tomhunter.accidentchek.intersection.service.AccidentIntersectionService.getAllAccidentsBetweenIdsAsJSONService() threw an Exception, e=" + e );
        }
        
        return json.toString();        
    }
    
    
    
    @Autowired
    public void setAccidentDAO(AccidentDAO accidentDAO)
    {
        this.accidentDAO = accidentDAO;
    }
    
    
    public static void main( String[] args )
    {
        AccidentIntersectionService service = new AccidentIntersectionService();
        String json = service.getAllAccidentsBetweenIdsAsJSONService( 1 , 100 );
        
        System.out.println( json );
    }


    public AccidentDAO getAccidentDAO()
    {
        if( accidentDAO == null )
        {
            this.accidentDAO = new AccidentDAO();
        }
        
        return accidentDAO;
    }
    
    
}
