// Mod: Time for Xin Calendar 2 (In-Page/Popup-Window)
// Copyright 2005  Xin Yang    All Rights Reserved.

var xc_bw=new Array();
function xc_aa(jk){
xc_bw[xc_bw.length]=jk}

;function xc_ci(date){
var gt=xcDateFormat.search(/hr/i);
var hf=xcDateFormat.search(/mi/i);
var jc=xcDateFormat.search(/ss/i);
var as=xcDateFormat.search(/am/i);
var hr=gt<0?"":date.substring(gt,gt+2);
var mi=hf<0?"":date.substring(hf,hf+2);
var ss=jc<0?"":date.substring(jc,jc+2);
var am=as<0?"":date.substring(as,as+2);
return new Array(hr,mi,ss,am)};
function xc_ew(date,hr,mi,ss,am)
{var gt=xcDateFormat.search(/hr/i);
var hf=xcDateFormat.search(/mi/i);
var jc=xcDateFormat.search(/ss/i);
var as=xcDateFormat.search(/am/i);
if(gt>=0){date=date.substring(0,gt)+hr+date.substring(gt+2)};if(hf>=0){date=date.substring(0,hf)+mi+date.substring(hf+2)};if(jc>=0){date=date.substring(0,jc)+ss+date.substring(jc+2)};if(as>=0){date=date.substring(0,as)+am+date.substring(as+2)};return date};function xc_fa(jk,ir,fe,ha,bu){if(typeof(jk.gw)=="undefined"){jk.gw=String((new Date()).getTime());xc_aa(jk)};var fr="xc_"+jk.gw;var gq=["","","",""],gn=["hr","mi","ss","am"],gp=[xcHours,xcMinutes,xcSeconds,xcAMPM],go="";var du=xc_bx(bu),date=du.gd()||du.fe;if(checkDate(date)==0){gq=xc_ci(date)};go=xc_fo+xc_fi;for(var i=0;i<gn.length;i++){if(xcDateFormat.toLowerCase().indexOf(gn[i])!=-1){go+="<TD><SELECT ID='"+fr+"_"+gn[i]+"' CLASS='"+xcCSSTimeList+"' ONCHANGE='xc_er("+bu+")'>";for(var j=0;j<gp[i].length;j++){go+="<OPTION";if(gp[i][j]==gq[i]){go+=" SELECTED"};go+=">"+gp[i][j]+"</OPTION>"};go+="</SELECT></TD>"}};go+=xc_fh+xc_fn;return go};function xc_er(gx){var du=xc_bx(gx);du.bf();var bo=du.bi(du.ao(du.gd()||du.fe||getCurrentDate()));du.kb(bo);du.ar(bo);du.ha=bo};function xc_ad(){xcCSSFootOther[xcCSSFootOther.length]=xcCSSTimeBlock;xcFootButtons[xcFootButtons.length]=xc_fa;xcFootButtonSwitch[xcFootButtonSwitch.length]=xcTimeBlockOrder;xcFootButtonLinks[xcFootButtonLinks.length]=null};xc_ad();function xc_al(date){var gv=hh=je=au="";var fi=xcCore==2?this.kf.document:document;if(/hr/i.test(xcDateFormat)){var gu=fi.getElementById("xc_"+this.jk.gw+"_hr");gv=gu.options[gu.selectedIndex].text};if(/mi/i.test(xcDateFormat)){var hg=fi.getElementById("xc_"+this.jk.gw+"_mi");hh=hg.options[hg.selectedIndex].text};if(/ss/i.test(xcDateFormat)){var jd=fi.getElementById("xc_"+this.jk.gw+"_ss");je=jd.options[jd.selectedIndex].text};if(/am/i.test(xcDateFormat)){var at=fi.getElementById("xc_"+this.jk.gw+"_am");au=at.options[at.selectedIndex].text};return beforeSetDateValue(this.ir,this.jk,xc_ew(date,gv,hh,je,au))};function compareDates(dz,ea){var bx=xc_ce();var d1=getDateNumbers(bx.test(dz)?dz:getCurrentDate()).join("");var d2=getDateNumbers(bx.test(ea)?ea:getCurrentDate()).join("");var t1=xc_ci(dz);var t2=xc_ci(ea);d1+=t1[0]==""?"00":t1[3]==xcAMPM[1]?xc_cd(parseInt(t1[0],10)+12):t1[0];d2+=t2[0]==""?"00":t2[3]==xcAMPM[1]?xc_cd(parseInt(t2[0],10)+12):t2[0];d1+=t1[1]==""?"00":t1[1];d2+=t2[1]==""?"00":t2[1];d1+=t1[2]==""?"00":t1[2];d2+=t2[2]==""?"00":t2[2];return(d1==d2?0:d1>d2?1:-1)};
