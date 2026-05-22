<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dms.model.ObjectMaster"%>
<%@ page import="com.dms.model.User"%>	
<%@ page import="com.dms.model.CourtMaster"%>	
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="EDMSApp" >
<head>
<link rel="icon" href="${pageContext.request.contextPath}/images/favicon.ico"/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/bootstrap/angular-datepicker.css'>

<link rel='stylesheet' href='${pageContext.request.contextPath}/css/editor.css'>
<script type="text/javascript"  src="${pageContext.request.contextPath}/assets/js/clock.js"></script>



<style type ="text/css" media ="print">
	body {visibility :hidden ; display:none;}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Allahabad High Court</title>

 <%@ include file="style.jsp"%>
<%@ include file="script.jsp"%>
<script>
$(document).ready(function(){
	  $('li.active').parent().parent().addClass('active');
});
</script>

<!-- Calendar Plugin  -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/select2.min.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/select2.full.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/pg-calendar/css/pignose.calendar.min.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/pg-calendar/js/pignose.calendar.full.min.js"></script>



<style type="text/css">
		.pignose-calendar {
		width: 90%;
		max-width: 500px;
	}
	
	.pignose-calendar .pignose-calendar-top-prev,
	.pignose-calendar .pignose-calendar-top-next {
		visibility: hidden !important;
	}
	
	.pignose-calendar .pignose-calendar-unit.pignose-calendar-unit-disabled.pignose-calendar-unit-disabled-weekdays a {
		background-color: #000;
		color: #fff !important;
	}
	
	.pignose-calendar .pignose-calendar-unit.pignose-calendar-unit-disabled a {
		background-color: blue;
		color: #fff !important;
	}
	
	
	 .toggle {
      --width: 60px;
      --height: calc(var(--width) / 3);

      position: relative;
      display: inline-block;
      width: var(--width);
      height: var(--height);
      border-radius: var(--height);
      cursor: pointer;
    }

    .toggle input {
      display: none;
    }

    .toggle .slider {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      border-radius: var(--height);
    border: 2px solid #969696; 
      box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3); 
      transition: all 0.4s ease-in-out;
    }

    .toggle .slider::before {
      content: "";
      position: absolute;
      top: 2.5px;
      left: 2px;
      width: calc(var(--height)*0.4);
      height: calc(var(--height)*0.4);
      border-radius: calc(var(--height) / 2);
      border: 3px solid #969696;
      background-color: #dbdbdb;
    box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.3);
      transition: all 0.4s ease-in-out;
    }

    .toggle input:checked+.slider {
      border-color: #2196F3;
    }

    .toggle input:checked+.slider::before {
      border-color: #2196F3;
      background-color: #c6e5ff;
      transform: translateX(calc(var(--width) - var(--height)));
    }

    .toggle .labels {
      position: absolute;
      top: 4px;
      left: 0;
      width: 100%;
      height: 100%;
      color: #4d4d4d;
      font-size: 10px;
      font-family: sans-serif;
      transition: all 0.4s ease-in-out;
    }

    .toggle .labels::after {
      content: attr(data-off);
      position: absolute;
      right: 5px;
      opacity: 1;
      text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.4);
      transition: all 0.4s ease-in-out;
    }

    .toggle .labels::before {
      content: attr(data-on);
      position: absolute;
      left: 5px;
      opacity: 0;
      text-shadow: 1px 1px 2px rgba(255, 255, 255, 0.4);
      transition: all 0.4s ease-in-out;
    }

    .toggle input:checked~.labels::after {
      opacity: 0;
    }

    .toggle input:checked~.labels::before {
      opacity: 1;
    }

</style>


</head>
<body onload="startTime()" style="font-family: 'Segoe UI', 'Helvetica Neue', sans-serif; ">
<% 
User user = null;
if(session.getAttribute("USER")!=null)
	 user = (User)session.getAttribute("USER");

String role=user.getUserroles().get(0).getLk().getLk_longname();
System.out.println("Role name="+role);
String url=request.getRequestURL()+"";
String home="";
String casefile="";
String caveat="";
String applications="";
String upload="";
String scrutinycase="";
String scrutinycaveat="";
String chamberName ="";
String scrutinyapplication="";
CourtMaster cm =user.getCourtMaster();
if(cm != null){
	 chamberName =cm.getCm_name();
}
String uri=(String) request.getAttribute("javax.servlet.forward.request_uri");

String basePath=(String) request.getAttribute("javax.servlet.forward.context_path");



%>
<% 
	List<ObjectMaster> parent_list  = (List<ObjectMaster>)session.getAttribute("ob_list");
	List<ObjectMaster> child_list  = (List<ObjectMaster>)session.getAttribute("ob_list");
%>
<div id="page-container" class="fade page-sidebar-fixed page-header-fixed" style ="padding-top:35px">
		<!-- begin #header -->
		<div id="header" class="header navbar navbar-default navbar-fixed-top" style ="height:35px">
			<!-- begin container-fluid -->
		<!-- 	<div class="container-fluid" > -->
				<!-- begin mobile sidebar expand / collapse button -->
				<div class="navbar-header">
					<a href="" class="navbar-brand" style ="height:35px"><!-- <span class="navbar-logo"></span>Allahabad High Court e-Court  -->
					<span style ="padding-left: 30px; font-style: italic;
    font-size: 20px;" id ="listType"></span>   <span style ="padding-left: 20px; color:red ;font-weight: bold;
    font-size: 25px;" id ="serial"></span>
    
    </a>
    
   
     
						<%
			if (role.equals("ECOURT") || role.equals("DMSAdmin") || role.equals("Ecourt_Previlage")) {
			%>
				<div  id="slidebar" style="padding-top: 10px;padding-left:550px; align:center" >
						<label class="toggle">
   							 <input type="checkbox" id="slide" onclick="slide()">
   							 <span class="slider"></span>
   							 <span class="labels" data-on="LEFT" data-off="RIGHT"></span>
  						</label>
				</div>
			<%
			}
			%>
					
					
				</div>
				
				<!-- end mobile sidebar expand / collapse button -->
				
				<!-- begin header navigation right -->
				<ul class="nav navbar-nav navbar-right">
				<li>
						<div id="clockdate" style ="padding-top: 10px ; display:inline;">
  							<div class="clockdate-wrapper" style ="display:inline;">
  							<div id="date" style ="display:inline;"></div>
    							<div id="clock" style ="padding-top:15px"></div>
   					  			
 							</div>
						</div>
					</li>
				
					<li class="dropdown navbar-user">
						<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
							<%-- <img src="${pageContext.request.contextPath}/assets/img/user-13.jpg" alt="" /> --%> 
							
							
							<%!
							public static String capitalizeWord(String str)
							{  
							    String words[]=str.split("\\s");  
							    String capitalizeWord="";  
							    for(String w:words)
							    {  
							       /*  String first=w.substring(0,1);  
							        String afterfirst=w.substring(1);  
							        capitalizeWord+=first.toUpperCase()+afterfirst.toLowerCase()+" ";   */
							    }
							   // return capitalizeWord.trim();  
							   
							   return str;
							}  
							%>
							
							
							
							
								<% if(role.equals("Judge") || role.equals("Ecourt_Previlage"))
								{ %>
								<span class="hidden-xs"><b><%=""%></b><b><%="Hon'ble Justice\n"+capitalizeWord(user.getUm_fullname()) %><%-- (<%=role%>) --%></b></span> <b class="caret"></b>
								<% }
								
								else  if(role.equals("Stamp_Reporter")) 
								{%>
									<span class="hidden-xs"><b><%=""%></b><b><%=""+capitalizeWord(user.getUm_fullname()) %></b></span> <b class="caret"></b>
								<%}
								
								else  if(role.equals("Chief Justice")) 
								{%>
									<span class="hidden-xs"><b><%=""%></b><b><%="Hon'ble Chief Justice\n"+capitalizeWord(user.getUm_fullname()) %>(<%=role%>)</b></span> <b class="caret"></b>
								<%}
								else  if(cm != null) 
								{%>
									<%-- <span class="hidden-xs"><b><%=""%></b><b><%="Hon'ble  Justice\n"+capitalizeWord(user.getUm_fullname()) %>(<%=chamberName%>)</b></span> <b class="caret"></b>
								 --%>
								<span class="hidden-xs"><b><%=""%></b><b><%="Hon'ble  Justice\n"+capitalizeWord(user.getUm_fullname()) %>(<%=chamberName%>)</b></span> <b class="caret"></b>
							
								 <%}
								
								else  if(role.equals("ECOURT")) 
								{%>
									<img src="${pageContext.request.contextPath}/assets/img/1.jpg" alt="" />
								<%-- <img src="${pageContext.request.contextPath}/assets/img/user-13.jpg" alt="" /> --%> 
								<span class="hidden-xs"><b><%=""%></b><b><%=capitalizeWord(user.getUm_fullname()) %> (<%=role%>)</span> </b><b class="caret"></b>
									<%}
								else{ %>
								<img src="${pageContext.request.contextPath}/assets/img/1.jpg" alt="" />
								<%-- <img src="${pageContext.request.contextPath}/assets/img/user-13.jpg" alt="" /> --%> 
								<span class="hidden-xs"><%=user.getUm_fullname() %></span> <b class="caret"></b>
								<%} %>
																			
								
							<%-- <span class="hidden-xs"><%=user.getUm_fullname() %> (<%=role%>)</span> <b class="caret"></b> --%>
						</a>
						<ul class="dropdown-menu animated fadeInLeft">
							<li><a href="${pageContext.request.contextPath}/dms/logout">Log Out</a></li>
								<li><a href="${pageContext.request.contextPath}/user/edit">Edit Profile</a></li>
						</ul>
					</li>
				</ul>
				<!-- end header navigation right -->
			<!-- </div> -->
			<!-- end container-fluid -->
			
		</div>
		<!-- end #header -->
		
		<!-- begin #sidebar -->
		<div id="sidebar" class="sidebar" style ="font-size: 16px;font-weight: bold;">
			<!-- begin sidebar scrollbar -->
			<div data-scrollbar="true" data-height="100%"  id="demo2">
				<ul class="nav">
					<% 
							 
						 for(int i=0;i<parent_list.size();i++)
						 {
							 ObjectMaster om = parent_list.get(i);
							System.out.println(om.getOm_object_link()+" "+om.getOm_object_stages());
							if(om.getOm_object_stages()==0)
							{
								%> 
								<li class="has-sub" >
						    		 <a><b class="caret pull-right"></b><i class="fa fa-file-o"></i><%=om.getOm_object_name() %>
						    		 </a>
		                            
		                        	 <ul class="sub-menu">
			                        	 <%
									 		for(int j=0;j<child_list.size();j++)
											 {
												
												 ObjectMaster ch_om = child_list.get(j);
												 String active="";
												 String fullPath=basePath+ch_om.getOm_object_link();
												 
												 if(om.getOm_id().equals(ch_om.getOm_parent_id()))
												 {													 
													 if(uri.equals(fullPath)){
														 active="active";														 
													 }
													 else{
														 active="";	 
													 }
													 //add child
													
														if(ch_om.getOm_id()==42L){
															 %>
															 <li  class=<%=active %>><a style ="font-weight: bold;" 
															 href="<%=ch_om.getOm_object_link()%>" target="_blank"> <%=ch_om.getOm_object_name() %> </a></li>
															 <%
														} else{
															 %>
														 
														 <li  class=<%=active %>><a style ="font-weight: bold;" href="${pageContext.request.contextPath}<%=ch_om.getOm_object_link()%>"> <%=ch_om.getOm_object_name() %> </a></li>
														 <%
														}
													 
												 }
											 }
			                        	 %>	 
			                        	 
		                        	 </ul>
		                        	  
		                        </li>
		                       <%
							}
							
							
						 }
					if(role.equals("ECOURT")){
					%>
					<li  ><a style ="font-weight: bold;" href="" data-toggle="modal" data-target="#user_Modal"> Calendar </a></li><%} %>
					<li><a href="javascript:;" class="sidebar-minify-btn" data-click="sidebar-minify" ><i class="fa fa-angle-double-left"></i></a></li>
					</div>
				</ul>
				<!-- end sidebar nav -->
				 <!-- <button id="sidebarToggle" class="toolbarButton" title="Toggle Sidebar" tabindex="11" data-l10n-id="toggle_sidebar">
                                <span data-l10n-id="toggle_sidebar_label">Toggle Sidebar</span>
                            </button> -->
				<!-- <div id="sidebarContent" >
                <div id="thumbnailView" style="display:none">
                </div>
                <div id="outlineView" class="hidden">
                </div>
                <div id="attachmentsView" class="hidden">
                </div>
                
            </div> -->
         
			
			<!-- end sidebar scrollbar -->
		
		
			
				
				</div>
				<div id="sidebarContent" style=" margin-top: 19px; position:fixed;" >
				
               
                <div id="outlineView" class="hidden" width ="105px"></div>
            </div>
	</body>
				
		<!-- end #sidebar -->
		
		<!-- begin #content -->
			 
			
	<!-- ================== END PAGE LEVEL JS ================== -->
	
	
	<script>
	function right() {
		document.getElementById("sidebar").style.width = 0 + "px";
		document.getElementById("content").style.marginLeft = "0px";
		document.getElementById("content").style.marginRight = "220px";
		document.getElementById("sidebarContent").style.right = "0";
		document.getElementById("sidebar").style.left = null;
		document.getElementById("sidebar").style.right = "0";
		document.getElementById("sidebar").style.width = 220 + "px";
		$('#myDiv').each(function () {
		    if (!$(this).text().match(/^\s*$/)) {
		        $(this).insertBefore($(this).prev('#metaDataPanel'));
		    }
		});
	}

	function left() {
		document.getElementById('sidebar').style.width = "0px";
		document.getElementById('sidebar').style.right = null;
		document.getElementById("sidebar").style.left = "0";
		document.getElementById('sidebarContent').style.right = null;
		document.getElementById('content').style.marginLeft = "220px";
		document.getElementById('content').style.marginRight = "0px";
		document.getElementById('sidebar').style.width =220 + "px";
		$('#metaDataPanel').each(function () {
		    if (!$(this).text().match(/^\s*$/)) {
		        $(this).insertBefore($(this).prev('#myDiv'));
		    }
		});
	}
	
	
	function slide(){
		console.log(document.getElementById('slide').checked);
		const lf=document.getElementById('slide').checked==true;
		if(lf==true){
			right()
			setCookie('slide', 1, 365);
		}
		else{
			left()
			setCookie('slide', 2, 365);
		}
	}
	
	window.addEventListener('load', function () {
		// show()
		})
	
	
		
		
		function show(){
		 var x = document.getElementById("slidebar");
		 
		
		 
		 if(document.URL.match(/view/g) || document.URL.match(/viewCauseList/g)){
			  x.style.display = "block";
			  
			  if(getCookie("slide")=="1"){
					right()
					document.getElementById('slide').checked=true;
				}else{
					left()
				}
		 }	 
		 else{
			  x.style.display = "none";
		 } 
	}
	
	
	// Set Cookie Function
	function setCookie(cName, cValue, expDays) {
	        let date = new Date();
	        date.setTime(date.getTime() + (expDays * 24 * 60 * 60 * 1000));
	        const expires = "expires=" + date.toUTCString();
	        document.cookie = cName + "=" + cValue + "; " + expires + "; path=/";
	}
	// Get Cookie Function
	function getCookie(cName) {
		      const name = cName + "=";
		      const cDecoded = decodeURIComponent(document.cookie);
		      const cArr = cDecoded .split('; ');
		      var res;
		      cArr.forEach(val => {
		          if (val.indexOf(name) === 0) res = val.substring(name.length);
		      })
		      return res;
		}
	
	
</script>
	
	
	
</html>

