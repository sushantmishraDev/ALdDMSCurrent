var edmsApp = angular.module("EDMSApp",['smart-table']);

edmsApp.directive ("select2", function ($timeout, $parse) {
	  return {
	    restrict: 'AC',
	    require: 'ngModel',
	    link: function(scope, element, attrs) {
	      console.log(attrs);
	      $timeout(function() {
	        element.select2();
	        
	        element.select2Initialized = true;
	      });

	      var refreshSelect = function() {
	        if (!element.select2Initialized) return;
	        $timeout(function() {
	          element.trigger('change');
	        });
	      };
	      
	      var recreateSelect = function () {
	        if (!element.select2Initialized) return;
	        $timeout(function() {
	          element.select2('destroy');
	          element.select2();
	        });
	      };

	      scope.$watch(attrs.ngModel, refreshSelect);

	      if (attrs.ngOptions) {
	        var list = attrs.ngOptions.match(/ in ([^ ]*)/)[1];
	        // watch for option list changel
	        scope.$watch(list, recreateSelect);
	      }

	      if (attrs.ngDisabled) {
	        scope.$watch(attrs.ngDisabled, refreshSelect);
	      }
	    }
	  };
	});

edmsApp.controller("UserMasterCtrl",['$scope','$http', function($scope,$http) {
	var urlBase="/dms/";
	$scope.masterentity = {};	
	$scope.masterdata=  [];
	$scope.roleData=[];
	$scope.departmentData=[];
	$scope.designationData=[];
	$scope.userrole={};
	$scope.userroles=[];
	$scope.errorlist=null;
	$scope.userpemissions=[];
	$scope.repositories=[];
	$scope.folders=[];
	$scope.userApprovalData=[];
	
	
	//getUserForApproval();
	getMasterdata();
	getRoleDD();
	
	
$scope.approveUser =function(data){
		
		console.log("approve urser dataaaaaaaaaaa",data);
		
		data.hcu_approved_status= "Y";
		
		$http.post(urlBase+'/userApproval',data).success(function (data){
			
			console.log("status of approve user",data);
			
			
		}).error(function(data, status, headers, config){
			
			console.log("error in getting data");
			
		});
};
		
		 function getUserForApproval() 
			{
		    	console.log("this method invokedddd");
				$http.get(urlBase+'/getUserForApproval').success(function (data) {
		            	console.log("user approval dataaaaaaaa",data);
		            	

		            	
		            	$scope.userApprovalData = data.modelData;
		            	console.log("dataaaaaaaaaaaaa approv",$scope.userApprovalData);
		           
		            }).
		            error(function(data, status, headers, config) {
		            	console.log("Error in getting User data");
		            });
		    };
		    
		
		
	function getMasterdata() 
	{
		$http.get(urlBase+'user/getallusers').success(function (data) {
            	$scope.masterdata = data; 
            	$scope.displayedCollection = [].concat($scope.masterdata);
            }).
            error(function(data, status, headers, config) {
            	console.log("Error in getting User data");
            });
    };
    function getRoleDD() {
		$http.get(urlBase+'user/getmasters').
            success(function (data) {
            	$scope.roleData=data.roleData;
            	$scope.departmentData=data.departmentData;
            	$scope.designationData=data.designationData;
            	$scope.courtsData=data.courtsData;
            	$scope.masterdata.um_pass_validity_date = new Date();
            }).
            error(function(data, status, headers, config) {
            	console.log("Error in getting property details");
            });
		
		}
    
    $scope.changeStatus=function(permission){
    	$scope.errorlist=[];
    	$scope.entity=angular.copy(permission);
    	
    	if($scope.entity.status==0)
    		$scope.entity.status=1;
    	else
    		$scope.entity.status=0;
    	
    	$http.post(urlBase+'user/updateuserpermission',$scope.entity).
        success(function (data) {
        	if(data.response=="TRUE"){
        		$scope.repositories=data.repositories;
            	$scope.folders=data.folders;
        	}else{
        		$scope.errorlist=data.dataMapList;
        	}
        }).
        error(function(data, status, headers, config) {
        	console.log("Error in getting property details");
        });
    };
    
    
    
    $scope.user_create = function(masterentity) {
    	console.log(masterentity);
    	
    	$scope.masterentity=masterentity;
    	
//    	if($scope.masterentity){
//    		angular.forEach($scope.masterdata,function(value,index){	    			
//    			if(value.username==$scope.masterentity.username){
//    				if($scope.masterentity.um_id){
//    					if($scope.masterentity.um_id!=value.um_id){
//    						$scope.error=true;
//    					}
//    				}else{
//    					$scope.error=true;
//    				}
//    			}
//            });
//    	}
    	
    		var response = $http.post(urlBase+'user/create',$scope.masterentity);		 
				response.success(function(data, status, headers, config) {					
					if(data.response=="FALSE"){					
						$scope.errorlist=data.dataMapList;
					}else{		
						$('#user_Modal').modal('hide');		
						$scope.errorlist=[];
						
						$('.form-group').removeClass('has-error');
						
								alert("User created Successfully!");
								getMasterdata();						
					}
					
			});
			response.error(function(data, status, headers, config) {
				alert( "Error");
			});
    	
	};
	
	
	$scope.user_update=function(masterentity)
	{
		console.log(masterentity);
		$scope.masterentity=masterentity;
		
		
		var response=$http.post(urlBase+'user/update',$scope.masterentity);
		response.success(function(data,status,headers,config){
			if(data.response=="FALSE")
				{
				$scope.errorlist=data.dataMapList;					
					
				}
			else
				{
				$('#user_Modal').modal('hide');		
				$scope.errorlist=null;
				$('.form-group').removeClass('has-error');
				alert("User updated Successfully!");
				getMasterdata();					
			    }
			
			
		});
			response.error(function(data, status, headers, config) {
				alert( "Error");
			}); 
	};
	$scope.setMasterdata = function(masterentity) {
		$scope.masterentity = angular.copy(masterentity);
		
		if($scope.masterentity.userroles.length>0)
			$scope.masterentity.um_role_id= $scope.masterentity.userroles[0].ur_role_id;
	};
    
	 getCaseTypes();
	function getCaseTypes(){
		  $http.get(urlBase+'master/getcasetypes').success(function (masterentity) {
			  for (var i = 0; i < masterentity.modelList.length; i++) {
  			  masterentity.modelList[i].labelandname ="";
  			  masterentity.modelList[i].labelandname = masterentity.modelList[i].ct_label.concat("-",masterentity.modelList[i].ct_name);
  		  }
		    		$scope.caseTypes=masterentity.modelList;
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting casetypes");
		      });
	  }

	$scope.resetModel=function()
	{
		$scope.masterentity.um_rec_status=1;
		$scope.masterentity={};		
		$('.form-group').removeClass('has-error');	
		$scope.index=-1;
		
	};
    
	$scope.Refresherrorlist=function(data)
	{
		$scope.um_id=data.um_id;
		$scope.errorlist = null;
	};

	$scope.user={};
	
	$scope.changePassword = function(data) {
				$scope.user=data;
				$scope.user.um_id=$scope.um_id;
		var response = $http.post(urlBase+'user/changepassword',
				$scope.user);
		response.success(function(data, status, headers, config) {
			if (data.response == "TRUE") {
				$('#pass_Modal').modal('hide');		
				$scope.errorlist=null;
				$('.form-group').removeClass('has-error');
				alert("Password changed Successfully!");

			} else {
				$scope.errorlist = data.dataMapList;
				$scope.ansDetails = [];
			
			}

		});
		response.error(function(data, status, headers, config) {
			console.log("Error in getting Master");
		});

  
}
}]);