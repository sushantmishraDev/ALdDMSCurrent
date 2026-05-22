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
@Table(name = "sub_applications")
public class EfilingSubApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_applications_seq")
	@SequenceGenerator(name = "sub_applications_seq", sequenceName = "sub_applications_seq", allocationSize = 1)
	@Column(name = "sb_ap_id")
	private Long sb_ap_id;

	@Column(name = "sb_ap_mid")
	private Long sb_ap_mid;

	@Column(name = "sb_ap_no")
	private Integer sb_ap_no;

	@Column(name = "sb_ap_year")
	private Integer sb_ap_year;

	@Column(name = "sb_ap_at_mid")
	private Long sb_ap_at_mid;

	@Column(name = "sb_ap_rec_status")
	private Integer sb_ap_rec_status;

	@Column(name = "sb_ap_cr_date")
	private Date sb_ap_cr_date;

	@Column(name = "sb_ap_mod_by")
	private Long sb_ap_mod_by;

	@Column(name = "sb_ap_mod_date")
	private Date sb_ap_mod_date;

	public Long getSb_ap_id() {
		return sb_ap_id;
	}

	public void setSb_ap_id(Long sb_ap_id) {
		this.sb_ap_id = sb_ap_id;
	}

	public Long getSb_ap_mid() {
		return sb_ap_mid;
	}

	public void setSb_ap_mid(Long sb_ap_mid) {
		this.sb_ap_mid = sb_ap_mid;
	}

	public Integer getSb_ap_no() {
		return sb_ap_no;
	}

	public void setSb_ap_no(Integer sb_ap_no) {
		this.sb_ap_no = sb_ap_no;
	}

	public Integer getSb_ap_year() {
		return sb_ap_year;
	}

	public void setSb_ap_year(Integer sb_ap_year) {
		this.sb_ap_year = sb_ap_year;
	}

	public Integer getSb_ap_rec_status() {
		return sb_ap_rec_status;
	}

	public void setSb_ap_rec_status(Integer sb_ap_rec_status) {
		this.sb_ap_rec_status = sb_ap_rec_status;
	}

	public Date getSb_ap_cr_date() {
		return sb_ap_cr_date;
	}

	public void setSb_ap_cr_date(Date sb_ap_cr_date) {
		this.sb_ap_cr_date = sb_ap_cr_date;
	}

	public Long getSb_ap_at_mid() {
		return sb_ap_at_mid;
	}

	public void setSb_ap_at_mid(Long sb_ap_at_mid) {
		this.sb_ap_at_mid = sb_ap_at_mid;
	}

	public Long getSb_ap_mod_by() {
		return sb_ap_mod_by;
	}

	public void setSb_ap_mod_by(Long sb_ap_mod_by) {
		this.sb_ap_mod_by = sb_ap_mod_by;
	}

	public Date getSb_ap_mod_date() {
		return sb_ap_mod_date;
	}

	public void setSb_ap_mod_date(Date sb_ap_mod_date) {
		this.sb_ap_mod_date = sb_ap_mod_date;
	}

	@Override
	public String toString() {
		return "SubApplication [sb_ap_id=" + sb_ap_id + ", sb_ap_mid=" + sb_ap_mid + ", sb_ap_no=" + sb_ap_no
				+ ", sb_ap_year=" + sb_ap_year + ", sb_ap_at_mid=" + sb_ap_at_mid + ", sb_ap_rec_status="
				+ sb_ap_rec_status + ", sb_ap_cr_date=" + sb_ap_cr_date + ", sb_ap_mod_by=" + sb_ap_mod_by
				+ ", sb_ap_mod_date=" + sb_ap_mod_date + "]";
	}


}
