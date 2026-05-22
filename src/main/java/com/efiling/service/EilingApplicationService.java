package com.efiling.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efiling.model.ApplicationCheckListMapping;
import com.dms.model.User;
import com.efiling.model.EfilingApplication;





@Service
public class EilingApplicationService 
{
	
	@PersistenceContext(unitName="persistenceUnitEfiling")
	@Qualifier(value = "entityManagerFactoryEfiling")
	private EntityManager emEfling;
	
	@Transactional
	public List<EfilingApplication> getApplicationList(Long ap_fd_mid) {
	List<EfilingApplication> eApplicationList=null;
	eApplicationList= emEfling.createQuery("SELECT eap FROM EfilingApplication eap where eap.ap_fd_mid ="+ap_fd_mid+" and eap.ap_stage_lid in (1000041)").getResultList();
	
		return eApplicationList;
	}
	
	
	
		@Transactional
		public List<EfilingApplication> getPenidingApplicationList(Long ap_fd_mid) {
		List<EfilingApplication> eApplicationList=null;
		eApplicationList= emEfling.createQuery("SELECT eap FROM EfilingApplication eap where eap.ap_fd_mid ="+ap_fd_mid+" and eap.ap_stage_lid not in (1000041,1000035,1000049)").getResultList();
		
			return eApplicationList;
		}
	
	
	@Transactional
	public List<ApplicationCheckListMapping> getApplicationCheckList(Long ap_id) {
		
		// TODO Auto-generated method stub
		List<ApplicationCheckListMapping> result=null;
		Query query=null;
		query = emEfling.createQuery("SELECT a from ApplicationCheckListMapping a where a.cm_rec_status=1 and a.cm_ap_mid=:id").setParameter("id", ap_id);
		result=query.getResultList();
		return result;
	}
	
	
	@Transactional
	public Date getApplicationSubmitDate(Long ap_id) {
		// TODO Auto-generated method stub
		Date result=null;
		Query query=null;
		query = emEfling.createNativeQuery("SELECT a.as_cr_date from application_stage a where a.as_stage_lid=1000036 and a.as_ap_mid=:id order by a.as_cr_date desc limit 1").setParameter("id", ap_id);
		result=(Date) query.getSingleResult();
		return result;
	}
		
}	
		
		
		
		
		
		
		
