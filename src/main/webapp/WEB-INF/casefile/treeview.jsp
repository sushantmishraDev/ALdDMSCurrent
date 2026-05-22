

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ page import="com.dms.model.ObjectMaster"%>
<%@ page import="com.dms.model.User"%>

 <%
User user = null;
if (session.getAttribute("USER") != null)
	user = (User) session.getAttribute("USER");
 String role = user.getUserroles().get(0).getLk().getLk_longname();
%>

<style>



.order{
background: #c3e485;
}

.ofr{
background: #93cfe5;
}



.str{
background: #e47c77;
}

/* .str{
background: #f0f3f5;
} */


.stay{
background: #61CBCA;
}

.exemption{
background: #D7B081;
}

.nomi{
background: #7AD277;
}

.med{
background: #C1D3AC;
}

.delay{
background: ;
}



#notification {
  /* background-color: #555; */
  color: white;
  text-decoration: none;
  padding: 5px 10px;
  position: relative; 
  display: inline-block; 
  border-radius: 2px;
}

#notification:hover {
  background: red;
}

#notification .badge {
  position: absolute;
  top: -2px;
  right: -10px;
  padding: 6px 10px;
  border-radius: 50%;
  background-color: red;
  color: white;   
}
.tooltip {
        border: 1px solid black;
        width: 230px;
        margin-left: 58px;
        margin-top: -35px;
        background-color: darkcyan;
        border-radius: 4px;
        height: 20px;
        color: white;
    
</style>

<c:if test="${isApplication==1}">
<div class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Application
					</a>
               </h3>
           </div>
           <div id="collapseThree" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;">
                   <div class="table-responsive">

						<table id="data-table" class="table table-striped table-bordered">
                           <thead>
                               <tr>
                                   <th>Type</th>
                                   <th>Name</th>
                                   <th>Counsel</th>
                                   
                                </tr>
                          </thead>
                                <tr>
                                 <td >${application_type} <br/> ${application_no}/ ${application_year} <br/> ${submitted_date}</td>
                                 <td>${name}</td>
                                 <td>${counsel}</td>
                                 
                         		</tr>
                		</table>
                </div>
          </div>
    </div>
</div>
</c:if>
<div class="panel-group" id="accordion" >
<div  ng-show="orderData.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseORDER">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Order / Office report
					</a>
               </h3>
           </div>
           <div id="collapseORDER" class="panel-collapse collapse in">
               <div class="panel-body" id ="order" style="padding:2px;max-height:450px;overflow:scroll;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="orderData" st-safe-src="orderDataList" style ="table-layout: fixed; width: 100%" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               	   <th style="width: 10%;">Sr.<br>No.</th>
                                   <th st-sort-default="true" st-sort="sd_submitted_date" style="width: 25%;" st-skip-natural="true">Type</th>
                                   <th st-sort="sd_description" style="width: 65%;">Description</th>
                                <!--    <th  st-sort="sd_counsel">Name</th> -->
                                   <!-- <th> New Tab</th> -->
                                </tr>
                                <tr ng-repeat="data in orderData" ng-style="data.checked==true?personColour:''" ng-class="data.document_type == 'ORD' ? 'order' : (data.document_type == 'Off. Rep.' ? 'ofr' : (data.document_type == 'NOMINATION' ? 'nomi' :(data.document_type == null ? 'med' :'str')))">
                                <td>{{$index+1}}</span></td>
                                 <td style="padding:10px 5px;width:35%">
                                 
                                 <!-- <span  ng-if="data.sd_id!=null" ng-click="showSubDocument(data.sd_id,$index,orderData)" style="text-decoration: underline;cursor:pointer;"><b>{{data.document_type}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></span>
                                 <span  ng-if="data.sd_id==null"><b>{{data.document_type}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></span>&nbsp &nbsp&nbsp &nbsp&nbsp &nbsp
                                  -->
                                  
                                   <span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>

                                 <span  ng-show="data.sd_nonmaintainable" ng-click="showSubDocument(data.sd_id,$index,orderData)" style="text-decoration: underline;cursor:pointer; color: red;"><b>{{data.document_type}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></span>
                                 <!-- <span ng-hide="data.sd_nonmaintainable"  ng-if="data.sd_id!=null" ng-click="showSubDocument(data.sd_id,$index,orderData)" style="text-decoration: underline;cursor:pointer;"><b>{{data.document_type}}
                                 <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></span> -->
                                 
                                 
                                  <span ng-hide="data.sd_nonmaintainable" ng-if="data.sd_id!=null  && data.document_type!='St. Rep.'"" ng-click="showSubDocument(data.sd_id,$index,orderData)" style="text-decoration: underline;cursor:pointer;"><b>{{data.document_type !=null ? data.document_type : data.indexField.if_name}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></span>
                                  <span ng-hide="data.sd_nonmaintainable" ng-if="data.judgmentID != null" ng-click="showOrderFromElegalix(data.sd_id,$index,data)" style="text-decoration: underline;cursor:pointer;"><b>{{data.document_type}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></span>
                                  <span ng-hide="data.sd_nonmaintainable" ng-if="data.sd_id!=null && data.document_type=='St. Rep.'"" ng-click="showSubDocument(data.sd_id,$index,orderData)" style="text-decoration: underline;cursor:pointer;"><b>{{data.document_type}} <br/> {{data.sd_stamp_date | date:'dd-MM-yyyy'}}</b></span>
                                  
                                 
                                  
                                  <br>
                                 <span  ng-if="data.sd_id==null &&  data.judgmentID == null " ><b >{{data.document_type}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></span>
                                 <br>
                                 <% if(!role.equals("Stamp_Reporter") && (!role.equals("Assistant Review Officer")) && (!role.equals("Review_Officer"))) {%>
                                                                   <span  ng-if="data.sd_id!=null || data.judgmentID != null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                                                    <% }%>
                                                                    
                                                                   
                                                                  <!--  <span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data.sd_id)" ></span></span> -->
                                                                  
                                
                                 
                                 
                                 
                                  <!-- <span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-plus" ng-click="showdemo(data.sd_id)" ></span></span> -->
                                  <span  ng-if="data.sd_id!=null "><span  style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,orderData)" ></span></span>
                                 
                                <!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->  
                                 </td>
                                 
                                  <td  style="text-align:justify ; overflow-x:auto">
                                  <span ng-if="data.ord_consignment_no">
                                  	<span  ng-click="trackReport(data.ord_id)" style = "text-decoration: underline;cursor:pointer;" ><b>{{data.consignmentno}}</b><br/></span>
                                  	<span  style = "background-color: #ffffcc" "text-decoration: underline;cursor:pointer;"><b>{{data.ord_consignment_no}}</b><br/></span>
                                  	</span>
                                  	<!-- <span> <br>{{data.ord_remark}}<br><br><b>{{data.um_fullname}}</br></b></span> -->
                                  	<span><div ng-bind-html="data.ord_remark" ></div><br><br><b>{{data.um_fullname!="Submitted By:- Administrator" ? data.um_fullname :" "}}</br></b></span>
                                  </td>
<!--                                  <td  style="text-align:justify"> {{data.ord_remark}}<br></td> -->
                               <!--   <td>{{data.sd_description}}</td> -->
                                 <!-- <td><i ng-click="caseTab(data.sd_id)" class="fa fa-plus-circle" aria-hidden="true"></i></td> -->
                         </tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>
<div  ng-show="petitions.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Petition
					</a>
               </h3>
               
           </div>
           <div id="collapseOne" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="petitions" st-safe-src="petitionsData" class="table table-striped table-bordered">
                      <!--    <table id="data-table" st-table="petitions" st-safe-src="petitionsData" class="table table-striped table-bordered"> -->
                           <thead>
                               	<tr>
                               	   <th style="width: 2%;">Sr.<br>No.</th> 
                                   <th style="width: 56%" st-sort="sd_submitted_date">Type</th>
                                   <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th>
                                </tr>
                                
                                <tr >
                                 <!-- <td ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.indexField.if_label}} <br/>  {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>
                                  -->
                                   <td>{{$index+1}}</td>
                                   <td style="padding:10px 5px;width:35%">
                                   <div ng-repeat="data in petitions" ng-style="data.checked==true?personColour:''" style ="margin-bottom:10px">
                                 
                                   
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,petitionsData)" style="text-decoration: underline;cursor:pointer;">
                                 	<b>{{data.indexField.if_label}} <br/>  {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b>
                                 	<br>
                                 	</span>
                                 	<span ng-repeat="awp in data.applicationWithPetition | orderBy: 'awp_cr_date'" style="text-decoration: none;">{{awp.applicationType.at_name}}<br>{{awp.awp_ap_no}}/{{awp.awp_ap_year}},<br></span>
                                 	
                                 	<span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,petitions)" ></span></span>
                                 	<br>
                                 	<!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->
                                 	
                                 	
                                 	</div>
                                 </td>
                                 
                                 <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td>
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>


<div ng-show="medReports.length > 0"   class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseTwentyFive">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Mediation
					</a>
               </h3>
           </div>
           <div id="collapseTwentyFive" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="medReports" st-safe-src="medReportsData" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               	  <th style="width: 2%;">Sr.<br>No.</th>
                                   <th st-sort="sd_submitted_date" st-skip-natural="true">Type</th>
                                   <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th>
                                </tr>
                                <tr ng-repeat="data in medReports">
                                <!--  <td ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.indexField.if_label}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>
                                 -->
                                  <td>{{$index+1}}</td>
                                 <td style="padding:10px 5px;width:35%">
                                 <span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                 
                                 	<span  ng-click="showMedReport(data.mdn_id,$index,medReportsData)" style="text-decoration: underline;cursor:pointer;"><b>Mediation Receipt
                                	 <br/> {{data.mdn_transaction_no}}  <br/> {{data.mdn_pay_date | date:'dd-MM-yyyy'}},</b></span>
                                	 <br>
                                	
									<br>
									
                                 	<!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->
                                 </td>
                                 <td style="text-align:justify">{{data.mdn_remark}}</td>
                                 <td></td>
                         </tr>
                      <!--    
                          <tr ng-repeat="data in medSub" ng-style="data.checked==true?personColour:''">
                                 <td ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.indexField.if_label}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>
                                
                                  <td>{{$index+1}}</td>
                                 <td style="padding:10px 5px;width:35%">
                                 <span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                 
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,medSubData)" style="text-decoration: underline;cursor:pointer;"><b>{{data.indexField.if_name}}
                                	  <br/> {{data.sd_cr_date | date:'dd-MM-yyyy'}},</b></span>
                                	 <br>
									<br>
									<span  ng-if="data.sd_id!=null"><span  style="float: center;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,medSub)" ></span></span>
                                 	<br>
                                 	<span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span>
                                 </td>
                                 <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td>
                         </tr> -->
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>




<div ng-show="coun_affidavits.length > 0"  ng-show="coun_affidavits.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Counter
					</a>
               </h3>
           </div>
           <div id="collapseFive" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="coun_affidavits" st-safe-src="coun_affidavitsData" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               	  <th style="width: 2%;">Sr.<br>No.</th>
                                   <th st-sort="sd_submitted_date" st-skip-natural="true">Type</th>
                                   <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th>
                                </tr>
                                <tr ng-repeat="data in coun_affidavits" ng-style="data.checked==true?personColour:''">
                                <!--  <td ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.indexField.if_label}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>
                                 -->
                                  <td>{{$index+1}}</td>
                                 <td style="padding:10px 5px;width:35%">
                                 <span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                 
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,coun_affidavitsData)" style="text-decoration: underline;cursor:pointer;"><b>{{data.indexField.if_label}}
                                	 <br/> {{data.sd_document_no}} /{{data.sd_document_year}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}},</b></span>
                                	 <br>
                                	 <span ng-repeat="sb in data.subApplications"  style="text-decoration: underline;cursor:pointer;" ng-click="showSubDocument(data.sd_id,$index,coun_affidavitsData)">
										{{sb.applicationType.at_name}}<br>{{sb.sb_ap_no}}/{{sb.sb_ap_year}},<br>
									</span>
									<br>
									<span  ng-if="data.sd_id!=null"><span  style="float: center;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,coun_affidavits)" ></span></span>
                                 	<br>
                                 	<!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->
                                 </td>
                                 <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td>
                         </tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>
<div  ng-show="rejoinders.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Rejoinder
					</a>
               </h3>
           </div>
           <div id="collapseTwo" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="rejoinders" st-safe-src="rejoindersData" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               		<th style="width: 2%;">Sr.<br>No.</th>
                                   <th st-sort="sd_submitted_date" st-skip-natural="true">Type</th>
                                   <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th>
                                </tr>
                                <tr ng-repeat="data in rejoinders" ng-style="data.checked==true?personColour:''">
                                 <!-- <td  ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.indexField.if_label}}  {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>
                                  -->
                                   <td>{{$index+1}}</td>
                                  <td style="padding:10px 5px;width:35%">
                                  <span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                  
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,rejoindersData)" style="text-decoration: underline;cursor:pointer;">
                                 	<b>{{data.indexField.if_label}} 
                                 	<br/> {{data.sd_document_no}}/{{data.sd_document_year}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}},</b></span>
                                 	<br>
									<span ng-repeat="sb in data.subApplications"  style="text-decoration: underline;cursor:pointer;" ng-click="showSubDocument(data.sd_id,$index,rejoindersData)">
										{{sb.applicationType.at_name}}<br>{{sb.sb_ap_no}}/{{sb.sb_ap_year}},<br>
									</span>
                                 	<br>
                                 	<span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,rejoinders)" ></span></span>
                                 	<br>
                                 	<!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->
                                 </td>
                                 <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td>
                         </tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>
<div  ng-show="supp_affidavits.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Supplimentary
					</a>
               </h3>
           </div>
           <div id="collapseFour" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="supp_affidavits" st-safe-src="supp_affidavitsData" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               		<th style="width: 2%;">Sr.<br>No.</th>
                                   <th st-sort="sd_submitted_date" st-skip-natural="true">Type</th>
                                   <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th>
                                </tr>
                                <tr ng-repeat="data in supp_affidavits" ng-style="data.checked==true?personColour:''">
                                 <!-- <td  ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.indexField.if_label}}  {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>
                                  -->
                                   <td>{{$index+1}}</td>
                                  <td style="padding:10px 5px;width:35%">
                                 	 <span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,supp_affidavitsData)" style="text-decoration: underline;cursor:pointer;">
                                 	<b>{{data.indexField.if_label}}  
                                 	<br/> {{data.sd_document_no}} /{{data.sd_document_year}} <br>  {{data.sd_submitted_date | date:'dd-MM-yyyy'}},</b>
                                 	</span><br>
									<span ng-repeat="sb in data.subApplications"  style="text-decoration: underline;cursor:pointer;" ng-click="showSubDocument(data.sd_id,$index,supp_affidavitsData)">
										{{sb.applicationType.at_name}}<br>{{sb.sb_ap_no}}/{{sb.sb_ap_year}},<br>
									</span>
									<br>
									<span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,supp_affidavits)" ></span></span>
                                 	<br>
                                 	<!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->
                                 </td>
                                 <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td>
                         </tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>

<div  ng-show="supp_coun_affidavits.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseSix">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Supplimentary Counter
					</a>
               </h3>
           </div>
           <div id="collapseSix" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="supp_coun_affidavits" st-safe-src="supp_coun_affidavitsData" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               	   <th style="width: 2%;">Sr.<br>No.</th>
                                   <th st-sort="sd_submitted_date" st-skip-natural="true">Type</th>
                                   <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th>
                                </tr>
                                <tr ng-repeat="data in supp_coun_affidavits" ng-style="data.checked==true?personColour:''">
                                 <!-- <td  ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.indexField.if_label}}  {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>
                                  -->
                                   <td>{{$index+1}}</td>
                                   <td style="padding:10px 5px;width:35%">
                                   	<span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,supp_coun_affidavitsData)" style="text-decoration: underline;cursor:pointer;">
                                 	<b>{{data.indexField.if_label}} 
                                 	<br/> {{data.sd_document_no}} /{{data.sd_document_year}} <br> {{data.sd_submitted_date | date:'dd-MM-yyyy'}},</b>
                                 	</span><br>
									<span ng-repeat="sb in data.subApplications"  style="text-decoration: underline;cursor:pointer;" ng-click="showSubDocument(data.sd_id,$index,supp_coun_affidavitsData)">
										{{sb.applicationType.at_name}}<br>{{sb.sb_ap_no}}/{{sb.sb_ap_year}},<br>
									</span>
									<br>
									<span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,supp_coun_affidavits)" ></span></span>
                                 	<br>
                                 	<!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->
                                 </td>
                                 <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td>
                         </tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>

<div  ng-show="supp_rejoinders.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseSeven">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Supplimentary Rejoinder
					</a>
               </h3>
           </div>
           <div id="collapseSeven" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="supp_rejoinders" st-safe-src="supp_rejoindersData" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               	   <th style="width: 2%;">Sr.<br>No.</th>
                                   <th st-sort="sd_submitted_date" st-skip-natural="true">Type</th>
                                   <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th>
                                </tr>
                                <tr ng-repeat="data in supp_rejoinders" ng-style="data.checked==true?personColour:''">
                                 <!-- <td  ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.indexField.if_label}}  {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>
                                  -->
                                   <td>{{$index+1}}</td>
                                   <td style="padding:10px 5px;width:35%">
                                   	<span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,supp_rejoindersData)" style="text-decoration: underline;cursor:pointer;">
                                 		<b>{{data.indexField.if_label}}
                                 		<br/> {{data.sd_document_no}} /{{data.sd_document_year}} <br>  {{data.sd_submitted_date | date:'dd-MM-yyyy'}},</b>
                                 	 </span><br>									  
									<span ng-repeat="sb in data.subApplications"  style="text-decoration: underline;cursor:pointer;" ng-click="showSubDocument(data.sd_id,$index,supp_rejoindersData)">
										{{sb.applicationType.at_name}}<br>{{sb.sb_ap_no}}/{{sb.sb_ap_year}},<br>
									</span>
									<br>
									<span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,supp_rejoinders)" ></span></span>
                                 	<br>
                                 	<!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->
                                 </td>
                                 <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td>
                         </tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>


<div  ng-show="applications.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Misc. Applications
					</a>
               </h3>
           </div>
           <div id="collapseThree" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="applications" st-safe-src="applicationsData" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               	  <th style="width: 2%;">Sr.<br>No.</th>
                                   <th st-sort="sd_submitted_date" st-skip-natural="true">Type</th>
                                   <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th>
                                </tr>
                                 <tr ng-repeat="data in applications" ng-style="data.checked==true?personColour:''"  >
<!--                                                                   <tr ng-repeat="data in applications" ng-style="data.checked==true?personColour:''"  ng-class="data.documentType.at_name == 'STAY APPLICATION' ? 'stay' : (data.documentType.at_name == 'EXEMPTION' ? 'exemption': (data.documentType.at_name == 'CONDONATION OF DELAY' ? 'delay':'str'))">
 -->                                 
                              <!--   <tr ng-repeat="data in applications"> -->
                                <!--  <td  ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.documentType.at_name}} <br/> {{data.sd_document_no}} /{{data.sd_document_year}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>                                 
                                 -->
                                 <td>{{$index+1}}</td>
                                 <td style="padding:10px 5px;width:35%">
                                 	<span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,applicationsData)" style="text-decoration: underline;cursor:pointer;">
                                 	<b>{{data.documentType.at_name}}  <br/> {{data.sd_document_no}} /{{data.sd_document_year}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}},</b></span>
                                 	<br>
                                 	<span ng-repeat="sb in data.subApplications"  style="text-decoration: underline;cursor:pointer;" ng-click="showSubDocument(data.sd_id,$index,applicationsData)">
                                 		 {{sb.applicationType.at_name}}<br>{{sb.sb_ap_no}}/{{sb.sb_ap_year}},<br>
                                 	</span>
                                 	<br>
                                 	<span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,applications)" ></span></span>
                                 	
                                 	
                                 	
                                 	
                                 	
                                 	<span  ng-if="data.sd_id!=null && casefile.fd_file_source=='LKO  '"><span  style="left: 50%; position: relative;" class="glyphicon glyphicon-edit" ng-click="editsubdocument(data)" data-toggle="modal" data-target="#editsubDocument"></span></span>
                                 	<br>
                                 	
                                <!--  	for application stATUS
 -->                                 	<%--  <% if( (role.equals("Assistant Review Officer")) || (role.equals("Review_Officer")) || (role.equals("DMSAdmin"))) {%>
                                 	 <div ng-if= "case_type_label == 'CLRE' ">
                                                                    	<input type="radio" ng-model="data.sd_application_status" value="Disposed" ng-change='changeApplicationStatus(data)'>D
  <input type="radio" ng-model="data.sd_application_status" value="Pending" ng-change='changeApplicationStatus(data)'>P
  <input type="radio" ng-model="data.sd_application_status" value="For Orders" ng-change='changeApplicationStatus(data)'>FO
   </div>
                                                                    <% }%>
                                                                    
                                                                    <% if( (role.equals("ECOURT")) ) {%>
                                 	 <div ng-if= "case_type_label == 'CLRE' || case_type_label == 'COMP' || case_type_label == 'COPP' || case_type_label == 'MCOA' || case_type_label == 'COMA' || case_type_label == 'COMAD' ">
                                                               <span style="color : red;">{{data.sd_application_status}}</span>
   </div>
                                                                    <% }%> --%>
                                                                   
                               
  
 
                                 	<span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span>
                                 </td>
                                 <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td>
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>
<div  ng-show="others.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseEight">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Others
					</a>
               </h3>
           </div>
           <div id="collapseEight" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="others" st-safe-src="othersData" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               	  <th style="width: 2%;">Sr.<br>No.</th>
                                   <th st-sort="sd_submitted_date" st-skip-natural="true">Type</th>
                                   <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th>
                                </tr>
                                <tr ng-repeat="data in others" ng-style="data.checked==true?personColour:''">
                                 <!-- <td  ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.documentType.at_name}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>                                 
                                  -->
                                   <td>{{$index+1}}</td>
                                   <td style="padding:10px 5px;width:35%">
                                   <span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,othersData)" style="text-decoration: underline;cursor:pointer;">
                                 		<b>{{data.documentType.at_name}}  
                                 		<br/><br/> 
                                 		 {{data.sd_document_no}} /{{data.sd_document_year}} <br>{{data.sd_submitted_date | date:'dd-MM-yyyy'}},</b>
                                 	</span>
                                 	<br>
                                 	<span ng-repeat="sb in data.subApplications"  style="text-decoration: underline;cursor:pointer;" ng-click="showSubDocument(data.sd_id,$index,othersData)">
                                 		{{sb.applicationType.at_name}}<br>{{sb.sb_ap_no}}/{{sb.sb_ap_year}},<br>
                                 	</span>
                                 	<br>
                                 	<span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,others)" ></span></span>
                                 	<br>
                                 	<!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->
                                 </td> 
                                 <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td>
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>

<div  ng-show="lower_court_record.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseTwenty">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Lower Court Record(LCR)
					</a>
               </h3>
           </div>
           <div id="collapseTwenty" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="lower_court_record" st-safe-src="lower_court_recordData" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                               	  <th style="width: 2%;">Sr.<br>No.</th>
                                   <th st-sort="sd_submitted_date" st-skip-natural="true">Type</th>
                                   <!-- <th st-sort="sd_description">Name</th>
                                   <th  st-sort="sd_counsel">Counsel</th> -->
                                </tr>
                                <tr ng-repeat="data in lower_court_record" ng-style="data.checked==true?personColour:''">
                                 <!-- <td  ng-click="showSubDocument(data.sd_id)" style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><b>{{data.documentType.at_name}} <br/> {{data.sd_submitted_date | date:'dd-MM-yyyy'}}</b></td>                                 
                                  -->
                                   <td>{{$index+1}}</td>
                                   <td style="padding:10px 5px;width:35%">
                                   <span  style="float: right; top:" st-sort-default="true" st-sort="sd_submitted_date" st-skip-natural="true"></span>
                                 	<span  ng-click="showSubDocument(data.sd_id,$index,lower_court_recordData)" style="text-decoration: underline;cursor:pointer;">
                                 		<b>{{data.indexField.if_label}}  
                                 		
                                 		 </b>
                                 	</span>
                                 	
                                 	<span ng-repeat="sb in data.subApplications"  style="text-decoration: underline;cursor:pointer;" ng-click="showSubDocument(data.sd_id,$index,lower_court_recordData)">
                                 		{{sb.applicationType.at_name}}<br>{{sb.sb_ap_no}}/{{sb.sb_ap_year}},<br>
                                 	</span>
                                 	<br>
                                 	<span  ng-if="data.sd_id!=null"><span  style="float: left;" class="glyphicon glyphicon-folder-open" ng-click="showdemo(data)" ></span></span>
                                 	<span  ng-if="data.sd_id!=null"><span style="float: right;" class="glyphicon glyphicon-new-window" ng-click="caseTab(data.sd_id,$index,lower_court_recordData)" ></span></span>
                                 	<br>
                                 	<!-- <span style="float: right;"><input type="checkbox" ng-model="data.checkBoxValue " ng-click="checkHighlight(data)"></span> -->
                                 </td> 
                                 <!-- <td style="text-align:justify">{{data.sd_description}}</td>
                                 <td>{{data.sd_counsel}}</td> -->
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>
<!-- <div  ng-show="casefile.impugnedOrders.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseImpugnedOrders">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Impugned Orders
					</a>
               </h3>
           </div>
           <div id="collapseImpugnedOrders" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="casefile.impugnedOrders" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                                   <th>Case Details</th>
                                   <th>Type</th>
                                   <th>District</th>                                  
                                </tr>
                                <tr ng-repeat="data in casefile.impugnedOrders">
                                 <td style="text-decoration: underline;cursor:pointer;padding:10px 5px;width:35%"><span ng-if="data.io_court_type==1">{{data.lcCaseType.ct_name}}<br/>{{data.io_case_no}}/{{data.io_case_year}}</span><span ng-click="showHighCourtCase(data.io_id)"  ng-if="data.io_court_type==2">{{data.hcCaseType.ct_name}}<br/>{{data.io_case_no}}/{{data.io_case_year}}</span></td>
                                 
                                 <td style="text-align:justify"><span ng-if="data.io_court_type==1">Lower Court</span><span ng-if="data.io_court_type==2">High Court</span></td>
                                 <td style="text-align:justify">{{data.district.dt_name}}</td>
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div> -->
<div  ng-show="casefile.petitioners.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                  <!--  <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapsePetitioners">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Petitioners
					</a> -->
					
					Petitioners
               </h3>
           </div>
           <!-- <div id="collapsePetitioners" class="panel-collapse collapse in"> -->
           <div id="collapsePetitioners" class="panel-default">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="casefile.petitioners" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                                   <th>Name</th>                                  
                                </tr>
                                <tr>
                                 <td style="text-align:justify">
                                  <span ng-repeat="data in casefile.petitioners">
                                  	<span style="color:blue;">({{$index+1}})</span> {{data.pt_name}}<b style="font-size:16px;"> </b>
                                  </span>  
                                 </td>  
                         	    </tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>
<div  ng-show="casefile.respondents.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                  <!--  <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseRespondents">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Respondents
					</a> -->
					Respondents
               </h3>
           </div>
          <!--  <div id="collapseRespondents" class="panel-collapse collapse in"> -->
           <div id="collapseRespondents" class="panel-default">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="casefile.respondents" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                                   <th>Name</th>                                  
                                </tr>
                                <tr>
                                 <td style="text-align:justify">
                                  <span ng-repeat="data in casefile.respondents">
                                  	<span style="color:blue;">({{$index+1}})</span> {{data.rt_name}}<b style="font-size:16px;"> </b>
                                  </span>  
                                 </td>                                 
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>

<div  ng-show="casefile.respondents.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
              <!--      <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapsePetAdv">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Petitioner Advocate(s)
					</a> -->
					Petitioner Advocate(s)
               </h3>
           </div>
           <div id="collapsePetAdv" class="panel-default" >
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                                   <th>Name</th>                                  
                                </tr>
                                <tr>
                                 <td style="text-align:justify">
                                   {{casefile.fd_pet_adv}}
                                 </td>                                 
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>

<div  ng-show="casefile.respondents.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <!-- <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseResAdv">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Respondent Advocate(s)
					</a> -->
					Respondent Advocate(s)
               </h3>
           </div>
           <!-- <div id="collapseResAdv" class="panel-collapse collapse in"> -->
             <div id="collapseResAdv" class="panel-default">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                                   <th>Name</th>                                  
                                </tr>
                                <tr>
                                 <td style="text-align:justify">
                                   {{casefile.fd_res_adv}}
                                 </td>                                 
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>

<div  ng-show="connectedCasesList.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseConnected">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Connected Cases
					</a>
               </h3>
           </div>
           <div id="collapseConnected" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="casefile.respondents" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                                   <th>Case No</th>
                                      <th>Petitioner vs Respondent</th>                          
                                </tr>
                                <tr ng-repeat="data in connectedCasesList " ng-if="$index > 0">
                                 <td  ng-show ='!data.cl_fd_mid' style="text-align:center;cursor: pointer;">
						<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2" >Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						<span ng-if="data.cl_list_type_mid !=1 && data.cl_list_type_mid !=2">{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						</br>
						<span class ="text-danger">Not in Dms</span>
						</td> 
						<td ng-show ='data.cl_fd_mid'  style="cursor: pointer;text-decoration: underline;">
					<!-- 	<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2" ng-click="viewApplication(data.cl_ano,data.cl_ayr,data.cl_fd_mid)">Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span> -->
						<span ng-if="data.cl_list_type_mid==1 || data.cl_list_type_mid==2" ng-click="viewApplication(data)">Application No. {{data.cl_ano}}/{{data.cl_ayr}}<br/>{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						
						<span ng-if="data.cl_list_type_mid !=1 && data.cl_list_type_mid !=2" ng-click="viewCaseFile(data)">{{data.caseType.ct_label}}<br/> {{data.cl_case_no}}/{{data.cl_case_year}}</span>
						</td>
						<td>{{data.cl_first_petitioner}} <br/> vs <br/> {{data.cl_first_respondent}} </td>
                                 
                                                                
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>




<!-- <div  ng-show="casefile.pCounsels.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapsePCounsels">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Petitioner Counsels
					</a>
               </h3>
           </div>
           <div id="collapsePCounsels" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="casefile.pCounsels" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                                   <th>Name</th>                                  
                                </tr>
                                <tr ng-repeat="data in casefile.pCounsels">
                                 <td style="text-align:justify">{{data.pc_name}}</td>                                 
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>
<div  ng-show="casefile.rCounsels.length > 0" class="panel panel-inverse overflow-hidden">
           <div class="panel-heading">
               <h3 class="panel-title">
                   <a class="accordion-toggle accordion-toggle-styled" data-toggle="collapse" data-parent="#accordion" href="#collapseRCounsels">
				    <i class="fa fa-plus-circle pull-right"></i> 
					Respondent Counsels
					</a>
               </h3>
           </div>
           <div id="collapseRCounsels" class="panel-collapse collapse in">
               <div class="panel-body" style="padding:2px;max-height:450px;overflow:auto;">
                   <div class="table-responsive">
                       <table id="data-table" st-table="casefile.rCounsels" class="table table-striped table-bordered">
                           <thead>
                               	<tr>
                                   <th>Name</th>                                  
                                </tr>
                                <tr ng-repeat="data in casefile.rCounsels">
                                 <td style="text-align:justify">{{data.rc_name}}</td>                                 
                         		</tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
</div>
 -->
 
 
 
 
 
</div>
