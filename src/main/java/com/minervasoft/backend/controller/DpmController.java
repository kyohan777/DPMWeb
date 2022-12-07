package com.minervasoft.backend.controller;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.ibatis.type.TypeException;
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
import com.minervasoft.backend.vo.StepStatsVO;
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
    
    
    /**
     * 로그인ID 검사
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/login/loginCheckByCookie.do")
    @ResponseBody
    public ResponseSelOneLoginChrrVO selectLoginChkInfoByCookie(LoginChrrVO paramVO, HttpServletRequest request, ModelMap modelMap) {
        
    	ResponseSelOneLoginChrrVO response = new ResponseSelOneLoginChrrVO();
    	String sRemoteAddr = request.getRemoteAddr();
    	String sApiKey = "368B184727E89AB69FAF";
    	String sToken = "";
    	String userId = "";
    	int nResult = -1;
    	//SSO sso = new SSO(sApiKey)	;
    	InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getLocalHost();
			String ip = inetAddress.toString();
			HttpSession ss = request.getSession();

			if (ip.contains("KISESVT2N") || ip.contains("KISESVP1N")) {
				Cookie[] cs = request.getCookies();
				if(cs != null) {
					for(int i=0; i<cs.length; i++) {
						//logger.debug("cs[i].getName() : " + cs[i].getName());
						if(cs[i].getName().equals("ssotoken")) {
							if(cs[i].getValue()!="") {
								//logger.debug("cs[i].getValue() : " + cs[i].getValue());
								//nResult = sso.verifyToken(cs[i].getValue());
								//logger.debug("nResult: " + nResult);
								if(nResult >= 0) {
									//userId  = sso.getValueUserID();
									//logger.debug("userId : " + userId);
								}
							}
						}
					}
				}	
			}
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			response.setRsYn("N");
		}
    	
        
        try {
        	paramVO.setChrrId(userId);
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
        	

        	String sRemoteAddr = request.getRemoteAddr();
        	InetAddress inetAddress = InetAddress.getLocalHost();
        	String ip = inetAddress.toString();
        	//logger.debug("ip : " + ip);
        	if (ip.contains("KISESVT2N") || ip.contains("KISESVP1N")) {
        		String sID = "";
        		String sPwd = "";
        		String sApiKey = "368B184727E89AB69FAF";
        		int nResult = -1;
        		//SSO sso = new SSO(sApiKey)	;	
        		
        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);
        		sID = paramVO.getChrrId();
        		sPwd = paramVO.getChrrPwd();
        		//SsoAuthInfo authInfo = new SsoAuthInfo();
        		if (!sID.equals("keytool")) {
        			//authInfo = sso.authID(sID, sPwd, true, sRemoteAddr);
            		//nResult = sso.getLastError();
        		}
        		
        		
        		if (nResult >= 0 || sID.equals("keytool")) {
        			//세션정보저장후 메인페이지로 이동
                	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
                		request.getSession().setAttribute("loginInfo", loginInfoVO);
                		request.getSession().setMaxInactiveInterval(60*300);
                		
                		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
                		modelMap.addAttribute("chrrId", loginInfoVO.getChrrId());
                		modelMap.addAttribute("chrrNm", loginInfoVO.getChrrNm());
                		modelMap.addAttribute("contentPage", "firstView.jsp");
                		
                		returnPage = "dpm/main";
                	} else {
                		modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
                	}
        		}
        		else {
        			modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
        		}
        	}else {
        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);

            	//세션정보저장후 메인페이지로 이동
            	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
            		request.getSession().setAttribute("loginInfo", loginInfoVO);
            		request.getSession().setMaxInactiveInterval(60*300);
            		
            		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
            		modelMap.addAttribute("chrrId", loginInfoVO.getChrrId());
            		modelMap.addAttribute("chrrNm", loginInfoVO.getChrrNm());
            		modelMap.addAttribute("contentPage", "firstView.jsp");
            		
            		returnPage = "dpm/main";
            	} else {
            		modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
            	}
        		
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
            		modelMap.addAttribute("contentPage", "firstView.jsp");
            		
            		returnPage = "dpm/main";
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
    
    


    /********************************************* 
     * 분리보관
     *********************************************/
     
}
