package com.minervasoft.backend.controller;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.type.TypeException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.minervasoft.backend.service.DpmService;
import com.minervasoft.backend.vo.AgentAssignVO;
import com.minervasoft.backend.vo.BizStatsTodayVO;
import com.minervasoft.backend.vo.BizStatsVO;
import com.minervasoft.backend.vo.CalibVerifiVo;
import com.minervasoft.backend.vo.ChrrGroupAuthVO;
import com.minervasoft.backend.vo.ChrrVO;
import com.minervasoft.backend.vo.CodeVO;
import com.minervasoft.backend.vo.CommonVO;
import com.minervasoft.backend.vo.DailyStatsVO;
import com.minervasoft.backend.vo.GroupAuthVO;
import com.minervasoft.backend.vo.ImageVerifyVO;
import com.minervasoft.backend.vo.LoginChrrVO;
import com.minervasoft.backend.vo.MaskingHistoryVO;
import com.minervasoft.backend.vo.MenuAuthVO;
import com.minervasoft.backend.vo.MenuVO;
import com.minervasoft.backend.vo.MonthlyStatsVO;
import com.minervasoft.backend.vo.ResponseCalibVerifiVo;
import com.minervasoft.backend.vo.ResponseSelListAgentAssignVO;
import com.minervasoft.backend.vo.ResponseSelListAuthVO;
import com.minervasoft.backend.vo.ResponseSelListBizStatsTodayVO;
import com.minervasoft.backend.vo.ResponseSelListBizStatsVO;
import com.minervasoft.backend.vo.ResponseSelListChrrGroupAuthVO;
import com.minervasoft.backend.vo.ResponseSelListChrrVO;
import com.minervasoft.backend.vo.ResponseSelListCodeVO;
import com.minervasoft.backend.vo.ResponseSelListDailyStatsVO;
import com.minervasoft.backend.vo.ResponseSelListImageVerifyVO;
import com.minervasoft.backend.vo.ResponseSelListMaskingHistoryVO;
import com.minervasoft.backend.vo.ResponseSelListMenuAuthVO;
import com.minervasoft.backend.vo.ResponseSelListMenuVO;
import com.minervasoft.backend.vo.ResponseSelListMonthlyStatsVO;
import com.minervasoft.backend.vo.ResponseSelListStepStatsVO;
import com.minervasoft.backend.vo.ResponseSelListXtromDailyStatsVO;
import com.minervasoft.backend.vo.ResponseSelOneChrrVO;
import com.minervasoft.backend.vo.ResponseSelOneLoginChrrVO;
import com.minervasoft.backend.vo.ResponseStatisticsVo;
import com.minervasoft.backend.vo.ResponseUserManageVo;
import com.minervasoft.backend.vo.StatisticsVO;
import com.minervasoft.backend.vo.StepStatsVO;
import com.minervasoft.backend.vo.UserManageVo;
import com.minervasoft.backend.vo.XtromDailyStatsVO;

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
    
     
}
