
/**
  * @File Name : dpm2050.js
  * @Description : 마스킹이력조회
  * @Modification Information
  * 
  *   수정일       수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2019.04.01             최초 생성
  *
  *  
  *  ------------------------------------------------
  *  jqGrid 4.7.0  jQuery Grid
  *  Copyright (c) 2008, Tony Tomov, tony@trirand.com
  *  Dual licensed under the MIT and GPL licenses
  *  http://www.opensource.org/licenses/mit-license.php
  *  http://www.gnu.org/licenses/gpl-2.0.html
  *  Date: 2014-12-08  
  *  ------------------------------------------------
 */

var modDpm2050 = (function(){
	var serverDate = modComm.getServerDate();
	var totRowCnt	= 0;	
	
	/**
	 * 초기화
	 */	
	function init() {
		//calendar
		modComm.setDatepicker("txtStartDt","imgStartDt");
		modComm.setDatepicker("txtEndDt","imgEndDt");
		$("#txtStartDt").val(modComm.getGridDateFormat(serverDate));		
		$("#txtEndDt").val(modComm.getGridDateFormat(serverDate));		
		
		//업무구분코드, 진행단계코드
		modComm.setSelectboxByCd("EDMS_JOB_CFCD","selBizCd", "cId", "cnm", "전체", "AL");		
		modComm.setSelectboxByCd("MASK_PRG_STSC","selStepCd", "cId", "cnm", "전체", "AL");
		
		//마스터 그리드 초기화 시작
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListMaskingHistory.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '업무구분코드', name: 'edmsJobCfcd', width: 0, align: 'center', hidden:true },	        	
	            { label: '업무', name: 'bizBsnNm', width: 100, align: 'center' },
	            { label: '엘리먼트ID', name: 'elementid', width: 150, align: 'center' },
	            { label: '마스킹 진행상태', name: 'maskPrgStsNm', width: 150, align: 'center' },
	            { label: '처리일자', name: 'prcDt', width: 100, align: 'center', formatter:currencyFmatterDate},
	            { label: '인식결과', name: 'cgnRzt', width: 90, align: 'center' },
	            { label: '지문SCORE', name: 'fpScore', width: 110, align: 'right', formatter:'integer' },
	            { label: '지문건수', name: 'fpCn', width: 90, align: 'right', formatter:'integer'},
	            { label: '마스킹이미지수', name: 'imgMaskCountInfo', width: 130, align: 'right', formatter:'integer' },
	            { label: '처리서버', name: 'maskSvrnm', width: 100, align: 'center' },
	            { label: '에이전트', name: 'maskAgent', width: 100, align: 'center' },
	            { label: '등록일시', name: 'rgDtm', width: 130, align: 'center', formatter:currencyFmatterDate},
	            { label: 'RNUM', name: 'rnum', width: 100, align: 'left' }
	        ],
	        height: 250,
	        autowidth:true,
	        rowNum: 100,
	        rownumbers: true,
	        viewrecords: true,
	        loadtext: "<img src='/images/loadinfo.net.gif' />",
	        emptyrecords:"조회된 데이터가 없습니다.",	        
	        
	        //jqGrid 추가옵션영역
	        pager: $("#jqGridPager"),
	        rowList: [100,200,300,500,1000],	        	        

	        //jsonReader 영역
	        jsonReader : {
	        	repeatitems: false,
	        	root: function(data) {
	        		if(data.rsYn == "N" && !modComm.isEmpty(data.rsMsg)) alert(data.rsMsg);
	        		return data.selList;
	        	},
	        	page: function(data) {return data.pageNumber},	//현재 페이지 번호
	        	total: function(data) {return data.totPageCnt},		//전체 페이지 수
	        	records: function(data) {return data.totRowCnt}	//전체 데이터 수
	        },
	        //로드완료 시 (조회 시 reloadGrid 후에도 호출)  
	        loadComplete: function() {
	        	//console.log("그리드 load complete");
	        	if($("#jqGrid").getGridParam("reccount") > 0) {
	        		$("#jqGrid").jqGrid("setSelection", 1);
		        	selListDetail(1);	        		
	        	}
	        },
	        
	        //페이지 이벤트
	        onPaging: function(action) {
	        	var curPage  = $("#jqGrid").getGridParam("page");
	        	var lastPage = $("#jqGrid").getGridParam("lastpage");
	        	var userPage = $("#jqGridPager").find("input.ui-pg-input").val();
	        	var pageSize = $("#jqGridPager").find("select.ui-pg-selbox option:selected").val();

	        	switch(action.split("_")[0]) {
	        		case "next" :	//다음페이지
	        			if(!modComm.isEmpty(curPage) && !modComm.isEmpty(lastPage) && curPage<lastPage) selListPage(curPage + 1, pageSize);        			
	        			break;
	        		case "prev" :	//이전페이지
	        			if(!modComm.isEmpty(curPage) && curPage > 1) {
	        				selListPage(curPage - 1, pageSize);
	        			}
	        			break;
	        		case "first" :	//처음페이지
	        			if(!modComm.isEmpty(curPage)) selListPage(1, pageSize);
	        			break;
	        		case "last" :	//마지막페이지
	        			if(!modComm.isEmpty(curPage)) selListPage(lastPage, pageSize);        			
	        			break;
	        		case "user" : //페이지번호 직접 입력시
	        			if(modComm.isEmpty(userPage) || userPage > lastPage || userPage < 1) {
	        				alert("페이지 범위가 올바르지 않습니다.");
	        				return;
	        			} else {
	        				selListPage(userPage, pageSize);	
	        			}	        			
	        			break;
	        		case "records" : //페이지사이즈 변경시
	        			if(!modComm.isEmpty(curPage)) {
	        				$("#jqGrid").setGridParam({rowNum : pageSize});
	        				selListPage(1, pageSize);
	        			}
	        			break;
	        		default : 
	        			break;
	        	}
	        	
	        },
	        //셀클릭 이벤트
	        onCellSelect: function(rowid, iCol, data, event){
	        	if(rowid > 0 && iCol >= 0) {
		        	selListDetail(rowid);
	        	} 
	        }
		});
		//그리드 초기화 종료
		
		//상세 그리드 초기화
		$("#jqGridDetail").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListMaskingHistoryDetail.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	        	{ label: '업무구분코드', name: 'edmsJobCfcd', width: 0, align: 'center', hidden:true },	
	            { label: '엘리먼트ID', name: 'elementid', width: 150, align: 'center' },
	            { label: '일련번호', name: 'sqno', width: 120, align: 'center' },
	            { label: '진행단계', name: 'maskPrgStsNm', width: 150, align: 'center' },
	            { label: '처리일시', name: 'wkDtm', width: 180, align: 'center', formatter:currencyFmatterDateTime},
	            { label: '처리서버', name: 'maskSvrnm', width: 120, align: 'center' },
	            { label: '에이전트', name: 'maskAgent', width: 120, align: 'center' }	            
	        ],
	        height: 70,
	        autowidth:true,
	        rowNum: -1,
	        rownumbers: true,
	        viewrecords: true,
	        loadtext: "<img src='/images/loadinfo.net.gif' />",	        
	        emptyrecords:"조회된 데이터가 없습니다.",	        	     	        	        

	        //jsonReader 영역
	        jsonReader : {
	        	repeatitems: false,
	        	root: function(data) {
	        		if(data.rsYn == "N" && !modComm.isEmpty(data.rsMsg)) alert(data.rsMsg);
	        		return data.selList;
	        	}
	        },
	        
	        loadComplete: function() { 
	        	if($("#jqGridDetail").getGridParam("reccount") > 0) {
	        		$("#jqGridDetail").jqGrid("setSelection", 1);
	        	}
	        }		
		});	        
		//상세 그리드 초기화		
		
		//그리드 resize 
		modComm.resizeJqGridWidth("jqGrid","gridContainer",$("#gridContainer").width(), true);
		modComm.resizeJqGridWidth("jqGridDetail","gridContainer",$("#gridContainer").width(), true);	
		
		//엑셀출력을 위한 컬럼정보 생성
		modComm.addGridColEl("jqGrid", "gridLabelList", "gridNameList", "gridWidthList", "gridAlignList");
	};
	
	/**
	 * 그리드항목 포맷정의 일자
	 */		
    function currencyFmatterDate(celval, opts , el) {
    	return modComm.getGridDateFormat(celval);
    };
    
    /**
	 * 그리드항목 포맷정의 일시
	 */    
    function currencyFmatterDateTime(celval, opts , el) {
    	return modComm.getGridDateFormat(celval,"date_time");
    };	
	
    /**
	 * 조건유효성검사
	 */    
    function getValidation(){
		if(!modComm.isValidBetweenDate("txtStartDt", "txtEndDt")) return false;		
		if($("#txtEndDt").val().replace(/-/gi,"") > serverDate) {
			alert("당일 이후는 조회가 불가합니다.");
			$("#txtEndDt").focus();
			return false;    					
		}
		
		return true;
    };
    
    /**
	 * 마스터 조회
	 */  
	function selList() {
		//조회조건 확인
		if(!getValidation()) return;
		
		//전체건수 조회
    	var objParam = {};
    	var arrForm = $("#frm2050").serializeArray();
    	//console.log(arrForm);
    	if(arrForm) {
    		arrForm.forEach(function(item) {
    			objParam[item.name] = item.value;
    		});
    	}
    	
    	//console.log(objParam);
    	selTotalCount(objParam);
		
    	//전체건수가 있으면 목록조회
		if(totRowCnt < 1) {
			alert("조회된 데이터가 없습니다.");
			return;
		} else {
        	objParam.totRowCnt	= totRowCnt;
			$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});			
			selListPage(1, $("#jqGridPager").find("select.ui-pg-selbox option:selected").val());
		}
		
		
	};
	
    /**
	 * 전체건수 조회
	 */  	
	function selTotalCount(objParam) {
		totRowCnt = 0;
		modAjax.request("/dpm/selOneMaskingHistoryTotRowCnt.do", objParam,  {
			async: false,
			success: function(data) {				
				if(!modComm.isEmpty(data) && data.rsYn == "Y" && data.hasOwnProperty("totRowCnt")) {
					totRowCnt = data.totRowCnt;	
				}
			},
            error: function(response) {
                console.log(response);
            }
    	});		
	};
	
    /**
	 * 마스터 페이징조회
	 */ 	
	function selListPage(pageNumber, pageSize) {		
		var objParam = $("#jqGrid").getGridParam("postData");    	
    	objParam.pageNumber = pageNumber;
    	objParam.pageSize	= pageSize;
    	objParam.totPageCnt	= Math.floor(objParam.totRowCnt/pageSize) + 1;
    	
    	$("#jqGridDetail").jqGrid('clearGridData');
    	
    	$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGrid").trigger('reloadGrid');
		$("#spnTotCnt").text(totRowCnt);
    			
	};	
		
    /**
	 * 상세조회
	 */ 		
	function selListDetail(rowid) {
    	var objParam = {
    			"edmsJobCfcd":$("#jqGrid").jqGrid('getRowData',rowid).edmsJobCfcd,
    			"elementid":$("#jqGrid").jqGrid('getRowData',rowid).elementid
    		};
    	
    	$("#jqGridDetail").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGridDetail").trigger('reloadGrid');		
	};
	
    /**
	 * 엑셀출력
	 */ 	
	function excelWrite() {		
		//조회조건 확인
		if(!getValidation()) return;	
		
		//조회조건
		var objParam = {};
		var arrForm = $("#frm2050").serializeArray();
		if(arrForm) {
			arrForm.forEach(function(item) {
				objParam[item.name] = item.value;
			});
		}
		
		selTotalCount(objParam);
		
    	//전체건수가 있으면 엑셀출력
		if(totRowCnt < 1) {
			alert("엑셀출력할 데이터가 없습니다.");
			return;
		} else {			
			var frmLogin = $("#frm2050")[0];
			frmLogin.action = "/dpm/selListMaskingHistoryExcel.do";
			frmLogin.method = "post";
			frmLogin.submit();			
		}		
	}	
	
	return {
		init: init,
		selList: selList,
		excelWrite: excelWrite
	};

})();

/**
 * 조회버튼 클릭
 */
$("#btnSearch").on("click", function() {
	//console.log("조회버튼 클릭");
	modDpm2050.selList();
});

/**
 * 엑셀버튼 클릭
 */
$("#btnExcel").on("click", function() {
	//console.log("엑셀버튼 클릭");
	modDpm2050.excelWrite();
});



/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpm2050.init();
	
	
});