
/**
  * @File Name : firstView.js
  * @Description : 첫화면
  * @Modification Information
  * 
  *   수정일       수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2019.04.01             최초 생성
  *
  *  
  *
 */

var modFirstView = (function(){

	
	return {
		
	};

})();


/**
 * DOM  load 완료 시 실행
 */
$(document).ready(function() {	
	$("#divMenuPath").hide();
	$("#contentPage").load("/dpm/dpm1010.ui");
});