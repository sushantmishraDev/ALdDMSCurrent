var EDMSApp = angular.module("EDMSApp", ['smart-table']);

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

EDMSApp.controller("CaseFileCtrl",	function($scope, $sce, $http,$filter) {
	var urlBase="/dms/";
	$scope.subDocuments=[];
	$scope.document_name= $('#document_name').val();
	//alert($scope.fd_file_bar_code);
	var url = urlBase+'uploads/' + $scope.document_name+".pdf";
	DEFAULT_URL = url;
	 PDFViewerApplication.open(DEFAULT_URL);
	 
	$scope.metadata=[];
	
	 $scope.case_type_label ;
	
	$scope.pFlag =false;
	$scope.nFlag =false;
	$scope.doc_id= $('#doc_id').val();
	$scope.cl_court_no= $('#cl_court_no').val();
	$scope.cl_rec_status= $('#cl_rec_status').val();
	$scope.cl_serial_no= $('#cl_serial_no').val();
	$scope.cl_list_type_mid= $('#cl_list_type_mid').val();
	$scope.cl_fd_mid= $('#cl_fd_mid').val();
	$scope.cl_ayr= $('#cl_ayr').val();
	$scope.cl_ano= $('#cl_ano').val();
	

	
	
	if(!$scope.cl_court_no || !$scope.cl_rec_status || !$scope.cl_serial_no || !$scope.cl_list_type_mid || !$scope.cl_fd_mid ){
		$scope.nFlag =false;
	}
	
	
	
	
	
	
	$scope.cl_dol= $('#cl_dol').val();
	
	$scope.cl_court_no1= $('#cl_court_no1').val();
	$scope.cl_rec_status1= $('#cl_rec_status1').val();
	$scope.cl_serial_no1= $('#cl_serial_no1').val();
	$scope.cl_list_type_mid1= $('#cl_list_type_mid1').val();
	$scope.cl_fd_mid1= $('#cl_fd_mid1').val();
	$scope.cl_serial_noCurrent= $('#cl_serial_noCurrent').val();
	$scope.cl_serial_noCurrent1= $('#cl_serial_noCurrent').val();
	$scope.cl_ayr1= $('#cl_ayr1').val();
	$scope.cl_ano1= $('#cl_ano1').val();
	$scope.cl_dol= $('#cl_dol').val();
	/*$scope.minSerial= $('#minSerial').val();
	$scope.maxSerial= $('#maxSerial').val();*/
	
	//$scope.allSerial= $('#allSerial').attributes;
	
	
	console.log("dropdown serial :",$scope.cl_serial_noCurrent1," ",$('#cl_serial_noCurrent').val());
	
	
	
	

	
	$scope.cl_court_noCurrent= $('#cl_court_noCurrent').val();
	$scope.cl_rec_statusCurrent= $('#cl_rec_statusCurrent').val();
	$scope.cl_serial_noCurrent= $('#cl_serial_noCurrent').val();
	$scope.cl_list_type_midCurrent= $('#cl_list_type_midCurrent').val();
	$scope.cl_dolCurrent= $('#cl_dolCurrent').val();
	
	$scope.listTypeName= $('#listTypeName').val();
	
	
	/*$scope.numbers = Array.from({length: ($scope.maxSerial-$scope.minSerial+1)}, (_, i) => Number($scope.minSerial) + i);*/
	
	
	
$scope.showMedReport=function(sd_id,$index,data){
		
		console.log("subDocument",data);
		document.getElementById('outlineView').innerHTML = '';
		
		
		
		
		
		
		
		
		
		$http.get(urlBase+'casefile/showMedReport/'+sd_id).success(function (data) {
	    	$scope.sample=data.modelData;
	    	DEFAULT_URL=urlBase+'uploads/' +data.modelData.document_name+".pdf";
	    //	DEFAULT_URL="192.168.0.162:8080/elegalix_restapi/api/judgment/8042762";
			console.log($scope.document_name);
			PDFViewerApplication.open(DEFAULT_URL);
			clearInterval(myVar1);
			count =0;
			console.log("ppppppppppppppppppppppp----------"+DEFAULT_URL);
			myFunction();
			//showBookMark();
			
			if(!myVar1){
			
				myVar1 =setInterval(booMarkShowFunction, 600);
				}
			else{
			
				myVar1 =setInterval(booMarkShowFunction, 600);
			}
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });

	};
	
	
	
	$scope.viewCaseFile=function(id){
		  window.open(urlBase+"casefile/view/"+id.cl_fd_mid,"_blank");
	  }
	
	$scope.viewCaseFileFromModal=function(data){
		var nPflag ="N";
		console.log("Cause List ",data);
		  window.open(urlBase+"casefile/viewCauseList/"+data.cl_fd_mid+'?cl_court_no='+data.cl_court_no+'&cl_rec_status='+data.cl_rec_status+'&cl_serial_no='+data.cl_serial_no+'&cl_dol='+data.cl_dol+'&cl_list_type_mid='+data.cl_list_type_mid+'&nPflag='+nPflag,"_self");
	  }
	

getSerials=function(){
		$scope.allSerial=[];
		console.log($scope.nFlag+" flags "+$scope.pFlag);
		
		var causeList ={};
		
		causeList.cl_fd_mid =$scope.doc_id;
		causeList.cl_court_no=$scope.cl_court_noCurrent;
		causeList.cl_rec_status =$scope.cl_rec_statusCurrent;
		causeList.cl_serial_no= $scope.cl_serial_noCurrent;
		causeList.cl_dol =$scope.cl_dolCurrent;
		causeList.cl_list_type_mid =$scope.cl_list_type_midCurrent;
	$http.post(urlBase+'causelist/getSerials',causeList)
	.success(function(data) {
		if(data.response=="TRUE"){
			
			
			$scope.allSerial=data.data;
			console.log("all serail :",$scope.allSerial);
			
		}else{
			
		}
		
}).error(function(data, status, headers, config) {
	console.log("Error in adding causelist ");
});	
	}


getSerials();


getNextnPrevious=function(){
	
	var causeList ={};
	causeList.cl_fd_mid =$scope.doc_id;
		causeList.cl_court_no=$scope.cl_court_noCurrent;
		causeList.cl_rec_status =$scope.cl_rec_statusCurrent;
		causeList.cl_serial_no= $scope.cl_serial_noCurrent;
		causeList.cl_dol =$scope.cl_dolCurrent;
		causeList.cl_list_type_mid =$scope.cl_list_type_midCurrent;
	
	$http.post(urlBase+'casefile/getNextnPrevious',causeList)
	.success(function(data) {
		if(data.response=="TRUE"){
			if(data.modelData!=null){
				
				$scope.nFlag=true;
				$scope.clNext=data.modelData;
				
				$scope.cl_court_no= $scope.clNext.cl_court_no;
				$scope.cl_rec_status= $scope.clNext.cl_rec_status;
				$scope.cl_serial_no= $scope.clNext.cl_serial_no;
				$scope.cl_list_type_mid= $scope.clNext.cl_list_type_mid;
				$scope.cl_fd_mid= $scope.clNext.cl_fd_mid;
				$scope.cl_ayr= $scope.clNext.cl_ayr;
				$scope.cl_ano= $scope.clNext.cl_ano;
				$scope.cl_dol= $scope.clNext.cl_dol;
				
			}
			if(data.data!=null){
				$scope.pFlag =true;
				$scope.clPrev=data.data;
				$scope.cl_court_no1= $scope.clPrev.cl_court_no;
				$scope.cl_rec_status1= $scope.clPrev.cl_rec_status;
				$scope.cl_serial_no1= $scope.clPrev.cl_serial_no;
				$scope.cl_list_type_mid1= $scope.clPrev.cl_list_type_mid;
				$scope.cl_fd_mid1= $scope.clPrev.cl_fd_mid;
				$scope.cl_serial_noCurrent= $scope.clPrev.cl_serial_noCurrent;
				//$scope.cl_serial_noCurrent1= $scope.clPrev.cl_serial_noCurrent;
				$scope.cl_ayr1= $scope.clPrev.cl_ayr;
				$scope.cl_ano1= $scope.clPrev.cl_ano;
				$scope.cl_dol1= $scope.clPrev.cl_dol;
			}
			
			
		}else{
			
		}
		
}).error(function(data, status, headers, config) {
	console.log("Error in adding causelist ");
});	
	
}

getNextnPrevious();


	
getConnectedCases =function(){
	
	$scope.connectedCasesList =[];
		
		var causeList ={};
		causeList.cl_fd_mid =$scope.doc_id;
			causeList.cl_court_no=$scope.cl_court_noCurrent;
			causeList.cl_rec_status =$scope.cl_rec_statusCurrent;
			causeList.cl_serial_no= $scope.cl_serial_noCurrent;
			causeList.cl_dol =$scope.cl_dolCurrent;
			causeList.cl_list_type_mid =$scope.cl_list_type_midCurrent;
		$http.post(urlBase+'causelist/getConnectedCases',causeList)
		.success(function(data) {
			if(data.response=="TRUE"){
				
				
				$scope.connectedCasesList=data.modelList;
				$scope.listTypeName =$scope.connectedCasesList[0].clType.clt_description;
				document.getElementById("listType").innerHTML =$scope.listTypeName;
			}else{
				
			}
			
	}).error(function(data, status, headers, config) {
		console.log("Error in adding causelist ");
	});	
		
	}
	
	getConnectedCases();
	
	
	var serialDesc ='Sr No. '+$scope.cl_serial_noCurrent
	
	document.getElementById("serial").innerHTML =serialDesc;
	
	
	
	
	$scope.cl_dol1= $('#cl_dol1').val();
	
	$scope.doc_id= $('#doc_id').val();
	
	
	if(!$scope.cl_court_no1 || !$scope.cl_rec_status1 || !$scope.cl_serial_no1 || !$scope.cl_list_type_mid1 || !$scope.cl_fd_mid1 ){
		$scope.pFlag =false;
	}
	
	
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_court_no);
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_rec_status);
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_serial_no);
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_dol);
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_list_type_mid);
	//$scope.judgmentdocId= $('#judgmentdocId').val();
	
	
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_court_no1);
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_rec_status1);
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_serial_no1);
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_dol1);
	console.log("xxxxxxxxxxxxxxxxxxx",$scope.cl_list_type_mid1);
	//alert(2);

	
	 
	 
	 
   // alert(DEFAULT_URL);
	 $scope.case_type_label ;
		$scope.petitions=[];
		$scope.petitionsData=[];
		$scope.rejoinders=[];
		$scope.rejoindersData=[];
		$scope.applications=[];
		$scope.medReportData=[];
		$scope.applicationsData=[];
		$scope.supp_affidavits=[];
		$scope.supp_affidavitsData=[];
		$scope.coun_affidavits=[];
		$scope.coun_affidavitsData=[];
		$scope.supp_coun_affidavits=[];
		$scope.supp_coun_affidavitsData=[];
		$scope.supp_rejoinders=[];
		$scope.supp_rejoindersData=[];
		$scope.order_sheets=[];
		$scope.order_sheetsData=[];
		$scope.office_repofrts=[];
		$scope.office_reportsData=[];
		$scope.orderFromElegalix=[];
		$scope.others=[];
		$scope.othersData=[];
		$scope.changetop=false;
		$scope.nt={};
		$scope.isSelected =false;
		$scope.checkedOrderData=null;
		$scope.notifications=[];
		$scope.pendingApp=[];
		$scope.lower_court_record=[];
		$scope.medSub=[];
		$scope.medSubData=[];
	
	var bookMarkToggle =false;
	var count =0;
	
	getSubDocuments();
	getCaseFileDetails();
	$scope.connectedMatch ='';
	
	$scope.changeSerial = function(){
		var flag="N";
		$scope.cl_fd_mid=9999999999;
		$scope.cl_fd_mid1=9999999999;
		$scope.cl_rec_status=1;
		if($scope.cl_list_type_mid == 1 || $scope.cl_list_type_mid == 2 ){
			  window.open(urlBase+'casefile/applicationviewCauseList/?cl_court_no='+$scope.cl_court_no+'&cl_rec_status='+$scope.cl_rec_status+'&cl_serial_no='+$scope.cl_serial_noCurrent1+'&cl_dol='+$scope.cl_dol+'&cl_list_type_mid='+$scope.cl_list_type_mid+'&app_no='+$scope.cl_ano+'&app_year='+$scope.cl_ayr+'&case_id='+9999999999,"_self");
			
		}
		else {
			if($scope.cl_court_no){
				
			
			console.log($scope.cl_serial_noCurrent);
			console.log(urlBase+"casefile/viewCauseList/"+$scope.cl_fd_mid+'?cl_court_no='+$scope.cl_court_no+'&cl_rec_status='+$scope.cl_rec_status+'&cl_serial_no='+$scope.cl_serial_noCurrent1+'&cl_dol='+$scope.cl_dol+'&cl_list_type_mid='+$scope.cl_list_type_mid);
			 window.open(urlBase+"casefile/viewCauseList/"+$scope.cl_fd_mid+'?cl_court_no='+$scope.cl_court_no+'&cl_rec_status='+$scope.cl_rec_status+'&cl_serial_no='+$scope.cl_serial_noCurrent1+'&cl_dol='+$scope.cl_dol+'&cl_list_type_mid='+$scope.cl_list_type_mid+'&nPflag='+flag,"_self");	
			}
			else {
				  window.open(urlBase+"casefile/viewCauseList/"+$scope.cl_fd_mid1+'?cl_court_no='+$scope.cl_court_no1+'&cl_rec_status='+$scope.cl_rec_status1+'&cl_serial_no='+$scope.cl_serial_noCurrent1+'&cl_dol='+$scope.cl_dol1+'&cl_list_type_mid='+$scope.cl_list_type_mid1+'&nPflag='+flag,"_self");
			}
			}
	
			
		
	}
	
	$scope.getNextCase =function(flag){
		
		if($scope.connectedCasesList.length > 1){
			
			for(var i =0 ;i < $scope.connectedCasesList.length ; i++){
				if($scope.connectedCasesList[i].cl_fd_mid == $scope.doc_id){
					$scope.connectedMatch =i; 
					console.log("condition trueeee",$scope.connectedCasesList[i]);
					
				}
			}
			
			$('#connectedCasesModal').modal('show');
			//alert("do something	");
		}
		
		else{
		
	if($scope.cl_list_type_mid == 1 || $scope.cl_list_type_mid == 2 ){
		  window.open(urlBase+'casefile/applicationviewCauseList/?cl_court_no='+$scope.cl_court_no+'&cl_rec_status='+$scope.cl_rec_status+'&cl_serial_no='+$scope.cl_serial_no+'&cl_dol='+$scope.cl_dol+'&cl_list_type_mid='+$scope.cl_list_type_mid+'&app_no='+$scope.cl_ano+'&app_year='+$scope.cl_ayr+'&case_id='+$scope.cl_fd_mid,"_self");
		
	}
	else {
		  window.open(urlBase+"casefile/viewCauseList/"+$scope.cl_fd_mid+'?cl_court_no='+$scope.cl_court_no+'&cl_rec_status='+$scope.cl_rec_status+'&cl_serial_no='+$scope.cl_serial_no+'&cl_dol='+$scope.cl_dol+'&cl_list_type_mid='+$scope.cl_list_type_mid+'&nPflag='+flag,"_self");
	}
		}
	}
	
$scope.getNextCaseFromModal =function(flag){
		
		
		
		console.log("flaggggggggggggggg",flag);
		
	if($scope.cl_list_type_mid == 1 || $scope.cl_list_type_mid == 2 ){
		  window.open(urlBase+'casefile/applicationviewCauseList/?cl_court_no='+$scope.cl_court_no+'&cl_rec_status='+$scope.cl_rec_status+'&cl_serial_no='+$scope.cl_serial_no+'&cl_dol='+$scope.cl_dol+'&cl_list_type_mid='+$scope.cl_list_type_mid+'&app_no='+$scope.cl_ano+'&app_year='+$scope.cl_ayr+'&case_id='+$scope.cl_fd_mid,"_self");
		
	}
	else {
		  window.open(urlBase+"casefile/viewCauseList/"+$scope.cl_fd_mid+'?cl_court_no='+$scope.cl_court_no+'&cl_rec_status='+$scope.cl_rec_status+'&cl_serial_no='+$scope.cl_serial_no+'&cl_dol='+$scope.cl_dol+'&cl_list_type_mid='+$scope.cl_list_type_mid+'&nPflag='+flag,"_self");
	}
	}	
	
	
$scope.getPreviousCase =function(flag){
		
	
	if($scope.connectedCasesList.length > 1){
		
		for(var i =0 ;i < $scope.connectedCasesList.length ; i++){
			if($scope.connectedCasesList[i].cl_fd_mid == $scope.doc_id){
				$scope.connectedMatch =i; 
				console.log("condition trueeee",$scope.connectedCasesList[i]);
				
			}
		}
		
		$('#connectedCasesModal').modal('show');
		//alert("do something	");
	}
	else {
		console.log("flaggggggggggggggg",flag);
		if($scope.cl_list_type_mid1 == 1 || $scope.cl_list_type_mid1 == 2 ){
	
			 window.open(urlBase+'casefile/applicationviewCauseList?cl_court_no='+$scope.cl_court_no1+'&cl_rec_status='+$scope.cl_rec_status1+'&cl_serial_no='+$scope.cl_serial_no1+'&cl_dol='+$scope.cl_dol1+'&cl_list_type_mid='+$scope.cl_list_type_mid1+'&app_no='+$scope.cl_ano1+'&app_year='+$scope.cl_ayr1+'&case_id='+$scope.cl_fd_mid1,"_self");
		}
		else {
			  window.open(urlBase+"casefile/viewCauseList/"+$scope.cl_fd_mid1+'?cl_court_no='+$scope.cl_court_no1+'&cl_rec_status='+$scope.cl_rec_status1+'&cl_serial_no='+$scope.cl_serial_no1+'&cl_dol='+$scope.cl_dol1+'&cl_list_type_mid='+$scope.cl_list_type_mid1+'&nPflag='+flag,"_self");
		}
	}
	}

$scope.getPreviousCaseFromModal =function(flag){
	
	console.log("flaggggggggggggggg",flag);
	if($scope.cl_list_type_mid1 == 1 || $scope.cl_list_type_mid1 == 2 ){

		 window.open(urlBase+'casefile/applicationviewCauseList?cl_court_no='+$scope.cl_court_no1+'&cl_rec_status='+$scope.cl_rec_status1+'&cl_serial_no='+$scope.cl_serial_no1+'&cl_dol='+$scope.cl_dol1+'&cl_list_type_mid='+$scope.cl_list_type_mid1+'&app_no='+$scope.cl_ano1+'&app_year='+$scope.cl_ayr1+'&case_id='+$scope.cl_fd_mid1,"_self");
	}
	else {
		  window.open(urlBase+"casefile/viewCauseList/"+$scope.cl_fd_mid1+'?cl_court_no='+$scope.cl_court_no1+'&cl_rec_status='+$scope.cl_rec_status1+'&cl_serial_no='+$scope.cl_serial_no1+'&cl_dol='+$scope.cl_dol1+'&cl_list_type_mid='+$scope.cl_list_type_mid1+'&nPflag='+flag,"_self");
	}
}


$scope.getNextApp =function(flag){
	
	console.log("flaggggggggggggggg",flag);
	
	// window.open(urlBase+"casefile/applicationviewCauseList/?cl_court_no="+data.cl_court_no+'&cl_rec_status='+data.cl_rec_status+'&cl_serial_no='+data.cl_serial_no+'&cl_dol='+data.cl_dol+'&cl_list_type_mid='+data.cl_list_type_mid+'&app_no='+data.cl_ano+'&app_year='+data.cl_ayr+'&data.cl_fd_mid='+data.cl_fd_mid,"_blank");

	  window.open(urlBase+'casefile/applicationviewCauseList?cl_court_no='+$scope.cl_court_no+'&cl_rec_status='+$scope.cl_rec_status+'&cl_serial_no='+$scope.cl_serial_no+'&cl_dol='+$scope.cl_dol+'&cl_list_type_mid='+$scope.cl_list_type_mid+'&app_no='+$scope.cl_ano+'&app_year='+$scope.cl_ayr+'&case_id='+$scope.cl_fd_mid,"_self");
}	


$scope.getPreviousApp =function(flag){
	
	console.log("flaggggggggggggggg",flag);
	
	

	  window.open(urlBase+"casefile/viewCauseList/"+$scope.cl_fd_mid1+'?cl_court_no='+$scope.cl_court_no1+'&cl_rec_status='+$scope.cl_rec_status1+'&cl_serial_no='+$scope.cl_serial_no1+'&cl_dol='+$scope.cl_dol1+'&cl_list_type_mid='+$scope.cl_list_type_mid1+'&app_no='+$scope.cl_ano+'&app_year='+$scope.cl_ayr,"_self");
}
	
	
	
	$scope.setReservedCase=function(id){
		  $http.get(urlBase+'reserve/reservedCase/'+id).success(function (data) {
			  $scope.casefile=data.modelData;
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting tree data");
		      });
		}
	
$scope.changeTop = function (){
	
		
		if(!$scope.changetop){
		console.log("top changeeeeeeeeeeeeeeeeeeeeeeeees",document.getElementById("pdfToolBar").style.top);
		document.getElementById("pdfToolBar").style.top =-1.7+'%';
		console.log("top changeeeeeeeeeeeeeeeeeeeeeeeees",document.getElementById("pdfToolBar").style.top);
		console.log("top changeeeeeeeeeeeeeeeeeeeeeeeees",document.getElementById("sidebarContainer").style.top);
		document.getElementById("sidebarContainer").style.top =-1.7+'%';
		document.getElementById("casePanelHeading").style.top =-1.7+'%';
		console.log("top changeeeeeeeeeeeeeeeeeeeeeeeees",document.getElementById("sidebarContainer").style.top);
		$scope.changetop =true;
		}
		else {
			//window.location.reload();
			var ord =document.getElementById("order");
		
			/*document.getElementById("order").style.overflow ='auto';
			document.getElementById("order").style.max-height = 450+'px';
			document.getElementById("order").style.padding=2+'px';*/
			ord.style.overflow ='scroll';
			ord.style.maxHeight = 450+'px';
		ord.style.padding=2+'px';
			
			//$( "#tree" ).load(window.location.href + " #tree" );
			console.log("else top changeeeeeeeeeeeeeeeeeeeeeeeees",document.getElementById("pdfToolBar").style.top);
			document.getElementById("pdfToolBar").style.top =70+'px';
			console.log("else  changeeeeeeeeeeeeeeeeeeeeeeeees",document.getElementById("pdfToolBar").style.top);
			document.getElementById("casePanelHeading").style.top =70+'px';
			
			/*console.log("else top changeeeeeeeeeeeeeeeeeeeeeeeees",document.getElementById("sidebarContainer").style.top);
			document.getElementById("sidebarContainer").style.top =9+'%';
			console.log("else  changeeeeeeeeeeeeeeeeeeeeeeeees",document.getElementById("sidebarContainer").style.top);*/
			
			
			
			$scope.changetop =false;
			
		}
		
		
		
		
		
		
	}

	
	 function topFunction() {
	    	  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
	    	  
	    	}
	    
	   // topFunction();
	    var x=document.documentElement;
	    
	  //  x.addEventListener("click",topFunction); 
	    
	    function myFunction() {
	    	  myVar = setTimeout(topFunction,1000);
	    	}
	    
	    myFunction();
	    
	    /*function booMarkShowFunction(){
	    	console.log("bookmark function called");
	    	var outlineView = document.getElementById('outlineView');
	    	console.log("outline viewwwwwwwwwwwwwww",outlineView);
	    	if(outlineView.hasChildNodes()){
	    		clearInterval(myVar);
	    		console.log("conditionnnn trueeeeeee");
	    	document.getElementById("sidebarContent").hidden=true;
            	console.log(document.getElementById("sidebarContent").hidden);
            	if(document.getElementById("sidebarContent").hidden==true){
            		document.getElementById("sidebarContent").hidden=false;
            		document.getElementById("sidebar").hidden=true;
            		
            		                       	
                    }
            	else{
            		document.getElementById("sidebarContent").hidden=true;
            		document.getElementById("sidebar").hidden=false;
            		
            		
            	}
                
            	
	    	outlineView.classList.remove('hidden');
	    	}
	    	else {
	    		console.log("do nothinggggggggggggg");
	    		document.getElementById("sidebar").hidden=false;
	    		document.getElementById("sidebarContent").hidden=true;
	    		outlineView.classList.add('hidden');
	    	}
	    	
	    	
	    	
	    	
	    }*/
	    var myVar1 = setInterval(booMarkShowFunction, 600);
	   
	    function booMarkShowFunction(){
	    	 count++;
	    	
	    	var outlineView = document.getElementById('outlineView');
	    
	    	if(outlineView.hasChildNodes()){
	    	
	    		
	    	document.getElementById("sidebarContent").hidden=true;
            	
            	if(document.getElementById("sidebarContent").hidden==true){
            
            		document.getElementById("sidebarContent").hidden=false;
            		document.getElementById("sidebar").hidden=true;
            		outlineView.classList.remove('hidden');
            		bookMarkToggle =true;
            		clearInterval(myVar1);
            		                       	
                    }
            	else {
            	
            		document.getElementById("sidebarContent").hidden=true;
            		document.getElementById("sidebar").hidden=false;
            		
            		
            		
            	}
                
            	
	    	
	    	}
	    	else if(bookMarkToggle) {
	    	
	    		document.getElementById("sidebar").hidden=false;
	    		document.getElementById("sidebarContent").hidden=true;
	    		outlineView.classList.add('hidden');
	    		bookMarkToggle =false;
	    		clearInterval(myVar1);
	    	}
	    	else if(count > 10){
	    		console.log("counttttttttttttttttttttttt",count);
	    		document.getElementById("sidebar").hidden=false;
	    		document.getElementById("sidebarContent").hidden=true;
	    		outlineView.classList.add('hidden');
	    		count =0;
	    		clearInterval(myVar1);
	    		
	    	}
	    	/*else if(!bookMarkToggle) {
	    		console.log("do nothinggggggggggggg");
	    		document.getElementById("sidebar").hidden=false;
	    		document.getElementById("sidebarContent").hidden=true;
	    		outlineView.classList.add('hidden');
	    		clearInterval(myVar1);
	    		
	    	}*/
	    	
	    	
	    }
	    
	  
	    
	   /* function showBookMark() {
	    	  myVar = setTimeout(booMarkShowFunction,600);
	    	}*/
	    
	   /* function showBookMark() {
	    	  myVar = setTimeout(booMarkShowFunction,600);
	    	}
	
	
	    showBookMark(); */
	    
	    
	    
	function persistLinkOnNewTab(sd_id,$index,doc_type){
		console.log("subDocument"+doc_type.valueOf()[$index].checked);
		console.log("subDocument"+doc_type.valueOf()[$index].checkBoxValue);
		if(doc_type.valueOf()[$index].checked==false){
		    $scope.personColour = { 'color': 'blue' };
		    doc_type.valueOf()[$index].checked=true;
		}
		if(doc_type.valueOf()[$index].checkBoxValue ==false){
			doc_type.valueOf()[$index].checkBoxValue= true;
		}
		
		
		/*console.log(sd_id);
		console.log($scope.subDocuments);
		
		if(doc_type.valueOf()[$index].checked==false){
		    $scope.personColour = { 'color': 'blue' };
		    doc_type.valueOf()[$index].checked=true;
		}*/
		
		
/*		if(doc_type=='St. Rep.' || doc_type=='ORD' || doc_type=='Off. Rep.')
		if($scope.orderData[$index].isColor==false){
		    $scope.personColour = { 'color': 'blue' };
		    $scope.orderData[$index].isColor=true;
		}
		
		if(doc_type=='petition'){
			if($scope.petitionsData[$index].checked==false){
			    $scope.personColour = { 'color': 'blue' };
			    $scope.petitionsData[$index].checked=true;
			}
			}
		
		if(doc_type=='coun_affidavit'){
			if($scope.coun_affidavits[$index].checked==false){
			    $scope.personColour = { 'color': 'blue' };
			    $scope.petitionsData[$index].checked=true;
			}
			}*/
		
		$http.get(urlBase+'casefile/subdocument/'+sd_id).success(function (data) {
	
	    	
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });

	};

	$scope.checkHighlight = function (data){
		data.checkBoxValue
		data.checked =false;
		
		
		$http.get(urlBase+'casefile/subdocument-highlight-status/'+data.sd_id+'/'+data.checkBoxValue).success(function (data) {
	    	$scope.sample=data;
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
		
		
		
	}
	
	
	
	function getSubDocuments(){
		$http.get(urlBase+'casefile/getsubdocuments/'+$scope.doc_id).success(function (data) {
	    	$scope.subDocuments=data.modelList;
	    	console.log($scope.subDocuments);
	    	 for(var v = 0; v < $scope.subDocuments.length; v++)
	    	 {
	    		 if($scope.subDocuments[v].checked==true)
	    		 {
	    			 $scope.subDocuments[v].checkBoxValue =true;
	    			 $scope.personColour = { 'color': 'blue' };
	    		 }
	    		 else if($scope.subDocuments[v].checked==false)
	    		 {
	    			 $scope.subDocuments[v].checkBoxValue =false;
	    		 }
		        	for(var i = 0; i <  $scope.subDocuments[v].subApplications.length; i++){
		        		var date=$scope.subDocuments[v].sd_submitted_date;
		        		console.log(date);
		        		var date1=new Date(date);
		        		var date2 = $filter('date')(new Date(date1), 'dd/MM/yyyy');
		        	//	var filterdatetime = $filter('date')( date1 );
		        		//if (v != i) {
		        		
		        			if ($scope.subDocuments[v].sd_id == $scope.subDocuments[v].subApplications[i].sb_ap_sd_mid) {
		        				/*if($scope.subDocuments[v].documentType.at_id == 14 || $scope.subDocuments[v].documentType.at_id ==22 ||
		        						$scope.subDocuments[v].documentType.at_id ==7 ||
		        						$scope.subDocuments[v].documentType.at_id ==44 ||
		        						$scope.subDocuments[v].documentType.at_id ==45) {*/
		        				
		        				
		        				console.log("$scope.subDocuments[v].subApplications------"+$scope.subDocuments[v].subApplications[i].sb_ap_sd_mid);
		        				console.log("---"+$scope.subDocuments[v].subApplications[i].sb_ap_sd_mid+'/'
		        						+$scope.subDocuments[v].subApplications[i].sb_ap_no+'/'
		        						+$scope.subDocuments[v].subApplications[i].sb_ap_year+'/'
		        						+$scope.subDocuments[v].subApplications[i].applicationType.at_name);
		        				
		        				
		        					/*$scope.subDocuments[v].indexField.if_label=$scope.subDocuments[v].documentType.at_name+ ' / '
			        				+$scope.subDocuments[v].sd_document_no +' / '+$scope.subDocuments[v].sd_document_year+' / '+date2 +' // '
			        				+$scope.subDocuments[i].documentType.at_name+' / '
			        				+$scope.subDocuments[i].sd_document_no+' / '+$scope.subDocuments[i].sd_document_year;
		        					
		        					$scope.subDocuments[v].documentType.at_name=$scope.subDocuments[v].documentType.at_name+ ' / '
			        				+$scope.subDocuments[v].sd_document_no +' / '+$scope.subDocuments[v].sd_document_year+' / '+date2 +' // '
			        				+$scope.subDocuments[i].documentType.at_name+' / '
			        				+$scope.subDocuments[i].sd_document_no+' / '+$scope.subDocuments[i].sd_document_year;
			        			
		        					$scope.subDocuments.splice(i,1); 
		        					i=i-1;
		        					$scope.subDocuments[v].flag=true;
		        					$scope.apflag=true;*/
							}
						//}
		        	}
		        	
		        	/*console.log($scope.subDocuments);*/
		        	//$scope.applications[0]=0;
		        }	  
	    	angular.forEach($scope.subDocuments, function(value, key) {
	    		  $scope.subdocument=value;
	    		  var type=$scope.subdocument.indexField.if_name;
	    		  switch(type){
	    		  case'petition':
	    			  $scope.petitions.push($scope.subdocument);
	    			  console.log("petitionssssssssssss",$scope.petitions);
	    			  
	    			  $scope.petitions.sort(function(a,b){
	    				  // Turn your strings into dates, and then subtract them
	    				  // to get a value that is either negative, positive, or zero.
	    				  return new Date(b.sd_submitted_date) - new Date(a.sd_submitted_date);
	    				});
	    			  
	    			  for(var i =0 ;i < $scope.petitions.length ;i++){
	    				  
	    				  if($scope.petitions[i].sd_rec_status==7){
		    					 /* $scope.petitions[i].indexField.if_label ='COMPLIANCE OF ORDER';*/	
		    					  $scope.petitions[i].indexField.if_label ='REVISED PETITION UNDER COURT ORDERS';
		    				  }
	    				  
	    				  if($scope.petitions[i].sd_rec_status==9 ){
	    				  $scope.petitions[i].indexField.if_label ='AMENDMENT PETITION';
	    				  }
	    				  if($scope.petitions[i].sd_rec_status==8 ){
		    				  $scope.petitions[i].indexField.if_label ='AMENDED MEMO PAGE';
		    				  }
	    			  }
	    			  
	    			  break;
	    		  case'rejoinder':
	    			  $scope.rejoinders.push($scope.subdocument);
	    			  break;
	    		  case'application':
	    			  $scope.applications.push($scope.subdocument);
	    			  break;
					case'olr':
	    			  $scope.applications.push($scope.subdocument);
	    			  break;
	    		  case'supp_affidavit':
	    			  $scope.supp_affidavits.push($scope.subdocument);
	    			  break;
	    		  case'coun_affidavit':
	    			  $scope.coun_affidavits.push($scope.subdocument);
	    			  break;
	    		  case'supp_coun_affidavit':
	    			  $scope.supp_coun_affidavits.push($scope.subdocument);
	    			  break;
	    		  case'supp_rejoinder':
	    			  $scope.supp_rejoinders.push($scope.subdocument);
	    			  break;
	    		  case'order_sheet':
	    			  $scope.order_sheets.push($scope.subdocument);
	    			  break;
					case'others':
	    			  $scope.others.push($scope.subdocument);
	    			  break;
					case'lower_court_record':
		    			  $scope.lower_court_record.push($scope.subdocument);
		    			  break;
		    		default:
		    			$scope.medSub.push($scope.subdocument);
		    		// $scope.order_sheets.push($scope.subdocument);
		    		 break;
	    		  }
	    		  
	    		  console.log($scope.medSub);
	    		});
	    	
	    	//generateRecallOrder();
	    	getOrdersFromElegalix();
	    	
	    	getOrderReports();
	    	$scope.petitionsData = [].concat($scope.petitions);
	        $scope.rejoindersData = [].concat($scope.rejoinders);
	        $scope.applicationsData = [].concat($scope.applications);
	        $scope.supp_affidavitsData = [].concat($scope.supp_affidavits);
	        $scope.coun_affidavitsData = [].concat($scope.coun_affidavits);
	        $scope.supp_coun_affidavitsData = [].concat($scope.supp_coun_affidavits);
	        $scope.supp_rejoindersData = [].concat($scope.supp_rejoinders);
			$scope.othersData = [].concat($scope.others);
			$scope.lower_court_recordData = [].concat($scope.lower_court_record);
			$scope.medSubData = [].concat($scope.medSub);
			
			
			/*console.log($scope.petitionsData);
			console.log($scope.applicationsData);*/
	        
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
	}
	
	function getCaseFileDetails(){
		$http.get(urlBase+'casefile/getcasefiledetails/'+$scope.doc_id).success(function (data) {
	    	$scope.casefile=data.modelData;
	    	$scope.case_type_label =$scope.casefile.caseType.ct_label;
	    	  
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
	}
	function getOrderReports(){
		$http.get(urlBase+'casefile/getorderreports/'+$scope.doc_id).success(function (data) {
	    	$scope.orderReports=data.modelList;	

	    	generateReportData();
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
	}
	
	$scope.medReports=[];
	$scope.medReportsData=[];
	getMedReports();
	function getMedReports(){
		$http.get(urlBase+'casefile/getmedreciept/'+$scope.doc_id).success(function (data) {
	    	$scope.medReports=data.modelList;	
	    	
	    	$scope.medReportsData = [].concat($scope.medReports);

	    	
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
	}
	
	
/*	function generateReportData(){
		$scope.orderData=[];
		

		console.log($scope.orderReports);
		
		$scope.orderData = $sce.trustAsHtml($scope.orderData.ord_remark);
		
		
		angular.forEach($scope.order_sheets, function(value, key) {
			
			$scope.orderData = $sce.trustAsHtml($scope.orderData.ord_remark);
			$scope.ordermodel={'sd_id':value.sd_id,'document_type':value.documentType.at_description,'sd_created_date':value.sd_cr_date,'sd_submitted_date':value.sd_submitted_date,'sd_party':value.sd_party,'sd_description':value.sd_description,'ord_remark':'','checked':false};
			
			
			//$scope.ordermodel={'sd_id':value.sd_id,'document_type':value.documentType.at_description,'sd_created_date':value.sd_cr_date,'sd_submitted_date':value.sd_submitted_date,'sd_party':value.sd_party,'sd_nonmaintainable':value.sd_nonmaintainable,'sd_description':value.sd_description,'ord_remark':'','checked':false};
			$scope.ordermodel={'sd_id':value.sd_id,'document_type':value.documentType.at_description,'sd_created_date':value.sd_cr_date,'sd_submitted_date':value.sd_submitted_date,'sd_party':value.sd_party,'sd_nonmaintainable':value.sd_nonmaintainable,'sd_description':value.sd_description,'ord_remark':'','checked':value.checked,'checkBoxValue':
				value.checkBoxValue};
			$scope.orderData.push($scope.ordermodel);
			
		});
		
		angular.forEach($scope.orderReports, function(value, key) {
			var sd_id=null;
			if(value[0].subDocument!=null)
				sd_id=value[0].subDocument.sd_id;
				$scope.ordermodel={'sd_id':sd_id,'document_type':'Off. Rep.','sd_created_date':value[0].ord_created,'sd_submitted_date':value[0].ord_created,'sd_party':'','sd_description':'','ord_remark': $sce.trustAsHtml(value[0].ord_remark),'ord_consignment_no':value[0].ord_consignment_no==null?'': value[0].ord_consignment_no,'consignmentno':value[0].ord_consignment_no==null?'':'Consignment_No:-', 'um_fullname':'Submitted By:-  '+value[1].um_fullname};
				$scope.orderData.push($scope.ordermodel);
				console.log($scope.orderData)
				
				
				
			});
		//alert(JSON.stringify($scope.orderData));
		$scope.orderDataList = [].concat($scope.orderData);
		
		console.log("orderData-----");
		console.log($scope.orderData);
		
	}*/
	
	
	
	function generateReportData(){
		$scope.orderData=[];
		var i=0;
		var crDate=null;
		
      angular.forEach($scope.medSub, function(value, key) {

			

			$scope.orderData.push(value);
			});
		

		angular.forEach($scope.order_sheets, function(value, key) {

		/*$scope.orderData = $sce.trustAsHtml($scope.orderData.ord_remark);*/
		/*$scope.ordermodel={'sd_id':value.sd_id,'document_type':value.documentType.at_description,'sd_created_date':value.sd_cr_date,'sd_submitted_date':value.sd_submitted_date,'sd_party':value.sd_party,'sd_description':value.sd_description,'ord_remark':'','checked':false};*/

		if(value.documentType.at_description=="St. Rep." && i==0)
		{
		crDate=value.sd_submitted_date;
		$scope.stFile=value.sd_id;
		i++;
		}

		$scope.ordermodel={'sd_id':value.sd_id,'document_type':value.documentType.at_description,'sd_created_date':value.sd_cr_date,'sd_submitted_date':value.sd_submitted_date,'sd_stamp_date':value.sd_submitted_date,'sd_party':value.sd_party,'sd_nonmaintainable':value.sd_nonmaintainable,'sd_description':value.sd_description,'ord_remark':'','checked':false,'judgmentID':value.judgmentID};
		if(value.documentType.at_description=="St. Rep." && $scope.orderData.length >1)
		{
			$scope.stFile=value.sd_id;
			
		$scope.ordermodel.sd_submitted_date=crDate;
		i++;
		}

		$scope.orderData.push($scope.ordermodel);
		});
		angular.forEach($scope.orderReports, function(value, key) {
		var sd_id=null;
		if(value[0].subDocument!=null)
		sd_id=value[0].subDocument.sd_id;
		$scope.ordermodel={'sd_id':sd_id,'document_type':'Off. Rep.','sd_created_date':value[0].ord_created,'sd_submitted_date':value[0].ord_created,'sd_party':'','sd_description':'','ord_remark': $sce.trustAsHtml(value[0].ord_remark),'ord_consignment_no':value[0].ord_consignment_no==null?'': value[0].ord_consignment_no,'consignmentno':value[0].ord_consignment_no==null?'':'Consignment_No:-', 'um_fullname':'Submitted By:- '+value[1].um_fullname};
		$scope.orderData.push($scope.ordermodel);
		/*console.log($scope.orderData)*/



		});
		//alert(JSON.stringify($scope.orderData));
		$scope.orderDataList = [].concat($scope.orderData);

		console.log("orderData-----");
		console.log($scope.orderData);
		}
	
	
	function getOrdersFromElegalix(){
		$http.get(urlBase+'casefile/getOrdersFromElegalix/'+$scope.doc_id).success(function (data) {
			console.log("Orderssssssssss",data);
	    	$scope.orderFromElegalix=data.modelList;
	    	
	    	for(var i =0 ; i< $scope.orderFromElegalix.length ; i++ ){
				$scope.order_sheets.push($scope.orderFromElegalix[i]);
			}
	    	
	    	generateReportData();
	    	
	    	console.log("Data from Elegalix-----------------",$scope.orderFromElegalix);
	    	  
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
		
	}
	
	
	
	function generateRecallOrder(){
		$scope.recallOrderData=[];
		angular.forEach($scope.applications, function(value, key) {
			console.log($scope.applications[key]);
			if($scope.applications[key].documentType.at_name=="RECALL OF ORDER"){
			$scope.recallOrdermodel={'sd_id':value.sd_id,'sd_document_no':value.sd_document_no,'sd_document_year':value.sd_document_year,'document_type':value.documentType.at_description,'sd_created_date':value.sd_cr_date,'sd_submitted_date':value.sd_submitted_date,'sd_party':value.sd_party,'sd_description':value.sd_description};
			$scope.recallOrderData.push($scope.recallOrdermodel);
			}
		});
		//alert(JSON.stringify($scope.orderData));
		//$scope.orderDataList = [].concat($scope.orderData);
		
		console.log("orderData-----"+$scope.recallOrderData);
		console.log($scope.recallOrderData);
	}
	
	
	$scope.colorNotChange = function()
	{
		
	}
	
	$scope.showSubDocument=function(sd_id,$index,doc_type){
		document.getElementById('outlineView').innerHTML = '';
		console.log("subDocument"+$scope.subDocuments);
		if(doc_type.valueOf()[$index].checked==false){
		    $scope.personColour = { 'color': 'blue' };
		    doc_type.valueOf()[$index].checked=true;
		}
		if(doc_type.valueOf()[$index].checkBoxValue ==false){
			doc_type.valueOf()[$index].checkBoxValue= true;
		}
		
		
		/*console.log(sd_id);
		console.log($scope.subDocuments);
		
		if(doc_type.valueOf()[$index].checked==false){
		    $scope.personColour = { 'color': 'blue' };
		    doc_type.valueOf()[$index].checked=true;
		}*/
		
		
/*		if(doc_type=='St. Rep.' || doc_type=='ORD' || doc_type=='Off. Rep.')
		if($scope.orderData[$index].isColor==false){
		    $scope.personColour = { 'color': 'blue' };
		    $scope.orderData[$index].isColor=true;
		}
		
		if(doc_type=='petition'){
			if($scope.petitionsData[$index].checked==false){
			    $scope.personColour = { 'color': 'blue' };
			    $scope.petitionsData[$index].checked=true;
			}
			}
		
		if(doc_type=='coun_affidavit'){
			if($scope.coun_affidavits[$index].checked==false){
			    $scope.personColour = { 'color': 'blue' };
			    $scope.petitionsData[$index].checked=true;
			}
			}*/
		
		
		
		$http.get(urlBase+'casefile/subdocument/'+sd_id).success(function (data) {
	    	$scope.sample=data.modelData;
	    	DEFAULT_URL=urlBase+'uploads/' +data.modelData.document_name+".pdf";
	    //	DEFAULT_URL="192.168.0.162:8080/elegalix_restapi/api/judgment/8042762";
			console.log($scope.document_name);
			PDFViewerApplication.open(DEFAULT_URL);
			clearInterval(myVar1);
			count =0;
			console.log("ppppppppppppppppppppppp----------"+DEFAULT_URL);
			myFunction();
			//showBookMark();
			
			if(!myVar1){
			
				myVar1 =setInterval(booMarkShowFunction, 600);
				
				}
			else{
			
				myVar1 =setInterval(booMarkShowFunction, 600);
			}
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });

	};
	
	$scope.showStampReport=function(sd_id){
		$http.get(urlBase+'casefile/subdocument/'+sd_id).success(function (data) {
	    	$scope.sample=data.modelData;
	    	DEFAULT_URL=urlBase+'uploads/' +data.modelData.document_name+".pdf";
	    //	DEFAULT_URL="192.168.0.162:8080/elegalix_restapi/api/judgment/8042762";
			console.log($scope.document_name);
			PDFViewerApplication.open(DEFAULT_URL);
			clearInterval(myVar1);
			count =0;
			console.log("ppppppppppppppppppppppp----------"+DEFAULT_URL);
			myFunction();
			//showBookMark();
			
			if(!myVar1){
			
				myVar1 =setInterval(booMarkShowFunction, 600);
				
				}
			else{
			
				myVar1 =setInterval(booMarkShowFunction, 600);
			}
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
		
	}
	
	$scope.showOrderFromElegalix=function(sd_id,$index,data){
		
		console.log("subDocument",data);
		document.getElementById('outlineView').innerHTML = '';
		
		if(data.checked==false){
		    $scope.personColour = { 'color': 'blue' };
		    data.checked=true;
		}
		if(data.checkBoxValue ==false){
			data.checkBoxValue= true;
		}
		
		
		
		
		
		
		
		$http.get(urlBase+'casefile/getOrderFromElegalix/'+data.judgmentID).success(function (data) {
	    	$scope.sample=data.modelData;
	    	DEFAULT_URL=urlBase+'uploads/' +data.modelData.document_name+".pdf";
	    //	DEFAULT_URL="192.168.0.162:8080/elegalix_restapi/api/judgment/8042762";
			console.log($scope.document_name);			
			PDFViewerApplication.open(DEFAULT_URL);
			clearInterval(myVar1);
			count =0;
			console.log("ppppppppppppppppppppppp----------"+DEFAULT_URL);
			myFunction();
			//showBookMark();
			
			if(!myVar1){
			
				myVar1 =setInterval(booMarkShowFunction, 600);
				}
			else{
			
				myVar1 =setInterval(booMarkShowFunction, 600);
			}
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });

	};
	
	
	
	
	
	$scope.applicationTypeList=[];
	
	 function getApplications(){
		  $http.get(urlBase+'master/getApplicationsTypeList').success(function (data) {
		    		$scope.applicationTypeList=data.modelList;		    	
		    	
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting casetypes");
		      });
	  }
	 $scope.index_fields=[];
	  function getIndexFields(){
		  $http.get(urlBase+'master/getindexfields').success(function (data) {
		    		$scope.index_fields=data.modelList;		    	
		    	
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting indexfields");
		      });
	  }
	
	$scope.editSubdocument={};
	  $scope.editsubdocument=function(subdocumentData) 
	  {
		  getIndexFields();
		  getApplications();
		  $scope.editSubdocument = subdocumentData;
		  $scope.formattedDate =   $filter('date')($scope.editSubdocument.sd_submitted_date, "dd-MM-yyyy");
		  $scope.editSubdocument.sd_submitted_date =$scope.formattedDate;	
	  }	
	  
	  $scope.docData = {};
	  $scope.editSubDoc=function(documentData) 
	  {
		  $scope.docData.sd_id = documentData.sd_id;
		  $scope.docData.sd_document_id = documentData.sd_document_id;
		  var response =$http.post(urlBase+'casefile/editSubDoc',$scope.docData);
			response.success(function(data, status, headers, config){
				   if(data.response=="TRUE")
				   {
					$('#editsubDocument').modal('hide');
					alert("Application Update Successfully");
					window.location.reload();
				   }
				   else
				   {
					  $('#editsubDocument').modal('hide');
					  alert("Application is not update");
					  window.location.reload();
				   }
		
				
			});
		  
	  }
	  
	
	
	
	 
	$scope.viewAllOrders=function(){
		//window.open(urlBase+'casefile/viewdocument/'+sd_id,'_blank');
		/*window.open(urlBase+'casefile/vieworders/'+$scope.doc_id,'_self');*/
		
		$http.get(urlBase+'casefile/vieworders1/'+$scope.doc_id).success(function (data) {
	    	$scope.sample=data.modelData;
	    	console.log("Model Dataaaaaaa",data);
	    	DEFAULT_URL=urlBase+'uploads/' +data.modelData.document_name+".pdf";
	    //	DEFAULT_URL="192.168.0.162:8080/elegalix_restapi/api/judgment/8042762";
			console.log($scope.document_name);
			PDFViewerApplication.open(DEFAULT_URL);
			clearInterval(myVar1);
			count =0;
			console.log("ppppppppppppppppppppppp----------"+DEFAULT_URL);
			myFunction();
			//showBookMark();
			
			if(!myVar1){
			
				myVar1 =setInterval(booMarkShowFunction, 600);
				
				}
			else{
			
				myVar1 =setInterval(booMarkShowFunction, 600);
			}
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
		
	};
	
	$scope.downloadfiles=function(){
		//window.open(urlBase+'casefile/viewdocument/'+sd_id,'_blank');
		window.open(urlBase+'casefile/download/'+$scope.doc_id,'_self');
	};
	
	$scope.showHighCourtCase=function(io_id){
		//window.open(urlBase+'casefile/viewdocument/'+sd_id,'_blank');
		window.open(urlBase+'casefile/impugnedorder/'+io_id,'_self');
	};
	
	$scope.trackReport=function(ord_id){
		window.open('https://www.indiapost.gov.in/MBE/Pages/Content/Speed-Post.aspx', '_blank');
	};
	
	$scope.caseTab = function(id,$index,doc_type) 
    {
		persistLinkOnNewTab(id,$index,doc_type);
      window.open(urlBase+'casefile/subdocumentTab/'+id, '_blank');
      
    /*  if(doc_type.valueOf()[$index].checked==false){
		    $scope.personColour = { 'color': 'blue' };
		    doc_type.valueOf()[$index].checked=true;
		}*/
      
     };
	
    
     $scope.saveNotes = function(nt) {
    	 console.log("in");
    	 console.log(nt);
    	 console.log($scope.doc_id);
    	 $scope.nt = nt;
    	 
    	 if(nt.nt_notes!=null)
    		 {
    		 $scope.notesdata={
    				 'nt_notes':nt.nt_notes,
    				 'nt_fd_mid':$scope.doc_id
    		 };
    		 debugger;
				console.log($scope.notesdata);
    		 }
    	 
    	 var response = $http.post(urlBase+ 'casefile/NotesSave',$scope.notesdata);
    	 	response.success(function(data) {
    		 console.log("***Notes Is saved***");
				if (data.response == "FALSE") {
					$scope.errorlist = data.dataMapList;
				} 
				else {
					$scope.errorlist = [];
					/*alert("Notes Save Successfully!");*/
					getMasterdata();

				}
			})
};


$scope.openNav=function (){
	
	document.getElementById("sidebar").style.zIndex = "1";
	document.getElementById("header").style.zIndex = "2";
	document.getElementById("mainContainer").style.zIndex = "1";
	
		console.log($scope.doc_id);
	
	
	    $http.get(urlBase+'casefile/getnotes/'+$scope.doc_id).success(function (data) {
	    	$scope.nt=data.modelData;	
	    	console.log($scope.nt);
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });
	
	
	}


$scope.getNotification=function(casefile){
	$scope.notifications=angular.copy(casefile);
}

$scope.getPendingApp=function(casefile){
	$scope.pendingApp=angular.copy(casefile);
}


/*$scope.changeColor = function($index) {
	if($scope.orderData[$index].isColor==false){
    $scope.personColour = { 'color': 'blue' };
    $scope.orderData[$index].isColor=true;
}
                           // or 'background-color' whatever you what
}

$scope.recoverColor = function() {
    $scope.personColour = {};
}*/
    	

/* ------------------ Sushant----------------------------*/
     

$scope.showdemo = function(data) {
	console.log("ordersssssssssssssssss",data);
	if (data.sd_id != null) {
		$http
				.get(
						urlBase + 'casefile/Getfilename/'
								+ data.sd_id)
				.success(
						function(data) {
							$scope.subdocumentdetails = data.modelData;
							$('.myform')
									.append(
											'<div id="myModal" class="modal-content" style="z-index: 10008; left: 30%; top: 0%;"><div id="myModalHeader" class="modal-header" style="background-color: #00acac;padding: 10px;"><button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color:red;width:20px;"><span aria-hidden="true">&times;</span></button><h5 class="modal-title" id="myModalLabel"><strong style="color: #FBFCFD;">'+$scope.subdocumentdetails.documentType.at_name+'</strong></h5></div><div class="modal-body"><object type="application/pdf" data="'
													+ urlBase
													+ 'uploads/'	
													+ $scope.subdocumentdetails.sd_document_name
													+ '.pdf#toolbar=1&navpanes=0&scrollbar=0'
													+ '" width="100%" height="90%"></object></div></div>');
							$('.modal-content').draggable({
					            handle: ".modal-header",
					            containment:'window',
					        });
						})
				.error(
						function(data, status, headers,
								config) {
							console
									.log("Error in getting Report ");
						});
	}
	else if (data.judgmentID != null ){
		
		/*$http.get(urlBase+'casefile/getOrderFromElegalix/'+data.judgmentID).success(function (data) {
	    	$scope.sample=data.modelData;
	    	DEFAULT_URL=urlBase+'uploads/' +data.modelData.document_name+".pdf";
	    //	DEFAULT_URL="192.168.0.162:8080/elegalix_restapi/api/judgment/8042762";
			console.log($scope.document_name);
			PDFViewerApplication.open(DEFAULT_URL);
			clearInterval(myVar1);
			count =0;
			console.log("ppppppppppppppppppppppp----------"+DEFAULT_URL);
			myFunction();
			//showBookMark();
			
			if(!myVar1){
			
				myVar1 =setInterval(booMarkShowFunction, 600);
				}
			else{
			
				myVar1 =setInterval(booMarkShowFunction, 600);
			}
	      }).
	      error(function(data, status, headers, config) {
	      	console.log("Error in getting sub documents");
	      });*/
		
		$http
		.get(
				urlBase + 'casefile/getOrderFromElegalix/'
						+ data.judgmentID)
		.success(
				function(data) {
					$scope.subdocumentdetails = data.modelData;
					 console.log("aaaaaaaaaaaaaaaaaaaaaaaaa",$scope.subdocumentdetails);
					$('.myform')
							.append(
									'<div id="myModal" class="modal-content" style="z-index: 10008; left: 30%; top: 0%;"><div id="myModalHeader" class="modal-header" style="background-color: #00acac;padding: 10px;"><button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color:red;width:20px;"><span aria-hidden="true">&times;</span></button><h5 class="modal-title" id="myModalLabel"><strong style="color: #FBFCFD;">'+'ORDER'+'</strong></h5></div><div class="modal-body"><object type="application/pdf" data="'
											+ urlBase
											+ 'uploads/'	
											+ $scope.subdocumentdetails.document_name
											+ '.pdf#toolbar=0&navpanes=0&scrollbar=0'
											+ '" width="100%" height="90%"></object></div></div>');
					$('.modal-content').draggable({
			            handle: ".modal-header",
			            containment:'window',
			        });
				})
		.error(
				function(data, status, headers,
						config) {
					console
							.log("Error in getting Report ");
				});
	}
};

/*$scope.changeTop = function (){
	
	console.log("top changeeeeeeeeeeeeeeeeeeeeeeeees",document.getElementById("pdfToolBar").style.top);
	document.getElementById("pdfToolBar").style.top = -15+'px';
}*/

	
});