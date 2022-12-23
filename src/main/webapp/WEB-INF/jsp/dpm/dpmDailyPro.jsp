<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  /**
  * @File Name : dpmDailyPro.jsp
  * @Description : 일일 처리 현황
  * @Modification Information
  * 
  *   수정일             수정자                   수정내용
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
%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"> 
    <title>지문정보 마스킹처리 공정관리 시스템</title>
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
										maxlength="10" autocomplete="off"> 
										<span class="icon-calendar">
										<img id="imgStartDt" img src="../images/icon-calendar.png" alt="달력">
										</span>
                                    </div>
                                </li>
                            </ul>
                            <!-- 엑셀출력을 위한 컬럼정보 -->
                            <input id="prcDt" type="hidden" name="prcDt" value="${prcDt}">
							<input id="gridLabelList" type="hidden" name="gridLabels"> 
							<input id="gridNameList"  type="hidden" name="gridNames"> 
							<input id="gridWidthList" type="hidden" name="gridWidths"> 
							<input id="gridAlignList" type="hidden" name="gridAligns">
   						</form>
                            <ul class="smbc-top-btn-wrap">
                                <li class="search-btn"><button id="searchBtn">조회</button></li>
                            </ul>
                        </div>
                        <div class="smbc-data-wrap">
                            <div class="smbc-data-title">
                                <h3>일일 처리 현황</h3>
                                 <ul class="smbc-data-top-menu">
                                    <li><button id="btnExcel">엑셀다운로드</button></li>
                                    <c:if test="${chrrId == 'admin'}"> <li><button id="btnBatch">일일 배치</button></li></c:if>
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
    <jsp:include page="/WEB-INF/jsp/include/script.jsp" />    
    <script type="text/javascript" src="/js/dpm/dpmDailyPro.js"></script>
   
</body>
</html>