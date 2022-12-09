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
<html  style="height: 99%;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"> 
    <title>지문정보 마스킹처리 공정관리 시스템</title>
</head>
<body>
    <form id="frmMain" role="form"  method="post">
    <div class="system clearfix search-container">
        <div class="">
           <jsp:include page="header.jsp" />
            <div id="contentPage"">
                <!-- content페이지 start-->
				<div class="info-container" >
					<div class="data-container" >
						<div class="leftframe_02" style="width: 100%; height: 100%; min-height: 580px">
						<div class="data-container"  style="width: 100%; height: 100%;">
                     		<div id="gridContainer">
								<table id="jqGrid"></table>
								<div id="jqGridPager"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- content페이지 end-->

            </div>
            <!-- content페이지 end-->      
        </div>
    </div>
    
    </form>    
    <jsp:include page="/WEB-INF/jsp/include/script.jsp" />    
    <script type="text/javascript" src="/js/dpm/dpmDailyPro.js"></script>
   
</body>
</html>