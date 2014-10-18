var se, m = 0, h = 0, s = 0;
function second() {
    if (s > 0 && (s % 60) == 0) { m += 1; s = 0; }
    if (m > 0 && (m % 60) == 0) { h += 1; m = 0; }
    t = (m <= 9 ? "0" + m : m) + ":" + (s <= 9 ? "0" + s : s); 
    $(".clock").text("倒计时"+t);
    s += 1;
}
function countdown() {
	var ss = parseInt($(".clock em").text());
    ss -= 1;
    $(".clock em").text(ss);
    if(ss==0){
    	if(parseInt($(".clock").attr("answer")) == 1){
    		$("#form_q").submit();
    	}else{
    		location.href= $(".clock").attr("url");
    	}
    }
}
function calcCurrentTime(){
	
}
$(document).ready(
	function startclock() { 
		se = setInterval("countdown()", 1000); 
	}
);