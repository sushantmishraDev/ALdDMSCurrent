package com.dms.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dms.model.ActionResponse;
import com.dms.model.AddCauseList;
import com.dms.model.Case;
import com.dms.model.CaseFileDetail;
import com.dms.model.CaseType;
import com.dms.model.CauseList;
import com.dms.model.CauseListType;
import com.dms.model.CourtMaster;
import com.dms.model.CourtOrder;
import com.dms.model.CourtUserMapping;
import com.dms.model.Lookup;
import com.dms.model.Petitioner;
import com.dms.model.PetitionerCounsel;
import com.dms.model.Rec;
import com.dms.model.Record;
import com.dms.model.Respondent;
import com.dms.model.RespondentCounsel;
import com.dms.model.SameCrimDetails;
import com.dms.model.SameLcrDetails;
import com.dms.model.SubApplication;
import com.dms.model.SubDocument;
import com.dms.model.User;
import com.dms.model.UserRole;
import com.dms.service.CaseFileDetailService;
import com.dms.service.CauseListService;
import com.dms.service.CourtMasterService;
import com.dms.service.LookupService;
import com.dms.service.MasterService;
import com.dms.service.SubDocumentService;
import com.dms.utility.GlobalFunction;
import com.lowagie.text.DocumentException;

@Controller
public class CauseListController {
	
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
	private SubDocumentService subDocumentService;

	private GlobalFunction globalfunction;

	public CauseListController() {
		globalfunction = new GlobalFunction();
	}

	@RequestMapping(value = "/causelist/home", method = RequestMethod.GET)
	public String adminHome() {

		return "/causelist/home";

	}
	@RequestMapping(value = "/causelist/getCauseListReportCourtWisePage", method = RequestMethod.GET)
	public String reportPage() {

		return "/causelist/causeListReport";

	}
	
	@RequestMapping(value = "/causelist/downloadCauseList", method = RequestMethod.GET)
	public String downloadCauseList() {

		return "/causelist/downloadCauseList";

	}

	@RequestMapping(value = "/causelist/manage", method = RequestMethod.GET)
	public String manage() {
		return "/causelist/manage";
	}

	@RequestMapping(value = "/causelist/search", method = RequestMethod.GET)
	public String search() {
		return "/causelist/search";
	}

	@RequestMapping(value = "/causelist/orders", method = RequestMethod.GET)
	public String courtOrders() {

		return "/causelist/orders";

	}
	
	
	@RequestMapping(value = "/causelist/addcase_tocauslist", method = RequestMethod.GET)
	public String addcase_tocauslist()
	{
		return "/causelist/addcase_tocauslist";
	}
	
	
	@RequestMapping(value = "/causelist/addManualCase", method = RequestMethod.POST)
	public @ResponseBody String addManualCase(@RequestBody CauseList causeList, HttpSession session) 
	{
		String jsonData = null;
		ActionResponse<CauseList> response=new ActionResponse<>();
		User u=(User) session.getAttribute("USER");
	
		causeList.setCaseType(causeList.getCaseType());
		causeList.setCl_case_no(causeList.getCl_case_no());
		causeList.setCl_case_year(causeList.getCl_case_year());
		causeList.setCl_ano(causeList.getCl_ano());
		causeList.setCl_ayr(causeList.getCl_ayr());
		causeList.setCl_serial_no(causeList.getCl_serial_no());
		causeList.setCl_list_type_mid(causeList.getCl_list_type_mid());
		causeList.setCl_court_no(causeList.getCl_court_no());
		causeList.setCl_dol(causeList.getCl_dol1());
		causeList.setCl_first_petitioner(causeList.getCl_first_petitioner());
		causeList.setCl_first_respondent(causeList.getCl_first_respondent());
		causeList.setCl_petitioner_council(causeList.getCl_petitioner_council());
		causeList.setCl_respondent_council(causeList.getCl_respondent_council());
		causeList.setCl_rec_status(1);
		
		
		causeListService.save(causeList);
		
		if(true) {
		response.setResponse("TRUE");
		response.setData("Record saved successfully");
		}
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
			
	}
	
	
	

	@RequestMapping(value = "/causelist/type/{typeId}", method = RequestMethod.GET)
	public String manage(Model model, @PathVariable Long typeId, HttpSession session) {
		User u = (User) session.getAttribute("USER");

		CauseListType listType = causeListService.findCauseListType(typeId);
		model.addAttribute("type_id", typeId);
		model.addAttribute("listType", listType);
		// Lookup extractPath=lookupService.getLookUpObject("CAUSELIST_PATH");
		// CourtUserMapping mapping=courtService.getCourtMappingByUserId(u.getUm_id());
		// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		// String currentDate = formatter.format(new Date());
		// String
		// listPath=extractPath.getLk_longname()+File.separator+currentDate+File.separator+mapping.getCourtMaster().getCm_name()+File.separator+listType.getClt_description()+".html";
		// model.addAttribute("listPath", listPath);

		// String uploadPath = context.getRealPath("");
		// File dest = new
		// File(uploadPath+File.separator+"uploads"+File.separator+"causelist.txt");
		//
		// try {
		// FileUtils.copyFile(source, dest);
		// }
		// catch (IOException e) {
		// e.printStackTrace();
		// }

		return "/causelist/typewise";
	}
	
	@RequestMapping(value = "/causelist/getSerials", method = RequestMethod.POST)
	public @ResponseBody String getSerials(@RequestBody CauseList causeList, HttpSession session) 
	{
		String jsonData = null;
		ActionResponse<CauseList> response=new ActionResponse<>();
		User u=(User) session.getAttribute("USER");
	
		
		List<Integer> causeLists =causeListService.getSerialAvailbaleCases(causeList);
		
		
		System.out.println("Cause List Dataaaaaaaaaaa"+causeLists.size());
		
	
		if(causeLists != null) {
			//response.setModel(causeLists);
			response.setData(causeLists);
			response.setResponse("TRUE");
		//response.setData("Record saved successfully");
		}
		else {
			
		}
			
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
			
	}
	
	
	
	@RequestMapping(value = "/causelist/getConnectedCases", method = RequestMethod.POST)
	public @ResponseBody String getConnectedCases(@RequestBody CauseList causeList, HttpSession session) 
	{
		String jsonData = null;
		ActionResponse<CauseList> response=new ActionResponse<>();
		User u=(User) session.getAttribute("USER");
	
		
		List<CauseList> causeLists =causeListService.getListConnectedForNextCase(causeList);
		
		
		System.out.println("Cause List Dataaaaaaaaaaa"+causeLists.size());
		
	
		if(causeLists != null) {
			response.setModelList(causeLists);
			response.setResponse("TRUE");
		//response.setData("Record saved successfully");
		}
		else {
			
		}
			
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
			
	}
	
	
	
	@RequestMapping(value = "/causelist/typeForTrasnfer/{typeId}/{fromCourt}", method = RequestMethod.GET)
	public String manage1(Model model, @PathVariable Long typeId,@PathVariable Integer fromCourt, HttpSession session) {
		User u = (User) session.getAttribute("USER");

		CauseListType listType = causeListService.findCauseListType(typeId);
		model.addAttribute("type_id", typeId);
		model.addAttribute("listType", listType);
		model.addAttribute("fromCourt", fromCourt);
		// Lookup extractPath=lookupService.getLookUpObject("CAUSELIST_PATH");
		// CourtUserMapping mapping=courtService.getCourtMappingByUserId(u.getUm_id());
		// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		// String currentDate = formatter.format(new Date());
		// String
		// listPath=extractPath.getLk_longname()+File.separator+currentDate+File.separator+mapping.getCourtMaster().getCm_name()+File.separator+listType.getClt_description()+".html";
		// model.addAttribute("listPath", listPath);

		// String uploadPath = context.getRealPath("");
		// File dest = new
		// File(uploadPath+File.separator+"uploads"+File.separator+"causelist.txt");
		//
		// try {
		// FileUtils.copyFile(source, dest);
		// }
		// catch (IOException e) {
		// e.printStackTrace();
		// }

		return "/causelist/typeWiseForTransfer";
	}
		
	@RequestMapping(value = "/causelist/ccmstodmsReportPage", method = RequestMethod.GET)
	public  String ccmstodmsReport() {
			
		return "/causelist/ccmstodmsReport";
	}

	
	@RequestMapping(value = "/causelist/ccmstodmsReport", method = RequestMethod.GET)
			
	public @ResponseBody String ccmstodmsReport( Model model,@RequestParam(value = "cl_dol",required=true) String cl_dol,  @RequestParam(value = "fd_file_source",required=true ) String fd_file_source) {
			
		String jsonData = null;
		ActionResponse<CauseList> response=new ActionResponse<>();
	
		List<CauseList> list = causeListService.ccmstodmsList(cl_dol,fd_file_source);
		response.setResponse("true");
		response.setModelList(list);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
		
		@RequestMapping(value = "/causelist/getCauseLists", method = RequestMethod.POST)
		public @ResponseBody String getCauseLists(@RequestBody CauseList causeList, HttpSession session) {
			String jsonData = "";
			
			
			ActionResponse<CauseList> response = new ActionResponse<CauseList>();
			User user = (User) session.getAttribute("USER");
			List<UserRole> userroles = user.getUserroles();
			String userRole = "";
			causeList.setCl_dol(new Date());
			for (UserRole userrole : userroles) {
				userRole = userrole.getLk().getLk_longname();
			}
			if (userRole.equals("ECOURT")) {
				CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());
				causeList.setCl_court_no(mapping.getCum_court_mid());
			}

			List<CauseList> list = causeListService.getList(causeList);
			
			if(causeList.getCl_list_type_mid()==null) {
				for(CauseList cl : list) {
					if(cl.getCl_fd_mid()!=null ) {
						SubDocument sd=subDocumentService.getPetitionSubDocument(cl.getCl_fd_mid(), 1);
						if(sd ==null) {
							cl.setPetAvailable(true);
						}
					}
				}
			}
			else if(causeList.getCl_list_type_mid()==1L || causeList.getCl_list_type_mid()==2L) {
				for(CauseList cl : list) {
					if(cl.getCl_fd_mid()!=null ) {
						SubDocument sd=subDocumentService.getAppSubDocument(cl);
						if(sd ==null) {
							SubApplication sb=subDocumentService.getSubAppSubDocument(cl);
							if(sb==null) {
								cl.setAppAvailable(true);
								}
							
						}
					}
				}
			}
			else {
			for(CauseList cl : list) {
				if(cl.getCl_fd_mid()!=null ) {
					SubDocument sd=subDocumentService.getPetitionSubDocument(cl.getCl_fd_mid(), 1);
					if(sd ==null) {
						cl.setPetAvailable(true);
					}
				}
				if(cl.getCl_iscrime().trim().equals("Y")) {
				List<SameCrimDetails> scd=subDocumentService.getAllSameCrimDetails(cl.getCl_id());
				
				if(scd.size()!=0) {
					cl.setSameCrimDetails(scd);
				}
				}
				if(cl.getCl_lcr_no()!=null) {
					List<SameLcrDetails> sld=subDocumentService.getAllSameLcrDetails(cl.getCl_id());
					
					if(sld.size()!=0) {
						cl.setSameLcrDetails(sld);
					}
					}
			}
			}
			response.setResponse("true");
			response.setModelList(list);
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		}
		
		@RequestMapping(value = "/causelist/getCauseListNew", method = RequestMethod.POST)
		public @ResponseBody String getCauseListNew(@RequestBody CauseList causeList, HttpSession session) {

			String jsonData = "";
			
			
			ActionResponse<CauseList> response = new ActionResponse<CauseList>();
			User user = (User) session.getAttribute("USER");
			List<UserRole> userroles = user.getUserroles();
			String userRole = "";
			for (UserRole userrole : userroles) {
				userRole = userrole.getLk().getLk_longname();
			}
			if (userRole.equals("ECOURT")) {
				CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());
				causeList.setCl_court_no(mapping.getCum_court_mid());
			}
			//causeList.setCl_dol(new Date());

			List<CauseList> list = causeListService.getList(causeList);
			
			for(CauseList cl : list) {
				if(cl.getCl_fd_mid()!=null) {
					Object[] cfd=caseFileDetailService.getCaseFileDetail1(cl.getCl_fd_mid());
				SubDocument sd=subDocumentService.getPetitionSubDocument(cl.getCl_fd_mid(), 1);
				
				Petitioner pt=caseFileDetailService.getFirstPetitioner(cl.getCl_fd_mid());
				
				
				if(pt!=null && !pt.getPt_name().trim().equals(cl.getCl_first_petitioner().trim())) {
					cl.setCl_ecourt_status(false);
				}
				
				if(sd ==null) {
					cl.setPetAvailable(true);
				}
				
			/*	cl.setCaseChecked(cfd.getFd_cl_flag());
				cl.setFileSource(cfd.getFd_file_source().trim());*/
				
				cl.setCaseChecked(Boolean.parseBoolean(cfd[0].toString()));
				cl.setFileSource(cfd[1].toString().trim());
				}
				
				if(cl.getCl_list_type_mid()==1L || cl.getCl_list_type_mid()==2L) {
					SubDocument sd1=subDocumentService.getAppSubDocument(cl);
					if(sd1 ==null) {
						SubApplication sb=subDocumentService.getSubAppSubDocument(cl);
						if(sb==null) {
							cl.setAppAvailable(true);
							}						
					}
				}
				else {
					/*if(cl.getCl_serial_no()==163) {*/
					if(cl.getCl_iscrime().trim().equals("Y")) {
						List<SameCrimDetails> scd=subDocumentService.getAllSameCrimDetails(cl.getCl_id());
						
						if(scd.size()!=0) {
							cl.setSameCrimDetails(scd);
						}
						}
					if(cl.getCl_lcr_no()!=null) {
						List<SameLcrDetails> sld=subDocumentService.getAllSameLcrDetails(cl.getCl_id());
						
						if(sld.size()!=0) {
							cl.setSameLcrDetails(sld);
						}
						}
					/*SubDocument sd1=subDocumentService.getNewAppSubDocument(cl.getCl_fd_mid(),1, cfd.getFd_cl_date());
					if(sd1!=null)
					{
						
						cl.setAppNew(true);
					}*/
					
				}
			
			}
			
			/*if(causeList.getCl_list_type_mid()==null) {}
			else if(causeList.getCl_list_type_mid()==1L || causeList.getCl_list_type_mid()==2L) {}
			else {}*/
			response.setResponse("true");
			response.setModelList(list);
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		
		}
	

	@RequestMapping(value = "/causelist/getCauseList", method = RequestMethod.POST)
	public @ResponseBody String getCauseList(@RequestBody CauseList causeList, HttpSession session) {
		String jsonData = "";
		
		
		ActionResponse<CauseList> response = new ActionResponse<CauseList>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		if (userRole.equals("ECOURT")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());
			causeList.setCl_court_no(mapping.getCum_court_mid());
		}
		//causeList.setCl_dol(new Date());

		List<CauseList> list = causeListService.getList(causeList);
		
		for(CauseList cl : list) {
			

			if(cl.getCl_fd_mid()!=null) {
				Object[] cfd=caseFileDetailService.getCaseFileDetail1(cl.getCl_fd_mid());
			SubDocument sd=subDocumentService.getPetitionSubDocument(cl.getCl_fd_mid(), 1);
			
			Petitioner pt=caseFileDetailService.getFirstPetitioner(cl.getCl_fd_mid());
			
			
			if(pt!=null && !pt.getPt_name().trim().equals(cl.getCl_first_petitioner().trim())) {
				cl.setCl_ecourt_status(false);
			}
			
			if(sd ==null) {
				cl.setPetAvailable(true);
			}
			
		/*	cl.setCaseChecked(cfd.getFd_cl_flag());
			cl.setFileSource(cfd.getFd_file_source().trim());*/
			
			cl.setCaseChecked(Boolean.parseBoolean(cfd[0].toString()));
			cl.setFileSource(cfd[1].toString().trim());
			}
			
			if(cl.getCl_list_type_mid()==1L || cl.getCl_list_type_mid()==2L) {
				SubDocument sd1=subDocumentService.getAppSubDocument(cl);
				if(sd1 ==null) {
					SubApplication sb=subDocumentService.getSubAppSubDocument(cl);
					if(sb==null) {
						cl.setAppAvailable(true);
						}						
				}
			}
			else {
				/*if(cl.getCl_serial_no()==163) {*/
				if(cl.getCl_iscrime().trim().equals("Y")) {
					List<SameCrimDetails> scd=subDocumentService.getAllSameCrimDetails(cl.getCl_id());
					
					if(scd.size()!=0) {
						cl.setSameCrimDetails(scd);
					}
					}
				if(cl.getCl_lcr_no()!=null) {
					List<SameLcrDetails> sld=subDocumentService.getAllSameLcrDetails(cl.getCl_id());
					
					if(sld.size()!=0) {
						cl.setSameLcrDetails(sld);
					}
					}
				/*SubDocument sd1=subDocumentService.getNewAppSubDocument(cl.getCl_fd_mid(),1, cfd.getFd_cl_date());
				if(sd1!=null)
				{
					
					cl.setAppNew(true);
				}*/
				
			}
		
		
		}
		
/*		if(causeList.getCl_list_type_mid()==null) {
			for(CauseList cl : list) {
				if(cl.getCl_fd_mid()!=null ) {
					SubDocument sd=subDocumentService.getPetitionSubDocument(cl.getCl_fd_mid(), 1);
					CaseFileDetail cfd=caseFileDetailService.getCaseFileDetail(cl.getCl_fd_mid());
					
					if(cfd.getPetitioners().size()!=0 && !cfd.getPetitioners().get(0).getPt_name().equals(cl.getCl_first_petitioner())) {
						cl.setCl_ecourt_status(false);
					}
					
					if(sd ==null) {
						cl.setPetAvailable(true);
					}
					
					cl.setCaseChecked(cfd.getFd_cl_flag());
					cl.setFileSource(cfd.getFd_file_source().trim());
				}
			}
		}
		else if(causeList.getCl_list_type_mid()==1L || causeList.getCl_list_type_mid()==2L) {
			for(CauseList cl : list) {
				if(cl.getCl_fd_mid()!=null ) {
					SubDocument sd=subDocumentService.getAppSubDocument(cl);
					CaseFileDetail cfd=caseFileDetailService.getCaseFileDetail(cl.getCl_fd_mid());
					if(cfd.getPetitioners().size()!=0 && !cfd.getPetitioners().get(0).getPt_name().equals(cl.getCl_first_petitioner())) {
						cl.setCl_ecourt_status(false);
					}
					if(sd ==null) {
						SubApplication sb=subDocumentService.getSubAppSubDocument(cl);
						if(sb==null) {
							cl.setAppAvailable(true);
							}
						
					}
					cl.setCaseChecked(cfd.getFd_cl_flag());
					cl.setFileSource(cfd.getFd_file_source().trim());
				}
			}
		}
		else {
		for(CauseList cl : list) {
			if(cl.getCl_iscrime().equals("Y")) {
				List<SameCrimDetails> scd=subDocumentService.getAllSameCrimDetails(cl.getCl_id());
				
				if(scd.size()!=0) {
					cl.setSameCrimDetails(scd);
				}
				}
			if(cl.getCl_lcr_no()!=null && !cl.getCl_iscrime().equals("Y")) {
				List<SameLcrDetails> sld=subDocumentService.getAllSameLcrDetails(cl.getCl_id());
				
				if(sld.size()!=0) {
					cl.setSameLcrDetails(sld);
				}
				}
			if(cl.getCl_fd_mid()!=null ) {
				SubDocument sd=subDocumentService.getPetitionSubDocument(cl.getCl_fd_mid(), 1);
				if(sd ==null) {
					cl.setPetAvailable(true);
				}
				CaseFileDetail cfd=caseFileDetailService.getCaseFileDetail(cl.getCl_fd_mid());
				
				System.out.println("petition size :"+cfd.getPetitioners());
				
				if(cfd.getPetitioners().size()!=0 && !cfd.getPetitioners().get(0).getPt_name().equals(cl.getCl_first_petitioner())) {
					cl.setCl_ecourt_status(false);
				}
				
				SubDocument sd1=subDocumentService.getNewAppSubDocument(cl.getCl_fd_mid(),1, cfd.getFd_cl_date());
				if(sd1!=null)
				{
					
					cl.setAppNew(true);
				}
				cl.setCaseChecked(cfd.getFd_cl_flag());
				cl.setFileSource(cfd.getFd_file_source().trim());
			}
			System.out.println(cl);
		}
		}*/
		response.setResponse("true");
		response.setModelList(list);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
	@RequestMapping(value = "/causelist/getCauseListReportCourtWise", method = RequestMethod.POST)
	public @ResponseBody String getCauseListReportCourtWise(@RequestBody CauseList causeList, HttpSession session) {
		String jsonData = "";
		ActionResponse<Object> response = new ActionResponse<Object>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		

		Object clObject = causeListService.getListReportCourtWise(causeList);
		response.setResponse("true");
		response.setModelData(clObject);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
	@RequestMapping(value = "/causelist/getCauseListOnSearch", method = RequestMethod.POST)
	public @ResponseBody String getCauseListOnSearch(@RequestBody CauseList causeList, HttpSession session) {
		// set court to cl transfer to because of in ui user already select transferred cases
		String jsonData = "";
		ActionResponse<CauseList> response = new ActionResponse<CauseList>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		if (userRole.equals("ECOURT")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());
			//causeList.setCl_court_no(mapping.getCum_court_mid());
			causeList.setCl_transfer_to(mapping.getCum_court_mid());
		}

		List<CauseList> list = causeListService.getListOnSearchForTransfer(causeList);
		response.setResponse("true");
		response.setModelList(list);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
	@RequestMapping(value = "/causelist/getCauseListForTransfer", method = RequestMethod.POST)
	public @ResponseBody String getCauseListForTransFer(@RequestBody CauseList causeList, HttpSession session) {
		String jsonData = "";
		ActionResponse<CauseList> response = new ActionResponse<CauseList>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		if (userRole.equals("ECOURT")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());
			causeList.setCl_court_no(mapping.getCum_court_mid());
		}

		List<CauseList> list = causeListService.getListForTransfer(causeList);
		response.setResponse("true");
		response.setModelList(list);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
	@RequestMapping(value = "/causelist/getCauseListForTransferCases", method = RequestMethod.POST)
	public @ResponseBody String getCauseList1(@RequestBody CauseList causeList, HttpSession session) {
		String jsonData = "";
		ActionResponse<CauseList> response = new ActionResponse<CauseList>();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		if (userRole.equals("ECOURT")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());
			//causeList.setCl_court_no(mapping.getCum_court_mid());
			causeList.setCl_transfer_to(mapping.getCum_court_mid());
		}

		List<CauseList> list = causeListService.getList1(causeList);
		response.setResponse("true");
		response.setModelList(list);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	@RequestMapping(value = "/causelist/create", method = RequestMethod.POST)
	public @ResponseBody String create(MultipartHttpServletRequest request, HttpSession session)
			throws DocumentException {
		ActionResponse<CauseList> response = new ActionResponse();
		response.setResponse("TRUE");
		String jsonData = "";
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String currentDate = formatter.format(date);
		Lookup causelistPath = lookupService.getLookUpObject("CAUSELIST_PATH");
		MultipartFile mpf = null;
		Iterator<String> itr = request.getFileNames();
		String xmlPath = "";
		List<CourtMaster> cList = courtService.getCourtLists();
		// Truncate Causelist data, petitioner advocates and respondent advocates data
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		Date dateBefore7Days = cal.getTime();
		causeListService.deleteCauseList(dateBefore7Days);
		// causeListService.deletePetitionerAdvocates();
		// causeListService.deleteRespondentAdvocates();

		Map<Integer, Integer> courtsList = new HashMap<Integer, Integer>();

		for (CourtMaster cm : cList) {
			courtsList.put(cm.getCm_value(), cm.getCm_id());
		}

		List<CaseType> caseTypes = masterService.getCaseTypes();
		Map<String, Long> caseTypeList = new HashMap<String, Long>();

		for (CaseType ct : caseTypes) {
			caseTypeList.put(ct.getCt_label(), ct.getCt_id());
		}
		List<CauseListType> causeListType = causeListService.getCauseListTypes();
		Map<String, Long> causeListMap = new HashMap<String, Long>();

		for (CauseListType clt : causeListType) {
			causeListMap.put(clt.getClt_name(), clt.getClt_id());
		}
		boolean result = false;
		while (itr.hasNext()) {
			mpf = request.getFile(itr.next());

			xmlPath = causelistPath.getLk_longname() + File.separator + "CauseList_" + currentDate + ".xml";

			try {
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(xmlPath));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		File file = new File(xmlPath);

		int count = 0;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Record.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Record record = (Record) jaxbUnmarshaller.unmarshal(file);

			List<Rec> list = record.getRec();
			Rec rc = list.get(0);
			String Date = rc.getDol();
			date = new SimpleDateFormat("dd/MM/yyyy").parse(Date);

			result = causeListService.delete(date);

			for (Rec rec : list) {
				Case cases = rec.getCases();

				try {

					// case_type=causeListService.findCaseType(cases.getType(), 8L);

					CauseList cl = new CauseList();

					String sDate = rec.getDol();
					date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
					cl.setCl_case_type_mid(caseTypeList.get(cases.getType()));
					cl.setCl_case_no(cases.getNo());
					cl.setCl_case_year(cases.getYear());
					cl.setCl_court_no(courtsList.get(rec.getCourtno()));
					cl.setCl_serial_no(rec.getSlno());
					cl.setCl_list_type_mid(causeListMap.get(rec.getLtype()));
					cl.setCl_first_petitioner(rec.getPet());
					cl.setCl_first_respondent(rec.getRes());
					cl.setCl_petitioner_council(rec.getPetadv());
					cl.setCl_respondent_council(rec.getResadv());
					cl.setCl_ano(rec.getAno());
					cl.setCl_ayr(rec.getAyr());

					cl.setCl_applawp(rec.getApplawp());
					cl.setCl_applawr(rec.getApplawr());
					cl.setCl_stage(rec.getStage());
					cl.setCl_dol(date);
					cl.setCl_rec_status(1);
					System.out.println("Caseno=" + cl.getCl_case_no() + " & case year=" + cl.getCl_case_year()
							+ " &count=" + count);

					System.out.println(result);
					cl = causeListService.save(cl);
					// PetitionerAdvocate pa=new PetitionerAdvocate();
					// pa.setPa_name(rec.getPetadv());
					// pa.setPa_cl_mid(cl.getCl_id());
					// causeListService.savePetAdvocate(pa);
					//
					// RespondentAdvocate ra=new RespondentAdvocate();
					// ra.setRa_name(rec.getResadv());
					// ra.setRa_cl_mid(cl.getCl_id());
					// causeListService.saveResAdvocate(ra);
					//
					// Map<String, String> extpadv=(Map<String, String>) rec.getExtpadv();
					// try {
					// if(!extpadv.isEmpty())
					// {
					//
					// for(Entry<String, String> extp:extpadv.entrySet()){
					// PetitionerAdvocate padvocate=new PetitionerAdvocate();
					// padvocate.setPa_name(extp.getValue());
					// padvocate.setPa_cl_mid(cl.getCl_id());
					// causeListService.savePetAdvocate(padvocate);
					// }
					// }
					// } catch (Exception e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					// Map<String, String> extradv=(Map<String, String>) rec.getExtradv();
					// try {
					// if(!extradv.isEmpty())
					// {
					// for(Entry<String, String> extr:extradv.entrySet()){
					// RespondentAdvocate radvocate=new RespondentAdvocate();
					// radvocate.setRa_name(extr.getValue());
					// radvocate.setRa_cl_mid(cl.getCl_id());
					// causeListService.saveResAdvocate(radvocate);
					// }
					// }
					// } catch (Exception e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					CaseFileDetail casefile = new CaseFileDetail();
					casefile.setFd_case_no(cl.getCl_case_no());
					casefile.setFd_case_type(cl.getCl_case_type_mid());
					casefile.setFd_case_year(cl.getCl_case_year());

					CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFile(casefile);
					if (caseFileDetail == null) {
						count++;
					} else {
						cl.setCl_fd_mid(caseFileDetail.getFd_id());

						causeListService.save(cl);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse("FALSE");
			response.setData("Error reading causelist");
		}
		if (count > 0) {
			response.setData(count + " no. of case files not found");
			response.setResponse("FALSE");
		}

		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	@RequestMapping(value = "/causelist/getCauseListTypes", method = RequestMethod.GET)
	public @ResponseBody String getCauseListTypes() {
		ActionResponse<CauseListType> response = new ActionResponse<CauseListType>();
		String jsonData = "";
		List<CauseListType> types = causeListService.getCauseListTypes();
		response.setData("TRUE");
		response.setModelList(types);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	@RequestMapping(value = "/causelist/getCourtList", method = RequestMethod.GET)
	public @ResponseBody String getCourtList() {
		ActionResponse<CourtMaster> response = new ActionResponse<CourtMaster>();
		String jsonData = "";
		List<CourtMaster> courtList = courtService.getCourtLists();
		response.setData("TRUE");
		response.setModelList(courtList);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	/*
	 * @RequestMapping(value = "/causelist/upload",method = RequestMethod.POST)
	 * public @ResponseBody String upload(MultipartHttpServletRequest
	 * request,HttpSession session){ String jsonData=""; ActionResponse<CauseList>
	 * response=new ActionResponse<CauseList>();
	 * 
	 * CauseList causeList=new CauseList(); Lookup
	 * lkZipPath=lookupService.getLookUpObject("CAUSELIST_ZIP"); Lookup
	 * extractPath=lookupService.getLookUpObject("CAUSELIST_PATH"); String
	 * zipFilePath = lkZipPath.getLk_longname(); String destDirectory =
	 * extractPath.getLk_longname(); UnzipUtility unzipper = new UnzipUtility();
	 * MultipartFile mpf = null;
	 * 
	 * 
	 * Iterator<String> itr = request.getFileNames(); String zipFullPath="";
	 * SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); String
	 * cl_dol =request.getParameter("cl_date");
	 * destDirectory=destDirectory+File.separator+cl_dol; File destDir = new
	 * File(destDirectory); if (!destDir.exists()) { destDir.mkdir(); } if
	 * (!destDir.exists()) { destDir.mkdir(); }
	 * 
	 * System.out.println("date="+cl_dol); while (itr.hasNext()) { mpf =
	 * request.getFile(itr.next());
	 * 
	 * zipFullPath=zipFilePath + File.separator +cl_dol+".zip";
	 * 
	 * try { FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(zipFullPath));
	 * }catch (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * try { unzipper.unzip(zipFullPath, destDirectory);
	 * 
	 * } catch (Exception ex) { // some errors occurred ex.printStackTrace(); }
	 * SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); Date
	 * judgementDate = new Date(); try { judgementDate =
	 * formatter.parse(request.getParameter("cl_date")); } catch (ParseException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); }
	 * causeList.setCl_date(judgementDate);
	 * 
	 * causeList.setCl_created(new Date());
	 * 
	 * causeList=causeListService.save(causeList);
	 * 
	 * response.setData("TRUE"); response.setModelData(causeList);
	 * jsonData=globalfunction.convert_to_json(response); return jsonData; }
	 */
	@RequestMapping(value = "causelist/updatepriority/{id}", method = RequestMethod.GET)
	public @ResponseBody String updatePriority(@PathVariable("id") Long cl_id, HttpSession session) {
		String jsonData = null;
		ActionResponse<CauseList> response = new ActionResponse<>();
		CauseList causeList = causeListService.getByPk(cl_id);
		Integer count = causeListService.getPrioritywise(causeList.getCl_fd_mid());
		if (count == null) {
			count = 0;
		}
		count = count + 1;
		causeList.setCl_sequence(count);
		causeListService.save(causeList);

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "causelist/downloadfiles", method = RequestMethod.GET)
	public void downloadCaseFile(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		CauseList causeList = new CauseList();
		Integer courtno = Integer.parseInt(request.getParameter("courtno"));
		String date = request.getParameter("date");
		String expectedPattern = "dd-MM-yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		Date cl_dol = new Date();
		try {
			cl_dol = formatter.parse(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		causeList.setCl_court_no(courtno);
		causeList.setCl_dol(cl_dol);
		CourtMaster courtMaster = courtMasterService.getCourt(causeList.getCl_court_no());

		List<CauseList> list = causeListService.getList(causeList);
		Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
		// Lookup lookUpZip=lookupService.getLookUpObject("ZIP_DOWNLOAD_PATH");
		Lookup lookUpZip = lookupService.getLookUpObject("CAUSELIST_DOWNLOAD_PATH");

		String dest_folder = lookUpZip.getLk_longname() + File.separator + courtMaster.getCm_name() + "_" + date;
		File destFolder = new File(dest_folder);

		if (!destFolder.exists()) {
			destFolder.mkdir();
		}

		for (CauseList cause : list) {
			List<SubDocument> subDocuments = subDocumentService.getSubDocuments(cause.getCl_fd_mid());
			CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(cause.getCl_fd_mid());
			String basePath = lookupRepo.getLk_longname() + caseFileDetail.getCaseType().getCt_label();
			String casefile = destFolder + File.separator + caseFileDetail.getCaseType().getCt_label()
					+ caseFileDetail.getFd_case_no() + caseFileDetail.getFd_case_year() + ".pdf";
			String casefilebk = destFolder + File.separator + caseFileDetail.getCaseType().getCt_label() + "_"
					+ caseFileDetail.getFd_case_no() + "_" + caseFileDetail.getFd_case_year() + ".pdf";
			File f = new File(casefile);
			globalfunction.merge(casefile, casefilebk, subDocuments, basePath);
		}

		/*
		 * List<SubDocument>
		 * subDocuments=subDocumentService.getSubDocuments(caseFileId);
		 * 
		 * 
		 * 
		 * CaseFileDetail
		 * caseFileDetail=caseFileDetailService.getCaseFileDetail(caseFileId); String
		 * dest_folder=lookUpZip.getLk_longname()+File.separator+caseFileDetail.
		 * getCaseType().getCt_label()+caseFileDetail.getFd_case_no()+caseFileDetail.
		 * getFd_case_year(); File destFolder = new File(dest_folder);
		 * 
		 * if(!destFolder.exists()) { destFolder.mkdir(); } for(SubDocument
		 * subDocument:subDocuments){ String
		 * srcPath=lookupRepo.getLk_longname()+File.separator+caseFileDetail.getCaseType
		 * ().getCt_label()+File.separator+subDocument.getIndexField().getIf_name()+File
		 * .separator+subDocument.getSd_document_name()+".pdf"; String
		 * destPath=dest_folder+File.separator+subDocument.getSd_document_name()+".pdf";
		 * File source=new File(srcPath); File dest=new File(destPath); try {
		 * FileUtils.copyFile(source.getAbsoluteFile(), dest.getAbsoluteFile()); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 */
		try {
			globalfunction.zipFolder(destFolder.getAbsolutePath(), destFolder.getAbsolutePath() + ".zip");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// to download file
		try {
			response.setContentType("application/zip");
			PrintWriter out = response.getWriter();
			String filename = destFolder.getName() + ".zip";
			String filepath = destFolder.getAbsolutePath() + ".zip";
			File zipFile = new File(dest_folder + ".zip");

			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

			FileInputStream fileInputStream = new FileInputStream(filepath);

			int i;
			while ((i = fileInputStream.read()) != -1) {
				out.write(i);
			}
			fileInputStream.close();
			out.close();
			FileUtils.deleteDirectory(destFolder);
			zipFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/causelist/updateapplicationstatus", method = RequestMethod.POST)
	public @ResponseBody String updateapplicationstatus(@RequestBody List<CauseList> applications,
			HttpSession session) {
		String jsonData = null;
		ActionResponse<CauseList> response = new ActionResponse<>();
		int i = 0;
		for (CauseList causelist : applications) {
			SubDocument subDocument = subDocumentService.getApplication(causelist.getCl_ano(), causelist.getCl_ayr());
			if (subDocument != null) {
				subDocument.setSd_status(causelist.getCl_stage_id());
				subDocument.setSd_date(causelist.getCl_date());
				subDocumentService.save(subDocument);
				causeListService.save(causelist);
				i++;
			}
		}
		if (i > 0) {
			response.setResponse("TRUE");
		} else {
			response.setResponse("False");
		}
		response.setData(i + " No.of records updated");

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "/causelist/uploadorders", method = RequestMethod.POST)
	public @ResponseBody String uploadOrders(MultipartHttpServletRequest request, HttpSession session)
			throws DocumentException {
		ActionResponse<CourtOrder> response = new ActionResponse();
		User user = (User) session.getAttribute("USER");
		CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

		String jsonData = "";
		Lookup ordersPath = lookupService.getLookUpObject("ORDERS_PATH");
		MultipartFile mpf = null;
		Iterator<String> itr = request.getFileNames();
		while (itr.hasNext()) {
			mpf = request.getFile(itr.next());
			String filename = UUID.randomUUID() + ".pdf";
			String filePath = ordersPath.getLk_longname() + File.separator + filename;

			try {
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(filePath));
				CourtOrder co = new CourtOrder();
				co.setCo_document_name(filename);
				co.setCo_cr_by(user.getUm_id());
				co.setCo_cr_date(new Date());
				co.setCo_court_no(mapping.getCum_court_mid());
				causeListService.saveCourtOrder(co);
				response.setResponse("TRUE");
				response.setData("Successfully uploaded file");
			} catch (IOException e) {
				response.setResponse("FALSE");
				response.setData("Error occurred while uploaded file");
			}

		}
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	@RequestMapping(value = "/causelist/getallorders", method = RequestMethod.GET)
	public @ResponseBody String getAllOrders(HttpSession session) {
		ActionResponse<CourtOrder> response = new ActionResponse<CourtOrder>();
		String jsonData = "";
		User user = (User) session.getAttribute("USER");
		CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());
		List<CourtOrder> orderList = causeListService.getOrdersList(mapping.getCum_court_mid());
		response.setData("TRUE");
		response.setModelList(orderList);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	@RequestMapping(value = "causelist/vieworder/{id}", method = RequestMethod.GET)
	public void viewOrder(@PathVariable("id") Long co_id, HttpSession session, HttpServletResponse response) {
		CourtOrder courtorder = causeListService.getCourtOrder(co_id);
		Lookup lookupRepo = lookupService.getLookUpObject("ORDERS_PATH");
		String srcPath = lookupRepo.getLk_longname() + File.separator + courtorder.getCo_document_name();
		File file = new File(srcPath);
		response.setHeader("content-disposition", "inline");
		response.setContentType("application/pdf");
		response.setContentLength((int) file.length());

		try {
			ServletOutputStream out = response.getOutputStream();
			FileInputStream stream = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(stream);
			BufferedOutputStream bos = new BufferedOutputStream(out);

			byte[] buff = new byte[2048];
			int bytesRead;

			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bis.close();
			stream.close();
			bos.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "causelist/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable("id") Long id, HttpSession session) {
		ActionResponse<CauseList> response = new ActionResponse<CauseList>();
		String jsonData = null;
		User u = (User) session.getAttribute("USER");
		CauseList cl = causeListService.getByPk(id);
		cl.setCl_rec_status(0);
		cl.setCl_mod_by(u.getUm_id());
		cl.setCl_mod_date(new Date());
		causeListService.save(cl);

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;

	}

	@RequestMapping(value = "/causelist/updatecauselist", method = RequestMethod.POST)
	public @ResponseBody String updatecauselist(@RequestBody List<CauseList> causelist, HttpSession session) {
		String jsonData = null;
		ActionResponse<CauseList> response = new ActionResponse<>();
		int i = 0;
		for (CauseList cl : causelist) {
			cl.setCl_court_no(cl.getCl_new_court_no());
			causeListService.save(cl);
			i++;
		}
		if (i > 0) {
			response.setResponse("TRUE");
		} else {
			response.setResponse("False");
		}
		response.setData(i + " No.of records updated");

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "/causelist/deleteall", method = RequestMethod.POST)
	public @ResponseBody String deleteall(@RequestBody List<CauseList> causelist, HttpSession session) {
		String jsonData = null;
		ActionResponse<CauseList> response = new ActionResponse<>();
		int i = 0;
		for (CauseList cl : causelist) {
			cl.setCl_rec_status(0);
			causeListService.save(cl);
			i++;
		}
		if (i > 0) {
			response.setResponse("TRUE");
		} else {
			response.setResponse("False");
		}
		response.setData(i + " No.of records deleted");

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	
	@RequestMapping(value = "/causelist/vieapplication", method = RequestMethod.POST)
	public @ResponseBody String vieapplication(@RequestBody CaseFileDetail casefiledetails, HttpSession session) 
	{
		
		System.out.println("---------"+casefiledetails.getCl_court_no()+",jjj"+casefiledetails.getCl_dol());
		User u=(User) session.getAttribute("USER");
		String jsonData = null;
		ActionResponse<SubDocument> response=new ActionResponse<>();
		CauseList causelist = new CauseList();
		CauseList cl=null;
		
		 List<SubDocument> subdocumentlist= subDocumentService.getApplicationByFDdmid(casefiledetails.getFd_id());
		 for (SubDocument subDocument : subdocumentlist) 
		 {
			 System.out.println("----app no ----"+subDocument.getSd_document_no()+"------app year-"+subDocument.getSd_document_year());
		 }
		 response.setResponse("TRUE");
		 response.setModelList(subdocumentlist);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
		
	}
	
	public Integer getMaxSerialNo(CauseList causelist) 
	{
		Integer clserialno = causeListService.getSerialNoByListType(causelist);
		int clserial_Count;
		if(clserialno!=null)
		{
			clserial_Count = clserialno.intValue();
			clserial_Count++;
		}
		else
		{
			clserial_Count=1;
		}
		System.out.println("-----clserial_Count-------------"+clserial_Count);
		return clserial_Count;
	}	

	
	@RequestMapping(value = "/causelist/addCaseToCouseList", method = RequestMethod.POST)
	public @ResponseBody String addCaseToCouseList(@RequestBody List<CaseFileDetail> casefiledetail, HttpSession session) 
	{
		String jsonData = null;
		ActionResponse<CauseList> response=new ActionResponse<>();
		CaseFileDetail casefiledetails=null;
		int clserial_Count=0;
		for(int i=0;i<casefiledetail.size();i++) {
			casefiledetails=casefiledetail.get(i);
		System.out.println("---------"+casefiledetails.getCl_court_no()+",jjj"+casefiledetails.getCl_dol());
		
		User u=(User) session.getAttribute("USER");
	
		CauseList causelist = new CauseList();
		CauseList cl=null;
			CauseList clist= causeListService.getByFDmid(casefiledetails.getFd_id());
			if(clist!=null)
			{
				clist.setCl_court_no((casefiledetails.getCl_court_no().intValue()));
				clist.setCl_list_type_mid(casefiledetails.getCl_list_type_mid());
				clist.setCl_dol(casefiledetails.getCl_dol());
				clist.setCl_mod_by(u.getUm_id());
				clist.setCl_mod_date(new Date());
				clist.setCl_rec_status(1);
				List<SubDocument> sd=subDocumentService.getAllSubDocuments(casefiledetails.getFd_id());
				if(sd!=null)
				{
					for (SubDocument sb : sd) 
					{
						System.out.println("sb.getSd_document_no()---"+sb.getSd_document_no()+ ", sb.getSd_document_year()--"+sb.getSd_document_year());
						clist.setCl_ano(sb.getSd_document_no());
						clist.setCl_ayr(sb.getSd_document_year());
						
						CauseList clobj =causeListService.getListByAppYear(clist);
						System.out.println("--applno "+clobj.getCl_ano()+"+--aap year--+"+clobj.getCl_ayr()+  "+-causelist fd_mid ===+"+clobj.getCl_fd_mid());
						
						if(clobj.getCl_ano()!=null &&clobj.getCl_ayr()!=null && clobj.getCl_fd_mid()!=null)
						{
						/*	if(clserial_Count==0) {
							 clserial_Count=getMaxSerialNo(clist);
							}
							clobj.setCl_serial_no(clserial_Count);*/
							
							clobj.setCl_court_no((casefiledetails.getCl_court_no().intValue()));
							clobj.setCl_list_type_mid(casefiledetails.getCl_list_type_mid());
							clobj.setCl_dol(casefiledetails.getCl_dol());
							clobj.setCl_mod_by(u.getUm_id());
							clobj.setCl_mod_date(new Date());
							clobj.setCl_serial_no(casefiledetails.getCl_serial_no());
							clobj.setCl_rec_status(1);
							
							cl=causeListService.save(clobj);
						}
						if(clobj.getCl_ano()==null &&clobj.getCl_ayr()==null && clobj.getCl_fd_mid()==null)
						{
							newAddToCL(casefiledetails, u);
						}
					}
				}
				else
				{
					/*if(clserial_Count==0) {
						 clserial_Count=getMaxSerialNo(clist);
						}
					clist.setCl_serial_no(clserial_Count);*/
					
					clist.setCl_serial_no(casefiledetails.getCl_serial_no());
					
					cl=causeListService.save(clist);
				}
				if(cl.getCl_id()!=null)
				{
					response.setResponse("TRUE");
					response.setData("Record updated successfully");
				}
				else
				{
					response.setResponse("False");
					response.setData("Error occurred while updating record");
				}
			}
			else
			{
				if(casefiledetails.getFd_id()==null)
				{
					causelist.setCl_court_no((casefiledetails.getCl_court_no().intValue()));
					causelist.setCl_list_type_mid(casefiledetails.getCl_list_type_mid());
					causelist.setCl_dol(casefiledetails.getCl_dol());
					causelist.setCl_serial_no(casefiledetails.getCl_serial_no());
					causelist.setCl_mod_by(u.getUm_id());
					causelist.setCl_mod_date(new Date());
					causelist.setCl_rec_status(1);
					Integer cl2=0;
					cl2=causeListService.updateCauseList(causelist);
					 if(cl2!=0)
						{
							response.setResponse("TRUE");
							response.setData(""+cl2 +" Record update successfully");
						}
						else
						{
							response.setResponse("False");
							response.setData("Record not match");
						}
				}
				else
				{
					String newaddretun = newAddToCL(casefiledetails, u);
					if(newaddretun.equals("TRUE"))
					{
						response.setResponse("TRUE");
						response.setData("Record added successfully");
					}
					else
					{
						response.setResponse("False");
						response.setData("Error occurred while adding record");
					}
				}
			}
		}
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
			
	}
	
	
	
	public  String newAddToCL(CaseFileDetail casefiledetails, User u)
	{
		String addretun="False";
		System.out.println("--------------newaddtoCL method call-----------------");
		String jsonData = null;
		ActionResponse<CauseList> response=new ActionResponse<>();
		CauseList causelist = new CauseList();
		CauseList cl=null;
		
		causelist.setCl_fd_mid(casefiledetails.getFd_id());
		causelist.setCl_case_type_mid(casefiledetails.getFd_case_type());
		causelist.setCl_case_no(casefiledetails.getFd_case_no());
		causelist.setCl_case_year(casefiledetails.getFd_case_year());
		causelist.setCl_court_no((casefiledetails.getCl_court_no().intValue()));
		causelist.setCl_list_type_mid(casefiledetails.getCl_list_type_mid());
		causelist.setCl_dol(casefiledetails.getCl_dol());
		causelist.setCl_mod_by(u.getUm_id());
		causelist.setCl_mod_date(new Date());
		causelist.setCl_rec_status(1);
		
			for(Petitioner pt:casefiledetails.getPetitioners())
				{
					if(pt.getPt_sequence()==1)
					  {
						  causelist.setCl_first_petitioner(pt.getPt_name());
						  break;
					  }
				}
           for(Respondent rt:casefiledetails.getRespondents())
           	{
        	   if(rt.getRt_sequence()==1)
        	   {
        		   causelist.setCl_first_respondent(rt.getRt_name());
        		   break;
        	   }
           }
           PetitionerCounsel pc=null;
           RespondentCounsel rc= null;
		   pc=causeListService.getPetionerCounsel(casefiledetails.getFd_id());
		   rc=causeListService.getRespondentCounsel(casefiledetails.getFd_id());
		  
		  if(pc!=null)
		  	{
			  causelist.setCl_petitioner_council(pc.getPc_name());
		  	}
		  else
		  	{
			  causelist.setCl_petitioner_council("");
		  	}
		  if(rc!=null)
		  	{
			    causelist.setCl_respondent_council(rc.getRc_name());
		  	}
		  else
		  	{
			  causelist.setCl_respondent_council("");
		  	}
		  causelist.setCl_rec_status(1);
		  causelist.setCl_stage(10);
		  List<SubDocument> sd =subDocumentService.getAllSubDocuments(casefiledetails.getFd_id());
		  if(sd !=null)
			{
				for (SubDocument sb : sd) 
				{
					System.out.println("sb.getSd_document_no()---"+sb.getSd_document_no()+ ", sb.getSd_document_year()--"+sb.getSd_document_year());
					
					causelist.setCl_ano(sb.getSd_document_no());
					causelist.setCl_ayr(sb.getSd_document_year());
					causelist.setCl_rec_status(1);
					
						CauseList clobj =causeListService.getListByAppYear(causelist);
						
						if(clobj.getCl_ano()==null &&clobj.getCl_ayr()==null && clobj.getCl_fd_mid()==null)
						{
							causelist.setCl_serial_no(casefiledetails.getCl_serial_no());
							cl=causeListService.save(causelist);	
						}
				}
			}
		  else
		  {
			  causelist.setCl_serial_no(casefiledetails.getCl_serial_no());
				
			  cl=causeListService.save(causelist);
		  }
		  if(cl.getCl_id()!=null)
			{
				addretun ="TRUE";
			}
			else
			{
				addretun ="False";
			}
		  
		  return addretun;
	}
	
	

	
	@RequestMapping(value = "/causelist/addcase", method = RequestMethod.POST)
	public @ResponseBody String addcase(@RequestBody CauseList causelist, HttpSession session) {
		String jsonData = null;
		ActionResponse<CauseList> response = new ActionResponse<>();
		if (causelist.getCl_list_type_mid() != 1) {
			// if type is not application
			CaseFileDetail casefile = new CaseFileDetail();
			casefile.setFd_case_no(causelist.getCl_case_no());
			casefile.setFd_case_type(causelist.getCl_case_type_mid());
			casefile.setFd_case_year(causelist.getCl_case_year());

			CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFile(casefile);
			if (caseFileDetail != null) {
				causelist.setCl_fd_mid(caseFileDetail.getFd_id());

				for (Petitioner pt : caseFileDetail.getPetitioners()) {

					if (pt.getPt_sequence() == 1) {
						causelist.setCl_first_petitioner(pt.getPt_name());
						break;

					}

				}

				for (Respondent rt : caseFileDetail.getRespondents()) {

					if (rt.getRt_sequence() == 1) {

						causelist.setCl_first_respondent(rt.getRt_name());
						break;
					}

				}

			}

			RespondentCounsel rc = null;
			PetitionerCounsel pc = null;
			rc = causeListService.getRespondentCounsel(caseFileDetail.getFd_id());
			pc = causeListService.getPetionerCounsel(caseFileDetail.getFd_id());
			causelist.setCl_respondent_council(rc.getRc_name());
			causelist.setCl_petitioner_council(pc.getPc_name());
		}

		causelist.setCl_rec_status(1);
		CauseList cl = causeListService.save(causelist);

		if (cl.getCl_id() != null) {
			response.setResponse("TRUE");
			response.setData("Record added successfully");
		} else {
			response.setResponse("False");
			response.setData("Error occurred while adding record");
		}

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
//============================================================
	@RequestMapping(value = "/causelist/getParty/{fdId}", method = RequestMethod.GET)
	public @ResponseBody ActionResponse<CauseList> getPartyNameById(@PathVariable Long fdId, HttpSession session) {

	    CauseList partyName = causeListService.getPartyNameById(fdId);

	    ActionResponse<CauseList> response = new ActionResponse<>();
	    response.setResponse("true");
	    response.setData(partyName);

	    return response; 
	}
	
	
	
}
