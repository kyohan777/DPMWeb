package com.minervasoft.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.minervasoft.backend.service.CustomImage;
import com.minervasoft.backend.service.CustomTextType;
import com.minervasoft.backend.service.DpmService;
import com.minervasoft.backend.service.impl.MagicCryptoService;
import com.minervasoft.backend.service.impl.XvarmService;
import com.minervasoft.backend.vo.CalibVerifiVo;
import com.minervasoft.backend.vo.ImageVerifyVO;
import com.minervasoft.backend.vo.LoginChrrVO;
import com.minervasoft.backend.vo.ResponseStatisticsVo;
import com.minervasoft.backend.vo.StatisticsVO;

import kr.smartflow.viewer.Converter;

//import SafeSignOn.SSO;
//import SafeSignOn.SsoAuthInfo;

@Controller
@Configuration
public class DpmVrfController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource(name = "DpmService")
    private DpmService dpmService; 
    
	@Autowired
	MagicCryptoService  magicCryptoService;
	
	@Autowired
	XvarmService xvarmService;
    
    
    @Value("${minerva.baseFolder}")
    private String baseFolder;
    
    @Value("${minerva.encDownFolder}")
    private String encDownFolder;
        
    
    @Value("${minerva.noFileImg}")
    private String noFileImg;
    
    
    
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
            List<StatisticsVO> list = dpmService.getDpmCalibVerifiInfo(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            //response.setTotPageCnt(2);
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<StatisticsVO>());
        }
        
        return response;
    }
    
    
    /**
     *  [IMR] 교정 검증 :: 전체 cnt 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmCalibVerifiTotRowCnt.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmCalibVerifiTotRowCnt(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
        	StatisticsVO one = dpmService.getDpmCalibVerifiTotRowCnt(paramVO);
        	if(one != null) {
        		response.setTotRowCnt(one.getTotRowCnt());
        	}
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    
    
    /**
     *  [IMR] 교정/검증 결과 화면
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
     *  [MASK] 교정/검증 화면
     *  2022.12.15 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmMaskVerifiInfo.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmMaskVerifiInfo(StatisticsVO paramVO) {
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
    public String imrConfirm(@RequestParam Map<String, String> param, HttpServletRequest request) {
    	
    	logger.debug("param:" + param.toString());
    	//param:{elementId=adsdf_2.jpg, intvisionImr={"A":"Y","B":"Y","C":"Y","D":"Y","E":"Y","TM_RECV_YN":"Y","SMS_RECV_YN":"Y","DM_RECV_YN":"Y","EMAIL_RECV_YN":"Y","TM_OFFER_YN":"Y","SMS_OFFER_YN":"Y","DM_OFFER_YN":"Y","EMAIL_OFFER_YN":"Y"}, A=Y, B=Y, C=Y, D=Y, E=Y, TM_RECV_YN=N, SMS_RECV_YN=N, DM_RECV_YN=Y, EMAIL_RECV_YN=Y, TM_OFFER_YN=Y, EMAIL_OFFER_YN=Y, DM_OFFER_YN=Y, SMS_OFFER_YN=Y}
    	StatisticsVO paramVO = new StatisticsVO(); 
    	
    	int updCnt = 0;
    	JSONObject returnObj = new JSONObject();
    	try {
	        HttpSession session = request.getSession();
	        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
	        paramVO.setChgEno(loginVO.getChrrId());
	    	
	    	logger.info("getIntvisionImr ~~~:" + param.get("intvisionImr"));
	    	String intvisionImr = param.get("intvisionImr");
	    	String elementId  = param.get("elementId");
	    	paramVO.setElementId(elementId);
	    	
	    	String flag = param.get("flag");
	        if("upd".equals(flag)) {
	        	paramVO.setUserUpdateYn("01"); //수정
	    	} else {
	    		paramVO.setUserUpdateYn("02"); //변경없음
	    	}
	    	
	    	JSONObject jsonObjRoot = new JSONObject(param);
	    	jsonObjRoot.remove("elementId");
	    	jsonObjRoot.remove("intvisionImr");
	    	jsonObjRoot.remove("flag");
	    	
	    	JSONParser parser = new JSONParser();
	    	Object obj = parser.parse(intvisionImr);
	    	JSONObject jsonObj = (JSONObject) obj;
			
			Map<String, Object> map = jsonToMap(jsonObjRoot);
	        ObjectMapper om = new ObjectMapper();
	        om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true); //key로 정렬 설정
	        String jsonStr = om.writeValueAsString(map); 
	        logger.info(jsonStr);
	        paramVO.setIntvisionImr(jsonStr);
 	
	        // 데이터 비교
	        /*
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
	    	*/
	        
	        
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
    
    
    
    /**
     *  [MASK] 교정/검증 확정 처리
     * @param paramVO
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/dpm/maskConfirm.do")
    @ResponseBody
    public String maskConfirm(StatisticsVO paramVO, HttpServletRequest request) {
    	
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
	    	
	    	//String intvisionImr  = (String)jsonObjRoot.get("intvisionImr");
	    	jsonObjRoot.remove("elementId");
	    	jsonObjRoot.remove("intvisionImr");
	    	
	    	//TO DO
	    	// MASK 미완료 건 처리 어떻게 할지....
	    	//paramVO.setUserUpdateYn("02"); //변경없음
	    	paramVO.setUserUpdateYn("01"); //수정
	    	
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
    
    
    /**
     *  [MASK] 교정/검증 원복 처리
     * @param paramVO
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/dpm/maskRecover.do")
    @ResponseBody
    public String maskRecover(@RequestParam Map<String, String> param, HttpServletRequest request) {
    	
    	logger.debug("paramVO.toString() ~~~:" + param.toString());
    	
    	int updCnt = 0;
    	JSONObject returnObj = new JSONObject();
    	try {
	        HttpSession session = request.getSession();
	        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
	        param.put("chgEno", loginVO.getChrrId());
	        
	        updCnt = dpmService.updMaskRecover(param);
	    	
	    	returnObj.put("errMsg", "success");
    	} catch (Exception e) {
			logger.error("", e);
			returnObj.put("errMsg", e.getMessage());
			updCnt = 0;
		}
    	
    	returnObj.put("updCnt", updCnt);
    	
    	return returnObj.toJSONString();
    }
 
    
        
    
    /**
     *  [MASK] 교정/검증 원복 처리
     * @param paramVO
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/dpm/getDpmDailyProSummaryCnt.do")
    @ResponseBody
    public JSONObject getDpmDailyProSummaryCnt(StatisticsVO param, HttpServletRequest request) {
    	
    	logger.debug("paramVO.toString() ~~~:" + param.toString());
    	
    	JSONObject returnObj = new JSONObject();
    	
    	Map<String,Object> response = new HashMap<String,Object>();
    	
    	try {
	        HttpSession session = request.getSession();
	        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
	        	        
	        response = dpmService.getDpmDailyProSummaryCnt(param);
	        if( String.valueOf(response.get("TOT_CNT")).equals("0")) {
	        	response.put("NO_RECOG_CN", 0);
	        	response.put("RECOG_CN", 0);
	        	response.put("NOT_PRC_CN", 0);
	        	response.put("ERR_CN", 0);
	        }
	        
	        returnObj = new JSONObject(response);
	    		    	
    	} catch (Exception e) {
			logger.error("", e);
			
		}
    	   	
    	
    	return returnObj;
    }
    

    
    
    @RequestMapping(value = "/showFile.do")
    public void showFile(HttpServletRequest request, HttpServletResponse response) {
    	
    	String filename = request.getParameter("filename");
    	String elementid = request.getParameter("elementid");
        logger.info("filename:" + filename);
    	
    	String s = "XVARM_MAIN::2019102316515801::IMAGE";
    	
    	String sDecFile = encDownFolder + filename;
    	String sEncFile = encDownFolder + "enc_" + filename;
    	
    	Path srcPath = Paths.get(sDecFile);
		if (Files.notExists(srcPath, LinkOption.NOFOLLOW_LINKS)) {
			
			try {
	        	xvarmService.downloadFile(elementid, "enc_" + filename, encDownFolder);
	        	magicCryptoService.DecryptFile(sEncFile, sDecFile);
        	
	        } catch (Exception e) {
	        	logger.error("error!!", e);
	        }
		
		}			
  	
            	
    	logger.info("baseFolder" + baseFolder);
    	logger.info("noFileImg" + noFileImg);
    	
    	srcPath = Paths.get(sDecFile);
		if(Files.notExists(srcPath, LinkOption.NOFOLLOW_LINKS)) {
			 /*
	    	 String userDir = System.getProperty("user.dir");
	         String filePath = String.format("%s/test.png", userDir);
	         System.out.println("생성될 파일 : " + filePath);
	    	
	    	 CustomImage image = CustomImage.builder()
		                .imageWidth(600)
		                .imageHeight(600)
		                .imageColor("#5CDB95")
		                .build();
	        image.converting(
	        			filePath,
		                CustomTextType.title.getText("ERROR"),
		                //CustomTextType.title.getText("IMAGE ++"),
		                //CustomTextType.title.getText("CONVERTING"),
		                //CustomTextType.subtitle.getText("created by jogeum"),
		                CustomTextType.content.getText("파일이 경로에 없습니다.")
		                //CustomTextType.comment.getText("java 11 / lombok / awt / 나눔고딕")
		        );
	        Converter.getImage(filePath, response);
	        */
	        Converter.getImage(noFileImg, response);
		} else {
			Converter.getImage(baseFolder + filename, response);
		}
    }
    
    
    @RequestMapping(value = "/showPathFile.do")
    public void showPathFile(HttpServletRequest request, HttpServletResponse response) {
        
        String filename = request.getParameter("filename");
        logger.info("file path:" + filename);
    	
        /*
    	if (filename == null) {
    		System.err.println("usage example: /show_file.jsp?filename=/tif/jpeg/1.tif");
    		return;
    	} else if (filename.indexOf("..") >= 0) {
    		System.err.println("filename not allowed contains ..");
    		return;
    	}
    	*/
    	       
        
    	Path srcPath = Paths.get(filename);
		if(Files.notExists(srcPath, LinkOption.NOFOLLOW_LINKS)) {
			 /*
	    	 String userDir = System.getProperty("user.dir");
	         String filePath = String.format("%s/test.png", userDir);
	         System.out.println("생성될 파일 : " + filePath);
	    	
	    	 CustomImage image = CustomImage.builder()
		                .imageWidth(600)
		                .imageHeight(600)
		                .imageColor("#5CDB95")
		                .build();
	        image.converting(
	        			filePath,
		                CustomTextType.title.getText("ERROR"),
		                //CustomTextType.title.getText("IMAGE ++"),
		                //CustomTextType.title.getText("CONVERTING"),
		                //CustomTextType.subtitle.getText("created by jogeum"),
		                CustomTextType.content.getText("파일이 경로에 없습니다.")
		                //CustomTextType.comment.getText("java 11 / lombok / awt / 나눔고딕")
		        );
	        Converter.getImage(filePath, response);
	        */
	        Converter.getImage(noFileImg, response);
		} else {
			//Converter.getImage(baseFolder + filename, response);
			Converter.getImage(filename, response);
		}
    }
    
  //json을 받아 hashmap으로 변환하는 메소드
    public static Map<String, Object> jsonToMap(JSONObject json) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
	    
	    if(json != null) {
	        retMap = toMap(json);
	    }
	    return retMap;
	}

	//json객체 안에 또다른 json 객체가 있을 경우
	public static Map<String, Object> toMap(JSONObject object) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

	    @SuppressWarnings("rawtypes")
		Set keys = object.keySet();
	    @SuppressWarnings("unchecked")
		Iterator<String> keysItr = keys.iterator();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);
	        
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }
	        
	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}
	
    //json객체 안에 json 배열이 있을경우
	public static List<Object> toList(JSONArray array) throws Exception {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.size(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
 

    
     
}
