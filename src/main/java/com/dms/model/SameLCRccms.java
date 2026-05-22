package com.dms.model;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class SameLCRccms {
	
	@JsonProperty("DisplayCaseno")
	String DisplayCaseno;

	@JsonProperty("PartyDetail")
	String PartyDetail;
	
	@JsonProperty("CaseStatus")
	String CaseStatus;
	
	@JsonProperty("DisposedDate")
	String DisposedDate;

	public String getDisplayCaseno() {
		return DisplayCaseno;
	}

	public void setDisplayCaseno(String displayCaseno) {
		DisplayCaseno = displayCaseno;
	}

	public String getPartyDetail() {
		return PartyDetail;
	}

	public void setPartyDetail(String partyDetail) {
		PartyDetail = partyDetail;
	}

	public String getCaseStatus() {
		return CaseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		CaseStatus = caseStatus;
	}

	public String getDisposedDate() {
		return DisposedDate;
	}

	public void setDisposedDate(String disposedDate) {
		DisposedDate = disposedDate;
	}
	
	
	
}
