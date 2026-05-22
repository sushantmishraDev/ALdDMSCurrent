package com.dms.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.dms.model.ActionResponse;
import com.dms.model.CaseFileDetail;
import com.dms.model.CaseType;
import com.dms.model.CauseApi;
import com.dms.model.CauseList;
import com.dms.model.CauseListNew;
import com.dms.model.CauseListType;
import com.dms.model.CauseListTypeCcms;
import com.dms.model.CourtMaster;
import com.dms.model.CourtUserMapping;
import com.dms.model.DMSJudge_mapping;
import com.dms.model.ExtraCauseListInfo;
import com.dms.model.SameCrimDetails;
import com.dms.model.SameCrimeCcms;
import com.dms.model.SameCrimeDetailsNew;
import com.dms.model.SameLCRccms;
import com.dms.model.SameLcrDetails;
import com.dms.model.User;
import com.dms.model.UserRole;
import com.dms.service.BenchService;
import com.dms.service.CaseFileDetailService;
import com.dms.service.CauseListService;
import com.dms.service.CourtMasterService;
import com.dms.service.LookupService;
import com.dms.service.MasterService;
import com.dms.utility.GlobalFunction;

@Controller
@RequestMapping("/bench")
public class BenchController {
	
	
	@Autowired
	CauseListService causeListService;
	@Autowired
	ServletContext context;

	@Autowired
	CourtMasterService courtService;

	@Autowired
	LookupService lookupService;

	@Autowired
	private CaseFileDetailService caseFileDetailService;

	@Autowired
	MasterService masterService;
	
	@Autowired
	CourtMasterService courtMasterService;
	
	@Autowired
	BenchService benchService;
	
	private GlobalFunction globalfunction;

	public BenchController() {
		globalfunction = new GlobalFunction();
	}
	
	
	@RequestMapping(value = "/manageBench", method = RequestMethod.GET)
	public String adminHome() {

		return "/bench/manageBench";

	}
	
	@RequestMapping(value = "getCauseListTypes", method = RequestMethod.POST)
	public @ResponseBody String getCauseListTypes(@RequestBody String cltType) {
		ActionResponse<CauseListType> response = new ActionResponse<CauseListType>();
		String jsonData = "";
		List<CauseListType> types =new ArrayList<>();
		CauseListType clt=new CauseListType();
		clt.setClt_description("Entire List");
		clt.setClt_ccms_list(null);
		
		if(cltType.equals("trans")) {
			 types = causeListService.getCauseListTypesTrans();	
		}
		if(cltType.equals("supp")) {
			 types = causeListService.getCauseListTypesSupp();	
		}
		types.add(clt);
		response.setData("TRUE");
		response.setModelList(types);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
	

	@RequestMapping(value = "/nextTrans", method = RequestMethod.POST)
	public @ResponseBody String nextTrans(@RequestBody CourtMaster courtmaster, HttpSession session) {
		
		

		
		Integer serviceStatus=0;

		String jsonData = "";
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-YYYY");
		 DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date currentDate =new Date();
		 
		 Date date3=null;
		 Date date2=null;
		 
		 String date=null;
		 
		 Date date4=null;
			try {
				//date2 =dateFormat1.parse(dateFormat1.format(currentDate));
				date =dateFormat1.format(courtmaster.getClDate());
				String date5=dateFormat2.format(courtmaster.getClDate());
				 date2 = dateFormat1.parse(date);
				 date3=dateFormat2.parse(date);
				 date4=dateFormat2.parse(date5);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//List<CauseListType> clt=benchService.getCuaselistTypetrans();
			
			RestTemplate restTemplate = new RestTemplate();
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			 
			 MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
				
				MultiValueMap<String, String> map5 = new LinkedMultiValueMap<>();
				 
				
				 map5.add("ListingDate", date);
				 map5.add("BenchId",courtmaster.getCm_bench_id().toString());
				 
				 HttpEntity<MultiValueMap<String, String>> entity5 = new HttpEntity<>(map5, headers);
				 
				 HttpEntity<Object> response5 =
					     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetDailyTransferCauseListType",
					                           HttpMethod.POST,
					                           entity5,
					                           Object.class);
					 List<Object> mv5=(List<Object>) response5.getBody();
					 
					 ObjectMapper m5 = new ObjectMapper();
					 m5.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					 CauseListTypeCcms props5 = m5.convertValue(mv5.get(0), CauseListTypeCcms.class);
					 
					 List<CauseListType> clt=benchService.getCuaselistTypetrans(props5.getCauseListType());
			
			for(CauseListType clt2 : clt) {
				

				 map.add("BenchId",courtmaster.getCm_bench_id().toString());
				 map.add("CauseListDate", date);
				 map.add("CauseId",clt2.getClt_ccms_list().toString());
				// map.add("CauseId","1003");
				 
				 
				 
				 HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
				 
				
				// dmsService.deleteCltypeNew(date3,cmlist.get(bid).getCm_id(),clt2.getClt_id());

				 
				 HttpEntity<Object> response1 =
				     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCourtTransferredCaseList",
				                           HttpMethod.POST,
				                           entity,
				                           Object.class);
				 List<Object> mv=(List<Object>) response1.getBody();
				 
				
				 
				 if(mv.size()!=0) {
					 serviceStatus=1;
					 benchService.deleteCltype(dateFormat2.format(courtmaster.getClDate()),courtmaster.getCm_id(),clt2.getClt_id());
					
				 for(Object t :mv) {
					 
					 ObjectMapper m = new ObjectMapper();
					 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					 CauseApi props = m.convertValue(t, CauseApi.class);
					 
					 CauseList dmsCauseList = new CauseList();
					 CauseListNew dmsCauseList1 = new CauseListNew();
					 dmsCauseList.setCl_iscrime("N");
					 
					// CauseListNew dmsCauseList1 = new CauseListNew();
					 
					 
///////////////////////////
							 MultiValueMap<String, String> mapExtraa = new LinkedMultiValueMap<>();
							 mapExtraa.add("ListingDate",date);	
							 mapExtraa.add("CINO",props.getCino());

							 HttpEntity<MultiValueMap<String, String>> entityExtra = new HttpEntity<>(mapExtraa, headers);

							 
							 HttpEntity<Object> responseExtraa = null;
							 try{
								 responseExtraa= restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GETEcourtCauseList",
				                           HttpMethod.POST,
				                           entityExtra,
				                           Object.class);
							 }
							 catch(Exception e)
							 {
								 e.printStackTrace();
								/* continue;*/
							 }
							 
							 ExtraCauseListInfo props4=new ExtraCauseListInfo();
							 
							 List<Object> mv1Extra=(List<Object>) responseExtraa.getBody();
							 
							 if(mv1Extra.size()!=0) {
									 ObjectMapper m2 = new ObjectMapper();
									 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
									 props4 = m2.convertValue(mv1Extra.get(0), ExtraCauseListInfo.class);
							 }
							 
								/////
							 
					 
					 
					 
					 
					 String [] bnName=props.getBench_Name().split(",");
					 
					 String [] joCode=(props.getJo_code()!=null ? props.getJo_code().split(",") : null);
					 String jgName="";
					 if(joCode!=null && joCode.length !=0) {
					 for (int i = 0; i < bnName.length; i++) {
							 jgName=jgName+", Hon'ble Justice "+(String) bnName[i];
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) joCode[i] ,courtmaster.getClDate());
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  joCode[i]).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) bnName[i]);
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						}
					 }
					 else {
						 if( props.getJo_code()!=null) {
						 jgName=jgName+", Hon'ble Justice "+(String) props.getBench_Name();
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) props.getJo_code() ,date2);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  props.getJo_code()).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) props.getBench_Name());
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						 }
					 }
					 
					 
					 courtmaster.setCm_judges_name(props.getBench_Name());
					 benchService.save(courtmaster);
					  
					  if(!props.getSerialNo().contentEquals("") && props.getCase_type_name()!=null){
					 
					 CaseType ct = benchService.getCaseTypeFromLabel(props.getCase_type_name());
					 if(ct==null) {
						 System.out.println("error");
					 }
					 dmsCauseList.setCl_case_type_mid(ct.getCt_id());
					 dmsCauseList.setCl_case_no(props.getReg_no());
					 dmsCauseList.setCl_case_year(Integer.parseInt(props.getReg_year()));
					 dmsCauseList.setCl_court_no(courtmaster.getCm_id());
						dmsCauseList.setCisCaseNo(props.getCase_Number());
						dmsCauseList.setCl_rec_status(1);
						dmsCauseList.setCl_list_type_mid(clt2.getClt_id());
						dmsCauseList.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						dmsCauseList.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						dmsCauseList.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						dmsCauseList.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						dmsCauseList.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						dmsCauseList.setCl_ano(props.getIa_no() !="" && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						dmsCauseList.setCl_ayr(props.getIa_year().trim()!="" && !props.getIa_year().equals("N") && !props.getIa_year().equals("") ? Integer.parseInt(props.getIa_year().trim()) : null);
						dmsCauseList.setCl_dol(date4);
						dmsCauseList.setCl_ccms_id(Long.parseLong(props.getCase_id()));
						dmsCauseList.setCl_mod_by(user.getUm_id());
						dmsCauseList.setCl_mod_date(new Date());
						dmsCauseList.setCl_iscrime("N");
						
						
						if(props4.getNoticeNo() !=null) {
							dmsCauseList.setCl_notice_no(!props4.getNoticeNo().isEmpty() ? props4.getNoticeNo() : null);
							dmsCauseList.setCl_district_name(!props4.getDistrictName().isEmpty() ? props4.getDistrictName() : null);
							dmsCauseList.setCl_injail_date(!props4.getInJailSinceDate().isEmpty() ? props4.getInJailSinceDate() : null);
							dmsCauseList.setCl_crime_no(!props4.getCrimeNo().isEmpty() ? props4.getCrimeNo() : null);
							dmsCauseList.setCl_crime_year(!props4.getCrimeYear().isEmpty() ? props4.getCrimeYear() : null);
							dmsCauseList.setCl_crime_dist(!props4.getCrimeDistName().isEmpty() ? props4.getCrimeDistName() : null);
							dmsCauseList.setCl_crime_ps(!props4.getCrimePoliceStnName().isEmpty() ? props4.getCrimePoliceStnName() :null);
							dmsCauseList.setCl_iscrime(!props4.getIsCrime().isEmpty() ? props4.getIsCrime() :"N");
							dmsCauseList.setCl_short_order(!props4.getShortOrderName().isEmpty() ? props4.getShortOrderName() :null);
							dmsCauseList.setCl_app_last_date(!props4.getLastListingDate().isEmpty() ? props4.getLastListingDate() :null);
							dmsCauseList.setCl_applied_by(!props4.getApplicationAppliedBy().isEmpty() ? props4.getApplicationAppliedBy() :null);
							dmsCauseList.setCl_next_list(!props4.getNextDateInSystem().isEmpty() ? props4.getNextDateInSystem() :null);
							dmsCauseList.setCl_lcr_no(!props4.getLowerCourtLCRNo().isEmpty() ? props4.getLowerCourtLCRNo() :null);
							dmsCauseList.setCl_lcr_year(!props4.getLowerCourtLCRYear().isEmpty() ? props4.getLowerCourtLCRYear() :null);
							}
						
						
						
						
						
						
						
						
// cause list new transfer
						
						
						dmsCauseList1.setCl_case_type_mid(ct.getCt_id());
						 dmsCauseList1.setCl_case_no(props.getReg_no());
						 dmsCauseList1.setCl_case_year(Integer.parseInt(props.getReg_year()));
						 dmsCauseList1.setCl_court_no(courtmaster.getCm_id());
						 dmsCauseList1.setCisCaseNo(props.getCase_Number());
						 dmsCauseList1.setCl_bench_number(courtmaster.getCm_bench_id());
						 dmsCauseList1.setCl_rec_status(1);
						 dmsCauseList1.setCl_list_type_mid(clt2.getClt_id());
						 dmsCauseList1.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						 dmsCauseList1.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						 dmsCauseList1.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						 dmsCauseList1.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						 dmsCauseList1.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						 dmsCauseList1.setCl_ano(props.getIa_no() !="" && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						 dmsCauseList1.setCl_ayr(props.getIa_year().trim()!="" && !props.getIa_year().equals("N") && !props.getIa_year().equals("") ? Integer.parseInt(props.getIa_year().trim()) : null);
						 dmsCauseList1.setCl_dol(date4);
						 dmsCauseList1.setCl_ccms_id(Long.parseLong(props.getCase_id()));
						 dmsCauseList1.setCl_mod_by(user.getUm_id());
						 dmsCauseList1.setCl_mod_date(new Date());
						 dmsCauseList1.setCl_iscrime("N");
							
							
							if(props4.getNoticeNo() !=null) {
								dmsCauseList1.setCl_notice_no(!props4.getNoticeNo().isEmpty() ? props4.getNoticeNo() : null);
								dmsCauseList1.setCl_district_name(!props4.getDistrictName().isEmpty() ? props4.getDistrictName() : null);
								dmsCauseList1.setCl_injail_date(!props4.getInJailSinceDate().isEmpty() ? props4.getInJailSinceDate() : null);
								dmsCauseList1.setCl_crime_no(!props4.getCrimeNo().isEmpty() ? props4.getCrimeNo() : null);
								dmsCauseList1.setCl_crime_year(!props4.getCrimeYear().isEmpty() ? props4.getCrimeYear() : null);
								dmsCauseList1.setCl_crime_dist(!props4.getCrimeDistName().isEmpty() ? props4.getCrimeDistName() : null);
								dmsCauseList1.setCl_crime_ps(!props4.getCrimePoliceStnName().isEmpty() ? props4.getCrimePoliceStnName() :null);
								dmsCauseList1.setCl_iscrime(!props4.getIsCrime().isEmpty() ? props4.getIsCrime() :"N");
								dmsCauseList1.setCl_short_order(!props4.getShortOrderName().isEmpty() ? props4.getShortOrderName() :null);
								dmsCauseList1.setCl_app_last_date(!props4.getLastListingDate().isEmpty() ? props4.getLastListingDate() :null);
								dmsCauseList1.setCl_applied_by(!props4.getApplicationAppliedBy().isEmpty() ? props4.getApplicationAppliedBy() :null);
								dmsCauseList1.setCl_next_list(!props4.getNextDateInSystem().isEmpty() ? props4.getNextDateInSystem() :null);
								dmsCauseList1.setCl_lcr_no(!props4.getLowerCourtLCRNo().isEmpty() ? props4.getLowerCourtLCRNo() :null);
								dmsCauseList1.setCl_lcr_year(!props4.getLowerCourtLCRYear().isEmpty() ? props4.getLowerCourtLCRYear() :null);
								}
						
						
						
						
						
						CaseFileDetail casefile = new CaseFileDetail();
						casefile.setFd_case_no(dmsCauseList.getCl_case_no());
						casefile.setFd_case_type(dmsCauseList.getCl_case_type_mid());
						casefile.setFd_case_year(dmsCauseList.getCl_case_year());

						CaseFileDetail caseFileDetail = benchService.getCaseFile(casefile);
						if (caseFileDetail != null) {
							dmsCauseList.setCl_fd_mid(caseFileDetail.getFd_id());
							dmsCauseList1.setCl_fd_mid(caseFileDetail.getFd_id());
							caseFileDetail.setFd_pet_adv(dmsCauseList.getCl_petitioner_council());
							caseFileDetail.setFd_res_adv(dmsCauseList.getCl_respondent_council());
							benchService.save(caseFileDetail);
							
						} 
						dmsCauseList=benchService.save(dmsCauseList);
						dmsCauseList1=benchService.save(dmsCauseList1);
						
						
						if(props4.getIsCrime() != null &&  props4.getIsCrime().trim().equals("Y")) {
							for(SameCrimeCcms sc : props4.getCaseDetailsOfSameCrimeNoList()) {
								SameCrimDetails scd=new SameCrimDetails();
								//SameCrimeDetailsNew scd1=new SameCrimeDetailsNew();
								
								String [] caseN=sc.getDisplayCaseno().split("/");

							 CaseType ct1 = benchService.getCaseTypeFromLabel(caseN[0]);
							 
							 
							 CaseFileDetail casefile1 = new CaseFileDetail();
							 if(ct1!=null) {
								casefile1.setFd_case_no(caseN[1]);
								casefile1.setFd_case_type(ct1.getCt_id());
								casefile1.setFd_case_year(Integer.parseInt(caseN[2]));
							 }

								CaseFileDetail caseFileDetail1 = benchService.getCaseFile(casefile1);

								if(caseFileDetail1!=null) {
								scd.setScd_fd_mid(caseFileDetail1.getFd_id());
								}
								scd.setScd_cl_id(dmsCauseList.getCl_id());
								scd.setScd_dol(dmsCauseList.getCl_dol());
								scd.setScd_display(sc.getDisplayCaseno());
								scd.setScd_pet_name(sc.getPetName());
								scd.setScd_res_name(sc.getResName());
								scd.setScd_ps_name(sc.getPoliceStName());
								scd.setScd_case_status(sc.getCaseStatus());
								scd.setScd_ps_code(sc.getPS_Code());
								scd.setScd_disp_date(sc.getOrderDate());
								scd.setScd_jo_code(sc.getJudge_code());
								
								benchService.save(scd);
								

							}
							
						
						}
						System.out.println("saved");
					 
					/* if(props.getConnectedCase()=="Y" && ( clt2.getClt_id()!=1L ||  clt2.getClt_id()!=2L ||  clt2.getClt_id()!=21L
							 ||  clt2.getClt_id()!=20L ||  clt2.getClt_id()!=23L ||  clt2.getClt_id()!=24L ||  clt2.getClt_id()!=28L)) {
						

						 if(props.getConnectedCase().equals("Y") && clt2.getClt_id()!=1L) {*/
						 if(clt2.getClt_id()!=28L) {
							 
							
					 
					 MultiValueMap<String, String> map2 = new LinkedMultiValueMap<>();
						 map2.add("Case_no",props.getCase_Number());		
						 map2.add("ListingDate", date);
						 map2.add("ListType",clt2.getClt_ccms_list().toString());

						 HttpEntity<MultiValueMap<String, String>> entity2 = new HttpEntity<>(map2, headers);

						 
						 HttpEntity<Object> response2 =
						     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCaseDetailByConnectedCase",
						                           HttpMethod.POST,
						                           entity2,
						                           Object.class);
						 
						 List<Object> mv1=(List<Object>) response2.getBody();
						 
							
						 
						 for(Object t1 :mv1) {
							 
							 ObjectMapper m1 = new ObjectMapper();
							 CauseApi props1 = m1.convertValue(t1, CauseApi.class);
							 
							 
							 
							 CauseList dmsCauseList2 = new CauseList();
							 CauseListNew dmsCauseList21 = new CauseListNew();
							
							 CaseType ct1 = benchService.getCaseTypeFromLabel(props1.getCase_type_name());
							 dmsCauseList2.setCl_case_type_mid(ct1.getCt_id());
							 dmsCauseList2.setCl_case_no(props1.getReg_no());
							 dmsCauseList2.setCl_case_year(Integer.parseInt(props1.getReg_year()));
							 dmsCauseList2.setCl_court_no(courtmaster.getCm_id());
							// dmsCauseList2.setCisCaseNo(props1.getCase_Number());
							 dmsCauseList2.setCl_rec_status(1);
							 dmsCauseList2.setCl_list_type_mid(clt2.getClt_id());
							 dmsCauseList2.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
							 dmsCauseList2.setCl_first_petitioner(props1.getPetname() !=null ? props1.getPetname() : " .");
							 dmsCauseList2.setCl_first_respondent(props1.getResname()  !=null ? props1.getResname() : " .");
							 dmsCauseList2.setCl_petitioner_council(props1.getPet_adv());
							 dmsCauseList2.setCl_respondent_council(props1.getRes_adv());
							 dmsCauseList2.setCl_dol(date4);
							 dmsCauseList2.setCl_iscrime("N");
								dmsCauseList2.setCl_mod_by(user.getUm_id());
								dmsCauseList2.setCl_mod_date(new Date());
							 
							 
							
							 
								//causelist new
								
								 dmsCauseList21.setCl_case_type_mid(ct1.getCt_id());
								 dmsCauseList21.setCl_case_no(props1.getReg_no());
								 dmsCauseList21.setCl_case_year(Integer.parseInt(props1.getReg_year()));
								 dmsCauseList21.setCl_court_no(courtmaster.getCm_id());
								 dmsCauseList21.setCisCaseNo(props1.getCase_Number());
								 dmsCauseList21.setCl_rec_status(1);
								 dmsCauseList21.setCl_bench_number(courtmaster.getCm_bench_id());
								 dmsCauseList21.setCl_list_type_mid(clt2.getClt_id());
								 dmsCauseList21.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
								 dmsCauseList21.setCl_first_petitioner(props1.getPetname() !=null ? props1.getPetname() : " .");
								 dmsCauseList21.setCl_first_respondent(props1.getResname()  !=null ? props1.getResname() : " .");
								 dmsCauseList21.setCl_petitioner_council(props1.getPet_adv());
								 dmsCauseList21.setCl_respondent_council(props1.getRes_adv());
								 dmsCauseList21.setCl_dol(date3);
								 dmsCauseList21.setCl_iscrime("N");
							 
							 
							
							 
								
								CaseFileDetail casefile1 = new CaseFileDetail();
								casefile1.setFd_case_no(dmsCauseList2.getCl_case_no());
								casefile1.setFd_case_type(dmsCauseList2.getCl_case_type_mid());
								casefile1.setFd_case_year(dmsCauseList2.getCl_case_year());

								CaseFileDetail caseFileDetail1 = benchService.getCaseFile(casefile1);
								if (caseFileDetail1 != null) {
									dmsCauseList2.setCl_fd_mid(caseFileDetail1.getFd_id());
									dmsCauseList21.setCl_fd_mid(caseFileDetail1.getFd_id());
									
								} 
								benchService.save(dmsCauseList2);
								benchService.save(dmsCauseList21);
								
								System.out.println("saved");
							 
						 }
						 }
					 }
				 
				 System.out.println(t);
			}
				 }
				 map.clear();
				 
			 
			}
		
		if(serviceStatus==1) {
			response.setResponse("TRUE");
		}
		else {
			response.setResponse("FALSE");
		}
			
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		
	
	}
	

	@RequestMapping(value = "/transferCourt", method = RequestMethod.POST)
	public @ResponseBody String getTransfer(@RequestBody CourtMaster courtmaster, HttpSession session) {
		
		Integer serviceStatus=0;

		String jsonData = "";
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-YYYY");
		 DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date currentDate =new Date();
		 
		 Date date3=null;
		 Date date2=null;
		 
		 String date=null;
		 
		 Date date4=null;
			try {
				//date2 =dateFormat1.parse(dateFormat1.format(currentDate));
				date =dateFormat1.format(currentDate);
				String date5=dateFormat2.format(currentDate);
				 date2 = dateFormat1.parse(date);
				 date3=dateFormat2.parse(date);
				 date4=dateFormat2.parse(date5);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//List<CauseListType> clt=benchService.getCuaselistTypetrans();
			
			RestTemplate restTemplate = new RestTemplate();
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			 
			 MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
				
				MultiValueMap<String, String> map5 = new LinkedMultiValueMap<>();
				 
				
				 map5.add("ListingDate", date);
				 map5.add("BenchId",courtmaster.getCm_bench_id().toString());
				 
				 HttpEntity<MultiValueMap<String, String>> entity5 = new HttpEntity<>(map5, headers);
				 
				 HttpEntity<Object> response5 =
					     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetDailyTransferCauseListType",
					                           HttpMethod.POST,
					                           entity5,
					                           Object.class);
					 List<Object> mv5=(List<Object>) response5.getBody();
					 
					 ObjectMapper m5 = new ObjectMapper();
					 m5.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					 CauseListTypeCcms props5 = m5.convertValue(mv5.get(0), CauseListTypeCcms.class);
					 
					 List<CauseListType> clt=new ArrayList<>();
					 
					 if(courtmaster.getClListType()==null) {
						 clt=benchService.getCuaselistTypetrans(props5.getCauseListType());
					 }
					 else {
						 clt=benchService.getCuaselistTypetrans(courtmaster.getClListType().toString());
					 }
			
			for(CauseListType clt2 : clt) {
				

				 map.add("BenchId",courtmaster.getCm_bench_id().toString());
				 map.add("CauseListDate", date);
				 map.add("CauseId",clt2.getClt_ccms_list().toString());
				// map.add("CauseId","1003");
				 
				 
				 
				 HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
				 
				
				// dmsService.deleteCltypeNew(date3,cmlist.get(bid).getCm_id(),clt2.getClt_id());

				 
				 HttpEntity<Object> response1 =
				     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCourtTransferredCaseList",
				                           HttpMethod.POST,
				                           entity,
				                           Object.class);
				 List<Object> mv=(List<Object>) response1.getBody();
				 
				
				 
				 if(mv.size()!=0) {
					 serviceStatus=1;
					 benchService.deleteCltype(dateFormat2.format(currentDate),courtmaster.getCm_id(),clt2.getClt_id());
					 benchService.deleteCltypeNew(dateFormat2.format(currentDate),courtmaster.getCm_id(),clt2.getClt_id());
					
				 for(Object t :mv) {
					 
					 ObjectMapper m = new ObjectMapper();
					 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					 CauseApi props = m.convertValue(t, CauseApi.class);
					 
					 CauseList dmsCauseList = new CauseList();
					 CauseListNew dmsCauseList1= new CauseListNew();
					 dmsCauseList.setCl_iscrime("N");
					 
					// CauseListNew dmsCauseList1 = new CauseListNew();
					 
					 
///////////////////////////
							 MultiValueMap<String, String> mapExtraa = new LinkedMultiValueMap<>();
							 mapExtraa.add("ListingDate",date);	
							 mapExtraa.add("CINO",props.getCino());

							 HttpEntity<MultiValueMap<String, String>> entityExtra = new HttpEntity<>(mapExtraa, headers);

							 
							 HttpEntity<Object> responseExtraa = null;
							 try{
								 responseExtraa= restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GETEcourtCauseList",
				                           HttpMethod.POST,
				                           entityExtra,
				                           Object.class);
							 }
							 catch(Exception e)
							 {
								 e.printStackTrace();
								/* continue;*/
							 }
							 
							 ExtraCauseListInfo props4=new ExtraCauseListInfo();
							 
							 List<Object> mv1Extra=(List<Object>) responseExtraa.getBody();
							 
							 if(mv1Extra.size()!=0) {
									 ObjectMapper m2 = new ObjectMapper();
									 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
									 props4 = m2.convertValue(mv1Extra.get(0), ExtraCauseListInfo.class);
							 }
							 
								/////
							 
					 
					 
					 
					 
					 String [] bnName=props.getBench_Name().split(",");
					 
					 String [] joCode=(props.getJo_code()!=null ? props.getJo_code().split(",") : null);
					 String jgName="";
					 if(joCode!=null && joCode.length !=0) {
					 for (int i = 0; i < bnName.length; i++) {
							 jgName=jgName+", Hon'ble Justice "+(String) bnName[i];
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) joCode[i] ,date4);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  joCode[i]).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) bnName[i]);
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						}
					 }
					 else {
						 if( props.getJo_code()!=null) {
						 jgName=jgName+", Hon'ble Justice "+(String) props.getBench_Name();
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) props.getJo_code() ,date2);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  props.getJo_code()).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) props.getBench_Name());
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						 }
					 }
					 
					 
					 courtmaster.setCm_judges_name(props.getBench_Name());
					 benchService.save(courtmaster);
					  
					  if(!props.getSerialNo().contentEquals("") && props.getCase_type_name()!=null){
					 
					 CaseType ct = benchService.getCaseTypeFromLabel(props.getCase_type_name());
					 if(ct==null) {
						 System.out.println("error");
					 }
					 dmsCauseList.setCl_case_type_mid(ct.getCt_id());
					 dmsCauseList.setCl_case_no(props.getReg_no());
					 dmsCauseList.setCl_case_year(Integer.parseInt(props.getReg_year()));
					 dmsCauseList.setCl_court_no(courtmaster.getCm_id());
						dmsCauseList.setCisCaseNo(props.getCase_Number());
						dmsCauseList.setCl_rec_status(1);
						dmsCauseList.setCl_list_type_mid(clt2.getClt_id());
						dmsCauseList.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						dmsCauseList.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						dmsCauseList.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						dmsCauseList.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						dmsCauseList.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						dmsCauseList.setCl_ano(props.getIa_no() !="" && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						dmsCauseList.setCl_ayr(props.getIa_year().trim()!="" && !props.getIa_year().equals("N") && !props.getIa_year().equals("") ? Integer.parseInt(props.getIa_year().trim()) : null);
						dmsCauseList.setCl_dol(date4);
						dmsCauseList.setCl_ccms_id(Long.parseLong(props.getCase_id()));
						dmsCauseList.setCl_mod_by(user.getUm_id());
						dmsCauseList.setCl_mod_date(new Date());
						dmsCauseList.setCl_iscrime("N");
						
						
						if(props4.getNoticeNo() !=null) {
							dmsCauseList.setCl_notice_no(!props4.getNoticeNo().isEmpty() ? props4.getNoticeNo() : null);
							dmsCauseList.setCl_district_name(!props4.getDistrictName().isEmpty() ? props4.getDistrictName() : null);
							dmsCauseList.setCl_injail_date(!props4.getInJailSinceDate().isEmpty() ? props4.getInJailSinceDate() : null);
							dmsCauseList.setCl_crime_no(!props4.getCrimeNo().isEmpty() ? props4.getCrimeNo() : null);
							dmsCauseList.setCl_crime_year(!props4.getCrimeYear().isEmpty() ? props4.getCrimeYear() : null);
							dmsCauseList.setCl_crime_dist(!props4.getCrimeDistName().isEmpty() ? props4.getCrimeDistName() : null);
							dmsCauseList.setCl_crime_ps(!props4.getCrimePoliceStnName().isEmpty() ? props4.getCrimePoliceStnName() :null);
							dmsCauseList.setCl_iscrime(!props4.getIsCrime().isEmpty() ? props4.getIsCrime() :"N");
							dmsCauseList.setCl_short_order(!props4.getShortOrderName().isEmpty() ? props4.getShortOrderName() :null);
							dmsCauseList.setCl_app_last_date(!props4.getLastListingDate().isEmpty() ? props4.getLastListingDate() :null);
							dmsCauseList.setCl_applied_by(!props4.getApplicationAppliedBy().isEmpty() ? props4.getApplicationAppliedBy() :null);
							dmsCauseList.setCl_next_list(!props4.getNextDateInSystem().isEmpty() ? props4.getNextDateInSystem() :null);
							dmsCauseList.setCl_lcr_no(!props4.getLowerCourtLCRNo().isEmpty() ? props4.getLowerCourtLCRNo() :null);
							dmsCauseList.setCl_lcr_year(!props4.getLowerCourtLCRYear().isEmpty() ? props4.getLowerCourtLCRYear() :null);
							}
						
						
						// cause list new transfer
						
						
						dmsCauseList1.setCl_case_type_mid(ct.getCt_id());
						 dmsCauseList1.setCl_case_no(props.getReg_no());
						 dmsCauseList1.setCl_case_year(Integer.parseInt(props.getReg_year()));
						 dmsCauseList1.setCl_court_no(courtmaster.getCm_id());
						 dmsCauseList1.setCisCaseNo(props.getCase_Number());
						 dmsCauseList1.setCl_bench_number(courtmaster.getCm_bench_id());
						 dmsCauseList1.setCl_rec_status(1);
						 dmsCauseList1.setCl_list_type_mid(clt2.getClt_id());
						 dmsCauseList1.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						 dmsCauseList1.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						 dmsCauseList1.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						 dmsCauseList1.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						 dmsCauseList1.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						 dmsCauseList1.setCl_ano(props.getIa_no() !="" && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						 dmsCauseList1.setCl_ayr(props.getIa_year().trim()!="" && !props.getIa_year().equals("N") && !props.getIa_year().equals("") ? Integer.parseInt(props.getIa_year().trim()) : null);
						 dmsCauseList1.setCl_dol(date4);
						 dmsCauseList1.setCl_ccms_id(Long.parseLong(props.getCase_id()));
						 dmsCauseList1.setCl_mod_by(user.getUm_id());
						 dmsCauseList1.setCl_mod_date(new Date());
						 dmsCauseList1.setCl_iscrime("N");
							
							
							if(props4.getNoticeNo() !=null) {
								dmsCauseList1.setCl_notice_no(!props4.getNoticeNo().isEmpty() ? props4.getNoticeNo() : null);
								dmsCauseList1.setCl_district_name(!props4.getDistrictName().isEmpty() ? props4.getDistrictName() : null);
								dmsCauseList1.setCl_injail_date(!props4.getInJailSinceDate().isEmpty() ? props4.getInJailSinceDate() : null);
								dmsCauseList1.setCl_crime_no(!props4.getCrimeNo().isEmpty() ? props4.getCrimeNo() : null);
								dmsCauseList1.setCl_crime_year(!props4.getCrimeYear().isEmpty() ? props4.getCrimeYear() : null);
								dmsCauseList1.setCl_crime_dist(!props4.getCrimeDistName().isEmpty() ? props4.getCrimeDistName() : null);
								dmsCauseList1.setCl_crime_ps(!props4.getCrimePoliceStnName().isEmpty() ? props4.getCrimePoliceStnName() :null);
								dmsCauseList1.setCl_iscrime(!props4.getIsCrime().isEmpty() ? props4.getIsCrime() :"N");
								dmsCauseList1.setCl_short_order(!props4.getShortOrderName().isEmpty() ? props4.getShortOrderName() :null);
								dmsCauseList1.setCl_app_last_date(!props4.getLastListingDate().isEmpty() ? props4.getLastListingDate() :null);
								dmsCauseList1.setCl_applied_by(!props4.getApplicationAppliedBy().isEmpty() ? props4.getApplicationAppliedBy() :null);
								dmsCauseList1.setCl_next_list(!props4.getNextDateInSystem().isEmpty() ? props4.getNextDateInSystem() :null);
								dmsCauseList1.setCl_lcr_no(!props4.getLowerCourtLCRNo().isEmpty() ? props4.getLowerCourtLCRNo() :null);
								dmsCauseList1.setCl_lcr_year(!props4.getLowerCourtLCRYear().isEmpty() ? props4.getLowerCourtLCRYear() :null);
								}
						
						
						
						
						
						CaseFileDetail casefile = new CaseFileDetail();
						casefile.setFd_case_no(dmsCauseList.getCl_case_no());
						casefile.setFd_case_type(dmsCauseList.getCl_case_type_mid());
						casefile.setFd_case_year(dmsCauseList.getCl_case_year());

						CaseFileDetail caseFileDetail = benchService.getCaseFile(casefile);
						if (caseFileDetail != null) {
							dmsCauseList.setCl_fd_mid(caseFileDetail.getFd_id());
							dmsCauseList1.setCl_fd_mid(caseFileDetail.getFd_id());
							caseFileDetail.setFd_pet_adv(dmsCauseList.getCl_petitioner_council());
							caseFileDetail.setFd_res_adv(dmsCauseList.getCl_respondent_council());
							benchService.save(caseFileDetail);
							
						} 
						dmsCauseList=benchService.save(dmsCauseList);
						dmsCauseList1=benchService.save(dmsCauseList1);
						
						
						if(props4.getIsCrime() != null &&  props4.getIsCrime().trim().equals("Y")) {
							for(SameCrimeCcms sc : props4.getCaseDetailsOfSameCrimeNoList()) {
								SameCrimDetails scd=new SameCrimDetails();
								//SameCrimeDetailsNew scd1=new SameCrimeDetailsNew();
								
								String [] caseN=sc.getDisplayCaseno().split("/");

							 CaseType ct1 = benchService.getCaseTypeFromLabel(caseN[0]);
							 
							 
							 CaseFileDetail casefile1 = new CaseFileDetail();
							 if(ct1!=null) {
								casefile1.setFd_case_no(caseN[1]);
								casefile1.setFd_case_type(ct1.getCt_id());
								casefile1.setFd_case_year(Integer.parseInt(caseN[2]));
							 }

								CaseFileDetail caseFileDetail1 = benchService.getCaseFile(casefile1);

								if(caseFileDetail1!=null) {
								scd.setScd_fd_mid(caseFileDetail1.getFd_id());
								}
								scd.setScd_cl_id(dmsCauseList.getCl_id());
								scd.setScd_dol(dmsCauseList.getCl_dol());
								scd.setScd_display(sc.getDisplayCaseno());
								scd.setScd_pet_name(sc.getPetName());
								scd.setScd_res_name(sc.getResName());
								scd.setScd_ps_name(sc.getPoliceStName());
								scd.setScd_case_status(sc.getCaseStatus());
								scd.setScd_ps_code(sc.getPS_Code());
								scd.setScd_disp_date(sc.getOrderDate());
								scd.setScd_jo_code(sc.getJudge_code());
								
								benchService.save(scd);
								

							}
							
						
						}
						System.out.println("saved");
					 
					/* if(props.getConnectedCase()=="Y" && ( clt2.getClt_id()!=1L ||  clt2.getClt_id()!=2L ||  clt2.getClt_id()!=21L
							 ||  clt2.getClt_id()!=20L ||  clt2.getClt_id()!=23L ||  clt2.getClt_id()!=24L ||  clt2.getClt_id()!=28L)) {
						

						 if(props.getConnectedCase().equals("Y") && clt2.getClt_id()!=1L) {*/
						 if(clt2.getClt_id()!=28L) {
							 
							
					 
					 MultiValueMap<String, String> map2 = new LinkedMultiValueMap<>();
						 map2.add("Case_no",props.getCase_Number());		
						 map2.add("ListingDate", date);
						 map2.add("ListType",clt2.getClt_ccms_list().toString());

						 HttpEntity<MultiValueMap<String, String>> entity2 = new HttpEntity<>(map2, headers);

						 
						 HttpEntity<Object> response2 =
						     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCaseDetailByConnectedCase",
						                           HttpMethod.POST,
						                           entity2,
						                           Object.class);
						 
						 List<Object> mv1=(List<Object>) response2.getBody();
						 
							
						 
						 for(Object t1 :mv1) {
							 
							 ObjectMapper m1 = new ObjectMapper();
							 CauseApi props1 = m1.convertValue(t1, CauseApi.class);
							 
							 
							 
							 CauseList dmsCauseList2 = new CauseList();
							 CauseListNew dmsCauseList21 = new CauseListNew();
							
							 CaseType ct1 = benchService.getCaseTypeFromLabel(props1.getCase_type_name());
							 dmsCauseList2.setCl_case_type_mid(ct1.getCt_id());
							 dmsCauseList2.setCl_case_no(props1.getReg_no());
							 dmsCauseList2.setCl_case_year(Integer.parseInt(props1.getReg_year()));
							 dmsCauseList2.setCl_court_no(courtmaster.getCm_id());
							// dmsCauseList2.setCisCaseNo(props1.getCase_Number());
							 dmsCauseList2.setCl_rec_status(1);
							 dmsCauseList2.setCl_list_type_mid(clt2.getClt_id());
							 dmsCauseList2.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
							 dmsCauseList2.setCl_first_petitioner(props1.getPetname() !=null ? props1.getPetname() : " .");
							 dmsCauseList2.setCl_first_respondent(props1.getResname()  !=null ? props1.getResname() : " .");
							 dmsCauseList2.setCl_petitioner_council(props1.getPet_adv());
							 dmsCauseList2.setCl_respondent_council(props1.getRes_adv());
							 dmsCauseList2.setCl_dol(date4);
							 dmsCauseList2.setCl_iscrime("N");
								dmsCauseList2.setCl_mod_by(user.getUm_id());
								dmsCauseList2.setCl_mod_date(new Date());
								
								
								//causelist new
								
								 dmsCauseList21.setCl_case_type_mid(ct1.getCt_id());
								 dmsCauseList21.setCl_case_no(props1.getReg_no());
								 dmsCauseList21.setCl_case_year(Integer.parseInt(props1.getReg_year()));
								 dmsCauseList21.setCl_court_no(courtmaster.getCm_id());
								 dmsCauseList21.setCisCaseNo(props1.getCase_Number());
								 dmsCauseList21.setCl_rec_status(1);
								 dmsCauseList21.setCl_bench_number(courtmaster.getCm_bench_id());
								 dmsCauseList21.setCl_list_type_mid(clt2.getClt_id());
								 dmsCauseList21.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
								 dmsCauseList21.setCl_first_petitioner(props1.getPetname() !=null ? props1.getPetname() : " .");
								 dmsCauseList21.setCl_first_respondent(props1.getResname()  !=null ? props1.getResname() : " .");
								 dmsCauseList21.setCl_petitioner_council(props1.getPet_adv());
								 dmsCauseList21.setCl_respondent_council(props1.getRes_adv());
								 dmsCauseList21.setCl_dol(date3);
								 dmsCauseList21.setCl_iscrime("N");
							 
							 
							
							 
								
								CaseFileDetail casefile1 = new CaseFileDetail();
								casefile1.setFd_case_no(dmsCauseList2.getCl_case_no());
								casefile1.setFd_case_type(dmsCauseList2.getCl_case_type_mid());
								casefile1.setFd_case_year(dmsCauseList2.getCl_case_year());

								CaseFileDetail caseFileDetail1 = benchService.getCaseFile(casefile1);
								if (caseFileDetail1 != null) {
									dmsCauseList2.setCl_fd_mid(caseFileDetail1.getFd_id());
									dmsCauseList21.setCl_fd_mid(caseFileDetail1.getFd_id());
									
								} 
								benchService.save(dmsCauseList2);
								benchService.save(dmsCauseList21);
								
								System.out.println("saved");
							 
						 }
						 }
					 }
				 
				 System.out.println(t);
			}
				 }
				 map.clear();
				 
			 
			}
		
		if(serviceStatus==1) {
			response.setResponse("TRUE");
		}
		else {
			response.setResponse("FALSE");
		}
			
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		
	}
	

	@RequestMapping(value = "/supplimentryCourtAll", method = RequestMethod.POST)
	public @ResponseBody String getSupplimentryAll(HttpSession session) {
		
		

		Integer serviceStatus=0;
		String jsonData = "";
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-YYYY");
		 DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date currentDate =new Date();
		 
		 Date date3=null;
		 Date date2=null;
		 
		 Date date4=null;
		 
		 String date=null;
			try {
				//date2 =dateFormat1.parse(dateFormat1.format(currentDate));
				date =dateFormat1.format(currentDate);
				String date5=dateFormat2.format(currentDate);
				 date2 = dateFormat1.parse(date);
				 date3=dateFormat2.parse(date5);
				 
				  date4=dateFormat2.parse(date5);
				 System.out.println(date4);
				 System.out.println(date4);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			List<CourtMaster> cmlist =new ArrayList<CourtMaster>();
			cmlist =benchService.getCourtMasterLists();
			

			
			
		//	List<CauseListType> clt=benchService.getCuaselistTypeSupp();
			
			RestTemplate restTemplate = new RestTemplate();
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			 
			 MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			 for(int bid =0 ; bid < cmlist.size() ; bid++) 
			 {

				 
				 if(cmlist.get(bid).getCm_bench_id()!=null) {
					 
					 MultiValueMap<String, String> map5 = new LinkedMultiValueMap<>();
					 
					 map5.add("BenchId",cmlist.get(bid).getCm_bench_id().toString());
					 map5.add("CauseListDate", dateFormat1.format(date3));
					 
					 HttpEntity<MultiValueMap<String, String>> entity5 = new HttpEntity<>(map5, headers);
					 
					 HttpEntity<Object> response5 =
						     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCauseListTypeByBenchId",
						                           HttpMethod.POST,
						                           entity5,
						                           Object.class);
						 List<Object> mv5=(List<Object>) response5.getBody();
						 
						 ObjectMapper m5 = new ObjectMapper();
						 m5.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
						 CauseListTypeCcms props5 = m5.convertValue(mv5.get(0), CauseListTypeCcms.class);
						 
						 List<CauseListType> clt=benchService.getCuaselistTypeSupp2(props5.getCauselistType());
					
					 for(CauseListType clt2 : clt) {
						 map.add("BenchId",cmlist.get(bid).getCm_bench_id().toString());
						 map.add("CauseListDate", dateFormat1.format(date3));
						 map.add("CauseId",clt2.getClt_ccms_list().toString());
						// map.add("CauseId","1003");
						 
						 
						 
						 HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
						 
						Boolean b= benchService.seleteCltype(dateFormat2.format(currentDate),cmlist.get(bid).getCm_id(),clt2.getClt_id());
						if(b==true){
							continue;
						}
						// benchService.deleteCltypeNew(dateFormat2.format(currentDate),cmlist.get(bid).getCm_id(),clt2.getClt_id());

						 
						 HttpEntity<Object> response7 =
						     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCourtCaseList",
						                           HttpMethod.POST,
						                           entity,
						                           Object.class);
						 List<Object> mv=(List<Object>) response7.getBody();
						 
						
						 
						 if(mv.size()!=0) {
							 serviceStatus=1;
						 for(Object t :mv) {
							 
							 ObjectMapper m = new ObjectMapper();
							 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
							 CauseApi props = m.convertValue(t, CauseApi.class);
							 
							 CauseList dmsCauseList = new CauseList();
							 
							/* CauseListNew dmsCauseList1 = new CauseListNew();*/
							 
							 
							 String [] bnName=props.getBench_Name().split(",");
							 
							 String [] joCode=(props.getJo_code()!=null ? props.getJo_code().split(",") : null);
							 
							 
								///////////////////////////
							 MultiValueMap<String, String> mapExtraa = new LinkedMultiValueMap<>();
							 mapExtraa.add("ListingDate",dateFormat1.format(date3));	
							 mapExtraa.add("CINO",props.getCino());

							 HttpEntity<MultiValueMap<String, String>> entityExtra = new HttpEntity<>(mapExtraa, headers);

							 
							 HttpEntity<Object> responseExtraa = null;
							 try{
								 responseExtraa= restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GETEcourtCauseList",
				                           HttpMethod.POST,
				                           entityExtra,
				                           Object.class);
							 }
							 catch(Exception e)
							 {
								 e.printStackTrace();
								/* continue;*/
							 }
							 
							 ExtraCauseListInfo props4=new ExtraCauseListInfo();
							 
							 List<Object> mv1Extra=(List<Object>) responseExtraa.getBody();
							 
							 if(mv1Extra.size()!=0) {
									 ObjectMapper m2 = new ObjectMapper();
									 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
									 props4 = m2.convertValue(mv1Extra.get(0), ExtraCauseListInfo.class);
							 }
							 
								/////
							 
							 
							 String jgName="";
							 if(joCode!=null && joCode.length !=0) {
							 for (int i = 0; i < bnName.length; i++) {
									 jgName=jgName+", Hon'ble Justice "+(String) bnName[i];
									//judge mapping Multiple judge in one bench
									DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(cmlist.get(bid).getCm_bench_id(),(String) joCode[i] ,date3);
									if(jm1==null) {
										DMSJudge_mapping judgem1=new DMSJudge_mapping();
										judgem1.setEjm_bench_id(cmlist.get(bid).getCm_bench_id());
										judgem1.setEjm_cm_mid(cmlist.get(bid).getCm_id());
										judgem1.setEjm_jo_code(((String)  joCode[i]).replaceAll("\\s", ""));
										judgem1.setEjm_rec_stauts(1);
										judgem1.setEjm_date(date3);
										judgem1.setEjm_judge_name((String) bnName[i]);
										judgem1=benchService.saveJudgeMapping(judgem1);
										System.out.println(judgem1+"done........");
									}
								}
							 }
							 else {
								 if( props.getJo_code()!=null) {
								 jgName=jgName+", Hon'ble Justice "+(String) props.getBench_Name();
									//judge mapping Multiple judge in one bench
									DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(cmlist.get(bid).getCm_bench_id(),(String) props.getJo_code() ,date2);
									if(jm1==null) {
										DMSJudge_mapping judgem1=new DMSJudge_mapping();
										judgem1.setEjm_bench_id(cmlist.get(bid).getCm_bench_id());
										judgem1.setEjm_cm_mid(cmlist.get(bid).getCm_id());
										judgem1.setEjm_jo_code(((String)  props.getJo_code()).replaceAll("\\s", ""));
										judgem1.setEjm_rec_stauts(1);
										judgem1.setEjm_date(date3);
										judgem1.setEjm_judge_name((String) props.getBench_Name());
										judgem1=benchService.saveJudgeMapping(judgem1);
										System.out.println(judgem1+"done........");
									}
								 }
							 }
							 
							 
							 cmlist.get(bid).setCm_judges_name(props.getBench_Name());
							 benchService.save(cmlist.get(bid));
							  
							  if(!props.getSerialNo().contentEquals("") && props.getCase_type_name()!=null){
							 
							 CaseType ct = benchService.getCaseTypeFromLabel(props.getCase_type_name());
							 if(ct==null) {
								 System.out.println("error");
							 }
							 dmsCauseList.setCl_case_type_mid(ct.getCt_id());
							 dmsCauseList.setCl_case_no(props.getReg_no());
							 dmsCauseList.setCl_case_year(Integer.parseInt(props.getReg_year()));
							 dmsCauseList.setCl_court_no(cmlist.get(bid).getCm_id());
								dmsCauseList.setCisCaseNo(props.getCase_Number());
								dmsCauseList.setCl_rec_status(1);
								dmsCauseList.setCl_list_type_mid(clt2.getClt_id());
								dmsCauseList.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
								dmsCauseList.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
								dmsCauseList.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
								dmsCauseList.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
								dmsCauseList.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
								dmsCauseList.setCl_ano(props.getIa_no() !=null && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
								dmsCauseList.setCl_ayr(props.getIa_year()!=null && !props.getIa_year().equals("N") && !props.getIa_year().equals("    ") ? Integer.parseInt(props.getIa_year()) : null);
								dmsCauseList.setCl_dol(date3);
								dmsCauseList.setCl_notice_no(!props4.getNoticeNo().isEmpty() ? props4.getNoticeNo() : null);
								dmsCauseList.setCl_district_name(!props4.getDistrictName().isEmpty() ? props4.getDistrictName() : null);
								dmsCauseList.setCl_injail_date(!props4.getInJailSinceDate().isEmpty() ? props4.getInJailSinceDate() : null);
								dmsCauseList.setCl_crime_no(!props4.getCrimeNo().isEmpty() ? props4.getCrimeNo() : null);
								dmsCauseList.setCl_crime_year(!props4.getCrimeYear().isEmpty() ? props4.getCrimeYear() : null);
								dmsCauseList.setCl_crime_dist(!props4.getCrimeDistName().isEmpty() ? props4.getCrimeDistName() : null);
								dmsCauseList.setCl_crime_ps(!props4.getCrimePoliceStnName().isEmpty() ? props4.getCrimePoliceStnName() :null);
								dmsCauseList.setCl_iscrime(!props4.getIsCrime().isEmpty() ? props4.getIsCrime() :"N");
								dmsCauseList.setCl_short_order(!props4.getShortOrderName().isEmpty() ? props4.getShortOrderName() :null);
								dmsCauseList.setCl_app_last_date(!props4.getLastListingDate().isEmpty() ? props4.getLastListingDate() :null);
								dmsCauseList.setCl_applied_by(!props4.getApplicationAppliedBy().isEmpty() ? props4.getApplicationAppliedBy() :null);
								dmsCauseList.setCl_next_list(!props4.getNextDateInSystem().isEmpty() ? props4.getNextDateInSystem() :null);
								dmsCauseList.setCl_lcr_no(!props4.getLowerCourtLCRNo().isEmpty() ? props4.getLowerCourtLCRNo() :null);
								dmsCauseList.setCl_lcr_year(!props4.getLowerCourtLCRYear().isEmpty() ? props4.getLowerCourtLCRYear() :null);
								dmsCauseList.setCl_ccms_id(Long.parseLong(props.getCase_id()));
								
								
								
								
						
								
								
								CaseFileDetail casefile = new CaseFileDetail();
								casefile.setFd_case_no(dmsCauseList.getCl_case_no());
								casefile.setFd_case_type(dmsCauseList.getCl_case_type_mid());
								casefile.setFd_case_year(dmsCauseList.getCl_case_year());

								CaseFileDetail caseFileDetail = benchService.getCaseFile(casefile);
								if (caseFileDetail != null) {
									dmsCauseList.setCl_fd_mid(caseFileDetail.getFd_id());
									
								} 
								benchService.save(dmsCauseList);
								System.out.println("saved");
							 /*
							 if(props.getConnectedCase()=="Y" && ( clt2.getClt_id()!=1L ||  clt2.getClt_id()!=2L ||  clt2.getClt_id()!=21L
									 ||  clt2.getClt_id()!=20L ||  clt2.getClt_id()!=23L ||  clt2.getClt_id()!=24L ||  clt2.getClt_id()!=28L)) {*/
								

								/* if(props.getConnectedCase().equals("Y") && clt2.getClt_id()!=1L) {*/
								 if(true && clt2.getClt_id()!=1L) {
									 
									/* if(props.getSerialNo().equals("3123") && props.getFor_bench_id()=="B4111") {
										 System.out.println("enter");
										 continue;
									 }*/
							 
							 MultiValueMap<String, String> map2 = new LinkedMultiValueMap<>();
								 map2.add("Case_no",props.getCase_Number());
								 map2.add("ListingDate", dateFormat1.format(date3));
								 map2.add("ListType",clt2.getClt_ccms_list().toString());

								 HttpEntity<MultiValueMap<String, String>> entity2 = new HttpEntity<>(map2, headers);

								 
								 HttpEntity<Object> response2 =
								     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCaseDetailByConnectedCase",
								                           HttpMethod.POST,
								                           entity2,
								                           Object.class);
								 
								 List<Object> mv1=(List<Object>) response2.getBody();
								 
									
								 
								 for(Object t1 :mv1) {
									 
									 ObjectMapper m1 = new ObjectMapper();
									 CauseApi props1 = m1.convertValue(t1, CauseApi.class);
									 
									 
									 
									 CauseList dmsCauseList2 = new CauseList();
									// CauseListNew dmsCauseList21 = new CauseListNew();
									 CaseType ct1 = benchService.getCaseTypeFromLabel(props1.getCase_type_name());
									 dmsCauseList2.setCl_case_type_mid(ct1.getCt_id());
									 dmsCauseList2.setCl_case_no(props1.getReg_no());
									 dmsCauseList2.setCl_case_year(Integer.parseInt(props1.getReg_year()));
									 dmsCauseList2.setCl_court_no(cmlist.get(bid).getCm_id());
									 dmsCauseList2.setCisCaseNo(props1.getCase_Number());
									 dmsCauseList2.setCl_rec_status(1);
									 dmsCauseList2.setCl_list_type_mid(clt2.getClt_id());
									 dmsCauseList2.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
									 dmsCauseList2.setCl_first_petitioner(props1.getPetname() !=null ? props1.getPetname() : " .");
									 dmsCauseList2.setCl_first_respondent(props1.getResname()  !=null ? props1.getResname() : " .");
									 dmsCauseList2.setCl_petitioner_council(props1.getPet_adv());
									 dmsCauseList2.setCl_respondent_council(props1.getRes_adv());
									 dmsCauseList2.setCl_dol(date3);
									 dmsCauseList2.setCl_ccms_id(Long.parseLong(props1.getCase_id()));
									 
									 
									 
								
									 
									 
										
									 CaseFileDetail casefile1 = new CaseFileDetail();
										casefile1.setFd_case_no(dmsCauseList2.getCl_case_no());
										casefile1.setFd_case_type(dmsCauseList2.getCl_case_type_mid());
										casefile1.setFd_case_year(dmsCauseList2.getCl_case_year());

										CaseFileDetail caseFileDetail1 = benchService.getCaseFile(casefile1);
										if (caseFileDetail1 != null) {
											dmsCauseList2.setCl_fd_mid(caseFileDetail1.getFd_id());
											
										} 
										benchService.save(dmsCauseList2);
									
										System.out.println("saved");
									 
								 }
								 }
							 }
						 
						 System.out.println(t);
					}
						 }
						 map.clear();
						 
					 }
					 map5.clear();
					 
					
				 }
			 
			 }
		
		
			if(serviceStatus==1) {
				response.setResponse("TRUE");
			}
			else {
				response.setResponse("FALSE");
			}
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		
	}
	
	@RequestMapping(value = "/nextCorrection", method = RequestMethod.POST)
	public @ResponseBody String nextCorrection(@RequestBody CourtMaster courtmaster, HttpSession session) {
		


		Integer serviceStatus=0;
		String jsonData = "";
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-YYYY");
		 DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date currentDate =new Date();
		 
		 Date date3=null;
		 Date date2=null;
		 
		 Date date4=null;
		 
		 String date=null;
			try {
				//date2 =dateFormat1.parse(dateFormat1.format(currentDate));
				date =dateFormat1.format(courtmaster.getClDate());
				String date5=dateFormat2.format(courtmaster.getClDate());
				 date2 = dateFormat1.parse(date);
				 date3=dateFormat2.parse(date5);
				 
				  date4=dateFormat2.parse(date5);
				 System.out.println(date4);
				 System.out.println(date4);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RestTemplate restTemplate = new RestTemplate();
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			 
			 MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			
			
				 
				 List<CauseListType> clt=benchService.getCuaselistTypeCorrection();
			
			//List<CauseListType> clt=benchService.getCuaselistTypeSupp();
			
			
			
			for(CauseListType clt2 : clt) {
				 map.add("BenchId",courtmaster.getCm_bench_id().toString());
				 map.add("CauseListDate", dateFormat1.format(date3));
			     map.add("CauseListType",clt2.getClt_ccms_list().toString());
				  // map.add("CauseListType","1008");
				// map.add("CauseId","1003");
				 
				 
				 
				 HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
				 
				 benchService.deleteCltype(dateFormat2.format(courtmaster.getClDate()),courtmaster.getCm_id(),clt2.getClt_id());
				 serviceStatus=1;

				 
				 HttpEntity<Object> response1 =
				     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCorrectionCauselistData",
						/* restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetIAMentionCauseList",*/
				                           HttpMethod.POST,
				                           entity,
				                           Object.class);
				 List<Object> mv=(List<Object>) response1.getBody();
				 
				
				 
				 if(mv.size()!=0) {
					
				 for(Object t :mv) {
					 
					 ObjectMapper m = new ObjectMapper();
					 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					 CauseApi props = m.convertValue(t, CauseApi.class);
					 
					 CauseList dmsCauseList = new CauseList();
					 
					// CauseListNew dmsCauseList1 = new CauseListNew();
					 
					 
					 String [] bnName=props.getBench_Name().split(",");
					 
					 String [] joCode=(props.getJo_code()!=null ? props.getJo_code().split(",") : null);
					 String jgName="";
					 if(joCode!=null && joCode.length !=0) {
					 for (int i = 0; i < joCode.length; i++) {
							 jgName=jgName+", Hon'ble Justice "+(String) bnName[i];
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) joCode[i] ,date3);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  joCode[i]).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) bnName[i]);
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						}
					 }
					 else {
						 if( props.getJo_code()!=null) {
						 jgName=jgName+", Hon'ble Justice "+(String) props.getBench_Name();
							//judge mapping Multiple judge in one bench
							
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) props.getJo_code() ,date2);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  props.getJo_code()).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) props.getBench_Name());
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						 }
					 }
					 
					 
					 courtmaster.setCm_judges_name(props.getBench_Name());
					 benchService.save(courtmaster);
					  
					  if(!props.getSerialNo().contentEquals("") ){
					 
						  CaseType ct = benchService.getCaseTypeFromLabel(props.getCase_type_name());
					 dmsCauseList.setCl_case_type_mid(ct.getCt_id());
					 dmsCauseList.setCl_case_no(props.getReg_no());
					 dmsCauseList.setCl_case_year(Integer.parseInt(props.getReg_year()));
					 dmsCauseList.setCl_court_no(courtmaster.getCm_id());
						dmsCauseList.setCisCaseNo(props.getCase_Number());
						dmsCauseList.setCl_rec_status(1);
						dmsCauseList.setCl_list_type_mid(clt2.getClt_id());
						dmsCauseList.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						dmsCauseList.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						dmsCauseList.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						dmsCauseList.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						dmsCauseList.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						dmsCauseList.setCl_ano(props.getIa_no() !=null && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						dmsCauseList.setCl_ayr(props.getIa_year()!=null && !props.getIa_year().equals("N") && !props.getIa_year().equals("    ") ? Integer.parseInt(props.getIa_year()) : null);
						dmsCauseList.setCl_dol(date3);
						dmsCauseList.setCl_iscrime("N");
						
						CauseListNew dmsCauseList1 =new CauseListNew();
						
						dmsCauseList1.setCl_case_type_mid(ct.getCt_id());
						dmsCauseList1.setCl_case_no(props.getReg_no());
						dmsCauseList1.setCl_case_year(Integer.parseInt(props.getReg_year()));
						dmsCauseList1.setCl_court_no(courtmaster.getCm_id());
						dmsCauseList1.setCisCaseNo(props.getCase_Number());
						dmsCauseList1.setCl_rec_status(1);
						dmsCauseList1.setCl_list_type_mid(clt2.getClt_id());
						dmsCauseList1.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						dmsCauseList1.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						dmsCauseList1.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						dmsCauseList1.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						dmsCauseList1.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						dmsCauseList1.setCl_ano(props.getIa_no() !=null && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						dmsCauseList1.setCl_ayr(props.getIa_year()!=null && !props.getIa_year().equals("N") && !props.getIa_year().equals("    ") ? Integer.parseInt(props.getIa_year()) : null);
						dmsCauseList1.setCl_dol(date3);
						dmsCauseList1.setCl_iscrime("N");
						
						
						CaseFileDetail casefile = new CaseFileDetail();
						casefile.setFd_case_no(dmsCauseList.getCl_case_no());
						casefile.setFd_case_type(dmsCauseList.getCl_case_type_mid());
						casefile.setFd_case_year(dmsCauseList.getCl_case_year());

						CaseFileDetail caseFileDetail = benchService.getCaseFile(casefile);
						if (caseFileDetail != null) {
							dmsCauseList.setCl_fd_mid(caseFileDetail.getFd_id());
							dmsCauseList1.setCl_fd_mid(caseFileDetail.getFd_id());
							
							dmsCauseList.setCl_file_source(caseFileDetail.getFd_file_source().trim());
						} 
						dmsCauseList = benchService.save(dmsCauseList);
						dmsCauseList1=benchService.save(dmsCauseList1);
						System.out.println("saved");
					 /*
					 if(props.getConnectedCase()=="Y" && ( clt2.getClt_id()!=1L ||  clt2.getClt_id()!=2L ||  clt2.getClt_id()!=21L
							 ||  clt2.getClt_id()!=20L ||  clt2.getClt_id()!=23L ||  clt2.getClt_id()!=24L ||  clt2.getClt_id()!=28L)) {*/
						

						
					 }
				 
				 System.out.println(t);
			}
				 }
				 map.clear();
				 
			 }
		
		
			if(serviceStatus==1) {
				response.setResponse("TRUE");
			}
			else {
				response.setResponse("FALSE");
			}
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		
		
	
	}
	
	@RequestMapping(value = "/correctionCourt", method = RequestMethod.POST)
	public @ResponseBody String getCorrection(@RequestBody CourtMaster courtmaster, HttpSession session) {

		Integer serviceStatus=0;
		String jsonData = "";
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-YYYY");
		 DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date currentDate =new Date();
		 
		 Date date3=null;
		 Date date2=null;
		 
		 Date date4=null;
		 
		 String date=null;
			try {
				//date2 =dateFormat1.parse(dateFormat1.format(currentDate));
				date =dateFormat1.format(currentDate);
				String date5=dateFormat2.format(currentDate);
				 date2 = dateFormat1.parse(date);
				 date3=dateFormat2.parse(date5);
				 
				  date4=dateFormat2.parse(date5);
				 System.out.println(date4);
				 System.out.println(date4);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RestTemplate restTemplate = new RestTemplate();
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			 
			 MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			
			
				 
				 List<CauseListType> clt=benchService.getCuaselistTypeCorrection();
			
			//List<CauseListType> clt=benchService.getCuaselistTypeSupp();
			
			
			
			for(CauseListType clt2 : clt) {
				 map.add("BenchId",courtmaster.getCm_bench_id().toString());
				 map.add("CauseListDate", dateFormat1.format(date3));
				map.add("CauseListType",clt2.getClt_ccms_list().toString());
				// map.add("CauseListType","1017");
				// map.add("CauseId","1003");
				 
				 
				 
				 HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
				 
				 benchService.deleteCltype(dateFormat2.format(currentDate),courtmaster.getCm_id(),clt2.getClt_id());
				 serviceStatus=1;

				 
				 HttpEntity<Object> response1 =
				     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCorrectionCauselistData",
						/* restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetIAMentionCauseList",*/
				                           HttpMethod.POST,
				                           entity,
				                           Object.class);
				 List<Object> mv=(List<Object>) response1.getBody();
				 
				
				 
				 if(mv.size()!=0) {
					
				 for(Object t :mv) {
					 
					 ObjectMapper m = new ObjectMapper();
					 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					 CauseApi props = m.convertValue(t, CauseApi.class);
					 
					 CauseList dmsCauseList = new CauseList();
					 
					// CauseListNew dmsCauseList1 = new CauseListNew();
					 
					 
					 String [] bnName=props.getBench_Name().split(",");
					 
					 String [] joCode=(props.getJo_code()!=null ? props.getJo_code().split(",") : null);
					 String jgName="";
					 if(joCode!=null && joCode.length !=0) {
					 for (int i = 0; i < joCode.length; i++) {
							 jgName=jgName+", Hon'ble Justice "+(String) bnName[i];
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) joCode[i] ,date3);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  joCode[i]).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) bnName[i]);
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						}
					 }
					 else {
						 if( props.getJo_code()!=null) {
						 jgName=jgName+", Hon'ble Justice "+(String) props.getBench_Name();
							//judge mapping Multiple judge in one bench
							
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) props.getJo_code() ,date2);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  props.getJo_code()).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) props.getBench_Name());
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						 }
					 }
					 
					 
					 courtmaster.setCm_judges_name(props.getBench_Name());
					 benchService.save(courtmaster);
					  
					  if(!props.getSerialNo().contentEquals("") ){
					 
						  CaseType ct = benchService.getCaseTypeFromLabel(props.getCase_type_name());
					 dmsCauseList.setCl_case_type_mid(ct.getCt_id());
					 dmsCauseList.setCl_case_no(props.getReg_no());
					 dmsCauseList.setCl_case_year(Integer.parseInt(props.getReg_year()));
					 dmsCauseList.setCl_court_no(courtmaster.getCm_id());
						dmsCauseList.setCisCaseNo(props.getCase_Number());
						dmsCauseList.setCl_rec_status(1);
						dmsCauseList.setCl_list_type_mid(clt2.getClt_id());
						dmsCauseList.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						dmsCauseList.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						dmsCauseList.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						dmsCauseList.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						dmsCauseList.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						dmsCauseList.setCl_ano(props.getIa_no() !=null && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						dmsCauseList.setCl_ayr(props.getIa_year()!=null && !props.getIa_year().equals("N") && !props.getIa_year().equals("    ") ? Integer.parseInt(props.getIa_year()) : null);
						dmsCauseList.setCl_dol(date3);
						dmsCauseList.setCl_iscrime("N");
						
						
						CaseFileDetail casefile = new CaseFileDetail();
						casefile.setFd_case_no(dmsCauseList.getCl_case_no());
						casefile.setFd_case_type(dmsCauseList.getCl_case_type_mid());
						casefile.setFd_case_year(dmsCauseList.getCl_case_year());

						CaseFileDetail caseFileDetail = benchService.getCaseFile(casefile);
						if (caseFileDetail != null) {
							dmsCauseList.setCl_fd_mid(caseFileDetail.getFd_id());
							
							dmsCauseList.setCl_file_source(caseFileDetail.getFd_file_source().trim());
						} 
						benchService.save(dmsCauseList);
						System.out.println("saved");
					 /*
					 if(props.getConnectedCase()=="Y" && ( clt2.getClt_id()!=1L ||  clt2.getClt_id()!=2L ||  clt2.getClt_id()!=21L
							 ||  clt2.getClt_id()!=20L ||  clt2.getClt_id()!=23L ||  clt2.getClt_id()!=24L ||  clt2.getClt_id()!=28L)) {*/
						

						
					 }
				 
				 System.out.println(t);
			}
				 }
				 map.clear();
				 
			 }
		
		
			if(serviceStatus==1) {
				response.setResponse("TRUE");
			}
			else {
				response.setResponse("FALSE");
			}
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		
		
	}
	
	@RequestMapping(value = "/nextDayGen", method = RequestMethod.POST)
	public @ResponseBody String nextDayGen(@RequestBody CourtMaster courtmaster, HttpSession session) {
		

		Integer serviceStatus=0;
		String jsonData = "";
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-YYYY");
		 DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date currentDate =new Date();
		 
		 Date date3=null;
		 Date date2=null;
		 
		 Date date4=null;
		 
		 String date=null;
			try {
				//date2 =dateFormat1.parse(dateFormat1.format(currentDate));
				date =dateFormat1.format(courtmaster.getClDate());
				String date5=dateFormat2.format(courtmaster.getClDate());
				 date2 = dateFormat1.parse(date);
				 date3=dateFormat2.parse(date5);
				 
				  date4=dateFormat2.parse(date5);
				 System.out.println(date4);
				 System.out.println(date4);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RestTemplate restTemplate = new RestTemplate();
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			 
			 MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			
			MultiValueMap<String, String> map5 = new LinkedMultiValueMap<>();
			 
			 map5.add("BenchId",courtmaster.getCm_bench_id().toString());
			 map5.add("CauseListDate", dateFormat1.format(date3));
			 
			 HttpEntity<MultiValueMap<String, String>> entity5 = new HttpEntity<>(map5, headers);
			 
			 HttpEntity<Object> response5 =
				     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCauseListTypeByBenchId",
				                           HttpMethod.POST,
				                           entity5,
				                           Object.class);
				 List<Object> mv5=(List<Object>) response5.getBody();
				 
				 ObjectMapper m5 = new ObjectMapper();
				 m5.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				 CauseListTypeCcms props5 = m5.convertValue(mv5.get(0), CauseListTypeCcms.class);
				 
				 List<CauseListType> clt=benchService.getNextCauseList(props5.getCauselistType());
			
			//List<CauseListType> clt=benchService.getCuaselistTypeSupp();
			
			
			
			for(CauseListType clt2 : clt) {
				

				 map.add("BenchId",courtmaster.getCm_bench_id().toString());
				 map.add("CauseListDate", date);
				 map.add("CauseId",clt2.getClt_ccms_list().toString());
				// map.add("CauseId","1003");
				 
				 
				 
				 HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
				 
				
				// dmsService.deleteCltypeNew(date3,cmlist.get(bid).getCm_id(),clt2.getClt_id());

				 
				 HttpEntity<Object> response1 =
				     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCourtCaseList",
				                           HttpMethod.POST,
				                           entity,
				                           Object.class);
				 List<Object> mv=(List<Object>) response1.getBody();
				 
				
				 
				 if(mv.size()!=0) {
					 benchService.deleteCltype(dateFormat2.format(courtmaster.getClDate()),courtmaster.getCm_id(),clt2.getClt_id());
					 serviceStatus=1;
				 for(Object t :mv) {
					
					
					 ObjectMapper m = new ObjectMapper();
					 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					 CauseApi props = m.convertValue(t, CauseApi.class);
					 
					 CauseList dmsCauseList = new CauseList();
					 CauseListNew dmsCauseList1 = new CauseListNew();
					 dmsCauseList.setCl_iscrime("N");
					 
					// CauseListNew dmsCauseList1 = new CauseListNew();
					 
					 
					 String [] bnName=props.getBench_Name().split(",");
					 
					 String [] joCode=(props.getJo_code()!=null ? props.getJo_code().split(",") : null);
					 String jgName="";
					 if(joCode!=null && joCode.length !=0) {
					 for (int i = 0; i < bnName.length; i++) {
							 jgName=jgName+", Hon'ble Justice "+(String) bnName[i];
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) joCode[i] ,date3);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  joCode[i]).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) bnName[i]);
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						}
					 }
					 else {
						 if( props.getJo_code()!=null) {
						 jgName=jgName+", Hon'ble Justice "+(String) props.getBench_Name();
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) props.getJo_code() ,date2);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  props.getJo_code()).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) props.getBench_Name());
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						 }
					 }
					 
					 
					 courtmaster.setCm_judges_name(props.getBench_Name());
					 benchService.save(courtmaster);
					 
					 
						///////////////////////////
					 MultiValueMap<String, String> mapExtraa = new LinkedMultiValueMap<>();
					 mapExtraa.add("ListingDate",dateFormat1.format(date3));	
					 mapExtraa.add("CINO",props.getCino());

					 HttpEntity<MultiValueMap<String, String>> entityExtra = new HttpEntity<>(mapExtraa, headers);

					 
					 HttpEntity<Object> responseExtraa = null;
					 try{
						 responseExtraa= restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GETEcourtCauseList",
		                           HttpMethod.POST,
		                           entityExtra,
		                           Object.class);
					 }
					 catch(Exception e)
					 {
						 e.printStackTrace();
						/* continue;*/
					 }
					 
					 ExtraCauseListInfo props4=new ExtraCauseListInfo();
					 
					 List<Object> mv1Extra=(List<Object>) responseExtraa.getBody();
					 
					 if(mv1Extra.size()!=0) {
							 ObjectMapper m2 = new ObjectMapper();
							 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
							 props4 = m2.convertValue(mv1Extra.get(0), ExtraCauseListInfo.class);
					 }
					 
						/////
					  
					  if(!props.getSerialNo().contentEquals("") && props.getCase_type_name()!=null){
					 
					 CaseType ct = benchService.getCaseTypeFromLabel(props.getCase_type_name());
					 if(ct==null) {
						 System.out.println("error");
					 }
					 dmsCauseList.setCl_case_type_mid(ct.getCt_id());
					 dmsCauseList.setCl_case_no(props.getReg_no());
					 dmsCauseList.setCl_case_year(Integer.parseInt(props.getReg_year()));
					 dmsCauseList.setCl_court_no(courtmaster.getCm_id());
						dmsCauseList.setCisCaseNo(props.getCase_Number());
						dmsCauseList.setCl_rec_status(1);
						dmsCauseList.setCl_list_type_mid(clt2.getClt_id());
						dmsCauseList.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						dmsCauseList.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						dmsCauseList.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						dmsCauseList.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						dmsCauseList.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						dmsCauseList.setCl_ano(props.getIa_no() !=null && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						dmsCauseList.setCl_ayr(props.getIa_year()!=null && !props.getIa_year().equals("N") && !props.getIa_year().equals("    ") ? Integer.parseInt(props.getIa_year()) : null);
						dmsCauseList.setCl_dol(date3);
						if(props4.getNoticeNo() !=null) {
						dmsCauseList.setCl_notice_no(!props4.getNoticeNo().isEmpty() ? props4.getNoticeNo() : null);
						dmsCauseList.setCl_district_name(!props4.getDistrictName().isEmpty() ? props4.getDistrictName() : null);
						dmsCauseList.setCl_injail_date(!props4.getInJailSinceDate().isEmpty() ? props4.getInJailSinceDate() : null);
						dmsCauseList.setCl_crime_no(!props4.getCrimeNo().isEmpty() ? props4.getCrimeNo() : null);
						dmsCauseList.setCl_crime_year(!props4.getCrimeYear().isEmpty() ? props4.getCrimeYear() : null);
						dmsCauseList.setCl_crime_dist(!props4.getCrimeDistName().isEmpty() ? props4.getCrimeDistName() : null);
						dmsCauseList.setCl_crime_ps(!props4.getCrimePoliceStnName().isEmpty() ? props4.getCrimePoliceStnName() :null);
						dmsCauseList.setCl_iscrime(!props4.getIsCrime().isEmpty() ? props4.getIsCrime() :"N");
						dmsCauseList.setCl_short_order(!props4.getShortOrderName().isEmpty() ? props4.getShortOrderName() :null);
						dmsCauseList.setCl_app_last_date(!props4.getLastListingDate().isEmpty() ? props4.getLastListingDate() :null);
						dmsCauseList.setCl_applied_by(!props4.getApplicationAppliedBy().isEmpty() ? props4.getApplicationAppliedBy() :null);
						dmsCauseList.setCl_next_list(!props4.getNextDateInSystem().isEmpty() ? props4.getNextDateInSystem() :null);
						dmsCauseList.setCl_lcr_no(!props4.getLowerCourtLCRNo().isEmpty() ? props4.getLowerCourtLCRNo() :null);
						dmsCauseList.setCl_lcr_year(!props4.getLowerCourtLCRYear().isEmpty() ? props4.getLowerCourtLCRYear() :null);
						}
						dmsCauseList.setCl_ccms_id(Long.parseLong(props.getCase_id()));
						
						
						
						
						dmsCauseList1.setCl_case_type_mid(ct.getCt_id());
						dmsCauseList1.setCl_case_no(props.getReg_no());
						dmsCauseList1.setCl_case_year(Integer.parseInt(props.getReg_year()));
						dmsCauseList1.setCl_court_no(courtmaster.getCm_id());
						dmsCauseList1.setCisCaseNo(props.getCase_Number());
						dmsCauseList1.setCl_bench_number(courtmaster.getCm_bench_id());
						dmsCauseList1.setCl_rec_status(1);
						dmsCauseList1.setCl_list_type_mid(clt2.getClt_id());
						dmsCauseList1.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						dmsCauseList1.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						dmsCauseList1.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						dmsCauseList1.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						dmsCauseList1.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						dmsCauseList1.setCl_ano(props.getIa_no() !=null && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						dmsCauseList1.setCl_ayr(props.getIa_year()!=null && !props.getIa_year().equals("N") && !props.getIa_year().equals("    ") ? Integer.parseInt(props.getIa_year()) : null);
						dmsCauseList1.setCl_dol(date3);
							if(props4.getNoticeNo() !=null) {
								dmsCauseList1.setCl_notice_no(!props4.getNoticeNo().isEmpty() ? props4.getNoticeNo() : null);
								dmsCauseList1.setCl_district_name(!props4.getDistrictName().isEmpty() ? props4.getDistrictName() : null);
								dmsCauseList1.setCl_injail_date(!props4.getInJailSinceDate().isEmpty() ? props4.getInJailSinceDate() : null);
								dmsCauseList1.setCl_crime_no(!props4.getCrimeNo().isEmpty() ? props4.getCrimeNo() : null);
								dmsCauseList1.setCl_crime_year(!props4.getCrimeYear().isEmpty() ? props4.getCrimeYear() : null);
								dmsCauseList1.setCl_crime_dist(!props4.getCrimeDistName().isEmpty() ? props4.getCrimeDistName() : null);
								dmsCauseList1.setCl_crime_ps(!props4.getCrimePoliceStnName().isEmpty() ? props4.getCrimePoliceStnName() :null);
								dmsCauseList1.setCl_iscrime(!props4.getIsCrime().isEmpty() ? props4.getIsCrime() :"N");
								dmsCauseList1.setCl_short_order(!props4.getShortOrderName().isEmpty() ? props4.getShortOrderName() :null);
								dmsCauseList1.setCl_app_last_date(!props4.getLastListingDate().isEmpty() ? props4.getLastListingDate() :null);
								dmsCauseList1.setCl_applied_by(!props4.getApplicationAppliedBy().isEmpty() ? props4.getApplicationAppliedBy() :null);
								dmsCauseList1.setCl_next_list(!props4.getNextDateInSystem().isEmpty() ? props4.getNextDateInSystem() :null);
								dmsCauseList1.setCl_lcr_no(!props4.getLowerCourtLCRNo().isEmpty() ? props4.getLowerCourtLCRNo() :null);
								dmsCauseList1.setCl_lcr_year(!props4.getLowerCourtLCRYear().isEmpty() ? props4.getLowerCourtLCRYear() :null);
							}
							dmsCauseList1.setCl_ccms_id(Long.parseLong(props.getCase_id()));
						
						
						
						CaseFileDetail casefile = new CaseFileDetail();
						casefile.setFd_case_no(dmsCauseList.getCl_case_no());
						casefile.setFd_case_type(dmsCauseList.getCl_case_type_mid());
						casefile.setFd_case_year(dmsCauseList.getCl_case_year());

						CaseFileDetail caseFileDetail = benchService.getCaseFile(casefile);
						if (caseFileDetail != null) {
							dmsCauseList.setCl_fd_mid(caseFileDetail.getFd_id());
							dmsCauseList1.setCl_fd_mid(caseFileDetail.getFd_id());
							
						} 
						dmsCauseList = benchService.save(dmsCauseList);
						dmsCauseList1=benchService.save(dmsCauseList1);
						
						
						
						
						
						/*
						
						CaseFileDetail casefile = new CaseFileDetail();
						casefile.setFd_case_no(dmsCauseList.getCl_case_no());
						casefile.setFd_case_type(dmsCauseList.getCl_case_type_mid());
						casefile.setFd_case_year(dmsCauseList.getCl_case_year());

						CaseFileDetail caseFileDetail = benchService.getCaseFile(casefile);
						if (caseFileDetail != null) {
							dmsCauseList.setCl_fd_mid(caseFileDetail.getFd_id());
							
						} 
						dmsCauseList = benchService.save(dmsCauseList);
						System.out.println("saved");*/
						
						
						if(props4.getIsCrime() != null &&  props4.getIsCrime().trim().equals("Y")) {
							for(SameCrimeCcms sc : props4.getCaseDetailsOfSameCrimeNoList()) {
								SameCrimDetails scd=new SameCrimDetails();
								SameCrimeDetailsNew scd1=new SameCrimeDetailsNew();
								
								String [] caseN=sc.getDisplayCaseno().split("/");

							 CaseType ct2 = benchService.getCaseTypeFromLabel(caseN[0]);
							 
							 
							 CaseFileDetail casefile2 = new CaseFileDetail();
							 if(ct2!=null) {
								 casefile2.setFd_case_no(caseN[1]);
								 casefile2.setFd_case_type(ct2.getCt_id());
								 casefile2.setFd_case_year(Integer.parseInt(caseN[2]));
							 }

								CaseFileDetail caseFileDetail2 = benchService.getCaseFile(casefile2);

								if(caseFileDetail2!=null) {
								scd.setScd_fd_mid(caseFileDetail2.getFd_id());
								}
								scd.setScd_cl_id(dmsCauseList.getCl_id());
								scd.setScd_dol(dmsCauseList.getCl_dol());
								scd.setScd_display(sc.getDisplayCaseno());
								scd.setScd_pet_name(sc.getPetName());
								scd.setScd_res_name(sc.getResName());
								scd.setScd_ps_name(sc.getPoliceStName());
								scd.setScd_case_status(sc.getCaseStatus());
								scd.setScd_ps_code(sc.getPS_Code());
								scd.setScd_disp_date(sc.getOrderDate());
								scd.setScd_jo_code(sc.getJudge_code());
								
								benchService.save(scd);
							}
							
						
						}
						
						
						if(props4.getCaseDetailsOfSameLCRNoList().size()!=0) {
							for(SameLCRccms sl : props4.getCaseDetailsOfSameLCRNoList()) {
								SameLcrDetails sld=new SameLcrDetails();
							//	SameCrimeDetailsNew scd1=new SameCrimeDetailsNew();
								
								String [] caseN=sl.getDisplayCaseno().split("/");

							 CaseType ct2 = benchService.getCaseTypeFromLabel(caseN[0]);
							 
							 
							 CaseFileDetail casefile1 = new CaseFileDetail();
							 if(ct2!=null) {
								casefile1.setFd_case_no(caseN[1]);
								casefile1.setFd_case_type(ct2.getCt_id());
								casefile1.setFd_case_year(Integer.parseInt(caseN[2]));
							 }

								CaseFileDetail caseFileDetail1 = benchService.getCaseFile(casefile1);

								if(caseFileDetail1!=null) {
									sld.setSld_fd_mid(caseFileDetail1.getFd_id());
								}
								sld.setSld_cl_mid(dmsCauseList.getCl_id());
								sld.setSld_dol(dmsCauseList.getCl_dol());
								sld.setSld_display(sl.getDisplayCaseno());
								sld.setSld_party(sl.getPartyDetail());
								sld.setSld_status(sl.getCaseStatus());
								sld.setSld_disp_date(sl.getDisposedDate());
								
								benchService.save(sld);
								

							}
						}
					 
					/* if(props.getConnectedCase()=="Y" && ( clt2.getClt_id()!=1L ||  clt2.getClt_id()!=2L ||  clt2.getClt_id()!=21L
							 ||  clt2.getClt_id()!=20L ||  clt2.getClt_id()!=23L ||  clt2.getClt_id()!=24L ||  clt2.getClt_id()!=28L)) {
						

						 if(props.getConnectedCase().equals("Y") && clt2.getClt_id()!=1L) {*/
						 if(true && clt2.getClt_id()!=1L) {
							 
							
					 
					 MultiValueMap<String, String> map2 = new LinkedMultiValueMap<>();
						 map2.add("Case_no",props.getCase_Number());	
						 map2.add("ListingDate", dateFormat1.format(date3));
						 map2.add("ListType",clt2.getClt_ccms_list().toString());

						 HttpEntity<MultiValueMap<String, String>> entity2 = new HttpEntity<>(map2, headers);

						 
						 HttpEntity<Object> response2 =
						     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCaseDetailByConnectedCase",
						                           HttpMethod.POST,
						                           entity2,
						                           Object.class);
						 
						 List<Object> mv1=(List<Object>) response2.getBody();
						 
							
						 
						 for(Object t1 :mv1) {
							 
							 ObjectMapper m1 = new ObjectMapper();
							 CauseApi props1 = m1.convertValue(t1, CauseApi.class);
							 
							 
							 
							 CauseList dmsCauseList2 = new CauseList();
							 CauseListNew dmsCauseList21 = new CauseListNew();
							
							 CaseType ct1 = benchService.getCaseTypeFromLabel(props1.getCase_type_name());
							 dmsCauseList2.setCl_case_type_mid(ct1.getCt_id());
							 dmsCauseList2.setCl_case_no(props1.getReg_no());
							 dmsCauseList2.setCl_case_year(Integer.parseInt(props1.getReg_year()));
							 dmsCauseList2.setCl_court_no(courtmaster.getCm_id());
							// dmsCauseList2.setCisCaseNo(props1.getCase_Number());
							 dmsCauseList2.setCl_rec_status(1);
							 dmsCauseList2.setCl_list_type_mid(clt2.getClt_id());
							 dmsCauseList2.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
							 dmsCauseList2.setCl_first_petitioner(props1.getPetname() !=null ? props1.getPetname() : " .");
							 dmsCauseList2.setCl_first_respondent(props1.getResname()  !=null ? props1.getResname() : " .");
							 dmsCauseList2.setCl_petitioner_council(props1.getPet_adv());
							 dmsCauseList2.setCl_respondent_council(props1.getRes_adv());
							 dmsCauseList2.setCl_dol(date4);		
								dmsCauseList2.setCl_mod_by(user.getUm_id());
								dmsCauseList2.setCl_mod_date(new Date());
								dmsCauseList2.setCl_iscrime("N");
								dmsCauseList2.setCl_ccms_id(Long.parseLong(props1.getCase_id()));
							 
							 
							
							 
								
//causelist New
								
								
								// CaseType ct1 = benchService.getCaseTypeFromLabel(props1.getCase_type_name());
								 dmsCauseList21.setCl_case_type_mid(ct1.getCt_id());
								 dmsCauseList21.setCl_case_no(props1.getReg_no());
								 dmsCauseList21.setCl_case_year(Integer.parseInt(props1.getReg_year()));
								 dmsCauseList21.setCl_court_no(courtmaster.getCm_id());
								 dmsCauseList21.setCl_bench_number(courtmaster.getCm_bench_id());
								// dmsCauseList2.setCisCaseNo(props1.getCase_Number());
								 dmsCauseList21.setCl_rec_status(1);
								 dmsCauseList21.setCl_list_type_mid(clt2.getClt_id());
								 dmsCauseList21.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
								 dmsCauseList21.setCl_first_petitioner(props1.getPetname() !=null ? props1.getPetname() : " .");
								 dmsCauseList21.setCl_first_respondent(props1.getResname()  !=null ? props1.getResname() : " .");
								 dmsCauseList21.setCl_petitioner_council(props1.getPet_adv());
								 dmsCauseList21.setCl_respondent_council(props1.getRes_adv());
								 dmsCauseList21.setCl_dol(date4);		
									dmsCauseList21.setCl_mod_by(user.getUm_id());
									dmsCauseList21.setCl_mod_date(new Date());
									dmsCauseList21.setCl_iscrime("N");
									dmsCauseList2.setCl_ccms_id(Long.parseLong(props1.getCase_id()));
							 
							 
							
							 
								
								CaseFileDetail casefile1 = new CaseFileDetail();
								casefile1.setFd_case_no(dmsCauseList2.getCl_case_no());
								casefile1.setFd_case_type(dmsCauseList2.getCl_case_type_mid());
								casefile1.setFd_case_year(dmsCauseList2.getCl_case_year());

								CaseFileDetail caseFileDetail1 = benchService.getCaseFile(casefile1);
								if (caseFileDetail1 != null) {
									dmsCauseList2.setCl_fd_mid(caseFileDetail1.getFd_id());
									dmsCauseList21.setCl_fd_mid(caseFileDetail1.getFd_id());
									
								} 
								benchService.save(dmsCauseList2);
								benchService.save(dmsCauseList21);
								
								
							
								
								System.out.println("saved");
							 
						 }
						 }
					 }
				 
				 System.out.println(t);
			}
				 }
				 map.clear();
				 
			 
			}
		
		
			if(serviceStatus==1) {
				response.setResponse("TRUE");
			}
			else {
				response.setResponse("FALSE");
			}
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		
		
	}
	
	@RequestMapping(value = "/supplimentryCourt", method = RequestMethod.POST)
	public @ResponseBody String getSupplimentry(@RequestBody CourtMaster courtmaster, HttpSession session) {
		Integer serviceStatus=0;
		String jsonData = "";
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-YYYY");
		 DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date currentDate =new Date();
		 
		 Date date3=null;
		 Date date2=null;
		 
		 Date date4=null;
		 
		 String date=null;
			try {
				//date2 =dateFormat1.parse(dateFormat1.format(currentDate));
				date =dateFormat1.format(currentDate);
				String date5=dateFormat2.format(currentDate);
				 date2 = dateFormat1.parse(date);
				 date3=dateFormat2.parse(date5);
				 
				  date4=dateFormat2.parse(date5);
				 System.out.println(date4);
				 System.out.println(date4);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RestTemplate restTemplate = new RestTemplate();
			 HttpHeaders headers = new HttpHeaders();
			 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			 
			 MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			
			MultiValueMap<String, String> map5 = new LinkedMultiValueMap<>();
			 
			 map5.add("BenchId",courtmaster.getCm_bench_id().toString());
			 map5.add("CauseListDate", dateFormat1.format(date3));
			 
			 HttpEntity<MultiValueMap<String, String>> entity5 = new HttpEntity<>(map5, headers);
			 
			 HttpEntity<Object> response5 =
				     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCauseListTypeByBenchId",
				                           HttpMethod.POST,
				                           entity5,
				                           Object.class);
				 List<Object> mv5=(List<Object>) response5.getBody();
				 
				 ObjectMapper m5 = new ObjectMapper();
				 m5.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				 CauseListTypeCcms props5 = m5.convertValue(mv5.get(0), CauseListTypeCcms.class);
				 
				/* List<CauseListType> clt=benchService.getCuaselistTypeSupp3(props5.getCauselistType());*/
				 
				 List<CauseListType> clt=new ArrayList<>();
				 
				 if(courtmaster.getClListType()!=null) {
					 clt=benchService.getCuaselistTypeSupp3(courtmaster.getClListType().toString());
				 }
				 else {
					 clt= benchService.getCuaselistTypeSupp3(props5.getCauselistType());
				 }
			
			//List<CauseListType> clt=benchService.getCuaselistTypeSupp();
			
			
			
			for(CauseListType clt2 : clt) {
				

				 map.add("BenchId",courtmaster.getCm_bench_id().toString());
				 map.add("CauseListDate", date);
				 map.add("CauseId",clt2.getClt_ccms_list().toString());
				// map.add("CauseId","1003");
				 
				 
				 
				 HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
				 
				
				// dmsService.deleteCltypeNew(date3,cmlist.get(bid).getCm_id(),clt2.getClt_id());

				 
				 HttpEntity<Object> response1 =
				     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCourtCaseList",
				                           HttpMethod.POST,
				                           entity,
				                           Object.class);
				 List<Object> mv=(List<Object>) response1.getBody();
				 
				
				 
				 if(mv.size()!=0) {
					 benchService.deleteCltype(dateFormat2.format(currentDate),courtmaster.getCm_id(),clt2.getClt_id());
					 benchService.deleteCltypeNew(dateFormat2.format(currentDate),courtmaster.getCm_id(),clt2.getClt_id());
					 serviceStatus=1;
				 for(Object t :mv) {
					
					
					 ObjectMapper m = new ObjectMapper();
					 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					 CauseApi props = m.convertValue(t, CauseApi.class);
					 
					 CauseList dmsCauseList = new CauseList();
					 CauseListNew dmsCauseList1=new CauseListNew();
					 dmsCauseList.setCl_iscrime("N");
					 
					// CauseListNew dmsCauseList1 = new CauseListNew();
					 
					 
					 String [] bnName=props.getBench_Name().split(",");
					 
					 String [] joCode=(props.getJo_code()!=null ? props.getJo_code().split(",") : null);
					 String jgName="";
					 if(joCode!=null && joCode.length !=0) {
					 for (int i = 0; i < bnName.length; i++) {
							 jgName=jgName+", Hon'ble Justice "+(String) bnName[i];
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) joCode[i] ,date3);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  joCode[i]).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) bnName[i]);
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						}
					 }
					 else {
						 if( props.getJo_code()!=null) {
						 jgName=jgName+", Hon'ble Justice "+(String) props.getBench_Name();
							//judge mapping Multiple judge in one bench
							DMSJudge_mapping jm1=benchService.getJudgeMappingDetails(courtmaster.getCm_bench_id(),(String) props.getJo_code() ,date2);
							if(jm1==null) {
								DMSJudge_mapping judgem1=new DMSJudge_mapping();
								judgem1.setEjm_bench_id(courtmaster.getCm_bench_id());
								judgem1.setEjm_cm_mid(courtmaster.getCm_id());
								judgem1.setEjm_jo_code(((String)  props.getJo_code()).replaceAll("\\s", ""));
								judgem1.setEjm_rec_stauts(1);
								judgem1.setEjm_date(date4);
								judgem1.setEjm_judge_name((String) props.getBench_Name());
								judgem1=benchService.saveJudgeMapping(judgem1);
								System.out.println(judgem1+"done........");
							}
						 }
					 }
					 
					 
					 courtmaster.setCm_judges_name(props.getBench_Name());
					 benchService.save(courtmaster);
					 
					 
						///////////////////////////
					 MultiValueMap<String, String> mapExtraa = new LinkedMultiValueMap<>();
					 mapExtraa.add("ListingDate",dateFormat1.format(date3));	
					 mapExtraa.add("CINO",props.getCino());

					 HttpEntity<MultiValueMap<String, String>> entityExtra = new HttpEntity<>(mapExtraa, headers);

					 
					 HttpEntity<Object> responseExtraa = null;
					 try{
						 responseExtraa= restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GETEcourtCauseList",
		                           HttpMethod.POST,
		                           entityExtra,
		                           Object.class);
					 }
					 catch(Exception e)
					 {
						 e.printStackTrace();
						/* continue;*/
					 }
					 
					 ExtraCauseListInfo props4=new ExtraCauseListInfo();
					 
					 List<Object> mv1Extra=(List<Object>) responseExtraa.getBody();
					 
					 if(mv1Extra.size()!=0) {
							 ObjectMapper m2 = new ObjectMapper();
							 m.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
							 props4 = m2.convertValue(mv1Extra.get(0), ExtraCauseListInfo.class);
					 }
					 
						/////
					  
					  if(!props.getSerialNo().contentEquals("") && props.getCase_type_name()!=null){
					 
					 CaseType ct = benchService.getCaseTypeFromLabel(props.getCase_type_name());
					 if(ct==null) {
						 System.out.println("error");
					 }
					 dmsCauseList.setCl_case_type_mid(ct.getCt_id());
					 dmsCauseList.setCl_case_no(props.getReg_no());
					 dmsCauseList.setCl_case_year(Integer.parseInt(props.getReg_year()));
					 dmsCauseList.setCl_court_no(courtmaster.getCm_id());
						dmsCauseList.setCisCaseNo(props.getCase_Number());
						dmsCauseList.setCl_rec_status(1);
						dmsCauseList.setCl_list_type_mid(clt2.getClt_id());
						dmsCauseList.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						dmsCauseList.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						dmsCauseList.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						dmsCauseList.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						dmsCauseList.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						dmsCauseList.setCl_ano(props.getIa_no() !=null && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						dmsCauseList.setCl_ayr(props.getIa_year()!=null && !props.getIa_year().equals("N") && !props.getIa_year().equals("    ") ? Integer.parseInt(props.getIa_year()) : null);
						dmsCauseList.setCl_dol(date3);
						if(props4.getNoticeNo() !=null) {
						dmsCauseList.setCl_notice_no(!props4.getNoticeNo().isEmpty() ? props4.getNoticeNo() : null);
						dmsCauseList.setCl_district_name(!props4.getDistrictName().isEmpty() ? props4.getDistrictName() : null);
						dmsCauseList.setCl_injail_date(!props4.getInJailSinceDate().isEmpty() ? props4.getInJailSinceDate() : null);
						dmsCauseList.setCl_crime_no(!props4.getCrimeNo().isEmpty() ? props4.getCrimeNo() : null);
						dmsCauseList.setCl_crime_year(!props4.getCrimeYear().isEmpty() ? props4.getCrimeYear() : null);
						dmsCauseList.setCl_crime_dist(!props4.getCrimeDistName().isEmpty() ? props4.getCrimeDistName() : null);
						dmsCauseList.setCl_crime_ps(!props4.getCrimePoliceStnName().isEmpty() ? props4.getCrimePoliceStnName() :null);
						dmsCauseList.setCl_iscrime(!props4.getIsCrime().isEmpty() ? props4.getIsCrime() :"N");
						dmsCauseList.setCl_short_order(!props4.getShortOrderName().isEmpty() ? props4.getShortOrderName() :null);
						dmsCauseList.setCl_app_last_date(!props4.getLastListingDate().isEmpty() ? props4.getLastListingDate() :null);
						dmsCauseList.setCl_applied_by(!props4.getApplicationAppliedBy().isEmpty() ? props4.getApplicationAppliedBy() :null);
						dmsCauseList.setCl_next_list(!props4.getNextDateInSystem().isEmpty() ? props4.getNextDateInSystem() :null);
						dmsCauseList.setCl_lcr_no(!props4.getLowerCourtLCRNo().isEmpty() ? props4.getLowerCourtLCRNo() :null);
						dmsCauseList.setCl_lcr_year(!props4.getLowerCourtLCRYear().isEmpty() ? props4.getLowerCourtLCRYear() :null);
						}
						dmsCauseList.setCl_ccms_id(Long.parseLong(props.getCase_id()));
						
						
						
						///cause List New
						
						
						dmsCauseList1.setCl_case_type_mid(ct.getCt_id());
						dmsCauseList1.setCl_case_no(props.getReg_no());
						dmsCauseList1.setCl_case_year(Integer.parseInt(props.getReg_year()));
						dmsCauseList1.setCl_court_no(courtmaster.getCm_id());
						dmsCauseList1.setCisCaseNo(props.getCase_Number());
						dmsCauseList1.setCl_bench_number(courtmaster.getCm_bench_id());
						dmsCauseList1.setCl_rec_status(1);
						dmsCauseList1.setCl_list_type_mid(clt2.getClt_id());
						dmsCauseList1.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
						dmsCauseList1.setCl_first_petitioner(props.getPet_Name() !=null ? props.getPet_Name() : " .");
						dmsCauseList1.setCl_first_respondent(props.getRes_Name()  !=null ? props.getRes_Name() : " .");
						dmsCauseList1.setCl_petitioner_council(props.getPet_adv() !=null ? props.getPet_adv() : " .");
						dmsCauseList1.setCl_respondent_council(props.getRes_adv() !=null? props.getRes_adv() : " .");
						dmsCauseList1.setCl_ano(props.getIa_no() !=null && !props.getIa_no().equals("N")    ? Integer.parseInt(props.getIa_no()) : null);
						dmsCauseList1.setCl_ayr(props.getIa_year()!=null && !props.getIa_year().equals("N") && !props.getIa_year().equals("    ") ? Integer.parseInt(props.getIa_year()) : null);
						dmsCauseList1.setCl_dol(date3);
							if(props4.getNoticeNo() !=null) {
								dmsCauseList1.setCl_notice_no(!props4.getNoticeNo().isEmpty() ? props4.getNoticeNo() : null);
								dmsCauseList1.setCl_district_name(!props4.getDistrictName().isEmpty() ? props4.getDistrictName() : null);
								dmsCauseList1.setCl_injail_date(!props4.getInJailSinceDate().isEmpty() ? props4.getInJailSinceDate() : null);
								dmsCauseList1.setCl_crime_no(!props4.getCrimeNo().isEmpty() ? props4.getCrimeNo() : null);
								dmsCauseList1.setCl_crime_year(!props4.getCrimeYear().isEmpty() ? props4.getCrimeYear() : null);
								dmsCauseList1.setCl_crime_dist(!props4.getCrimeDistName().isEmpty() ? props4.getCrimeDistName() : null);
								dmsCauseList1.setCl_crime_ps(!props4.getCrimePoliceStnName().isEmpty() ? props4.getCrimePoliceStnName() :null);
								dmsCauseList1.setCl_iscrime(!props4.getIsCrime().isEmpty() ? props4.getIsCrime() :"N");
								dmsCauseList1.setCl_short_order(!props4.getShortOrderName().isEmpty() ? props4.getShortOrderName() :null);
								dmsCauseList1.setCl_app_last_date(!props4.getLastListingDate().isEmpty() ? props4.getLastListingDate() :null);
								dmsCauseList1.setCl_applied_by(!props4.getApplicationAppliedBy().isEmpty() ? props4.getApplicationAppliedBy() :null);
								dmsCauseList1.setCl_next_list(!props4.getNextDateInSystem().isEmpty() ? props4.getNextDateInSystem() :null);
								dmsCauseList1.setCl_lcr_no(!props4.getLowerCourtLCRNo().isEmpty() ? props4.getLowerCourtLCRNo() :null);
								dmsCauseList1.setCl_lcr_year(!props4.getLowerCourtLCRYear().isEmpty() ? props4.getLowerCourtLCRYear() :null);
							}
							dmsCauseList1.setCl_ccms_id(Long.parseLong(props.getCase_id()));
						
						
						
						CaseFileDetail casefile = new CaseFileDetail();
						casefile.setFd_case_no(dmsCauseList.getCl_case_no());
						casefile.setFd_case_type(dmsCauseList.getCl_case_type_mid());
						casefile.setFd_case_year(dmsCauseList.getCl_case_year());

						CaseFileDetail caseFileDetail = benchService.getCaseFile(casefile);
						if (caseFileDetail != null) {
							dmsCauseList.setCl_fd_mid(caseFileDetail.getFd_id());
							dmsCauseList1.setCl_fd_mid(caseFileDetail.getFd_id());
							
						} 
						dmsCauseList = benchService.save(dmsCauseList);
						dmsCauseList1=benchService.save(dmsCauseList1);
						System.out.println("saved");
						
						
						if(props4.getIsCrime() != null &&  props4.getIsCrime().trim().equals("Y")) {
							for(SameCrimeCcms sc : props4.getCaseDetailsOfSameCrimeNoList()) {
								SameCrimDetails scd=new SameCrimDetails();
								SameCrimeDetailsNew scd1=new SameCrimeDetailsNew();
								
								String [] caseN=sc.getDisplayCaseno().split("/");

							 CaseType ct2 = benchService.getCaseTypeFromLabel(caseN[0]);
							 
							 
							 CaseFileDetail casefile2 = new CaseFileDetail();
							 if(ct2!=null) {
								 casefile2.setFd_case_no(caseN[1]);
								 casefile2.setFd_case_type(ct2.getCt_id());
								 casefile2.setFd_case_year(Integer.parseInt(caseN[2]));
							 }

								CaseFileDetail caseFileDetail2 = benchService.getCaseFile(casefile2);

								if(caseFileDetail2!=null) {
								scd.setScd_fd_mid(caseFileDetail2.getFd_id());
								}
								scd.setScd_cl_id(dmsCauseList.getCl_id());
								scd.setScd_dol(dmsCauseList.getCl_dol());
								scd.setScd_display(sc.getDisplayCaseno());
								scd.setScd_pet_name(sc.getPetName());
								scd.setScd_res_name(sc.getResName());
								scd.setScd_ps_name(sc.getPoliceStName());
								scd.setScd_case_status(sc.getCaseStatus());
								scd.setScd_ps_code(sc.getPS_Code());
								scd.setScd_disp_date(sc.getOrderDate());
								scd.setScd_jo_code(sc.getJudge_code());
								
								benchService.save(scd);
							}
							
						
						}
						
						
						if(props4.getCaseDetailsOfSameLCRNoList().size()!=0) {
							for(SameLCRccms sl : props4.getCaseDetailsOfSameLCRNoList()) {
								SameLcrDetails sld=new SameLcrDetails();
							//	SameCrimeDetailsNew scd1=new SameCrimeDetailsNew();
								
								String [] caseN=sl.getDisplayCaseno().split("/");

							 CaseType ct2 = benchService.getCaseTypeFromLabel(caseN[0]);
							 
							 
							 CaseFileDetail casefile1 = new CaseFileDetail();
							 if(ct2!=null) {
								casefile1.setFd_case_no(caseN[1]);
								casefile1.setFd_case_type(ct2.getCt_id());
								casefile1.setFd_case_year(Integer.parseInt(caseN[2]));
							 }

								CaseFileDetail caseFileDetail1 = benchService.getCaseFile(casefile1);

								if(caseFileDetail1!=null) {
									sld.setSld_fd_mid(caseFileDetail1.getFd_id());
								}
								sld.setSld_cl_mid(dmsCauseList.getCl_id());
								sld.setSld_dol(dmsCauseList.getCl_dol());
								sld.setSld_display(sl.getDisplayCaseno());
								sld.setSld_party(sl.getPartyDetail());
								sld.setSld_status(sl.getCaseStatus());
								sld.setSld_disp_date(sl.getDisposedDate());
								
								benchService.save(sld);
								

							}
						}
					 
					/* if(props.getConnectedCase()=="Y" && ( clt2.getClt_id()!=1L ||  clt2.getClt_id()!=2L ||  clt2.getClt_id()!=21L
							 ||  clt2.getClt_id()!=20L ||  clt2.getClt_id()!=23L ||  clt2.getClt_id()!=24L ||  clt2.getClt_id()!=28L)) {
						

						 if(props.getConnectedCase().equals("Y") && clt2.getClt_id()!=1L) {*/
						 if(true && clt2.getClt_id()!=1L) {
							 
							
					 
					 MultiValueMap<String, String> map2 = new LinkedMultiValueMap<>();
						 map2.add("Case_no",props.getCase_Number());	
						 map2.add("ListingDate", dateFormat1.format(date3));
						 map2.add("ListType",clt2.getClt_ccms_list().toString());

						 HttpEntity<MultiValueMap<String, String>> entity2 = new HttpEntity<>(map2, headers);

						 
						 HttpEntity<Object> response2 =
						     restTemplate.exchange("http://192.168.0.114/CCMSAPI/API/DisplayBoard/GetCaseDetailByConnectedCase",
						                           HttpMethod.POST,
						                           entity2,
						                           Object.class);
						 
						 List<Object> mv1=(List<Object>) response2.getBody();
						 
							
						 
						 for(Object t1 :mv1) {
							 
							 ObjectMapper m1 = new ObjectMapper();
							 CauseApi props1 = m1.convertValue(t1, CauseApi.class);
							 
							 
							 
							 CauseList dmsCauseList2 = new CauseList();
							 CauseListNew dmsCauseList21 = new CauseListNew();
							
							 CaseType ct1 = benchService.getCaseTypeFromLabel(props1.getCase_type_name());
							 dmsCauseList2.setCl_case_type_mid(ct1.getCt_id());
							 dmsCauseList2.setCl_case_no(props1.getReg_no());
							 dmsCauseList2.setCl_case_year(Integer.parseInt(props1.getReg_year()));
							 dmsCauseList2.setCl_court_no(courtmaster.getCm_id());
							// dmsCauseList2.setCisCaseNo(props1.getCase_Number());
							 dmsCauseList2.setCl_rec_status(1);
							 dmsCauseList2.setCl_list_type_mid(clt2.getClt_id());
							 dmsCauseList2.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
							 dmsCauseList2.setCl_first_petitioner(props1.getPetname() !=null ? props1.getPetname() : " .");
							 dmsCauseList2.setCl_first_respondent(props1.getResname()  !=null ? props1.getResname() : " .");
							 dmsCauseList2.setCl_petitioner_council(props1.getPet_adv());
							 dmsCauseList2.setCl_respondent_council(props1.getRes_adv());
							 dmsCauseList2.setCl_dol(date4);		
								dmsCauseList2.setCl_mod_by(user.getUm_id());
								dmsCauseList2.setCl_mod_date(new Date());
								dmsCauseList2.setCl_iscrime("N");
								dmsCauseList2.setCl_ccms_id(Long.parseLong(props1.getCase_id()));
								
								
								//causelist New
								
								
								// CaseType ct1 = benchService.getCaseTypeFromLabel(props1.getCase_type_name());
								 dmsCauseList21.setCl_case_type_mid(ct1.getCt_id());
								 dmsCauseList21.setCl_case_no(props1.getReg_no());
								 dmsCauseList21.setCl_case_year(Integer.parseInt(props1.getReg_year()));
								 dmsCauseList21.setCl_court_no(courtmaster.getCm_id());
								 dmsCauseList21.setCl_bench_number(courtmaster.getCm_bench_id());
								// dmsCauseList2.setCisCaseNo(props1.getCase_Number());
								 dmsCauseList21.setCl_rec_status(1);
								 dmsCauseList21.setCl_list_type_mid(clt2.getClt_id());
								 dmsCauseList21.setCl_serial_no(Integer.parseInt(props.getSerialNo()));
								 dmsCauseList21.setCl_first_petitioner(props1.getPetname() !=null ? props1.getPetname() : " .");
								 dmsCauseList21.setCl_first_respondent(props1.getResname()  !=null ? props1.getResname() : " .");
								 dmsCauseList21.setCl_petitioner_council(props1.getPet_adv());
								 dmsCauseList21.setCl_respondent_council(props1.getRes_adv());
								 dmsCauseList21.setCl_dol(date4);		
									dmsCauseList21.setCl_mod_by(user.getUm_id());
									dmsCauseList21.setCl_mod_date(new Date());
									dmsCauseList21.setCl_iscrime("N");
									dmsCauseList2.setCl_ccms_id(Long.parseLong(props1.getCase_id()));
							 
							 
							
							 
								
								CaseFileDetail casefile1 = new CaseFileDetail();
								casefile1.setFd_case_no(dmsCauseList2.getCl_case_no());
								casefile1.setFd_case_type(dmsCauseList2.getCl_case_type_mid());
								casefile1.setFd_case_year(dmsCauseList2.getCl_case_year());

								CaseFileDetail caseFileDetail1 = benchService.getCaseFile(casefile1);
								if (caseFileDetail1 != null) {
									dmsCauseList2.setCl_fd_mid(caseFileDetail1.getFd_id());
									dmsCauseList21.setCl_fd_mid(caseFileDetail1.getFd_id());
									
								} 
								benchService.save(dmsCauseList2);
								benchService.save(dmsCauseList21);
								
								
							
								
								System.out.println("saved");
							 
						 }
						 }
					 }
				 
				 System.out.println(t);
			}
				 }
				 map.clear();
				 
			 
			}
		
		
			if(serviceStatus==1) {
				response.setResponse("TRUE");
			}
			else {
				response.setResponse("FALSE");
			}
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		}
	
	
	@RequestMapping(value = "/updateCourt", method = RequestMethod.POST)
	public @ResponseBody String getCauseList(@RequestBody CourtMaster courtmaster, HttpSession session) {
		String jsonData = "";
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		
		if(!courtmaster.isUpdateFlag()) {
		
		if(courtmaster.getCm_bench_id() != null) {
			CourtMaster cm1	= benchService.getCourtByBenchId(courtmaster.getCm_bench_id());
			
			if(cm1 != null) {
				
				response.setResponse("FALSE");
				response.setModelData(cm1);
				jsonData = globalfunction.convert_to_json(response);
				return jsonData;
				
				
				
			}
		}
		
		
			CourtMaster mapping = benchService.updateCourt(courtmaster);
		
	
if(mapping != null) {
		
		response.setResponse("TRUE");
		response.setModelData(courtmaster);
}
else {
	response.setResponse("FALSE");
}
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
		
	}
		
		else {
			if(courtmaster.getCm_bench_id() != null) {
				CourtMaster cm2	= benchService.getCourtByBenchId(courtmaster.getCm_bench_id());
				
				if(cm2 != null) {
					
					cm2.setCm_bench_id(null);
					CourtMaster mapping = benchService.updateCourt(cm2);
					if(cm2 != null) {
						CourtMaster mapping1 = benchService.updateCourt(courtmaster);
						response.setResponse("TRUE");
						response.setModelData(mapping1);
						jsonData = globalfunction.convert_to_json(response);
						return jsonData;
					}
					
					
					
					
				}
			}
			return jsonData;
		}
	}
	
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)	
	public @ResponseBody String create(@RequestBody CourtMaster cm,HttpSession session) {	
		String jsonData="";
		ActionResponse<CourtMaster> response = new ActionResponse<>();		
		
		User user = (User) session.getAttribute("USER");
		cm.setCm_cr_by(user.getUm_id());
		cm.setCm_cr_date(new Date());
		if(cm.getCm_rec_status()==1) {
			cm.setCm_name("Court_"+cm.getCm_name());
		}
		
		if(cm.getCm_rec_status()==3) {
			cm.setCm_name("In-Chamber-"+cm.getCm_name());
		}
		
		CourtMaster cmExist=benchService.getCourtByBenchName(cm.getCm_name());
		if(cmExist!=null) {
			response.setResponse("FALSE");
			response.setData("Court Already Exist");
		}
		else {
			cm=benchService.updateCourt(cm);
			if(cm!=null) {
				response.setResponse("TRUE");
				response.setData("Court Added Successfully");
			}
		}
		
		
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	
	
	@RequestMapping(value = "/getAllCourts", method = RequestMethod.GET)
	public @ResponseBody String getAllCourts() {
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		String jsonData = "";
		List<CourtMaster> types = benchService.getAllCourts();
		if(!types.isEmpty()) {
		response.setData("TRUE");
		response.setModelList(types);
		
		}
		else {
			response.setData("FALSE");
			
		}
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	

	
	
	

}
