package com.minervasoft.backend.vo;

public class StatisticsVO extends AbstractVO{
	private String  prcDt;
	private String textPrcDt;
	private Integer prcDtCnt;
	private Integer prcCn;
	private Integer errCn;
	private String  errRat;
	private String  prcRat;
	private Integer verifyUpdateCn;
	private Integer maintainCn;
	private Integer aY;
	private Integer aN;
	private Integer bY;
	private Integer bN;
	private Integer cY;
	private Integer cN;
	private Integer dY;
	private Integer dN;
	private Integer eY;
	private Integer eN;
	private Integer tmRecvAgrY;
	private Integer tmRecvAgrN;
	private Integer smsRecvAgrY;
	private Integer smsRecvAgrN;
	private Integer dmRecvAgrY;
	private Integer dmRecvAgrN;
	private Integer emailRecvAgrY;
	private Integer emailRecvAgrN;
	private Integer tmOfferAgrY;
	private Integer tmOfferAgrN;
	private Integer dmOfferAgrY;
	private Integer dmOfferAgrN;
	private Integer emailOfferAgrY;
	private Integer emailOfferAgrN;
	private String  gridLabels = "";
	private String  gridNames = "";
	private String  gridWidths = "";
	private String  gridAligns = "";
	private String  excelDownYn = "";
	private Integer cgnRzt = 0;
	private Integer pageNumber = 0;
	private Integer totPageCnt = 0;
	private Integer totRowCnt  = 0;
	private Integer pageSize   = 0;
	
	
	public String getPrcDt() {
		return prcDt;
	}
	public void setPrcDt(String prcDt) {
		this.prcDt = prcDt;
	}
	public Integer getPrcDtCnt() {
		return prcDtCnt;
	}
	public void setPrcDtCnt(Integer prcDtCnt) {
		this.prcDtCnt = prcDtCnt;
	}
	public Integer getPrcCn() {
		return prcCn;
	}
	public void setPrcCn(Integer prcCn) {
		this.prcCn = prcCn;
	}
	public Integer getErrCn() {
		return errCn;
	}
	public void setErrCn(Integer errCn) {
		this.errCn = errCn;
	}
	public Integer getVerifyUpdateCn() {
		return verifyUpdateCn;
	}
	public void setVerifyUpdateCn(Integer verifyUpdateCn) {
		this.verifyUpdateCn = verifyUpdateCn;
	}
	public Integer getMaintainCn() {
		return maintainCn;
	}
	public void setMaintainCn(Integer maintainCn) {
		this.maintainCn = maintainCn;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getTotPageCnt() {
		return totPageCnt;
	}
	public void setTotPageCnt(Integer totPageCnt) {
		this.totPageCnt = totPageCnt;
	}
	public Integer getTotRowCnt() {
		return totRowCnt;
	}
	public void setTotRowCnt(Integer totRowCnt) {
		this.totRowCnt = totRowCnt;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getErrRat() {
		return errRat;
	}
	public void setErrRat(String errRat) {
		this.errRat = errRat;
	}
	public String getPrcRat() {
		return prcRat;
	}
	public void setPrcRat(String prcRat) {
		this.prcRat = prcRat;
	}
	public String getTextPrcDt() {
		return textPrcDt;
	}
	public void setTextPrcDt(String textPrcDt) {
		this.textPrcDt = textPrcDt;
	}
	public String getGridLabels() {
		return gridLabels;
	}
	public void setGridLabels(String gridLabels) {
		this.gridLabels = gridLabels;
	}
	public String getGridNames() {
		return gridNames;
	}
	public void setGridNames(String gridNames) {
		this.gridNames = gridNames;
	}
	public String getGridWidths() {
		return gridWidths;
	}
	public void setGridWidths(String gridWidths) {
		this.gridWidths = gridWidths;
	}
	public String getGridAligns() {
		return gridAligns;
	}
	public void setGridAligns(String gridAligns) {
		this.gridAligns = gridAligns;
	}
	public String getExcelDownYn() {
		return excelDownYn;
	}
	public void setExcelDownYn(String excelDownYn) {
		this.excelDownYn = excelDownYn;
	}
	public Integer getCgnRzt() {
		return cgnRzt;
	}
	public void setCgnRzt(Integer cgnRzt) {
		this.cgnRzt = cgnRzt;
	}
	public Integer getaY() {
		return aY;
	}
	public void setaY(Integer aY) {
		this.aY = aY;
	}
	public Integer getaN() {
		return aN;
	}
	public void setaN(Integer aN) {
		this.aN = aN;
	}
	public Integer getbY() {
		return bY;
	}
	public void setbY(Integer bY) {
		this.bY = bY;
	}
	public Integer getbN() {
		return bN;
	}
	public void setbN(Integer bN) {
		this.bN = bN;
	}
	public Integer getcY() {
		return cY;
	}
	public void setcY(Integer cY) {
		this.cY = cY;
	}
	public Integer getcN() {
		return cN;
	}
	public void setcN(Integer cN) {
		this.cN = cN;
	}
	public Integer getdY() {
		return dY;
	}
	public void setdY(Integer dY) {
		this.dY = dY;
	}
	public Integer getdN() {
		return dN;
	}
	public void setdN(Integer dN) {
		this.dN = dN;
	}
	public Integer geteY() {
		return eY;
	}
	public void seteY(Integer eY) {
		this.eY = eY;
	}
	public Integer geteN() {
		return eN;
	}
	public void seteN(Integer eN) {
		this.eN = eN;
	}
	public Integer getTmRecvAgrY() {
		return tmRecvAgrY;
	}
	public void setTmRecvAgrY(Integer tmRecvAgrY) {
		this.tmRecvAgrY = tmRecvAgrY;
	}
	public Integer getTmRecvAgrN() {
		return tmRecvAgrN;
	}
	public void setTmRecvAgrN(Integer tmRecvAgrN) {
		this.tmRecvAgrN = tmRecvAgrN;
	}
	public Integer getSmsRecvAgrY() {
		return smsRecvAgrY;
	}
	public void setSmsRecvAgrY(Integer smsRecvAgrY) {
		this.smsRecvAgrY = smsRecvAgrY;
	}
	public Integer getSmsRecvAgrN() {
		return smsRecvAgrN;
	}
	public void setSmsRecvAgrN(Integer smsRecvAgrN) {
		this.smsRecvAgrN = smsRecvAgrN;
	}
	public Integer getDmRecvAgrY() {
		return dmRecvAgrY;
	}
	public void setDmRecvAgrY(Integer dmRecvAgrY) {
		this.dmRecvAgrY = dmRecvAgrY;
	}
	public Integer getDmRecvAgrN() {
		return dmRecvAgrN;
	}
	public void setDmRecvAgrN(Integer dmRecvAgrN) {
		this.dmRecvAgrN = dmRecvAgrN;
	}
	public Integer getEmailRecvAgrY() {
		return emailRecvAgrY;
	}
	public void setEmailRecvAgrY(Integer emailRecvAgrY) {
		this.emailRecvAgrY = emailRecvAgrY;
	}
	public Integer getEmailRecvAgrN() {
		return emailRecvAgrN;
	}
	public void setEmailRecvAgrN(Integer emailRecvAgrN) {
		this.emailRecvAgrN = emailRecvAgrN;
	}
	public Integer getTmOfferAgrY() {
		return tmOfferAgrY;
	}
	public void setTmOfferAgrY(Integer tmOfferAgrY) {
		this.tmOfferAgrY = tmOfferAgrY;
	}
	public Integer getTmOfferAgrN() {
		return tmOfferAgrN;
	}
	public void setTmOfferAgrN(Integer tmOfferAgrN) {
		this.tmOfferAgrN = tmOfferAgrN;
	}
	public Integer getDmOfferAgrY() {
		return dmOfferAgrY;
	}
	public void setDmOfferAgrY(Integer dmOfferAgrY) {
		this.dmOfferAgrY = dmOfferAgrY;
	}
	public Integer getDmOfferAgrN() {
		return dmOfferAgrN;
	}
	public void setDmOfferAgrN(Integer dmOfferAgrN) {
		this.dmOfferAgrN = dmOfferAgrN;
	}
	public Integer getEmailOfferAgrY() {
		return emailOfferAgrY;
	}
	public void setEmailOfferAgrY(Integer emailOfferAgrY) {
		this.emailOfferAgrY = emailOfferAgrY;
	}
	public Integer getEmailOfferAgrN() {
		return emailOfferAgrN;
	}
	public void setEmailOfferAgrN(Integer emailOfferAgrN) {
		this.emailOfferAgrN = emailOfferAgrN;
	}
	
	
	
	
	
}
