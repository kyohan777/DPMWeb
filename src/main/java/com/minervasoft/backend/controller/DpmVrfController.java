package com.minervasoft.backend.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.minervasoft.backend.service.DpmService;
import com.minervasoft.backend.vo.CalibVerifiVo;
import com.minervasoft.backend.vo.ImageVerifyVO;
import com.minervasoft.backend.vo.LoginChrrVO;
import com.minervasoft.backend.vo.ResponseStatisticsVo;
import com.minervasoft.backend.vo.StatisticsVO;

import kr.smartflow.viewer.Converter;

//import SafeSignOn.SSO;
//import SafeSignOn.SsoAuthInfo;

@Controller
public class DpmVrfController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource(name = "DpmService")
    private DpmService dpmService; 
    
    
    /**
     *  [IMR] 교정/검증 화면
     *  2022.12.15 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmCalibVerifiInfo.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmCalibVerifiInfo(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
            List<StatisticsVO> list = dpmService.getDpmDailyProInfo(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<StatisticsVO>());
        }
        
        return response;
    }
    
    /**
     *  [IMR] 교정/검증 화면
     *  2022.12.15 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmImrResultInfo.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmImrResultInfo(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
            List<StatisticsVO> list = dpmService.getDpmDailyProInfo(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<StatisticsVO>());
        }
        
        return response;
    }
    
    
    /**
     *  [IMR] 결과 조회 사유 입력
     * @param paramVO
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/dpm/insertViewReason.do")
    @ResponseBody
    public JSONObject insertViewReason(CalibVerifiVo paramVO, HttpServletRequest request) {
    	
    	int updCnt = 0;
    	JSONObject returnObj = new JSONObject();
    	
    	HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
        paramVO.setChrrId(loginVO.getChrrId());
    	try {
    		updCnt = dpmService.insertViewReason(paramVO);
    		returnObj.put("errMsg", "success");
		} catch (Exception e) {
			logger.error("", e);
			returnObj.put("errMsg", e.getMessage());
			updCnt = 0;
		}
    	returnObj.put("updCnt", updCnt);
    	
    	return returnObj;
    }
    

    
    /**
     *  [IMR] 교정/검증 확정 처리
     * @param paramVO
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/dpm/imrConfirm.do")
    @ResponseBody
    public String imrConfirm(StatisticsVO paramVO, HttpServletRequest request) {
    	
    	int updCnt = 0;
    	JSONObject returnObj = new JSONObject();
    	try {
	        HttpSession session = request.getSession();
	        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
	        paramVO.setChgEno(loginVO.getChrrId());
	    	
	    	logger.info("getIntvisionImr ~~~:" + paramVO.getIntvisionImr());
	    	String strParam = paramVO.getIntvisionImr();
	    	
	    	JSONParser parser = new JSONParser();
	    	Object obj = parser.parse(strParam);
			
	    	JSONObject jsonObjRoot = (JSONObject) obj;
	    	String elementId  = (String)jsonObjRoot.get("elementId");
	    	paramVO.setElementId(elementId);
	    	
	    	String intvisionImr  = (String)jsonObjRoot.get("intvisionImr");
	    	jsonObjRoot.remove("elementId");
	    	jsonObjRoot.remove("intvisionImr");
	    	paramVO.setIntvisionImr(jsonObjRoot.toJSONString());
	    	
	    	obj = parser.parse(intvisionImr);
	    	JSONObject jsonObj = (JSONObject) obj;
	    	    	
	    	// 데이터 비교
	    	boolean eqYn = true;
	    	Iterator<String> keys = jsonObjRoot.keySet().iterator();
	    	Iterator<String> keysIv = jsonObj.keySet().iterator();
	    	while(keys.hasNext()) {
	    		if(eqYn == false) {
	    			break;
	    		}
	    	    String key = keys.next();
	    	    String val = (String)jsonObjRoot.get(key);
	    	    while(keysIv.hasNext()) {
	    	    	String key_sub = keysIv.next();
	        	    String val_sub = (String)jsonObj.get(key_sub);
	        	    if(key.equals(key_sub)) {
	        	    	if(val.equals(val_sub)) {
	        	    		eqYn = true;
	        	    	} else {
	        	    		eqYn = false;
	        	    	}
	        	    	break;
	        	    }
	    	    }
	    	}
	    	if(eqYn == true) {
	    		paramVO.setUserUpdateYn("02"); //변경없음
	    	} else {
	    		paramVO.setUserUpdateYn("01"); //수정
	    	}
	    	paramVO.setUserConfirm("99");
	    	
	    	returnObj.put("errMsg", "success");
    	} catch (Exception e) {
			logger.error("", e);
			returnObj.put("errMsg", e.getMessage());
		}
        
    	try {
    		updCnt = dpmService.updImrConfirm(paramVO);
    		returnObj.put("errMsg", "success");
		} catch (Exception e) {
			logger.error("", e);
			returnObj.put("errMsg", e.getMessage());
			updCnt = 0;
		}
    	returnObj.put("updCnt", updCnt);
    	
    	return returnObj.toJSONString();
    }
    
    
    
    
    @RequestMapping(value = "/showFile.do")
    public void showFile(HttpServletRequest request, HttpServletResponse response) {
        
        String filename = request.getParameter("filename");
    	
    	if (filename == null) {
    		System.err.println("usage example: /show_file.jsp?filename=/tif/jpeg/1.tif");
    		return;
    	} else if (filename.indexOf("..") >= 0) {
    		System.err.println("filename not allowed contains ..");
    		return;
    	}
    	
    	String baseFolder  = "D:/project_minerva/git/DPMWeb/src/main/webapp/WEB-INF/temp/";
    	
    	Converter.getImage(baseFolder + filename, response);
    }

    
     
}
