package com.minervasoft.backend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.minervasoft.backend.service.DpmService;
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
