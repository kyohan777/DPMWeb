
/**
  * @File Name : dpmDayPro.js
  * @Description : 일별 통계
  * @Modification Information
  * 
  *   수정일       수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2022.12.06             최초 생성
  *  ------------------------------------------------
 */
var currntPageIndex;
var gridEventFlag;
var selectByGrid;
var onSelistfinger;
var serverDate = modComm.getServerDate();
var modDpmDayPro = (function(){    
    var totRowCnt = 0;
    var gridHeight = '100%';
	function init() {
		modComm.setMonthpicker("textPrcDt","imgStartDt");
		//마스터 그리드 초기화 시작
		console.log($("#prcDt").val());
		if($("#prcDt").val() != ''){
			$("#textPrcDt").val($("#prcDt").val().replaceAll('/','-'));
		}else{
			$("#textPrcDt").val(modComm.getGridDateFormat(serverDate,'month'));	
		}
		
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/getDpmDayProInfo.do',
	        mtype: "POST",
	        datatype: "local",
	        postData: {"textPrcDt" : $("#textPrcDt").val()},
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '년/월/일',     name: 'prcDt',    	  index:'PRC_DT',		sorttype:'text',width: 80,align: 'center'},
	            { label: '대상건수(A)',  name: 'prcDtCnt', 	  index:'PRC_DT_CNT',	sorttype:'int', width: 90,align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '인식 O(B)',   name: 'prcCn',    	  index:'PRC_CN',		sorttype:'int', width: 90, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '인식 X(C)',   name: 'noCn',    	      index:'NO_CN',		sorttype:'int', width: 80, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '오류(D)', 	   name: 'errCn',    	  index:'ERR_CN',		sorttype:'int', width: 60, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '오류율', 	   name: 'errRat',   	  index:'ERR_RAT',		sorttype:'int', width: 55, align: 'right'},
	            { label: '처리율',       name: 'prcRat',        index:'PRC_RAT',		sorttype:'int', width: 55, align: 'right'},	  
	            { label: '검증건수', 	   name: 'verifyCn',      index:'VERIFY_CN', 	sorttype:'int', width: 60, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},	  
	            { label: '수정건수', 	   name: 'verifyUpdateCn',index:'VERIFY_UPDATE_CN',sorttype:'int', width: 60, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '수정율', 	   name: 'verifyUpdateRat',index:'VERIFY_UPDATE_RAT',sorttype:'int', width: 55, align: 'right'},
	            { label: '동의', 	  	   name: 'ay', 		   	  index:'A_Y',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'an', 		  	  index:'A_N',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'by', 		   	  index:'B_Y',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'bn',         	  index:'B_N',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 	  	   name: 'cy', 		   	  index:'C_Y',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'cn', 		   	  index:'C_N',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'dy', 		      index:'D_Y',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'dn', 		      index:'D_N',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'ey', 		      index:'E_Y',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'en', 		      index:'E_N',			sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'tmRecvY', 	  index:'TM_RECV_Y',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'tmRecvN', 	  index:'TM_RECV_N',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'smsRecvY',      index:'SMS_RECV_Y',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'smsRecvN',      index:'SMS_RECV_N',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'dmRecvY', 	  index:'DM_RECV_Y',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'dmRecvN', 	  index:'DM_RECV_N',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'emailRecvY',    index:'EMAIL_RECV_Y', sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'emailRecvN',    index:'EMAIL_RECV_N', sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'tmOfferY',      index:'TM_OFFER_Y',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'tmOfferN',      index:'TM_OFFER_N',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'dmOfferY',      index:'SMS_OFFER_Y',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'dmOfferN',      index:'SMS_OFFER_N',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'emailOfferY',   index:'DM_OFFER_Y',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'emailOfferN',   index:'DM_OFFER_N',	sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '동의', 		   name: 'smsOfferY',     index:'EMAIL_OFFER_Y',sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }},
	            { label: '미동의', 	   name: 'smsOfferN',     index:'EMAIL_OFFER_N',sorttype:'int', width: 50, align: 'center', formatter:'integer',formatoptions: { defaultValue: '', thousandsSeparator: ',' }}
	            
	        ],
	       
	        height: gridHeight,
	        autowidth:true,
	        rowNum: 100,
	        sortable : true,
			loadonce : false, //이옵션이 정렬시에 다시쿼리 안날리고 화면에서 하는거
	        rownumbers: true,
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
	        loadComplete: function(data) {
	        },
	        onSortCol: function(columnName, columnIndex, sortOrder) {
    			var pageNumber = $(".ui-pg-input").val();
				var pageSize   = $("#jqGridPager").find("select.ui-pg-selbox option:selected").val();
    			$('#columnName').val(columnName);
    			$('#sortOrder').val(sortOrder);
    			console.log("sortOrder="+sortOrder);
    			selListPage(pageNumber,pageSize);
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
	        },
	        //셀더블클릭 이벤트 - deprecated
	        ondblClickRow: function(rowid, iRow, iCol) {
				var rowdata = 	$("#jqGrid").getRowData(rowid);
				$("#prcDt").val(rowdata.prcDt);
				var frmDayPro = $("#frmDayPro")[0];
				frmDayPro.action = "/dpm/dpmDailyPro.do";
				frmDayPro.method = "post";
				frmDayPro.submit();			
	        },
	        
		});
		//jQuery("#jqGrid").jqGrid('setFrozenColumns');
		jQuery("#jqGrid").jqGrid('setGroupHeaders', {
  			useColSpanStyle: false, 
  			groupHeaders:[
 				{startColumnName: 'prcCn',		    numberOfColumns: 4, titleText: '처리현황'},
 				{startColumnName: 'verifyCn',		numberOfColumns: 3, titleText: '검증현황'},
 				{startColumnName: 'ay',		    	numberOfColumns: 2, titleText: '금융안내'},
 				{startColumnName: 'by',		    	numberOfColumns: 2, titleText: '금융이외'},
 				{startColumnName: 'cy',		    	numberOfColumns: 2, titleText: '보험제공'},
 				{startColumnName: 'dy',		    	numberOfColumns: 2, titleText: '딜러제공'},
 				{startColumnName: 'ey',		    	numberOfColumns: 2, titleText: 'KB제공'},
 				{startColumnName: 'tmRecvY',		numberOfColumns: 2, titleText: '수집-전화'},
 				{startColumnName: 'smsRecvY',	    numberOfColumns: 2, titleText: '수집-문자'},
 				{startColumnName: 'dmRecvY',		numberOfColumns: 2, titleText: '수집-DM'},
 				{startColumnName: 'emailRecvY',	    numberOfColumns: 2, titleText: '수집-메일'},
 				{startColumnName: 'tmOfferY',	    numberOfColumns: 2, titleText: '제공-전화'},
 				{startColumnName: 'dmOfferY',	    numberOfColumns: 2, titleText: '제공-DM'},
 				{startColumnName: 'emailOfferY',	numberOfColumns: 2, titleText: '제공-메일'},
 				{startColumnName: 'smsOfferY',		numberOfColumns: 2, titleText: '제공-문자'}
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
			$("#jqGrid > tbody").append("<tr class='ui-widget-content jqgrow ui-ltr'><td colspan='35' class='text-center'>조회된 결과가 없습니다.</td></tr>");
			return;
		} else {
			$("#columnName").val("");
			$("#sortOrder").val("");
        	var pageNumber = 1;
			var pageSize   = $("#jqGridPager").find("select.ui-pg-selbox option:selected").val();
        	objParam.totRowCnt	= totRowCnt;
    		objParam.pageNumber = pageNumber;
    		objParam.pageSize	= pageSize;
    		objParam.totPageCnt	= Math.ceil(totRowCnt/pageSize);
    		objParam.startPageNumber = (((pageNumber - 1) * pageSize));
			$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});	
			$("#jqGrid").trigger('reloadGrid');		
			//selListPage(1, $("#jqGridPager").find("select.ui-pg-selbox option:selected").val());
		}
	
	};
	
    /**
	 * 전체건수 조회
	 */  	
	function selTotalCount(objParam) {
		totRowCnt = 0;
		modAjax.request("/dpm/getDpmDayProInfoTotRowCnt.do", objParam,  {
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
    	objParam.startPageNumber = (((pageNumber - 1) * pageSize));
    	objParam.columnName = $("#columnName").val();
    	objParam.sortOrder = $("#sortOrder").val();
    	$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGrid").trigger('reloadGrid');    	
		//$("#spnTotCnt").text(totRowCnt);
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
			alert("조회된 결과가 없습니다.");
			//$("#jqGrid > tbody").append("<tr class='ui-widget-content jqgrow ui-ltr'><td colspan='35' class='text-center'>조회된 결과가 없습니다.</td></tr>");
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
	modDpmDayPro.selList();
});

/**
 * 엑셀버튼 클릭
 */
$("#btnExcel").on("click", function() {
	modDpmDayPro.excelWrite();
});


/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpmDayPro.init();
	modDpmDayPro.selList();
});

$("#textPrcDt").keydown(function(key){
	if(key.keyCode == 13) {
		modDpmDailyPro.selList();
	}
});

//# sourceURL=dpm1010.js
