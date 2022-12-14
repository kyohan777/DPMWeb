<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SMBC 전자문서 관리시스템</title>
    <style>
    #box-left {
 		 flex: 1;
	}
	#box-center {
  		flex: 1;
  		
	}
	#box-right {
		flex: 1;
	}
	#mkTable > tr{
	height : 50px
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
                        <form id="frmCalibVerifiInfo" role="form"  method="post"> 
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
                                <h3>교정/검증 처리</h3>
                                <ul class="smbc-data-top-menu">
                                    <li><button id="btnExcel">엑섹다운로드</button></li>
                                </ul>
                            </div>
                            <div class="smbc-data-wrap">
                            <div class="smbc-data-con-wrap" style="display: flex; height: calc(100%);">
                           		 <div id='box-left'>
                           			 <div id="gridContainer">
									<table id="jqGrid"></table>
									<div id="jqGridPager"></div>
									</div>
                            	</div>
                            	<div id='box-center'></div>
                            	<div id='box-right'>
                            	 <div class="smbc-data-con-wrap">
                                <div class="smbc-data-grid-wrap">
                                    <table>
                                        <colgroup>
                                            <col/>
                                            <col/>
                                            <col/>
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th style="text-align: center">구분</th>
                                                <th style="text-align: center">NO</th>
                                                <th style="text-align: center">YES</th>
                                            </tr>
                                        </thead>
                                        <tbody id="mkTable">
                                            <tr>
                                                <td>금융상품안내 및 이용권유를 위한 수집, 이용</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>금융상품 이외의 서비스 안내 및 이용권유를 위한 수집,이용</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>업무제휴계약을 체결한 제휴 보험사에 제공</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>업무제휴계약을 체결한 제휴 딜러사에 제공</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>KB국민은행 등 KB금융 그룹 내 자회사에 제공</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>[수집 마케팅] 전화 수신 동의</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>[수집 마케팅] 문자(SMS) 수신 동의</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>[수집 마케팅] DM 수신 동의 (우편을 DM으로 인식)</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>[수집 마케팅] 이메일 수신 동의</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>[제공 마케팅] 전화 제공 동의</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                             <tr>
                                                <td>[제공 마케팅] 이메일 제공 동의</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                            <tr>
                                                <td>[제공 마케팅] DM 제공 동의</td>
                                                <td><input type ="checkbox"></td>
                                                <td><input type ="checkbox"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            	</div>
                             
                            </div>
                        </div>
                        </div>
					</div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/include/script.jsp" />  
    <script type="text/javascript" src="/js/dpm/dpmCalibVerifiInfo.js"></script>
</body>
</html>