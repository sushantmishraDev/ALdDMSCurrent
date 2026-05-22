<%@ include file="../content/header2.jsp"%>


    <html>

<head>
   <link rel='stylesheet' href='${pageContext.request.contextPath}/css/globalStyle.css' type='text/css' />

</head>


    <body>
        <div id="content" class="content">
            <div class="container-fluid" ng-controller="CaseFileController" oncontextmenu="return false;">

                <div class="row">
                    <!-- begin col-12 -->
                    <!-- begin panel -->
                    <div class="panel panel-inverse">
                        <div class="panel-heading">
                            <div class="panel-heading-btn">
                                <a href="javascript:;" class="btn btn-xs btn-icon btn-circle btn-default" data-click="panel-expand"><i class="fa fa-expand"></i></a>
                            </div>
                            <h4 class="panel-title">View Case File Details</h4>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table id="data-table" st-table="displayedCollection" st-safe-src="masterdata" class="table table-striped table-bordered nowrap table-hover" width="100%">
                                    <thead>
                                        <tr>
                                        
                                            <td width="25%">
                                                <select class="form-control" ng-model="search.fd_case_type" ng-options="caseType.ct_id as caseType.labelandname for caseType in caseTypes  | orderBy:'ct_label' | filter:{ct_status:1}"">
                                                    <option value="">Select Case Type</option>
                                                </select>
                                            </td>
                                            
                                            <td>
                                                <input type="text" class="form-control" placeholder="Case No" ng-model="search.fd_case_no">
                                            </td>
                                            <td>
                                                <input type="text" class="form-control" placeholder="Case Year" ng-model="search.fd_case_year">
                                            </td>
                                            <td>
                                                <button id="search" type="submit" class="btn btn-primary btn-sm" ng-click="searchCaseFiles()">Search</button>
                                            </td>
                                        </tr>

                                    </thead>
                                </table>
                                
                                <table id="data-table" class="table table-striped table-bordered">
                                    <thead>
                                        <tr>
                                        	<th style="width: 1%;">Sr.No.</th>
                                            <th>Case Type</th>
                                            <th>Case No</th>
                                            <th>Case Year</th>
                                            <th>Exist in Efiling</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr ng-repeat="row in caseFileList" class="odd gradeX">
                                        	<td>{{$index+1}}</td>
                                            <td>{{row.caseType.ct_name}}</td>
                                            <td>{{row.fd_case_no}}</td>
                                            <td>{{row.fd_case_year}}</td>
                                            <td align="center">{{dmsCaseData.status}}</td>
                                            <td>
                                                <% if(role.equals("DMSAdmin") || role.equals("Ecourt_Team")) {%>
                                                    <button class="btn-ui btn-primary-ui" ng-click="viewCaseFile(row.fd_id)">View</button>
                                                    <button class="btn-ui btn-purple-ui" ng-click="viewDetail(row.fd_id)">View Detail</button>
                                                    <button class="btn-ui btn-info-ui" ng-click="downloadFiles(row.fd_id)">Download</button>

                                                    <button class="btn-ui btn-primary-ui" ng-click="setModel(row)" data-toggle="modal" data-target="#updateCaseType">Change Case Type</button>
                                                    <button type="button" class="btn-ui btn-purple-ui" data-toggle="modal" ng-click="setModel(row)" data-target="#uploadDocument">Upload</button>
                                                      <button class="btn-ui btn-info-ui" ng-click="searchreport(row.fd_id)">Office Reports</button>

                                                    <button class="btn-ui btn-primary-ui" ng-click="setModel(row)" data-toggle="modal" data-target="#caseAssignTo">Assign To</button>
                                                    <button class="btn-ui btn-purple-ui" ng-click="setModel(row)" data-toggle="modal"  data-target="#addcaseefiling">AddCaseToEfiling</button>
                                                      <button class="btn-ui btn-info-ui" ng-click="addPartyName(row.fd_id)">Add Party</button>
                                                    <% }%>

                                                        <% if(role.equals("Review_Officer") || role.equals("Assistant Review Officer")) {%>
                                                            <button type="button" class="btn-ui btn-primary-ui" data-toggle="modal" ng-click="setModel(row)" data-target="#uploadDocument">Upload
                                                            </button>
                                                             <button class="btn-ui btn-purple-ui" ng-click="setModel(row)" data-toggle="modal" data-target="#updateCaseType">Change Case Type</button>
                                                               <button class="btn-ui btn-info-ui" ng-click="searchreport(row.fd_id)">Office Reports</button>
                                                            <button class="btn-ui btn-primary-ui" ng-click="viewCaseFile(row.fd_id)">View</button>
                                                            <% }%>
                                                            <% if(role.equals("Stamp_Reporter") || role.equals("REGISTRAR") || role.equals("REGISTRAR (J)") || role.equals("JOINT REGISTRAR") || role.equals("JOINT REGISTRAR (J)")) {%>
                                                            <button type="button" class="btn-ui btn-primary-ui" data-toggle="modal" ng-click="setModel(row)" data-target="#uploadDocument">Upload
                                                            </button>
                                                                    <button class="btn-ui btn-purple-ui" ng-click="viewCaseFile(row.fd_id)">View</button>
                                                                    <% }%>

                                                                <% if(role.equals("PS")) {%>
                                                                    <button class="btn-ui btn-purple-ui" ng-click="viewCaseFile(row.fd_id)">View</button>
                                                                    <% }%>

                                                                        <% if(role.equals("Judge")) {%>
                                                                            <button class="btn-ui btn-purple-ui" ng-click="viewCaseFile(row.fd_id)">View</button>

                                                                            <% }%>

                                            </td>
                                        </tr>
                                        <tr ng-show="showStatus">
									<td colspan="6" style="text-align:center;">No Record found!!</td>
								</tr>
                                    </tbody>
                                </table>
                                <div ng-if="showLoader" style="height: 60px">
							<div id="loader" class="center"></div>
						</div>
                            </div>
                                		<div ng-show ="officeReport.length > 0" class="container-fluid">
                       <div class="col-md-12">
						<table  style="table-layout:fixed;" id="data-table" style ="table-layout: fixed; width: 100%" class="table table-striped table-bordered" >
							<thead >
	
								<tr >
									<th style="width: 10%;">Date</th>
									<!-- <th style="width: 30%;">User</th> -->
									<th style="width: 70%; overflow-x : auto;">Report</th>
									
									<th style="width: 20%;" class="col-md-1">Action</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-show="officeReport.length <= 0" >
									<td colspan="4" style="text-align:center;">No Report Found for Edit</td>
								</tr>
								
								<tr ng-repeat="data in officeReport ">
								
		                            <td >{{data.ord_created | date:"dd/MM/yyyy hh:mm a"}}</td>
		                          <!--    <td>{{data.submittedBy.um_fullname}}</td> -->
										 <td style="width: 50%; overflow-x : auto;" ng-bind-html="data.ord_remark"></td>
									<!-- <td>{{data.ord_remark}}</td> -->	
									
									<td><button  ng-show ="data.cl_flag ==0" type="button" class="btn btn-success btn-sm" data-toggle="modal" ng-click="setOfficeRpt(data)" data-target="#updateOfficeRpt">Edit Report</button>
									
									<p  ng-show ="data.cl_flag == 1" >Case is Listed In Court</p>
									<p  ng-show ="data.cl_flag == 2" >Time Expired</p>
									</td> 
									
 
		                             <!-- <td class="col-md-1"><button id="view" type="submit" class="btn btn-sm btn-success"
							ng-click="Getfile(data)"  data-toggle="modal"> view
						</button></td> -->
								</tr>
							</tbody>
						</table> 	
						</div>
						</div>
						
						                                		<div ng-show ="officeReport.length <= 0" class="container-fluid">
                       <div class="col-md-12">
						<table  style="table-layout:fixed;" id="data-table" style ="table-layout: fixed; width: 100%" class="table table-striped table-bordered" >
							<thead >
	
								<tr >
									<th style="width: 10%;">Date</th>
									<!-- <th style="width: 30%;">User</th> -->
									<th style="width: 70%; overflow-x : auto;">Report</th>
									
									<th style="width: 20%;" class="col-md-1">Action</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-show="officeReport.length <= 0" >
									<td colspan="4" style="text-align:center;">No Report Found for Edit</td>
								</tr>
								
								<tr ng-repeat="data in officeReport ">
								
		                            <td >{{data.ord_created | date:"dd/MM/yyyy hh:mm a"}}</td>
		                          <!--    <td>{{data.submittedBy.um_fullname}}</td> -->
										 <td style="width: 50%; overflow-x : auto;" ng-bind-html="data.ord_remark"></td>
									<!-- <td>{{data.ord_remark}}</td> -->	
									
									<td><button  ng-show ="data.cl_flag ==0" type="button" class="btn btn-success btn-sm" data-toggle="modal" ng-click="setOfficeRpt(data)" data-target="#updateOfficeRpt">Edit Report</button>
									<p  ng-show ="data.cl_flag == 1" >Case is Listed In Court</p>
									<p  ng-show ="data.cl_flag == 2" >Time Expired</p>
									</td> 
									
 
		                             <!-- <td class="col-md-1"><button id="view" type="submit" class="btn btn-sm btn-success"
							ng-click="Getfile(data)"  data-toggle="modal"> view
						</button></td> -->
								</tr>
							</tbody>
						</table> 	
						</div>
						</div>
						
                           <div class="modal fade" id="addcaseefiling" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    		<div class="modal-dialog modal-lg">
                              	 <div class="modal-content">
                            		 <div class="modal-header">
                                	 <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    	<span aria-hidden="true">&times;</span>
                               		 </button>
                               		 <h4 class="modal-title" id="myModalLabel">
										<strong>Add Case Details To Efling</strong>
									</h4>
                            		</div>
                         	   			<%@ include file="../casefile/addCaseEfiling.jsp"%>
                        		 </div>
                   		 		</div>
              		 	    </div>
                            <div class="modal fade" id="uploadDocument" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                            <h4 class="modal-title" id="myModalLabel">
										<strong>Upload Document</strong>
									</h4>
                                        </div>
                                        <%@ include file="../casefile/_upload_form.jsp"%>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="updateOfficeRpt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                            <h4 class="modal-title" id="myModalLabel">
										<strong>Upload Document</strong>
									</h4>
                                        </div>
                                        <%@ include file="../casefile/_update_officeRpt.jsp"%>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="viewFiles" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                            <h4 class="modal-title" id="myModalLabel">
										<strong>Stage History</strong>
									</h4>
                                        </div>
                                        <%@ include file="../casefile/filelist.jsp"%>
                                    </div>
                                </div>
                            </div>
                            <div class="modal fade" id="updateCaseType" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                            <h4 class="modal-title" id="myModalLabel">
										<strong>Change Case Type</strong>
									</h4>
                                        </div>
                                        <%@ include file="../casefile/updateCasetype.jsp"%>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="caseAssignTo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                            <h4 class="modal-title" id="myModalLabel">
										<strong>Case Assign To</strong>
									</h4>
                                        </div>
                                        <%@ include file="../casefile/caseAssignTo.jsp"%>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>

                    <!-- end panel -->

                    <!-- end col-12 -->
                </div>
            </div>
        </div>

        <!-- end row -->
    </body>

    <!-- ================== END PAGE LEVEL JS ================== -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/angularJs/ng-file-upload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/angularJs/ngMask.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/scripts/controllers/CaseFileController.js?v=10"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap/angular-datepicker.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap/ui-bootstrap-tpls.0.11.2.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/apps.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/scripts/controllers/editor.js"></script>

    <script>
        $(document).ready(function() {
            $("#txtEditor").Editor();
            $("#txtEditor1").Editor();
            App.init();

        });
    </script>

    <!-- <link href="editor.css" type="text/css" rel="stylesheet"/> -->

    </html>