
<form class="form-horizontal reduce-gap" name="masterForm" role="form"
	novalidate>
	<div class="modal-body">
		<div ng-show="errorlist" class="alert alert-block alert-danger">
			<ul>
				<span ng-repeat="errors in errorlist"> <span
					ng-repeat="n in errors track by $index">
						<li>{{(n)}}</li>
				</span>
				</span>
			</ul>
		</div>

		<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<label class="col-md-4 control-label" for="um_fullname">Full
						Name<span class="star">*</span>
					</label>
					<div class="col-md-7">
						<input class="form-control" required type="text" id="um_fullname"
							name="um_fullname" ng-model="masterentity.um_fullname"
							placeholder="User Full Name" />
					</div>
				</div>
			</div>
			<div class="col-md-6" ng-if="!masterentity.um_id">
				<div class="form-group">
					<label class="col-md-4  control-label" for="username">Username<span
						class="star">*</span></label>
					<div class="col-md-7">
						<input type="text" required class="form-control" id="username"
							name="username" placeholder="Login Id"
							ng-model="masterentity.username" />
					</div>
				</div>
			</div>
			<div class="col-md-6" ng-if="masterentity.um_id">
				<div class="form-group">
					<label class="col-md-4  control-label" for="username">Username<span
						class="star">*</span></label>
					<div class="col-md-7">
						<input type="text" class="form-control" id="username"
							name="username" ng-model="masterentity.username"
							ng-readonly="true" />
					</div>
				</div>
			</div>

			<div class="col-md-6" ng-if="!masterentity.um_id">
				<div class="form-group">
					<label class="col-md-4  control-label" for="password">Password<span
						class="star">*</span></label>
					<div class="col-md-7">
						<input type="password" required class="form-control" id="password"
							placeholder="password" ng-model="masterentity.password" />
					</div>
				</div>
			</div>



			<div class="col-md-6" ng-if="!masterentity.um_id">
				<div class="form-group">
					<label class="col-md-4  control-label" for="confirmpassword">Confirm
						Password<span class="star">*</span>
					</label>
					<div class="col-md-7">
						<input type="password" required class="form-control"
							id="confirmpassword" placeholder="password"
							ng-model="masterentity.confirmpassword" />
					</div>
				</div>
			</div>

			<div class="col-md-6" ng-if="!masterentity.um_id">
				<div class="form-group">
					<label class="col-md-4  control-label" for="um_role_id">User
						Role <span class="star">*</span>
					</label>
					<div class="col-md-7">
						<select id="um_role_id" ng-model="masterentity.um_role_id"
							class="form-control" id="role_id" required name="role_id"
							ng-options="rl.lk_id as rl.lk_longname for rl in roleData"></select>
					</div>
				</div>
			</div>
			<div class="col-md-6"
				ng-if="!masterentity.um_id && masterentity.um_role_id==351426">
				<div class="form-group">
					<label class="col-md-4  control-label" for="um_role_id">Court
						<span class="star">*</span>
					</label>
					<div class="col-md-7">
						<select id="court_id" ng-model="masterentity.court_id"
							class="form-control" id="court_id" required name="court_id"
							ng-options="court.cm_id as court.cm_name for court in courtsData | orderBy:'cm_id' "></select>
					</div>
				</div>
			</div>

			<div class="col-md-6" ng-if="masterentity.um_id">
				<div class="form-group">
					<label class="col-md-4  control-label" for="um_role_id">User
						Role <span class="star">*</span>
					</label>
					<div class="col-md-7">
						<select id="um_role_id" ng-model="masterentity.um_role_id"
							class="form-control" id="role_id" required name="role_id"
							ng-options="rl.lk_id as rl.lk_longname for rl in roleData"
							disabled></select>
					</div>
				</div>
			</div>

			<!-- Devi Prasad start  -->
			<div class="col-md-6"
				ng-if=" masterentity.um_role_id==351444 || masterentity.um_role_id==351433">
				<div class="form-group">
					<label class="col-md-4  control-label"> Case Type <span
						class="star">*</span>
					</label>
					<div class="col-md-7">
						<select style="width: 100%;" class="form-control" id="um_ct_id"
							ng-model="masterentity.um_ct_id" name="um_ct_id[]"
							ng-options="caseType.ct_id as caseType.labelandname for caseType in caseTypes  | orderBy:'ct_label' | filter:{ct_status:1}"
							multiple="multiple" select2="">
							<option value="">Select Case Type</option>
						</select>
					</div>

				</div>
			</div>
			<!-- Devi Prasad end  -->




			<div class="col-md-6">
				<div class="form-group">
					<label class="col-md-4  control-label" for="um_vendor_id">Status
						<span class="star">*</span>
					</label>
					<div class="col-md-7">
						<input type="radio" required name="Active" value="1"
							ng-model="masterentity.um_rec_status" /> Active <input
							type="radio" required name="InActive" value="2"
							ng-model="masterentity.um_rec_status" /> InActive
					</div>
				</div>
			</div>


		</div>
	</div>

	<div class="modal-footer">
		<div ng-if="!masterentity.um_id">
			<input type="submit" value="Submit" id="create-masterForm"
				ng-disabled="masterForm.$invalid" data-loading-text="Creating..."
				autocomplete="off" ng-click="user_create(masterentity)"
				class="btn btn-success" />
			<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
		</div>

		<div ng-if="masterentity.um_id">
			<input type="submit" value="Reset" id="update-masterForm"
				ng-disabled="masterForm.$invalid" data-loading-text="Creating..."
				autocomplete="off" ng-click="Date_Reset()" class="btn btn-success" />
			<input type="submit" value="Update" id="update-masterForm"
				ng-disabled="masterForm.$invalid" data-loading-text="Updating..."
				autocomplete="off" ng-click="user_update(masterentity)"
				class="btn btn-success" />
			<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
		</div>

	</div>

</form>

