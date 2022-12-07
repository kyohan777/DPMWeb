package com.minervasoft.backend.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class WebController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /********************************************* 
     * 로그인 및 공통  
     *********************************************/    
    
    /**
     * 로그인
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/index.ui")
    public String index(ModelMap modelMap) {        
        try {

        } catch(Exception e) {
            e.printStackTrace();
        }        
        
        logger.debug("Called login");
        
        return "dpm/login";
    }
    
    /**
     * 로그인 후 첫화면 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/firstView.ui")
    public String firstView(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/firstView";
    }      
    
    /**
     * 에러페이지(EXCEPTION 발생 등) 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/error/error.ui")
    public String error(ModelMap modelMap, HttpServletRequest request) {
    	
    	Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
    	
        try {        	
        	if(inputFlashMap != null) {
        		
        		Map<String, Object> errInfoMap = (Map) inputFlashMap.get("error");
        		
        		modelMap.addAttribute("MESSAGE", errInfoMap.get("MESSAGE"));
        		modelMap.addAttribute("REQUEST_URI", errInfoMap.get("REQUEST_URI"));
        		modelMap.addAttribute("STATUS_CODE", errInfoMap.get("STATUS_CODE"));
        		modelMap.addAttribute("EXCEPTION_TYPE", errInfoMap.get("EXCEPTION_TYPE"));
        		modelMap.addAttribute("EXCEPTION", errInfoMap.get("EXCEPTION"));
        		modelMap.addAttribute("SERVLET_NAME", errInfoMap.get("SERVLET_NAME"));
        		modelMap.addAttribute("chrrId", errInfoMap.get("chrrId"));
        	}        	
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "error/error";
    }     
    
    /********************************************* 
     * 마스킹검증 
     *********************************************/
    /**
     * dpm1010 이미지 검증 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm1010.ui")
    public String dpm1010(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm1010";
    }
    
    /**
     * dpm1010Pop 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm0010Pop.ui")
    public String dpm0010Pop(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm0010Pop";
    }    
    
    /**
     * dpm1020 AGENT 할당 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm1020.ui")
    public String dpm1020(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm1020";
    }
    
    /**
     * dpm1030 이관DB초기화 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm1030.ui")
    public String dpm1030(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm1030";
    }    
    
    /********************************************* 
     * 통계  
     *********************************************/
    /**
     * dpm2010 업무별현황 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm2010.ui")
    public String dpm2010(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm2010";
    }
    
    /**
     * dpm2020 마스킹단계별현황 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm2020.ui")
    public String dpm2020(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm2020";
    }
    
    /**
     * dpm2030 일별현황 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm2030.ui")
    public String dpm2030(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm2030";
    }
    
    /**
     * dpm2040 월별현황 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm2040.ui")
    public String dpm2040(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm2040";
    }    
    
    /**
     * dpm2050 마스킹 이력조회 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm2050.ui")
    public String dpm2050(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm2050";
    }
    
    /**
     * dpm2060 Xtorm 일별 처리현황 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm2060.ui")
    public String dpm2060(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm2060";
    }     
  
    /********************************************* 
     * 관리
     *********************************************/    
    /**
     * dpm3010 코드관리 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm3010.ui")
    public String dpm3010(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm3010";
    }
    
    /**
     * dpm3020 담당자관리 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm3020.ui")
    public String dpm3020(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm3020";
    }
    
    /**
     * dpm3030 그룹관리 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm3030.ui")
    public String dpm3030(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm3030";
    }
    
    /**
     * dpm3040 권한관리 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm3040.ui")
    public String dpm3040(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm3040";
    }
    
    /**
     * dpm3050 메뉴관리 
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/dpm/dpm3050.ui")
    public String dpm3050(ModelMap modelMap) {
        try {        	
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "dpm/dpm3050";
    }     
    
}
