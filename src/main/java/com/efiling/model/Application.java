package com.efiling.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "application")
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_seq")
	@SequenceGenerator(name = "application_seq", sequenceName = "application_seq", allocationSize = 1)
	@Column(name = "ap_id")
	private Long ap_id;
	
	@Column(name = "ap_fd_mid")
	private Long ap_fd_mid;

	@Column(name = "ap_no")
	private Integer ap_no;
	
	@Column(name="ap_year")
	private Integer ap_year;
	
	@Column(name = "ap_at_mid")
	private Long ap_at_mid;

	@Column(name = "ap_filed_by")
	private Integer ap_filed_by;

	@Column(name = "ap_applicant_name")
	private String ap_applicant_name;


	@Column(name = "ap_cr_by")
	private Long ap_cr_by;
	
	@Column(name = "ap_cr_date")
	private Date ap_cr_date;

	@Column(name = "ap_stage_lid")
	private Long ap_stage_lid;

	@Column(name = "ap_draft_no")
	private String ap_draft_no;
	
	@Column(name = "ap_assign_to")
	private Long ap_assign_to;
	
	@Column(name = "ap_appl_email")
	private String ap_appl_email;
	
	@Column(name = "ap_appl_mobile")
	private String ap_appl_mobile;
	
	@Column(name = "ap_diary_no")
	private String ap_diary_no;
	

	@Column(name = "ap_olr_no")
	private Integer ap_olr_no;

	@Column(name = "ap_olr_year")
	private Integer ap_olr_year;
	
	@OneToOne
	@JoinColumn(name="ap_at_mid",referencedColumnName="at_id",insertable=false,updatable=false)
	private EfilingApplicationTypes applicationType;
	
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ap_stage_lid",insertable = false, updatable = false)
	private EfilingLookup caseStage;
	
	@OneToOne
	@JoinColumn(name="ap_fd_mid",insertable=false,updatable=false)
	private EfilingCaseFileDetail caseFileDetail;
	
	@OneToOne
	@JoinColumn(name="ap_cr_by",insertable=false,updatable=false)
	private EfilingUser counsel;

	
	public Long getAp_id() {
		return ap_id;
	}

	public void setAp_id(Long ap_id) {
		this.ap_id = ap_id;
	}

	public Long getAp_fd_mid() {
		return ap_fd_mid;
	}

	public void setAp_fd_mid(Long ap_fd_mid) {
		this.ap_fd_mid = ap_fd_mid;
	}

	public Integer getAp_no() {
		return ap_no;
	}

	public void setAp_no(Integer ap_no) {
		this.ap_no = ap_no;
	}

	public Integer getAp_year() {
		return ap_year;
	}

	public void setAp_year(Integer ap_year) {
		this.ap_year = ap_year;
	}

	public Long getAp_at_mid() {
		return ap_at_mid;
	}

	public void setAp_at_mid(Long ap_at_mid) {
		this.ap_at_mid = ap_at_mid;
	}

	public Integer getAp_filed_by() {
		return ap_filed_by;
	}

	public void setAp_filed_by(Integer ap_filed_by) {
		this.ap_filed_by = ap_filed_by;
	}

	public String getAp_applicant_name() {
		return ap_applicant_name;
	}

	public void setAp_applicant_name(String ap_applicant_name) {
		this.ap_applicant_name = ap_applicant_name;
	}

	public Long getAp_cr_by() {
		return ap_cr_by;
	}

	public void setAp_cr_by(Long ap_cr_by) {
		this.ap_cr_by = ap_cr_by;
	}

	public Date getAp_cr_date() {
		return ap_cr_date;
	}

	public void setAp_cr_date(Date ap_cr_date) {
		this.ap_cr_date = ap_cr_date;
	}

	public Long getAp_stage_lid() {
		return ap_stage_lid;
	}

	public void setAp_stage_lid(Long ap_stage_lid) {
		this.ap_stage_lid = ap_stage_lid;
	}

	public String getAp_draft_no() {
		return ap_draft_no;
	}

	public void setAp_draft_no(String ap_draft_no) {
		this.ap_draft_no = ap_draft_no;
	}

	public Long getAp_assign_to() {
		return ap_assign_to;
	}

	public void setAp_assign_to(Long ap_assign_to) {
		this.ap_assign_to = ap_assign_to;
	}

	public String getAp_appl_email() {
		return ap_appl_email;
	}

	public void setAp_appl_email(String ap_appl_email) {
		this.ap_appl_email = ap_appl_email;
	}

	public String getAp_appl_mobile() {
		return ap_appl_mobile;
	}

	public void setAp_appl_mobile(String ap_appl_mobile) {
		this.ap_appl_mobile = ap_appl_mobile;
	}

	public String getAp_diary_no() {
		return ap_diary_no;
	}

	public void setAp_diary_no(String ap_diary_no) {
		this.ap_diary_no = ap_diary_no;
	}

	
	public EfilingApplicationTypes getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(EfilingApplicationTypes applicationType) {
		this.applicationType = applicationType;
	}


	

	public EfilingCaseFileDetail getCaseFileDetail() {
		return caseFileDetail;
	}

	public void setCaseFileDetail(EfilingCaseFileDetail caseFileDetail) {
		this.caseFileDetail = caseFileDetail;
	}

	

	public EfilingLookup getCaseStage() {
		return caseStage;
	}

	public void setCaseStage(EfilingLookup caseStage) {
		this.caseStage = caseStage;
	}

	public EfilingUser getCounsel() {
		return counsel;
	}

	public void setCounsel(EfilingUser counsel) {
		this.counsel = counsel;
	}

	public Integer getAp_olr_no() {
		return ap_olr_no;
	}

	public void setAp_olr_no(Integer ap_olr_no) {
		this.ap_olr_no = ap_olr_no;
	}

	public Integer getAp_olr_year() {
		return ap_olr_year;
	}

	public void setAp_olr_year(Integer ap_olr_year) {
		this.ap_olr_year = ap_olr_year;
	}	
	
	@Override
	public String toString() {
	    return "Application{" +
	            "ap_id=" + ap_id +
	            ", ap_fd_mid=" + ap_fd_mid +
	            ", ap_no=" + ap_no +
	            ", ap_year=" + ap_year +
	            ", ap_at_mid=" + ap_at_mid +
	            ", ap_filed_by=" + ap_filed_by +
	            ", ap_applicant_name='" + ap_applicant_name + '\'' +
	            ", ap_cr_by=" + ap_cr_by +
	            ", ap_cr_date=" + ap_cr_date +
	            ", ap_stage_lid=" + ap_stage_lid +
	            ", ap_draft_no='" + ap_draft_no + '\'' +
	            ", ap_assign_to=" + ap_assign_to +
	            ", ap_appl_email='" + ap_appl_email + '\'' +
	            ", ap_appl_mobile='" + ap_appl_mobile + '\'' +
	            ", ap_diary_no='" + ap_diary_no + '\'' +
	            ", ap_olr_no=" + ap_olr_no +
	            ", ap_olr_year=" + ap_olr_year +
	            ", applicationType=" + (applicationType != null ? applicationType.getAt_id() : null) +
	            '}';
	}
	
	
}
