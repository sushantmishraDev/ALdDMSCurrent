package com.dms.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.model.ApplicationWithPetition;
import com.dms.model.CaseFileDetail;
import com.dms.model.CaseType;
import com.dms.model.CauseList;
import com.dms.model.CauseListNew;
import com.dms.model.CauseListType;
import com.dms.model.CourtMaster;
import com.dms.model.DMSJudge_mapping;
import com.dms.model.SameCrimDetails;
import com.dms.model.SameLcrDetails;

@Service
public class BenchService {
	
	
	@PersistenceContext(unitName="persistenceUnitDMS")
	@Qualifier(value = "entityManagerFactoryDMS")
	EntityManager em;
	
	@Transactional
	public List<CourtMaster> getAllCourts() 
	{		
		List<CourtMaster> cm =null;
		try {
			String query  ="SELECT ct from CourtMaster ct order by ct.cm_value";
			cm=   em.createQuery(query).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cm;
	}
	
	public List<CauseListType> getCuaselistTypeCorrection() {
		
		List<CauseListType> courtmaster=null; 
		try {
			/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_id not in (1,5,7,2,27,28,29,30,31,32,33,34,36)");
			*/
			Query query  =  em.createQuery("SELECT cm from CauseListType cm where cm.clt_id  in (2)  ");
			
			/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_description ilike '"+"%Transferred%"+"'");
			*/courtmaster= (List<CauseListType>) query.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courtmaster;
	}
	
	@Transactional
	public CourtMaster updateCourt(CourtMaster cm) {

		CourtMaster cmnew = null;
    	try {	
    		cmnew= em.merge(cm);	    	
	    }catch (Exception e) {		
	    	e.printStackTrace();	    	
		}
		return cmnew;
	}
	
	@Transactional
	public boolean deleteCltype(String date, Integer cl_court_no,Long clt) {
		// TODO Auto-generated method stub
	boolean result=false;
		try {
			Query query  =  em.createQuery("DELETE  from CauseList c WHERE c.cl_dol ='"+date+"' and c.cl_list_type_mid =:clt "
					+ " and c.cl_court_no=:cl_court_no and c.cl_rec_status !=3").setParameter("cl_court_no",cl_court_no).setParameter("clt",clt);
			query.executeUpdate();
			result=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

        return result;
}
	
	@Transactional
	public boolean deleteCltypeNew(String date, Integer cl_court_no,Long clt) {
		// TODO Auto-generated method stub
		
	boolean result=false;
		try {
			Query query  =  em.createQuery("DELETE  from CauseListNew c WHERE c.cl_dol ='"+date+"' and c.cl_list_type_mid =:clt"
					+ " and c.cl_court_no=:cl_court_no and c.cl_rec_status !=3").setParameter("cl_court_no",cl_court_no).setParameter("clt",clt);
			query.executeUpdate();
			result=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

        return result;
}
	
	@Transactional
	public boolean seleteCltype(String date, Integer cl_court_no,Long clt) {
		// TODO Auto-generated method stub
	boolean result=false;
	List<CauseList> cl=null;
		try {
			Query query  =  em.createQuery("select c from CauseList c WHERE c.cl_dol ='"+date+"' and c.cl_list_type_mid =:clt "
					+ " and c.cl_court_no=:cl_court_no and c.cl_rec_status !=3").setParameter("cl_court_no",cl_court_no).setParameter("clt",clt);
			cl=query.getResultList();
			if(cl.size()!=0) {
			result=true;
			}
			else {
				result=false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

        return result;
}
	
	//judge mapping Multiple judge in one bench
		@Transactional
		public DMSJudge_mapping getJudgeMappingDetails(Integer benchid,String jo_code,Date date) {			
			DMSJudge_mapping Judge_mapping=null;
			/*Date date =new Date();*/
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			String d=formatter.format(date);
			
			try {
				Judge_mapping  =  (DMSJudge_mapping) em.createQuery("SELECT j FROM DMSJudge_mapping j WHERE"
						+ " j.ejm_bench_id ="+benchid+" AND j.ejm_date='"+d+" 00:00:00' AND j.ejm_jo_code='"+jo_code.trim()+"'")
						.setMaxResults(1).getSingleResult();
				//Judge_mapping= (DMSJudge_mapping) query.setMaxResults(1).getSingleResult();			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			return Judge_mapping;
		}
		//judge mapping Multiple judge in one bench
			@Transactional
			 public DMSJudge_mapping saveJudgeMapping(DMSJudge_mapping cl) {
				    
				DMSJudge_mapping judgeMap = null;
			    	try {	
			    		judgeMap= em.merge(cl);	    	
				    }catch (Exception e) {		
				    	e.printStackTrace();	    	
					}
			    	return judgeMap;
			    }
			
			
			@Transactional
			 public CourtMaster save(CourtMaster cl) {
				    
				CourtMaster causeList = null;
			    	try {	
			    		causeList= em.merge(cl);	    	
				    }catch (Exception e) {		
				    	e.printStackTrace();	    	
					}
			    	return causeList;
			    }
			
			@Transactional
			 public CaseFileDetail save(CaseFileDetail cl) {
				    
				CaseFileDetail causeList = null;
			    	try {	
			    		causeList= em.merge(cl);	    	
				    }catch (Exception e) {		
				    	e.printStackTrace();	    	
					}
			    	return causeList;
			    }
			
			
			@Transactional
			public CaseType getCaseTypeFromLabel(String ct_label) {
				System.out.println("case typeeeeeeeeeeeee"+ct_label);
				CaseType ct =null;
				if(ct_label != null) {
				try {
				ct =(CaseType) em.createQuery("select ct from CaseType ct where ct.ct_label=:ct_label").setParameter("ct_label",ct_label).getSingleResult();
				}
				catch(Exception e) {
					e.printStackTrace();
					return ct;
				}
				}
				
				return ct;
			}
			
			@Transactional
			 public CauseList save(CauseList cl) {
				    
					CauseList causeList = null;
			    	try {	
			    		causeList= em.merge(cl);	    	
				    }catch (Exception e) {		
				    	e.printStackTrace();	    	
					}
			    	return causeList;
			    }
			
			@Transactional
			 public CauseListNew save(CauseListNew cl) {
				    
				CauseListNew causeList = null;
			    	try {	
			    		causeList= em.merge(cl);	    	
				    }catch (Exception e) {		
				    	e.printStackTrace();	    	
					}
			    	return causeList;
			    }
			
			@Transactional
			public CaseFileDetail getCaseFile(CaseFileDetail casefile) {
				// TODO Auto-generated method stub
				CaseFileDetail result = null;
				try {
					result = (CaseFileDetail) em.createQuery("SELECT c FROM CaseFileDetail c where c.fd_case_type=:fd_case_type and c.fd_case_no=:fd_case_no and c.fd_case_year=:fd_case_year and fd_rec_status=1")
							.setParameter("fd_case_type", casefile.getFd_case_type()).setParameter("fd_case_no", casefile.getFd_case_no()).setParameter("fd_case_year", casefile.getFd_case_year())
							.getSingleResult();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}

				return result;
			}
			
			
			@Transactional
			 public SameCrimDetails save(SameCrimDetails cl) {
				    
				SameCrimDetails causeList = null;
			    	try {	
			    		causeList= em.merge(cl);	    	
				    }catch (Exception e) {		
				    	e.printStackTrace();	    	
					}
			    	return causeList;
			    }
			
			@Transactional
			 public SameLcrDetails save(SameLcrDetails cl) {
				    
				SameLcrDetails causeList = null;
			    	try {	
			    		causeList= em.merge(cl);	    	
				    }catch (Exception e) {		
				    	e.printStackTrace();	    	
					}
			    	return causeList;
			    }
			
			public List<CourtMaster> getCourtMasterLists() {
				
				List<CourtMaster> courtmaster=null;
				try {
					Query query  =  em.createQuery("SELECT cm from CourtMaster cm where cm.cm_bench_id is not null "
							+ "   order by cm.cm_value");
					courtmaster= (List<CourtMaster>) query.getResultList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return courtmaster;
			}
			
			public List<CauseListType> getCuaselistTypeSupp2(String clt_ccms_list) {
				
				List<CauseListType> courtmaster=null; 
				
				
				try {
					Query query  =  em.createQuery("SELECT cm from CauseListType cm where cm.clt_ccms_list in ("+clt_ccms_list+") and cm.clt_id not in (1,5,7) ");
					
					/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_id  in (1,5,7)   ");*/
					
					/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_description ilike '"+"%Transferred%"+"'");
					*/courtmaster= (List<CauseListType>) query.getResultList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return courtmaster;
			}
	
	
public List<CauseListType> getCuaselistTypeSupp() {
		
		List<CauseListType> courtmaster=null; 
		try {
			Query query  =  em.createQuery("SELECT cm from CauseListType cm where cm.clt_id not in (1,5,7,2,27,28,29,30,31,32,33,34,36)");
			/*Query query  =  em.createQuery("SELECT cm from CauseListType cm where cm.clt_id  in (13)");*/
			
			/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_id  in (1,5,7)   ");*/
			
			/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_description ilike '"+"%Transferred%"+"'");
			*/courtmaster= (List<CauseListType>) query.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courtmaster;
	}

public List<CauseListType> getCuaselistTypetrans(String clt_ccms_list) {
	
	List<CauseListType> courtmaster=null; 
	//clt_ccms_list="1026";
	
	try {
		Query query  =  em.createQuery("SELECT cm from CauseListType cm where cm.clt_ccms_list in ("+clt_ccms_list+") ");
		
		/*Query query  =  em.createQuery("SELECT cm from CauseListType cm where cm.clt_id in(26,27,28,29,30,31,32,33,34,36,38,39,40,3,1,5)");*/
		
		/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_id  in (1,5,7)   ");*/
		
		/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_description ilike '"+"%Transferred%"+"'");
		*/courtmaster= (List<CauseListType>) query.getResultList();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return courtmaster;
}


public List<CauseListType> getNextCauseList(String clt_ccms_list) {
	
	List<CauseListType> courtmaster=null; 
	
	
	try {
		Query query  =  em.createQuery("SELECT cm from CauseListType cm where cm.clt_ccms_list in ("+clt_ccms_list+") ");
		
		/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_id  in (1,5,7)   ");*/
		
		/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_description ilike '"+"%Transferred%"+"'");
		*/courtmaster= (List<CauseListType>) query.getResultList();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return courtmaster;
}

public List<CauseListType> getCuaselistTypeSupp3(String clt_ccms_list) {
	
	List<CauseListType> courtmaster=null; 
	
	
	try {
		Query query  =  em.createQuery("SELECT cm from CauseListType cm where cm.clt_ccms_list in ("+clt_ccms_list+") and cm.clt_id not in (1,5,3) ");
		
		/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_id  in (1,5,7)   ");*/
		
		/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_description ilike '"+"%Transferred%"+"'");
		*/courtmaster= (List<CauseListType>) query.getResultList();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return courtmaster;
}

public List<CauseListType> getCuaselistTypetrans() {
	
	List<CauseListType> courtmaster=null; 
	try {
		Query query  =  em.createQuery("SELECT cm from CauseListType cm where cm.clt_id in(26,27,28,29,30,31,32,33,34,36)");
		
		/*Query query  =  em2.createQuery("SELECT cm from CauseListType cm where cm.clt_description ilike '"+"%Transferred%"+"'");
		*/courtmaster= (List<CauseListType>) query.getResultList();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return courtmaster;
}


	@Transactional
	public CourtMaster getCourtByBenchId(Integer cm_bench_id) {
		CourtMaster cm =null;
		try {
			String query  ="SELECT ct from CourtMaster ct where ct.cm_bench_id =:cm_bench_id";
			cm=   (CourtMaster) em.createQuery(query).setParameter("cm_bench_id",cm_bench_id).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cm;
	}
	
	
	@Transactional
	public CourtMaster getCourtByBenchName(String  cm_name) {
		CourtMaster cm =null;
		try {
			String query  ="SELECT ct from CourtMaster ct where ct.cm_name =:cm_name";
			cm=   (CourtMaster) em.createQuery(query).setParameter("cm_name",cm_name).getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cm;
	}

}
