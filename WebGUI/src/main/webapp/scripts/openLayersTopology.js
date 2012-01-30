var map;
var initial_markersXML;
var initial_linesXML;
var flag = true;

function init() {
	  var options = {
               projection: new OpenLayers.Projection('EPSG:900913'),
               displayProjection: new OpenLayers.Projection('EPSG:4326'),
               units: 'm',
               numZoomLevels: 8,
               maxResolution: 1565143.0339,
               maxExtent: new OpenLayers.Bounds(-20037508, -20037508,
                                                20037508, 20037508.34)
       };
       map = new OpenLayers.Map('map', options);
       var gphy = new OpenLayers.Layer.Google(
           'Google Physical',
           {type: G_PHYSICAL_MAP, 'sphericalMercator': true}
       );
       var gmap = new OpenLayers.Layer.Google(
           'Google Streets', // the default
           { 'sphericalMercator': true}
       );
       map.addLayers([gphy, gmap]);
       map.setCenter(new OpenLayers.LonLat( 12.920068, 50.457761).transform(new OpenLayers.Projection('EPSG:4326'), new OpenLayers.Projection('EPSG:900913')), 4);
       map.addControl(new OpenLayers.Control.LayerSwitcher());
       var vector = new OpenLayers.Layer.Vector('AutoBAHN Connections');
       map.addLayer(vector);
       var markers = new OpenLayers.Layer.Markers('AutoBAHN IDMs');
       map.addLayer(markers);
       make_all(map);

};


function createRequestObject() {
    var tmpXmlHttpObject;
    if (window.XMLHttpRequest) { 
        tmpXmlHttpObject = new XMLHttpRequest();
    } else if (window.ActiveXObject) { 
        tmpXmlHttpObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return tmpXmlHttpObject;
}

function gup( name )
{
		name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
		var regexS = "[\\?&]"+name+"=([^&#]*)";
		var regex = new RegExp( regexS );
		var results = regex.exec( window.location.href );
		if( results == null )
			return null;
		else
			return results[1];
}

function makeGetRequest() {
	var http= createRequestObject();
    http.open('get', '/autobahn-gui/portal/secure/services-list.htm');
    http.onreadystatechange = processResponse(http);
    http.send(null);
}

function processResponse(http) {
    if(http.readyState == 4){
        var response = http.responseText; 
        document.getElementById('services').innerHTML = response;
    }
}

function addLines (map, linesXML, flag){
	
	var length = linesXML.length;
	var vector = map.getLayersByName ('AutoBAHN Connections')[0];
	
	//If flag==false  then it's not first time execution and the topology data are
	//updated. The old vector is destroyed and a new one is created with z-index to 0
	//to avoid overlapping issues
	if(flag == false){
		vector.destroy();
		vector= new OpenLayers.Layer.Vector('AutoBAHN Connections');
		map.addLayer(vector);
		map.setLayerIndex(vector,0);
	}
	
	for (var i = 0; i < length; i++) {
      var start_lat = parseFloat(linesXML[i].getAttribute('start-lat'));
      var start_lng = parseFloat(linesXML[i].getAttribute('start-lng'));
      var end_lat = parseFloat(linesXML[i].getAttribute('end-lat'));
      var end_lng = parseFloat(linesXML[i].getAttribute('end-lng'));

	  
      var color =  linesXML[i].getAttribute('color');
      var tickness = parseFloat(linesXML[i].getAttribute('tickness'));
      var style = {
          strokeColor: color,
          fillOpacity: 0.5,
          strokeWidth: tickness,
          strokeDashstyle: 'solid'
      };
      
      var start= new OpenLayers.LonLat(start_lng,start_lat).transform(new OpenLayers.Projection('EPSG:4326'), new OpenLayers.Projection('EPSG:900913'));
      var end= new OpenLayers.LonLat(end_lng,end_lat).transform(new OpenLayers.Projection('EPSG:4326'), new OpenLayers.Projection('EPSG:900913'));
      var start_point= new OpenLayers.Geometry.Point(start.lon, start.lat);
      var end_point= new OpenLayers.Geometry.Point(end.lon, end.lat);
      var line= new OpenLayers.Geometry.LineString([start_point,end_point]);
	  var v= new OpenLayers.Feature.Vector(line,null,style);
      vector.addFeatures([v]);
	  vector.redraw();
    }    
}

function addMarkers (map, markersXML){
	var markers = map.getLayersByName ('AutoBAHN IDMs')[0];
	 var features = map.getLayersByName ('Popups')[0];
	 if (markers != null){

    	 markers.clearMarkers();
     }else{
   	  markers = new OpenLayers.Layer.Markers('AutoBAHN IDMs');
   	  map.addLayer (markers);
   	 }
     
     for (var i = 0; i < markersXML.length; i++) {
       var lat = parseFloat(markersXML[i].getAttribute('lat'));
       var lng = parseFloat(markersXML[i].getAttribute('lng'));
    
       var point = new OpenLayers.LonLat(lng,lat).transform(new OpenLayers.Projection('EPSG:4326'), new
       		OpenLayers.Projection('EPSG:900913'));
       var html = markersXML[i].getAttribute('html');
       var label = markersXML[i].getAttribute('label');
       var image  = markersXML[i].getAttribute ('icon'); 
       
       var size;
       var offset;
       var icon;  		
   	
      
       if (image == '/autobahn-gui/images/autobahnMarker-info.png'){
   			size = new OpenLayers.Size(16,16);
   			
     	}
       else
       {	
    	   if (image == '/autobahn-gui/images/autobahnMarker-reservation-small.png') {
        	   size = new OpenLayers.Size(10,10);
           }
    	   else
    		 {
    		   size = new OpenLayers.Size(32,32);
    		 }
     	}	
        var feature = new OpenLayers.Feature(markers, point, {icon:new OpenLayers.Icon(image,size)}); 
        feature.closeBox = true;
        feature.popupClass = OpenLayers.Class(OpenLayers.Popup.FramedCloud, {
            "autoSize": false, 
            "minSize": new OpenLayers.Size(300,300)
        });
        if(html!=null){
        feature.data.popupContentHTML = html;
        feature.data.overflow =  'auto';
        }        
        var marker = feature.createMarker();
        markers.addMarker(marker);
        marker.events.register('mousedown', feature, onFeatureSelect);
    }
     

}

function refreashMap (map){
    var request = GXmlHttp.create();
    var service = gup('service');
  
    var domain = gup('domain');
    if (service==null || service=="") {
		request.open('GET', '/autobahn-gui/portal/secure/topology.xml?service=&domain=', true);
	} else {
		var url = '/autobahn-gui/portal/secure/topology.xml'+'?service='+service+'&domain='+domain;		  	
	  	request.open('GET', url, true);
	}
	
	request.onreadystatechange = function() {
		if (request.readyState == 4) {
	
			var xmlDoc = GXml.parse(request.responseText);
			var linesXML = xmlDoc.documentElement.getElementsByTagName('line');            
			var markersXML = xmlDoc.documentElement.getElementsByTagName('marker');
			
			//For first time execution only. Variables with prefix initial_
			//are used to store the previous topology data
			if(flag == true){
				addLines(map, linesXML, flag);
				addMarkers (map, markersXML);
								  
				initial_markersXML = markersXML;
				initial_linesXML = linesXML;
				
				flag = false;
			  
				return;
			}
			
			//Checks if topology has been updated with the help
			//of initial_* variables
			if(initial_markersXML != markersXML){
				addMarkers(map,markersXML);		
				initial_markersXML = markersXML;				    			
			}

			if(initial_linesXML != linesXML){
				addLines(map, linesXML, flag);
				initial_linesXML = linesXML;				    
			}		
		}
	};
	
  	request.send(null);
}

function onPopupClose(evt) {
    selectControl.unselect(this.feature);
}
function onFeatureSelect(evt) {
    var feature = this;

    removeAllMapPopup();

    popup = feature.createPopup(true);
    feature.popup = popup;
    popup.feature = feature;
    map.addPopup(popup);

}
function onFeatureUnselect(evt) {
    feature = evt.feature;
    if (feature.popup) {
        popup.feature = null;
        map.removePopup(feature.popup);
        feature.popup.destroy();
        feature.popup = null;
    }
}

function removeAllMapPopup(){
	while( map.popups.length ) {
		map.removePopup(map.popups[0]);
	}
}

function make_all(map){
if (GBrowserIsCompatible()) {
    setInterval('refreashMap(map)',3000);
    refreashMap(map);
  }else {
    alert('Sorry, the Google Maps API is not compatible with this browser');
  }

}