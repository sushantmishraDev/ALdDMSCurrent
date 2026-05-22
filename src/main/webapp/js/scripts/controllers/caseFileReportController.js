var EDMSApp = angular.module("EDMSApp", ['ui.bootstrap','smart-table','angularUtils.directives.dirPagination']);

EDMSApp.controller('caseFileReportController',['$scope', '$http' ,'$q','$filter','$sce',function ($scope, $http,$q,$filter,$sce) {
	var urlBase="/dms/";

	
	   /*var ctx = document.getElementById('myChart1').getContext('2d');
	   var chart1 = new Chart(ctx, {
	       // The type of chart we want to create
	       type: 'line',

	       // The data for our dataset
	       data: {
	           labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
	           datasets: [{
	               label: 'My First dataset',
	               backgroundColor: 'rgb(255, 99, 132)',
	               borderColor: 'rgb(255, 99, 132)',
	               data: [45, 10, 5, 2, 20, 30, 45]
	           },
	           {
	               label: 'Second dataset',
	               backgroundColor: 'rgb(255, 42, 50)',
	               borderColor: 'rgb(255, 99, 132)',
	               data: [20, 50, 5, 2, 20, 30, 10]
	           }]
	       },

	       // Configuration options go here
	       options: {}
	   });

	   var ctx1 = document.getElementById('myChart2').getContext('2d');
	   var chart = new Chart(ctx1, {
	       // The type of chart we want to create
	       type: 'bar',

	       // The data for our dataset
	       data: {
	           labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
	           datasets: [{
	               label: 'My First dataset',
	               backgroundColor: 'rgb(255, 99, 132)',
	               borderColor: 'rgb(255, 99, 132)',
	               data: [45, 10, 5	]
	           },
	           {
	               label: 'Second dataset',
	               backgroundColor: 'rgb(255, 42, 50)',
	               borderColor: 'rgb(255, 99, 132)',
	               data: [20, 50, 5, 2, 20, 30, 10]
	           }]
	       },

	       // Configuration options go here
	       options: {}
	   });*/
	   
	$scope.caseFileReportData ={};
	$scope.caseFileReportByType =[];
	$scope.caseFileReportByYear =[];
	$scope.caseStageReport =[];
	var caseStageReportTemp =[];
	$scope.cavStageReport =[];
	$scope.appStageReport =[];
	
	getCaseYearlyReport()
	   getCavFilAppReport();
	   getCaseTypeReport();
	   
	   
	   var backgroundColor=["#CCCC99" ,"#CCCC99" ,"#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99" ,"#CCCC99"
		   ,"#CCCC99"  ,"#006064","#00695C","#CCCC33 ","#616161","#263238","#660033","#330099","#330033","#333333","#333333","#003366","#336666","#336666","#336666","#99CC66","#33CCCC","#009999","#333300","#333300"];
	   var borderColor =["#CCCC99" ,"#CCCC99" ,"#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99", "#CCCC99" ,"#CCCC99"
		   ,"#CCCC99"  ,"#006064","#00695C","#CCCC33 ","#616161","#263238","#660033","#330099","#330033","#333333","#333333","#003366","#336666","#336666","#336666","#99CC66","#33CCCC","#009999","#333300","#333300"];
	   
	   
	 
	   $scope.caseStageReport =function(){
		   $scope.caseStageReport=[];
		   caseStageReportTemp =[];
		   
		   console.log(" case function invodeddddddd");
			   $http.get(urlBase+'/casereports/getcasestagereport')
				.success(function(data1){
					console.log("data of case types",data1);
					
					for(j =0 ; j< data1.length ; j++){
						
						var temp ={};
						temp.stage =data1[j][0];
						temp.count =data1[j][1];
						
						caseStageReportTemp.push(temp);
						
						
					}
					$scope.caseStageReport =caseStageReportTemp;
					console.log("reportttttttttttttttttttttttt dataaaaaaaaaaaaa",$scope.caseStageReport);
					
					var chartView7 = new Object();
					chartView7.type ='doughnut';
					chartView7.data ={};
					chartView7.data.labels =[];
					for (i = 0; i < data1.length; i++) {
						
					chartView7.data.labels.push(data1[i][0])
					}
					
					console.log("data of case types",chartView7);
					chartView7.data.datasets=[];
					var dataSet ={};
					dataSet.data=[];
				//	dataSet.label ="Total Reports According to case types";
					dataSet.backgroundColor =['#2196f38c', '#f443368c', '#3f51b570','#CCCC99','#660033','#e6194b'];
					dataSet.borderColor =['#2196f38c', '#f443368c', '#3f51b570','#CCCC99','#660033','#e6194b'];
					
					for (i = 0; i < data1.length; i++) {
						
						dataSet.data.push(data1[i][1]);
						}
					
					chartView7.data.datasets.push(dataSet);
					
					chartView7.options ={};
					
					
					
					console.log("chartttttt viewdataaaa",chartView7);
					
					var ctx7 = document.getElementById('myChart7').getContext('2d');
					   var chart = new Chart(ctx7, chartView7);
					
					
				
				
				
				
				
				}).error(function(data, status, headers, config) {
					console.log("Error in getting Report ");
					});	
			   
			   
		 
		   
	   }
	   
	   $scope.cavStageReport =function(){
		   console.log(" cav function invodeddddddd");
		   console.log(" case function invodeddddddd");
		   $scope.caseStageReport=[];
		   caseStageReportTemp =[];
		   $http.get(urlBase+'/casereports/getcavstagereport')
			.success(function(data1){
				console.log("data of case types",data1);
				
				for(j =0 ; j< data1.length ; j++){
					
					var temp ={};
					temp.stage =data1[j][0];
					temp.count =data1[j][1];
					
					caseStageReportTemp.push(temp);
					
					
				}
				$scope.caseStageReport =caseStageReportTemp;
				console.log("reportttttttttttttttttttttttt dataaaaaaaaaaaaa",$scope.caseStageReport);
				
				var chartView7 = new Object();
				chartView7.type ='doughnut';
				chartView7.data ={};
				chartView7.data.labels =[];
				for (i = 0; i < data1.length; i++) {
					
				chartView7.data.labels.push(data1[i][0])
				}
				
				console.log("data of case types",chartView7);
				chartView7.data.datasets=[];
				var dataSet ={};
				dataSet.data=[];
			//	dataSet.label ="Total Reports According to case types";
				dataSet.backgroundColor =['#2196f38c', '#f443368c', '#3f51b570','#CCCC99','#660033','#e6194b'];
				dataSet.borderColor =['#2196f38c', '#f443368c', '#3f51b570','#CCCC99','#660033','#e6194b'];
				
				for (i = 0; i < data1.length; i++) {
					
					dataSet.data.push(data1[i][1]);
					}
				
				chartView7.data.datasets.push(dataSet);
				
				chartView7.options ={};
				
				
				
				console.log("chartttttt viewdataaaa",chartView7);
				
				var ctx7 = document.getElementById('myChart7').getContext('2d');
				   var chart = new Chart(ctx7, chartView7);
				
				
			
			
			
			
			
			}).error(function(data, status, headers, config) {
				console.log("Error in getting Report ");
				});	
		   
		   
	   }
	   
	   $scope.appStageReport =function(){
		   console.log(" app function invodeddddddd");
		   
	   }
	   
	   
	   function getCaseYearlyReport(){
		   $http.get(urlBase+'/casereports/getcaseyearlyreport')
			.success(function(data1){
				console.log("data of case typesyyyyyyyyyyyyyyyyy",data1);
				var tempArray =[];
         for(j =0 ; j< data1.cases.length ; j++){
					
					var temp ={};
					temp.year =data1.cases[j][0];
					temp.casecount =data1.cases[j][1];
					tempArray.push(temp);
				//	$scope.caseFileReportByYear.push(temp);
					
					
				}
         
         for(j =0 ; j< data1.applications.length ; j++){
				
				tempArray[j].appcount =data1.applications[j][1];
				
				
			}
         for(j =0 ; j< data1.caveats.length ; j++){
				
        		tempArray[j].caveatcount =data1.caveats[j][1];
				
				
				
			}
         
         console.log("temppppppppppppppppppppppppppppppppppppppppppppppppppp",tempArray);
         $scope.caseFileReportByYear =tempArray;
         
         var chartView5 = new Object();
			chartView5.type ='bar';
			chartView5.data ={};
			chartView5.data.labels =[];
			
			for (i = 0; i < tempArray.length; i++) {
				
				chartView5.data.labels.push(tempArray[i].year);
				}
			
			chartView5.data.datasets=[];
			for (i = 0; i < tempArray.length; i++) {
			var dataSet ={};
			dataSet.data=[];
			
			dataSet.label ="Case Registration Yearly";
			dataSet.backgroundColor ='#2196f38c';
			dataSet.borderColor ='#2196f38c';
			for (i = 0; i < tempArray.length; i++) {
				
				dataSet.data.push(tempArray[i].casecount);
				}
			chartView5.data.datasets.push(dataSet);
			}
			for (i = 0; i < tempArray.length; i++) {
				var dataSet ={};
				dataSet.data=[];
				
				dataSet.label ="App Registration Yearly";
				dataSet.backgroundColor ='#CCCC99';
				dataSet.borderColor ='#CCCC99';
				for (i = 0; i < tempArray.length ; i++) {
					
					dataSet.data.push(tempArray[i].appcount);
					}
				chartView5.data.datasets.push(dataSet);
				}
			for (i = 0; i < tempArray.length; i++) {
				var dataSet ={};
				dataSet.data=[];
				
				dataSet.label ="Caveat Registration Yearly";
				dataSet.backgroundColor ='#616161';
				dataSet.borderColor ='#616161';
				for (i = 0; i < tempArray.length ; i++) {
					
					dataSet.data.push(tempArray[i].caveatcount);
					}
				chartView5.data.datasets.push(dataSet);
				}
			
			
			
			chartView5.options ={};
			
			
			
			console.log("chartttttt viewdataaaa",chartView5);
			
			var ctx5 = document.getElementById('myChart5').getContext('2d');
			   var chart = new Chart(ctx5, chartView5);
			
         
         console.log("data of case typesyyyyyyyyyyyyyyyyy angular ",$scope.caseFileReportByYear);
				
			}).error(function(data, status, headers, config) {
				console.log("Error in getting Report ");
				});	
		   
		   
	   }
				
				
				
	   function getCaseTypeReport(){
		   $http.get(urlBase+'/casereports/getcasetypereport')
			.success(function(data1){
				console.log("data of case types",data1);
				
				for(j =0 ; j< data1.length ; j++){
					
					var temp ={};
					temp.count =data1[j][0];
					temp.name =data1[j][1];
					
					$scope.caseFileReportByType.push(temp);
					
					
				}
				
				console.log("reportttttttttttttttttttttttt dataaaaaaaaaaaaa",$scope.caseFileReportByType);
				
				var chartView2 = new Object();
				chartView2.type ='doughnut';
				chartView2.data ={};
				chartView2.data.labels =[];
				for (i = 0; i < data1.length; i++) {
					
				chartView2.data.labels.push(data1[i][1])
				}
				
				console.log("data of case types",chartView2);
				chartView2.data.datasets=[];
				var dataSet ={};
				dataSet.data=[];
			//	dataSet.label ="Total Reports According to case types";
				dataSet.backgroundColor =['#2196f38c', '#f443368c', '#3f51b570','#3f51b570','#3f51b570','#e6194b','#3cb44b',
					'#ffe119','#4363d8','#f58231','#911eb4'];
				dataSet.borderColor =['#2196f38c', '#f443368c', '#3f51b570','#3f51b570','#3f51b570','#e6194b','#3cb44b',
					'#ffe119','#4363d8','#f58231','#911eb4'];
				
				for (i = 0; i < data1.length; i++) {
					
					dataSet.data.push(data1[i][0]);
					}
				
				chartView2.data.datasets.push(dataSet);
				
				chartView2.options ={};
				
				
				
				console.log("chartttttt viewdataaaa",chartView2);
				
				var ctx4 = document.getElementById('myChart4').getContext('2d');
				   var chart = new Chart(ctx4, chartView2);
				
				
			
			
			
			
			
			}).error(function(data, status, headers, config) {
				console.log("Error in getting Report ");
				});	
		   
		   
	   }
	   
	   
	   
	  function getCavFilAppReport(){
		  $http.get(urlBase+'/casereports/getfilecavapp')
			.success(function(data1){

			
			console.log("data from server",data1);
			
			$scope.caseFileReportData =data1;
			console.log("data from server",$scope.caseFileReportData);
			
			var chartView = new Object();
			chartView.type ='doughnut';
			chartView.data ={};
			chartView.data.labels =[];
			var caseData ={};
			/*caseData.Application ="Application";
			caseData.caveat="caveat";
			caseData.Cases="Cases";*/
			chartView.data.labels.push("Application");
			chartView.data.labels.push("caveat");
			chartView.data.labels.push("Cases");
			
			chartView.data.datasets=[];
			/*chartView.data.datasets[0].label ="First Case Report"
				chartView.data.datasets[0].backgroundColor =['#2196f38c', '#f443368c', '#3f51b570'];
			chartView.data.datasets[0].borderColor =['#2196f38c', '#f443368c', '#3f51b570'];
			chartView.data.datasets[0].data=[];
			chartView.data.datasets[0].data.push(data1.applicationCount);
			chartView.data.datasets[0].data.push(data1.caveatCount);
			chartView.data.datasets[0].data.push(data1.fileCount);*/
			
			var dataSet ={};
			dataSet.label ="First Case Report";
			dataSet.backgroundColor =['#2196f38c', '#f443368c', '#3f51b570'];
			dataSet.borderColor =['#2196f38c', '#f443368c', '#3f51b570'];
			dataSet.data=[];
			dataSet.data.push(data1.applicationCount);
			dataSet.data.push(data1.caveatCount);
			dataSet.data.push(data1.fileCount);
			
			/*caseData.count =data1.applicationCount;*/
			
			chartView.data.datasets.push(dataSet);
			
			chartView.options ={};
			
			console.log("chartttttt viewdataaaa",chartView);
			
			var ctx3 = document.getElementById('myChart3').getContext('2d');
			   var chart = new Chart(ctx3, chartView);
			
			
			
			
			
			


		}).error(function(data, status, headers, config) {
		console.log("Error in getting Report ");
		});	
		   console.log("method invoked");
	   }

    
    
    
    
   
    $scope.trustedHtml=null;
	 $scope.open = function($event,opened) {
		    $event.preventDefault();
		    $event.stopPropagation();

		    $scope[opened] = true;

		  };
		  
			 $scope.opens = function($event,opened) {
				    $event.preventDefault();
				    $event.stopPropagation();

				    $scope[opened] = true;
				  };

		//		  
				  $scope.searchreport = function(model) {                                                                     

						$http.get(urlBase+'reports/getReportData',{params: {'fromDate':$scope.model.cl_date,'toDate':$scope.model.cl_dol}}).success(function(data) {
					
							for(let i =0 ; i< data.modelData.length ;i++){
								var handoverdata1={};
								
								console.log("data in LLLLLLLLLLLLLLL",data.modelData.length);
							
								
								handoverdata1.ct_name =data.modelData[i][2].ct_label;
								handoverdata1.sd_cr_date =data.modelData[i][0].sd_cr_date;
								handoverdata1.sd_document_name =data.modelData[i][0].sd_document_name;
								handoverdata1.fd_case_no =data.modelData[i][1].fd_case_no;
								handoverdata1.sd_id =data.modelData[i][0].sd_id;
								handoverdata1.fd_case_year =data.modelData[i][1].fd_case_year;
								handoverdata1.um_fullname =data.modelData[i][0].user.um_fullname;
									handoverdata1.ord_remark =$sce.trustAsHtml(data.modelData[i][3].ord_remark);
								$scope.handoverdataArray.push(handoverdata1);
							}
							

						}).error(function(data, status, headers, config) {
							console.log("Error in getting ProductivityReportData ");
						});

					};

			$scope.Getfile=function(data)

			{
			filename=data.sd_document_name;
			sd_id=data.sd_id;
			$http.get(urlBase+'reports/Getfilename',{params: {'sd_document_name':filename,'sd_id':sd_id}})
				.success(function(data){

					
				$scope.fileurl=urlBase+"uploads/"+filename+".pdf";
				$scope.title=filename;
				$('#casefile_Modal').modal('show');
				console.log($scope.fileurl);


			}).error(function(data, status, headers, config) {
			console.log("Error in getting Report ");
			});	
			//}
			};


			function getMasterdata() {
			debugger;
			var response = $http.get(urlBase+'reports/getByOfficeReport');
			response.success(function(data, status, headers, config) {
				console.log(data);
				//$scope.displayedCollections = [].concat($scope.handoverdata);
			//	console.log($scope.displayedCollections);

				for(let i =0 ; i< data.modelData.length ;i++){
					var handoverdata1={};
					
					console.log("data in LLLLLLLLLLLLLLL",data.modelData[i][2].ct_label);

					handoverdata1.ct_name =data.modelData[i][2].ct_label;
					console.log(handoverdata1,"data in LLLLLLLLLLLLLLL",data.modelData[i][2].ct_label);
					handoverdata1.sd_cr_date =data.modelData[i][0].sd_cr_date;
					handoverdata1.sd_document_name =data.modelData[i][0].sd_document_name;
					handoverdata1.fd_case_no =data.modelData[i][1].fd_case_no;
					handoverdata1.sd_id =data.modelData[i][0].sd_id;
					handoverdata1.fd_case_year =data.modelData[i][1].fd_case_year;
					handoverdata1.um_fullname =data.modelData[i][0].user.um_fullname;
					handoverdata1.ord_remark =$sce.trustAsHtml(data.modelData[i][3].ord_remark);
					$scope.handoverdataArray.push(handoverdata1);
				}
				
	
				
				
			});
			response.error(function(data, status, headers, config) {
			});

			};
			
			
			
			
			
			/*===================== Vijay Chaurasiya ======================================*/
				
				$scope.getPDMSToDMSReport = function() {

					var start = $filter('date')($scope.startDate, 'yyyy-MM-dd');
					var end = $filter('date')($scope.endDate, 'yyyy-MM-dd');


					console.log("Start Date:", start, "End Date:", end);

					var response = $http.get(urlBase + 'reports/getMovePdmsToDmsReports', {
						params: {
							startDate: start,
							endDate: end
						}
					});

					response.success(function(data) {
						console.log(data);
						$scope.requests = data;
					});

					response.error(function(error) {
						console.error('Error:', error);
					});
				};
							
			
			
			
			
			
			

}]);