

/*Support dropdown menu*/
$('.dropdown-toggle').dropdown();


/* Display the toop tip*/
$('.nav-tooltip').tooltip();



$('.nav-popover').popover({html : true});

$(document).ready(function(){
  $('.nav-popover').popover({ 
    html : true,
    content: function() {
      return $('#popover_content_wrapper').html();
    }
  });
});

$(document).ready(function(){
	  $('.danger').popover({ 
	    html : true,
	    content: function() {
	      return $('#popover_content_wrapper').html();
	    }
	  });
  	
	/*make <div> sortablt, so that user can move div to any place they want*/
	$(".sortable").sortable();
	
	/*multiple select function */
	$(".chzn-select").chosen();
	
	/*date picker*/
	$('.datepicker').datepicker();
});

   //fold and unfold div boxes
   $(".btn-minimize").click(function(){
   		var $target = $(this).parent().parent().next('.box-content');
   		
   		if($target.is(':visible')) $('i',$(this)).removeClass('icon-chevron-up').addClass('icon-chevron-down');
		else 					   $('i',$(this)).removeClass('icon-chevron-down').addClass('icon-chevron-up');
		$target.slideToggle();
   });
   
   //show/hide filter div for EDC Main screen
	$("#filter-trigger").click(function(){
		$("#filter-box").toggle('slow');
		
		$(".chzn-select").chosen();
	});
	
	$('#hide-filter-btn').click(function(){
		$("#filter-box").toggle('slow');
	});
	
