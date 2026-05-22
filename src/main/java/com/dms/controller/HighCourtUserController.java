package com.dms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dms.model.ActionResponse;
import com.dms.model.CaseFileDetail;
import com.dms.model.HighCourtDesignation;
import com.dms.model.HighCourtSection;
import com.dms.model.HighCourtUser;
import com.dms.model.User;
import com.dms.model.UserRole;
import com.dms.service.HighCourtUserService;
import com.dms.service.UserRoleService;
import com.dms.service.UserService;
import com.dms.utility.GlobalFunction;


@Controller
public class HighCourtUserController {
	
	
	@Autowired
	HighCourtUserService hcus;
	
	@Autowired
	UserService us;
	
	@Autowired
	UserRoleService use;
	
	
private GlobalFunction globalfunction;
	

	public HighCourtUserController() {
		globalfunction = new GlobalFunction();
	}
	
	@RequestMapping(value = "/userregistration")
	public @ResponseBody String getUserData(HttpSession session) {
		
		return "You can register";
	}
	
	
	@RequestMapping(value = "/userregistration/create", method = RequestMethod.POST)	
	public @ResponseBody String create(@RequestBody HighCourtUser hcu) {
		String jsonData = null;
		Long SectionOfficerLevel =1l;
		GlobalFunction globalfunction=new GlobalFunction();
		ActionResponse<HighCourtUser> response = new ActionResponse<>();
		hcu.setHcu_approved_status("N");
		hcu.setHcu_cr_date(new Date());
		hcu.setHcu_rec_status(1);
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+hcu);
		HighCourtUser u= hcus.findSectionOfficer(hcu.getHcu_hcs_mid(),SectionOfficerLevel);
		
		System.out.println("highcourtuser-----------------"+u);
		
		hcu.setHcu_senior_id(u.getHcu_id());
		hcu.setHcu_password(globalfunction.md5encryption(hcu.getHcu_password()));
		//hcu.setHcu_confirm_password(globalfunction.md5encryption(hcu.getHcu_confirm_password()));
		
		HighCourtUser hcu1 =	hcus.saveHighCourtUser(hcu);
		
		System.out.println("userRegistration "+hcu1);
		if(hcu1 != null) {
			response.setResponse("TRUE");
			response.setModelData(hcu1);
			
		}else
		{
			response.setResponse("FALSE");
		}
		
		jsonData = globalfunction.convert_to_json(response);
		
		return jsonData;
	}
	
	
	// Authenticated API
	@RequestMapping(value ="/userApproval" ,method= RequestMethod.POST)
	public @ResponseBody String userApproval(@RequestBody HighCourtUser hcu , HttpSession session) {
		
		System.out.println("fffffffffffffffffffffffffffff"+hcu);
		HashMap<String, String> map = new HashMap<String,String>();
		User user =new User();
		User u1 =null;
		UserRole ur =new UserRole();
		HighCourtUser hcu1 =new HighCourtUser();
		User u = (User) session.getAttribute("USER");
		
		if(u.getUserroles().get(0).getLk().getLk_longname().equals("SECTIONOFFICER")) {
			
			user.setUsername(hcu.getHcu_office_id());
			user.setUm_fullname(hcu.getHcu_username());
			user.setPassword(hcu.getHcu_password());
			user.setConfirmpassword(hcu.getHcu_password());
			user.setMod_by(u.getUm_id());
			user.setCourt_id(9);
			user.setMod_date(new Date());
			user.setUm_rec_status(1);
			user.setCr_by(u.getUm_id());
			user.setCr_date(new Date());
			
			try {
			u1 =us.save(user);
			System.out.println("user master info"+u1);
			
			}
			catch(Exception e) {
				System.out.println("exception in maim threadddddddd"+e);
			}
			
			if(u1 != null) {
				System.out.println("exception in maim threadddddddd"+u1);
				System.out.println("exception in maim threadddddddd"+u1.getUm_id());
				
				ur.setUr_um_mid(u1.getUm_id());
				ur.setUr_rec_status(1);
				ur.setUr_role_id(351433L);
				ur.setUr_cr_date(new Date());
				try {
				ur =use.save(ur);
				}
				catch(Exception e) {
					
				}
				
				
			}
			
			if(ur != null && u1 != null) {
				hcu.setHc_um_mid(u1.getUm_id());
				hcu1 =hcus.saveHighCourtUser(hcu);
				
				return "data added";
			}
			
			
			
			
			
		}
		System.out.println("user session info "+u);
		//u.setUm_fullname(hcu.set);
		
		return "some isuue";
	}
	
	@RequestMapping(value ="/getUserForApproval")
	public @ResponseBody String getUserForAprroval(HttpSession session) {
		
		
		ActionResponse<List<HighCourtUser>> response = new ActionResponse<>();
		List<HighCourtUser> hculist =null;
		User u = (User) session.getAttribute("USER");
		String jsonData = null;
		System.out.println("userinfoooooooooooooo"+u.getUm_id());
		HighCourtUser hcu =hcus.getHighCourtUser(u.getUm_id());
		
		if(hcu != null) {
			
			 hculist =hcus.getUsersForApproval(hcu.getHcu_senior_id());
		}
		
		if(!hculist.isEmpty()) {
			response.setResponse("TRUE");
			response.setModelData(hculist);
			}
			else {
				response.setResponse("FALSE");
			}
			

			jsonData = globalfunction.convert_to_json(response);

			return jsonData;
		
		
	}
	
	@RequestMapping(value ="/userregistration/getUserDesignations")
	public @ResponseBody String  getUserDesignation(HttpSession session){
		
		System.out.println("enter in method");
		
		String jsonData = null;
		ActionResponse<List<HighCourtDesignation>> response = new ActionResponse<>();

		List<HighCourtDesignation> hcd = hcus.getUserDesignation();
		if(hcd!=null) {
		response.setResponse("TRUE");
		}
		else {
			response.setResponse("FALSE");
		}
		response.setModelData(hcd);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
		
		
		
		
	}
	
	@RequestMapping(value ="/userregistration/getUserSections")
	public @ResponseBody String  getUserSection(HttpSession session){
		
		System.out.println("enter in section method");
		
		String jsonData = null;
		ActionResponse<List<HighCourtSection>> response = new ActionResponse<>();

		List<HighCourtSection> hcs = hcus.getHighCourtSetion();
		if(hcs!=null) {
		response.setResponse("TRUE");
		}
		else {
			response.setResponse("FALSE");
		}
		response.setModelData(hcs);

		jsonData = globalfunction.convert_to_json(response);

		return jsonData;
		
		
		
		
	}
	
	
		
	

}
