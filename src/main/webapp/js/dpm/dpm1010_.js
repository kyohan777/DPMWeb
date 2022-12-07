
/**
  * @File Name : dpm1010.js
  * @Description : 이미지검증
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

var modDpm1010 = (function(){    
    var serverDate = modComm.getServerDate();
    var totRowCnt = 0;
    var axAllViewCtlC1;	//이미지뷰어 OBJECT
    var objCommUtil;	//파일제어 및 Multipart 전송 OBJECT
    var winImgView;    
    
	/**
	 * 초기화
	 */	
	function init() {
		//calendar
		modComm.setDatepicker("txtStartDt","imgStartDt");
		modComm.setDatepicker("txtEndDt","imgEndDt");
		$("#txtStartDt").val(modComm.getGridDateFormat(serverDate));		
		$("#txtEndDt").val(modComm.getGridDateFormat(serverDate));		
		
		//업무구분코드
		modComm.setSelectboxByCd("EDMS_JOB_CFCD","selBizCd", "cId", "cnm", "전체", "AL");		
		modComm.setSelectboxByCd("IMG_STYL_ID","selImgStylId", "cId", "cnm", "전체", "0000000", "Y");
		
		//지문score
		var i;
		for(i=0; i<=100; i++) {
			$("#selFpScore").append("<option value='" + i + "'>" + i + "</option>");	
		}
		$("#selAgentCn").val(0);		
		
		//마스터 그리드 초기화 시작
		$("#jqGrid").jqGrid({
	    	//jqGrid url 전송선언
	        url: '/dpm/selListImageVerify.do',
	        mtype: "POST",
	        //styleUI: "Bootstrap",	        
	        datatype: "local",
	        postData: {},
	        
	        //jqGrid 양식선언부        
	        colModel: [
	            { label: 'EDMS업무구분코드', name: 'edmsJobCfcd', width: 0, align: 'center', hidden:true },
	            { label: '처리일자', name: 'prcDt', width: 100, align: 'center', formatter:currencyFmatterDate},
	            { label: '업무', name: 'bizBsnNm', width: 60, align: 'center' },
	            //{ label: '이미지양식ID', name: 'imgStylId', width: 0, align: 'center', hidden:true },
	            { label: '폼', name: 'imgStylNm', width: 100, align: 'center' },
	            { label: '마스킹수', name: 'fpCn', width: 80, align: 'right', formatter:'integer' },
	            //{ label: '지문SCORE', name: 'fpScore', width: 100, align: 'right', formatter:'integer' },
	            { label: '페이지수', name: 'imgCountInfo', width: 80, align: 'right', formatter:'integer' },
	            { label: '인덱스ID', name: 'idxId', width: 0, align: 'center', hidden:true },
	            { label: '엘리먼트ID', name: 'elementid', width: 140, align: 'center' },
	            { label: '마스크엘리먼트ID', name: 'elementidMask', width: 0, align: 'center', hidden:true },
	            { label: '마스크진행상태코드', name: 'maskPrgStsc', width: 0, align: 'center', hidden:true },
	            { label: '처리서버', name: 'maskSvrnm', width: 100, align: 'center' },
	            { label: '처리에이전트', name: 'maskAgent', width: 100, align: 'center' },
	            { label: '이미지저장경로', name: 'imgPath', width: 0, align: 'center', hidden:true },
	            { label: '기타5', name: 'etc5', width: 0, align: 'center', hidden:true },
	            { label: '기타4', name: 'etc4', width: 0, align: 'center', hidden:true }
	            
	        ],
	        height: 350,
	        autowidth:true,
	        rowNum: 100,
	        rownumbers: true,
	        viewrecords: true,
	        loadtext: "<img src='/images/loadinfo.net.gif' />",
	        emptyrecords:"조회된 데이터가 없습니다.",
	        multiselect: false,
	        scrollrows: true,
	        shrinkToFit:false,
	        
	        //jqGrid 추가옵션영역
	        pager: $("#jqGridPager"),
	        rowList: [100,200,300,500,1000],

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
	        	//console.log("그리드 load complete");
	        	if($("#jqGrid").getGridParam("reccount") > 0) {
	        		addThumbnailMaskFile();
	        		//$("#jqGrid").jqGrid("setSelection", 1);
	        	}
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
	        //셀클릭 이벤트
	        onCellSelect: function(rowid, iCol, data, event){
	        	if(rowid > 0 && iCol >= 0) {
	        	} 
	        },
	        //셀더블클릭 이벤트
	        ondblClickRow: function(rowid, iRow, iCol) {
	        	if(rowid < 1 || iCol <0 ) return;
	        	
	        	var sMask = $("#jqGrid").jqGrid('getRowData',rowid).etc4;
	        	var sThumbnail = $("#jqGrid").jqGrid('getRowData',rowid).etc5;
	        	var sOrigin = $("#jqGrid").jqGrid('getRowData',rowid).imgPath;
	        	var sEID = $("#jqGrid").jqGrid('getRowData',rowid).elementid;
	        	var sIDXID = $("#jqGrid").jqGrid('getRowData',rowid).idxId;
	        	var sJobID = $("#jqGrid").jqGrid('getRowData',rowid).bprBsnDsc;
	        	
	        	pSubLoadImgView(sOrigin, sMask, sIDXID, sEID, sJobID, sThumbnail);
	        }
	        //멀티셀렉트 사용시 체크박스 선택 시에만 체크되도록 함
	        /*
	        beforeSelectRow: function(rowid, e) {
	            var $jqGrid = $(this),
	            i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
	            cm = $jqGrid.jqGrid('getGridParam', 'colModel');
	            return (cm[i].name === 'cb');
	        }
	        */
		}).hideCol('cb');	//.hideCol('cb')는 multiselect: true인 경우 앞의 체크박스를 숨기기 위함
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
    	return modComm.getGridDateFormat(celval);
    };
    
    /**
	 * 그리드항목 포맷정의 일시
	 */    
    function currencyFmatterDateTime(celval, opts , el) {
    	return modComm.getGridDateFormat(celval,"date_time");
    };	
	
    /**
	 * 조건유효성검사
	 */    
    function getValidation(){
		if(!modComm.isValidBetweenDate("txtStartDt", "txtEndDt")) return false;		
		if($("#txtEndDt").val().replace(/-/gi,"") > serverDate) {
			alert("당일 이후는 조회가 불가합니다.");
			$("#txtEndDt").focus();
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
		
		//전체건수 조회
    	var objParam = {};
    	var arrForm = $("#frm1010").serializeArray();
    	//console.log(arrForm);
    	if(arrForm) {
    		arrForm.forEach(function(item) {
    			objParam[item.name] = item.value;
    		});
    	}
    	
    	//console.log(objParam);
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
		modAjax.request("/dpm/selOneImageVerifyTotRowCnt.do", objParam,  {
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
    	objParam.totPageCnt	= Math.floor(objParam.totRowCnt/pageSize) + 1;
    	
    	$("#jqGrid").setGridParam({datatype : 'json', postData : objParam});
    	$("#jqGrid").trigger('reloadGrid');    	
		$("#spnTotCnt").text(totRowCnt);
    			
	};	
	
    /**
	 * 엑셀출력
	 */ 	
	function excelWrite() {		
		//조회조건 확인
		if(!getValidation()) return;	
		
		//조회조건
		var objParam = {};
		var arrForm = $("#frm1010").serializeArray();
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
			var frmLogin = $("#frm1010")[0];
			frmLogin.action = "/dpm/selListImageVerifyExcel.do";
			frmLogin.method = "post";
			frmLogin.submit();			
		}		
	};
	
    /**
	 * 이미지조회 팝업창 open
	 */ 	
	function pSubLoadImgView(sOrigin, sMask, sIDXID, sEID, sJobID, sThumbnail) {
		//팝업창에서 참조할 값 set
		$("#txt1010PopupOrigin").val(sOrigin);
		$("#txt1010PopupMask").val(sMask);
		$("#txt1010PopupIDXID").val(sIDXID);
		$("#txt1010PopupEID").val(sEID);
		$("#txt1010PopupJobID").val(sJobID);
		$("#txt1010PopupThumbnail").val(sThumbnail);
				
		var nWidth = "700";
		var nHeight = "700";
		  
		// 듀얼 모니터 고려한 윈도우 띄우기
		var curX = window.screenLeft;
		var curY = window.screenTop;
		var curWidth = document.body.clientWidth;
		var curHeight = document.body.clientHeight;
		  
		var nLeft = curX + (curWidth / 2) - (nWidth / 2);
		var nTop = curY + (curHeight / 2) - (nHeight / 2);

		var strOption = "";
		strOption += "left=" + nLeft + "px,";
		strOption += "top=" + nTop + "px,";
		strOption += "width=" + nWidth + "px,";
		strOption += "height=" + nHeight + "px,";
		strOption += "toolbar=no,menubar=no,location=no,";
		strOption += "resizable=yes,status=yes";		
		
		var winImgView = window.open("/dpm/dpm0010Pop.ui", "frm1010Popup", strOption);
	};	
	
    /**
	 *************************************************************************************************************
	 * 이미지뷰어 dll OBJECT 제어
	 *************************************************************************************************************
	 */ 	
	
    /**
     *  Function :  작업 폴더를 생성하고 썸네일 경로를 초기화한다.
	    Param    :	없음
	    Return	 :	없음
     */	
    function objectInit() {    	
        /// Viewer Create
        var divViewer = document.getElementById("divAllView");        
        var viewerHTML = 
        f_createObjectToHTML("MImgView", "99%", "420px", MALLVIEW_OCX_CLSID, "", "");
        divViewer.innerHTML = viewerHTML;
        
        axAllViewCtlC1 = document.getElementById("MImgView");
        ThumbnailViewerInit(axAllViewCtlC1);
        
        /// CommUtil Create
        var divCommUtil = document.getElementById("divCommUtil");
        var commUtilHTML = 
        f_createObjectToHTML("CommUtil", "0", "0", COMMUTIL_OCX_CLSID, "", "");
        divCommUtil.innerHTML = commUtilHTML;
        objCommUtil = document.getElementById("CommUtil");
        	
        /// FileManager Create
        var divFileManager = document.getElementById("divFileManager");
        var fileManagerHTML = 
        f_createObjectToHTML("MFileManager", "0", "0", MFILEMANAGER_OCX_CLSID, "", "");
        divFileManager.innerHTML = fileManagerHTML;

    	
    };
    
    /**
     *  Function :  HTML에 OBJECT를 Write한다.
	    Param    :	id       - Object ID
	    			width    - Object Size
	    			height   - Object Size
	    			classid  - Object CLASSID
	    			codebase - Object CODEBASE
	    			version  - Object version
	    Return	 :	strHTML
     */	    
    function f_createObjectToHTML (id, width, height, classid, codebase, version) {
    	var strHTML = '';
    	
    	strHTML += '<SCRIPT FOR="' + id + '" EVENT="axAllViewCtlC1_OnThumbnailMouseEvent(sender, e)" LANGUAGE="javascript">';
    	strHTML += 'alert("TEST");';
    	strHTML += '</SCRIPT>';
    	
    	
    	if (width  == "") width = 0;
    	if (height == "") height = 0;

    	strHTML += '<OBJECT  ID="' + id + '" WIDTH="' + width + '" HEIGHT="' + height + '" ';
    	strHTML += 'CLASSID="' + classid;

    	if (codebase != "") {
    		strHTML += '" CODEBASE="' + codebase;
    		if (version != "")
    			strHTML += '#version=' + version;
    	}

    	strHTML += '">';
    	strHTML += '</OBJECT>';
    	//alert("strHTML: " + strHTML);

    	//document.writeln(strHTML);

    	return strHTML;
    };    
    
    
    /**
     *  Function :  썸네일 화면을 초기화 한다.
	    Param    :	obj - 올뷰 object
	    Return	 :	없음
     */	     
    function ThumbnailViewerInit(obj)
    {
    	obj.AutoCreateThumbnails = 1;
    	obj.ShowThumbnailsView = true;	// 썸네일 전용 뷰어 (썸네일 보이기)
    	obj.ShowMainView = false;	// 썸네일 전용 뷰어 (베인뷰어 숨김)
    	obj.LockThumbnailPosition = 0;	// 드래그 허용 불가 | 0 : 변경가능 , 1 : 변경불가
    	obj.SetOnOffOption(2);		// 썸네일 클릭시 빨간박스 생기도록
    	obj.MultiPageThumbnailsMultiSelectMode = 1;// 썸네일 다중선택 | 0 : 단일 , 1 : 다중
    	obj.ReDrawThumbnails();		// 썸네일 화면 다시 그리기
    	obj.setThumbnailsViewFrameSize = 120;
    	obj.DrawImage();
    };
    
    /**
     *  Function :  썸네일 추가
     */	    
    function addThumbnailMaskFile()
    {
    	var arrGridData = $('#jqGrid').getRowData();
    	
    	axAllViewCtlC1.ClearThumbnails();
    	arrGridData.forEach(function(item) {
    		if($("#selCategory").val() == "5") {	// 이미지
    			var imgPath = item.imgPath;    			
    			if(!modComm.isEmpty(imgPath)) {
    				axAllViewCtlC1.AddThumbnailFile(imgPath, objCommUtil.GetFileName(imgPath, false), imgPath);
    			}
    		} 
    		else {	//지문
    			var arrMaskPath = item.etc5.split("|");
    			if(arrMaskPath.length != 0) {
        			for(i=0; i<arrMaskPath.length-1; i++)
        			{
        				axAllViewCtlC1.AddThumbnailFile(arrMaskPath[i], objCommUtil.GetFileName(arrMaskPath[i], false) , arrMaskPath[i]);
        			}
    			}
    		}
    	});
    	
    	axAllViewCtlC1.ReDrawThumbnails();
    	axAllViewCtlC1.RedrawMainView();
    };
    
    
    /**
     *  Function :  뷰어 이벤트 초기화 
     */	    
    function objectEventInit()
    {
    	if(window.attachEvent) {
        	axAllViewCtlC1.attachEvent('OnThumbnailMouseEvent', function(thumbnailType, buttonType, thumbnailIndex, x, y){return MImgView_OnThumbnailMouseEvent(thumbnailType, buttonType, thumbnailIndex, x, y); });
        	axAllViewCtlC1.attachEvent('OnSelectedThumbnail', function(index){return MImgView_OnSelectedThumbnail(index); });    		
    	}    	
    	else if(window.addEventListener) {	//ie11 이벤트 처리안됨
        	axAllViewCtlC1.addEventListener('OnThumbnailMouseEvent', function(thumbnailType, buttonType, thumbnailIndex, x, y){return MImgView_OnThumbnailMouseEvent(thumbnailType, buttonType, thumbnailIndex, x, y); }, false);
        	axAllViewCtlC1.addEventListener('OnSelectedThumbnail', function(index){return MImgView_OnSelectedThumbnail(index); }, false);
    	}
    };
    
    /**
     *  Function :  뷰어 마우스 이벤트 
     */	    
    function MImgView_OnThumbnailMouseEvent(thumbnailType, buttonType, thumbnailIndex, x, y){
    	//console.log("MImgView_OnThumbnailMouseEvent 호출!");

    	//마우스 더블클릭 시
    	if(buttonType == 2) {
    		setGridSelectionByThumbnail();
    		
    		var arrSelRowid = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
    		    		
    		if(arrSelRowid.length > 0) {
            	var sMask = $("#jqGrid").jqGrid('getRowData',arrSelRowid[0]).etc4;
            	var sThumbnail = $("#jqGrid").jqGrid('getRowData',arrSelRowid[0]).etc5;
            	var sOrigin = $("#jqGrid").jqGrid('getRowData',arrSelRowid[0]).imgPath;
            	var sEID = $("#jqGrid").jqGrid('getRowData',arrSelRowid[0]).elementid;
            	var sIDXID = $("#jqGrid").jqGrid('getRowData',arrSelRowid[0]).idxId;
            	var sJobID = $("#jqGrid").jqGrid('getRowData',arrSelRowid[0]).bprBsnDsc;
            	
            	pSubLoadImgView(sOrigin, sMask, sIDXID, sEID, sJobID, sThumbnail);
    		}    		
    	}
    };
    
    /**
     *  Function :  뷰어 썸네일선택 이벤트 
     */	    
    function MImgView_OnSelectedThumbnail(index){
    	//console.log("MImgView_OnSelectedThumbnail 호출!");    	
    	setGridSelectionByThumbnail();
    };
    
    /**
     *  그리드에서 선택된 썸네일이미지 파일명과 일치하는 행을 찾아 선택
     */	     
    function setGridSelectionByThumbnail() {
    	var splitThu, arrSplitIdx, selIdx, sFile, arrFile, gridRowCnt, gridImgPath;
    	
    	splitThu = axAllViewCtlC1.GetSelectedThumbnailIndex;
    	arrSplitidx = splitThu.split("^^");
    	selIdx = parseInt(arrSplitidx[0]);
    	
    	$("#jqGrid").jqGrid('resetSelection');
    	//$("#jqGrid").jqGrid("setSelection", selIdx+1);    	
    	
    	sFile = axAllViewCtlC1.GetSelectedThumbnailFileName.replace(/^^/gi,"");          // 마스킹 대상 이미지 파일 가져오기
    	arrFile = objCommUtil.GetFileName(sFile, false).split("+");
    	
    	if(arrFile.length > 0) {
    		sFile = arrFile[0];
    		gridRowCnt = $("#jqGrid").getGridParam("reccount");
    		for(i=1; i<=gridRowCnt; i++) {
    			gridImgPath = $("#jqGrid").jqGrid('getRowData',i).imgPath;     			
    			if(!modComm.isEmpty(gridImgPath) && gridImgPath.indexOf(sFile) != -1) {
    				$("#jqGrid").jqGrid("setSelection", i);
    			}
    		}
    	}    	
    }    
    
    /**
     *  Function :  썸네일 사이즈 변경적용
     */	    
    function setThumbnailSize()
    {
    	axAllViewCtlC1.setThumbnailsViewFrameSize = $("#selThumbnailSize").val();
    	axAllViewCtlC1.RedrawMainView();
    };    
	
	return {
		init: init,
		selList: selList,
		excelWrite: excelWrite,		
		objectInit: objectInit,		
		objectEventInit:objectEventInit,
		setThumbnailSize: setThumbnailSize		
	};

})();

/**
 * 조회버튼 클릭
 */
$("#btnSearch").on("click", function() {
	//console.log("조회버튼 클릭");
	modDpm1010.selList();
});

/**
 * 엑셀버튼 클릭
 */
$("#btnExcel").on("click", function() {
	//console.log("엑셀버튼 클릭");
	modDpm1010.excelWrite();
});


/**
 * 썸네일크기 변경 시 
 */
$("#selThumbnailSize").on("change", function() {
	modDpm1010.setThumbnailSize($("#selThumbnailSize").val());
});



/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {
	modDpm1010.init();
	modDpm1010.objectInit();
	modDpm1010.objectEventInit();
	
});
