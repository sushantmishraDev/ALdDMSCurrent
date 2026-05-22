package com.dms.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.efiling.model.EfilingApplication;

@Entity
@Table(name="case_file_details")
public class CaseFileDetail{

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="case_file_details_seq")
	@SequenceGenerator(name="case_file_details_seq", sequenceName="case_file_details_seq", allocationSize=1)
	@Column(name="fd_id")
	private Long fd_id;
	
	@Column(name="fd_case_type")
	private Long fd_case_type;
	
	@Column(name="fd_case_no")
	private String fd_case_no="";
	
	@Column(name="fd_case_year")
	private Integer fd_case_year;	
	
	@Column(name="fd_document_name")
	private String fd_document_name;
	
	@Column(name="fd_file_source")
	private String fd_file_source;
	
	@Column(name="fd_rec_status")
	private int fd_rec_status;
	
	@Column(name="fd_stage_lid")
	private Long fd_stage_lid;
	
	@Column(name="fd_cr_by")
	private Long fd_cr_by;
	
	@Column(name="fd_cr_date")
	private Date fd_cr_date;	
	
	@Column(name="fd_mod_by")
	private Long fd_mod_by;	
	
	@Column(name="fd_mod_date")
	private Date fd_mod_date;
	
	@Column(name="fd_disposal_date")
	private Date fd_disposal_date;		

	@Column(name="fd_category_code")
	private Long fd_category_code;
  
	@Column(name="fd_district")
	private Long fd_district;
  
	@Column(name="fd_bench_type")
	private Long fd_bench_type;
  
	@Column(name="fd_act_section")
	private String fd_act_section;  
  
	@Column(name="fd_keywords")
	private String fd_keywords;
	

	@Column(name="fd_pet_adv")
	private String fd_pet_adv;
	
	

	@Column(name="fd_res_adv")
	private String fd_res_adv;
  
	@Column(name="fd_bench_code")
	private Long fd_bench_code;
	
	@Column(name="fd_assign_to")
	private Long fd_assign_to;
	
	@Column(name="fd_rc_flag")
	private Boolean fd_rc_flag;
	
	@Column(name="fd_cl_flag")
	private Boolean fd_cl_flag;
	
	@Column(name="fd_cl_by")
	private Long fd_cl_by;
	
	
	@Column(name="fd_cl_date")
	private Date fd_cl_date;
	
	
	
	
	public String getFd_pet_adv() {
		return fd_pet_adv;
	}

	public void setFd_pet_adv(String fd_pet_adv) {
		this.fd_pet_adv = fd_pet_adv;
	}

	public String getFd_res_adv() {
		return fd_res_adv;
	}

	public void setFd_res_adv(String fd_res_adv) {
		this.fd_res_adv = fd_res_adv;
	}

	public Boolean getFd_cl_flag() {
		return fd_cl_flag;
	}

	public void setFd_cl_flag(Boolean fd_cl_flag) {
		this.fd_cl_flag = fd_cl_flag;
	}

	public Long getFd_cl_by() {
		return fd_cl_by;
	}

	public void setFd_cl_by(Long fd_cl_by) {
		this.fd_cl_by = fd_cl_by;
	}

	public Date getFd_cl_date() {
		return fd_cl_date;
	}

	public void setFd_cl_date(Date fd_cl_date) {
		this.fd_cl_date = fd_cl_date;
	}

	@Transient
	private Long cl_list_type_mid;
	
	@Transient
	private Long cl_court_no;
	
	@Transient
	private Date cl_dol;
	
	@Transient
	private Integer cl_serial_no;
	
	@Transient
	private String status;
	
	@Transient
	private Long cl_to_judge;
	
	@Transient
	private Integer notificationCount;
	
	@Transient
	private Integer pendingAppCount;
	
	@Transient
	private List<EfilingApplication> efilingApplicationList;
	
	
	@Transient
	private List<EfilingApplication> efilingPendingApplicationList;
	
	
	
	
	

	public Integer getPendingAppCount() {
		return pendingAppCount;
	}

	public void setPendingAppCount(Integer pendingAppCount) {
		this.pendingAppCount = pendingAppCount;
	}

	public List<EfilingApplication> getEfilingPendingApplicationList() {
		return efilingPendingApplicationList;
	}

	public void setEfilingPendingApplicationList(List<EfilingApplication> efilingPendingApplicationList) {
		this.efilingPendingApplicationList = efilingPendingApplicationList;
	}

	public Integer getNotificationCount() {
		return notificationCount;
	}

	public void setNotificationCount(Integer notificationCount) {
		this.notificationCount = notificationCount;
	}

	public List<EfilingApplication> getEfilingApplicationList() {
		return efilingApplicationList;
	}

	public void setEfilingApplicationList(List<EfilingApplication> efilingApplicationList) {
		this.efilingApplicationList = efilingApplicationList;
	}

	
	
	public Boolean getFd_rc_flag() {
		return fd_rc_flag;
	}

	public void setFd_rc_flag(Boolean fd_rc_flag) {
		this.fd_rc_flag = fd_rc_flag;
	}

	public Long getCl_to_judge() {
		return cl_to_judge;
	}

	public void setCl_to_judge(Long cl_to_judge) {
		this.cl_to_judge = cl_to_judge;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCl_serial_no() {
		return cl_serial_no;
	}

	public void setCl_serial_no(Integer cl_serial_no) {
		this.cl_serial_no = cl_serial_no;
	}

	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fd_case_type",insertable = false, updatable = false)
	private CaseType caseType;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "pt_fd_mid")
	@Where(clause="pt_rec_status=1")
	@OrderBy("pt_sequence ASC")
	private List<Petitioner> petitioners;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "rt_fd_mid")
	@Where(clause="rt_rec_status=1")
	@OrderBy("rt_sequence ASC")
	private List<Respondent> respondents;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "pc_fd_mid")
	@Where(clause="pc_rec_status=1")
	private List<PetitionerCounsel> pCounsels;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "rc_fd_mid")
	@Where(clause="rc_rec_status=1")
	private List<RespondentCounsel> rCounsels;
	
	

	
	/*@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "sd_fd_mid")
	@Where(clause="sd_rec_status=1")
	private List<SubDocument> subDocument;*/
	
	/*@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "io_fd_mid")
	private List<ImpugnedOrder> impugnedOrders;
	*/
	
	@Transient
	private CaseNominated caseNominated;

	public CaseNominated getCaseNominated() {
		return caseNominated;
	}

	public void setCaseNominated(CaseNominated caseNominated) {
		this.caseNominated = caseNominated;
	}

	@Transient
	private String case_type;
	
	
	@Transient
	private String judgement_date;
	
	@Transient
	private String bench_code;
	
	@Transient
	private Long judgement_id;
	
	@Transient
	private String first_petitioner;
	
	@Transient
	private String first_respondent;
	
	
	

	public Long getFd_id() {
		return fd_id;
	}

	public void setFd_id(Long fd_id) {
		this.fd_id = fd_id;
	}

	public Long getFd_case_type() {
		return fd_case_type;
	}

	public void setFd_case_type(Long fd_case_type) {
		this.fd_case_type = fd_case_type;
	}

	public String getFd_case_no() {
		return fd_case_no;
	}

	public void setFd_case_no(String fd_case_no) {
		this.fd_case_no = fd_case_no;
	}

	public Integer getFd_case_year() {
		return fd_case_year;
	}

	public void setFd_case_year(Integer fd_case_year) {
		this.fd_case_year = fd_case_year;
	}

	public String getFd_document_name() {
		return fd_document_name;
	}

	public void setFd_document_name(String fd_document_name) {
		this.fd_document_name = fd_document_name;
	}

	public String getFd_file_source() {
		return fd_file_source;
	}

	public void setFd_file_source(String fd_file_source) {
		this.fd_file_source = fd_file_source;
	}

	public int getFd_rec_status() {
		return fd_rec_status;
	}

	public void setFd_rec_status(int fd_rec_status) {
		this.fd_rec_status = fd_rec_status;
	}

	public Long getFd_stage_lid() {
		return fd_stage_lid;
	}

	public void setFd_stage_lid(Long fd_stage_lid) {
		this.fd_stage_lid = fd_stage_lid;
	}

	public Long getFd_cr_by() {
		return fd_cr_by;
	}

	public void setFd_cr_by(Long fd_cr_by) {
		this.fd_cr_by = fd_cr_by;
	}

	public Date getFd_cr_date() {
		return fd_cr_date;
	}

	public void setFd_cr_date(Date fd_cr_date) {
		this.fd_cr_date = fd_cr_date;
	}

	public Long getFd_mod_by() {
		return fd_mod_by;
	}

	public void setFd_mod_by(Long fd_mod_by) {
		this.fd_mod_by = fd_mod_by;
	}

	public Date getFd_mod_date() {
		return fd_mod_date;
	}

	public void setFd_mod_date(Date fd_mod_date) {
		this.fd_mod_date = fd_mod_date;
	}

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public String getCase_type() {
		return case_type;
	}

	public void setCase_type(String case_type) {
		this.case_type = case_type;
	}

	public String getJudgement_date() {
		return judgement_date;
	}

	public void setJudgement_date(String judgement_date) {
		this.judgement_date = judgement_date;
	}

	public String getBench_code() {
		return bench_code;
	}

	public void setBench_code(String bench_code) {
		this.bench_code = bench_code;
	}

	public Long getJudgement_id() {
		return judgement_id;
	}

	public void setJudgement_id(Long judgement_id) {
		this.judgement_id = judgement_id;
	}

	/*public List<ImpugnedOrder> getImpugnedOrders() {
		return impugnedOrders;
	}

	public void setImpugnedOrders(List<ImpugnedOrder> impugnedOrders) {
		this.impugnedOrders = impugnedOrders;
	}*/

	public List<Petitioner> getPetitioners() {
		return petitioners;
	}

	public void setPetitioners(List<Petitioner> petitioners) {
		this.petitioners = petitioners;
	}

	public List<Respondent> getRespondents() {
		return respondents;
	}

	public void setRespondents(List<Respondent> respondents) {
		this.respondents = respondents;
	}

	public List<PetitionerCounsel> getpCounsels() {
		return pCounsels;
	}

	public void setpCounsels(List<PetitionerCounsel> pCounsels) {
		this.pCounsels = pCounsels;
	}

	public List<RespondentCounsel> getrCounsels() {
		return rCounsels;
	}

	public void setrCounsels(List<RespondentCounsel> rCounsels) {
		this.rCounsels = rCounsels;
	}

	public Date getFd_disposal_date() {
		return fd_disposal_date;
	}

	public void setFd_disposal_date(Date fd_disposal_date) {
		this.fd_disposal_date = fd_disposal_date;
	}

	public Long getFd_category_code() {
		return fd_category_code;
	}

	public void setFd_category_code(Long fd_category_code) {
		this.fd_category_code = fd_category_code;
	}

	public Long getFd_district() {
		return fd_district;
	}

	public void setFd_district(Long fd_district) {
		this.fd_district = fd_district;
	}

	public Long getFd_bench_type() {
		return fd_bench_type;
	}

	public void setFd_bench_type(Long fd_bench_type) {
		this.fd_bench_type = fd_bench_type;
	}

	public String getFd_act_section() {
		return fd_act_section;
	}

	public void setFd_act_section(String fd_act_section) {
		this.fd_act_section = fd_act_section;
	}

	public String getFd_keywords() {
		return fd_keywords;
	}

	public void setFd_keywords(String fd_keywords) {
		this.fd_keywords = fd_keywords;
	}

	public Long getFd_bench_code() {
		return fd_bench_code;
	}

	public void setFd_bench_code(Long fd_bench_code) {
		this.fd_bench_code = fd_bench_code;
	}

	public String getFirst_petitioner() {
		return first_petitioner;
	}

	public void setFirst_petitioner(String first_petitioner) {
		this.first_petitioner = first_petitioner;
	}

	public String getFirst_respondent() {
		return first_respondent;
	}

	public void setFirst_respondent(String first_respondent) {
		this.first_respondent = first_respondent;
	}

	public Long getCl_list_type_mid() {
		return cl_list_type_mid;
	}

	public void setCl_list_type_mid(Long cl_list_type_mid) {
		this.cl_list_type_mid = cl_list_type_mid;
	}

	public Long getCl_court_no() {
		return cl_court_no;
	}

	public void setCl_court_no(Long cl_court_no) {
		this.cl_court_no = cl_court_no;
	}

	public Date getCl_dol() {
		return cl_dol;
	}

	public void setCl_dol(Date cl_dol) {
		this.cl_dol = cl_dol;
	}

/*	public List<SubDocument> getSubDocument() {
		return subDocument;
	}

	public void setSubDocument(List<SubDocument> subDocument) {
		this.subDocument = subDocument;
	}
*/
	public Long getFd_assign_to() {
		return fd_assign_to;
	}

	public void setFd_assign_to(Long fd_assign_to) {
		this.fd_assign_to = fd_assign_to;
	}

	@Override
	public String toString() {
		return "CaseFileDetail [fd_id=" + fd_id + ", fd_case_type=" + fd_case_type + ", fd_case_no=" + fd_case_no
				+ ", fd_case_year=" + fd_case_year + ", fd_document_name=" + fd_document_name + ", fd_file_source="
				+ fd_file_source + ", fd_rec_status=" + fd_rec_status + ", fd_stage_lid=" + fd_stage_lid + ", fd_cr_by="
				+ fd_cr_by + ", fd_cr_date=" + fd_cr_date + ", fd_mod_by=" + fd_mod_by + ", fd_mod_date=" + fd_mod_date
				+ ", fd_disposal_date=" + fd_disposal_date + ", fd_category_code=" + fd_category_code + ", fd_district="
				+ fd_district + ", fd_bench_type=" + fd_bench_type + ", fd_act_section=" + fd_act_section
				+ ", fd_keywords=" + fd_keywords + ", fd_bench_code=" + fd_bench_code + ", fd_assign_to=" + fd_assign_to
				+ ", fd_rc_flag=" + fd_rc_flag + ", cl_list_type_mid=" + cl_list_type_mid + ", cl_court_no="
				+ cl_court_no + ", cl_dol=" + cl_dol + ", cl_serial_no=" + cl_serial_no + ", status=" + status
				+ ", cl_to_judge=" + cl_to_judge + ", notificationCount=" + notificationCount
				+ ", efilingApplicationList=" + efilingApplicationList + ", caseType=" + caseType + ", petitioners="
				+ petitioners + ", respondents=" + respondents + ", pCounsels=" + pCounsels + ", rCounsels=" + rCounsels
				+ ", caseNominated=" + caseNominated + ", case_type=" + case_type
				+ ", judgement_date=" + judgement_date + ", bench_code=" + bench_code + ", judgement_id=" + judgement_id
				+ ", first_petitioner=" + first_petitioner + ", first_respondent=" + first_respondent + "]";
	}

	

	
	
}