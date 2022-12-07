<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  /**
  * @File Name : dpm2040.jsp
  * @Description : 월별현황
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
                <img src="../images/icon-arrow-point.png" alt="이력조회" > <span class="subtitle2">월별 처리현황</span>
            </div>
            <form id="frm2040" role="form"  method="post">            
                <div class="search-box">
                        
                <div class="guide-text float-left">처리일자</div>
                    <div class="float-left">
                    <div class="form group">
                        <div class="input-group date">
                            <input id="txtStartMm" name="startBasMm" type="text" class="form-control "  style="width:130px; margin-right: 0px; padding-right:30px;" maxlength="10" >                     
                            <span class="icon-calendar"><img id="imgStartDt" src="../images/icon-calendar.png" alt="달력"></span>                     
                        </div>
                    </div>
                    </div>
                    <div class="guide-text float-left" style="margin:5px 3px;">~</div>
                    <div class="float-left">
                    <div class="form group">
                        <div class="input-group date">
                            <input id="txtEndMm" name="endBasMm" type="text" class="form-control " style="width:130px; margin-right: 5px; padding-right:30px;" maxlength="10" >
                            <span class="icon-calendar"><img id="imgEndDt" src="../images/icon-calendar.png" alt="달력"></span>
                        </div>
                    </div>
                    </div>
                    <button id="btnSearch" type="button" class="btn btn-secondary btn-bg" style="width:100px;">
                        <i class="fas fa-search"></i>&nbsp;&nbsp; 조회</button>
                    <button id="btnExcel" type="button" class="btn btn-secondary btn-white" style="width:100px;">EXCEL</button>
                
                </div>
                    
            <!-- 엑셀출력을 위한 컬럼정보 -->
            <input id="gridLabelList" type="hidden" name="gridLabels" >
            <input id="gridNameList" type="hidden" name="gridNames" >
            <input id="gridWidthList" type="hidden" name="gridWidths" >
            <input id="gridAlignList" type="hidden" name="gridAligns" >
            </form>

            <div class="subtitle2-container clearfix">
                <div class="float-right" style="margin-top:5px"><span id="spnTotCnt">0</span>개</div>
            </div>
            
            <!-- grid start-->
            <div id="gridContainer">
                <table id="jqGrid"></table>
            </div>
            <!-- grid end -->
        </div>
                
    </div>
     <!-- content페이지 end-->

    <script type="text/javascript" src="/js/dpm/dpm2040.js"></script>
  
</body>
</html>