
/**
  * @File Name : dpm3010.js
  * @Description : 코드관리
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

var modDpm3010 = (function(){	
	
	/**
	 * 초기화
	 */	
	function init() {		
		//분류코드 그리드 초기화 시작
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListCodeForManagement.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '코드', name: 'cId', width: 170, align: 'center' },
	            { label: '코드분류명', name: 'cnm', width: 200, align: 'left' },
	            { label: '상위코드ID', name: 'upCId', width: 0, align: 'center', hidden:true },
	            { label: '사용유무', name: 'uyn', width: 0, align: 'center', hidden:true },
	            { label: '등록자', name: 'rgEnnm', width: 100, align: 'center' },
	            { label: '등록자개인번호', name: 'rgEno', width: 0, align: 'center', hidden:true },
	            { label: '등록일시', name: 'rgDtm', width: 130, align: 'center', formatter:currencyFmatterDateTime},
	            { label: '수정자', name: 'chgEnnm', width: 100, align: 'center' },
	            { label: '수정자개인번호', name: 'chgEno', width: 0, align: 'center', hidden:true },
	            { label: '수정일시', name: 'chgDtm', width: 130, align: 'center', formatter:currencyFmatterDateTime}	                     
	        ],
	        height: 350,
	        autowidth:true, 
	        shrinkToFit:false,
	        forceFit:true,
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
	        		setMasterInfo(1);
	        		selListDetail(1);
	        	} else {
	        		setMasterInfo(-1);
	        		selListDetail(-1);	        		
	        	}
	        },
	        //셀클릭 이벤트
	        onCellSelect: function(rowid, iCol, data, event){
	        	if(rowid > 0 && iCol >= 0) {
	        		setMasterInfo(rowid);
		        	selListDetail(rowid);
	        	} 
	        }
		});
		
		//상세분류코드 그리드 초기화 시작		
		$("#jqGridDetail").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListCodeDetailForManagement.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '상위코드ID', name: 'upCId', width: 0, align: 'center', hidden:true },
	            { label: '코드분류명', name: 'upCnm', width: 180, align: 'left' },	            
	            { label: '코드', name: 'cId', width: 80, align: 'center' },
	            { label: '코드명', name: 'cnm', width: 180, align: 'left' },
	            { label: '표시순서', name: 'scrnMrkSq', width: 80, align: 'right' },	            
	            { label: '사용유무', name: 'uyn', width: 0, align: 'center', hidden:true },
	            { label: '등록자', name: 'rgEnnm', width: 100, align: 'center' },
	            { label: '등록자개인번호', name: 'rgEno', width: 0, align: 'center', hidden:true },
	            { label: '등록일시', name: 'rgDtm', width: 130, align: 'center', formatter:currencyFmatterDateTime},
	            { label: '수정자', name: 'chgEnnm', width: 100, align: 'center' },
	            { label: '수정자개인번호', name: 'chgEno', width: 0, align: 'center', hidden:true },
	            { label: '수정일시', name: 'chgDtm', width: 130, align: 'center', formatter:currencyFmatterDateTime}
	        ],
	        height: 350,
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
	        	if($("#jqGridDetail").getGridParam("reccount") > 0) {
	        		$("#jqGridDetail").jqGrid("setSelection", 1);
	        		setDetailInfo(1);
	        	} else {
	            	setDetailInfo(-1);
	        	}
	        },
	        //셀클릭 이벤트
	        onCellSelect: function(rowid, iCol, data, event){
	        	if(rowid > 0 && iCol >= 0) {
	        		setDetailInfo(rowid);
	        	} 
	        }
		});		
		//그리드 초기화 종료

		//그리드 resize 
		//modComm.resizeJqGridWidth("jqGrid","gridContainer",$("#gridContainer").width(), true);
		modComm.resizeJqGridWidth("jqGridDetail","gridContainerDetail",$("#gridContainerDetail").width(), true);		
		
		selList();
	};
	
    /**
	 * 그리드항목 포맷정의 일시
	 */    
    function currencyFmatterDateTime(celval, opts , el) {
    	return modComm.getGridDateFormat(celval,"date_time");
    };	
		
    /**
	 * 마스터 조회
	 */  
	function selList() {
    	var objParam = {"upCId" : "0"};
    	$("#jqGridDetail").jqGrid('clearGridData');
    	
    	$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGrid").trigger('reloadGrid');
	};
	
    /**
	 * 상세 조회
	 */	
	function selListDetail(rowid) {
    	var objParam = {"upCId":$("#jqGrid").jqGrid('getRowData',rowid).cId};
    	
    	$("#jqGridDetail").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGridDetail").trigger('reloadGrid');		
	};
	
    /**
	 * 선택한 마스터정보 set
	 */		
	function setMasterInfo(rowid) {
		var readonly;
		if(rowid == -1) {
			readonly = false;
			
			$("#txtCode").val("");
			$("#txtCodeNm").val("");			
			$("#txtCode").focus();
		} else {
			readonly = true;
			
			$("#txtCode").val($("#jqGrid").jqGrid('getRowData',rowid).cId);
			$("#txtCodeNm").val($("#jqGrid").jqGrid('getRowData',rowid).cnm);			
		}
		
		$("#txtCode").attr("readonly", readonly);
	};
	
    /**
	 * 선택한 상세정보 set
	 */			
	function setDetailInfo(rowid) {
		var readonly;
		if(rowid == -1) {
			readonly = false;
			
			var codeSelRowId = $("#jqGrid").jqGrid('getGridParam', 'selrow');
			if(codeSelRowId > 0) {
				$("#txtDetailUpCode").val($("#jqGrid").jqGrid('getRowData',codeSelRowId).cId);
			} else {
				$("#txtDetailUpCode").val("");
			}
			$("#txtDetailCode").val("");
			$("#txtDetailCodeNm").val("");
			$("#txtScrnMrkSq").val("");
			$("#selCodeUseYn").val("Y");
			$("#txtDetailCode").focus();
		} else {
			readonly = true;
			
			$("#txtDetailUpCode").val($("#jqGridDetail").jqGrid('getRowData',rowid).upCId);
			$("#txtDetailCode").val($("#jqGridDetail").jqGrid('getRowData',rowid).cId);
			$("#txtDetailCodeNm").val($("#jqGridDetail").jqGrid('getRowData',rowid).cnm);
			$("#txtScrnMrkSq").val($("#jqGridDetail").jqGrid('getRowData',rowid).scrnMrkSq);
			$("#selCodeUseYn").val($("#jqGridDetail").jqGrid('getRowData',rowid).uyn);			
		}
		$("#txtDetailUpCode").attr("readonly", readonly);
		$("#txtDetailCode").attr("readonly", readonly);		
	};	
		
	
	return {
		init: init,
		selList: selList,
		selListDetail: selListDetail,		
		setMasterInfo: setMasterInfo,
		setDetailInfo: setDetailInfo
	};

})();

/**
 * 분류코드 초기화버튼 클릭
 */
$("#btnCodeInit").on("click", function() {
	modDpm3010.setMasterInfo(-1);
});

/**
 * 상세분류코드 초기화버튼 클릭
 */
$("#btnDetailCodeInit").on("click", function() {
	modDpm3010.setDetailInfo(-1);
});

/**
 * 분류코드 저장버튼 클릭
 */
$("#btnCodeSave").on("click", function() {
	var code = $("#txtCode").val();
	var codeNm = $("#txtCodeNm").val();
	
	if(modComm.isEmpty(code)) {
		alert("코드 입력이 누락 되었습니다.");
		$("#txtCode").focus();
		return;
	}
	
	if(modComm.isEmpty(codeNm)) {
		alert("코드분류명 입력이 누락 되었습니다.");
		$("#txtCodeNm").focus();
		return;
	}
	
	var objParam = {
			"cId": code,
			"cnm": codeNm,
			"chgEno": $("#txtLoginchrrId").val(),
			"rgEno": $("#txtLoginchrrId").val()
	};
	
	modAjax.request("/dpm/saveCode.do", objParam,  {
		//async: false,
		success: function(cnt) {
			alert("분류코드 저장이 완료되었습니다.");
			modDpm3010.selList();
		},
        error: function(cnt) {
            console.log(cnt);
        }
	});
});

/**
 * 분류코드 삭제버튼 클릭
 */
$("#btnCodeDelete").on("click", function() {
	var code = $("#txtCode").val();
	var codeNm = $("#txtCodeNm").val();
	
	if(modComm.isEmpty(code)) {
		alert("코드 입력이 누락 되었습니다.");
		$("#txtCode").focus();
		return;
	}
	
	if(modComm.isEmpty(codeNm)) {
		alert("코드분류명 입력이 누락 되었습니다.");
		$("#txtCodeNm").focus();
		return;
	}
	
	var objParam = {
			"cId": code,
			"cnm": codeNm,
			"chgEno": $("#txtLoginchrrId").val(),
			"rgEno": $("#txtLoginchrrId").val()
	};
	
	modAjax.request("/dpm/deleteCode.do", objParam,  {
		//async: false,
		success: function(cnt) {
			alert("분류코드 삭제가 완료되었습니다.");
			modDpm3010.selList();
		},
        error: function(cnt) {
            console.log(cnt);
        }
	});
});

/**
 * 상세분류코드 저장버튼 클릭
 */
$("#btnDetailCodeSave").on("click", function() {
	var upCode = $("#txtDetailUpCode").val();	
	var code = $("#txtDetailCode").val();
	var codeNm = $("#txtDetailCodeNm").val();
	var scrnMrkSq = $("#txtScrnMrkSq").val();
	
	if(modComm.isEmpty(upCode)) {
		alert("상위코드 입력이 누락 되었습니다.");
		$("#txtDetailUpCode").focus();
		return;
	}
	
	if(modComm.isEmpty(code)) {
		alert("코드 입력이 누락 되었습니다.");
		$("#txtDetailCode").focus();
		return;
	}
	
	if(modComm.isEmpty(codeNm)) {
		alert("코드분류명 입력이 누락 되었습니다.");
		$("#txtDetailCodeNm").focus();
		return;
	}
	
	if(modComm.isEmpty(scrnMrkSq)) {
		alert("화면표시순서 입력이 누락 되었습니다.");
		$("#txtScrnMrkSq").focus();
		return;
	}	
	
	var objParam = {
			"upCId": upCode,
			"cId": code,
			"cnm": codeNm,
			"chgEno": $("#txtLoginchrrId").val(),
			"rgEno": $("#txtLoginchrrId").val(),
			"scrnMrkSq": scrnMrkSq,
			"uyn": $("#selCodeUseYn").val()
	};
	
	//console.log(objParam);

	modAjax.request("/dpm/saveDetailCode.do", objParam,  {
		//async: false,
		success: function(cnt) {
			alert("상세분류코드 저장이 완료되었습니다.");	
			modDpm3010.selListDetail($("#jqGrid").jqGrid('getGridParam', 'selrow'));						
		},
        error: function(cnt) {
            console.log(cnt);
        }
	});	
});


/**
 * 분류코드 삭제버튼 클릭
 */
$("#btnDetailCodeDelete").on("click", function() {
	var upCode = $("#txtDetailUpCode").val();	
	var code = $("#txtDetailCode").val();
	var codeNm = $("#txtDetailCodeNm").val();
	var scrnMrkSq = $("#txtScrnMrkSq").val();
	
	if(modComm.isEmpty(upCode)) {
		alert("상위코드 입력이 누락 되었습니다.");
		$("#txtDetailUpCode").focus();
		return;
	}
	
	if(modComm.isEmpty(code)) {
		alert("코드 입력이 누락 되었습니다.");
		$("#txtDetailCode").focus();
		return;
	}
	
	if(modComm.isEmpty(codeNm)) {
		alert("코드분류명 입력이 누락 되었습니다.");
		$("#txtDetailCodeNm").focus();
		return;
	}
	
	if(modComm.isEmpty(scrnMrkSq)) {
		alert("화면표시순서 입력이 누락 되었습니다.");
		$("#txtScrnMrkSq").focus();
		return;
	}	
	
	var objParam = {
			"upCId": upCode,
			"cId": code,
			"cnm": codeNm,
			"chgEno": $("#txtLoginchrrId").val(),
			"rgEno": $("#txtLoginchrrId").val(),
			"scrnMrkSq": scrnMrkSq,
			"uyn": $("#selCodeUseYn").val()
	};
	
	modAjax.request("/dpm/deleteDetailCode.do", objParam,  {
		//async: false,
		success: function(cnt) {
			alert("분류코드 삭제가 완료되었습니다.");
			modDpm3010.selList();
		},
        error: function(cnt) {
            console.log(cnt);
        }
	});
});

/**
 * input text의 numberOnly 속성처리
 */
$("input:text[numberOnly]").on("keyup", function() {
	$(this).val($(this).val().replace(/[^0-9]/g,""));
});

/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpm3010.init();
	
});