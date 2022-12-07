<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  /**
  * @File Name : dpm3010.jsp
  * @Description : 코드관리
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
                    <img src="../images/icon-arrow-point.png" alt="이력조회" > <span class="subtitle2">분류코드</span>
                </div>
                
	            <!-- grid start-->
	            <div id="gridContainer">
	               <table id="jqGrid"></table>
	            </div>
	            <!-- grid end -->
                    
                <br style="line-height:10px;"/>
                <div style="float:right;">
	                <button id="btnCodeInit" type="button" class="btn btn-secondary btn-white" style="width:100px;">초기화</button>
	                <button id="btnCodeSave" type="button" class="btn btn-secondary btn-white" style="width:100px;">저장</button>
	                <button id="btnCodeDelete" type="button" class="btn btn-secondary btn-white" style="width:100px;">삭제</button>
                </div>

                <br style="line-height:22px;"/><br style="line-height:22px;"/>
                <div class="button-tab">
                    <div class="tab-content" id="pills-tabContent">
                        <div class="tab-pane fade show active" id="TAB01" role="tabpanel" aria-labelledby="TAB01">
                            <div class="form-list-container">
                              <table class="table table-bordered">
                                <thead>
                                  <tr>
                                    <th width="40%">코드</th>
                                    <th width="60%">코드분류명</th>
                                    </tr>
                                </thead>
                                <tbody>
                                  <tr>
                                    <td class="text-center"><input id="txtCode" type="text" class="form-control float-left"  maxlength="20"></td>
                                    <td class="text-center"><input id="txtCodeNm"  type="text" class="form-control float-left" maxlength="120" ></td>
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
	                <img src="../images/icon-arrow-point.png" alt="이력조회" ><span class="subtitle2">상세분류코드</span>
	            </div>
                    
                <!-- grid start-->
                <div id="gridContainerDetail">
                   <table id="jqGridDetail"></table>             
                </div>
                <!-- grid end -->
                
                <br style="line-height:10px;"/>
                <div style="float:right;">
	                <button id="btnDetailCodeInit" type="button" class="btn btn-secondary btn-white" style="width:100px;">초기화</button>
	                <button id="btnDetailCodeSave" type="button" class="btn btn-secondary btn-white" style="width:100px;">저장</button>
	                <button id="btnDetailCodeDelete" type="button" class="btn btn-secondary btn-white" style="width:100px;">삭제</button>
                </div>
                    
                <br style="line-height:22px;"/><br style="line-height:22px;"/>
                <div class="button-tab">
                    <div class="tab-content" id="pills-tabContent">
                        <div class="tab-pane fade show active" id="TAB01" role="tabpanel" aria-labelledby="TAB01">
                            <div class="form-list-container">
                              <table class="table table-bordered">
                                <thead>
                                  <tr>
                                    <th width="">상위코드</th>
                                    <th width="">코드</th>
                                    <th width="40%">코드명</th>
                                    <th width="">표시순서</th>
                                    <th width="">사용여부</th>
                                    </tr>
                                </thead>
                                <tbody>
                                  <tr>
                                    <td class="text-center"><input id="txtDetailUpCode"  type="text" class="form-control float-left" maxlength="20"></td>
                                    <td class="text-center"><input id="txtDetailCode"  type="text" class="form-control float-left" maxlength="20"></td>
                                    <td class="text-center"><input id="txtDetailCodeNm"  type="text" class="form-control float-left" maxlength="120"></td>
                                    <td class="text-center"><input id="txtScrnMrkSq" type="text" class="form-control float-left" maxlength="3" numberOnly></td>
                                    <td class="text-center">
                                        <select id="selCodeUseYn" class="form-control float-left">
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
    <script type="text/javascript" src="/js/dpm/dpm3010.js"></script>
</body>
</html>