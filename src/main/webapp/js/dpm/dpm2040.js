
/**
  * @File Name : dpm2040.js
  * @Description : 월별현황
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

var modDpm2040 = (function(){
	var serverDate = modComm.getServerDate();	
	
	/**
	 * 초기화
	 */	
	function init() {
		//calendar
		modComm.setMonthpicker("txtStartMm","imgStartDt");
		modComm.setMonthpicker("txtEndMm","imgEndDt");
		
		$("#txtStartMm").val(modComm.getGridDateFormat(modComm.addMonth(serverDate,-1), "month"));
		$("#txtEndMm").val(modComm.getGridDateFormat(modComm.addDate(serverDate,-1), "month"));
		
		
		//마스터 그리드 초기화 시작
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListMonthlyStats.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '처리월', name: 'basMm', width: 100, align: 'center', formatter:currencyFmatterDate},
	            { label: '전체대상건수', name: 'allCn', width: 130, align: 'right', formatter:'integer' },
	            { label: '전체누적진행율(%)', name: 'accPrcRatio', width: 130, align: 'right' },
	            //{ label: '평균월처리건수', name: '', width: 130, align: 'right', formatter:'integer' },
	            { label: '당월처리건수', name: 'prcCn', width: 130, align: 'right', formatter:'integer' },
	            { label: '누적처리건수', name: 'acmPrcCn', width: 130, align: 'right', formatter:'integer' },
	            { label: '당월마스킹건수', name: 'maskCn', width: 130, align: 'right', formatter:'integer' },
	            { label: '누적마스킹건수', name: 'acmMaskCn', width: 130, align: 'right', formatter:'integer' }
	            //{ label: '전체누적마스킹율(%)', name: '', width: 130, align: 'right' }	         
	        ],
	        height: 400,
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
	        	if($("#jqGrid").getGridParam("reccount") > 0) {
	        		$("#jqGrid").jqGrid("setSelection", 1);
	        		$("#spnTotCnt").text($("#jqGrid").getGridParam("reccount"));
	        	}
	        }
		});
		//그리드 초기화 종료

		//그리드 resize 
		modComm.resizeJqGridWidth("jqGrid","gridContainer",$("#gridContainer").width(), true);	
		
		//엑셀출력을 위한 컬럼정보 생성
		modComm.addGridColEl("jqGrid", "gridLabelList", "gridNameList", "gridWidthList", "gridAlignList");
	};
		
	/**
	 * 그리드항목 포맷정의 일자
	 */		
    function currencyFmatterDate(celval, opts , el) {
    	return modComm.getGridDateFormat(celval,"month");
    };
    
    /**
	 * 조건유효성검사
	 */    
    function getValidation(){
		if(!modComm.isValidBetweenDate("txtStartMm", "txtEndMm")) return false;
		if($("#txtEndMm").val().replace(/-/gi,"") > serverDate.substr(0,6)) {
			alert("당월 이후는 조회가 불가합니다.");
			$("#txtEndMm").focus();
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
		
    	var objParam = {};
    	var arrForm = $("#frm2040").serializeArray();
    	//console.log(arrForm);
    	if(arrForm) {
    		arrForm.forEach(function(item) {
    			objParam[item.name] = item.value;
    		});
    	}
    	
    	//console.log(objParam);
    	
    	$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGrid").trigger('reloadGrid');
    	    	

	};
	
    /**
	 * 엑셀출력
	 */ 	
	function excelWrite() {		
		//조회조건 확인
		if(!modComm.isValidBetweenDate("txtStartMm", "txtEndMm")) return;		
				
		var frmLogin = $("#frm2040")[0];
		frmLogin.action = "/dpm/selListMonthlyStatsExcel.do";
		frmLogin.method = "post";
		frmLogin.submit();	
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
	modDpm2040.selList();
});

/**
 * 엑셀버튼 클릭
 */
$("#btnExcel").on("click", function() {
	modDpm2040.excelWrite();
});



/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpm2040.init();
	
	
});