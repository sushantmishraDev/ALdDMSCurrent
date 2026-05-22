<jsp:include page="../content/header2.jsp"></jsp:include>

<<style>
<!--
.container-fluid {
    padding-right: 0px;
    padding-left: 0px;
    margin-right: auto;
    margin-left: auto;
}
-->
</style>

<div id="content" class="content" ng-controller="ECourtHomeCtrl">
	<div class="container-fluid">
			<div class="col-md-3 col-sm-6">
				<a target="_new" href="/dms/causelist/type/5">
					<div class="widget widget-stats bg-green">
						<div class="stats-icon">
							<i class="fa fa-desktop"></i>
						</div>
						<div class="stats-info">
							<h4 style ="font-size: 15px;">Fresh Cases</h4>
							<h4 style ="font-size: 15px;">Total Case Listed : {{freshcount}}</h4>
						</div>
				</a>
						<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/5">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
					</div>
				</a>
		</div>
		
		
		
			<div class="container-fluid">
	   <div  class="col-md-3 col-sm-6">
	   	<a target="_new" href="/dms/causelist/type/3">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;" >Daily Cause List</h4>
					<h4 style ="font-size: 15px;" >Total Case Listed : {{dailycount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/3">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		
			<div class="container-fluid">
		<div class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/1">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 14px;" >Daily IA</h4>
					<h4 style ="font-size: 15px;" >Total Case Listed : {{applicationcount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/1">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>

        	<div class="container-fluid">
		<div class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/2">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;" >Correction Application</h4>
					<h4 style ="font-size: 15px;" >Total Case Listed : {{correctioncount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/2">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		
		<div class="container-fluid">
			<div class="col-md-3 col-sm-6">
				<a target="_new" href="/dms/causelist/type/35">
					<div class="widget widget-stats bg-green">
						<div class="stats-icon">
							<i class="fa fa-desktop"></i>
						</div>
						<div class="stats-info">
							<h4 style ="font-size: 15px;">'As-Fresh' List</h4>
							<h4 style ="font-size: 15px;">Total Case Listed : {{putFreshcount}}</h4>
						</div>
				</a>
						<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/35">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
					</div>
				</a>
		</div>
			<div class="container-fluid">
		<div class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/4">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;" >Backlog</h4>
					<h4 style ="font-size: 15px;">Total Case Listed : {{backlogcount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/4">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>

			<div class="container-fluid">
		<div class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/6">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Fresh Supplementry</h4>
					<h4 style ="font-size: 15px;">Total Case Listed : {{supplementarycount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/6">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
			<div class="container-fluid">
		<div class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/7">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Additional/Unlisted</h4>
					<h4 style ="font-size: 15px;">Total Case Listed : {{additionalcount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/7">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
			
		</div>
			<div class="container-fluid">
		<div  ng-show ="showProductionList" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/19">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Production Cause List</h4>
					<h4 style ="font-size: 15px;">{{productioncount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/19">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
			
			
		</div>
		</div>
		</br>
		</br>
		</br>
		</br>
		
		<div class="container-fluid">
		<div  ng-show ="showtdList" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/26">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Daily Cause List </h4>
					<h4 style ="font-size: 15px;">{{tdcount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/26">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		
		<div class="container-fluid">
		<div  ng-show ="showtfList" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/27">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Fresh List </h4>
					<h4 style ="font-size: 15px;">{{tfcount}}</h4>
				</div>
				</a>
			<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/27">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
			
			<div class="container-fluid">
		<div  ng-show ="showtiaList" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/28">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Daily IA</h4>
					<h4 style ="font-size: 15px;">{{tiacount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/28">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		<div class="container-fluid">
		<div  ng-show ="showtsList" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/29">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Fresh Supplementary </h4>
					<h4 style ="font-size: 15px;">{{tscount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/29">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		
		<div class="container-fluid">
		<div  ng-show ="showtsList1" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/29">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Fresh Supplementary -1</h4>
					<h4 style ="font-size: 15px;">{{tscount1}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/38">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		
		<div class="container-fluid">
		<div  ng-show ="showtsList2" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/29">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Fresh Supplementary -2 </h4>
					<h4 style ="font-size: 15px;">{{tscount2}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/39">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		
		<div class="container-fluid">
		<div  ng-show ="showtsList3" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/29">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Fresh Supplementary -3 </h4>
					<h4 style ="font-size: 15px;">{{tscount3}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/40">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		
		<div class="container-fluid">
		<div  ng-show ="showtuList" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/30">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Additional/Unlisted </h4>
					<h4 style ="font-size: 15px;">{{tucount}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/30">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		<div class="container-fluid">
		<div  ng-show ="showtu1List" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/31">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Additional/Unlisted List-1</h4>
					<h4 style ="font-size: 15px;">{{tu1count}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/31">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
			<div class="container-fluid">
		<div  ng-show ="showtu2List" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/32">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Additional/Unlisted List-2</h4>
					<h4 style ="font-size: 15px;">{{tu2count}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/32">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
			<div class="container-fluid">
		<div  ng-show ="showtu3List" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/33">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Additional/Unlisted List-3</h4>
					<h4 style ="font-size: 15px;">{{tu3count}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/33">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		
		<div class="container-fluid">
		<div  ng-show ="showtpfList" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/36">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Fresh List -1 </h4>
					<h4 style ="font-size: 15px;">{{tpfcount}}</h4>
				</div>
				</a>
			<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/36">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
			<div class="container-fluid">
		<div  ng-show ="showtu4List" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/34">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Additional/Unlisted List-4</h4>
					<h4 style ="font-size: 15px;">{{tu4count}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/34">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		<div class="container-fluid">
		<div  ng-show ="showtu5List" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/causelist/type/34">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Additional/Unlisted List-5</h4>
					<h4 style ="font-size: 15px;">{{tu5count}}</h4>
				</div>
				</a>
				<div class="stats-link">
							<a target="_new" href="/dms/causelist/type/41">View Detail <i class="fa fa-arrow-circle-o-right"></i></a>
						</div>
			</div>
		</div>
		<div class="container-fluid">
		<div ng-show ="showTransfer" class="col-md-3 col-sm-6">
			<a target="_new" href="/dms/ecourt/transferHome">
		<!-- <div  class="col-md-3 col-sm-6"> -->
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style ="font-size: 15px;">Transferred Cases/Applications</h4>
					<h4 style ="font-size: 15px;">{{totalCases}}</h4>
				</div>
				</a>
				<div class="stats-link">
					<a targert="_new" href="/dms/ecourt/transferHome">View Detail <i
						class="fa fa-arrow-circle-o-right"></i></a>
				</div>
			</div>
		</div>
		
		<div class="container-fluid">
	<div ng-show="showtu6List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/42">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Fresh List-2</h4>
					<h4 style="font-size: 15px;">{{tu6count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/42">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu7List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/43">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Daily Cause List-1</h4>
					<h4 style="font-size: 15px;">{{tu7count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/43">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu8List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/44">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Daily Cause List-2</h4>
					<h4 style="font-size: 15px;">{{tu8count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/44">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu9List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/45">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Daily IA List-1</h4>
					<h4 style="font-size: 15px;">{{tu9count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/45">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu10List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/46">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Daily IA List-2</h4>
					<h4 style="font-size: 15px;">{{tu10count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/46">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu11List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/47">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted
						List-1-1</h4>
					<h4 style="font-size: 15px;">{{tu11count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/47">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu12List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/48">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted
						List-1-2</h4>
					<h4 style="font-size: 15px;">{{tu12count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/48">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu13List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/49">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted
						List-1-3</h4>
					<h4 style="font-size: 15px;">{{tu13count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/49">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu14List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/50">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted
						List-1-2</h4>
					<h4 style="font-size: 15px;">{{tu14count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/50">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu15List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/51">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted
						List-2-2</h4>
					<h4 style="font-size: 15px;">{{tu15count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/51">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu16List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/52">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted
						List-3-2</h4>
					<h4 style="font-size: 15px;">{{tu16count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/52">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu17List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/53">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Fresh Supplementary
						List-1-1</h4>
					<h4 style="font-size: 15px;">{{tu17count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/53">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu18List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/54">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Fresh Supplementary
						List-2-1</h4>
					<h4 style="font-size: 15px;">{{tu18count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/54">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu19List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/55">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Fresh Supplementary
						List-3-1</h4>
					<h4 style="font-size: 15px;">{{tu19count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/55">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu20List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/56">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Fresh Supplementary
						List-1-2</h4>
					<h4 style="font-size: 15px;">{{tu20count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/56">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu21List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/57">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Fresh Supplementary
						List-2-2</h4>
					<h4 style="font-size: 15px;">{{tu21count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/57">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div ng-show="showtu22List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/58">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Fresh Supplementary
						List-3-2</h4>
					<h4 style="font-size: 15px;">{{tu22count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/58">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div ng-show="showtu23List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/59">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted List-4-1 
						</h4>
					<h4 style="font-size: 15px;">{{tu23count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/59">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div ng-show="showtu24List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/60">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted List-4-2
					</h4>
					<h4 style="font-size: 15px;">{{tu24count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/60">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu25List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/61">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted List-5-1</h4>
					<h4 style="font-size: 15px;">{{tu25count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/61">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu26List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/62">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Additional/Unlisted List-5-2</h4>
					<h4 style="font-size: 15px;">{{tu26count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/62">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu27List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/63">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Supplementary Fresh List-0
						</h4>
					<h4 style="font-size: 15px;">{{tu27count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/63">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div ng-show="showtu28List" class="col-md-3 col-sm-6">
		<a target="_new" href="/dms/causelist/type/64">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4 style="font-size: 15px;">Transferred Supplementary Fresh List-0-1
						</h4>
					<h4 style="font-size: 15px;">{{tu28count}}</h4>
				</div>
		</a>
		<div class="stats-link">
			<a target="_new" href="/dms/causelist/type/64">View Detail <i
				class="fa fa-arrow-circle-o-right"></i></a>
		</div>
	</div>
</div>
		
		</div>
		
			
		
<!-- 				                                                 <button id="doownloadCauseList" type="button" class="btn btn-primary btn-sm" ng-click="doownloadCauseList()">Cause List</button>
 -->		

		<!-- Sushant -->

<!-- 		<div class="col-md-3 col-sm-6">
			<div class="widget widget-stats bg-green">
				<div class="stats-icon">
					<i class="fa fa-desktop"></i>
				</div>
				<div class="stats-info">
					<h4>Click To view Previous Date Causlist</h4>
					<h4></h4>
				</div>
				<div class="stats-link">
					<a targert="_new" ng-click="show()">View Detail <i
						class="fa fa-arrow-circle-o-right"></i></a>
				</div>
			</div>
		</div> -->

	<div class="modal fade" id="user_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog modal-lg">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						        <h4 class="modal-title" id="myModalLabel"> <span ng-if="!masterentity.um_id"><strong> Calendar</strong></span>
						      
						      </div>	     
					  <%@ include file="../ecourt/calender_form.jsp"%>
						    </div>
						  </div>
					</div>
	<div class="container-fluid" ng-show="divShow">
		<label class="control-label col-md-4">Date of Decision: <span class="text-danger"> * </span></label>
				<div class="col-md-4">
					<input type="text" class="form-control" datepicker-popup="{{format1}}" name="fromDate1" ng-change="getCauseList(cause_list_date)" ng-model="cause_list_date" is-open="fromDate1" max-date="maxDate"  datepicker-options="dateOptions" ng-disabled="true" date-disabled="disabled(date, mode)" close-text="Close" show-button-bar="false" />
					<span class="input-group-addon" ng-click="open1($event,'fromDate1')"><i class="glyphicon glyphicon-calendar"></i></span>
				</div>
				
				
				
				
				
	</div>
	
</div>

 <div style="position: absolute;">
    <marquee width=100% direction="left">
     <font size="2" face="verdana" color="red"> 
The list (in new format) have been populated based on CCMS data. Any changes/ modification in case listing data in CCMS, the same will be reflected in e-Court also.</font>
</marquee>
    </div>


</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/scripts/controllers/EcourtHomeController.js?v=9"></script>
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