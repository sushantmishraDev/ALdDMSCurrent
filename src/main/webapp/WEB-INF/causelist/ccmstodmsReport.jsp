<%@ include file="../content/header2.jsp"%>
<div id="content" class="content" ng-controller="causeListController">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-inverse">
				<div class="panel-heading">
					<h4 class="panel-title">Ccms To Dms Report</h4>
				</div>
				<div class="panel-body">
					<!-- 				<div> -->
					<table id="data-table"
						class="table table-striped table-bordered table-hover"
						width="100%">
						<thead>
							<tr>
								<td width="20%"><select name="value" style="padding: 5px;width: 55%" 
									ng-model="model.fd_file_source">
										<option value="">Select File Type</option>
										<option value="F">Fresh File</option>
										<option value="P">Dispose File</option>
								</select></td>


								<td width="25%">
									<div class="input-group date">
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
										<button type="submit" class="btn btn-sm btn-success"
											ng-click="getCcmstodmsFileReport(model)">Search</button>

									</div>
								</td>
							</tr>

						</thead>
					</table>
					
					<!--</div> -->


					<div id="report_table" class="row">
						<button id="btnPrint" class="btn btn-success">Print</button>

						<table st-table="ccmstodmsdata" st-safe-src="masterdata"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>

									<th style="text-align: center">Court No</th>
									<th style="text-align: center">Serial No</th>
									<th style="text-align: center">Ccms Name</th>
									<th style="text-align: center">Dms Name</th>
									<th style="text-align: center">Case Type</th>
									<th style="text-align: center">Case No</th>
									<th style="text-align: center">Case Year</th>
									<th style="text-align: center">Action</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="data in ccmstodmsdata">
									<td style="color: rgb(0, 0, 255);">{{data[1]}}</td>
									<td>{{data[2]}}</td>
									<td>{{data[3]}}</td>

									<td>{{data[4]}}</td>

									<td>{{data[5]}}</td>

									<td>{{data[6]}}</td>

									<td>{{data[7]}}</td>
									
									<td><button class="btn btn-success btn-sm" ng-click="viewCaseFile2(data[0])">View</button></td>

								</tr>
								<tr ng-if="ccmstodmsdata.length==0 && search">
									<td colspan="8" class="alert alert-danger">No Records
										Found</td>
								</tr>
							</tbody>
						</table>
					</div>

					<!-- <div class="row">
						<table st-table="ccmstodmsdata" st-safe-src="masterdata"
							class="table table-striped table-bordered table-hover">
						<thead>
						
						</thead>
						<tbody>
						<tr>
						<td>
						Total Count 
						</td>
					<td>
						{{totalCount}}
						</td>
						
						
						</tr>
						
						</tbody>
						</table>
					</div> -->



				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/ng-file-upload.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/ngMask.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/scripts/controllers/CauseListController.js?v=4"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/ui-bootstrap-tpls.0.11.2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/angular-tree-control.js"></script>
<link rel='stylesheet'
	href='${pageContext.request.contextPath}/css/tree-control/tree-control.css'>
<link rel='stylesheet'
	href='${pageContext.request.contextPath}/css/tree-control/tree-control-attribute.css'>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Smart-Table-master/dist/smart-table.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/apps.min.js"></script>
<script>
	$(document).ready(function() {
		App.init();

	});
</script>
</body>
</html>