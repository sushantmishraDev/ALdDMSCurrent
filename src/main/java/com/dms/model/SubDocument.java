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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import com.efiling.model.EfilingApplication;

@Entity
@Table(name="sub_documents")
public class SubDocument {
	
	@Id 
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="sub_documents_seq")
	@SequenceGenerator(name="sub_documents_seq", sequenceName="sub_documents_seq", allocationSize=1)
	@Column(name = "sd_id") 
	private Long sd_id;
	
	@Column(name = "sd_fd_mid")
	private Long sd_fd_mid;
	
	@Column(name = "sd_if_mid")
	private Long sd_if_mid;
	
	@Column (name="sd_document_name")
	private String sd_document_name;
	
	@Column (name="sd_application_status")
	private String sd_application_status;
	
	@Column (name = "sd_no_of_pages")
	private int sd_no_of_pages;
	
	@Column (name = "sd_version")
	private int sd_version;
	
	@Column (name= "sd_document_id")
	private Integer sd_document_id;
	
	@Column (name= "sd_document_no")
	private Integer sd_document_no;
	
	@Column (name= "sd_document_year")
	private Integer sd_document_year;
	
	@Column (name="sd_party")
	private String sd_party;
	
	@Column (name="sd_olr_no")
	private Integer sd_olr_no;
	
	
	@Column (name="sd_description")
	private String sd_description;
	
	@Column (name = "sd_submitted_date")
	private Date sd_submitted_date;
	
	@Column (name = "sd_cr_by")
	private Long sd_cr_by;
	
	@Column (name="sd_olr_year")
	private Integer sd_olr_year;
	
	

	@Column (name = "sd_cr_date")
	private Date sd_cr_date;
	
	@Column (name= "sd_rec_status")
	private int sd_rec_status;
	
	@Column (name= "sd_major_sequence")
	private int sd_major_sequence;
	
	@Column (name= "sd_minor_sequence")
	private int sd_minor_sequence;
	
	@Column (name= "sd_judgement_id")
	private Long sd_judgement_id;
	
	@Column (name="sd_counsel")
	private String sd_counsel;
	
	@Column (name="sd_status")
	private Long sd_status;
	
	@Column (name="sd_date")
	private Date sd_date;
	
	@Column (name = "sd_mod_by")
	private Long sd_mod_by;
	
	private transient Long judgmentID;
	
	@Column (name = "sd_mod_date")
	private Date sd_mod_date;
	
	@Column(name="sd_nonmaintainable")
	private boolean sd_nonmaintainable;
	
	@Column(name = "checked")
	private boolean checked;
	
	
	
	private transient boolean checkBoxValue;
	
	
	
	
	public Integer getSd_olr_year() {
		return sd_olr_year;
	}

	public void setSd_olr_year(Integer sd_olr_year) {
		this.sd_olr_year = sd_olr_year;
	}
	
	
	public boolean isCheckBoxValue() {
		return checkBoxValue;
	}
	
	
	public Integer getSd_olr_no() {
		return sd_olr_no;
	}

	public void setSd_olr_no(Integer sd_olr_no) {
		this.sd_olr_no = sd_olr_no;
	}


	public void setCheckBoxValue(boolean checkBoxValue) {
		this.checkBoxValue = checkBoxValue;
	}

	public String getSd_application_status() {
		return sd_application_status;
	}

	public void setSd_application_status(String sd_application_status) {
		this.sd_application_status = sd_application_status;
	}

	public Long getJudgmentID() {
		return judgmentID;
	}

	public void setJudgmentID(Long judgmentID) {
		this.judgmentID = judgmentID;
	}

	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sd_if_mid",insertable = false, updatable = false)
	private IndexField indexField;
	
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sd_document_id",insertable = false, updatable = false)
	private ApplicationTypes documentType;
	
	@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sd_status",insertable = false, updatable = false)
	private Lookup applicationStatus;
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name = "awp_sd_mid",referencedColumnName="sd_id",insertable = false, updatable = false)	
	@Where(clause="awp_rec_status=1")
	private List<ApplicationWithPetition> applicationWithPetition;
	
	

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name = "sb_ap_sd_mid",referencedColumnName="sd_id",insertable = false, updatable = false)	
	@Where(clause="sb_ap_rec_status=1")
	private List<SubApplication> subApplications;

	
	/*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sd_cr_by" , insertable = false, updatable = false)
	private User user;*/
	

	public List<SubApplication> getSubApplications() {
		return subApplications;
	}

	public void setSubApplications(List<SubApplication> subApplications) {
		this.subApplications = subApplications;
	}

	public List<ApplicationWithPetition> getApplicationWithPetition() {
		return applicationWithPetition;
	}

	public void setApplicationWithPetition(List<ApplicationWithPetition> applicationWithPetition) {
		this.applicationWithPetition = applicationWithPetition;
	}

	public Long getSd_id() {
		return sd_id;
	}

	public void setSd_id(Long sd_id) {
		this.sd_id = sd_id;
	}

	public Long getSd_fd_mid() {
		return sd_fd_mid;
	}

	public void setSd_fd_mid(Long sd_fd_mid) {
		this.sd_fd_mid = sd_fd_mid;
	}

	public Long getSd_if_mid() {
		return sd_if_mid;
	}

	public void setSd_if_mid(Long sd_if_mid) {
		this.sd_if_mid = sd_if_mid;
	}

	public String getSd_document_name() {
		return sd_document_name;
	}

	public void setSd_document_name(String sd_document_name) {
		this.sd_document_name = sd_document_name;
	}

	public int getSd_no_of_pages() {
		return sd_no_of_pages;
	}

	public void setSd_no_of_pages(int sd_no_of_pages) {
		this.sd_no_of_pages = sd_no_of_pages;
	}

	public int getSd_version() {
		return sd_version;
	}

	public void setSd_version(int sd_version) {
		this.sd_version = sd_version;
	}

	public Integer getSd_document_id() {
		return sd_document_id;
	}

	public void setSd_document_id(Integer sd_document_id) {
		this.sd_document_id = sd_document_id;
	}

	public Integer getSd_document_no() {
		return sd_document_no;
	}

	public void setSd_document_no(Integer sd_document_no) {
		this.sd_document_no = sd_document_no;
	}

	public Integer getSd_document_year() {
		return sd_document_year;
	}

	public void setSd_document_year(Integer sd_document_year) {
		this.sd_document_year = sd_document_year;
	}

	public String getSd_party() {
		return sd_party;
	}

	public void setSd_party(String sd_party) {
		this.sd_party = sd_party;
	}

	public String getSd_description() {
		return sd_description;
	}

	public void setSd_description(String sd_description) {
		this.sd_description = sd_description;
	}

	public Date getSd_submitted_date() {
		return sd_submitted_date;
	}

	public void setSd_submitted_date(Date sd_submitted_date) {
		this.sd_submitted_date = sd_submitted_date;
	}

	public Long getSd_cr_by() {
		return sd_cr_by;
	}

	public void setSd_cr_by(Long sd_cr_by) {
		this.sd_cr_by = sd_cr_by;
	}

	public Date getSd_cr_date() {
		return sd_cr_date;
	}

	public void setSd_cr_date(Date sd_cr_date) {
		this.sd_cr_date = sd_cr_date;
	}

	public int getSd_rec_status() {
		return sd_rec_status;
	}

	public void setSd_rec_status(int sd_rec_status) {
		this.sd_rec_status = sd_rec_status;
	}

	public int getSd_major_sequence() {
		return sd_major_sequence;
	}

	public void setSd_major_sequence(int sd_major_sequence) {
		this.sd_major_sequence = sd_major_sequence;
	}

	public int getSd_minor_sequence() {
		return sd_minor_sequence;
	}

	public void setSd_minor_sequence(int sd_minor_sequence) {
		this.sd_minor_sequence = sd_minor_sequence;
	}
	
	

	public String getSd_counsel() {
		return sd_counsel;
	}

	public void setSd_counsel(String sd_counsel) {
		this.sd_counsel = sd_counsel;
	}

	public IndexField getIndexField() {
		return indexField;
	}

	public void setIndexField(IndexField indexField) {
		this.indexField = indexField;
	}

	public Long getSd_judgement_id() {
		return sd_judgement_id;
	}

	public void setSd_judgement_id(Long sd_judgement_id) {
		this.sd_judgement_id = sd_judgement_id;
	}

	public ApplicationTypes getDocumentType() {
		return documentType;
	}

	public void setDocumentType(ApplicationTypes documentType) {
		this.documentType = documentType;
	}

	public Long getSd_status() {
		return sd_status;
	}

	public void setSd_status(Long sd_status) {
		this.sd_status = sd_status;
	}

	public Lookup getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(Lookup applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Date getSd_date() {
		return sd_date;
	}

	public void setSd_date(Date sd_date) {
		this.sd_date = sd_date;
	}

	public Long getSd_mod_by() {
		return sd_mod_by;
	}

	public void setSd_mod_by(Long sd_mod_by) {
		this.sd_mod_by = sd_mod_by;
	}

	public Date getSd_mod_date() {
		return sd_mod_date;
	}

	public void setSd_mod_date(Date sd_mod_date) {
		this.sd_mod_date = sd_mod_date;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isSd_nonmaintainable() {
		return sd_nonmaintainable;
	}

	public void setSd_nonmaintainable(boolean sd_nonmaintainable) {
		this.sd_nonmaintainable = sd_nonmaintainable;
	}


	
}
