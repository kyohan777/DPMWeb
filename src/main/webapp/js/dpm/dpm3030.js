
/**
  * @File Name : dpm3030.js
  * @Description : 그룹관리
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

var modDpm3030 = (function(){	
	
	/**
	 * 초기화
	 */	
	function init() {
		modComm.setSelectboxByUrl("/dpm/selListChrr.do", {}, "selChrrAuthChrrId", "chrrId", "chrrNm", "", "");
		
		
		//그룹 그리드 초기화 시작
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListGroupAuth.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '그룹ID', name: 'authGrpId', width: 170, align: 'center' },
	            { label: '그룹명', name: 'authGrpnm', width: 200, align: 'left' },
	            { label: '등록자', name: 'rgEnnm', width: 100, align: 'center' },
	            { label: '등록자ID', name: 'rgEno', width: 0, align: 'center', hidden:true },
	            { label: '등록일시', name: 'rgDtm', width: 130, align: 'center', formatter:currencyFmatterDateTime},
	            { label: '수정자', name: 'chgEnnm', width: 100, align: 'center' },
	            { label: '수정자ID', name: 'chgEno', width: 0, align: 'center', hidden:true },
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
		
		//사용자목록 그리드 초기화 시작		
		$("#jqGridDetail").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListChrrGroupAuth.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '그룹ID', name: 'authGrpId', width: 0, align: 'center', hidden:true },
	            { label: '그룹명', name: 'authGrpnm', width: 180, align: 'left' },
	            { label: 'ID', name: 'chrrId', width: 120, align: 'center' },
	            { label: '사용자명', name: 'chrrNm', width: 180, align: 'left' },
	            { label: '부서명', name: 'deptnm', width: 120, align: 'left' },
	            { label: '사용유무', name: 'uyn', width: 80, align: 'center' },
	            { label: '등록자', name: 'rgEnnm', width: 100, align: 'center' },
	            { label: '등록자ID', name: 'rgEno', width: 0, align: 'center', hidden:true },
	            { label: '등록일시', name: 'rgDtm', width: 130, align: 'center', formatter:currencyFmatterDateTime},
	            { label: '수정자', name: 'chgEnnm', width: 100, align: 'center' },
	            { label: '수정자ID', name: 'chgEno', width: 0, align: 'center', hidden:true },
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
		//modComm.resizeJqGridWidth("jqGridDetail","gridContainerDetail",$("#gridContainerDetail").width(), true);		
		
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
    	var objParam = {};
    	$("#jqGridDetail").jqGrid('clearGridData');
    	
    	$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGrid").trigger('reloadGrid');
	};
	
    /**
	 * 상세 조회
	 */	
	function selListDetail(rowid) {
    	var objParam = {"authGrpId":$("#jqGrid").jqGrid('getRowData',rowid).authGrpId};
    	
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
			
			$("#txtGrpAuthGrpId").val("");
			$("#txtGrpAuthGrpNm").val("");			
			$("#txtGrpAuthGrpId").focus();
		} else {
			readonly = true;
			$("#txtGrpAuthGrpId").val($("#jqGrid").jqGrid('getRowData',rowid).authGrpId);
			$("#txtGrpAuthGrpNm").val($("#jqGrid").jqGrid('getRowData',rowid).authGrpnm);
		}
		
		$("#txtGrpAuthGrpId").attr("readonly", readonly);
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
				$("#txtChrrAuthGrpId").val($("#jqGrid").jqGrid('getRowData',codeSelRowId).authGrpId);
			} else {
				$("#txtChrrAuthGrpId").val("");
			}
			$("#selChrrAuthChrrId").val("");
			$("#selChrrAuthUseYn").val("Y");
		} else {
			readonly = true;
			
			$("#txtChrrAuthGrpId").val($("#jqGridDetail").jqGrid('getRowData',rowid).authGrpId);
			$("#selChrrAuthChrrId").val($("#jqGridDetail").jqGrid('getRowData',rowid).chrrId);
			$("#selChrrAuthUseYn").val($("#jqGridDetail").jqGrid('getRowData',rowid).uyn);			
		}
		$("#txtChrrAuthGrpId").attr("readonly", readonly);		
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
 * 권한그룹 초기화버튼 클릭
 */
$("#btnAuthInit").on("click", function() {
	modDpm3030.setMasterInfo(-1);
});

/**
 * 권한사용자 초기화버튼 클릭
 */
$("#btnChrrAuthInit").on("click", function() {
	modDpm3030.setDetailInfo(-1);
});

/**
 * 권한그룹 저장버튼 클릭
 */
$("#btnAuthSave").on("click", function() {
	
	var authGrpId = $("#txtGrpAuthGrpId").val();
	var authGrpNm = $("#txtGrpAuthGrpNm").val();
	
	if(modComm.isEmpty(authGrpId)) {
		alert("그룹ID 입력이 누락 되었습니다.");
		$("#txtGrpAuthGrpId").focus();
		return;
	}
	
	if(modComm.isEmpty(authGrpNm)) {
		alert("그룹명 입력이 누락 되었습니다.");
		$("#txtGrpAuthGrpNm").focus();
		return;
	}
	
	var objParam = {
			"authGrpId": authGrpId,
			"authGrpnm": authGrpNm,
			"chgEno": $("#txtLoginchrrId").val(),
			"rgEno": $("#txtLoginchrrId").val()
	};
	
	modAjax.request("/dpm/saveGroupAuth.do", objParam,  {
		//async: false,
		success: function(cnt) {
			alert("그룹정보 저장이 완료되었습니다.");
			modDpm3030.selList();
		},
        error: function(cnt) {
            console.log(cnt);
        }
	});
	
});

/**
 * 권한사용자 저장버튼 클릭
 */
$("#btnChrrAuthSave").on("click", function() {
	
	var authGrpId = $("#txtChrrAuthGrpId").val();	
	var chrrId = $("#selChrrAuthChrrId").val();
	var uyn = $("#selChrrAuthUseYn").val();
	
	if(modComm.isEmpty(authGrpId)) {
		alert("그룹ID 입력이 누락 되었습니다.");
		$("#txtChrrAuthGrpId").focus();
		return;
	}
	
	if(modComm.isEmpty(chrrId)) {
		alert("담당자 선택이 안되었습니다.");
		$("#selChrrAuthChrrId").focus();
		return;
	}
	
	if(modComm.isEmpty(uyn)) {
		alert("사용유무 선택이 안되었습니다.");
		$("#selChrrAuthUseYn").focus();
		return;
	}
	
	var objParam = {
			"authGrpId": authGrpId,
			"chrrId": chrrId,
			"uyn": uyn,
			"chgEno": $("#txtLoginchrrId").val(),
			"rgEno": $("#txtLoginchrrId").val()
	};
	
	console.log(objParam);

	modAjax.request("/dpm/saveChrrGroupAuth.do", objParam,  {
		//async: false,
		success: function(cnt) {
			alert("그룹 사용자정보 저장이 완료되었습니다.");	
			modDpm3030.selListDetail($("#jqGrid").jqGrid('getGridParam', 'selrow'));
		},
        error: function(cnt) {
            console.log(cnt);
        }
	});
	
	 
});

/**
 * 권한사용자 삭제버튼 클릭
 */
$("#btnChrrAuthDel").on("click", function() {
	
	var authGrpId = $("#txtChrrAuthGrpId").val();	
	var chrrId = $("#selChrrAuthChrrId").val();
	
	if(modComm.isEmpty(authGrpId)) {
		alert("그룹ID를 선택해주세요.");
		$("#txtChrrAuthGrpId").focus();
		return;
	}
	
	if(modComm.isEmpty(chrrId)) {
		alert("담당자를 선택해주세요..");
		$("#selChrrAuthChrrId").focus();
		return;
	}
	
	if (!confirm("그룹 사용자정보를 삭제 하시겠습니까?")) {
		return;
	}
	
	
	var objParam = {
			"authGrpId": authGrpId,
			"chrrId": chrrId
	};
	
	console.log(objParam);

	modAjax.request("/dpm/deleteChrrGroupAuth.do", objParam,  {
		//async: false,
		success: function(cnt) {
			alert("그룹 사용자정보 삭제가 완료되었습니다.");	
			modDpm3030.selListDetail($("#jqGrid").jqGrid('getGridParam', 'selrow'));
		},
        error: function(cnt) {
            console.log(cnt);
        }
	});
	
	 
});

/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpm3030.init();
	
});