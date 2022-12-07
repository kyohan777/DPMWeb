<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  /**
  * @File Name : dpm1020.jsp
  * @Description : AGENT 할당
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
                <img src="../images/icon-arrow-point.png" alt="이력조회" > <span class="subtitle2">AGENT별 할당현황</span>
            </div>
            <form id="frm1020" role="form"  method="post">       
            <div class="search-box">                       
                <div class="guide-text float-left">할당서버</div>
                   <!--  <select id="selSvrnm" name="maskSvrnm"  class="form-control float-left" style="width:200px; margin-right: 5px;">
                    </select> -->
                    <!-- <div class="guide-text float-left">AGENT 수</div>
                        <select id="selAgentCn" name="agentCn"  class="form-control float-left" style="width:200px; margin-right: 5px;">
                        </select>    -->           
                    <!-- <div class="guide-text float-left">업무구분</div>
                    <select id="selBizCd" name="bprBsnDsc" class="form-control float-left" style="width:200px; margin-right: 5px;">
                    </select> -->
                    <button id="btnSearch" type="button" class="btn btn-secondary btn-bg" style="width:100px;">
                        <i class="fas fa-search"></i>&nbsp;&nbsp; 조회</button>
                    <button id="btnAssign" type="button" class="btn btn-secondary btn-white" style="width:100px;">할당</button>                   
            </div>
            </form>
            <div class="subtitle2-container clearfix">
                <div class="float-right" style="margin-top:5px">총 <span id="spnTotCnt">0</span>개</div>
            </div>
            <!-- grid start-->
            <div id="gridContainer">
                <table id="jqGrid"></table>             
            </div>
            <!-- grid end -->
            <br style="line-height:10px;"/>
            <div style="float:right;">
                <button id="btnChrrInit" type="button" class="btn btn-secondary btn-white" style="width:100px;">초기화</button>
                <button id="btnChrrSave" type="button" class="btn btn-secondary btn-white" style="width:100px;">저장</button>
                <button id="btnChrrDelete" type="button" class="btn btn-secondary btn-white" style="width:100px;">삭제</button>
            </div>
                
            <br style="line-height:22px;"/><br style="line-height:22px;"/>
            <div class="button-tab">
                <div class="tab-content" id="pills-tabContent">
                    <div class="tab-pane fade show active" id="TAB01" role="tabpanel" aria-labelledby="TAB01">
                        <div class="form-list-container">
                          <table class="table table-bordered">
                            <thead>
                              <tr>
                                <th width="">서버번호</th>
                                <th width="">재처리횟수</th>
                                <th width="">조회건수</th>
                                <th width="">에이전트할당갯수</th>
                                </tr>
                            </thead>
                            <tbody>
                              <tr>
                                <td class="text-center"><input id="txtServerNo"  type="text" class="form-control float-left" maxlength="1"></td>
                                <td class="text-center"><input id="txtReprocCnt"  type="text" class="form-control float-left" maxlength="100"></td>
                                <td class="text-center"><input id="txtRowCnt"  type="text" class="form-control float-left" maxlength="30"></td>  
                                <td class="text-center"><input id="txtAgentCnt"  type="text" class="form-control float-left" maxlength="30"></td>                               
                               </tr>
                               </tbody>
                          </table>
                          
                        </div>
                    </div>
                </div>
            </div>
        </div>
                
    </div>
     <!-- content페이지 end-->                       
    <script type="text/javascript" src="/js/dpm/dpm1020.js"></script>
</body>
</html>