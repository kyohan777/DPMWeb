package com.minervasoft.backend.vo;

public class UserManageVo  extends AbstractVO{
	private String chrrId; 			//열람자ID
	private String chrrNm; 			//성명
	private String chrrPwd;			//비밀번호
	private String companyId;		//사번
	private Integer idNo;			//연번
	private String prcDt;			//조회일자
	private String deptnm;			//부서명
	private String uyn;				//사용여부
	private String rgDt;			//등록일자
	private String rgTm;			//등록일시
	private String rgId;			//등록자 id
	private String rgNm;			//등록자명
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
	public String getDeptnm() {
		return deptnm;
	}
	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}
	public String getUyn() {
		return uyn;
	}
	public void setUyn(String uyn) {
		this.uyn = uyn;
	}
	public String getRgDt() {
		return rgDt;
	}
	public void setRgDt(String rgDt) {
		this.rgDt = rgDt;
	}
	public String getRgTm() {
		return rgTm;
	}
	public void setRgTm(String rgTm) {
		this.rgTm = rgTm;
	}
	public String getRgId() {
		return rgId;
	}
	public void setRgId(String rgId) {
		this.rgId = rgId;
	}
	public String getRgNm() {
		return rgNm;
	}
	public void setRgNm(String rgNm) {
		this.rgNm = rgNm;
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
	public String getChrrPwd() {
		return chrrPwd;
	}
	public void setChrrPwd(String chrrPwd) {
		this.chrrPwd = chrrPwd;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
