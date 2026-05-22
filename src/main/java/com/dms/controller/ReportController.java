package com.dms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dms.model.ActionResponse;
import com.dms.model.DigitizationRequest;
import com.dms.model.DownloadFile;
import com.dms.model.DownloadReport;
import com.dms.service.CaseFileDetailService;
import com.dms.service.CommonReportsService;
import com.dms.service.DownloadFileService;
import com.dms.service.LookupService;
import com.dms.service.SubDocumentService;
import com.dms.utility.GlobalFunction;

@Controller
@RequestMapping("/reports")
public class ReportController {
	
	@Autowired
	private LookupService lookupService;
	
	@Autowired
	private CommonReportsService commonReportsService;
	
	@Autowired
	private SubDocumentService subDocumentService;
	@Autowired
	private CaseFileDetailService caseFileDetailService;
	@Autowired
	ServletContext context;
	
	@Autowired
	private DownloadFileService downloadService;
	
	private GlobalFunction globalfunction;
	
	public ReportController() {
		globalfunction = new GlobalFunction();
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String Download() {
		return "/reports/download";
	}

	@RequestMapping(value = "/officeReport", method = RequestMethod.GET)
	public String searchByParty() {
		return "/reports/officeReport";
	}
	
	@RequestMapping(value = "/getdownloadhistory", method = RequestMethod.GET)
	public @ResponseBody String getdownloadhistory(@RequestParam(value="itemsPerPage")int itemsPerPage,@RequestParam(value="pagenumber")int pagenumber) {
		String jsonData = null;
		ActionResponse<DownloadReport> response=new ActionResponse<>();
		
		List<DownloadReport> reports=downloadService.getDownloadReports(itemsPerPage,pagenumber);
		Integer total_count=downloadService.getDownloadReportsCount();
		response.setResponse("TRUE");
		response.setModelList(reports);
		response.setData(total_count);
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	@RequestMapping(value = "/getdownloadedfiles/{id}", method = RequestMethod.GET)
	public @ResponseBody String getdownloadedfiles(@PathVariable("id") Long dr_id,HttpSession session) {
		String jsonData = null;
		ActionResponse<DownloadFile> response=new ActionResponse<>();
		
		List<DownloadFile> files=downloadService.getFiles(dr_id);
		response.setResponse("TRUE");
		response.setModelList(files);
		
		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
	}
	
	

	//================ Vijay Chaurasiya ===========================	
	
	  @RequestMapping(value = "/movePdmsToDmsReports", method = RequestMethod.GET)
		public String getMovePdmsToDmsReport() {
			return "/reports/movePdmsToDmsReports";
		}
		
		@RequestMapping(value = "/getMovePdmsToDmsReports", method = RequestMethod.GET)
		@ResponseBody
		public List<DigitizationRequest> getRequests(

		        @RequestParam("startDate")
		        @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,

		        @RequestParam("endDate")
		        @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

		    try {
		        // logging only
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        System.out.println("=========Start Date: " + sdf.format(startDate));
		        System.out.println("End Date  : " + sdf.format(endDate));

		        return commonReportsService
		                .getDigitizationRequestsByDateRange(startDate, endDate);

		    } catch (Exception e) {
		        e.printStackTrace();
		        return new ArrayList<DigitizationRequest>();
		    }
		}

	
	
	

}
