
/**
  * @File Name : dpm1020.js
  * @Description : AGENT 할당
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

var modDpm1020 = (function(){
	var serverDate = modComm.getServerDate();
	var totRowCnt	= 0;	
	//그리드 초기화 종료
	var lastSelection;
	/**
	 * 초기화
	 */	
	function init() {
		//업무구분코드, 할당서버
		//modComm.setSelectboxByCd("BPR_BSN_DSC","selBizCd", "cId", "cnm", "전체", "AL");		
		modComm.setSelectboxByCd("EDMS_JOB_CFCD","selBizCd", "cId", "cnm", "전체", "AL");
		modComm.setSelectboxByCd("MASK_SERVER_CD","selSvrnm", "cId", "cnm", "전체", "000");
		//AGENT수
//		var i;
//		for(i=1; i<=100; i++) {
//			$("#selAgentCn").append("<option value='" + i + "'>" + i + "</option>");	
//		}
//		$("#selAgentCn").val(30);		
		
		//마스터 그리드 초기화 시작
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListAgentAssign.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //cws 추가
	        cellEdit:true,
	        cellsubmit:'clentArray',
	        cellurl:'clentArray', //'/dpm/updAgentAssign.do',

	        afterEditCell : function(rowid, cellname,value,iRow,iCol){

	        },
	        
	        afterSaveCell : function(rowid, cellname,value,iRow,iCol){

	        },
	        
	        //jqGrid 양식선언부        
	        colModel: [
//	            { label: 'BPR업무구분코드', name: 'bprBsnDsc', width: 0, align: 'center', hidden:true },	        	
//	            { label: '서버명', name: 'maskSvrnm', width: 80, align: 'center',editable:true },
//	            { label: 'AGENT명', name: 'maskAgent', width: 80, align: 'center',editable:true },	            
//	            { label: '전체 건수', name: 'cntAll', width: 130, align: 'right', formatter:'integer',editable:true},
//	            { label: '이미지이관완료 건수(10)', name: 'cnt10', width: 130, align: 'right', formatter:'integer',editable:true},	            
//	            { label: '이미지다운완료 건수(20)', name: 'cnt20', width: 130, align: 'right', formatter:'integer',editable:true},
//	            { label: '인식완료 건수(30)', name: 'cnt30', width: 130, align: 'right', formatter:'integer',editable:true},
//	            { label: '수정완료 건수(50)', name: 'cnt40', width: 130, align: 'right', formatter:'integer',editable:true},
//	            { label: '이미지업로드완료 건수(60)', name: 'cnt50', width: 130, align: 'right', formatter:'integer',editable:true},
//	            { label: '검증완료 건수(65)', name: 'cnt60', width: 130, align: 'right', formatter:'integer',editable:true},
//	            { label: '운영반영완료 건수(70)', name: 'cnt70', width: 130, align: 'right', formatter:'integer',editable:true}
	        		        	
	            { label: '서버번호', name: 'serverNo', width: 80, align: 'right'},
	            { label: '할당여부', name: 'managerStatus', index:'managerStatus', width: 80, align: 'center', editable:true, edittype: 'select', 
	            	editoptions:{
	            	    	  		value:"ON:할당;OFF:미할당",
	            	    	  		dataEvents:[{type:'change',fn:function(e){
		            	    	  			var rowid = $("jqGrid").getGridParam("selrow");
		            	    	  			var rowData = getJsonRow(rowid);
		            	    	  			var value = rowData['managerStatus'];
		            	    	  			var evalue = $(e.target).val();
		            	    	  				
		            	    	  			$("#select_box > option[value='"+$(e.target).val()+"']").attr("selected", "true");
		            	    	  			detailChanged(grid,"managerStatus_DTIL",$(e.target).val());
	            	    	  			}
	            	    	  		}]
	            	},
	            	formatter:'select'	    	  		  
	            },
	            { label: '기동상태', name: 'stats', index:'stats', width: 80, align: 'center', editable:false, edittype: 'select', 
	            	editoptions:{
	            	    	  		value:"ACT:동작중;STOP:미동작;ERROR:오류",
	            	    	  		dataEvents:[{type:'change',fn:function(e){
		            	    	  			var rowid = $("jqGrid").getGridParam("selrow");
		            	    	  			var rowData = getJsonRow(rowid);
		            	    	  			var value = rowData['stats'];
		            	    	  			var evalue = $(e.target).val();
		            	    	  				
		            	    	  			$("#select_box > option[value='"+$(e.target).val()+"']").attr("selected", "true");
		            	    	  			detailChanged(grid,"stats_DTIL",$(e.target).val());
	            	    	  			}
	            	    	  		}]
	            	},
	            	formatter:'select'	    	  		  
	            },	
	            
	            { label: '재처리 횟수', name: 'reprocCnt', width: 130, align: 'right', formatter:'integer',editable:true},
	            { label: 'Agent별 조회건수', name: 'rowCnt', width: 130, align: 'right', formatter:'integer',editable:true},	            
	            { label: 'Agent 할당갯수', name: 'agentCnt', width: 130, align: 'right', formatter:'integer',editable:true},
	            { label: 'Agent 조회시작일', name: 'startIpdttm', width: 130, align: 'center', editable:true,
	            	editoptions:{ dataInit: function(e){
	            			$(e).datepicker({dateFormat:'yy-mm-dd'});
	            		}	
	            	}
	            },
	            { label: 'Agent 조회종료일', name: 'endIpdttm', width: 130, align: 'center', editable:true,
	            	editoptions:{ dataInit: function(e){
	            			$(e).datepicker({dateFormat:'yy-mm-dd'});
	            		}	
	            	}
	            },
	            { label: '운영기준일자', name: 'etc3', width: 130, align: 'center', editable:true,
	            	editoptions:{ dataInit: function(e){
	            			$(e).datepicker({dateFormat:'yy-mm-dd'});
	            		}	
	            	}
	            },

	            { label: '업무구분', name: 'edmsJobCfcd', index:'edmsJobCfcd', width: 130, align: 'center', editable:true, edittype: 'select', 
	            	editoptions:{
    	    	  		value:"ALL:전체;CO:보상손사;CP:준법;CS:법인영업;CT:상품계약;FI:재무;GA:총무;LO:여신종합;PS:개인영업;RA:퇴직연금;SP:고객",
    	    	  		dataEvents:[{type:'change',fn:function(e){
        	    	  			var rowid = $("jqGrid").getGridParam("selrow");
        	    	  			var rowData = getJsonRow(rowid);
        	    	  			var value = rowData['edmsJobCfcd'];
        	    	  			var evalue = $(e.target).val();
        	    	  				
        	    	  			$("#select_box > option[value='"+$(e.target).val()+"']").attr("selected", "true");
        	    	  			detailChanged(grid,"edmsJobCfcd_DTIL",$(e.target).val());
    	    	  			}
    	    	  		}]
	            	},
	            	formatter:'select'
	            },

	            { label: 'Agent 기동시간', name: 'etc1', width: 130, align: 'right', editable:true},
	            { label: 'Agent 종료시간', name: 'etc2', width: 130, align: 'center', editable:true},
	            { label: '변경일자', name: 'chgDtm', width: 130, align: 'center', editable:false},
	            { label: '변경자', name: 'chgEno', width: 130, align: 'center'},

	        ],
	        height: 400,
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
	        //로드완료 시 (조회 시 reloadGrid 후에도 호출)  
	        loadComplete: function() {
	        	//console.log("그리드 load complete");
	        	if($("#jqGrid").getGridParam("reccount") > 0) {
	        		$("#jqGrid").jqGrid("setSelection", 1);    
	        		setMasterInfo(1);
	        		currentRow = 1;
	        	}else{

		        	setMasterInfo(-1);

	        	}
	        },
	      //셀클릭 이벤트
	        onCellSelect: function(rowid, iCol, data, event){
	        	//alert(rowid);
	        	//alert(iCol);
	        	if(rowid > 0){// && iCol >= 0) {
	        		$("#jqGrid").jqGrid('resetSelection');
	        		$("#jqGrid").jqGrid("setSelection", rowid);
	        		setMasterInfo(rowid);
	        		currentRow = rowid;

	        	} 
	        },
	        onSelectRow: editRow
  

		});
		
		
		function editRow(id){
			//alert(id);
			if(id && id !== lastSelection){
				var grid = $("#jqGrid");

				$("#jqGrid").saveRow(lastSelection,false,"clientArray");
				$("#jqGrid").editRow(id,true,null,null,"clientArray"); 
				lastSelection = id;
			}
        	
        }
		
		
		//그리드 resize 
		modComm.resizeJqGridWidth("jqGrid","gridContainer",$("#gridContainer").width(), true);			
	};
	
    
    /**
	 * 마스터 조회
	 */  
	function selList() {		
		lastSelection = 0;
		$("#jqGrid").jqGrid('clearGridData');
    	var objParam = {};
    	var arrForm = $("#frm1020").serializeArray();
    	//console.log(arrForm);
    	if(arrForm) {
    		arrForm.forEach(function(item) {
    			objParam[item.name] = item.value;
    		});
    	}
    	
    	//console.log("조회파라미터");
    	//console.log(objParam);
    	
		$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});			
    	$("#jqGrid").trigger('reloadGrid');

	};	

	function getCurrentRow() {	
		
//		for(var i = 0; i < $("#jqGrid").getGridParam('reccount');i++){
//			alert("currentRow : " + (i+1).toString());
//			$("#jqGrid").saveRow(i+1,false,"clientArray");
//
//		}
		//alert("currentRow : " + lastSelection.toString());
		$("#jqGrid").saveRow(lastSelection,false,"clientArray");
		//alert(lastSelection.toString() + " saved complete.");
		var objParam = $("#jqGrid").getRowData(currentRow);
		
		var data = JSON.stringify(objParam);

		var serverNo = objParam.serverNo;
		modAjax.request("/dpm/updAgentAssign.do", objParam,  {
			//async: false,
			success: function() {
				//alert("[" + cnt + "] 건의 AGENT 할당이 완료되었습니다.");
				alert("[" + serverNo.trim() + "번] 서버의 AGENT 할당이 완료되었습니다.");
				selList();						
			},
	        error: function(serverNo) {
	            console.log();
	        }
		});	
		
	};	
	
    /**
	 * 선택한 마스터정보 set
	 */		
	function setMasterInfo(rowid) {
	    /**
		 * 선택한 마스터정보 set
		 */		
		var readonly;
		if(rowid == -1) {
			readonly = false;
			
			
			$("#txtServerNo").val("");
			$("#txtReprocCnt").val("");
			$("#txtRowCnt").val("");
			$("#txtAgentCnt").val("");
			$("#txtServerNo").focus();
		} else {
			readonly = true;
			
			$("#txtServerNo").val($("#jqGrid").jqGrid('getRowData',rowid).serverNo);
			console.log($("#jqGrid").jqGrid('getRowData',rowid).serverNo);
			$("#txtReprocCnt").val($("#jqGrid").jqGrid('getRowData',rowid).reprocCnt);	
			console.log($("#jqGrid").jqGrid('getRowData',rowid).reprocCnt);
			$("#txtRowCnt").val($("#jqGrid").jqGrid('getRowData',rowid).rowCnt);
			$("#txtAgentCnt").val($("#jqGrid").jqGrid('getRowData',rowid).agentCnt);
		}
		
		$("#txtServerNo").attr("readonly", readonly);

	};

	return {
		init: init,
		selList: selList,
		setMasterInfo: setMasterInfo,
		getCurrentRow: getCurrentRow
	};

})();

/**
 * 초기화버튼 클릭
 */
$("#btnChrrInit").on("click", function() {
	modDpm1020.setMasterInfo(-1);
});

/**
 * 저장버튼 클릭
 */
$("#btnChrrSave").on("click", function() {
	var serverNo = $("#txtServerNo").val();
	var reprocCnt = $("#txtReprocCnt").val();
	var rowCnt = $("#txtRowCnt").val();
	var agentCnt = $("#txtAgentCnt").val();	
	
	if(modComm.isEmpty(serverNo)) {
		alert("서버번호 입력이 누락 되었습니다");
		$("#txtServerNo").focus();
		return;
	}
	
	if(modComm.isEmpty(reprocCnt)) {
		alert("재처리횟수 입력이 누락 되었습니다.");
		$("#txtReprocCnt").focus();
		return;
	}
	
	if(modComm.isEmpty(rowCnt)) {
		alert("조회건수 입력이 누락 되었습니다.");
		$("#txtRowCnt").focus();
		return;
	}	
	
	if(modComm.isEmpty(agentCnt)) {
		alert("에이전트할당건수 입력이 누락 되었습니다.");
		$("#txtAgentCnt").focus();
		return;
	}	
	
	if(isNaN(serverNo)) {
		alert("서버번호에 숫자를 입력해 주세요.");
		$("#txtServerNo").focus();
		return;
	}
	
	if(isNaN(reprocCnt)) {
		alert("재처리횟수 숫자를 입력해 주세요.");
		$("#txtReprocCnt").focus();
		return;
	}
	
	if(isNaN(rowCnt)) {
		alert("조회건수 숫자를 입력해 주세요.");
		$("#txtRowCnt").focus();
		return;
	}	
	
	if(isNaN(agentCnt)) {
		alert("에이전트할당건수 숫자를 입력해 주세요.");
		$("#txtAgentCnt").focus();
		return;
	}
	
	var objParam = {
			"serverNo": serverNo,
			"reprocCnt": reprocCnt,
			"rowCnt": rowCnt,
			"agentCnt": agentCnt,			
			"chgEno": $("#txtLoginchrrId").val(),
			"rgEno": $("#txtLoginchrrId").val(),
			"etc3" : CONVERTDAY
	};
	
	modAjax.request("/dpm/saveChrrForAgent.do", objParam,  {
		//async: false,
		success: function(cnt) {
			alert("서버정보 저장이 완료되었습니다.");
			modDpm1020.selList();
		},
        error: function(cnt) {
            console.log(cnt);
        }
	});
});

/**
 * 분류코드 삭제버튼 클릭
 */
$("#btnChrrDelete").on("click", function() {
	var serverNo = $("#txtServerNo").val();
	
	if(modComm.isEmpty(serverNo)) {
		alert("서버번호 입력이 누락 되었습니다.");
		$("#txtServerNo").focus();
		return;
	}
	
	
	var objParam = {
			"serverNo": serverNo,
			"chgEno": $("#txtLoginchrrId").val(),
			"rgEno": $("#txtLoginchrrId").val()
	};
	
	modAjax.request("/dpm/deleteAgent.do", objParam,  {
		//async: false,
		success: function(cnt) {
			alert("서버정보 삭제가 완료되었습니다.");
			modDpm1020.selList();
		},
        error: function(cnt) {
            console.log(cnt);
        }
	});
});


/**
 * 조회버튼 클릭
 */
$("#btnSearch").on("click", function() {
	modDpm1020.selList();
});

/**
 * 할당버튼 클릭
 */
$("#btnAssign").on("click", function() {
	if(!confirm("각 서버의 AgentManger에서 작업을 멈추셨습니까? 재할당 하시겠습니까?")) return;
	

	modDpm1020.getCurrentRow(); 

});


/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpm1020.init();
	
});