<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <script src="http://maps.google.com/maps/api/js?sensor=true"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script src="https://raw.githubusercontent.com/pittss/goMap/master/js/jquery.gomap-1.3.2.js"></script>
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
        <META HTTP-EQUIV="Expires" CONTENT="-1">
    </head>
    <body>
        <h1 style="color:blue;vertical-align: middle; margin-left:30px; margin-top:50px;">AccidentChek:&nbsp;<i>Accident Potential By Intersection</i></h1>

        <div>
            <div style="float:left; width:160px; text-align: right; margin-left: 30px; color:blue;">Score One Location:&nbsp;</div>
            <div style="float:left;">
                <input type="text" placeholder="Street Address" id="w-input-one-location" value="" style="width:250px;" />
            </div>
            <div style="float:left;">&nbsp;</div>
            <div style="float:left;">
                <button id="w-button-one-location" type="button" style="color:blue;">Get Score for One Location</button>
            </div>
            <div style="clear:both;"></div>
        </div>
        
        <div style="height: 25px;">&nbsp;</div>

        <div>
            <div style="float:left; width:160px; text-align: right; margin-left: 30px; color:blue;">Score a Route:&nbsp;</div>
            <div style="float:left;">
                <input type="text" placeholder="Starting Address" id="w-input-start-location" value="" style="width:250px;" />
            </div>
            <div style="float:left; width: 5px;">&nbsp;</div>
            <div style="float:left;">
                <input type="text" placeholder="Ending Address" id="w-input-end-location" value="" style="width:250px;" />
            </div>
            <div style="float:left;">&nbsp;</div>
            <div style="float:left;">
                <button id="w-button-route" type="button" style="color:blue;">Get Score for a Route</button>
            </div>
            <div style="clear:both;"></div>
        </div>
        

        <script>
            $(document).ready(function() 
                {
                    //--------------One Location GoMap Search: START----------------------------------
                    $("#w-button-one-location").click(function() 
                    {
                        $('#map').goMap({ 
                            address: $('#w-input-one-location').val(),
                            zoom:19,
                            scaleControl: true,
                            markers:[
                            {
                               address: $('#w-input-one-location').val(),
                               title: 'Home',
                               icon: 'http://gmaps-samples.googlecode.com/svn/trunk/markers/blue/blank.png'
                            }] 
                        }); // end of goMap                       
                                           
                       $.getJSON("${pageContext.request.contextPath}/getScoreForOneLocation",
                       {
                          streetAddress : $('#w-input-one-location').val()
                       }, 
                       function(data) 
                       {
                          var data       = JSON.stringify(data);
                          var json       = JSON.parse(data);
                          var accidents  = json["accidents"];
                          
                          for( var i = 0; i < accidents.length && i < 99; i++ )
                          {   
                              var marker = accidents[ i ];
                              
                              $.goMap.createMarker({ 
                                 latitude : marker.latitude,
                                 longitude : marker.longitude,
                                 title: 'Marker ' + (i + 1),
                                 icon: "http://gmaps-samples.googlecode.com/svn/trunk/markers/red/marker" + (i + 1) + ".png"
                              });
                          }
                          
                          var danger = json["danger"];
                          $("#dangerAssessment").html( danger ); 
                       })
                       .done(      function() {} )
                       .fail(      function() {} )
                       .complete(  function() {} );                    
                    }); // end of w-button-one-location
                    //--------------One Location GoMap Search: END-------------------------------------    
  
                    //-----------------Route Search: START--------------------------------------
                   $("#w-button-route").click(function() 
                        {
                            $.getJSON("${pageContext.request.contextPath}/getScoreForRoute",
                            {
                                startAddress : $('#w-input-start-location').val(),
                                endAddress: $('#w-input-end-location').val()
                            }, 
                            function(data) 
                            {
                                var data = JSON.stringify(data);
                                var json = JSON.parse(data);
                                showRouteMap(json);
                                calcRoute();

                                var accidents = json["accidents"];
                                
                                for( var i = 0; i < accidents.length; i++ )
                                {
                                    var accident        = accidents[ i ];
                                    var latString       = accident["latitude"];
                                    var lngString       = accident["longitude"];
                                    var latitude        = parseFloat(latString);
                                    var longitude       = parseFloat(lngString);                                   
                                    var latlng          = new google.maps.LatLng(latitude,longitude);
                                    var marker          = new google.maps.Marker({position:latlng, map:map});
                                    markersArray[ i ]   = marker;
                                }
                                
                                alert( "markersArray.length=" + markersArray.length );                              
                            })
                            .done(      function() {} )
                            .fail(      function() {} )
                            .complete(  function() {} );
                        });                    
                    //-----------------Route Search: END----------------------------------------
                    
                    //-----------------Route Search Functions: START-------------------------------------------
                    var map;
                    var directionsDisplay;
                    var directionsService;
                    var stepDisplay;
                    var markersArray = [];
                    
                    function showRouteMap( json )
                    {
                        directionsService   = new google.maps.DirectionsService();
                        var indianapolis    = new google.maps.LatLng(39.779329,-86.240563);          
                        var mapOptions      = { zoom:16, center: indianapolis, mapTypeId: google.maps.MapTypeId.SATELLITE };
                        map                 = new google.maps.Map( document.getElementById('map'), mapOptions) ;
                        
                        var rendererOptions = { map: map, suppressMarkers:false };

                        directionsDisplay   = new google.maps.DirectionsRenderer( rendererOptions );
                        
                        stepDisplay         = new google.maps.InfoWindow();
                        //directionsDisplay.setMap(map);
                    }
                    
                    function calcRoute() 
                    {
                        var start   = $('#w-input-start-location').val();
                        var end     = $('#w-input-end-location').val();
                        var request = { origin:start, destination:end, travelMode: google.maps.TravelMode.DRIVING };
          
                        directionsService.route(request, function(response, status) 
                        {   
                            if (status == google.maps.DirectionsStatus.OK) 
                            {
                                directionsDisplay.setDirections(response);
                            }
                        });
                    }
                    //-----------------Route Search Functions: END00-------------------------------------------
                    
                    //-----------------OneLocation: START-------------------------------------------------------
                    function showMap(latitude,longitude) 
                    { 
                        var googleLatandLong = new google.maps.LatLng(latitude,longitude);
        
                        var mapOptions = { 
                                zoom:19,
                                center: googleLatandLong,
                                mapTypeId: google.maps.MapTypeId.SATELLITE 
                            };
        
                        var mapDiv = document.getElementById("map");
                        map = new google.maps.Map(mapDiv, mapOptions);
            
                        var title = "Server Location"; 
                        
                        addMarker(map, googleLatandLong, title, "");
                    }   
                    
                    function setupMarker( map, accidents )
                    {                                           
                        for( var i = 0; i < accidents.length; i++ )
                        {
                            var latitude  = accidents[ i ].latitude;
                            var longitude = accidents[ i ].longitude;
                            var latlong   = new google.maps.LatLng(latitude,longitude);
                           
                            var markerOptions = {
                                    position: latlong, 
                                    map: map,
                                    title: "Marker-" + (i+1), 
                                    clickable: true,
                                    icon: "http://gmaps-samples.googlecode.com/svn/trunk/markers/red/marker" + (i + 1) + ".png"
                                };
                
                            var marker = new google.maps.Marker(markerOptions); 
                        }
                    }
        
                    function addMarker(map, latlong, title, content) 
                    {                       
                        var markerOptions = {
                                position: latlong, 
                                map: map,
                                title: title, 
                                clickable: true
                            };
            
                        var marker = new google.maps.Marker(markerOptions); 
                    }
                     //-----------------OneLocation: END--------------------------------------------------------
                }); // end ready
        </script>
        
        <br/>
        <div id="dangerAssessment" style="font-size:16pt;margin-left:20px;"></div>   
           
        <div style="width:800px;height:700px;margin-left:20px;" id="map"></div>

    </body>
</html>