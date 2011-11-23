
$("#ajaxerror").ajaxError(function() {
  $(this).text('An error occured..please try again.');
});

$.fn.serializeObject = function()
{
    var o = {};
    var a = $(":input").serializeArray();
   // alert(a);
    jQuery.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || "");
        } else {
            o[this.name] = this.value || "";
        }
    });
    return o;
};

var form_init = function() {
/*
 //$("#logform").ajaxForm(options);
 $("#logform").submit(function(){

//   $("#logform").ajaxSubmit({
 //   target:     "#pageContent",
 //   url:        "/autobahn-gui/j_spring_security_check",
//    success:    function(data) {

 //       $("#pageContent").html(data);
 //       return false;
 //   }
      var aa=$("#logform").serialize();
    
    jQuery.post("/autobahn-gui/j_spring_security_check", aa,
   function(data,textStatus, XMLHttpRequest){


   //if(!jQuery.browser.mozilla)
   
   //{ $("body").html(data);}
  // else {
   //alert("Login was successful..redirecting to secure area");
   window.location = "/autobahn-gui/portal/secure/request-service.htm";
   //}

   },"html");


     //return false to prevent normal browser submit and page navigation 
   return false; 

});
*/
 
// select the overlay element - and "make it an overlay"
$("#facebox").overlay({
 
	// custom top position
	top: 200,
 
	// some mask tweaks suitable for facebox-looking dialogs
	mask: {
 
		// you might also consider a "transparent" color for the mask
		color: '#fff',
 
		// load mask a little faster
		loadSpeed: 200,
 
		// very transparent
		opacity: 1.0
	},
 
	// disable this for modal dialog-type of overlays
	closeOnClick: false,
 
	// load it immediately after the construction
	load: true
 
});
}