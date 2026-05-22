var EDMSApp = angular.module("EDMSApp", ['ngFileUpload','ngMask','ui.bootstrap']);

//EDMSApp.controller('CaseFileController',['$scope','$http','$sce','Upload',function ($scope, $http,$sce,Upload) {
	
	EDMSApp.controller('CaseFileController',['$scope','$http','$sce','Upload','$q',function ($scope, $http,$sce,Upload,$q) {
	
	
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
		
		
		$scope.subapplications = [];
		
		$scope.addNewColumn = function() {
			$scope.counter++;

			var newItemNo = $scope.subapplications.length + 1;
			$scope.subapplications.push({
				'sb_ap_sd_mid' : $scope.casefile.subDocument.sd_id,
				'sb_ap_fd_mid' : $scope.casefile.fd_id,
				'sb_ap_year' : new Date().getFullYear(),
				'sb_ap_rec_status':1
			});

		};
		
		function convertDate(inputFormat) 
		{
			  function pad(s) { return (s < 10) ? '0' + s : s; }
			  var d = new Date(inputFormat);
			  return [ d.getFullYear(), pad(d.getMonth()+1),pad(d.getDate())].join('-');
		}
		
		$scope.searchreport = function(id) {                                                                     

			 $http.get(urlBase+'casefile/getReportData/'+id).success(function (data) {
			
				 $scope.officeReport =data.modelList;
				 
				 for(let i =0 ; i< $scope.officeReport.length ;i++){
					 $scope.officeReport[i].ord_remark =$sce.trustAsHtml(data.modelList[i].ord_remark);
				 }
					

				}).error(function(data, status, headers, config) {
					console.log("Error in getting ProductivityReportData ");
				});

			};
			
			$scope.setOfficeRpt=function(officeRpt){
				  $scope.officeRpt=officeRpt;	
				  $scope.TemplateDescription=$scope.officeRpt.ord_remark;
				  console.log($scope.officeRpt);
				  $("#txtEditor1").Editor("setText", $sce.getTrustedHtml($scope.officeRpt.ord_remark));
				  console.log($("#txtEditor").Editor("getText"));
			  }
			
			
			$scope.updateOfficeRpt=function(officeRpt){
				$scope.loading = false;
				
				  $scope.newString = $("#txtEditor1").Editor("getText");
				 // $scope.newString = $scope.newString.replace(/&amp;/g, "&");
				  
				  officeRpt.ord_remark=$scope.newString;
				  
				  console.log($scope.newString);
				/*var response =$http.post(urlBase+'casefile/updatereportdata',officeRpt);
				response.success(function(data, status, headers, config){
					   if(data.response=="Update"){
						   
						alert("Office Report Updated Successfully!");
						$scope.registerCase=data.modelData;
						$("#updateOfficeRpt").modal("hide");
					   }
					   else if(data.response=="Not Allowed") {
						   alert("Not Allowed!");
						   
					   }
					   else if(data.response=="Listed"){
						   alert("Listed In Court");
					   }
					   else {
						   alert("Some Problem while uploading");
						   
					   }
				});	*/
				
				var file=$scope.picFile3;
				 console.log("File Found",file);
				  
				  if(file)
				  {	
					  console.log("Fileeeeeeeeee exists ",file);
					  $http({
						    method: 'POST',
						    url:  urlBase + 'casefile/updatereportdatafile',
						    headers: {'Content-Type': undefined },
						    transformRequest: function (data) {
						        var formData = new FormData();

						        formData.append('officeRpt', new Blob([angular.toJson(officeRpt)], {
						            type: "application/json"
						        }));
						        formData.append("file", data.file);
						        return formData;
						    },
						    data: {subDocument: $scope.subdocument, file: file }

						}).
						success(function (data, status, headers, config) {
							if(data.response=="Update"){
								   
								alert("Office Report Updated Successfully!");
								$scope.registerCase=data.modelData;
								$("#updateOfficeRpt").modal("hide");
								$scope.picFile3 =null;
							   }
							   else if(data.response=="Not Allowed") {
								   $scope.picFile3 =null;
								   alert("Not Allowed!");
								   
							   }
							   else if(data.response=="Listed"){
								   $scope.picFile3 =null;
								   alert("Listed In Court");
							   }
							   else {
								   $scope.picFile3 =null;
								   alert("Some Problem while uploading");
								   
							   }
							
						}).
					  error(function(data, status, headers, config) {
					      	console.log("Error in getting casetypes");
					      	$scope.picFile3 =null;
					      });
				  }
				  else {
					 
					  console.log("File not Found",file);
					  var response =$http.post(urlBase+'casefile/updatereportdata',officeRpt);
						response.success(function(data, status, headers, config){
							   if(data.response=="Update"){
								   
								alert("Office Report Updated Successfully!");
								$scope.registerCase=data.modelData;
								$("#updateOfficeRpt").modal("hide");
							   }
							   else if(data.response=="Not Allowed") {
								   alert("Not Allowed!");
								   
							   }
							   else if(data.response=="Listed"){
								   alert("Listed In Court");
							   }
							   else {
								   alert("Some Problem while uploading");
								   
							   }
						});	
					  
				  }
				
	
			}

	  
	  function getCaseTypes(){
		  $http.get(urlBase+'master/getcasetypes').success(function (data) {
			  for (var i = 0; i < data.modelList.length; i++) {
    			  data.modelList[i].labelandname ="";
    			  data.modelList[i].labelandname = data.modelList[i].ct_label.concat("-",data.modelList[i].ct_name);
    			
    		  }
			
		    		$scope.caseTypes=data.modelList;
		    		
		    		 /* for (var i = 0; i < $scope.caseTypes.length; i++) {
		    			  $scope.caseTypes[i].labelandname ="";
		    			  $scope.caseTypes[i].labelandname = caseTypes[i].ct_label.concat("-",caseTypes[i].ct_label);
		    			  console.log("aaaaaaaaaaaaaaaaaaaaaaaa",$scope.caseTypes[i].labelandname);
		    		  }*/
		    			  
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting casetypes");
		      });
	  }
	  
	  function getCaseTypesForChangeCaseType(){
		  $http.get(urlBase+'master/getcasetypesforchange').success(function (data) {
			  $scope.caseTypesForChange;
			  for (var i = 0; i < data.modelList.length; i++) {
    			  data.modelList[i].labelandname ="";
    			  data.modelList[i].labelandname = data.modelList[i].ct_label.concat("-",data.modelList[i].ct_name);
    			
    		  }
			
			  $scope.caseTypesForChange=data.modelList;
		    		
		    		 /* for (var i = 0; i < $scope.caseTypes.length; i++) {
		    			  $scope.caseTypes[i].labelandname ="";
		    			  $scope.caseTypes[i].labelandname = caseTypes[i].ct_label.concat("-",caseTypes[i].ct_label);
		    			  console.log("aaaaaaaaaaaaaaaaaaaaaaaa",$scope.caseTypes[i].labelandname);
		    		  }*/
		    			  
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting casetypes");
		      });
	  }
	  
	  
	  getCaseTypesForChangeCaseType();
	  
	  
	  function getIndexFields(){
		  $http.get(urlBase+'master/getindexfields').success(function (data) {
		    		$scope.index_fields=data.modelList;	
		    		$scope.amendMemo={
		    				if_id : 654321,
		    				if_label :'AMENDED MEMO PAGE'
		    		};
		    		$scope.index_fields.push($scope.amendMemo);
		    	
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
		  $scope.showLoader =true;
	    	$scope.showStatus =false;
		  $scope.caseFileList=[];
		  $http.post(urlBase+'casefile/getCaseFileList',$scope.search).success(function (data) {
		    	if(data.response=="TRUE")
		    		{
		    		 $scope.showLoader =false;
		    		$scope.caseFileList=data.modelList;	
		    		
		    		if($scope.caseFileList.length > 0){
		    	        
		            }
		            else {
		           	 $scope.showStatus =true;
		           	 
		        	 $scope.searchmodel = {fd_case_no : "37124",
		        				 fd_case_type : 157,
		        				 fd_case_year :  "2015"};
		           	 
		           	$http.post(baseUrl+'/searchfileDestructed/searchBycase',$scope.searchmodel).success(function(data){ 
				  		  
						$scope.dataCollections =data.data;
						$scope.displayedCollection = [].concat($scope.dataCollections);
						
		           	});
		            }
		            $scope.showLoader =false;
		    		}
		    	
		    	
		    	
		    	else
		    		{
		    		$scope.caseFileList=[];
		    		 $scope.showLoader =false;
		    		 $scope.showStatus =true;
		    		 
		    		}
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
			 var response = 
$http.post(urlBase+'casefile/addCaseEfling',$scope.dmsCaseData);
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
		$scope.subdocument.ord_remark=$scope.newString.replace("&nbsp;"," ");
		
		  $scope.subdocument.ord_remark=$scope.newString.replace(/&nbsp;/g, " ");
		
		  $scope.subdocument.ord_remark=$("#txtEditor").Editor("getText");
		  console.log($scope.TemplateDescription);
		 $scope.subdocument.sd_fd_mid=$scope.casefile.fd_id;
		 $scope.subdocument.subApplications=$scope.subapplications;
		  if($scope.sd_submitted_date!=null){
			  $scope.sd_submitted_date=convertDate($scope.sd_submitted_date);
			}
		  $scope.subdocument.sd_submitted_date=$scope.sd_submitted_date;
		  
		  
		 if($scope.subdocument.if_id!=43 && $scope.subdocument.if_id!=10 && $scope.subdocument.if_id!=19 ){ 
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
			    /*file.upload = Upload.upload({
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
			      });*/
				  
				  $http({
					    method: 'POST',
					    url:  urlBase + 'casefile/uploadJudgement',
					    headers: {'Content-Type': undefined },
					    transformRequest: function (data) {
					        var formData = new FormData();

					        formData.append('subDocument', new Blob([angular.toJson($scope.subdocument)], {
					            type: "application/json"
					        }));
					        formData.append("file", data.file);
					        return formData;
					    },
					    data: {subDocument: $scope.subdocument, file: file }

					}).
					success(function (data, status, headers, config) {
						if(data.response=="TRUE"){
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
						
					});
			  }
			 
			}
	  
	  
/*	  $scope.save=function() 
	  {
		  $scope.loading = false;
		  $scope.newString = $("#txtEditor").Editor("getText");
		$scope.subdocument.ord_remark=$scope.newString.replace("&nbsp;"," ");
		
		  $scope.subdocument.ord_remark=$scope.newString.replace(/&nbsp;/g, " ");
		
		  $scope.subdocument.ord_remark=$("#txtEditor").Editor("getText");
		  console.log($scope.TemplateDescription);
		 $scope.subdocument.sd_fd_mid=$scope.casefile.fd_id;
		  if($scope.sd_submitted_date!=null){
			  $scope.sd_submitted_date=convertDate($scope.sd_submitted_date);
			}
		  $scope.subdocument.sd_submitted_date=$scope.sd_submitted_date;
		  
		  
		 if($scope.subdocument.if_id!=43 && $scope.subdocument.if_id!=10 && $scope.subdocument.if_id!=19 ){ 
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
			 
			}*/
	  
	  
/*	  $scope.save=function() 
	  {
		  
		  
		  var test = {
			        description:"Test",
			        status: "REJECTED"
			    };

			var fd = new FormData();
			fd.append('data', angular.toJson(test));
			fd.append("file", $scope.file);
			$http({
			    method: 'POST',
			    url: 'EmployeeService/employee/data/fileupload',
			    headers: {'Content-Type': undefined},
			    data: fd,
			    transformRequest: angular.identity
			})
			.success(function(data, status) {
			                alert("success");
			});
			
			
			
			
			
		  
		  $scope.loading = false;
		  $scope.newString = $("#txtEditor").Editor("getText");
		$scope.subdocument.ord_remark=$scope.newString.replace("&nbsp;"," ");
		
		  $scope.subdocument.ord_remark=$scope.newString.replace(/&nbsp;/g, " ");
		
		  $scope.subdocument.ord_remark=$("#txtEditor").Editor("getText");
		  console.log($scope.TemplateDescription);
		 $scope.subdocument.sd_fd_mid=$scope.casefile.fd_id;
		  if($scope.sd_submitted_date!=null){
			  $scope.sd_submitted_date=convertDate($scope.sd_submitted_date);
			}
		  $scope.subdocument.sd_submitted_date=$scope.sd_submitted_date;
		  
		  
		 if($scope.subdocument.if_id!=43 && $scope.subdocument.if_id!=10 && $scope.subdocument.if_id!=19 ){ 
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
				  var fd = new FormData(); 
				  
				  fd.append('data', angular.toJson($scope.subdocument));
					fd.append("file", file);
					
					$http({
					    method: 'POST',
					    url: urlBase +'EmployeeService/employee/data/fileupload',
					    headers: {'Content-Type': undefined},
					    data: fd,
					    transformRequest: angular.identity
					})
					.success(function(data, status) {
					                alert("success");
					});
				  
				  
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
			 
			}*/
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
		  
		  var sb ={};
		  
		sb.at_id=  $scope.subdocument.at_id
		sb.sd_fd_mid =  $scope.subdocument.sd_fd_mid
		sb.ord_remark=  $scope.subdocument.ord_remark
		sb.sd_submitted_date=  $scope.subdocument.sd_submitted_date
		sb.ord_consignment_no=  $scope.subdocument.ord_consignment_no
		
		
		  
		  $http.post(urlBase+'casefile/addreportdata',angular.toJson(sb)
				  
				  
			  )
			  .success(function (data) {
			    	if(data.response=="TRUE"){
			    		$scope.loading = true;
		        	$scope.picFile='';
		        	$scope.if_id='';
		        	$scope.ord_remark='';
			    		alert("Successfully added order report data");	
			    	}
			    	
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
		  window.open(urlBase+"casefile/view/"+id,"_blank");
	  }
	  $scope.viewDetail=function(id){
		  window.open(urlBase+"casefile/viewdetail/"+id,"_blank");
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
		  
		  if($scope.new_case_type && $scope.new_case_no && $scope.new_case_year ){
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
		  else {
			  alert("Please Enter All The Fields");
		  }
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
				
				
				
				//===========================================================================================================================================			
							/*====================== Vijay Chaurasiya  Start from ) =========================*/
				//========================================================================================================================================
					
							$scope.addPartyName = function(id) {

							    console.log("FD ID:", id);

							    var petReq = $http.get(urlBase + 'casefile/getpetitionerByFdId/' + id);
							    var resReq = $http.get(urlBase + 'casefile/getrespondentByFdId/' + id);

							    $q.all([petReq, resReq]).then(function(responses) {

							        //  Assign objects safely
							        $scope.petitionerObj = responses[0].data.data;
							        $scope.respondentObj = responses[1].data.data;

							        console.log("Petitioner Obj:", $scope.petitionerObj);
							        console.log("Respondent Obj:", $scope.respondentObj);

							        //  Safe existence check
							        var hasPetitioner = $scope.petitionerObj && $scope.petitionerObj.pt_fd_mid;
							        var hasRespondent = $scope.respondentObj && $scope.respondentObj.rt_fd_mid;

							        //  If BOTH exist → DO NOTHING
							        if (hasPetitioner && hasRespondent) {
							            alert("Petitioner & Respondent already exist. No need to save.");
							            return;
							        }

							        // ❗ If any missing → fetch from cause list
							        console.log("Fetching from CauseList...");

							        return $http.get(urlBase + 'causelist/getParty/' + id)
							            .then(function(response) {

							                if (response.data && response.data.data) {

							                    var petName = response.data.data.cl_first_petitioner;
							                    var resName = response.data.data.cl_first_respondent;

							                    //  Assign for UI
							                    $scope.petitioner = petName;
							                    $scope.respondent = resName;

							                    console.log("Final Petitioner:", petName);
							                    console.log("Final Respondent:", resName);

							                    //  Save only missing

							                    if (!hasPetitioner) {

							                        var payload1 = {
							                            pt_fd_mid: id,
							                            pt_name: petName,
							                            pt_rec_status: 1
							                        };

							                        $http.post(urlBase + 'casefile/savepetitioner', payload1)
							                            .then(function(res) {
							                                console.log("Petitioner Saved:", res.data);
															alert("Addedd Petitioner details Successfully!")
							                            })
							                            .catch(function(err) {
							                                console.error("Petitioner Save Error:", err);
							                            });
							                    }

							                    if (!hasRespondent) {

							                        var payload2 = {
							                            rt_fd_mid: id,
							                            rt_name: resName,
							                            rt_rec_status: 1
							                        };

							                        $http.post(urlBase + 'casefile/saverespondent', payload2)
							                            .then(function(res) {
							                                console.log("Respondent Saved:", res.data);
							                            })
							                            .catch(function(err) {
							                                console.error("Respondent Save Error:", err);
							                            });
							                    }

							                }
							            });

							    }).catch(function(error) {
							        console.error("Error in APIs:", error);
							    });
							};
							
							/*====================== Vijay Chaurasiya  end ) =========================*/		
					
							
				
				
				
				
				
	
				
			
	  //
	  /*END*/  
	  
	  

	  
}]);