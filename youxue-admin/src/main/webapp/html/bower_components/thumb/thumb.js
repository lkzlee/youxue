$(document).ready(function(){
	$(".show-carousel").click(function(){
		var id = this.id;
		var numSelect = parseInt(id.substr(9,id.length));
		var divId = "carousel-div"+numSelect;
		

  
		$("#"+divId).modal();
	})
	$("#close").click(function(){
	
	})
})