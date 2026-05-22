
<form class="form-horizontal reduce-gap" name="masterForm" role="form" novalidate>      
<div class="modal-body">
<div ng-show="errorlist" class="alert alert-block alert-danger">
	<ul>
    <span ng-repeat="errors in errorlist"  >
	        <span ng-repeat="n in errors track by $index">
	        	<li>{{(n)}}</li>
	        </span>
	    </span>
    </ul>
</div>

<div class="row">
	<div class="col-md-6">
	<div class="form-group">
			<label class="col-md-4 control-label" for="um_fullname">Bench Number<span class="star">*</span></label>
		    <div class="col-md-7">
						<input class="form-control" required type="text" id="becnhId"
							name="becnhId" ng-model="masterentity.cm_bench_id"
							placeholder="Bench Number"  ng-readonly="true" />
					</div>		   
	</div>
	</div>	
	
	
	
	
	
	<div class="col-md-6">
		<div class="form-group">
				<label class="col-md-4  control-label" for="um_vendor_id">Cause List Date <span class="star">*</span></label>
			    <div class="col-md-7">
		      		<input type="text" class="form-control" datepicker-popup="{{format1}}" name="fromDate1" ng-model="masterentity.clDate" is-open="fromDate1"  datepicker-options="dateOptions" ng-disabled="true" date-disabled="disabled(date, mode)" close-text="Close" show-button-bar="false" />
                    <span class="input-group-addon" ng-click="open1($event,'fromDate1')"><i class="glyphicon glyphicon-calendar"></i></span>
				</div>		   
		</div>
	</div>
	
	<div class="col-md-6" ng-if="suppFlag || tansFlag">
				<div class="form-group">
					<label class="col-md-3  control-label" for="um_role_id">CuaseListType<span class="star">*</span>
					</label>
					<div class="col-md-7" >
					<select style="width :100%;" ng-options="c.clt_ccms_list as (c.clt_description+' ('+c.clt_name+') - '+c.clt_ccms_list) for c in causeListTypes | orderBy:'clt_id' "
										class="form-control" ng-model="masterentity.clListType" select=""></select>
										
						
					</div>
				</div>
			</div>

		
</div>
</div>

<div class="modal-footer"> 
	<div ng-hide="suppFlag || tansFlag">
			<input type="submit" value="Generate" id="create-masterForm" data-loading-text="Creating..." autocomplete="off" ng-click="nextCause(masterentity)" class="btn btn-success"/>      			
			<input type="submit" value="Transfer" id="create-masterForm" data-loading-text="Creating..." autocomplete="off" ng-click="nextTrans(masterentity)" class="btn btn-secondary"/> 
			<input type="submit" value="Correction" id="create-masterForm" data-loading-text="Creating..." autocomplete="off" ng-click="nextCorrection(masterentity)" class="btn btn-primary"/>      			
				<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>  
	</div>
	<!-- {{suppFlag}}/{{tansFlag}} -->
	<div ng-if="suppFlag">
			<input type="submit" value="Supplimentry" id="create-masterForm" data-loading-text="Creating..." autocomplete="off" ng-click="supplimentryToday(masterentity)" class="btn btn-success"/>   			
				<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>  
	</div>
	
	<div ng-if="tansFlag">
			<input type="submit" value="Trenasfer" id="create-masterForm" data-loading-text="Creating..." autocomplete="off" ng-click="transferToday(masterentity)" class="btn btn-success"/>   			
				<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>  
	</div>
	
	
	     
</div>

</form>



 