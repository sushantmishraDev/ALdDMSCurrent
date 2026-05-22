package com.dms.model;

import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class ExtraCauseListInfo {

	@JsonProperty("NoticeNo")
	String NoticeNo;
	@JsonProperty("DistrictName")
	String DistrictName;
	@JsonProperty("InJailSinceDate")
	String InJailSinceDate;
	@JsonProperty("CrimeNo")
	String CrimeNo;
	@JsonProperty("CrimeYear")
	String CrimeYear;
	@JsonProperty("CrimeDistName")
	String CrimeDistName;
	@JsonProperty("CauseListTypeId")
	String CauseListTypeId;
	@JsonProperty("CrimePoliceStnName")
	String CrimePoliceStnName;
	@JsonProperty("IsCrime")
	String IsCrime;
	@JsonProperty("ShortOrderName")
	String ShortOrderName;
	@JsonProperty("PS_Code")
	String PS_Code;

	@JsonProperty("LastListingDate")
	String LastListingDate;
	
	@JsonProperty("ApplicationAppliedBy")
	String ApplicationAppliedBy;
	
	@JsonProperty("NextDateInSystem")
	String NextDateInSystem;
	
	@JsonProperty("LowerCourtLCRNo")
	String LowerCourtLCRNo;
	
	@JsonProperty("LowerCourtCaseBelongsValue")
	String LowerCourtCaseBelongsValue;
	
	@JsonProperty("LowerCourtLCRYear")
	String LowerCourtLCRYear;
	
	@JsonProperty("CaseDetailsOfSameCrimeNoList")
	List<SameCrimeCcms> CaseDetailsOfSameCrimeNoList;
	
	@JsonProperty("CaseDetailsOfSameLCRNoList")
	List<SameLCRccms> CaseDetailsOfSameLCRNoList;
	
	
	
	@JsonProperty("LcaseTypeName")
	String LcaseTypeName;
	
	
	
	
	public List<SameLCRccms> getCaseDetailsOfSameLCRNoList() {
		return CaseDetailsOfSameLCRNoList;
	}

	public void setCaseDetailsOfSameLCRNoList(List<SameLCRccms> caseDetailsOfSameLCRNoList) {
		CaseDetailsOfSameLCRNoList = caseDetailsOfSameLCRNoList;
	}

	public String getCauseListTypeId() {
		return CauseListTypeId;
	}

	public void setCauseListTypeId(String causeListTypeId) {
		CauseListTypeId = causeListTypeId;
	}

	public String getLcaseTypeName() {
		return LcaseTypeName;
	}

	public void setLcaseTypeName(String lcaseTypeName) {
		LcaseTypeName = lcaseTypeName;
	}

	public String getLowerCourtCaseBelongsValue() {
		return LowerCourtCaseBelongsValue;
	}

	public void setLowerCourtCaseBelongsValue(String lowerCourtCaseBelongsValue) {
		LowerCourtCaseBelongsValue = lowerCourtCaseBelongsValue;
	}

	public String getPS_Code() {
		return PS_Code;
	}

	public void setPS_Code(String pS_Code) {
		PS_Code = pS_Code;
	}

	

	public String getLowerCourtLCRNo() {
		return LowerCourtLCRNo;
	}

	public void setLowerCourtLCRNo(String lowerCourtLCRNo) {
		LowerCourtLCRNo = lowerCourtLCRNo;
	}

	public String getLowerCourtLCRYear() {
		return LowerCourtLCRYear;
	}

	public void setLowerCourtLCRYear(String lowerCourtLCRYear) {
		LowerCourtLCRYear = lowerCourtLCRYear;
	}

	public List<SameCrimeCcms> getCaseDetailsOfSameCrimeNoList() {
		return CaseDetailsOfSameCrimeNoList;
	}

	public void setCaseDetailsOfSameCrimeNoList(List<SameCrimeCcms> caseDetailsOfSameCrimeNoList) {
		CaseDetailsOfSameCrimeNoList = caseDetailsOfSameCrimeNoList;
	}

	public String getLastListingDate() {
		return LastListingDate;
	}

	public void setLastListingDate(String lastListingDate) {
		LastListingDate = lastListingDate;
	}

	public String getApplicationAppliedBy() {
		return ApplicationAppliedBy;
	}

	public void setApplicationAppliedBy(String applicationAppliedBy) {
		ApplicationAppliedBy = applicationAppliedBy;
	}

	public String getNextDateInSystem() {
		return NextDateInSystem;
	}

	public void setNextDateInSystem(String nextDateInSystem) {
		NextDateInSystem = nextDateInSystem;
	}

	public String getShortOrderName() {
		return ShortOrderName;
	}

	public void setShortOrderName(String shortOrderName) {
		ShortOrderName = shortOrderName;
	}

	public String getNoticeNo() {
		return NoticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		NoticeNo = noticeNo;
	}

	public String getDistrictName() {
		return DistrictName;
	}

	public void setDistrictName(String districtName) {
		DistrictName = districtName;
	}

	public String getInJailSinceDate() {
		return InJailSinceDate;
	}

	public void setInJailSinceDate(String inJailSinceDate) {
		InJailSinceDate = inJailSinceDate;
	}

	public String getCrimeNo() {
		return CrimeNo;
	}

	public void setCrimeNo(String crimeNo) {
		CrimeNo = crimeNo;
	}

	public String getCrimeYear() {
		return CrimeYear;
	}

	public void setCrimeYear(String crimeYear) {
		CrimeYear = crimeYear;
	}

	public String getCrimeDistName() {
		return CrimeDistName;
	}

	public void setCrimeDistName(String crimeDistName) {
		CrimeDistName = crimeDistName;
	}

	public String getCrimePoliceStnName() {
		return CrimePoliceStnName;
	}

	public void setCrimePoliceStnName(String crimePoliceStnName) {
		CrimePoliceStnName = crimePoliceStnName;
	}

	public String getIsCrime() {
		return IsCrime;
	}

	public void setIsCrime(String isCrime) {
		IsCrime = isCrime;
	}

}
