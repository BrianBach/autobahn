// Mod: Special Days for Xin Calendar 2 (In-Page/Popup-Window)
// Copyright 2004  Xin Yang    All Rights Reserved.

function setLoopWeek(co,cw,cx,jh,ke){var eu=0;for(var i=5;i<setLoopWeek.arguments.length;i++){if(setLoopWeek.arguments[i].search(/(sun|mon|tue|wed|thu|fri|sat)/i)!=-1){eu=xc_br(RegExp.$1);for(j=jh-1;j<6;j+=ke){xc_eh(co,"jb",j+":"+eu,[cw,cx],0);if(eu==0){xc_eh(co,"jb",j+":7",[cw,cx],0)}}}}};function setLoopMonth(co,cw,cx,jg,hp){for(var i=5;i<setLoopMonth.arguments.length;i++){for(j=jg-1;j<12;j+=hp){xc_eh(co,"jb",xc_cd(j)+":"+xc_cd(setLoopMonth.arguments[i]),[cw,cx],0)}}};function setLoopYear(co,cw,cx,ec){var m,d,av;var hi=ec.search(/mm/i),hm=ec.search(/mon/i),fd=ec.search(/dd/i);var ef=new RegExp('^'+aj(ec)+'$');for(var i=4;i<setLoopYear.arguments.length;i++){av=setLoopYear.arguments[i];if(ef.test(av)){if(hi!=-1){m=av.substring(hi,hi+2)-1}else{m=xc_de(av.substring(hm,hm+3))};d=av.substring(fd,fd+2)-0;xc_eh(co,"jb",xc_cd(m)+":"+xc_cd(d),[cw,cx],0)}}};function xc_ff(bj){return bj.ge("jb",xc_cd(bj.cf)+":"+xc_cd(bj.cd))||bj.ge("jb",bj.cg+":"+bj.ce)};xc_fd[xc_fd.length]=xc_ff;
