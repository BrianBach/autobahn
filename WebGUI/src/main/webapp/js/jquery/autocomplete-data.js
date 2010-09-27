var gui = ["http://localhost:8081/autobahn-gui/service/gui", "ftp://localhost:8083/autobahn-gui/service/gui","http://localhost3:8081/autobahn-gui/service/gui","http://server2:8081/autobahn-gui/service/gui","http://server:8085/autobahn-gui/service/gui"];
var areaid = new Array();

for (i=0;i<100;i+=10){
    for (j=0;j<10;j++){
    areaid[i+j]=i+"."+j+".0.0";
    }
}