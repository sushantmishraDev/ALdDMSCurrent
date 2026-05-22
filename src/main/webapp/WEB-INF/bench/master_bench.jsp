
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
			<label class="col-md-4 control-label" for="um_fullname">Court Name<span class="star">*</span></label>
		    <div class="col-md-7">
	      		<input class="form-control" required type="text" id="cm_name" name="cm_name" ng-model="court.cm_name" placeholder="Full Name"  /> 
	      	</div>		   
	</div>
	</div>	
	
	
	
	
	
	<div class="col-md-6">
		<div class="form-group">
				<label class="col-md-4  control-label" for="um_vendor_id">CauseList Date <span class="star">*</span></label>
			    <div class="col-md-7">
		      		<input type="radio" required  name="Active" value="1"
											ng-model="court.cm_rec_status" /> Ecourt 
					<input type="radio" required name="InActive" value="3"
											ng-model="court.cm_rec_status" /> In-Chamber
				</div>		   
		</div>
	</div>

		
</div>
</div>

<div class="modal-footer"> 
	<div ng-if="!masterentity.um_id">
			<input type="submit" value="Submit" id="create-masterForm" data-loading-text="Creating..." autocomplete="off" ng-click="court_create(masterentity)" class="btn btn-success"/>      			
				<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>  
	</div>
	
	
	     
</div>

</form>

 