function createRequestObject() {
    var tmpXmlHttpObject;

    if (window.XMLHttpRequest) { 
        tmpXmlHttpObject = new XMLHttpRequest();
    } else if (window.ActiveXObject) { 
        tmpXmlHttpObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    return tmpXmlHttpObject;
}

//call the above function to create the XMLHttpRequest object
var http = createRequestObject();

function makeGetRequest() {
    http.open('get', '/autobahn-gui/portal/secure/services-list.htm');
    http.onreadystatechange = processResponse;
    http.send(null);
}

function processResponse() {
    if(http.readyState == 4){
        var response = http.responseText; 
        document.getElementById('services').innerHTML = response;
    }
}

// This function picks up the click and opens the corresponding info window

function myclick(i) {
  hmarkers[i].openInfoWindowHtml(htmls[i]);
}

function refreashMap (){
  	// Read the data from example.xml
  // create the map
  mgr.clearMarkers();
  var request = GXmlHttp.create();

  var service = gup("service");
  var domain = gup("domain");

  if (service==null)
  	request.open("GET", "/autobahn-gui/portal/secure/topology.xml", true);
	  else{
	  	var url = "/autobahn-gui/portal/secure/topology.xml"+"?"+"service="+service+"&domain="+domain;
	  	request.open("GET", url, true);
	  }
  request.onreadystatechange = function() {
    if (request.readyState == 4) {
      var xmlDoc = GXml.parse(request.responseText);
      // obtain the array of markers and loop through it
      var markers = xmlDoc.documentElement.getElementsByTagName("marker");
      for (var i = 0; i < markers.length; i++) {
        // obtain the attribues of each marker
        var lat = parseFloat(markers[i].getAttribute("lat"));
        var lng = parseFloat(markers[i].getAttribute("lng"));
        var point = new GLatLng(lat,lng);
        var html = markers[i].getAttribute("html");
        var label = markers[i].getAttribute("label");
        var image  = markers[i].getAttribute ("icon");
        var text = markers[i].getAttribute ("title");
        /*if (i==0 && !isStarted){
			map.setCenter(point,4);
			isStarted =true;
		}*/
        // create the marker
			if (text ==null)
        	var marker = createMarker(point,label,html,image);
        else
        	var marker = createMarker(point,label,html,image,text);
        mgr.addMarker(marker,0);

        //map.addOverlay(marker);
      }      
      mgr.refresh();
      var lines = xmlDoc.documentElement.getElementsByTagName("line");
      var lenght = drawedLines.length;
      //map.clearOverlays();
      //map.addOverlay (mgr);
      for (var i=0;i<lenght;i++)
      	map.removeOverlay (drawedLines[i]);
      drawedLines.length =0;
      lenght =  lines.length;
      
      for (var i = 0; i < lenght; i++) {
        // obtain the attribues of each marker
        var start_lat = parseFloat(lines[i].getAttribute("start-lat"));
        var start_lng = parseFloat(lines[i].getAttribute("start-lng"));
        var end_lat = parseFloat(lines[i].getAttribute("end-lat"));
        var end_lng = parseFloat(lines[i].getAttribute("end-lng"));
        var color =  lines[i].getAttribute("color");
        var tickness = parseFloat(lines[i].getAttribute("tickness"));
        var start = new GLatLng(start_lat,start_lng);
        var end = new GLatLng(end_lat,end_lng);
        // create the marker
        var line = new  GPolyline([start,end],color,tickness);
        map.addOverlay(line);
        drawedLines.push(line);
        
      }
      
    }
  }
  
  request.send(null);
  }
function createMarker(point,name,html, image,text) {
  	var Icon = new GIcon();
  	if (image == "/autobahn-gui/images/autobahnMarker-info.png"){
  		Icon.iconSize = new GSize(16, 16);
  		Icon.shadowSize = new GSize(16, 16);
  		
  		Icon.image =image;
  		Icon.size=GPoint(16,16);
  		Icon.height=32;
		
  		Icon.size=GPoint(16,16);
  		Icon.height=16;
  	}else{	
  		Icon.iconSize = new GSize(32, 32);
  		Icon.shadowSize = new GSize(32, 30);
  		
  		Icon.image =image;
  		Icon.size=GPoint(32,32);
  		Icon.height=32;
		
  		Icon.size=GPoint(32,32);
  		Icon.height=32;
  	}
  	
  	Icon.image =image;
  	Icon.iconAnchor = new GPoint(6, 20);
  	Icon.infoWindowAnchor = new GPoint(5,1);
    opts = { 
			"icon": Icon,
			"clickable": true,
			"labelText": text,
			"labelOffset": new GSize(-6, -10)
	};
	var marker = new LabeledMarker(point, opts);
    

	GEvent.addListener(marker, "dragstart", function() {
			map.closeInfoWindow();
		 });

	GEvent.addListener(marker, "dragend", function() {
			 marker.openInfoWindowHtml("Just bouncing along...");
		});
    GEvent.addListener(marker, "click", function() {
      marker.openInfoWindowHtml(html);
    });
    GEvent.addListener(marker, "mouseover", function() {
      marker.setImage(image);
    });
    GEvent.addListener(marker, "mouseout", function() {
      marker.setImage(image);
    });

    gmarkers[i] = marker;
    htmls[i] = html;
    // add a line to the side_bar html, with click, mouseover and mouseout event handlers
    side_bar_html += '<a href="javascript:myclick(' + i + ')" onmouseover="gmarkers['+i+'].setImage(\'marker.png\')" onmouseout="gmarkers['+i+'].setImage(\'coldmarker.png\')">' + name + '</a><br>';
    i++;
    return marker;
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

function make_all(){
if (GBrowserIsCompatible()) {
    
    var side_bar_html = "";
    var gmarkers = [];
    var htmls = [];
    var i = 0;

         
  
    var map = new GMap2(document.getElementById("map"));
    map.addControl(new GLargeMapControl());
    map.addControl(new GMapTypeControl());
    map.setCenter(new GLatLng(50.457761, 12.920068),4);
	  var mgrOptions = { borderPadding: 50, maxZoom: 15, trackMarkers: true };
	  var mgr = new MarkerManager(map, mgrOptions);
	  makeGetRequest();
	  var isStarted = false;
	  var drawedLines=[];
    //while (true){
    setInterval("refreashMap()",15000);
    refreashMap();
    //}
    //refreashMap();
  }

  else {
    alert("Sorry, the Google Maps API is not compatible with this browser");
  }

}

