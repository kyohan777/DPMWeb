<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  /**
  * @File Name : login.jsp
  * @Description : 로그인화면
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
    <meta http-equiv="X-UA-Compatible" content="IE=11">     
    <title>로그인</title>    
</head>
<body>
    <div class="login-container">
    	<!-- <a href = "http://10.254.12.106:8080/DPMPackageSetup_KB.exe">공정관리 시스템 패키지 다운로드</a> -->
    	<a href = "/down/DPMPackageSetup_KB.exe">공정관리 시스템 패키지 다운로드</a>
    	<a href = "/down/NDP452-KB2901907-x86-x64-AllOS-ENU.exe">.Net Framework 다운로드</a>
        <div class="container">
            <div>
                <div class="login-box">
                    <img src="/images/login-box_1.png" alt="로그인영역">
                    <form id="frmLogin" role="form"  method="post" name="loginForm">
                        <div>
                            <input type="text"  name="chrrId" id="txtUserId" placeholder="아이디를 입력하세요." class="input-login style-none">
                        </div>
                        <div>
                            <input type="password" name="chrrPwd" id="txtPassword" placeholder="패스워드를 입력하세요" class="input-password style-none">
                        </div>                            
                        <div>
                            <button id="btnLogin" class="btn style-none login-button">로그인</button>
                        </div>
                        <br>
                        <div>
                            <input type="checkbox" id="ckbLastLogin" value="ckbLastLogin" style="position:absolute;top:262px;left:470px;width:20px;height:20px;border-radius:5px;padding: 20px;" >
                                <span class="subtitle" style="position:absolute;top:257px;left:490px;">ID저장</span>
                        </div>
                        <div>
                            <input id="loginResult" type="hidden" value="${loginResult}">
                        </div>
                  </form>
                </div>
            </div>
        </div>
        <div class="login-footer text-center">
            <div class="text-muted">COPYRIGHT(C) 2019 KB손해보험(주)  ALL RIGHTS RESERVED.</div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/jsp/include/script.jsp" />	
    <script type="text/javascript" src="/js/dpm/login.js"></script>    
</body>
</html>