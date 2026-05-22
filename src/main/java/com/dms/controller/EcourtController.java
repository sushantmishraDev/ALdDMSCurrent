package com.dms.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dms.model.ActionResponse;
import com.dms.model.CaseFileDetail;
import com.dms.model.CauseList;
import com.dms.model.CauseListType;
import com.dms.model.CourtUserMapping;
import com.dms.model.Lookup;
import com.dms.model.SubDocument;
import com.dms.model.User;
import com.dms.model.UserRole;
import com.dms.service.CaseFileDetailService;
import com.dms.service.CauseListService;
import com.dms.service.CourtMasterService;
import com.dms.service.LookupService;
import com.dms.service.OrderReportService;
import com.dms.service.SubDocumentService;
import com.dms.utility.GlobalFunction;

@Controller
@RequestMapping("/ecourt")
public class EcourtController {

	private GlobalFunction globalfunction;

	@Autowired
	CauseListService causeListService;
	
	@Autowired
	SubDocumentService subDocumentService;

	@Autowired
	private LookupService lookupService;

	@Autowired
	private CaseFileDetailService caseFileDetailService;
	
	
	@Autowired
	private OrderReportService orderReportService;

	@Autowired
	CourtMasterService courtMasterService;

	public EcourtController() {
		// TODO Auto-generated constructor stub
		globalfunction = new GlobalFunction();
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String adminHome() {

		return "/ecourt/home";

	}
	
	@RequestMapping(value = "/transferHome", method = RequestMethod.GET)
	public String adminHome1() {

		return "/ecourt/transferHome" ;
	}

	@RequestMapping(value = "/downloadCauseList", method = RequestMethod.GET)
	public @ResponseBody String downloadCauseList(HttpSession session, HttpServletRequest request)
			throws ParseException, IOException {

		String result = null;

		String webAppPAth = "E:/dmslocal/dms/src/main/webapp";
		
		Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date1.toString());

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");

		String userMaster = globalfunction.convert_to_json(user);
		String repo = lookupService.getLookUpObject("DOWNLOADPATH").getLk_longname();

		File ecourt = new File(repo + File.separator + user.getUsername());
		if (ecourt.exists()) {
			FileUtils.deleteDirectory(new File(repo + File.separator + user.getUsername()));
		}

		String dirPath = repo + File.separator + user.getUsername();
		String metadata = "metadata";
		String dashboard = "dashboard";
		String list1 = "list";
		String fileview = "fileview";
		String caseFiles = "caseFiles";
		
		
		

		File newDirectory = new File(dirPath);
//Create directory for non existed path.
		boolean isCreated = newDirectory.mkdirs();
		if (isCreated) {
			System.out.printf("1. Successfully created directories, path:%s", newDirectory.getCanonicalPath());
			Path sourceDirectory = Paths.get(webAppPAth);
			Path targetDirectory = Paths.get(dirPath + File.separator + "webapp");

			File sourceDirectory1 = new File(webAppPAth);
			File targetDirectory1 = new File(dirPath + File.separator + "webapp");

			// copy source to target using Files Class
			FileUtils.copyDirectory(sourceDirectory1, targetDirectory1);
		} else if (newDirectory.exists()) {
			System.out.println("1. Directory path already exist, path:%s");
			// FileUtils.deleteDirectory(newDirectory);
			// newDirectory.mkdirs();
		} else {
			System.out.println("1. Unable to create directory");

		}

//Create file under new directory path C:\newDirectory\directory
		/*
		 * File newFile = new File(dirPath + File.separator + metadata); //Create new
		 * file under specified directory isCreated = newFile.createNewFile(); if
		 * (isCreated) {
		 * System.out.printf("\n2. Successfully created new file, path:%s",
		 * newFile.getCanonicalPath()); } else { //File may already exist
		 * System.out.printf("\n2. Unable to create new file"); }
		 */

//Create new directory under C:\nonExistedDirectory\directory
		File oneMoreDirectory = new File(dirPath + File.separator + metadata);
//Create directory for existed path.
		isCreated = oneMoreDirectory.mkdir();
		if (isCreated) {
			System.out.printf("\n3. Successfully created new directory, path:%s", oneMoreDirectory.getCanonicalPath());
			// now create Sub folders
			File oneMoreDirectory1 = new File(dirPath + File.separator + metadata + File.separator + dashboard);
			isCreated = oneMoreDirectory1.mkdir();
			if (isCreated) {
				// get Dashboard data
			}

			File oneMoreDirectory2 = new File(dirPath + File.separator + metadata + File.separator + list1);
			isCreated = oneMoreDirectory2.mkdir();
			if (isCreated) {
				// get list data
			}

			File oneMoreDirectory3 = new File(dirPath + File.separator + metadata + File.separator + fileview);
			isCreated = oneMoreDirectory3.mkdir();
			if (isCreated) {
				// file data with filed
			}
			File oneMoreDirectory4 = new File(dirPath + File.separator + metadata + File.separator + caseFiles);
			isCreated = oneMoreDirectory4.mkdir();
			if (isCreated) {
				// file data with filed
			}

		} else { // Directory may already exist
			System.out.printf("\n3. Unable to create directory");
		}

//Create file under new directory path C:\newDirectory\directory
//File anotherNewFile = new File(oneMoreDirectory + File.separator + anotherNewFileName);

//Create new file under specified directory
		/*
		 * isCreated = anotherNewFile.createNewFile(); if (isCreated) {
		 * System.out.printf("\n4. Successfully created new file, path:%s",
		 * anotherNewFile.getCanonicalPath()); } else { //File may already exist
		 * System.out.printf("\n4. Unable to create new file"); }
		 */
		/*
		 * File userMasterFile = new
		 * File(repo+File.separator+user.getUsername()+File.separator+metadata+File.
		 * separator+dashboard+File.separator+"userMaster.json");
		 * userMasterFile.createNewFile();
		 */
		FileWriter file = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
				+ File.separator + dashboard + File.separator + "userMaster.json");
		file.write(userMaster);
		file.close();

		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}

		List list = new ArrayList();
		List listAdditional = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(date1);
			causeList.setCl_court_no(mapping.getCum_court_mid());

			CauseList cl1 = new CauseList();
			cl1.setCl_dol(date1);
			cl1.setCl_list_type_mid(1l);
			List<CauseList> applicationList = causeListService.getList(cl1);

			CauseList cl2 = new CauseList();
			cl2.setCl_dol(date1);
			cl2.setCl_list_type_mid(2l);
			List<CauseList> correctionList = causeListService.getList(cl2);

			CauseList cl3 = new CauseList();
			cl3.setCl_dol(date1);
			cl3.setCl_list_type_mid(3l);
			List<CauseList> dailyList = causeListService.getList(cl3);

			CauseList cl4 = new CauseList();
			cl4.setCl_dol(date1);
			cl4.setCl_list_type_mid(4l);
			List<CauseList> backlogList = causeListService.getList(cl4);

			CauseList cl5 = new CauseList();
			cl5.setCl_dol(date1);
			cl5.setCl_list_type_mid(5l);
			List<CauseList> freshList = causeListService.getList(cl5);

			CauseList cl6 = new CauseList();
			cl6.setCl_dol(date1);
			cl6.setCl_list_type_mid(6l);
			List<CauseList> suppList = causeListService.getList(cl6);

			CauseList cl7 = new CauseList();
			cl7.setCl_dol(date1);
			cl7.setCl_list_type_mid(7l);
			List<CauseList> addList = causeListService.getList(cl7);

			String l1 = globalfunction.convert_to_json(applicationList);
			String l2 = globalfunction.convert_to_json(correctionList);
			String l3 = globalfunction.convert_to_json(dailyList);
			String l4 = globalfunction.convert_to_json(backlogList);
			String l5 = globalfunction.convert_to_json(freshList);
			String l6 = globalfunction.convert_to_json(suppList);
			String l7 = globalfunction.convert_to_json(addList);

			// Code for getting CaseFileDetails office report and sb Document.

			List<CaseFileDetail> cfdList = new ArrayList<CaseFileDetail>();
			List<List<Object>> orderReportData =new  ArrayList<List<Object>>();

			for (CauseList cl : applicationList) {

				if (cl.getCl_fd_mid() != null) {

					CaseFileDetail cfd = caseFileDetailService.getCaseFileDetail(cl.getCl_fd_mid());
					
					
					cfdList.add(cfd);
					List<SubDocument> subList =subDocumentService.getAllSubDocuments(cfd.getFd_id());
					
					for(SubDocument sub: subList){
						
					if(sub.getSd_document_name() != null) {	
					String srcPath = lookupRepo.getLk_longname() + File.separator
							+ cfd.getCaseType().getCt_label()
							+ File.separator + sub.getIndexField().getIf_name()
							+ File.separator + sub.getSd_document_name()
							+ ".pdf";

					File source = new File(srcPath);
					
					if(source.exists()) {
					
					

				//	String uploadPath = context.getRealPath("");
					File dest = new File(repo + File.separator + user.getUsername() + File.separator + metadata + File.separator + caseFiles + File.separator 
							+ File.separator + sub.getSd_document_name()
							+ ".pdf");

					try {
						FileUtils.copyFile(source, dest);
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
					}
					}
					List<Object> orderReport= orderReportService.getOrderReports1(cl.getCl_fd_mid());
					if(orderReport !=null || !orderReport.isEmpty()) {
						orderReportData.add(orderReport);
					}
				}

			}
			for (CauseList cl : correctionList) {

				if (cl.getCl_fd_mid() != null) {

					CaseFileDetail cfd = caseFileDetailService.getCaseFileDetail(cl.getCl_fd_mid());
					cfdList.add(cfd);
//List<SubDocument> subList =cfd.getSubDocument();
					List<SubDocument> subList =subDocumentService.getAllSubDocuments(cfd.getFd_id());
					
					for(SubDocument sub: subList){
						
					if(sub.getSd_document_name() != null) {	
					String srcPath = lookupRepo.getLk_longname() + File.separator
							+ cfd.getCaseType().getCt_label()
							+ File.separator + sub.getIndexField().getIf_name()
							+ File.separator + sub.getSd_document_name()
							+ ".pdf";

					File source = new File(srcPath);
					
					if(source.exists()) {
					
					

				//	String uploadPath = context.getRealPath("");
					File dest = new File(repo + File.separator + user.getUsername() + File.separator + metadata + File.separator + caseFiles + File.separator 
							+ File.separator + sub.getSd_document_name()
							+ ".pdf");

					try {
						FileUtils.copyFile(source, dest);
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
					}
					}
					List<Object> orderReport= orderReportService.getOrderReports1(cl.getCl_fd_mid());
					if(orderReport !=null || !orderReport.isEmpty()) {
						orderReportData.add(orderReport);
					}
				}

			}
			for (CauseList cl : dailyList) {

				if (cl.getCl_fd_mid() != null) {

					CaseFileDetail cfd = caseFileDetailService.getCaseFileDetail(cl.getCl_fd_mid());
					cfdList.add(cfd);
/*List<SubDocument> subList =cfd.getSubDocument();*/
					
					List<SubDocument> subList =subDocumentService.getAllSubDocuments(cfd.getFd_id());
					
					for(SubDocument sub: subList){
						
					if(sub.getSd_document_name() != null) {	
					String srcPath = lookupRepo.getLk_longname() + File.separator
							+ cfd.getCaseType().getCt_label()
							+ File.separator + sub.getIndexField().getIf_name()
							+ File.separator + sub.getSd_document_name()
							+ ".pdf";

					File source = new File(srcPath);
					
					if(source.exists()) {
					
					

				//	String uploadPath = context.getRealPath("");
					File dest = new File(repo + File.separator + user.getUsername() + File.separator + metadata + File.separator + caseFiles + File.separator 
							+ File.separator + sub.getSd_document_name()
							+ ".pdf");

					try {
						FileUtils.copyFile(source, dest);
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
					}
					}
					List<Object> orderReport= orderReportService.getOrderReports1(cl.getCl_fd_mid());
					if(orderReport !=null || !orderReport.isEmpty()) {
						orderReportData.add(orderReport);
					}
				}

			}
			for (CauseList cl : freshList) {

				if (cl.getCl_fd_mid() != null) {

					CaseFileDetail cfd = caseFileDetailService.getCaseFileDetail(cl.getCl_fd_mid());
					cfdList.add(cfd);
/*List<SubDocument> subList =cfd.getSubDocument();*/
					
					List<SubDocument> subList =subDocumentService.getAllSubDocuments(cfd.getFd_id());
					
					for(SubDocument sub: subList){
						
					if(sub.getSd_document_name() != null) {	
					String srcPath = lookupRepo.getLk_longname() + File.separator
							+ cfd.getCaseType().getCt_label()
							+ File.separator + sub.getIndexField().getIf_name()
							+ File.separator + sub.getSd_document_name()
							+ ".pdf";

					File source = new File(srcPath);
					
					if(source.exists()) {
					
					

				//	String uploadPath = context.getRealPath("");
					File dest = new File(repo + File.separator + user.getUsername() + File.separator + metadata + File.separator + caseFiles + File.separator 
							+ File.separator + sub.getSd_document_name()
							+ ".pdf");

					try {
						FileUtils.copyFile(source, dest);
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
					}
					}
					List<Object> orderReport= orderReportService.getOrderReports1(cl.getCl_fd_mid());
					if(orderReport !=null || !orderReport.isEmpty()) {
						orderReportData.add(orderReport);
					}
				}

			}
			for (CauseList cl : suppList) {

				if (cl.getCl_fd_mid() != null) {

					CaseFileDetail cfd = caseFileDetailService.getCaseFileDetail(cl.getCl_fd_mid());
					cfdList.add(cfd);
					List<Object> orderReport= orderReportService.getOrderReports1(cl.getCl_fd_mid());
					if(orderReport !=null || !orderReport.isEmpty()) {
						orderReportData.add(orderReport);
					}
				}

			}
			for (CauseList cl : addList) {

				if (cl.getCl_fd_mid() != null) {

					CaseFileDetail cfd = caseFileDetailService.getCaseFileDetail(cl.getCl_fd_mid());
					
					cfdList.add(cfd);
/*List<SubDocument> subList =cfd.getSubDocument();*/
					
					List<SubDocument> subList =subDocumentService.getAllSubDocuments(cfd.getFd_id());
					
					for(SubDocument sub: subList){
						
					if(sub.getSd_document_name() != null) {	
					String srcPath = lookupRepo.getLk_longname() + File.separator
							+ cfd.getCaseType().getCt_label()
							+ File.separator + sub.getIndexField().getIf_name()
							+ File.separator + sub.getSd_document_name()
							+ ".pdf";

					File source = new File(srcPath);
					
					if(source.exists()) {
					
					

				//	String uploadPath = context.getRealPath("");
					File dest = new File(repo + File.separator + user.getUsername() + File.separator + metadata + File.separator + caseFiles + File.separator 
							+ File.separator + sub.getSd_document_name()
							+ ".pdf");

					try {
						FileUtils.copyFile(source, dest);
					} catch (IOException e) {
						e.printStackTrace();
					}
					}
					}
					}
					List<Object> orderReport= orderReportService.getOrderReports1(cl.getCl_fd_mid());
					if(orderReport !=null || !orderReport.isEmpty()) {
						orderReportData.add(orderReport);
					}
				}

			}
			
			
			
			// for writing CaseFileDetailsFiles 
			
			
			String cfdListJson = globalfunction.convert_to_json(cfdList);
			
			String orderReports = globalfunction.convert_to_json(orderReportData);
			
			
			
			FileWriter orderReportsFile = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
					+ File.separator + list1 + File.separator + "orderReorts.json");
			orderReportsFile.write(orderReports);
			orderReportsFile.close();
			
			
			
			FileWriter cfdDetails = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
					+ File.separator + list1 + File.separator + "caseFiles.json");
			cfdDetails.write(cfdListJson);
			cfdDetails.close();

			FileWriter f1 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
					+ File.separator + list1 + File.separator + "applicationList.json");
			f1.write(l1);
			f1.close();

			FileWriter f2 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
					+ File.separator + list1 + File.separator + "correctionList.json");
			f2.write(l2);
			f2.close();

			FileWriter f3 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
					+ File.separator + list1 + File.separator + "dailyList.json");
			f3.write(l3);
			f3.close();

			FileWriter f4 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
					+ File.separator + list1 + File.separator + "backlogList.json");
			f4.write(l4);
			f4.close();

			FileWriter f5 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
					+ File.separator + list1 + File.separator + "freshList.json");
			f5.write(l5);
			f5.close();

			FileWriter f6 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
					+ File.separator + list1 + File.separator + "suppList.json");
			f6.write(l6);
			f6.close();

			FileWriter f7 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
					+ File.separator + list1 + File.separator + "addList.json");
			f7.write(l7);
			f7.close();

			list = causeListService.getListByType(causeList);
			listAdditional = causeListService.getListByType(causeList);
		} else {
			list = causeListService.getListByType(causeList);
			listAdditional = causeListService.getListByType(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("1")) {
				c.setCl_list_type_mid(1L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("2")) {
				c.setCl_list_type_mid(2L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("3")) {
				c.setCl_list_type_mid(3L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("4")) {
				c.setCl_list_type_mid(4L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("5")) {
				c.setCl_list_type_mid(5L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("6")) {
				c.setCl_list_type_mid(6L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("7")) {
				c.setCl_list_type_mid(7L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			cList.add(c);
		}
		List<CauseList> listAdd = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);

			if (row1[1].toString().equals("11")) {
				c.setCl_list_type_mid(11L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(11l);
				c.setcType(clt);
				listAdd.add(c);
			}
			if (row1[1].toString().equals("12")) {
				c.setCl_list_type_mid(12L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(12l);
				c.setcType(clt);
				listAdd.add(c);
			}
			if (row1[1].toString().equals("13")) {
				c.setCl_list_type_mid(13L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(13l);
				c.setcType(clt);
				listAdd.add(c);
			}
			if (row1[1].toString().equals("14")) {
				c.setCl_list_type_mid(14L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(14l);
				c.setcType(clt);
				listAdd.add(c);
			}

			if (row1[1].toString().equals("15")) {
				c.setCl_list_type_mid(15L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(15l);
				c.setcType(clt);
				listAdd.add(c);
			}

		}
		
		
		List<CauseList> supList = new ArrayList<>();
		
		for (int i = 0; i < listAdditional.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("8")) {
				c.setCl_list_type_mid(8L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(8l);
				c.setcType(clt);
				supList.add(c);
			}
			if (row1[1].toString().equals("9")) {
				c.setCl_list_type_mid(9L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(9l);
				c.setcType(clt);
				supList.add(c);
			}
			if (row1[1].toString().equals("10")) {
				c.setCl_list_type_mid(10L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(10l);
				c.setcType(clt);
				supList.add(c);
			}

		}

		// response.setResponse("TRUE");
		String caseCount = globalfunction.convert_to_json(cList);
		String addCount = globalfunction.convert_to_json(listAdd);
		
		String suppCount = globalfunction.convert_to_json(supList);
		List<CauseListType> listType1 = causeListService.getCauseListTypes();
		/*
		 * CauseListType listType2 = causeListService.findCauseListType(2l);
		 * CauseListType listType3 = causeListService.findCauseListType(3l);
		 * CauseListType listType4 = causeListService.findCauseListType(4l);
		 * CauseListType listType5 = causeListService.findCauseListType(5l);
		 * CauseListType listType6 = causeListService.findCauseListType(6l);
		 * CauseListType listType7 = causeListService.findCauseListType(7l);
		 * 
		 * 
		 * // for cause list type description String l1 =
		 * globalfunction.convert_to_json(listType1); String l2 =
		 * globalfunction.convert_to_json(listType2); String l3 =
		 * globalfunction.convert_to_json(listType3); String l4 =
		 * globalfunction.convert_to_json(listType4); String l5 =
		 * globalfunction.convert_to_json(listType5); String l6 =
		 * globalfunction.convert_to_json(listType6); String l7 =
		 * globalfunction.convert_to_json(listType7);
		 */

		String l1 = globalfunction.convert_to_json(listType1);

		FileWriter file3 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
				+ File.separator + list1 + File.separator + "listTypes.json");
		file3.write(l1);
		file3.close();

		/*
		 * File caseCountFile =new File
		 * (repo+File.separator+user.getUsername()+File.separator+metadata+File.
		 * separator+dashboard+File.separator+"caseCount.json");
		 * caseCountFile.createNewFile();
		 */
		FileWriter file1 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
				+ File.separator + dashboard + File.separator + "caseCount.json");
		file1.write(caseCount);
		file1.close();
		FileWriter file2 = new FileWriter(repo + File.separator + user.getUsername() + File.separator + metadata
				+ File.separator + dashboard + File.separator + "addCount.json");
		file2.write(addCount);
		file2.close();

		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	
	@RequestMapping(value = "/getreport", method = RequestMethod.GET)
	public @ResponseBody String getDashBoardReport(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		
		Date date2=new Date();

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(date2);
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getListByType(causeList);
		} else {
			list = causeListService.getListByType(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

	
		for (Object obj : list) {
		    Object[] row = (Object[]) obj;
		    
		    if(Long.valueOf(row[1].toString())==21L) {
		    	 CauseList c = new CauseList();
				    c.setCl_list_type_mid(2L);
				    c.setCount(Integer.parseInt("0"));
				    c.setListTypeName("CORRECTION APPLICATION"); 
				    cList.add(c);
		    }

		    CauseList c = new CauseList();
		    c.setCl_list_type_mid(Long.valueOf(row[1].toString()));
		    c.setCount(Integer.parseInt(row[0].toString()));
		    c.setListTypeName(row[2].toString()); 

		    cList.add(c);
		}

		
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}


	@RequestMapping(value = "/getreport_old", method = RequestMethod.GET)
	public @ResponseBody String getDashBoardReport_old(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		
		Date date2=new Date();

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(date2);
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getListByType(causeList);
		} else {
			list = causeListService.getListByType(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("1")) {
				c.setCl_list_type_mid(1L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("2")) {
				c.setCl_list_type_mid(2L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("3")) {
				c.setCl_list_type_mid(3L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("4")) {
				c.setCl_list_type_mid(4L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("5")) {
				c.setCl_list_type_mid(5L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("6")) {
				c.setCl_list_type_mid(6L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("7")) {
				c.setCl_list_type_mid(7L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("19")) {
				c.setCl_list_type_mid(19L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("28")) {
				c.setCl_list_type_mid(28L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("20")) {
				c.setCl_list_type_mid(20L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("21")) {
				c.setCl_list_type_mid(21L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("22")) {
				c.setCl_list_type_mid(22L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("23")) {
				c.setCl_list_type_mid(23L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("24")) {
				c.setCl_list_type_mid(24L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("24")) {
				c.setCl_list_type_mid(24L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("25")) {
				c.setCl_list_type_mid(25L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("26")) {
				c.setCl_list_type_mid(26L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("27")) {
				c.setCl_list_type_mid(27L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("28")) {
				c.setCl_list_type_mid(28L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("29")) {
				c.setCl_list_type_mid(29L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("30")) {
				c.setCl_list_type_mid(30L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("31")) {
				c.setCl_list_type_mid(31L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("32")) {
				c.setCl_list_type_mid(32L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("33")) {
				c.setCl_list_type_mid(33L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("34")) {
				c.setCl_list_type_mid(34L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("35")) {
				c.setCl_list_type_mid(35L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("36")) {
				c.setCl_list_type_mid(36L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("38")) {
				c.setCl_list_type_mid(38L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("39")) {
				c.setCl_list_type_mid(39L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("40")) {
				c.setCl_list_type_mid(40L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("41")) {
				c.setCl_list_type_mid(41L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			
			if (row1[1].toString().equals("42")) {
				c.setCl_list_type_mid(42L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("43")) {
				c.setCl_list_type_mid(43L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("44")) {
				c.setCl_list_type_mid(44L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("45")) {
				c.setCl_list_type_mid(45L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("46")) {
				c.setCl_list_type_mid(46L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("47")) {
				c.setCl_list_type_mid(47L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("48")) {
				c.setCl_list_type_mid(48L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("49")) {
				c.setCl_list_type_mid(49L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("50")) {
				c.setCl_list_type_mid(50L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("51")) {
				c.setCl_list_type_mid(51L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("52")) {
				c.setCl_list_type_mid(52L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}if (row1[1].toString().equals("53")) {
				c.setCl_list_type_mid(53L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("54")) {
				c.setCl_list_type_mid(54L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("55")) {
				c.setCl_list_type_mid(55L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}if (row1[1].toString().equals("56")) {
				c.setCl_list_type_mid(56L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}if (row1[1].toString().equals("57")) {
				c.setCl_list_type_mid(57L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}if (row1[1].toString().equals("58")) {
				c.setCl_list_type_mid(58L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("59")) {
				c.setCl_list_type_mid(59L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("60")) {
				c.setCl_list_type_mid(60L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("61")) {
				c.setCl_list_type_mid(61L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("62")) {
				c.setCl_list_type_mid(62L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("63")) {
				c.setCl_list_type_mid(63L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			if (row1[1].toString().equals("64")) {
				c.setCl_list_type_mid(64L);
				c.setCount(Integer.parseInt(row1[0].toString()));
			}
			
			cList.add(c);
		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}

	@RequestMapping(value = "/getreportforadditionallists", method = RequestMethod.GET)
	public @ResponseBody String getDashBoardReport1(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getListByType(causeList);
		} else {
			list = causeListService.getListByType(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);

			if (row1[1].toString().equals("11")) {
				c.setCl_list_type_mid(11L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(11l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("12")) {
				c.setCl_list_type_mid(12L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(12l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("13")) {
				c.setCl_list_type_mid(13L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(13l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("14")) {
				c.setCl_list_type_mid(14L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(14l);
				c.setcType(clt);
				cList.add(c);
			}

			if (row1[1].toString().equals("15")) {
				c.setCl_list_type_mid(15L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(15l);
				c.setcType(clt);
				cList.add(c);
			}
			
			if (row1[1].toString().equals("25")) {
				c.setCl_list_type_mid(25L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(25l);
				c.setcType(clt);
				cList.add(c);
			}

		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	@RequestMapping(value = "/getreportforadditionallistsForTransferCases", method = RequestMethod.GET)
	public @ResponseBody String getDashBoardReportTransfer1(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		
		String fromCourt1 =request.getParameter("fromCourt");
Integer fromCourt =Integer.parseInt(fromCourt1);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(fromCourt);
			causeList.setCl_transfer_to(mapping.getCum_court_mid());

			list = causeListService.getListByTypeForTransferCases(causeList);
		} else {
			list = causeListService.getListByTypeForTransferCases(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);

			if (row1[1].toString().equals("11")) {
				c.setCl_list_type_mid(11L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(11l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("12")) {
				c.setCl_list_type_mid(12L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(12l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("13")) {
				c.setCl_list_type_mid(13L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(13l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("14")) {
				c.setCl_list_type_mid(14L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(14l);
				c.setcType(clt);
				cList.add(c);
			}

			if (row1[1].toString().equals("15")) {
				c.setCl_list_type_mid(15L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(15l);
				c.setcType(clt);
				cList.add(c);
			}

		}
		
		
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	@RequestMapping(value = "/getreportforDIAlists", method = RequestMethod.GET)
	public @ResponseBody String getDashBoardReport4(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getListByType(causeList);
		} else {
			list = causeListService.getListByType(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("20")) {
				c.setCl_list_type_mid(20L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(20l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("23")) {
				c.setCl_list_type_mid(23L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(23l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("24")) {
				c.setCl_list_type_mid(24L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(24l);
				c.setcType(clt);
				cList.add(c);
			}

		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}

	@RequestMapping(value = "/getreportforcorrectionlists", method = RequestMethod.GET)
	public @ResponseBody String getDashBoardReport3(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getListByType(causeList);
		} else {
			list = causeListService.getListByType(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("21")) {
				c.setCl_list_type_mid(21L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(21l);
				c.setcType(clt);
				cList.add(c);
			}

		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	@RequestMapping(value = "/getreportforsupplementarylist", method = RequestMethod.GET)
	public @ResponseBody String getDashBoardReport2(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getListByType(causeList);
		} else {
			list = causeListService.getListByType(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("8")) {
				c.setCl_list_type_mid(8L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(8l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("9")) {
				c.setCl_list_type_mid(9L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(9l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("10")) {
				c.setCl_list_type_mid(10L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(10l);
				c.setcType(clt);
				cList.add(c);
			}

		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	
	@RequestMapping(value = "/getreportforproductionlist", method = RequestMethod.GET)
	public @ResponseBody String getreportforproductionlist(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getListByType(causeList);
		} else {
			list = causeListService.getListByType(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("37")) {
				c.setCl_list_type_mid(37L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(37l);
				c.setcType(clt);
				cList.add(c);
			}			

		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	
	
	
	
	@RequestMapping(value = "/getreportforfreshmentionlist", method = RequestMethod.GET)
	public @ResponseBody String getFreshMentionCount(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getListByType(causeList);
		} else {
			list = causeListService.getListByType(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("16")) {
				c.setCl_list_type_mid(16L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(16l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("17")) {
				c.setCl_list_type_mid(17L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(17l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("18")) {
				c.setCl_list_type_mid(18L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(18l);
				c.setcType(clt);
				cList.add(c);
			}

		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	@RequestMapping(value = "/getreportforsupplementarylistForTransferCases", method = RequestMethod.GET)
	public @ResponseBody String getreportforsupplementarylistForTransferCases(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");
		String fromCourt1 =request.getParameter("fromCourt");
Integer fromCourt =Integer.parseInt(fromCourt1);
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(fromCourt);
			causeList.setCl_transfer_to(mapping.getCum_court_mid());

			list = causeListService.getListByTypeForTransferCases(causeList);
		} else {
			list = causeListService.getListByTypeForTransferCases(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("8")) {
				c.setCl_list_type_mid(8L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(8l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("9")) {
				c.setCl_list_type_mid(9L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(9l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("10")) {
				c.setCl_list_type_mid(10L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(10l);
				c.setcType(clt);
				cList.add(c);
			}

		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	
	@RequestMapping(value = "/getFreshMentionListForTransferCases", method = RequestMethod.GET)
	public @ResponseBody String getFreshMentionListForTransferCases(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");
		String fromCourt1 =request.getParameter("fromCourt");
Integer fromCourt =Integer.parseInt(fromCourt1);
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(fromCourt);
			causeList.setCl_transfer_to(mapping.getCum_court_mid());

			list = causeListService.getListByTypeForTransferCases(causeList);
		} else {
			list = causeListService.getListByTypeForTransferCases(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("16")) {
				c.setCl_list_type_mid(16L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(16l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("17")) {
				c.setCl_list_type_mid(17L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(17l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("18")) {
				c.setCl_list_type_mid(18L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(18l);
				c.setcType(clt);
				cList.add(c);
			}

		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	@RequestMapping(value = "/getTranferCases", method = RequestMethod.GET)
	public @ResponseBody String getTransferCases(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getTransferCases(causeList);
		} else {
			list = causeListService.getTransferCases(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			CauseList c = new CauseList();
			Object[] row1 = (Object[]) list.get(i);
			if (row1[1].toString().equals("1")) {
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCl_list_type_mid(1L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				cList.add(c);
			}
			if (row1[1].toString().equals("2")) {
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCl_list_type_mid(2L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				cList.add(c);
			}
			if (row1[1].toString().equals("3")) {
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCl_list_type_mid(3L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				cList.add(c);
			}
			if (row1[1].toString().equals("4")) {
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCl_list_type_mid(4L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				cList.add(c);
			}
			if (row1[1].toString().equals("5")) {
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCl_list_type_mid(5L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				cList.add(c);
			}
			if (row1[1].toString().equals("6")) {
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCl_list_type_mid(6L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				cList.add(c);
			}
			if (row1[1].toString().equals("7")) {
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCl_list_type_mid(7L);
				c.setCount(Integer.parseInt(row1[0].toString()));
				cList.add(c);
			}
			
			if (row1[1].toString().equals("11")) {
				c.setCl_list_type_mid(11L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(11l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("12")) {
				c.setCl_list_type_mid(12L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(12l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("13")) {
				c.setCl_list_type_mid(13L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(13l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("14")) {
				c.setCl_list_type_mid(14L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(14l);
				c.setcType(clt);
				cList.add(c);
			}
			
			if (row1[1].toString().equals("8")) {
				c.setCl_list_type_mid(8L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCount(Integer.parseInt(row1[0].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				CauseListType clt = causeListService.findCauseListType(8l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("9")) {
				c.setCl_list_type_mid(9L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(9l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("10")) {
				c.setCl_list_type_mid(10L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(10l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("16")) {
				c.setCl_list_type_mid(16L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(16l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("17")) {
				c.setCl_list_type_mid(17L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(17l);
				c.setcType(clt);
				cList.add(c);
			}
			if (row1[1].toString().equals("18")) {
				c.setCl_list_type_mid(18L);
				c.setCl_court_no(Integer.parseInt(row1[2].toString()));
				c.setCourtMaster(causeListService.getCourMaster(Integer.parseInt(row1[2].toString())));
				c.setCount(Integer.parseInt(row1[0].toString()));
				CauseListType clt = causeListService.findCauseListType(18l);
				clt.setClt_cr_by(user.getCr_by());
				clt.setClt_description("For Correction Application");
				c.setcType(clt);
				cList.add(c);
			}
			
		
			

		}
		response.setModelList(cList);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
	
	
	@RequestMapping(value = "/getTranferCasesCount", method = RequestMethod.GET)
	public @ResponseBody String getTransferCasesCount(HttpSession session, HttpServletRequest request)
			throws ParseException {

		String result = null;

		ActionResponse<CauseList> response = new ActionResponse<CauseList>();

		String date = request.getParameter("causelist_date");

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		System.out.println("datadate: " + date);

		CauseList causeList = new CauseList();
		User user = (User) session.getAttribute("USER");
		List<UserRole> userroles = user.getUserroles();
		String userRole = "";
		for (UserRole userrole : userroles) {
			userRole = userrole.getLk().getLk_longname();
		}
		List list = new ArrayList();

		if (userRole.equals("ECOURT") || userRole.equals("Private_Secretary") || userRole.equals("Bench Secretary")) {
			CourtUserMapping mapping = courtMasterService.getCourtMapping(user.getUm_id());

			causeList.setCl_dol(new Date());
			causeList.setCl_court_no(mapping.getCum_court_mid());

			list = causeListService.getTransferCasesCount(causeList);
		} else {
			list = causeListService.getTransferCasesCount(causeList);
		}
		List<CauseList> cList = new ArrayList<>();

		
		response.setModelList(list);

		if (list != null) {
			response.setResponse("TRUE");
			result = globalfunction.convert_to_json(response);
		} else {
			response.setResponse("FALSE");
		}

		return result;

	}
}
