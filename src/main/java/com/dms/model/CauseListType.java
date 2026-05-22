package com.dms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="cause_list_type")
public class CauseListType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="cause_list_type_seq")
	@SequenceGenerator(name="cause_list_type_seq", sequenceName="cause_list_type_seq",allocationSize=1)
	@Column(name="clt_id")
	private Long clt_id;
	
	@Column(name="clt_name")
	private String clt_name;
	
	@Column(name="clt_rec_status")
	private Integer clt_rec_status;
	
	@Column(name="clt_cr_by")
	private Long clt_cr_by;
	
	@Column(name="clt_cr_date")
	private Date clt_cr_date;
	
	@Column(name="clt_mod_by")
	private Long clt_mod_by;
	
	@Column(name="clt_mod_date")
	private Date clt_mod_date;
	
	@Column(name="clt_description")
	private String clt_description;
	
	@Column(name = "clt_ccms_list")
	private Integer clt_ccms_list;
	
	@Column(name = "clt_list_priority")
	private Integer clt_list_priority;
	
	

	public Integer getClt_ccms_list() {
		return clt_ccms_list;
	}

	public void setClt_ccms_list(Integer clt_ccms_list) {
		this.clt_ccms_list = clt_ccms_list;
	}
	
	

	public Integer getClt_list_priority() {
		return clt_list_priority;
	}

	public void setClt_list_priority(Integer clt_list_priority) {
		this.clt_list_priority = clt_list_priority;
	}

	public Long getClt_id() {
		return clt_id;
	}

	public void setClt_id(Long clt_id) {
		this.clt_id = clt_id;
	}

	public String getClt_name() {
		return clt_name;
	}

	public void setClt_name(String clt_name) {
		this.clt_name = clt_name;
	}

	public Integer getClt_rec_status() {
		return clt_rec_status;
	}

	public void setClt_rec_status(Integer clt_rec_status) {
		this.clt_rec_status = clt_rec_status;
	}

	public Long getClt_cr_by() {
		return clt_cr_by;
	}

	public void setClt_cr_by(Long clt_cr_by) {
		this.clt_cr_by = clt_cr_by;
	}

	public Date getClt_cr_date() {
		return clt_cr_date;
	}

	public void setClt_cr_date(Date clt_cr_date) {
		this.clt_cr_date = clt_cr_date;
	}

	public Long getClt_mod_by() {
		return clt_mod_by;
	}

	public void setClt_mod_by(Long clt_mod_by) {
		this.clt_mod_by = clt_mod_by;
	}

	public Date getClt_mod_date() {
		return clt_mod_date;
	}

	public void setClt_mod_date(Date clt_mod_date) {
		this.clt_mod_date = clt_mod_date;
	}

	public String getClt_description() {
		return clt_description;
	}

	public void setClt_description(String clt_description) {
		this.clt_description = clt_description;
	}

	@Override
	public String toString() {
		return "CauseListType [clt_id=" + clt_id + ", clt_name=" + clt_name + ", clt_rec_status=" + clt_rec_status
				+ ", clt_cr_by=" + clt_cr_by + ", clt_cr_date=" + clt_cr_date + ", clt_mod_by=" + clt_mod_by
				+ ", clt_mod_date=" + clt_mod_date + ", clt_description=" + clt_description + "]";
	}
	
	

}
