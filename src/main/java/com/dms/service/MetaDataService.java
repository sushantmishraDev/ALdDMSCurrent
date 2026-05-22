package com.dms.service;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.model.MetaData;
import com.dms.model.MetaField;




@Service
public class MetaDataService {	

	@PersistenceContext(unitName="persistenceUnitDMS")
	@Qualifier(value = "entityManagerFactory2")
	private EntityManager em;
	
	@Transactional("transactionManager2")
	public  MetaData save( MetaData metaData) {
		 MetaData result = new  MetaData();		
		try {
			result = em.merge(metaData);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	@Transactional
	public List< MetaField> getAll() {
		List< MetaField> result = new ArrayList< MetaField>() ;
		try{
			result = em.createQuery("SELECT m FROM MetaField m where mf_rec_status =1 ORDER BY m.mf_sequence ").getResultList();
		}catch(Exception e)	{
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional
	public List<Object> getDropDownList(String query) {
		List<Object> result = new ArrayList<Object>() ;
		try{
			result = em.createQuery(query).getResultList();
		}catch(Exception e)	{
			e.printStackTrace();
		}
		return result;
	}	
	
	@Transactional
	public List< MetaData> getAllByfdid(Long fd_id) {
		List< MetaData> result = new ArrayList< MetaData>() ;
		try{
			result = em.createQuery("SELECT md FROM  MetaData md_rec_status =1 AND md where md_fd_mid = "+fd_id).getResultList();
		}catch(Exception e)	{
			e.printStackTrace();
		}
		return result;
	}
	@Transactional
	public  MetaData getByPk(Long md_id) {
		 MetaData result = new  MetaData() ;
		try{
			result = ( MetaData) em.createQuery("SELECT md FROM  MetaData md where md_id = "+md_id).getSingleResult();
		}catch(Exception e)	{
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional
	public List< MetaData> getAllData(Long md_fd_mid) {
		List< MetaData> result = new ArrayList< MetaData>() ;
		try{
			Query query = em
					.createQuery("SELECT m FROM  MetaData m WHERE md_rec_status =1 AND md_fd_mid=:md_fd_mid");
			query.setParameter("md_fd_mid", md_fd_mid);			
			result = (List< MetaData>) query.getResultList();
		}catch(Exception e)	{
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional
	public List< MetaData> getByfd_mfid(Long md_fd_mid,Long md_mf_mid) {
		List< MetaData> result = new ArrayList< MetaData>() ;
		try{
			Query query = em
					.createQuery("SELECT m FROM  MetaData m WHERE md_rec_status =1 AND md_fd_mid=:md_fd_mid and md_mf_mid=:md_mf_mid");
			query.setParameter("md_fd_mid", md_fd_mid).setParameter("md_mf_mid", md_mf_mid);			
			result = (List< MetaData>) query.getResultList();
		}catch(Exception e)	{
			e.printStackTrace();
		}
		return result;
	}
	

	
	
	@Transactional
	public void deleteByPk(Long id) {
		 MetaData r2 = em.find( MetaData.class, id);
		em.remove(r2);
	}
	
	@Transactional
	public Integer getByMaxSequence(Long md_fd_mid, Long md_mf_mid) {
		// TODO Auto-generated method stub
		Integer sequence=2;
		try{
			Query query = em
					.createQuery("select max(md_rec_status) from  MetaData where md_fd_mid=:md_fd_mid and md_mf_mid=:md_mf_mid");
			query.setParameter("md_fd_mid", md_fd_mid).setParameter("md_mf_mid", md_mf_mid);			
			sequence = (Integer) query.getSingleResult();
		}catch(Exception e)	{
			e.printStackTrace();
		}
		return sequence;
	}



}
