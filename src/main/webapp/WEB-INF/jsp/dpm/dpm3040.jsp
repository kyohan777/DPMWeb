<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  /**
  * @File Name : dpm3040.jsp
  * @Description : 권한관리
  * @Modification Information
  * 
  *   수정일             수정자                   수정내용
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
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>지문정보 마스킹처리 공정관리 시스템</title>
    
</head>
<body>
    <!-- content페이지 start-->
    <div class="info-container">
        <div class="data-container">
            <div class="subtitle2-container">
                <img src="../images/icon-arrow-point.png" alt="이력조회" > <span class="subtitle2">권한 관리</span>
            </div>

            <form id="frm3040" role="form"  method="post">
                <div class="search-box">
                    <div class="guide-text float-left">그룹</div>
                        <select id="searchSelauthGrpId" name="authGrpId" class="form-control float-left" style="width:200px; margin-right: 5px;">
                        </select>
                        <div class="guide-text float-left">메뉴</div>
                        <select id="searchSelmeneId" name="upMenuSqno" class="form-control float-left" style="width:200px; margin-right: 5px;">
                        </select>              
    
                        <button id="btnSearch" type="button" class="btn btn-secondary btn-bg" style="width:100px;">
                        <i class="fas fa-search"></i>&nbsp;&nbsp; 조회</button>
                </div>
            </form>
                    
            <div class="subtitle2-container clearfix">
                <div class="float-right" style="margin-top:5px">총 <span id="spnTotCnt">0</span>개</div>
            </div>
                          
            <!-- grid start-->
            <div id="gridContainer">
                <table id="jqGrid"></table>
            </div>

                    
        </div>
                
    </div>
    <!-- content페이지 end-->
            
    <script type="text/javascript" src="/js/dpm/dpm3040.js"></script>
</body>
</html>