package com.dms.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dms.model.ActionResponse;
import com.dms.model.ApplicationTypes;
import com.dms.model.ApplicationWithPetition;
import com.dms.model.CaseFileDetail;
import com.dms.model.CaseLkoToAldHistory;
import com.dms.model.CaseNominated;
import com.dms.model.CaseType;
import com.dms.model.CaseTypeLko;
import com.dms.model.CauseList;
import com.dms.model.CauseListHistory;
import com.dms.model.CauseListType;
import com.dms.model.CourtMaster;
import com.dms.model.CourtUserMapping;
import com.dms.model.DigitizationRequest;
import com.dms.model.DownloadFile;
import com.dms.model.DownloadModel;
import com.dms.model.DownloadReport;
import com.dms.model.ImpugnedOrder;
import com.dms.model.IndexField;
import com.dms.model.Judge;
import com.dms.model.JudgeName;
import com.dms.model.Lookup;
import com.dms.model.MediationDocs;
import com.dms.model.MetaData;
import com.dms.model.Notes;
import com.dms.model.OrderFromElegalix;
import com.dms.model.OrderReport;
import com.dms.model.OrderReportSub;
import com.dms.model.Petitioner;
import com.dms.model.PetitionerCounsel;
import com.dms.model.RegularToDefective;
import com.dms.model.Respondent;
import com.dms.model.RespondentCounsel;
import com.dms.model.SubApplication;
import com.dms.model.SubDocument;
import com.dms.model.SubDocumentApplicationStatus;
import com.dms.model.User;
import com.dms.service.CaseFileDetailService;
import com.dms.service.CaseNominatedService;
import com.dms.service.CasetypeService;
import com.dms.service.CauseListService;
import com.dms.service.CommonReportsService;
import com.dms.service.CourtMasterService;
import com.dms.service.DownloadFileService;
import com.dms.service.LKOMasterService;
import com.dms.service.LookupService;
import com.dms.service.MasterService;
import com.dms.service.OrderReportService;
import com.dms.service.SubDocumentService;
import com.dms.service.UserService;
import com.dms.utility.GlobalFunction;
import com.dms.utility.PDFMerger;
import com.efiling.model.ApplicationCheckListMapping;
import com.efiling.model.EfilingApplication;
import com.efiling.model.EfilingCaseFileDetail;
import com.efiling.service.AddCaseEfilingService;
import com.efiling.service.AmendmentService;
import com.efiling.service.EilingApplicationService;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/casefile")
public class CaseFileController {

	@Autowired
	ServletContext context;
	
	@Autowired
	private CommonReportsService commonReportService;
	
	
	@Autowired
	private LookupService lookupService;

	@Autowired
	private MasterService masterService;

	@Autowired
	private CaseFileDetailService caseFileDetailService;

	@Autowired
	private OrderReportService orderReportService;

	@Autowired
	private SubDocumentService subDocumentService;

	@Autowired
	private CasetypeService casetypeService;

	@Autowired
	private DownloadFileService downloadService;
	
	@Autowired
	private UserService usermaster;
	
	@Autowired
	private AmendmentService amendmentService;
	
	@Autowired
	private LKOMasterService lkoMasterService;
	
	@Autowired
	private AddCaseEfilingService addcaseEfilingService;
	
	@Autowired 
	private CauseListService causeListService;

	@Autowired
	private CaseNominatedService caseNominatedService;
	
	@Autowired
	private CourtMasterService courtMasterService;
	
	@Autowired
	private EilingApplicationService eilingApplicationService;
	
	private GlobalFunction globalfunction;
	

	public CaseFileController() {
		globalfunction = new GlobalFunction();
	}
	
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String Manage() {
		
		//downloadFile();
		
		return "/casefile/manage";
		
	}

	@RequestMapping(value = "/download_manage", method = RequestMethod.GET)
	public String download_manage() {
		
		return "/casefile/download_manage";
	}

	@RequestMapping(value = "/case_stempreport", method = RequestMethod.GET)
	public String caseStempReport() {
		
		return "/casefile/case_stempreport";
		
		
		
	}
	
	
	@RequestMapping(value = "/getReportData/{id}", method = RequestMethod.GET)
	public @ResponseBody String getReportData(@PathVariable("id") Long fd_id,
			HttpSession session) {
		User u = (User) session.getAttribute("USER");
		String jsonData = null;
		ActionResponse<OrderReport> response = new ActionResponse<>();

	
		
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		Date currentDate = new Date(); 
		
		Date cl_date = new Date(); 
		
		String cl_date1 =simpleDateFormat.format(cl_date);
		
		try {
			cl_date =simpleDateFormat.parse(cl_date1);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		
		
		
		String currentDateString =simpleDateFormat.format(currentDate);
		Date d1 =null;
		
		try {
			 d1 =simpleDateFormat.parse(currentDateString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar c1 = Calendar.getInstance(); 
		c1.setTime(d1); 
		c1.add(Calendar.DATE, -1);
		
		d1 =c1.getTime();
		
		System.out.println("Previous Date"+d1);
		
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		
		dt = c.getTime();
		
		
		
		String nextdate = simpleDateFormat.format(dt);
		
		String time  =" 09:50:50";
		
		nextdate =nextdate+time;
		
		
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Date d2=null;
		try {
			 d2 =simpleDateFormat1.parse(nextdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<OrderReport> subDocuments = subDocumentService.getOrderReport1(fd_id,d1, d2,u.getUm_id());
		
		
		
		for(OrderReport sb:subDocuments) {
			
			Date reportDate =sb.getOrd_created();
			
			String reportDate1 =simpleDateFormat.format(reportDate);
			
			simpleDateFormat.format(new Date());
			Date currentDatewithZeroTimeStamp =null;
			try {
		 currentDatewithZeroTimeStamp =simpleDateFormat.parse(simpleDateFormat.format(new Date()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(sb.getOrd_created().compareTo(currentDatewithZeroTimeStamp) < 0) {
				// hence office report is created before the current date so we match for timing which is 9:50 am.
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
				dateFormat.format(date);
				System.out.println(dateFormat.format(date));

				try {
					if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("09:50")))
					{
					    System.out.println("Current time is greater than 09.50");
					    sb.setCl_flag(2);
					}else{
					    System.out.println("Current time is less than 9:50");
					    
					    Date cl_date2 =simpleDateFormat.parse(simpleDateFormat.format(sb.getOrd_created()));
					    
					    CauseList cl =causeListService.getByFDmidAndDate(sb.getOrd_fd_mid(), cl_date2 );
						if(cl != null) {
							
							 System.out.println("Case is Already Listed");
							 sb.setCl_flag(1);
							
							
							
							
							
							
						}
						else {
							Date date11 = new Date();
							SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm") ;
							dateFormat.format(date11);
							System.out.println(dateFormat.format(date11));

							try {
								if(dateFormat1.parse(dateFormat1.format(date11)).after(dateFormat1.parse("09:50")))
								{
								    System.out.println("Current time is greater than 09.50");
								    sb.setCl_flag(2);
								}else{
								    System.out.println("Current time is less than 9:50");
								 
								}
							} catch (ParseException e) {
							
								e.printStackTrace();
							}
							
						}
					  
					}
				} catch (ParseException e) {
				
					e.printStackTrace();
				}
				
			}
			else {
				
			
			
			CauseList cl =causeListService.getByFDmidAndDate(sb.getOrd_fd_mid(), cl_date );
			if(cl != null) {
				
				
				
				
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
				dateFormat.format(date);
				System.out.println(dateFormat.format(date));

				try {
					if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("09:50")))
					{
					    System.out.println("Current time is greater than 09.50");
					    sb.setCl_flag(1);
					}else{
					    System.out.println("Current time is less than 9:50");
					 
					}
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				
				
				
			}
		}
		}
		
		
		
		System.out.println("dateeeeee"+ dt);
		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd-MM-yyyy");
		
		
		
		response.setResponse("TRUE");
		response.setModelList(subDocuments);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
		
	
	}
	
	@RequestMapping(value = "/updatereportdata", method = RequestMethod.POST)
	public @ResponseBody String updatereportdata(@RequestBody OrderReport officeRpt,HttpSession session) throws DocumentException {
		ActionResponse<OrderReport> response = new ActionResponse<OrderReport>();
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		
		
	
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		Date currentDate = new Date(); 
		
		Date cl_date = new Date(); 
		
		String cl_date1 =simpleDateFormat.format(cl_date);
		
		try {
			cl_date =simpleDateFormat.parse(cl_date1);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		
		
		
		String currentDateString =simpleDateFormat.format(currentDate);
		Date d1 =null;
		
		try {
			 d1 =simpleDateFormat.parse(currentDateString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Calendar c1 = Calendar.getInstance(); 
		c1.setTime(d1); 
		c1.add(Calendar.DATE, -1);
		
		d1 =c1.getTime();
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		
		
		
		String nextdate = simpleDateFormat.format(dt);
		
		String time  =" 09:50:50";
		
		nextdate =nextdate+time;
		
		
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("dateeeeeeeeeeeeeeeeeeeoforderreprt"+nextdate);
		
		Date d2=null;
		try {
			 d2 =simpleDateFormat1.parse(nextdate);
			System.out.println("dateeeeeeeeeeeeeeeeeeeoforderreprt"+d2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OrderReport sb = orderReportService.getOrderReportById(officeRpt.getOrd_id(),d1, d2,u.getUm_id());
		
		if(sb != null) {
			
	Date reportDate =sb.getOrd_created();
			
			String reportDate1 =simpleDateFormat.format(reportDate);
			
			simpleDateFormat.format(new Date());
			Date currentDatewithZeroTimeStamp =null;
			try {
		 currentDatewithZeroTimeStamp =simpleDateFormat.parse(simpleDateFormat.format(new Date()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(sb.getOrd_created().compareTo(currentDatewithZeroTimeStamp) < 0) {
				// hence office report is created before the current date so we match for timing which is 10 am.
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
				dateFormat.format(date);
				System.out.println(dateFormat.format(date));

				try {
					if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("09:50")))
					{
					    System.out.println("Current time is greater than 09.50");
					    sb.setCl_flag(2);
					    response.setResponse("Not Allowed");
					}else{
					    System.out.println("Current time is less than 9:50");
					    
					    Date cl_date2 =simpleDateFormat.parse(simpleDateFormat.format(sb.getOrd_created()));
					    
					    CauseList cl =causeListService.getByFDmidAndDate(sb.getOrd_fd_mid(), cl_date2 );
						if(cl != null) {
							 response.setResponse("Listed");
							 System.out.println("Case is Already Listed");
							 sb.setCl_flag(1);
							
							
							
							
							
							
						}
						else {
							Date date11 = new Date();
							SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm") ;
							dateFormat.format(date11);
							System.out.println(dateFormat.format(date11));

							try {
								if(dateFormat1.parse(dateFormat1.format(date11)).after(dateFormat1.parse("09:50")))
								{
								    System.out.println("Current time is greater than 09.50");
								    response.setResponse("Not Allowed");
								    
								    // 2 for not allowed and 1 for listed in court
								    officeRpt.setCl_flag(2);
								}else{
								    System.out.println("Current time is less than 9:50");
								   
								  
									
								    officeRpt.setOrd_mod_date(new Date());
									officeRpt.setOrd_mod_by(u.getUm_id());
									response.setModelData(orderReportService.save(officeRpt));
									response.setResponse("Update");
								}
							} catch (ParseException e) {
							
								e.printStackTrace();
							}
							
						}
					  
					}
				} catch (ParseException e) {
				
					e.printStackTrace();
				}
				
			}
			else {
				
			
			
			CauseList cl =causeListService.getByFDmidAndDate(sb.getOrd_fd_mid(), cl_date );
			if(cl != null) {
				
				
				
				
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
				dateFormat.format(date);
				System.out.println(dateFormat.format(date));

				try {
					if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("09:50")))
					{
						response.setResponse("Listed");
						sb.setCl_flag(2);
					    System.out.println("Current time is greater than 09.50");
					}else{
					    System.out.println("Current time is less than 9:50");
					   // sb.setCl_flag(true);
			
					    officeRpt.setOrd_mod_date(new Date());
						officeRpt.setOrd_mod_by(u.getUm_id());
						response.setModelData(orderReportService.save(officeRpt));
						response.setResponse("Update");
					}
				} catch (ParseException e) {
					
					
					e.printStackTrace();
				}
				
				
				
			}
			else {
				
				officeRpt.setOrd_mod_date(new Date());
				officeRpt.setOrd_mod_by(u.getUm_id());
				response.setModelData(orderReportService.save(officeRpt));
				response.setResponse("Update");
				
				
			}
		}
		
		
			
		/*	CauseList cl =causeListService.getByFDmidAndDate(subDocuments.getOrd_fd_mid(), cl_date );
			if(cl != null) {
				
				
				
				
				
				Date date = new Date() ;
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
				dateFormat.format(date);
				System.out.println(dateFormat.format(date));

				try {
					if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("09:50")))
					{
					    System.out.println("Current time is greater than 09.50");
					    response.setResponse("Listed");
					}else{
					    System.out.println("Current time is less than 9:50");
					    subDocuments.setCl_flag(true);
					    officeRpt.setOrd_mod_date(new Date());
						officeRpt.setOrd_mod_by(u.getUm_id());
						response.setModelData(orderReportService.save(officeRpt));
						response.setResponse("Update");
					}
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				
				
				
			}
			else {
				officeRpt.setOrd_mod_date(new Date());
				officeRpt.setOrd_mod_by(u.getUm_id());
				response.setModelData(orderReportService.save(officeRpt));
				response.setResponse("Update");
			}*/
			
	}
		else {
			response.setResponse("Not Allowed");
		}
		
		
		
		/*officeRpt.setOrd_mod_date(new Date());
		officeRpt.setOrd_mod_by(u.getUm_id());
		response.setModelData(orderReportService.save(officeRpt));*/
		
		/*l*/
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
	@RequestMapping(value = "/updatereportdatafile", method = RequestMethod.POST)
	public @ResponseBody String updatereportdatafile(@RequestPart(value = "officeRpt") OrderReport officeRpt, @RequestPart("file") MultipartFile file ,HttpSession session) throws DocumentException {
		ActionResponse<OrderReport> response = new ActionResponse<OrderReport>();
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		
		
		MultipartFile mpf = file;
		System.out.println("Thier is no file to update");
		if(mpf.isEmpty()) {
			System.out.println("Thier is no file to update");
		}
		
		else {
			System.out.println("File Existsssssssssss");
		}
		
		
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		Date currentDate = new Date(); 
		
		Date cl_date = new Date(); 
		
		String cl_date1 =simpleDateFormat.format(cl_date);
		
		try {
			cl_date =simpleDateFormat.parse(cl_date1);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		
		
		
		String currentDateString =simpleDateFormat.format(currentDate);
		Date d1 =null;
		
		try {
			 d1 =simpleDateFormat.parse(currentDateString);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Calendar c1 = Calendar.getInstance(); 
		c1.setTime(d1); 
		c1.add(Calendar.DATE, -1);
		
		d1 =c1.getTime();
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		
		
		
		String nextdate = simpleDateFormat.format(dt);
		
		String time  =" 09:50:50";
		
		nextdate =nextdate+time;
		
		
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("dateeeeeeeeeeeeeeeeeeeoforderreprt"+nextdate);
		
		Date d2=null;
		try {
			 d2 =simpleDateFormat1.parse(nextdate);
			System.out.println("dateeeeeeeeeeeeeeeeeeeoforderreprt"+d2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OrderReport sb = orderReportService.getOrderReportById(officeRpt.getOrd_id(),d1, d2,u.getUm_id());
		
		if(sb != null) {
			
	Date reportDate =sb.getOrd_created();
			
			String reportDate1 =simpleDateFormat.format(reportDate);
			
			simpleDateFormat.format(new Date());
			Date currentDatewithZeroTimeStamp =null;
			try {
		 currentDatewithZeroTimeStamp =simpleDateFormat.parse(simpleDateFormat.format(new Date()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(sb.getOrd_created().compareTo(currentDatewithZeroTimeStamp) < 0) {
				// hence office report is created before the current date so we match for timing which is 10 am.
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
				dateFormat.format(date);
				System.out.println(dateFormat.format(date));

				try {
					if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("09:50")))
					{
					    System.out.println("Current time is greater than 09.50");
					    sb.setCl_flag(2);
					    response.setResponse("Not Allowed");
					}else{
					    System.out.println("Current time is less than 9:50");
					    
					    Date cl_date2 =simpleDateFormat.parse(simpleDateFormat.format(sb.getOrd_created()));
					    
					    CauseList cl =causeListService.getByFDmidAndDate(sb.getOrd_fd_mid(), cl_date2 );
						if(cl != null) {
							 response.setResponse("Listed");
							 System.out.println("Case is Already Listed");
							 sb.setCl_flag(1);
							
							
							
							
							
							
						}
						else {
							Date date11 = new Date();
							SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm") ;
							dateFormat.format(date11);
							System.out.println(dateFormat.format(date11));

							try {
								if(dateFormat1.parse(dateFormat1.format(date11)).after(dateFormat1.parse("09:50")))
								{
								    System.out.println("Current time is greater than 09.50");
								    response.setResponse("Not Allowed");
								    
								    // 2 for not allowed and 1 for listed in court
								    officeRpt.setCl_flag(2);
								}else{
								    System.out.println("Current time is less than 9:50");
								   
								    // for replacing files if exists
								    
								 //   SubDocument sd = subDocumentService.getByPK(officeRpt);
									String docname = officeRpt.getSubDocument().getSd_document_name();
									Lookup lookup = lookupService.getLookUpObject("REPOSITORYPATH");
									
									CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(officeRpt.getSubDocument().getSd_fd_mid());
								    File newFile = new File(lookup.getLk_longname() + File.separator
											+ caseFileDetail.getCaseType().getCt_label()
											+ File.separator + officeRpt.getSubDocument().getIndexField().getIf_name() + File.separator+docname+".pdf");
									try {
										FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFile));
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									///
									
								    officeRpt.setOrd_mod_date(new Date());
									officeRpt.setOrd_mod_by(u.getUm_id());
									response.setModelData(orderReportService.save(officeRpt));
									response.setResponse("Update");
								}
							} catch (ParseException e) {
							
								e.printStackTrace();
							}
							
						}
					  
					}
				} catch (ParseException e) {
				
					e.printStackTrace();
				}
				
			}
			else {
				
			
			
			CauseList cl =causeListService.getByFDmidAndDate(sb.getOrd_fd_mid(), cl_date );
			if(cl != null) {
				
				
				
				
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
				dateFormat.format(date);
				System.out.println(dateFormat.format(date));

				try {
					if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("09:50")))
					{
						response.setResponse("Listed");
						sb.setCl_flag(2);
					    System.out.println("Current time is greater than 09.50");
					}else{
					    System.out.println("Current time is less than 9:50");
					   // sb.setCl_flag(true);
					    
					    String docname = officeRpt.getSubDocument().getSd_document_name();
						Lookup lookup = lookupService.getLookUpObject("REPOSITORYPATH");
						
						CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(officeRpt.getSubDocument().getSd_fd_mid());
					    File newFile = new File(lookup.getLk_longname() + File.separator
								+ caseFileDetail.getCaseType().getCt_label()
								+ File.separator + officeRpt.getSubDocument().getIndexField().getIf_name() + File.separator+docname+".pdf");
						try {
							FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFile));
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			
					    officeRpt.setOrd_mod_date(new Date());
						officeRpt.setOrd_mod_by(u.getUm_id());
						response.setModelData(orderReportService.save(officeRpt));
						response.setResponse("Update");
					}
				} catch (ParseException e) {
					
					
					e.printStackTrace();
				}
				
				
				
			}
			else {
				String docname = officeRpt.getSubDocument().getSd_document_name();
				Lookup lookup = lookupService.getLookUpObject("REPOSITORYPATH");
				
				CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(officeRpt.getSubDocument().getSd_fd_mid());
			    File newFile = new File(lookup.getLk_longname() + File.separator
						+ caseFileDetail.getCaseType().getCt_label()
						+ File.separator + officeRpt.getSubDocument().getIndexField().getIf_name() + File.separator+docname+".pdf");
				try {
					FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFile));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				officeRpt.setOrd_mod_date(new Date());
				officeRpt.setOrd_mod_by(u.getUm_id());
				response.setModelData(orderReportService.save(officeRpt));
				response.setResponse("Update");
				
				
			}
		}
		
		
			
		/*	CauseList cl =causeListService.getByFDmidAndDate(subDocuments.getOrd_fd_mid(), cl_date );
			if(cl != null) {
				
				
				
				
				
				Date date = new Date() ;
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
				dateFormat.format(date);
				System.out.println(dateFormat.format(date));

				try {
					if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("09:50")))
					{
					    System.out.println("Current time is greater than 09.50");
					    response.setResponse("Listed");
					}else{
					    System.out.println("Current time is less than 9:50");
					    subDocuments.setCl_flag(true);
					    officeRpt.setOrd_mod_date(new Date());
						officeRpt.setOrd_mod_by(u.getUm_id());
						response.setModelData(orderReportService.save(officeRpt));
						response.setResponse("Update");
					}
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				
				
				
			}
			else {
				officeRpt.setOrd_mod_date(new Date());
				officeRpt.setOrd_mod_by(u.getUm_id());
				response.setModelData(orderReportService.save(officeRpt));
				response.setResponse("Update");
			}*/
			
	}
		else {
			response.setResponse("Not Allowed");
		}
		
		
		
		/*officeRpt.setOrd_mod_date(new Date());
		officeRpt.setOrd_mod_by(u.getUm_id());
		response.setModelData(orderReportService.save(officeRpt));*/
		
		/*l*/
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
	
	@RequestMapping(value = "/viewdetail/{id}", method = RequestMethod.GET)
	public String viewDetail(@PathVariable("id") Long docId, Model model) {
		model.addAttribute("doc_id", docId);
		return "/casefile/viewdetail";
	}
	
	@RequestMapping(value = "/getApplicationWithPetition/{id}", method = RequestMethod.GET)
	public  @ResponseBody String getApplicationWithPetition(@PathVariable("id") Long docId) {
		
		String jsonData = null;
		ActionResponse<ApplicationWithPetition> response = new ActionResponse<>();
		List<ApplicationWithPetition>  awp =caseFileDetailService.getApplicationWithPetiton(docId);
		
		if(awp != null) {
			response.setResponse("TRUE");
			response.setModelList(awp);

			jsonData = globalfunction.convert_to_json(response);

			return jsonData;
		}
		else {
			response.setResponse("TRUE");
		}
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "/downloadlist/{id}", method = RequestMethod.GET)
	public String downloadlist(@PathVariable("id") Long docId, Model model) {
		model.addAttribute("doc_id", docId);
		return "/casefile/downloadlist";
	}

	@RequestMapping(value = "/showfile/{id}", method = RequestMethod.GET)
	public String showfile(@PathVariable("id") Long docId, Model model) {
		SubDocument subDocument = subDocumentService.getByPK(docId);
		String returnview = "/casefile/showfile";
		if (subDocument == null) {
			returnview = "/casefile/notfound";
		} else {

			CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(subDocument.getSd_fd_mid());
			model.addAttribute("document_name",subDocument.getSd_document_name());
			System.out.println("filename=" + subDocument.getSd_document_name());
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + subDocument.getIndexField().getIf_name()
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf";

			File source = new File(srcPath);

			String uploadPath = context.getRealPath("");
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf");

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return returnview;
	}
	
	

	@RequestMapping(value = "/changeApplicationStatus", method = RequestMethod.POST)
	public @ResponseBody String changeApplicationStatus(
			@RequestBody SubDocument sb, HttpSession session) {
		
		User u = (User) session.getAttribute("USER");
		String jsonData = null;
		ActionResponse<SubDocument> response = new ActionResponse<>();
		
		
		SubDocumentApplicationStatus sdas =new SubDocumentApplicationStatus();
		
		sdas.setSdas_date(new Date());
		sdas.setSdas_mod_by(u.getUm_id());
		sdas.setSdas_sd_id(sb.getSd_id());
		sdas.setSdas_fd_id(sb.getSd_fd_mid());
		sdas.setSdas_status(sb.getSd_application_status());
		
		
		subDocumentService.saveSubDocumentApplicationStatus(sdas);
		
		subDocumentService.save(sb);
		
		
		response.setResponse("TRUE");
		

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	
	
	@RequestMapping(value = "/getCaseFileList", method = RequestMethod.POST)
	public @ResponseBody String getCaseList(
			@RequestBody CaseFileDetail casefile, HttpSession session) {
		String jsonData = null;
		ActionResponse<CaseFileDetail> response = new ActionResponse<>();
		List<CaseFileDetail> casefileList = caseFileDetailService.getCaseFiles(casefile);
		ActionResponse< com.pdms.model.CaseFileDetail> response1 = new ActionResponse<>();
		 com.pdms.model.CaseFileDetail cfdPdms=null;
		
		if(casefileList.size()==0) {
			String encodedText;
			 String notEncoded = "admin:785213";

		      // Base64
		      encodedText = new String(Base64.encodeBase64(notEncoded.getBytes()));
		      System.out.println("Encoded: " + encodedText);
		      System.out.println("Decoded:"
		          + new String(Base64.decodeBase64(encodedText.getBytes())));
			 
			
			 
			    String encodedAuth = "Basic " + encodedText;
			    
			    
			RestTemplate restTemp=new RestTemplate();
			 
			// create headers
			
			
			 HttpHeaders headers = new HttpHeaders();
			 headers.add("Authorization",encodedAuth);
			 headers.setContentType(MediaType.APPLICATION_JSON);
			 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			 
			 
			 
			
			 
			
			
			 
			String result=null;
			 
			 String url="http://192.168.0.178/dms-rest-api/rest/findCaseFile/BAIL/37124/2015/A";
			
			 try {
				 
				 
			        HttpEntity<String> entity = new HttpEntity<String>(headers);
			        ResponseEntity< com.pdms.model.CaseFileDetail> res = restTemp.exchange(url, HttpMethod.GET, entity,  com.pdms.model.CaseFileDetail.class);
			        cfdPdms=res.getBody();
			 }
			 catch (Exception e) {
				result="api fail";
			}
			
		}
		if(casefileList.size()!=0) {
			response.setResponse("TRUE");
			response.setModelList(casefileList);
			jsonData = globalfunction.convert_to_json(response);
		}
		if(cfdPdms !=null) {
			response1.setResponse("TRUE");
			response1.setModelData(cfdPdms);;
			jsonData = globalfunction.convert_to_json(response1);
		}
		/*response.setResponse("TRUE");
		response.setModelList(casefileList);*/

	

		return jsonData;
	}
	
	
	@RequestMapping(value = "/updateAppWithPetition", method = RequestMethod.POST)
	public @ResponseBody String updateAppWithPetition(
			@RequestBody ApplicationWithPetition awp, HttpSession session) {
		String jsonData = null;
		ActionResponse<ApplicationWithPetition> response = new ActionResponse<>();
	
		ApplicationWithPetition awp1 =caseFileDetailService.save(awp);
	if(awp1 != null) {
		response.setResponse("TRUE");
		response.setModelData(awp1);
	}
	else {
		response.setResponse("FALSE");
		
	}
		
	

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	
	@RequestMapping(value = "/deleteAppWithPetition", method = RequestMethod.POST)
	public @ResponseBody String deleteAppWithPetition(
			@RequestBody ApplicationWithPetition awp, HttpSession session) {
		String jsonData = null;
		ActionResponse<ApplicationWithPetition> response = new ActionResponse<>();
	
		Long id =awp.getAwp_id();
		boolean  deleteflag =caseFileDetailService.deleteApplicationWithPetition(id);
	if(deleteflag) {
		response.setResponse("TRUE");
		
	}
	else {
		response.setResponse("FALSE");
		
	}
		
	

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	

	// add new case file details in efiling database
	// created by Afnan
	@RequestMapping(value = "/addCaseEfling", method = RequestMethod.POST)
	@ResponseBody
	public String addCaseEfling(@RequestBody CaseFileDetail cfd,HttpSession session) {

		ActionResponse<CaseFileDetail> response = new ActionResponse<CaseFileDetail>();
		String jsonData = null;
		Long caseType =cfd.getFd_case_type();
		String caseNo= cfd.getFd_case_no();
		Integer year = cfd.getFd_case_year();
		Long caseYear= new Long(year);
		
		EfilingCaseFileDetail efilingcfd = amendmentService.getEilingCaseFileDetails(caseYear, caseType, caseNo);	
		if (efilingcfd!= null) 
		{
		/*	efilingcfd.setFd_first_petitioner(cfd.getFirst_petitioner());
			efilingcfd.setFd_first_respondent(cfd.getFirst_respondent());
			addcaseEfilingService.addCaseEfling(efilingcfd);*/
			response.setData("Case already exist in Efiling");
			response.setResponse("TRUE");
		}
		else 
		{
			EfilingCaseFileDetail ecfd = new EfilingCaseFileDetail();
			ecfd.setFd_case_type(cfd.getFd_case_type());
			ecfd.setFd_case_no(cfd.getFd_case_no());
			ecfd.setFd_case_year(cfd.getFd_case_year());
			ecfd.setFd_first_petitioner(cfd.getFirst_petitioner());
			ecfd.setFd_first_respondent(cfd.getFirst_respondent());
			ecfd.setFd_cr_date(new Date());
			ecfd.setFd_rec_status(1);
			response.setData("Case added to Efiling Successfully");
			response.setResponse("TRUE");
			addcaseEfilingService.saveEfiling(ecfd);
		}
		if (response != null)
			jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	
	
	
	@RequestMapping(value = "/getCaseFileLists", method = RequestMethod.POST)
	public @ResponseBody String getCaseLists(
			@RequestBody CaseFileDetail casefile, HttpSession session) {
		String jsonData = null;
		ActionResponse<CaseFileDetail> response = new ActionResponse<>();

		CaseFileDetail casefileList = caseFileDetailService.getCaseFile(casefile);
		if(casefileList!=null) {
		response.setResponse("TRUE");
		}
		else {
			response.setResponse("FALSE");
		}
		response.setModelData(casefileList);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	} 

	@RequestMapping(value = "/updateOfficeReportData", method = RequestMethod.POST)
	public @ResponseBody String updateOfficeReportData(
			@RequestPart(value = "subDocument") OrderReportSub sb, @RequestPart("file") MultipartFile file ,HttpSession session)
			throws DocumentException {
		
		System.out.println("SubDocumnet Dataaaaaaaaaaa-----------"+sb);
		ActionResponse<SubDocument> response = new ActionResponse<SubDocument>();
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		Lookup lookup = lookupService.getLookUpObject("REPOSITORYPATH");
	//	Long caseFileId = Long.parseLong(request.getParameter("sd_fd_mid"), 10);
		Long caseFileId =sb.getSd_fd_mid();
		
		
		//Long indexFieldId = Long.parseLong(request.getParameter("if_id"), 10);
		
		Long indexFieldId = sb.getIf_id();
		Integer at_id=null;
		if(indexFieldId!=43 && indexFieldId!=10 && indexFieldId!=19 ) {
		//at_id = Integer.parseInt(request.getParameter("at_id"), 10);
		
		at_id = sb.getAt_id();
		}
		//String ord_remark = request.getParameter("ord_remark");
		String ord_remark  =sb.getOrd_remark();
		
		
		//String order_date = request.getParameter("sd_submitted_date");
		String order_date = sb.getSd_submitted_date();
		//String consignmentsno =request.getParameter("ord_consignment_no");
		String consignmentsno =sb.getOrd_consignment_no();
		
		
		Integer sd_document_no = null;
		Integer sd_document_year = null;
		try {
			if(sb.getSd_document_no() != null) {
			sd_document_no = sb.getSd_document_no();
			}
			
			if(sb.getSd_document_year() != null) {
		sd_document_year = sb.getSd_document_year();
			}
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Date orderDate = new Date();

		try {
			orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(order_date);

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MultipartFile mpf = file;
		//Iterator<String> itr = request.getFileNames();
		String newfilepath = "";
		CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(caseFileId);
		IndexField indexField = masterService.getIndexField(indexFieldId);
		Integer count = subDocumentService.getCount(caseFileId);
		count = count + 1;
		//while (itr.hasNext()) {
			//mpf = request.getFile(itr.next());
			String filename = caseFileDetail.getFd_document_name() + "_"
					+ indexField.getIf_type_code() + "_" + count;
			newfilepath = lookup.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + indexField.getIf_name() + File.separator
					+ filename + ".pdf";
			SubDocument subDocument = new SubDocument();
			subDocument.setSd_cr_by(u.getUm_id());
			subDocument.setSd_cr_date(new Date());
			subDocument.setSd_fd_mid(caseFileId);
			subDocument.setSd_if_mid(indexFieldId);
			subDocument.setSd_version(1);
			subDocument.setSd_document_name(filename);
			subDocument.setSd_document_id(at_id);
			subDocument.setSd_submitted_date(orderDate);
			subDocument.setSd_rec_status(1);
			subDocument.setSd_minor_sequence(count);
			subDocument.setSd_document_no(sd_document_no);
			subDocument.setSd_document_year(sd_document_year);
			try {
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
						newfilepath));
				File source = new File(newfilepath);
				PdfReader reader = new PdfReader(source.getAbsolutePath());
				Integer no_of_pages = reader.getNumberOfPages();
				subDocument.setSd_no_of_pages(no_of_pages);

				subDocument = subDocumentService.save(subDocument);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(indexFieldId!=43 && indexFieldId!=10 && indexFieldId!=19 ) {
				if (at_id == 100003)
				{
					OrderReport or = new OrderReport();
					if (orderDate!=null)
					{	
						or.setOrd_created(orderDate);
					}
					else
					{
						or.setOrd_created(new Date());
					}
					or.setOrd_remark(ord_remark);
					or.setOrd_fd_mid(caseFileId);
					or.setOrd_sd_mid(subDocument.getSd_id());
					or.setOrd_created_by(u.getUm_id());
					int sts = 1;
					or.setOrd_rec_status(sts);
					or.setOrd_remark(ord_remark);
					or.setOrd_submitted_date(new Date());
					if(consignmentsno!=null)
					{
						or.setOrd_consignment_no(consignmentsno.toUpperCase());
					}
					orderReportService.save(or);
				}
			}
			
		//}

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	@RequestMapping(value = "/uploadJudgement", method = RequestMethod.POST)
	public @ResponseBody String uploadJudgement(
			@RequestPart(value = "subDocument") OrderReportSub sb, @RequestPart("file") MultipartFile file ,HttpSession session)
			throws DocumentException {
		
		System.out.println("SubDocumnet Dataaaaaaaaaaa-----------"+sb);
		ActionResponse<SubDocument> response = new ActionResponse<SubDocument>();
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		Lookup lookup = lookupService.getLookUpObject("REPOSITORYPATH");
	//	Long caseFileId = Long.parseLong(request.getParameter("sd_fd_mid"), 10);
		Long caseFileId =sb.getSd_fd_mid();
		
		
		//Long indexFieldId = Long.parseLong(request.getParameter("if_id"), 10);
		
		Long indexFieldId = sb.getIf_id();
		Integer at_id=null;
		if(indexFieldId!=43 && indexFieldId!=10 && indexFieldId!=19 ) {
		//at_id = Integer.parseInt(request.getParameter("at_id"), 10);
		
		at_id = sb.getAt_id();
		}
		//String ord_remark = request.getParameter("ord_remark");
		String ord_remark  =sb.getOrd_remark();
		
		
		//String order_date = request.getParameter("sd_submitted_date");
		String order_date = sb.getSd_submitted_date();
		//String consignmentsno =request.getParameter("ord_consignment_no");
		String consignmentsno =sb.getOrd_consignment_no();
		
		
		Integer sd_document_no = null;
		Integer sd_document_year = null;
		try {
			if(sb.getSd_document_no() != null) {
			sd_document_no = sb.getSd_document_no();
			}
			
			if(sb.getSd_document_year() != null) {
		sd_document_year = sb.getSd_document_year();
			}
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Date orderDate = new Date();

		try {
			orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(order_date);

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MultipartFile mpf = file;
		//Iterator<String> itr = request.getFileNames();
		String newfilepath = "";
		CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(caseFileId);
		IndexField indexField = masterService.getIndexField(indexFieldId);
		Integer count = subDocumentService.getCount(caseFileId);
		count = count + 1;
		//while (itr.hasNext()) {
			//mpf = request.getFile(itr.next());
			String filename = caseFileDetail.getFd_document_name() + "_"
					+ indexField.getIf_type_code() + "_" + count;
			newfilepath = lookup.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + indexField.getIf_name() + File.separator
					+ filename + ".pdf";
			SubDocument subDocument = new SubDocument();
			subDocument.setSd_cr_by(u.getUm_id());
			subDocument.setSd_cr_date(new Date());
			subDocument.setSd_fd_mid(caseFileId);
			subDocument.setSd_if_mid(indexFieldId);
			subDocument.setSd_version(1);
			subDocument.setSd_document_name(filename);
			subDocument.setSd_document_id(at_id);
			subDocument.setSd_submitted_date(orderDate);
			subDocument.setSd_rec_status(1);
			subDocument.setSd_minor_sequence(count);
			subDocument.setSd_document_no(sd_document_no);
			subDocument.setSd_document_year(sd_document_year);
        if(indexFieldId==654321) {
        	subDocument.setSd_if_mid(1L);
        	subDocument.setSd_rec_status(8);
			}
			try {
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
						newfilepath));
				File source = new File(newfilepath);
				PdfReader reader = new PdfReader(source.getAbsolutePath());
				Integer no_of_pages = reader.getNumberOfPages();
				subDocument.setSd_no_of_pages(no_of_pages);

				subDocument = subDocumentService.save(subDocument);
				for(SubApplication sba : sb.getSubApplications()) {
					sba.setSb_ap_sd_mid(subDocument.getSd_id());
					subDocumentService.saveSA(sba);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			if(indexFieldId!=43 && indexFieldId!=10 && indexFieldId!=19 ) {
				if (at_id == 100003)
				{
					OrderReport or = new OrderReport();
					if (orderDate!=null)
					{	
						or.setOrd_created(orderDate);
					}
					else
					{
						or.setOrd_created(new Date());
					}
					or.setOrd_remark(ord_remark);
					or.setOrd_fd_mid(caseFileId);
					or.setOrd_sd_mid(subDocument.getSd_id());
					or.setOrd_created_by(u.getUm_id());
					int sts = 1;
					or.setOrd_rec_status(sts);
					or.setOrd_remark(ord_remark);
					or.setOrd_submitted_date(new Date());
					if(consignmentsno!=null)
					{
						or.setOrd_consignment_no(consignmentsno.toUpperCase());
					}
					orderReportService.save(or);
				}
				/*if (at_id==1000023)
				{
					OrderReport or = new OrderReport();
					if (orderDate!=null)
					{	
						or.setOrd_created(orderDate);
					}
					else
					{
						or.setOrd_created(new Date());
					}
					or.setOrd_remark(ord_remark);
					or.setOrd_fd_mid(caseFileId);
					or.setOrd_sd_mid(subDocument.getSd_id());
					or.setOrd_created_by(u.getUm_id());
					int sts = 1;
					or.setOrd_rec_status(sts);
					or.setOrd_remark(ord_remark);
					or.setOrd_submitted_date(new Date());
					if(consignmentsno!=null)
					{
						or.setOrd_consignment_no(consignmentsno.toUpperCase());
					}
					orderReportService.save(or);
				}*/
			}
			
		//}

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	
/*	@RequestMapping(value = "/uploadJudgement", method = RequestMethod.POST)
	public @ResponseBody String uploadJudgement(
			MultipartHttpServletRequest request, HttpSession session)
			throws DocumentException {
		ActionResponse<SubDocument> response = new ActionResponse<SubDocument>();
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		Lookup lookup = lookupService.getLookUpObject("REPOSITORYPATH");
		Long caseFileId = Long.parseLong(request.getParameter("sd_fd_mid"), 10);
		Long indexFieldId = Long.parseLong(request.getParameter("if_id"), 10);
		Integer at_id=null;
		if(indexFieldId!=43 && indexFieldId!=10 && indexFieldId!=19 ) {
		at_id = Integer.parseInt(request.getParameter("at_id"), 10);
		}
		String ord_remark = request.getParameter("ord_remark");
		String order_date = request.getParameter("sd_submitted_date");
		String consignmentsno =request.getParameter("ord_consignment_no");
		Integer sd_document_no = null;
		Integer sd_document_year = null;
		try {
			sd_document_no = Integer.parseInt(request.getParameter("sd_document_no"));
			sd_document_year = Integer.parseInt(request.getParameter("sd_document_year"));
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Date orderDate = new Date();

		try {
			orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(order_date);

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MultipartFile mpf = null;
		Iterator<String> itr = request.getFileNames();
		String newfilepath = "";
		CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(caseFileId);
		IndexField indexField = masterService.getIndexField(indexFieldId);
		Integer count = subDocumentService.getCount(caseFileId);
		count = count + 1;
		while (itr.hasNext()) {
			mpf = request.getFile(itr.next());
			String filename = caseFileDetail.getFd_document_name() + "_"
					+ indexField.getIf_type_code() + "_" + count;
			newfilepath = lookup.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + indexField.getIf_name() + File.separator
					+ filename + ".pdf";
			SubDocument subDocument = new SubDocument();
			subDocument.setSd_cr_by(u.getUm_id());
			subDocument.setSd_cr_date(new Date());
			subDocument.setSd_fd_mid(caseFileId);
			subDocument.setSd_if_mid(indexFieldId);
			subDocument.setSd_version(1);
			subDocument.setSd_document_name(filename);
			subDocument.setSd_document_id(at_id);
			subDocument.setSd_submitted_date(orderDate);
			subDocument.setSd_rec_status(1);
			subDocument.setSd_minor_sequence(count);
			subDocument.setSd_document_no(sd_document_no);
			subDocument.setSd_document_year(sd_document_year);
			try {
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
						newfilepath));
				File source = new File(newfilepath);
				PdfReader reader = new PdfReader(source.getAbsolutePath());
				Integer no_of_pages = reader.getNumberOfPages();
				subDocument.setSd_no_of_pages(no_of_pages);

				subDocument = subDocumentService.save(subDocument);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(indexFieldId!=43 && indexFieldId!=10 && indexFieldId!=19 ) {
				if (at_id == 100003)
				{
					OrderReport or = new OrderReport();
					if (orderDate!=null)
					{	
						or.setOrd_created(orderDate);
					}
					else
					{
						or.setOrd_created(new Date());
					}
					or.setOrd_remark(ord_remark);
					or.setOrd_fd_mid(caseFileId);
					or.setOrd_sd_mid(subDocument.getSd_id());
					or.setOrd_created_by(u.getUm_id());
					int sts = 1;
					or.setOrd_rec_status(sts);
					or.setOrd_remark(ord_remark);
					or.setOrd_submitted_date(new Date());
					if(consignmentsno!=null)
					{
						or.setOrd_consignment_no(consignmentsno.toUpperCase());
					}
					orderReportService.save(or);
				}
			}
			
		}

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	*/
	
	@RequestMapping(value = "/addreportdata", method = RequestMethod.POST)
	public @ResponseBody String addReportData(@RequestBody OrderReportSub sb,HttpSession session) throws DocumentException {
		ActionResponse<SubDocument> response = new ActionResponse<SubDocument>();
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		Long caseFileId = sb.getSd_fd_mid();
		Long indexFieldId = 39L;

		String ord_remark = sb.getOrd_remark();
		System.out.println("ord_remark----"+ord_remark);
		String consigmtno =null;
		String date = sb.getSd_submitted_date();
		if(sb.getOrd_consignment_no() != null) {
 consigmtno =sb.getOrd_consignment_no().toUpperCase();
		}
		Date d1 =null;
		try {
			d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (indexFieldId.longValue() == 39L) {
			OrderReport or = new OrderReport();
			if (d1!=null)
			{	
				or.setOrd_created(d1);
			}
			else
			{	
				Date d2 = new Date();                          
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");          
	            try {
					d2 = dateFormat.parse(d2.toString());
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				
				or.setOrd_created(d2);
			}
			or.setOrd_fd_mid(caseFileId);
			or.setOrd_created_by(u.getUm_id());
			or.setOrd_rec_status(1);
			or.setOrd_submitted_date(new Date());
			if(consigmtno!=null && !consigmtno.equals("UNDEFINED"))
			{
				or.setOrd_consignment_no(consigmtno);
			}
			if(ord_remark.equals("UNDEFINED"))
			{}
			else
			{
				or.setOrd_remark(ord_remark);
			}
			orderReportService.save(or);

		}
		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	
	@RequestMapping(value = "/addAppWithPetition", method = RequestMethod.POST)
	public @ResponseBody String addAppWithPetition(HttpServletRequest request,HttpSession session)
	{
		ActionResponse<ApplicationWithPetition> response = new ActionResponse<ApplicationWithPetition>();
		ApplicationWithPetition awp = new ApplicationWithPetition();
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		Long at_id = Long.parseLong(request.getParameter("at_id"));
		Long fd_id = Long.parseLong(request.getParameter("fd_id"));
		Long sd_id = Long.parseLong(request.getParameter("awp_sd_mid"));
		Long app_no =  Long.parseLong(request.getParameter("awp_ap_no"));
		Integer app_year =  Integer.parseInt(request.getParameter("awp_ap_year"));
		
		Date date = new Date();                          
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");          
        try {
			date = dateFormat.parse(date.toString());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		awp.setAwp_sd_mid(sd_id);
		awp.setAwp_ap_no(app_no);
		awp.setAwp_ap_year(app_year);
		awp.setAwp_ap_at_mid(at_id);
		awp.setAwp_rec_status(1);
		awp.setAwp_cr_date(date);
		awp.setAwp_submitted_by(u.getUm_id());
		awp.setAwp_fd_mid(fd_id);
		
		 ApplicationWithPetition applicationWithPetition = caseFileDetailService.save(awp);
		if(applicationWithPetition==null)
		{
			response.setResponse("FALSE");	
		}
		else
		{
			response.setResponse("TRUE");
		}
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
	
	@RequestMapping(value = "/downloadfiles/{id}", method = RequestMethod.GET)
	public void downloadCaseFile(@PathVariable("id") Long caseFileId,
			HttpServletResponse response, HttpSession session) {
		List<SubDocument> subDocuments = subDocumentService
				.getAllSubDocuments(caseFileId);
		Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
		Lookup lookUpZip = lookupService.getLookUpObject("ZIP_DOWNLOAD_PATH");

		CaseFileDetail caseFileDetail = caseFileDetailService
				.getCaseFileDetail(caseFileId);
		String dest_folder = lookUpZip.getLk_longname() + File.separator
				+ caseFileDetail.getCaseType().getCt_label()
				+ caseFileDetail.getFd_case_no()
				+ caseFileDetail.getFd_case_year();
		File destFolder = new File(dest_folder);

		if (!destFolder.exists()) {
			destFolder.mkdir();
		}
		for (SubDocument subDocument : subDocuments) {
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + subDocument.getIndexField().getIf_name()
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf";
			String destPath = dest_folder + File.separator
					+ subDocument.getSd_document_name() + ".pdf";
			File source = new File(srcPath);
			File dest = new File(destPath);
			try {
				FileUtils.copyFile(source.getAbsoluteFile(),
						dest.getAbsoluteFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			globalfunction.zipFolder(destFolder.getAbsolutePath(),
					destFolder.getAbsolutePath() + ".zip");
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
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ filename + "\"");

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

	/*@RequestMapping(value = "/vieworders/{id}", method = RequestMethod.GET)
	public String viewOrders(@PathVariable("id") Long docId, Model model) {
		Long metafieldId = 39L;
		List<SubDocument> subDocuments = subDocumentService.getByField(docId,
				metafieldId);
		String returnview = "/casefile/view";
		if (subDocuments.size() == 0) {
			returnview = "/casefile/notfound";
		} else {
			String uploadPath = context.getRealPath("");
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(docId);
			String filename = "ORDS"
					+ caseFileDetail.getCaseType().getCt_label()
					+ caseFileDetail.getFd_case_no()
					+ caseFileDetail.getFd_case_year();
			String orderswithbk = "Orders"
					+ caseFileDetail.getCaseType().getCt_label()
					+ caseFileDetail.getFd_case_no()
					+ caseFileDetail.getFd_case_year();
			String outputFilePath = uploadPath + File.separator + "uploads"
					+ File.separator + filename;
			String bookmarkFile = uploadPath + File.separator + "uploads"
					+ File.separator + orderswithbk;
			String basePath = "";
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");

			basePath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label();

			List<InputStream> list = new ArrayList<InputStream>();
			Map<String, Integer> map = new HashMap<String, Integer>();
			Integer pagecount = 0;
			int i = 0;
			try {
				if (subDocuments.size() > 1) {
					OutputStream out = new FileOutputStream(new File(
							outputFilePath + ".pdf"));
					for (SubDocument subDocument : subDocuments) {
						String srcPath = basePath + File.separator
								+ subDocument.getIndexField().getIf_name()
								+ File.separator
								+ subDocument.getSd_document_name() + ".pdf";
						String label = subDocument.getIndexField()
								.getIf_label();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"dd-MM-yyyy");
						if (subDocument.getSd_submitted_date() != null) {
							String date = formatter.format(subDocument
									.getSd_submitted_date());
							label = label + "_" + date;
						}
						if (i == 0) {
							String lastfilePath = srcPath;
							PdfReader reader = new PdfReader(lastfilePath);
							map.put(label, 1);
							reader.close();
						}
						if (i > 0) {

							String lastfilePath = basePath
									+ File.separator
									+ subDocuments.get(i - 1).getIndexField()
											.getIf_name()
									+ File.separator
									+ subDocuments.get(i - 1)
											.getSd_document_name() + ".pdf";
							PdfReader reader = new PdfReader(lastfilePath);
							pagecount += reader.getNumberOfPages();
							map.put(label, pagecount + 1);
							reader.close();
						}
						list.add(new FileInputStream(new File(srcPath)));
						i++;
					}
					PDFMerger.doMerge(list, out);
					
					//PDFMerger.doMerge(list, out);
					PDFMerger.doMerge(list, out,true);
					
				
					// File f=new File(outputFilePath+".pdf");
					// f.delete();
				} else {
					String srcPath = basePath + File.separator
							+ subDocuments.get(0).getIndexField().getIf_name()
							+ File.separator
							+ subDocuments.get(0).getSd_document_name();
					File src = new File(srcPath + ".pdf");
					File dest = new File(bookmarkFile + ".pdf");
					FileUtils.copyFile(src, dest);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			model.addAttribute("document_name", filename);
			model.addAttribute("doc_id", docId);
			model.addAttribute("isApplication", null);
			model.addAttribute("isImpugnedOrder", null);
		}

		return returnview;
	}*/
	
	@RequestMapping(value = "/vieworders/{id}", method = RequestMethod.GET)
	public String viewOrders(@PathVariable("id") Long docId, Model model) {
		Long metafieldId = 39L;
		List<SubDocument> subDocuments = new ArrayList<SubDocument>();
		RegularToDefective cfdconvertmain =null;
		String returnview = "/casefile/view";
		if (false) {
			//returnview = "/casefile/notfound";
		} else {
			String uploadPath = context.getRealPath("");
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(docId);
			String filename = "ORDS"
					+ caseFileDetail.getCaseType().getCt_label()
					+ caseFileDetail.getFd_case_no()
					+ caseFileDetail.getFd_case_year();
			String orderswithbk = "Orders"
					+ caseFileDetail.getCaseType().getCt_label()
					+ caseFileDetail.getFd_case_no()
					+ caseFileDetail.getFd_case_year();
			String outputFilePath = uploadPath + File.separator + "uploads"
					+ File.separator + filename;
			String bookmarkFile = uploadPath + File.separator + "uploads"
					+ File.separator + orderswithbk;
			String basePath = "";
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");

			basePath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label();

			List<InputStream> list = new ArrayList<InputStream>();
			Map<String, Integer> map = new HashMap<String, Integer>();
			Integer pagecount = 0;
			int i = 0;
			if(caseFileDetail != null) {
				/*	if(cfd.getFd_file_source().equals("P") && cfd.getFd_case_year().intValue() < 2010) {*/
				if(caseFileDetail.getFd_file_source().trim().equals("P")) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sDate1="2017-08-17 00:00:00"; 
						     Date sbDate =null;
							try {
								sbDate = formatter.parse(sDate1);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						

					// get order from dms before 2010-01-01
					List<SubDocument> subDocumentfromdms =subDocumentService.getSubDocumentOrders(docId,sbDate);
					
					if(subDocumentfromdms != null) {

						subDocuments.addAll(subDocumentfromdms);
						
					}
					
					
					
					
					
				}
				
				String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+caseFileDetail.getCaseType().getCt_label()+"/"+caseFileDetail.getFd_case_no()+"/"+caseFileDetail.getFd_case_year();
				//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
					
				 System.out.println("uriiiiiiiiii"+uri);
			    RestTemplate restTemplate = new RestTemplate();
			   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
			    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
			    SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			    ResponseEntity<OrderFromElegalix[]> response1 =null;
			    ResponseEntity<Object> response2 =null;
			    List<OrderFromElegalix> orderListFromElegalix =null;
			    
			    try {
			     
			   
			    
			    	response1 =
				    		  restTemplate.getForEntity(
				    				  uri,
				    		  OrderFromElegalix[].class);
			    		
			    	orderListFromElegalix   =	Arrays.asList(response1.getBody());

			    
			    System.out.println("Test Data"+orderListFromElegalix);
			   String date =sm.format(orderListFromElegalix.get(0).getJudgmentDate());
			   
			    }
			    catch(HttpClientErrorException e) {
			    	
			    	
			    	
			    	e.printStackTrace();
			    	
			    	
			    	
			    	
				    		/*  restTemplate.getForEntity(
				    				  uri,
				    		  Object.class);
			    		
			    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
			    	System.out.println("Response 2"+response2);*/
			    	
			    }
			   
			   // System.out.println("E"+date);
			    if(orderListFromElegalix != null) {
			    	for(OrderFromElegalix order:orderListFromElegalix) {
			 		   SubDocument sb =new SubDocument();
			 		   sb.setJudgmentID(order.getJudgmentID());
			 		   sb.setSd_document_id(100002);
			 		   sb.setSd_if_mid(39l);
			 		   sb.setSd_submitted_date(order.getJudgmentDate());
			 		   sb.setSd_rec_status(1);
			 		   sb.setSd_fd_mid(docId);
			 		   sb.setIndexField(masterService.getIndexField(39L));
			 		   sb.setChecked(false);
			 		   sb.setSd_nonmaintainable(false);
			 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
			 		   sb.setDocumentType(masterService.getApplicationById(100002));
			 		 sb.setSd_document_name(order.getJudgmentID().toString());
			 		 sb.setSubApplications(new ArrayList<SubApplication>());
			 		 
			 		subDocuments.add(sb);
			 		
			 
					downloadFile(sb.getJudgmentID(), uploadPath);
			 		 
			 		 
			 		  
			 	   }
			    }
			    
			    cfdconvertmain =caseFileDetailService.getConvertedCase(caseFileDetail.getFd_case_type().toString(), caseFileDetail.getFd_case_no().toString(), caseFileDetail.getFd_case_year().toString());
			    if(cfdconvertmain != null) {
			    	// again check order for elegalix for old case no 
			    	
			    	// This list is used when there more than converted cases
			    	List<RegularToDefective>  cfdconvertlist =new ArrayList<RegularToDefective>();
			    	
			    	cfdconvertlist.add(cfdconvertmain);
			    	
			    	RegularToDefective cfdconvert1  =caseFileDetailService.getConvertedCaseFromOld(cfdconvertmain.getRdh_case_type_old(),cfdconvertmain.getRdh_case_no_old(), cfdconvertmain.getRdh_case_year_old());
			    	
			    	
			    	if(cfdconvert1 != null) {
			    		cfdconvertlist.add(cfdconvert1);
			    		RegularToDefective cfdconvert2  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert1.getRdh_case_type_old(),cfdconvert1.getRdh_case_no_old(), cfdconvert1.getRdh_case_year_old());
				    	
				    	
				    	if(cfdconvert2 != null) {
				    		cfdconvertlist.add(cfdconvert2);
				    		RegularToDefective cfdconvert3  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert2.getRdh_case_type_old(),cfdconvert2.getRdh_case_no_old(), cfdconvert2.getRdh_case_year_old());
					    	
					    	
					    	if(cfdconvert3 != null) {
					    		cfdconvertlist.add(cfdconvert3);
					    		RegularToDefective cfdconvert4  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert3.getRdh_case_type_old(),cfdconvert3.getRdh_case_no_old(), cfdconvert3.getRdh_case_year_old());
						    	
						    	
						    	if(cfdconvert4 != null) {
						    		cfdconvertlist.add(cfdconvert4);
						    		
						    	}
					    		
					    	}
				    		
				    	}
			    	}
			    	

			    	for(RegularToDefective cfdconvert: cfdconvertlist) {
			    	

			    	
			    	CaseType ct1 =masterService.getCaseTypeById(Long.parseLong(cfdconvert.getRdh_case_type_old()));
			    	
			    	String uri1 = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+ct1.getCt_label()+"/"+cfdconvert.getRdh_case_no_old()+"/"+cfdconvert.getRdh_case_year_old();
					//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
						
					 System.out.println("uriiiiiiiiii"+uri);
				    RestTemplate restTemplate1 = new RestTemplate();
				   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
				    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
				    SimpleDateFormat sm1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				    ResponseEntity<OrderFromElegalix[]> re1 =null;
				    ResponseEntity<Object> re2 =null;
				    List<OrderFromElegalix> orderListFromElegalix1 =null;
				    
				    try {
				     
				   
				    
				    	re1 =
					    		  restTemplate1.getForEntity(
					    				  uri1,
					    		  OrderFromElegalix[].class);
				    		
				    	orderListFromElegalix1   =	Arrays.asList(re1.getBody());

				    
				    System.out.println("Test Data"+orderListFromElegalix1);
				   String date =sm.format(orderListFromElegalix1.get(0).getJudgmentDate());
				   
				    }
				    catch(HttpClientErrorException e) {
				    	System.out.println("Response22222222222222"+re1);
				    	
				    	
				    	e.printStackTrace();
				    	System.out.println("status------------------"+e.getStatusCode());
				    	System.out.println("Response------------------"+e.getResponseBodyAsString());
				    	
				    	
				    	
					   /* 		  restTemplate1.getForEntity(
					    				  uri1,
					    		  Object.class);
				    		
				    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
				    	System.out.println("Response 2"+re2);*/
				    	
				    }
				   
				   // System.out.println("E"+date);
				    if( orderListFromElegalix1 != null) {
				    	for(OrderFromElegalix order:orderListFromElegalix1) {
				 		   SubDocument sb =new SubDocument();
				 		   sb.setJudgmentID(order.getJudgmentID());
				 		   sb.setSd_document_id(100002);
				 		   sb.setSd_if_mid(39l);
				 		   sb.setSd_submitted_date(order.getJudgmentDate());
				 		   sb.setSd_rec_status(1);
				 		   sb.setSd_fd_mid(docId);
				 		  sb.setChecked(false);
				 		   sb.setIndexField(masterService.getIndexField(39L));
				 		   sb.setSd_nonmaintainable(false);
				 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
				 		   sb.setDocumentType(masterService.getApplicationById(100002));
				 		 sb.setSd_document_name(order.getJudgmentID().toString());
				 		 sb.setSubApplications(new ArrayList<SubApplication>());
				 		 
				 		subDocuments.add(sb);
				 		 
				 		downloadFile(sb.getJudgmentID(), uploadPath);
				 		  
				 	   }
				    }
			    	
			    }
			    }
			   
				}
			Collections.sort(subDocuments, new Comparator<SubDocument>() {
				  public int compare(SubDocument sd1, SubDocument sd2) {
				      if (sd1.getSd_submitted_date() == null || sd2.getSd_submitted_date() == null)
				        return 0;
				      return sd1.getSd_submitted_date().compareTo(sd2.getSd_submitted_date());
				  }
				});
			if (subDocuments.size() == 0) {
				returnview = "/casefile/view";
			}
			try {
				if (subDocuments.size() > 0) {
					OutputStream out = new FileOutputStream(new File(
							outputFilePath + ".pdf"));
					for (SubDocument subDocument : subDocuments) {
						String srcPath =null;
						
						if(subDocument.getJudgmentID() == null) {
							 srcPath = basePath + File.separator
									+ subDocument.getIndexField().getIf_name()
									+ File.separator
									+ subDocument.getSd_document_name() + ".pdf";
							}else if(subDocument.getSd_document_id() != null) {
								if(subDocument.getSd_document_id().intValue() == 100002) {
								srcPath =uploadPath+File.separator +"uploads"+File.separator+subDocument.getJudgmentID()+".pdf";
								}
								
							}
					/* srcPath = basePath + File.separator
								+ subDocument.getIndexField().getIf_name()
								+ File.separator
								+ subDocument.getSd_document_name() + ".pdf";*/
						String label = subDocument.getIndexField()
								.getIf_label();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"dd-MM-yyyy");
						if (subDocument.getSd_submitted_date() != null) {
							String date = formatter.format(subDocument
									.getSd_submitted_date());
							label = label + "_" + date;
						}
						if (i == 0) {
							String lastfilePath = srcPath;
							PdfReader reader = new PdfReader(lastfilePath);
							map.put(label, 1);
							reader.close();
						}
						if (i > 0) {
							String lastfilePath =null;
if(subDocuments.get(i - 1).getJudgmentID() == null) {
							 lastfilePath = basePath
									+ File.separator
									+ subDocuments.get(i - 1).getIndexField()
											.getIf_name()
									+ File.separator
									+ subDocuments.get(i - 1)
											.getSd_document_name() + ".pdf";
}
else if(subDocuments.get(i - 1).getSd_document_id() != null) {
	if(subDocuments.get(i - 1).getSd_document_id().intValue() == 100002) {
		
		lastfilePath =uploadPath+File.separator +"uploads"+File.separator+subDocument.getJudgmentID()+".pdf";
	}
	
}
							PdfReader reader = new PdfReader(lastfilePath);
							pagecount += reader.getNumberOfPages();
							map.put(label, pagecount + 1);
							reader.close();
						}
						list.add(new FileInputStream(new File(srcPath)));
						i++;
					}
					//PDFMerger.doMerge(list, out);
					PDFMerger.doMerge(list, out,true);

					// globalfunction.doBookmark(outputFilePath+".pdf",bookmarkFile+".pdf",map);
					// File f=new File(outputFilePath+".pdf");
					// f.delete();
				} else {
					String srcPath = null;
					if(subDocuments.get(0).getJudgmentID() == null) {
						 srcPath = basePath + File.separator
								+ subDocuments.get(0).getIndexField().getIf_name()
								+ File.separator
								+ subDocuments.get(0).getSd_document_name() + ".pdf";
						}else if(subDocuments.get(0).getSd_document_id() != null) {
							if(subDocuments.get(0).getSd_document_id().intValue() == 100002) {
							srcPath =uploadPath+File.separator +"uploads"+File.separator+subDocuments.get(0).getJudgmentID()+".pdf";
							}
							
						}
					File src = new File(srcPath + ".pdf");
					File dest = new File(bookmarkFile + ".pdf");
					FileUtils.copyFile(src, dest);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			model.addAttribute("document_name", filename);
			model.addAttribute("doc_id", docId);
			model.addAttribute("isApplication", null);
			model.addAttribute("isImpugnedOrder", null);
		}

		return returnview;
	}
	
	/*@RequestMapping(value = "/subdocument/{id}", method = RequestMethod.GET)
	public @ResponseBody String subdocument(@PathVariable("id") Long subDocId, Model model,HttpSession session) {
		
	}*/
	@RequestMapping(value = "/vieworders1/{id}", method = RequestMethod.GET)
	public  @ResponseBody String viewOrders1(@PathVariable("id") Long docId, Model model) {
		Long metafieldId = 39L;
		List<SubDocument> subDocuments = new ArrayList<SubDocument>();
		RegularToDefective cfdconvertmain =null;
		String returnview = "/casefile/view";
		if (false) {
			//returnview = "/casefile/notfound";
		} else {
			String uploadPath = context.getRealPath("");
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(docId);
			String filename = "ORDS"
					+ caseFileDetail.getCaseType().getCt_label()
					+ caseFileDetail.getFd_case_no()
					+ caseFileDetail.getFd_case_year();
			String orderswithbk = "Orders"
					+ caseFileDetail.getCaseType().getCt_label()
					+ caseFileDetail.getFd_case_no()
					+ caseFileDetail.getFd_case_year();
			String outputFilePath = uploadPath + File.separator + "uploads"
					+ File.separator + filename;
			String bookmarkFile = uploadPath + File.separator + "uploads"
					+ File.separator + orderswithbk;
			String basePath = "";
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");

			basePath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label();

			List<InputStream> list = new ArrayList<InputStream>();
			Map<String, Integer> map = new HashMap<String, Integer>();
			Integer pagecount = 0;
			int i = 0;
			if(caseFileDetail != null) {
				/*	if(cfd.getFd_file_source().equals("P") && cfd.getFd_case_year().intValue() < 2010) {*/
				if(caseFileDetail.getFd_file_source().trim().equals("P")) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sDate1="2017-08-17 00:00:00"; 
						     Date sbDate =null;
							try {
								sbDate = formatter.parse(sDate1);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						

					// get order from dms before 2010-01-01
					List<SubDocument> subDocumentfromdms =subDocumentService.getSubDocumentOrders(docId,sbDate);
					
					if(subDocumentfromdms != null) {

						subDocuments.addAll(subDocumentfromdms);
						
					}
					
					
					
					
					
				}
				
				String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+caseFileDetail.getCaseType().getCt_label()+"/"+caseFileDetail.getFd_case_no()+"/"+caseFileDetail.getFd_case_year();
				//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
					
				 System.out.println("uriiiiiiiiii"+uri);
			    RestTemplate restTemplate = new RestTemplate();
			   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
			    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
			    SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			    ResponseEntity<OrderFromElegalix[]> response1 =null;
			    ResponseEntity<Object> response2 =null;
			    List<OrderFromElegalix> orderListFromElegalix =null;
			    
			    try {
			     
			   
			    
			    	response1 =
				    		  restTemplate.getForEntity(
				    				  uri,
				    		  OrderFromElegalix[].class);
			    		
			    	orderListFromElegalix   =	Arrays.asList(response1.getBody());

			    
			    System.out.println("Test Data"+orderListFromElegalix);
			   String date =sm.format(orderListFromElegalix.get(0).getJudgmentDate());
			   
			    }
			    catch(HttpClientErrorException e) {
			    	
			    	
			    	
			    	e.printStackTrace();
			    	
			    	
			    	
			    	
				    		/*  restTemplate.getForEntity(
				    				  uri,
				    		  Object.class);
			    		
			    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
			    	System.out.println("Response 2"+response2);*/
			    	
			    }
			   
			   // System.out.println("E"+date);
			    if(orderListFromElegalix != null) {
			    	for(OrderFromElegalix order:orderListFromElegalix) {
			 		   SubDocument sb =new SubDocument();
			 		   sb.setJudgmentID(order.getJudgmentID());
			 		   sb.setSd_document_id(100002);
			 		   sb.setSd_if_mid(39l);
			 		   sb.setSd_submitted_date(order.getJudgmentDate());
			 		   sb.setSd_rec_status(1);
			 		   sb.setSd_fd_mid(docId);
			 		   sb.setIndexField(masterService.getIndexField(39L));
			 		   sb.setChecked(false);
			 		   sb.setSd_nonmaintainable(false);
			 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
			 		   sb.setDocumentType(masterService.getApplicationById(100002));
			 		 sb.setSd_document_name(order.getJudgmentID().toString());
			 		 sb.setSubApplications(new ArrayList<SubApplication>());
			 		 
			 		subDocuments.add(sb);
			 		
			 
					downloadFile(sb.getJudgmentID(), uploadPath);
			 		 
			 		 
			 		  
			 	   }
			    }
			    
			    cfdconvertmain =caseFileDetailService.getConvertedCase(caseFileDetail.getFd_case_type().toString(), caseFileDetail.getFd_case_no().toString(), caseFileDetail.getFd_case_year().toString());
			    if(cfdconvertmain != null) {
			    	// again check order for elegalix for old case no 
			    	
			    	// This list is used when there more than converted cases
			    	List<RegularToDefective>  cfdconvertlist =new ArrayList<RegularToDefective>();
			    	
			    	cfdconvertlist.add(cfdconvertmain);
			    	
			    	RegularToDefective cfdconvert1  =caseFileDetailService.getConvertedCaseFromOld(cfdconvertmain.getRdh_case_type_old(),cfdconvertmain.getRdh_case_no_old(), cfdconvertmain.getRdh_case_year_old());
			    	
			    	
			    	if(cfdconvert1 != null) {
			    		cfdconvertlist.add(cfdconvert1);
			    		RegularToDefective cfdconvert2  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert1.getRdh_case_type_old(),cfdconvert1.getRdh_case_no_old(), cfdconvert1.getRdh_case_year_old());
				    	
				    	
				    	if(cfdconvert2 != null) {
				    		cfdconvertlist.add(cfdconvert2);
				    		RegularToDefective cfdconvert3  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert2.getRdh_case_type_old(),cfdconvert2.getRdh_case_no_old(), cfdconvert2.getRdh_case_year_old());
					    	
					    	
					    	if(cfdconvert3 != null) {
					    		cfdconvertlist.add(cfdconvert3);
					    		RegularToDefective cfdconvert4  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert3.getRdh_case_type_old(),cfdconvert3.getRdh_case_no_old(), cfdconvert3.getRdh_case_year_old());
						    	
						    	
						    	if(cfdconvert4 != null) {
						    		cfdconvertlist.add(cfdconvert4);
						    		
						    	}
					    		
					    	}
				    		
				    	}
			    	}
			    	

			    	for(RegularToDefective cfdconvert: cfdconvertlist) {
			    	

			    	
			    	CaseType ct1 =masterService.getCaseTypeById(Long.parseLong(cfdconvert.getRdh_case_type_old()));
			    	
			    	String uri1 = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+ct1.getCt_label()+"/"+cfdconvert.getRdh_case_no_old()+"/"+cfdconvert.getRdh_case_year_old();
					//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
						
					 System.out.println("uriiiiiiiiii"+uri);
				    RestTemplate restTemplate1 = new RestTemplate();
				   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
				    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
				    SimpleDateFormat sm1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				    ResponseEntity<OrderFromElegalix[]> re1 =null;
				    ResponseEntity<Object> re2 =null;
				    List<OrderFromElegalix> orderListFromElegalix1 =null;
				    
				    try {
				     
				   
				    
				    	re1 =
					    		  restTemplate1.getForEntity(
					    				  uri1,
					    		  OrderFromElegalix[].class);
				    		
				    	orderListFromElegalix1   =	Arrays.asList(re1.getBody());

				    
				    System.out.println("Test Data"+orderListFromElegalix1);
				   String date =sm.format(orderListFromElegalix1.get(0).getJudgmentDate());
				   
				    }
				    catch(HttpClientErrorException e) {
				    	System.out.println("Response22222222222222"+re1);
				    	
				    	
				    	e.printStackTrace();
				    	System.out.println("status------------------"+e.getStatusCode());
				    	System.out.println("Response------------------"+e.getResponseBodyAsString());
				    	
				    	
				    	
					   /* 		  restTemplate1.getForEntity(
					    				  uri1,
					    		  Object.class);
				    		
				    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
				    	System.out.println("Response 2"+re2);*/
				    	
				    }
				   
				   // System.out.println("E"+date);
				    if( orderListFromElegalix1 != null) {
				    	for(OrderFromElegalix order:orderListFromElegalix1) {
				 		   SubDocument sb =new SubDocument();
				 		   sb.setJudgmentID(order.getJudgmentID());
				 		   sb.setSd_document_id(100002);
				 		   sb.setSd_if_mid(39l);
				 		   sb.setSd_submitted_date(order.getJudgmentDate());
				 		   sb.setSd_rec_status(1);
				 		   sb.setSd_fd_mid(docId);
				 		  sb.setChecked(false);
				 		   sb.setIndexField(masterService.getIndexField(39L));
				 		   sb.setSd_nonmaintainable(false);
				 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
				 		   sb.setDocumentType(masterService.getApplicationById(100002));
				 		 sb.setSd_document_name(order.getJudgmentID().toString());
				 		 sb.setSubApplications(new ArrayList<SubApplication>());
				 		 
				 		subDocuments.add(sb);
				 		 
				 		downloadFile(sb.getJudgmentID(), uploadPath);
				 		  
				 	   }
				    }
			    	
			    }
			    }
			   
				}
			Collections.sort(subDocuments, new Comparator<SubDocument>() {
				  public int compare(SubDocument sd1, SubDocument sd2) {
				      if (sd1.getSd_submitted_date() == null || sd2.getSd_submitted_date() == null)
				        return 0;
				      return sd1.getSd_submitted_date().compareTo(sd2.getSd_submitted_date());
				  }
				});
			if (subDocuments.size() == 0) {
				returnview = "/casefile/view";
			}
			try {
				if (subDocuments.size() > 0) {
					OutputStream out = new FileOutputStream(new File(
							outputFilePath + ".pdf"));
					for (SubDocument subDocument : subDocuments) {
						String srcPath =null;
						
						if(subDocument.getJudgmentID() == null) {
							 srcPath = basePath + File.separator
									+ subDocument.getIndexField().getIf_name()
									+ File.separator
									+ subDocument.getSd_document_name() + ".pdf";
							}else if(subDocument.getSd_document_id() != null) {
								if(subDocument.getSd_document_id().intValue() == 100002) {
								srcPath =uploadPath+File.separator +"uploads"+File.separator+subDocument.getJudgmentID()+".pdf";
								}
								
							}
					/* srcPath = basePath + File.separator
								+ subDocument.getIndexField().getIf_name()
								+ File.separator
								+ subDocument.getSd_document_name() + ".pdf";*/
						String label = subDocument.getIndexField()
								.getIf_label();
						SimpleDateFormat formatter = new SimpleDateFormat(
								"dd-MM-yyyy");
						if (subDocument.getSd_submitted_date() != null) {
							String date = formatter.format(subDocument
									.getSd_submitted_date());
							label = label + "_" + date;
						}
						if (i == 0) {
							String lastfilePath = srcPath;
							PdfReader reader = new PdfReader(lastfilePath);
							map.put(label, 1);
							reader.close();
						}
						if (i > 0) {
							String lastfilePath =null;
if(subDocuments.get(i - 1).getJudgmentID() == null) {
							 lastfilePath = basePath
									+ File.separator
									+ subDocuments.get(i - 1).getIndexField()
											.getIf_name()
									+ File.separator
									+ subDocuments.get(i - 1)
											.getSd_document_name() + ".pdf";
}
else if(subDocuments.get(i - 1).getSd_document_id() != null) {
	if(subDocuments.get(i - 1).getSd_document_id().intValue() == 100002) {
		
		lastfilePath =uploadPath+File.separator +"uploads"+File.separator+subDocument.getJudgmentID()+".pdf";
	}
	
}
							PdfReader reader = new PdfReader(lastfilePath);
							pagecount += reader.getNumberOfPages();
							map.put(label, pagecount + 1);
							reader.close();
						}
						list.add(new FileInputStream(new File(srcPath)));
						i++;
					}
					//PDFMerger.doMerge(list, out);
					PDFMerger.doMerge(list, out,true);

					// globalfunction.doBookmark(outputFilePath+".pdf",bookmarkFile+".pdf",map);
					// File f=new File(outputFilePath+".pdf");
					// f.delete();
				} else {
					String srcPath = null;
					if(subDocuments.get(0).getJudgmentID() == null) {
						 srcPath = basePath + File.separator
								+ subDocuments.get(0).getIndexField().getIf_name()
								+ File.separator
								+ subDocuments.get(0).getSd_document_name() + ".pdf";
						}else if(subDocuments.get(0).getSd_document_id() != null) {
							if(subDocuments.get(0).getSd_document_id().intValue() == 100002) {
							srcPath =uploadPath+File.separator +"uploads"+File.separator+subDocuments.get(0).getJudgmentID()+".pdf";
							}
							
						}
					File src = new File(srcPath + ".pdf");
					File dest = new File(bookmarkFile + ".pdf");
					FileUtils.copyFile(src, dest);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			model.addAttribute("document_name", filename);
			model.addAttribute("doc_id", docId);
			model.addAttribute("isApplication", null);
			model.addAttribute("isImpugnedOrder", null);
		}

		/*return returnview;*/
		
		String jsonData = null;
		ActionResponse<Model> response = new ActionResponse<>();

		response.setResponse("TRUE");
		response.setModelData(model);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") Long docId, Model model,HttpSession session) 
	{

		User u = (User) session.getAttribute("USER");
		SubDocument subDocument = subDocumentService.getPetitionSubDocument(docId, 1);
		String returnview = "/casefile/view";
		
		List<MediationDocs> medDoc=new ArrayList<>();
		medDoc=orderReportService.getMedReports(docId);
		
		if(subDocument==null) {
			subDocument=subDocumentService.getOtherSubDocument(docId, 1);
		}
		
		if (subDocument == null && medDoc.size()==0) 
		{	
			
			returnview = "/casefile/notfound";
		}
		else 
		{

			CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(docId);
			CauseList clt=causeListService.getByFDmid(docId);
			
			model.addAttribute("doc_id", docId);
			model.addAttribute("isApplication", null);
			model.addAttribute("isImpugnedOrder", null);
			
			if(u.getUserroles().get(0).getLk().getLk_longname().equals("Ecourt_Team") && clt!=null) {
			
			caseFileDetail.setFd_cl_flag(true);
			caseFileDetail.setFd_cl_date(new Date());
			caseFileDetail.setFd_cl_by(u.getUm_id());
			caseFileDetail.setFd_pet_adv(clt.getCl_petitioner_council());
			caseFileDetail.setFd_res_adv(clt.getCl_respondent_council());
			}
			
			caseFileDetail=caseFileDetailService.save(caseFileDetail);
			
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			
			
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator ;
			
		/*	String srcPath = "D:\\Allahabad High Court\\Allahabad" + File.separator
			+ caseFileDetail.getCaseType().getCt_label()
			+ File.separator ;*/
			
			String fileName=null;
			
			if(subDocument != null) {
				model.addAttribute("document_name",subDocument.getSd_document_name());
				if(subDocument.getIndexField().getIf_id() ==44L || subDocument.getIndexField().getIf_id() ==45L || subDocument.getIndexField().getIf_id() ==46L
						|| subDocument.getIndexField().getIf_id() ==47L || subDocument.getIndexField().getIf_id() ==48L || subDocument.getIndexField().getIf_id() ==49L 
						|| subDocument.getIndexField().getIf_id() ==50L || subDocument.getIndexField().getIf_id() ==51L || subDocument.getIndexField().getIf_id() ==52L 
						|| subDocument.getIndexField().getIf_id() ==53L) {
					subDocument.getIndexField().setIf_name("mediation");
				}
				srcPath= srcPath+subDocument.getIndexField().getIf_name()
						+ File.separator + subDocument.getSd_document_name()
						+ ".pdf";
				fileName=subDocument.getSd_document_name();
			}
			else {
				model.addAttribute("document_name",medDoc.get(0).getMdn_doc_name());
				srcPath= srcPath+"mediation"
						+ File.separator + medDoc.get(0).getMdn_doc_name()
						+ ".pdf";
				fileName=medDoc.get(0).getMdn_doc_name();
			}
			

			File source = new File(srcPath);
			
			/*File source = new File("C:\\Users\\Alok\\Documents\\WRIC323162025_PETN_1.pdf");*/
			
			/*File source = new File("C:\\Users\\Alok\\Pictures\\stamp rept\\WTAX43662025_PETN_1.pdf");*/

			String uploadPath = context.getRealPath("");
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + fileName
					+ ".pdf");

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
		}
		
		return returnview;
	}
	
	@RequestMapping(value = "/getNextnPrevious", method = RequestMethod.POST)
	public @ResponseBody String getNextnPrevious(@RequestBody CauseList causeList, HttpSession session) 
	{
		
		

		Integer cl_temp =causeList.getCl_serial_no();
		String jsonData = null;
		ActionResponse<CauseList> response=new ActionResponse<>();
		User u=(User) session.getAttribute("USER");
		
		boolean nextCase =false;
		boolean previousCase =false;
		CauseList  clNext =null;
		CauseList  clPrevious =null;
	
		////////next
		

		
		
		
		
		
		
		
		


		clNext=causeListService.getNextCase(causeList);

if(clNext != null) {
	
	if(clNext.getCl_fd_mid() != null) {
		// confirm case exists with fd_id
		nextCase= true;
	}
}

causeList.setCl_serial_no(cl_temp);



	
		
		//////////next


///////////////// previous



clPrevious=causeListService.getPreviousCase(causeList);
 
 if(clPrevious != null) {
		
		if(clPrevious.getCl_fd_mid() != null) {
			// confirm case exists with fd_id
			previousCase= true;
		}
	}







/////////previous
		
		
		
		
			response.setModelData(clNext);
			response.setData(clPrevious);
			response.setResponse("TRUE");
		//response.setData("Record saved successfully");
		
			
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
			
	
	}
	
	

	
	@RequestMapping(value = "/viewCauseList/{id}", method = RequestMethod.GET)
	public String viewCauseList(@PathVariable("id") Long docId,@RequestParam("cl_court_no") Integer cl_court_no,@RequestParam("cl_rec_status") Integer cl_rec_status,@RequestParam("cl_serial_no") Integer cl_serial_no,@RequestParam("cl_dol") String cl_dol ,@RequestParam("cl_list_type_mid") Long cl_list_type_mid,@RequestParam("nPflag") String nPflag ,Model model) 
	{
		
		
		Integer cl_temp =cl_serial_no;
		System.out.println("cl daaaaaaaa"+cl_court_no);
		System.out.println("cl daaaaaaaa"+cl_rec_status);
		System.out.println("cl daaaaaaaa"+cl_serial_no);
		System.out.println("cl daaaaaaaa"+cl_dol);
		
		CauseListType ct =causeListService.getCauseListTypesById(cl_list_type_mid);
		
		
		
	Date cl_dol1 =	new Date(Long.valueOf(cl_dol));
	
	CauseList cl =new CauseList();
	String pattern = "yyyy-MM-dd";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
	String cl_date1 =simpleDateFormat.format(cl_dol1);
	
	Date cl_dol2 =null; 
	
	
	try {
		cl_dol2 =simpleDateFormat.parse(cl_date1);
	} catch (ParseException e2) {
		
		e2.printStackTrace();
	}
	
	cl.setCl_court_no(cl_court_no);
	
	cl.setCl_rec_status(cl_rec_status);
	cl.setCl_serial_no(cl_serial_no);
	cl.setCl_dol(cl_dol2);
	
	cl.setCl_list_type_mid(cl_list_type_mid);
	boolean nextCase =false;
	boolean previousCase =false;
	CauseList  clNext =null;
	CauseList  clPrevious =null;
	if(docId !=9999999999L) {
		cl.setCl_fd_mid(docId);
	}
	else {
		CauseList cuasl=causeListService.getFirstCauseListCase(cl);
		cl.setCl_fd_mid(cuasl.getCl_fd_mid());
		docId=cuasl.getCl_fd_mid();
	}
	
	CauseList causelist=causeListService.getCauseListByIdandDate(cl);
	
	/*Integer minSerial=causeListService.getMinSerial(cl);
	Integer maxSerial=causeListService.getMaxSerial(cl);*/
	//List<Integer> serials=causeListService.getSerialAvailbaleCases(cl);
	
	

	
	System.out.println("cl daaaaaaaa"+cl_dol1);

		SubDocument subDocument = subDocumentService.getPetitionSubDocument(docId, 1);
		String returnview = "/casefile/viewCauseList";
		
		List<MediationDocs> medDoc=new ArrayList<>();
		medDoc=orderReportService.getMedReports(docId);
		
		if(subDocument==null) {
			subDocument=subDocumentService.getOtherSubDocument(docId, 1);
		}
		if (subDocument == null && medDoc.size()==0) 
		{
			returnview = "/casefile/notfound";
			
		}
		else 
		{
			
			

			CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(docId);
			
			caseFileDetail.setFd_pet_adv(causelist.getCl_petitioner_council());
			caseFileDetail.setFd_res_adv(causelist.getCl_respondent_council());
			
			
			caseFileDetail=caseFileDetailService.save(caseFileDetail);
			
			
			
			
			
			model.addAttribute("cl_serial_noCurrent", cl_temp);
			model.addAttribute("doc_id", docId);
			model.addAttribute("cl_court_noCurrent", cl_court_no);
			model.addAttribute("cl_rec_statusCurrent", cl_rec_status);
			//model.addAttribute("allSerial", serials);
		//	model.addAttribute("maxSerial", maxSerial);
			
			model.addAttribute("listTypeName", ct.getClt_description());
			
			
			
			model.addAttribute("cl_list_type_midCurrent", cl_list_type_mid);
			
			model.addAttribute("cl_dolCurrent", cl_dol);
			
			
			
			
			
			model.addAttribute("isApplication", null);
			model.addAttribute("isImpugnedOrder", null);
			
			
			
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator ;
			
			String fileName=null;
			
			if(subDocument != null) {
				model.addAttribute("document_name",subDocument.getSd_document_name());
				srcPath= srcPath+subDocument.getIndexField().getIf_name()
						+ File.separator + subDocument.getSd_document_name()
						+ ".pdf";
				fileName=subDocument.getSd_document_name();
			}
			else {
				model.addAttribute("document_name",medDoc.get(0).getMdn_doc_name());
				srcPath= srcPath+"mediation"
						+ File.separator + medDoc.get(0).getMdn_doc_name()
						+ ".pdf";
				fileName=medDoc.get(0).getMdn_doc_name();
			}

			File source = new File(srcPath);

			String uploadPath = context.getRealPath("");
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + fileName
					+ ".pdf");

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
		}
		
		return returnview;
	}

	@RequestMapping(value = "/impugnedorder/{id}", method = RequestMethod.GET)
	public String impugnedorder(@PathVariable("id") Long ioId, Model model) {

		ImpugnedOrder io = caseFileDetailService.getImpugnedOrder(ioId);
		Integer caseYear = null;
		try {
			caseYear = Integer.parseInt(io.getIo_case_year());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CaseFileDetail cfd = caseFileDetailService.getCaseFileDetail(
				io.getIo_hc_case_type(), io.getIo_case_no(), caseYear);
		Long docId = io.getIo_fd_mid();

		SubDocument subDocument = subDocumentService.getPetitionSubDocument(
				cfd.getFd_id(), 1);
		String returnview = "/casefile/view";
		if (subDocument == null) {
			returnview = "/casefile/notfound";
		} else {

			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(cfd.getFd_id());
			model.addAttribute("document_name",
					subDocument.getSd_document_name());
			model.addAttribute("doc_id", docId);
			model.addAttribute("isApplication", null);
			model.addAttribute("isImpugnedOrder", 1);
			model.addAttribute("casetype", cfd.getCaseType().getCt_label());
			model.addAttribute("caseno", cfd.getFd_case_no());
			model.addAttribute("caseyear", cfd.getFd_case_year());
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + subDocument.getIndexField().getIf_name()
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf";

			File source = new File(srcPath);

			String uploadPath = context.getRealPath("");
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf");

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return returnview;
		}

	
	@RequestMapping(value = "/subdocument-highlight-status/{id}/{checkBoxValue}", method = RequestMethod.GET)
	public @ResponseBody String subdocumentStatus(@PathVariable("id") Long subDocId,@PathVariable("checkBoxValue") Boolean bool,HttpSession session) 
	{
		User u = (User) session.getAttribute("USER");
		Long userRole=u.getUserroles().get(0).getUr_role_id();
		CauseList causeList =null;
		String causeListCaseDate =null; 
		SubDocument subDocument = subDocumentService.getByPK(subDocId);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate=dateFormat.format(new Date());
		Long cl_fd_mid =subDocument.getSd_fd_mid();
		causeList =causeListService.getByFDmid(cl_fd_mid);
		if(causeList != null) 
		{
			causeListCaseDate =dateFormat.format(causeList.getCl_dol());
		}
		if(causeList != null &&  currentDate.equals(causeListCaseDate) && userRole==351426) 
			{
				if(subDocument.getSd_if_mid() == 41L || subDocument.getSd_if_mid() == 14L ) 
			{
				
				subDocument.setChecked(false);
				SubDocument subDocument1 =subDocumentService.save(subDocument);
			}
			}
		
		return null ;
	}
	
	
	@RequestMapping(value = "/showMedReport/{id}", method = RequestMethod.GET)
	public @ResponseBody String showMedReport(@PathVariable("id") Long subDocId, Model model,HttpSession session) {
		
		CauseList causeList =null;
		String causeListCaseDate =null;
		User u = (User) session.getAttribute("USER");
		Long userRole=u.getUserroles().get(0).getUr_role_id();
		
	// write code to view order from ealiglix
		
		
		
		MediationDocs medDoc = subDocumentService.getByMedPK(subDocId);
	

		String returnview = "/casefile/view";
		if (medDoc == null) 
		{
			returnview = "/casefile/notfound";
		}
		else
		{
			Long docId = medDoc.getMdn_fd_mid();
			CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(docId);
			model.addAttribute("doc_id", docId);
			model.addAttribute("document_name",medDoc.getMdn_doc_name());
			model.addAttribute("isApplication", null);
			model.addAttribute("isImpugnedOrder", null);
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + "mediation"
					+ File.separator + medDoc.getMdn_doc_name()
					+ ".pdf";

			File source = new File(srcPath);

			String uploadPath = context.getRealPath("");
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + medDoc.getMdn_doc_name()
					+ ".pdf");
			
			System.out.println("destination: "+dest);

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//String response=globalfunction.convert_to_json(returnview);
		
		String jsonData = null;
		ActionResponse<Model> response = new ActionResponse<>();

		response.setResponse("TRUE");
		response.setModelData(model);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	
	
	
	
	@RequestMapping(value = "/subdocument/{id}", method = RequestMethod.GET)
	public @ResponseBody String subdocument(@PathVariable("id") Long subDocId, Model model,HttpSession session) {
		
		CauseList causeList =null;
		String causeListCaseDate =null;
		User u = (User) session.getAttribute("USER");
		Long userRole=u.getUserroles().get(0).getUr_role_id();
		
	// write code to view order from ealiglix
		
		
		
		SubDocument subDocument = subDocumentService.getByPK(subDocId);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate=dateFormat.format(new Date());
		Long cl_fd_mid =subDocument.getSd_fd_mid();
		causeList =causeListService.getByFDmid(cl_fd_mid);
		if(causeList != null) 
		{
			causeListCaseDate =dateFormat.format(causeList.getCl_dol());
		}
		if(causeList != null &&  currentDate.equals(causeListCaseDate) && 351426==userRole) 
		{
			if(subDocument.getSd_if_mid() == 41L || subDocument.getSd_if_mid() == 14L ) 
			{
			subDocument.setChecked(true);
			SubDocument subDocument1 =subDocumentService.save(subDocument);
		}
		
		}

		String returnview = "/casefile/view";
		if (subDocument == null) 
		{
			returnview = "/casefile/notfound";
		}
		else
		{
			if(subDocument.getIndexField().getIf_id() ==44L || subDocument.getIndexField().getIf_id() ==45L || subDocument.getIndexField().getIf_id() ==46L
					|| subDocument.getIndexField().getIf_id() ==47L || subDocument.getIndexField().getIf_id() ==48L || subDocument.getIndexField().getIf_id() ==49L 
					|| subDocument.getIndexField().getIf_id() ==50L || subDocument.getIndexField().getIf_id() ==51L || subDocument.getIndexField().getIf_id() ==52L 
					|| subDocument.getIndexField().getIf_id() ==53L) {
				subDocument.getIndexField().setIf_name("mediation");
			}
			Long docId = subDocument.getSd_fd_mid();
			CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(docId);
			model.addAttribute("doc_id", docId);
			model.addAttribute("document_name",subDocument.getSd_document_name());
			model.addAttribute("isApplication", null);
			model.addAttribute("isImpugnedOrder", null);
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + subDocument.getIndexField().getIf_name()
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf";

			File source = new File(srcPath);

			String uploadPath = context.getRealPath("");
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf");
			
			System.out.println("destination: "+dest);

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//String response=globalfunction.convert_to_json(returnview);
		
		String jsonData = null;
		ActionResponse<Model> response = new ActionResponse<>();

		response.setResponse("TRUE");
		response.setModelData(model);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	
	@RequestMapping(value = "/getOrderFromElegalix/{id}", method = RequestMethod.GET)
	public @ResponseBody String getOrderFromElegalix(@PathVariable("id") Long subDocId, Model model,HttpSession session) {
		
		CauseList causeList =null;
		String causeListCaseDate =null;
		User u = (User) session.getAttribute("USER");
		Long userRole=u.getUserroles().get(0).getUr_role_id();
		
	// write code to view order from ealiglix
		
		String uploadPath = context.getRealPath("");
		
		downloadFile(subDocId, uploadPath);
		
		
		

		
		
		//String response=globalfunction.convert_to_json(returnview);
		model.addAttribute("doc_id", subDocId);
		model.addAttribute("document_name",subDocId);
		model.addAttribute("isApplication", null);
		model.addAttribute("isImpugnedOrder", null);
		
		String jsonData = null;
		ActionResponse<Model> response = new ActionResponse<>();

		response.setResponse("TRUE");
		response.setModelData(model);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	
	
	
	   
	   public void downloadFile(Long id, String path){
		   
		   // This method will download file using RestTemplate
		   System.out.println("Method called");
		   
		   String uploadPath =path+File.separator +"uploads"+File.separator+id+".pdf";
		   
	       try {
	    	   
	    	   RestTemplate restTemplate =new RestTemplate();
	           HttpHeaders headers = new HttpHeaders();
	           headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
	           headers.add("Accept","*/*");
	           HttpEntity<String> entity = new HttpEntity<>(headers);
	           ResponseEntity<byte[]> response = restTemplate.exchange("http://192.168.0.162:8080/elegalix_restapi2/api/judgment/"+id,HttpMethod.GET, entity, byte[].class);
	           Files.write(Paths.get(uploadPath), response.getBody());
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();
	       }
	   }
	
	@RequestMapping(value = "/subdocumentTab/{id}", method = RequestMethod.GET)
	public String subdocumentTab(@PathVariable("id") Long subDocId, Model model) {
		SubDocument subDocument = subDocumentService.getByPK(subDocId);

		String returnview = "/casefile/view";
		if (subDocument == null) {
			returnview = "/casefile/notfound";
		} else {
			Long docId = subDocument.getSd_fd_mid();
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(docId);

			model.addAttribute("doc_id", docId);
			model.addAttribute("document_name",
					subDocument.getSd_document_name());
			model.addAttribute("isApplication", null);
			model.addAttribute("isImpugnedOrder", null);
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + subDocument.getIndexField().getIf_name()
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf";

			File source = new File(srcPath);

			String uploadPath = context.getRealPath("");
			
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf");
			
			System.out.println("destination: "+dest);

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//String response=globalfunction.convert_to_json(returnview);
	return 	returnview;
	
	}
	

	@RequestMapping(value = "/applicationview", method = RequestMethod.GET)
	public String subdocument(@RequestParam("app_no") Integer app_no,
			@RequestParam("app_year") Integer app_year,
			@RequestParam("case_id") Long case_id, Model model,HttpSession session) 
	{
		User u = (User) session.getAttribute("USER");
		
		SubDocument subDocument =null;
		
		List<MediationDocs> medDoc=new ArrayList<>();
		medDoc=orderReportService.getMedReports(case_id);
		
		SubDocument sb1= subDocumentService.getApplicationByCase(app_no,
				app_year, case_id);
		
		if(sb1==null) {
			subDocument=subDocumentService.getOtherSubDocument(case_id, 1);
		}
		else {
			subDocument=sb1;
		}
		String returnview = "/casefile/view";
		if (subDocument == null && medDoc.size()==0) 
		{
			SubApplication sb =null;
			SubDocument  sd =null;
			sb =subDocumentService.getSubApplication(app_no,app_year,case_id);
			if(sb != null) {
				sd =subDocumentService.getByPK(sb.getSb_ap_sd_mid());
				if(sd != null) {
					Long docId = sd.getSd_fd_mid();
					CaseFileDetail caseFileDetail1 = caseFileDetailService.getCaseFileDetail(docId);
					Date date = sb.getSb_ap_cr_date();

					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					String submitted_date = "";
					if (date != null) 
					{
						submitted_date = formatter.format(date);
					}

					CaseFileDetail caseFileDetail = caseFileDetailService
							.getCaseFileDetail(docId);
					if(u.getUserroles().get(0).getLk().getLk_longname().equals("Ecourt_Team")) {
						
						caseFileDetail.setFd_cl_flag(true);
						caseFileDetail.setFd_cl_date(new Date());
						caseFileDetail.setFd_cl_by(u.getUm_id());
						caseFileDetailService.save(caseFileDetail);
						}
					model.addAttribute("doc_id", docId);
					model.addAttribute("document_name",sd.getSd_document_name());
					model.addAttribute("isApplication", 1);
					model.addAttribute("isImpugnedOrder", null);
					model.addAttribute("application_type", sb.getApplicationType().getAt_name());
					model.addAttribute("application_no",sb.getSb_ap_no());
					model.addAttribute("application_year",sb.getSb_ap_year());
					model.addAttribute("submitted_date", submitted_date);
					//model.addAttribute("party", subDocument.getSd_party());
					//model.addAttribute("name", subDocument.getSd_description());
				//	model.addAttribute("counsel", subDocument.getSd_counsel());
					
					Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
					/*String srcPath = lookupRepo.getLk_longname() + File.separator
							+ caseFileDetail1.getCaseType().getCt_label()
							+ File.separator + subDocument.getIndexField().getIf_name()
							+ File.separator + sb.getSb_ap_documentname()
							+ ".pdf";*/
					
					String srcPath = lookupRepo.getLk_longname() + File.separator
							+ caseFileDetail1.getCaseType().getCt_label()
							+ File.separator + "application"
							+ File.separator + sd.getSd_document_name()
							+ ".pdf";

					File source = new File(srcPath);

					String uploadPath = context.getRealPath("");
					File dest = new File(uploadPath + File.separator + "uploads"
							+ File.separator + sd.getSd_document_name()
							+ ".pdf");

					try {
						FileUtils.copyFile(source, dest);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					return returnview;
					
				}
				
				returnview = "/casefile/notfound";
				
				
			}
			
			returnview = "/casefile/notfound";
		} else {
			Long docId = (subDocument!=null ? subDocument.getSd_fd_mid() :medDoc.get(0).getMdn_fd_mid());
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(docId);
			Date date = (subDocument!=null ?subDocument.getSd_submitted_date() :medDoc.get(0).getMdn_cr_date());
			
/*CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(docId);*/
			
			
			
			if(u.getUserroles().get(0).getLk().getLk_longname().equals("Ecourt_Team")) {
			
			caseFileDetail.setFd_cl_flag(true);
			caseFileDetail.setFd_cl_date(new Date());
			caseFileDetail.setFd_cl_by(u.getUm_id());
			caseFileDetailService.save(caseFileDetail);
			}

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String submitted_date = "";
			if (date != null) {
				submitted_date = formatter.format(date);
			}

			model.addAttribute("doc_id", docId);
			model.addAttribute("document_name",
					(subDocument!=null ?subDocument.getSd_document_name() :medDoc.get(0).getMdn_doc_name()));
			
			model.addAttribute("isImpugnedOrder", null);
			if(subDocument !=null) {
				if(sb1!=null) {
			model.addAttribute("application_type", subDocument
					.getDocumentType().getAt_name());
			model.addAttribute("application_no",
					subDocument.getSd_document_no());
			model.addAttribute("application_year",
					subDocument.getSd_document_year());
			model.addAttribute("isApplication", 1);
				}
			model.addAttribute("submitted_date", submitted_date);
			model.addAttribute("party", subDocument.getSd_party());
			model.addAttribute("name", subDocument.getSd_description());
			model.addAttribute("counsel", subDocument.getSd_counsel());
			}
			

			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator ;
			
			String fileName=null;
			
			if(subDocument != null) {
				model.addAttribute("document_name",subDocument.getSd_document_name());
				srcPath= srcPath+subDocument.getIndexField().getIf_name()
						+ File.separator + subDocument.getSd_document_name()
						+ ".pdf";
				fileName=subDocument.getSd_document_name();
			}
			else {
				model.addAttribute("document_name",medDoc.get(0).getMdn_doc_name());
				srcPath= srcPath+"mediation"
						+ File.separator + medDoc.get(0).getMdn_doc_name()
						+ ".pdf";
				fileName=medDoc.get(0).getMdn_doc_name();
			}

			File source = new File(srcPath);

			String uploadPath = context.getRealPath("");
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + fileName
					+ ".pdf");

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnview;
	}
	
	
	@RequestMapping(value = "/applicationviewCauseList", method = RequestMethod.GET)
	public String subdocumentCauseList(@RequestParam("cl_court_no") Integer cl_court_no,@RequestParam("cl_rec_status") Integer cl_rec_status,@RequestParam("cl_serial_no") Integer cl_serial_no,@RequestParam("cl_dol") String cl_dol ,@RequestParam("cl_list_type_mid") Long cl_list_type_mid,@RequestParam("app_no") Integer app_no,
			@RequestParam("app_year") Integer app_year,
			@RequestParam("case_id") Long case_id, Model model) 
	{
		
		Integer cl_temp =cl_serial_no;
		System.out.println("cl daaaaaaaa"+cl_court_no);
		System.out.println("cl daaaaaaaa"+cl_rec_status);
		System.out.println("cl daaaaaaaa"+cl_serial_no);
		System.out.println("cl daaaaaaaa"+cl_dol);
		
		CauseListType ct =causeListService.getCauseListTypesById(cl_list_type_mid);
		
		
		
	Date cl_dol1 =	new Date(Long.valueOf(cl_dol));
	
	CauseList cl =new CauseList();
	String pattern = "yyyy-MM-dd";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
	String cl_date1 =simpleDateFormat.format(cl_dol1);
	
	Date cl_dol2 =null; 
	
	
	try {
		cl_dol2 =simpleDateFormat.parse(cl_date1);
	} catch (ParseException e2) {
		
		e2.printStackTrace();
	}
	
	cl.setCl_court_no(cl_court_no);
	
	cl.setCl_rec_status(cl_rec_status);
	cl.setCl_serial_no(cl_serial_no);
	cl.setCl_dol(cl_dol2);
	cl.setCl_fd_mid(case_id);
	cl.setCl_list_type_mid(cl_list_type_mid);
	boolean nextCase =false;
	boolean previousCase =false;
	CauseList  clNext =null;
	CauseList  clPrevious =null;
	
	if(true) {
		
	
	
	
	
	
	
	


		clNext=causeListService.getNextCase(cl);

if(clNext != null) {
	
	if(clNext.getCl_fd_mid() != null) {
		// confirm case exists with fd_id
		nextCase= true;
	}
}


if(clNext == null || !nextCase) {
	List<CauseList> clList =causeListService.getListForNextCase(cl);
	
	
	// jump to next existing case
	//causeListService.getByFDmid(cl.getCl_fd_mid());
	
	
	
	for(CauseList cal :clList) {
		
		if(cal.getCl_serial_no() > cl.getCl_serial_no()) {
			//  we get the next serial no
			
			if(cal.getCl_fd_mid() != null) {
				
				// get connected for next case 
				List<CauseList> ca2  =causeListService.getListConnectedForNextCase(cal);
				
				if(ca2 != null) {
					clNext =ca2.get(0);
					break;
				}
				
				//clNext =cal;
		
			}
		}
		
	}
}
	}
	if(true) {
		
		cl.setCl_serial_no(cl_temp);
		clPrevious=causeListService.getPreviousCase(cl);
		 
		 if(clPrevious != null) {
				
				if(clPrevious.getCl_fd_mid() != null) {
					// confirm case exists with fd_id
					previousCase= true;
				}
			}


			if(clPrevious == null || !previousCase) {
				List<CauseList> clList =causeListService.getListForPreviousCase(cl);
				
				
				
				
				for(CauseList cal :clList) {
					
					if(cal.getCl_serial_no() < cl.getCl_serial_no()) {
						//  we get the next serial no
						
						if(cal.getCl_fd_mid() != null) {
							
							List<CauseList> ca2 =	causeListService.getListConnectedForPriviousCase(cal);
						if(ca2 != null) {
							clPrevious =ca2.get(0);
						break;
						}
						}
					}
					
				}
			}
		
	}

	
	System.out.println("cl daaaaaaaa"+cl_dol1);
		
		
		SubDocument sb1 = subDocumentService.getApplicationByCase(app_no,
				app_year, case_id);
		SubDocument subDocument =null;
		
		if(sb1==null) {
			subDocument=subDocumentService.getOtherSubDocument(case_id, 1);
		}
		else {
			subDocument=sb1;
		}
		
		SubApplication sb =null;
		SubDocument  sd =null;
		sb =subDocumentService.getSubApplication(app_no,app_year,case_id);
		
		
		String returnview = "/casefile/viewCauseList";
		if (sb1 == null && sb!=null) 
		{
			model.addAttribute("listTypeName", ct.getClt_description());
			
			model.addAttribute("cl_serial_noCurrent", cl_temp);
		
			model.addAttribute("cl_court_noCurrent", cl_court_no);
			model.addAttribute("cl_rec_statusCurrent", cl_rec_status);
			
			model.addAttribute("listTypeName", ct.getClt_description());
			
			
			
			model.addAttribute("cl_list_type_midCurrent", cl_list_type_mid);
			
			model.addAttribute("cl_dolCurrent", cl_dol);
			if(clNext != null) {
				model.addAttribute("cl_court_no", clNext.getCl_court_no());
				model.addAttribute("cl_rec_status", clNext.getCl_rec_status());
				model.addAttribute("cl_serial_no", clNext.getCl_serial_no());
				model.addAttribute("cl_list_type_mid", clNext.getCl_list_type_mid());
				model.addAttribute("cl_ano", clNext.getCl_ano());
				model.addAttribute("cl_ayr", clNext.getCl_ayr());
				
				
				Long cldate =clNext.getCl_dol().getTime();
				model.addAttribute("cl_dol", cldate);
				model.addAttribute("cl_fd_mid", clNext.getCl_fd_mid());
				}
				
				if(clPrevious != null) {
					model.addAttribute("cl_court_no1", clPrevious.getCl_court_no());
					model.addAttribute("cl_rec_status1", clPrevious.getCl_rec_status());
					model.addAttribute("cl_serial_no1", clPrevious.getCl_serial_no());
					model.addAttribute("cl_list_type_mid1", clPrevious.getCl_list_type_mid());
					model.addAttribute("cl_ano1", clPrevious.getCl_ano());
					model.addAttribute("cl_ayr1", clPrevious.getCl_ayr());
					
					Long cldate =clPrevious.getCl_dol().getTime();
					model.addAttribute("cl_dol1", cldate);
					model.addAttribute("cl_fd_mid1", clPrevious.getCl_fd_mid());
					}
				model.addAttribute("cl_serial_noCurrent", cl_temp);
			
				model.addAttribute("cl_court_noCurrent", cl_court_no);
				model.addAttribute("cl_rec_statusCurrent", cl_rec_status);
				
				model.addAttribute("listTypeName", ct.getClt_description());
				
				
				
				model.addAttribute("cl_list_type_midCurrent", cl_list_type_mid);
				
				model.addAttribute("cl_dolCurrent", cl_dol);
			
				model.addAttribute("listTypeName", ct.getClt_description());
				System.out.println("eeeeeeeeeee"+model);
			
			if(sb != null) {
				model.addAttribute("cl_serial_noCurrent", cl_temp);
				sd =subDocumentService.getByPK(sb.getSb_ap_sd_mid());
				if(sd != null) {
					Long docId = sd.getSd_fd_mid();
					CaseFileDetail caseFileDetail1 = caseFileDetailService.getCaseFileDetail(docId);
					Date date = sb.getSb_ap_cr_date();

					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					String submitted_date = "";
					if (date != null) 
					{
						submitted_date = formatter.format(date);
					}
					if(clNext != null) {
						model.addAttribute("cl_court_no", clNext.getCl_court_no());
						model.addAttribute("cl_rec_status", clNext.getCl_rec_status());
						model.addAttribute("cl_serial_no", clNext.getCl_serial_no());
						model.addAttribute("cl_list_type_mid", clNext.getCl_list_type_mid());
						model.addAttribute("cl_ano", clNext.getCl_ano());
						model.addAttribute("cl_ayr", clNext.getCl_ayr());
						
						Long cldate =clNext.getCl_dol().getTime();
						model.addAttribute("cl_dol", cldate);
						model.addAttribute("cl_fd_mid", clNext.getCl_fd_mid());
						}
						
						if(clPrevious != null) {
							model.addAttribute("cl_court_no1", clPrevious.getCl_court_no());
							model.addAttribute("cl_rec_status1", clPrevious.getCl_rec_status());
							model.addAttribute("cl_serial_no1", clPrevious.getCl_serial_no());
							model.addAttribute("cl_list_type_mid1", clPrevious.getCl_list_type_mid());
							model.addAttribute("cl_ano1", clPrevious.getCl_ano());
							model.addAttribute("cl_ayr1", clPrevious.getCl_ayr());
							
							Long cldate =clPrevious.getCl_dol().getTime();
							model.addAttribute("cl_dol1", cldate);
							model.addAttribute("cl_fd_mid1", clPrevious.getCl_fd_mid());
							}
						

					model.addAttribute("doc_id", docId);
					model.addAttribute("document_name",sd.getSd_document_name());
					model.addAttribute("isApplication", 1);
					model.addAttribute("isImpugnedOrder", null);
					model.addAttribute("application_type", sb.getApplicationType().getAt_name());
					model.addAttribute("application_no",sb.getSb_ap_no());
					model.addAttribute("application_year",sb.getSb_ap_year());
					model.addAttribute("submitted_date", submitted_date);
					//model.addAttribute("party", subDocument.getSd_party());
					//model.addAttribute("name", subDocument.getSd_description());
				//	model.addAttribute("counsel", subDocument.getSd_counsel());
					
					Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
					/*String srcPath = lookupRepo.getLk_longname() + File.separator
							+ caseFileDetail1.getCaseType().getCt_label()
							+ File.separator + subDocument.getIndexField().getIf_name()
							+ File.separator + sb.getSb_ap_documentname()
							+ ".pdf";*/
					
					String srcPath = lookupRepo.getLk_longname() + File.separator
							+ caseFileDetail1.getCaseType().getCt_label()
							+ File.separator + "application"
							+ File.separator + sd.getSd_document_name()
							+ ".pdf";

					File source = new File(srcPath);

					String uploadPath = context.getRealPath("");
					File dest = new File(uploadPath + File.separator + "uploads"
							+ File.separator + sd.getSd_document_name()
							+ ".pdf");

					try {
						FileUtils.copyFile(source, dest);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					return returnview;
					
				}
				
				returnview = "/casefile/notfound";
				
				
			}
			
			returnview = "/casefile/notfound";
		} else if(sb1 !=null) {
			model.addAttribute("listTypeName", ct.getClt_description());
			
			model.addAttribute("cl_serial_noCurrent", cl_temp);
			
			model.addAttribute("cl_court_noCurrent", cl_court_no);
			model.addAttribute("cl_rec_statusCurrent", cl_rec_status);
			
			model.addAttribute("listTypeName", ct.getClt_description());
			
			
			
			model.addAttribute("cl_list_type_midCurrent", cl_list_type_mid);
			
			model.addAttribute("cl_dolCurrent", cl_dol);
			
			if(clNext != null) {
				model.addAttribute("cl_court_no", clNext.getCl_court_no());
				model.addAttribute("cl_rec_status", clNext.getCl_rec_status());
				model.addAttribute("cl_serial_no", clNext.getCl_serial_no());
				model.addAttribute("cl_list_type_mid", clNext.getCl_list_type_mid());
				model.addAttribute("cl_ano", clNext.getCl_ano());
				model.addAttribute("cl_ayr", clNext.getCl_ayr());
				
				Long cldate =clNext.getCl_dol().getTime();
				model.addAttribute("cl_dol", cldate);
				model.addAttribute("cl_fd_mid", clNext.getCl_fd_mid());
				}
				
				if(clPrevious != null) {
					model.addAttribute("cl_court_no1", clPrevious.getCl_court_no());
					model.addAttribute("cl_rec_status1", clPrevious.getCl_rec_status());
					model.addAttribute("cl_serial_no1", clPrevious.getCl_serial_no());
					model.addAttribute("cl_list_type_mid1", clPrevious.getCl_list_type_mid());
					model.addAttribute("cl_ano1", clPrevious.getCl_ano());
					model.addAttribute("cl_ayr1", clPrevious.getCl_ayr());
					
					Long cldate =clPrevious.getCl_dol().getTime();
					model.addAttribute("cl_dol1", cldate);
					model.addAttribute("cl_fd_mid1", clPrevious.getCl_fd_mid());
					}
			Long docId = subDocument.getSd_fd_mid();
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(docId);
			Date date = subDocument.getSd_submitted_date();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String submitted_date = "";
			if (date != null) {
				submitted_date = formatter.format(date);
			}
			model.addAttribute("listTypeName", ct.getClt_description());
			model.addAttribute("doc_id", docId);
			model.addAttribute("document_name",
					subDocument.getSd_document_name());
			model.addAttribute("isApplication", 1);
			model.addAttribute("isImpugnedOrder", null);
			model.addAttribute("application_type", subDocument
					.getDocumentType().getAt_name());
			model.addAttribute("application_no",
					subDocument.getSd_document_no());
			model.addAttribute("application_year",
					subDocument.getSd_document_year());
			model.addAttribute("submitted_date", submitted_date);
			model.addAttribute("party", subDocument.getSd_party());
			model.addAttribute("name", subDocument.getSd_description());
			model.addAttribute("counsel", subDocument.getSd_counsel());

			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + subDocument.getIndexField().getIf_name()
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf";

			File source = new File(srcPath);

			String uploadPath = context.getRealPath("");
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf");

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {

			model.addAttribute("listTypeName", ct.getClt_description());
			
			model.addAttribute("cl_serial_noCurrent", cl_temp);
			
			model.addAttribute("cl_court_noCurrent", cl_court_no);
			model.addAttribute("cl_rec_statusCurrent", cl_rec_status);
			
			model.addAttribute("listTypeName", ct.getClt_description());
			
			
			
			model.addAttribute("cl_list_type_midCurrent", cl_list_type_mid);
			
			model.addAttribute("cl_dolCurrent", cl_dol);
			
			if(clNext != null) {
				model.addAttribute("cl_court_no", clNext.getCl_court_no());
				model.addAttribute("cl_rec_status", clNext.getCl_rec_status());
				model.addAttribute("cl_serial_no", clNext.getCl_serial_no());
				model.addAttribute("cl_list_type_mid", clNext.getCl_list_type_mid());
				model.addAttribute("cl_ano", clNext.getCl_ano());
				model.addAttribute("cl_ayr", clNext.getCl_ayr());
				
				Long cldate =clNext.getCl_dol().getTime();
				model.addAttribute("cl_dol", cldate);
				model.addAttribute("cl_fd_mid", clNext.getCl_fd_mid());
				}
				
				if(clPrevious != null) {
					model.addAttribute("cl_court_no1", clPrevious.getCl_court_no());
					model.addAttribute("cl_rec_status1", clPrevious.getCl_rec_status());
					model.addAttribute("cl_serial_no1", clPrevious.getCl_serial_no());
					model.addAttribute("cl_list_type_mid1", clPrevious.getCl_list_type_mid());
					model.addAttribute("cl_ano1", clPrevious.getCl_ano());
					model.addAttribute("cl_ayr1", clPrevious.getCl_ayr());
					
					Long cldate =clPrevious.getCl_dol().getTime();
					model.addAttribute("cl_dol1", cldate);
					model.addAttribute("cl_fd_mid1", clPrevious.getCl_fd_mid());
					}
			Long docId = subDocument.getSd_fd_mid();
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(docId);
			Date date = subDocument.getSd_submitted_date();

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String submitted_date = "";
			if (date != null) {
				submitted_date = formatter.format(date);
			}
			model.addAttribute("listTypeName", ct.getClt_description());
			model.addAttribute("doc_id", docId);
			model.addAttribute("document_name",
					subDocument.getSd_document_name());
			
			model.addAttribute("isImpugnedOrder", null);
			model.addAttribute("submitted_date", submitted_date);
			model.addAttribute("party", subDocument.getSd_party());
			model.addAttribute("name", subDocument.getSd_description());
			model.addAttribute("counsel", subDocument.getSd_counsel());

			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + subDocument.getIndexField().getIf_name()
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf";

			File source = new File(srcPath);

			String uploadPath = context.getRealPath("");
			File dest = new File(uploadPath + File.separator + "uploads"
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf");

			try {
				FileUtils.copyFile(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return returnview;
	}

	
	@RequestMapping(value = "/getsubdocuments/{id}", method = RequestMethod.GET)
	public @ResponseBody String getSubDocuments(@PathVariable("id") Long fd_id,
			HttpSession session) {
		String jsonData = null;
		ActionResponse<SubDocument> response = new ActionResponse<>();

		List<SubDocument> subDocuments = null;
		subDocuments =subDocumentService.getSubDocuments(fd_id);
		
		//CaseFileDetail cfd =caseFileDetailService.getCaseFileDetail(fd_id);
		
		//RegularToDefective cfdconvert =null;
		
		
		
		
		
		
		//if(cfd != null) {
/*if(cfd.getFd_file_source().equals("P")) {
	// This case is moved from pdms old orders should be taken care of
	SubDocument lastOrder =subDocumentService.getLastOrderOfPendingCase(fd_id);
	
	if(lastOrder != null) {
		
		System.out.println("Last date of order ----------"+lastOrder.getSd_submitted_date());
	}
	
	subDocuments =subDocumentService.getSubDocumentsIncludingOrder(fd_id);
	
	
}
else {
	
	subDocuments =subDocumentService.getSubDocuments(fd_id);
}*/
			
	   
	//	}
		
		response.setResponse("TRUE");
		response.setModelList(subDocuments);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
		
	}
	
	@RequestMapping(value = "/getOrdersFromElegalix/{id}", method = RequestMethod.GET)
	public @ResponseBody String getOrdersFromElegalix(@PathVariable("id") Long fd_id,
			HttpSession session) {
		String jsonData = null;
		ActionResponse<SubDocument> response = new ActionResponse<>();

		List<SubDocument> subDocuments = new ArrayList<SubDocument>();
		
		CaseFileDetail cfd =caseFileDetailService.getCaseFileDetail(fd_id);
		
		RegularToDefective cfdconvertmain =null;
		
		CaseLkoToAldHistory cla=null;
		
		
		 Date sbDate =null;
		if(cfd != null) {
				if(cfd.getFd_file_source().trim().equals("P") || cfd.getFd_file_source().trim().equals("LKO")  ) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sDate1="2017-08-17 00:00:00"; 
					    
						try {
							sbDate = formatter.parse(sDate1);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					

				// get order from dms before 2010-01-01
				List<SubDocument> subDocumentfromdms =subDocumentService.getSubDocumentOrders(fd_id,sbDate);
				
				if(subDocumentfromdms != null) {

					subDocuments.addAll(subDocumentfromdms);
				}
				
				List<SubDocument> subDocumentTransfer = subDocumentService.getSubDocumentOrdersForTransfer(fd_id,
						sbDate);

				if (subDocumentTransfer != null) {
					subDocuments.addAll(subDocumentTransfer);
				}

				
				
				
			}
		
		String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+cfd.getCaseType().getCt_label()+"/"+cfd.getFd_case_no()+"/"+cfd.getFd_case_year();
		//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
			
		 System.out.println("uriiiiiiiiii"+uri);
	    RestTemplate restTemplate = new RestTemplate();
	   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
	    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
	    SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	    ResponseEntity<OrderFromElegalix[]> response1 =null;
	    ResponseEntity<Object> response2 =null;
	    List<OrderFromElegalix> orderListFromElegalix =null;
	    
	    try {
	    	response1 =
		    		  restTemplate.getForEntity(
		    				  uri,
		    		  OrderFromElegalix[].class);
	    		
	    	orderListFromElegalix   =	Arrays.asList(response1.getBody());

	    
	    System.out.println("Test Data"+orderListFromElegalix);
	   String date =sm.format(orderListFromElegalix.get(0).getJudgmentDate());
	   
	    }
	    catch(HttpClientErrorException e) {
	    	System.out.println("Response"+response1);
	    	
	    	
	    	e.printStackTrace();
	    	System.out.println("status------------------"+e.getStatusCode());
	    	System.out.println("Response------------------"+e.getResponseBodyAsString());
	    	
	    }
	    
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sDate1="2017-08-17 00:00:00"; 
			    
				try {
					sbDate = formatter.parse(sDate1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	   
	   // System.out.println("E"+date);
	    if(orderListFromElegalix != null) {
	    	for(OrderFromElegalix order:orderListFromElegalix) {
	    		if(order.getJudgmentDate().after(sbDate)) {
	 		   SubDocument sb =new SubDocument();
	 		   sb.setJudgmentID(order.getJudgmentID());
	 		   sb.setSd_document_id(100002);
	 		   sb.setSd_if_mid(39l);
	 		   sb.setSd_submitted_date(order.getJudgmentDate());
	 		   sb.setSd_rec_status(1);
	 		   sb.setSd_fd_mid(fd_id);
	 		   sb.setIndexField(masterService.getIndexField(39L));
	 		   sb.setChecked(false);
	 		   sb.setSd_nonmaintainable(false);
	 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
	 		   sb.setDocumentType(masterService.getApplicationById(100002));
	 		 sb.setSd_document_name(order.getJudgmentID().toString());
	 		 sb.setSubApplications(new ArrayList<SubApplication>());
	 		 
	 		subDocuments.add(sb);
	 		 
	    		}
	 		  
	 	   }
	    }
	    cla=caseFileDetailService.getTramsferdCase(cfd.getFd_case_type().intValue(), cfd.getFd_case_no().toString(), cfd.getFd_case_year().intValue());;
	    cfdconvertmain =caseFileDetailService.getConvertedCase(cfd.getFd_case_type().toString(), cfd.getFd_case_no().toString(), cfd.getFd_case_year().toString());
	    if(cla!=null) {
	    	

	    	

	    	
	    	/*CaseType ct1 =lkoMasterService.getCaseTypesLKOById(cla.getOld_case_type().longValue());*/
	    	
	    	CaseTypeLko ct1 =casetypeService.getByIdLko(cla.getOld_case_type().longValue());
	    	
	    	String uri1 = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/L/"+ct1.getCt_label()+"/"+cla.getOld_case_no()+"/"+cla.getOld_case_year();
			//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
				

		    RestTemplate restTemplate1 = new RestTemplate();
		   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
		    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
		    SimpleDateFormat sm1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		    ResponseEntity<OrderFromElegalix[]> re1 =null;
		    ResponseEntity<Object> re2 =null;
		    List<OrderFromElegalix> orderListFromElegalix1 =null;
		    
		    try {
		     
		   
		    
		    	re1 =
			    		  restTemplate1.getForEntity(
			    				  uri1,
			    		  OrderFromElegalix[].class);
		    		
		    	orderListFromElegalix1   =	Arrays.asList(re1.getBody());

		    
		  
		   String date =sm.format(orderListFromElegalix1.get(0).getJudgmentDate());
		   
		    }
		    catch(HttpClientErrorException e) {
		    	
		    	
		    	
		    	e.printStackTrace();
		    
		    	
		    	
		    	
			   /* 		  restTemplate1.getForEntity(
			    				  uri1,
			    		  Object.class);
		    		
		    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
		    	System.out.println("Response 2"+re2);*/
		    	
		    }
		   
		   // System.out.println("E"+date);
		    if( orderListFromElegalix1 != null) {
		    	for(OrderFromElegalix order:orderListFromElegalix1) {
		 		   SubDocument sb =new SubDocument();
		 		   sb.setJudgmentID(order.getJudgmentID());
		 		   sb.setSd_document_id(100002);
		 		   sb.setSd_if_mid(39l);
		 		   sb.setSd_submitted_date(order.getJudgmentDate());
		 		   sb.setSd_rec_status(1);
		 		   sb.setSd_fd_mid(fd_id);
		 		  sb.setChecked(false);
		 		   sb.setIndexField(masterService.getIndexField(39L));
		 		   sb.setSd_nonmaintainable(false);
		 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
		 		   sb.setDocumentType(masterService.getApplicationById(100002));
		 		 sb.setSd_document_name(order.getJudgmentID().toString());
		 		 sb.setSubApplications(new ArrayList<SubApplication>());
		 		 
		 		subDocuments.add(sb);
		 		 
		 		 
		 		  
		 	   }
		    }
	    	
	    
	    	
	    }
	    if(cfdconvertmain != null) {
	    	// again check order for elegalix for old case no 
	    	
	    	// This list is used when there more than converted cases
	    	List<RegularToDefective>  cfdconvertlist =new ArrayList<RegularToDefective>();
	    	
	    	cfdconvertlist.add(cfdconvertmain);
	    	
	    	RegularToDefective cfdconvert1  =caseFileDetailService.getConvertedCaseFromOld(cfdconvertmain.getRdh_case_type_old(),cfdconvertmain.getRdh_case_no_old(), cfdconvertmain.getRdh_case_year_old());
	    	
	    	
	    	if(cfdconvert1 != null) {
	    		cfdconvertlist.add(cfdconvert1);
	    		RegularToDefective cfdconvert2  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert1.getRdh_case_type_old(),cfdconvert1.getRdh_case_no_old(), cfdconvert1.getRdh_case_year_old());
		    	
		    	
		    	if(cfdconvert2 != null) {
		    		cfdconvertlist.add(cfdconvert2);
		    		RegularToDefective cfdconvert3  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert2.getRdh_case_type_old(),cfdconvert2.getRdh_case_no_old(), cfdconvert2.getRdh_case_year_old());
			    	
			    	
			    	if(cfdconvert3 != null) {
			    		cfdconvertlist.add(cfdconvert3);
			    		RegularToDefective cfdconvert4  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert3.getRdh_case_type_old(),cfdconvert3.getRdh_case_no_old(), cfdconvert3.getRdh_case_year_old());
				    	
				    	
				    	if(cfdconvert4 != null) {
				    		cfdconvertlist.add(cfdconvert4);
				    		
				    	}
			    		
			    	}
		    		
		    	}
	    	}
	    	

	    	for(RegularToDefective cfdconvert: cfdconvertlist) {
	    	

	    	
	    	CaseType ct1 =masterService.getCaseTypeById(Long.parseLong(cfdconvert.getRdh_case_type_old()));
	    	
	    	String uri1 = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+ct1.getCt_label()+"/"+cfdconvert.getRdh_case_no_old()+"/"+cfdconvert.getRdh_case_year_old();
			//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
				

		    RestTemplate restTemplate1 = new RestTemplate();
		   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
		    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
		    SimpleDateFormat sm1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		    ResponseEntity<OrderFromElegalix[]> re1 =null;
		    ResponseEntity<Object> re2 =null;
		    List<OrderFromElegalix> orderListFromElegalix1 =null;
		    
		    try {
		     
		   
		    
		    	re1 =
			    		  restTemplate1.getForEntity(
			    				  uri1,
			    		  OrderFromElegalix[].class);
		    		
		    	orderListFromElegalix1   =	Arrays.asList(re1.getBody());

		    
		  
		   String date =sm.format(orderListFromElegalix1.get(0).getJudgmentDate());
		   
		    }
		    catch(HttpClientErrorException e) {
		    	
		    	
		    	
		    	e.printStackTrace();
		    
		    	
		    	
		    	
			   /* 		  restTemplate1.getForEntity(
			    				  uri1,
			    		  Object.class);
		    		
		    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
		    	System.out.println("Response 2"+re2);*/
		    	
		    }
		   
		   // System.out.println("E"+date);
		    if( orderListFromElegalix1 != null) {
		    	for(OrderFromElegalix order:orderListFromElegalix1) {
		 		   SubDocument sb =new SubDocument();
		 		   sb.setJudgmentID(order.getJudgmentID());
		 		   sb.setSd_document_id(100002);
		 		   sb.setSd_if_mid(39l);
		 		   sb.setSd_submitted_date(order.getJudgmentDate());
		 		   sb.setSd_rec_status(1);
		 		   sb.setSd_fd_mid(fd_id);
		 		  sb.setChecked(false);
		 		   sb.setIndexField(masterService.getIndexField(39L));
		 		   sb.setSd_nonmaintainable(false);
		 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
		 		   sb.setDocumentType(masterService.getApplicationById(100002));
		 		 sb.setSd_document_name(order.getJudgmentID().toString());
		 		 sb.setSubApplications(new ArrayList<SubApplication>());
		 		 
		 		subDocuments.add(sb);
		 		 
		 		 
		 		  
		 	   }
		    }
	    	
	    }
	    }
	   
		}
		
		response.setResponse("TRUE");
		response.setModelList(subDocuments);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
		
	}
	
	/*@RequestMapping(value = "/getsubdocuments/{id}", method = RequestMethod.GET)
	public @ResponseBody String getSubDocuments(@PathVariable("id") Long fd_id,
			HttpSession session) {
		String jsonData = null;
		ActionResponse<SubDocument> response = new ActionResponse<>();

		List<SubDocument> subDocuments = subDocumentService.getSubDocuments(fd_id);
		response.setResponse("TRUE");
		response.setModelList(subDocuments);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}*/


	@RequestMapping(value = "/getcasefiledetails/{id}", method = RequestMethod.GET)
	public @ResponseBody String getcasefiledetails(
			@PathVariable("id") Long fd_id, HttpSession session) 
	{
		User user = (User) session.getAttribute("USER");
		Long userRole=user.getUserroles().get(0).getUr_role_id();
		
		
		String jsonData = null;
		Petitioner pt =null;
		Respondent rt =null;
		ActionResponse<CaseFileDetail> response = new ActionResponse<>();
		CaseFileDetail casefile = caseFileDetailService.getCaseFileDetail(fd_id);
		 
		pt=caseFileDetailService.getPetitionerBycase(fd_id);
		rt=caseFileDetailService.getRespondentBycase(fd_id);
		
		if(pt!=null && rt!=null)
		{
		casefile.setFirst_petitioner(pt.getPt_name());
		casefile.setFirst_respondent(rt.getRt_name());
		}

		/*-----get notification of application stage in (1000041) form Efiling-----*/
		if(user.getUserroles().get(0).getLk().getLk_longname().equals("ECOURT") 
				|| user.getUserroles().get(0).getLk().getLk_longname().equals("Judge") || user.getUserroles().get(0).getLk().getLk_longname().equals("DMSAdmin")
				|| user.getUserroles().get(0).getLk().getLk_longname().equals("Ecourt_Team"))
		{
			EfilingCaseFileDetail efilingcfd = amendmentService.getEilingCaseFileDetails(casefile.getFd_case_year().longValue(),
					casefile.getFd_case_type(),casefile.getFd_case_no());
			if(efilingcfd!=null)
			{
				List<EfilingApplication> eApplicatinoList = eilingApplicationService.getApplicationList(efilingcfd.getFd_id());
				List<EfilingApplication> ePendingApplicatinoList = eilingApplicationService.getPenidingApplicationList(efilingcfd.getFd_id());
				List<ApplicationCheckListMapping> appCheckListmapping=null;
				if(eApplicatinoList!=null)
				{
					for (int i = 0; i < eApplicatinoList.size(); i++) 
					{
						appCheckListmapping= eilingApplicationService.getApplicationCheckList(eApplicatinoList.get(i).getAp_id());
						eApplicatinoList.get(i).setAppCheckListMapping(appCheckListmapping);
					}
				}
				if(ePendingApplicatinoList !=null) {
					for (int i = 0; i < ePendingApplicatinoList.size(); i++) 
					{
						
						ePendingApplicatinoList.get(i).setFilingDate(eilingApplicationService.getApplicationSubmitDate(ePendingApplicatinoList.get(i).getAp_id()));
					}
				}
				casefile.setNotificationCount(eApplicatinoList.size());
				casefile.setEfilingApplicationList(eApplicatinoList);
				casefile.setPendingAppCount(ePendingApplicatinoList.size());
				casefile.setEfilingPendingApplicationList(ePendingApplicatinoList);
			}
		}
		
		/*-------End-------*/
		
		response.setResponse("TRUE");
		response.setModelData(casefile);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "/getorderreports/{id}", method = RequestMethod.GET)
	public @ResponseBody String getorderreports(@PathVariable("id") Long fd_id,
			HttpSession session) {
		String jsonData = null;
		ActionResponse<Object> response = new ActionResponse<>();
		List<Object> orderReportData = orderReportService.getOrderReports(fd_id);
		
		response.setResponse("TRUE");
		response.setModelList(orderReportData);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	
	
	@RequestMapping(value = "/getmedreciept/{id}", method = RequestMethod.GET)
	public @ResponseBody String getmedreciept(@PathVariable("id") Long fd_id,
			HttpSession session) {
		String jsonData = null;
		ActionResponse<MediationDocs> response = new ActionResponse<>();
		List<MediationDocs> medRecipt = orderReportService.getMedReports(fd_id);
		
		response.setResponse("TRUE");
		response.setModelList(medRecipt);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "/getmetadata/{id}", method = RequestMethod.GET)
	public @ResponseBody String getMetadata(@PathVariable("id") Long fd_id,
			HttpSession session) {
		String jsonData = null;
		ActionResponse<MetaData> response = new ActionResponse<>();

		List<MetaData> metadata = caseFileDetailService.getMetadata(fd_id);
		response.setResponse("TRUE");
		response.setModelList(metadata);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "/viewdocument/{id}", method = RequestMethod.GET)
	public void copysubdocument(@PathVariable("id") Long sd_id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String jsonData = null;
		/*
		 * ActionResponse<SubDocument> response=new ActionResponse<>();
		 * 
		 * SubDocument subDocument = subDocumentService.getByPK(sd_id);
		 * CaseFileDetail
		 * caseFileDetail=caseFileDetailService.getCaseFileDetail(
		 * subDocument.getSd_fd_mid()); User user=(User)
		 * session.getAttribute("USER");
		 * 
		 * Lookup lookupRepo=lookupService.getLookUpObject("REPOSITORYPATH");
		 * String
		 * srcPath=lookupRepo.getLk_longname()+File.separator+caseFileDetail
		 * .getCaseType
		 * ().getCt_label()+File.separator+subDocument.getIndexField(
		 * ).getIf_name
		 * ()+File.separator+subDocument.getSd_document_name()+".pdf";
		 * 
		 * File source = new File(srcPath);
		 * 
		 * String uploadPath = context.getRealPath(""); File dest = new
		 * File(uploadPath
		 * +File.separator+"uploads"+File.separator+subDocument.getSd_document_name
		 * ()+".pdf");
		 * 
		 * try { FileUtils.copyFile(source, dest); } catch (IOException e) {
		 * e.printStackTrace(); } response.setResponse("TRUE");
		 * response.setData(subDocument.getSd_document_name()+".pdf");
		 * jsonData=globalfunction.convert_to_json(response); return jsonData;
		 */
		SubDocument subDocument = subDocumentService.getByPK(sd_id);
		CaseFileDetail caseFileDetail = caseFileDetailService
				.getCaseFileDetail(subDocument.getSd_fd_mid());

		Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
		String srcPath = lookupRepo.getLk_longname() + File.separator
				+ caseFileDetail.getCaseType().getCt_label() + File.separator
				+ subDocument.getIndexField().getIf_name() + File.separator
				+ subDocument.getSd_document_name() + ".pdf";
		System.out.println("filename=" + srcPath);
		File source = new File(srcPath);
		// response.setContentType("application/pdf");
		// response.setHeader("Content-Disposition", "inline; filename='" +
		// srcPath + "'");
		// response.setContentLength((int) source.length());
		File file = new File(srcPath);
		response.setHeader("content-disposition", "inline");
		response.setContentType("application/pdf");
		response.setContentLength((int) file.length());

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
	}

	@RequestMapping(value = "/deletesubdocument/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteSubdocument(@PathVariable("id") Long id,HttpSession session) {
		ActionResponse<SubDocument> response = new ActionResponse<SubDocument>();
		String jsonData = null;
		User u = (User) session.getAttribute("USER");
		SubDocument sd = subDocumentService.getByPK(id);
		sd.setSd_rec_status(0);
		sd.setSd_mod_date(new Date());
		sd.setSd_mod_by(u.getUm_id());
		subDocumentService.save(sd);

		
		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;

	}
	
	@RequestMapping(value = "/editSubDoc", method = RequestMethod.POST)
	public @ResponseBody String editSubDoc(@RequestBody SubDocument subdocument, HttpSession session) 
	{
		ActionResponse<SubDocument> response = new ActionResponse<SubDocument>();
		String jsonData = null;
		User u = (User) session.getAttribute("USER");
		SubDocument sd = subDocumentService.getByPK(subdocument.getSd_id());
		sd.setSd_document_id(subdocument.getSd_document_id());
		sd.setSd_mod_date(new Date());
		sd.setSd_mod_by(u.getUm_id());
		subDocumentService.save(sd);
		
		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	

	@RequestMapping(value = "/replaceSubDocument", method = RequestMethod.POST)
	@ResponseBody
	public String replaceSubDocument(MultipartHttpServletRequest request,HttpSession session) throws PersistenceException, ValidationException 
	{
		ActionResponse<SubDocument> response = new ActionResponse<SubDocument>();
		String jsonData = null;
		User u = (User) session.getAttribute("USER");
		Long sd_id = Long.parseLong(request.getParameter("sd_id"));
		Long sd_fd_mid = Long.parseLong(request.getParameter("sd_fd_mid"));
		SubDocument sd = subDocumentService.getByPK(sd_id);
		String docname = sd.getSd_document_name();
		Lookup lookup = lookupService.getLookUpObject("REPOSITORYPATH");
		MultipartFile mpf = null;
		Iterator<String> itr = request.getFileNames();
		CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(sd_fd_mid);
		
		while (itr.hasNext()) 
		{
			mpf = request.getFile(itr.next());
			
			File oldFilename = new File(lookup.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + sd.getIndexField().getIf_name() + File.separator+docname+".pdf");
			
			File RenameOldFile = new File(lookup.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + sd.getIndexField().getIf_name() + File.separator+docname + "_OLD_"+"1"+".pdf");
			
			int filecount=1;
			while(RenameOldFile.exists())
			{
				RenameOldFile = new File(lookup.getLk_longname() + File.separator
						+ caseFileDetail.getCaseType().getCt_label()
						+ File.separator + sd.getIndexField().getIf_name() + File.separator+docname +
						"_OLD_"+filecount+".pdf");
				
				filecount++;	
			}
			
			oldFilename.renameTo(RenameOldFile);
			
			File newFile = new File(lookup.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + sd.getIndexField().getIf_name() + File.separator+docname+".pdf");
			try {
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(newFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);
		}
		return jsonData;
	}
	
	@RequestMapping(value = "/deleteofficereport/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String officereport(@PathVariable("id") Long id, HttpSession session) {
		ActionResponse<OrderReport> response = new ActionResponse<OrderReport>();
		String jsonData = null;
		User u = (User) session.getAttribute("USER");
		OrderReport or = orderReportService.getOrderReport(id);
		or.setOrd_rec_status(0);
		or.setOrd_mod_by(u.getUm_id());
		or.setOrd_mod_date(new Date());
		orderReportService.save(or);
		if (or.getSubDocument() != null) {
			SubDocument sd = subDocumentService.getByPK(or.getSubDocument()
					.getSd_id());
			sd.setSd_rec_status(0);
			sd.setSd_mod_date(new Date());
			sd.setSd_mod_by(u.getUm_id());
			subDocumentService.save(sd);
		}

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;

	}

	@RequestMapping(value = "/updatecasetype", method = RequestMethod.POST)
	public @ResponseBody String updateCaseType(HttpServletRequest request,HttpSession session) throws DocumentException 
	{
		ActionResponse<CaseFileDetail> response = new ActionResponse();
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		
		Long caseFileId = Long.parseLong(request.getParameter("fd_id"), 10);
		Long newCaseTypeId = Long.parseLong(request.getParameter("new_case_type"), 10);
		String newCaseNo = request.getParameter("new_case_no");
		String newCaseYear = request.getParameter("new_case_year");
		CaseFileDetail cfd = caseFileDetailService.getCaseFileDetail(caseFileId);
		
		if(cfd != null) {
			if(caseFileId != null && newCaseTypeId != null && newCaseYear != null ) {
				if(cfd.getFd_case_type().equals(caseFileId) &&  cfd.getFd_case_no().equals(newCaseNo) && cfd.getFd_case_year().equals(Integer.parseInt(newCaseYear))) {
					
					response.setResponse("FALSE");
					response.setData("SameCase");
			
				jsonData = globalfunction.convert_to_json(response);
				return jsonData;
					
				}
				else {
					
					
				}
			}
			
		}
		
		Long oldctId = cfd.getFd_case_type();
		String oldcaseType=cfd.getCaseType().getCt_label();
		String oldcaseNo =cfd.getFd_case_no();
		Integer oldcaseYear =cfd.getFd_case_year();
		
		List<SubDocument> subdocuments = subDocumentService.getAllSubDocuments(caseFileId);
		Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
		CaseType newCaseType = casetypeService.getById(newCaseTypeId);
		
		

		String documentname = cfd.getFd_document_name();
		documentname = documentname.replace(cfd.getCaseType().getCt_label(), newCaseType.getCt_label());
		documentname = documentname.replace(cfd.getFd_case_no(), newCaseNo);
		documentname = documentname.replace(cfd.getFd_case_year().toString(), newCaseYear);

		cfd.setFd_document_name(documentname);
		cfd.setFd_case_type(newCaseTypeId);
		cfd.setFd_case_no(newCaseNo);
		cfd.setFd_case_year(Integer.parseInt(newCaseYear));
		cfd.setFd_mod_date(new Date());
		cfd.setFd_mod_by(u.getUm_id());
		CaseFileDetail cd = caseFileDetailService.save(cfd);
		
		
		
	
		RegularToDefective rdh = new RegularToDefective();
		rdh.setRdh_case_no_old(oldcaseNo);
		rdh.setRdh_case_year_old(oldcaseYear.toString());
		rdh.setRdh_case_type_old(oldctId.toString());
		rdh.setRdh_fd_mid_old(cfd.getFd_id());
		rdh.setRdh_cr_by(u.getUm_id());
		rdh.setRdh_cr_date(new Date());
		rdh.setRdh_case_no(cd.getFd_case_no());
		rdh.setRdh_case_type(cd.getFd_case_type().toString());
		rdh.setRdh_case_year(cd.getFd_case_year().toString());
		rdh.setRdh_fd_mid_new(cd.getFd_id());
		caseFileDetailService.saveRegularToDefective(rdh);
		
		boolean copyflag = false;
		Integer newYear = null;
		
		
		System.out.println("oldctId--"+oldctId   +"oldcaseType--"+oldcaseType +"oldcaseNo--"+oldcaseNo + "oldcaseYear --"+oldcaseYear);
		for (SubDocument subDocument : subdocuments)
		{

			newYear = new Integer(newCaseYear);
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ cfd.getCaseType().getCt_label() + File.separator
					+ subDocument.getIndexField().getIf_name() + File.separator
					+ subDocument.getSd_document_name() + ".pdf";
			
			String filename = subDocument.getSd_document_name();
			filename = filename.replace(cfd.getCaseType().getCt_label(),newCaseType.getCt_label());
			filename = filename.replaceAll(oldcaseNo, newCaseNo);
			filename = filename.replaceAll(oldcaseYear.toString(), newCaseYear);
			
			/*filename = filename.replace(cfd.getCaseType().getCt_label(),newCaseType.getCt_label());
			filename = filename.replace(cfd.getFd_case_no().toString(), newCaseNo);
			filename = filename.replace(cfd.getFd_case_year().toString(),newCaseYear);*/
			
			
			
			String destPath = lookupRepo.getLk_longname() + File.separator
					+ newCaseType.getCt_label() + File.separator
					+ subDocument.getIndexField().getIf_name() + File.separator
					+ filename + ".pdf";
			subDocument.setSd_document_name(filename);

			File source = new File(srcPath);
			File dest = new File(destPath);
			try
			{
				if (!dest.exists()) 
				{
					// if copy of file does not exist in new case type folder
					FileUtils.copyFile(source, dest);
					subDocumentService.save(subDocument);
					source.delete();
					copyflag = true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (copyflag) 
		{
			if (cfd.getFd_file_source().equals("O"))
			{
				// update online case and add new regular case
				EfilingCaseFileDetail ecfd = caseFileDetailService.getEfilingCaseFileDetail(cfd.getFd_case_type(),cfd.getFd_case_no(), cfd.getFd_case_year());
				ecfd.setFd_rd_status("Defective_to_Regular");
				caseFileDetailService.saveCaseFileEfiling(ecfd);
				
				EfilingCaseFileDetail regularEcfd = new EfilingCaseFileDetail();
				regularEcfd.setFd_case_type(newCaseTypeId);
				regularEcfd.setFd_case_no(newCaseNo);
				regularEcfd.setFd_case_year(newYear);
				regularEcfd.setFd_first_petitioner(ecfd.getFd_first_petitioner());
				regularEcfd.setFd_first_respondent(ecfd.getFd_first_respondent());
				regularEcfd.setFd_cr_date(new Date());
				regularEcfd.setFd_rec_status(1);
				
				caseFileDetailService.saveCaseFileEfiling(regularEcfd);
				
			}
			
			response.setResponse("TRUE");
		} else {
			response.setResponse("FALSE");
		}
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	
	@RequestMapping(value = "/caseAssignTo", method = RequestMethod.POST)
	public @ResponseBody String caseAssignTo(HttpServletRequest request,HttpSession session) 
	{
		ActionResponse<CaseFileDetail> response = new ActionResponse();
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		Long caseFileId = Long.parseLong(request.getParameter("fd_id"));
		String user_id =	request.getParameter("fd_assign_to");
		Long caseAssingTo = Long.parseLong(user_id);
		CaseFileDetail cfd = caseFileDetailService.getCaseFileDetail(caseFileId);
		cfd.setFd_assign_to(caseAssingTo);
	    Date date = new Date();
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String predate = dateFormat.format(date); 
		
		 List<CaseFileDetail> casefileList = caseFileDetailService.getCaseListByUserAndDate(caseAssingTo,predate);	 
		 for (CaseFileDetail cf : casefileList)
		 {
			 cf.setFd_mod_date(null);
			 cf.setFd_assign_to(u.getUm_id());
			 CaseFileDetail cd = caseFileDetailService.save(cf);
		}
		cfd.setFd_mod_date(date);
		CaseFileDetail cd = caseFileDetailService.save(cfd);
		if(cd!=null)
		{
			response.setResponse("TRUE");
		}
		else
		{
			response.setResponse("false");
		}
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
	
	
	@RequestMapping(value = "/deletepetitioner/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deletepetitioner(@PathVariable("id") Long id,
			HttpSession session) {
		ActionResponse<Petitioner> response = new ActionResponse<Petitioner>();
		String jsonData = null;
		User u = (User) session.getAttribute("USER");
		Petitioner pet = caseFileDetailService.getPetitioner(id);
		pet.setPt_rec_status(0);
		pet.setPt_mod_by(u.getUm_id());
		pet.setPt_mod_date(new Date());
		caseFileDetailService.save(pet);

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;

	}

	@RequestMapping(value = "/deleterespondent/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleterespondent(@PathVariable("id") Long id,
			HttpSession session) {
		ActionResponse<Respondent> response = new ActionResponse<Respondent>();
		String jsonData = null;
		User u = (User) session.getAttribute("USER");
		Respondent res = caseFileDetailService.getRespondent(id);
		res.setRt_rec_status(0);
		res.setRt_mod_by(u.getUm_id());
		res.setRt_mod_date(new Date());
		caseFileDetailService.save(res);

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;

	}

	@RequestMapping(value = "/deletepcounsel/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deletepcounsel(@PathVariable("id") Long id,
			HttpSession session) {
		ActionResponse<PetitionerCounsel> response = new ActionResponse<PetitionerCounsel>();
		String jsonData = null;
		User u = (User) session.getAttribute("USER");
		PetitionerCounsel pc = caseFileDetailService.getPetitionerCounsel(id);
		pc.setPc_rec_status(0);
		pc.setPc_mod_by(u.getUm_id());
		pc.setPc_mod_date(new Date());
		caseFileDetailService.save(pc);

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;

	}

	@RequestMapping(value = "/deletercounsel/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deletercounsel(@PathVariable("id") Long id,
			HttpSession session) {
		ActionResponse<RespondentCounsel> response = new ActionResponse<RespondentCounsel>();
		String jsonData = null;
		User u = (User) session.getAttribute("USER");
		RespondentCounsel rc = caseFileDetailService.getRespondentCounsel(id);
		rc.setRc_rec_status(0);
		caseFileDetailService.save(rc);

		response.setResponse("TRUE");
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;

	}

	@RequestMapping(value = "/uploadpetition", method = RequestMethod.POST)
	public @ResponseBody String uploadPetition(MultipartHttpServletRequest request, HttpSession session)throws DocumentException 
	{
		ActionResponse<SubDocument> response = new ActionResponse();
		response.setResponse("FALSE");
		User u = (User) session.getAttribute("USER");
		String jsonData = "";
		Lookup lookup = lookupService.getLookUpObject("REPOSITORYPATH");
		Long caseFileId = Long.parseLong(request.getParameter("fd_id"), 10);
		SubDocument sd = subDocumentService.getPetitionSubDocument(caseFileId,1);
		if (sd != null) {
			sd.setSd_rec_status(0);
			sd = subDocumentService.save(sd);
		} else {
			sd = subDocumentService.getPetitionSubDocument(caseFileId, 0);
		}
		MultipartFile mpf = null;
		Iterator<String> itr = request.getFileNames();
		String newfilepath = "";
		CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(caseFileId);
		IndexField indexField = masterService.getIndexField(1L);
		Integer count = subDocumentService.getCount(caseFileId);
		count = count + 1;

		while (itr.hasNext()) {
			mpf = request.getFile(itr.next());
			String filename = caseFileDetail.getFd_document_name() + "_"
					+ indexField.getIf_type_code() + "_" + count;
			newfilepath = lookup.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + indexField.getIf_name() + File.separator
					+ filename + ".pdf";
			SubDocument subDocument = new SubDocument();
			subDocument.setSd_cr_by(u.getUm_id());
			subDocument.setSd_cr_date(new Date());
			subDocument.setSd_fd_mid(caseFileId);
			subDocument.setSd_if_mid(1L);
			subDocument.setSd_document_name(filename);
			subDocument.setSd_document_id(sd.getSd_document_id());
			subDocument.setSd_submitted_date(sd.getSd_submitted_date());
			subDocument.setSd_rec_status(1);
			subDocument.setSd_document_no(sd.getSd_document_no());
			subDocument.setSd_document_year(sd.getSd_document_year());
			subDocument.setSd_counsel(sd.getSd_counsel());
			subDocument.setSd_description(sd.getSd_description());
			subDocument.setSd_party(sd.getSd_party());
			try {
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(
						newfilepath));
				File source = new File(newfilepath);
				PdfReader reader = new PdfReader(source.getAbsolutePath());
				Integer no_of_pages = reader.getNumberOfPages();
				subDocument.setSd_no_of_pages(no_of_pages);

				subDocument = subDocumentService.save(subDocument);
				List<ApplicationWithPetition> ApplicationWithPetition = caseFileDetailService.getApplicationWithPetiton(subDocument.getSd_fd_mid());  
				for (ApplicationWithPetition awp : ApplicationWithPetition)
				{
					awp.setAwp_sd_mid(subDocument.getSd_id());
					caseFileDetailService.save(awp);
				
				}
				
				
				
				
				
				reader.close();
				response.setResponse("TRUE");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	@RequestMapping(value = "/addfiles", method = RequestMethod.POST)
	public @ResponseBody String addfiles(@RequestBody DownloadModel downloadModel, HttpSession session,HttpServletRequest request) 
	{
		String jsonData = "";
		ActionResponse<DownloadModel> response = new ActionResponse<>();
		User u = (User) session.getAttribute("USER");
		List<OrderReport> orderReports = downloadModel.getOrderReports();
		List<SubDocument> subDocuments = downloadModel.getSubDocuments();
		String ipaddress = request.getRemoteAddr();
		DownloadReport dr = new DownloadReport();
		Integer pages = 0;
		Double dr_amount = 0.00;
		Long drFd=null;
		List<DownloadReport> downloadreports= downloadService.getDownloadReport(downloadModel.getFd_id());
		
		if (!subDocuments.isEmpty() || !orderReports.isEmpty()) {
			dr.setDr_cr_date(new Date());
			dr.setDr_fd_mid(downloadModel.getFd_id());
			dr.setDr_cr_by(u.getUm_id());
			dr.setDr_rec_status(1);
			dr.setDr_ip_address(ipaddress);
			dr = downloadService.saveReport(dr);
			response.setResponse("TRUE");
			response.setData("Files added for downloading");
		} else {
			response.setResponse("FALSE");
			response.setData("Please select files for downloading");
		}
		if (!subDocuments.isEmpty()) {
			for (SubDocument subDocument : subDocuments) {
				DownloadFile df = new DownloadFile();
				df.setDf_dr_mid(dr.getDr_id());
				df.setDf_sd_mid(subDocument.getSd_id());
				df.setDf_pages(subDocument.getSd_no_of_pages());
				df.setDf_submitted_date(subDocument.getSd_submitted_date());
				df = downloadService.saveFile(df);
				pages = pages + subDocument.getSd_no_of_pages();
			}
		}
		if (!orderReports.isEmpty()) {
			for (OrderReport orderReport : orderReports) {
				DownloadFile df = new DownloadFile();
				df.setDf_dr_mid(dr.getDr_id());
				df.setDf_ord_mid(orderReport.getOrd_id());
				df.setDf_pages(1);
				df.setDf_submitted_date(orderReport.getOrd_created());
				df = downloadService.saveFile(df);
				pages = pages + 1;
				if (orderReport.getSubDocument() != null) {
					SubDocument subDocument = orderReport.getSubDocument();
					DownloadFile df2 = new DownloadFile();
					df2.setDf_dr_mid(dr.getDr_id());
					df2.setDf_sd_mid(subDocument.getSd_id());
					df2.setDf_pages(subDocument.getSd_no_of_pages());
					df2.setDf_submitted_date(subDocument.getSd_submitted_date());
					downloadService.saveFile(df2);
					pages = pages + subDocument.getSd_no_of_pages();
					
					
				}
			}
		}
		// change by afnan start 
		
		for (DownloadReport dlr : downloadreports) 
		{
			drFd =dlr.getDr_fd_mid();
		}
		if(drFd!=null)
		{
			Double amount3 = Math.ceil(pages * 2.0);
			dr_amount =amount3;
		}
		
			
		else
		{
			
		if (pages > 0) {
			if (pages <= 10) {
				dr_amount = 15.00;
			} else {
				Double amount1 = 15.00;
				Integer pagesremaining = pages - 10;
				
				/*Double amount2 = Math.ceil(pagesremaining / 5.0) * 10;*/  // more then 10 like 11 consider as 20 amount 
				Double amount2 = Math.ceil(pagesremaining * 2.0);  // 9.2 consider as 10 amount
				dr_amount = amount1 + amount2;
			}
		}
		
		}
		
		// change by afnan end	
		
		dr.setDr_amount(dr_amount);
		downloadService.saveReport(dr);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}

	@RequestMapping(value = "/getdownloadhistory/{id}", method = RequestMethod.GET)
	public @ResponseBody String getdownloadhistory(
			@PathVariable("id") Long fd_id, HttpSession session) {
		String jsonData = null;
		ActionResponse<DownloadReport> response = new ActionResponse<>();

		List<DownloadReport> reports = downloadService.getDownloadReport(fd_id);
		response.setResponse("TRUE");
		response.setModelList(reports);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "/getdownloadedfiles/{id}", method = RequestMethod.GET)
	public @ResponseBody String getdownloadedfiles(
			@PathVariable("id") Long dr_id, HttpSession session) {
		String jsonData = null;
		ActionResponse<DownloadFile> response = new ActionResponse<>();

		List<DownloadFile> files = downloadService.getFiles(dr_id);
		response.setResponse("TRUE");
		response.setModelList(files);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}

	@RequestMapping(value = "/downloadfile/{id}", method = RequestMethod.GET)
	public void downloadfiles(@PathVariable("id") Long dr_id, HttpSession session, HttpServletResponse response) {
		String jsonData = null;

		///////// sushant

		DownloadReport report = downloadService.getById(dr_id);

		String pdfname = null;

		if (report.getDr_rec_status() == 1) {
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			Lookup lookupDownload = lookupService.getLookUpObject("DOWNLOADPATH");
			CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(report.getDr_fd_mid());
			String basePath = lookupRepo.getLk_longname() + File.separator + caseFileDetail.getCaseType().getCt_label();

			List<InputStream> list = new ArrayList<InputStream>();

			List<DownloadFile> files = downloadService.getFiles(report.getDr_id());
			String downloadFolder = lookupDownload.getLk_longname() + File.separator
					+ caseFileDetail.getFd_document_name();
			File dir = new File(downloadFolder);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String outputFilePath = downloadFolder + File.separator + File.separator
					+ caseFileDetail.getFd_document_name();
			OutputStream out;
			try {
				out = new FileOutputStream(new File(outputFilePath + ".pdf"));

				for (DownloadFile file : files) {
					if (file.getDf_sd_mid() != null) {
						SubDocument subDocument = file.getSubDocument();
						String srcPath = basePath + File.separator + subDocument.getIndexField().getIf_name()
								+ File.separator + subDocument.getSd_document_name() + ".pdf";

						String coverName = downloadFolder + File.separator + subDocument.getSd_id() + ".pdf";

						if (subDocument.getSd_if_mid() != 39 && subDocument.getSd_if_mid() != 1) {
							createCoverPdf(subDocument, caseFileDetail, coverName);
							list.add(new FileInputStream(new File(coverName)));
						}
						list.add(new FileInputStream(new File(srcPath)));

					}
					if (file.getDf_ord_mid() != null) {
						pdfname = downloadFolder + File.separator + file.getDf_id() + ".pdf";
						createOfficeRptPdf(file.getOrdeReport(), pdfname);
						// PDFCreator.createPDF(file.getOrdeReport(), pdfname);
						list.add(new FileInputStream(new File(pdfname)));
					}
				}
				//PDFMerger.doMerge(list, out);
				PDFMerger.doMerge(list, out,true);

				for (DownloadFile file : files) {
					if (file.getDf_ord_mid() != null) {
						pdfname = downloadFolder + File.separator + file.getDf_id() + ".pdf";

						File dFile = new File(pdfname);

						dFile.delete();

					}

					if (file.getDf_sd_mid() != null) {
						SubDocument subDocument = file.getSubDocument();

						String coverName = downloadFolder + File.separator + subDocument.getSd_id() + ".pdf";

						if (subDocument.getSd_if_mid() != 39 && subDocument.getSd_if_mid() != 1) {
							// createCoverPdf(subDocument,caseFileDetail, coverName);
							File cFile = new File(coverName);

							cFile.delete();
						}

					}

					/*
					 * SubDocument subDocument = file.getSubDocument();
					 * 
					 * String coverName=downloadFolder + File.separator + subDocument.getSd_id() +
					 * ".pdf";
					 * 
					 * if(subDocument.getSd_if_mid()!=39 && subDocument.getSd_if_mid()!=1) {
					 * //createCoverPdf(subDocument,caseFileDetail, coverName); File cFile=new
					 * File(coverName); cFile.delete();
					 * 
					 * 
					 * String coverName=downloadFolder + File.separator +
					 * file.getSubDocument().getSd_id() + ".pdf"; File cFile=new File(coverName);
					 * cFile.delete();
					 * 
					 * }
					 */
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				globalfunction.zipFolder(dir.getAbsolutePath(), dir.getAbsolutePath() + ".zip");
				response.setContentType("application/zip");
				PrintWriter out1 = response.getWriter();
				String filename = dir.getName() + ".zip";
				String filepath = dir.getAbsolutePath() + ".zip";
				File zipFile = new File(dir + ".zip");

				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

				FileInputStream fileInputStream = new FileInputStream(filepath);

				int i;
				while ((i = fileInputStream.read()) != -1) {
					out1.write(i);
				}
				fileInputStream.close();
				out1.close();
				// FileUtils.deleteDirectory(dir);
				zipFile.delete();
				dir.delete();
				report.setDr_rec_status(2);
				downloadService.saveReport(report);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public void createCoverPdf(SubDocument subDoc, CaseFileDetail caseFile, String path) throws IOException {
		Document doc = new Document();

		HTMLWorker htmlWorker = new HTMLWorker(doc);

		Font underlin = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLDITALIC);

		Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

		PdfWriter writer;

		Image img;

		try {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(path));

			doc.open();

			/*
			 * try {
			 * 
			 * //Get waterMarkImage from some URL Image waterMarkImage =
			 * Image.getInstance("/dms/src/main/webapp/images/demo.gif");
			 * 
			 * //Get width and height of whole page float pdfPageWidth =
			 * doc.getPageSize().getWidth(); float pdfPageHeight =
			 * doc.getPageSize().getHeight();
			 * 
			 * //Set waterMarkImage on whole page
			 * writer.getDirectContentUnder().addImage(waterMarkImage, pdfPageWidth, 0, 0,
			 * pdfPageHeight, 0, 0); } catch (BadElementException e1) { // TODO
			 * Auto-generated catch block e1.printStackTrace(); }
			 */

			Chunk title1 = new Chunk(subDoc.getDocumentType().getAt_name() + "", underlin);

			title1.setUnderline(0.1f, -2f);

			Paragraph title = new Paragraph(title1);
			title.setAlignment(Element.ALIGN_CENTER);
			//title.setFont(underlin);

			doc.add(title);

			//htmlWorker.parse(new StringReader("<br><br>"+.getOrd_remark()));

			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);

			//usermaster.getByuserid(officeRpt.getOrd_created_by());

			Chunk glue = new Chunk(new VerticalPositionMark());
			Paragraph p = new Paragraph("Case Type", boldFont);
			/* p.add("\n\n\n"+caseFile.getCaseType().getCt_name()); */
			p.add(new Chunk(glue));
			p.add(" Case No");
			p.add(new Chunk(glue));
			p.add("Case Year");

			doc.add(p);
			String createdDate = caseFile.getCaseType().getCt_name();
			Paragraph p1 = new Paragraph(createdDate);
			// p.setAlignment(Element.ALIGN_RIGHT);
			p1.add(new Chunk(glue));
			p1.add("" + caseFile.getFd_case_no());
			p1.add(new Chunk(glue));
			p1.add("" + caseFile.getFd_case_year());
			//p1.add(createdUser.getUm_fullname());

			doc.add(p1);

			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);

			Chunk glue2 = new Chunk(new VerticalPositionMark());
			Paragraph ap = new Paragraph("Application No", boldFont);
			/* p.add("\n\n\n"+caseFile.getCaseType().getCt_name()); */
			ap.add(new Chunk(glue2));
			ap.add("Application Year");
			ap.add(new Chunk(glue2));
			ap.add("Submitted Date");

			doc.add(ap);
			String appNo = subDoc.getSd_document_no() + "";
			Paragraph ap1 = new Paragraph(appNo);
			ap1.add(new Chunk(glue2));
			ap1.add("" + subDoc.getSd_document_year());
			ap1.add(new Chunk(glue2));
			ap1.add(DateFormat.getDateInstance().format(subDoc.getSd_submitted_date()));
			//p1.add(createdUser.getUm_fullname());

			doc.add(ap1);

			doc.close();
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (com.itextpdf.text.DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	@RequestMapping(value = "/downloadCauseList", method = RequestMethod.GET)
	public void downloadCauseList(@RequestParam("cl_court_no") String cl_court_no ,@RequestParam("cl_list_type_mid") String cl_list_type_mid,@RequestParam("cl_dol") String cl_dol,
			HttpSession session, HttpServletResponse response) {
		
		CauseList cl =new CauseList();
		
		//Date cl_dol1 =	new Date(Long.valueOf(cl_dol));
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern);
		
		//String cl_date1 =simpleDateFormat1.format(cl_dol1);
		String clFolder =null;
		Date cl_dol2 =null; 
		
		
		try {
			cl_dol2 =simpleDateFormat1.parse(cl_dol);
		} catch (ParseException e2) {
			
			e2.printStackTrace();
		}
		Long listType =null;
		Integer court =Integer.parseInt(cl_court_no);
		if(!cl_list_type_mid.equals("null")) {
			listType =Long.parseLong(cl_list_type_mid);
		}
		
		cl.setCl_court_no(court);
		cl.setCl_dol(cl_dol2);
		cl.setCl_list_type_mid(listType);
		
		CourtMaster cm =causeListService.getCourMaster(court);
		String jsonData = null;
		//Long fd_id =1l;
		OrderReport officeRpt= null;
		//DownloadReport report = downloadService.getById(dr_id);
		Lookup lookupDownload = lookupService
				.getLookUpObject("DOWNLOADPATH");
		
	 clFolder = lookupDownload.getLk_longname()
				+ File.separator + cm.getCm_name();
		File CausListFolder = new File(clFolder);
		if (!CausListFolder.exists()) {
			CausListFolder.mkdir();
		}
		
		List<CauseList> clList =causeListService.getList(cl);

		for(int j =0 ; j < clList.size() ; j++) {
		
			;
			
		if(clList.get(j).getCl_fd_mid() != null) {
			
			CaseFileDetail cfd =caseFileDetailService.getCaseFileDetail(clList.get(j).getCl_fd_mid());
			if(cfd.getFd_file_source().trim().equals("O")) {
			String  downloadFolder = clFolder
						+ File.separator + clList.get(j).getClType().getClt_description();
				File dir1 = new File(downloadFolder);
				if (!dir1.exists()) {
					dir1.mkdir();
				}
			
			Long fd_id =clList.get(j).getCl_fd_mid() ;
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			/*Lookup lookupDownload = lookupService
					.getLookUpObject("DOWNLOADPATH");*/
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(fd_id);
			String basePath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label();

			List<InputStream> list = new ArrayList<InputStream>();
			
			Boolean ofcRpt=false;
			String newOfficePdf=null;
			String uploadPath = context.getRealPath("");
			
			List<OrderReport> officeReports;
			RegularToDefective cfdconvertmain =null;
			
			//int lastIteration=0;
			
			Date lastSubmittedDate=null;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

			List<SubDocument> subDocuments = downloadService.getSubFiles(fd_id);
			
			 Date sbDate =null;
			if(caseFileDetail != null) {
				/*	if(cfd.getFd_file_source().equals("P") && cfd.getFd_case_year().intValue() < 2010) {*/
				if(caseFileDetail.getFd_file_source().trim().equals("P")) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sDate1="2017-08-17 00:00:00"; 
						    
							try {
								sbDate = formatter.parse(sDate1);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						

					// get order from dms before 2010-01-01
					List<SubDocument> subDocumentfromdms =subDocumentService.getSubDocumentOrders(fd_id,sbDate);
					
					if(subDocumentfromdms != null) {

						subDocuments.addAll(subDocumentfromdms);
						
					}
					
					
					
					
					
				}
				
				String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+caseFileDetail.getCaseType().getCt_label()+"/"+caseFileDetail.getFd_case_no()+"/"+caseFileDetail.getFd_case_year();
				//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
					
				
			    RestTemplate restTemplate = new RestTemplate();
			   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
			    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
			    SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			    ResponseEntity<OrderFromElegalix[]> response1 =null;
			    ResponseEntity<Object> response2 =null;
			    List<OrderFromElegalix> orderListFromElegalix =null;
			    
			    try {
			     
			   
			    
			    	response1 =
				    		  restTemplate.getForEntity(
				    				  uri,
				    		  OrderFromElegalix[].class);
			    		
			    	orderListFromElegalix   =	Arrays.asList(response1.getBody());

			    
			 
			   String date =sm.format(orderListFromElegalix.get(0).getJudgmentDate());
			   
			    }
			    catch(HttpClientErrorException e) {
			    	
			    	
			    	
			    	e.printStackTrace();
			    	
			    	
			    	
			    	
				    		/*  restTemplate.getForEntity(
				    				  uri,
				    		  Object.class);
			    		
			    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
			    	System.out.println("Response 2"+response2);*/
			    	
			    }
			   
			   // System.out.println("E"+date);
			    SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date d=null;
				try {
					d = s.parse("2017-08-17 00:00:00");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    if(orderListFromElegalix != null) {
			    	for(OrderFromElegalix order:orderListFromElegalix) {
			    		if(order.getJudgmentDate().after(d)) {
			 		   SubDocument sb =new SubDocument();
			 		   sb.setJudgmentID(order.getJudgmentID());
			 		   sb.setSd_document_id(100002);
			 		   sb.setSd_if_mid(39l);
			 		   sb.setSd_submitted_date(order.getJudgmentDate());
			 		   sb.setSd_rec_status(1);
			 		   sb.setSd_fd_mid(fd_id);
			 		   sb.setIndexField(masterService.getIndexField(39L));
			 		   sb.setChecked(false);
			 		   sb.setSd_nonmaintainable(false);
			 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
			 		   sb.setDocumentType(masterService.getApplicationById(100002));
			 		 sb.setSd_document_name(order.getJudgmentID().toString());
			 		 sb.setSubApplications(new ArrayList<SubApplication>());
			 		 
			 		subDocuments.add(sb);
			 		
			 
					downloadFile(sb.getJudgmentID(), uploadPath);
			 		 
			 		 
			    		} 
			 	   }
			    	
			    }
			    
			    CaseLkoToAldHistory cla=null;
			    
			    cla=caseFileDetailService.getTramsferdCase(caseFileDetail.getFd_case_type().intValue(), caseFileDetail.getFd_case_no().toString(), caseFileDetail.getFd_case_year().intValue());;
			    //cfdconvertmain =caseFileDetailService.getConvertedCase(cfd.getFd_case_type().toString(), cfd.getFd_case_no().toString(), cfd.getFd_case_year().toString());
			    if(cla!=null) {
			    	

			    	

			    	
			    	/*CaseType ct1 =lkoMasterService.getCaseTypesLKOById(cla.getOld_case_type().longValue());*/
			    	
			    	CaseTypeLko ct1 =casetypeService.getByIdLko(cla.getOld_case_type().longValue());
			    	
			    	String uri1 = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/L/"+ct1.getCt_label()+"/"+cla.getOld_case_no()+"/"+cla.getOld_case_year();
					//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
						

				    RestTemplate restTemplate1 = new RestTemplate();
				   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
				    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
				    SimpleDateFormat sm1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				    ResponseEntity<OrderFromElegalix[]> re1 =null;
				    ResponseEntity<Object> re2 =null;
				    List<OrderFromElegalix> orderListFromElegalix1 =null;
				    
				    try {
				     
				   
				    
				    	re1 =
					    		  restTemplate1.getForEntity(
					    				  uri1,
					    		  OrderFromElegalix[].class);
				    		
				    	orderListFromElegalix1   =	Arrays.asList(re1.getBody());

				    
				  
				   String date =sm.format(orderListFromElegalix1.get(0).getJudgmentDate());
				   
				    }
				    catch(HttpClientErrorException e) {
				    	
				    	
				    	
				    	e.printStackTrace();
				    
				    	
				    	
				    	
					   /* 		  restTemplate1.getForEntity(
					    				  uri1,
					    		  Object.class);
				    		
				    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
				    	System.out.println("Response 2"+re2);*/
				    	
				    }
				   
				   // System.out.println("E"+date);
				    if( orderListFromElegalix1 != null) {
				    	for(OrderFromElegalix order:orderListFromElegalix1) {
				 		   SubDocument sb =new SubDocument();
				 		   sb.setJudgmentID(order.getJudgmentID());
				 		   sb.setSd_document_id(100002);
				 		   sb.setSd_if_mid(39l);
				 		   sb.setSd_submitted_date(order.getJudgmentDate());
				 		   sb.setSd_rec_status(1);
				 		   sb.setSd_fd_mid(fd_id);
				 		  sb.setChecked(false);
				 		   sb.setIndexField(masterService.getIndexField(39L));
				 		   sb.setSd_nonmaintainable(false);
				 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
				 		   sb.setDocumentType(masterService.getApplicationById(100002));
				 		 sb.setSd_document_name(order.getJudgmentID().toString());
				 		 sb.setSubApplications(new ArrayList<SubApplication>());
				 		 
				 		subDocuments.add(sb);
				 		
				 		downloadFile(sb.getJudgmentID(), uploadPath);
				 		 
				 		 
				 		  
				 	   }
				    }
			    	
			    
			    	
			    }
			    
			    cfdconvertmain =caseFileDetailService.getConvertedCase(caseFileDetail.getFd_case_type().toString(), caseFileDetail.getFd_case_no().toString(), caseFileDetail.getFd_case_year().toString());
			    if(cfdconvertmain != null) {
			    	// again check order for elegalix for old case no 
			    	
			    	// This list is used when there more than converted cases
			    	List<RegularToDefective>  cfdconvertlist =new ArrayList<RegularToDefective>();
			    	
			    	cfdconvertlist.add(cfdconvertmain);
			    	
			    	RegularToDefective cfdconvert1  =caseFileDetailService.getConvertedCaseFromOld(cfdconvertmain.getRdh_case_type_old(),cfdconvertmain.getRdh_case_no_old(), cfdconvertmain.getRdh_case_year_old());
			    	
			    	
			    	if(cfdconvert1 != null) {
			    		cfdconvertlist.add(cfdconvert1);
			    		RegularToDefective cfdconvert2  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert1.getRdh_case_type_old(),cfdconvert1.getRdh_case_no_old(), cfdconvert1.getRdh_case_year_old());
				    	
				    	
				    	if(cfdconvert2 != null) {
				    		cfdconvertlist.add(cfdconvert2);
				    		RegularToDefective cfdconvert3  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert2.getRdh_case_type_old(),cfdconvert2.getRdh_case_no_old(), cfdconvert2.getRdh_case_year_old());
					    	
					    	
					    	if(cfdconvert3 != null) {
					    		cfdconvertlist.add(cfdconvert3);
					    		RegularToDefective cfdconvert4  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert3.getRdh_case_type_old(),cfdconvert3.getRdh_case_no_old(), cfdconvert3.getRdh_case_year_old());
						    	
						    	
						    	if(cfdconvert4 != null) {
						    		cfdconvertlist.add(cfdconvert4);
						    		
						    	}
					    		
					    	}
				    		
				    	}
			    	}
			    	

			    	for(RegularToDefective cfdconvert: cfdconvertlist) {
			    	

			    	
			    	CaseType ct1 =masterService.getCaseTypeById(Long.parseLong(cfdconvert.getRdh_case_type_old()));
			    	
			    	String uri1 = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+ct1.getCt_label()+"/"+cfdconvert.getRdh_case_no_old()+"/"+cfdconvert.getRdh_case_year_old();
					//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
					
				    RestTemplate restTemplate1 = new RestTemplate();
				   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
				    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
				    SimpleDateFormat sm1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				    ResponseEntity<OrderFromElegalix[]> re1 =null;
				    ResponseEntity<Object> re2 =null;
				    List<OrderFromElegalix> orderListFromElegalix1 =null;
				    
				    try {
				     
				   
				    
				    	re1 =
					    		  restTemplate1.getForEntity(
					    				  uri1,
					    		  OrderFromElegalix[].class);
				    		
				    	orderListFromElegalix1   =	Arrays.asList(re1.getBody());

				    
				 
				   String date =sm.format(orderListFromElegalix1.get(0).getJudgmentDate());
				   
				    }
				    catch(HttpClientErrorException e) {
				    
				    	
				    	
				    	e.printStackTrace();
				    
				    	
				    	
				    	
					   /* 		  restTemplate1.getForEntity(
					    				  uri1,
					    		  Object.class);
				    		
				    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
				    	System.out.println("Response 2"+re2);*/
				    	
				    }
				   
				   // System.out.println("E"+date);
				    if( orderListFromElegalix1 != null) {
				    	for(OrderFromElegalix order:orderListFromElegalix1) {
				 		   SubDocument sb =new SubDocument();
				 		   sb.setJudgmentID(order.getJudgmentID());
				 		   sb.setSd_document_id(100002);
				 		   sb.setSd_if_mid(39l);
				 		   sb.setSd_submitted_date(order.getJudgmentDate());
				 		   sb.setSd_rec_status(1);
				 		   sb.setSd_fd_mid(fd_id);
				 		  sb.setChecked(false);
				 		   sb.setIndexField(masterService.getIndexField(39L));
				 		   sb.setSd_nonmaintainable(false);
				 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
				 		   sb.setDocumentType(masterService.getApplicationById(100002));
				 		 sb.setSd_document_name(order.getJudgmentID().toString());
				 		 sb.setSubApplications(new ArrayList<SubApplication>());
				 		 
				 		subDocuments.add(sb);
				 		 
				 		downloadFile(sb.getJudgmentID(), uploadPath);
				 		 
				 		  
				 	   }
				    }
			    	
			    }
			    }
			   
				}
			
			Collections.sort(subDocuments, new Comparator<SubDocument>() {
				  public int compare(SubDocument sd1, SubDocument sd2) {
				      if (sd1.getSd_submitted_date() == null || sd2.getSd_submitted_date() == null)
				        return 0;
				      return sd1.getSd_submitted_date().compareTo(sd2.getSd_submitted_date());
				  }
				});
			
			List<HashMap<String, Object>> bookmark = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> link1 = new HashMap<String, Object>();
			/* downloadFolder = lookupDownload.getLk_longname()
					+ File.separator + caseFileDetail.getFd_document_name();*/
			File dir = new File(downloadFolder);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String outputFilePath = downloadFolder + File.separator
					 +clList.get(j).getCl_serial_no()+"_"+caseFileDetail.getCaseType().getCt_label()+"_"+caseFileDetail.getFd_case_no()+"_"+caseFileDetail.getFd_case_year();
			OutputStream out;
			try {
				out = new FileOutputStream(new File(outputFilePath + ".pdf"));
				OrderReport or=null;
				for (SubDocument file : subDocuments) {
					if (file.getSd_fd_mid() != null) {
						String srcPath =null;
						//SubDocument subDocument = file.getSubDocument();
						if(file.getJudgmentID() == null) {
						 srcPath = basePath + File.separator
								+ file.getIndexField().getIf_name()
								+ File.separator
								+ file.getSd_document_name() + ".pdf";
						}else if(file.getSd_document_id() != null) {
							if(file.getSd_document_id().intValue() == 100002) {
							srcPath =uploadPath+File.separator +"uploads"+File.separator+file.getJudgmentID()+".pdf";
							}
							
						}
						
						
						

					
						
						
						
							officeReports=orderReportService.getOfficeReport(lastSubmittedDate,file.getSd_submitted_date(),file.getSd_fd_mid());
							
							for (OrderReport officRpt :officeReports)
							{
								if(officRpt.getOrd_sd_mid()==null)
								{
								
									newOfficePdf=lookupDownload.getLk_longname()
											+ File.separator + officRpt.getOrd_id()+".pdf";
									createOfficeRptPdf(officRpt,newOfficePdf);
									list.add(new FileInputStream(new File(newOfficePdf)));
									
									link1.put("Title", "Office Report Dated-"+simpleDateFormat.format(officRpt.getOrd_created()));	
									 link1.put("Action", "GoTo");
									link1.put("Page", String.format("%d Fit", 1));
									bookmark.add(link1);
									link1 = new HashMap<String, Object>();
								}
							}
							
							
						
						
						
						if(file.getSd_if_mid()==39 && file.getSd_document_id()==100003) {
							 officeRpt=orderReportService.getBySdId(file.getSd_id());
							if(officeRpt!=null) {
							 newOfficePdf=lookupDownload.getLk_longname()
										+ File.separator + officeRpt.getOrd_id()+".pdf";
							 
							 createOfficeRptPdf(officeRpt,newOfficePdf);
							 
							 ofcRpt=true;
							 
							 //link1.put("Title", (file.getSd_document_id() !=null ? file.getDocumentType().getAt_name() :file.getIndexField().getIf_name()) +" Dated-"+simpleDateFormat.format(file.getSd_submitted_date()));
							 link1.put("Title", "Office Report Dated-"+simpleDateFormat.format(officeRpt.getOrd_created()));	
							 link1.put("Action", "GoTo");
								link1.put("Page", String.format("%d Fit", 1));
								bookmark.add(link1);
								link1 = new HashMap<String, Object>();
							}
							// list.add(new FileInputStream(new File(newOfficePdf)));
						}
						
						if(ofcRpt==true) {
							list.add(new FileInputStream(new File(newOfficePdf)));
							list.add(new FileInputStream(new File(srcPath)));
							ofcRpt=false;
							
						}
						else {
						list.add(new FileInputStream(new File(srcPath)));
						}
						
						link1.put("Title", (file.getSd_document_id() !=null ? file.getDocumentType().getAt_name() :file.getIndexField().getIf_name()) +(file.getSd_submitted_date() !=null ?" Dated-"+simpleDateFormat.format(file.getSd_submitted_date()) : ""));
						link1.put("Action", "GoTo");
						link1.put("Page", String.format("%d Fit", 1));
						bookmark.add(link1);
						link1 = new HashMap<String, Object>();

					}
					 /* or= new OrderReport();
					   or=downloadService.getOrderReport(file.getSd_id());
					if (or.getOrd_id() != null) {
						String pdfname = downloadFolder + File.separator
								+ or.getOrd_id() + ".pdf";
						PDFCreator.createPDF(or, pdfname);
						list.add(new FileInputStream(new File(pdfname)));
					}*/
					
					lastSubmittedDate=file.getSd_submitted_date();
					
				}
				
				officeReports=orderReportService.getOfficeReport(lastSubmittedDate,new Date(),fd_id);
				
				for (OrderReport officRpt :officeReports)
				{
					if(officRpt.getOrd_sd_mid()==null)
					{
					
						newOfficePdf=lookupDownload.getLk_longname()
								+ File.separator + officRpt.getOrd_id()+".pdf";
						createOfficeRptPdf(officRpt,newOfficePdf);
						list.add(new FileInputStream(new File(newOfficePdf)));
						
						link1.put("Title", "Office Report Dated-"+simpleDateFormat.format(officRpt.getOrd_created()));
						link1.put("Action", "GoTo");
						link1.put("Page", String.format("%d Fit", 1));
						bookmark.add(link1);
						link1 = new HashMap<String, Object>();
					}
				}
				//PDFMerger.doMerge(list, out);
				PDFMerger.doMerge(list, out,bookmark,true);
				
				List<OrderReport> offcrpt=orderReportService.getOfficeReports(fd_id);
				
				for (OrderReport officRpt :offcrpt)
				{
						newOfficePdf=lookupDownload.getLk_longname()
								+ File.separator + officRpt.getOrd_id()+".pdf";
						File file=new File(newOfficePdf);
						file.delete();
				}
				
				
				
				//for(OrderReport ofcRpt :offcrpt.)
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
	}
		}
	}
		try {
		globalfunction.zipFolder(CausListFolder.getAbsolutePath(),
				CausListFolder.getAbsolutePath() + ".zip");
		response.setContentType("application/zip");
		PrintWriter out1 = response.getWriter();
		String filename = CausListFolder.getName() + ".zip";
		String filepath = CausListFolder.getAbsolutePath() + ".zip";
		File zipFile = new File(CausListFolder + ".zip");

		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + filename + "\"");

		FileInputStream fileInputStream = new FileInputStream(filepath);

		int i;
		while ((i = fileInputStream.read()) != -1) {
			out1.write(i);
		}
		fileInputStream.close();
		out1.close();
		 FileUtils.deleteDirectory(CausListFolder);
		zipFile.delete();
		//CausListFolder.delete();
	} catch (Exception e) {
		e.printStackTrace();
	}
		}
	
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable("id") Long fd_id,
			HttpSession session, HttpServletResponse response) {
		String jsonData = null;

		OrderReport officeRpt= null;
		//DownloadReport report = downloadService.getById(dr_id);

		//if (report.getDr_rec_status() == 1) {
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			Lookup lookupDownload = lookupService
					.getLookUpObject("DOWNLOADPATH");
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(fd_id);
			String basePath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label();

			List<InputStream> list = new ArrayList<InputStream>();
			
			Boolean ofcRpt=false;
			String newOfficePdf=null;
			String uploadPath = context.getRealPath("");
			
			List<OrderReport> officeReports;
			RegularToDefective cfdconvertmain =null;
			
			//int lastIteration=0;
			
			Date lastSubmittedDate=null;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

			List<SubDocument> subDocuments = downloadService.getSubFiles(fd_id);
			
			
			
			Date sbDate =null;
			if(caseFileDetail != null) {
				/*	if(cfd.getFd_file_source().equals("P") && cfd.getFd_case_year().intValue() < 2010) {*/
				if(caseFileDetail.getFd_file_source().trim().equals("P")) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sDate1="2017-08-17 00:00:00"; 
						     
							try {
								sbDate = formatter.parse(sDate1);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						

					// get order from dms before 2010-01-01
					List<SubDocument> subDocumentfromdms =subDocumentService.getSubDocumentOrders(fd_id,sbDate);
					
					if(subDocumentfromdms != null) {

						subDocuments.addAll(subDocumentfromdms);
						
					}
					
					
					
					
					
				}
				
				String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+caseFileDetail.getCaseType().getCt_label()+"/"+caseFileDetail.getFd_case_no()+"/"+caseFileDetail.getFd_case_year();
				//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
					
				 System.out.println("uriiiiiiiiii"+uri);
			    RestTemplate restTemplate = new RestTemplate();
			   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
			    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
			    SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			    ResponseEntity<OrderFromElegalix[]> response1 =null;
			    ResponseEntity<Object> response2 =null;
			    List<OrderFromElegalix> orderListFromElegalix =null;
			    
			    try {
			     
			   
			    
			    	response1 =
				    		  restTemplate.getForEntity(
				    				  uri,
				    		  OrderFromElegalix[].class);
			    		
			    	orderListFromElegalix   =	Arrays.asList(response1.getBody());

			    
			  
			   String date =sm.format(orderListFromElegalix.get(0).getJudgmentDate());
			   
			    }
			    catch(HttpClientErrorException e) {
			    	
			    	
			    	
			    	e.printStackTrace();
			    	
			    	
			    	
			    	
				    		/*  restTemplate.getForEntity(
				    				  uri,
				    		  Object.class);
			    		
			    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
			    	System.out.println("Response 2"+response2);*/
			    	
			    }
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String sDate1="2017-08-17 00:00:00"; 
				
				Date d=null;
				
				try {
					d=formatter.parse(sDate1);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   
			   // System.out.println("E"+date);
			    if(orderListFromElegalix != null) {
			    	for(OrderFromElegalix order:orderListFromElegalix) {
			    		if(order.getJudgmentDate().after(d)) {
			 		   SubDocument sb =new SubDocument();
			 		   sb.setJudgmentID(order.getJudgmentID());
			 		   sb.setSd_document_id(100002);
			 		   sb.setSd_if_mid(39l);
			 		   sb.setSd_submitted_date(order.getJudgmentDate());
			 		   sb.setSd_rec_status(1);
			 		   sb.setSd_fd_mid(fd_id);
			 		   sb.setIndexField(masterService.getIndexField(39L));
			 		   sb.setChecked(false);
			 		   sb.setSd_nonmaintainable(false);
			 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
			 		   sb.setDocumentType(masterService.getApplicationById(100002));
			 		 sb.setSd_document_name(order.getJudgmentID().toString());
			 		 sb.setSubApplications(new ArrayList<SubApplication>());
			 		 
			 		subDocuments.add(sb);
			 		
			 
					downloadFile(sb.getJudgmentID(), uploadPath);
					
			    		}
			 		 
			 		 
			 		  
			 	   }
			    }
			    
			    CaseLkoToAldHistory cla=null;
			    
			    cla=caseFileDetailService.getTramsferdCase(caseFileDetail.getFd_case_type().intValue(), caseFileDetail.getFd_case_no().toString(), caseFileDetail.getFd_case_year().intValue());;
			    //cfdconvertmain =caseFileDetailService.getConvertedCase(cfd.getFd_case_type().toString(), cfd.getFd_case_no().toString(), cfd.getFd_case_year().toString());
			    if(cla!=null) {
			    	

			    	

			    	
			    	/*CaseType ct1 =lkoMasterService.getCaseTypesLKOById(cla.getOld_case_type().longValue());*/
			    	
			    	CaseTypeLko ct1 =casetypeService.getByIdLko(cla.getOld_case_type().longValue());
			    	
			    	String uri1 = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/L/"+ct1.getCt_label()+"/"+cla.getOld_case_no()+"/"+cla.getOld_case_year();
					//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
						

				    RestTemplate restTemplate1 = new RestTemplate();
				   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
				    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
				    SimpleDateFormat sm1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				    ResponseEntity<OrderFromElegalix[]> re1 =null;
				    ResponseEntity<Object> re2 =null;
				    List<OrderFromElegalix> orderListFromElegalix1 =null;
				    
				    try {
				     
				   
				    
				    	re1 =
					    		  restTemplate1.getForEntity(
					    				  uri1,
					    		  OrderFromElegalix[].class);
				    		
				    	orderListFromElegalix1   =	Arrays.asList(re1.getBody());

				    
				  
				   String date =sm.format(orderListFromElegalix1.get(0).getJudgmentDate());
				   
				    }
				    catch(HttpClientErrorException e) {
				    	
				    	
				    	
				    	e.printStackTrace();
				    
				    	
				    	
				    	
					   /* 		  restTemplate1.getForEntity(
					    				  uri1,
					    		  Object.class);
				    		
				    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
				    	System.out.println("Response 2"+re2);*/
				    	
				    }
				   
				   // System.out.println("E"+date);
				    if( orderListFromElegalix1 != null) {
				    	for(OrderFromElegalix order:orderListFromElegalix1) {
				 		   SubDocument sb =new SubDocument();
				 		   sb.setJudgmentID(order.getJudgmentID());
				 		   sb.setSd_document_id(100002);
				 		   sb.setSd_if_mid(39l);
				 		   sb.setSd_submitted_date(order.getJudgmentDate());
				 		   sb.setSd_rec_status(1);
				 		   sb.setSd_fd_mid(fd_id);
				 		  sb.setChecked(false);
				 		   sb.setIndexField(masterService.getIndexField(39L));
				 		   sb.setSd_nonmaintainable(false);
				 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
				 		   sb.setDocumentType(masterService.getApplicationById(100002));
				 		 sb.setSd_document_name(order.getJudgmentID().toString());
				 		 sb.setSubApplications(new ArrayList<SubApplication>());
				 		 
				 		subDocuments.add(sb);
				 		downloadFile(sb.getJudgmentID(), uploadPath);
				 		 
				 		  
				 	   }
				    }
			    	
			    
			    	
			    }
			    
			    cfdconvertmain =caseFileDetailService.getConvertedCase(caseFileDetail.getFd_case_type().toString(), caseFileDetail.getFd_case_no().toString(), caseFileDetail.getFd_case_year().toString());
			    if(cfdconvertmain != null) {
			    	// again check order for elegalix for old case no 
			    	
			    	// This list is used when there more than converted cases
			    	List<RegularToDefective>  cfdconvertlist =new ArrayList<RegularToDefective>();
			    	
			    	cfdconvertlist.add(cfdconvertmain);
			    	
			    	RegularToDefective cfdconvert1  =caseFileDetailService.getConvertedCaseFromOld(cfdconvertmain.getRdh_case_type_old(),cfdconvertmain.getRdh_case_no_old(), cfdconvertmain.getRdh_case_year_old());
			    	
			    	
			    	if(cfdconvert1 != null) {
			    		cfdconvertlist.add(cfdconvert1);
			    		RegularToDefective cfdconvert2  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert1.getRdh_case_type_old(),cfdconvert1.getRdh_case_no_old(), cfdconvert1.getRdh_case_year_old());
				    	
				    	
				    	if(cfdconvert2 != null) {
				    		cfdconvertlist.add(cfdconvert2);
				    		RegularToDefective cfdconvert3  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert2.getRdh_case_type_old(),cfdconvert2.getRdh_case_no_old(), cfdconvert2.getRdh_case_year_old());
					    	
					    	
					    	if(cfdconvert3 != null) {
					    		cfdconvertlist.add(cfdconvert3);
					    		RegularToDefective cfdconvert4  =caseFileDetailService.getConvertedCaseFromOld(cfdconvert3.getRdh_case_type_old(),cfdconvert3.getRdh_case_no_old(), cfdconvert3.getRdh_case_year_old());
						    	
						    	
						    	if(cfdconvert4 != null) {
						    		cfdconvertlist.add(cfdconvert4);
						    		
						    	}
					    		
					    	}
				    		
				    	}
			    	}
			    	

			    	for(RegularToDefective cfdconvert: cfdconvertlist) {
			    	

			    	
			    	CaseType ct1 =masterService.getCaseTypeById(Long.parseLong(cfdconvert.getRdh_case_type_old()));
			    	
			    	String uri1 = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/"+ct1.getCt_label()+"/"+cfdconvert.getRdh_case_no_old()+"/"+cfdconvert.getRdh_case_year_old();
					//	String uri = "http://192.168.0.162:8080/elegalix_restapi2/api/judgments/A/WPIL/564/2021";
						
					 System.out.println("uriiiiiiiiii"+uri);
				    RestTemplate restTemplate1 = new RestTemplate();
				   /* OderFromElegalixList result =  restTemplate.getForObject(uri, OderFromElegalixList.class);
				    List<OrderFromElegalix> listofobjects =result.getOrderFromElegalixList();*/
				    SimpleDateFormat sm1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				   // SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				    ResponseEntity<OrderFromElegalix[]> re1 =null;
				    ResponseEntity<Object> re2 =null;
				    List<OrderFromElegalix> orderListFromElegalix1 =null;
				    
				    try {
				     
				   
				    
				    	re1 =
					    		  restTemplate1.getForEntity(
					    				  uri1,
					    		  OrderFromElegalix[].class);
				    		
				    	orderListFromElegalix1   =	Arrays.asList(re1.getBody());

				    
				  
				   String date =sm.format(orderListFromElegalix1.get(0).getJudgmentDate());
				   
				    }
				    catch(HttpClientErrorException e) {
				    	System.out.println("Response22222222222222"+re1);
				    	
				    	
				    	e.printStackTrace();
				    	System.out.println("status------------------"+e.getStatusCode());
				    	System.out.println("Response------------------"+e.getResponseBodyAsString());
				    	
				    	
				    	
					   /* 		  restTemplate1.getForEntity(
					    				  uri1,
					    		  Object.class);
				    		
				    //	orderListFromElegalix   =	Arrays.asList(response2.getBody());
				    	System.out.println("Response 2"+re2);*/
				    	
				    }
				   
				   // System.out.println("E"+date);
				    if( orderListFromElegalix1 != null) {
				    	for(OrderFromElegalix order:orderListFromElegalix1) {
				 		   SubDocument sb =new SubDocument();
				 		   sb.setJudgmentID(order.getJudgmentID());
				 		   sb.setSd_document_id(100002);
				 		   sb.setSd_if_mid(39l);
				 		   sb.setSd_submitted_date(order.getJudgmentDate());
				 		   sb.setSd_rec_status(1);
				 		   sb.setSd_fd_mid(fd_id);
				 		  sb.setChecked(false);
				 		   sb.setIndexField(masterService.getIndexField(39L));
				 		   sb.setSd_nonmaintainable(false);
				 		  // sb.setDocumen(masterService.getApplicationById(1000002L));
				 		   sb.setDocumentType(masterService.getApplicationById(100002));
				 		 sb.setSd_document_name(order.getJudgmentID().toString());
				 		 sb.setSubApplications(new ArrayList<SubApplication>());
				 		 
				 		subDocuments.add(sb);
				 		 
				 		downloadFile(sb.getJudgmentID(), uploadPath);
				 		  
				 	   }
				    }
			    	
			    }
			    }
			   
				}
			
			Collections.sort(subDocuments, new Comparator<SubDocument>() {
				  public int compare(SubDocument sd1, SubDocument sd2) {
				      if (sd1.getSd_submitted_date() == null || sd2.getSd_submitted_date() == null)
				        return 0;
				      return sd1.getSd_submitted_date().compareTo(sd2.getSd_submitted_date());
				  }
				});
			
			List<HashMap<String, Object>> bookmark = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> link1 = new HashMap<String, Object>();
			String downloadFolder = lookupDownload.getLk_longname()
					+ File.separator + caseFileDetail.getFd_document_name();
			File dir = new File(downloadFolder);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String outputFilePath = downloadFolder + File.separator
					+ File.separator + caseFileDetail.getFd_document_name();
			OutputStream out;
			try {
				out = new FileOutputStream(new File(outputFilePath + ".pdf"));
				OrderReport or=null;
				for (SubDocument file : subDocuments) {
					if (file.getSd_fd_mid() != null) {
						String srcPath =null;
						//SubDocument subDocument = file.getSubDocument();
						if(file.getJudgmentID() == null) {
						 srcPath = basePath + File.separator
								+ file.getIndexField().getIf_name()
								+ File.separator
								+ file.getSd_document_name() + ".pdf";
						}else if(file.getSd_document_id() != null) {
							if(file.getSd_document_id().intValue() == 100002) {
							srcPath =uploadPath+File.separator +"uploads"+File.separator+file.getJudgmentID()+".pdf";
							}
							
						}
						
						
						

					
						
						
						
							officeReports=orderReportService.getOfficeReport(lastSubmittedDate,file.getSd_submitted_date(),file.getSd_fd_mid());
							
							for (OrderReport officRpt :officeReports)
							{
								if(officRpt.getOrd_sd_mid()==null)
								{
								
									newOfficePdf=lookupDownload.getLk_longname()
											+ File.separator + officRpt.getOrd_id()+".pdf";
									createOfficeRptPdf(officRpt,newOfficePdf);
									list.add(new FileInputStream(new File(newOfficePdf)));
									
									link1.put("Title", "Office Report Dated-"+simpleDateFormat.format(officRpt.getOrd_created()));	
									 link1.put("Action", "GoTo");
									link1.put("Page", String.format("%d Fit", 1));
									bookmark.add(link1);
									link1 = new HashMap<String, Object>();
								}
							}
							
							
						
						
						
						if(file.getSd_if_mid()==39 && file.getSd_document_id()==100003) {
							 officeRpt=orderReportService.getBySdId(file.getSd_id());
							 if(officeRpt !=null) {
							
							 newOfficePdf=lookupDownload.getLk_longname()
										+ File.separator + officeRpt.getOrd_id()+".pdf";
							 
							 createOfficeRptPdf(officeRpt,newOfficePdf);
							 
							 ofcRpt=true;
							 
							 //link1.put("Title", (file.getSd_document_id() !=null ? file.getDocumentType().getAt_name() :file.getIndexField().getIf_name()) +" Dated-"+simpleDateFormat.format(file.getSd_submitted_date()));
							 link1.put("Title", "Office Report Dated-"+simpleDateFormat.format(officeRpt.getOrd_created()));	
							 link1.put("Action", "GoTo");
								link1.put("Page", String.format("%d Fit", 1));
								bookmark.add(link1);
								link1 = new HashMap<String, Object>();
							 }
							// list.add(new FileInputStream(new File(newOfficePdf)));
						}
						
						if(ofcRpt==true) {
							list.add(new FileInputStream(new File(newOfficePdf)));
							list.add(new FileInputStream(new File(srcPath)));
							ofcRpt=false;
							
						}
						else {
						list.add(new FileInputStream(new File(srcPath)));
						}
						
						link1.put("Title", (file.getSd_document_id() !=null ? file.getDocumentType().getAt_name() :file.getIndexField().getIf_name()) +(file.getSd_submitted_date() !=null ?" Dated-"+simpleDateFormat.format(file.getSd_submitted_date()) : ""));
						link1.put("Action", "GoTo");
						link1.put("Page", String.format("%d Fit", 1));
						bookmark.add(link1);
						link1 = new HashMap<String, Object>();

					}
					 /* or= new OrderReport();
					   or=downloadService.getOrderReport(file.getSd_id());
					if (or.getOrd_id() != null) {
						String pdfname = downloadFolder + File.separator
								+ or.getOrd_id() + ".pdf";
						PDFCreator.createPDF(or, pdfname);
						list.add(new FileInputStream(new File(pdfname)));
					}*/
					
					lastSubmittedDate=file.getSd_submitted_date();
					
				}
				
				officeReports=orderReportService.getOfficeReport(lastSubmittedDate,new Date(),fd_id);
				
				for (OrderReport officRpt :officeReports)
				{
					if(officRpt.getOrd_sd_mid()==null)
					{
					
						newOfficePdf=lookupDownload.getLk_longname()
								+ File.separator + officRpt.getOrd_id()+".pdf";
						createOfficeRptPdf(officRpt,newOfficePdf);
						list.add(new FileInputStream(new File(newOfficePdf)));
						
						link1.put("Title", "Office Report Dated-"+simpleDateFormat.format(officRpt.getOrd_created()));
						link1.put("Action", "GoTo");
						link1.put("Page", String.format("%d Fit", 1));
						bookmark.add(link1);
						link1 = new HashMap<String, Object>();
					}
				}
				//PDFMerger.doMerge(list, out);
				PDFMerger.doMerge(list, out,bookmark,true);
				
				List<OrderReport> offcrpt=orderReportService.getOfficeReports(fd_id);
				
				for (OrderReport officRpt :offcrpt)
				{
						newOfficePdf=lookupDownload.getLk_longname()
								+ File.separator + officRpt.getOrd_id()+".pdf";
						File file=new File(newOfficePdf);
						file.delete();
				}
				
				
				
				//for(OrderReport ofcRpt :offcrpt.)
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				globalfunction.zipFolder(dir.getAbsolutePath(),
						dir.getAbsolutePath() + ".zip");
				response.setContentType("application/zip");
				PrintWriter out1 = response.getWriter();
				String filename = dir.getName() + ".zip";
				String filepath = dir.getAbsolutePath() + ".zip";
				File zipFile = new File(dir + ".zip");

				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + filename + "\"");

				FileInputStream fileInputStream = new FileInputStream(filepath);

				int i;
				while ((i = fileInputStream.read()) != -1) {
					out1.write(i);
				}
				fileInputStream.close();
				out1.close();
				// FileUtils.deleteDirectory(dir);
				zipFile.delete();
			//	dir.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

/*	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable("id") Long fd_id,
			HttpSession session, HttpServletResponse response) {
		String jsonData = null;

		OrderReport officeRpt= null;
		//DownloadReport report = downloadService.getById(dr_id);

		//if (report.getDr_rec_status() == 1) {
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			Lookup lookupDownload = lookupService
					.getLookUpObject("DOWNLOADPATH");
			CaseFileDetail caseFileDetail = caseFileDetailService
					.getCaseFileDetail(fd_id);
			String basePath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label();

			List<InputStream> list = new ArrayList<InputStream>();
			
			Boolean ofcRpt=false;
			String newOfficePdf=null;
			
			List<OrderReport> officeReports;
			
			//int lastIteration=0;
			
			Date lastSubmittedDate=null;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

			List<SubDocument> subDocuments = downloadService.getSubFiles(fd_id);
			List<HashMap<String, Object>> bookmark = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> link1 = new HashMap<String, Object>();
			String downloadFolder = lookupDownload.getLk_longname()
					+ File.separator + caseFileDetail.getFd_document_name();
			File dir = new File(downloadFolder);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String outputFilePath = downloadFolder + File.separator
					+ File.separator + caseFileDetail.getFd_document_name();
			OutputStream out;
			try {
				out = new FileOutputStream(new File(outputFilePath + ".pdf"));
				OrderReport or=null;
				for (SubDocument file : subDocuments) {
					if (file.getSd_fd_mid() != null) {
						//SubDocument subDocument = file.getSubDocument();
						
						String srcPath = basePath + File.separator
								+ file.getIndexField().getIf_name()
								+ File.separator
								+ file.getSd_document_name() + ".pdf";
						
						
						

					
						
						
						
							officeReports=orderReportService.getOfficeReport(lastSubmittedDate,file.getSd_submitted_date(),file.getSd_fd_mid());
							
							for (OrderReport officRpt :officeReports)
							{
								if(officRpt.getOrd_sd_mid()==null)
								{
									
								
									newOfficePdf=lookupDownload.getLk_longname()
											+ File.separator + officRpt.getOrd_id()+".pdf";
									createOfficeRptPdf(officRpt,newOfficePdf);
									list.add(new FileInputStream(new File(newOfficePdf)));
									
									link1.put("Title", "Office Report Dated-"+simpleDateFormat.format(officRpt.getOrd_created()));	
									 link1.put("Action", "GoTo");
									link1.put("Page", String.format("%d Fit", 1));
									bookmark.add(link1);
									link1 = new HashMap<String, Object>();
								}
							}
							
							
						
						
						
						if(file.getSd_if_mid()==39 && file.getSd_document_id()==100003) {
							 officeRpt=orderReportService.getBySdId(file.getSd_id());
							
							 newOfficePdf=lookupDownload.getLk_longname()
										+ File.separator + officeRpt.getOrd_id()+".pdf";
							 
							 createOfficeRptPdf(officeRpt,newOfficePdf);
							 
							 ofcRpt=true;
							 
							 //link1.put("Title", (file.getSd_document_id() !=null ? file.getDocumentType().getAt_name() :file.getIndexField().getIf_name()) +" Dated-"+simpleDateFormat.format(file.getSd_submitted_date()));
							 link1.put("Title", "Office Report Dated-"+simpleDateFormat.format(officeRpt.getOrd_created()));	
							 link1.put("Action", "GoTo");
								link1.put("Page", String.format("%d Fit", 1));
								bookmark.add(link1);
								link1 = new HashMap<String, Object>();
							
							// list.add(new FileInputStream(new File(newOfficePdf)));
						}
						
						if(ofcRpt==true) {
							list.add(new FileInputStream(new File(newOfficePdf)));
							list.add(new FileInputStream(new File(srcPath)));
							
						}
						else {
						list.add(new FileInputStream(new File(srcPath)));
						}
						
						link1.put("Title", (file.getSd_document_id() !=null ? file.getDocumentType().getAt_name() :file.getIndexField().getIf_name()) +" Dated-"+simpleDateFormat.format(file.getSd_submitted_date()));
						link1.put("Action", "GoTo");
						link1.put("Page", String.format("%d Fit", 1));
						bookmark.add(link1);
						link1 = new HashMap<String, Object>();

					}
					  or= new OrderReport();
					   or=downloadService.getOrderReport(file.getSd_id());
					if (or.getOrd_id() != null) {
						String pdfname = downloadFolder + File.separator
								+ or.getOrd_id() + ".pdf";
						PDFCreator.createPDF(or, pdfname);
						list.add(new FileInputStream(new File(pdfname)));
					}
					
					lastSubmittedDate=file.getSd_submitted_date();
					
				}
				
				officeReports=orderReportService.getOfficeReport(lastSubmittedDate,new Date(),fd_id);
				
				for (OrderReport officRpt :officeReports)
				{
					if(officRpt.getOrd_sd_mid()==null)
					{
					
						newOfficePdf=lookupDownload.getLk_longname()
								+ File.separator + officRpt.getOrd_id()+".pdf";
						createOfficeRptPdf(officRpt,newOfficePdf);
						list.add(new FileInputStream(new File(newOfficePdf)));
						
						link1.put("Title", "Office Report Dated-"+simpleDateFormat.format(officRpt.getOrd_created()));
						link1.put("Action", "GoTo");
						link1.put("Page", String.format("%d Fit", 1));
						bookmark.add(link1);
						link1 = new HashMap<String, Object>();
					}
				}
				//PDFMerger.doMerge(list, out);
				PDFMerger.doMerge(list, out,bookmark,true);
				
				List<OrderReport> offcrpt=orderReportService.getOfficeReports(fd_id);
				
				for (OrderReport officRpt :offcrpt)
				{
						newOfficePdf=lookupDownload.getLk_longname()
								+ File.separator + officRpt.getOrd_id()+".pdf";
						File file=new File(newOfficePdf);
						file.delete();
				}
				
				
				
				//for(OrderReport ofcRpt :offcrpt.)
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				globalfunction.zipFolder(dir.getAbsolutePath(),
						dir.getAbsolutePath() + ".zip");
				response.setContentType("application/zip");
				PrintWriter out1 = response.getWriter();
				String filename = dir.getName() + ".zip";
				String filepath = dir.getAbsolutePath() + ".zip";
				File zipFile = new File(dir + ".zip");

				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + filename + "\"");

				FileInputStream fileInputStream = new FileInputStream(filepath);

				int i;
				while ((i = fileInputStream.read()) != -1) {
					out1.write(i);
				}
				fileInputStream.close();
				out1.close();
				// FileUtils.deleteDirectory(dir);
				zipFile.delete();
				dir.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	
	
	public void createOfficeRptPdf(OrderReport officeRpt,String path ) throws IOException {
		 Document doc=new Document();
		 
		 HTMLWorker htmlWorker = new HTMLWorker(doc);
		 
			Font underlin =new Font(Font.FontFamily.HELVETICA  , 20, Font.BOLDITALIC);
			 
			 PdfWriter writer;
			try {
				writer = PdfWriter.getInstance(doc, new FileOutputStream(path));
			
			 
			 doc.open();
			/* Paragraph title=new Paragraph(Font.BOLDITALIC,"Office Report");
			 title.setFont(underlin);
			
			 title.setAlignment(Element.ALIGN_CENTER);*/
			 
			 Chunk title1=new Chunk("Office Report",underlin);
	          
			 title1.setUnderline(0.1f, -2f);
			 
			 Paragraph title=new Paragraph(title1);
			 title.setAlignment(Element.ALIGN_CENTER);
			 //title.setFont(underlin);
			 
			 
			doc.add(title);			
			
			/*doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);*/
			
			htmlWorker.parse(new StringReader("<br><br>"+officeRpt.getOrd_remark()));
			 
			// doc.add(new Paragraph("\n\n"+officeRpt.getOrd_remark()));
			 
			 doc.add(Chunk.NEWLINE);
			 doc.add(new Chunk().NEWLINE);
			 doc.add(new Chunk().NEWLINE);
/*				 Paragraph dated=new Paragraph(Font.BOLDITALIC,"Dated");
			 dated.add("\n\n\n"+officeRpt.getOrd_created());
			 dated.setAlignment(Element.ALIGN_LEFT);
			 
			 doc.add(dated);
			 
			
			
			 
			 
			 User createdUser=usermaster.getByuserid(officeRpt.getOrd_created_by());
			 Paragraph createdBy=new Paragraph(Font.BOLDITALIC,"Author");
			 createdBy.add("\n\n\n"+createdUser.getUm_fullname());
			 createdBy.setAlignment(Element.ALIGN_RIGHT);
			 
			 doc.add(createdBy);*/
			 
			 User createdUser=usermaster.getByuserid(officeRpt.getOrd_created_by());
			 Chunk glue = new Chunk(new VerticalPositionMark());
			 Paragraph p = new Paragraph(Font.BOLDITALIC,"Dated");
			 //p.add("\n\n\n"+officeRpt.getOrd_created());
			 
			 
			 
			 
			 p.add(new Chunk(glue));
			 p.add("Submitted By");
			 doc.add(p);
			 String createdDate=DateFormat.getDateInstance().format(officeRpt.getOrd_created());
			 Paragraph p1 = new Paragraph(createdDate);
			 p1.add(new Chunk(glue));
			 p1.add(createdUser.getUm_fullname());
			
			 doc.add(p1);
			 
			 
			 
			 doc.close();
			 writer.close();
			 
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (com.itextpdf.text.DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@RequestMapping(value = "/deleteDownloadHistory/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteDownloadHistory(
			@PathVariable("id") Long fd_id, HttpSession session) {
		String jsonData = null;
		ActionResponse<DownloadReport> response = new ActionResponse<>();
		boolean result = false;
		result = downloadService.deleteDownloadHistory(fd_id);
		if (result) {
			response.setResponse("TRUE");
		}
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}


	@RequestMapping(value = "/downloadCase", produces = { "application/pdf" }, method = RequestMethod.GET)
	public @ResponseBody String downloadCase() {
		String jsonData = null;
		
		File file = new File("D:\\Allahabad High Court\\Allahabad\\CLRE\\petition\\CLRE642018_PETN_1.pdf");
		
		
		
		
		

		return jsonData;
	}

	
	 /*--------------Sushant---------------------*/        
	
	@RequestMapping(value = "/NotesSave", method = RequestMethod.POST)
	public @ResponseBody String saveNotes(@RequestBody Notes Note, HttpSession session) {
		Notes nt = null;
		System.err.println("Fd_Id : " + Note.getNt_fd_mid());

		User user = (User) session.getAttribute("USER");
		ActionResponse<Notes> response = new ActionResponse<Notes>();
		System.out.println("== Notes Update ==");
		String jsonData = null;
		Long fdid =Note.getNt_fd_mid();
		//Notes prenot = subDocumentService.getNote(l);
		
		Judge judge = courtMasterService.getJudgeByCourtMappingUmid(user.getUm_id());
		JudgeName jn= courtMasterService.getJudgeNameByCourtMappingUmid(user.getUm_id());
		/*Notes prenot = subDocumentService.getNote(fdid,judge.getJg_id());*/
		Notes prenot = subDocumentService.getNote(fdid);
		System.out.println("== Note==id "+prenot);
		
		if(prenot==null)
		{
			nt = new Notes();
			nt.setNt_id(Note.getNt_id());
			nt.setNt_fd_mid(Note.getNt_fd_mid());
			nt.setNt_notes(Note.getNt_notes());
			nt.setNt_cr_date(new Date());
			//nt.setNt_cr_by(user.getUm_id());
			/*nt.setNt_cr_by(judge.getJg_id());*/
			nt.setNt_cr_by(jn.getJn_id());
			subDocumentService.saveNote(nt);
			response.setResponse("TRUE");
			
		}
		else
		{
			Note.setNt_notes(Note.getNt_notes());
			Note.setNt_mod_date(new Date());
			int flag=subDocumentService.updateNote(Note,jn.getJn_id());
			if(flag==1) {
				response.setResponse("TRUE");				
			}
		}
		
	/*	
		if (Note.getNt_notes() != null) {
			nt = new Notes();
			nt.setNt_id(Note.getNt_id());
			nt.setNt_fd_mid(Note.getNt_fd_mid());
			nt.setNt_notes(Note.getNt_notes());
			nt.setNt_cr_date(new Date());
			nt.setNt_cr_by(user.getUm_id());
			subDocumentService.saveNote(nt);
			response.setResponse("TRUE");
		} else 
		{	
			
			response.setResponse("FALSE");
			
		}*/
		
		
		
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
	}
		
		@RequestMapping(value = "/getnotes/{id}", method = RequestMethod.GET)
		public @ResponseBody String getNotes(@PathVariable("id") Long fd_id, HttpSession session) {
			String jsonData = null;
			ActionResponse<Notes> response = new ActionResponse<>();
			/*Notes notedata = subDocumentService.getNote(fd_id);*/
			
			User user = (User) session.getAttribute("USER");
			
			CourtUserMapping cum =courtMasterService.getCourtMappingForUser(user.getUm_id());
			//	session.setAttribute("USER", user);
				if(cum != null) {
					CourtMaster cm =courtMasterService.getCourtMaster(cum.getCum_court_mid());
					user.setCourtMaster(cm);
				}
			
				List<JudgeName> judge = courtMasterService.getJudgeByECourtMappingUmid(user.getCourtMaster().getCm_bench_id());
			
			/*Notes notedata = subDocumentService.getNote(fd_id,judge.getJn_id());*/
				
				Notes notedata = subDocumentService.getNote1(fd_id,judge);
				
				/*Notes notedata = subDocumentService.getNote(fd_id);*/
			
			response.setResponse("TRUE");
			response.setModelData(notedata);
			
			jsonData = globalfunction.convert_to_json(response);
			
			return jsonData;
		}
	
		
		@RequestMapping(value = "/getCaseListByUser", method = RequestMethod.GET)
		public @ResponseBody String getCaseListByUser(HttpSession session) 
		{
			String jsonData = null;
			List<CaseFileDetail> casefileList=null;
			User user = (User) session.getAttribute("USER");
			System.out.println(user.getUsername());
			ActionResponse<CaseFileDetail> response = new ActionResponse<>();
			
			//if(user.getUm_id()==29 || user.getUm_id()==40 || user.getUm_id()==33 || user.getUm_id()==36)
			if(user.getUm_id()==29 || user.getUm_id()==33 || user.getUm_id()==36)
			{
				casefileList = caseFileDetailService.getCaseListByUser(user.getUm_id());
			}
			
			else if(user.getUm_id()==38 || user.getUm_id()==39 )
			{
				casefileList = caseFileDetailService.getCaseListByUser(user.getUm_id());
				
				for (int i = 0; i < casefileList.size(); i++) 
				{
					CaseNominated cn  = caseNominatedService.getCaseNominatedByfdmid(casefileList.get(i).getFd_id());
					if(cn!=null)
					{
						casefileList.get(i).setCl_court_no(cn.getCn_tocm_mid());
						casefileList.get(i).setCl_list_type_mid(cn.getCn_toclt_mid());
						casefileList.get(i).setCl_to_judge(cn.getCn_tojg_mid());
						casefileList.get(i).setCl_dol(cn.getCn_todate());
						
						casefileList.get(i).setCaseNominated(cn);
					}
				
				}
			}
			
			response.setResponse("TRUE");
			response.setModelList(casefileList);

			jsonData = globalfunction.convert_to_json(response);

			return jsonData;
		}
		
		
		@RequestMapping(value = "/Getfilename/{id}", method = RequestMethod.GET)
		public @ResponseBody String Getfilename(@PathVariable("id") Long sd_id,HttpSession session) {
		String jsonData = null;
		ActionResponse<SubDocument> response=new ActionResponse<>();
		SubDocument subDocument = subDocumentService.getByPK(sd_id);
		String returnview="/casefile/view";
		if(subDocument==null){
		returnview="/casefile/notfound";
		}else{
		Long docId=subDocument.getSd_fd_mid();
		if(subDocument.getDocumentType()==null)
		{
			ApplicationTypes applicationTypes = new ApplicationTypes();
			applicationTypes.setAt_name("PETITION");
			subDocument.setDocumentType(applicationTypes);
			
			
		}
		CaseFileDetail caseFileDetail=caseFileDetailService.getCaseFileDetail(docId);
		Lookup lookupRepo=lookupService.getLookUpObject("REPOSITORYPATH");
		String srcPath=lookupRepo.getLk_longname()+File.separator+caseFileDetail.getCaseType().getCt_label()+File.separator+subDocument.getIndexField().getIf_name()+File.separator+subDocument.getSd_document_name()+".pdf";

		File source = new File(srcPath);

		String uploadPath = context.getRealPath("");
		File dest = new File(uploadPath+File.separator+"uploads"+File.separator+subDocument.getSd_document_name()+".pdf");
		try {
		FileUtils.copyFile(source, dest);
		}
		catch (IOException e) {
		e.printStackTrace();
		}
		}
		response.setResponse("TRUE");
		response.setModelData(subDocument);
		jsonData = globalfunction.convert_to_json(response);
		return jsonData;
		}

		//Pankaj
		
		@SuppressWarnings("unused")
		@RequestMapping(value = "/addReportupdate", method = RequestMethod.POST)
		public @ResponseBody String addReportupdate(@RequestBody OrderReport orderReport, HttpServletRequest request,
				HttpSession session) throws DocumentException{
			OrderReport or=null;
			String jsonData = "";

			ActionResponse<OrderReport> response = new ActionResponse<OrderReport>();
			System.out.println(orderReport.getOrd_fd_mid());
			//CauseList caust=orderReportService.coustlist(orderReport.getOrd_fd_mid());
			CauseListHistory causthistory = caseFileDetailService.getcausthistory(orderReport.getOrd_fd_mid());
			
			if (causthistory==null) {
				User u = (User) session.getAttribute("USER");
				orderReport.setOrd_created_by(u.getUm_id());
				or=orderReportService.save(orderReport);
				response.setModelData(or);
				response.setResponse("TRUE");
			} else {
				try {
					

					   Date sDate1=orderReport.getOrd_created();  
					   Date dateto =causthistory.getClh_dol();
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						DateFormat dateFormat2 = new SimpleDateFormat("HH");
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		
						Date date = new Date();
						
						String	d= dateFormat.format(sDate1);
						String	T= dateFormat2.format(sDate1);
						String	s= dateFormat1.format(dateto);
						String	T1= dateFormat2.format(date);
						int TT=(9);
						int MM1 = Integer.parseInt(T1);
				
					
					  if ((s.compareTo(d)==0) ) {
						if (TT >= MM1) {
							User u = (User) session.getAttribute("USER");
							orderReport.setOrd_created_by(u.getUm_id());
							or=orderReportService.save(orderReport);
							response.setModelData(or);
							response.setResponse("TRUE");
							
					} else {
						response.setResponse("Time");
					}
						
					} else if (s.compareTo(d) < 0) {
						User u = (User) session.getAttribute("USER");
						orderReport.setOrd_created_by(u.getUm_id());
						or=orderReportService.save(orderReport);
						response.setModelData(or);
						response.setResponse("TRUE");
					} else {
						response.setResponse("Time");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
				
			
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		}
		@RequestMapping(value = "/getByOfficeedit/{id}",method = RequestMethod.GET)
		public @ResponseBody String getReport(@PathVariable("id")Long fd_id, HttpSession session ,HttpServletRequest request) {

				String jsonData = null;
				String offcrpt1=null;
				String causthistory1=null;
				String response1=null;
			ActionResponse<Object> response = new ActionResponse();
			User user = (User) session.getAttribute("USER");

			CauseListHistory causthistory = caseFileDetailService.getcausthistory(fd_id);
			
			List<OrderReport> offcrpt=orderReportService.getOfficeReportsedit(fd_id,(user.getUm_id()),causthistory);

			offcrpt1=globalfunction.convert_to_json(offcrpt); 
			causthistory1=globalfunction.convert_to_json(causthistory); 
			response.setResponse("true");
			response1=globalfunction.convert_to_json(response); 
			jsonData = "{\"offcrpt\":"+offcrpt1+",\"causthistory\":"+causthistory1+",\"response\":"+response1+"}";
			return jsonData;

		}
		
		@RequestMapping(value = "/Getfileview", method = RequestMethod.GET)
		public @ResponseBody String Getfilename(HttpServletRequest request) {
			String sd_document_name = request.getParameter("sd_document_name");
			String subDocIds = request.getParameter("ord_sd_mid");
			Long subDocId=Long.parseLong(subDocIds);
			System.out.println(sd_document_name+"<><>,.<.,,,,,,,,,,,"+subDocIds);
			SubDocument subDocument = subDocumentService.getByPK(subDocId);

		Long docId = subDocument.getSd_fd_mid();
			CaseFileDetail caseFileDetail = caseFileDetailService.getCaseFileDetail(docId);
		
			Lookup lookupRepo = lookupService.getLookUpObject("REPOSITORYPATH");
			
			String srcPath = lookupRepo.getLk_longname() + File.separator
					+ caseFileDetail.getCaseType().getCt_label()
					+ File.separator + subDocument.getIndexField().getIf_name()
					+ File.separator + subDocument.getSd_document_name()
					+ ".pdf";
			String jsonData = null;
			ActionResponse<CaseFileDetail> response = new ActionResponse();
			File src = new File(srcPath);

			String pth = request.getContextPath();

			String uploadPath = context.getRealPath("");
			File destination = new File(uploadPath + "uploads\\" + sd_document_name + ".pdf");
			System.out.println("VIEW PDF PATH : " + uploadPath + "uploads\\" + sd_document_name + ".pdf");

			try {
				FileUtils.copyFile(src, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return jsonData;
		}
		
		
		
		@RequestMapping(value = "/deleteremark", method = RequestMethod.GET)
		public @ResponseBody String deleteremark(HttpServletRequest request) {
			ActionResponse<OrderReport> response = new ActionResponse();
			String subDocIds = request.getParameter("ord_id");
			Long subDocId=Long.parseLong(subDocIds);
			
			String jsonData = null;
			
			OrderReport oder = orderReportService.getOrderReport(subDocId);
			CauseListHistory causthistory = caseFileDetailService.getcausthistory(oder.getOrd_fd_mid());
			if (causthistory==null) {
				if (oder.getOrd_sd_mid()!=null) {
					SubDocument sub = subDocumentService.getByPK(oder.getOrd_sd_mid());
					sub.setSd_rec_status(0);
					subDocumentService.save(sub);
					}
					oder.setOrd_rec_status(0);
					OrderReport OrderReport = orderReportService.save(oder);
					if(OrderReport==null)
					{
						response.setResponse("FALSE");	
					}
					else
					{
						response.setResponse("TRUE");
					}
			} else {
				try {
					

					   Date sDate1=oder.getOrd_created();  
					   Date dateto =causthistory.getClh_dol();
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						DateFormat dateFormat2 = new SimpleDateFormat("HH");
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		
						Date date = new Date();
						
						String	d= dateFormat.format(sDate1);
						String	T= dateFormat2.format(sDate1);
						String	s= dateFormat1.format(dateto);
						String	T1= dateFormat2.format(date);
						int TT=(9);
						int MM1 = Integer.parseInt(T1);
				
					
					  if ((s.compareTo(d)==0) ) {
						if (TT >= MM1) {
							if (oder.getOrd_sd_mid()!=null) {
								SubDocument sub = subDocumentService.getByPK(oder.getOrd_sd_mid());
								sub.setSd_rec_status(0);
								subDocumentService.save(sub);
								}
								oder.setOrd_rec_status(0);
								OrderReport OrderReport = orderReportService.save(oder);
								if(OrderReport==null)
								{
									response.setResponse("FALSE");	
								}
								else
								{
									response.setResponse("TRUE");
								}
							
					} else {
						response.setResponse("Time");
					}
						
					} else if (s.compareTo(d) < 0) {
						if (oder.getOrd_sd_mid()!=null) {
							SubDocument sub = subDocumentService.getByPK(oder.getOrd_sd_mid());
							sub.setSd_rec_status(0);
							subDocumentService.save(sub);
							}
							oder.setOrd_rec_status(0);
							OrderReport OrderReport = orderReportService.save(oder);
							if(OrderReport==null)
							{
								response.setResponse("FALSE");	
							}
							else
							{
								response.setResponse("TRUE");
							}
					} else {
						response.setResponse("Time");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			jsonData = globalfunction.convert_to_json(response);
			return jsonData;
		}
		
		
		
		
		//======================================== VIJAY CHAURASIYA  START FROM HERE========================================
		
		@RequestMapping(value = "/getpetitionerByFdId/{fdId}", method = RequestMethod.GET)
		public @ResponseBody String getPetitionerByFd(@PathVariable("fdId") Long fdId) {

		    ActionResponse<Petitioner> response = new ActionResponse<>();
		    String jsonData = null;

		    try {
		        Petitioner pet = caseFileDetailService.getPetitionerByFdId(fdId);

		        response.setResponse("TRUE");
		        response.setData(pet);

		    } catch (Exception e) {
		        e.printStackTrace();
		        response.setResponse("FALSE");
		    }

		    jsonData = globalfunction.convert_to_json(response);
		    return jsonData;
		}
		
		@RequestMapping(value = "/savepetitioner", method = RequestMethod.POST)
		public @ResponseBody String savePetitioner(@RequestBody Petitioner pet,
		                                           HttpSession session) {

		    ActionResponse<Petitioner> response = new ActionResponse<>();
		    String jsonData = null;

		    try {
		        User u = (User) session.getAttribute("USER");

		        // Set required fields
		        pet.setPt_rec_status(1);
		        pet.setPt_cr_by(u.getUm_id());
		        pet.setPt_cr_date(new Date());

		        // Save
		        caseFileDetailService.save(pet);

		        response.setResponse("TRUE");
		        response.setData(pet);

		    } catch (Exception e) {
		        e.printStackTrace();
		        response.setResponse("FALSE");
		    }

		    jsonData = globalfunction.convert_to_json(response);
		    return jsonData;
		}
		
		@RequestMapping(value = "/saverespondent", method = RequestMethod.POST)
		public @ResponseBody String saveRespondent(@RequestBody Respondent res,
		                                           HttpSession session) {

		    ActionResponse<Respondent> response = new ActionResponse<>();
		    String jsonData = null;

		    try {
		        User u = (User) session.getAttribute("USER");

		        //  Set required fields
		        res.setRt_rec_status(1);
		        res.setRt_cr_by(u.getUm_id());
		        res.setRt_cr_date(new Date());

		        // Save
		        caseFileDetailService.save(res);

		        response.setResponse("TRUE");
		        response.setData(res);

		    } catch (Exception e) {
		        e.printStackTrace();
		        response.setResponse("FALSE");
		    }

		    jsonData = globalfunction.convert_to_json(response);
		    return jsonData;
		}
		
		
		@RequestMapping(value = "/getrespondentByFdId/{id}", method = RequestMethod.GET)
		public @ResponseBody String getRespondent(@PathVariable("id") Long id) {

		    ActionResponse<Respondent> response = new ActionResponse<>();
		    String jsonData = null;

		    try {
		        Respondent res = caseFileDetailService.getRespondentByFdId(id);

		        response.setResponse("TRUE");
		        response.setData(res);

		    } catch (Exception e) {
		        e.printStackTrace();
		        response.setResponse("FALSE");
		    }

		    jsonData = globalfunction.convert_to_json(response);
		    return jsonData;
		}
		
		
		
		//========================================================================================================
			//======================================== VIJAY CHAURASIYA  END WITH ========================================
			
		
		

	
		
		
}
