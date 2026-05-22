package com.dms.model;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "digitization_request", schema = "public")
public class DigitizationRequest {

   @Id
   @Column(name = "dr_id")
   private Long drId;

   @Column(name = "dr_case_no")
   private String drCaseNo;

   @Column(name = "dr_case_year")
   private Integer drCaseYear;

   @Column(name = "dr_case_type")
   private String drCaseType;

   @Column(name = "dr_barcode")
   private String barcode;

   @Column(name = "dr_file_server")
   private String fileServer;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "dr_cr_by", referencedColumnName = "um_id")
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   private User createdBy;
   

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "dr_req_status")
   private String reqStatus;

   @Column(name = "dr_move_date")
   private Date moveDate;

	public Long getDrId() {
		return drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
	}

	public String getDrCaseNo() {
		return drCaseNo;
	}

	public void setDrCaseNo(String drCaseNo) {
		this.drCaseNo = drCaseNo;
	}

	public Integer getDrCaseYear() {
		return drCaseYear;
	}

	public void setDrCaseYear(Integer drCaseYear) {
		this.drCaseYear = drCaseYear;
	}

	public String getDrCaseType() {
		return drCaseType;
	}

	public void setDrCaseType(String drCaseType) {
		this.drCaseType = drCaseType;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getFileServer() {
		return fileServer;
	}

	public void setFileServer(String fileServer) {
		this.fileServer = fileServer;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public Date getMoveDate() {
		return moveDate;
	}

	public void setMoveDate(Date moveDate) {
		this.moveDate = moveDate;
	}
   
   

   
}
