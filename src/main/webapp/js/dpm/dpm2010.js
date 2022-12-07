
/**
  * @File Name : dpm2010.js
  * @Description : 업무별현황
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

var modDpm2010 = (function(){
	var serverDate = modComm.getServerDate();	
    var gridHeight = 480;
	/**
	 * 초기화
	 */	
	function init() {		
		//전일기준 업무별현황 그리드 초기화 시작
		$("#jqGridYday").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListBizStats.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '업무', name: 'bizBsnNm', width: 70, align: 'center' },	        	
	            { label: '대상건수', name: 'allCn', width: 70, align: 'right', formatter:'integer' },	            
	            { label: '누적처리건수', name: 'prcCn', width: 90, align: 'right', formatter:'integer' },	  
	            { label: '미대상건수', name: 'nonCn', width: 90, align: 'right', formatter:'integer' },	
	            { label: '처리율', name: 'prcRatio', width: 80, align: 'right' },
	            { label: '마스킹수', name: 'maskCn', width: 80, align: 'right', formatter:'integer' },
	            { label: '마스킹률', name: 'maskRatio', width: 80, align: 'right' },
	            { label: '검증수', name: 'verCn', width: 80, align: 'right', formatter:'integer' },
	            { label: '검증률', name: 'verRatio', width: 80, align: 'right' },	 
	            { label: '오탐수', name: 'fprCn', width: 80, align: 'right', formatter:'integer' },
	            { label: '오탐률', name: 'fprRatio', width: 80, align: 'right' },	 
	            { label: '미탐수', name: 'fnrCn', width: 80, align: 'right', formatter:'integer' },
	            { label: '미탐률', name: 'fnrRatio', width: 80, align: 'right' }	 
	        ],
	        height: 500,
	        autowidth:true,
	        rowNum: -1,
	        rownumbers: true,
	        viewrecords: true,
	        shrinkToFit:false,
	        forceFit:true,
	        loadtext: "<img src='/images/loadinfo.net.gif' />",
	        emptyrecords:"조회된 데이터가 없습니다.",	        
	        
	        //jqGrid 추가옵션영역	        	        

	        //jsonReader 영역
	        jsonReader : {
	        	repeatitems: false,
	        	root: function(data) {
	        		if(data.rsYn == "N" && !modComm.isEmpty(data.rsMsg)) alert(data.rsMsg);
	        		return data.selList;
	        	}
	        },
	        //로드완료 시 (조회 시 reloadGrid 후에도 호출)  
	        loadComplete: function() {
	        	//console.log("그리드 load complete");
	        	if($("#jqGridYday").getGridParam("reccount") > 0) {
	        		$("#jqGridYday").jqGrid("setSelection", 1);
	        	}
	        }
		});
		
		//당일기준 업무별현황 그리드 초기화 시작		
		$("#jqGridTday").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListBizStatsToday.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '업무', name: 'bizBsnNm', width: 150, align: 'center' },	        	
	            { label: '대상 이미지 건수', name: 'prcCn', width: 130, align: 'right', formatter:'integer' },	            
	            { label: '마스킹수', name: 'maskCn', width: 130, align: 'right', formatter:'integer' }                 
	        ],
	        height: 500,
	        autowidth:true,
	        rowNum: -1,
	        rownumbers: true,
	        viewrecords: true,
	        loadtext: "<img src='/images/loadinfo.net.gif' />",
	        emptyrecords:"조회된 데이터가 없습니다.",	        
	        
	        //jqGrid 추가옵션영역	        	        

	        //jsonReader 영역
	        jsonReader : {
	        	repeatitems: false,
	        	root: function(data) {
	        		if(data.rsYn == "N" && !modComm.isEmpty(data.rsMsg)) alert(data.rsMsg);
	        		return data.selList;
	        	}
	        },
	        //로드완료 시 (조회 시 reloadGrid 후에도 호출)  
	        loadComplete: function() {
	        	//console.log("그리드 load complete");
	        	if($("#jqGridTday").getGridParam("reccount") > 0) {
	        		$("#jqGridTday").jqGrid("setSelection", 1);
	        	}
	        }
		});		
		//그리드 초기화 종료

		//그리드 resize 
		modComm.resizeJqGridWidth("jqGridYday","gridContainerYday",$("#gridContainerYday").width(), true);
		modComm.resizeJqGridWidth("jqGridTday","gridContainerTday",$("#gridContainerTday").width(), true);
		//modComm.resizeJqGridWidth("jqGridYday","gridContainerYday",$("#gridContainerYday").width(), (parseInt($(window).height())-gridHeight), false);	
		//modComm.resizeJqGridWidth("jqGridTday","gridContainerTday",$("#gridContainerTday").width(), (parseInt($(window).height())-gridHeight), false);	
		
		//엑셀출력을 위한 컬럼정보 생성
		modComm.addGridColEl("jqGridYday", "gridLabelListYday", "gridNameListYday", "gridWidthListYday", "gridAlignListYday");
		modComm.addGridColEl("jqGridTday", "gridLabelListTday", "gridNameListTday", "gridWidthListTday", "gridAlignListTday");
		
		selList();
	};
		
    /**
	 * 마스터 조회
	 */  
	function selList() {
    	var objParam = {};    	
    	
    	$("#jqGridYday").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGridYday").trigger('reloadGrid');
    	    	
    	$("#jqGridTday").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGridTday").trigger('reloadGrid');
	};
	
    /**
	 * 엑셀출력
	 */ 	
	function excelWriteYday() {		
		var frmLogin = $("#frm2010Yday")[0];
		frmLogin.action = "/dpm/selListBizStatsExcel.do";
		frmLogin.method = "post";
		frmLogin.submit();	
	};
	
	function excelWriteTday() {
		var frmLogin = $("#frm2010Tday")[0];
		frmLogin.action = "/dpm/selListBizStatsTodayExcel.do";
		frmLogin.method = "post";
		frmLogin.submit();	
	};		
	
	return {
		init: init,
		selList: selList,
		excelWriteYday: excelWriteYday,
		excelWriteTday: excelWriteTday		
	};

})();

/**
 * 엑셀버튼 클릭
 */
$("#btnExcelYday").on("click", function() {
	modDpm2010.excelWriteYday();
});

$("#btnExcelTday").on("click", function() {
	modDpm2010.excelWriteTday();
});


/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpm2010.init();
	
});