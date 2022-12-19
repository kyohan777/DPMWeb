package com.minervasoft.backend.controller;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.type.TypeException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
public class DpmController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource(name = "DpmService")
    private DpmService dpmService;
    
    
    /********************************************* 
     * 로그인 및 공통  
     *********************************************/
    
    /**
     * 로그인ID 검사
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/login/loginCheck.do")
    @ResponseBody
    public ResponseSelOneLoginChrrVO selectLoginChkInfo(LoginChrrVO paramVO) {
        
    	ResponseSelOneLoginChrrVO response = new ResponseSelOneLoginChrrVO();
        
        try {
        	LoginChrrVO one = dpmService.selOneLoginChrr(paramVO);
            response.setSelOne(one);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    
//    @RequestMapping(value = "/login/getUserInfoSSO.do")
//    public String getUserInfoSSO(ChrrVO paramVO, HttpServletRequest request, ModelMap modelMap) {
//    	
//        try {
//        	
//
//        	String sRemoteAddr = request.getRemoteAddr();
//        	String sApiKey = "368B184727E89AB69FAF";
//        	InetAddress inetAddress = InetAddress.getLocalHost();
//        	String ip = inetAddress.toString();
//        	String sToken = "";
//        	int nResult = -1;
//        	logger.debug("ip : " + ip);
//        	if (ip.contains("10.254.12.106")) { //ip.contains("10.254.10.124")||
//        		SSO sso = new SSO(sApiKey)	;	
//        		SsoAuthInfo authInfo = new SsoAuthInfo();
//        		Cookie[] cs = request.getCookies();
//				if(cs != null) {
//					for(int i=0; i<cs.length; i++) {
//						logger.debug("cs[i].getName() : " + cs[i].getName());
//						if(cs[i].getName().equals("ssotoken")) {
//							if(cs[i].getValue()!="") {
//								logger.debug("cs[i].getValue() : " + cs[i].getValue());
//								nResult = sso.verifyToken(cs[i].getValue());
//								logger.debug("nResult: " + nResult);
//								if(nResult >= 0) {
//									sToken = cs[i].getValue();
//								}
//							}
//						}
//					}
//				}	
//				if (!sToken.equals("")) {
//					authInfo = sso.userView(sToken, sRemoteAddr);
//					
//					String sUserName = authInfo.getUserName();
//				}
//        		
//        	}
////        		String sID = "";
////        		String sPwd = "";
////        		String sApiKey = "368B184727E89AB69FAF";
////        		int nResult = -1;
////        		
////        		
////        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);
////        		sID = paramVO.getChrrId();
////        		sPwd = paramVO.getChrrPwd();
////        		
////        		authInfo = sso.authID(sID, sPwd, true, sRemoteAddr);
////        		nResult = sso.getLastError();
////        		if (nResult >= 0) {
////        			//세션정보저장후 메인페이지로 이동
////                	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
////                		request.getSession().setAttribute("loginInfo", loginInfoVO);
////                		request.getSession().setMaxInactiveInterval(60*300);
////                		
////                		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
////                		modelMap.addAttribute("chrrId", loginInfoVO.getChrrId());
////                		modelMap.addAttribute("chrrNm", loginInfoVO.getChrrNm());
////                		modelMap.addAttribute("contentPage", "firstView.jsp");
////                		
////                		returnPage = "dpm/main";
////                	} else {
////                		modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
////                	}
////        		}
////        		else {
////        			modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
////        		}
////        	}else {
//        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);
//
//            	//세션정보저장후 메인페이지로 이동
//            	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
//            		request.getSession().setAttribute("loginInfo", loginInfoVO);
//            		request.getSession().setMaxInactiveInterval(60*300);
//            		
//            		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
//            		modelMap.addAttribute("chrrId", loginInfoVO.getChrrId());
//            		modelMap.addAttribute("chrrNm", loginInfoVO.getChrrNm());
//            		modelMap.addAttribute("contentPage", "firstView.jsp");
//            		
//            		returnPage = "dpm/main";
//            	} else {
//            		modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
//            	}
//        		
//        	//}
//        	
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        
//        return returnPage;
//        
//    } 
    
    /**
     * 로그인처리
     * @param paramVO
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/login/login.do")
    public String login(LoginChrrVO paramVO, HttpServletRequest request, ModelMap modelMap) {
    	String returnPage = "dpm/login";
    	
        try {
        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);
            	//세션정보저장후 메인페이지로 이동
            	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
            		request.getSession().setAttribute("loginInfo", loginInfoVO);
            		request.getSession().setMaxInactiveInterval(60*300);
            		
            		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
            		returnPage = "dpm/firstView";
            	} else {
            		modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
            	}
        	
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return returnPage;
        
    } 
    
    
    @RequestMapping(value = "/login/loginSSO.do")
    public String login2(LoginChrrVO paramVO, HttpServletRequest request, ModelMap modelMap) {
    	String returnPage = "dpm/login";
    	
        try {
        	

        	String sRemoteAddr = request.getRemoteAddr();
        	InetAddress inetAddress = InetAddress.getLocalHost();
        	String ip = inetAddress.toString();
        	logger.debug("ip : " + ip);
        	
        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);

            	//세션정보저장후 메인페이지로 이동
            	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
            		request.getSession().setAttribute("loginInfo", loginInfoVO);
            		request.getSession().setMaxInactiveInterval(60*300);
            		
            		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
            		modelMap.addAttribute("chrrId", loginInfoVO.getChrrId());
            		modelMap.addAttribute("chrrNm", loginInfoVO.getChrrNm());
            		
            		returnPage = "dpm/dpmDayPro";
            	} else {
            		//modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
            	}
        		
        	
        	
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return returnPage;
        
    } 
    
    /**
     * 권한에 따른 메뉴목록조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListMenuAuth.do")
    @ResponseBody
    public ResponseSelListMenuAuthVO selListMenuAuth(MenuAuthVO paramVO) {
        
        ResponseSelListMenuAuthVO response = new ResponseSelListMenuAuthVO();
        
        try {
            List<MenuAuthVO> list = dpmService.selListMenuAuth(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<MenuAuthVO>());
        }
        
        return response;
    }
    
    /**
     * 코드목록조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListCode.do")
    @ResponseBody
    public ResponseSelListCodeVO selCodeList(CodeVO paramVO) {
        
        ResponseSelListCodeVO response = new ResponseSelListCodeVO();
        
        try {
            List<CodeVO> list = dpmService.selListCode(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<CodeVO>());
        }
        
        return response;
    }
    
    /**
     * 서버일자, 일시 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getServerDateTime.do")
    @ResponseBody
    public CommonVO getServerDateTime() {
        
    	CommonVO response = new CommonVO();
        
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
    	SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	String date = formatDate.format(System.currentTimeMillis());
    	String time = formatTime.format(System.currentTimeMillis());
    	
    	response.setServerDate(date);
    	response.setServerTime(time);
        
        return response;
    }

    /**
     * 브라우저명 리턴
     * @param request
     * @return
     */
    private String getBrowser(HttpServletRequest request) {

        String header =request.getHeader("User-Agent");
        if (header.contains("MSIE")) {
               return "MSIE";

        } else if(header.contains("Chrome")) {
               return "Chrome";

        } else if(header.contains("Opera")) {
               return "Opera";
        }

        return "Firefox";
    }
    
    /**
     * 엑셀출력 요청 header정보 set
     * @param request
     * @param response
     * @param fileName
     * @throws Exception
     */
    private void setExcelDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {
    	 String header = getBrowser(request);

    	 if (header.contains("MSIE")) {

  	        String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");

  	        response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");

  	 } else if (header.contains("Firefox")) {

  	        //String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

  	        //response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
  		 	String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");

	        response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");

  	 } else if (header.contains("Opera")) {

  	        String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

  	        response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");

  	 } else if (header.contains("Chrome")) {

  	        String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

  	        response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");

  	 }
       response.setCharacterEncoding("UTF-8");
       response.setContentType("application/vnd.ms-excel");
       response.setHeader("Pragma","public");
       response.setHeader("Expires","0");
    }
    
    
    /********************************************* 
     * 마스킹검증 
     *********************************************/
    /**
     * 이미지검증 :: 이미지검증 전체건수 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selOneImageVerifyTotRowCnt.do")
    @ResponseBody
    public ResponseSelListImageVerifyVO selOneImageVerifyTotRowCnt(ImageVerifyVO paramVO) {
    	ResponseSelListImageVerifyVO response = new ResponseSelListImageVerifyVO();
        
        try {
        	ImageVerifyVO one = dpmService.selOneImageVerifyTotRowCnt(paramVO);
            response.setTotRowCnt(one.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     * 이미지검증 :: 이미지검증현황 목록조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListImageVerify.do")
    @ResponseBody
    public ResponseSelListImageVerifyVO selListImageVerify(ImageVerifyVO paramVO) {
    	ResponseSelListImageVerifyVO response = new ResponseSelListImageVerifyVO();
        
        try {
            List<ImageVerifyVO> list = dpmService.selListImageVerify(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<ImageVerifyVO>());
        }
        
        return response;
    }
    
    /**
     * 이미지검증 :: 이미지검증현황 엑셀출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListImageVerifyExcel.do")
    public String selListImageVerifyExcel(ImageVerifyVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<ImageVerifyVO> list = new ArrayList<>();    	
    	CommonVO commonVO = getServerDateTime();
    	String filename = commonVO.getServerTime().concat("_이미지마스킹검증리스트.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);

        try {
        	ImageVerifyVO one = dpmService.selOneImageVerifyTotRowCnt(paramVO);
        	
        	int pageSize = 10000;
	    	int totRowCnt = one.getTotRowCnt() ;
	    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;	    	
	    	paramVO.setPageSize(pageSize);
	    	paramVO.setExcelDownYn("Y");
	    	
        	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
        		paramVO.setPageNumber(pageNumber);
        		List<ImageVerifyVO> listPage = dpmService.selListImageVerify(paramVO);
        		
        		list.addAll(listPage);
        	}
        	
        	modelMap.put("gridLabels", paramVO.getGridLabels());
        	modelMap.put("gridNames", paramVO.getGridNames());
        	modelMap.put("gridWidths", paramVO.getGridWidths());
        	modelMap.put("gridAligns", paramVO.getGridAligns());
        	modelMap.put("VO", "ImageVerifyVO");
        	modelMap.put("excelList", list);        	        	
        } catch(TypeException e) {
        	e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    	return "excelView";
    }
    
    /**
     * 이미지검증 :: 지문마스킹대상 수정(이미지조회 팝업)
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/updFpMaskObj.do")
    @ResponseBody
    public int updFpMaskObj(ImageVerifyVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.updFpMaskObj(paramVO);
		} catch (Exception e) {
			e.printStackTrace();
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    /**
     * 이미지검증 :: 지문마스킹이력 등록(이미지조회 팝업)
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/insFpMaskHis.do")
    @ResponseBody
    public int insFpMaskHis(MaskingHistoryVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getPrcmnEno())) paramVO.setPrcmnEno(loginVO.getChrrId());
        
    	try {
    		logger.debug("elementid : " + paramVO.getElementid());
    		updCnt = dpmService.insFpMaskHis(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }      
    
    
    /**
     * AGENT할당 :: AGENT할당 목록 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListAgentAssign.do")
    @ResponseBody
    public ResponseSelListAgentAssignVO selListAgentAssign(AgentAssignVO paramVO) {
        
        ResponseSelListAgentAssignVO response = new ResponseSelListAgentAssignVO();
        
        try {
            List<AgentAssignVO> list = dpmService.selListAgentAssign(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<AgentAssignVO>());
        }
        
        return response;
    }  
    
    /**
     * AGENT할당 :: AGENT할당 처리
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/updAgentAssign.do")
    @ResponseBody
    public int updAgentAssign(AgentAssignVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.updAgentAssign(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    /**
     * 담당자관리 :: 에이전트 저장
     * @param paramVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/dpm/saveChrrForAgent.do")
    @ResponseBody
    public int saveChrrForAgent(AgentAssignVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.saveChrrForAgent(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }    
    
    /**
     * 코드관리 :: 에이전트삭제
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/deleteAgent.do")
    @ResponseBody
    public int deleteAgent(AgentAssignVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.deleteAgent(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    /********************************************* 
     * 통계  
     *********************************************/
    /**
     * 업무별현황 :: 전일기준 업무별현황 조회
     * @return
     */
    @RequestMapping(value = "/dpm/selListBizStats.do")
    @ResponseBody
    public ResponseSelListBizStatsVO selListBizStats() {
        
        ResponseSelListBizStatsVO response = new ResponseSelListBizStatsVO();
        
        try {
            List<BizStatsVO> list = dpmService.selListBizStats();
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<BizStatsVO>());
        }
        
        return response;
    }
    
    /**
     * 업무별현황 :: 전일기준 업무별현황 엑셀출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListBizStatsExcel.do")
    public String selListBizStatsExcel(BizStatsVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<BizStatsVO> list = new ArrayList<>();    	
    	CommonVO commonVO = getServerDateTime();
    	String filename = commonVO.getServerTime().concat("_전일기준 업무별현황.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);

    	
        try {
        	paramVO.setExcelDownYn("Y");
        	list = dpmService.selListBizStats();
        	
        	modelMap.put("gridLabels", paramVO.getGridLabels());
        	modelMap.put("gridNames", paramVO.getGridNames());
        	modelMap.put("gridWidths", paramVO.getGridWidths());
        	modelMap.put("gridAligns", paramVO.getGridAligns());
        	modelMap.put("VO", "BizStatsVO");
        	modelMap.put("excelList", list);
        	        	        	
        } catch(TypeException e) {
        	e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }    	
        
    	return "excelView";
    }
    
    /**
     * 업무별현황 :: 당일 업무별 진행현황 조회
     * @return
     */
    @RequestMapping(value = "/dpm/selListBizStatsToday.do")
    @ResponseBody
    public ResponseSelListBizStatsTodayVO selListBizStatsToday() {
        
        ResponseSelListBizStatsTodayVO response = new ResponseSelListBizStatsTodayVO();
        
        try {
            List<BizStatsTodayVO> list = dpmService.selListBizStatsToday();
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<BizStatsTodayVO>());
        }
        
        return response;
    }
    
    /**
     * 업무별현황 :: 당일 업무별 진행현황 엑셀출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListBizStatsTodayExcel.do")
    public String selListBizStatsTodayExcel(BizStatsTodayVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<BizStatsTodayVO> list = new ArrayList<>();    	
    	CommonVO commonVO = getServerDateTime();
    	String filename = commonVO.getServerTime().concat("_당일 업무별 진행현황.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);

        try {
        	paramVO.setExcelDownYn("Y");
        	list = dpmService.selListBizStatsToday();
        	
        	modelMap.put("gridLabels", paramVO.getGridLabels());
        	modelMap.put("gridNames", paramVO.getGridNames());
        	modelMap.put("gridWidths", paramVO.getGridWidths());
        	modelMap.put("gridAligns", paramVO.getGridAligns());
        	modelMap.put("VO", "BizStatsTodayVO");
        	modelMap.put("excelList", list);        	        	
        } catch(TypeException e) {
        	e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    	return "excelView";
    }    
    
    /**
     * 일별현황 :: 일별현황 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListDailyStats.do")
    @ResponseBody
    public ResponseSelListDailyStatsVO selListDailyStats(DailyStatsVO paramVO) {

        ResponseSelListDailyStatsVO response = new ResponseSelListDailyStatsVO();
        
        try {
            List<DailyStatsVO> list = dpmService.selListDailyStats(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<DailyStatsVO>());
        }
        
        
        return response;
    }
    
    /**
     * 일별현황 :: 일별현황 엑셀출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListDailyStatsExcel.do")
    public String selListDailyStatsExcel(DailyStatsVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<DailyStatsVO> list = new ArrayList<>();    	
    	CommonVO commonVO = getServerDateTime();
    	String filename = commonVO.getServerTime().concat("_일별현황.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);

        try {
        	paramVO.setExcelDownYn("Y");
        	list = dpmService.selListDailyStats(paramVO);
        	
        	modelMap.put("gridLabels", paramVO.getGridLabels());
        	modelMap.put("gridNames", paramVO.getGridNames());
        	modelMap.put("gridWidths", paramVO.getGridWidths());
        	modelMap.put("gridAligns", paramVO.getGridAligns());
        	modelMap.put("VO", "DailyStatsVO");
        	modelMap.put("excelList", list);        	        	
        } catch(TypeException e) {
        	e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    	return "excelView";
    }     
    
    /**
     * 월별현황 :: 월별현황 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListMonthlyStats.do")
    @ResponseBody
    public ResponseSelListMonthlyStatsVO selListMonthlyStats(MonthlyStatsVO paramVO) {
        
        ResponseSelListMonthlyStatsVO response = new ResponseSelListMonthlyStatsVO();
        
        try {
            List<MonthlyStatsVO> list = dpmService.selListMonthlyStats(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<MonthlyStatsVO>());
        }
        
        return response;
    }
    
    /**
     * 월별현황 :: 월별현황 엑셀출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListMonthlyStatsExcel.do")
    public String selListMonthlyStatsExcel(MonthlyStatsVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<MonthlyStatsVO> list = new ArrayList<>();    	
    	CommonVO commonVO = getServerDateTime();
    	String filename = commonVO.getServerTime().concat("_월별현황.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);

        try {
        	paramVO.setExcelDownYn("Y");
        	list = dpmService.selListMonthlyStats(paramVO);
        	
        	modelMap.put("gridLabels", paramVO.getGridLabels());
        	modelMap.put("gridNames", paramVO.getGridNames());
        	modelMap.put("gridWidths", paramVO.getGridWidths());
        	modelMap.put("gridAligns", paramVO.getGridAligns());
        	modelMap.put("VO", "MonthlyStatsVO");
        	modelMap.put("excelList", list);        	        	
        } catch(TypeException e) {
        	e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    	return "excelView";
    }    
    
    /**
     * 마스킹이력조회 :: 마스킹이력 전체건수 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selOneMaskingHistoryTotRowCnt.do")
    @ResponseBody
    public ResponseSelListMaskingHistoryVO selOneMaskingHistoryTotRowCnt(MaskingHistoryVO paramVO) {

    	ResponseSelListMaskingHistoryVO response = new ResponseSelListMaskingHistoryVO();
        
        try {
            MaskingHistoryVO one = dpmService.selOneMaskingHistoryTotRowCnt(paramVO);
            response.setTotRowCnt(one.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     * 마스킹이력조회 :: 마스킹이력목록 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListMaskingHistory.do")
    @ResponseBody
    public ResponseSelListMaskingHistoryVO selListMaskingHistory(MaskingHistoryVO paramVO) {

    	ResponseSelListMaskingHistoryVO response = new ResponseSelListMaskingHistoryVO();
        
        try {
            List<MaskingHistoryVO> list = dpmService.selListMaskingHistory(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<MaskingHistoryVO>());
        }
        
        return response;
    }        
    
    /**
     * 마스킹이력조회 :: 마스킹이력 상세조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListMaskingHistoryDetail.do")
    @ResponseBody
    public ResponseSelListMaskingHistoryVO selListMaskingHistoryDetail(MaskingHistoryVO paramVO) {

    	ResponseSelListMaskingHistoryVO response = new ResponseSelListMaskingHistoryVO();
        
        try {
            List<MaskingHistoryVO> list = dpmService.selListMaskingHistoryDetail(paramVO);
            response.setSelList(list);
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<MaskingHistoryVO>());
        }
        
        return response;
    }
    
    /**
     * 마스킹이력조회 :: 마스킹이력 엑셀출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListMaskingHistoryExcel.do")
    public String selListMaskingHistoryExcel(MaskingHistoryVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<MaskingHistoryVO> list = new ArrayList<>();    	
    	CommonVO commonVO = getServerDateTime();
    	String filename = commonVO.getServerTime().concat("_마스킹이력조회.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);

        try {
        	MaskingHistoryVO one = dpmService.selOneMaskingHistoryTotRowCnt(paramVO);
        	
        	int pageSize = 10000;
	    	int totRowCnt = one.getTotRowCnt() ;
	    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;	    	
	    	paramVO.setPageSize(pageSize);
	    	paramVO.setExcelDownYn("Y");
	    	
        	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
        		paramVO.setPageNumber(pageNumber);
        		List<MaskingHistoryVO> listPage = dpmService.selListMaskingHistory(paramVO);
        		
        		list.addAll(listPage);
        	}
        	
        	modelMap.put("gridLabels", paramVO.getGridLabels());
        	modelMap.put("gridNames", paramVO.getGridNames());
        	modelMap.put("gridWidths", paramVO.getGridWidths());
        	modelMap.put("gridAligns", paramVO.getGridAligns());
        	modelMap.put("VO", "MaskingHistoryVO");
        	modelMap.put("excelList", list);        	        	
        } catch(TypeException e) {
        	e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    	return "excelView";
    }       
    
    
    /********************************************* 
     * 관리
     *********************************************/
    /**
     * 코드관리 :: 분류코드목록 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListCodeForManagement.do")
    @ResponseBody
    public ResponseSelListCodeVO selListCodeForManagement(CodeVO paramVO) {
        
        ResponseSelListCodeVO response = new ResponseSelListCodeVO();
        
        try {
            List<CodeVO> list = dpmService.selListCodeForManagement(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<CodeVO>());
        }
        
        return response;
    }
    
    /**
     * 코드관리 :: 상세분류코드목록 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListCodeDetailForManagement.do")
    @ResponseBody
    public ResponseSelListCodeVO selListCodeDetailForManagement(CodeVO paramVO) {
        
        ResponseSelListCodeVO response = new ResponseSelListCodeVO();
        
        try {
            List<CodeVO> list = dpmService.selListCodeDetailForManagement(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<CodeVO>());
        }
        
        return response;
    }
    
    /**
     * 코드관리 :: 코드저장
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/saveCode.do")
    @ResponseBody
    public int saveCode(CodeVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.saveCode(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    /**
     * 코드관리 :: 코드삭제
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/deleteCode.do")
    @ResponseBody
    public int deleteCode(CodeVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.deleteCode(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    /**
     * 코드관리 :: 상세코드저장
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/saveDetailCode.do")
    @ResponseBody
    public int saveDetailCode(CodeVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.saveDetailCode(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    /**
     * 코드관리 :: 코드삭제
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/deleteDetailCode.do")
    @ResponseBody
    public int deleteDetailCode(CodeVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.deleteDetailCode(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    /**
     * 담당자관리 :: 담당자목록조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListChrrForManagement.do")
    @ResponseBody
    public ResponseSelListChrrVO selListChrrForManagement(ChrrVO paramVO) {
        
        ResponseSelListChrrVO response = new ResponseSelListChrrVO();
        
        try {
            List<ChrrVO> list = dpmService.selListChrrForManagement(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<ChrrVO>());
        }
        
        return response;
    }    
    

    
    
    /**
     * 담당자관리 :: 담당자정보 저장
     * @param paramVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/dpm/saveChrrForMng.do")
    @ResponseBody
    public int saveChrrForMng(ChrrVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.saveChrrForMng(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }    
    
    /**
     * 그룹관리 :: 담당자목록 조회
     * @return
     */
    @RequestMapping(value = "/dpm/selListChrr.do")
    @ResponseBody
    public ResponseSelListChrrVO selListChrr() {
        
        ResponseSelListChrrVO response = new ResponseSelListChrrVO();
        
        try {
            List<ChrrVO> list = dpmService.selListChrr();
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<ChrrVO>());
        }
        
        return response;
    }
    
    /**
     * 그룹관리 :: 그룹목록 조회
     * @return
     */
    @RequestMapping(value = "/dpm/selListGroupAuth.do")
    @ResponseBody
    public ResponseSelListAuthVO selListGroupAuth() {
        
        ResponseSelListAuthVO response = new ResponseSelListAuthVO();
        
        try {
            List<GroupAuthVO> list = dpmService.selListGroupAuth();
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<GroupAuthVO>());
        }
        
        return response;
    }
    
    /**
     * 그룹관리 :: 사용자목록 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListChrrGroupAuth.do")
    @ResponseBody
    public ResponseSelListChrrGroupAuthVO selListChrrGroupAuth(ChrrGroupAuthVO paramVO) {
        
        ResponseSelListChrrGroupAuthVO response = new ResponseSelListChrrGroupAuthVO();
        
        try {
            List<ChrrGroupAuthVO> list = dpmService.selListChrrGroupAuth(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<ChrrGroupAuthVO>());
        }
        
        return response;
    } 
    
    /**
     * 그룹관리 :: 권한그룹 저장
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/saveGroupAuth.do")
    @ResponseBody
    public int saveGroupAuth(GroupAuthVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
    	
    	try {
    		updCnt = dpmService.saveGroupAuth(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    

    /**
     * 그룹관리 :: 사용자권한그룹 저장
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/saveChrrGroupAuth.do")
    @ResponseBody
    public int saveChrrGroupAuth(ChrrGroupAuthVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
    	
    	try {
    		updCnt = dpmService.saveChrrGroupAuth(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    /**
     * 권한관리 :: 권한목록 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListAuth.do")
    @ResponseBody
    public ResponseSelListAuthVO selListAuth(GroupAuthVO paramVO) {
        
        ResponseSelListAuthVO response = new ResponseSelListAuthVO();
        
        try {
            List<GroupAuthVO> list = dpmService.selListAuth(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<GroupAuthVO>());
        }
        
        return response;
    }    
    
    /**
     * 권한관리 :: 메뉴권한목록 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListMenuAuthForManagement.do")
    @ResponseBody
    public ResponseSelListMenuAuthVO selListMenuAuthForManagement(MenuAuthVO paramVO) {
        
    	ResponseSelListMenuAuthVO response = new ResponseSelListMenuAuthVO();
        
        try {
            List<MenuAuthVO> list = dpmService.selListMenuAuthForManagement(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<MenuAuthVO>());
        }
        
        return response;
    }
    
    /**
     * 권한관리 :: 메뉴권한 저장
     * @param paramVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/dpm/saveMenuAuth.do")
    @ResponseBody
    public int saveMenuAuth(MenuAuthVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
    	
    	try {
    		updCnt = dpmService.saveMenuAuth(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }    
    
    /**
     * 메뉴관리 :: 상위메뉴목록 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListMenu.do")
    @ResponseBody
    public ResponseSelListMenuVO selListMenu(MenuVO paramVO) {
        
        ResponseSelListMenuVO response = new ResponseSelListMenuVO();
        
        
        try {
            List<MenuVO> list = dpmService.selListMenu(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<MenuVO>());
        }
        
        return response;
    }
    
    /**
     * 메뉴관리 :: 하위메뉴목록 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/selListMenuForManagement.do")
    @ResponseBody
    public ResponseSelListMenuVO selListMenuForManagement(MenuVO paramVO) {
        
        ResponseSelListMenuVO response = new ResponseSelListMenuVO();
        
        try {
            List<MenuVO> list = dpmService.selListMenuForManagement(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<MenuVO>());
        }
        
        return response;
    }
    
    /**
     * 메뉴관리 :: 메뉴저장
     * @param paramVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/dpm/saveMenu.do")
    @ResponseBody
    public int saveMenu(MenuVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
    	
    	try {
    		updCnt = dpmService.saveMenu(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    /**
     * 담당자관리 :: 담당자정보 삭제
     * @param paramVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/dpm/deleteChrrForMng.do")
    @ResponseBody
    public int deleteChrrForMng(ChrrVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
        
    	try {
    		updCnt = dpmService.deleteChrrForMng(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }    
    
    /**
     * 그룹관리 :: 권한그룹 저장
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/deleteChrrGroupAuth.do")
    @ResponseBody
    public int deleteChrrGroupAuth(ChrrGroupAuthVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
    	
    	try {
    		updCnt = dpmService.deleteChrrGroupAuth(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    

    
    /**
     * 메뉴관리 :: 메뉴삭제
     * @param paramVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/dpm/deleteMenu.do")
    @ResponseBody
    public int deleteMenu(MenuVO paramVO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
    	int updCnt;
        
        if(StringUtils.isEmpty(paramVO.getChgEno())) paramVO.setChgEno(loginVO.getChrrId());
        if(StringUtils.isEmpty(paramVO.getRgEno())) paramVO.setChgEno(loginVO.getChrrId());
    	
    	try {
    		updCnt = dpmService.deleteMenu(paramVO);
		} catch (Exception e) {
			updCnt = 0;
		}
    	
    	return updCnt;
    }
    
    
    
    /********************************************* 
     * 기타
     *********************************************/    
    
    @RequestMapping(value = "/dpm/selOneChrr.do")
    @ResponseBody
    public ResponseSelOneChrrVO selOneChrr(ChrrVO paramVO) {
        
        ResponseSelOneChrrVO response = new ResponseSelOneChrrVO();
        
        try {
            ChrrVO one = dpmService.selOneChrr(paramVO);
            response.setSelOne(one);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }
    
    
    @RequestMapping(value = "/dpm/selListChrrMenu.do")
    @ResponseBody
    public ResponseSelListMenuVO selListChrrMenu(ChrrVO paramVO) {
        
        ResponseSelListMenuVO response = new ResponseSelListMenuVO();
        
        try {
            List<MenuVO> list = dpmService.selListChrrMenu(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<MenuVO>());
        }
        
        return response;
    }
    
    
    @RequestMapping(value = "/dpm/selListStepStats.do")
    @ResponseBody
    public ResponseSelListStepStatsVO selListStepStats() {
        
        ResponseSelListStepStatsVO response = new ResponseSelListStepStatsVO();
        
        try {
            List<StepStatsVO> list = dpmService.selListStepStats();
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<StepStatsVO>());
        }
        
        return response;
    }
    
    
    @RequestMapping(value = "/dpm/selListXtormDailyStats.do")
    @ResponseBody
    public ResponseSelListXtromDailyStatsVO selListXtormDailyStats(XtromDailyStatsVO paramVO) {
        
        ResponseSelListXtromDailyStatsVO response = new ResponseSelListXtromDailyStatsVO();
        
        try {
            List<XtromDailyStatsVO> list = dpmService.selListXtormDailyStats(paramVO);
            response.setSelList(list);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<XtromDailyStatsVO>());
        }
        
        return response;
    }
    
    


    /******************************************************************************************
     * 
     * 
     * 분리보관
     * 
     * 
     ******************************************************************************************/
    
    
    
    /**
     *  [IMR] 일일 처리 현황:: 일일 처리 현황 전체 cnt 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmDailyProInfoTotRowCnt.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmDailyProInfoTotRowCnt(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
        	StatisticsVO one = dpmService.getDpmDailyProInfoTotRowCnt(paramVO);
            response.setTotRowCnt(one.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  [IMR] 일일 처리 현황:: 일일 처리 현황 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmDailyProInfo.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmDailyProInfo(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
            List<StatisticsVO> list = dpmService.getDpmDailyProInfo(paramVO);
            for(StatisticsVO vo : list) {
                if(vo.getIntvisionImr() != null) {
                	//json string data 파싱하기
                	String json = vo.getIntvisionImr(); 
                	JSONParser parser = new JSONParser();
                	Object obj = parser.parse(json);
                	JSONObject jsonObj = (JSONObject) obj;
                	System.out.println(jsonObj);
                	
                	vo.setAyn((String)jsonObj.get("A"));
                	vo.setByn((String)jsonObj.get("B"));
                	vo.setCyn((String)jsonObj.get("C"));
                	vo.setDyn((String)jsonObj.get("D"));
                	vo.setEyn((String)jsonObj.get("E"));
                	vo.setTmRecvYn((String)jsonObj.get("TM_RECV_YN"));
                	vo.setSmsRecvYn((String)jsonObj.get("SMS_RECV_YN"));
                	vo.setDmRecvYn((String)jsonObj.get("DM_RECV_YN"));
                	vo.setEmailRecvYn((String)jsonObj.get("EMAIL_RECV_YN"));
                	vo.setTmOfferYn((String)jsonObj.get("TM_OFFER_YN"));
                	vo.setDmOfferYn((String)jsonObj.get("DM_OFFER_YN"));
                	vo.setEmailOfferYn((String)jsonObj.get("EMAIL_OFFER_YN"));
                }
            }
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
     *  [IMR] 일별 통계:: 일별 통계 전체 cnt 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmDayProInfoTotRowCnt.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmDayProInfoTotRowCnt(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
        	StatisticsVO one = dpmService.getDpmDayProInfoTotRowCnt(paramVO);
            response.setTotRowCnt(one.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  [IMR] 일별 통계:: 일별 통계 현황 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmDayProInfo.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmDayProInfo(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
            List<StatisticsVO> list = dpmService.getDpmDayProInfo(paramVO);
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
     *  [IMR] 월별 통계:: 월별 통계 전체 cnt 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmMonthProInfoTotRowCnt.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmMonthProInfoTotRowCnt(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
        	StatisticsVO one = dpmService.getDpmMonthProInfoTotRowCnt(paramVO);
            response.setTotRowCnt(one.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  [IMR] 월별 통계:: 일별 통계 현황 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmMonthProInfo.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmMonthProInfo(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
            List<StatisticsVO> list = dpmService.getDpmMonthProInfo(paramVO);
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
     *  [IMR] 월별 통계:: 월별 통계 전체 cnt 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getdpmImrResViewerInfoTotRowCnt.do")
    @ResponseBody
    public ResponseCalibVerifiVo getdpmImrResViewerInfoTotRowCnt(CalibVerifiVo paramVO) {
    	ResponseCalibVerifiVo response = new ResponseCalibVerifiVo();
        
        try {
        	CalibVerifiVo one = dpmService.getdpmImrResViewerInfoTotRowCnt(paramVO);
            response.setTotRowCnt(one.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  [IMR] 결과 열람자 이력 조회 화면
     *  2022.12.08 신규 개발 
     * @param CalibVerifiVo
     * @return
     */
    @RequestMapping(value = "/dpm/getdpmImrResViewerInfo.do")
    @ResponseBody
    public ResponseCalibVerifiVo getdpmImrResViewerInfo(CalibVerifiVo paramVO) {
    	ResponseCalibVerifiVo response = new ResponseCalibVerifiVo();
        
        try {
            List<CalibVerifiVo> list = dpmService.getdpmImrResViewerInfo(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<CalibVerifiVo>());
        }
        
        return response;
    }
    
    
    /**
     *  사용자 관리  전체 cnt 조회
     *  2022.12.12 신규 개발 
     * @param CalibVerifiVo
     * @return
     */
    @RequestMapping(value = "/dpm/getdpmUserManageInfoTotRowCnt.do")
    @ResponseBody
    public ResponseUserManageVo getdpmUserManageInfoTotRowCnt(UserManageVo paramVO) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
        
        try {
        	UserManageVo one = dpmService.getdpmUserManageInfoTotRowCnt(paramVO);
            response.setTotRowCnt(one.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  사용자 관리 조회
     *  2022.12.12 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getdpmUserManageInfo.do")
    @ResponseBody
    public ResponseUserManageVo getdpmUserManageInfo(UserManageVo paramVO) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
        
        try {
            List<UserManageVo> list = dpmService.getdpmUserManageInfo(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<UserManageVo>());
        }
        
        return response;
    }
    
    /**
     * 사용자 정보 등록
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/insertUserInfo.do")
    @ResponseBody
    public ResponseUserManageVo insertUserInfo(UserManageVo paramVO,HttpServletRequest request) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
    	HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
        paramVO.setRgId(loginVO.getChrrId());
        try {
        	dpmService.insertUserInfo(paramVO);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     * 사용자 정보 수정
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/updateUserInfo.do")
    @ResponseBody
    public ResponseUserManageVo updateUserInfo(UserManageVo paramVO,HttpServletRequest request) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
    	HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
        paramVO.setRgId(loginVO.getChrrId());
        try {
        	dpmService.updateUserInfo(paramVO);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     * 사용자 정보 삭제
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/deleteUserInfo.do")
    @ResponseBody
    public ResponseUserManageVo deleteUserInfo(UserManageVo paramVO,HttpServletRequest request) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
        try {
        	dpmService.deleteUserInfo(paramVO);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }
    
    
    /**
     * 일별 통계 > 엑셀 출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListDpmDayProExcel.do")
    public void selListDpmDayProExcel(StatisticsVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<StatisticsVO> list  = new ArrayList<>();    	
    	CommonVO commonVO 		 = getServerDateTime();
    	String filename 		 = commonVO.getServerTime().concat("_일별 통계.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);
    	 String title = "YYYY/MM/DD,대상,처리현황,'','',처리율,검증건수,'',금융안내,'',금융이외,'',보험제공,'',딜러제공,'',KB제공,'',수집-전화,'',수집-문자,'',수집-DM,'',수집-메일,'',제공-전화,'',제공-DM,'',제공-메일,'',제공-문자,''";
    	StatisticsVO one = dpmService.getDpmDayProInfoTotRowCnt(paramVO);
    	
    	int pageSize   = 10000;
    	int totRowCnt  = one.getTotRowCnt() ;
    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;
    	paramVO.setPageSize(pageSize);
    	
    	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
    		paramVO.setPageNumber(pageNumber);
    		List<StatisticsVO> listPage = dpmService.getDpmDayProInfo(paramVO);
    		list.addAll(listPage);
    	}

    	modelMap.put("gridLabels", paramVO.getGridLabels());
    	modelMap.put("gridNames",  paramVO.getGridNames());
    	modelMap.put("gridWidths", paramVO.getGridWidths());
    	modelMap.put("headerMergeYn","Y");
    	modelMap.put("mergeTitle", title);
    	modelMap.put("VO", "StatisticsVO");
    	modelMap.put("excelList", list);
    	
    	excelDownload(modelMap,request,response);
        
    }
    
    
    
    /**
     * 사용자 관리 > 엑셀 출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListUserInfoExcel.do")
    public void selListUserInfoExcel(UserManageVo paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	List<UserManageVo> list = new ArrayList<>();    	
    	CommonVO commonVO 		 = getServerDateTime();
    	String filename 		 = commonVO.getServerTime().concat("_사용자 정보.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);
    	UserManageVo one = dpmService.getdpmUserManageInfoTotRowCnt(paramVO);
    	
    	int pageSize   = 10000;
    	int totRowCnt  = one.getTotRowCnt() ;
    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;	    	
    	paramVO.setPageSize(pageSize);
    	
    	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
    		paramVO.setPageNumber(pageNumber);
    		List<UserManageVo> listPage = dpmService.getdpmUserManageInfo(paramVO);
    		list.addAll(listPage);
    	}
    	
    	modelMap.put("gridLabels", paramVO.getGridLabels());
    	modelMap.put("gridNames", paramVO.getGridNames());
    	modelMap.put("gridWidths", paramVO.getGridWidths());
    	modelMap.put("headerMergeYn","N");
    	modelMap.put("VO", "UserManageVo");
    	modelMap.put("excelList", list);
    	
    	excelDownload(modelMap,request,response);
        
    }
    
    
    /**
     * 월별 통계 > 엑셀 출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListDpmMonthProExcel.do")
    public void selListDpmMonthProExcel(StatisticsVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<StatisticsVO> list  = new ArrayList<>();    	
    	CommonVO commonVO 		 = getServerDateTime();
    	String filename 		 = commonVO.getServerTime().concat("_월별 통계.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);
    	StatisticsVO one = dpmService.getDpmMonthProInfoTotRowCnt(paramVO);
    	String title = "YYYY/MM,대상,처리현황,'','',처리율,검증건수,'',금융안내,'',금융이외,'',보험제공,'',딜러제공,'',KB제공,'',수집-전화,'',수집-문자,'',수집-DM,'',수집-메일,'',제공-전화,'',제공-DM,'',제공-메일,'',제공-문자,''";
    	int pageSize   = 10000;
    	int totRowCnt  = one.getTotRowCnt() ;
    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;
    	paramVO.setPageSize(pageSize);
    	
    	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
    		paramVO.setPageNumber(pageNumber);
    		List<StatisticsVO> listPage = dpmService.getDpmMonthProInfo(paramVO);
    		list.addAll(listPage);
    	}
    	
    	modelMap.put("gridLabels", paramVO.getGridLabels());
    	modelMap.put("gridNames",  paramVO.getGridNames());
    	modelMap.put("gridWidths", paramVO.getGridWidths());
    	modelMap.put("headerMergeYn","Y");
    	modelMap.put("mergeTitle", title);
    	modelMap.put("VO", "StatisticsVO");
    	modelMap.put("excelList", list);
    	
    	excelDownload(modelMap,request,response);
    	
        
    }
    
    
    /**
     * 일일 처리 현황 > 엑셀 출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListDpmDailyProExcel.do")
    public void selListDpmDailyProExcel(StatisticsVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<StatisticsVO> list  = new ArrayList<>();    	
    	CommonVO commonVO 		 = getServerDateTime();
    	String filename 		 = commonVO.getServerTime().concat("_일일 처리 현황.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);
    	StatisticsVO one = dpmService.getDpmDailyProInfoTotRowCnt(paramVO);
    	int pageSize   = 10000;
    	int totRowCnt  = one.getTotRowCnt() ;
    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;
    	paramVO.setPageSize(pageSize);
    	
    	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
    		paramVO.setPageNumber(pageNumber);
    		List<StatisticsVO> listPage = dpmService.getDpmDailyProInfo(paramVO);
    		 for(StatisticsVO vo : listPage) {
                 if(vo.getIntvisionImr() != null) {
                 	//json string data 파싱하기
                 	String json = vo.getIntvisionImr(); 
                 	JSONParser parser = new JSONParser();
                 	Object obj = parser.parse(json);
                 	JSONObject jsonObj = (JSONObject) obj;
                 	System.out.println(jsonObj);
                 	
                 	vo.setAyn((String)jsonObj.get("A"));
                 	vo.setByn((String)jsonObj.get("B"));
                 	vo.setCyn((String)jsonObj.get("C"));
                 	vo.setDyn((String)jsonObj.get("D"));
                 	vo.setEyn((String)jsonObj.get("E"));
                 	vo.setTmRecvYn((String)jsonObj.get("TM_RECV_YN"));
                 	vo.setSmsRecvYn((String)jsonObj.get("SMS_RECV_YN"));
                 	vo.setDmRecvYn((String)jsonObj.get("DM_RECV_YN"));
                 	vo.setEmailRecvYn((String)jsonObj.get("EMAIL_RECV_YN"));
                 	vo.setTmOfferYn((String)jsonObj.get("TM_OFFER_YN"));
                 	vo.setDmOfferYn((String)jsonObj.get("DM_OFFER_YN"));
                 	vo.setEmailOfferYn((String)jsonObj.get("EMAIL_OFFER_YN"));
                 	vo.setSmsOfferYn((String)jsonObj.get("SMS_OFFER_YN"));
                 }
             }
    		list.addAll(listPage);
    	}
    	
    	modelMap.put("gridLabels", paramVO.getGridLabels());
    	modelMap.put("gridNames",  paramVO.getGridNames());
    	modelMap.put("gridWidths", paramVO.getGridWidths());
    	modelMap.put("headerMergeYn","N");
    	modelMap.put("VO", "StatisticsVO");
    	modelMap.put("excelList", list);
    	
    	excelDownload(modelMap,request,response);
    	
        
    }
    
    
    @SuppressWarnings("unchecked")
	protected final void excelDownload(Map<String,Object> model, HttpServletRequest request , HttpServletResponse response) throws Exception {
    	logger.debug("excelDownload start!!!!");
    	List<Object> rowList  = new ArrayList<>();    	
    	String gridLabels    = (String) model.get("gridLabels");
        String gridNames     = (String) model.get("gridNames");
        String gridWidths    = (String) model.get("gridWidths");
        String headerMergeYn = (String) model.get("headerMergeYn");
        rowList = (ArrayList<Object>) model.get("excelList");
        String vo =  (String) model.get("VO");
        Class<?> voClass = Class.forName("com.minervasoft.backend.vo." + vo);                        
        List<Method> methodList = new ArrayList<Method>();            
        String[] nameList = gridNames.split(",");
        
        for(String name : nameList) {
        	String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        		methodList.add(voClass.getMethod(methodName,null));
        }
        
        try(Workbook workbook = new XSSFWorkbook()) {
        	
            Sheet sheet = workbook.createSheet();
            String[] widthList = gridWidths.split(",");
            for(int i=0; i<widthList.length; i++) {
            	sheet.setColumnWidth(i, Integer.parseInt(widthList[i]) * 50);
            }
            
            int rowNo = 0;
            Row headerRow = sheet.createRow(rowNo++);
            
            if(headerMergeYn =="Y") {
            	 String title = (String) model.get("mergeTitle");
            	 String[] titleList = title.split(",");
            	 //셀 병합
                sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));  //일자
                sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));  //대상
                sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));  //처리현황
                sheet.addMergedRegion(new CellRangeAddress(0,1,5,5));  //처리율
                sheet.addMergedRegion(new CellRangeAddress(0,0,6,7));  //검증건수
                sheet.addMergedRegion(new CellRangeAddress(0,0,8,9));  //금융안내
                sheet.addMergedRegion(new CellRangeAddress(0,0,10,11));//금융이외
                sheet.addMergedRegion(new CellRangeAddress(0,0,12,13));//보험제공
                sheet.addMergedRegion(new CellRangeAddress(0,0,14,15));//딜러제공
                sheet.addMergedRegion(new CellRangeAddress(0,0,16,17));//KB제공
                sheet.addMergedRegion(new CellRangeAddress(0,0,18,19));//수집-전화
                sheet.addMergedRegion(new CellRangeAddress(0,0,20,21));//수집-문자
                sheet.addMergedRegion(new CellRangeAddress(0,0,22,23));//수집-DM
                sheet.addMergedRegion(new CellRangeAddress(0,0,24,25));//수집-메일
                sheet.addMergedRegion(new CellRangeAddress(0,0,26,27));//제공-전화
                sheet.addMergedRegion(new CellRangeAddress(0,0,28,29));//제공-DM
                sheet.addMergedRegion(new CellRangeAddress(0,0,30,31));//제공-메일
                sheet.addMergedRegion(new CellRangeAddress(0,0,32,33));//제공-문자
                int headNo= 0;
                //merge header 생성
                for(String name : titleList) {
                	if(name != "") {
                		headerRow.createCell(headNo).setCellValue(name);
                	}else {
                		headerRow.createCell(headNo);
                	}
                	headNo++;
                }
            }
            
            String[] labelList = gridLabels.split(",");
            headerRow = sheet.createRow(rowNo++);
	    	if(labelList != null) {
	    		for(int i=0; i<labelList.length; i++) {
	    			Cell cell = headerRow.createCell(i);
	    			cell.setCellValue(labelList[i]);
	    		}
	    	}
	    	
	    	if(rowList != null) {
            	for(int i = 0; i < rowList.size(); i++) {
            		Row aRow = (Row) sheet.createRow(rowNo++);
            		setEachRow(aRow, rowList.get(i), methodList);
            	}
            		
            }
     
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch(TypeException e) {
        	e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 행 데이터 생성
     * @param aRow
     * @param vo
     * @param methodList
     * @throws Exception
     */
    private void setEachRow(Row aRow, Object vo, List<Method> methodList) throws Exception {
    	for(int i=0; i<methodList.size(); i++) {
    		Cell cell = aRow.createCell(i);
    		if(!methodList.get(i).invoke(vo).equals(null)) {
    			String val = methodList.get(i).invoke(vo).toString();
    			cell.setCellValue(val);
    		}
        		
    	}
    }	
    
    
     
}
