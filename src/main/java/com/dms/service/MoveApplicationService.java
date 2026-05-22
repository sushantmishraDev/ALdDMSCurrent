package com.dms.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.model.CaseFileDetail;
import com.dms.model.IndexField;
import com.dms.model.Lookup;
import com.dms.model.SubDocument;
import com.efiling.model.Application;
import com.efiling.model.ApplicationIndexFieldMapping;
import com.efiling.model.ApplicationStage;
import com.efiling.model.EfilingSubApplication;
import com.itextpdf.text.pdf.PdfReader;
import com.dms.model.SubApplication;

@Service
public class MoveApplicationService {
	
	  @Autowired
	    private ApplicationService applicationService;

	    @Autowired
	    private CaseFileDetailService caseFileDetailService;

	    @Autowired
	    private LookupService lookupService;

	    @Autowired
	    private SubDocumentService subDocumentService;

	    @Autowired
	    private MetaDataService metaDataService;
	    
	    
	    
	    // ======================== Vijay Chaurasiya============================================
	    
	    public String moveApplications() {

	        List<Application> applicationfileList = applicationService.getApplicationsForMove();

	        HashMap<Long, Long> indexFieldMapping = new HashMap<>();

	        List<ApplicationIndexFieldMapping> appIndexFieldList =
	                applicationService.getApplicationIndexFieldMapping();

	        for (ApplicationIndexFieldMapping daim : appIndexFieldList) {
	            indexFieldMapping.put(daim.getAim_at_mid(), daim.getAim_if_mid());
	        }

	      String result=  moveApplicationFiles(applicationfileList, indexFieldMapping, applicationService, caseFileDetailService,
					lookupService, subDocumentService, metaDataService);
	      
	      return result;
	    }
	    
	    
	  //================== ======================================================
	
	    
	    public static String moveApplicationFiles(List<Application> appList, HashMap<Long, Long> aim,
				ApplicationService applicationService, CaseFileDetailService caseFileDetailService,
				LookupService lookupService, SubDocumentService subDocumentService, MetaDataService metaDataService) {

	    	int total = 0;
	    	int success = 0;
	    	int failed = 0;
	    	
	    	
			for (Application app : appList) {
				
				total++;
				
				System.out.println("app_draft_no " + app.getAp_draft_no() + " ap_fd_mid " + app.getAp_fd_mid());
				Long at_mid = app.getAp_at_mid();
				if (aim.containsKey(at_mid)) {

					try {
						CaseFileDetail dmscfd = caseFileDetailService.getCaseFileByCaseTypeNoYear(
								app.getCaseFileDetail().getFd_case_type(), app.getCaseFileDetail().getFd_case_no(),
								app.getCaseFileDetail().getFd_case_year());

						if (dmscfd.getFd_document_name() == null) {
							dmscfd.setFd_case_type(app.getCaseFileDetail().getFd_case_type());
							dmscfd.setFd_case_no(app.getCaseFileDetail().getFd_case_no());
							dmscfd.setFd_case_year(app.getCaseFileDetail().getFd_case_year());
							dmscfd.setFd_document_name(app.getCaseFileDetail().getCaseType().getCt_label()
									+ app.getCaseFileDetail().getFd_case_no() + app.getCaseFileDetail().getFd_case_year());
							dmscfd.setFd_file_source("A");
							dmscfd.setFd_rec_status(1);
							dmscfd.setFd_stage_lid(1000047L);
							dmscfd.setFd_cr_by(1L);
							dmscfd.setFd_cr_date(new Date());

							dmscfd = caseFileDetailService.save(dmscfd);

						} // end if loop

						boolean copy_flag = false, sub_flag = false;
						int no_of_pages = 0;

						IndexField indexField = subDocumentService.getIndexField(aim.get(at_mid));

						Long docCount = applicationService.getCount(dmscfd.getFd_id());
						docCount++;
						String doc_name = dmscfd.getFd_document_name() + "_" + indexField.getIf_type_code() + "_"
								+ docCount;

						Lookup lookupDMS = lookupService.getLookUpObject("REPOSITORYPATH");
						Lookup lookupEfiling = lookupService.getLookUpObject("EFILING_PATH");

						String sourcebasepath = lookupEfiling.getLk_longname();
						String destbasepath = lookupDMS.getLk_longname();

						File source = new File(sourcebasepath + File.separator + "application" + File.separator
								+ app.getAp_draft_no() + ".pdf");

						File dest = new File(destbasepath + File.separator + dmscfd.getCaseType().getCt_label()
								+ File.separator + indexField.getIf_name() + File.separator + doc_name + ".pdf");

					
						  try {
							  
							  FileUtils.copyFile(source, dest); 
						      PdfReader reader = new PdfReader(dest.getAbsolutePath());
						      no_of_pages = reader.getNumberOfPages();
						      copy_flag = true;
						      
						  } catch (IOException e) 
						  { 
							  e.printStackTrace(); 
							  
							  }
							  
							  
						 
                         
						if (copy_flag && no_of_pages > 0) 
						
						
						
						{
							
							
							success++;
							
							
							SubDocument dmssb = new SubDocument();

							dmssb.setSd_fd_mid(dmscfd.getFd_id());
							dmssb.setSd_if_mid(indexField.getIf_id());
							dmssb.setSd_document_name(doc_name);
							dmssb.setSd_document_id(app.getAp_at_mid().intValue());
							dmssb.setSd_document_no(app.getAp_no());
							dmssb.setSd_document_year(app.getAp_year());
							dmssb.setSd_olr_no(app.getAp_olr_no()); // olr no
							dmssb.setSd_olr_year(app.getAp_olr_year()); // olr year
							dmssb.setSd_major_sequence(indexField.getIf_sequence());
							dmssb.setSd_minor_sequence(docCount.intValue());
							dmssb.setSd_no_of_pages(no_of_pages);
							dmssb.setSd_cr_date(new Date());
							dmssb.setSd_cr_by(1L);
							dmssb.setSd_rec_status(1);
							dmssb.setSd_version(1);
							if (app.getAp_filed_by() == 1) {
								dmssb.setSd_party("P");
							} else if (app.getAp_filed_by() == 2) {
								dmssb.setSd_party("R");
							} else if (app.getAp_filed_by() == 3) {
								dmssb.setSd_party("O");
							}

							dmssb.setSd_description(app.getAp_applicant_name());
							dmssb.setSd_counsel(app.getCounsel().getUm_fullname());

							ApplicationStage aps = applicationService.getFileSubmittedStage(app.getAp_id());

							dmssb.setSd_submitted_date(aps.getAs_cr_date());

    						SubDocument dmssd = subDocumentService.save(dmssb);
						
							

							// Move Sub Applications to Sub Application table in DMS
							List<EfilingSubApplication> subApplications = applicationService.getSubApplications(app.getAp_id());

							if (!subApplications.isEmpty()) 
							{
								for (EfilingSubApplication subApplication : subApplications) 
								{
									Long sub_at_mid = subApplication.getSb_ap_at_mid();
									if (aim.containsKey(sub_at_mid))
									{
										SubApplication dmsSubApplication = new SubApplication();
										
										dmsSubApplication.setSb_ap_sd_mid(dmssd.getSd_id());
										dmsSubApplication.setSb_ap_no(subApplication.getSb_ap_no());
										dmsSubApplication.setSb_ap_year(subApplication.getSb_ap_year());
										dmsSubApplication.setSb_ap_at_mid(subApplication.getSb_ap_at_mid());
										dmsSubApplication.setSb_ap_rec_status(1);
										dmsSubApplication.setSb_ap_cr_date(new Date());
										dmsSubApplication.setSb_ap_fd_mid(dmssd.getSd_fd_mid());
										
										subDocumentService.save(dmsSubApplication);
										

									}
								}
							}

							sub_flag = true;

							if (copy_flag && sub_flag) {
								System.out.println(app.getAp_draft_no() + " File Moved");

								ApplicationStage as = new ApplicationStage();
								as.setAs_ap_mid(app.getAp_id());
								as.setAs_cr_date(new Date());
								as.setAs_stage_lid(1000049L);
								as.setAs_cr_by(1562l);

								
								  ApplicationStage saveData= applicationService.saveStage(as);
								  System.out.println("==================Application Stage updated with id " +
								  saveData); System.out.println("=========DB stage vs new: " +
								  app.getAp_stage_lid());
								 
								// update register case file in efiling
								app.setAp_stage_lid(1000049L);
								Application updatedApplication=applicationService.save(app);
								System.out.println("==================Application updated with id " + updatedApplication);
							}

						}

					}catch (Exception e) {
					    failed++;
					    e.printStackTrace();
					}
				} // end if(aim.containsKey(at_mid))
			} // end for each loop
			
			return "Total: " + total + ", Success: " + success + ", Failed: " + failed;

		}

	    // ======================== Vijay Chaurasiya============================================    
	    
	    
	

}
