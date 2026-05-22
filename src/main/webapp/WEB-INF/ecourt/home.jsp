<jsp:include page="../content/header2.jsp"></jsp:include>

<
<style>
<!--
.container-fluid {
	padding-right: 0px;
	padding-left: 0px;
	margin-right: auto;
	margin-left: auto;
}

.separator {
  display: flex;       /* Use Flexbox for easy alignment */
  align-items: center; /* Vertically centers the text and lines */
  text-align: center;
  margin: 20px 0;      /* Adds some vertical spacing */
}

.separator::before,
.separator::after {
  content: '';         /* Required for pseudo-elements */
  flex: 1;             /* Makes the lines take up remaining space */
  border-bottom: 1px solid #000; /* Styles the line */
}

.separator:not(:empty)::before {
  margin-right: .5em;  /* Space between the line and text */
}

.separator:not(:empty)::after {
  margin-left: .5em;   /* Space between the text and the other line */
}


</style>




<div id="content" class="content" ng-controller="ECourtHomeCtrl" style="background-color: white;height: 100%;">


	<!-- Loader -->
	<div class="row justify-content-center align-items-center"
		ng-if="isLoading" style="min-height: 300px;">
		<div class="col-12 d-flex justify-content-center">
			<div class="text-center">
				<div class="spinner-border text-primary" role="status"
					style="width: 4rem; height: 4rem;"></div>
				<h4 class="mt-3" style="font-weight: 600; color: #444;">Loading...</h4>
			</div>
		</div>
	</div>


	<div class="container-fluid">

		<div class="row "
			ng-if="!isLoading && (!defaultData || defaultData.length === 0) && (!additionalcount || additionalcount == 0) && (!removedAdditional || removedAdditional == 0)"
			style="min-height: 300px;">

			<div class="col-12  ">
				<div class="card shadow-lg p-4"
					style="max-width: 450px; border-left: 6px solid #ff9800; border-radius: 12px; margin-left:280px;
					 border-right: 6px solid #ff9800; margin-top:100px;">
					<div class="card-body text-center">
						<i class="fa fa-exclamation-triangle"
							style="font-size: 40px; color: #ff9800;"></i>

						<h3 class="mt-3" style="font-weight: 700; color: #444;">No Case Listed In This Court !</h3>

						<!--  <p style="font-size: 16px; color: #666;">
                        Today, there are currently no lists available.
                    </p> -->
					</div>
				</div>
			</div>

		</div>
		<div class="row"
			ng-if="(defaultData && defaultData.length > 0) || (additionalcount && additionalcount > 0)">

			<!-- Default Data Cards -->
			<div ng-hide="item.count==0" class="col-md-3 col-sm-6 d-flex" ng-repeat="item in defaultData">
			<a target="_new" href="/dms/causelist/type/{{item.cl_list_type_mid}}" >
				<div class="widget widget-stats bg-green w-75 d-flex flex-column"
					style="display: flex; flex-direction: column; justify-content: space-between; height: 100%; width: 238px;">

					<div class="stats-icon">
						<i class="fa fa-desktop"></i>
					</div>

					<div class="stats-info flex-grow-1">
						<h4 style="font-size: 15px;">{{item.listTypeName}}</h4>
						<h4 style="font-size: 15px;">Total Case Listed:
							{{item.count}}</h4>
					</div>

					<div class="stats-link">
						<a target="_new"
							href="/dms/causelist/type/{{item.cl_list_type_mid}}"> View
							Detail <i class="fa fa-arrow-circle-o-right"></i>
						</a>
					</div>

				</div>
				</a>
			</div>

			<!-- Additional Count  -->
			<div class="col-md-3 col-sm-6 d-flex"
				ng-if="additionalcount && additionalcount > 0">
				<a target="_new" href="/dms/causelist/type/7">
				<div class="widget widget-stats bg-green w-75 d-flex flex-column"
					style="display: flex; flex-direction: column; justify-content: space-between; height: 120px; width: 238px;">

					<div class="stats-icon">
						<i class="fa fa-desktop"></i>
					</div>

					<div class="stats-info">
						<h4 style="font-size: 15px;">Additional/Unlisted</h4>
						<h4 style="font-size: 15px;">Total Case Listed :
							{{additionalcount}}</h4>
					</div>

					<div class="stats-link">
						<a target="_new" href="/dms/causelist/type/7"> View Detail <i
							class="fa fa-arrow-circle-o-right"></i>
						</a>
					</div>

				</div></a>
			</div>

		</div>

	</div>

	<br>
	<!-- <hr class="bg-dark text-dark"> -->
	
	<div ng-show="removedAdditional!=0" class="separator">
  <span style="font-size: 20px;color: darkviolet; "><i>Transferred Lists</i></span>
</div>


	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-sm-6 d-flex"
				ng-repeat="item in removedAdditional">
				<a target="_new" href="/dms/causelist/type/{{item.cl_list_type_mid}}">
				<div class="widget widget-stats bg-green w-75 d-flex flex-column"
					style="display: flex; flex-direction: column; justify-content: space-between; height: 100%; width: 238px;">

					<div class="stats-icon">
						<i class="fa fa-desktop"></i>
					</div>

					<div class="stats-info flex-grow-1">
						<h4 style="font-size: 15px;">{{item.listTypeName}}</h4>
						<h4 style="font-size: 15px;">Total Case Listed:
							{{item.count}}</h4>
					</div>

					<div class="stats-link">
						<a target="_new"
							href="/dms/causelist/type/{{item.cl_list_type_mid}}"> View
							Detail <i class="fa fa-arrow-circle-o-right"></i>
						</a>
					</div>

				</div></a>
			</div>
		</div>
	</div>

	<!-- Transfered Case list -->
	<div class="container-fluid" ng-show="divShow">
		<label class="control-label col-md-4">Date of Decision: <span
			class="text-danger"> * </span></label>
		<div class="col-md-4">
			<input type="text" class="form-control"
				datepicker-popup="{{format1}}" name="fromDate1"
				ng-change="getCauseList(cause_list_date)" ng-model="cause_list_date"
				is-open="fromDate1" max-date="maxDate"
				datepicker-options="dateOptions" ng-disabled="true"
				date-disabled="disabled(date, mode)" close-text="Close"
				show-button-bar="false" /> <span class="input-group-addon"
				ng-click="open1($event,'fromDate1')"><i
				class="glyphicon glyphicon-calendar"></i></span>
		</div>





	</div>
	
	<div style="position: absolute;">
	<marquee width=100% direction="left">
		<font size="2" face="verdana" color="red"> The list (in new
			format) have been populated based on CCMS data. Any changes/
			modification in case listing data in CCMS, the same will be reflected
			in e-Court also.</font>
	</marquee>
</div>

</div>




</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/scripts/controllers/EcourtHomeController.js?v=11"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/angular-datepicker.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap/ui-bootstrap-tpls.0.11.2.js"></script>

<script src="${pageContext.request.contextPath}/assets/js/apps.min.js"></script>
<script>
	$(document).ready(function() {
		App.init();

	});
</script>
</html>