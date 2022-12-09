<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"> 
</head>
<body>
 <form id="frmMonthPro" role="form"  method="post"> 
                        <div class="smbc-top-search-wrap">
                            <ul class="smbc-top-search">
                                <li>
                                    <label>생성일자</label>
                                    <div class="datepicker-wrap">
										<input id="txtStartDt" name="startDt" type="text" class="form-control "
									     style="width: 130px; margin-right: 0px; padding-right: 30px;"
										maxlength="10"> 
										<span class="icon-calendar">
										<img id="imgStartDt" img src="../images/icon-calendar.png" alt="달력">
										</span>
                                    </div>
                                </li>
                            </ul>
                            <ul class="smbc-top-btn-wrap">
                                <li class="search-btn"><button>조회</button></li>
                            </ul>
                        </div>
                        <div class="smbc-data-wrap">
                            <div class="smbc-data-title">
                                <h3>월별 통계</h3>
                                <ul class="smbc-data-top-menu">
                                    <li><button>엑섹다운로드</button></li>
                                </ul>
                            </div>
                            <div class="smbc-data-con-wrap">
                             <div id="gridContainer">
								<table id="jqGrid"></table>
								<div id="jqGridPager"></div>
							</div>
                            </div>
                        </div>
                          <!-- 엑셀출력을 위한 컬럼정보 -->
						<input id="gridLabelList" type="hidden" name="gridLabels"> 
						<input id="gridNameList"  type="hidden" name="gridNames"> 
						<input id="gridWidthList" type="hidden" name="gridWidths"> 
						<input id="gridAlignList" type="hidden" name="gridAligns">
   </form>
    <script type="text/javascript" src="/js/dpm/dpmMonthPro.js"></script>
</body>
</html>