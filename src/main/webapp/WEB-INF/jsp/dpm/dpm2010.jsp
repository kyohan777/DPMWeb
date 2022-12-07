<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  /**
  * @File Name : dpm2010.jsp
  * @Description : 업무별현황
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
                <form id="frm2010Yday" role="form"  method="post">                    
                <!-- 엑셀출력을 위한 컬럼정보 -->
                <input id="gridLabelListYday" type="hidden" name="gridLabels" >
                <input id="gridNameListYday" type="hidden" name="gridNames" >
                <input id="gridWidthListYday" type="hidden" name="gridWidths" >
                <input id="gridAlignListYday" type="hidden" name="gridAligns" >
                </form>
                <form id="frm2010Tday" role="form"  method="post">
                <input id="gridLabelListTday" type="hidden" name="gridLabels" >
                <input id="gridNameListTday" type="hidden" name="gridNames" >
                <input id="gridWidthListTday" type="hidden" name="gridWidths" >
                <input id="gridAlignListTday" type="hidden" name="gridAligns" >                
                </form>
                <div class="leftframe_01" style="width:800px">
                  <div class="data-container">                  
                    <div class="subtitle2-container">
                        <img src="../images/icon-arrow-point.png" alt="이력조회" > <span class="subtitle2">전일기준 업무별 현황</span>
                        <button id="btnExcelYday" type="button" class="btn btn-secondary btn-white" style="width:100px;margin-left:520px;">Excel</button>
                    </div>
                    
		            <!-- grid start-->
		            <div id="gridContainerYday">
		                <table id="jqGridYday"></table>
		            </div>
		            <!-- grid end -->
                </div>
                
            </div>


                <div class="rightframe_01" style="left:870px;width:640px;min-width:640px;">
                    <div class="data-container">
                    <div class="subtitle2-container">
                        <img src="../images/icon-arrow-point.png" alt="이력조회" ><span class="subtitle2">당일 업무별 진행현황</span> 
                         <button id="btnExcelTday" type="button" class="btn btn-secondary btn-white" style="width:100px;margin-left:350px;">Excel</button>
                    </div>
                    
                    <!-- grid start-->
                    <div id="gridContainerTday">
                        <table id="jqGridTday"></table>
                    </div>
                    <!-- grid end -->
                </div>
                
            </div>

      </div>
     <!-- content페이지 end-->
            
    <script type="text/javascript" src="/js/dpm/dpm2010.js"></script>
</body>
</html>