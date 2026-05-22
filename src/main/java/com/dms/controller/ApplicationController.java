package com.dms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dms.model.ServiceLogs;
import com.dms.model.User;
import com.dms.service.MoveApplicationService;
import com.dms.service.ServiceLogsService;

@Controller
@RequestMapping("/application")
public class ApplicationController {
	
	@Autowired
	private MoveApplicationService moveApplicationService;
	
	@Autowired
	private ServiceLogsService serviceLogsService;
	

	
	//======================Vijay Chaurasiya============================================

	@RequestMapping(value = "/moveApplications", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> moveApplications(HttpSession session) {

	    Map<String, Object> response = new HashMap<>();

	    try {

	        String result = moveApplicationService.moveApplications();
	    	//String result=null;

	        // Parse string (temporary)
	      String[] parts = result.split(",");

			
			  int total = Integer.parseInt(parts[0].split(":")[1].trim());
			  int success =
			  Integer.parseInt(parts[1].split(":")[1].trim());
			  int failed =
			  Integer.parseInt(parts[2].split(":")[1].trim());
			 
	        
	          			  // Log the service call	
			  
			  if(success>0) {
				  ServiceLogs sl = new ServiceLogs();
				  sl.setServiceName("Move Applications");
				  sl.setLogMessage("Moved " + success + " applications successfully.");
				  User u=(User) session.getAttribute("USER");
				  if (u != null) {
					  sl.setUserId(u.getUm_id());
				  }
				  sl.setLogDate(new Date());
				  serviceLogsService.save(sl);
			  }
	       
			 

			
			  response.put("total", total); response.put("success", success);
			  response.put("failed", failed);
			 
	        
	       

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("error", true);
	    }

	    return response;
	}
	
	
	//======================Vijay Chaurasiya============================================
	

}
