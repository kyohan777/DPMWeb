package com.minervasoft.backend.vo;

public class CalibVerifiVo extends AbstractVO{
	private String chrrId; 			//열람자ID
	private String chrrNm; 			//성명
	private Integer idNo;			//연번
	private String prcDt;			//조회일자
	private String elementId;		
	private String queryReason;		//조회사유
	private String custId;			//고객 번호
	private String startPrcDt;		//조회조건 > 시작
	private String endPrcDt;		//조회조건 > 종료
	private String prcTm;			//조회 시간
	private String deprNm;			//부서명
	private Integer pageNumber = 0;
	private Integer totPageCnt = 0;
	private Integer totRowCnt  = 0;
	private Integer pageSize   = 0;
	
	public String getChrrId() {
		return chrrId;
	}
	public void setChrrId(String chrrId) {
		this.chrrId = chrrId;
	}
	public String getChrrNm() {
		return chrrNm;
	}
	public void setChrrNm(String chrrNm) {
		this.chrrNm = chrrNm;
	}
	public Integer getIdNo() {
		return idNo;
	}
	public void setIdNo(Integer idNo) {
		this.idNo = idNo;
	}
	public String getPrcDt() {
		return prcDt;
	}
	public void setPrcDt(String prcDt) {
		this.prcDt = prcDt;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public String getQueryReason() {
		return queryReason;
	}
	public void setQueryReason(String queryReason) {
		this.queryReason = queryReason;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getStartPrcDt() {
		return startPrcDt;
	}
	public void setStartPrcDt(String startPrcDt) {
		this.startPrcDt = startPrcDt;
	}
	public String getEndPrcDt() {
		return endPrcDt;
	}
	public void setEndPrcDt(String endPrcDt) {
		this.endPrcDt = endPrcDt;
	}
	public String getPrcTm() {
		return prcTm;
	}
	public void setPrcTm(String prcTm) {
		this.prcTm = prcTm;
	}
	public String getDeprNm() {
		return deprNm;
	}
	public void setDeprNm(String deprNm) {
		this.deprNm = deprNm;
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
	
	
	
}
