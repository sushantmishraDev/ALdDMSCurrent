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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "cause_list")
public class CauseList {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cause_list_seq")
	@SequenceGenerator(name = "cause_list_seq", sequenceName = "cause_list_seq", allocationSize = 1)
	@Column(name = "cl_id")
	private Long cl_id;

	@Column(name = "cl_case_type_mid")
	private Long cl_case_type_mid;

	@Column(name = "cl_case_no")
	private String cl_case_no;

	@Column(name = "cl_case_year")
	private Integer cl_case_year;

	@Column(name = "cl_court_no")
	private Integer cl_court_no;

	@Column(name = "cl_transfer_to")
	private Integer cl_transfer_to;

	@Column(name = "cl_serial_no")
	private Integer cl_serial_no;

	@Column(name = "cl_list_type_mid")
	private Long cl_list_type_mid;

	@Column(name = "cl_first_petitioner")
	private String cl_first_petitioner;

	@Column(name = "cl_first_respondent")
	private String cl_first_respondent;

	@Column(name = "cl_petitioner_council")
	private String cl_petitioner_council;

	@Column(name = "cl_respondent_council")
	private String cl_respondent_council;

	@Column(name = "cl_ano")
	private Integer cl_ano;

	@Column(name = "cl_ayr")
	private Integer cl_ayr;

	@Column(name = "cl_applawp")
	private String cl_applawp;

	@Column(name = "cl_applawr")
	private String cl_applawr;

	@Column(name = "cl_stage")
	private Integer cl_stage;

	@Column(name = "cl_dol")
	private Date cl_dol;

	@Column(name = "cl_ecourt_status")
	private Boolean cl_ecourt_status;
	
	@Column(name = "cl_lcr_no")
	private String cl_lcr_no;
	
	@Column(name = "cl_lcr_year")
	private String cl_lcr_year;

	@Transient
	private Date cl_dol1;

	@Column(name = "cl_fd_mid")
	private Long cl_fd_mid;

	@Column(name = "cl_sequence")
	private Integer cl_sequence;
	
	@Column(name = "cl_isapporder")
	private String cl_isapporder;
	

	@Column(name = "cl_bunch_name")
	private String cl_bunch_name;
	

	@Column(name = "cl_case_stage")
	private String cl_case_stage;
	
	

	@Transient
	private Integer count;

	@Transient
	private CauseListType cType;

	@Transient
	private boolean caseChecked;
	
	 @Transient
	private String listTypeName;

	@Transient
	private Date listing_date;

	@Column(name = "cl_rec_status")
	private Integer cl_rec_status;

	@Column(name = "cl_mod_by")
	private Long cl_mod_by;

	@Column(name = "cl_mod_date")
	private Date cl_mod_date;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cl_case_type_mid", insertable = false, updatable = false)
	private CaseType caseType;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cl_list_type_mid", insertable = false, updatable = false)
	private CauseListType clType;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cl_stage_id", insertable = false, updatable = false)
	private Lookup applicationStage;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cl_court_no", insertable = false, updatable = false)
	private CourtMaster courtMaster;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cl_transfer_to", insertable = false, updatable = false)
	private CourtMaster courtMasterForTransfer;
	


	@Transient
	private List<SameCrimDetails> sameCrimDetails;
	
	
	@Transient
	private List<SameLcrDetails> sameLcrDetails;
	
	/*@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name = "scd_cl_id",referencedColumnName="cl_id",insertable = false, updatable = false)	
	@Where(clause = "cl_iscrime = 'Y'")
	private List<SameCrimDetails> sameCrimDetails;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name = "sld_cl_mid",referencedColumnName="cl_id",insertable = false, updatable = false)	
	@Where(clause = "cl_lcr_no !=null")
	private List<SameLcrDetails> sameLcrDetails;*/
	
	
	@Column(name = "cl_ccms_id")
	private Long cl_ccms_id;

	@Transient
	private boolean petAvailable = false;

	@Transient
	private boolean appAvailable = false;

	@Transient
	private boolean appNew = false;

	@Transient
	private String fileSource;
	
	@Column(name = "cl_file_source")
	private String cl_file_source;

	@Column(name = "cl_notice_no")
	private String cl_notice_no;

	@Column(name = "cl_district_name")
	private String cl_district_name;

	@Column(name = "cl_injail_date")
	private String cl_injail_date;

	@Column(name = "cl_crime_no")
	private String cl_crime_no;

	@Column(name = "cl_crime_year")
	private String cl_crime_year;

	@Column(name = "cl_crime_dist")
	private String cl_crime_dist;

	@Column(name = "cl_crime_ps")
	private String cl_crime_ps;

	@Column(name = "cl_iscrime")
	private String cl_iscrime;
	
	@Column(name = "cl_short_order")
	private String cl_short_order;
	
	
	@Column(name = "cl_app_last_date")
	private String cl_app_last_date;
	
	@Column(name = "cl_applied_by")
	private String cl_applied_by;
	
	@Column(name = "cl_next_list")
	private String cl_next_list;
	
	
	@Column(name = "cl_ps_code")
	private String cl_ps_code;
	
	@Transient
	private String cisCaseNo;
	
	
	@Column(name = "cl_islcr")
	private String cl_islcr;
	
	@Column(name = "cl_lcase_name")
	private String cl_lcase_name;
	
	
	

	public String getListTypeName() {
		return listTypeName;
	}

	public void setListTypeName(String listTypeName) {
		this.listTypeName = listTypeName;
	}

	public String getCl_islcr() {
		return cl_islcr;
	}

	public void setCl_islcr(String cl_islcr) {
		this.cl_islcr = cl_islcr;
	}

	public String getCl_lcase_name() {
		return cl_lcase_name;
	}

	public void setCl_lcase_name(String cl_lcase_name) {
		this.cl_lcase_name = cl_lcase_name;
	}

	public String getCl_bunch_name() {
		return cl_bunch_name;
	}

	public void setCl_bunch_name(String cl_bunch_name) {
		this.cl_bunch_name = cl_bunch_name;
	}

	public String getCl_case_stage() {
		return cl_case_stage;
	}

	public void setCl_case_stage(String cl_case_stage) {
		this.cl_case_stage = cl_case_stage;
	}

	public List<SameLcrDetails> getSameLcrDetails() {
		return sameLcrDetails;
	}

	public void setSameLcrDetails(List<SameLcrDetails> sameLcrDetails) {
		this.sameLcrDetails = sameLcrDetails;
	}

	public String getCl_file_source() {
		return cl_file_source;
	}

	public void setCl_file_source(String cl_file_source) {
		this.cl_file_source = cl_file_source;
	}

	public String getCl_isapporder() {
		return cl_isapporder;
	}

	public void setCl_isapporder(String cl_isapporder) {
		this.cl_isapporder = cl_isapporder;
	}

	public String getCl_ps_code() {
		return cl_ps_code;
	}

	public void setCl_ps_code(String cl_ps_code) {
		this.cl_ps_code = cl_ps_code;
	}

	public String getCisCaseNo() {
		return cisCaseNo;
	}

	public void setCisCaseNo(String cisCaseNo) {
		this.cisCaseNo = cisCaseNo;
	}

	public Long getCl_ccms_id() {
		return cl_ccms_id;
	}

	public void setCl_ccms_id(Long cl_ccms_id) {
		this.cl_ccms_id = cl_ccms_id;
	}

	public String getCl_lcr_no() {
		return cl_lcr_no;
	}

	public void setCl_lcr_no(String cl_lcr_no) {
		this.cl_lcr_no = cl_lcr_no;
	}

	public String getCl_lcr_year() {
		return cl_lcr_year;
	}

	public void setCl_lcr_year(String cl_lcr_year) {
		this.cl_lcr_year = cl_lcr_year;
	}

	public List<SameCrimDetails> getSameCrimDetails() {
		return sameCrimDetails;
	}

	public void setSameCrimDetails(List<SameCrimDetails> sameCrimDetails) {
		this.sameCrimDetails = sameCrimDetails;
	}

	public String getCl_app_last_date() {
		return cl_app_last_date;
	}

	public void setCl_app_last_date(String cl_app_last_date) {
		this.cl_app_last_date = cl_app_last_date;
	}

	public String getCl_applied_by() {
		return cl_applied_by;
	}

	public void setCl_applied_by(String cl_applied_by) {
		this.cl_applied_by = cl_applied_by;
	}

	public String getCl_next_list() {
		return cl_next_list;
	}

	public void setCl_next_list(String cl_next_list) {
		this.cl_next_list = cl_next_list;
	}

	public String getCl_short_order() {
		return cl_short_order;
	}

	public void setCl_short_order(String cl_short_order) {
		this.cl_short_order = cl_short_order;
	}

	public String getCl_notice_no() {
		return cl_notice_no;
	}

	public void setCl_notice_no(String cl_notice_no) {
		this.cl_notice_no = cl_notice_no;
	}

	public String getCl_district_name() {
		return cl_district_name;
	}

	public void setCl_district_name(String cl_district_name) {
		this.cl_district_name = cl_district_name;
	}

	public String getCl_injail_date() {
		return cl_injail_date;
	}

	public void setCl_injail_date(String cl_injail_date) {
		this.cl_injail_date = cl_injail_date;
	}

	public String getCl_crime_no() {
		return cl_crime_no;
	}

	public void setCl_crime_no(String cl_crime_no) {
		this.cl_crime_no = cl_crime_no;
	}

	public String getCl_crime_year() {
		return cl_crime_year;
	}

	public void setCl_crime_year(String cl_crime_year) {
		this.cl_crime_year = cl_crime_year;
	}

	public String getCl_crime_dist() {
		return cl_crime_dist;
	}

	public void setCl_crime_dist(String cl_crime_dist) {
		this.cl_crime_dist = cl_crime_dist;
	}

	public String getCl_crime_ps() {
		return cl_crime_ps;
	}

	public void setCl_crime_ps(String cl_crime_ps) {
		this.cl_crime_ps = cl_crime_ps;
	}

	public String getCl_iscrime() {
		return cl_iscrime;
	}

	public void setCl_iscrime(String cl_iscrime) {
		this.cl_iscrime = cl_iscrime;
	}

	public Boolean getCl_ecourt_status() {
		return cl_ecourt_status;
	}

	public void setCl_ecourt_status(Boolean cl_ecourt_status) {
		this.cl_ecourt_status = cl_ecourt_status;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public boolean isCaseChecked() {
		return caseChecked;
	}

	public void setCaseChecked(boolean caseChecked) {
		this.caseChecked = caseChecked;
	}

	public boolean isAppNew() {
		return appNew;
	}

	public void setAppNew(boolean appNew) {
		this.appNew = appNew;
	}

	public boolean isAppAvailable() {
		return appAvailable;
	}

	public void setAppAvailable(boolean appAvailable) {
		this.appAvailable = appAvailable;
	}

	public boolean isPetAvailable() {
		return petAvailable;
	}

	public void setPetAvailable(boolean petAvailable) {
		this.petAvailable = petAvailable;
	}

	public CauseListType getClType() {
		return clType;
	}

	public void setClType(CauseListType clType) {
		this.clType = clType;
	}

	public CourtMaster getCourtMasterForTransfer() {
		return courtMasterForTransfer;
	}

	public void setCourtMasterForTransfer(CourtMaster courtMasterForTransfer) {
		this.courtMasterForTransfer = courtMasterForTransfer;
	}

	@Column(name = "cl_stage_id")
	private Long cl_stage_id;

	@Column(name = "cl_date")
	private Date cl_date;

	@Transient
	private boolean checked;

	@Transient
	private Integer cl_new_court_no;

	public Integer getCl_transfer_to() {
		return cl_transfer_to;
	}

	public void setCl_transfer_to(Integer cl_transfer_to) {
		this.cl_transfer_to = cl_transfer_to;
	}

	public Date getCl_dol1() {
		return cl_dol1;
	}

	public void setCl_dol1(Date cl_dol1) {
		this.cl_dol1 = cl_dol1;
	}

	public CauseListType getcType() {
		return cType;
	}

	public void setcType(CauseListType cType) {
		this.cType = cType;
	}

	public Long getCl_id() {
		return cl_id;
	}

	public void setCl_id(Long cl_id) {
		this.cl_id = cl_id;
	}

	public Long getCl_case_type_mid() {
		return cl_case_type_mid;
	}

	public void setCl_case_type_mid(Long cl_case_type_mid) {
		this.cl_case_type_mid = cl_case_type_mid;
	}

	public String getCl_case_no() {
		return cl_case_no;
	}

	public void setCl_case_no(String cl_case_no) {
		this.cl_case_no = cl_case_no;
	}

	public Integer getCl_case_year() {
		return cl_case_year;
	}

	public void setCl_case_year(Integer cl_case_year) {
		this.cl_case_year = cl_case_year;
	}

	public Integer getCl_court_no() {
		return cl_court_no;
	}

	public void setCl_court_no(Integer cl_court_no) {
		this.cl_court_no = cl_court_no;
	}

	public Integer getCl_serial_no() {
		return cl_serial_no;
	}

	public void setCl_serial_no(Integer cl_serial_no) {
		this.cl_serial_no = cl_serial_no;
	}

	public Long getCl_list_type_mid() {
		return cl_list_type_mid;
	}

	public void setCl_list_type_mid(Long cl_list_type_mid) {
		this.cl_list_type_mid = cl_list_type_mid;
	}

	public String getCl_first_petitioner() {
		return cl_first_petitioner;
	}

	public void setCl_first_petitioner(String cl_first_petitioner) {
		this.cl_first_petitioner = cl_first_petitioner;
	}

	public String getCl_first_respondent() {
		return cl_first_respondent;
	}

	public void setCl_first_respondent(String cl_first_respondent) {
		this.cl_first_respondent = cl_first_respondent;
	}

	public String getCl_petitioner_council() {
		return cl_petitioner_council;
	}

	public void setCl_petitioner_council(String cl_petitioner_council) {
		this.cl_petitioner_council = cl_petitioner_council;
	}

	public String getCl_respondent_council() {
		return cl_respondent_council;
	}

	public void setCl_respondent_council(String cl_respondent_council) {
		this.cl_respondent_council = cl_respondent_council;
	}

	public Integer getCl_ano() {
		return cl_ano;
	}

	public void setCl_ano(Integer cl_ano) {
		this.cl_ano = cl_ano;
	}

	public Integer getCl_ayr() {
		return cl_ayr;
	}

	public void setCl_ayr(Integer cl_ayr) {
		this.cl_ayr = cl_ayr;
	}

	public String getCl_applawp() {
		return cl_applawp;
	}

	public void setCl_applawp(String cl_applawp) {
		this.cl_applawp = cl_applawp;
	}

	public String getCl_applawr() {
		return cl_applawr;
	}

	public void setCl_applawr(String cl_applawr) {
		this.cl_applawr = cl_applawr;
	}

	public Integer getCl_stage() {
		return cl_stage;
	}

	public void setCl_stage(Integer cl_stage) {
		this.cl_stage = cl_stage;
	}

	public Date getCl_dol() {
		return cl_dol;
	}

	public void setCl_dol(Date cl_dol) {
		this.cl_dol = cl_dol;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getCl_fd_mid() {
		return cl_fd_mid;
	}

	public void setCl_fd_mid(Long cl_fd_mid) {
		this.cl_fd_mid = cl_fd_mid;
	}

	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public Integer getCl_sequence() {
		return cl_sequence;
	}

	public void setCl_sequence(Integer cl_sequence) {
		this.cl_sequence = cl_sequence;
	}

	public Long getCl_stage_id() {
		return cl_stage_id;
	}

	public void setCl_stage_id(Long cl_stage_id) {
		this.cl_stage_id = cl_stage_id;
	}

	public Date getCl_date() {
		return cl_date;
	}

	public void setCl_date(Date cl_date) {
		this.cl_date = cl_date;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setStatus(boolean checked) {
		this.checked = checked;
	}

	public Lookup getApplicationStage() {
		return applicationStage;
	}

	public void setApplicationStage(Lookup applicationStage) {
		this.applicationStage = applicationStage;
	}

	public Integer getCl_rec_status() {
		return cl_rec_status;
	}

	public void setCl_rec_status(Integer cl_rec_status) {
		this.cl_rec_status = cl_rec_status;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getCl_mod_by() {
		return cl_mod_by;
	}

	public void setCl_mod_by(Long cl_mod_by) {
		this.cl_mod_by = cl_mod_by;
	}

	public Date getCl_mod_date() {
		return cl_mod_date;
	}

	public void setCl_mod_date(Date cl_mod_date) {
		this.cl_mod_date = cl_mod_date;
	}

	public CourtMaster getCourtMaster() {
		return courtMaster;
	}

	public void setCourtMaster(CourtMaster courtMaster) {
		this.courtMaster = courtMaster;
	}

	public Integer getCl_new_court_no() {
		return cl_new_court_no;
	}

	public void setCl_new_court_no(Integer cl_new_court_no) {
		this.cl_new_court_no = cl_new_court_no;
	}

	public Date getListing_date() {
		return listing_date;
	}

	public void setListing_date(Date listing_date) {
		this.listing_date = listing_date;
	}

	@Override
	public String toString() {
		return "CauseList [cl_id=" + cl_id + ", cl_case_type_mid=" + cl_case_type_mid + ", cl_case_no=" + cl_case_no
				+ ", cl_case_year=" + cl_case_year + ", cl_court_no=" + cl_court_no + ", cl_transfer_to="
				+ cl_transfer_to + ", cl_serial_no=" + cl_serial_no + ", cl_list_type_mid=" + cl_list_type_mid
				+ ", cl_first_petitioner=" + cl_first_petitioner + ", cl_first_respondent=" + cl_first_respondent
				+ ", cl_petitioner_council=" + cl_petitioner_council + ", cl_respondent_council="
				+ cl_respondent_council + ", cl_ano=" + cl_ano + ", cl_ayr=" + cl_ayr + ", cl_applawp=" + cl_applawp
				+ ", cl_applawr=" + cl_applawr + ", cl_stage=" + cl_stage + ", cl_dol=" + cl_dol + ", cl_dol1="
				+ cl_dol1 + ", cl_fd_mid=" + cl_fd_mid + ", cl_sequence=" + cl_sequence + ", count=" + count
				+ ", cType=" + cType + ", listing_date=" + listing_date + ", cl_rec_status=" + cl_rec_status
				+ ", cl_mod_by=" + cl_mod_by + ", cl_mod_date=" + cl_mod_date + ", caseType=" + caseType
				+ ", applicationStage=" + applicationStage + ", courtMaster=" + courtMaster
				+ ", courtMasterForTransfer=" + courtMasterForTransfer + ", cl_stage_id=" + cl_stage_id + ", cl_date="
				+ cl_date + ", checked=" + checked + ", cl_new_court_no=" + cl_new_court_no + "]";
	}

}
