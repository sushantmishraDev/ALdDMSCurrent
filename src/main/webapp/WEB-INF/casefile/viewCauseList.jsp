<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page import="java.util.List"%>
        <%@ page import="com.dms.model.ObjectMaster"%>
            <%@ page import="com.dms.model.User"%>

                <%
User user = null;
if (session.getAttribute("USER") != null)
	user = (User) session.getAttribute("USER");
 String role = user.getUserroles().get(0).getLk().getLk_longname();
%>


                    <jsp:include page="../content/header2.jsp"></jsp:include>



<script>
$(document).ready(function(){
	var a=10006;
		$(document.body).on('mousedown', '.modal-header' ,function(){
		$( ".modal-content" ).resizable().find('.close').click(function(e){ $(this).parent().parent().remove();});
    	});
    	
});

//,stack: ".myform div"
</script>
<style>
.top {z-index: 10005;}
.bottom {z-index:10004;}
.row {
    margin: 0;
}
 #myModal {
    position: absolute;
    text-align: center; 
    height:100%;
    width:43%;
    z-index: 10004;
}
#myModalHeader {
    cursor: move;
    background-color: #2196F3;
    color: #fff;
}
#myDiv.fullscreen{
    z-index: 9999; 
    margin-left: 12%;
   margin-top: 3%;
    width: 88%; 
    position: fixed; 
    top: 0; 
    left: 0; 
 }
  #myDiv.fullscreenRight{
    z-index: 9999; 
    margin-right: 12%;
   margin-top: 3%;
    width: 88%; 
    position: fixed; 
    top: 0; 
    right: 0; 
 }
.modal-body{
 height: 100%;
    resize: none;
} 
.close:hover{
	background-color:white;
}

.affix {
    top: 0;
    width: 100%;
    z-index: 9999 !important;
  }

  .affix + .container-fluid {
    padding-top: 70px;
  }
  
  .priviousButton {
    appearance:none;
    -webkit-appearance:none;
    background:transparent;
    border:none;
    outline:none;
    background-image:url(${pageContext.request.contextPath}/images/go_previous_blue.png);
    background-size:25px 25px;
    background-position: 18px 5px;
     padding:20px 20px;
    /* padding-top:2px;
    padding-bottom:2px;
    padding-left:5px;
    padding-right:5px; */
    background-repeat:no-repeat;
    cursor:pointer;
}


.priviousButton:focus {
    appearance:none;
    -webkit-appearance:none;
    background:transparent;
    border:none;
    outline:none;
    background-image:url(${pageContext.request.contextPath}/images/go_previous_blue.png);
    background-size:25px 25px;
    background-position: 18px 5px;
   transform: rotate(340deg);
     padding:20px 20px;
    /* padding-top:2px;
    padding-bottom:2px;
    padding-left:5px;
    padding-right:5px; */
    background-repeat:no-repeat;
    cursor:pointer;
}


.nextButton {
    appearance:none;
    -webkit-appearance:none;
    background:transparent;
    border:none;
    outline:none;
    background-image:url(${pageContext.request.contextPath}/images/go_next_blue.png);
    background-size:25px 25px;
    background-position: 18px 5px;
    padding:20px 20px;
    background-repeat:no-repeat;
    cursor:pointer;
}

.nextButton:focus {
    appearance:none;
    -webkit-appearance:none;
    background:transparent;
    border:none;
    outline:none;
    transform: rotate(30deg);
    background-image:url(${pageContext.request.contextPath}/images/go_next_blue.png);
    background-size:25px 25px;
    background-position: 18px 5px;
    padding:20px 20px;
    background-repeat:no-repeat;
    cursor:pointer;
}

.greenyellow {
    background-color: #21f73bd6; 
}

.animateme {
    
  animation: 2s infinite bgcolorchange;
}
@keyframes bgcolorchange {
  
  25% {
    background: #21f73bd6;
  }
  50% {
    background: white;
  }
  75% {
    background: #21f73bd6;
  }
  100% {
    background-color: white;
  }
}
@-webkit-keyframes bgcolorchange {
    0%   {background:  #21f73bd6;}
    25%  {background: white;}
    75%  {background:  #21f73bd6;}
    100% {background: white;}
}
  
 

</style>



                    <div id="content" class="content">
                        <div class="container-fluid" ng-controller="CaseFileCtrl">
                          <!-- Sushant -->
                            <div id="mySidenav" class="sidenav" title="Notes">

                                <textarea rows="5" id="comment" placeholder="insert some text" ng-model="nt.nt_notes"  ng-model-options="{debounce: 500}" ng-change="saveNotes(nt)"></textarea>

                            </div>

                            <!--sushant  -->
                         <div class="myform"></div>
                            <div class="row">
                                <div class="panel panel-inverse" style="min-height:950px;">
                                 <div class="col-md-4" id="metaDataPanel">
                                    <div class="panel-heading" id ="casePanelHeading" style ="background-color:white; color :black; top:50.2px; position:sticky; z-index:1;">
                                        <div class="panel-heading-btn" >
                                            <!--  <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" ng-click ="changeTop()" data-click="panel-expand"><i class="fa fa-expand"></i></a> -->
                                      <!--       <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" ng-click ="changeTop()" data-click="panel-expand"><i class="fa fa-expand"></i></a> -->
                                            
                                            
                                        </div>
                                       
                                       <!--  <h4 class="panel-title" style="font-size: 4mm;">{{casefile.caseType.ct_name}} ({{casefile.caseType.ct_label}}) &nbsp No.&nbsp{{casefile.fd_case_no}}&nbsp of &nbsp{{casefile.fd_case_year}}</h4> -->
       <!-- <h4 class="panel-title" style="font-size: 5mm;     font-weight: bold;">{{casefile.caseType.ct_label}}  No.&nbsp{{casefile.fd_case_no}}&nbsp of &nbsp{{casefile.fd_case_year}} <span>
       <button ng-show ="pFlag" type="button" ng-click="getPreviousCase('P')" class="priviousButton" ></button>
        <span ng-show ="!pFlag"  >First</span>
         <span style ="color :red" >{{cl_serial_noCurrent}}</span>
        
       <button ng-show ="nFlag"  type="button" class="nextButton"  ng-click="getNextCase('N')"></button> </span>
       
         <span ng-show ="!nFlag" >Last</span></h4> -->
         
         
         <h4 class="panel-title" style="font-size: 4mm;     font-weight: bold;">{{casefile.caseType.ct_label}}  No.&nbsp{{casefile.fd_case_no}}&nbsp of &nbsp{{casefile.fd_case_year}} <span ng-show ="nFlag || pFlag">
       <button ng-show ="pFlag" type="button" ng-click="getPreviousCase('P')" class="priviousButton" ></button>
     <!--    <span ng-show ="!pFlag"  >First</span> -->
       <!--   <span style ="color :red" >{{cl_serial_noCurrent}}</span> -->
       
       <select style="width: 30%;" class="form-control" id="serial" 
							ng-model="cl_serial_noCurrent1" name="serial"
							
							 ng-change="changeSerial()"
							select2="">
							<option>Select Serial Number</option>
							<option ng-repeat=" cl_serial_noCurrent1 in allSerial">{{cl_serial_noCurrent1}}</option>
						</select>
        
       <button ng-show ="nFlag"  type="button" class="nextButton"  ng-click="getNextCase('N')"></button>
        
        </span>
       <span>
      
       </span>
       
       <!--   <span ng-show ="!nFlag" >Last</span></h4> -->
       
  
                                        
                                        <c:if test="${isImpugnedOrder==1}">
                                            <h4 class="panel-title">Impugned Order Details ${casetype}/${caseno}/${caseyear}</h4>
                                        </c:if>
                                    </div>
                                     <div class="panel-heading" style ="background-color:white; color :white; top:90.2px; position:sticky; z-index:1; padding:8px 0px 5px 0px;">
                                        <div class="panel-heading-btn" >
                                            <!--  <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" ng-click ="changeTop()" data-click="panel-expand"><i class="fa fa-expand"></i></a> -->
                                      <!--       <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" ng-click ="changeTop()" data-click="panel-expand"><i class="fa fa-expand"></i></a> -->
                                            
                                            
                                        </div>
                                       <div class="btn-group" role="group" style ="position:sticky; top:90.2px;">

<%
	if (role.equals("ECOURT") || role.equals("Judge")) {
%>
<button class="btn btn-info btn-sm" ng-click="showStampReport(stFile)"  style ="position:sticky; top:90.2px;">Stamp Report</button> 
 <button class="btn btn-success btn-sm" ng-click="openNav()" id="btn_click" style ="position:sticky; top:90.2px;">Notes</button> 
 
 <%
	}



%> 

  <button class="btn btn-success btn-sm" ng-click="viewAllOrders()" style ="position:sticky; top:90.2px;">View All Orders</button> 

 <!-- <a href="#collapseImpugnedOrders" class="btn btn-success btn-sm">Impugned Orders</a> -->
 
 
 

<%
	if (role.equals("DMSAdmin") || role.equals("ECOURT") || role.equals("Judge")) {
%>
<button class="btn btn-success btn-sm" ng-click="downloadfiles()" style ="position:sticky; top:90.2px;">DownloadFile</button>

<span ng-if="casefile.notificationCount">
	<button class="btn btn-success btn-sm" data-toggle="modal"
			ng-click="getNotification(casefile)" id="notification" data-target="#notificationview"  style ="position:sticky; top:90.2px;">Objection
			<span class="badge" ng-if=casefile.notificationCount!="0">{{casefile.notificationCount}}</span>
	</button>
</span>

<span ng-if="casefile.pendingAppCount">
	<button class="btn btn-success btn-sm" data-toggle="modal"
			ng-click="getPendingApp(casefile)" id="notification" data-target="#pendingAppview"  style ="position:sticky; top:90.2px;">Scrutiny Pending
			<span class="badge" ng-if=casefile.pendingAppCount!="0">{{casefile.pendingAppCount}}</span>
	</button>
</span>

	
<%
	}


%>

<%
	if (role.equals("ECOURT") || role.equals("Judge")) {
%>
 <span >
		<button class="btn btn-success btn-sm" ng-if=casefile.fd_rc_flag==true id="rc"  ng-click="setReservedCase(casefile.fd_id)" style ="position:sticky; top:90.2px;">
			<span style="color: white;">Unreserved</span> 
		</button>
		<button class="btn btn-success btn-sm" ng-if=casefile.fd_rc_flag==false id="rc" style="position:sticky; top:90.2px;" ng-click="setReservedCase(casefile.fd_id)">
			<span style="color: white;">Reserved</span> 
		</button>
</span> 
 
 <%
	}

%> 
</div>
                                        <%-- <h4 class="panel-title" style="font-size: 4mm;">Case File Details &nbsp{{casefile.caseType.ct_label}}/ &nbsp{{casefile.fd_case_no}}/ &nbsp{{casefile.fd_case_year}}</h4>
                                        <c:if test="${isImpugnedOrder==1}">
                                            <h4 class="panel-title">Impugned Order Details ${casetype}/${caseno}/${caseyear}</h4>
                                        </c:if> --%>
                                    </div>
                                    
                                  


                                    <div class="panel-body" style ="padding-top:0px;">
                                        <input type="hidden" class="form-control" value=${doc_id} id="doc_id" name="doc_id">
                                        <input type="hidden" class="form-control" value=${document_name} id="document_name" name="document_name">
                                         <input type="hidden" class="form-control" value=${cl_court_no} id="cl_court_no" name="cl_court_no">
                                          <input type="hidden" class="form-control" value=${cl_rec_status} id="cl_rec_status" name="cl_rec_status">
                                           <input type="hidden" class="form-control" value=${cl_serial_no} id="cl_serial_no" name="cl_serial_no">
                                            <input type="hidden" class="form-control" value=${cl_dol} id="cl_dol" name="cl_dol">
                                             <input type="hidden" class="form-control" value=${cl_list_type_mid} id="cl_list_type_mid" name="cl_list_type_mid">
                                               <input type="hidden" class="form-control" value=${cl_fd_mid} id="cl_fd_mid" name="cl_fd_mid">
                                                 <input type="hidden" class="form-control" value=${cl_ayr} id="cl_ayr" name="cl_ayr">
                                                       <input type="hidden" class="form-control" value=${cl_ano} id="cl_ano" name="cl_ano">
                                               
                                               
                                               
                                                <input type="hidden" class="form-control" value=${cl_court_no1} id="cl_court_no1" name="cl_court_no1">
                                          <input type="hidden" class="form-control" value=${cl_rec_status1} id="cl_rec_status1" name="cl_rec_status1">
                                           <input type="hidden" class="form-control" value=${cl_serial_no1} id="cl_serial_no1" name="cl_serial_no1">
                                            <input type="hidden" class="form-control" value=${cl_dol1} id="cl_dol1" name="cl_dol1">
                                             <input type="hidden" class="form-control" value=${cl_list_type_mid1} id="cl_list_type_mid1" name="cl_list_type_mid1">
                                               <input type="hidden" class="form-control" value=${cl_fd_mid1} id="cl_fd_mid1" name="cl_fd_mid1">
                                                 <input type="hidden" class="form-control" value=${cl_serial_noCurrent} id="cl_serial_noCurrent" name="cl_serial_noCurrent">
                                                    <input type="hidden" class="form-control" value=${cl_ayr1} id="cl_ayr1" name="cl_ayr1">
                                                       <input type="hidden" class="form-control" value=${cl_ano1} id="cl_ano1" name="cl_ano1">
                                                       <!--  <input type="hidden" class="form-control" value=${minSerial} id="minSerial" name="minSerial">
                                                         <input type="hidden" class="form-control" value=${maxSerial} id="maxSerial" name="maxSerial"> -->
                                                         
                                                          <input type="hidden" class="form-control" value=${allSerial} id="allSerial" name="allSerial">
                                                       
        
			
			   <input type="hidden" class="form-control" value=${cl_court_noCurrent} id="cl_court_noCurrent" name="cl_court_noCurrent">
                                          <input type="hidden" class="form-control" value=${cl_rec_statusCurrent} id="cl_rec_statusCurrent" name="cl_rec_statusCurrent">
                                           <input type="hidden" class="form-control" value=${cl_serial_noCurrent} id="cl_serial_noCurrent" name="cl_serial_noCurrent">
                                            <input type="hidden" class="form-control" value=${cl_dolCurrent} id="cl_dolCurrent" name="cl_dolCurrent">
                                             <input type="hidden" class="form-control" value=${cl_list_type_midCurrent} id="cl_list_type_midCurrent" name="cl_list_type_midCurrent">
                                             
                                                 <input type="hidden" class="form-control" value=${listTypeName} id="listTypeName" name="listTypeName">
			
			
			
                                               
                                        <!-- <div class="col-md-12"> -->
										 
                                            <%-- <jsp:include page="treeview.jsp"></jsp:include> --%>
                                          

                                                <div id ="tree" class="scroll" style="float:right; width:100%; height:900px; overflow:auto;">
                                                 
                                                    <jsp:include page="treeview.jsp"></jsp:include>
                                               <!--  </div> -->
	
                                        </div>
                                        </div>
                                        </div>
                                        <% if(role.equals("DMSAdmin") || role.equals("ECOURT") || role.equals("Judge")  ) {%>
                                       
                                            <div class="col-md-8" id="myDiv">
                                             <link href="${pageContext.request.contextPath}/assets/css/custom.css" rel="stylesheet" />	
                                                <jsp:include page="viewer2.jsp"></jsp:include>
                                            </div>

                                            <%} %>

                                                <% if(role.equals("Private_Secretary") || role.equals("Review_Officer") || role.equals("Download Copy") || role.equals("Stemp Reporter")
                                                		|| role.equals("REGISTRAR") || role.equals("Assistant Review Officer") || role.equals("Bench Secretary") || role.equals("CaueList_Uploader") || role.equals("Stamp_Reporter")   )   {%>
                                                    <div class="col-md-8" id="myDiv">
                                                      <link href="${pageContext.request.contextPath}/assets/css/custom.css" rel="stylesheet" />	
                                                        <jsp:include page="viewer1.jsp"></jsp:include>
                                                    </div>
                                                    <%} %>

                                    </div>
                             <!-- afnan create  -->  
                             <div class="modal fade" id="notificationview" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   						<div class="modal-dialog modal-lg" style="width:70%;">
									<div class="modal-content">
										<div class="modal-header">
					 						 <!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
					 						<h5 class="modal-title" id="myModalLabel"><strong>Objection On Application </strong></h5>
					 					</div>	     
					 				<%@ include file="../casefile/notification.jsp"%>
									</div>
		   						</div>
	  						</div>
	  						
	  							 <div class="modal fade" id="pendingAppview" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   						<div class="modal-dialog modal-lg" style="width:70%;">
									<div class="modal-content">
										<div class="modal-header">
					 						 <!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
					 						<h5 class="modal-title" id="myModalLabel"><strong>Applications  </strong></h5>
					 					</div>	     
					 				<%@ include file="../casefile/pendingApplication.jsp"%>
									</div>
		   						</div>
	  						</div>
	  						
	  						<div class="modal fade" id="connectedCasesModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		   						<div class="modal-dialog modal-lg" style="width:70%;">
									<div class="modal-content">
										<div class="modal-header">
					 						 <!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
					 						<h5 class="modal-title" id="myModalLabel"><strong>Connected Cases</strong></h5>
					 					</div>	     
					 				<%@ include file="../casefile/connectedCaseModal.jsp"%>
									</div>
		   						</div>
	  						</div>
	  						
	  						<div class="modal fade" id="editsubDocument" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  		<div class="modal-dialog modal-lg">
							    	<div class="modal-content">
							      	<div class="modal-header">
							        	<h4 class="modal-title" id="myModalLabel"><strong>Change Application Type</strong></h4>
							      	</div>	     
						  			<%@ include file="../casefile/editSubdocument.jsp"%>
							    	</div>
						  		</div>
							</div>
	  						
							<!-- afnan create  end-->
                                </div>

                            </div>
                            
                            
        

                          

		

                        </div>

                    </div>
                    
                    <link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/select2.min.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/select2.full.min.js"></script>
       
                    <a href="javascript:;" class="btn btn-icon btn-circle btn-success btn-scroll-to-top fade" data-click="scroll-top"><i class="fa fa-angle-up"></i></a>
                    </div>

                    </body>
                    <script type="text/javascript" src="${pageContext.request.contextPath}/js/scripts/controllers/CaseFileViewControllerForCauseList.js?v=13"></script>
                    <script src="${pageContext.request.contextPath}/assets/js/apps.min.js"></script>

                    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Smart-Table-master/dist/smart-table.js"></script>

                    <style>
                        .st-sort-ascent:before {
                            content: '\25B2';
                        }
                        
                        .st-sort-descent:before {
                            content: '\25BC';
                        }
                        /* 
/* 	    /*Suhsant  */
                        
                        .sidenav {
                            height: 100%;
                            width: 100%;
                            position: fixed;
                        }
                        
                        textarea {
                            height: 100%;
                            width: 100%;
                            font-size: 28px;
                            border-style: none;
                            border-color: Transparent;
                        }
                        
                        .ui-widget.sidenav-dialog {
                            font-family: Verdana, Arial, sans-serif;
                            font-size: 1em;
                        }
                        
                        .ui-widget-content.sidenav-dialog {
                            background: #F9F9F9;
                            border: 1px solid #90d93f;
                            color: #222222;
                        }
                        
                        .ui-dialog.sidenav-dialog {
                            left: 0;
                            outline: 0 none;
                            padding: 0 !important;
                            position: absolute;
                            top: 0;
                        }
                        
                        .ui-dialog.sidenav-dialog .ui-dialog-content {
                            background: none repeat scroll 0 0 transparent;
                            border: 0 none;
                            overflow: auto;
                            position: relative;
                            padding: 0 !important;
                            margin: 0;
                        }
                        
                        .ui-dialog.sidenav-dialog .ui-widget-header {
                            background: #b0de78;
                            border: 0;
                            color: #fff;
                            font-weight: normal;
                        }
                        
                        .ui-dialog.sidenav-dialog .ui-dialog-titlebar {
                            padding: 0.1em .5em;
                            position: relative;
                            font-size: 1em;
                            color: #191919 !important;
                        }
                        /*Sushant  */
                        /* ------------------------------ */
                    </style>

                    <script>
                        $(document).ready(function() {
                            App.init();

                        });

                        $(function() {
                            $("#mySidenav").dialog({
                                    autoOpen: false,
                                    height: 200,
                                    width: 350,
                                    resizable: true,
                                    position: {
                                        my: "left top",
                                        at: "right bottom"
                                    },
                                    dialogClass: 'no-close sidenav-dialog'
                                })
                                /* 
                                			.parent().draggable({
                                             containment: '#content'
                                           }) */
                            ;
                            $('#btnPresent').click(function(e){
								if(getCookie("slide")=="1"){
									 $('#myDiv').toggleClass('fullscreenRight'); 
								}
								else{
							    $('#myDiv').toggleClass('fullscreen'); 
							}
							});
                            $("#btn_click").click(function() {
                                $("#mySidenav").dialog("open");
                            });
                            $("#mySidenav").on('click', '#closebtn', function() {
                                setTimeout(function() {
                                    $("#mySidenav").dialog("close");
                                }, 1000);
                            });

                        });
                        
                        $(function() {
                        	document.getElementById("sidebarContent").hidden=true;
                            $("#sidebarToggle").click(function() {
                            	console.log(document.getElementById("sidebarContent").hidden);
                            	if(document.getElementById("sidebarContent").hidden==true){
                            		document.getElementById("sidebarContent").hidden=false;
                            		document.getElementById("sidebar").hidden=true;
                            		
                            		                       	
                                    }
                            	else{
                            		document.getElementById("sidebarContent").hidden=true;
                            		document.getElementById("sidebar").hidden=false;
                            		
                            		
                            	}
                                
                            });

                        });
                    </script>

                    </html>