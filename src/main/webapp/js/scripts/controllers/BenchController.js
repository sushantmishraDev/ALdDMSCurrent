var EDMSApp = angular.module("EDMSApp", ['ngFileUpload','ngMask','ui.bootstrap']);


EDMSApp.directive ("select2", function ($timeout, $parse) {
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




EDMSApp.controller('BenchController',['$scope','$http','Upload',function ($scope, $http,Upload) {
	  var urlBase="/dms/";
	  $scope.picFile='';
	  $scope.caseTypes=[];
	  $scope.search={};
	  $scope.subdocument={};
	  $scope.offRep={};
	  $scope.dmsCaseData={};
	  getCaseListByUser();
	  getCaseTypes();
	  getIndexFields();
	  getMasterdata();
	  $scope.courtList =[];
	  $scope.causeListTypes =[];
	  
	  $scope.court={};
	  
	  $scope.editablerow = '';
	  
	  
	  $scope.updateBench = function (indx,courtmaster) {
		  console.log("Court Master",courtmaster);
		  $http.post(urlBase+'bench/updateCourt',courtmaster).success(function (data) {
		    	if(data.response=="TRUE"){
		    		console.log("data daaaaaa",data);
		    		 $scope.courtList[indx] = angular.copy(data.modelData);
		        $scope.reset();  
		    	}
		    	else if(data.response == "FALSE") {
		    		console.log("Dataaaaaaaaaaa",data);
		    		/*$scope.courtmaster1 =data.modelData;*/
		    		bootbox.confirm({
		    		    title: "Please Read The Content",
		    		    message: "The Bench Id - "+data.modelData.cm_bench_id+ " you want to update is already exists in "+data.modelData.cm_name
		    		            +" if you want to update it than please click confirm else click cancel",
		    		    buttons: {
		    		        cancel: {
		    		            label: '<i class="fa fa-times"></i> Cancel'
		    		            	
		    		        },
		    		        confirm: {
		    		            label: '<i class="fa fa-check"></i> Confirm'
		    		        }
		    		    },
		    		    callback: function (result) {
		    		        console.log('This was logged in the callback: ' + result);
		    		        if(result){
		    		        	courtmaster.updateFlag =true;
		    		        	 $http.post(urlBase+'bench/updateCourt',courtmaster).success(function (data) {
		    		 		    	if(data.response=="TRUE"){
		    		 		    		console.log("data daaaaaa",data);
		    		 		    		/* $scope.courtList[indx] = angular.copy(data.modelData);*/
		    		 		    		 $scope.reset();  
		    		 		    		getAllCourts();
		    		 		       
		    		 		    	}
		    		        	 }).
		    				      error(function(data, status, headers, config) {
		    				      	console.log("Error in getting tree data");
		    				      });
		    		        	
		    		        	
		    		        }
		    		        else {
		    		        	getAllCourts();
		    		        }
		    		    }
		    		});
		    	}
		    	else
		    		{
		    		console.log("Some problem")
		    		}
		    		
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting tree data");
		      });
		  
		        $scope.courtList[indx] = angular.copy($scope.editablerow);
		        $scope.reset();
	    };
	    $scope.reset = function(){
	    	$scope.editablerow = [];
	    	}
	  
	  $scope.editBench = function(content) { 
		  console.log("dataaaaaaaaaaaaaaaa",content);
	        $scope.editablerow = angular.copy(content);   
		
	 }
	  
	  $scope.supplimentryAll = function() { 
		  console.log("dataaaaaaaaaaaaaaaa",content);
		  $scope.court=content;
		  $scope.showLoader =true;
		  
		  var response = $http.post(urlBase+'bench/supplimentryCourtAll');		 
			response.success(function(data, status, headers, config) {					
				if(data.response=="FALSE"){					
					alert("Kuch Nhi Utha");
				}
				
				if(data.response=="TRUE"){					
					alert("CHAL GAYI");
				}
				 $scope.showLoader =false;
				
		});
		response.error(function(data, status, headers, config) {
			alert( "Error");
		});
	          
		
	 }
	  
	  $scope.supplimentryToday = function(content) { 
		  console.log("dataaaaaaaaaaaaaaaa",content);
		  $scope.court=content;
		  $scope.showLoader =true;
		  $('#next_Modal').modal('hide');
		  
		  var response = $http.post(urlBase+'bench/supplimentryCourt',$scope.court);		 
			response.success(function(data, status, headers, config) {					
				if(data.response=="FALSE"){					
					alert("Kuch Nhi Utha");
				}
				
				if(data.response=="TRUE"){					
					alert("CHAL GAYI");
				}
				 $scope.showLoader =false;
				
		});
		response.error(function(data, status, headers, config) {
			alert( "Error");
		});
	          
		
	 }
	  
	  $scope.transferToday = function(content) { 
		  console.log("dataaaaaaaaaaaaaaaa",content);
		  $scope.court=content;
		  $scope.showLoader =true;
		  $('#next_Modal').modal('hide');
		  
		  var response = $http.post(urlBase+'bench/transferCourt',$scope.court);		 
			response.success(function(data, status, headers, config) {					
				if(data.response=="FALSE"){					
					alert("Kuch Nhi Utha");
				}
				
				if(data.response=="TRUE"){					
					alert("ho gaya transfer");
				}
				 $scope.showLoader =false;
				
		});
		response.error(function(data, status, headers, config) {
			alert( "Error");
		});
	          
		
	 }
	  
	  $scope.nextCause = function(masterentity) {
	    	console.log(masterentity);
	    	
	    	 
			  console.log("dataaaaaaaaaaaaaaaa",content);
			  $scope.court=masterentity;
			  $scope.showLoader =true;
			  $('#next_Modal').modal('hide');
			  
			  var response = $http.post(urlBase+'bench/nextDayGen',$scope.court);		 
				response.success(function(data, status, headers, config) {					
					if(data.response=="FALSE"){					
						alert("Kuch Nhi Utha");
					}
					
					if(data.response=="TRUE"){					
						alert("CHAL GAYI");
					}
					 $scope.showLoader =false;
					
			});
			response.error(function(data, status, headers, config) {
				alert( "Error");
			});
		          
			
		 
	    }
	  
	  $scope.nextTrans = function(masterentity) {
	    	console.log(masterentity);
	    	
	    	 
			  console.log("dataaaaaaaaaaaaaaaa",content);
			  $scope.court=masterentity;
			  $scope.showLoader =true;
			  $('#next_Modal').modal('hide');
			  
			  var response = $http.post(urlBase+'bench/nextTrans',$scope.court);		 
				response.success(function(data, status, headers, config) {					
					if(data.response=="FALSE"){					
						alert("Kuch Nhi Utha");
					}
					
					if(data.response=="TRUE"){					
						alert("CHAL GAYI");
					}
					 $scope.showLoader =false;
					
			});
			response.error(function(data, status, headers, config) {
				alert( "Error");
			});
		          
			
		 
	    }
	  
	  $scope.nextCorrection = function(masterentity) {
	    	console.log(masterentity);
	    	
	    	 
			  console.log("dataaaaaaaaaaaaaaaa",content);
			  $scope.court=masterentity;
			  $scope.showLoader =true;
			  
			  $('#next_Modal').modal('hide');
			  
			  var response = $http.post(urlBase+'bench/nextCorrection',$scope.court);		 
				response.success(function(data, status, headers, config) {					
					if(data.response=="FALSE"){					
						alert("Kuch Nhi Utha");
					}
					
					if(data.response=="TRUE"){					
						alert("CHAL GAYI");
					}
					 $scope.showLoader =false;
					
			});
			response.error(function(data, status, headers, config) {
				alert( "Error");
			});
		          
			
		 
	    }
	  
	  
	  
	  $scope.correctionIaToday = function(content) { 
		  console.log("dataaaaaaaaaaaaaaaa",content);
		  $scope.court=content;
		  $scope.showLoader =true;
		  
		  var response = $http.post(urlBase+'bench/correctionCourt',$scope.court);		 
			response.success(function(data, status, headers, config) {					
				if(data.response=="FALSE"){					
					alert("Kuch Nhi Utha");
				}
				
				if(data.response=="TRUE"){					
					alert("CHAL GAYI");
				}
				 $scope.showLoader =false;
				
		});
		response.error(function(data, status, headers, config) {
			alert( "Error");
		});
	          
		
	 }
	  
	  $scope.getData = function(content) { 
		    if (content.cm_id == $scope.editablerow.cm_id) return 'edit';
		        else return 'view';
		 }
	  
	  
	  function getAllCourts(){
		  $http.get(urlBase+'bench/getAllCourts').success(function (data) {
		    		$scope.courtList=data.modelList;	
		    		console.log("dataaaaaaaaaaaaaaaaa",$scope.courtList);
		    	
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting casetypes");
		      });
	  }
	  
	  $scope.setMasterdata = function(masterentity) {
			$scope.masterentity = angular.copy(masterentity);
		};
		
		$scope.tansFlag=false;
		  $scope.setMasterdataTrans = function(masterentity) {
				$scope.masterentity = angular.copy(masterentity);
				$scope.masterentity.clDate=new Date();
				$scope.tansFlag=true;
				 getCauseListTypes("trans");
			};
			
			$scope.suppFlag=false;
			  $scope.setMasterdataSupp = function(masterentity) {
					$scope.masterentity = angular.copy(masterentity);
					$scope.masterentity.clDate=new Date();
					$scope.suppFlag=true;
					 getCauseListTypes("supp");
				};
				
				function getCauseListTypes(type) {
					var response = $http.post(urlBase+'bench/getCauseListTypes',type);
					response.success(function(data, status, headers, config) {
						$scope.causeListTypes = data.modelList;
					});
					console.log($scope.causeListTypes);
					response.error(function(data, status, headers, config) {
						alert("Error");
					});

				}
				
				
		
		
	  
	  getAllCourts();
	  
	  
	    $scope.court_create = function(court) {
	    	
	    		var response = $http.post(urlBase+'bench/create',$scope.court);		 
					response.success(function(data, status, headers, config) {					
						if(data.response=="FALSE"){					
							alert(data.data);
						}else{		
							$('#user_Modal').modal('hide');		
							
							alert(data.data);
											
						}
						
				});
				response.error(function(data, status, headers, config) {
					alert( "Error");
				});
	    	
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

	  
	  function getCaseTypes(){
		  $http.get(urlBase+'master/getcasetypes').success(function (data) {
		    		$scope.caseTypes=data.modelList;		    	
		    	
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting casetypes");
		      });
	  }
	  
	 
	  
	  function getIndexFields(){
		  $http.get(urlBase+'master/getindexfields').success(function (data) {
		    		$scope.index_fields=data.modelList;		    	
		    	
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting indexfields");
		      });
	  }
	  $scope.getApplications=function(){
		  $http.get(urlBase+'master/getapplications/'+$scope.subdocument.if_id).success(function (data) {
	    		$scope.applications=data.modelList;		    	
	    	
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting indexfields");
	      });
	  }
	  $scope.setModel=function(casefile){
		  $scope.casefile=casefile;		  
		  $scope.sd_submitted_date='';
		  $scope.subdocument={};
		  $scope.picFile='';
		  console.log($scope.casefile);
	  }
	  $scope.searchCaseFiles=function()
	  {
		  $scope.caseFileList=[];
		  $http.post(urlBase+'casefile/getCaseFileList',$scope.search).success(function (data) {
		    	if(data.response=="TRUE")
		    		$scope.caseFileList=data.modelList;		    	
		    	else
		    		$scope.caseFileList=[];
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting tree data");
		      });
		  
		 /* $scope.caseFileList=[];
		  $http.post(urlBase+'casefile/getCaseFileList',$scope.search).success(function (data) {
		    	if(data.response=="TRUE")
		    		{
		    		$scope.caseFileList=data.modelList;
		    		
		    		$scope.dmsCaseData=$scope.caseFileList[0];
		    		$scope.dmsCaseData.caseType.ct_label=$scope.search.fd_case_type;
		    	 	for (var i = 0; i < $scope.dmsCaseData.petitioners.length; i++) {
		        		if($scope.dmsCaseData.petitioners[i].pt_sequence==1)
		        			{
		        			$scope.dmsCaseData.first_petitioner=$scope.dmsCaseData.petitioners[i].pt_name;
		        			console.log("$scope.dmsCaseData.first_petitioner---"+$scope.dmsCaseData.first_petitioner);
		        			}
		        	};
		        	for (var i = 0; i < $scope.dmsCaseData.respondents.length; i++) {
		        		if($scope.dmsCaseData.respondents[i].rt_sequence==1)
		        			{
		        			$scope.dmsCaseData.first_respondent=$scope.dmsCaseData.respondents[i].rt_name;
		        			console.log("$scope.dmsCaseData.first_respondent---"+$scope.dmsCaseData.first_respondent);
		        			}
		        	};
		    		
		    		}
		    	else
		    		{
		    		$scope.caseFileList=[];
		    		$scope.dmsCaseData=$scope.caseFileList[0];
		    		}
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting tree data");
		      });*/
		  
		  
		 
		  
	  }
	  
	  $scope.loading = true;
		 $scope.addCaseEfling=function() 
			{
			 $scope.loading = false;
			 var response = $http.post(urlBase+'casefile/addCaseEfling',$scope.dmsCaseData);
			 response.success(function(data, status, headers, config){
				 
				   if(data.response=="TRUE"){
					   $scope.dmsCaseData=data.data;
			        	alert(data.data);
			        	$("#addcaseefiling").modal("hide");
			        	$scope.loading = true;
				   }
				   else{					   
					   alert("error ocurred  please check details");
			        	$("#addcaseefiling").modal("hide");
			        	$scope.loading = true;
				   }  
			 });
			 
			}
	  
		function getCaseListByUser(){
			  $http.get(urlBase+'casefile/getCaseListByUser').success(function (data) {
			    		$scope.caseFileList=data.modelList;		    	
			    	console.log($scope.caseList);
			      }).
			      error(function(data, status, headers, config) {
			      	console.log("Error in getting casetypes");
			      });
		  }
	  
	  
	  $scope.ord_remark="";
	  $scope.loading = true;
	  $scope.cancel= function()
	  {
		  $scope.loading = true;
	  }
	  $scope.save=function() 
	  {
		  $scope.loading = false;
		  $scope.newString = $("#txtEditor").Editor("getText");
		/*$scope.subdocument.ord_remark=$scope.newString.replace("&nbsp;"," ");*/
		
		  $scope.subdocument.ord_remark=$scope.newString.replace(/&nbsp;/g, " ");
		
		  /*$scope.subdocument.ord_remark=$("#txtEditor").Editor("getText");*/
		  console.log($scope.TemplateDescription);
		 $scope.subdocument.sd_fd_mid=$scope.casefile.fd_id;
		  if($scope.sd_submitted_date!=null){
			  $scope.sd_submitted_date=convertDate($scope.sd_submitted_date);
			}
		  $scope.subdocument.sd_submitted_date=$scope.sd_submitted_date;
		  
		  
		 if($scope.subdocument.if_id!=43 ){ 
		 if($scope.subdocument.if_id==null ||$scope.subdocument.at_id== null){
			
			 alert("Please select required fields");
			 $scope.loading = true;
			 return false;
			
		 }
		 }
		 
		    var file=$scope.picFile;
			  if($scope.subdocument.ord_remark!='' && file==""){
				  addReportData();
				  $scope.loading = true;
			  }
			  if(file!="")
			  {				  
			    file.upload = Upload.upload({
			      url: urlBase + 'casefile/uploadJudgement',
			      headers: {
			    	  'optional-header': 'header-value'
			        },
	    		   file:file,
	    		   fields:$scope.subdocument,
			    });

			    file.upload.then(function (response) {
			        if(response.data.response=="TRUE"){
			        	$scope.errorlist =null;
			        	alert("Successfully uploaded document");
			        	$("#uploadDocument").modal("hide");
			        	//window.location.reload();
			        	$scope.loading = true;
			        	$scope.picFile='';
			        	$scope.if_id='';
			        	$scope.ord_remark='';
			        	
			        }else{
			        	$scope.errorlist = response.data.dataMapList;
			        	//$scope.loading = true;
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
			 
			}
	  $scope.uploadNotice=function() 
	  {
		  
		  
		  
		  
		
		    var file=$scope.picFile;
		    $scope.subdocument={};
			  if(file!="")
			  {				  
			    file.upload = Upload.upload({
			      url: urlBase + 'notice/uploadNotice',
			      headers: {
			    	  'optional-header': 'header-value'
			        },
	    		   file:file,
	    		   fields:$scope.subdocument,
			    });

			    file.upload.then(function (response) {
			        if(response.data.response=="TRUE"){
			        	$scope.errorlist =null;
			        	alert("Successfully uploaded document");
			        	$("#uploadDocument").modal("hide");
			        	//window.location.reload();
			        	$scope.loading = true;
			        	$scope.picFile='';
			        	$scope.if_id='';
			        	$scope.ord_remark='';
			        	
			        }else{
			        	$scope.errorlist = response.data.dataMapList;
			        	//$scope.loading = true;
			        }
			      }, function (response) {
			    	  console.log("responseeeeeeeeeeeeeeeeeeeee",response);
			        
			      }, function (evt) {
			        // Math.min is to fix IE which reports 200% sometimes
			        //file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
			      });

			      file.upload.xhr(function (xhr) {
			        // xhr.upload.addEventListener('abort', function(){console.log('abort complete')}, false);
			      });
			  }
			 
			}
	  function addReportData(){
		  $scope.subdocument.sd_submitted_date=$scope.sd_submitted_date;
		  $http.post(urlBase+'casefile/addreportdata?at_id='+$scope.subdocument.at_id
				  +"&sd_fd_mid="+$scope.subdocument.sd_fd_mid
				  +"&ord_remark="+$scope.subdocument.ord_remark
				  +"&sd_submitted_date="+$scope.subdocument.sd_submitted_date
				  +"&ord_consignment_no="+$scope.subdocument.ord_consignment_no
			  )
			  .success(function (data) {
			    	if(data.response=="TRUE")
			    		alert("Successfully added order report data");		    	
			    	else
			    		alert("Error occurred while adding order report data");
			    	
			    	$("#uploadDocument").modal("hide");
			      }).
			      error(function(data, status, headers, config) {
			      	console.log("Error in getting tree data");
			      });
	  }
	  $scope.downloadFiles=function(id){
			window.open(urlBase+"casefile/downloadlist/"+id,"_self");
		}
	  $scope.viewCaseFile=function(id){
		  window.open(urlBase+"casefile/view/"+id,"_self");
	  }
	  $scope.viewDetail=function(id){
		  window.open(urlBase+"casefile/viewdetail/"+id,"_self");
	  }
	  $scope.getSubDocuments=function(doc_id){
		  $scope.doc_id=doc_id;
		  $http.get(urlBase+'casefile/getsubdocuments/'+$scope.doc_id).success(function (data) {
		    	$scope.subDocuments=data.modelList;
		  }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
	  }
	  
	  $scope.deleteSubDocument=function(id){
		  var result=confirm("Are you really want to Delete file");
		  if (result) {
			  $http({
				  method : 'DELETE',
				  url : urlBase + 'casefile/deletesubdocument/' + id
		   		}).success(function(response) {
		   			alert("Successfully deleted record");
		   			$scope.getSubDocuments($scope.doc_id);			
		   		});	
		  }
	  }
	  $scope.loading=true;
	  $scope.updateCasetype=function(){
		 
		  $scope.loading=false;
		  
		  if($scope.new_case_type==$scope.casefile.fd_case_type){
			  $scope.loading=true;
			  alert("Existing and new case type is same");
			  return false;
		  }
		  $http.post(urlBase+'casefile/updatecasetype?fd_id='+$scope.casefile.fd_id
				  +"&new_case_type="+$scope.new_case_type
				  +"&new_case_no="+$scope.new_case_no
				  +"&new_case_year="+$scope.new_case_year
				  
			  )
			  .success(function (data) {
			    	if(data.response=="TRUE"){
			    		$scope.loading=true;
			    		alert("Successfully updated case type information");
			    	}
			    	else
			    		{
			    		$scope.loading=true;
			    		alert("Error occurred while updating case type information");
			    		}
			    	
			    	$scope.new_case_type='';
			    	$scope.new_case_no='';
			    	$scope.new_case_year='';
			    	
			    	$("#updateCaseType").modal("hide");
			      }).
			      error(function(data, status, headers, config) {
			    	  $scope.loading=true;
			      	console.log("Error in getting tree data");
			      });
	  }
	  

	  $scope.caseAssignTo=function(){
	 		  $scope.loading=false;
	 		 var userid = $scope.um_id; 
	 		  console.log("um_id---"+$scope.um_id);
	 		  if(typeof userid==="undefined"){
	 			  $scope.loading=true;
	 			  alert("Please Select User");
	 			  return false;
	 		  }
	 		  $http.post(urlBase+'casefile/caseAssignTo?fd_id='+$scope.casefile.fd_id
	 				  +"&fd_assign_to="+$scope.um_id
	 			  ).success(function (data) {
	 			    	if(data.response=="TRUE"){
	 			    		$scope.loading=true;
	 			    		alert("Successfully Assigned");
	 			    	}
	 			    	else
	 			    		{
	 			    		$scope.loading=true;
	 			    		alert("Error occurred while case assigning");
	 			    		}
	 			    	
	 			    	$scope.new_case_type='';
	 			    	$("#caseAssignTo").modal("hide");
	 			      }).error(function(data, status, headers, config) {
	 			    	  $scope.loading=true;
	 			      	console.log("Error in getting tree data");
	 			      });
	 	  }
	  $scope.validate = true;
	  $scope.offrepohide=false;
	  $scope.hideOther=function(){
		  if($scope.offRep.off_rep==1)
			  {
			  $scope.offrepohide=true;
			  /*$scope.subdocument.ord_consignment_no = "";
			  $scope.subdocument.ord_consignment_no = false;*/
			 
			  }
		  else
			  {
			  $scope.offrepohide=false;
			  /*$scope.subdocument.ord_consignment_no = true;*/
			  }
		  
	  }
	  
	  
	  function getMasterdata() 
		{
			$http.get(urlBase+'user/getallusers').success(function (data) {
	            	$scope.masterdata = data; 
	            	$scope.displayedCollection = [].concat($scope.masterdata);
	            }).
	            error(function(data, status, headers, config) {
	            	console.log("Error in getting User data");
	            });
		}
	  
	  
	  $scope.updateremark=function(data,index){
			debugger;
			console.log("index dataaaaaaaaaaaaaaa",index);
			console.log("index dataaaaaaaaaaaaaaa",data);
			$scope.updateData= data;
			$scope.orderIndex =index;
			console.log("xxxxxxxxxxxxxxxxx",$scope.updateData);
			var x = document.getElementsByClassName("Editor-editor");
	
			console.log("object of editor",x);
			
			$("#updateremar").modal("show");
			x[1].innerHTML = data.ord_remark;
			};

	  //Pankaj
	  $scope.viewCaseedit=function(id){
			debugger;
			var response = $http.get(urlBase+'casefile/getByOfficeedit/'+id);
			response.success(function(data, status, headers, config) {
				console.log(data);
				
				$scope.dataArray=[];
				for(let i =0 ; i< data.offcrpt.length ;i++){
					var officeReportdata1={};
					
					console.log("data in LLLLLLLLLLLLLLL",data.offcrpt.length);
					officeReportdata1.ord_id =data.offcrpt[i].ord_id;
					
					officeReportdata1.ct_name =data.offcrpt[i].caseFileDetail.caseType.ct_label;
					officeReportdata1.ord_created =data.offcrpt[i].ord_created;
					//
					officeReportdata1.fd_case_no =data.offcrpt[i].caseFileDetail.fd_case_no;
					if (data.offcrpt[i].subDocument != null) {
						//handoverdata1.sd_id =data.modelData[i].subDocument.sd_id;
						officeReportdata1.sd_document_name =data.offcrpt[i].subDocument.sd_document_name;
					} 
					//
					officeReportdata1.ord_sd_mid =data.offcrpt[i].ord_sd_mid;
					officeReportdata1.ord_mod_by =data.offcrpt[i].ord_mod_by;
					officeReportdata1.ord_mod_date =data.offcrpt[i].ord_mod_date;
					officeReportdata1.ord_rec_status =data.offcrpt[i].ord_rec_status;
					officeReportdata1.ord_fd_mid =data.offcrpt[i].ord_fd_mid;
					officeReportdata1.ord_created =data.offcrpt[i].ord_created;
					officeReportdata1.ord_consignment_no =data.offcrpt[i].ord_consignment_no;
					officeReportdata1.ord_submitted_date =data.offcrpt[i].ord_submitted_date;
					officeReportdata1.um_fullname =data.offcrpt[i].user.um_fullname;
					
					
					officeReportdata1.ord_remark =$sce.trustAsHtml(data.offcrpt[i].ord_remark);
					console.log("data in LLLLpppppppppppppppLLLLLLLLLLL",officeReportdata1);
					$scope.dataArray.push(officeReportdata1);
					console.log("data in LLLLpppppppppppppppLLLLLLLLLLL11111111",$scope.dataArray);
				}
				
			});
			response.error(function(data, status, headers, config) {
			});

			};
			
	
			
			  //
			  
			//  function update(){
			  $scope.update=function(){
				  $scope.loading = false;
				  $scope.newString = $("#txtEditor1").Editor("getText");			
				  	console.log("updated data",$scope.updateData);
		
					$scope.subdocument = {
							'ord_id' : $scope.updateData.ord_id,
							'ord_remark':$scope.newString.replace(/&nbsp;/g, " "),
							'ord_created': $scope.updateData.ord_created,
							'ord_fd_mid': $scope.updateData.ord_fd_mid,
							'ord_submitted_date': $scope.updateData.ord_submitted_date,
							'ord_mod_by': $scope.updateData.ord_mod_by,
							'ord_created_by': $scope.updateData.ord_created_by,
							'ord_sd_mid': $scope.updateData.ord_sd_mid,
							'ord_rec_status': $scope.updateData.ord_rec_status,
							'ord_mod_date': $scope.updateData.ord_mod_date,
							'ord_consignment_no': $scope.updateData.ord_consignment_no
	
						};
	
				  $http.post(urlBase+'casefile/addReportupdate',$scope.subdocument)
					  .success(function (data) {
						  
						  console.log("data of repert",data);
						  
						  
					    	if(data.response=="TRUE"){
					    		console.log("yyyyyyyyyyyyyyyyyyyyyyyyy",$scope.dataArray[$scope.orderIndex]);
					    		//console.log("yyyyyyyyyyyyyyyyyyyyyyyyy",$scope.handoverdataArray[$scope.orderIndex].ord_remark);
					    		$scope.dataArray[$scope.orderIndex].ord_remark=$sce.trustAsHtml(data.modelData.ord_remark)
					    		alert("Successfully added order report data"),
					    		
					    	$("#updateremar").modal("hide");
					    	}
					    	else  if (data.response=="Time_over") {
					    		alert("Today is on the case caust list");
							} else if (data.response=="Time") {
								alert("Time Over");
							}else 
					    		alert("Error occurred while adding order report data");
					    	
					    	$("#updateremar").modal("hide");
					      }).
					      error(function(data, status, headers, config) {
					      	console.log("Error in getting tree data");
					      });
				 
			  }


				$scope.Getfileview=function(data)

				{
				filename=data.sd_document_name;
				ord_sd_mid=data.ord_sd_mid;
				$http.get(urlBase+'casefile/Getfileview',{params: {'sd_document_name':filename,'ord_sd_mid':ord_sd_mid}})
					.success(function(data){

						
					//$scope.fileurl=urlBase+"uploads/"+filename+".pdf";
					//$scope.title=filename;
					//$('#casefile_Modal').modal('show');
						window.open(urlBase+"uploads/"+filename+".pdf",'_blank');
					console.log($scope.fileurl);


				}).error(function(data, status, headers, config) {
				console.log("Error in getting Report ");
				});	
				//}
				};	
				
				
				$scope.deleteremark=function(data)

				{
				ord_id=data.ord_id;
				$http.get(urlBase+'casefile/deleteremark',{params: {'ord_id':ord_id}})
					.success(function(data){

						
							if (data.response=="TRUE") {
								alert("Successfully deleted record");
					   			window.location.reload();	
								} else {
									alert("Data Not Delete")
								}


				}).error(function(data, status, headers, config) {
				console.log("Error in getting Report ");
				});	
				//}
				};	
				
				//========================== Vijay chaurasiya ==================//
				
				$scope.isMoving = false;

				$scope.moveApplication = function () {

				    if ($scope.isMoving) return; // prevent double click

				    $scope.isMoving = true;
				    console.log("======== move application =======");

					$http.post(urlBase + 'application/moveApplications')
					.then(function (response) {

					    $scope.isMoving = false;

					    let data = response.data;

					    if (data.success > 0) {
					        alert("Success: " + data.success);
					    } else {
					        alert("No applications moved (Total: " + data.total + ")");
					    }

					})
					.catch(function (error) {
					    $scope.isMoving = false;
					    alert("Error: " + error.status);
					});
				};
				
				
				
				
				
				
				
				
				
				
				
				
				
			
	  //
	  /*END*/  
	  
	  

	  
}]);