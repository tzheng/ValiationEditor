<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />

		<!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame
		Remove this if you use the .htaccess -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

		<title>Validation Tool</title>
		<meta name="description" content="This is a page template for reference" />
		<meta name="author" content="Team Samsung SCTS" />

				
		<!-- CSS Framework - Bootstrap, stroll.css -->
		<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.css'/>" />
		<link rel="stylesheet" href="<c:url value='/resources/css/jquery.gritter.css'/>"/>
		<link rel="stylesheet" href="<c:url value='/resources/css/codemirror.css'/>" />
		<link rel="stylesheet" href="<c:url value='/resources/css/chosen.css'/>"/>
			
		<!-- customize css file -->
		<link rel="stylesheet" href="<c:url value='/resources/css/template.css'/>" />
		<link rel="stylesheet" href="<c:url value='/resources/css/validation.css'/>" />	
	</head>

	<body>
		
		<!-- Header of the HTML Page, including Navigation Bar -->
		<header class="nav-space fixed">
		    <div class="nav-wrapper"></div> <!--Wrapper wrap the navbar, specify the background and opacity -->
			
			<div class="columns row-fluid">
					<div class="topbar left" id="topbar-left">
						<a href="http://edcmain-env.elasticbeanstalk.com/index1.html">
							<img class="logo-icon" src="<c:url value='/resources/img/nav_bar/blue-samsung-logo.png'/>" />
						</a>
					</div>
					<div class="topbar left">
						<span class="nav-title">Validation Editor</span>
					</div>
					
					<div class="topbar right" id="topbar-right">
				  			<!--display username-->
		              		<ul class="nav">
			                    <li id="fat-menu" class="dropdown">
			                      <a href="#" id="drop1" role="button" class="dropdown-toggle" data-toggle="dropdown">
				                       <span class="ca-icon">U</span> Barack Obama | Logon: 3:20 PM<b class="caret"></b>
								  </a>
			                      <ul class="dropdown-menu" role="menu" aria-labelledby="drop1">
			                        <li role="presentation"><a role="menuitem" class="nav-item" tabindex="-1" href="#">Action</a></li>
			                        <li role="presentation"><a role="menuitem" class="nav-item" tabindex="-1" href="#">Another action</a></li>
			                      </ul>
			                    </li>
		                  	</ul>

		                  	<!-- dislpay message icon -->
		                  	<ul class="nav" id="nav-msg">
			                    <li class="dropdown">
			                      <a href="#" id="add-regular" role="button" class="dropdown-toggle nav-tooltip" data-toggle="dropdown"
			                     		data-toggle="tooltip" data-placement="bottom" title="Mssages">
										<span class="ca-icon">@</span><sup><span class="hidden-phone">9</span></sup>
								   </a>
			                    </li>
			                    
		                  	</ul>
		                  	<!-- display logout icon -->
		                  	<ul class="nav">
		                  		 <li class="dropdown">
			                     	<a href="#" role="button" class="dropdown-toggle nav-tooltip logout-icon" data-toggle="dropdown"
			                     		data-toggle="tooltip" data-placement="bottom" title="Logout">
										<img src="<c:url value='/resources/img/logout.png'/>" />
								    </a>
			                     </li>
		                  	</ul>
                  	</div>
			</div>
		</header>
		
		<!-- Start to Edit here, replace the div with other content -->
		<div class="mainbody" >
			
			<div class="container-fluid">
				<div class="row-fluid sortable">
					
					<!-- left part of validator -->
					<div class="span2 boxes">
						<!-- box title -->
						<div class="box-title">
							<h2 class="box-title-text">
								<i class="icon-file"></i>
								<span class="break"></span>
								CRFs
							</h2>
		
							<div class="box-title-btn">
								<a href="#" class="btn-minimize nav-tooltip" data-toggle="tooltip" data-placement="bottom" title="Show / Hide"><i class="icon-chevron-up"></i></a>
							</div>
						</div>
						<!-- box-content -->
						<div class="box-content" id="crf_list">
							<ul>
								<c:forEach var="forms" items="${formlist }">
									<li id="formlist${forms.name}" class="" onclick="jumpTo('${forms.name}')">${forms.fullname }</li>
								</c:forEach>
							</ul>
							<input type="hidden" value="${activeform }" id="activeform" />
						</div> 
						
					</div> <!-- end, left part of validator -->
					
					<!-- right part of validator -->
					<div class="span10 sortable">
						
						<!-- Variable list-->
						<div class="row-fluid">
							<div class="span12 boxes">
								<div class="box-title">
									<h2 class="box-title-text">
										<i class="icon-list"></i>
										<span class="break"></span>
										Variable List
									</h2>
				
									<div class="box-title-btn">
										<a href="#" class="btn-minimize nav-tooltip" data-toggle="tooltip" data-placement="bottom" title="Show / Hide"><i class="icon-chevron-up"></i></a>
									</div>
								</div>
								<!-- box-content -->

								<div class="box-content" id="variable_list" >
									
									<div class="container-fluid">
										<div class="row-fluid">
											<div class="span3" id="variable_list_left">
												<ul>
													<c:forEach var="variable" items="${variablelist }">
														<c:choose>
															<c:when test="${variable.defined == 1 }">
																<li onclick="jumpToRule('${variable.name}')">
																		${variable.name} <span class="badge pull-right" style="margin-top: 5px; margin-right: 5px;">Set</span>
																</li>
															</c:when>
															<c:otherwise>
																<li onclick="fillVariable('${variable.name}','${variable.question}')">
																	${variable.name}
																</li>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</ul>
											</div>
											
											<div class="span9" id="variable-rules">
												<form method="post" action="addrule" id="addruleform">
													<input type="hidden" name="form" value="form1" id="variableform" />
													<table class="table table-bordered table-condensed">
														<tbody>
															<tr>
																<td>Variable Name</td>
																<td> 
															  		 <input type="text" name="variableName" id="variableName" placeholder="Variable Name" value="${variablerule.variableName}" />
													  		 	</td>
													  		 	
													  		 	<td>Required</td>
													  		 	<td><label class="radio inline">
																	  <input type="radio" name="required"  value="1" checked> Yes
																	</label>
																	<label class="radio inline">
																	  <input type="radio" name="required"  value="0"> No
																	</label>
																</td>
															</tr>
															
															<tr>
																<td>Rule Name</td>
																<td><input type="text" id="ruleName" class="input-medium" name="ruleName" placeholder="Rule name" value="${variablerule.ruleName}"></td>
																
																<td>Category</td>
																<td>
																	<label class="radio inline">
																	  <input type="radio" name="category" id="optionsRadios1" value="0" checked> Simple
																	</label>
																	<label class="radio inline">
																	  <input type="radio" name="category" id="optionsRadios2" value="1"> Complex
																	</label>
																</td>
															</tr>
															<tr>
																<td>Description</td>
																<td colspan=3><input type="text" class="input-xlarge" id="description" name="description" value="${variablerule.description}"></td>
															</tr>
															<tr>
																<td>Range</td>
																<td><input class="input-mini" type="text" id="rangeFrom" name="rangeFrom" placeholder="From" value="${variablerule.rangeFrom}"/> - 
																	<input class="input-mini" type="text" id="rangeTo" name="rangeTo" placeholder="To" value="${variablerule.rangeTo}"/>
																</td>
																
																<td>Unit</td>
																<td><select class="input-small" name="unit">
																		<option value=" "> </option>
																		<option value="year">years</option>
																	  <option value="cm">cm</option>
																	  <option value="m">m</option>
																	  <option value="kgs">kgs</option>
																	  <option value="lbs">lbs</option>
																	</select> 
																</td>
															</tr>
															
															<tr>
																<td>Invoke Type</td>
																<td colspan=3>
																	<label class="radio inline">
																	  <input type="radio" name="invokeType" id="optionsRadios1" value="1" checked> Save
																	</label>
																	<label class="radio inline">
																	  <input type="radio" name="invokeType" id="optionsRadios2" value="2"> CRF Entry
																	</label>
																	<label class="radio inline">
																	  <input type="radio" name="invokeType" id="optionsRadios2" value="3"> On Demand
																	</label>
																</td>
															</tr>
															
															<input id="code_embed" name="code_embed" type="hidden" value="" />
														</tbody>
													</table>
													<input type ="hidden" id="currentform" name="currentform" value="${activeform }" />
													<a href="#" style="margin-top: -15px;" id="saverulebtn"  class="btn btn-primary" >Add Rule</a>
												</form>
											</div>
										</div>
									</div>
									
								</div> 
							</div>
						</div>
						
						
						
						<div class="row-fluid">
							
							<!-- Common Use script -->
							<div class="span6 boxes">
								<div class="box-title">
									<h2 class="box-title-text">
										<i class="icon-tags"></i>
										<span class="break"></span>
										Commonly Use Script
									</h2>
				
									<div class="box-title-btn">
										<a href="#common_use" class="nav-tooltip" data-toggle="tooltip" data-placement="bottom" id="addscriptbtn" title="Add Script"><i class="icon-plus"></i></a>
										<a href="#" class="btn-minimize nav-tooltip" data-toggle="tooltip" data-placement="bottom" title="Show / Hide"><i class="icon-chevron-up"></i></a>
									</div>
								</div>
								<!-- box-content -->

								<div class="box-content" id="common_use">
									<table class="table table-condensed">
										<thead>
											<tr>
												<th class="span2">ID</th>
												<th class="span6">Name</th>
												<th class="span4">
													<!--   
													Sort by:	
													<select class="form-control input-small" id="sort_by">
														<option>Name</option>
														<option>Frequency</option>
													</select> -->
												</th>
											</tr>
											</thead>
											<tbody>
												<c:forEach var="scripts" items="${scriptlist }">
													<tr>
														<td class="span2">${scripts.id }</td>
														<td class="span8">${scripts.name }</td>
														<td class="span2">
															<a href="#" onclick="CommonUse('${scripts.id}')" class=""><i class=" icon-plus-sign"></i> Use </a>
															<a href="<c:url value='/deletescript?id=${scripts.id }'/>" onclick="" class=""><i class=" icon-trash"></i> Delete</a>
														</td>					
													</tr>
												</c:forEach>
										</tbody>		
									</table>	
									<hr />
								 <!-- <a href="#common_use" class="btn btn-primary pull-right" id="addscriptbtn">Add Script</a> -->
									<div id="addscriptdiv" style="display: none;">
									<form action="addcommonscript" method="post">
										<fieldset>
										    <label>Script Name</label>
										    <input type="text" placeholder="Script Name.." name="scriptname">
										    <br />
										    <label>Script</label>
										    <textarea style="height: 200px; width: 400px;" name="scriptcontent"></textarea>
										    <br />
										    <button type="submit" class="btn btn-primary">Save Script</button>
										    <a href="#common_use" id="cancelsave" class="btn">Cancel</a>
										</fieldset>
									</form>
									</div>
								</div> 
							</div>
							
							
							<!-- Sample Data Test-->
							<div class="span6 boxes">
								<div class="box-title">
									<h2 class="box-title-text">
										<i class="icon-pencil"></i>
										<span class="break"></span>
										Sample Test
									</h2>
				
									<div class="box-title-btn">
										<a href="#" class="btn-minimize nav-tooltip" data-toggle="tooltip" data-placement="bottom" title="Show / Hide"><i class="icon-chevron-up"></i></a>
									</div>
								</div>
								<!-- box-content -->
								<div class="box-content" id="sample_test">
									<form action="sampletest" id="sampletest" method="post">
									<table class="table table-striped table-condensed" id="sample_test_input">
										<tbody>
											<tr>
												<th>Variable Name</th>
												<th>Value</th>
											</tr>
											
											<tr id="variable0">
												<td>
													<select data-placeholder="Variable..." id="select1" class="chzn-select" name="testvar0">
												  		 	<option value=""></option>
															<c:forEach var="variable" items="${variablelist }">
																<option value="${variable.name}">${variable.name}</option>
															</c:forEach>
												  	</select>
												</td>
												<td><input type="text" name="testval0"/> <a href="#sample_test" onclick="DelLine('variable0')"><i class="icon-remove"></i></a> </td>
											</tr>
										</tbody>
									</table>
									<input type="hidden" id="variablenum" name="variablenum" value="1" />
									
									<table class="table table-condensed">
										<tbody>
											<tr>
												<td> </td>
												<td>
													<a class="btn btn-primary" onClick="AddLine()"><i class="icon-plus-sign icon-white"></i> Add Variables</a>  
													<a class="btn btn-primary" id="samplebtn"> <!-- onClick="SubmitSample()" --> <i class="icon-pencil icon-white"></i> Test</a>
												</td>
											</tr>
											<tr>
												<td><strong>Test Result</strong> </td>
												<td style="padding-top: 10px;">
													<strong> <span id="testing" class="label label-info" style="font-size: 18px; display:none;">Testing ... </span></strong>
													<strong> <span class="label label-danger" id="test-result" style="display: none;">False</span> </strong>
												<!-- 
													<c:if test="${empty testresult}">
													</c:if>
													
													<c:if test="${testresult == 'True'}">
														<strong> <span class="label label-success">True</span></strong> 
													</c:if>
													<c:if test="${testresult == 'False'}">
														<strong> <span class="label label-danger">False</span> </strong>
													</c:if>
													 -->
												</td>
											</tr>
										</tbody>
									</table>
									</form>
								</div> 
							</div>
						</div>
					</div> <!--end, right part of validator -->
				</div>
			</div> <!-- end of .container-fluid -->
			
			<div class="boxes" id="bottomNav">
				<div class="box-title script-edit script-edit-hide">
					<h2 class="box-title-text" id="editor-title" style="width: 90%; color: #efefef; text-align: center; text-shadow: none;">
						<strong><i class="icon-arrow-up icon-white arrow1"></i> Script Editor Panel <i class="icon-arrow-up icon-white arrow1"></i></strong> 
					</h2>

					<div class="box-title-btn">
						<a href="#"  class="nav-tooltip" data-toggle="tooltip" data-placement="top" title="Show / Hide Script Editor"><i class="icon-chevron-down icon-white arrow"></i></a>
					</div>
				</div>
									
				<div class="box-content" id="text-box-content" style="${displayoption}">
					<div class="row-fluid">
						<div class="span8" id="editor_space">
							<div class="row-fluid" >
								<div class="span5 editor-btn">
									<div class="btn-toolbar">
										<div class="btn-group">
									    	<button class="btn">Add</button>
									    	<button class="btn">If..Else</button>
									    	<button class="btn">NULL</button>
									    	<button class="btn">AND</button>
									    	<button class="btn">OR</button>
										</div>
									</div>
							    </div>
								
								<div class="span4 editor-btn">
									<div class="btn-toolbar">
										<div class="btn-group">
									    	<button class="btn"> = </button>
									    	<button class="btn"> != </button>
									    	<button class="btn"> &gt; </button>
									    	<button class="btn"> &lt; </button>
									    	<button class="btn"> || </button>
									    	<button class="btn"> && </button>
										</div>
									</div>
								</div>
								
								<div class="span3 editor-btn">
									<div class="btn-toolbar">
										<div class="btn-group">
									    	<button class="btn"> + </button>
									    	<button class="btn"> - </button>
									    	<button class="btn"> x </button>
									    	<button class="btn"> \ </button>
									    	<button class="btn">( )</button>
										</div>
									</div>
								</div>
							</div>
							
							<form id="script" method="post" action="grammarcheck">
								<div class="row-fluid" id="code-area">
									<div class="span12">
										<textarea id="code" name="code">${code}</textarea>
									</div>
								</div>
								<input type ="hidden" id="formname" name="formname" value="${activeform }" />
								<input type="hidden" id="submittype" name="submittype" value="GrammarCheck" />
								<input type="hidden" id="scriptid" name="scriptid" value="" />
								<a class="btn pull-right btn-primary" id="grammer_check" onclick="formSubmit('SaveScript')">Save Script</a>
								<a class="btn pull-right btn-primary" id="grammer_check" onclick="formSubmit('GrammarCheck')">Grammar Check</a>
							</form>
						</div>
						
						<div class="span4">
							<strong>Message</strong>
							<div id="grammer-check-result">
								<c:choose>
									<c:when test="${hasnoerror == 1 }">
										<div class="alert alert-success">
										  No Grammar Error
										</div>
									</c:when>
									<c:when test="${hasnoerror == 2 }">
										<div class="alert alert-success">
										  Save Script Successfully.
										</div>
									</c:when>
									<c:otherwise>
										<c:forEach var="errors" items="${errorlist }">
										<dl class="dl-horizontal">
										  <dt>${errors.line }</dt>
										  <dd>${errors.content } </dd>
										</dl>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div> <!-- end of .box-content -->
			</div>
		</div>
	 	
	 	
    	<!-- footer of the page -->
    	<footer id="validation-footer">
    		
    	</footer>
    	
    	<!-- javascripts -->
    	<script src="http://code.jquery.com/jquery-1.9.0.js"></script>
    	<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
    	<script src="<c:url value='/resources/js/bootstrap.js'/>"></script>
    	<script src="<c:url value='/resources/js/bootstrap-dropdown.js'/>"></script>
    	<script src="<c:url value='/resources/js/bootstrap-tooltip.js'/>"></script>
    	<script src="<c:url value='/resources/js/bootstrap-popover.js'/>"></script>
    	
		<script src="<c:url value='/resources/js/codemirror.js'/>"></script>
		<script src="<c:url value='/resources/js/scripteditor/javascript.js'/>"></script>
    	<script src="<c:url value='/resources/js/jquery.gritter.min.js'/>"></script>
    	<script src="<c:url value='/resources/js/chosen.jquery.min.js'/>"></script>
    	
    	<script src="<c:url value='/resources/js/template.js'/>"></script>
    	<script src="<c:url value='/resources/js/validation.js'/>"></script>
    	
    	<script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
    	
	</body>
</html>