
           <%@ include file="../content/header2.jsp"%>

<style>
.custom-panel {
	border-radius: 12px;
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
	border: none;
	overflow: hidden;
}

/* Header */
.panel-heading {
	background: linear-gradient(135deg, #1f4037, #99f2c8);
	color: #fff;
	padding: 15px 20px;
}

.panel-title {
	font-weight: 600;
	font-size: 18px;
}

/* Filter section */
.filter-box {
	background: #f8f9fb;
	padding: 15px;
	border-bottom: 1px solid #eee;
}

/* Inputs */
.form-control {
	border-radius: 6px;
	box-shadow: none;
}

/* Button */
.btn-primary {
	background: #4ca1af;
	border: none;
	border-radius: 20px;
	padding: 6px 18px;
	transition: 0.3s;
}

.btn-primary:hover {
	background: #357f8a;
}

/* Table */
.custom-table {
	border-radius: 8px;
	overflow: hidden;
}

.custom-table th {
	background: #f1f3f6;
	font-weight: 600;
	text-align: center;
}

.custom-table td {
	vertical-align: middle;
	text-align: center;
}

/* Zebra rows */
.custom-table tbody tr:nth-child(even) {
	background: #fafafa;
}

/* Hover effect */
.custom-table tbody tr:hover {
	background: #eef7ff;
}

/* Badge */
.badge-user {
	background: #6c5ce7;
	color: #fff;
	padding: 5px 10px;
	border-radius: 12px;
	font-size: 12px;
}

/* Empty state */
.no-data {
	padding: 20px;
	color: #999;
	font-size: 14px;
}

.panel-headings {
	background: linear-gradient(135deg, #a18cd1, #fbc2eb);
	color: #ffffff;
	padding: 16px 20px;
	display: flex;
	align-items: center;
	justify-content: space-between;
	border-radius: 12px 12px 0 0;
	box-shadow: 0 3px 10px rgba(161, 140, 209, 0.3);
	position: relative;
	overflow: hidden;
}
</style>



<div id="content" class="content">
	<div class="container" style="background-color: white;"
		ng-controller="caseFileReportController" ng-init="getCaseTypes();">


		<div class="row justify-content-center">
			<div class="col-md-11 mt-4">

				<div class="">

					<!-- Header -->
					<div
						class="panel-headings d-flex justify-content-between align-items-center">
						<h4 class="panel-title">
							<i class="fa fa-file-text-o"></i> Move PDMS To DMS Report
						</h4>


					</div>

					<!-- Filters / Controls -->
					<!-- Filters / Controls -->
					<div class="panel-body filter-box">
						<div class="row">
							<div class="col-md-3">
								<label><b>From Date</b></label> <input type="date"
									class="form-control" ng-model="startDate">
							</div>

							<div class="col-md-3">
								<label><b>To Date</b></label> <input type="date"
									class="form-control" ng-model="endDate">
							</div>


							<div class="col-md-2" style="margin-top: 25px;">
								<button class="btn btn-primary" ng-click="getPDMSToDMSReport()">
									<i class="fa fa-search"></i> Search
								</button>

							</div>
						</div>
					</div>

					<!-- Table -->
					<div class="panel-body table-responsive">

						<div class="panel-body table-responsive">

							<table class="table custom-table">
								<thead>
									<tr>
										<th>#</th>
										<th>Case No</th>
										<th>Year</th>
										<th>Case Type</th>
										<th>Barcode</th>
										<th>Created By</th>
										<th>Move Date</th>
										<th>Status</th>
									</tr>
								</thead>

								<tbody>
									<tr ng-repeat="r in requests track by $index">
										<td>{{$index + 1}}</td>
										<td><b>{{r.drCaseNo}}</b></td>
										<td>{{r.drCaseYear}}</td>
										<td>{{r.drCaseType}}</td>
										<td>{{r.barcode}}</td>

										<td><span class="badge-user"> {{r.createdBy &&
												r.createdBy.um_fullname}} </span></td>

										<td>{{r.moveDate | date:'dd MMM yyyy, hh:mm a'}}</td>

										<td><span class="label"
											ng-class="{'label-success': r.reqStatus == 't', 'label-danger': r.reqStatus != 't'}">
												{{r.reqStatus == 't' ? 'Active' : 'Inactive'}} </span></td>
									</tr>
									<tr ng-if="!requests || requests.length == 0">
										<td colspan="7" class="text-center no-data"><i
											class="fa fa-info-circle"></i> No records found</td>
									</tr>
								</tbody>
							</table>

						</div>

					</div>

				</div>
			</div>
		</div>
	</div>
</div>

<!-- </div> -->
<!-- end row -->
</body>


<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/scripts/controllers/caseFileReportController.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/angular-datepicker.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/ui-bootstrap-tpls.0.11.2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/dirPagination.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/chartModule/Chart.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Smart-Table-master/dist/smart-table.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/apps.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.js"></script>
<script>
	$(document).ready(function() {
		App.init();
	});

	document.getElementById("btnPrint").onclick = function() {

		var pdf = new jsPDF('p', 'pt', 'a4');

		return html2canvas(
				$('#target'),
				{
					background : "#ffffff",
					onrendered : function(canvas) {

						html2canvas(
								$('#target1'),
								{
									background : "#ffffff",
									onrendered : function(canvas2) {

										html2canvas(
												$('#target3'),
												{
													background : "#ffffff",
													onrendered : function(
															canvas3) {

														var myImage = canvas
																.toDataURL("image/jpeg,1.0");

														// Adjust width and height
														var imgWidth = (canvas.width * 60) / 240;
														var imgHeight = (canvas.height * 70) / 240;
														/*  var imgWidth =doc.internal.pageSize.getWidth();;
														  var imgHeight =doc.internal.pageSize.getHeight();*/
														// jspdf changes
														pdf.addImage(myImage,
																'JPEG', 30, 50,
																555, 400); // 2: 19

														var myImage2 = canvas2
																.toDataURL("image/jpeg,1.0");

														var myImage3 = canvas3
																.toDataURL("image/jpeg,1.0");

														pdf.addPage();

														pdf.addImage(myImage2,
																'JPEG', 30, 10,
																555, 800); // 2: 19

														pdf.addPage();

														pdf.addImage(myImage3,
																'JPEG', 30, 10,
																555, 450); // 2: 19
														pdf.save('test.pdf');
													}
												});

									}
								});

					}
				});

		console.log($("#target").width() + "  " + $("#target").height());
		
	}
</script>


</html>