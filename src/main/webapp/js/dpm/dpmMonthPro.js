
/**
  * @File Name : dpmMonthPro.js
  * @Description : 월별 통계
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

var modDpmMonthPro = (function(){    
    var totRowCnt = 0;
    var gridHeight = 600;
	/**
	 * 초기화
	 */	
	function init() {
		//마스터 그리드 초기화 시작
		modComm.setMonthpicker("textPrcDt","imgStartDt");
		//마스터 그리드 초기화 시작
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/getDpmMonthProInfo.do',
	        mtype: "POST",
	        datatype: "local",
	        postData: {"textPrcDt" : $("#textPrcDt").val()},
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: 'YYYY/MM',    name: 'prcDt',    	   width: 200,align: 'center'},
	            { label: '대상', 	       name: 'prcDtCnt', 	   width: 100,align: 'center'},
	            { label: '정상', 		   name: 'prcCn',    	   width: 80, align: 'center'},
	            { label: '오류', 		   name: 'errCn',    	   width: 80, align: 'center'},
	            { label: '오류율', 	   name: 'errRat',   	   width: 80, align: 'center'},
	            { label: '처리율', 	   name: 'prcRat',         width: 80, align: 'center'},	  
	            { label: '수정', 		   name: 'verifyUpdateCn', width: 80, align: 'center'},
	            { label: '유지', 		   name: 'maintainCn',     width: 80, align: 'center'},
	            { label: '동의', 	  	   name: 'aY', 		   	   width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'aN', 		  	   width: 80, align: 'center'},
	            { label: '동의', 		   name: 'bY', 		   	   width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'bN',         	   width: 80, align: 'center'},
	            { label: '동의', 	  	   name: 'cY', 		   	   width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'cN', 		   	   width: 80, align: 'center'},
	            { label: '동의', 		   name: 'dY', 		       width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'dN', 		       width: 80, align: 'center'},
	            { label: '동의', 		   name: 'eY', 		       width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'eN', 		       width: 80, align: 'center'},
	            { label: '동의', 		   name: 'tmRecvAgrY', 	   width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'tmRecvAgrN', 	   width: 80, align: 'center'},
	            { label: '동의', 		   name: 'smsRecvAgrY',    width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'smsRecvAgrN',    width: 80, align: 'center'},
	            { label: '동의', 		   name: 'dmRecvAgrY', 	   width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'dmRecvAgrN', 	   width: 80, align: 'center'},
	            { label: '동의', 		   name: 'emailRecvAgrY',  width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'emailRecvAgrN',  width: 80, align: 'center'},
	            { label: '동의', 		   name: 'tmOfferAgrY',    width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'tmOfferAgrN',    width: 80, align: 'center'},
	            { label: '동의', 		   name: 'dmOfferAgrY',    width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'dmOfferAgrN',    width: 80, align: 'center'},
	            { label: '동의', 		   name: 'emailOfferAgrY', width: 80, align: 'center'},
	            { label: '미동의', 	   name: 'emailOfferAgrN', width: 80, align: 'center'}
	        ],
	       
	        height: gridHeight,
	        autowidth:true,
	        rowNum: 100,
	        rownumbers: false,
	        rownumWidth : 40,
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
	        //Row클릭 이벤트 일별 통계 화면으로 이동 
	        onSelectRow: function(rowid) {
				var rowdata = 	$("#jqGrid").getRowData(rowid);
				$("#prcDt").val(rowdata.prcDt);
				var frmMonthPro = $("#frmMonthPro")[0];
				frmMonthPro.action = "/dpm/dpmDayPro.do";
				frmMonthPro.method = "post";
				frmMonthPro.submit();			

	        },
	        //셀더블클릭 이벤트 - deprecated
	        ondblClickRow: function(rowid, iRow, iCol) {
	        },
	        
		});
		
		jQuery("#jqGrid").jqGrid('setGroupHeaders', {
  			useColSpanStyle: true, 
  			groupHeaders:[
 				{startColumnName: 'prcCn',  		numberOfColumns: 3, titleText: '<center>처리 현황</center>'},
 				{startColumnName: 'verifyUpdateCn', numberOfColumns: 2, titleText: '<center>검증 건수</center>'},
 				{startColumnName: 'aY',		    	numberOfColumns: 2, titleText: '<center>금융안내</center>'},
 				{startColumnName: 'bY',		    	numberOfColumns: 2, titleText: '<center>금융이외</center>'},
 				{startColumnName: 'cY',		    	numberOfColumns: 2, titleText: '<center>보험제공</center>'},
 				{startColumnName: 'dY',		    	numberOfColumns: 2, titleText: '<center>딜러제공</center>'},
 				{startColumnName: 'eY',		    	numberOfColumns: 2, titleText: '<center>KB제공</center>'},
 				{startColumnName: 'tmRecvAgrY',		numberOfColumns: 2, titleText: '<center>수집-전화</center>'},
 				{startColumnName: 'smsRecvAgrY',	numberOfColumns: 2, titleText: '<center>수집-문자</center>'},
 				{startColumnName: 'dmRecvAgrY',		numberOfColumns: 2, titleText: '<center>수집-DM</center>'},
 				{startColumnName: 'emailRecvAgrY',	numberOfColumns: 2, titleText: '<center>수집-메일</center>'},
 				{startColumnName: 'tmOfferAgrY',	numberOfColumns: 2, titleText: '<center>제공-전화</center>'},
 				{startColumnName: 'dmOfferAgrY',	numberOfColumns: 2, titleText: '<center>제공-DM</center>'},
 				{startColumnName: 'emailOfferAgrY',	numberOfColumns: 2, titleText: '<center>제공-메일</center>'}
  			] 
  			
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
    	var arrForm = $("#frmMonthPro").serializeArray();
    	//console.log(arrForm);
    	if(arrForm) {
    		arrForm.forEach(function(item) {
    			objParam[item.name] = item.value;
    			console.log(item.value);  
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
		modAjax.request("/dpm/getDpmMonthProInfoTotRowCnt.do", objParam,  {
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
	function selListPage(pageNumber, pageSize, selIdx) {		
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
		var arrForm = $("#frmMonthPro").serializeArray();
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
			var frmMonthPro = $("#frmMonthPro")[0];
			frmMonthPro.action = "/dpm/selListDpmMonthProExcel.do";
			frmMonthPro.method = "post";
			frmMonthPro.submit();			
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
 * 엑셀버튼 클릭
 */
$("#btnExcel").on("click", function() {
	modDpmMonthPro.excelWrite();
});

/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpmMonthPro.init();
	modDpmMonthPro.selList();
});
//# sourceURL=dpm1010.js
