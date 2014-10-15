var se, m = 0, h = 0, s = 0;
function second() {
    if (s > 0 && (s % 60) == 0) { m += 1; s = 0; }
    if (m > 0 && (m % 60) == 0) { h += 1; m = 0; }
    t = (m <= 9 ? "0" + m : m) + ":" + (s <= 9 ? "0" + s : s); 
    $("#showtime").text(t);
    s += 1;
}
function calcCurrentTime(){
	
}
$(document).ready(
	function startclock() { 
		se = setInterval("second()", 1000); 
	}
);