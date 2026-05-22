<%@ include file="../content/header2.jsp"%>
<html>

<style>

/* ===== Base Button (applies to all) ===== */
.btn-ui {
	border: none;
	border-radius: 20px;
	padding: 6px 14px;
	font-size: 13px;
	font-weight: 500;
	display: inline-flex;
	align-items: center;
	gap: 6px;
	cursor: pointer;
	transition: all 0.25s ease;
	color: #fff;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
}

/* Hover Effect */
.btn-ui:hover {
	transform: translateY(-2px);
	box-shadow: 0 5px 12px rgba(0, 0, 0, 0.25);
	color: #fff;
}

/* Click Effect */
.btn-ui:active {
	transform: scale(0.96);
}

/* Icon inside button */
.btn-ui i {
	font-size: 13px;
}

/* ===== Color Variants ===== */
.btn-primary-ui {
	background: linear-gradient(45deg, #007bff, #00c6ff);
}
/*  add purple color*/
.btn-purple-ui {
    background: linear-gradient(45deg, #6a11cb, #2575fc);
}

/* add   */

.btn-success-ui {
	background: linear-gradient(45deg, #28a745, #5cd65c);
}

.btn-danger-ui {
	background: linear-gradient(45deg, #dc3545, #ff6b6b);
}

.btn-warning-ui {
	background: linear-gradient(45deg, #ff9800, #ffc107);
}

.btn-info-ui {
	background: linear-gradient(45deg, #17a2b8, #5bc0de);
}

.btn-secondary-ui {
	background: linear-gradient(45deg, #6c757d, #adb5bd);
}

/* ===== Disabled State ===== */
.btn-ui:disabled {
	opacity: 0.6;
	cursor: not-allowed;
	box-shadow: none;
}

/* ===== Table spacing (optional but useful) ===== */
td .btn-ui {
	margin: 2px;
}
</style>



<body>
	<div id="content" class="content">
		<div class="container-fluid" ng-controller="BenchController"
			oncontextmenu="return false;">

			<div class="row">
				<!-- begin col-12 -->
				<!-- begin panel -->
				<div class="panel panel-inverse">
					<div class="panel-heading">
						<div class="panel-heading-btn">
							<a href="javascript:;"
								class="btn btn-xs btn-icon btn-circle btn-default"
								data-click="panel-expand"><i class="fa fa-expand"></i></a>
						</div>
						<h4 class="panel-title">Manage Benches</h4>
					</div>
					<div class="panel-body">

						<div class="row">
							<div class="col-md-12 ">
							
							<button class="btn-ui btn-primary-ui" ng-disabled="showLoader" ng-click="moveApplication()" >Move Applications</button>

								<button type="button"
									class="btn btn-ui btn-primary-ui pull-right"
									ng-click="resetModel()" data-toggle="modal"
									data-target="#user_Modal">

									<i class="fa fa-plus"></i> <span>Add New Court</span>
								</button>
							</div>
						</div>
						<div class="table-responsive">

							<table id="data-table" class="table table-striped table-bordered">
								<thead>
									<tr>

										<th>Court Name</th>
										<th>Bench Id</th>
										<th>Action</th>
										<th>Service</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="row in courtList" ng-include="getData(row)"
										class="odd gradeX">
										<div ng-if="showLoader" style="height: 60px">
											<div id="loader" class="center"></div>
										</div>

										<script type="text/ng-template" id="view">
                                        	
                                            <td>{{row.cm_name}}</td>
 <td>{{row.cm_bench_id}}</td>
<td>
 <button class="btn-ui btn-info-ui " ng-click="editBench(row)">Edit Bench</button>
</td>
 <td>                                                
 <!--  <button class="btn-ui btn-primary-ui" ng-disabled="showLoader" ng-click="supplimentryToday(row)" >Supplimentry</button> -->
<a class="btn-ui btn-purple-ui" ng-click="setMasterdataSupp(row)" data-toggle="modal" data-target="#next_Modal" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Supplimentry</a>
<!-- <button class="btn btn-success btn-sm" ng-disabled="showLoader" ng-click="transferToday(row)" >Transfer</button> -->
<a class="btn-ui btn-warning-ui" ng-click="setMasterdataTrans(row)" data-toggle="modal" data-target="#next_Modal" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Transfer</a>

<button class="btn-ui btn-primary-ui" ng-disabled="showLoader" ng-click="correctionIaToday(row)" >Correction&Mention</button>



<a class="btn-ui btn-secondary-ui" ng-click="setMasterdata(row)" data-toggle="modal" data-target="#next_Modal" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Next Day</a>



                                            </td>

</script>
										<!--  <td>{{row.cm_bench_id}}</td> -->
										<script type="text/ng-template" id="edit">

                                            <td>{{row.cm_name}}</td>
                                             <td><input type = "text" ng-model = "row.cm_bench_id"></td>
 <td><button class="btn btn-success btn-sm" ng-click="updateBench($index,row)">Update Bench</button>
 <button class="btn btn-success btn-sm" ng-click="reset()">Reset</button></td>
  
</script>


									</tr>
									<tr ng-show="caseFileList.length==0">
										<td colspan="8">
											<div class="alert alert-danger">No Records Found</div> <%--  <% if(role.equals("DMSAdmin")) {%>
                                                <button class="btn btn-success btn-sm" ng-click="setModel(row)" data-toggle="modal"  data-target="#addcaseefiling">AddCaseToEfiling</button>
                                                <% }%> --%>

										</td>
									</tr>
								</tbody>
							</table>

							<div class="col-md-12 ">
								<% if(user.getUsername().equals("11188") || user.getUsername().equals("team1"))  {%>
								<button class="btn-ui btn-purple-ui" ng-disabled="showLoader"
									ng-click="supplimentryAll()">Supplimetry All</button>

								<% }%>
							</div>
						</div>
						<div class="modal fade" id="addcaseefiling" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
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

						<div class="modal fade" id="next_Modal" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">
											<span><strong> Next Day Causelist</strong></span>
									</div>
									<%@ include file="../bench/_cause_modal.jsp"%>
								</div>
							</div>
						</div>

						<div class="modal fade" id="user_Modal" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">
											<span ng-if="!masterentity.um_id"><strong>
													Add New Bench</strong></span> <span ng-if="masterentity.um_id"><Strong>Update
													User</Strong></span>
										</h4>
									</div>
									<%@ include file="../bench/master_bench.jsp"%>
								</div>
							</div>
						</div>
						<div class="modal fade" id="viewFiles" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
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
						<div class="modal fade" id="updateCaseType" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">
											<strong>Update Case Type</strong>
										</h4>
									</div>
									<%@ include file="../casefile/updateCasetype.jsp"%>
								</div>
							</div>
						</div>

						<div class="modal fade" id="caseAssignTo" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
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

	<link type="text/css" rel="stylesheet"
		href="${pageContext.request.contextPath}/css/select2.min.css">
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/angularJs/select2.full.min.js"></script>
</body>

<!-- ================== END PAGE LEVEL JS ================== -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/ng-file-upload.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/ngMask.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/scripts/controllers/BenchController.js?v=7"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/angular-datepicker.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/ui-bootstrap-tpls.0.11.2.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/apps.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/bootbox.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/scripts/controllers/editor.js"></script>



<script>
        $(document).ready(function() {
            App.init();

        });
    </script>

<!-- <link href="editor.css" type="text/css" rel="stylesheet"/> -->

</html>