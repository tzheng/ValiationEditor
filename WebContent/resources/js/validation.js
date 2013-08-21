    var editor;
	$( "#code" ).change(function() {
	   document.getElementById("currentcode").value = $(this).val();
	});
	
	//fold and unfold div boxes
   var init = 0;
   $(document).ready(function(){
	   $("#validation-footer").css("margin-bottom","380px");
	   
	   var activef = "formlist" + $('#activeform').val();
	   document.getElementById(activef).className = "form-active"
		   
		   $('#samplebtn').click(function(){
			   $('#test-result').hide();
			   $('#testing').fadeIn('slow');
			   $('#testing').delay(2000).fadeOut(500);
			   $('#test-result').delay(3000).fadeIn(1000);
		   });
	   
	   $("#saverulebtn").click(function(){
		   $("#code_embed").val(editor.getValue());
		   document.getElementById("addruleform").submit();
		});
	   
	   if (init == 0) {
		       editor = CodeMirror.fromTextArea(document.getElementById("code"), {  
		       lineNumbers: true,  
		       extraKeys: {"Ctrl-Space": function(cm) {CodeMirror.simpleHint(cm, CodeMirror.javascriptHint);}}  
		   });  
		   $(".CodeMirror-scroll").hover(function(){  
		       $(this).get(0).style.cursor = "text";  
		   }); 
		   init = init + 1;
		}
	   
	   $("#addscriptbtn").click(function() {
		   $("#addscriptdiv").fadeIn(250);
		   $("#common_use").css("height","520px");
	   });
	   
	   $("#cancelsave").click(function() {
		   $("#addscriptdiv").fadeOut(250);
		   $("#common_use").css("height","230px");
	   });
	   
	   var $target = $(".script-edit").next('.box-content');
  		
   });
   
   
   $(".script-edit").click(function(){
   		var $target = $(this).next('.box-content');
   		
   		
   		if($target.is(':visible')) 
   			{ 
   				$('.arrow',$(this)).removeClass('icon-chevron-up').addClass('icon-chevron-down');
   				$('.arrow1').removeClass('icon-arrow-down').addClass('icon-arrow-up');
   				$("#validation-footer").css("margin-bottom","200px");
   			} else {
   				$('.arrow',$(this)).removeClass('icon-chevron-down').addClass('icon-chevron-up');
   				$('.arrow1').removeClass('icon-arrow-up').addClass('icon-arrow-down');
   				$("#validation-footer").css("margin-bottom","380px");
   			} 	
   		
   		$target.slideToggle();
   		
			 if (init == 0) {
	   			       editor = CodeMirror.fromTextArea(document.getElementById("code"), {  
	   			       lineNumbers: true,  
	   			       extraKeys: {"Ctrl-Space": function(cm) {CodeMirror.simpleHint(cm, CodeMirror.javascriptHint);}}  
	   			   });  
	   			   $(".CodeMirror-scroll").hover(function(){  
	   			       $(this).get(0).style.cursor = "text";  
	   			   }); 
	   			   init = init + 1;
	   			} else {
	   				editor.refresh();
	   			}
   		
   });
   
   
    //function for Sample test
   var elementCount = 0; 
	function AddLine(){     
       var TemO=document.getElementById("sample_test_input");     
       var newInput = document.createElement("tr");      
         
       elementCount = elementCount + 1;     
         
        
       newInput.id="variable"+(elementCount);     
       var selectId = "varselect" + (elementCount)
       newInput.innerHTML = '<tr>' 
							+	'<td>'
							+		'<select data-placeholder="Variable..." class="chzn-select" id="'+selectId+'" name="testvar'+ elementCount +'">'
							+				 	'<option value=""></option>'
							+		'</select>'
							+	'</td>'
							+	'<td> <input type="text" name="testval'+ elementCount +'"/>  <a href="#sample_test" onclick="DelLine(\'variable'+elementCount+'\')"> <i class="icon-remove"></i> </a> </td>'
							+	'</tr>';
            
       TemO.appendChild(newInput);    
       document.getElementById("variablenum").value = elementCount + 1;
       var select1 = document.getElementById("select1");
       var select2 = document.getElementById(selectId);
       select2.innerHTML = select1.innerHTML;
       $(".chzn-select").chosen();
   }       
    
	function DelLine(lineId) {
		var tempD = document.getElementById(lineId);
		var elecount = document.getElementById("variablenum").value;
		document.getElementById("variablenum").value = elecount - 1;
		elementCount = elementCount - 1;
		tempD.remove();
	}

	function formSubmit(action) {
		document.getElementById("submittype").value = action;  
		document.getElementById("script").submit();
	}
	
	function CommonUse(scriptid) {
		document.getElementById("scriptid").value = scriptid;
		document.getElementById("submittype").value = "CommonUse"; 
		document.getElementById("script").submit();
	}
	
	function SubmitSample() {
		document.getElementById("sampletest").submit();
	}
	
	function jumpTo(id) {
		window.location = '/getform?formname=' + id;
	}
	
	function jumpToRule(id) {
		window.location = '/getrule?variable=' + id;
	}
	
	function fillVariable(name, question) {
		document.getElementById("variableName").value = name;
		document.getElementById("description").value = question;
		document.getElementById("ruleName").value = "";
		document.getElementById("rangeFrom").value = "";
		document.getElementById("rangeTo").value = "";
	}
