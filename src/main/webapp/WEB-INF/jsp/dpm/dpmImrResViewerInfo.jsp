<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SMBC 전자문서 관리시스템</title>
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
                        <form id="frmImrResViewerInfo" role="form"  method="post"> 
                            <ul class="smbc-top-search">
                             	<li>
                                    <input type="text" class="input-st01" id="custId" name="custId" style ="background: #E3FFF0" placeholder="고객번호">
                                </li>
                                <li>
                                    <div class="datepicker-wrap">
										<input id="startPrcDt" name="textPrcDt" type="text" class="form-control "
									     style="width: 130px; margin-right: 0px; padding-right: 30px;"
										maxlength="10" placeholder="시작일"> 
										<span class="icon-calendar">
										<img id="imgStartDt" img src="../images/icon-calendar.png" alt="달력">
										</span>
                                    </div>
                                </li>
                                 <li>
                                    <div class="datepicker-wrap">
										<input id="endtPrcDt" name="textPrcDt" type="text" class="form-control "
									     style="width: 130px; margin-right: 0px; padding-right: 30px;"
										maxlength="10" placeholder="종료일"> 
										<span class="icon-calendar">
										<img id="imgEndtDt" img src="../images/icon-calendar.png" alt="달력">
										</span>
                                    </div>
                                </li>
                                
                                <li>
                        			<label>접속자 정보</label>
                        			<input type="text" value="사번 : ${companyId}" disabled style ="color:white">
                        			<input type="text" value="성명 : ${chrrNm}" disabled style ="color:white"> 
                    			</li>
                            </ul>
   					</form>
                            <ul class="smbc-top-btn-wrap">
                                <li class="search-btn"><button onclick="modDpmDayPro.selList();">조회</button></li>
                            </ul>
                        </div>
                        <div class="smbc-data-wrap">
                            <div class="smbc-data-title">
                                <h3>IMR 결과 열람자 이력 조회</h3>
                                <!-- <ul class="smbc-data-top-menu">
                                    <li><button>엑섹다운로드</button></li>
                                </ul> -->
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
    <script type="text/javascript" src="/js/dpm/dpmImrResViewerInfo.js"></script>
</body>
</html>