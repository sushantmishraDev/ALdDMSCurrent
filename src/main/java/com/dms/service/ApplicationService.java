package com.dms.service;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efiling.model.Application;
import com.efiling.model.ApplicationIndexFieldMapping;
import com.efiling.model.ApplicationStage;
import com.efiling.model.EfilingSubApplication;



@Service
public class ApplicationService {
	@PersistenceContext(unitName = "persistenceUnitEfiling")
	@Qualifier(value = "entityManagerFactory")
	private EntityManager em;

	@PersistenceContext(unitName = "persistenceUnitDMS")
	@Qualifier(value = "entityManagerFactory2")
	private EntityManager em2;

	@Transactional("transactionManagerEfiling")
	public Application save(Application s) {

		System.out.println("EntityManager contains entity: " + em.contains(s));
		Application master = null;
		try {
			master = em.merge(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return master;
	}

	@Transactional("transactionManagerEfiling")
	public ApplicationStage saveStage(ApplicationStage s) {
		
		System.out.println(
			    "Transaction active: " +
			    org.springframework.transaction.support.TransactionSynchronizationManager.isActualTransactionActive()
			);
		
		System.out.println("EntityManager contains entity: " + em.contains(s));

	    try {
	        return em.merge(s);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;  // ✅ VERY IMPORTANT
	    }
	}

	@Transactional
	public List<Application> getApplicationsForMove() {
		List<Application> result = new ArrayList<Application>();
		try {
			result = em.createQuery("SELECT ap FROM Application ap where ap.ap_stage_lid=1000043").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public ApplicationStage getFileSubmittedStage(Long ap_id) {
		ApplicationStage result = new ApplicationStage();
		try {
			result = (ApplicationStage) em.createQuery("SELECT aps FROM ApplicationStage aps where aps.as_ap_mid="
					+ ap_id + " and aps.as_stage_lid=1000042").getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public List<ApplicationIndexFieldMapping> getApplicationIndexFieldMapping() {
		List<ApplicationIndexFieldMapping> result = new ArrayList<ApplicationIndexFieldMapping>();
		try {
			result = em.createQuery("SELECT aim FROM ApplicationIndexFieldMapping aim").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public Long getFilesCount(Long fd_id, Long if_id) {
		Long result = 0L;
		try {
			result = (Long) em2.createQuery("SELECT count(sd) FROM SubDocument sd where sd.sd_fd_mid=" + fd_id
					+ " and sd.sd_if_mid=" + if_id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public Long getCount(Long fd_id) {
		Long result = 0L;
		try {
			result = (Long) em2.createQuery("SELECT count(sd) FROM SubDocument sd where sd.sd_fd_mid=" + fd_id)
					.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<EfilingSubApplication> getSubApplications(Long ap_id) {

		List<EfilingSubApplication> result = new ArrayList<EfilingSubApplication>();
		try {
			result = em.createQuery(
					"SELECT sbap FROM EfilingSubApplication sbap where sbap.sb_ap_mid=:ap_id and sbap.sb_ap_rec_status=1")
					.setParameter("ap_id", ap_id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/*
	 * @Transactional public DMSApplicationTypes getByPk(Long id) {
	 * DMSApplicationTypes result = (DMSApplicationTypes) em.
	 * createQuery("select at from DMSApplicationTypes at where at.at_id = :at_id").
	 * setParameter("at_id", id).getSingleResult(); return result; }
	 * 
	 * @Transactional public List<DMSApplicationTypes> getAllFiles() {
	 * List<DMSApplicationTypes> result = em.
	 * createQuery("select at from DMSApplicationTypes at where at_rec_status = 1").
	 * getResultList(); return result; }
	 */

}
