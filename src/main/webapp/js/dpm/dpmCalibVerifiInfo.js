
/**
  * @File Name : dpmCalibVerifiInfo.js
  * @Description : 교정/검증 처리
  * @Modification Information
  * 
  *   수정일       수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2022.12.06             최초 생성
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
var currntPageIndex;
var gridEventFlag;
var selectByGrid;
var onSelistfinger;
var serverDate = modComm.getServerDate();
var modDpmCalibVerifiInfo = (function(){    
    var totRowCnt = 0;
    var gridHeight = '100%';
	/**
	 * 초기화
	 */	
	function init() {
		modComm.setDatepicker("textPrcDt","imgStartDt");
		
		//마스터 그리드 초기화 시작
		//$("#txtStartDt").val(modComm.getGridDateFormat(serverDate));
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/getDpmCalibVerifiInfo.do',
	        mtype: "POST",
	        datatype: "local",
	        postData: {"textPrcDt" : $("#textPrcDt").val()},
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '엘리먼트ID',   name: 'elementId',    	   align: 'center'},
	            { label: '파일명',       name: 'imgFileName', 	   align: 'center'},
	            { label: '진행상태',     name: 'maskPrgStsc', 	   align: 'center', width: '80px'},
	            { label: '최초탐지페이지', name: 'fstImrPage',    	   align: 'center', width: '60px'},
	            { label: '검증여부', 	   name: 'userConfirm',    	   align: 'center', width: '90px'},
	            { label: '수정/유지', 	   name: 'userUpdateYn',   	   align: 'center', width: '90px'},
	            { label: 'intvisionImr',  name: 'intvisionImr',    align: 'center'}
	        ],
	       
	        height: gridHeight,
	        autowidth:true,
	        rowNum: 100,
	        rownumbers: false,
	        viewrecords: true,
	        loadtext: "<img src='/images/loadinfo.net.gif' />",
	        scrollrows: true,
	        shrinkToFit:false,
	        forceFit:true,
	        multiselect: false,
	        //jqGrid 추가옵션영역
	        pager: $("#jqGridPager"),
	        rowList: [50,100,200,300,500,1000],
	        //jsonReader 영역
	        jsonReader : {
	        	repeatitems: false,
	        	root: function(data) {
	        		if(data.rsYn == "N" && !modComm.isEmpty(data.rsMsg)) alert(data.rsMsg);
	        		return data.selList;
	        	},
	        	page: function(data) {return data.pageNumber},	//현재 페이지 번호
	        	total: function(data) {return data.totPageCnt},	//전체 페이지 수
	        	records: function(data) {return data.totRowCnt}	//전체 데이터 수
	        },
	        //로드완료 시 (조회 시 reloadGrid 후에도 호출)  
	        loadComplete: function() {
	        	
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
	      //Row클릭 이벤트
	        onSelectRow: function(rowid) {
				var selRowData = $("#jqGrid").getRowData(rowid);
				$("#viwerIframe").get(0).contentWindow.viewerSetImg(selRowData.imgFileName);
				
				var intvisionImr = selRowData.intvisionImr;
				if(intvisionImr != null && intvisionImr != undefined && $.trim(intvisionImr) !='' ) {
					var jsonImr = JSON.parse(intvisionImr);
					
					$("input:radio[name='A']:radio[value='" + jsonImr.A + "']").prop('checked', true); 
					$("input:radio[name='B']:radio[value='" + jsonImr.B + "']").prop('checked', true);
					$("input:radio[name='C']:radio[value='" + jsonImr.C + "']").prop('checked', true);
					$("input:radio[name='D']:radio[value='" + jsonImr.D + "']").prop('checked', true);
					$("input:radio[name='E']:radio[value='" + jsonImr.E + "']").prop('checked', true);
					
					$("input:radio[name='TM_RECV_YN']:radio[value='" + jsonImr.TM_RECV_YN + "']").prop('checked', true);
					$("input:radio[name='SMS_RECV_YN']:radio[value='" + jsonImr.SMS_RECV_YN + "']").prop('checked', true);
					$("input:radio[name='DM_RECV_YN']:radio[value='" + jsonImr.DM_RECV_YN + "']").prop('checked', true);
					$("input:radio[name='EMAIL_RECV_YN']:radio[value='" + jsonImr.EMAIL_RECV_YN + "']").prop('checked', true);
					$("input:radio[name='TM_OFFER_YN']:radio[value='" + jsonImr.TM_OFFER_YN + "']").prop('checked', true);
					$("input:radio[name='EMAIL_OFFER_YN']:radio[value='" + jsonImr.EMAIL_OFFER_YN + "']").prop('checked', true);
					$("input:radio[name='DM_OFFER_YN']:radio[value='" + jsonImr.DM_OFFER_YN + "']").prop('checked', true);
					
				} else {
					$("input:radio[name='A']").prop('checked', false);
					$("input:radio[name='B']").prop('checked', false);
					$("input:radio[name='C']").prop('checked', false);
					$("input:radio[name='D']").prop('checked', false);
					$("input:radio[name='E']").prop('checked', false);
					
					$("input:radio[name='TM_RECV_YN']").prop('checked', false);
					$("input:radio[name='SMS_RECV_YN']").prop('checked', false);
					$("input:radio[name='DM_RECV_YN']").prop('checked', false);
					$("input:radio[name='EMAIL_RECV_YN']").prop('checked', false);
					$("input:radio[name='TM_OFFER_YN']").prop('checked', false);
					$("input:radio[name='EMAIL_OFFER_YN']").prop('checked', false);
					$("input:radio[name='DM_OFFER_YN']").prop('checked', false);
				}
				
	        },
	        //셀더블클릭 이벤트 - deprecated
	        ondblClickRow: function(rowid, iRow, iCol) {
	        },
	        
		});
		//그리드 초기화 종료
		//그리드 resize 
		modComm.resizeJqGridWidth("jqGrid","gridContainer",$("#gridContainer").width(), true);
		
		//엑셀출력을 위한 컬럼정보 생성
		modComm.addGridColEl("jqGrid", "gridLabelList", "gridNameList", "gridWidthList", "gridAlignList");
	};
		



    
    /**
	 * 마스터 조회
	 */  
	function selList() {
		$("#jqGrid").jqGrid('clearGridData');
		
		//전체건수 조회
    	var objParam = {};
    	var arrForm = $("#frmDayPro").serializeArray();
    	//console.log(arrForm);
    	if(arrForm) {
    		arrForm.forEach(function(item) {
    			objParam[item.name] = item.value;
    		});
    	}
    	
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
		modAjax.request("/dpm/getDpmDailyProInfoTotRowCnt.do", objParam,  {
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
    	objParam.totPageCnt	= Math.ceil(objParam.totRowCnt/pageSize);
    	$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGrid").trigger('reloadGrid');    	
		$("#spnTotCnt").text(totRowCnt);
	};	
	

	
    /**
	 * 엑셀출력
	 */ 	
	function excelWrite() {		

		//조회조건
		var objParam = {};
		var arrForm = $("#frmDayPro").serializeArray();
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
			var frmDayPro = $("#frmDayPro")[0];
			frmDayPro.action = "/dpm/selListDpmDayProExcel.do";
			frmDayPro.method = "post";
			frmDayPro.submit();			
		}		
	};
	
	return {
		init: init,
		selList: selList,
		selListPage: selListPage,
		excelWrite: excelWrite		
	};

})();
/**
 * 조회버튼 클릭
 */
$("#searchBtn").on("click", function() {
	$("#prcDt").val();
	modDpmCalibVerifiInfo.selList();
});

/**
 * 엑셀버튼 클릭
 */
$("#btnExcel").on("click", function() {
	modDpmCalibVerifiInfo.excelWrite();
});



var $thumbnails = null; 
var $view = null; 

    
    
/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	console.log("ready~~~~~");
	
		
	modDpmCalibVerifiInfo.init();
	modDpmCalibVerifiInfo.selList();
	
	
	//viwerIframe.src = '/sfview/viewer.jsp';
	
	
});



String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g,"");
}


//# sourceURL=dpm1010.js
