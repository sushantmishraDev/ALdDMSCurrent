<%@ include file="../content/header2.jsp"%>
<div id="content" class="content" ng-controller="causeListController">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-inverse">
		       <div class="panel-heading">
		          <h4 class="panel-title">Manage Cause List</h4>
		       </div>
				<div class="panel-body">
<!-- 				<div> -->
					<table id="data-table" class="table table-striped table-bordered table-hover" width="100%">
					<thead>
						<tr>			
						<%
	if (!role.equals("Judge")) {
%>			
							<td width="25%" >
								<div class="input-group" >
									<select  ng-options="c.clt_id as (c.clt_description+' ('+c.clt_name+')') for c in causeListTypes | orderBy:'clt_id' "
										class="form-control" ng-model="model.cl_list_type_mid" select2=""><option value="">Select List Type</option></select>
								</div>	
							</td>
							<td width="25%">
								<div class="input-group" >
								<select ng-options="c.cm_id as c.cm_name for c in courtList | orderBy:'cm_value'"
									class="form-control" ng-model="model.cl_court_no" select2=""><option value="">Select Court</option></select>
								</div>
							</td>
							
											<%
	}
%>			
	
							
							<td width="25%">
								<div class="input-group date" >
								<input type="text" class="form-control"
									datepicker-popup="{{format}}" ng-model="model.cl_dol"
									is-open="opened1" datepicker-options="dateOptions"
									ng-disabled="true" /> <span class="input-group-addon"
									ng-click="open($event,'opened1')"><i
									class="glyphicon glyphicon-calendar"></i></span>
								</div>
							</td>
							
							<td width="25%">
								<div class='input-group'>
									<button type="submit" class="btn btn-sm btn-success" ng-click="getList(model)" ng-disabled="!model.cl_court_no && !model.cl_list_type_mid">Search</button>
									<button type="submit" class="btn btn-sm btn-success" ng-click="downloadList(model)">Download</button>
								</div>
							</td>
							
						</tr>
					</thead>
					</table>
<!-- 					</div> -->
				
					<div class="row">
									<%
	if (!role.equals("Judge") && !role.equals("Ecourt_Team")) {
%>			
	
						<div class="col-xs-offset-7">
							<button type="button" class="btn btn-sm btn-success" ng-click="refreshModel()" data-toggle="modal" data-target="#causeListCreate">
								Upload CauseList
							</button>
							<a href="${pageContext.request.contextPath}/causelist/addcase_tocauslist">
								<button type="button" class="btn btn-sm btn-success" >Add CaseToCauseList</button>
								</a>
								<button type="button" class="btn btn-sm btn-success" data-toggle="modal" data-target="#addManualCase">
								Add Manual Case
							</button>
								<!-- below link button  not used properly -->
							 <!-- <button type="button" class="btn btn-sm btn-success" ng-click="getCaseTypes()" data-toggle="modal" data-target="#caseAdd">
								Add Case
							</button>  -->
							
							
							<button type="button" class="btn btn-sm btn-success" data-toggle="modal" data-target="#causeListUpdate">
								Update Court
							</button>
							<button type="button" class="btn btn-sm btn-success" ng-click="deleteall()">
								Delete All
							</button>
						</div>
										<%
	}
%>			
	
					</div>
					<%
	if (!role.equals("Judge")) {
%>			
					<!-- <div class="tw-toggle"> -->
  <input type="radio" name="toggle" value="false" ng-model="filterType">
  <label class="toggle toggle-yes">efile</label>
  <input checked type="radio" name="toggle" value="-1" ng-model="filterType">
  <label class="toggle toggle-yes">all</label>
  <input type="radio" name="toggle" value="true" ng-model="filterType">
  <label class="toggle toggle-yes">Digitized</label>
  <span></span>  
<!-- </div> -->
<%
	}
%>			
					<div class="row" >
					
					<font size="2" face="Arial, Helvetica, sans-serif" color="#666362" >
						<table st-table="displayedCollection" st-safe-src="masterdata"  class="table table-bordered" >
						<thead >
						<tr>
						<th>Sr. No.</th>
						<th>Cause List Type</th>
						<th style="text-align:center">Case Detail</th>
						<th style="text-align:center">Party Name</th>
						<th style="text-align:center">Petitioner's Counsel</th>
						<th style="text-align:center">Respondent's Counsel</th>
						<th style="text-align:center">Court No.</th>
						<th style="text-align:center">Case Status</th>
						
						 	<%
	if (!role.equals("Judge") && !role.equals("Ecourt_Team")) {
%>		
<th>Select All
							<input id="{{data.cl_id}}" type="checkbox" value="{{data.cl_id}}" name="cl_id" ng-click="checkAll()" ng-model="selectedAll" />
						</th>
						<th>Action</th>
						 	<%
	}
%>		
						</tr>
						</thead>
						  <div ng-if="showLoader" style="height: 60px">
							<div id="loader" class="center"></div>
						</div>
						<tbody font color="black">
						<tr ng-repeat-start="data in displayedCollection | filter: filterExpression" >
						 <tr ng-show="displayedCollection[$index-1].cl_case_stage !==data.cl_case_stage" >    
                        <td ng-show="displayedCollection[$index-1].cl_case_stage !==data.cl_case_stage" colspan="9" style="text-align: center;text-color:brown">
                        <span><b><u>{{data.cl_case_stage}}</u></b></span></td></tr>
                          <tr ng-show="displayedCollection[$index-1].cl_bunch_name !==data.cl_bunch_name" >    
                        <td ng-show="displayedCollection[$index-1].cl_bunch_name !==data.cl_bunch_name" colspan="6" style="text-align: left;text-color:brown">
                        <span><b><u>{{data.cl_bunch_name}}</u></b></span></td></tr>
                        <tr ng-style="data.caseChecked==true 
						&& data.appNew==true ? personColour1 : (data.caseChecked ==true) ? personColour :''" bgcolor= "#E1EBEE"
						 style="font-size: 12 px; important;">
						<td><span ng-show="displayedCollection[$index-1].cl_serial_no==data.cl_serial_no && displayedCollection[$index-1].cl_court_no == data.cl_court_no"><b>with</b></span>
							<span ng-show="displayedCollection[$index-1].cl_serial_no!=data.cl_serial_no && displayedCollection[$index-1].cl_court_no != data.cl_court_no">{{data.cl_serial_no}}</span>
								<span ng-show="displayedCollection[$index-1].cl_serial_no==data.cl_serial_no && displayedCollection[$index-1].cl_court_no != data.cl_court_no">{{data.cl_serial_no}}</span>
								<span ng-show="displayedCollection[$index-1].cl_serial_no!=data.cl_serial_no && displayedCollection[$index-1].cl_court_no == data.cl_court_no">{{data.cl_serial_no}}</span>
						<br/>
						 <span ng-show="displayedCollection[$index-1].cl_serial_no!=data.cl_serial_no">{{data.cl_short_order}}</span>
						</td>
						<td>{{data.clType.clt_description}}</td>
					<!-- <td ng-show ='data.cl_fd_mid' style="text-align:center;cursor: pointer;text-decoration: underline;">
						<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2" ng-click="viewApplication(data.cl_ano,data.cl_ayr,data.cl_fd_mid)">Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						<span ng-if="data.cl_list_type_mid !=1 && data.cl_list_type_mid !=2" ng-click="viewCaseFile(data.cl_fd_mid)">{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						</td>
						<td  ng-show ='!data.cl_fd_mid' style="text-align:center;cursor: pointer;">
						<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2" >Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						<span ng-if="data.cl_list_type_mid !=1 && data.cl_list_type_mid !=2">{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						</br>
						<span class ="text-danger">Not in Dms</span>
						</td> -->
							<td ng-show ='data.cl_fd_mid  && data.petAvailable==false && data.appAvailable==false'  style="text-align:center;cursor: pointer;text-decoration: underline;">
					<!-- 	<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2" ng-click="viewApplication(data.cl_ano,data.cl_ayr,data.cl_fd_mid)">Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span> -->
						<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2 || data.cl_list_type_mid==28" ng-click="viewApplication1(data.cl_ano,data.cl_ayr,data.cl_fd_mid,$index,displayedCollection)">Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						
						<!-- <span ng-if="data.cl_list_type_mid !=1 && data.cl_list_type_mid !=2 && data.cl_list_type_mid!=28" ng-click="viewCaseFile(data)">{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						 -->
						 <span ng-if="data.cl_list_type_mid !=1 && data.cl_list_type_mid !=2 && data.cl_list_type_mid!=28" ng-click="viewCaseFile1(data.cl_fd_mid,$index,displayedCollection,data)">{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						<br/>{{data.cl_district_name}}<br/><span ng-show ="data.fileSource==='F'" class ="text-info">Digitized</span>
						<span ng-show ="data.fileSource==='O'" class ="text-info">E-filed</span>
						 </td>
						<td  ng-show ='!data.cl_fd_mid && data.petAvailable==false ' style="text-align:center;cursor: pointer;">
						<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2 || data.cl_list_type_mid==28" >Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						<span ng-if="data.cl_list_type_mid !=1 && data.cl_list_type_mid !=2 && data.cl_list_type_mid!=28">{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						</br>{{data.cl_district_name}}<br/>
						<span class ="text-danger">Case File not in DMS</span>
						</td>
						
							
						
						<td  ng-show ='data.cl_fd_mid && data.petAvailable==true' style="text-align:center;cursor: pointer;">
						<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2 || data.cl_list_type_mid==28" >Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						<span ng-if="data.cl_list_type_mid !=1 && data.cl_list_type_mid !=2 && data.cl_list_type_mid!=28">{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						</br>{{data.cl_district_name}}<br/>
						<span class ="text-danger">Petition Not Available</span>
						</td>
						<td  ng-show ='data.cl_fd_mid && data.appAvailable==true' style="text-align:center;cursor: pointer;text-decoration: underline;">
						<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2 || data.cl_list_type_mid==28  || data.cl_list_type_mid==20 || data.cl_list_type_mid==24 || data.cl_list_type_mid==21"  ng-click="viewApplication1(data.cl_ano,data.cl_ayr,data.cl_fd_mid,$index,displayedCollection)">Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						<span ng-if="data.cl_list_type_mid !=1 && data.cl_list_type_mid !=2 && data.cl_list_type_mid!=28 && data.cl_list_type_mid!=20 && data.cl_list_type_mid!=24 && data.cl_list_type_mid!=21" ng-click="viewCaseFile1(data)">{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						</br>{{data.cl_district_name}}<br/>
						<span class ="text-danger">Application Not E_Filed</span>
						</td>
						<td style="text-align:center">{{data.cl_first_petitioner}} <br/> vs <br/> {{data.cl_first_respondent}} 
						<%
	if (role.equals("Ecourt_Team")) {
%>			
<span ng-show='data.cl_ecourt_status==false && data.caseChecked ==false' style="color:purple;"><b>Party Mismatch</b></span>
<%
	}
%>			
						
						</td>
						<td style="text-align:center">{{data.cl_petitioner_council}}</td>
						<td style="text-align:center">{{data.cl_respondent_council}}</td>
						<td style="text-align:center">{{data.courtMaster.cm_name}}</td>
						<td style="text-align:center" ng-show="data.cl_ccms_id"><a href="http://192.168.0.97/status/view-case-detail/{{data.cl_ccms_id}}" target=”_blank”>Status</a></td>
						
                         	<%
	if (!role.equals("Judge") && !role.equals("Ecourt_Team")) {
%>		
                        <td>
                        	<input type="checkbox" name="checked" id="checked" value={{data.cl_id}} ng-model="data.checked" />
                         </td>
						<td>
							<button type="button" class="btn btn-sm btn-success" ng-click="deletecase(data.cl_id)">Delete</button>
							
						</td>
							<%
	}
%>		
						</tr>
								<tr  ng-repeat-end ng-show="displayedCollection[$index-1].cl_serial_no!=data.cl_serial_no && (
                        data.cl_notice_no !==null || data.cl_lcr_no !==null || data.cl_crime_no !==null || data.cl_crime_ps !==null || data.cl_crime_dist !==null)">    
                        <td colspan="9" ng-show="data.cl_list_type_mid!==1">
                         <span class="font-italic" ng-show="data.cl_list_type_mid!==1 && data.cl_notice_no!==null" >Notice no: <p  style="color :purple;">{{data.cl_notice_no}}</p></span>
                        <span class="font-italic" style="color :purple;" ng-show="data.cl_list_type_mid!==1 && data.cl_lcr_no !== null">TC No.: {{data.cl_lcase_name}}/{{data.cl_lcr_no}}/{{data.cl_lcr_year}}</span><br/>                        
                       
                        <span class="font-italic" ng-show="data.cl_list_type_mid!==1 && data.cl_crime_no !==null"><b>Crime No.</b>- {{data.cl_crime_no}}/{{data.cl_crime_year}},</span>
                         <span class="font-italic" ng-show="data.cl_list_type_mid!==1 && data.cl_crime_ps !==null"><b>Police St.</b>- {{data.cl_crime_ps}},</span>
                         <span class="font-italic" ng-show="data.cl_list_type_mid!==1 && data.cl_crime_dist !=null"> <b>District</b>- {{data.cl_crime_dist}},</span></b><br/>
                         
                          
                           <div ng-show="data.sameLcrDetails !==null">                       
                         -Details of Cases filed earlier with same Trial Court Case No.,Case Type & District (Subject to further verification by Section from original Records):<br/>
                          .............................................................................
                            <div ng-repeat="sld in data.sameLcrDetails">                      
                          <span class="font-italic">
                          <span ng-show ="sld.sld_fd_mid"  style="text-align:center;cursor: pointer;text-decoration: underline;color:red;" 
                          ng-click="viewCaseFileExtra(sld.sld_fd_mid)">
                          {{sld.sld_display}}</span>
                          <span ng-show ="!sld.sld_fd_mid"  >
                          {{sld.sld_display}}</span>, Title- {{sld.sld_party}}  ,Status-{{sld.sld_status}}({{sld.sld_disp_date}})
                          </span><br/>
                          </div ></div> <br>
                          
                          
                             <div ng-show="data.sameCrimDetails !=null">
                          -Details of Cases filed earlier with same Police Station,Crime No. & District<br/>
                          (Subject to further verification by Section from original Records):<br/>
                          .............................................................................
                            <div ng-repeat="scd in data.sameCrimDetails">                      
                          <span class="font-italic">
                          <span ng-show ="scd.scd_fd_mid"  style="text-align:center;cursor: pointer;text-decoration: underline;color:red;" 
                          ng-click="viewCaseFileExtra(scd.scd_fd_mid)">
                          {{scd.scd_display}}</span>
                          <span ng-show ="!scd.scd_fd_mid"  >
                          {{scd.scd_display}}</span>, Title- {{scd.scd_pet_name}} vs {{scd.scd_res_name}} ,Status-{{scd.scd_case_status}},  Police St.-{{scd.scd_ps_name}}
                          </span><br/>
                          </div ></div>
                         
                        </td><td colspan="5" ng-show="data.cl_list_type_mid==1">
                         <span class="font-italic" ng-show="data.cl_app_last_date.trim()!==''"> Last Listing : {{data.cl_app_last_date}},</span>
                          <span class="font-italic" ng-show="data.cl_next_list.trim()!==''"> Next Date In System : {{data.cl_next_list}},</span>
                          <span class="font-italic" ng-show="data.cl_applied_by.trim()!==''"> Applied by : {{data.cl_applied_by}},</span></b><br/></td>
                      
                        </tr>
						<tr ng-show="displayedCollection.length==0  && !search" >
							<td colspan="8" class="alert alert-danger">No Records Found</td>
						</tr>
						</tbody>
						</table>
						</font>
					</div>
						<%
	if (!role.equals("Judge")) {
%>			
					<div class="modal fade" id="causeListCreate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog modal-lg">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						        <h4 class="modal-title" id="myModalLabel"><strong> Upload Cause List </strong></h4>
						      </div>	     
					  			<%@ include file="../causelist/_master_form.jsp"%>
						    </div>
						  </div>
					</div>
					<div class="modal fade" id="causeListUpdate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog modal-lg">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						        <h4 class="modal-title" id="myModalLabel"><strong>Update Cause List </strong></h4>
						      </div>	     
					  			<%@ include file="../causelist/_update_causelist.jsp"%>
						    </div>
						  </div>
					</div>
					<div class="modal fade" id="addManualCase" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog modal-lg">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						        <h4 class="modal-title" id="myModalLabel"><strong>Add Manual Case </strong></h4>
						      </div>	     
					  			<%@ include file="../causelist/add_manual_case.jsp"%>
						    </div>
						  </div>
					</div>
					<div class="modal fade" id="caseAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog modal-lg">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						        <h4 class="modal-title" id="myModalLabel"><strong>Add Cause List </strong></h4>
						      </div>	     
					  			<%@ include file="../causelist/_add_case.jsp"%>
						    </div>
						  </div>
					</div>
						<%
	}
%>			
				</div>
			</div>
		</div>
	</div>
</div>
</div> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/angularJs/ng-file-upload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/angularJs/ngMask.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/scripts/controllers/CauseListController.js?v=5"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap/ui-bootstrap-tpls.0.11.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/angularJs/angular-tree-control.js"></script>
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/tree-control/tree-control.css'>
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/tree-control/tree-control-attribute.css'>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Smart-Table-master/dist/smart-table.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/apps.min.js"></script>

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/select2.min.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/select2.full.min.js"></script>

	<script>
		$(document).ready(function() {
			App.init();
			
		});
	</script>
	
	
	
	<style>



</style>
</body>
</html>