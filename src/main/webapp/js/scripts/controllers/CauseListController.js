/*
 * 
 */

var edmsApp = angular.module("EDMSApp", ['ngFileUpload','smart-table','ui.bootstrap','ngMask']);

//edmsApp.directive('fileModel', [ '$parse', function($parse) {
//    return {
//        restrict : 'A',
//        link : function(scope, element, attrs) {
//            var model = $parse(attrs.fileModel);
//            var modelSetter = model.assign;
//
//            element.bind('change', function() {
//                scope.$apply(function() {
//                    modelSetter(scope, element[0].files[0]);
//                });
//            });
//        }
//    };
//} ]);


/**
 * Controller in index.jsp
 */

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


edmsApp.controller("causeListController",['$scope','$http','Upload', function($scope,$http,Upload) {
	var urlBase="/dms/";
	  //$scope.usingFlash = FileAPI && FileAPI.upload != null;
		$scope.model={};
	  $scope.invalidFiles = [];
	  $scope.checkListObj=[];
	  $scope.model={};
	  $scope.report={};
	  $scope.itr=0;
	  $scope.type_id= $('#type_id').val();
	  $scope.checkListObj=[];
	  $scope.$watch('files', function (files) {
	    $scope.formUpload = false;
	    if (files != null) {
	      if (!angular.isArray(files)) {
	        $timeout(function () {
	          $scope.files = files = [files];
	        });
	        return;
	      }
	      for (var i = 0; i < files.length; i++) {
	        $scope.errorMsg = null;
	        (function (f) {
	          $scope.upload(f, true);
	        })(files[i]);
	      }
	    }
	  });
	$scope.folder={};
	$scope.errorlist=null;
	$scope.picFile='';
    $scope.causeListTypes =[];
    $scope.masterdata=[];
    $scope.displayedCollection = []
    $scope.courtList=[];
    $scope.causelist={};
    $scope.clmodel={};
    $scope.search=false;
    $scope.caseFileList=[{}];
    $scope.entity2 =[];
    $scope.len=0;
    $scope.subDocuments=[];
    $scope.approw=false;
    $scope.showList =false;
    $scope.showSupplementaryList =false;
    $scope.unlistedList =false;
    $scope.causelist_date = convertDate(new Date());
    $scope.filterType="-1";

    
    
    $scope.caseTypes=[];
    getCaseTypes();
    getCauseListTypes();
    getCourtList();
    
    
    $scope.movieArray=[];
    $scope.addcl=[];    
    
    
    $scope.personColour = { 'color': 'blue' };
	 $scope.personColour1 = { 'color': 'red' };
    
	 
	/* $scope.getCcmstodmsFileReport=function(){	
			getccmstodmsReport();
		}*/
					
	 $scope.getCcmstodmsFileReport=function(){	
			
				$http.get(urlBase+'/causelist/ccmstodmsReport',{params: {'cl_dol':$scope.model.cl_dol,'fd_file_source':$scope.model.fd_file_source}}).success(function(data) {
								
					$scope.ccmstodmsdata=data.modelList;
													
					if($scope.ccmstodmsdata.length == 0){
						alert("data is not found");
					}
					/*else{
						alert("data is found");
					}*/

			}).error(function(data, status, headers, config) {
				console.log("Error in getting Data ");
			});	
		};
    
    $scope.addManualCase = function () {
 	   
	  	var response = $http.post(urlBase+'causelist/addManualCase',$scope.causelist);		  	
		response.success(function(data, status, headers, config) {
			
			if(data.response=="TRUE"){
				
		    	alert("Record saved successfully");
		    	$("#addManualCase").modal("hide");
			}
		});
		response.error(function(data, status, headers, config) {
			
			alert("Error occurred while saved record");
		});
	}
    
	function loadMasterData1() {
		var response = $http.get(urlBase+'ecourt/getreportforadditionallists?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData.length);
			
			if($scope.listData.length != 0){
				 $scope.showList =true;
			}
		
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	function loadMasterData4() {
		var response = $http.get(urlBase+'ecourt/getreportforDIAlists?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData.length);
			
			if($scope.listData.length != 0){
				 $scope.showList =true;
			}
		
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	function loadMasterData3() {
		var response = $http.get(urlBase+'ecourt/getreportforcorrectionlists?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData.length);
			
			if($scope.listData.length != 0){
				 $scope.showList =true;
			}
		
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	
	
	function loadMasterData2() {
		var response = $http.get(urlBase+'ecourt/getreportforsupplementarylist?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData.length);
			
if($scope.listData.length != 0){
	$scope.showList =true;
			}
			/*angular.forEach($scope.masterdata, function(object, key) {
				  
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
				});*/
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	
	$("#btnPrint").click(function() 
	{
		
		var printContents = document.getElementById("report_table").innerHTML;
	    var popupWin = window.open('', '_blank', 'width=300,height=300');
	    popupWin.document.open();
	   // popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body onload="window.print()">' + printContents + '</body></html>');
	    
	    var style = "<style>";
        style = style + "table, th, td {border: solid 1px #DDD; border-collapse: collapse;";
        style = style + "padding: 2px 3px;text-align: left;}";
        style = style + "</style>";
	    
        popupWin.document.write('<html><head>');
        //popupWin.document.write('<title></title>');
        popupWin.document.write(style);        
        popupWin.document.write('</head>');
        popupWin.document.write('<body onload="window.print()">');
        //popupWin.document.write('<h6 style="text-align: center;">Stamp Report </h6>');
        popupWin.document.write(printContents);
        popupWin.document.write('</body></html>');
        
	    popupWin.document.close();
	});
	
	
	
	function getProductionLists() {
		var response = $http.get(urlBase+'ecourt/getreportforproductionlist?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData.length);
			
if($scope.listData.length != 0){
	$scope.showList =true;
			}
			
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	
	
	function getFresMentionLists() {
		var response = $http.get(urlBase+'ecourt/getreportforfreshmentionlist?causelist_date='+$scope.causelist_date);
		response.success(function(data, status, headers, config) {		
			$scope.listData= data.modelList;
			console.log("Dataaaaaaaa",$scope.listData.length);
			
if($scope.listData.length != 0){
	$scope.showList =true;
			}
			/*angular.forEach($scope.masterdata, function(object, key) {
				  
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
				});*/
			
		});
		response.error(function(data, status, headers, config) {
			//alert("Error");
		});
		
	};
	
	if($scope.type_id == 7){
		loadMasterData1();
	}
	if($scope.type_id == 1){
		loadMasterData4();
	}
	if($scope.type_id == 2){
		loadMasterData3();
	}
	else if($scope.type_id == 6){
		loadMasterData2();
	}
	else if ($scope.type_id == 5){
		getFresMentionLists();
	}
	
	else if ($scope.type_id == 19){
		getProductionLists();
	}
	
	
	 $scope.viewList =function(id){
		 console.log("idddddd",id);
		 window.open(urlBase+"causelist/type/"+id,"_blank");
		 
		 
	 }
	
	function convertDate(inputFormat) 
	{
		  function pad(s) { return (s < 10) ? '0' + s : s; }
		  var d = new Date(inputFormat);
		  return [ d.getFullYear(), pad(d.getMonth()+1),pad(d.getDate())].join('-');
	}
    $scope.addCaseToCouseList = function (clmodel) {       
    	
    	$scope.appMendotory=false;
    	if(clmodel.cl_list_type_mid==1 || clmodel.cl_list_type_mid==2 ){
    		if($scope.subDocumentsList==undefined && $scope.itr!=0){
	  		$scope.appMendotory=true;
    		}
	  	}
    	
    	 var len=Object.keys($scope.caseFileList).length;
    	  for(var i=0;i<len;i++){
    		  $scope.caseFileList[i].cl_court_no=clmodel.cl_court_no;
    		  $scope.caseFileList[i].cl_dol=clmodel.cl_dol;
    		  $scope.caseFileList[i].cl_list_type_mid=clmodel.cl_list_type_mid;
    		  $scope.caseFileList[i].subDocument=$scope.subDocumentsList;
    	  }
    	  
			  	if($scope.caseFileList==undefined)
			  		{
			  		$scope.entity2 =clmodel;
			  		}
			  	else
			  		{
			  		$scope.entity2= $scope.caseFileList;
			  		}   	
			  	
			  	
			  	
			  	if($scope.appMendotory==true){
			  		alert("Please select Application");
			  	}

			  	else
		  	{
		  	var response = $http.post(urlBase+'causelist/addCaseToCouseList',$scope.entity2);		  	
			response.success(function(data, status, headers, config) {
				$scope.courtList = data.modelList;
				if(data.response=="TRUE"){
					$scope.caseFileList=[{}];
					$scope.clmodel={};
					$scope.subDocuments={};
					$scope.itr=0;
					alert(data.data);
					 getCourtList();
				}else{
					alert(data.data);
				}

			});
			response.error(function(data, status, headers, config) {
				alert("Input field not empty");
			});
		  	}
		}
    
	$scope.checklist = function () 
	{
		$scope.subDocumentsList=[];
  	  	angular.forEach($scope.subDocuments,function(value,index){
  	  		
  	  	if(value.checked == true)
		{
			 $scope.subDocumentsList.push(value);
		}
  		  
        });
	 };
    
    
    $scope.vieapplication = function (row1,clmodel) {
	  	/*$scope.row=$scope.caseFileList[0];*/
    	$scope.row=row1;
	  	if($scope.row==undefined)
	  		{
	  		$scope.entity2 =clmodel;
	  			console.log("view Application");
	  		}
	  	else
	  		{
	  		$scope.entity2 =$scope.row; //angular.extend($scope.row,clmodel);
	  		}
	  	var response = $http.post(urlBase+'causelist/vieapplication',$scope.entity2);
		response.success(function(data, status, headers, config) {
			$scope.subDocuments = data.modelList;
			if(data.response=="TRUE")
			{
				if($scope.subDocuments.length==0)
					{
						alert("No Record found..");
					}
				else
					{
				$scope.approw=true;
					}
				
			}else{
				alert(data.data);
			}

		});
		response.error(function(data, status, headers, config) {
			alert("Error");
		});
	}
    

/*	function getCauseListTypes() {
		var response = $http.get(urlBase+'causelist/getCauseListTypes');
		response.success(function(data, status, headers, config) {
			$scope.causeListTypes = data.modelList;
		});
		response.error(function(data, status, headers, config) {
			alert("Error");
		});

	}*/
    
	$scope.deleteCauseFile=function(index){
		if(index==0 && $scope.itr==1){
			$scope.caseFileList[index]={};
			$scope.itr=0;	
		
	}
		else{
			$scope.caseFileList.splice(index, 1);
			$scope.itr--;
		}
	}
	
	$scope.applicationOnly=function(index,clmodel){
		
		if(clmodel.cl_list_type_mid==1 || clmodel.cl_list_type_mid==2){
			
			if($scope.itr!=0){
			
			for(var i=$scope.itr-1;i>index;i--){
				$scope.caseFileList.splice(i, 1);
			}
			$scope.itr=index+1;
			}	
		}
	}
	
	
	  $scope.searchCaseFiles=function() 
	  {
		  $scope.len=Object.keys($scope.caseFileList).length;
		  var bool=false;
		  console.log($scope.len);
		  for(var i=0;i<$scope.len;i++){
			  var test1=$scope.caseFileList[i];
			  if(test1.fd_case_no==$scope.search.fd_case_no && test1.fd_case_year==$scope.search.fd_case_year){
				  bool=true;
			  }
		  }
		  
		  console.log($scope.caseFileList);
		  
		  if(!bool){
		  $http.post(urlBase+'casefile/getCaseFileLists',$scope.search).success(function (data) {
		    	if(data.response=="TRUE")
		    		{
		    		$scope.caseFileList[$scope.itr]=data.modelData;
		    		$scope.addcl=data.modelList;
		    		console.log("-----------------------------"+$scope.addcl);
		    		console.log($scope.caseFileList);
		    		$scope.itr++;
		    		}
		    	else
		    		alert("No Record Found");
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting tree data");
		      });
	  }
		  console.log($scope.caseFileList.length);
	  }
    
    
    $scope.formats = ['dd-MM-yyyy', 'yyyy-MM-dd', 'shortDate'];
	$scope.format = $scope.formats[0];
    $scope.today = function() {
    	$scope.model.cl_dol = new Date();
    	$scope.clmodel.cl_dol = new Date();
    	$scope.report.cl_date = null;
    	$scope.maxDate = new Date()
	};
	if($scope.type_id!='' && $scope.type_id!=null){
		$scope.showLoader =true;
		$scope.model.cl_list_type_mid=$scope.type_id;
		$scope.model.cl_dol=new Date();
		$scope.maxDate = new Date()
		getData($scope.model);
	}
	$scope.today();
	
	$scope.clear = function () {
		$scope.model.cl_dol = null;
		$scope.report.cl_date=null;
		$scope.clmodel.cl_dol = null;
	};	

	 $scope.open = function($event,opened) {
		    $event.preventDefault();
		    $event.stopPropagation();

		    $scope[opened] = true;
		  };
    	
	$scope.dateOptions = {
	    formatYear: 'yy',
	    startingDay: 1
	};

    

	function getCauseListTypes() {
		var response = $http.get(urlBase+'causelist/getCauseListTypes');
		response.success(function(data, status, headers, config) {
			$scope.causeListTypes = data.modelList;
		});
		response.error(function(data, status, headers, config) {
			alert("Error");
		});

	}
	
	  function getCaseTypes(){
		  $http.get(urlBase+'master/getcasetypes').success(function (data) {
		    		$scope.caseTypes=data.modelList;		    	
		    	
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting casetypes");
		      });
	  }
	  
	$scope.getCaseTypes=function() {
		var response = $http.get(urlBase+'master/getcasetypes');
		response.success(function(data, status, headers, config) {
			$scope.caseTypes = data.modelList;
		});
		response.error(function(data, status, headers, config) {
			alert("Error");
		});

	}
	$scope.getApplicationStages=function() {
		var response = $http.get(urlBase+'master/getapplicationstages');
		response.success(function(data, status, headers, config) {
			$scope.applicationStages = data.modelList;
		});
		response.error(function(data, status, headers, config) {
			alert("Error");
		});

	}
	function getCourtList() {
		var response = $http.get(urlBase+'causelist/getCourtList');
		response.success(function(data, status, headers, config) {
			$scope.courtList = data.modelList;

		});
		response.error(function(data, status, headers, config) {
			alert("Error");
		});
	}
	
	 /*$scope.showLoader =false;*/
		
	
	$scope.getList=function(){	
		
		$scope.search=true;
		 $scope.showLoader =true;
		$http.post(urlBase+'causelist/getCauseListNew',$scope.model)
			.success(function(data) {
				$scope.masterdata=data.modelList;
				 $scope.showLoader =false;
				$scope.displayedCollection = [].concat($scope.masterdata);
			//$scope.SearchList = data.SearchedData;
		}).error(function(data, status, headers, config) {
			 $scope.showLoader =false;
			console.log("Error in getting Cause List Data ");
		});	
	
	}
	
	$scope.filterExpression = function(causeList) {
		if($scope.filterType==="false"){
			return (causeList.fileSource==="O");
		}
		else if($scope.filterType==="true"){
			return (causeList.fileSource==="F");
		}
		else{
			return (causeList);
		}
        
    };
	
	function getData(){	
		$scope.search=true;
		$scope.showLoader =true;
		$http.post(urlBase+'causelist/getCauseLists',$scope.model)
			.success(function(data) {
				$scope.showLoader =false;
				$scope.masterdata=data.modelList;
				$scope.displayedCollection = [].concat($scope.masterdata);
			//$scope.SearchList = data.SearchedData;
		}).error(function(data, status, headers, config) {
			$scope.showLoader =false;
			console.log("Error in getting Cause List Data ");
		});	
	};
	
	function getCauseListReportCourtWise(){
		$scope.totalCount =0;
		$scope.search=true;
		$http.post(urlBase+'causelist/getCauseListReportCourtWise',$scope.model)
			.success(function(data) {
			
				$scope.displayedCollection = data.modelData;
				for(var i =0 ;i< $scope.displayedCollection.length ; i++ ){
					
					
					$scope.totalCount =$scope.totalCount + $scope.displayedCollection[i][2];
				}
			//$scope.SearchList = data.SearchedData;
		}).error(function(data, status, headers, config) {
			console.log("Error in getting Cause List Data ");
		});	
	};
	
$scope.getCauseListPage =function (){
	$scope.totalCount =0;
	$scope.search=true;
	$http.post(urlBase+'/casefile/downloadCauseList',$scope.model)
		.success(function(data) {
		
			/*$scope.displayedCollection = data.modelData;
			for(var i =0 ;i< $scope.displayedCollection.length ; i++ ){
				
				
				$scope.totalCount =$scope.totalCount + $scope.displayedCollection[i][2];
			}*/
		//$scope.SearchList = data.SearchedData;
	}).error(function(data, status, headers, config) {
		console.log("Error in getting Cause List Data ");
	});	
	}
 	


	
	
	$scope.getCauseListReport=function(){	
		getCauseListReportCourtWise();
	}
	
	
	
	$scope.downloadList=function(){	
		
		var  d1 =	convertDate($scope.model.cl_dol);
		
		if(!$scope.model.cl_list_type_mid){
			$scope.model.cl_list_type_mid =null;
		}
		
	if(!$scope.model.cl_court_no){
		$scope.model.cl_court_no=null;
	}
	  
	    window.open(urlBase+'casefile/downloadCauseList/?cl_court_no='+$scope.model.cl_court_no+'&cl_list_type_mid='+$scope.model.cl_list_type_mid+'&cl_dol='+d1,'_self');
	  
	};
    $scope.$watch('files', function (files) {
        $scope.formUpload = false;
        if (files != null) {
          if (!angular.isArray(files)) {
            $timeout(function () {
              $scope.files = files = [files];
            });
            return;
          }
          for (var i = 0; i < files.length; i++) {
            $scope.errorMsg = null;
            (function (f) {
              $scope.upload(f, true);
            })(files[i]);
          }
        }
      });
	$scope.refreshModel=function(){
		$scope.document={};
		$scope.repositories=[];
		$scope.treedata=[];
		$scope.picFile='';
		$scope.errorlist=null;
	};
	$scope.onFileSelect = function ($files) {
        $scope.uploadProgress = 0;
        $scope.selectedFile = $files;
    };
    $scope.files = [];

    //listen for the file selected event
    $scope.$on("fileSelected", function (event, args) {
    	$scope.$apply(function () {            
            $scope.files.push(args.file);
        });
    });
    
    $scope.loading = true;
	$scope.save=function() 
	{
		
		
		 $scope.loading = false;
		  var file=$scope.picFile;
		  if($scope.picFile==''){
			  alert("Please select file");
			  return false;
		  }
		  	//var d = new Date($scope.causelist.cl_date);
		  	//var m=("0" + (d.getMonth() + 1)).slice(-2)
		    //var year=d.getFullYear();
		    //var n = d.getDate()+"-"+m+"-"+year;
		    //$scope.causelist.cl_date=n;
		    file.upload = Upload.upload({
		      url: urlBase + 'causelist/create',
		      headers: {
		    	  'optional-header': 'header-value'
		        },
		        fields:$scope.causelist,
    		   file:file,
    		  
		    });
		   
		    file.upload.then(function (response) {
		        if(response.data.response=="TRUE"){
		        	$scope.errorlist =null;
		        	
		        	alert("Successfully uploaded complete causelist");
		        	$("#causeListCreate").modal("hide");
		        	$scope.loading = true;
		        	//window.location.reload();
		        }else{
		        	alert(response.data.data);
		        	$("#causeListCreate").modal("hide");
		        	$scope.loading = true;
		        }
		      }, function (response) {
		        
		      }, function (evt) {
		        // Math.min is to fix IE which reports 200% sometimes
		        //file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
		      });

		      file.upload.xhr(function (xhr) {
		        // xhr.upload.addEventListener('abort', function(){console.log('abort complete')}, false);
		      });
		}  	  
	$scope.viewCaseFile2=function(data){
		  window.open(urlBase+"casefile/view/"+data,"_blank");
	  }
	
	
	
	
	$scope.viewCaseFile=function(data){
		var nPflag ="N";
		console.log("Cause List ",data);
		  window.open(urlBase+"casefile/viewCauseList/"+data.cl_fd_mid+'?cl_court_no='+data.cl_court_no+'&cl_rec_status='+data.cl_rec_status+'&cl_serial_no='+data.cl_serial_no+'&cl_dol='+data.cl_dol+'&cl_list_type_mid='+data.cl_list_type_mid+'&nPflag='+nPflag,"_blank rel='opener'");
	  }
	
	
	$scope.viewCaseFile1=function(id,$index,doc_type,data){
		console.log(data);
	
		    $scope.personColour = { 'color': 'blue' };
		    doc_type.valueOf()[$index].caseChecked=true;
		    
		
		
		  window.open(urlBase+"casefile/view/"+id,"_blank");
	  }
	
	
	$scope.viewCaseFileExtra=function(id){
		    
		
		
		  window.open(urlBase+"casefile/view/"+id,"_blank");
	  }
	
	
	$scope.viewApplication1=function(application_no,application_year,case_id,$index,doc_type){
		
		 $scope.personColour = { 'color': 'blue' };
		 doc_type.valueOf()[$index].caseChecked=true;
		    
		
		  window.open(urlBase+"casefile/applicationview/?app_no="+application_no+"&app_year="+application_year+"&case_id="+case_id,"_blank");
		 /*  $scope.personColour = { 'color': 'blue' };
		    doc_type.valueOf()[$index].caseChecked=true;*/
	  }
	
	$scope.viewApplication=function(data){
		console.log("Dataaaaaaaaaaaaa",data);
		  window.open(urlBase+"casefile/applicationviewCauseList/?cl_court_no="+data.cl_court_no+'&cl_rec_status='+data.cl_rec_status+'&cl_serial_no='+data.cl_serial_no+'&cl_dol='+data.cl_dol+'&cl_list_type_mid='+data.cl_list_type_mid+'&app_no='+data.cl_ano+'&app_year='+data.cl_ayr+'&case_id='+data.cl_fd_mid,"_blank");
	  }
	
	$scope.updatePriority=function(id){
		var response = $http.get(urlBase+'causelist/updatepriority/'+id);
		response.success(function(data, status, headers, config) {
			if(data.response=="TRUE"){
				alert("Priority updated please refresh the page for updated list");
			}
		});
		response.error(function(data, status, headers, config) {
			alert("Error");
		});
	  }
	$scope.submitReport=function(){
		var confirmbox = confirm("Do you really want to proceed");
	   	 if (confirmbox) 
	   	 {
	   		 angular.forEach($scope.displayedCollection,function(value,index){							 
				if(value.checked == true)
				{
					$scope.Obj=value;
					$scope.Obj.cl_stage_id=$scope.report.cl_stage_id;
					$scope.Obj.cl_date=$scope.report.cl_date;
					$scope.checkListObj.push($scope.Obj);
				}
				
			});
	   		 if($scope.checkListObj.length>0){
	   			$http.post(urlBase+'causelist/updateapplicationstatus',$scope.checkListObj)
				.success(function(data) {
					if(data.response=="TRUE"){
						alert(data.data);
					}else{
						alert(data.data);
					}
					window.location.reload();
				//$scope.SearchList = data.SearchedData;
				}).error(function(data, status, headers, config) {					
				});	
	   		 }else{
	   			 alert('Please select atleast one application');
	   		 }
	   	 }
	}
	$scope.deletecase=function(id){
		  var result=confirm("Are you really want to delete this record");
		  if (result) {
			  $http({
				  method : 'DELETE',
				  url : urlBase + 'causelist/delete/' + id
		   		}).success(function(response) {
		   			alert("Successfully deleted record");
		   			window.location.reload();			
		   		});	
		  }
	  }
	
	$scope.checkAll = function () 
	{		
  	  if ($scope.selectedAll) {
            $scope.selectedAll = true;
        } else {
            $scope.selectedAll = false;
        }
  	  	angular.forEach($scope.displayedCollection,function(value,index){
  		  
  		  value.checked=$scope.selectedAll;
  		 // $scope.bundlelist.splice($scope.bundlelist.indexOf(value), 1);
  		  
        });

	 };
	 $scope.updatecauselist=function(){
			var confirmbox = confirm("Do you really want to proceed");
		   	 if (confirmbox) 
		   	 {
		   		 angular.forEach($scope.displayedCollection,function(value,index){							 
					if(value.checked == true)
					{
						$scope.Obj=value;
						$scope.Obj.cl_new_court_no=$scope.cl_new_court_no;
						$scope.checkListObj.push($scope.Obj);
					}
				});
		   		 if($scope.checkListObj.length>0){
		   			$http.post(urlBase+'causelist/updatecauselist',$scope.checkListObj)
					.success(function(data) {
						if(data.response=="TRUE"){
							alert(data.data);
						}else{
							alert(data.data);
						}
						window.location.reload();
					//$scope.SearchList = data.SearchedData;
					}).error(function(data, status, headers, config) {					
					});	
		   		 }else{
		   			 alert('Please select atleast one record');
		   		 }
		   	 }
		}
	 $scope.deleteall=function(){
			var confirmbox = confirm("Do you really want to proceed");
		   	 if (confirmbox) 
		   	 {
		   		 angular.forEach($scope.displayedCollection,function(value,index){							 
					if(value.checked == true)
					{
						$scope.Obj=value;
						$scope.Obj.cl_new_court_no=$scope.cl_new_court_no;
						$scope.checkListObj.push($scope.Obj);
					}
				});
		   		 if($scope.checkListObj.length>0){
		   			$http.post(urlBase+'causelist/deleteall',$scope.checkListObj)
					.success(function(data) {
						if(data.response=="TRUE"){
							alert(data.data);
						}else{
							alert(data.data);
						}
						window.location.reload();
					//$scope.SearchList = data.SearchedData;
					}).error(function(data, status, headers, config) {					
					});	
		   		 }else{
		   			 alert('Please select atleast one record');
		   		 }
		   	 }
		}
	 $scope.create=function(){	
			$http.post(urlBase+'causelist/addcase',$scope.clmodel)
				.success(function(data) {
					if(data.response=="TRUE"){
						alert(data.data);
					}else{
						alert(data.data);
					}
					$("#caseAdd").modal("hide");
				//$scope.SearchList = data.SearchedData;
			}).error(function(data, status, headers, config) {
				console.log("Error in adding causelist ");
			});	
		};

		

		

		
}]);
