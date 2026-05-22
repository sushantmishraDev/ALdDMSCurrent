var EDMSApp = angular.module('EDMSApp', ['ui.bootstrap']);
EDMSApp.directive('loading', ['$http', function ($http) {
    return {
        restrict: 'A',
        link: function (scope, elm, attrs) {
            scope.isLoading = function () {
                return $http.pendingRequests.length > 0;
            };
            scope.$watch(scope.isLoading, function (v) {
                if (v) {
                    elm.show();
                } else {
                    elm.hide();
                }
            });
        }
    };
}]);



EDMSApp.controller('ECourtHomeCtrl',['$scope','$http',function ($scope, $http) {
	var baseUrl="/dms/";
	$scope.causelist_date = convertDate(new Date());
	loadMasterData();
	//$scope.labels = ["Download Sales", "In-Store Sales", "Mail-Order Sales", "Tele Sales", "Corporate Sales"];
	//$scope.data = [300, 500, 100, 40, 120];
	$scope.dailycount=0;
	$scope.freshcount=0;
	$scope.putFreshcount=0;
	$scope.backlogcount=0;
	$scope.supplementarycount=0;
	$scope.additionalcount=0;
	$scope.applicationcount=0;
	$scope.correctioncount=0;
	$scope.productioncount=0;
	$scope.tiacount=0;
	$scope.divShow=false;
	$scope.showProductionList =false;
	$scope.showtiaList =false;
	$scope.tdcount=0;
	$scope.showtdList =false;
	$scope.tfacount=0;
	$scope.showtfList =false;
	$scope.tscount=0;
	$scope.showtsList =false;
	$scope.tscount1=0;
	$scope.showtsList1 =false;
	$scope.tscount2=0;
	$scope.showtsList2 =false;
	$scope.tscount3=0;
	$scope.showtsList3 =false;
	$scope.tucount=0;
	$scope.showtuList =false;
	$scope.tu1count=0;
	$scope.tu2count=0;
	$scope.tu3count=0;
	$scope.tu4count=0;
	$scope.tu5count=0;
	$scope.showtu1List =false;
	$scope.showtu2List =false;
	$scope.showtu3List =false;
	$scope.showtu4List =false;
	$scope.showtu5List =false;
	
	$scope.showtu6List = false;
	$scope.showtu7List = false;
	$scope.showtu8List = false;
	$scope.showtu9List = false;
	$scope.showtu10List = false;
	$scope.showtu11List = false;
	$scope.showtu12List = false;
	$scope.showtu13List = false;
	$scope.showtu14List = false;
	$scope.showtu15List = false;
	$scope.showtu16List = false;
	$scope.showtu17List = false;
	$scope.showtu18List = false;
	$scope.showtu19List = false;
	$scope.showtu20List = false;
	$scope.showtu21List = false;
	$scope.showtu22List = false;
	$scope.showtu23List = false;
	$scope.showtu24List = false;
	$scope.showtu25List = false;
	$scope.showtu26List = false;
	$scope.showtu27List = false;
	$scope.showtu28List = false;
	
	$scope.tu6count = 0;
	$scope.tu7count = 0;
	$scope.tu8count = 0;
	$scope.tu9count = 0;
	$scope.tu10count = 0;
	$scope.tu11count = 0;
	$scope.tu12count = 0;
	$scope.tu13count = 0;
	$scope.tu14count = 0;
	$scope.tu15count = 0;
	$scope.tu16count = 0;
	$scope.tu17count = 0;
	$scope.tu18count = 0;
	$scope.tu19count = 0;
	$scope.tu20count = 0;
	$scope.tu21count = 0;
	$scope.tu22count = 0;
	$scope.tu23count = 0;
	$scope.tu24count = 0;
	$scope.tu25count = 0;
	$scope.tu26count = 0;
	$scope.tu27count = 0;
	$scope.tu28count = 0;
	
	$scope.listData =[];
	
	 $scope.open1 = function($event,type) {
		    $event.preventDefault();
		    $event.stopPropagation();
		    
		    if(type=="fromDate1")
		    	$scope.fromDate1= true;
		    if(type=="toDate1")
		    	$scope.toDate= true;
		};
	
	function loadMasterData2() {
		var countadd =0;
		$scope.listData =[];
		var response = $http.get(baseUrl+'ecourt/getreportforadditionallists?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData);
			  angular.forEach($scope.listData, function(object, key) {
				  countadd = countadd + object.count;
				  
			  
		  });
			  $scope.additionalcount= countadd+$scope.additionalcount;
			
			if($scope.listData.length > 0){
				//$scope.showTransfer = true;
			}
		
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	function getFreshMentionCount() {
		var countfreshmention =0;
		$scope.listData =[];
		var response = $http.get(baseUrl+'ecourt/getreportforfreshmentionlist?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData);
			  angular.forEach($scope.listData, function(object, key) {
				  countfreshmention = countfreshmention + object.count;
				  
			  
		  });
			  $scope.freshcount= countfreshmention+$scope.freshcount;
			
			if($scope.listData.length > 0){
				//$scope.showTransfer = true;
			}
		
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	loadMasterData5();
	function loadMasterData5() {
		var countadd =0;
		var response = $http.get(baseUrl+'ecourt/getreportforcorrectionlists?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData.length);
			 angular.forEach($scope.listData, function(object, key) {
				  countadd = countadd + object.count;
				  
			  
		  });
			 $scope.correctioncount=countadd+$scope.correctioncount;
			
			if($scope.listData.length != 0){
				 $scope.showList =true;
			}
		
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	
	getFreshMentionCount();
	function loadMasterData4() {
		var countadd =0;
		$scope.listData =[];
		var response = $http.get(baseUrl+'ecourt/getreportforsupplementarylist?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData);
			  angular.forEach($scope.listData, function(object, key) {
				  countadd = countadd + object.count;
				  
			  
		  });
			  $scope.supplementarycount= countadd+$scope.supplementarycount;
			
			if($scope.listData.length > 0){
				//$scope.showTransfer = true;
			}
		
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	loadMasterData4();
	
	
	
	
	
	function loadMasterData3() {
		$scope.showTransfer =false;
		
		
		$scope.totalCases =null;
		var response = $http.get(baseUrl+'ecourt/getTranferCasesCount?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {	
			if(data.modelList.length > 0 && data.modelList[0] > 0)
				{
				$scope.totalCases= data.modelList[0];
			
			 $scope.showTransfer =true;
				}
			
	
			 
		
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	loadMasterData3();
	loadMasterData2();
	
	$scope.isLoading = true;
	
	function loadMasterData() {

		var response = $http.get(baseUrl + 'ecourt/getreport?causelist_date='
		+ $scope.causelist_date);


		response.success(function (data) {


		$scope.masterdata = data.modelList;

		$scope.backupdata = data.modelList;

		$scope.backupdata1 = $scope.backupdata;


		// Allowed list IDs

		$scope.allowedListIds = [1, 2, 3, 4, 5, 35, 6, 7,21];


		$scope.additionListIds = [

		7, 11, 12, 13, 14, 15, 25, 23, 24,

		8, 9, 10, 16, 17, 18, 19, 20, 22

		];


		// Default Data

		$scope.defaultData = $scope.masterdata.filter(function (item) {

		return $scope.allowedListIds.includes(item.cl_list_type_mid);

		});


		// Other Data

		$scope.otherData = $scope.masterdata.filter(item =>

		!$scope.allowedListIds.includes(item.cl_list_type_mid)

		);


		// Removed additional list

		$scope.removedAdditional = $scope.otherData.filter(item =>

		!$scope.additionListIds.includes(item.cl_list_type_mid)

		);


		// Stop loader

		$scope.isLoading = false;

		});


		response.error(function () {

		$scope.isLoading = false; // also stop loader on error

		});

		}
	
	function loadMasterData_old() {
		

		var response = $http.get(baseUrl + 'ecourt/getreport?causelist_date=' + $scope.causelist_date);
		response.success(function(data, status, headers, config) {
			 
			$scope.masterdata = data.modelList;
             console.log(data.modelList)
			 console.log("master data***************"+$scope.masterdata);
             $scope.masterdata = data.modelList;
			 $scope.backupdata = data.modelList;
			 $scope.backupdata1 = $scope.backupdata
			 
			 


           // Allowed card list types
           $scope.allowedListIds = [1,2,3,4,5,35,6,7];
		   
		   $scope.additionListIds = [7,11,,12,13,,14,15,25,23,24,8,9,10,16,17,18,19,20,22];

           //  Default data Filter automatically
           $scope.defaultData = $scope.masterdata.filter(function(item){
               return $scope.allowedListIds.includes(item.cl_list_type_mid);
           });
		   
		  
		   
		   console.log("----------------------------filter data"+ $scope.defaultData)
		   
		   $scope.otherData = $scope.backupdata.filter(item =>
		       !$scope.allowedListIds.includes(item.cl_list_type_mid)
		   );
		   
		   //  additional data Filter automatically
		   $scope.removedAdditional = $scope.otherData.filter(item =>
		   	       !$scope.additionListIds.includes(item.cl_list_type_mid)
		   	   );
			   
		   console.log("removerd additionaldata"+ $scope.removedAdditional)
		   console.log(JSON.stringify( $scope.otherData))
		   
		angular.forEach($scope.defaultData, function(object, key) {

				if (object.cl_list_type_mid == 1) {
					$scope.applicationcount = object.count
				}
				if (object.cl_list_type_mid == 2) {
					$scope.correctioncount = object.count
				}
				if (object.cl_list_type_mid == 3) {
					$scope.dailycount = object.count
				}
				if (object.cl_list_type_mid == 4) {
					$scope.backlogcount = object.count
				}
				if (object.cl_list_type_mid == 5) {
					$scope.freshcount = object.count
				}

				if (object.cl_list_type_mid == 35) {
					$scope.putFreshcount = object.count
				}
				if (object.cl_list_type_mid == 6) {
					$scope.supplementarycount = object.count
				}
			/*	if (object.cl_list_type_mid == 7) {
					$scope.additionalcount = object.count;
				}
*/


			});

		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});

	
	}
	
	
	
	function loadMasterData_old() {
		var response = $http.get(baseUrl+'ecourt/getreport?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.masterdata= data.modelList;
			
			angular.forEach($scope.masterdata, function(object, key) {
				  
				  if(object.cl_list_type_mid==1){
					  $scope.applicationcount=object.count
				  }
				  if(object.cl_list_type_mid==2){
					  $scope.correctioncount=object.count
				  }
				  if(object.cl_list_type_mid==3){
					  $scope.dailycount=object.count
				  }
				  if(object.cl_list_type_mid==4){
					  $scope.backlogcount=object.count
				  }
				  if(object.cl_list_type_mid==5){
					  $scope.freshcount=object.count
				  }
				  
				  if(object.cl_list_type_mid==35){
					  $scope.putFreshcount=object.count
				  }
				  if(object.cl_list_type_mid==6){
					  $scope.supplementarycount=object.count
				  }
				  if(object.cl_list_type_mid==7){
					$scope.additionalcount=object.count;					 
					}
				  
				  if(object.cl_list_type_mid==19){
						$scope.productioncount=object.count;
						
						if($scope.productioncount > 0){
							$scope.showProductionList =true;
						}
						}
				  if(object.cl_list_type_mid==26){
						$scope.tdcount=object.count;
						
						if($scope.tdcount > 0){
							$scope.showtdList =true;
						}
						}
				  if(object.cl_list_type_mid==27){
						$scope.tfcount=object.count;
						
						if($scope.tfcount > 0){
							$scope.showtfList =true;
						}
						}
				  if(object.cl_list_type_mid==36){
						$scope.tpfcount=object.count;
						
						if($scope.tpfcount > 0){
							$scope.showtpfList =true;
						}
						}
				  
				  if(object.cl_list_type_mid==28){
						$scope.tiacount=object.count;
						
						if($scope.tiacount > 0){
							$scope.showtiaList =true;
						}
						}
				  if(object.cl_list_type_mid==29){
						$scope.tscount=object.count;
						
						if($scope.tscount > 0){
							$scope.showtsList =true;
						}
						}
				  if(object.cl_list_type_mid==30){
						$scope.tucount=object.count;
						
						if($scope.tucount > 0){
							$scope.showtuList =true;
						}
						}
				  if(object.cl_list_type_mid==31){
						$scope.tu1count=object.count;
						
						if($scope.tu1count > 0){
							$scope.showtu1List =true;
						}
						}
				  if(object.cl_list_type_mid==32){
						$scope.tu2count=object.count;
						
						if($scope.tu2count > 0){
							$scope.showtu2List =true;
						}
						}
				  if(object.cl_list_type_mid==33){
						$scope.tu3count=object.count;
						
						if($scope.tu3count > 0){
							$scope.showtu3List =true;
						}
						}
				  if(object.cl_list_type_mid==34){
						$scope.tu4count=object.count;
						
						if($scope.tu4count > 0){
							$scope.showtu4List =true;
						}
						}
				  
				  if(object.cl_list_type_mid==38){
						$scope.tscount1=object.count;
						
						if($scope.tscount1 > 0){
							$scope.showtsList1 =true;
						}
						}
				  
				  if(object.cl_list_type_mid==39){
						$scope.tscount2=object.count;
						
						if($scope.tscount2 > 0){
							$scope.showtsList2 =true;
						}
						}
				  
				  if(object.cl_list_type_mid==40){
						$scope.tscount3=object.count;
						
						if($scope.tscount3 > 0){
							$scope.showtsList3 =true;
						}
						}
				  if(object.cl_list_type_mid==41){
						$scope.tu5count=object.count;
						
						if($scope.tu5count > 0){
							$scope.showtu5List =true;
						}
						}
				  
				  if (object.cl_list_type_mid == 42) {
						$scope.tu6count = object.count;
						if ($scope.tu6count > 0) {
							$scope.showtu6List = true;
						}
					}
					if (object.cl_list_type_mid == 43) {
						$scope.tu7count = object.count;
						if ($scope.tu7count > 0) {
							$scope.showtu7List = true;
						}
					}
					if (object.cl_list_type_mid == 44) {
						$scope.tu8count = object.count;
						if ($scope.tu8count > 0) {
							$scope.showtu8List = true;
						}
					}
					if (object.cl_list_type_mid == 45) {
						$scope.tu9count = object.count;
						if ($scope.tu9count > 0) {
							$scope.showtu9List = true;
						}
					}
					if (object.cl_list_type_mid == 46) {
						$scope.tu10count = object.count;
						if ($scope.tu10count > 0) {
							$scope.showtu10List = true;
						}
					}
					if (object.cl_list_type_mid == 47) {
						$scope.tu11count = object.count;
						if ($scope.tu11count > 0) {
							$scope.showtu11List = true;
						}
					}
					if (object.cl_list_type_mid == 48) {
						$scope.tu12count = object.count;
						if ($scope.tu12count > 0) {
							$scope.showtu12List = true;
						}
					}

					if (object.cl_list_type_mid == 49) {
						$scope.tu13count = object.count;
						if ($scope.tu13count > 0) {
							$scope.showtu13List = true;
						}
					}


					if (object.cl_list_type_mid == 50) {
						$scope.tu14count = object.count;
						if ($scope.tu14count > 0) {
							$scope.showtu14List = true;
						}
					}

					if (object.cl_list_type_mid == 51) {
						$scope.tu15count = object.count;
						if ($scope.tu15count > 0) {
							$scope.showtu15List = true;
						}
					}
					if (object.cl_list_type_mid == 52) {
						$scope.tu16count = object.count;
						if ($scope.tu16count > 0) {
							$scope.showtu16List = true;
						}
					}

					if (object.cl_list_type_mid == 53) {
						$scope.tu17count = object.count;
						if ($scope.tu17count > 0) {
							$scope.showtu17List = true;
						}
					}

					if (object.cl_list_type_mid == 54) {
						$scope.tu18count = object.count;
						if ($scope.tu18count > 0) {
							$scope.showtu18List = true;
						}
					}

					if (object.cl_list_type_mid == 55) {
						$scope.tu19count = object.count;
						if ($scope.tu19count > 0) {
							$scope.showtu19List = true;
						}
					}
					if (object.cl_list_type_mid == 56) {
						$scope.tu20count = object.count;
						if ($scope.tu20count > 0) {
							$scope.showtu20List = true;
						}
					}
					if (object.cl_list_type_mid == 57) {
						$scope.tu21count = object.count;
						if ($scope.tu21count > 0) {
							$scope.showtu21List = true;
						}
					}
					if (object.cl_list_type_mid == 58) {
						$scope.tu22count = object.count;
						if ($scope.tu22count > 0) {
							$scope.showtu22List = true;
						}
					}
					if (object.cl_list_type_mid == 59) {
						$scope.tu23count = object.count;
						if ($scope.tu23count > 0) {
							$scope.showtu23List = true;
						}
					}
					if (object.cl_list_type_mid == 60) {
						$scope.tu24count = object.count;
						if ($scope.tu24count > 0) {
							$scope.showtu24List = true;
						}
					}
					if (object.cl_list_type_mid == 61) {
						$scope.tu25count = object.count;
						if ($scope.tu25count > 0) {
							$scope.showtu25List = true;
						}
					}
					if (object.cl_list_type_mid == 62) {
						$scope.tu26count = object.count;
						if ($scope.tu26count > 0) {
							$scope.showtu26List = true;
						}
					}
					if (object.cl_list_type_mid == 63) {
						$scope.tu27count = object.count;
						if ($scope.tu27count > 0) {
							$scope.showtu27List = true;
						}
					}
					if (object.cl_list_type_mid == 64) {
										$scope.tu28count = object.count;
										if ($scope.tu28count > 0) {
											$scope.showtu28List = true;
										}
									}
					if (object.cl_list_type_mid == 33) {
						$scope.tu3count = object.count;

						if ($scope.tu3count > 0) {
							$scope.showtu3List = true;
						}
					}
					if (object.cl_list_type_mid == 34) {
						$scope.tu4count = object.count;

						if ($scope.tu4count > 0) {
							$scope.showtu4List = true;
						}
					}

					if (object.cl_list_type_mid == 38) {
						$scope.tscount1 = object.count;

						if ($scope.tscount1 > 0) {
							$scope.showtsList1 = true;
						}
					}

					if (object.cl_list_type_mid == 39) {
						$scope.tscount2 = object.count;

						if ($scope.tscount2 > 0) {
							$scope.showtsList2 = true;
						}
					}

					if (object.cl_list_type_mid == 40) {
						$scope.tscount3 = object.count;

						if ($scope.tscount3 > 0) {
							$scope.showtsList3 = true;
						}
					}
					if (object.cl_list_type_mid == 41) {
						$scope.tu5count = object.count;

						if ($scope.tu5count > 0) {
							$scope.showtu5List = true;
						}
					}

					  
					
				});
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	function loadMasterData1() {
		var response = $http.get(baseUrl+'ecourt/downloadCauseList?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.masterdata= data.modelList;
			angular.forEach($scope.masterdata, function(object, key) {
				  
				  if(object.cl_list_type_mid==1){
					  $scope.applicationcount=object.count
				  }
				  if(object.cl_list_type_mid==2){
					  $scope.correctioncount=object.count
				  }
				  if(object.cl_list_type_mid==3){
					  $scope.dailycount=object.count
				  }
				  if(object.cl_list_type_mid==4){
					  $scope.backlogcount=object.count
				  }
				  if(object.cl_list_type_mid==5){
					  $scope.freshcount=object.count
				  }
				  if(object.cl_list_type_mid==6){
					  $scope.supplementarycount=object.count
				  }
				  if(object.cl_list_type_mid==7){
					  $scope.additionalcount=object.count
				  }
				});
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	
	$scope.doownloadCauseList =function (){
		
		  console.log("function invokedddddgggg");
		  loadMasterData1();
	  }
	
	
	
	$scope.getCauseList=function(cause_list_date){
		$scope.causelist_date=convertDate(cause_list_date);
		loadMasterData();
		
		console.log("hello"+cause_list_date);
		
	}
	
	$scope.show=function (){
		
		
		$scope.divShow=true;
	
		};
	  $scope.open1 = function($event,type) {
		    $event.preventDefault();
		    $event.stopPropagation();
		    
		    if(type=="fromDate1")
		    	$scope.fromDate1= true;
		    if(type=="toDate1")
		    	$scope.toDate= true;
		};
		
		
		$scope.toggleMax = function() {
		    //$scope.minDate = $scope.minDate ? null : new Date();
			$scope.maxDate = new Date();
		};
		$scope.toggleMax();
		
		$scope.open = function($event,type) {
		    $event.preventDefault();
		    $event.stopPropagation();
		    
		    if(type=="fromDate")
		    	$scope.fromDate= true;
		    if(type=="toDate")
		    	$scope.toDate= true;
		};
		
		$scope.dateOptions = {
		    formatYear: 'yy',
		    startingDay: 1
		    
		};
		
		$scope.formats = ['dd-MMMM-yyyy','dd-mm-yyyy', 'yyyy/MM/dd', 'dd-MM-yyyy', 'shortDate'];
		$scope.format = $scope.formats[3];
		
		function convertDate(inputFormat) 
		{
			  function pad(s) { return (s < 10) ? '0' + s : s; }
			  var d = new Date(inputFormat);
			  return [ d.getFullYear(), pad(d.getMonth()+1),pad(d.getDate())].join('-');
		}
}]);