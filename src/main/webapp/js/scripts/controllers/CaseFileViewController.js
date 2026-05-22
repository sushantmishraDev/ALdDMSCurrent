var EDMSApp = angular.module("EDMSApp", ['smart-table']);


EDMSApp.controller("CaseFileCtrl",	function($scope, $sce, $http,$filter) {
	var urlBase="/dms/";
	$scope.subDocuments=[];
	$scope.metadata=[];
	$scope.doc_id= $('#doc_id').val();
	//$scope.judgmentdocId= $('#judgmentdocId').val();
	
	//alert(2);
	$scope.document_name= $('#document_name').val();
	//alert($scope.fd_file_bar_code);
	var url = urlBase+'uploads/' + $scope.document_name+".pdf";
	 DEFAULT_URL = url;
	 //PDFViewerApplication.open(DEFAULT_URL);
	 
	 PDFViewerApplication.open({
		  url: DEFAULT_URL
		});
	
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
	
	
	$scope.icon= $sce.trustAsHtml('<i class="fa fa-stack-overflow" style="color:red;font-size:34px" aria-hidden="true"></i>');
	
	/*function getHeaderHeight(){
		
		var obj =document.getElementById("headerHeigh");
		console.log("objjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj",obj);
	}
	getHeaderHeight();*/
	
	
	$scope.setReservedCase=function(id){
		  $http.get(urlBase+'reserve/reservedCase/'+id).success(function (data) {
			  $scope.casefile=data.modelData;
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting tree data");
		      });
		}
	
	
	$scope.changeApplicationStatus=function(data){
		
		console.log("Dataaaaaaaaaaa",data);
		
		var response =$http.post(urlBase+'casefile/changeApplicationStatus',data);
		response.success(function(data, status, headers, config){
			   if(data.response=="TRUE")
			   {
				
				   console.log("Dataaaaaaaaaaaaaaaa",data);
			   }
			   else
			   {
				 
			   }
	
			
		});
		 /* $http.get(urlBase+'reserve/reservedCase/'+id).success(function (data) {
			  $scope.casefile=data.modelData;
		      }).
		      error(function(data, status, headers, config) {
		      	console.log("Error in getting tree data");
		      });*/
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
	    	
	    	//console.log("outlineView",outlineView);
	    
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
	    	//console.log($scope.subDocuments);
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
	    			  //console.log("petitionssssssssssss",$scope.petitions);
	    			  
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
	    		  
	    		 // console.log($scope.medSub);
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
	    	
	    	console.log("fffffffffffff",$scope.case_type_label);
	    	
	    	
	    	
	    	  
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
		i++;
		}

		$scope.ordermodel={'sd_id':value.sd_id,'document_type':value.documentType.at_description,'sd_created_date':value.sd_cr_date,'sd_submitted_date':value.sd_submitted_date,'sd_stamp_date':value.sd_submitted_date,'sd_party':value.sd_party,'sd_nonmaintainable':value.sd_nonmaintainable,'sd_description':value.sd_description,'ord_remark':'','checked':false,'judgmentID':value.judgmentID};
		if(value.documentType.at_description=="St. Rep." && $scope.orderData.length >1)
		{
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
		//$scope.orderData.push($scope.medSub);
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
			console.log(PDFViewerApplication.pdfSidebar.isOpen);
			/*PDFViewerApplication.pdfSidebar.*/
			/*PDFViewerApplication.pdfSidebar.isOpen=true;
			console.log(PDFViewerApplication.pdfSidebar.isOpen);*/
			//PDFViewerApplication.open(DEFAULT_URL);
			PDFViewerApplication.open({
				  url: DEFAULT_URL
				});
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
		window.open(urlBase+'casefile/vieworders/'+$scope.doc_id,'_self');
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
	console.log("noti");
	console.log(casefile);
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