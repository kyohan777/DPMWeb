<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	/**
	* @File Name : dpm1010.jsp
	* @Description : 이미지검증
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
	*  ------------------------------------------------*
	*/
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=10">
<title>지문정보 마스킹처리 공정관리 시스템</title>
<script type="text/javascript" src="/js/dpm/dpm1010.js"></script>

<!-- <script type="text/javascript">
	function MImgView_OnThumbnailMouseEvent(thumbnailType, buttonType,
			thumbnailIndex, x, y) {
		//console.log("MImgView_OnThumbnailMouseEvent 호출!");

		//마우스 더블클릭 시
		if (buttonType == 2) {
			setGridSelectionByThumbnail();

			var arrSelRowid = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');

			if (arrSelRowid.length > 0) {
				var sMask = $("#jqGrid").jqGrid('getRowData', arrSelRowid[0]).etc4;
				var sThumbnail = $("#jqGrid").jqGrid('getRowData',
						arrSelRowid[0]).etc5;
				var sOrigin = $("#jqGrid").jqGrid('getRowData', arrSelRowid[0]).imgPath;
				var sEID = $("#jqGrid").jqGrid('getRowData', arrSelRowid[0]).elementid;
				var sIDXID = $("#jqGrid").jqGrid('getRowData', arrSelRowid[0]).idxId;
				var sJobID = $("#jqGrid").jqGrid('getRowData', arrSelRowid[0]).edmsJobCfcd;

				pSubLoadImgView(sOrigin, sMask, sIDXID, sEID, sJobID,
						sThumbnail);
			}
		}
	};
	function MImgView_OnSelectedThumbnail(index) {
		console.log("MImgView_OnSelectedThumbnail 호출!");
		setGridSelectionByThumbnail();
	};
</script>
<SCRIPT language='javascript' for="MImgView"
	event="axAllViewCtlC1_OnThumbnailMouseEvent(sender, e)">
	alert("WA!");
	//MImgView_OnThumbnailMouseEvent(e.thumbnailType, e.buttonType,e.thumbnailIndex,e.x, e.y);
</SCRIPT>
<SCRIPT language='javascript' for="MImgView"
	event="axAllViewCtlC1_OnSelectedThumbnail(sender, e)">
alert("WA!");
	//MImgView_OnSelectedThumbnail(e.index);
</SCRIPT> -->

</head>
<body>

	<!-- content페이지 start-->
	<div class="info-container">
		<div class="data-container">
			<div class="subtitle2-container">
				<img src="../images/icon-arrow-point.png" alt="이력조회"> <span
					class="subtitle2">이미지 검증현황</span>
			</div>
			<form id="frm1010" role="form" method="post">
				<div class="search-box">
					<div class="guide-text float-left">처리일자</div>
					<div class="float-left">
						<div class="form group">
							<div class="input-group date">
								<input id="txtStartDt" name="startDt" type="text"
									class="form-control "
									style="width: 130px; margin-right: 0px; padding-right: 30px;"
									maxlength="10"> <span class="icon-calendar"><img
									id="imgStartDt" img src="../images/icon-calendar.png" alt="달력"></span>
							</div>
						</div>
					</div>
					<div class="guide-text float-left" style="margin: 5px 3px;">~</div>
					<div class="float-left">
						<div class="form group">
							<div class="input-group date">
								<input id="txtEndDt" name="endDt" type="text"
									class="form-control "
									style="width: 130px; margin-right: 5px; padding-right: 30px;"
									maxlength="10"> <span class="icon-calendar"><img
									id="imgEndDt" img src="../images/icon-calendar.png" alt="달력"></span>
							</div>
						</div>
					</div>
					<div class="guide-text float-left">업무구분</div>
					<select id="selBizCd" name="bprBsnDsc"
						class="form-control float-left"
						style="width: 100px; margin-right: 5px;">
					</select>
					<!-- 
                <div class="guide-text float-left">검증여부</div>
                <select name="" id="" class="form-control float-left" style="width:80px; margin-right: 5px;">
                </select>
                -->
					<!-- <div class="guide-text float-left">지문SCORE</div>
					<select id="selFpScore" name="fpScore"
						class="form-control float-left"
						style="width: 80px; margin-right: 5px;">
					</select>
					<div class="guide-text float-left">폼코드</div>
					<select id="selImgStylId" name="imgStylId"
						class="form-control float-left"
						style="width: 100px; margin-right: 5px;">
					</select> -->
					<div class="guide-text float-left">종류</div>
					<select id="selCategory" class="form-control float-left"
						style="width: 100px; margin-right: 5px;">
						<option value="1" selected="selected">지문</option>
						<option value="5">이미지</option>
					</select>
					<!--                         
                <div class="guide-text float-left">개인정보존재여부</div>
                <select id="" name="" class="form-control float-left" style="width:80px; margin-right: 5px;">
                </select>
                -->
					<div class="guide-text float-left">지문존재여부</div>
					<select id="selFpExistYn" name="fpExistYn"
						class="form-control float-left"
						style="width: 80px; margin-right: 5px;">
						<option value="9" selected="selected">전체</option>
						<option value="1">유</option>
						<option value="0">무</option>
					</select>
					<button id="btnSearch" type="button"
						class="btn btn-secondary btn-bg" style="width: 100px;">
						<i class="fas fa-search"></i>&nbsp;&nbsp; 조회
					</button>
					<button id="btnExcel" type="button"
						class="btn btn-secondary btn-white" style="width: 100px;">Excel</button>
				</div>

				<!-- 엑셀출력을 위한 컬럼정보 -->
				<input id="gridLabelList" type="hidden" name="gridLabels"> <input
					id="gridNameList" type="hidden" name="gridNames"> <input
					id="gridWidthList" type="hidden" name="gridWidths"> <input
					id="gridAlignList" type="hidden" name="gridAligns">
			</form>

			<!-- 팝업에서 참조할  파라미터 -->
			<input type="hidden" id="txt1010PopupOrigin" name="sOriginParam">
			<input type="hidden" id="txt1010PopupMask" name="sMaskParam">
			<input type="hidden" id="txt1010PopupIDXID" name="sIDXIDParam">
			<input type="hidden" id="txt1010PopupEID" name="sEIDParam"> <input
				type="hidden" id="txt1010PopupJobID" name="sJobIDParam"> <input
				type="hidden" id="txt1010PopupThumbnail" name="sThumbnailParam">

			<div class="leftframe_02" style="width: 1000px;">
				<div class="data-container">
					<div class="subtitle2-container">
						<!-- 
                        <img src="../images/icon-arrow-point.png" alt="이력조회" > <span class="subtitle2">이미지 검증현황</span>
                        -->

						<SPAN class="subtitle" style="margin-left: 5px; font-size: 14px">※
							원본 이미지 체크 선택하고 더블클릭하면 원본 이미지가 조회됩니다.</SPAN>
						<button id="btnVerity" type="button"
							class="btn btn-secondary btn-white"
							style="width: 100px; margin-left: 340px;">검증완료</button>
						<!-- 
                        <button class="btn btn-secondary btn-white" style="width:100px;margin-left:10px;">Excel</button>
                         -->
						<span class="checkbox"> <!-- 
                            <label style="font-size: 1.2em;vertical-align:middle">
                                <input type="checkbox" value="" checked >
                                <span class="cr"><i class="cr-icon fa fa-check"></i></span>
                            </label>
                             --> &nbsp; <input id="chkMaskYn"
							type="checkbox"
							style="vertical-align: middle; width: 15px; height: 15px">
							<SPAN class="subtitle" style="font-size: 12px">원본이미지</SPAN>
						</span>
					</div>
					<!-- 
                    <div>※ 원본 이미지 체크 선책하고 더블클릭하면 원본 이미지가 조회됩니다.</div>
                     -->
					<div id="gridContainer">
						<table id="jqGrid"></table>
						<div id="jqGridPager"></div>
					</div>
				</div>
			</div>

			<div class="rightframe_02"
				style="min-height: 470px; left: 1050px; min-width: 400PX; width: 35%">
				<div class="data-container">
					<div class="subtitle2-container" style="margin-left: -20px;">
						<select id="selThumbnailSize" class="form-control float-right"
							style="width: 100px; margin-right: 20px;">
							<option value=80>작게</option>
							<option value=120 selected="selected">보통</option>
							<option value=200>크게</option>
						</select>
						<div class="guide-text float-right subtitle"
							style="margin-top: 7px; margin-right: 25px; font-size: 12px">썸네일크기</div>
						<br>
					</div>

					<div style="margin-top: 24px;">
						<table width="100%" height="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td valign="top" id="tdAllView">
									<div id="divAllView">
										<OBJECT ID="MImgView" WIDTH="99%" HEIGHT="420px"
											CLASSID="CLSID:79AE1E4A-D911-487B-A9FC-9771D12D6F5F"></OBJECT>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<div id="divFileManager"></div>
					<div id="divCommUtil"></div>
					<!-- <object id="MagicLock" classid="CLSID:0F278CBA-923C-40D6-82DB-B95F5D820271"></object> -->


					<!-- 
                    <div class="imageviewbox" style="margin-top:10px;">
                        <ul>
                            <li><img src="../images/fingerprint_thum.png" /><br>201902260929350M</li>
                            <li><img src="../images/fingerprint_thum.png" /><br>201902260929350M<</li>
                        </ul>
                    
                    </div>
                     -->
				</div>
			</div>
		</div>
	</div>
	<!-- content페이지 end-->







</body>
</html>
