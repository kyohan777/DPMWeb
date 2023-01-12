<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"> 
    <title>전자문서관리 시스템</title>
    <link href="/css/layerPop.css" rel="stylesheet">
    <link href="/css/jquery.json-viewer.css" rel="stylesheet">
    <style type="text/css">
		p.options label {
  			margin-right: 10px;
		}
		p.options input[type=checkbox] {
  			vertical-align: middle;
		}
		textarea#json-input {
  			width: 100%;
  			height: 200px;
		}
		pre#json-renderer {
  			border: 1px solid #aaa;
  			overflow: scroll;
 			height: 500px;
		}
		.sect01 {
  			position: relative;
  			width: 30px; /* X 사이즈 */
  			height: 30px; /* X 세로 중앙 */
  			margin: 0 auto; /* 가운데 정렬 */
		}
		.sect01 .line-box {
  			position: absolute;
  			top: 50%;
  			left: 50%;
  			transform: translate(1045%, -50%);
  			width: 100%;
  			height: 100%;
		}
		.sect01 .line-box > span {
  			position: absolute;
  			top: 50%;
  			width: 100%;
  			height: 2px;
  			background-color: #000;
		}
		.sect01 .line-01 {
  			transform: rotate(135deg) translateX(0%);
		}
		.sect01 .line-02 {
  			transform: rotate(45deg) translateX(0%);
		}
    </style>
</head>
<body>
    <div class="smbc-wrap">
        <div class="smbc-main-wrap">
            <div class="smbc-main-box">
            	<!-- 메뉴바 영역 -->
                <header class="smbc-header">
                    <jsp:include page="header.jsp" />
                </header>
                <!-- 메뉴바 영역 -->
                <div class="smbc-content-wrap">
                   <div class="smbc-main-content-wrap" id= "contentPage">
                        <div class="smbc-top-search-wrap">
                        <form id="frmDailyPro" role="form"  method="post"> 
                            <ul class="smbc-top-search">
                             	 <li>
                                    <label>처리일자</label>
                                    <div class="datepicker-wrap">
										<input id="textPrcDt" name="textPrcDt" type="text" class="form-control "
									     style="width: 130px; margin-right: 0px; padding-right: 30px;"
										maxlength="10" autocomplete="off" onchange="modDpmDailyPro.selList();"> 
										<span class="icon-calendar">
										<img id="imgStartDt" img src="../images/icon-calendar.png" alt="달력">
										</span>
                                    </div>
                                </li>
                                <li>
                                    <label>상태코드</label>
                                    <div class="datepicker-wrap">
										<select id="maskPrgStsc" name="maskPrgStsc"class="form-control"
									     style="width: 130px; background: #E3FFF0;margin-right: 0px; padding-right: 30px;"
									     onchange="modDpmDailyPro.selList();">
									     	<option value="">전체</option>
									     	<option value="Y">인식됨</option>
									     	<option value="N">인식안됨</option>
									     	<option value="E">오류</option>
									     </select> 
                                    </div>
                                </li>
                            </ul>
                            <!-- 엑셀출력을 위한 컬럼정보 -->
                            <input id="prcDt" type="hidden" name="prcDt" value="${prcDt}">
                            <input id="chrrId" type="hidden" name="prcDt" value="${chrrId}">
							<input id="columnName" type="hidden" name="columnName">
							<input id="sortOrder" type="hidden" name="sortOrder">
							<input id="gridLabelList" type="hidden" name="gridLabels"> 
							<input id="gridNameList"  type="hidden" name="gridNames"> 
							<input id="gridWidthList" type="hidden" name="gridWidths"> 
							<input id="gridAlignList" type="hidden" name="gridAligns">
   						</form>
                            <!-- <ul class="smbc-top-btn-wrap">
                                <li class="search-btn"><button id="searchBtn">조회</button></li>
                            </ul> -->
                        </div>
                        <div class="smbc-data-wrap">
                            <div class="smbc-data-title">
                                <h3>일일 처리 현황</h3>
                                 <ul class="smbc-data-top-menu">
                                    <li><button id="btnExcel">엑셀다운로드</button></li>
                                    <c:if test="${chrrId == 'admin'}"> <li><button onclick="modDpmDailyPro.batchTotCheck();">일일 배치</button></li></c:if>
                                </ul>
                            </div>
                            <div class="smbc-data-con-wrap">
                             <div id="gridContainer">
								<table id="jqGrid"></table>
								<div id="jqGridPager"></div>
							</div>
                            </div>
                        </div>
					</div>
                </div>
            </div>
        </div>
    </div>
    <!-------------------- layerPopup------------------>
<div class="dim-layer">
    <div class="dimBg"></div>
    <div id="layer" class="pop-layer" style="width:700px">
        <div class="">
            <div class="pop-conts">
           		<div class="sect01">
  					<div class="line-box" id="close">
   						 <span class="line-01"></span>
    					<span class="line-02"></span>
  					</div>
				</div>
                <!--content //-->
                <div>
                <pre id="json-renderer"></pre>
                </div>
               <!--  <div class="btn-r">
                    <a href="#" class="btn-layerClose" id="close">닫기</a>
                </div> -->
                <!--// content-->
            </div>
        </div>
    </div>
</div>
<!--//----------------- layerPopup------------------>


    <script type="text/javascript" src="/js/libs/jquery.jqGrid.js"></script>
    <jsp:include page="/WEB-INF/jsp/include/script.jsp" />    
    <script type="text/javascript" src="/js/dpm/dpmDailyPro.js"></script>
    <script type="text/javascript" src="/js/dpm/jquery.json-viewer.js"></script>
    
   
</body>
</html>