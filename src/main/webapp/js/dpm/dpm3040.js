
/**
  * @File Name : dpm3040.js
  * @Description : 권한관리
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

var modDpm3040 = (function(){	
	
	/**
	 * 초기화
	 */	
	function init() {
		modComm.setSelectboxByUrl("/dpm/selListAuth.do", {}, "searchSelauthGrpId", "authGrpId", "authGrpnm");		
		modComm.setSelectboxByUrl("/dpm/selListMenu.do", {"upMenuSqno":"0"}, "searchSelmeneId", "menuSqno", "mnnm");				
		
		//권한관리 그리드 초기화 시작
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListMenuAuthForManagement.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: '화면ID', name: 'menuSqno', width: 100, align: 'center' },
	            { label: '화면명', name: 'mnnm', width: 250, align: 'center' },
	            { label: '권한그룹ID', name: 'authGrpId', width: 0, align: 'center', hidden:true },
	            { label: '권한여부', name: 'authUyn', width: 60, align: 'center', index: 'authUyn', editable: true, edittype: 'checkbox', editoptions: {value: "Y:N"}, formatter:'checkbox', formatoptions: {disabled : false}},
	            { label: '등록자', name: 'rgEnnm', width: 100, align: 'center' },
	            { label: '등록자ID', name: 'rgEno', width: 0, align: 'center', hidden:true },
	            { label: '등록일시', name: 'rgDtm', width: 130, align: 'center', formatter:currencyFmatterDateTime},
	            { label: '수정자', name: 'chgEnnm', width: 100, align: 'center' },
	            { label: '수정자ID', name: 'chgEno', width: 0, align: 'center', hidden:true },
	            { label: '수정일시', name: 'chgDtm', width: 130, align: 'center', formatter:currencyFmatterDateTime}
	        ],
	        height: 400,
	        autowidth:true, 
	        //shrinkToFit:false,
	        //forceFit:true,
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
	        	$("#spnTotCnt").text($("#jqGrid").getGridParam("reccount"));
	        	
	        	//체크박스 클릭 시 이벤트 정의
	        	$(".jqgrow td input:checkbox").each(function(row){
	        		$(this).click({"row":row}, function(e){
	        			//console.log(e.data.row);
	        			saveAuthMenu(row);
	        		});
	        	});
	        }
		});	
		//그리드 초기화 종료

		//그리드 resize 
		modComm.resizeJqGridWidth("jqGrid","gridContainer",$("#gridContainer").width(), true);
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
    	var arrForm = $("#frm3040").serializeArray();
    	//console.log(arrForm);
    	if(arrForm) {
    		arrForm.forEach(function(item) {
    			objParam[item.name] = item.value;
    		});
    	}
    	
    	$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGrid").trigger('reloadGrid');
	};
	
	function saveAuthMenu(row) {		
		var menuSqno  = $("#jqGrid").jqGrid('getRowData',row+1).menuSqno;
		var authGrpId = $("#jqGrid").jqGrid('getRowData',row+1).authGrpId;
		var authUyn   = $("#jqGrid").jqGrid('getRowData',row+1).authUyn;		

		
    	var objParam = {
    			"menuSqno" : menuSqno,
    			"authGrpId" : authGrpId,
    			"authUyn" : authUyn,
    			"chgEno": $("#txtLoginchrrId").val(),
    			"rgEno": $("#txtLoginchrrId").val()
    		};
    	
    	modAjax.request("/dpm/saveMenuAuth.do", objParam,  {
    		//async: false,
    		success: function(cnt) {
                selList();
    		},
            error: function(cnt) {
                //console.log(cnt);
            }
    	});
    			
	};
		
	
	return {
		init: init,
		selList: selList
	};

})();

/**
 * 조회버튼 클릭
 */
$("#btnSearch").on("click", function() {
	modDpm3040.selList();
});


/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpm3040.init();
	
});