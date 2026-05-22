package com.efiling.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="application_stage")
public class ApplicationStage 
{
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="application_stage_seq")
	@SequenceGenerator(name="application_stage_seq", sequenceName="application_stage_seq", allocationSize=1)
	@Column(name="as_id")
	private Long as_id;
	
	@Column(name="as_ap_mid")
	private Long as_ap_mid;
	 
	@Column(name="as_stage_lid")
	private Long as_stage_lid;
	
	@Column(name="as_cr_by")
	private Long as_cr_by;
	
	@Column(name="as_cr_date")
	private Date as_cr_date;

	public Long getAs_id() {
		return as_id;
	}

	public void setAs_id(Long as_id) {
		this.as_id = as_id;
	}

	public Long getAs_ap_mid() {
		return as_ap_mid;
	}

	public void setAs_ap_mid(Long as_ap_mid) {
		this.as_ap_mid = as_ap_mid;
	}

	public Long getAs_stage_lid() {
		return as_stage_lid;
	}

	public void setAs_stage_lid(Long as_stage_lid) {
		this.as_stage_lid = as_stage_lid;
	}

	public Long getAs_cr_by() {
		return as_cr_by;
	}

	public void setAs_cr_by(Long as_cr_by) {
		this.as_cr_by = as_cr_by;
	}

	public Date getAs_cr_date() {
		return as_cr_date;
	}

	public void setAs_cr_date(Date as_cr_date) {
		this.as_cr_date = as_cr_date;
	}
	
	
	
	@Override
	public String toString() {
	    return "ApplicationStage{" +
	            "as_id=" + as_id +
	            ", as_ap_mid=" + as_ap_mid +
	            ", as_stage_lid=" + as_stage_lid +
	            ", as_cr_by=" + as_cr_by +
	            ", as_cr_date=" + as_cr_date +
	            '}';
	}


	

	
}
