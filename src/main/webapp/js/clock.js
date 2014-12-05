var se, cost, m = 0, h = 0, s = 1;
function second() {
    if (s > 0 && (s % 60) == 0) { m += 1; s = 0; }
    if (m > 0 && (m % 60) == 0) { h += 1; m = 0; }
    t = (m <= 9 ? "0" + m : m) + ":" + (s <= 9 ? "0" + s : s); 
    s += 1;
}
function countdown() {
	var ss = parseInt($(".clock em").text());
    ss -= 1;
    if(ss < 0){
    	ss = 0;
    }
    $(".clock em").text(ss);
    if(ss==0){
    	if(parseInt($(".clock").attr("answer")) == 1){
    		$("#time").val(s);
    		$("#choice").val(5);
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
		cost = setInterval("second()", 1000); 
	}
);
$(document).ready(function(){
	$("#form_q").validate({
		ignore: [],
		rules:{
			choice:{
				min:1
			},
			messages:{
				choice:{
					min:"还未选择答案"
				}
			},
			errorElement:"em"
		}
	});
});