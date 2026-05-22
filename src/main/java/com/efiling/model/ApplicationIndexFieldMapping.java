package com.efiling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="application_indexfield_mapping")
public class ApplicationIndexFieldMapping {
	
	@Id
	@Column(name="aim_id")
	private Long aim_id;

	@Column(name="aim_at_mid")
	private Long aim_at_mid;
	
	@Column(name="aim_if_mid")
	private Long aim_if_mid;

	public Long getAim_id() {
		return aim_id;
	}

	public void setAim_id(Long aim_id) {
		this.aim_id = aim_id;
	}

	public Long getAim_at_mid() {
		return aim_at_mid;
	}

	public void setAim_at_mid(Long aim_at_mid) {
		this.aim_at_mid = aim_at_mid;
	}

	public Long getAim_if_mid() {
		return aim_if_mid;
	}

	public void setAim_if_mid(Long aim_if_mid) {
		this.aim_if_mid = aim_if_mid;
	}
	
	

}
