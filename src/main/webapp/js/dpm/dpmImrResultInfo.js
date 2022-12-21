
/**
  * @File Name : modDpmImrResultInfo.js
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

var rowidSel; 

var modDpmImrResultInfo = (function(){    
    var totRowCnt = 0;
    var gridHeight = '100%';
	/**
	 * 초기화
	 */	
	function init() {
		modComm.setDatepicker("textPrcDt","imgStartDt");
		//마스터 그리드 초기화 시작
		$("#textPrcDt").val(modComm.getGridDateFormat(serverDate));
		
		//alert(modComm.getGridDateFormat(serverDate));
		
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/getDpmImrResultInfo.do',
	        mtype: "POST",
	        datatype: "local",
	        postData: {"textPrcDt" : $("#textPrcDt").val()},
	        //jqGrid 양식선언부        
	        colModel: [
				{ label: '엘리먼트ID',   name: 'elementId', 	   align: 'center', width:'0px'},
				{ label: 'custId',    name: 'custId', 	   align: 'center', width:'0px'},
				{ label: 'contractId', name: 'contractId', 	   align: 'center', width:'0px'},
	            { label: '파일명',       name: 'imgFileName',	   align: 'center'},
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
				
				//$("#elementId").val(selRowData.elementId);
				//$("#custId").val(selRowData.custId);
				//$("#contractId").val(selRowData.contractId);
				
				$(".join_form #elementId").val(selRowData.elementId);
				$(".join_form #contractId").val(selRowData.custId);
				$(".join_form #custId").val(selRowData.contractId);
				
				rowidSel = rowid;
				newUser();
				//imrRadioSet(rowid);
				
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
		
		//열 숨기기
		$("#jqGrid").jqGrid("hideCol",["elementId","custId","contractId"]);
	};
		
	
	


    
    /**
	 * 마스터 조회
	 */  
	function selList() {
		
		//imr 데이터 초기화
		dataResetImr();
		viwerIframe.src = '/sfview/viewer.jsp';
		
		$("#jqGrid").jqGrid('clearGridData');
		
		//전체건수 조회
    	var objParam = {};
    	var arrForm = $("#frmCalibVerifiInfo").serializeArray();
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
	modDpmImrResultInfo.selList();
});

/**
 * 엑셀버튼 클릭
 */
$("#btnExcel").on("click", function() {
	modDpmImrResultInfo.excelWrite();
});


function imrRadioSet(rowid) {
		
		var selRowData = $("#jqGrid").getRowData(rowid);
		$("#viwerIframe").get(0).contentWindow.viewerSetImg(selRowData.imgFileName);
					
		
		var intvisionImr = selRowData.intvisionImr;
		if(intvisionImr != null && intvisionImr != undefined && $.trim(intvisionImr) !='' ) {
			var jsonImr = JSON.parse(intvisionImr);
			
			$("#intvisionImr").val(intvisionImr);
			
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
			dataResetImr();
		}
}


function dataResetImr() {
	$("#intvisionImr").val("");
							
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

/**
 * 확정버튼 클릭
 */
$("#btnConfirm").on("click", function() {
		
	if($("#elementId").val() == '') {
		alert("확정할 대상이 없습니다.");
		return;
	}
	if(!confirm("확정하시겠습니까?")) {
		return;
	}
	
	var imrObj = {};
	
	var arrForm = $("#frmImrInfo").serializeArray();
	if(arrForm) {
		arrForm.forEach(function(item) {
			imrObj[item.name] = item.value;
			console.log("name:" + item.name + ", value" + item.value);
		});
	}
	
	var strImr = JSON.stringify(imrObj);
	console.log("strImr: ~~~" + strImr);				
	/*
	var objParam = {
		"elementId" : $("#elementId").val(),
		"intvisionImr" : $("#intvisionImr").val(),
		"ayn" : strImr
	};
	*/
	var objParam = {
		"intvisionImr" : strImr,
 	};
	
	// 확정처리
	modAjax.request("/dpm/imrConfirm.do", objParam, {
		 async : false,
		 success : function(data) {
			var jsonData = JSON.parse(data);
			if(jsonData.updCnt == 1) {
				alert("성공적으로 반영하였습니다.");
			} 
			if(jsonData.errMsg != "success") {
				alert("오류:" + jsonData.errMsg);
			}
			modDpmImrResultInfo.selList();
			
		},
		error : function(data) {
			alert(data);
		}
	});
	
	
});


//레이어팝업 오픈
    function layer_popup(el){

        var $el = $(el);    //레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass('dimBg'); //dimmed 레이어를 감지하기 위한 boolean 변수

        isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

        var $elWidth = ~~($el.outerWidth()),
            $elHeight = ~~($el.outerHeight()),
            docWidth = $(document).width(),
            docHeight = $(document).height();

        // 화면의 중앙에 레이어를 띄운다.
        if ($elHeight < docHeight || $elWidth < docWidth) {
            $el.css({
                marginTop: -$elHeight /2,
                marginLeft: -$elWidth/2
            })
        } else {
            $el.css({top: 0, left: 0});
        }

        $el.find('#close').click(function(){
            isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
            return false;
        });

        $('.layer .dimBg').click(function(){
            $('.dim-layer').fadeOut();
            return false;
        });

    }
    
//신규 등록 레이어팝업 오픈
	function newUser(){
        layer_popup('#layer');
	};
	
	//레이어 폼 data 초기화
	function dataReSet(){
		$(".join_form #elementId").val('');
		$(".join_form #contractId").val('');
		$(".join_form #custId").val('');
		$(".join_form #NewRgReason").val('');
		rowidSel = null;
	}
	
    //저장 버튼
    $("#insert").on("click", function() {
		var objChrr;
		
		if(modComm.isEmpty($(".join_form #NewRgReason").val())) {
			alert("사유를 입력하십시오.");
			$(".join_form #NewRgReason").focus();
			return;
		}else{
			$(".join_form #NewRgReason").val().split("\n").join("\\n");
		}
		
		if(modComm.isEmpty($(".join_form #elementId").val())) {
			alert("해당 건의 elementId 가 없습니다.");
			return;
		}
		if(modComm.isEmpty($(".join_form #contractId").val())) {
			alert("해당 건의 계약번호가 없습니다.");
			return;
		}
		if(modComm.isEmpty($(".join_form #custId").val())) {
			alert("해당 건의 고객번호가 없습니다.");
			return;
		}
		
		
		var objParam = {};
		var arrForm = $("#frmUserInfo").serializeArray();
		//console.log(arrForm);
		if(arrForm) {
			arrForm.forEach(function(item) {
				objParam[item.name] = item.value;
			});
		}
		objParam["queryReason"] = $(".join_form #NewRgReason").val(); 
		console.log(objParam);
		
		modAjax.request("/dpm/insertViewReason.do", objParam,  {
		async: false,
		success: function(data) {				
			if(!modComm.isEmpty(data) && data.errMsg == "success") {
				alert("등록에 성공했습니다.");
				closePopup('#layer');
				imrRadioSet(rowidSel);
				dataReSet();// 등록 폼 리셋
			}
			if(!modComm.isEmpty(data) && data.errMsg != "success") {
				alert(data.errMsg);
			}
		},
        error: function(response) {
			alert(response);
        }
		});
	
	});

	
	//등록 후 레이어 닫기
	function closePopup(el){
		var $el = $(el);   
        var isDim = $el.prev().hasClass('dimBg'); 
        isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();
        isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); 
	}
	
    
    
/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
		
	modDpmImrResultInfo.init();
	modDpmImrResultInfo.selList();
	
	//viwerIframe.src = '/sfview/viewer.jsp';
	
});



String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g,"");
}


//# sourceURL=modDpmImrResultInfo.js
