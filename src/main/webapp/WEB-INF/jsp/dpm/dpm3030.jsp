<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  /**
  * @File Name : dpm3030.jsp
  * @Description : 그룹관리
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

        <div class="leftframe">
            <div class="data-container">
                <div class="subtitle2-container">
                    <img src="../images/icon-arrow-point.png" alt="이력조회" > <span class="subtitle2">그룹 목록</span>
                </div>
                
                <!-- grid start-->
                <div id="gridContainer">
                   <table id="jqGrid"></table>
                </div>
                <!-- grid end -->
                    
                <br style="line-height:10px;"/>
                <div style="float:right;">
                    <button id="btnAuthInit" type="button" class="btn btn-secondary btn-white" style="width:100px;">초기화</button>
                    <button id="btnAuthSave" type="button" class="btn btn-secondary btn-white" style="width:100px;">저장</button>
                </div>

                <br style="line-height:22px;"/><br style="line-height:22px;"/>
                <div class="button-tab">
                    <div class="tab-content" id="pills-tabContent">
                        <div class="tab-pane fade show active" id="TAB01" role="tabpanel" aria-labelledby="TAB01">
                            <div class="form-list-container">
                              <table class="table table-bordered">
                                <thead>
                                  <tr>
                                    <th width="40%">그룹ID</th>
                                    <th width="60%">그룹명</th>
                                    </tr>
                                </thead>
                                <tbody>
                                  <tr>
                                    <td class="text-center"><input id="txtGrpAuthGrpId"  type="text" class="form-control float-left"  maxlength="3"></td>
                                    <td class="text-center"><input id="txtGrpAuthGrpNm"  type="text" class="form-control float-left"  maxlength="50" ></td>
                                   </tr>
                                   </tbody>
                              </table>                              
                            </div>
                        </div>
                    </div>
                </div>                    
            </div>                
        </div>

        <div class="rightframe">
            <div class="data-container">
                <div class="subtitle2-container">
                    <img src="../images/icon-arrow-point.png" alt="이력조회" ><span class="subtitle2">사용자 목록</span>
                </div>
                    
                <!-- grid start-->
                <div id="gridContainerDetail">
                   <table id="jqGridDetail"></table>             
                </div>
                <!-- grid end -->
                
                <br style="line-height:10px;"/>
                <div style="float:right;">
                    <button id="btnChrrAuthInit" type="button" class="btn btn-secondary btn-white" style="width:100px;">초기화</button>
                    <button id="btnChrrAuthSave" type="button" class="btn btn-secondary btn-white" style="width:100px;">저장</button>
                    <button id="btnChrrAuthDel" type="button" class="btn btn-secondary btn-white" style="width:100px;">삭제</button>
                </div>
                    
                <br style="line-height:22px;"/><br style="line-height:22px;"/>
                <div class="button-tab">
                    <div class="tab-content" id="pills-tabContent">
                        <div class="tab-pane fade show active" id="TAB01" role="tabpanel" aria-labelledby="TAB01">
                            <div class="form-list-container">
                              <table class="table table-bordered">
                                <thead>
                                  <tr>
                                    <th width="">그룹ID</th>
                                    <th width="40%">담당자</th>
                                    <th width="">사용여부</th>
                                    </tr>
                                </thead>
                                <tbody>
                                  <tr>
                                    <td class="text-center"><input id="txtChrrAuthGrpId"  type="text" class="form-control float-left" maxlength="3"></td>
                                    <td class="text-center">
                                        <select id="selChrrAuthChrrId" class="form-control float-left">
                                        </select>                                    
                                    </td>
                                    <td class="text-center">
                                        <select id="selChrrAuthUseYn" class="form-control float-left">
                                            <option value="Y">사용</option>
                                            <option value="N">미사용</option>                                
                                        </select>
                                    </td>
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
      
     <!-- content페이지 end-->            
            
    <script type="text/javascript" src="/js/dpm/dpm3030.js"></script>
</body>
</html>