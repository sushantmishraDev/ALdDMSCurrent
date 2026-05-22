<jsp:include page="../content/header2.jsp"></jsp:include>

<head>
   <link rel='stylesheet' href='${pageContext.request.contextPath}/css/globalStyle.css' type='text/css' />

</head>


<div id="content" class="content">
	<div class="container-fluid" ng-controller="UserMasterCtrl">

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
					<h4 class="panel-title">Manage Users</h4>
				</div>
				<div class="panel-body">
				<div class="row">
						<div class="col-md-12 ">
							<button type="button" class="btn-ui btn-purple-ui btn-sm pull-right" ng-click="resetModel()" data-toggle="modal" data-target="#user_Modal">
								<span class="glyphicon glyphicon-plus-sign"></span> Create New User
							</button>
						</div>
					</div>
					<div class="table-responsive">
						<table st-table="displayedCollection" st-safe-src="masterdata"  class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
								<th st-sort="username">User Name</th>
								<th st-sort="um_pass_validity_date">Fullname</th>
								<th st-sort="um_account_activation">Role</th>
								<th style="width: 20%;" class="text-center">Action</th>					
								</tr>
								<tr>
								<th><input st-search="username" placeholder="search for Username" class="input-sm form-control" type="search"/></th>
									<th><input st-search="um_fullname" placeholder="search for User Full Name" class="input-sm form-control" type="search"/></th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="data in displayedCollection">
								<td>{{data.username}}</td>
								<td>{{data.um_fullname}}</td>
								<td>{{data.userroles[0].lk.lk_longname}}
								</td>
								<td>
								<a class="btn btn-info btn-xs" ng-click="setMasterdata(data)" data-toggle="modal" data-target="#user_Modal" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</a>
								<a class="btn btn-info btn-xs" ng-click="Refresherrorlist(data)" data-toggle="modal" data-target="#pass_Modal" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>Change Password</a>
								</td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5" class="text-center">
										<div st-pagination="" st-items-by-page="10"  st-displayed-pages="4" ></div>
									</td>
								</tr>
							</tfoot>
						</table>
						
					</div>					
					<div class="modal fade" id="user_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog modal-lg">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						        <h4 class="modal-title" id="myModalLabel"> <span ng-if="!masterentity.um_id"><strong> Add New User</strong></span>
						        <span ng-if="masterentity.um_id"><Strong>Update User</Strong></span></h4>
						      </div>	     
					  			<%@ include file="../user/_master_form.jsp"%>
						    </div>
						  </div>
					</div>					
					<div class="modal fade" id="pass_Modal" tabindex="-1"
						role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header" style="background-color: black;">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="myModalLabel">
										<strong style="color: #FBFCFD;">Change Password </strong>
									</h4>
								</div>
									<%@ include file="../user/passwordPage.jsp"%>
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

</div>
<!-- end row -->
</body>




<!-- ================== END PAGE LEVEL JS ================== -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/scripts/controllers/UserMasterController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Smart-Table-master/dist/smart-table.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/apps.min.js"></script>
	
	<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/select2.min.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/angularJs/select2.full.min.js"></script>
<script>
	$(document).ready(function() {
		App.init();

	});
</script>



</html>